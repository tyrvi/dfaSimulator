import java.io.*;
import java.util.*;
	
public class dfaSimulator {
	
	public static void main (String [] args) {
		
		try {
			Scanner sc = new Scanner(new File("input.txt"));

			String[] states = createStatesArray(sc.nextLine());
			String[] alphabet = createAlphabetArray(sc.nextLine());
			HashMap transitionFunction;
			
			int counter = 0;
			
			//System.out.println(states);
			//System.out.println(alphabet);
			
			while (sc.hasNextLine() && counter < 2*states.length) {
				String[] line = sc.nextLine().split(",");
				Tuple transition = new Tuple(line[0], line[1]);
				System.out.println(transition.getValue(0) + " " + transition.getValue(1));
				counter++;
				//System.out.println(line);
				//String[] sline = line.split(",");

				//for (int i = 0; i < sline.length; ++i) {
				//	System.out.println(sline[i]);
				//}
			}

			String startState = sc.nextLine();
			String acceptStates = sc.nextLine();
			//System.out.println(startState);
			//System.out.println(acceptStates);

			//Set set = transitionFunction.entrySet();
			//Iterator i = set.iterator();

			//while (i.hasNext()) {
			//	Map.Entry curr = (Map.Entry)i.next();
			//	System.out.println(curr.getKey() + ": " + curr.getValue());
			//}
		}
		
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}


	public static String[] createStatesArray (String line) {
		String[] states = line.split(",");

		return states;
	}

	public static String[] createAlphabetArray (String line) {
		String [] alphabet = line.split(",");

		return alphabet;
	}

	public static String[] createAcceptStatesArray (String line) {
		String [] acceptStates = line.split(",");

		return acceptStates;
	}

	//public static addTranstitionToFunction (string line) {
		
	//}
}
