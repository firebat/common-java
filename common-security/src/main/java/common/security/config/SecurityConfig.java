package common.security.config;

import java.util.concurrent.TimeUnit;

public class SecurityConfig {

    private String tokenHeader = "Authorization";
    private String tokenHead = "Bearer ";
    private String signature = "";
    private long expiration = TimeUnit.MILLISECONDS.convert(7, TimeUnit.DAYS);

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
