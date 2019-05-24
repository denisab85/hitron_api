package hitron.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import hitron.forwarding.ForwardingRules;
import hitron.forwarding.ForwardingStatus;
import lombok.Getter;

public class Api {

	private String homeUrl;

	@Getter
	private String csrfToken;

	private static final String TOKEN_PATTERN = "\\{\\s*\\\"token\\\"\\s*:\\s*\\\"(\\d+)\\\"\\s*\\}";
	private static final String LOGIN_URI = "/goform/login";
	private static final String CSRF_URI = "/data/getCsrfToken.asp";
	private static final String GET_FW_STATUS_URI = "/data/getForwardingStatus.asp";
	private static final String GET_FW_RULES_URI = "/data/getForwardingRules.asp";
	private static final String SET_FW_STATUS_URI = "/goform/Firewall";
	private static final String SET_FW_RULES_URI = "/goform/PfwCollection";

	private final CloseableHttpClient client = HttpClients.createDefault();

	public Api(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public boolean login(String username, String password) {
		boolean result = false;
		String request = String.format("user=%s&pwd=%s&rememberMe=0&pwdCookieFlag=0", username, password);
		String response = post(LOGIN_URI, request);
		if (response != null) {
			result = response.equals("index.htm#wizard_all/m/0/s/0");
			retrieveCsrfToken();
			result &= (csrfToken != null);
		}
		return result;
	}

	private void retrieveCsrfToken() {
		String response = get(CSRF_URI);
		Pattern record = Pattern.compile(TOKEN_PATTERN);
		Matcher matcher = record.matcher(response);
		if (matcher.find()) {
			csrfToken = matcher.group(1);
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

	public ForwardingStatus getForwardingStatus() {
		return new ForwardingStatus(get(GET_FW_STATUS_URI));
	}

	public ForwardingRules getForwardingRules() {
		return new ForwardingRules(get(GET_FW_RULES_URI));
	}

	private String encodeValue(String value) {
		String result = null;
		try {
			value = value.replace(" ", "");
			result = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setForwardingStatus(ForwardingStatus status) {
		String body = String.format("model=%s&csrf_token=%s", encodeValue(status.toString()), csrfToken);
		post(SET_FW_STATUS_URI, body);
	}

	public void setForwardingRules(ForwardingRules rules) {
		String body = String.format("model=%s&csrf_token=%s&_method=PUT", encodeValue(rules.toString()), csrfToken);
		post(SET_FW_RULES_URI, body);
	}

	private String get(String url) {
		String result = null;
		try {
			HttpGet get = new HttpGet(homeUrl + url);
			result = getBody(client.execute(get));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String post(String url, String request) {
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
}
