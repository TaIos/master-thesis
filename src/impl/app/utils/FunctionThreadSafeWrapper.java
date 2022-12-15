package utils;

import java.util.List;
import java.util.stream.Collectors;
import org.mariuszgromada.math.mxparser.Function;

/**
 * see https://github.com/mariuszgromada/MathParser.org-mXparser/issues/203
 */
public class FunctionThreadSafeWrapper {

  private final FunctionBorrow[] functionBorrows;
  private int next;

  public FunctionThreadSafeWrapper(List<Function> functions) {
    this.functionBorrows = functions.stream().map(FunctionBorrow::new).collect(Collectors.toList())
        .toArray(FunctionBorrow[]::new);
    this.next = 0;
  }


  public synchronized FunctionBorrow getNext() {
    for (; ; next++) {
      if (next == functionBorrows.length) {
        next = 0;
      }
      FunctionBorrow fb = functionBorrows[next];
      if (!fb.borrowed) {
        fb.borrowed = true;
        next++;
        return fb;
      }
    }
  }

  public static class FunctionBorrow implements AutoCloseable {

    private final Function function;
    private boolean borrowed;

    private FunctionBorrow(Function function) {
      this.function = function;
      this.borrowed = false;
    }


    @Override
    public void close() {
      borrowed = false;
    }

    public Function getFunction() {
      return function;
    }
  }

}
