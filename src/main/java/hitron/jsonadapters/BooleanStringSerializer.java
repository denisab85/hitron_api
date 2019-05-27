package hitron.jsonadapters;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class BooleanStringSerializer extends JsonSerializer<Boolean> {
	@Override
	public void serialize(Boolean value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value ? "Enabled" : "Disabled");
	}
}