package hitron.forwarding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = ForwardingRuleSerializer.class)
@JsonDeserialize(using = ForwardingRuleDeserializer.class)
public class ForwardingRule implements Comparable<ForwardingRule> {

	@Getter
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private transient int ruleIndex;
	private String appName;
	private int pubStart;
	private int pubEnd;
	private int priStart;
	private int priEnd;
	private String protocal;
	private String localIpAddr;
	private String remoteIpStar;
	private String remoteIpEnd;
	private boolean remoteOnOff;
	private boolean ruleOnOff;

	public ForwardingRule(String appName) {
		this.appName = appName;
	}

	@Override
	public int compareTo(ForwardingRule o) {
		if (this == o) {
			return 0;
		}
		if (o == null) {
			return -1;
		}
		return this.ruleIndex - o.ruleIndex;
	}
}
