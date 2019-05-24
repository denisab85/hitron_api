package hitron.forwarding;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import hitron.forwarding.ForwardingStatus;

public class ForwardingStatusTest {

	@Test
	public void whenToStringThenToStringCorrect() {
		ForwardingStatus fs = new ForwardingStatus();
		assertThat(fs.toString(), is(
				"{ \"rulesOnOff\":\"Enabled\", \"privateLan\":\"192.168.0.1\", \"subMask\":\"255.255.255.0\", \"HttpPort\":\"8080\", \"HttpsPort\":\"8181\", \"TelnetPort\":\"2323\", \"SshPort\":\"22\", \"forwardingRuleStatus\":\"1\" }"));
	}

	@Test
	public void whenFromStringThenToStringCorrect() {
		String string = "{ \"rulesOnOff\":\"Enabled\", \"privateLan\":\"192.168.0.1\", \"subMask\":\"255.255.255.0\", \"HttpPort\":\"8080\", \"HttpsPort\":\"8181\", \"TelnetPort\":\"2323\", \"SshPort\":\"22\", \"forwardingRuleStatus\":\"1\" }";
		ForwardingStatus fs = new ForwardingStatus(string);
		assertThat(fs.toString(), is(string));
	}

	@Test
	public void whenDisableThenToStringCorrect() {
		ForwardingStatus fs = new ForwardingStatus();
		fs.setEnabled(false);
		assertThat(fs.toString(), is(
				"{ \"rulesOnOff\":\"Disabled\", \"privateLan\":\"192.168.0.1\", \"subMask\":\"255.255.255.0\", \"HttpPort\":\"8080\", \"HttpsPort\":\"8181\", \"TelnetPort\":\"2323\", \"SshPort\":\"22\", \"forwardingRuleStatus\":\"1\" }"));
	}

}
