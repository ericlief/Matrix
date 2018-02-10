
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestMatrixNaive {

    public static void main(String[] args) {

	int B = Integer.parseInt(args[0]);  // block size B
	int C = Integer.parseInt(args[1]);  // cache size M
	int runs = 0;           // iterations to average over
	String fout = "normal-naive-" + B + "-" + C + ".csv";
	Path pathOut = Paths.get(System.getProperty("user.home")).resolve("code/ds/Matrix/output/" + fout);

	try (BufferedWriter out = Files.newBufferedWriter(pathOut, StandardOpenOption.WRITE,
		StandardOpenOption.CREATE)) {

	    // Iterate for increasing sizes N
	    for (int k = 54; k < 124; k++) {
		int n = (int) Math.pow(2, k / 9.0); // size of matrix 

		Matrix M = new Matrix(n);
		M.randInit();           // init with random numbers

		long startTime = 0; //= System.currentTimeMillis();

		// Uncomment to time and write normal tests
		if (n < 150) // more runs for smaller N runs = 10000; 
		    runs = 10000;

		else
		    runs = 100;// less runs for larger N

		// Ignore the first 10k iterations so as to allow the JVM to warm up 
		for (int t = -runs; t < runs; t++) {
		    // Time transpose operation 
		    if (t == 0)
			startTime = System.nanoTime();
		    // M.transposeOnDiag(M.A, 0, 0, n);
		    M.transpose(M.A, 0, 0, n);

		}

		long time = System.nanoTime() - startTime;
		double aveTime = (double) time / (double) runs;
		int nSwaps = (n * n - n) / 2; // number of pairs 
		double meanSwapTime = aveTime / nSwaps;

		// //System.out.println(n + " elap time  " + time);
		// //System.out.println("ave time " + aveTime);
		// //System.out.println("n swaps " +nSwaps);
		// //System.out.println("mean swap time " +meanSwapTime);

		// Uncomment to write 
		out.write(n + "," + aveTime + "," + meanSwapTime + "\n");

	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}