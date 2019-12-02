package com.freemarker;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shennan
 * @date 2019/10/12 9:33
 */
public class VMCaseFM {
  public static Boolean startFlage = false;

  public static String old1 = "#macro( message $key )$TemVal.resource.getMessage($key, null, $TemVal.locale)#end";
  public static String new1 = "<#macro message code, locale>${loccalMessage.getMessage(code, locale)}</#macro>";

  public static String old13 = "#end";
  public static String new13 = "</#if>";

  public static String startCase(String oldStr, String newStr, String str) {
    str = str.replace(oldStr, newStr);
    return str;
  }

  public static String caseMessage(String content) {
    String pattern = "#message\\('[0-9a-zA-Z\\.\\_]+'\\)";
    String sssStart = "<@message code=\"";
    String sssEnd = "\" locale=\"${locale}\"/>";
    // 创建 Pattern 对象
    Pattern r = Pattern.compile(pattern);
    // 现在创建 matcher 对象
    Matcher m = r.matcher(content);
    while (m.find()) {
      String oldMessage = m.group(0);
      String key = oldMessage.substring(oldMessage.indexOf("'") + 1, oldMessage.lastIndexOf("'"));
      String newMessage = sssStart + key + sssEnd;
      content = content.replace(oldMessage, newMessage);
    }
    return content;
  }


  public static String caseGetValue(String content) {
    String pattern = "\\$!\\{[\\[\\].\\{\\}\\(\\)\",0-9a-zA-Z\\.\\_]+\\}";
    String sssStart = "${";
    String sssEnd = "!''}";
    // 创建 Pattern 对象
    Pattern r = Pattern.compile(pattern);
    // 现在创建 matcher 对象
    Matcher m = r.matcher(content);
    while (m.find()) {
      String oldMessage = m.group(0);
      String key = oldMessage.substring(oldMessage.indexOf("$!{") + 3, oldMessage.lastIndexOf("}"));
      key = key.replace("TemVal.", "");
      String newMessage = sssStart + key + sssEnd;
      content = content.replace(oldMessage, newMessage);
    }
    return content;
  }

  public static String caseIf(String content, String pattern) {
    String sssStart = "<#if ";
    String sssEnd = ">";
    // 创建 Pattern 对象
    Pattern r = Pattern.compile(pattern);
    // 现在创建 matcher 对象
    Matcher m = r.matcher(content);
    while (m.find()) {
      String oldMessage = m.group(0);
      String key = oldMessage.substring(oldMessage.indexOf("#if(") + 4, oldMessage.lastIndexOf(")"));
      key = key.replaceAll("!''", "");
      key = key.replaceAll("\\$\\{", "");
      key = key.replaceAll("}", "");
      if ((key.contains("==") || key.contains("!=")) && !key.contains("\"") && !key.contains("'")) {
        key = key.replaceAll("==", "=='");
        key = key.replaceAll("!=", "!='");
        key = key.replaceAll(" &&", "' &&");
        key = key.replaceAll(" \\|\\|", "' \\|\\|");
        key = key + "'";
      }
      String newMessage = sssStart + key + sssEnd;
      content = content.replace(oldMessage, newMessage);
    }
    return content;
  }

  public static String caseElseIf(String content) {
    String pattern = "#elseif\\([\\| \\'\\&\\$\\!\\{\\}\\=0-9a-zA-Z\\.\\_]+\\)";
    String sssStart = "<#elseif ";
    String sssEnd = ">";
    // 创建 Pattern 对象
    Pattern r = Pattern.compile(pattern);
    // 现在创建 matcher 对象
    Matcher m = r.matcher(content);
    while (m.find()) {
      String oldMessage = m.group(0);
      String key = oldMessage.substring(oldMessage.indexOf("#elseif(") + 8, oldMessage.lastIndexOf(")"));
      key = key.replaceAll("!''", "");
      key = key.replaceAll("\\$\\{", "");
      key = key.replaceAll("}", "");
      if ((key.contains("==") || key.contains("!=")) && !key.contains("\"") && !key.contains("'")) {
        key = key.replaceAll("==", "=='");
        key = key.replaceAll("!=", "!='");
        key = key.replaceAll(" &&", "' &&");
        key = key.replaceAll(" \\|\\|", "' \\|\\|");
        key = key + "'";
      }

      String newMessage = sssStart + key + sssEnd;
      content = content.replace(oldMessage, newMessage);
    }
    return content;

  }


  public static String caseElseIf1(String content) {
    String pattern = "#elseif[\\| \\'\\$\\!\\{\\}\\=0-9a-zA-Z\\.\\_]+\\)";
    String sssStart = "<#elseif ";
    String sssEnd = ">";
    // 创建 Pattern 对象
    Pattern r = Pattern.compile(pattern);
    // 现在创建 matcher 对象
    Matcher m = r.matcher(content);
    while (m.find()) {
      String oldMessage = m.group(0);
      String key = oldMessage.substring(oldMessage.indexOf("#elseif ") + 8, oldMessage.lastIndexOf(")"));
      key = key.replaceAll("!''", "");
      key = key.replaceAll("\\$\\{", "");
      key = key.replaceAll("}", "");
      String newMessage = sssStart + key + sssEnd;
      content = content.replace(oldMessage, newMessage);
    }
    return content;
  }


  public static String caseLine(String str) {
    str = caseMessage(str);
    str = caseGetValue(str);
    str = str.replaceAll("elseif", "888888888888888888888");
    str = str.replaceAll("#else", "<#else>");
    str = str.replaceAll("888888888888888888888", "elseif");
    str = caseIf(str, "#if\\([\\| '\\&\\$\\!\\\"\\{\\}\\=0-9a-zA-Z\\.\\_]+\\)");
    str = caseElseIf(str);
    StringBuilder sbb = new StringBuilder();
    str = str.replaceAll(old13, new13);

    //格式化
    char[] chars = str.toCharArray();
    int x = 0;
    for (int j = 0; j < chars.length; j++) {
      char aChar = chars[j];
      if (aChar == '/') {
        x = x - 2;
        sbb.append(aChar);
      } else if (aChar == '<') {
        sbb.append("\r\n");
        x = x + 1;
        for (int k = 0; k < x; k++) {
          sbb.append(" ");
        }
        sbb.append(aChar);
      } else {
        sbb.append(aChar);
      }
    }
    return sbb.toString();
  }

  public static String caseFile(File file) {
    System.out.println("开始转换文件" + file.getName());
    try (BufferedReader br = new BufferedReader(new FileReader(file));) {
      String str = "";
      StringBuilder sb = new StringBuilder();
      StringBuilder sb1 = new StringBuilder();
      while ((str = br.readLine()) != null) {
        if (str.contains(old1)) {
          sb.append(startCase(old1, new1, str) + "\r\n");
          continue;
        }
        if (str.trim().contains("<body")) {
          startFlage = true;
        }
        if (str.trim().contains("</body>")) {

          sb.append(caseLine(sb1.toString()) + "\r\n");
          sb.append(str.trim() + "\r\n");
          startFlage = false;
          continue;
        }

        if (startFlage) {
          String trim = str.trim();
          trim = trim.replaceAll("\t", "");
          sb1.append(str.trim());
        } else {
          sb.append(str + "\r\n");
        }
      }
      startFlage = false;
      return sb.toString();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void outputFile(String str, String filePath) {
    try {
      File file = new File(filePath);
      if (!file.exists()) {
        file.createNewFile();
      }
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(file));) {
        bw.write(str);
        bw.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    String inPath = "C:\\Users\\wafer\\Desktop\\templates";
    String outPath = "C:\\Users\\wafer\\Desktop\\templates-out";

    File inFile = new File(inPath);
    File inFiles[] = inFile.listFiles();
    for (File file : inFiles) {
      String str = caseFile(file);
      String fileName = file.getName();
      fileName = fileName.replace("vm", "flt");
      outputFile(str, outPath + File.separator + fileName);
    }
  }

}
