package deque;

import org.junit.Test;

import java.util.Comparator;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class MaxArrayDequeTest {

    @Test
    public void maxTest(){
        Comparator<Integer> c = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
        MaxArrayDeque<Integer> ad = new MaxArrayDeque<>(c);
        ad.addFirst(16);
        ad.addFirst(15);
        ad.addFirst(14);
        ad.addFirst(13);
        ad.addFirst(12);
        ad.addFirst(11);
        ad.addFirst(10);
        ad.addFirst(9);
        ad.addFirst(8);
        ad.addFirst(7);
        ad.addFirst(6);
        ad.addLast(18);
        assertEquals(18, (int)ad.max());
    }
    @Test
    public void printDequeTest(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(16);
        ad.addFirst(15);
        ad.addFirst(14);
        ad.addFirst(13);
        ad.addFirst(12);
        ad.addFirst(11);
        ad.addFirst(10);
        ad.addFirst(9);
        ad.addFirst(8);
        ad.addFirst(7);
        ad.addFirst(6);
        ad.addLast(18);
        ad.printDeque();
    }

    @Test
    public void removeFirstTest(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(16);
        ad.addFirst(15);
        ad.addFirst(14);
        ad.addFirst(13);
        ad.addFirst(12);
        ad.addFirst(11);
        ad.addFirst(10);
        ad.addFirst(9);
        ad.addLast(18);
        for (int i = 0; i < 7; i++) {
            ad.removeFirst();
        }
        ad.addFirst(10);
        ad.printDeque();
    }
}
