import java.util.*;

// Implements a GNFA from a DFA
public class GNFA {
	private String[] states;
	private String[] alphabet;
	private String[][] transitionFunction;
	private String startState;
	private String acceptState;
	private String[] acceptStates;
	private String[][] oldTransitionFunction;

	// 5-tuple constructor assuming tuple describes a DFA
	public GNFA (String[] states, String[] alphabet, String[][] transitionFunction, String startState, String[] acceptStates) {
		Boolean startStateDecider = checkForIncomingStartStateTransitions(startState, transitionFunction);
		this.alphabet = createNewAlphabet(alphabet);
		this.acceptStates = acceptStates;
		if (startStateDecider == true) {
			this.startState = "start";
			this.states = createStateSetWithStart(this.startState, states);
		}
		else {
			this.startState = startState;
			this.states = createStateSetWithoutNewStart(this.startState, states);
		}
		this.transitionFunction = createNewTransitionFunction(transitionFunction, startState);
		this.acceptState = "accept";
		this.oldTransitionFunction = transitionFunction;
	}

	// DFA object constructor
	public GNFA (DFA dfa) {
		String[] states = dfa.getStates();
		String[] alphabet = dfa.getAlphabet();
		String[][] transitionFunction = dfa.getTransitionFunction();
		String startState = dfa.getStartState();
		String[] acceptStates = dfa.getAcceptStates();
		
		Boolean startStateDecider = checkForIncomingStartStateTransitions(startState, transitionFunction);
		this.alphabet = createNewAlphabet(alphabet);
		this.acceptStates = acceptStates;
		if (startStateDecider == true) {
			this.startState = "start";
			this.states = createStateSetWithStart(this.startState, states);
		}
		else {
			this.startState = startState;
			this.states = createStateSetWithoutNewStart(this.startState, states);
		}
		this.transitionFunction = createNewTransitionFunction(transitionFunction, startState);
		this.acceptState = "accept";
		this.oldTransitionFunction = transitionFunction;
	}

	// null constructor
	public GNFA () {
		this.states = null;
		this.alphabet = null;
		this.transitionFunction = null;
		this.startState = null;
		this.acceptState = null;
		this.acceptStates = null;
		this.oldTransitionFunction = null;
	}

	// Add the "empty" string to the alphabet
	private String[] createNewAlphabet(String[] alphabet) {
		String[] newAlphabet = new String[alphabet.length+1];

		for (int i = 0; i < alphabet.length; ++i) {
			newAlphabet[i] = alphabet[i];
		}

		newAlphabet[newAlphabet.length - 1] = "empty";

		return newAlphabet;
	}

	// Check if we need to create a new start state returns true if yes
	private Boolean checkForIncomingStartStateTransitions(String startState, String[][] transitionFunction) {
		for (int i = 0; i < transitionFunction.length; ++i) {
			for (int j = 0; j < transitionFunction[0].length; ++j) {
				if (transitionFunction[i][j].equals(startState)) {
					return true;
				}
			}
		}
		return false;
	}

	private String[] createStateSetWithStart(String startState, String[] states) {
		String[] startAndAccept = {startState, "accept"};
		String[] newStateSet = new String[states.length + startAndAccept.length];

		System.arraycopy(states, 0, newStateSet, 0, states.length);
		System.arraycopy(startAndAccept, 0, newStateSet, states.length, startAndAccept.length);

		return newStateSet;
	}
	
	private String[] createStateSetWithoutNewStart(String startState, String[] states) {
		String[] startAndAccept = {"accept"};
		String[] newStateSet = new String[states.length + startAndAccept.length];

		System.arraycopy(states, 0, newStateSet, 0, states.length);
		System.arraycopy(startAndAccept, 0, newStateSet, states.length, startAndAccept.length);

		return newStateSet;
	}

	// Create the transition function for the GNFA
	private String[][] createNewTransitionFunction(String[][] transitionFunction, String startState) {
		HashSet acceptStates = new HashSet(Arrays.asList(this.acceptStates));
		String[][] newTransitionFunction = new String[this.states.length][this.alphabet.length];
		// New transition function needs to create empty transition to new accept state
		for (int i = 0; i < transitionFunction.length; ++i) {
			for (int j = 0; j < transitionFunction[0].length; ++j) {
				newTransitionFunction[i][j] = transitionFunction[i][j];
			}
			if (acceptStates.contains(this.states[i])) {
				newTransitionFunction[i][newTransitionFunction[0].length-1] = "accept";
			}
		}

		// Start state has null transition to original start state
		if (this.startState == "start") {
			newTransitionFunction[newTransitionFunction.length-2][this.alphabet.length-1] = startState;
		}

		return newTransitionFunction;
	}

	public void createRegex() {
		List states = new ArrayList(Arrays.asList(this.states));
		int k = states.size();
		startState = this.startState;
		acceptState = this.acceptState;
		String[][] transitionFunction = this.transitionFunction;
		List alphabet = new ArrayList(Arrays.asList(this.alphabet));
		convertTransitionFunction(states, alphabet, transitionFunction);
	}

	public void convertTransitionFunction(List states, List alphabet, String[][] transitionFunction) {
		List<List<String>> delta = new ArrayList<List<String>>();
		String[][] del = new String[states.size()][states.size()];
		
		//System.out.println(states.size());

		for (int i = 0; i < transitionFunction.length; ++i) {
			//System.out.println(Arrays.toString(transitionFunction[i]));
			for (int j = 0; j < transitionFunction[i].length; ++j) {
				String d = "(qi, qj):(" + states.get(i) + ", " + transitionFunction[i][j] + ") = " + alphabet.get(j);
				System.out.println(d);
				//if (transitionFunction[i][j] == null)
				//del[i][states.indexOf(transitionFunction[i][j])] = alphabet.get(j).toString();
			}
		}
		
		String transitionFunctionString = "";
		
		for (int i = 0; i < del.length; ++i) {
			transitionFunctionString += "\t" + states.get(i);
		}
		transitionFunctionString += "\n";
		for (int i = 0; i < del.length; ++i) {
			transitionFunctionString += states.get(i) + "\t";
			for (int j = 0; j < this.alphabet.length; ++j) {
				transitionFunctionString += del[i][j] + "\t";
			}
			transitionFunctionString += "\n";
		}

		System.out.println(transitionFunctionString);
		
	}

	public GNFA createRegexRecursive(GNFA gnfa) {
		List<String> states = new ArrayList<String>(Arrays.asList(gnfa.getStates()));
		String startState = gnfa.getStartState();
		String acceptState = gnfa.getAcceptState();
		String[][] transitionFunction = gnfa.getTransitionFunction();
		String[] alphabet = gnfa.getAlphabet();
		
		if (states.size() == 2) {
			System.out.println(gnfa.toString());
			return gnfa;
		}
		int i = 0;
		
		System.out.println(states.get(i));
		states.remove(states.get(i));
		//List<String> statesWithoutStartAndAccept = states;
		
		//statesWithoutStartAndAccept.remove(gnfa.getStartState());
		//statesWithoutStartAndAccept.remove(gnfa.getAcceptState());
		
		//String qrip = statesWithoutStartAndAccept.get(0);
		//System.out.println(statesWithoutStartAndAccept.get(0));
		//statesWithoutStartAndAccept.remove(0);
		String[] updatedStates = states.toArray(new String[states.size()]);
		String[] acceptStates = gnfa.getAcceptStates();
		GNFA updatedGNFA = new GNFA(updatedStates, alphabet, transitionFunction, startState, acceptStates);

		return createRegexRecursive(updatedGNFA);
		
	}

	public void updateTransitionFunction(String[][] transitionFunction) {
		
	}

	public String transitionFunctionToString() {
		String transitionFunctionString = "";
		
		for (int i = 0; i < this.alphabet.length; ++i) {
			transitionFunctionString += "\t" + alphabet[i];
		}
		transitionFunctionString += "\n";
		for (int i = 0; i < this.states.length; ++i) {
			transitionFunctionString += this.states[i] + "\t";
			for (int j = 0; j < this.alphabet.length; ++j) {
				transitionFunctionString += this.transitionFunction[i][j] + "\t";
			}
			transitionFunctionString += "\n";
		}
		return transitionFunctionString;
	}

	public String alphabetToString() {
		return Arrays.toString(this.alphabet);
	}

	public String statesToString() {
		return Arrays.toString(this.states);
	}

	public String toString() {
		String pretty = "States = " + statesToString() + "\nAlphabet = " + alphabetToString() + "\nTransition Function\n" + transitionFunctionToString() + "Start state = " + this.startState + "\nAccept State = " + this.acceptState;
		return pretty;
	}

	public String[] getStates() {
		return this.states;
	}

	public String[] getAlphabet() {
		return this.alphabet;
	}

	public String[][] getTransitionFunction() {
		return this.transitionFunction;
	}

	public String getStartState() {
		return this.startState;
	}

	public String getAcceptState() {
		return this.acceptState;
	}

	public String[] getAcceptStates() {
		return this.acceptStates;
	}
}
