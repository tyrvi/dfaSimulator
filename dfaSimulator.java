import java.io.*;
import java.util.*;
	
public class dfaSimulator {
	
	public static void main (String [] args) {

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a file name: ");
		String file = sc.nextLine();
		DFA dfa = createDFAFromFile(file);
		//DFA dfa = createDFAFromFile("input1.txt");
		System.out.println(dfa.toString() + "\n");
		//System.out.println(dfa.runDFA(""));
		//GNFA gnfa = new GNFA(states, alphabet, transitionFunction, startState, acceptStates);
		//GNFA gnfa = dfa.convertToGNFA();
		GNFA gnfa = new GNFA(dfa);
		System.out.println(gnfa.toString() + "\n");
			
		String x = gnfa.createRegex();
		System.out.println(x);
		
	}

	public static DFA createDFAFromFile(String fileName) {
		try {
			Scanner sc = new Scanner(new File(fileName));

			String[] states = sc.nextLine().split(",");
			String[] alphabet = sc.nextLine().split(",");
			String[][] transitionFunction = new String[states.length][alphabet.length];
			String currentState;
			String symbol;
			String transitionState;
						
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
			String[] acceptStates = sc.nextLine().split(",");

			DFA dfa = new DFA(states, alphabet, transitionFunction, startState, acceptStates);

			return dfa;
							
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			
			return null;
		}
		
	}

}
