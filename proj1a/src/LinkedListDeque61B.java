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
            TNode newNode = new TNode(Sentinel, x, Sentinel.next);
            Sentinel.next.previous = newNode;
            Sentinel.next = newNode;
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
        while (p != Sentinel && p != null) {
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
        TNode p = Sentinel.next;
        if (p != null){
            if (p.next != Sentinel) {
                Sentinel.next = p.next;
                p.next.previous = Sentinel;
                size--;
            } else {
                //Only the sentinel finally left
                Sentinel.previous = null;
                Sentinel.next = null;
                size--;
            }
            return p.item;
        }
        return null;
    }


    @Override
    public T removeLast() {
        TNode p = Sentinel.previous;
        if (p != null) {
            if (p.previous != Sentinel) {
                Sentinel.previous = p.previous;
                p.previous.next = Sentinel;
                size--;
                return p.item;
            } else {
                //Only the sentinel finally left
                Sentinel.previous = null;
                Sentinel.next = null;
                size--;
                return p.item;
            }
        }
        return null;
    }


    @Override
    public T get(int index) {
        if (index <= 0 || index > size){
            return null;
        }
        int count=0;
        TNode currNode = Sentinel;
        while (count < index){
            currNode = currNode.next;
            count++;
        }
        if (currNode.item != null){
            return currNode.item;
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        //递归方式实现
        if (index <= 0 || index > size){
            //越界或只存在sentinel节点
            return null;
        }
        return getRecursive(Sentinel, index);
    }

    private T getRecursive(TNode curr, int index) {
        //assistant method to help shape public getRecursive method
        //index代表此刻节点curr离目标节点的数量距离
        if (index == 0) {
            if (curr != null) {
                return curr.item;
            } else {
                return null;
            }
        } else {
            return getRecursive(curr.next, index-1);
        }
    }
}

