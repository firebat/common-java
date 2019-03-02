package common.autoconfigure;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class ServerConfig {

    @NestedConfigurationProperty
    private HealthCheckConfig healthCheck = new HealthCheckConfig();

    private long dataCenterId = 1;
    private long machineId = 1;

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public HealthCheckConfig getHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(HealthCheckConfig healthCheck) {
        this.healthCheck = healthCheck;
    }
}
