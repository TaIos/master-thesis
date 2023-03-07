package utils.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.List;

public class DoubleDoubleListSerializer extends JsonSerializer<List<List<Double>>> {

  @Override
  public void serialize(List<List<Double>> value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartArray();
    for (List<Double> doubleList : value) {
      gen.writeStartArray();
      for (Double d : doubleList) {
        gen.writeNumber(DoubleSerializer.formatter.format(d));
      }
      gen.writeEndArray();
    }
    gen.writeEndArray();
  }

}
