package com.ymy.Conttoller;

import com.ymy.Components.CodeComponents;

public class Encode {

    static int[][] Sbox={
            {9,4,10,11},
            {13,1,8,5},
            {6,2,0,3},
            {12,14,15,7}

    };


    public static String encode(String plaintext,String key){
        //密钥扩展
        String[] extensionkey = CodeComponents.Extension(key);
        String key01=extensionkey[0]+extensionkey[1];
        String key23=extensionkey[2]+extensionkey[3];
        String key45=extensionkey[4]+extensionkey[5];


        // 轮密钥AK 半字节NS 行位移SR 列混淆MC
        String or = CodeComponents.Xor(plaintext, key45);
        String shiftleft = CodeComponents.ShitftRow(or);
        String s = CodeComponents.HalfByteSub(shiftleft, Sbox);
        String xor = CodeComponents.Xor(s, key23);
        String s1 = CodeComponents.MixColumn(xor);
        String shiftleft1 = CodeComponents.ShitftRow(s1);
        String s2 = CodeComponents.HalfByteSub(shiftleft1, Sbox);
        String res = CodeComponents.Xor(s2, key01);

        return res;
    }


    public static String Dencode(String cipher,String key){
        String key1 = key.substring(0, 16);
        String key2 = key.substring( 16);
        String middecode = encode(cipher, key1);
        return encode(middecode,key2);
    }


    public static String Tencode(String cipher,String key){
        String key1 = key.substring(0, 16);
        String key2 = key.substring( 16,32);
        String key3 = key.substring( 32);
        String middecode01 = encode(cipher, key1);
        String middecode02 = encode(middecode01, key1);
        return encode(middecode02,key2);
    }

    public static String Ascencode(String plaintext,String key){

        String[] chartobin=new String[(plaintext.length()+1)/2];
        String res="";
        String temp="";
        //2个字符为一组 进行decode
        for(int i=0;i<plaintext.length();i++){
            temp+= Binarys.charToBinary(plaintext.charAt(i), 8);
            if(temp.length()==16){
                chartobin[i/2]=temp;
                temp="";
            }
        }

        for (String s : chartobin) {
            if(s!=null){ res+= Binarys.BinaryToChar(encode(s, key));
            }
        }
        if(temp.length()!=0){
            temp+="00000000";
            res+=Binarys.BinaryToChar(encode(temp,key));
        }

        return res;
    }

    public static String AscDencode(String cipher,String key){
        String key1 = key.substring(0, 16);
        String key2 = key.substring( 16);
        String ascdecode = Ascencode(cipher, key1);
        return Ascencode(ascdecode,key2);
    }


    public static String AscTencode(String cipher,String key){
        String key1 = key.substring(0, 16);
        String key2 = key.substring( 16,32);
        String key3 = key.substring( 32);
        String middecode01 =Ascencode(cipher, key1);
        String middecode02 =Ascencode(middecode01, key1);
        return Ascencode(middecode02,key2);
    }




}
