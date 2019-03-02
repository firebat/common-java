package common.rpc.url;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.hash.Hashing;
import common.id.IdGenerator;
import common.id.RandomId;

import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

/**
 * 认证URL访问
 */
public class AuthUrl {

    public static final Splitter.MapSplitter splitter = Splitter.on('&').withKeyValueSeparator('=');
    public static final Joiner.MapJoiner joiner = Joiner.on('&').withKeyValueSeparator('=');

    private final String key;
    private final String secret;
    private final String url;

    // 允许重复参数
    private boolean duplicate = false;

    // URL有效期 秒
    private int timeout = 100;

    private IdGenerator<String> generator = new RandomId(13);

    public AuthUrl(String url, String key, String secret) {
        this.url = url;
        this.key = key;
        this.secret = secret;
    }

    public AuthUrl timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public AuthUrl duplicate(boolean duplicate) {
        this.duplicate = duplicate;
        return this;
    }

    public AuthUrl generator(IdGenerator generator) {
        this.generator = generator;
        return this;
    }

    public String signatureOf(String source) {
        byte[] sha256 = Hashing.hmacSha256(secret.getBytes()).hashString(source, Charsets.UTF_8).asBytes();
        String base64 = Base64.getEncoder().encodeToString(sha256);
        String md5 = Hashing.md5().hashString(base64, Charsets.UTF_8).toString();
        return md5.substring(5, 15);
    }

    public String build(final int unixtime, final String nonce) {

        Map<String, String> param = new TreeMap<>();
        String uri = url;

        int i = url.indexOf('?');
        if (i > 0) {
            uri = url.substring(0, i);
            param.putAll(splitter.split(url.substring(i + 1)));
        }

        final String expires = String.valueOf(unixtime + this.timeout);

        Preconditions.checkArgument(param.putIfAbsent("appkey", key) == null || duplicate, "appkey重复");
        Preconditions.checkArgument(param.putIfAbsent("expires", expires) == null || duplicate, "expires重复");
        Preconditions.checkArgument(param.putIfAbsent("nonce", nonce) == null || duplicate, "nonce重复");

        String query = joiner.join(param);
        final String signature = signatureOf(query);

        return uri + '?' + query + "&signature=" + signature;
    }

    public String build() {
        return build((int) (System.currentTimeMillis() / 1000L), generator.next());
    }
}
