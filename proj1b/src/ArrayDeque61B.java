import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    //the front and back indice
    private int front;
    private int back;

    //empty constructor
    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        front = 0;
        back = -1;
        //initialize back with -1 to avoid that position 0 could be taken up
        // if using addLast to empty lists
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        //头部添加，则front索引减1再求余，如此则可回溯到末端
        front = (front - 1 + items.length) % items.length;
        items[front] = x;
        size++;
        if (size == 1) {
            back = front;
        }
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        back = (back + 1 + items.length) % items.length;
        items[back] = x;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i=0; i < size; i++){
            //add添加对应元素到克隆数组末尾
            returnList.add(items[(front + i) % items.length]);
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
        if (items[front] == null || size == 0) {
            return null;
        }
        //resize down if necessary
        if (items.length > 15 && size*1.0/items.length < 0.25){
            resize(items.length/2);
        }
        T removed = items[front];
        //移除处元素赋值为null
        items[front] = null;
        front = (front + 1) % items.length;
        size--;
        return removed;
    }

    @Override
    public T removeLast() {
        if (items[back] == null || size == 0) {
            return null;
        }

        //resize down if necessary
        if (items.length > 15 && size*1.0/items.length < 0.25){
            resize(items.length/2);
        }

        T removed = items[back];
        //重置为null
        items[back] = null;
        back = (back - 1 + items.length) % items.length;
        size--;
        return removed;
    }


    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(front + index) % items.length];
    }


    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        /*
        将老数组元素通过遍历逐个复制到新数组，不使用arraycopy
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
         */
        for (int i = 0; i < size; i++) {
            newItems[i] = items[(front + i) % items.length];
        }
        //将newItems设为数组主体。并重置front和back
        items = newItems;
        front = 0;
        //因为此时是按照顺序复制了原数组元素，所以front, back分别为前端和末端索引
        back = size-1;
    }
}

