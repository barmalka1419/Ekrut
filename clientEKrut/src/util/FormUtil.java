package util;

// TODO: Auto-generated Javadoc
/**
 * The Class FormUtil.
 * class with functions for checking forms input validity
 */
public class FormUtil {

    /**
     * Check contain only digit.
     *the functions checks if the input string contains only digits
     * @param ID the id
     * @return true, if successful
     */
    // check if user id contain only digits
    public static boolean CheckContainOnlyDigit(String ID){
    	
    
        char[] idChar = ID.toCharArray();
        
        for (int i = 0; i < ID.length(); ++i){//Check first half 
        	
            if ((idChar[i])> 57 || idChar[i] < 48){	//Checks ascii vals to see if valid ID
            	
                return false;          
            }
        }
		return true;
    
    }
    
   
    
    /**
     * Check contain only letters.
     *the functions checks if the input string contains only letters
     * @param num the num
     * @return true, if successful
     */
    // check if user id contain only digits
    public static boolean CheckContainOnlyLetters(String num){
    	
    
       num = num.toLowerCase();
        
        for (int i = 0; i < num.length(); ++i){//Check first half 
        	
            if (num.charAt(i) < 97  || num.charAt(i) > 122)	//Checks ascii vals to see if valid ID
                return false;          
        
        }
        
		return true;
    }
    
    
    /**
     * Checks if is blank.
     * checks if the input is blank
     * @param cs the cs
     * @return true, if is blank(there is a char that isn't a whitespace)
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * Check valid city name.
     * check if the city name entered in address delivry form is valid
     * @param city the city
     * @return true, if successful
     */
    public static boolean CheckValidCityName(String city){
    	
        
    	city = city.toLowerCase();
         
         for (int i = 0; i < city.length(); ++i){//Check first half 
        	 
        	 if(city.charAt(i)==' ') {
        		 i++;
        	 }
        	 else {
         	
             if (city.charAt(i) < 97  || city.charAt(i) > 122)	//Checks ascii vals to see if valid ID
                 return false;          
         
         }

     }
         return true;
    }
}
