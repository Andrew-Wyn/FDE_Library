package fde;

import java.io.*; //gestione delle eccezioni di codifica in ascii 'UnsupportedEncodingException'

public class FDE {
    
    public static String encryption(String to_code, String key) throws UnsupportedEncodingException{
        
        String binary = delOff(BinToHex.toBin(HexToAscii.toHex(to_code)));
            
        String binaryk = delOff(BinToHex.toBin(HexToAscii.toHex(key)));

        //matrici di combinazioni lineari
        //codifica
        char [][] linear_appl = new char[4][4];
        linear_appl[0][0] = '1';
        linear_appl[0][1] = '0';
        linear_appl[0][2] = '1';
        linear_appl[0][3] = '1';
        linear_appl[1][0] = '0';
        linear_appl[1][1] = '1';
        linear_appl[1][2] = '1';
        linear_appl[1][3] = '0';
        linear_appl[2][0] = '1';
        linear_appl[2][1] = '1';
        linear_appl[2][2] = '0';
        linear_appl[2][3] = '0';
        linear_appl[3][0] = '1';
        linear_appl[3][1] = '0';
        linear_appl[3][2] = '0';

        
        //creare i due array a 4 dim 
        char [][][][]fotoSammlung = new char[4][4][4][4];
        char [][][][]fotoSammlungk = new char[4][4][4][4];

        int bit_ins = 0;
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                for(int x=0; x<4; x++){
                    for(int y=0; y<4; y++){
                        fotoSammlung[i][j][x][y]=binary.charAt(bit_ins);
                        fotoSammlungk[i][j][x][y]=binaryk.charAt(bit_ins);
                        bit_ins++;
                    }
                }
            }
        }

        //azzero per sicurezza
        bit_ins=0;

        /*******************************/
        //livello a 4 dimensioni
            
        for(int iiiii=0; iiiii<4; iiiii++){
            char [][][] temp = new char[4][4][4];
            char [][][] tempappo = new char[4][4][4];
            for(int ii=0; ii<iiiii; ii++){
                for(int i=0; i<4; i++){
                    for(int q=0; q<4; q++){
                        for(int z=0; z<4; z++){
                            for(int p=0; p<4; p++){
                                if(i==0){
                                    tempappo[q][z][p]=fotoSammlung[0][q][z][p];
                                }
                                if(i<3){
                                    temp[q][z][p]=fotoSammlung[i+1][q][z][p];
                                    fotoSammlung[i][q][z][p] = temp[q][z][p];
                                }else{
                                    fotoSammlung[i][q][z][p] = tempappo[q][z][p];
                                }
                            }
                        }
                    }  
                }
            }
            for(int i=0; i<4; i++){
                char [][] temp1 = new char[4][4];
                char [][] tempappo1 = new char[4][4];
                for(int ii=0; ii<i; ii++){
                    for(int q=0; q<4; q++){
                        for(int z=0; z<4; z++){
                            for(int p=0; p<4; p++){
                                if(q==0){
                                    tempappo1[z][p]=fotoSammlung[i][0][z][p];
                                }
                                if(q<3){
                                    temp1[z][p]=fotoSammlung[i][q+1][z][p];
                                    fotoSammlung[i][q][z][p] = temp1[z][p];
                                }else{
                                    fotoSammlung[i][q][z][p] = tempappo1[z][p];
                                }
                            }
                        }
                    }  
                }
                //livello a 3 dimensioni
                for(int j=0; j<4; j++){
                    char [] temp2 = new char[4];
                    char [] tempappo2 = new char[4];
                    for(int ii=0; ii<j; ii++){
                        for(int z=0; z<4; z++){
                            for(int p=0; p<4; p++){
                                if(z==0){
                                    tempappo2[p]=fotoSammlung[i][j][0][p];
                                }
                                if(z<3){
                                    temp2[p]=fotoSammlung[i][j][z+1][p];
                                    fotoSammlung[i][j][z][p] = temp2[p];
                                }else{
                                    fotoSammlung[i][j][z][p] = tempappo2[p];
                                }
                            }
                        }
                    }
                    //livello a 2 dimensioni
                    for(int x=0; x<4; x++){
                        char temp3 = 'a';
                        char tempappo3 = 'a';
                        for(int ii=0; ii<x; ii++){
                            for(int p=0; p<4; p++){
                                if(p==0){
                                    tempappo3=fotoSammlung[i][j][x][0];
                                }
                                if(p<3){
                                    temp3=fotoSammlung[i][j][x][p+1];
                                    fotoSammlung[i][j][x][p] = temp3;
                                }else{
                                    fotoSammlung[i][j][x][p] = tempappo3;
                                }
                            }
                        }
                        //livello a 1 dimensione
                        for(int y=0; y<4; y++){
                            //sommo il bit di posizione i,j,x,y con il bit di posizione i,j,x,y del blocco chiave
                            //somma xor
                            char un = fotoSammlung[i][j][x][y];
                                char du = fotoSammlungk[i][j][x][y];
                                if (un==du){
                                    fotoSammlung[i][j][x][y]='0';
                                } else {
                                    fotoSammlung[i][j][x][y]='1';
                                }
                            
                            if(y<3){
                                //sommo il bit di posizione i,j,x,y con il bit di posizione i,j,x,y+1
                                //somma xor
                                char un2 = fotoSammlung[i][j][x][y];
                                char du2 = fotoSammlung[i][j][x][y+1];
                                if (un2==du2){
                                    fotoSammlung[i][j][x][y]='0';
                                } else {
                                    fotoSammlung[i][j][x][y]='1';
                                }
                            }
                        }
                        //get vector to mix from fotosammlung
                        char[] toMix = new char[4];
                        for(int y=0; y<4; y++){
                            toMix[y] = fotoSammlung[i][j][x][y];
                        }
                        toMix = mixRow(toMix, linear_appl); //like mixcolumns
                        for(int y=0; y<4; y++){
                            fotoSammlung[i][j][x][y] = toMix[y];
                        }
                        if(x<3){
                            //sommo il cubo di posizione i con il cubo di posizione i+1
                            for(int y=0; y<4; y++){
                                //somma xor
                                char un = fotoSammlung[i][j][x][y];
                                char du = fotoSammlung[i][j][x+1][y];
                                if (un==du){
                                    fotoSammlung[i][j][x][y]='0';
                                } else {
                                    fotoSammlung[i][j][x][y]='1';
                                }
                            }
                        }
                    }
                    if(j<3){
                        //sommo il cubo di posizione i con il cubo di posizione i+1
                        for(int x=0; x<4; x++){
                            for(int y=0; y<4; y++){
                                //somma xor
                                char un = fotoSammlung[i][j][x][y];
                                char du = fotoSammlung[i][j+1][x][y];
                                if (un==du){
                                    fotoSammlung[i][j][x][y]='0';
                                } else {
                                    fotoSammlung[i][j][x][y]='1';
                                }
                            }
                        }
                    }
                }
                if(i<3){
                    //sommo il cubo di posizione i con il cubo di posizione i+1
                    for(int j=0; j<4; j++){
                        for(int x=0; x<4; x++){
                            for(int y=0; y<4; y++){
                                //somma xor
                                char un = fotoSammlung[i][j][x][y];
                                char du = fotoSammlung[i+1][j][x][y];
                                if (un==du){
                                    fotoSammlung[i][j][x][y]='0';
                                } else {
                                    fotoSammlung[i][j][x][y]='1';
                                }
                            }
                        }
                    }                    
                }
            }
        }
            
        //StringBuilder binaryc = new StringBuilder();
        String binaryc = "";

        //metto il vettore a quattro dimensioni in una stringa binaria
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                for(int x=0; x<4; x++){
                    for(int y=0; y<4; y++){
                        binaryc+=fotoSammlung[i][j][x][y];
                    }
                }
            }
        }
                
        to_code = HexToAscii.toAscii(BinToHex.toHex(binaryc)); 
       
        return to_code;
    }
    
    public static String decryption(String to_decode, String key) throws UnsupportedEncodingException {
                 
            String binaryc = delOff(BinToHex.toBin(HexToAscii.toHex(to_decode))); //errore di parse !!!! MANNAGGIA ALLA TROIA !!!!
                     
            String binaryk = delOff(BinToHex.toBin(HexToAscii.toHex(key)));
            
            /*--prova modifica di un singolo bit nella chiave--*/
            /*String bb = "";
            
            for(int i=0; i< binaryk.length(); i++){
                if(i!=255){
                    bb += binaryk.charAt(i);
                } else {
                    bb += '1';
                }
            }
            
            binaryk = bb;*/
                        
            /*--prova modifica di un singolo bit nel blocco da cifrare--*/
            /*String cc = "";
            
            for(int i=0; i< binaryc.length(); i++){
                if(i!=2){
                    cc += binaryc.charAt(i);
                } else {
                    cc += '0';
                }
            }
            
            binaryc = cc;*/

            //matrici di combinazioni lineari
            //decodifica
            char [][] r_linear_appl = new char[4][4];
            r_linear_appl[0][0] = '1';
            r_linear_appl[0][1] = '1';
            r_linear_appl[0][2] = '1';
            r_linear_appl[0][3] = '1';
            r_linear_appl[1][0] = '1';
            r_linear_appl[1][1] = '1';
            r_linear_appl[1][2] = '0';
            r_linear_appl[1][3] = '1';
            r_linear_appl[2][0] = '1';
            r_linear_appl[2][1] = '0';
            r_linear_appl[2][2] = '0';
            r_linear_appl[2][3] = '1';
            r_linear_appl[3][0] = '1';
            r_linear_appl[3][1] = '1';
            r_linear_appl[3][2] = '1';
            r_linear_appl[3][3] = '0';



            //creare i due array a 4 dim 
            char [][][][]fotoSammlung = new char[4][4][4][4];
            char [][][][]fotoSammlungk = new char[4][4][4][4];

            int bit_ins = 0;
            for(int i=0; i<4; i++){
                for(int j=0; j<4; j++){
                    for(int x=0; x<4; x++){
                        for(int y=0; y<4; y++){
                            fotoSammlung[i][j][x][y]=binaryc.charAt(bit_ins);
                            fotoSammlungk[i][j][x][y]=binaryk.charAt(bit_ins);
                            bit_ins++;
                        }
                    }
                }
            }

            //azzero per sicurezza
            bit_ins=0;

            /*******************************/
            
            //DECODIFICA
            for(int iiiii=0; iiiii<4; iiiii++){
                //livello a 4 dimensioni
                for(int i=3; i>=0; i--){
                    if(i<3){
                        //sommo il cubo di posizione i con il cubo di posizione i+1
                        for(int j=3; j>=0; j--){
                            for(int x=3; x>=0; x--){
                                for(int y=3; y>=0; y--){
                                    //somma xor
                                    char un = fotoSammlung[i][j][x][y];
                                    char du = fotoSammlung[i+1][j][x][y];
                                    if (un==du){
                                        fotoSammlung[i][j][x][y]='0';
                                    } else {
                                        fotoSammlung[i][j][x][y]='1';
                                    }
                                }
                            }
                        }                    
                    }
                    //livello a 3 dimensioni
                    for(int j=3; j>=0; j--){
                        if(j<3){
                            //sommo il cubo di posizione i con il cubo di posizione i+1
                            for(int x=3; x>=0; x--){
                                for(int y=3; y>=0; y--){
                                    //somma xor
                                    char un = fotoSammlung[i][j][x][y];
                                    char du = fotoSammlung[i][j+1][x][y];
                                    if (un==du){
                                        fotoSammlung[i][j][x][y]='0';
                                    } else {
                                        fotoSammlung[i][j][x][y]='1';
                                    }
                                }
                            }
                        }
                        //livello a 2 dimensioni
                        for(int x=3; x>=0; x--){
                            if(x<3){
                                //sommo il cubo di posizione i con il cubo di posizione i+1
                                for(int y=3; y>=0; y--){
                                    //somma xor
                                    char un = fotoSammlung[i][j][x][y];
                                    char du = fotoSammlung[i][j][x+1][y];
                                    if (un==du){
                                        fotoSammlung[i][j][x][y]='0';
                                    } else {
                                        fotoSammlung[i][j][x][y]='1';
                                    }
                                }
                            }
                            //get vector to mix from fotosammlung
                            char[] toMix = new char[4];
                            for(int y=0; y<4; y++){
                                toMix[y] = fotoSammlung[i][j][x][y];
                            }
                            toMix = mixRow(toMix, r_linear_appl);
                            for(int y=0; y<4; y++){
                                fotoSammlung[i][j][x][y] = toMix[y];
                            }
                            //livello a 1 dimensioni
                            for(int y=3; y>=0; y--){    

                                if(y<3){
                                    //sommo il cubo di posizione i con il cubo di posizione i+1
                                    //somma xor
                                    char un2 = fotoSammlung[i][j][x][y];
                                    char du2 = fotoSammlung[i][j][x][y+1];
                                    if (un2==du2){
                                        fotoSammlung[i][j][x][y]='0';
                                    } else {
                                        fotoSammlung[i][j][x][y]='1';
                                    }
                                }

                                char un = fotoSammlung[i][j][x][y];
                                char du = fotoSammlungk[i][j][x][y];
                                if (un==du){
                                    fotoSammlung[i][j][x][y]='0';
                                } else {
                                    fotoSammlung[i][j][x][y]='1';
                                }

                            }

                            char temp3 = 'a';
                            char tempappo3 = 'a';
                            for(int ii=0; ii<4-x; ii++){
                                for(int p=0; p<4; p++){
                                    if(p==0){
                                        tempappo3=fotoSammlung[i][j][x][0];
                                    }
                                    if(p<3){
                                        temp3=fotoSammlung[i][j][x][p+1];
                                        fotoSammlung[i][j][x][p] = temp3;
                                    }else{
                                        fotoSammlung[i][j][x][p] = tempappo3;
                                    }
                                }
                            }
                        }
                        char [] temp2 = new char[4];
                        char [] tempappo2 = new char[4];
                        for(int ii=0; ii<4-j; ii++){
                            for(int z=0; z<4; z++){
                                for(int p=0; p<4; p++){
                                    if(z==0){
                                        tempappo2[p]=fotoSammlung[i][j][0][p];
                                    }
                                    if(z<3){
                                        temp2[p]=fotoSammlung[i][j][z+1][p];
                                        fotoSammlung[i][j][z][p] = temp2[p];
                                    }else{
                                        fotoSammlung[i][j][z][p] = tempappo2[p];
                                    }
                                }
                            }
                        }
                    }
                    char [][] temp1 = new char[4][4];
                    char [][] tempappo1 = new char[4][4];
                    for(int ii=0; ii<4-i; ii++){
                        for(int q=0; q<4; q++){
                            for(int z=0; z<4; z++){
                                for(int p=0; p<4; p++){
                                    if(q==0){
                                        tempappo1[z][p]=fotoSammlung[i][0][z][p];
                                    }
                                    if(q<3){
                                        temp1[z][p]=fotoSammlung[i][q+1][z][p];
                                        fotoSammlung[i][q][z][p] = temp1[z][p];
                                    }else{
                                        fotoSammlung[i][q][z][p] = tempappo1[z][p];
                                    }
                                }
                            }
                        }  
                    }
                }
                char [][][] temp = new char[4][4][4];
                char [][][] tempappo = new char[4][4][4];
                for(int ii=0; ii<iiiii+1; ii++){
                    for(int i=0; i<4; i++){
                        for(int q=0; q<4; q++){
                            for(int z=0; z<4; z++){
                                for(int p=0; p<4; p++){
                                    if(i==0){
                                        tempappo[q][z][p]=fotoSammlung[0][q][z][p];
                                    }
                                    if(i<3){
                                        temp[q][z][p]=fotoSammlung[i+1][q][z][p];
                                        fotoSammlung[i][q][z][p] = temp[q][z][p];
                                    }else{
                                        fotoSammlung[i][q][z][p] = tempappo[q][z][p];
                                    }
                                }
                            }
                        }  
                    }
                }
            }

            StringBuilder binaryc2 = new StringBuilder();
            
            //metto il vettore a quattro dimensioni in una stringa binaria
            for(int i=0; i<4; i++){
                for(int j=0; j<4; j++){
                    for(int x=0; x<4; x++){
                        for(int y=0; y<4; y++){
                            binaryc2.append(fotoSammlung[i][j][x][y]);
                        }
                    }
                }
            }
            
            to_decode = HexToAscii.toAscii(BinToHex.toHex(binaryc2.toString()));
            return to_decode;
    }
    
    private static char[] mixRow(char[] vector, char[][] matrix){
        
        char[] out = new char[4];
        char sum = '0';
        char molt = '0';
        
        //moltiplication beetwen matrix and a vector
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                if(matrix[i][j]=='0' || vector[j]=='0'){
                    molt = '0';
                    if(true){
                        if (sum==molt){
                            sum = '0';
                        } else {
                            sum = '1';
                        }
                    }
                } else {
                    molt = '1';
                    if(true){
                        if (sum==molt){
                            sum = '0';
                        } else {
                            sum = '1';
                        }
                    }
                }
            }
            out[i] = sum;
            sum = '0';
            molt = '0';
        }        
        return out;
    }
    
    private static String delOff(String binaryO){
        if(binaryO.length() < 256){
            for(;;){
                if(binaryO.length() % 256 > 0 || binaryO.length() == 0){
                    binaryO += '0';
                } else {
                    break;
                }
            }
        }    
        return binaryO;
    }
}
