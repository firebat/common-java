package common.health;

import java.io.File;


public class FileHealthChecker implements HealthChecker {

    private final File file;

    public FileHealthChecker(String path) {
        log.info("FileHealthCheck on {}", path);
        this.file = new File(path);
    }

    @Override
    public boolean check() {
        return file.exists();
    }
}
