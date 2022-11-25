import exceptions.BaseException;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.http.JsonHttpErrorHandler;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Singleton
public class ErrorHandler extends JsonHttpErrorHandler {

  @Inject
  public ErrorHandler(Environment environment, OptionalSourceMapper sourceMapper) {
    super(environment, sourceMapper);
  }

  public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
    Optional<BaseException> baseExceptionOpt = retrieveBaseException(exception);
    if (baseExceptionOpt.isPresent()) {
      return CompletableFuture.completedFuture(
          Results.status(baseExceptionOpt.get().getHttpStatus(), baseExceptionOpt.get().toJson()));
    }
    return super.onServerError(request, exception);
  }

  private Optional<BaseException> retrieveBaseException(Throwable exception) {
    for (int depth = 0; depth < 3; ++depth, exception = exception.getCause()) {
      if (exception instanceof BaseException) {
        return Optional.of((BaseException) exception);
      }
      if (exception.getCause() == null) {
        break;
      }
    }
    return Optional.empty();
  }
}
