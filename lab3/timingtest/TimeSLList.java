package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        int start = 2;
        SLList<Integer> sllist = null;
        Stopwatch stopwatch = null;
        for(int i = 0; i < 8; ++i){
            int timesN = (int) Math.pow(start, i) * 1000;
            sllist = new SLList<>();
            for(int j = 0; j < timesN; ++j){
                sllist.addLast(j);
            }
            stopwatch = new Stopwatch();
            for(int k = 0; k < 10000; ++k){
                sllist.getLast();
            }
            times.addLast(stopwatch.elapsedTime());
            Ns.addLast(timesN);
            opCounts.addLast(timesN);
        }
        printTimingTable(Ns, times, opCounts);
    }

}
