/* Program Project: Calculator
 * Program Name: CalculatorDriver - 1 of 2
 * Program Developer: Jordan Halaby
 * Date due: 1/25/16
 * Program Description: This program is used to parse a given program into tokens
 * then it checks the BNF to make sure that the program parses correctly
 */

// import needed libraries
import java.util.*;

// Create class CalculatorDriver
public class CalculatorDriver {
	// create boolean variable for validation
	public static boolean valid = true;
	
	// Create Scanner object
	static Scanner keyboard;
	
	// Create string for the input program name
	static String programName;
	static String program;
	
	// Create Calculator object
	static Calculator calc;
	
	// main method
	public static void main(String[] args){
		// Prompt for program name
		System.out.println("Please enter program file name: ");
		
		// Get program name from user
		keyboard = new Scanner(System.in);
		programName = keyboard.nextLine();
		
		// close scanner object
		keyboard.close();
		
		// Get file path for program
		program = "C:/Users/halab_000/Documents/workspace/Compiler_Project1/src/" + programName;

		// Create calculator object with parameter of program name
		calc = new Calculator(program);
		
		// run calculator's program method and return the validity of the entered program
		valid = calc.program();
		
		// Check if parsing was valid and print success statement
		if(valid){
			System.out.println("Program parsed successfully");
		}
		else{
			System.out.println("Error parsing program");
		}
	}
}
