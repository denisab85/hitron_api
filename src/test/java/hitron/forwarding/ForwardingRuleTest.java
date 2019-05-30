package hitron.forwarding;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hitron.forwarding.ForwardingRule;

public class ForwardingRuleTest {

	private static final String json = "{\"ruleIndex\":1,\"appName\":\"RDP\",\"pubStart\":\"3396\",\"pubEnd\":\"3397\",\"priStart\":\"3389\",\"priEnd\":\"3390\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.6\",\"remoteIpStar\":\"72.136.0.12\",\"remoteIpEnd\":\"72.143.255.253\",\"remoteOnOff\":\"Specific\",\"ruleOnOff\":\"ON\"}";
	private static final ForwardingRule rule = new ForwardingRule(1, "RDP", 3396, 3397, 3389, 3390, "TCP", "192.168.0.6", "72.136.0.12",
			"72.143.255.253", true, true);

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void whenToStringThenAllCorrect() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(rule), is(json));
	}

	@Test
	public void whenFromStringThenEquals() throws IOException {
		ForwardingRule actual = objectMapper.readValue(json, ForwardingRule.class);
		assertThat(actual, is(rule));
	}

}
