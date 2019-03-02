package common.trie;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class RateItem implements NodeItem<Rate> {

    private Queue<Rate> rates = new PriorityQueue<>(Comparator.comparing(Rate::getRate));

    public void setData(Rate data) {
        rates.offer(data);
    }

    public Rate getData() {
        return rates.peek();
    }
}
