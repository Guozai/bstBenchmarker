import java.io.*;
import java.util.*;

/**
 * Benchmark program for assignment 1
 */
public class multisetBenchmarker {
    public static void main(String[] args) {
        int n = 500000000; // 500,000,000

        long startTime = System.nanoTime();
        LinkedListMultiset doubleLinked = new LinkedListMultiset();
        doubleLinked.add(n);
        doubleLinked.search(n);
        long endTime = System.nanoTime();

        System.out.println("time taken = " + ((double)(endTime - startTime)) / Math.pow(10, 9) + " sec");
    } // end of main()
} // end of class multisetBenchmarker
