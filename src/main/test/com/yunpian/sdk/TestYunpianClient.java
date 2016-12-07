/**
 * 
 */
package com.yunpian.sdk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dzh
 * @date Dec 6, 2016 1:05:20 AM
 * @since 1.2.0
 */
public class TestYunpianClient {

    static final Logger LOG = LoggerFactory.getLogger(TestYunpianClient.class);

    @Test
    @Ignore
    public void yunpianClientTest() {
        int count = 10000;
        final AtomicLong calc = new AtomicLong(count);
        long st = System.currentTimeMillis();

        final YunpianClient clnt = new YunpianClient().init();
        ExecutorService exec = Executors.newFixedThreadPool(10);
        try {
            while (calc.get() > 0) {
                exec.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            calc.decrementAndGet();
                            Future<HttpResponse> f = clnt.post("http://localhost", "");
                            EntityUtils.toString(f.get().getEntity(), YunpianConf.HTTP_CHARSET_DEFAULT);
                        } catch (Exception e) {
                            LOG.error(e.getMessage(), e.fillInStackTrace());
                            calc.set(0);
                        }
                    }
                });

                Thread.sleep(2);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.fillInStackTrace());
        } finally {
            try {
                exec.shutdown();
                exec.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOG.warn(e.getMessage());
            }
            clnt.close();
        }

        long et = System.currentTimeMillis();
        long time = (et - st) / 1000;
        long qps = count / time;
        LOG.info("exec count-{} time-{}s qps-{}", count, time, qps);
    }

}
