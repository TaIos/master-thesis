package utils.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class DoubleListSerializer extends JsonSerializer<List<Double>> {

  @Override
  public void serialize(List<Double> value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeArray(
        value.stream().map(DoubleSerializer.formatter::format).toArray(String[]::new),
        0,
        value.size());
  }
}
