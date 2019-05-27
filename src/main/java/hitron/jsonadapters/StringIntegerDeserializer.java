package hitron.jsonadapters;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StringIntegerDeserializer extends JsonDeserializer<Integer> {
	@Override
	public Integer deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		int result = 0;
		try {
			result = Integer.parseInt(parser.getText());
		} catch (Exception e) {

		}
		return result;
	}
}