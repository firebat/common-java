package common.management;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import common.concurrent.ManagedExecutors;
import common.concurrent.NamedRunnable;
import common.health.HealthChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerManager {

    private static final Logger log = LoggerFactory.getLogger(ServerManager.class);

    private static Supplier<ServerManager> supplier = Suppliers.memoize(() -> new ServerManager());

    private AtomicBoolean health = new AtomicBoolean(false);
    private AtomicBoolean healthCheckEnabled = new AtomicBoolean(false);

    public void setupHealthChecker(HealthChecker checker) {
        if (!healthCheckEnabled.compareAndSet(false, true)) {
            log.warn("HealthChecker enabled.");
            return;
        }

        ManagedExecutors.getScheduledExecutor().scheduleWithFixedDelay(new NamedRunnable("HealthChecker") {
            @Override
            public void doRun() {
                boolean state = checker.check(); // true - OK
                log.debug("state={}", state);
                health.compareAndSet(!state, state);
            }
        }, 5, 2, TimeUnit.SECONDS);
    }

    public boolean isHealth() {
        return health.get();
    }

    public int getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.valueOf(name.substring(0, name.indexOf('@')));
    }

    public static ServerManager get() {
        return supplier.get();
    }
}
