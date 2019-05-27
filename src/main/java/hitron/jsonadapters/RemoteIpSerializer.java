package hitron.jsonadapters;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class RemoteIpSerializer extends JsonSerializer<Boolean> {
	@Override
	public void serialize(Boolean value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
		jgen.writeString(value ? "Specific" : "Any");
	}
}
