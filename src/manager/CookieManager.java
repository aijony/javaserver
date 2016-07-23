package manager;

import clientFiles.CookieClient;

public class CookieManager {
	
    private static String cookieTag = "   1847282-$cookie$-38582747: ";
    
    
    /*
     * Gets the cookie tag from CookieManager's fields
    */
   
    /*
     * Checks to see if a cookie is set if not sets the initial cookie
     * If there is a change in the cookie than this method will 
     * set the cookie to the new value.
     * 
     * The cookie will change if the login changes
    */

    
    /*
     * Finds the cookie in the string and returns it,
     * this does not include the tag
     * If there is no cookie it will return the input.
    */
	public static String findCookie(String input) {
		if(checkCookie(input))
		return input.substring(input.indexOf(getCookieTag()) + getCookieTag().length());
		return input;
	}
	
	/*
	 * Will remove the cookie from a string and
	 * will return the altered string without a cookie	
	*/
	public static String removeCookie(String input){
		if(checkCookie(input))
		return input.substring(0, input.indexOf(getCookieTag()));
		return input;
	}
	
	/*
	 * Will check if a cookie exists by verifying the
	 * presence of a cookie tag
	*/
	public static boolean checkCookie(String input){
		int index = input.indexOf(getCookieTag());
    	if(index == -1){
    		
    		return false;
    	}
    	return true;
    	
	}

	/*
	 * Will get the global cookie tag.
	*/
	public static String getCookieTag() {
		return cookieTag;
	}

	public static void setCookieTag(String cookieTag) {
		CookieManager.cookieTag = cookieTag;
	}
	

}
