import com.ConfigApplication;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shennanb
 * @date 2019/10/9 9:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigApplication.class)
public class TestSendMail {

  @Autowired
  private Configuration configuration;

  @Autowired
  private JavaMailSender jms;

  @Test
  public void send() throws Exception{
    MimeMessage message =jms.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper( message,true);
      helper.setFrom("378191876@qq.com");
      helper.setTo("snanb@outlook.com");
      helper.setSubject("使用模板");

      Map<String, Object> model = new HashMap();
      model.put("UserName", "yao");

      try {
        Template template = configuration.getTemplate("welcome.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,model);

        helper.setText(html,true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    jms.send(message);
  }

}
