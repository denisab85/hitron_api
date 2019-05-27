package hitron.forwarding;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hitron.jsonadapters.BooleanStringSerializer;
import hitron.jsonadapters.IntegerStringSerializer;
import hitron.jsonadapters.StringBooleanDeserializer;
import hitron.jsonadapters.StringIntegerDeserializer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForwardingStatus {

	@Getter
	private static ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}

	@JsonSerialize(using = BooleanStringSerializer.class)
	@JsonDeserialize(using = StringBooleanDeserializer.class)
	private boolean rulesOnOff = true;
	@JsonSerialize
	private String privateLan = "192.168.0.1";
	@JsonSerialize
	private String subMask = "255.255.255.0";
	@JsonSerialize(using = IntegerStringSerializer.class)
	@JsonDeserialize(using = StringIntegerDeserializer.class)
	private int HttpPort = 8080;
	@JsonSerialize(using = IntegerStringSerializer.class)
	@JsonDeserialize(using = StringIntegerDeserializer.class)
	private int HttpsPort = 8181;
	@JsonSerialize(using = IntegerStringSerializer.class)
	@JsonDeserialize(using = StringIntegerDeserializer.class)
	private int TelnetPort = 2323;
	@JsonSerialize(using = IntegerStringSerializer.class)
	@JsonDeserialize(using = StringIntegerDeserializer.class)
	private int SshPort = 22;
	@JsonSerialize(using = IntegerStringSerializer.class)
	@JsonDeserialize(using = StringIntegerDeserializer.class)
	private int forwardingRuleStatus = 1;

}
