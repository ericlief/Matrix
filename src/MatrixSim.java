import java.util.Random;

public class MatrixSim {

    int[][] A;
    private int N;
    private static int CONST = 4;

    /**
     * Constructs a square nXn matrix
     * 
     * @param n
     */
    public MatrixSim(int n) {
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
		System.out.printf("X %d %d %d %d\n", i, j, j, i);
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
		    System.out.printf("X %d %d %d %d\n", i, j, j, i);

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
}