import java.util.*;

// Implements a GNFA can be created from a 5-tuple (states, alphabet, transition function, start state, accept states) or from a DFA object
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

		newAlphabet[newAlphabet.length - 1] = "\u03F5";

		return newAlphabet;
	}

	// Check if we need to create a new start state returns true if we do
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

	// We need to create a new start state so we add the new start and accept states to our set of states
	private String[] createStateSetWithStart(String startState, String[] states) {
		String[] startAndAccept = {startState, "accept"};
		String[] newStateSet = new String[states.length + startAndAccept.length];

		System.arraycopy(states, 0, newStateSet, 0, states.length);
		System.arraycopy(startAndAccept, 0, newStateSet, states.length, startAndAccept.length);

		return newStateSet;
	}

	// We do not need to create a new start state so we only add a new accept state to our set of states
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

		// Start state has empty transition to original start state
		if (this.startState == "start") {
			newTransitionFunction[newTransitionFunction.length-2][this.alphabet.length-1] = startState;
		}

		return newTransitionFunction;
	}

	// Create regular expression from the GNFA
	public String createRegex() {
		List states = new ArrayList(Arrays.asList(this.states));
		startState = this.startState;
		acceptState = this.acceptState;
		String[][] transitionFunction = this.transitionFunction;
		List alphabet = new ArrayList(Arrays.asList(this.alphabet));
		String[][] transitionTable = convertTransitionFunctionToTransitionTable(states, alphabet, transitionFunction);

		List statesCopy = new ArrayList(states);
		List statesWithoutStart = new ArrayList(states);
		List statesWithoutAccept = new ArrayList(states);
		statesWithoutStart.remove(startState);
		statesWithoutAccept.remove(acceptState);
		String qrip = "";
		String qi;
		String qj;
		int qripIndex = 0;
		int qiIndex = 0;
		int qjIndex = 0;
		String R1 = "";
		String R2 = "";
		String R3 = "";
		String R4 = "";
		String deltaPrime = "";

		// Non-recursive implementation of Convert(G) on page 73
		while (statesCopy.size() > 2) {
			//Choose qrip as long as it is not the start state and not the accept state
			for (int k = 0; k < statesCopy.size(); ++k) {
				if (!(startState.equals(statesCopy.get(k).toString())) && !(acceptState.equals(statesCopy.get(k).toString()))) {
					qrip = statesCopy.get(k).toString();
					qripIndex = states.indexOf(qrip);
					statesCopy.remove(qrip);
					statesWithoutStart.remove(qrip);
					statesWithoutAccept.remove(qrip);
					break;
				}
			}
			
			for (int i = 0; i < statesWithoutAccept.size(); ++i) {
				qi = statesWithoutAccept.get(i).toString();
				for (int j = 0; j < statesWithoutStart.size(); ++j) {
					qj = statesWithoutStart.get(j).toString();
					qiIndex = states.indexOf(qi);
					qjIndex = states.indexOf(qj);
					
					R1 = transitionTable[qiIndex][qripIndex];
					R2 = transitionTable[qripIndex][qripIndex];
					R3 = transitionTable[qripIndex][qjIndex];
					R4 = transitionTable[qiIndex][qjIndex];
					
					if ((R1 == null || R2 == null || R3 == null) && R4 == null) {
						deltaPrime = null;
					}
					else if (R1 == null || R2 == null || R3 == null) {
						deltaPrime = "(" + R4 + ")";
					}
					else if (R4 == null) {
						deltaPrime = "(" + R1 + ")" + "(" + R2 + ")*" + "(" + R3 + ")";
					}
					else {
						deltaPrime = "(" + R1 + ")" + "(" + R2 + ")*" + "(" + R3 + ")" + "\u222A(" + R4 + ")";
					}
					
					transitionTable[qiIndex][qjIndex] = deltaPrime;
				}
			}
			
		
		}
		
		return transitionTable[states.indexOf(startState)][states.indexOf(acceptState)];
	}

	public String[][] convertTransitionFunctionToTransitionTable(List states, List alphabet, String[][] transitionFunction) {
		String[][] delta = new String[states.size()][states.size()];
		
		for (int i = 0; i < transitionFunction.length; ++i) {
			for (int j = 0; j < transitionFunction[i].length; ++j) {
				if (transitionFunction[i][j] != null) {
					if (delta[i][states.indexOf(transitionFunction[i][j])] == null) {
						delta[i][states.indexOf(transitionFunction[i][j])] = alphabet.get(j).toString();
					}
					else {
						delta[i][states.indexOf(transitionFunction[i][j])] += "\u222A" +  alphabet.get(j).toString();
					}
				}
				
			}
		}
		/*
		String transitionFunctionString = "";
		
		for (int i = 0; i < delta.length; ++i) {
			transitionFunctionString += "\t" + this.states[i];
		}
		transitionFunctionString += "\n";
		for (int i = 0; i < delta.length; ++i) {
			transitionFunctionString += this.states[i] + "\t";
			for (int j = 0; j < delta.length; ++j) {
				transitionFunctionString += delta[i][j] + "\t";
			}
			transitionFunctionString += "\n";
		}
		System.out.println(transitionFunctionString);
		*/
		return delta;
	}


	
	// Print and get methods
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
		String pretty = "GNFA:\nStates = " + statesToString() + "\nAlphabet = " + alphabetToString() + "\nTransition Function\n" + transitionFunctionToString() + "Start state = " + this.startState + "\nAccept State = " + this.acceptState;
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
