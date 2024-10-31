package com.ymy.Conttoller;

import com.ymy.Components.CodeComponents;

//解密
public class Decode {

   static int[] imcBox2 = {0,2,4,6,8,10,12,14,3,1,7,5,11,9,15,13};
   static int[] imcBox9 = {0,9,1,8,2,11,3,10,4,13,5,12,6,15,7,14};

   static int[][] Sboxr={
            {10,5,9,11},
            {1,7,8,15},
            {6,0,2,3},
            {12,4,13,14}
    };
    public static String decode(String cipher,String key){


        //密钥扩展
        String[] extensionkey = CodeComponents.Extension(key);
        String key01=extensionkey[0]+extensionkey[1];
        String key23=extensionkey[2]+extensionkey[3];
        String key45=extensionkey[4]+extensionkey[5];


        // 轮密钥AK 半字节NS 行位移SR 列混淆MC
        String or=CodeComponents.Xor(cipher,key01);
        String s2 = CodeComponents.HalfByteSub(or, Sboxr);
        String shiftleft = CodeComponents.ShitftRow(s2);
        String s1 = CodeComponents.MixColumn_(shiftleft,imcBox9,imcBox2);
        String xor = CodeComponents.Xor(s1, key23);
        String s = CodeComponents.HalfByteSub(xor, Sboxr);
        String shiftleft1 = CodeComponents.ShitftRow(s);
        String res = CodeComponents.Xor(shiftleft1, key45);

         return res;
    }


    public static String Ddecode(String cipher,String key){
        String key1 = key.substring(0, 16);
        String key2 = key.substring( 16);
        String middecode = decode(cipher, key1);
        return decode(middecode,key2);
    }


    public static String Tdecode(String cipher,String key){
        String key1 = key.substring(0, 16);
        String key2 = key.substring( 16,32);
        String key3 = key.substring( 32);
        String middecode01 = decode(cipher, key1);
        String middecode02 = decode(middecode01, key1);
        return decode(middecode02,key2);
    }


    public static String Ascdecode(String cipher,String key){
        String[] chartobin=new String[(cipher.length()+1)/2];
        String res="";
        String temp="";
        //2个字符为一组 进行decode
        for(int i=0;i<cipher.length();i++){
            temp+= Binarys.charToBinary(cipher.charAt(i), 8);
            if(temp.length()==16){
                chartobin[i/2]=temp;
                temp="";
            }
        }

        for (String s : chartobin) {
            if(s!=null){ res+= Binarys.BinaryToChar(decode(s, key));
            }
        }
        if(temp.length()!=0){
            temp+="00000000";
            res+=Binarys.BinaryToChar(decode(temp,key));
        }

        return res;
    }

    public static String AscDdecode(String cipher,String key){
        String key1 = key.substring(0, 16);
        String key2 = key.substring( 16);
        String ascdecode = Ascdecode(cipher, key1);
        return Ascdecode(ascdecode,key2);
    }


    public static String AscTdecode(String cipher,String key){
        String key1 = key.substring(0, 16);
        String key2 = key.substring( 16,32);
        String key3 = key.substring( 32);
        String middecode01 =Ascdecode(cipher, key1);
        String middecode02 =Ascdecode(middecode01, key1);
        return Ascdecode(middecode02,key2);
    }




}
