package serverFiles;

import manager.CookieManager;
import manager.LogManager;

public class Cookie{
	
	private int cookieNumber;
	private int accountIdentifier;
	private int loggedIn = 0;
	
	
	
	public Cookie(){
		accountIdentifier = 0;		
	}
	public Cookie(int clientNum, int accountIdentifierNum, int logged){
		cookieNumber = clientNum;
		accountIdentifier = accountIdentifierNum;
		loggedIn = logged;
	}
	public Cookie(String cookieToString){
		cookieToString = unEncrypt(cookieToString);
		cookieNumber = getInt(SF.cookieNumberID, cookieToString);
		accountIdentifier = getInt(SF.accountIdentifierID, cookieToString);
		loggedIn = getInt(SF.loggedInID, cookieToString);
	}
	
	private int getInt(String identifier, String input){
		String output;
		int index = input.indexOf(identifier);
		if(index == -1){
			LogManager.log(identifier + "not found in " + input);
			return -1;
		}
		else{
		index += identifier.length();
		output = input.substring(index, index + 1);
		return Integer.parseInt(output);
		}
	}

	private String encrypt(String cookieToString){
		return cookieToString;
	}
	private String unEncrypt(String cookieToString){
		return cookieToString;
	}
	
	public int getCookieNumber(){
		return cookieNumber;
	}
	public int getAccountID(){
		return accountIdentifier;
	}
	public int getLoggedIn(){
		return loggedIn;
	}
	
	public String toString(){
		String output = SF.cookieNumberID + cookieNumber + SF.accountIdentifierID + accountIdentifier + SF.loggedInID + loggedIn;
		return CookieManager.getCookieTag() + encrypt(output);
	}
}
	


