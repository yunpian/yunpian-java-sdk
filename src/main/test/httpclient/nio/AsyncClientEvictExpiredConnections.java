package httpclient.nio;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.conn.NHttpClientConnectionManager;
import org.apache.http.nio.reactor.ConnectingIOReactor;

public class AsyncClientEvictExpiredConnections {

    public static void main(String[] args) throws Exception {
        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
        PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
        cm.setMaxTotal(100);
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setConnectionManager(cm).build();
        try {
            httpclient.start();

            // create an array of URIs to perform GETs on
            String[] urisToGet = { "http://hc.apache.org/", "http://hc.apache.org/httpcomponents-core-ga/",
                    "http://hc.apache.org/httpcomponents-client-ga/", };

            IdleConnectionEvictor connEvictor = new IdleConnectionEvictor(cm);
            connEvictor.start();

            final CountDownLatch latch = new CountDownLatch(urisToGet.length);
            for (final String uri : urisToGet) {
                final HttpGet httpget = new HttpGet(uri);
                httpclient.execute(httpget, new FutureCallback<HttpResponse>() {

                    @Override
                    public void completed(final HttpResponse response) {
                        latch.countDown();
                        System.out.println(httpget.getRequestLine() + "->" + response.getStatusLine());
                    }

                    @Override
                    public void failed(final Exception ex) {
                        latch.countDown();
                        System.out.println(httpget.getRequestLine() + "->" + ex);
                    }

                    @Override
                    public void cancelled() {
                        latch.countDown();
                        System.out.println(httpget.getRequestLine() + " cancelled");
                    }

                });
            }
            latch.await();

            // Sleep 10 sec and let the connection evictor do its job
            Thread.sleep(20000);

            // Shut down the evictor thread
            connEvictor.shutdown();
            connEvictor.join();

        } finally {
            httpclient.close();
        }
    }

    public static class IdleConnectionEvictor extends Thread {

        private final NHttpClientConnectionManager connMgr;

        private volatile boolean shutdown;

        public IdleConnectionEvictor(NHttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 5 sec
                        connMgr.closeIdleConnections(5, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }

}
