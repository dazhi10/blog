package com.nhb.utils;

/**
 * @author 大只
 * @date 2022/10/10 23:38
 */
public class RandomNum {
    //返回随机数
    public static StringBuffer getRandomNum(int bitNum){
        int[] s=new int[bitNum];
        for (int i=0;i<s.length;i++){
            //将每次生成的随机数*10  变成整形  装入数组中
            s[i]=(int)(Math.random()*10);
        }
        //考虑到每次对String的操作都会生成新的String对象，
        // 不仅效率低下，而且浪费大量优先的内存空间，这里我们就用StringBuffer
        StringBuffer randomNum=new StringBuffer();
        for (int i=0;i<s.length;i++){
            randomNum.append(String.valueOf(s[i]));
        }
        return randomNum;
    }
}

