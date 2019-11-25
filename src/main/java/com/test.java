package com;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author shennan
 * @date 2019/10/21 10:29
 */
public class test {

  public static void main(String[] args) {
    String str = "-1-2-5-6-7-";
    if (str.startsWith("-")){
      str = str.substring(1);
    }
    if (str.endsWith("-")){
      str = str.substring(0,str.length() - 1);
    }
    System.out.println(str);

    String[] split = str.split("-");
    List<String> ids = Arrays.asList(split);
    System.out.println(split.length);
    for (int i = 0; i < split.length ; i++) {
      System.out.println(split[i]);
    }
  }

}
