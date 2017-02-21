import java.util.*;

public class DFA {
	
	private String[] states;
	private String[] alphabet;
	private String[][] transitionFunction;
	private String startState;
	private String[] acceptStates;
	private String[][] transitionTable;
	//private HashSet acceptStates;
	
	public DFA(String[] states, String[] alphabet, String[][] transitionFunction, String startState, String[] acceptStates) {
		this.states = states;
		this.alphabet = alphabet;
		this.transitionFunction = transitionFunction;
		this.startState = startState;
		this.acceptStates = acceptStates;
	}
	
	public DFA() {
		this.states = null;
		this.alphabet = null;
		this.transitionFunction = null;
		this.startState = null;
		this.acceptStates = null;
	}
	
	public Boolean runDFA(String input) {
		String state = this.startState;
		String symbol;
		int indexOfState;
		int indexOfSymbol;
		HashSet acceptStates = new HashSet(Arrays.asList(this.acceptStates));

		for (int i = 0; i < input.length(); ++i) {
			symbol = Character.toString(input.charAt(i));
			indexOfState = Arrays.asList(this.states).indexOf(state);
			indexOfSymbol = Arrays.asList(this.alphabet).indexOf(symbol);
			state = transitionFunction[indexOfState][indexOfSymbol];
		}

		return acceptStates.contains(state);
  	}
	
	public GNFA convertToGNFA() {
		GNFA gnfa = new GNFA(this.states, this.alphabet, this.transitionFunction, this.startState, this.acceptStates);
	
		return gnfa;
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

	public String[] getAcceptStates() {
		return this.acceptStates;
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

	public String acceptStatesToString() {
		return Arrays.toString(this.acceptStates);
	}

	public String toString() {
		String pretty = "States = " + statesToString() + "\nAlphabet = " + alphabetToString() + "\nTransition Function\n" + transitionFunctionToString() + "Start state = " + this.startState + "\nAccept State = " + acceptStatesToString();
		return pretty;
	}
}
