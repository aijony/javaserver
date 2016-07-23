package clientFiles;
import manager.CookieManager;

public class CookieClient extends CookieManager {
	private static String cookie = "";
	public static String getCookie(){
    	return cookie;	
    }

    
    /*
     * Checks to see if a cookie is set if not sets the initial cookie
     * If there is a change in the cookie than this method will 
     * set the cookie to the new value.
     * 
     * The cookie will change if the login changes
    */
	public static void setCookie(String input){
		if(cookie.equals(findCookie(input)) == false ){
			cookie = getCookieTag() + findCookie(input);
		}
		
		
	}
}
