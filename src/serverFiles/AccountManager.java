package serverFiles;

import java.util.ArrayList;

import manager.LogManager;

public class AccountManager {
	
	private static ArrayList<Account> accountList = new ArrayList<Account>();
	
	/*
	* Checks admin rights of an account based off of the cookie's data
	*/
	public static boolean checkAdminRights(Cookie cookieInput){	
		return true;
	}
	
	/*
	* checks if an account exists
	*/
	public static int checkAccount(String username){
		int count = 0;
		for(Account acc : getAccountList()){
			if(acc.getUsername().equalsIgnoreCase(username))
				return count;
			count++;
		}
		return -1;
	}
	
	/*
	* Checks if password is valid
	* TODO Encryption
	*/
	public static boolean checkPassword(String password, int index){
	
		if(getAccountList().get(index).getPassword().equals(password))
			return true;
		else
			return false;
	}
	
	/*
	* Makes an account with a username
	* will add to the array list of accounts
	*/
	public static int makeAccount(String username){
		getAccountList().add(new Account(username));
		return getAccountList().size() - 1;
	}
	
	/*
	* Creates a password.
	*/
	public static boolean makePassword(int index, String password){
		if(getAccountList().size() < index){
			LogManager.log("Making password for non existant account");
			return false;
		}
		else if(getAccountList().get(index).hasPassword()){
			LogManager.log("Account already has password, no override will take place");
			return false;
		}
		else{
			getAccountList().get(index).setPassword(password);
			return true;
		}
	}

	
	public static ArrayList<Account> getAccountList() {
		return accountList;
	}

	
	
	
	}


