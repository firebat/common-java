package common.trie;

import junit.framework.TestCase;
import org.junit.Test;

public class TrieNodeTest extends TestCase {

    @Test
    public void testAll() {
        RateTree tree = new RateTree();
        tree.set("86", Rate.of("China", 20));

        tree.set("8613", Rate.of("China Mobile", 15));
        tree.set("8614", Rate.of("China Mobile", 15));
        tree.set("8615", Rate.of("China Mobile", 15));
        tree.set("8617", Rate.of("China Mobile", 15));
        tree.set("8618", Rate.of("China Mobile", 15));

        tree.set("86133", Rate.of("China Telecom", 10));
        tree.set("86149", Rate.of("China Telecom", 10));
        tree.set("86153", Rate.of("China Telecom", 10));

        tree.set("86130", Rate.of("China unicom", 12));
        tree.set("86131", Rate.of("China unicom", 12));
        tree.set("86132", Rate.of("China unicom", 12));
        tree.set("86135", Rate.of("China unicom", 12));

        assertEquals(13, tree.size());

        assertEquals(20, tree.get("8688489527").getRate());
        assertEquals(15, tree.get("8613811111111").getRate());
        assertEquals(10, tree.get("8613311111111").getRate());
        assertEquals(12, tree.get("8613511111111").getRate());
    }
}
