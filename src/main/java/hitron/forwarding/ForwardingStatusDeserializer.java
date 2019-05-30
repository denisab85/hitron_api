package hitron.forwarding;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ForwardingStatusDeserializer extends JsonDeserializer<ForwardingStatus> {

	@Override
	public ForwardingStatus deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		ForwardingStatus result = new ForwardingStatus();
		if (node.has("rulesOnOff"))
			result.setRulesOnOff(node.get("rulesOnOff").textValue().equalsIgnoreCase("Enabled"));
		if (node.has("privateLan"))
			result.setPrivateLan(node.get("privateLan").textValue());
		if (node.has("subMask"))
			result.setSubMask(node.get("subMask").textValue());
		if (node.has("HttpPort"))
			result.setHttpPort(node.get("HttpPort").asInt());
		if (node.has("HttpsPort"))
			result.setHttpsPort(node.get("HttpsPort").asInt());
		if (node.has("TelnetPort"))
			result.setTelnetPort(node.get("TelnetPort").asInt());
		if (node.has("SshPort"))
			result.setSshPort(node.get("SshPort").asInt());
		if (node.has("forwardingRuleStatus"))
			result.setForwardingRuleStatus(node.get("forwardingRuleStatus").asInt());
		return result;
	}

}