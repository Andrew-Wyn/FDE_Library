package fde;

import java.math.BigInteger;

class BinToHex {
    
    static int cont = 0;
   
    static String toHex(String bitStream){
        int byteLength = 4;
        int bitStartPos = 0, bitPos = 0;
        String hexString = "";
        int sum = 0;

        // pad '0' to make input bit stream multiple of 4 

        if(bitStream.length()%4 !=0){
            int tempCnt = 0;
            int tempBit = bitStream.length() % 4;           
            while(tempCnt < (byteLength - tempBit)){
                bitStream = "0" + bitStream;
                tempCnt++;
            }
        }

        // Group 4 bits, and find Hex equivalent 

        while(bitStartPos < bitStream.length()){
            while(bitPos < byteLength){
                sum = (int) (sum + Integer.parseInt("" + bitStream.charAt(bitStream.length()- bitStartPos -1)) * Math.pow(2, bitPos));
                bitPos++;
                bitStartPos++;
            }
            if(sum < 10)
                 hexString = Integer.toString(sum) + hexString;
            else 
                 hexString = (char) (sum + 55) + hexString;

            bitPos = 0;
            sum = 0;
        }
 
        return hexString;
    }
    
    static String toBin(String hex){
        StringBuilder binStrBuilder = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {

            String output = hex.substring(i, (i + 2));

            int decimal = Integer.parseInt(output, 16);

            String binStr = Integer.toBinaryString(decimal);
            int len = binStr.length();
            StringBuilder sbf = new StringBuilder();
            if (len < 8) {

                    for (int k = 0; k < (8 - len); k++) {
                            sbf.append("0");
                    }
                    sbf.append(binStr);
            } else {
                    sbf.append(binStr);
            }

            binStrBuilder.append(sbf.toString());
        }

        return binStrBuilder.toString();
    }
    
}
