package serverFiles;

/*
 * Various String finals that are mostly needed to prevent typos
 * on the programmers end. Most String will be found here if they are used
 * more than once. Yes I am lazy...
*/
public class SF {
	
	//Waiting Management
	public final static String ckAcc = "checkAccount";
	public final static String ckPsw = "checkPassword";
	public final static String mkAcc = "makeAccount";
	public final static String mkPsw = "makePassword";
	public final static String n = "none";
	
	//CookieTags
	public final static String cookieNumberID = "CookieNumber:";
	public final static String accountIdentifierID = "AccountIdentifier:";
	public final static String loggedInID = "loggedIn:";
	
	
	/*Input Commands
	 * 0:Help 1:shutdownclient 2:login 3: makeaccount
	 * 4:logout 5:accountinfo
	*/
	public final static String [] iC = {"help", "shutdownclient",
		"login", "makeaccount", "logout", "accountinfo", "shutdownserver"};
}
