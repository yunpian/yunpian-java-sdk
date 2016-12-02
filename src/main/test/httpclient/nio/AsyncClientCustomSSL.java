package httpclient.nio;

import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContexts;

public class AsyncClientCustomSSL {

	public final static void main(String[] args) throws Exception {
		// KeyStore trustStore =
		// KeyStore.getInstance(KeyStore.getDefaultType());
		// FileInputStream instream = new FileInputStream(new
		// File("my.keystore"));
		// try {
		// trustStore.load(instream, "nopassword".toCharArray());
		// } finally {
		// instream.close();
		// }
		// // Trust own CA and all self-signed certs
		// SSLContext sslcontext =
		// SSLContexts.custom().loadTrustMaterial(trustStore, new
		// TrustSelfSignedStrategy())
		// .build();
		SSLContext sslcontext = SSLContexts.createDefault();
		// Allow TLSv1 protocol only
		SSLIOSessionStrategy sslSessionStrategy = new SSLIOSessionStrategy(sslcontext, new String[] { "TLSv1" }, null,
				SSLIOSessionStrategy.getDefaultHostnameVerifier());
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setSSLStrategy(sslSessionStrategy).build();
		try {
			httpclient.start();
			HttpGet request = new HttpGet("https://github.com/dzh");
			Future<HttpResponse> future = httpclient.execute(request, null);
			HttpResponse response = future.get();
			System.out.println("Response: " + response.getStatusLine());
			System.out.println("Shutting down");
		} finally {
			httpclient.close();
		}
		System.out.println("Done");
	}

}
