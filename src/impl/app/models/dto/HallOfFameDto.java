package models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class HallOfFameDto implements Dto {
  List<HallOfFameRecordDto> records;
}
