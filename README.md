[ S-AES用户指南.pdf…]()

# 开发手册



### 1.项目描述

 S-AES算法实现程序是一个用于演示和理解S-AES（Simplified Advanced Encryption Standard）加密算法的工具，该程序的主要目的是帮助学生、研究人员和密码学爱好者理解对称密钥加密算法的基本原理以及实现数据加密与解密操作：主要包括对ASCⅡ编码字符串进行加密解密、对二进制字符进行加密解密、双重加密、中间相遇攻击、三重加密、分组加密工作模式等功能的实现。





### 2.使用说明

#### 	下载和安装

1. 下载地址：

2. 运行环境：安装Java JDK1.8
3. 取S-AES代码仓库下载相关Java代码，然后将代码带入您喜欢的Java集成开发环境(如IDEA)中。



### 3.程序结构

**1.目录结构**



![QQ20241031-165347](E:/teamwork02/src/com/img/QQ20241031-165347.png)



**2.主要组件**

**2.1 CodeComponents类**

* Xor()方法:对二进制字符串进行异或操作，返回异或结果的二进制字符串
* HalfByteSub()方法：执行半字节代替：将输入字符的每四位作为一个组，通过Sbox进行代替
* ShitftRow()方法：对输入字符串的四个行进行循环左移。
* MixColumn()方法：将输入字符串的每一列进行混淆。
* Extension()方法：扩展输入进来的字符串位数。

```java
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

```



**2.2 Binarys类**

* Judge()方法：判断输入的字符串是否为二进制字符串
* charToBinary()方法：将输入的字符串变成二进制字符串
* BinaryToChar()方法：将输入的二进制字符串变成字符字符串
* ChnToBinary()方法：将输入的中文字符串变成二进制字符串
* BinaryToChn()方法：将输入的二进制字符串变成中文字符串

```java
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
```



**2.3 Encode类**

* encode()方法：通过对CodeComponents类的各个方法应用，通过给定的密钥，对输入的二进制明文字符串进行加密，返回密文字符串。
* Dencode()方法：进行二重加密，将32位密钥分成两个16位密钥，每次加密的流程都是按照encode()方法进行。
* Tencode()方法：进行三重加密，将48位密钥分成三个16位密钥，每次加密的流程都是按照encode()方法进行。
* Ascencode()方法：通过对CodeComponents类的各个方法应用，通过给定的密钥，对输入的字符明文字符串进行加密，返回密文字符串。
* AscDencode()方法：进行二重加密，将32位密钥分成两个16位密钥，每次加密的流程都是按照Ascencode()方法进行。
* AscTencode()方法：进行三重加密，将48位密钥分成三个16位密钥，每次加密的流程都是按照Ascencode()方法进行。

```java
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
```



**2.4 Decode类**

* decode()方法：通过对CodeComponents类的各个方法应用，通过给定的密钥，对输入的二进制明文字符串进行解密，返回明文字符串。
* Ddecode()方法：进行二重解密，将32位密钥分成两个16位密钥，每次解密的流程都是按照decode()方法进行。
* Tdecode()方法：进行三重解密，将48位密钥分成三个16位密钥，每次解密的流程都是按照decode()方法进行。
* Ascdecode()方法：通过对CodeComponents类的各个方法应用，通过给定的密钥，对输入的字符明文字符串进行解密，返回明文字符串。
* AscDdecode()方法：进行二重解密，将32位密钥分成两个16位密钥，每次解密的流程都是按照Ascdecode()方法进行。
* AscTdecode()方法：进行三重加密，将48位密钥分成三个16位密钥，每次解密的流程都是按照Ascdecode()方法进行。

```java
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
```



**2.5 MIMT类**

*  quickSort()方法：快速排序方法，对整数数组进行快速排序，并保持密钥与密文下标一致。该方法用于对中间密文空间进行排序，以加速中间相遇攻击的查找过程。
*  binarysearch()方法：二分查找方法，对排序后的中间密文空间执行二分查找，以查找具有相同中间密文的密钥。该方法返回中间密文相同的空间的起始和结束下标。
*  SingleMsgAttack()方法：单一明密文对破解方法，该方法接受一个明文  和一个密文 ，并尝试通过遍历密钥空间，对密文进行解密，并查找中间密文空间，以找到相同的中间密钥。它返回一个包含可能的密钥的列表；
*  MultiMsgAttack：多明密文对破解方法，该方法接受多个明文和密文对，以及单一明密文对破解方法返回的密钥堆。它遍历给定的密钥空间，对每个密钥，遍历每个明密文对并检查是否中间密文相同。如果中间密文相同，将该密钥添加到结果列表中。

```java
//快排
public static void quickSort(int[] arr,String[] heap,int low,int high){
    int i,j,temp,t;
    String s;
    if(low>high){
        return;
    }
    i=low;
    j=high;
    //temp是基准位
    temp = arr[low];
    while (i<j) {
        //先看右边，依次往左递减
        while (temp<=arr[j]&&i<j) {
            j--;
        }
        //再看左边，依次往右递增
        while (temp>=arr[i]&&i<j) {
            i++;
        }
        //如果满足条件则交换
        if (i<j) {
            //交换整数数组
            t = arr[j];
            arr[j] = arr[i];
            arr[i] = t;
            //交换对应的密钥数组
            s = heap[j];
            heap[j] = heap[i];
            heap[i] = s;
        }
    }
    //最后将基准为与i和j相等位置的数字交换
    arr[low] = arr[i];
    arr[i] = temp;
    s = heap[low];
    heap[low] = heap[i];
    heap[i] = s;
    //递归调用左半数组
    quickSort(arr,heap,low,j-1);
    //递归调用右半数组
    quickSort(arr,heap,j+1,high);
}

//二分查找
public static int[] binarysearch(int[] li, int count) {
    //左指针
    int low = 0;
    //右指针
    int high = li.length - 1;
    int middle;
    while (low <= high) {
        middle = (low + high) / 2;
        if (count == li[middle]) {
            int big = middle,small = middle;
            //试探下标递增是否有相同的对象 如有则增大右下标
            while (big < 65535 && li[big + 1] == li[big]){
                big++;
            }
            //试探下标递减是否有相同的对象 如有则减小左下标
            while ( small > 0 &&li[small - 1] == li[small]){
                small--;
            }
            return new int[]{small,big};
        } else if (count < li[middle]) {
            high = middle - 1;
        } else {
            low = middle + 1;
        }
    }
    //如查找不到 返回负下标
    return new int[]{-1, -1};
}

/*
单一明密文对破解方法
 */
public static List<String> SingleMsgAttack(String original,String secret){
    List<String> keyArray = new ArrayList<>();
    StringBuilder key;
    //遍历前十六位的密钥空间 0-65535
    for (int i = 0; i < 65536; i++){
        //将整数转化成二进制字符串
        key = new StringBuilder(Integer.toBinaryString(i));
        //补0操作
        while (key.length() < 16){
            key.insert(0, '0');
        }
        //生成中间密文空间 并转化成整数数组 保持密文与对应密钥下标一致
        middleKey[i] = key.toString(); //key
        middleInt[i] = Integer.parseInt(Encode.encode(original, key.toString()),2);  //密文
    }
    //对密文空间做快速排序 排序方法会保持密钥与密文下标一致
    quickSort(middleInt, middleKey,0,65535);

    //遍历后十六位的密钥空间 0-65535
    for (int i = 0; i < 65536; i++){
        //将整数转化成二进制字符串
        key = new StringBuilder(Integer.toBinaryString(i));
        //补0操作
        while (key.length() < 16){
            key.insert(0, '0');
        }

        //用密钥空间对密文进行解密 得到中间密文后在密钥空间进行对分查找 找到相同的中间密钥后返回两个下标 标识中间密文相同的空间
        int[] mark = binarysearch(middleInt,Integer.parseInt(Decode.decode(secret, key.toString()),2));
        //检测是否查找到
        if (mark[0] > 0){
            for (int j = mark[0]; j <= mark[1]; j++){
                //将查找到的空间中的密钥与当前遍历的密钥组合 放入结果堆
                keyArray.add(middleKey[j] + key);
            }
        }
    }
    return keyArray;
}

/*
 * 多明密文对破解
 * 输入初步攻击得出的密钥堆、除了初步攻击输入以外的所有明密文堆
 */
public List<String> MultiMsgAttack(List<String> original,List<String> secret,List<String> keyArray){
    List<String> res = new ArrayList<>();
    //遍历给出的密钥空间
    for (String key : keyArray){
        //遍历给出的每个明密文对
        for (int i = 0; i < original.size(); i++){
            //判断中间密文是否相同 不同则退出循环 全程相同则加入结果堆
            if (!Objects.equals(Encode.encode(original.get(i), key.substring(0, 16)), Decode.decode(secret.get(i), key.substring(16, 32)))){
                break;
            }else if (i == original.size()-1){
                res.add(key);
            }
        }
    }
    return res;
}
```



**2.6 CBC类** 

*  CbcEncode()方法：CBC编码方法，该方法接受明文块列表 、密钥  和初始向量 (`IV`) 作为参数。它首先将初始向量 与第一个明文块异或，然后使用AES加密算法对结果进行加密，将加密结果放回原位。然后，将每个密文块与前一个密文块异或后，再进行AES加密，将加密结果转化为中文字符，并将其连接成最终的密文字符串；

*  CbcDecode：CBC解码方法，该方法接受密文块列表 、密钥和初始向量 (`IV`) 作为参数。它首先将第一个密文块使用AES解密算法进行解密，然后与初始向量 (`IV`) 异或，将结果转化为中文字符，将其放入结果字符串中。接着，对于每个后续的密文块，它将密文块使用AES解密后，与前一个密文块异或，再将结果转化为中文字符，添加到结果字符串 中。

```java
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
```



### 4.项目原理

#### 	4.1概述

 加密/解密算法使用一个16位明文/密文分组和一个16位密钥作为输入，生成一个16位密文/明文作为输出。



![QQ20241031-173634](E:/teamwork02/src/com/img/QQ20241031-173634.png)

#### 	4.2算法细节 	

* 使用4个不同的函数或变换：密钥加(A~k~)、半字节代替(NS)、行移位(SR)和列混淆(MC)，进行复合操作。

​	**4.2.1 加密算法**

![QQ20241031-174131](E:/teamwork02/src/com/img/QQ20241031-174131.png)

​	**4.2.2 解密算法**

![QQ20241031-175349](E:/teamwork02/src/com/img/QQ20241031-175349.png)



### 5.特别鸣谢

* 课程名称：信息安全导论
* 教学班级：992987-001
* 任课教师：胡海波
* 小组：重庆老表


