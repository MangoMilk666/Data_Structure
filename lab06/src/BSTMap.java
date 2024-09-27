import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V> {

    private class BSTNode {
        K _key;
        V _value;
        BSTNode leftChild;
        BSTNode rightChild;

        public BSTNode() {
            _key = null;
            _value = null;
            leftChild = null;
            rightChild = null;
        }

        public BSTNode(K k, V v, BSTNode left, BSTNode right) {
            _key = k;
            _value = v;
            leftChild = left;
            rightChild = right;
        }
    }

    private BSTNode root;
    private int size;

    //constructor
    public BSTMap() {
        root = null;
        size = 0;
    }

    public BSTMap(K key, V value) {
        root = new BSTNode(key, value, null, null);
        size = 1;
    }

    //添加新的映射对，若已经存在，修改映射值
    @Override
    public void put(K key, V value) {
        if (size == 0){
            root = new BSTNode(key, value, null, null);
            size++;
        } else {
            wherePut(root, key, value);
        }
    }

    //put helper method
    //两个功能：1.维护左右指针指向；2.返回想要的的结点（已有不动，缺少插入）
    public BSTNode wherePut(BSTNode node, K key, V value) {
        if (node == null) {
            size++;
            return new BSTNode(key, value, null, null);
        }
        if (key.compareTo(node._key) < 0) {
            node.leftChild =  wherePut(node.leftChild, key, value);
        } else if (key.compareTo(node._key) > 0) {
            node.rightChild =  wherePut(node.rightChild, key, value);
        } else {
            node._value = value;
        }
        //已经存在key值结点返回node，避免破坏原来的结点。
        return node;
    }

    //获取键对应值
    @Override
    public V get(K key) {
        if (size == 0) {
            return null;
        }
        if (!containsKey(key)) {
            throw new UnsupportedOperationException("不包含该键");
        }

        BSTNode p = root;
        while (p != null) {
            int com = key.compareTo(p._key);
            if (com > 0) {
                p = p.rightChild;
            } else if (com < 0) {
                p = p.leftChild;
            } else {
                return p._value;
            }
        }
        return null;
    }


    //判断是否包含该键
    //遍历即可
    @Override
    public boolean containsKey(K key) {
        if (size > 0) {
            BSTNode p = root;
            while (p != null) {
                int com = key.compareTo(p._key);
                if (com < 0) {
                    p = p.leftChild;
                } else if (com > 0) {
                    p = p.rightChild;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    //返回对应键值对数目
    @Override
    public int size() {
        return size;
    }

    //删除所有键值对
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    //
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}