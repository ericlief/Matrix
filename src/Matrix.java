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

    public Matrix(int n) {
	this.N = n;
	//		Create nxn Matrix
	//	System.out.printf("Creating an %dx%d matrix\n", n, n);
	System.out.printf("N %d\n", n);
	A = new int[n][n];

    }

    public void randInit() {
	Random rand = new Random();
	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < N; j++)
		A[i][j] = rand.nextInt() * Integer.MAX_VALUE;
	}
    }

    public void ordInit() {
	int x = 1;
	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < N; j++) {
		A[i][j] = x;
		x++;
	    }
	}
    }

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

    public void transpose(int[][] A, int m, int n, int N) {
	//	for (int i = 0; i < N - 1; i++) {
	//	    for (int j = i + 1; j < N; j++) {
	//		System.out.println("transposing " + A[i][j] + " with " + A[j][i]);
	//		int tmp = A[i][j];
	//		A[i][j] = A[j][i];
	//		A[j][i] = tmp;
	//		System.out.println();
	//	    }
	//	    
	//	}

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

    public void transposeOnDiag(int[][] A, int m, int n, int N) {

	//	System.out.printf("transpose on diag %dx%d @(%d,%d)\n", N, N, m, n);
	if (N < CONST) {
	    //	    System.out.println(N + " less than " + CONST);
	    //transpose(A);
	    transpose(A, m, n, N);

	}

	else {

	    //	    if (N % 2 != 0) {
	    //		
	    //		
	    //	    }
	    //	    System.out.println(Math.ceil(N / 2));
	    transposeOnDiag(A, m, n, N / 2);		// upper left submatrix
	    //transposeOnDiag(A, m + (int) Math.ceil(N / 2.0), n + (int) Math.ceil(N / 2.0), (int) Math.ceil(N / 2.0));	// lower right submatrix
	    transposeOnDiag(A, m + N / 2, n + N / 2, (int) Math.ceil(N / 2.0));	// lower right submatrix

	    //	    swap(A, m, N / 2, N / 2, N / 2, n, N / 2);
	    //	    swap(A, m, N / 2, N / 2, N / 2, n, N / 2);
	    //int M = N / 2;
	    //	    int rowA = 0; 	// first row pos for matrix A
	    //	    int colA = N / 2; 	// first col pos for matrix A
	    //	    int nRowsA = N / 2;
	    //	    int nColsA = (int) Math.ceil(N / 2.0);
	    transposeAndSwap(A, m, n + N / 2, N / 2, (int) Math.ceil(N / 2.0));
	    // 			m    n     M		N
	}
    }

    //    public void swap(int[][] A, int m, int n, int N, int r, int l, int L) {
    //    public void transposeAndSwap(int[][] A, int rowA, int colA, int nRowsA, int nColsA, int rowB, int colB, int nRowsB, int nColsB)
    public void transposeAndSwap(int[][] A, int m, int n, int M, int N) {
	//	System.out.printf("transpose and swap %dx%d @(%d,%d)\n", M, N, m, n);
	//	if (nRowsA < CONST && nColsA < CONST && nRowsB < CONST && nColsB < CONST) {
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

	else {

	    transposeAndSwap(A, m, n, M / 2, (int) Math.ceil(N / 2.0));	// uper right submatrix
	    transposeAndSwap(A, m, n + (int) Math.ceil(N / 2.0), M / 2, N / 2);	// lower left submatrix
	    transposeAndSwap(A, m + M / 2, n, (int) Math.ceil(M / 2.0), (int) Math.ceil(N / 2.0));	// uper right submatrix
	    transposeAndSwap(A, m + M / 2, n + (int) Math.ceil(N / 2.0), (int) Math.ceil(M / 2.0), N / 2);	// lower left submatrix

	}

    }

    public static void main(String[] args) {
	//System.out.println("starting");
	int B = Integer.parseInt(args[0]);	// block size B
	int C = Integer.parseInt(args[1]);	// cache size M
	int runs = 0; 			// iterations to average over
	String fout = "sim-" + B + "-" + C + ".csv";
	Path pathOut = Paths.get(System.getProperty("user.home")).resolve("code/ds/Matrix/output/" + fout);

	try (BufferedWriter out = Files.newBufferedWriter(pathOut, StandardOpenOption.WRITE,
		StandardOpenOption.CREATE)) {

	    // Iterate of increasing sizes N
	    for (int k = 54; k<124 ; k++) {
		int n = (int) Math.pow(2, k / 9.0);	// size of matrix 
		//System.out.println(n);
		Matrix M = new Matrix(n);
		M.randInit();			// init with random numbers
		//M.transpose(M.A, 0, 0, n);
		//	    System.out.println("E");
		//	    M = new Matrix(69);
		//	    M.randInit();

		long startTime = 0; //= System.currentTimeMillis();

		// Uncomment for cache simulator
       		M.transpose(M.A, 0, 0, n);
		// M.transposeOnDiag(M.A, 0, 0, n);
      		System.out.println("E");
		
		// Uncomment to time and write normal tests
		/*
		if (n < 150)  // more runs for smaller N
		    runs = 1000;
		else
		    runs = 100;// less runs for larger N
		
		// Ignore the first 10k iterations so as to allow the JVM to warm up
		for (int t = -runs; t < runs; t++) {
		    // Time transpose operation
		    if (t == 0) 
			startTime = System.nanoTime();
		    //M.transposeOnDiag(M.A, 0, 0, n);
		    M.transpose(M.A,0,0,n);
		    
		}
		
		long time = System.nanoTime() - startTime;

		double aveTime = (double) time / (double) runs;
		int nSwaps = (n*n-n)/2;  // number of pairs
		double meanSwapTime = aveTime / nSwaps;
		//System.out.println(n + " elap time  " + time);
		//System.out.println("ave time " + aveTime);
		//System.out.println("n swaps " +nSwaps);
		//System.out.println("mean swap time " +meanSwapTime);

		// Uncomment to write
		//out.write(n + "," + aveTime + "," + meanSwapTime +  "\n");
		*/		
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}

//   HashMap<Integer,Integer> params = new HashMap<>(5);
//
//    for (param : params) {
//	
//    }
//	String[] params = { "64, 64", "64, 1024", "64, 4096", "512, 512", "4096, 64" };
//
//	int B, C;	// block and cache sizes
//	for (int i = 0; i < params.length; i++) {
//	    String[] param = params[i].split(",");
//	    B = Integer.parseInt(param[0]);
//	    C = Integer.parseInt(param[1]);
//	    String fout = "naive-" + B + "-" + C + ".csv";
//	    Path pathOut = Paths.get(System.getProperty("user.home")).resolve("ds/" + fout);
//	    try (BufferedWriter out = Files.newBufferedWriter(pathOut, StandardOpenOption.WRITE,
//		    StandardOpenOption.WRITE)) {
//
//		//	    M.transpose(M.A, 0, 0, n);
//
//		//	    out.write(n + "," + aveSteps + "\n");
//
//		System.out.println("E");
//
//		for (int k = 54; k < 1000; k++) {
//
//		    final long startTime = System.currentTimeMillis();
//
//		    n = (int) Math.pow(2, k / 9.0);
//		    //System.out.println(n);
//		    M = new Matrix(n);
//		    M.randInit();
//		    //	    M.transpose(M.A, 0, 0, n);
//		    //	    System.out.println("E");
//		    //	    M = new Matrix(69);
//		    //	    M.randInit();
//
//		    M.transposeOnDiag(M.A, 0, 0, n);
//		    final long endTime = System.currentTimeMillis() - startTime;
//		    out.write(n + "," + endTime);
//		    
//		    if (k == 60)
//			break;
//		}
//	    } catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	    }

//M.ordInit();

//	M.print();
//	M.transpose(M.A, 0, 0, n);
//	M.print();
//	M.transpose(M.A, 0, 0, n);
//	M.print();
//	M.transposeOnDiag(M.A, 0, 0, n);
//	M.print();
