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
import hitron.forwarding.ForwardingRule;
import hitron.forwarding.ForwardingStatus;
import hitron.status.SysInfo;
import hitron.web.WebClient;
import static util.Utils.*;

public class Api {

	private WebClient webClient;
	private String csrfToken;

	private static final String TOKEN_PATTERN = "\\{\\s*\\\"token\\\"\\s*:\\s*\\\"(\\d+)\\\"\\s*\\}";

	private final CloseableHttpClient client = HttpClients.createDefault();

	public Api(String homeUrl, String username, String password) {
		webClient = new WebClient(homeUrl);
		login(username, password);
		csrfToken = getCsrfToken();
	}

	public String getCsrfToken() {
		if (csrfToken == null) {
			String response = webClient.get(getUri("CSRF"));
			Pattern record = Pattern.compile(TOKEN_PATTERN);
			Matcher matcher = record.matcher(response);
			if (matcher.find()) {
				csrfToken = matcher.group(1);
			}
		}
		return csrfToken;
	}

	public SysInfo getSysInfo() {
		try {
			List<SysInfo> statusList = ForwardingStatus.getObjectMapper().readValue(webClient.get(getUri("GET_SYSINFO")),
					new TypeReference<List<SysInfo>>() {
					});
			return statusList.get(0);
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ForwardingStatus getForwardingStatus() {
		try {
			List<ForwardingStatus> statusList = ForwardingStatus.getObjectMapper().readValue(webClient.get(getUri("GET_FW_STATUS")),
					new TypeReference<List<ForwardingStatus>>() {
					});
			return statusList.get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ForwardingRule> getForwardingRules() {
		try {
			return ForwardingRule.getObjectMapper().readValue(webClient.get(getUri("GET_FW_RULES")), new TypeReference<List<ForwardingRule>>() {
			});
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setForwardingStatus(ForwardingStatus status) {
		try {
			String body = String.format("model=%s&csrf_token=%s", encodeValue(ForwardingStatus.getObjectMapper().writeValueAsString(status)),
					csrfToken);
			webClient.post(getUri("SET_FW_STATUS"), body);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void setForwardingRules(List<ForwardingRule> rules) {
		try {
			String body = String.format("model=%s&csrf_token=%s&_method=PUT", encodeValue(ForwardingRule.getObjectMapper().writeValueAsString(rules)),
					csrfToken);
			webClient.post(getUri("SET_FW_RULES"), body);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		logout();
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean login(String username, String password) {
		String request = String.format("user=%s&pwd=%s&rememberMe=0&pwdCookieFlag=0", username, password);
		String response = webClient.post(getUri("LOGIN"), request);

		if (response.contains("Up to the session limit:"))
			throw new SessionLimitException(response);

		return response != null && response.equals("index.htm#wizard_all/m/0/s/0");
	}

	private void logout() {
		webClient.get(getUri("LOGOUT"));
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
