package httpclient.nio;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpPipeliningClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class AsyncClientPipelined {

	public static void main(final String[] args) throws Exception {
		CloseableHttpPipeliningClient httpclient = HttpAsyncClients.createPipelining();
		try {
			httpclient.start();

			HttpHost targetHost = new HttpHost("localhost", 8080);
			HttpGet[] resquests = { new HttpGet("/docs/index.html"), new HttpGet("/docs/introduction.html"),
					new HttpGet("/docs/setup.html"), new HttpGet("/docs/config/index.html") };

			Future<List<HttpResponse>> future = httpclient.execute(targetHost, Arrays.<HttpRequest>asList(resquests),
					null);
			List<HttpResponse> responses = future.get();
			System.out.println(responses);

			System.out.println("Shutting down");
		} finally {
			httpclient.close();
		}
		System.out.println("Done");
	}

}
