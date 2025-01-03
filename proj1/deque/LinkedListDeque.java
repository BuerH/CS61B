package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private class Node<T> {
        T val;
        Node<T> next;
        Node<T> prev;
    }

    private class ListIterator implements Iterator<T> {

        private Node<T> wizpos;

        ListIterator() {
            wizpos = head;
        }
        @Override
        public boolean hasNext() {
            if (wizpos.next != head) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            T obj = wizpos.next.val;
            wizpos = wizpos.next;
            return obj;
        }
    }

    private Node<T> head;
    private int size;
    public LinkedListDeque() {
        head = new Node<>();
        head.val = null;
        head.next = head;
        head.prev = head;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        Node<T> newNode = new Node<>();
        newNode.val = item;
        newNode.prev = head;
        newNode.next = head.next;
        head.next.prev = newNode;
        head.next = newNode;
        ++size;
    }

    @Override
    public void addLast(T item) {
        Node<T> newNode = new Node<>();
        newNode.val = item;
        newNode.next = head;
        newNode.prev = head.prev;
        head.prev.next = newNode;
        head.prev = newNode;
        ++size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node<T> p = head;
        while (p.next != head) {
            System.out.print(p.next.val + " ");
            p = p.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<T> p = head.next;
        p.next.prev = p.prev;
        head.next = p.next;
        size--;
        return p.val;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node<T> p = head.prev;
        p.prev.next = p.next;
        head.prev = p.prev;
        size--;
        return p.val;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int i = 0;
        Node<T> p = head.next;
        while (i < index) {
            p = p.next;
            i++;
        }
        return p.val;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return getIndexByRec(head.next, index);
    }

    private T getIndexByRec(Node<T> cur, int index) {
        if (index == 0) {
            return cur.val;
        }
        return getIndexByRec(cur.next, index - 1);
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Deque<?>) {
            Deque<T> deque = (Deque<T>) o;
            if (deque.size() != this.size) {
                return false;
            } else {
                for (int i = 0; i < size; i++) {
                    if (get(i) == null) {
                        if (deque.get(i) != null) {
                            return false;
                        }
                    } else if (!get(i).equals(deque.get(i))) {
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
    }*/
}
