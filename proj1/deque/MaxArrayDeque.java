package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{

    Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c){
        this.comparator = c;
    }

    public T max() {
        return getT(comparator);
    }

    private T getT(Comparator<T> comparator) {
        ArrayIterator it = (ArrayIterator) iterator();
        T maxVal = it.next();
        while (it.hasNext()) {
            T value = it.next();
            maxVal = comparator.compare((T)value, (T)maxVal) > 0 ? value:maxVal;
        }
        return maxVal;
    }

    public T max(Comparator<T> c) {
        return getT((Comparator<T>) c);
    }
}
