/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fde;

import java.io.UnsupportedEncodingException;

public class Main {
    
    public static void main(String[] args) throws UnsupportedEncodingException{
        String b = FDE.encryption("ciao", "miao");
        String c = FDE.decryption(b, "miao");
        System.out.println(b + "\n" + c);
    }
    
}
