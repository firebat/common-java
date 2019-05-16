package common.security.config;

import java.util.concurrent.TimeUnit;

public class AuthorizationConfig {

    // 请求头
    private String tokenHeader = "Authorization";

    // 数据前缀
    private String tokenHead = "Bearer ";

    // 签名
    private String signature = "";

    // 有效期
    private long expiration = TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS);

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
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
