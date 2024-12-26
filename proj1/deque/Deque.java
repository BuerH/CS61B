package deque;

public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);
    Boolean isEmpty();
    int size();
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);
    boolean equals(Object o);
}
