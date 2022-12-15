package logic.objective.parts.local;

import models.entity.Painting;

public interface LocalObjectivePart<T> {

  T eval(Painting painting);
}
