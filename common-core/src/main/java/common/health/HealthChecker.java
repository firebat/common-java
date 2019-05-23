package common.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface HealthChecker {

    Logger log = LoggerFactory.getLogger(HealthChecker.class);

    boolean check();
}
