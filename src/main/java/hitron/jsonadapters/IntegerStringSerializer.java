package hitron.jsonadapters;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IntegerStringSerializer extends JsonSerializer<Integer> {
	@Override
	public void serialize(Integer value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(String.valueOf(value));
	}
}