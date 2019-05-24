package hitron.forwarding;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import hitron.forwarding.ForwardingRule;
import hitron.forwarding.ForwardingRules;

public class ForwardingRulesTest {

	@Test
	public void whenDefaultToString() {
		ForwardingRules fr = new ForwardingRules();
		assertThat(fr.toString(), is("[]"));
	}

	@Test
	public void whenAddRdpThenCorrectToString() {
		ForwardingRules rules = new ForwardingRules();
		ForwardingRule rule = new ForwardingRule(1, true, "RDP", 3396, 3396, 3389, 3389, "TCP", "Specific", "192.168.0.6", "72.136.0.12",
				"72.143.255.253");
		rules.add(rule);
		assertThat(rules.toString(), is(
				"[{\"ruleIndex\":1,\"appName\":\"RDP\",\"pubStart\":\"3396\",\"pubEnd\":\"3396\",\"priStart\":\"3389\",\"priEnd\":\"3389\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.6\",\"remoteIpStar\":\"72.136.0.12\",\"remoteIpEnd\":\"72.143.255.253\",\"remoteOnOff\":\"Specific\",\"ruleOnOff\":\"ON\"}]"));
	}

	@Test
	public void whenFromStringThenToStringCorrect() {
		String string = "[{\"ruleIndex\":1,\"appName\":\"Skype-UDP-at-192.168.0.11%3A48545-%283986%29\",\"pubStart\":\"48545\",\"pubEnd\":\"48545\",\"priStart\":\"48545\",\"priEnd\":\"48545\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":2,\"appName\":\"Skype-TCP-at-192.168.0.11%3A48545-%283986%29\",\"pubStart\":\"48545\",\"pubEnd\":\"48545\",\"priStart\":\"48545\",\"priEnd\":\"48545\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":3,\"appName\":\"uTorrent-%28TCP%29\",\"pubStart\":\"54893\",\"pubEnd\":\"54893\",\"priStart\":\"54893\",\"priEnd\":\"54893\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":4,\"appName\":\"uTorrent-%28UDP%29\",\"pubStart\":\"54893\",\"pubEnd\":\"54893\",\"priStart\":\"54893\",\"priEnd\":\"54893\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":5,\"appName\":\"RDP\",\"pubStart\":\"3396\",\"pubEnd\":\"3396\",\"priStart\":\"3389\",\"priEnd\":\"3389\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.6\",\"remoteIpStar\":\"72.136.0.12\",\"remoteIpEnd\":\"72.143.255.253\",\"remoteOnOff\":\"Specific\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":6,\"appName\":\"Transmission-at-51413\",\"pubStart\":\"51413\",\"pubEnd\":\"51413\",\"priStart\":\"51413\",\"priEnd\":\"51413\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.18\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":7,\"appName\":\"Transmission-at-51413-51413\",\"pubStart\":\"51413\",\"pubEnd\":\"51413\",\"priStart\":\"51413\",\"priEnd\":\"51413\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.18\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"}]";
		ForwardingRules frs = new ForwardingRules(string);
		assertThat(frs.toString().replace(" ", ""), is(string));
	}

	@Test
	public void whenFromStringNotSortedThenToStringSorted() {
		String stringNotSorted = "[{\"ruleIndex\":2,\"appName\":\"Skype-TCP-at-192.168.0.11%3A48545-%283986%29\",\"pubStart\":\"48545\",\"pubEnd\":\"48545\",\"priStart\":\"48545\",\"priEnd\":\"48545\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":5,\"appName\":\"RDP\",\"pubStart\":\"3396\",\"pubEnd\":\"3396\",\"priStart\":\"3389\",\"priEnd\":\"3389\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.6\",\"remoteIpStar\":\"72.136.0.12\",\"remoteIpEnd\":\"72.143.255.253\",\"remoteOnOff\":\"Specific\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":7,\"appName\":\"Transmission-at-51413-51413\",\"pubStart\":\"51413\",\"pubEnd\":\"51413\",\"priStart\":\"51413\",\"priEnd\":\"51413\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.18\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":1,\"appName\":\"Skype-UDP-at-192.168.0.11%3A48545-%283986%29\",\"pubStart\":\"48545\",\"pubEnd\":\"48545\",\"priStart\":\"48545\",\"priEnd\":\"48545\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":3,\"appName\":\"uTorrent-%28TCP%29\",\"pubStart\":\"54893\",\"pubEnd\":\"54893\",\"priStart\":\"54893\",\"priEnd\":\"54893\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":4,\"appName\":\"uTorrent-%28UDP%29\",\"pubStart\":\"54893\",\"pubEnd\":\"54893\",\"priStart\":\"54893\",\"priEnd\":\"54893\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":6,\"appName\":\"Transmission-at-51413\",\"pubStart\":\"51413\",\"pubEnd\":\"51413\",\"priStart\":\"51413\",\"priEnd\":\"51413\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.18\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"}]";
		String stringSorted = "[{\"ruleIndex\":1,\"appName\":\"Skype-UDP-at-192.168.0.11%3A48545-%283986%29\",\"pubStart\":\"48545\",\"pubEnd\":\"48545\",\"priStart\":\"48545\",\"priEnd\":\"48545\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":2,\"appName\":\"Skype-TCP-at-192.168.0.11%3A48545-%283986%29\",\"pubStart\":\"48545\",\"pubEnd\":\"48545\",\"priStart\":\"48545\",\"priEnd\":\"48545\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":3,\"appName\":\"uTorrent-%28TCP%29\",\"pubStart\":\"54893\",\"pubEnd\":\"54893\",\"priStart\":\"54893\",\"priEnd\":\"54893\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":4,\"appName\":\"uTorrent-%28UDP%29\",\"pubStart\":\"54893\",\"pubEnd\":\"54893\",\"priStart\":\"54893\",\"priEnd\":\"54893\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":5,\"appName\":\"RDP\",\"pubStart\":\"3396\",\"pubEnd\":\"3396\",\"priStart\":\"3389\",\"priEnd\":\"3389\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.6\",\"remoteIpStar\":\"72.136.0.12\",\"remoteIpEnd\":\"72.143.255.253\",\"remoteOnOff\":\"Specific\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":6,\"appName\":\"Transmission-at-51413\",\"pubStart\":\"51413\",\"pubEnd\":\"51413\",\"priStart\":\"51413\",\"priEnd\":\"51413\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.18\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"},{\"ruleIndex\":7,\"appName\":\"Transmission-at-51413-51413\",\"pubStart\":\"51413\",\"pubEnd\":\"51413\",\"priStart\":\"51413\",\"priEnd\":\"51413\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.18\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"}]";
		ForwardingRules frs = new ForwardingRules(stringNotSorted);
		assertThat(frs.toString().replace(" ", ""), is(stringSorted));
	}

}
