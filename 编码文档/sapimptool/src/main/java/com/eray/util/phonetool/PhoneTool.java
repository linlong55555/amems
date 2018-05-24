package com.eray.util.phonetool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class PhoneTool
{
  /**
   * Logger for this class
   */
  private static String mobiRegx = "^(13[0-9]|14[5|7]|15[0-9]|18[0-9])\\d{8}$";
  private static Pattern pattern = Pattern.compile(mobiRegx);
  
  /**
   * 校验手机号码规则
   */
  public static boolean checkTelRule(String tel)
  {
    tel = StringUtils.trim(tel);
    Matcher match = pattern.matcher(tel);
    return match.matches();
  }
}
