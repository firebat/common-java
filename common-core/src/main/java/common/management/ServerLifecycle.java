package common.management;

import com.google.common.base.Strings;
import common.autoconfigure.CommonProperties;
import common.management.health.FileHealthChecker;
import org.springframework.context.SmartLifecycle;

public class ServerLifecycle implements SmartLifecycle {

    private boolean running = false;

    public ServerLifecycle(ServerManager manager, CommonProperties commonProperties) {
        String healthCheckFile = commonProperties.getServer().getHealthCheck().getFile();
        if (!Strings.isNullOrEmpty(healthCheckFile)) {
            manager.setupHealthChecker(new FileHealthChecker(healthCheckFile));
        }
    }

    @Override
    public void start() {
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
