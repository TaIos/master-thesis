package models.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class HallOfFameDto implements Dto {

  private List<HallOfFameRecordDto> records;
}
