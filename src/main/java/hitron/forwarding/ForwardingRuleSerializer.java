package hitron.forwarding;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ForwardingRuleSerializer extends JsonSerializer<ForwardingRule> {

	@Override
	public void serialize(ForwardingRule value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeNumberField("ruleIndex", value.getRuleIndex());
		jgen.writeStringField("appName", value.getAppName());
		jgen.writeStringField("pubStart", String.valueOf(value.getPubStart()));
		jgen.writeStringField("pubEnd", String.valueOf(value.getPubEnd()));
		jgen.writeStringField("priStart", String.valueOf(value.getPriStart()));
		jgen.writeStringField("priEnd", String.valueOf(value.getPriEnd()));
		jgen.writeStringField("protocal", value.getProtocal());
		jgen.writeStringField("localIpAddr", value.getLocalIpAddr());
		jgen.writeStringField("remoteIpStar", value.getRemoteIpStar());
		jgen.writeStringField("remoteIpEnd", value.getRemoteIpEnd());
		jgen.writeStringField("remoteOnOff", value.isRemoteOnOff() ? "Specific" : "Any");
		jgen.writeStringField("ruleOnOff", value.isRuleOnOff() ? "ON" : "OFF");
		jgen.writeEndObject();
	}
}