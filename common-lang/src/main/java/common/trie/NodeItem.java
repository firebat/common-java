package common.trie;

/**
 * 节点项
 *
 * @param <V> 数据类型
 */
public interface NodeItem<V> {

    void setData(V data);

    V getData();
}
