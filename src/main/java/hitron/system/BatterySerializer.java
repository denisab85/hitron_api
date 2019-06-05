package hitron.system;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BatterySerializer extends JsonSerializer<Battery> {

	@Override
	public void serialize(Battery value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeStringField("battRemain", String.valueOf(value.getBattRemain()));
		jgen.writeStringField("battCharge", getString(value.getBattCharge()));
		jgen.writeStringField("battPsm", getString(value.getBattPsm()));
		jgen.writeStringField("battInsert", getString(value.getBattInsert()));
		jgen.writeEndObject();
	}

	private String getString(Boolean value) {
		if (value == null) {
			return "N/A";
		} else {
			return value ? "ON" : "OFF";
		}
	}
}