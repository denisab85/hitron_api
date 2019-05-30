package hitron.forwarding;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ForwardingStatusSerializer extends JsonSerializer<ForwardingStatus> {

	@Override
	public void serialize(ForwardingStatus value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeStringField("rulesOnOff", value.isRulesOnOff() ? "Enabled" : "Disabled");
		jgen.writeStringField("privateLan", value.getPrivateLan());
		jgen.writeStringField("subMask", String.valueOf(value.getSubMask()));
		jgen.writeStringField("HttpPort", String.valueOf(value.getHttpPort()));
		jgen.writeStringField("HttpsPort", String.valueOf(value.getHttpsPort()));
		jgen.writeStringField("TelnetPort", String.valueOf(value.getTelnetPort()));
		jgen.writeStringField("SshPort", String.valueOf(value.getSshPort()));
		jgen.writeStringField("forwardingRuleStatus", String.valueOf(value.getForwardingRuleStatus()));
		jgen.writeEndObject();
	}
}