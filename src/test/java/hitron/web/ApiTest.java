package hitron.web;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static util.Utils.getProp;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hitron.api.Api;
import hitron.forwarding.ForwardingRule;
import hitron.forwarding.ForwardingStatus;
import hitron.system.SysInfo;
import hitron.system.SystemModel;
import hitron.wireless.WirelesSsid;

public class ApiTest {

	private static final String ROUTER_IP = getProp("hitron.host");
	private static final String USERNAME = getProp("hitron.user");
	private static final String PASSWORD = getProp("hitron.password");

	private static ObjectMapper objectMapper = new ObjectMapper();

	private static ForwardingStatus defaultStatus;
	private static List<ForwardingRule> defaultRules;

	private static Api api;

	private final Random random = new Random();

	@Before
	public void initApi() {
		api = new Api("http://" + ROUTER_IP, USERNAME, PASSWORD);
	}

	@After
	public void closeApi() {
		api.close();
	}

	@BeforeClass
	public static void backupRules() throws IOException {
		api = new Api("http://" + ROUTER_IP, USERNAME, PASSWORD);
		try {
			defaultStatus = api.getForwardingStatus();
			defaultRules = api.getForwardingRules();
		} finally {
			api.close();
		}
	}

	@AfterClass
	public static void restoreRules() throws IOException {
		api = new Api("http://" + ROUTER_IP, USERNAME, PASSWORD);
		try {
			api.setForwardingStatus(defaultStatus);
			api.setForwardingRules(defaultRules);
		} finally {
			api.close();
		}
	}

	private ForwardingRule getRandomRule() {
		ForwardingRule result = null;
		String randomName = String.format("Rule_%d", Math.abs(random.nextInt()));
		int randomPort = random.nextInt(65534) + 1;
		String json = String.format(
				"{\"ruleIndex\":1,\"appName\":\"%1$s\",\"pubStart\":\"%2$d\",\"pubEnd\":\"%2$d\",\"priStart\":\"%2$d\",\"priEnd\":\"%2$d\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"OFF\"}",
				randomName, randomPort);
		try {
			result = objectMapper.readValue(json, ForwardingRule.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Test
	public void whenLoginThenCsrfTokenSaved() {
		assertThat(api.getCsrfToken(), matchesPattern("\\d{20}"));
	}

	@Test
	public void whenGetForwardingStatusThenStatusReturned() {
		ForwardingStatus status = api.getForwardingStatus();
		assertThat(status, is(notNullValue()));
	}

	@Test
	public void whenSetForwardingStatusThenStatusUpdated() {
		ForwardingStatus status = null;
		boolean enabledOld = false;
		status = api.getForwardingStatus();
		enabledOld = status.isRulesOnOff();
		status.setRulesOnOff(!enabledOld);
		api.setForwardingStatus(status);
		status = api.getForwardingStatus();
		assertThat(status.isRulesOnOff(), not(enabledOld));
	}

	@Test
	public void whenGetSystemModelThenSystemModelReturned() {
		Api apiNoAuth = new Api("http://" + ROUTER_IP);
		SystemModel info = apiNoAuth.getSystemModel();
		assertThat(info, is(notNullValue()));
	}

	@Test
	public void whenGetSysInfoThenSysInfoReturned() {
		SysInfo info = api.getSysInfo();
		assertThat(info, is(notNullValue()));
	}

	@Test
	public void whenGetWirelesSsidThenWirelesSsidReturned() {
		WirelesSsid info = api.getWirelesSsid();
		assertThat(info, is(notNullValue()));
	}

	@Test
	public void whenGetForwardingRulesThenRulesReturned() {
		List<ForwardingRule> rules = api.getForwardingRules();
		assertThat(rules, is(notNullValue()));
	}

	@Test
	public void whenAddForwardingRuleThenRulePresent() throws JsonProcessingException {
		List<ForwardingRule> rules = null;
		ForwardingRule newRule = getRandomRule();
		rules = api.getForwardingRules();
		rules.add(newRule);
		api.setForwardingRules(rules);
		rules = api.getForwardingRules();
		rules.contains(newRule);
		assertThat(rules, hasItem(newRule));
	}

	@Test
	public void whenRemoveForwardingRuleThenRuleNotPresent() throws JsonProcessingException {
		ForwardingRule newRule = getRandomRule();
		List<ForwardingRule> rulesAfterRemoval = null;
		List<ForwardingRule> rules = api.getForwardingRules();
		rules.add(newRule);
		api.setForwardingRules(rules);
		rules.remove(rules.indexOf(newRule));
		api.setForwardingRules(rules);
		rulesAfterRemoval = api.getForwardingRules();
		assertThat(rulesAfterRemoval, not(hasItem(newRule)));
	}

}
