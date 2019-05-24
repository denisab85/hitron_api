package hitron.forwarding;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import hitron.forwarding.ForwardingRule;

public class ForwardingRuleTest {

	@Test
	public void whenToStringThenAllCorrect() {
		ForwardingRule fr = new ForwardingRule(1, true, "RDP", 3396, 3396, 3389, 3389, "TCP", "Specific", "192.168.0.6", "72.136.0.12",
				"72.143.255.253");
		assertThat(fr.toString(), is(
				"{\"ruleIndex\":1,\"appName\":\"RDP\",\"pubStart\":\"3396\",\"pubEnd\":\"3396\",\"priStart\":\"3389\",\"priEnd\":\"3389\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.6\",\"remoteIpStar\":\"72.136.0.12\",\"remoteIpEnd\":\"72.143.255.253\",\"remoteOnOff\":\"Specific\",\"ruleOnOff\":\"ON\"}"));
	}

	@Test
	public void whenFromStringThenToStringCorrect() {
		String string = "{\"ruleIndex\":1,\"appName\":\"Skype-UDP-at-192.168.0.11%3A48545-%283986%29\",\"pubStart\":\"48545\",\"pubEnd\":\"48545\",\"priStart\":\"48545\",\"priEnd\":\"48545\",\"protocal\":\"UDP\",\"localIpAddr\":\"192.168.0.11\",\"remoteIpStar\":\"0.0.0.0\",\"remoteIpEnd\":\"255.255.255.255\",\"remoteOnOff\":\"Any\",\"ruleOnOff\":\"ON\"}";
		ForwardingRule fr = new ForwardingRule(string);
		assertThat(fr.toString(), is(string));
	}

}
