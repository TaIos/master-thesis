package utils;

import java.io.InputStream;

public interface ResourceFileLoaderUtil {
  default InputStream getResourceAsInputStream(String path) throws Exception {
    return ResourceFileLoaderUtil.class.getResourceAsStream(path);
  }
}
