import java.util.HashMap;

public class dfa {
	
	private String[] states;
	private String[] alphabet;
	private HashMap transitionFunction;
	private String[] startState;
	private String[] acceptStates;
	
	public dfa(String[] states, String[] alphabet, HashMap transitionFunction, String startState, String[] acceptStates) {
		this.states = states;
		this.alphabet = alphabet;
		this.transitionFunction = transitionFunction;
		this.startState = startState;
		this.acceptStates = acceptStates;
	}

	public dfa() {
		this.states = null;
		this.alphabet = null;
		this.transitionFunction = null;
		this.startState = null;
		this.acceptStates = null;
	}

	
}
