import java.io.*;
import java.util.*;
	
public class dfaSimulator {
	
	public static void main (String [] args) {
		
		try {
			Scanner sc = new Scanner(new File("input3.txt"));
			
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
			   				
				counter++;
			}		

			String startState = sc.nextLine();
			String[] acceptStates = splitLine(sc.nextLine());
			

			DFA dfa = new DFA(states, alphabet, transitionFunction, startState, acceptStates);
			System.out.println("DFA:\n" + dfa.toString() + "\n");
			//System.out.println(dfa.runDFA(""));
			//GNFA gnfa = new GNFA(states, alphabet, transitionFunction, startState, acceptStates);
			//GNFA gnfa = dfa.convertToGNFA();
			GNFA gnfa = new GNFA(dfa);
			System.out.println("GNFA:\n" + gnfa.toString() + "\n");
			
			gnfa.createRegex();
		}
		   
		
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}


	public static String[] splitLine (String line) {
		String[] array = line.split(",");

		return array;
	}
}
