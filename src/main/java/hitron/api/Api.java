package hitron.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hitron.forwarding.ForwardingRule;
import hitron.forwarding.ForwardingStatus;
import hitron.web.WebClient;

public class Api {

	private WebClient webClient;
	private String csrfToken;

	private static final String LOGIN_URI = "/goform/login";
	private static final String CSRF_URI = "/data/getCsrfToken.asp";
	private static final String GET_FW_STATUS_URI = "/data/getForwardingStatus.asp";
	private static final String GET_FW_RULES_URI = "/data/getForwardingRules.asp";
	private static final String SET_FW_STATUS_URI = "/goform/Firewall";
	private static final String SET_FW_RULES_URI = "/goform/PfwCollection";

	private static final String TOKEN_PATTERN = "\\{\\s*\\\"token\\\"\\s*:\\s*\\\"(\\d+)\\\"\\s*\\}";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private final CloseableHttpClient client = HttpClients.createDefault();

	public Api(String homeUrl, String username, String password) {
		webClient = new WebClient(homeUrl);
		login(username, password);
		csrfToken = getCsrfToken();
	}

	public String getCsrfToken() {
		if (csrfToken == null) {
			String response = webClient.get(CSRF_URI);
			Pattern record = Pattern.compile(TOKEN_PATTERN);
			Matcher matcher = record.matcher(response);
			if (matcher.find()) {
				csrfToken = matcher.group(1);
			}
		}
		return csrfToken;
	}

	public ForwardingStatus getForwardingStatus() {
		return new ForwardingStatus(webClient.get(GET_FW_STATUS_URI));
	}

	public List<ForwardingRule> getForwardingRules() {
		try {
			return objectMapper.readValue(webClient.get(GET_FW_RULES_URI), new TypeReference<List<ForwardingRule>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setForwardingStatus(ForwardingStatus status) {
		String body = String.format("model=%s&csrf_token=%s", encodeValue(status.toString()), csrfToken);
		webClient.post(SET_FW_STATUS_URI, body);
	}

	public void setForwardingRules(List<ForwardingRule> rules) {
		try {
			String body = String.format("model=%s&csrf_token=%s&_method=PUT", encodeValue(objectMapper.writeValueAsString(rules)), csrfToken);
			webClient.post(SET_FW_RULES_URI, body);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean login(String username, String password) {
		String request = String.format("user=%s&pwd=%s&rememberMe=0&pwdCookieFlag=0", username, password);
		String response = webClient.post(LOGIN_URI, request);
		return response != null && response.equals("index.htm#wizard_all/m/0/s/0");
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
}
