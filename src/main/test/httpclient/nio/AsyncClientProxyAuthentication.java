package httpclient.nio;

import java.util.concurrent.Future;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class AsyncClientProxyAuthentication {

	public static void main(String[] args) throws Exception {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope("someproxy", 8080),
				new UsernamePasswordCredentials("username", "password"));
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setDefaultCredentialsProvider(credsProvider)
				.build();
		try {
			httpclient.start();
			HttpHost proxy = new HttpHost("someproxy", 8080);
			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			HttpGet httpget = new HttpGet("https://issues.apache.org/");
			httpget.setConfig(config);
			Future<HttpResponse> future = httpclient.execute(httpget, null);
			HttpResponse response = future.get();
			System.out.println("Response: " + response.getStatusLine());
			System.out.println("Shutting down");
		} finally {
			httpclient.close();
		}
	}

}
