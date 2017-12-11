

javac -d ../bin Matrix.java
#javac Matrix.java



#java -cp ../bin Matrix 64 64 | ./cachesim 64 64


#java -cp ../bin Matrix 64 64 | ./cachesim 64 64 >> ../output/"cachesim-obliv64-64.csv"
#java -cp ../bin Matrix 64 1024 | ./cachesim 64 1024 >> ../output/"cachesim-obliv64-1024.csv"
#java -cp ../bin Matrix 64 4096 | ./cachesim 64 4096 >> ../output/"cachesim64-4096.csv"
#java -cp ../bin Matrix 512 512 | ./cachesim 512 512 >> ../output/"cachesim512-512.csv"
java -cp ../bin Matrix 4096 64 | ./cachesim 4096 64 >> ../output/"cachesim4096-64.csv"

#java Matrix 64 64 | ./cachesim 64 64 >> ../output/"cachesim-obliv64-64.csv"
#java Matrix 64 1024 | ./cachesim 64 1024 >> ../output/"cachesim-obliv64-1024.csv"
#java Matrix 64 4096 | ./cachesim 64 4096 >> ../output/"cachesim-obliv64-4096.csv"
#java Matrix 512 512 | ./cachesim 512 512 >> ../output/"cachesim-obliv512-512.csv"
#java Matrix 4096 64 | ./cachesim 4096 64 >> ../output/"cachesim-obliv4096-64.csv"


#java -cp ../bin Matrix 64 64 | ./cachesim 64 64
#java -cp ../bin Matrix 64 1024 | ./cachesim 64 1024
#java -cp ../bin Matrix 64 4096 | ./cachesim 64 4096
#java -cp ../bin Matrix 512 512 | ./cachesim 512 512
#java -cp ../bin Matrix 4096 64 | ./cachesim 4096 64
