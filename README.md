# 2D Project

- SAT-Solver
- .BC generation for CEC for 32bitAdder and our Adder Implementation

## SAT-Solver
solver in java. takes in CNF format.
Uses a Path finding SCC algorithm for satisfiability checking, and Graphing with DFS for 2SAT problems.
Fallbacks to DPLL if the input CNF file is not a 2SAT problem.

Test files are in the test_files folder.

1. Set your run configurations with the test file you want to run with as args[0].
2. Increase your stack size if the .CNF file is big (a few hundred clauses), under VM arguments. (-Xss16M)
3. If you are sure the 2SAT problem is satisfiable, set **dangerous** for args[1].
   This will skip satisfiability checking for a faster speed.
4. Run and win caifan money.

## BC converters

1. Run 32addertobc.py to generate 32Adder.bc
2. Run bkaddertobc.rb to generate bkAdder.bcsolver in java. takes in CNF format.
Uses a Path finding SCC algorithm for satisfiability checking, and Graphing with DFS for 2SAT problems.
Fallbacks to DPLL if the input CNF file describes other SAT problems.
3. Merge the .bc files together
4. Add a ```Result:=sX:SX``` into your combined .bc file at the end, where **X** is your desired bit to compare.
5. Make bc2cnf
6. Convert the .bc file into .CNF.
7. Check the result bit, and set it at the bottom of the .CNF with ```X 0```, where **X** is your result bit.
8. Increase the clause variable at **p** to prevent an ArrayOutOfBoundsIndex.
9. Run the resultant .CNF file with the SAT Solver. It should be unsatisfiable.