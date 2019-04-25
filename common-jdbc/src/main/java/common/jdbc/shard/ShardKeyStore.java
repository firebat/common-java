package common.jdbc.shard;

public class ShardKeyStore {

    private static final ThreadLocal<String> keyStore = new ThreadLocal<>();

    public static void set(String key) {
        keyStore.set(key);
    }

    public static String get() {
        return keyStore.get();
    }

    public static void clear() {
        keyStore.remove();
    }
}
