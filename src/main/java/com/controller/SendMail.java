package com.controller;

import com.config.RedisTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shennanb
 * @date 2019/10/9 9:55
 */
@RequestMapping("/mail")
@RestController
public class SendMail {

  @Autowired
  private Configuration configuration;

  @Autowired
  private JavaMailSender jms;

  @Autowired
  private RedisTemplateLoader redisTemplateLoader;

  @RequestMapping("/send")
  public void send() throws Exception{
    MimeMessage message =jms.createMimeMessage();
//    StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
    try {
      MimeMessageHelper helper = new MimeMessageHelper( message,true);
      helper.setFrom("378191876@qq.com");
      helper.setTo("snanb@outlook.com");
      helper.setSubject("使用模板");

      Map<String, Object> model = new HashMap();
      model.put("UserName", "yao");

      try {
        configuration.setTemplateLoader(redisTemplateLoader);
        Template template = configuration.getTemplate("welcome");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
        helper.setText(html,true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    jms.send(message);
    System.out.println("发送邮件成功");
  }

}
