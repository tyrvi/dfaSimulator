//import java.util.Scanner;
import java.io.*;
import java.util.*;
	
public class dfaSimulator {
	
	public static void main (String [] args) {
		//String filename = "input.txt";
		//File file = new File(filename);

		try {
			Scanner sc = new Scanner(new File("input.txt"));

			String[] states = States(sc.nextLine());
			String[] alphabet = Alphabet(sc.nextLine());
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				//System.out.println(line);
				//String[] sline = line.split(",");

				//for (int i = 0; i < sline.length; ++i) {
				//	System.out.println(sline[i]);
				//}
				String[] states = States(line);
				for (int i = 0; i < states.length; ++i) {
					System.out.println(states[i]);
				}
				
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}


	public static String[] States (String line) {
		String[] states = line.split(",");

		return states;
	}

	public static String[] Alphabet (String line) {
		String [] alphabet = line.split(",");

		return alphabet;
	}
}
