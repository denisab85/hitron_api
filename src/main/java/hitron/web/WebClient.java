package hitron.web;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class WebClient {

	private final String homeUrl;

	private final CloseableHttpClient client = HttpClients.createDefault();

	public WebClient(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public String get(String url) {
		String result = null;
		try {
			HttpGet get = new HttpGet(homeUrl + url);
			result = getBody(client.execute(get));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String post(String url, String request) {
		String result = null;
		try {
			HttpPost post = new HttpPost(homeUrl + url);
			post.setEntity(new StringEntity(request));
			HttpResponse response = client.execute(post);
			result = getBody(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void close() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getBody(HttpResponse response) {
		String result = "";
		HttpEntity respEntity = response.getEntity();
		if (respEntity != null) {
			try {
				result = EntityUtils.toString(respEntity, "utf-8");
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
