package httpclient.nio;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpPipeliningClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.protocol.BasicAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;

public class AsyncClientPipelinedStreaming {

    public static void main(final String[] args) throws Exception {
        CloseableHttpPipeliningClient httpclient = HttpAsyncClients.createPipelining();
        try {
            httpclient.start();

            HttpHost targetHost = new HttpHost("localhost", 8080);
            HttpGet[] resquests = { new HttpGet("/docs/index.html"), new HttpGet("/docs/introduction.html"),
                    new HttpGet("/docs/setup.html"), new HttpGet("/docs/config/index.html") };

            List<MyRequestProducer> requestProducers = new ArrayList<MyRequestProducer>();
            List<MyResponseConsumer> responseConsumers = new ArrayList<MyResponseConsumer>();
            for (HttpGet request : resquests) {
                requestProducers.add(new MyRequestProducer(targetHost, request));
                responseConsumers.add(new MyResponseConsumer(request));
            }

            Future<List<Boolean>> future = httpclient.execute(targetHost, requestProducers, responseConsumers, null);
            future.get();
            System.out.println("Shutting down");
        } finally {
            httpclient.close();
        }
        System.out.println("Done");
    }

    static class MyRequestProducer extends BasicAsyncRequestProducer {

        private final HttpRequest request;

        MyRequestProducer(final HttpHost target, final HttpRequest request) {
            super(target, request);
            this.request = request;
        }

        @Override
        public void requestCompleted(final HttpContext context) {
            super.requestCompleted(context);
            System.out.println();
            System.out.println("Request sent: " + this.request.getRequestLine());
            System.out.println("=================================================");
        }
    }

    static class MyResponseConsumer extends AsyncCharConsumer<Boolean> {

        private final HttpRequest request;

        MyResponseConsumer(final HttpRequest request) {
            this.request = request;
        }

        @Override
        protected void onResponseReceived(final HttpResponse response) {
            System.out.println();
            System.out
                    .println("Response received: " + response.getStatusLine() + " -> " + this.request.getRequestLine());
            System.out.println("=================================================");
        }

        @Override
        protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
            while (buf.hasRemaining()) {
                System.out.print(buf.get());
            }
        }

        @Override
        protected void releaseResources() {
        }

        @Override
        protected Boolean buildResult(final HttpContext context) {
            System.out.println();
            System.out.println("=================================================");
            System.out.println();
            return Boolean.TRUE;
        }

    }

}
