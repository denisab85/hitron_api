package hitron.forwarding;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ForwardingRuleDeserializer extends JsonDeserializer<ForwardingRule> {

	@Override
	public ForwardingRule deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		ForwardingRule result = new ForwardingRule();
		if (node.has("ruleIndex"))
			result.setRuleIndex(node.get("ruleIndex").asInt());
		if (node.has("appName"))
			result.setAppName(node.get("appName").textValue());
		if (node.has("pubStart"))
			result.setPubStart(node.get("pubStart").asInt());
		if (node.has("pubEnd"))
			result.setPubEnd(node.get("pubEnd").asInt());
		if (node.has("priStart"))
			result.setPriStart(node.get("priStart").asInt());
		if (node.has("priEnd"))
			result.setPriEnd(node.get("priEnd").asInt());
		if (node.has("protocal"))
			result.setProtocal(node.get("protocal").textValue());
		if (node.has("localIpAddr"))
			result.setLocalIpAddr(node.get("localIpAddr").textValue());
		if (node.has("remoteIpStar"))
			result.setRemoteIpStar(node.get("remoteIpStar").textValue());
		if (node.has("remoteIpEnd"))
			result.setRemoteIpEnd(node.get("remoteIpEnd").textValue());
		if (node.has("remoteOnOff"))
			result.setRemoteOnOff(node.get("remoteOnOff").textValue().equalsIgnoreCase("Specific"));
		if (node.has("ruleOnOff"))
			result.setRuleOnOff(node.get("ruleOnOff").textValue().equalsIgnoreCase("ON"));
		return result;
	}

}