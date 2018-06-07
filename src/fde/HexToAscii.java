package fde;

import java.io.UnsupportedEncodingException;

class HexToAscii {
        
    static String toAscii(String hex){
  
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hex.length(); i+=2) {
            String str = hex.substring(i, i+2);
            output.append((char)Integer.parseInt(str, 16));
        }
        
        return output.toString();
    }
    
    static String toHex(String arg) throws UnsupportedEncodingException {
        /*ERRORE SUGLI SPECIAL CHARACTERS -- FIXED BY ME --*/
        char[] ch = arg.toCharArray();
            
        StringBuilder builder = new StringBuilder();

        for (char c : ch) {
           int i = (int) c;
           if(i < 16){
               builder.append("0"+Integer.toHexString(i).toUpperCase());
           } else {
               builder.append(Integer.toHexString(i).toUpperCase());               
           }           
        }
        
        return builder.toString();
    }
}
