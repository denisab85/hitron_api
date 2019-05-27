package hitron.forwarding;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import hitron.forwarding.ForwardingStatus;

public class ForwardingStatusTest {

	private static final String json = "{\"rulesOnOff\":\"Enabled\",\"privateLan\":\"192.168.0.1\",\"subMask\":\"255.255.255.0\",\"HttpPort\":\"8080\",\"HttpsPort\":\"8181\",\"TelnetPort\":\"2323\",\"SshPort\":\"22\",\"forwardingRuleStatus\":\"1\"}";
	private static final ForwardingStatus status = new ForwardingStatus();

	@Test
	public void whenSerializeThenStringCorrect() throws JsonProcessingException {
		assertThat(ForwardingStatus.getObjectMapper().writeValueAsString(status), is(json));
	}

	@Test
	public void whenDeserializeThenEquals() throws IOException {
		assertThat(ForwardingStatus.getObjectMapper().readValue(json, ForwardingStatus.class), is(status));
	}

}
