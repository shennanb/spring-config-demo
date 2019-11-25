package com.config;

import com.entity.MailTemplate;
import freemarker.cache.TemplateLoader;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.io.StringReader;

/**
 * @author shennanb
 * @date 2019/10/9 11:01
 */
@Component("redisTemplateLoader")
public class RedisTemplateLoader implements TemplateLoader {
  @Autowired
  private StringRedisTemplate redisTemplate;

  @Override
  public Object findTemplateSource(String name) {
    try {
      MailTemplate mailTemplate = RedisHelper.get(redisTemplate,name, MailTemplate.class);
      return new StringTemplateSource(name, mailTemplate.getContext(), mailTemplate.getTime());
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public long getLastModified(Object templateSource) {
    return ((StringTemplateSource) templateSource).lastModified;
  }

  @Override
  public Reader getReader(Object templateSource, String encoding) {
    return new StringReader(((StringTemplateSource) templateSource).source);
  }

  @Override
  public void closeTemplateSource(Object templateSource) {
    //do nothing
  }

  @Data
  private static class StringTemplateSource {
    private final String name;
    private final String source;
    private final long lastModified;

    StringTemplateSource(String name, String source, long lastModified) {
      if (name == null) {
        throw new IllegalArgumentException("name == null");
      }
      if (source == null) {
        throw new IllegalArgumentException("source == null");
      }
      if (lastModified < -1L) {
        throw new IllegalArgumentException("lastModified < -1L");
      }
      this.name = name;
      this.source = source;
      this.lastModified = lastModified;
    }
  }

}
