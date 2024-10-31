package com.ymy.Conttoller;

import java.util.ArrayList;
import java.util.List;

public class Binarys {
    public  boolean Judge(String text){
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (char c : text.toCharArray()) {
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;

    }


    //将每个字符，变成一个8位二进制数
    public  static String charToBinary(char c,int num){
        String out=Integer.toBinaryString(c);
        while(out.length()<num)
            out="0"+out;
        return out;
    }

    //将二进制数，转换为字符
    public static char BinaryToChar(String s) {
        return (char)Integer.parseInt(s,2);
    }




     //中文转二进制方法
    public static List<String> ChnToBinary(String str) {
        List<String> res = new ArrayList<>();
        //将字符串转为字符数组
        char[] chars=str.toCharArray();
        for (char aChar : chars) {
            //迭代字符数组 将每一位转化成二进制字符串 添加至res列表
            StringBuilder binstr = new StringBuilder(Integer.toBinaryString(aChar));
            while (binstr.length() < 16) {
                binstr.insert(0, "0");
            }
            res.add(binstr.toString());
        }
        return res;
    }


   // 二进制转中文方法
    public static String BinaryToChn(String binary) {
        int decimal = Integer.parseInt(binary, 2);
        return String.valueOf((char)decimal);
    }


}
