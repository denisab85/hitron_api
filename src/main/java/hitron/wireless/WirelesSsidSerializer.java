package hitron.wireless;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WirelesSsidSerializer extends JsonSerializer<WirelesSsid> {

	@Override
	public void serialize(WirelesSsid value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeNumberField("id", value.getId());
		jgen.writeStringField("band", value.getBand());
		jgen.writeStringField("ssidName", value.getSsidName());
		jgen.writeStringField("enable", value.isEnable() ? "ON" : "OFF");
		jgen.writeStringField("visible", value.isVisible() ? "ON" : "OFF");
		jgen.writeStringField("wmm", value.isWmm() ? "ON" : "OFF");
		jgen.writeNumberField("authMode", value.getAuthMode());
		jgen.writeNumberField("encryptType", value.getEncryptType());
		jgen.writeNumberField("securityType", value.getSecurityType());
		jgen.writeStringField("passPhrase", value.getPassPhrase());
		jgen.writeStringField("hiddenWepAtkip", value.getHiddenWepAtkip());
		jgen.writeStringField("wlsEnable", value.isWlsEnable() ? "ON" : "OFF");
		jgen.writeStringField("wepKey1", value.getWepKey1());
		jgen.writeStringField("wepKey2", value.getWepKey2());
		jgen.writeStringField("wepKey3", value.getWepKey3());
		jgen.writeStringField("wepKey4", value.getWepKey4());
		jgen.writeNumberField("txKey", value.getTxKey());
		jgen.writeNumberField("secuMode", value.getSecuMode());
		jgen.writeNumberField("isPriSsid", value.getIsPriSsid());
		jgen.writeStringField("defaultKey", value.getDefaultKey());
		jgen.writeNumberField("ssidSplit", value.getSsidSplit());
		jgen.writeStringField("authServer", value.getAuthServer());
		jgen.writeNumberField("authPort", value.getAuthPort());
		jgen.writeStringField("authSecret", value.getAuthSecret());
		jgen.writeNumberField("maxInactiveTime", value.getMaxInactiveTime());
		jgen.writeStringField("wepShownOpt", value.getWepShownOpt());
		jgen.writeStringField("phyMode", value.getPhyMode());
		jgen.writeEndObject();
	}
}