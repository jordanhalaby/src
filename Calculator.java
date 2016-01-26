/* Program Project: Calculator
 * Program Name: Calculator - 2 of 2
 * Program Developer: Jordan Halaby
 * Date due: 1/25/16
 * Program Description: This program is used to parse a given program into tokens
 * then it checks the BNF to make sure that the program parses correctly
 */

// import needed libraries
import java.io.*;
import java.util.*;

// Create class Calculator
public class Calculator {
	
	// create ArrayList for the tokens
	public static ArrayList<String> tokens = new ArrayList<String>();
	
	// create arrays for keywords, ids and numbers
	public static String[] keywords = {"$$", ":=", "read", "write", "(", ")", "+", "-", "*", "/"};
	public static String[] ids = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	public static String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

	public static ArrayList<String> tempTokens;
	public static ArrayList<String> lines;
	
	// create inputToken and currToken variables
	public static String inputToken;
	public static String currToken;

	// declare line variable
	public static String line;
	
	// set ArrayList index to 0
	public static int index = 0;
	
	// create boolean valid variable
	public static boolean valid = true;

	// create bufferedreader object
	public static BufferedReader reader;
	
	// create int listlen and String tokenInstance
	public static int listlen;
	public static String tokenInstance;
	
	// create int lineSize
	public static int lineSize;
	
	// create string currLine
	public static String currLine;
	
	// Create array of tokens
	public static String toks[];
			
	// create int toklen
	public static int toklen;
	
	// Calculator constructor - argument of program name
	public Calculator(String programName){
		// get all tokens from given program
		tokens = getTokens(programName); 
	}
	
	// program method returns validity of entered program
	public boolean program(){
		// get input token from index
		inputToken = currToken(index);
				
		// switch-case to determine what to match
		switch(inputToken){
			case "id":
			case "read":
			case "write":
			case "$$":
				stmt_list();
				match("$$");
				break;
			default:
				parse_error("program");
				break;
		}	
		return valid;
	}
	// declare match method with parameter of token to match
	public static void match(String tok){
			
		// get current token from index
		try{
			currToken = tokens.get(index);
		}
		catch(Exception e){
			// parse error
			parse_error("match");
			return;
		}
		// if token is consistent with matching token, increment index
		if(tok.equalsIgnoreCase(currToken)){
			index++;
		}
		else{
			// parse error
			parse_error("match");
			index++;
		}
		//index++;
	}
		
		
	// statement list method
	public static void stmt_list(){

		// get input token from index
		inputToken = currToken(index);
		
		// switch-case to determine what to match
		switch(inputToken){
			case "id":
			case "read":
			case "write":
				stmt();
				stmt_list();
				break;
			case "$$":
				break;
			default:
				parse_error("stmt_list");
				break;
		}
	}
		
	// statement method
	public static void stmt(){
		// get input token from index
		inputToken = currToken(index);
		
		// switch-case to determine what to match
		switch(inputToken){
			case "id":
				match("id");
				match(":=");
				expr();
				break;
			case "read":
				match("read");
				match("id");
				break;
			case "write":
				match("write");
				expr();
				break;
			default:
				parse_error("stmt");
				break;
		}
	}
		
	// expression method
	public static void expr(){

		// get input token from index
		inputToken = currToken(index);
		
		// switch-case to determine what to match
		switch(inputToken){
			case "id":
			case "number":
			case "(":
				term();
				term_tail();
				break;
			default:
				parse_error("expr");
				break;
		}
	}
		
	// term tail method
	public static void term_tail(){
		// get input token from index
		inputToken = currToken(index);
		
		// switch-case to determine what to match
		switch(inputToken){
			case "+":
			case "-":
				add_op();
				term();
				term_tail();
				break;
			case ")":
			case "id":
			case "read":
			case "write":
			case "$$":
				break;
			default:
				parse_error("term_tail");
				break;
		}
	}
	
	// add operation method
	public static void add_op(){
		// get input token from index
		inputToken = currToken(index);
		
		// switch-case to determine what to match
		switch(inputToken){
			case "+":
				match("+");
				break;
			case "-":
				match("-");
				break;
			default:
				parse_error("add_op");
		}
	}
		
	// term method
	public static void term(){

		// get input token from index
		inputToken = currToken(index);
		
		// switch-case to determine what to match
		switch(inputToken){
			case "id":
			case "number":
			case "(":
				factor();
				factor_tail();
				break;
			default:
				parse_error("term");
				break;
		}
	}
		
	// factor tail method
	public static void factor_tail(){


		// get input token from index
		inputToken = currToken(index);
		
		// switch-case to determine what to match
		switch(inputToken){
			case "*":
			case "/":
				mult_op();
				factor();
				factor_tail();
				break;
			case "+":
			case "-":
			case ")":
			case "id":
			case "read":
			case "write":
			case "$$":
				break;
			default:
				parse_error("factor_tail");
				return;
				//break;
		}
	}
		
	// multiplication operation
	public static void mult_op(){

		// get input token from index
		inputToken = currToken(index);
		
		// switch-case to determine what to match
		switch(inputToken){
			case "*":
				match("*");
				break;
			case "/":
				match("/");
				break;
			default:
				parse_error("mult_op");
				break;
		}
	}
		
	// factor method
	public static void factor(){

		// get input token from index
		inputToken = currToken(index);
		
		// switch-case to determine what to match
		switch(inputToken){
			case "id":
				match("id");
				break;
			case "number":
				match("number");
				break;
			case "(":
				match("(");
				expr();
				match(")");
				break;
			default:
				parse_error("factor");
				break;
		}
	}

	// currToken method - returns inputToken and has argument of index
	private static String currToken(int index) {
		try{
			// get input token from index
			inputToken = tokens.get(index);
		}
		catch(Exception e){
			parse_error("currToken");
		}
		// return inputToken
		return inputToken;
	}
		
	// getTokens method - takes argument of program name and returns ArrayList of tokens
	public static ArrayList<String> getTokens(String programName){

		// Create ArrayList variables for tokens and lines
		tempTokens = new ArrayList<String>();
		lines = new ArrayList<String>();
			
		// try-catch
		try {
			// Create BufferedReader object based off program name
			reader = new BufferedReader(new FileReader(new File(programName)));
			// get line by line from file
			while ((line = reader.readLine()) != null) {
				// add each line to ArrayList
				lines.add(line);
			}
			// close bufferedreader
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		// get number of lines
		lineSize = lines.size();
			
		// loop through each line
		for(int i = 0; i<lineSize; i++){
			// get current line
			currLine = lines.get(i);
			// split current line and add to array 
			toks = currLine.split(" ");
			
			// get token length
			toklen = toks.length;
			
			// loop through tokens in array
			for(int r = 0; r < toklen; r++){
				// get token from array
				tokenInstance = toks[r];
				
				// if the token is in the keywords array, simply add token to ArrayList
				if(contains(keywords,tokenInstance)){
					tempTokens.add(tokenInstance);
				}
				// if the token is in the ids array, add "id" to ArrayList
				else if(contains(ids,tokenInstance)){
					tempTokens.add("id");
				}
				// if the token is in the numbers array, add "number" to ArrayList
				else if(contains(numbers,tokenInstance)){
					tempTokens.add("number");
				}
				// otherwise, invalid token
				else{
					tokenError(tokenInstance);
				}
				
			}
		}
		// return ArrayList
		return tempTokens;
	}
		
	// contains method - arguments are array and token to find, returns true or false
	private static boolean contains(String[] list, String findToken) {
		// get list length
		listlen = list.length;
		// loop through list
		for(int i = 0; i<listlen; i++){
			// check if string argument is in the list array
			if(findToken.equalsIgnoreCase(list[i])){
				// if so, return true
				return true;
			}
		}
		// if not, return false
		return false;
	}	
	
	// tokenError - argument is invalid token
	//if the token is not a keyword, id, or number, change valid to false
	private static void tokenError(String myTok) {
		// Display that token is invalid
		System.out.println(myTok + " is not a valid token");
		// change valid to false
		valid = false;
	}
	
	// parse_error 
	// if a parse error arises, change valid to false
	private static void parse_error(String method) {
		System.out.println("Parse error in method: " + method);
		valid = false;
	}
}
