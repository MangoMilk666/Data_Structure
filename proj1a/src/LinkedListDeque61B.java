import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private class TNode{
        public T item;
        public TNode previous;
        public TNode next;

        public TNode(TNode m, T x, TNode n) {
            item = x;
            previous = m;
            next = n;
        }
    }
    //属性
    private int size;

    //设置一个哨兵节点
    private TNode Sentinel;

    //Constructor with value，不显示，没用到
//    public LinkedListDeque61B(T x) {
//
//    }
    //constructor with empty Deque
    public LinkedListDeque61B() {
        Sentinel = new TNode(null, null, null);
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        //first node is the node right next to Sentinel
        //先连后断
        if (size == 0) {
            TNode newNode = new TNode(Sentinel, x, Sentinel);
            Sentinel.next = newNode;
            Sentinel.previous = newNode;
        } else {
            Sentinel.next = new TNode(Sentinel, x, Sentinel.next);
        }
        size++;
    }

    @Override
    public void addLast(T x) {
        //last node is the node right in front of Sentinel
        if (size == 0) {
            TNode newNode = new TNode(Sentinel, x, Sentinel);
            Sentinel.previous = newNode;
            Sentinel.next = newNode;
        } else {
            TNode newNode = new TNode(Sentinel.previous, x, Sentinel);
            Sentinel.previous.next = newNode;
            Sentinel.previous = newNode;
        }
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        //复制链表中的元素至returnList
        TNode p = Sentinel.next;
        while (p != Sentinel) {
            if (p.item != null) {
                returnList.add(p.item);
            }
            p = p.next;
        }
        return returnList;
    }


    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (Sentinel.next != null){
            Sentinel.next = Sentinel.next.next;
        }
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}

