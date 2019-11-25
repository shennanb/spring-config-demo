package com.freemarker;

public class TestReplace
{
    public static void main(String[] args)
    {
        test4();
        test3();
        test2();
        test1();
    }
    
    /**
     * 4.   将下面的国家重叠的数字替换成 空格
            China12345America678922England342343434Mexica
     */
    public static void test4()
    {
        String str="China12345America678922England342343434Mexica";
        System.out.println(str.replaceAll("\\d+", " "));
    }
    
    /**
     * 3.   将下面的国家重叠的字符替换成 一个, 也就是去掉重复的分隔符
            China|||||America::::::England&&&&&&&Mexica
     */
    public static void test3()
    {
        String str="China|||||America::::::England&&&&&&&Mexica";
        System.out.println(str.replaceAll("(.)\\1+","$1"));
    }
    
    /**
     * 2.   将下面的国家重叠的字符替换成 竖线 |
            ChinaqqqAmericahhhhhEnglandaaaaaaMexica
     */
    public static void test2()
    {
        String str="ChinaqqqAmericahhhhhEnglandaaaaaaMexica";
        System.out.println(str.replaceAll("(.)\\1+", "|"));
    }
    
    /**
     * 1.   将字符串  "李阳   王丽      李明    张俊 小雷" 的空格和tab 替换成 逗号 ,
     */
    public static void test1()
    {
        String str="李阳  王丽      李明    张俊 小雷";
        System.out.println(str.replaceAll("[ \\t]+", ","));
    }
}