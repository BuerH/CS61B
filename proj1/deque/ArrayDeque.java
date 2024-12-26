package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    private class ArrayIterator implements Iterator<T> {
        private int wizpos;
        private int sum;
        ArrayIterator() {
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

    private int size;
    private T[] array;
    private int head, tail;

    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        head = 0;
        tail = 0;
    }

    private void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(head++) % array.length];
        }
        head = 0;
        tail = size;
        array = newArray;
    }
    @Override
    public void addFirst(T item) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        head = (head - 1 + array.length) % array.length;
        array[head] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        array[tail] = item;
        tail = (tail + 1) % array.length;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int p = head;
        while (p != tail) {
            System.out.print(array[p] + " ");
            p = (p + 1) % array.length;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (array.length >= 16 && size < array.length / 4) {
            resize(array.length / 4);
        }
        T obj = array[head];
        head = (head + 1) % array.length;
        size--;
        return obj;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (array.length >= 16 && size < array.length / 4) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ArrayDeque<?>) {
            ArrayDeque<T> deque = (ArrayDeque<T>) o;
            if (deque.size() != this.size) {
                return false;
            } else {
                Iterator<T> it = this.iterator();
                Iterator<T> oit = deque.iterator();
                while (it.hasNext()){
                    T thisE = it.next();
                    T otherE = oit.next();
                    if (thisE == null) {
                        if (otherE != null) {
                            return false;
                        }
                    } else if (!thisE.equals(otherE)) {
                        return false;
                    }
                }
                return true;
            }
        } else if (o instanceof LinkedListDeque<?>) {
            LinkedListDeque<T> deque = (LinkedListDeque<T>) o;
            if (deque.size() != this.size) {
                return false;
            } else {
                Iterator<T> it = this.iterator();
                Iterator<T> oit = deque.iterator();
                while (it.hasNext()){
                    T thisE = it.next();
                    T otherE = oit.next();
                    if (thisE == null) {
                        if (otherE != null) {
                            return false;
                        }
                    } else if (!thisE.equals(otherE)) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }
    /*public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ArrayDeque) {
            ArrayDeque<T> aD = (ArrayDeque<T>) o;
            if (aD.size() != this.size) {
                return false;
            } else {
                for(int i = 0; i < size; i++) {
                    if (aD.get(i) == null && get(i) == null) {
                        continue;
                    }
                    if (!aD.get(i).equals(this.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        } else if (o instanceof LinkedListDeque) {
            LinkedListDeque<T> lD = (LinkedListDeque<T>) o;
            if (lD.size() != this.size) {
                return false;
            } else {
                for(int i = 0; i < size; i++) {
                    if (lD.get(i) == null && get(i) == null) {
                        continue;
                    }
                    if (!lD.get(i).equals(this.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }*/

}
