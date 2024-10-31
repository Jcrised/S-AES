package com.ymy.Conttoller;

import java.util.ArrayList;
import java.util.List;
import com.ymy.Components.CodeComponents;

public class CBC {





    //加密   IV是16bit key是16bit
    public static List<String> CbcEncode(String plaintext,String IV,String key){
        List<String> list=new ArrayList<>();
        List<String> returnlist=new ArrayList<>();
        int round=(plaintext.length()+15)/16;

        //确保每个块都是16位
        for(int i=0;i<round;i++){
            if(plaintext.length()<16){
                plaintext=CodeComponents.addZero(plaintext,16);
            }
            list.add(plaintext.substring(0,16));
            plaintext=plaintext.substring(16);
        }
        String first = CodeComponents.Xor(list.get(0), IV);
        String c1 = Encode.encode(first, key);

        //索引值替换
        list.set(0,c1);
        returnlist.add(0,Binarys.BinaryToChn(c1));

        for(int i=1;i<round;i++){
            String xor = CodeComponents.Xor(list.get(i), list.get(i - 1));
           list.set(i,Encode.encode(xor,key)) ;
            returnlist.add(i,Binarys.BinaryToChn(Encode.encode(xor,key)));
        }
        return returnlist;

    }



        //解密
    public static List<String> CbcDecode(String cipher,String IV,String key){

        List<String> returnlist=new ArrayList<>();
        List<String> list= Binarys.ChnToBinary(cipher);
        int round=cipher.length()/16;

        String  first = Decode.decode(list.get(0), key);
        String p1 = CodeComponents.Xor(first, IV);
        //索引值替换
        returnlist.add(0,p1);

        for(int i=1;i<round;i++){

            String sencond = Decode.decode(list.get(i), key);
            returnlist.add(i, CodeComponents.Xor(sencond,list.get(i-1))); ;
        }
        return returnlist;

    }
}
