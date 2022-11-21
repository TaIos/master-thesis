package factory;

import exceptions.BaseException;

public interface Factory<In, Out> {
  Out create(In in) throws BaseException;
}
