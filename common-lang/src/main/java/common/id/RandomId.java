package common.id;

import java.util.concurrent.ThreadLocalRandom;

public class RandomId implements IdGenerator<String> {

    public static final String NUMBER = "1234567890";
    public static final String CHARACTER = "abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CODE_36 = "1234567890ABCEDFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CODE_62 = "1234567890abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final int length;
    private final String codes;

    public RandomId(int length) {
        this(length, CODE_62);
    }

    public RandomId(int length, String codes) {
        this.length = length;
        this.codes = codes;
    }

    @Override
    public String next() {

        ThreadLocalRandom random = ThreadLocalRandom.current();

        char[] cs = new char[length];
        for (int i = 0; i < length; i++) {
            cs[i] = codes.charAt(random.nextInt(codes.length()));
        }

        return new String(cs);
    }
}
