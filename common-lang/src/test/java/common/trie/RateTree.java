package common.trie;

public class RateTree extends TrieNode<Integer, Rate>{


    protected Integer createKey(String path, int level) {
        return Integer.valueOf(path.substring(level, level + 1));
    }

    @Override
    protected NodeItem<Rate> createItem(String path) {
        return new RateItem();
    }
}
