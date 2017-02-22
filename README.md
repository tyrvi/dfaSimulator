# DFA Simulator
## CS 4413 Automata Homework 5
### Thais Minet

# Overview
This program creates a DFA from a text file, converts the DFA to a GNFA and the GNFA to a regular expression. To run the program compile and then run **dfaSimulator.java** which contains the main function.

1. **dfaSimulator.java** contains the main function which takes input from the command line
  1. First prompts the user for an input file. 
  2. Then allows the selection of either testing strings on the created DFA, creating and displaying the GNFA created from the DFA or creating a regular expression from the DFA by converting the DFA to a GNFA and then to a regular expression.
2. **DFA.java** contains the DFA class which allows the creation of a DFA from a set of states, an alphabet, a transition function, start state, and set of final states.
  * Also allows the input of strings on the DFA to see if they are accepted or rejected.
3. **GNFA.java** contains the GNFA class which allows the creation of a GNFA from a DFA object, or a set of states, an alphabet, a transition function, start state, and set of final states.
  * Also contains a method for creating a regular expression from the GNFA.

# Input file
The input file is expected to be of the format not including everything after '#' on each line. Each state or symbol is expected to be separated by a ',' and there should not be any whitespace.
```
q1,q2,q3,q4 # states
0,1 # alphabet
q1,0,q1 # delta(q1,0) = q1
q1,1,q2
q2,0,q2
q2,1,q3
q3,0,q3
q3,1,q4
q4,0,q2
q4,1,q3
q1 # start state
q3,q4 # accept states
```
# DFA and GNFA
When being displayed the DFA and GNFA appear in the following format

DFA:

States = [q1, q2]

Alphabet = [0, 1]

Transition Function

|   | 0 |	1 |
|---|---|---|
|q1|q2|q1|	
|q2|q1|q2|	

Start state = q1

Accept States = [q2]

GNFA:

States = [q1, q2, start, accept]
Alphabet = [0, 1, ϵ]

Transition Function

|      |   0  |   1  |   ϵ  |
|------|------|------|------|
|q1    |q2    |q1    |null  |
|q2    |q1    |q2    |accept|
|start |null  |null  |q1    |	
|accept|null  |null  |null  |

Start state = start

Accept State = accept



Compiled with:
```
javac 1.8.0_121 and run with 
```
Tested using:
```
java version "1.8.0_101"
Java(TM) SE Runtime Environment (build 1.8.0_101-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.101-b13, mixed mode)
```
