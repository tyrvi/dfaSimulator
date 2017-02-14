import java.util.*;

public class DFA {
	
	private String[] states;
	private String[] alphabet;
	private Map<List<String>, String> transitionFunction;
	private String startState;
	private String[] acceptStates;
	
	public DFA(String[] states, String[] alphabet, Map<List<String>, String> transitionFunction, String startState, String[] acceptStates) {
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
		String nextState = this.startState;
		String finalState = "";
		String symbol = "";
		List<String> transition = new ArrayList<String>();
		
		for (int i = 0; i < input.length(); ++i) {
			//System.out.println(nextState + ": " + Character.toString(input.charAt(i)));
			transition = Arrays.asList(nextState, Character.toString(input.charAt(i)));
			nextState = transitionFunction.get(transition);
		}

		for (int j = 0; j < this.acceptStates.length; ++j) {
			if (nextState.equals(this.acceptStates[j])) {
				return true;
			}
		}
		return false;
	}
	
	public void printTransitionFunction() {
		Set set = transitionFunction.entrySet();
      
		Iterator i = set.iterator();
      
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			System.out.print(me.getKey() + ": ");
			System.out.println(me.getValue());
		}
	}
	
}
