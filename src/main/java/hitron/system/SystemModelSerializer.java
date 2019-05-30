package hitron.system;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SystemModelSerializer extends JsonSerializer<SystemModel> {

	@Override
	public void serialize(SystemModel value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeStringField("modelName", value.getModelName());
		jgen.writeStringField("skipWizard", String.valueOf(value.getSkipWizard()));
		jgen.writeStringField("theme", value.getTheme());
		jgen.writeEndObject();
	}
}