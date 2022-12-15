package logic.objective.parts.global;

import java.util.List;
import models.entity.Painting;

public interface GlobalObjectivePart<T> {

  T eval(List<Painting> paintings);
}
