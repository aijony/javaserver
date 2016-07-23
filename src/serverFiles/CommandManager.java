package serverFiles;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import manager.CookieManager;
import manager.LogManager;
import clientFiles.CookieClient;

public class CommandManager extends Thread{
	
	//Commands are volatile because they will changing in a multithreaded environment
	//TODO Test if they are actually needed

	volatile String buffer;
	
	private  final String uh = "uh still working on that";
	private  String helpString = "";
	
	
	volatile private  Cookie currentCookie;
		
	
	volatile private  int cookieNumber;
	volatile private  int logged;
	volatile private  int accID;
	
	volatile String commandInput;
	
	volatile CountDownLatch outputReadyLatch;
	volatile CountDownLatch inputReadyLatch;
	
	/*
	 * Creates a command manager with a shared output ready latch with whatever thread
	 * is creating it. It makes a guest account and creates and sets the latches.
	*/
	public CommandManager(CountDownLatch outLatch){
		AccountManager.getAccountList().add(new Account("guest"));
		AccountManager.makePassword(0, "DoNotGuessThisPassword");
		inputReadyLatch = new CountDownLatch(1);
		outputReadyLatch = outLatch;
	}
	
	/*
	 * Begins running the command manager
	 * It will constantly await an input change and then run the check Command.
	*/
	public void run(){
		while(true){
			try {
				
				checkCommand(waitInput());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	/*
	 * Checks the command and will run through what is neeeded depending on the thread
	 * TODO make each command it's own method.
	*/
	public void checkCommand(String input) throws InterruptedException{

		
		//Gets the cookie input
		String command = extractCookie(input).toLowerCase();

		
		//Help Command
		if(command.equals(SF.iC[0])){
			
			for(String c : SF.iC){
				helpString += c + " ";
			}
			
			setBuffer(helpString);}
		
		//Shutdown Client
		else if(command.equals(SF.iC[1])){
			setBuffer(SF.iC[1]);}
		
		//Login
		else if(command.equals(SF.iC[2])){
			setBuffer("Please input username");
			//Waits for password input
			
			int index = AccountManager.checkAccount(waitInput());
			//Checks if account exists
			if(index == -1){
				setBuffer("Account does not exist");
			}
			else{
				
				
				accID = index;
				refreshCookie(new Cookie(cookieNumber, accID, 0));
				//Checks password
				setBuffer("Account found please enter password");
				
				if(AccountManager.checkPassword(waitInput(), accID)){
					refreshCookie(new Cookie(cookieNumber, accID, 1));
					//Welcomes the new user
					setBuffer("Welcome " + AccountManager.getAccountList().get(accID).getUsername());
				}
				else{
					refreshCookie(new Cookie(cookieNumber, 0, 0));
					setBuffer("invalid username or password");
					
				}
			}
		}
		
		//Make account
		else if(command.equals(SF.iC[3])){
			setBuffer("Please input new account name");
			command = waitInput();
			if(AccountManager.checkAccount(command) != -1){
				setBuffer("Username in use please try again");
			}
			else{
			refreshCookie(new Cookie(cookieNumber, AccountManager.makeAccount(command), 0));	
			setBuffer("Okay, please input password");

			if(AccountManager.makePassword(accID, waitInput())){
				refreshCookie(new Cookie(cookieNumber, 0, 0));	
				setBuffer("Password set thank you");
			}
			else{
				setBuffer("Password NOT set");}
		}
		}
		
		//Logout
		else if(command.equals(SF.iC[4])){
			if(logged == 0){
				setBuffer("Not logged therefor cannot log out");
			}
			else if(logged == 1){
				refreshCookie(new Cookie(cookieNumber, 0, 0));
				setBuffer("Successfully logged out");
			}
		}
			
		//Get account info
		else if(command.equals(SF.iC[5])){
			if(logged == 0){
				setBuffer("Please login to access account info");
			}
			else if(logged == 1){
				setBuffer(AccountManager.getAccountList().get(accID).getAccountInfo());
			}
		}
		
		else if(command.equals(SF.iC[6]))
		{
			if(AccountManager.checkAdminRights(currentCookie))
				{
					System.exit(0);
				}
		}
		else{
			setBuffer("Type help for a list of valid commands");
		}
		
		
		
	}
	
	/*
	 * Refreshes the cookie and the fields (the fields are for easy access)
	 * This should be done whenever a cookie needs to be updated and should be
	 * called before the buffer is set so the client receives the updated cookie.
	*/
	public void refreshCookie(Cookie tempCookie){
		currentCookie = tempCookie;
		logged = currentCookie.getLoggedIn();
		cookieNumber = currentCookie.getCookieNumber();
		accID = currentCookie.getAccountID();
	}

	/*
	 * Extracts the cookie from the input and will also
	 * refresh the cookie. This will return the input
	 * command without the cookie.
	*/
	public String extractCookie(String input){
		String cookieInput;
		if(!CookieManager.checkCookie(input)){
    		cookieInput = "";
    		return input;
    	}
		else{
		
		cookieInput = CookieManager.findCookie(input);
		refreshCookie(new Cookie(cookieInput));
		
		return CookieManager.removeCookie(input);	
		
	}
	
	}
	  
	/*
	 * Sets the buffer (output and cookie). It will also count down the outputReadyLatch
	 * meaning whatever has the command manager should receive the message that the output
	 * is ready to be sent.
	*/
	private void setBuffer(String toBuffer) {
			buffer = toBuffer + currentCookie;
			LogManager.log(buffer);
			outputReadyLatch.countDown();
			
			
	}
		

	/*
	 * Waits for an input to occur. When it does it will return the input (while extracting the cookie)
	 * Continuing this thread wherever it left off.
	*/
	public String waitInput() throws InterruptedException{
		
		inputReadyLatch.await();
		inputReadyLatch = new CountDownLatch(1);
		return (extractCookie(commandInput));
	}

	/*
	 * Sets the input (this should be called from the object/class that has this one). 
	 * It will count down the latch notifying this thread that that it can continue where 
	 * it last left off when it called the wait input.
	*/
	public void setInput(String input){
		commandInput = input;
		inputReadyLatch.countDown();
	}
	

	



	

	
	
	
}
