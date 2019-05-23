package common.management;

import com.google.common.base.Strings;
import common.autoconfigure.HealthConfig;
import common.autoconfigure.ServerConfig;
import common.health.FileHealthChecker;
import org.springframework.context.SmartLifecycle;

public class ServerLifecycle implements SmartLifecycle {

    private boolean running = false;

    public ServerLifecycle(final ServerManager manager,
                           final ServerConfig serverConfig,
                           final HealthConfig healthConfig) {

        String healthCheckFile = healthConfig.getFile();
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
