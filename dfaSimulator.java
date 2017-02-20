import java.io.*;
import java.util.*;
	
public class dfaSimulator {
	
	public static void main (String [] args) {
		
		try {
			Scanner sc = new Scanner(new File("input4.txt"));
			
			String[] states = splitLine(sc.nextLine());
			String[] alphabet = splitLine(sc.nextLine());
			String[][] transitionFunction = new String[states.length][alphabet.length];
			String currentState;
			String symbol;
			String transitionState;
			String[][] del = new String[states.length][alphabet.length];
			
			int counter = 0;
			int numTransitions = alphabet.length*states.length;
			
			while (sc.hasNextLine() && counter < numTransitions) {
				String[] line = sc.nextLine().split(",");
								
				currentState = line[0];
				symbol = line[1];
				transitionState = line[2];
				
				int posCurrentState = Arrays.asList(states).indexOf(currentState);
				int posSymbol = Arrays.asList(alphabet).indexOf(symbol);
				transitionFunction[posCurrentState][posSymbol] = transitionState;
				del[posCurrentState][Arrays.asList(states).indexOf(transitionState)] = symbol;				
				counter++;
			}		

			String startState = sc.nextLine();
			String[] acceptStates = splitLine(sc.nextLine());
			//GNFA gnfa = new GNFA(states, alphabet, transitionFunction, startState, acceptStates);
			//System.out.println(gnfa.toString());
			//System.out.println(gnfa.transitionFunctionToString());
			DFA dfa = new DFA(states, alphabet, transitionFunction, startState, acceptStates);
			//System.out.println(dfa.toString());
			//System.out.println(dfa.runDFA("0000110"));
			GNFA gnfa = new GNFA(dfa);
			System.out.println(gnfa.toString());
			//gnfa.createRegex();
			//gnfa.createRegexRecursive(gnfa);
			//System.out.println(gnfa.toString());
			//GNFA gnfa1 = dfa.convertToGNFA();
			//System.out.println(gnfa1.toString());
			String transitionFunctionString = "";
		
			for (int i = 0; i < del.length; ++i) {
				transitionFunctionString += "\t" + states[i];
			}
			transitionFunctionString += "\n";
			for (int i = 0; i < del.length; ++i) {
				transitionFunctionString += states[i] + "\t";
				for (int j = 0; j < del.length; ++j) {
					transitionFunctionString += del[i][j] + "\t";
				}
				transitionFunctionString += "\n";
			}

			System.out.println(transitionFunctionString);
		}
		
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}


	public static String[] splitLine (String line) {
		String[] array = line.split(",");

		return array;
	}

	public static void buildTransitionFunction() {
		
	}
}
