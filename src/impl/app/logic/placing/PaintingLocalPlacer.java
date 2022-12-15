package logic.placing;

import models.entity.Painting;
import models.entity.Point;
import org.mariuszgromada.math.mxparser.Function;

public class PaintingLocalPlacer {

  public double placeAndEval(Painting painting, Function evalFunc) {
    LocalPlacerParameters bestParams = LocalPlacerParameters.withDefaultParams(painting, evalFunc);
    for (int x = painting.getAllocatedSpace().getX();
        x <= painting.getAllocatedSpace().getX() + painting.getAllocatedSpace().getWidth()
            - painting.getWidth(); x++) {
      for (int y = painting.getAllocatedSpace().getY();
          y <= painting.getAllocatedSpace().getY() + painting.getAllocatedSpace().getHeight()
              - painting.getHeight(); y++
      ) {
        bestParams.updateIfBetter(evalFunc.calculate(x, y), x, y);
      }
    }
    bestParams.setPaintingParameters(painting);
    return bestParams.funcValue;
  }

  private static class LocalPlacerParameters {

    private int x;
    private int y;
    private double funcValue;

    public static LocalPlacerParameters withDefaultParams(Painting painting,
        Function evalFunc) {
      LocalPlacerParameters params = new LocalPlacerParameters();
      params.x = painting.getAllocatedSpace().getX();
      params.y = painting.getAllocatedSpace().getY();
      params.funcValue = evalFunc.calculate(params.x, params.y);
      return params;
    }

    public void updateIfBetter(double funcValue, int x, int y) {
      if (funcValue < this.funcValue) {
        this.funcValue = funcValue;
        this.x = x;
        this.y = y;
      }
    }

    public void setPaintingParameters(Painting painting) {
      painting.setPlacement(new Point(x, y));
    }
  }

}
