package hitron.wireless;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class WirelesSsidDeserializer extends JsonDeserializer<WirelesSsid> {

	@Override
	public WirelesSsid deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		WirelesSsid result = new WirelesSsid();
		if (node.has("id"))
			result.setId(node.get("id").asInt());
		if (node.has("band"))
			result.setBand(node.get("band").textValue());
		if (node.has("ssidName"))
			result.setSsidName(node.get("ssidName").textValue());
		if (node.has("enable"))
			result.setEnable(node.get("enable").textValue().equalsIgnoreCase("ON"));
		if (node.has("visible"))
			result.setVisible(node.get("visible").textValue().equalsIgnoreCase("ON"));
		if (node.has("wmm"))
			result.setWmm(node.get("wmm").textValue().equalsIgnoreCase("ON"));
		if (node.has("authMode"))
			result.setAuthMode(node.get("authMode").asInt());
		if (node.has("encryptType"))
			result.setEncryptType(node.get("encryptType").asInt());
		if (node.has("securityType"))
			result.setSecurityType(node.get("securityType").asInt());
		if (node.has("passPhrase"))
			result.setPassPhrase(node.get("passPhrase").textValue());
		if (node.has("hiddenWepAtkip"))
			result.setHiddenWepAtkip(node.get("hiddenWepAtkip").textValue());
		if (node.has("wlsEnable"))
			result.setWlsEnable(node.get("wlsEnable").textValue().equalsIgnoreCase("ON"));
		if (node.has("wepKey1"))
			result.setWepKey1(node.get("wepKey1").textValue());
		if (node.has("wepKey2"))
			result.setWepKey2(node.get("wepKey2").textValue());
		if (node.has("wepKey3"))
			result.setWepKey3(node.get("wepKey3").textValue());
		if (node.has("wepKey4"))
			result.setWepKey4(node.get("wepKey4").textValue());
		if (node.has("txKey"))
			result.setTxKey(node.get("txKey").asInt());
		if (node.has("secuMode"))
			result.setSecuMode(node.get("secuMode").asInt());
		if (node.has("isPriSsid"))
			result.setIsPriSsid(node.get("isPriSsid").asInt());
		if (node.has("defaultKey"))
			result.setDefaultKey(node.get("defaultKey").textValue());
		if (node.has("ssidSplit"))
			result.setSsidSplit(node.get("ssidSplit").asInt());
		if (node.has("authServer"))
			result.setAuthServer(node.get("authServer").textValue());
		if (node.has("authPort"))
			result.setAuthPort(node.get("authPort").asInt());
		if (node.has("authSecret"))
			result.setAuthSecret(node.get("authSecret").textValue());
		if (node.has("maxInactiveTime"))
			result.setMaxInactiveTime(node.get("maxInactiveTime").asInt());
		if (node.has("wepShownOpt"))
			result.setWepShownOpt(node.get("wepShownOpt").textValue());
		if (node.has("phyMode"))
			result.setPhyMode(node.get("phyMode").textValue());
		return result;
	}

}