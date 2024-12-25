package deque;

import org.junit.Test;

public class ArrayDequeTest {

    @Test
    public void resizeTest(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.resize(16);
    }

    @Test
    public void addFirstLastTest(){
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
