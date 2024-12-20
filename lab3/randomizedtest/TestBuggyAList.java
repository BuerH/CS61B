package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> aListNoResizing = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();

        for (int i = 0; i < 3; i++) {
            aListNoResizing.addLast(i);
            buggyAList.addLast(i);
        }
        aListNoResizing.removeLast();
        buggyAList.removeLast();
        for (int i = 0; i < aListNoResizing.size(); i++) {
            assertEquals(aListNoResizing.get(i), buggyAList.get(i));
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int lSize = L.size();
                int bSize = B.size();
                assertEquals(lSize,bSize);
            } else if (operationNumber == 2 && L.size() > 0) {
                // getLast
                Integer last = L.getLast();
                Integer bLast = B.getLast();
                assertEquals(last,bLast);
            } else if (operationNumber == 3 && L.size() > 0) {
                // removeLast
                Integer removed = L.removeLast();
                Integer bRemoved = B.removeLast();
                assertEquals(removed,bRemoved);
            }
        }
    }
}
