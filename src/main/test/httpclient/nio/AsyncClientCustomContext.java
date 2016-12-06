package httpclient.nio;

import java.util.List;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class AsyncClientCustomContext {

	public final static void main(String[] args) throws Exception {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			// Create a local instance of cookie store
			CookieStore cookieStore = new BasicCookieStore();

			// Create local HTTP context
			HttpClientContext localContext = HttpClientContext.create();
			// Bind custom cookie store to the local context
			localContext.setCookieStore(cookieStore);

			HttpGet httpget = new HttpGet("http://localhost/");
			System.out.println("Executing request " + httpget.getRequestLine());

			httpclient.start();

			// Pass local context as a parameter
			Future<HttpResponse> future = httpclient.execute(httpget, localContext, null);

			// Please note that it may be unsafe to access HttpContext instance
			// while the request is still being executed

			HttpResponse response = future.get();
			System.out.println("Response: " + response.getStatusLine());
			List<Cookie> cookies = cookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				System.out.println("Local cookie: " + cookies.get(i));
			}
			System.out.println("Shutting down");
		} finally {
			httpclient.close();
		}
	}

}
