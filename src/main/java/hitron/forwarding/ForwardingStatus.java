package hitron.forwarding;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonSerialize(using = ForwardingStatusSerializer.class)
@JsonDeserialize(using = ForwardingStatusDeserializer.class)
public class ForwardingStatus {

	private boolean rulesOnOff = true;
	private String privateLan = "192.168.0.1";
	private String subMask = "255.255.255.0";
	private int HttpPort = 8080;
	private int HttpsPort = 8181;
	private int TelnetPort = 2323;
	private int SshPort = 22;
	private int forwardingRuleStatus = 1;

}
