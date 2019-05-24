package hitron.web;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import hitron.forwarding.ForwardingRule;
import hitron.forwarding.ForwardingRules;
import hitron.forwarding.ForwardingStatus;

public class ApiTest {

	private Api api;
	private Random random = new Random();
	private static final String username = "cusadmin";
	private static final String password = "658476611523";

	@Before
	public void initApi() {
		api = new Api("http://192.168.0.1");
	}

	@AfterClass
	public static void restoreRules() {
		Api api = new Api("http://192.168.0.1");
		ForwardingStatus defaultStatus = new ForwardingStatus(
				"{ \"rulesOnOff\":\"Enabled\", \"privateLan\":\"192.168.0.1\", \"subMask\":\"255.255.255.0\", \"HttpPort\":\"8080\", \"HttpsPort\":\"8181\", \"TelnetPort\":\"2323\", \"SshPort\":\"22\", \"forwardingRuleStatus\":\"1\" }");
		ForwardingRules defaultRules = new ForwardingRules(
				"[{\"ruleIndex\":1,\"appName\":\"Skype-UDP-at-192.168.0.11%3A48545-%283986%29\",\"pubStart\":\"48545\",\"pubEnd\":\"48545\",\"priStart\":\"48545\",\"priEnd\":\"48545\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":2,\"appName\":\"Skype-TCP-at-192.168.0.11%3A48545-%283986%29\",\"pubStart\":\"48545\",\"pubEnd\":\"48545\",\"priStart\":\"48545\",\"priEnd\":\"48545\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":3,\"appName\":\"uTorrent-%28TCP%29\",\"pubStart\":\"54893\",\"pubEnd\":\"54893\",\"priStart\":\"54893\",\"priEnd\":\"54893\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":4,\"appName\":\"uTorrent-%28UDP%29\",\"pubStart\":\"54893\",\"pubEnd\":\"54893\",\"priStart\":\"54893\",\"priEnd\":\"54893\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":5,\"appName\":\"RDP\",\"pubStart\":\"3396\",\"pubEnd\":\"3396\",\"priStart\":\"3389\",\"priEnd\":\"3389\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.6\",\"remoteIpStar\":\"72.136.0.12\",\"remoteIpEnd\":\"72.143.255.253\",\"remoteOnOff\":\"Specific\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":6,\"appName\":\"Transmission-at-51413\",\"pubStart\":\"51413\",\"pubEnd\":\"51413\",\"priStart\":\"51413\",\"priEnd\":\"51413\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.18\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":7,\"appName\":\"Transmission-at-51413-51413\",\"pubStart\":\"51413\",\"pubEnd\":\"51413\",\"priStart\":\"51413\",\"priEnd\":\"51413\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.18\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"}]");
		try {
			api.login(username, password);
			api.setForwardingStatus(defaultStatus);
			api.setForwardingRules(defaultRules);
		} finally {
			api.close();
		}
	}

	private ForwardingRule getRandomRule() {
		String randomName = String.format("Rule_%d", Math.abs(random.nextInt()));
		int randomPort = random.nextInt(65534) + 1;
		String init = String.format(
				"{\"ruleIndex\":1,\"appName\":\"%1$s\",\"pubStart\":\"%2$d\",\"pubEnd\":\"%2$d\",\"priStart\":\"%2$d\",\"priEnd\":\"%2$d\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"OFF\"}",
				randomName, randomPort);
		ForwardingRule rule = new ForwardingRule(init);
		return rule;
	}

	@Test
	public void whenLoginThenReturnTrue() {
		boolean result = false;
		try {
			result = api.login(username, password);
		} finally {
			api.close();
		}
		assertThat(result, is(true));
	}

	@Test
	public void whenLoginThenCsrfTokenSaved() {
		try {
			api.login(username, password);
		} finally {
			api.close();
		}
		assertThat(api.getCsrfToken(), matchesPattern("\\d{20}"));
	}

	@Test
	public void whenGetForwardingStatusThenStatusReturned() {
		ForwardingStatus status = null;
		try {
			api.login(username, password);
			status = api.getForwardingStatus();
		} finally {
			api.close();
		}
		assertThat(status, is(notNullValue()));
	}

	@Test
	public void whenSetForwardingStatusThenStatusUpdated() {
		ForwardingStatus status = null;
		boolean enabledOld = false;
		try {
			api.login(username, password);
			status = api.getForwardingStatus();
			enabledOld = status.getEnabled();
			status.setEnabled(!enabledOld);
			api.setForwardingStatus(status);
			status = api.getForwardingStatus();
		} finally {
			api.close();
		}
		assertThat(status.getEnabled(), not(enabledOld));
	}

	@Test
	public void whenGetForwardingRulesThenRulesReturned() {
		ForwardingRules rules = null;
		try {
			api.login(username, password);
			rules = api.getForwardingRules();
		} finally {
			api.close();
		}
		assertThat(rules, is(notNullValue()));
	}

	@Test
	public void whenAddForwardingRuleThenRulePresent() {
		ForwardingRules rules = null;
		ForwardingRule newRule = getRandomRule();
		try {
			api.login(username, password);
			rules = api.getForwardingRules();
			rules.add(newRule);
			api.setForwardingRules(rules);
			rules = api.getForwardingRules();
		} finally {
			api.close();
		}
		assertThat(rules.getRules(), hasItem(newRule));
	}

	@Test
	public void whenRemoveForwardingRuleThenRuleNotPresent() {
		ForwardingRule newRule = getRandomRule();
		ForwardingRules rulesAfterRemoval = null;
		try {
			api.login(username, password);
			ForwardingRules rules = api.getForwardingRules();
			rules.add(newRule);
			api.setForwardingRules(rules);
			rules.remove(rules.getRules().indexOf(newRule));
			api.setForwardingRules(rules);
			rulesAfterRemoval = api.getForwardingRules();
		} finally {
			api.close();
		}
		assertThat(rulesAfterRemoval.getRules(), not(hasItem(newRule)));
	}

}
