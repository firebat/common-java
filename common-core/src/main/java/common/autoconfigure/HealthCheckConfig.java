package common.autoconfigure;

public class HealthCheckConfig {

    private String url = "/healthcheck.html";
    private String file;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
