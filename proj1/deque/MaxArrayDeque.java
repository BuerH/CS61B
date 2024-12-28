package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private  Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    public T max() {
        return getT(comparator);
    }

    private T getT(Comparator<T> comparator) {
        T maxVal = get(0);
        for (int i = 0; i < size(); i++) {
            T value = get(i);
            maxVal = comparator.compare((T)value, (T)maxVal) > 0 ? value : maxVal;
        }
        return maxVal;
    }

    public T max(Comparator<T> c) {
        return getT((Comparator<T>) c);
    }
}
