package com.demo.menu;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.demo.menu.com.baidu.translate.demo.TransApi;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

  // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
  private static final String APP_ID = "20191104000352658";
  private static final String SECURITY_KEY = "8Cf3CemQf2oOfokVR8bt";

  public static void main(String[] args) throws Exception {
//        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
////      try (Stream<String> lines = Files.lines(Paths.get("C:\\Users\\wafer\\Desktop\\menu.txt"), Charset.defaultCharset())) {
////        lines.forEach(l ->{
////          String[] split = l.split("--->");
////          try {
////            String transResult = api.getTransResult(split[0], "auto", "wyw");
////            JSONObject jsonObject = JSONUtil.parseObj(transResult);
////            Object byPath = jsonObject.getByPath("trans_result[0].dst");
////            System.out.println(split[1]+"="+byPath);
////            Thread.sleep(1500);
////          }catch (Exception e){
////            e.fillInStackTrace();
////          }
////        });
////      }

//      System.out.println(System.currentTimeMillis());
//      System.out.println(System.currentTimeMillis()+5*60*1000);
//      System.out.println(System.currentTimeMillis());
//      System.out.println(System.currentTimeMillis());
//      try {
//        Thread.sleep(200);
//      }finally {
//
//      }
//      System.out.println(System.currentTimeMillis());

//    IntStream intStream = IntStream.range(0, 100)
//            .filter(x -> x % 2 == 0);
//
//    intStream.forEach(System.out::println);
//
//    System.out.println(intStream.count());

//    Stream.iterate(5, x -> x + 1)
//            .limit(20000)
//            .forEach(System.out::println);

//    Stream.iterate(new int[]{0, 1}, x -> new int[]{x[1], x[0] + x[1]})
//            .limit(20)
//            .forEach(y -> System.out.println(y[0] + y[1]));

    java.util.Calendar cal;
    cal = java.util.Calendar.getInstance();
    cal.setTime(new Date(Long.parseLong("1574242200000")));
    System.out.println(cal);

    Date date = new Date(Long.parseLong("1574242200000"));
    System.out.println(date
    );

  }

}
