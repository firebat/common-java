package common.trie;

import java.math.BigDecimal;

public class Rate {

    private String name;
    private int rate;

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }

    public static Rate of(String name, int rate) {
        Rate item = new Rate();
        item.name = name;
        item.rate = rate;
        return item;
    }
}
