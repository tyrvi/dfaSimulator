import java.util.*;

public class GNFA {
	private String[] states;
	private String[] alphabet;
	private String[][] transitionFunction;
	private String startState;
	private String acceptState;
	private HashSet acceptStates;
	
	public GNFA (String[] states, String[] alphabet, String[][] transitionFunction, String startState, String[] acceptStates) {
		
		Boolean startStateDecider = checkForIncomingStartStateTransitions(startState, transitionFunction);
		this.startState = createNewStartState(startState, startStateDecider);
		this.states = createNewStateSet(startStateDecider, states);
		this.acceptStates = new HashSet(Arrays.asList(acceptStates));
		this.alphabet = alphabet;
	}

	private Boolean checkForIncomingStartStateTransitions(String startState, String[][] transitionFunction) {
		for (int i = 0; i < transitionFunction.length; ++i) {
			for (int j = 0; j < transitionFunction[0].length; ++j) {
				if (transitionFunction[i][j].equals(startState)) {
					//System.out.println("start");
					return true;
				}
			}
		}
		//System.out.println(startState);
		return false;
	}

	private String[] createNewStateSet(Boolean decider, String[] states) {
		
		if (decider == true) {
			System.out.println("decider is true");
			String[] startAndAccept = {startState, "accept"};
			int startAndAcceptlen = startAndAccept.length;
		}
		else {
			System.out.println("decider is false");
			String[] startAndAccept = {"accept"};
			int startAndAcceptlen = startAndAccept.length;
		}
		
		String[] newStateSet = new String[states.length + startAndAcceptlen];

		System.arraycopy(states, 0, newStateSet, 0, states.length);
		System.arraycopy(startAndAccept, 0, newStateSet, states.length, startAndAccept.length);

		System.out.println(Arrays.toString(newStateSet));

		return newStateSet;
	}
	
	private String createNewStartState(String startState, Boolean decider) {
		if (decider == true) return "accept";
		else return startState;
	}
}
