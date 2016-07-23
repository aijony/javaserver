package serverFiles;

public class Account {
	private String username;
	private String password;
	private String privateKey;
	private String accountInfo = "AccountInfo: ";
	
	
	/*
	 * Makes an account with a username
	 * And sets a blank password
	*/
	public Account(String usrName){
		setUsername(usrName);
		setPassword("");
	}


	/* 
	 * Gets the account's username
	*/
	public String getUsername() {
		return username;
	}


	/* 
	 * Sets the user name
	*/
	private void setUsername(String username) {
		this.username = username;
	}
	/*
	 * Gets the password of the username.
	 */
	public String getPassword() {
		return password;
	}
	
	/*
	 * Sets the password of the username
	*/
	public void setPassword(String input) {
		password = input;
	}

	/*
	 * Checks if a password exists.
	*/	
	public boolean hasPassword() {
		if(password.equals(""))
			return false;
		else
			return true;
	}

	/*Gets the accountIno
	 * TODO make the account info actually have important information
	*/
	public String getAccountInfo() {
		accountInfo = getUsername() + " is pretty cool";
		
		return accountInfo;
	}
	
}
