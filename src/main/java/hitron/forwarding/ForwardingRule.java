package hitron.forwarding;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class ForwardingRule implements Comparable<ForwardingRule> {

	private transient int ruleIndex;
	private String appName;
	private String pubStart;
	private String pubEnd;
	private String priStart;
	private String priEnd;
	private String protocal;
	private String localIpAddr;
	private String remoteIpStar;
	private String remoteIpEnd;
	private String remoteOnOff;
	private String ruleOnOff;

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
