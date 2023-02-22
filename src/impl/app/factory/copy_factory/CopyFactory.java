package factory.copy_factory;

import exceptions.BaseException;

public interface CopyFactory<T> {

  T createCopy(T object) throws BaseException;
}
