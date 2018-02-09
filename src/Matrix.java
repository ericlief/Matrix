import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class Matrix {

    private int[][] A;
    private int N;
    private static int CONST = 4;

    /**
     * Constructs a square nXn matrix
     * 
     * @param n
     */
    public Matrix(int n) {
	this.N = n;

	// Create nxn Matrix
	//	System.out.printf("Creating an %dx%d matrix\n", n, n);
	System.out.printf("N %d\n", n);
	A = new int[n][n];

    }

    /**
     * Initialize a matrix with random 32-bit integers.
     */
    public void randInit() {
	Random rand = new Random();
	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < N; j++)
		A[i][j] = rand.nextInt() * Integer.MAX_VALUE;
	}
    }

    /**
     * Initialize matrix with ordered integers starting at 1.
     */
    public void ordInit() {
	int x = 1;
	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < N; j++) {
		A[i][j] = x;
		x++;
	    }
	}
    }

    /**
     * Utility method to print matrix
     */
    public void print() {
	System.out.println();
	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < N; j++)
		System.out.print(A[i][j] + "\t");
	    System.out.println();
	    System.out.println();
	    System.out.println();
	}
    }

    /**
     * Simple (sub-optimal) routine for transposing a matrix, used as a
     * base-case for the COA below.
     * 
     * @param A
     *            matrix
     * @param m
     *            first (x) coordinate of matrix
     * @param n
     *            first (y) coordinate of matrix
     * @param N
     *            size (if square nXn)
     */
    public void transpose(int[][] A, int m, int n, int N) {

	//	System.out.println("in transpose");
	int endRow = m + N;
	int endCol = n + N;
	//	System.out.println("row end " + endRow + " " + "end col " + endCol);
	for (int i = m; i < endRow - 1; i++) {
	    for (int j = n + i - m + 1; j < endCol; j++) {
//		System.out.printf("X %d %d %d %d\n", i, j, j, i);
		int tmp = A[i][j];
		A[i][j] = A[j][i];
		A[j][i] = tmp;
		//		System.out.println();
	    }
	}
    }

    /**
     * Cache oblivious algorithm (COA) for transposing a matrix, using
     * recursion, until the matrix if of a constant size, here set to less than
     * 4X4.
     * 
     * @param A
     *            matrix
     * @param m
     *            first (x) coordinate of matrix
     * @param n
     *            first (y) coordinate of matrix
     * @param N
     *            size (if square nXn)
     */
    public void transposeOnDiag(int[][] A, int m, int n, int N) {

	//	System.out.printf("transpose on diag %dx%d @(%d,%d)\n", N, N, m, n);

	// Less than constant, use simple transpose method
	if (N < CONST) {
	    //	    System.out.println(N + " less than " + CONST);
	    transpose(A, m, n, N);
	}

	// Use COA and transpose first across diagonal and then swap upper right 
	// and lower right matrices using recursion
	else {

	    transposeOnDiag(A, m, n, N / 2);		// upper left submatrix

	    transposeOnDiag(A, m + N / 2, n + N / 2, (int) Math.ceil(N / 2.0));	// lower right submatrix

	    transposeAndSwap(A, m, n + N / 2, N / 2, (int) Math.ceil(N / 2.0)); //swap upper right and lower left

	}
    }

    /**
     * swap upper right and lower right matrices using recursion
     * 
     * @param A
     *            matrix
     * @param m
     *            first (x) coordinate of matrix
     * @param n
     *            first (y) coordinate of matrix
     * @param N
     *            size of vertical dimension (rows)
     * @param M
     *            size of horizontal dimention (columns)
     */
    public void transposeAndSwap(int[][] A, int m, int n, int M, int N) {

	//	System.out.printf("transpose and swap %dx%d @(%d,%d)\n", M, N, m, n);
	System.out.println();

	// Base case, below constant, use a simple swap routine
	if (M < CONST && N < CONST) {
	    //	    System.out.println("base swap");
	    int endRow = m + M;
	    int endCol = n + N;
	    for (int i = m; i < endRow; i++) {
		for (int j = n; j < endCol; j++) {
		    //		    System.out.println("transposing " + A[i][j] + " with " + A[j][i]);
		    int tmp = A[i][j];
		    A[i][j] = A[j][i];
		    A[j][i] = tmp;
//		    System.out.printf("X %d %d %d %d\n", i, j, j, i);

		}
	    }
	}

	// Recurse until base case for each of the four sub-matrices
	else {

	    transposeAndSwap(A, m, n, M / 2, (int) Math.ceil(N / 2.0));	// uper right submatrix
	    transposeAndSwap(A, m, n + (int) Math.ceil(N / 2.0), M / 2, N / 2);	// lower left submatrix
	    transposeAndSwap(A, m + M / 2, n, (int) Math.ceil(M / 2.0), (int) Math.ceil(N / 2.0));	// uper right submatrix
	    transposeAndSwap(A, m + M / 2, n + (int) Math.ceil(N / 2.0), (int) Math.ceil(M / 2.0), N / 2);	// lower left submatrix

	}
    }

    public static void main(String[] args) {

	int B = Integer.parseInt(args[0]);	// block size B
	int C = Integer.parseInt(args[1]);	// cache size M
	int runs = 0; 			// iterations to average over
	String fout = "sim-" + B + "-" + C + ".csv";
	Path pathOut = Paths.get(System.getProperty("user.home")).resolve("code/ds/Matrix/output/" + fout);

	try (BufferedWriter out = Files.newBufferedWriter(pathOut, StandardOpenOption.WRITE,
		StandardOpenOption.CREATE)) {

	    // Iterate of increasing sizes N
	    for (int k = 54; k < 124; k++) {
		int n = (int) Math.pow(2, k / 9.0);	// size of matrix 

		Matrix M = new Matrix(n);
		M.randInit();			// init with random numbers

		long startTime = 0; //= System.currentTimeMillis();

		// Uncomment for cache simulator
		M.transpose(M.A, 0, 0, n);		// for simple transpose
		// M.transposeOnDiag(M.A, 0, 0, n);	// for COA
		System.out.println("E");

		// Uncomment to time and write normal tests
		if (n < 150) // more runs for smaller N runs = 10000; 

		    runs = 10000;

		else
		    runs = 100;// less runs for larger N

		// Ignore the first 10k iterations so as to allow the JVM to warm up 
		for (int t = -runs; t < runs; t++) {
		    // Time transpose operation 

		    if (t == 0)
			startTime = System.nanoTime(); //M.transposeOnDiag(M.A, 0, 0, n);
		    M.transpose(M.A, 0, 0, n);

		}

		long time = System.nanoTime() - startTime;
		double aveTime = (double) time / (double) runs;
		int nSwaps = (n * n - n) / 2; // number of pairs 
		double meanSwapTime = aveTime / nSwaps;

		//System.out.println(n + " elap time  " + time);
		//System.out.println("ave time " + aveTime);
		//System.out.println("n swaps " +nSwaps);
		//System.out.println("mean swap time " +meanSwapTime);

		// Uncomment to write 
		out.write(n + "," + aveTime + "," + meanSwapTime + "\n");

	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}