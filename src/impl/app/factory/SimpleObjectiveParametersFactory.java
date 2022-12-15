package factory;

import com.fasterxml.jackson.databind.JsonNode;
import exceptions.InvalidFieldValueInJsonException;
import java.util.List;
import models.dto.ObjectiveParametersDto;
import models.entity.SimpleObjectiveParameters;

public class SimpleObjectiveParametersFactory implements
    Factory<ObjectiveParametersDto, SimpleObjectiveParameters> {

  @Override
  public SimpleObjectiveParameters create(ObjectiveParametersDto dto)
      throws InvalidFieldValueInJsonException {
    throwIfValueInvalidOrMissingValues(dto.getParams());
    return SimpleObjectiveParameters.builder()
        .overlappingPenalizationConstant(
            dto.getParams().get("overlappingPenalizationConstant").asDouble())
        .outsideOfAllocatedAreaPenalizationConstant(
            dto.getParams().get("outsideOfAllocatedAreaPenalizationConstant").asDouble())
        .build();
  }

  private void throwIfValueInvalidOrMissingValues(JsonNode node)
      throws InvalidFieldValueInJsonException {
    for (var key : List.of("overlappingPenalizationConstant",
        "outsideOfAllocatedAreaPenalizationConstant")) {
      throwIfNotPresent(node, key);
      throwIfNotDouble(node, key);
    }
  }

  private void throwIfNotPresent(JsonNode node, String key)
      throws InvalidFieldValueInJsonException {
    if (!node.has(key)) {
      throw new InvalidFieldValueInJsonException(String.format("Key [%s] not present at [%s]",
          key, ObjectiveParametersDto.class.getSimpleName()));
    }
  }

  private void throwIfNotDouble(JsonNode node, String key) throws InvalidFieldValueInJsonException {
    String value = node.get(key).asText();
    try {
      Double.parseDouble(value);
    } catch (NumberFormatException e) {
      throw new InvalidFieldValueInJsonException(
          String.format("Value [%s] for key [%s] is not double at [%s]. Parsing exception: [%s]",
              value, key,
              ObjectiveParametersDto.class.getSimpleName(), e));
    }
  }
}
