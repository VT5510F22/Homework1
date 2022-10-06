/**
 *  Benchmark different version of the prefix sum algorithm
 *
 */

import java.time.*;
import java.util.*;

public class Benchmark {

    /* The input file we would like to use */
    public static String inputFileName = "in.txt";

    /* Total number of elements to hold in memory at a time */
    public static int [] bufferSizes = {100000}; //, 10000, 100000};

    /* Find the average of numRuns # of runs for each type of prefix sum */
    public static int numRuns = 10;

    public static void main(String [] args) {

        PrefixInterface[] prefixSums = {new SequentialPrefix(), new BetterParallelPrefix(), new ParallelPrefix(), new ParallelPrefixInternal()};
        System.out.printf("Running benchmarks...\n");

        /* Run for various buffer sizes  */
        for (int bufferSize : bufferSizes) {
            System.out.printf("Using buffer size of %d...\n\n", bufferSize);

            List<Long> avgTimeList = new ArrayList<Long>();
            double speedup;
            int curIndex = 0;
            /*Loop through all fo the class, performing the calcuations for a given buffer size*/
            for (PrefixInterface s : prefixSums) {
                System.out.printf("Running %s...\n", s.getClass().getName());

                long averageTime = 0;
                long difTime = 0;
                /*Perform each calculation fifty times and then take the average time*/
                for (int ii = 0; ii < 50; ii++) {
                    Instant startTime = null;
                    Instant endTime = null;
                    try {
                        startTime = Instant.now();
                        s.run(inputFileName, bufferSize);
                        endTime = Instant.now();
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                    difTime = Duration.between(startTime,endTime).toNanos();
                    averageTime += difTime;
                }

                /* TODO: Calculate results */
                averageTime = averageTime/50;
                avgTimeList.add(curIndex, averageTime);
                speedup = avgTimeList.get(0)/((double)avgTimeList.get(curIndex));
                curIndex++;



                /* Print results */
                System.out.printf("Average time: %o\n", averageTime);
                System.out.printf("Speedup: %.2f\n", speedup);
                System.out.println("----------------------------------------------------------\n");
            }//END for(IPrefix s : prefixSums)


        }
    }
}
