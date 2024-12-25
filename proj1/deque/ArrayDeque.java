package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    private class ArrayIterator implements Iterator<T> {
        private int wizpos;
        private int sum;
        public ArrayIterator() {
            wizpos = head;
            sum = size;
        }
        @Override
        public boolean hasNext() {
            if (sum > 0) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            T obj = array[wizpos];
            wizpos = (wizpos + 1) % array.length;
            sum--;
            return obj;
        }
    }

    int size;
    T[] array;
    int head, tail;

    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        head = 0;
        tail = 0;
    }

    public void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        if (head <= tail){
            System.arraycopy(array, head, newArray, 0, size);
        } else {
            System.arraycopy(array, head, newArray, 0, size-1);
            System.arraycopy(array, 0, newArray, size-1, tail);
        }
        head = 0;
        tail = size;
        array = newArray;
    }
    @Override
    public void addFirst(T item) {
        if (size == array.length){
            resize(array.length * 2);
        }
        head = (head-1 + array.length) % array.length;
        array[head] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == array.length){
            resize(array.length * 2);
        }
        array[tail] = item;
        tail+=1;
        size++;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0){
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int p = head;
        while (p != tail){
            System.out.print(array[p] + " ");
            p = (p + 1) % array.length;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (array.length >= 16 && size < array.length / 4){
            resize(array.length / 4);
        }
        T obj = array[head];
        head = (head + 1) % array.length;
        size--;
        return obj;
    }

    @Override
    public T removeLast() {
        if (array.length >= 16 && size < array.length / 4){
            resize(array.length / 4);
        }
        tail = (tail - 1 + array.length) % array.length;
        T obj = array[tail];
        size--;
        return obj;
    }

    @Override
    public T get(int index) {
        return array[(head + index) % array.length];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }
}
