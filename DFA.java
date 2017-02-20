import java.util.*;

public class DFA {
	
	private String[] states;
	private String[] alphabet;
	private String[][] transitionFunction;
	private String startState;
	private HashSet acceptStates;
	
	public DFA(String[] states, String[] alphabet, String[][] transitionFunction, String startState, String[] acceptStates) {
		this.states = states;
		this.alphabet = alphabet;
		this.transitionFunction = transitionFunction;
		this.startState = startState;
		this.acceptStates = new HashSet(Arrays.asList(acceptStates));
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

		for (int i = 0; i < input.length(); ++i) {
			symbol = Character.toString(input.charAt(i));
			indexOfState = Arrays.asList(this.states).indexOf(state);
			indexOfSymbol = Arrays.asList(this.alphabet).indexOf(symbol);
			state = transitionFunction[indexOfState][indexOfSymbol];
		}

		return acceptStates.contains(state);
  	}
	/*
	public GNFA convertToGNFA(DFA dfa) {
		GNFA gnfa = new GNFA(this.states, this.alphabet, this.transitionFunction, this.startState, this.acceptStates);
	
		return gnfa;
		}*/

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

	public HashSet getAcceptStates() {
		return this.acceptStates;
	}
	
	public String createTransitionFunctionString() {
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
}
