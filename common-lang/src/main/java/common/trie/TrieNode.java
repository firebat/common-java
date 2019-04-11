package common.trie;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Stack;

public abstract class TrieNode<K, V> {

    protected Map<K, TrieNode<K, V>> nodes = Maps.newHashMap();
    protected NodeItem<V> item;

    private void set(String path, int level, V data) {

        if (path.length() <= level) {
            if (item == null) {
                item = createItem(path);
            }
            item.setData(data);
            return;
        }

        K key = createKey(path, level);

        TrieNode<K, V> next = nodes.get(key);
        if (next == null) {
            nodes.put(key, next = createNode());
        }

        next.set(path, level + 1, data);
    }

    private NodeItem<V> get(String path, int level, Stack<NodeItem<V>> stack) {

        if (path.length() <= level) {
            return item;
        }

        if (item != null) {
            stack.push(item);
        }

        K key = createKey(path, level);

        TrieNode<K, V> next = nodes.get(key);
        if (next == null) {
            return null;
        }

        return next.get(path, level + 1, stack);
    }

    public void set(String path, V data) {

        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("path required");
        }

        set(path, 0, data);
    }

    public V get(String path) {
        NodeItem<V> item = getItem(path);
        return item == null ? null : item.getData();
    }

    public NodeItem<V> getItem(String path) {
        Stack<NodeItem<V>> stack = new Stack<>();
        NodeItem<V> item = get(path, 0, stack);

        return item != null ? item : stack.isEmpty() ? null : stack.pop();
    }

    public int size() {
        int count = nodes.values().stream().mapToInt(TrieNode::size).sum();
        return item == null ? count : count + 1;
    }

    public TrieNode<K, V> getNode(K key) {
        return nodes.get(key);
    }

    /**
     * 根据路径，生成当前层深的 key
     *
     * @param path  路径
     * @param level 当前层深
     */
    protected abstract K createKey(String path, int level);

    /**
     * 创建节点项
     *
     * @param path 路径
     */
    protected abstract NodeItem<V> createItem(String path);

    /**
     * 创建节点
     */
    protected TrieNode<K, V> createNode() {
        try {
            return getClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
