package com.ymy.Components;


public class CodeComponents {


    static int[][] Sbox={
            {9,4,10,11},
            {13,1,8,5},
            {6,2,0,3},
            {12,14,15,7}

    };

    static int[][] Sboxr={
            {10,5,9,11},
            {1,7,8,15},
            {6,0,2,3},
            {12,4,13,14}
    };


    static String RCON1="10000000";


    static String RCON2="00110000";



    //补零

    public static String addZero(String original,int length){
        StringBuilder originalBuilder = new StringBuilder(original);
        while (originalBuilder.length() < length){
            originalBuilder.insert(0, "0");
        }
        original = originalBuilder.toString();
        return original;
    }



    /**
     * 密钥加
     * @param plaintext
     * @param key
     * @return
     */
    //二进制进行异或操作
    public static String Xor(String plaintext,String key){

        String result=new String();
        for(int i=0;i<key.length();i++){
            result+=plaintext.charAt(i)^key.charAt(i);
        }
        return  result ;

    }




    /**
     * 半字节代替
     * @param origin
     * @param sbox
     * @return
     */


    public static String HalfByteSub(String origin,int[][] sbox){
        String answer=new String();
        String S0 = origin.substring(0, 4);
        String S1 = origin.substring(4, 8);
        String S2 = origin.substring(8, 12);
        String S3 = origin.substring(12, 16);

        S0 = addZero(Integer.toBinaryString( sbox[Integer.parseUnsignedInt(S0.substring(0,2),2)][Integer.parseUnsignedInt(S0.substring(2,4),2)]),4);
        S1 = addZero(Integer.toBinaryString( sbox[Integer.parseUnsignedInt(S1.substring(0,2),2)][Integer.parseUnsignedInt(S1.substring(2,4),2)]),4);
        S2 = addZero(Integer.toBinaryString( sbox[Integer.parseUnsignedInt(S2.substring(0,2),2)][Integer.parseUnsignedInt(S2.substring(2,4),2)]),4);
        S3 = addZero(Integer.toBinaryString( sbox[Integer.parseUnsignedInt(S3.substring(0,2),2)][Integer.parseUnsignedInt(S3.substring(2,4),2)]),4);
        return S0 + S1 + S2 + S3;
    }




    /**
     * 行移位
     * @param origin
     * @return
     */



    //行移位
    public static String ShitftRow(String origin){
        return origin.substring(0,4) + origin.substring(12,16) + origin.substring(8,12) + origin.substring(4,8);

    }




    /**
     * 列混淆
     */

    static int[]  mcBox = {0,4,8,12,3,7,11,15,6,2,14,10,5,1,13,9};

    public static String MixColumn(String origin){
        String one = origin.substring(0, 4);
        String two = origin.substring(4, 8);
        String three = origin.substring(8, 12);
        String four = origin.substring(12, 16);


        //parseInt(one, 2)二进制字符串转十进制整数 toBinaryString()十进制转二进制字符串
        String xor = Xor(one, addZero(Integer.toBinaryString(mcBox[Integer.parseInt(two, 2)]),4));
        String xor1 = Xor(two, addZero(Integer.toBinaryString(mcBox[Integer.parseInt(one, 2)]),4));
        String xor2 = Xor(three, addZero(Integer.toBinaryString(mcBox[Integer.parseInt(four, 2)]),4));
        String xor3 = Xor(four, addZero(Integer.toBinaryString(mcBox[Integer.parseInt(three, 2)]),4));

        return xor+xor1+xor2+xor3;

    }



    public static String MixColumn_(String origin,int[] MCbox01,int[] MCbox02){
        String one = origin.substring(0, 4);
        String two = origin.substring(4, 8);
        String three = origin.substring(8, 12);
        String four = origin.substring(12, 16);


        //parseInt(one, 2)二进制字符串转十进制整数 toBinaryString()十进制转二进制字符串
        String xor = Xor(addZero(Integer.toBinaryString(MCbox01[Integer.parseInt(one, 2)]),4), addZero(Integer.toBinaryString(MCbox02[Integer.parseInt(two, 2)]),4));
        String xor1 = Xor(addZero(Integer.toBinaryString(MCbox01[Integer.parseInt(two, 2)]),4), addZero(Integer.toBinaryString(MCbox02[Integer.parseInt(one, 2)]),4));
        String xor2 = Xor(addZero(Integer.toBinaryString(MCbox01[Integer.parseInt(three, 2)]),4), addZero(Integer.toBinaryString(MCbox02[Integer.parseInt(four, 2)]),4));
        String xor3 = Xor(addZero(Integer.toBinaryString(MCbox01[Integer.parseInt(four, 2)]),4), addZero(Integer.toBinaryString(MCbox02[Integer.parseInt(three, 2)]),4));

        return xor+xor1+xor2+xor3;

    }





    /**
     * 密钥扩展
     */

    public static String[] Extension(String orgin){
        String[] keys=new String[6];
        keys[0]=orgin.substring(0,8);
        keys[1]=orgin.substring(8);

        keys[2]=Xor(keys[0],g(keys[1],RCON1));
        keys[3]=Xor(keys[1],keys[2]);
        keys[4]=Xor(keys[2],g(keys[3],RCON2));
        keys[5]=Xor(keys[3],keys[4]);

        return keys;

    }

    //origin 8位
    public static String g(String origin,String RCON){

        String left = origin.substring(0, 4);
        String right = origin.substring(4);

        String afterleft = CodeComponents.addZero(Integer.toBinaryString(Sbox[Integer.parseInt(left.substring(0, 2), 2)][Integer.parseInt(left.substring(2), 2)]),4);
        String afterright = CodeComponents.addZero(Integer.toBinaryString(Sbox[Integer.parseInt(right.substring(0, 2), 2)][Integer.parseInt(right.substring(2), 2)]),4);
        addZero(afterright,4);
        addZero(afterleft,4);


        return Xor(afterright+afterleft,RCON);


    }



}
