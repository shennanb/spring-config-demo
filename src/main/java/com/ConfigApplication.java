package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author shennanb
 * @date 2019/10/8 16:04
 */

@SpringBootApplication
//@EnableConfigServer
public class ConfigApplication {

  public static void main(String[] args)  throws Exception{
    SpringApplication.run(ConfigApplication.class, args);
  }

}
