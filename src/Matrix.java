import java.util.Random;

public class Matrix {

    int[][] A;
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

	int endRow = m + N;
	int endCol = n + N;
	for (int i = m; i < endRow - 1; i++) {
	    for (int j = n + i - m + 1; j < endCol; j++) {
		int tmp = A[i][j];
		A[i][j] = A[j][i];
		A[j][i] = tmp;

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

	// Base case, below constant, use a simple swap routine
	if (M < CONST && N < CONST) {
	    int endRow = m + M;
	    int endCol = n + N;
	    for (int i = m; i < endRow; i++) {
		for (int j = n; j < endCol; j++) {
		    int tmp = A[i][j];
		    A[i][j] = A[j][i];
		    A[j][i] = tmp;
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