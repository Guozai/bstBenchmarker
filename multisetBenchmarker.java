import java.io.*;
import java.util.Random;

/**
 * Benchmark program for assignment 1
 */
public class multisetBenchmarker {

    /** Program name. */
    protected static final String progName = "DataGenerator";

    /** Start of integer range to generate values from. */
    protected int mStartOfRange;
    /** End of integer range to generate values from. */
    protected int mEndOfRange;
    /** Random generator to use. */
    Random mRandGen;

    /**
     * Constructor.
     *
     * @param startOfRange Start of integer range to generate values.
     * @param endOfRange End of integer range to generate values.
     * @throws IllegalArgumentException If range of integers is inappropriate
     */
    public multisetBenchmarker(int startOfRange, int endOfRange) throws IllegalArgumentException {
        if (startOfRange < 0 || endOfRange < 0 || startOfRange > endOfRange) {
            throw new IllegalArgumentException("startOfRange or endOfRange is invalid.");
        }
        mStartOfRange = startOfRange;
        mEndOfRange = endOfRange;
        // use current time as seed
        mRandGen = new Random(System.currentTimeMillis());
    } // end of DataGenerator()


    /**
     * Generate one sample, using sampling with replacement.
     */
    public int sampleWithReplacement() {
        return mRandGen.nextInt(mEndOfRange - mStartOfRange + 1) + mStartOfRange;
    } // end of sampleWithReplacement()


    /**
     * Generate 'sampleSize' number of samples, using sampling with replacement.
     *
     * @param sampleSize Number of samples to generate.
     */
    public int[] sampleWithReplacement(int sampleSize) {
        int[] samples = new int[sampleSize];

        for (int i = 0; i < sampleSize; i++) {
            samples[i] = sampleWithReplacement();
        }

        return samples;
    } // end of sampleWithReplacement()


    /**
     * Sample without replacement, using "Algorithm R" by Jeffrey Vitter, in paper "Random sampling without a reservoir".
     * This algorithm has O(size of range) time complexity.
     *
     * @param sampleSize Number of samples to generate.
     * @throws IllegalArgumentException When sampleSize is greater than the valid integer range.
     */
    public int[] sampleWithOutReplacement(int sampleSize) throws IllegalArgumentException {
        int populationSize = mEndOfRange - mStartOfRange + 1;

        if (sampleSize > populationSize) {
            throw new IllegalArgumentException("SampleSize cannot be greater than populationSize for sampling without replacement.");
        }

        int[] samples = new int[sampleSize];
        // fill it with initial values in the range
        for (int i = 0; i < sampleSize; i++) {
            samples[i] = i + mStartOfRange;
        }

        // replace
        for (int j = sampleSize; j < populationSize; j++) {
            int t = mRandGen.nextInt(j+1);
            if (t < sampleSize) {
                samples[t] = j + mStartOfRange;
            }
        }

        return samples;
    } // end of sampleWithOutReplacement()

    /**
     * Error message.
     */
    public static void usage() {
        System.err.println(progName + ": <start of range to sample from> <end of range to sample from> <number of values to sample> <type of sampling>");
        System.exit(1);
    } // end of usage()

    public static void main(String[] args) {
        // check correct number of command line arguments
        if (args.length != 4) {
            usage();
        }


        try {
            // integer range
            int startOfRange = Integer.parseInt(args[0]);
            int endOfRange = Integer.parseInt(args[1]);

            // number of values to sample
            int sampleSize = Integer.parseInt(args[2]);

            // type of sampling
            String samplingType = args[3];

            multisetBenchmarker bench = new multisetBenchmarker(startOfRange, endOfRange);

            int[] samples = null;
            switch (samplingType) {
                // sampling with replacement
                case "with":
                    samples = bench.sampleWithReplacement(sampleSize);
                    break;
                // sampling without replacement
                case "without":
                    samples = bench.sampleWithOutReplacement(sampleSize);
                    break;
                default:
                    System.err.println(samplingType + " is an unknown sampling type.");
                    usage();
            }

            LinkedListMultiset doubleLinked = new LinkedListMultiset();
            SortedLinkedListMultiset sortedList = new SortedLinkedListMultiset();
            BstMultiset bst = new BstMultiset();
            HashMultiset hash = new HashMultiset();
            BalTreeMultiset bal = new BalTreeMultiset();

            // print out samples
            if (samples != null) {
                long startTime = System.nanoTime();

                for (int i = 0; i < samples.length; i++) {
                    doubleLinked.add(samples[i]);
                }
                doubleLinked.search(samples[3]);

                long endTime = System.nanoTime();
                System.out.println("Double Linked List:");
                System.out.println("time taken = " + ((double)(endTime - startTime)) / Math.pow(10, 9) + " sec");
                //////////////////////////////////
                startTime = System.nanoTime();

                for (int i = 0; i < samples.length; i++) {
                    sortedList.add(samples[i]);
                }
                sortedList.search(samples[3]);

                endTime = System.nanoTime();
                System.out.println("Sorted Linked List:");
                System.out.println("time taken = " + ((double)(endTime - startTime)) / Math.pow(10, 9) + " sec");
                //////////////////////////////////
                startTime = System.nanoTime();

                for (int i = 0; i < samples.length; i++) {
                    bst.add(samples[i]);
                }
                bst.search(samples[3]);

                endTime = System.nanoTime();
                System.out.println("Binary Search Tree:");
                System.out.println("time taken = " + ((double)(endTime - startTime)) / Math.pow(10, 9) + " sec");
                //////////////////////////////////
                startTime = System.nanoTime();

                for (int i = 0; i < samples.length; i++) {
                    hash.add(samples[i]);
                }
                hash.search(samples[3]);

                endTime = System.nanoTime();
                System.out.println("Hash Map:");
                System.out.println("time taken = " + ((double)(endTime - startTime)) / Math.pow(10, 9) + " sec");
                //////////////////////////////////
                startTime = System.nanoTime();

                for (int i = 0; i < samples.length; i++) {
                    bal.add(samples[i]);
                }
                bal.search(samples[3]);

                endTime = System.nanoTime();
                System.out.println("Balanced Tree:");
                System.out.println("time taken = " + ((double)(endTime - startTime)) / Math.pow(10, 9) + " sec");
            }
        }
		catch (Exception e) {
            System.err.println(e.getMessage());
            usage();
        }
    } // end of main()
} // end of class multisetBenchmarker
