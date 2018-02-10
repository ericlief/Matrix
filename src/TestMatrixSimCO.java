
public class TestMatrixSimCO {

    public static void main(String[] args) {

	// Iterate for increasing sizes N
	for (int k = 54; k < 124; k++) {
	    int n = (int) Math.pow(2, k / 9.0); // size of matrix 

	    MatrixSim M = new MatrixSim(n);
	    M.randInit();           // init with random numbers

	    // Now print to cache simulator
	    // M.transpose(M.A, 0, 0, n);      // for simple transpose
	    M.transposeOnDiag(M.A, 0, 0, n);  // for COA

	    System.out.println("E");
	}
    }
}