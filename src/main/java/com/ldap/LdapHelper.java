package com.ldap;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class LdapHelper {

  private static DirContext ctx;

  public static DirContext getCtx() {
    String account = "wi00001";
    String password = "Aa123456";

    String root = "OU=test_demo,OU=syncuser_test";

    Hashtable env = new Hashtable();
    env.put(Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://192.168.0.106:389/DC=waferdemo,DC=com," + root);
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, account);
    env.put(Context.SECURITY_CREDENTIALS, password);
    try {
      ctx = new InitialDirContext(env);
      System.out.println("认证成功");
    } catch (javax.naming.AuthenticationException e) {
      e.printStackTrace();
      System.out.println("认证失败");
    } catch (Exception e) {
      System.out.println("认证出错：");
      e.printStackTrace();
    }
    return ctx;
  }

  public static void closeCtx() {
    try {
      ctx.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    DirContext ctx = LdapHelper.getCtx();
  }
}