package com.demo.menu;

import cn.hutool.core.util.StrUtil;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author shennan
 * @date 2019/11/4 16:11
 */
public class MenuSqlSwitch {

  public static void main(String[] args) throws Exception {
    try (Stream<String> lines = Files.lines(Paths.get("C:\\Users\\wafer\\Desktop\\menu.txt"), Charset.defaultCharset())) {
      lines.forEach(l ->{
        if (!l.contains("INFO") && !l.contains("DEBUG")){
          System.out.println(l);
        }
      });
    }
  }
}
