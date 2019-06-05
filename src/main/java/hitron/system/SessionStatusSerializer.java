package hitron.system;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SessionStatusSerializer extends JsonSerializer<SessionStatus> {

	@Override
	public void serialize(SessionStatus value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeStringField("sessionStatus", getString(value.getSessionStatus()));
		jgen.writeEndObject();
	}

	private String getString(Boolean value) {
		if (value == null) {
			return "N/A";
		} else {
			return value ? "Alive" : "Dead";
		}
	}
}