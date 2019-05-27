package hitron.forwarding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import hitron.jsonadapters.BooleanStringSerializer;
import hitron.jsonadapters.RemoteIpDeserializer;
import hitron.jsonadapters.RemoteIpSerializer;
import hitron.jsonadapters.StringBooleanDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
	@JsonSerialize(using = RemoteIpSerializer.class)
	@JsonDeserialize(using = RemoteIpDeserializer.class)
	private boolean remoteOnOff;
	@JsonSerialize(using = BooleanStringSerializer.class)
	@JsonDeserialize(using = StringBooleanDeserializer.class)
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
