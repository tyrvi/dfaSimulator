import java.io.*;
import java.util.*;
	
public class dfaSimulator {
	
	public static void main (String [] args) {
		
		try {
			Scanner sc = new Scanner(new File("input.txt"));
			
			String[] states = splitLine(sc.nextLine());
			String[] alphabet = splitLine(sc.nextLine());
			Map<List<String>, String> transitionFunction = new HashMap<List<String>, String>();
			
			int counter = 0;
						
			while (sc.hasNextLine() && counter < 2*states.length) {
				String[] line = sc.nextLine().split(",");
				List<String> temp = new ArrayList<String>(Arrays.asList(line[0], line[1]));
				List<String> unmodList = Collections.unmodifiableList(temp);
				transitionFunction.put(unmodList, line[2]);
				counter++;
			}

			String startState = sc.nextLine();
			String[] acceptStates = splitLine(sc.nextLine());

			DFA dfa = new DFA(states, alphabet, transitionFunction, startState, acceptStates);
			//dfa.printTransitionFunction();
			System.out.println(dfa.runDFA("11101100010001"));
			
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
