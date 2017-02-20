import java.util.*;

// Implements a GNFA from a DFA
public class GNFA {
	private String[] states;
	private String[] alphabet;
	private String[][] transitionFunction;
	private String startState;
	private String acceptState;
	private String[] acceptStates;

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
	}

	// null constructor
	public GNFA () {
		this.states = null;
		this.alphabet = null;
		this.transitionFunction = null;
		this.startState = null;
		this.acceptState = null;
		this.acceptStates = null;
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

}
