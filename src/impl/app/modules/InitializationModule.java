package modules;

import com.google.inject.AbstractModule;
import org.mariuszgromada.math.mxparser.License;

public class InitializationModule extends AbstractModule {

  static {
    // Confirm mXparser license, otherwise it prints warnings
    // https://mathparser.org/mxparser-tutorial/confirming-non-commercial-commercial-use/
    License.iConfirmNonCommercialUse("Martin Šafránek");
  }
}
