package com.demo.mysql.dict;

import lombok.Data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shennan
 * @date 2019/11/25 16:37
 */
public class ProcessMsqlDD {

  public static void main(String[] args) {
    try (Stream<String> lines =
                 Files.lines(Paths.get("C:\\Users\\wafer\\Desktop\\测试查询.txt"), Charset.defaultCharset())) {
      TreeMap<String, TreeMap<String, List<DD>>> collect = lines
        .map(line -> {
          line = line.replace("\"", "");
          String[] split = line.split(",");
          DD dd = new DD();
          dd.setTable_schema(split[0]);
          dd.setTable_name(split[1]);
          dd.setColumn_name(split[2]);
          dd.setColumn_type(split[3]);
          dd.setIs_nullable(split[4]);
          if (split.length > 5) {
            dd.setExtra(split[5]);
          }
          if (split.length > 6) {
            dd.setColumn_comment(split[6]);
          }
          return dd;
        })
        .collect(Collectors.groupingBy
          (DD::getTable_schema, TreeMap::new,
            Collectors.groupingBy
              (DD::getTable_name, TreeMap::new,
                Collectors.toList())));
      collect.entrySet().stream()
        .forEach(key -> {
          String name = key.getKey();
          System.out.println("### 库:" + name.substring(name.indexOf("_") + 1));
          key.getValue().entrySet().stream()
            .forEach(key1 -> {
              System.out.println("#### 表:" + key1.getKey());
              System.out.println("字段名 | 数据类型 | 允许为空 | PK | 字段说明");
              System.out.println("---|---|---|---|---");
              key1.getValue().stream().forEach(x -> System.out.println(x.toString()));
            });
        });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

@Data
class DD {
  private String table_schema;
  private String table_name;
  private String column_name;
  private String column_type;
  private String is_nullable;
  private String extra;
  private String column_comment;

  @Override
  public String toString() {
    return column_name + " | " + column_type + " | " + is_nullable + " | " + extra + " | " + column_comment;
  }
}
