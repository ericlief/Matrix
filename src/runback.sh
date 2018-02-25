javac -d ../bin *.java
gcc cachesim.c -o cachesim

# Cache sim tests
#java -cp ../bin MatrixSim 64 64 | ./cachesim 64 64 >> ../output/"sim-obliv64-64.csv"
#java -cp ../bin Matrix 64 1024 | ./cachesim 64 1024 >> ../output/"sim-obliv64-1024.csv"
#java -cp ../bin Matrix 64 4096 | ./cachesim 64 4096 >> ../output/"sim-obliv64-4096.csv"
#java -cp ../bin Matrix 512 512 | ./cachesim 512 512 >> ../output/"sim-obliv512-512.csv"
#java -cp ../bin Matrix 4096 64 | ./cachesim 4096 64 >> ../output/"sim-obliv4096-64.csv"

#java -cp ../bin Matrix 64 64 | ./cachesim 64 64 >> ../output/"sim-naive64-64.csv"
#java -cp ../bin Matrix 64 1024 | ./cachesim 64 1024 >> ../output/"sim-naive64-1024.csv"
#java -cp ../bin Matrix 64 4096 | ./cachesim 64 4096 >> ../output/"sim-naive64-4096.csv"
#java -cp ../bin Matrix 512 512 | ./cachesim 512 512 >> ../output/"sim-naive512-512.csv"
#java -cp ../bin Matrix 4096 64 | ./cachesim 4096 64 >> ../output/"sime-naive4096-64.csv"

#java Matrix 64 64 | ./cachesim 64 64 >> ../output/"cachesim-obliv64-64.csv"
#java Matrix 64 1024 | ./cachesim 64 1024 >> ../output/"cachesim-obliv64-1024.csv"
#java Matrix 64 4096 | ./cachesim 64 4096 >> ../output/"cachesim-obliv64-4096.csv"
#java Matrix 512 512 | ./cachesim 512 512 >> ../output/"cachesim-obliv512-512.csv"
#java Matrix 4096 64 | ./cachesim 4096 64 >> ../output/"cachesim-obliv4096-64.csv"

# Normal tests
#java -cp ../bin TestMatrixNaive 64 64 
#java -cp ../bin TestMatrixNaive 64 1024
#java -cp ../bin TestMatrixNaive 64 4096
#java -cp ../bin TestMatrixNaive 512 512
#java -cp ../bin TestMatrixNaive 4096 64
#java -cp ../bin TestMatrixCO 64 64 
#java -cp ../bin TestMatrixCO 64 1024
#java -cp ../bin TestMatrixCO 64 4096
#java -cp ../bin TestMatrixCO 512 512
java -cp ../bin TestMatrixCO 4096 64
