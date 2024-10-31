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
            root = wherePut(null, key, value);
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

    //非递归方法put
    public void put1(K key, V value){
        if (size == 0){
            root = new BSTNode(key, value, null, null);
            size++;
        } else {
            BSTNode p = root;
            BSTNode parent = null; //parent
            while (p != null){
                parent = p;
                if (key.compareTo(p._key) > 0){
                    p = p.rightChild;
                } else if (key.compareTo(p._key) < 0){
                    p = p.leftChild;
                } else {              //结点已经存在
                    p._value = value;
                    return;
                }
            }

            if (p == null){
                if (key.compareTo(parent._key) > 0){     //parent.rightChild == p
                    parent.rightChild = new BSTNode(key, value, null, null);
                } else {            //q.leftChild == p
                    parent.leftChild = new BSTNode(key, value, null, null);
                }
                size++;
            }
        }
    }

    //获取键对应值
    @Override
    public V get(K key) {
        if (size == 0 || !containsKey(key)) {
            return null;
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

    //返回键集合
    //遍历二叉树即可
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        return keys;
    }

    @Override
    public V remove(K key) {
        if (root == null || !containsKey(key)) {
            return null;
        }
        BSTNode p = root;
        BSTNode p_Parent = null;
        while (p != null) {
            if (p._key.compareTo(key) < 0) {
                p_Parent = p;
                p = p.rightChild;
            } else if (p._key.compareTo(key) > 0) {
                p_Parent = p;
                p = p.leftChild;
            } else {
                break;
            }
        }
        V removed = p._value;

        if (p.leftChild == null && p.rightChild == null) {       //叶子结点
            if (p_Parent == null) {
                root = null;
            } else if (p_Parent.leftChild == p/* && p_Parent != null*/) {
                p_Parent.leftChild = null;
            } else if (p_Parent.rightChild == p/* && p_Parent != null*/) {
                p_Parent.rightChild = null;
            }
        } else if (p.leftChild != null && p.rightChild == null) {   //一个结点，左不空右空
            if (p_Parent == null) {
                root = p.leftChild;
            } else if (p_Parent.leftChild == p/* && p_Parent != null*/) {
                p_Parent.leftChild = p.leftChild;
            } else if (p_Parent.rightChild == p/* && p_Parent != null*/) {
                p_Parent.rightChild = p.leftChild;
            }
        } else if (p.leftChild == null && p.rightChild != null) {  //一个结点，左空右不空
            if (p_Parent == null) {
                root = p.rightChild;
            } else if (p_Parent.leftChild == p/* && p_Parent != null*/) {
                p_Parent.leftChild = p.rightChild;
            } else if (p_Parent.rightChild == p/* && p_Parent != null*/) {
                p_Parent.rightChild = p.rightChild;
            }
        } else if (p.leftChild != null && p.rightChild != null) {   //有两个子结点（子树）
            //find a new root, bigger than its leftTree and smaller than rightTree
            //new root可以是p的左子树的最右结点，也可以是p的右子树的最左结点，代替p的位置
            BSTNode q = p.leftChild;
            BSTNode q_Parent = null;
            while (q.rightChild != null) {
                q_Parent = q;
                q = q.rightChild;
            }

            //take q as the new root，replace p
            //维护newRoot的子结点和父结点
            q.rightChild = p.rightChild;
            if (q_Parent != null) {           //q_Parent==null，则newRoot即为p.leftChild，左边不用管了
                q.leftChild = p.leftChild;
                q_Parent.rightChild = q.leftChild; //无论是否为null都可以操作
            }


            //维护oldRoot的父结点
            if (p_Parent == null) {
                root = q;
            } else if (p_Parent.leftChild == p/* && p_Parent != null*/) {
                p_Parent.leftChild = q;
            } else if (p_Parent.rightChild == p/* && p_Parent != null*/) {
                p_Parent.rightChild = q;
            }
        }
        size--;
        return removed;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}