package com.eray.thjw.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 工具类，操作字符串、日期、图像、IO等
 * 
 * @author 裴本桢
 * 
 */
public class Utils {
	private static Log log = LogFactory.getLog(Utils.class);

	/**
	 * 内部类，字符串操作
	 * 
	 * @author 裴本桢
	 * 
	 */
	public static class Str {
		
		/**
		 * 
		 * @Description 判断时间格式是否正确
		 * @CreateTime 2017-10-16 上午10:48:58
		 * @CreateBy 孙霁
		 */
		public static boolean timeReg(String str) {
			String regEx= "";
			if(str.indexOf(":") != -1){
				 regEx = "(0|[1-9]\\d*)(\\:[0-5]?[0-9]?)?$";
			}else if(str.indexOf("-") != -1){
				 regEx = "(0|[1-9]\\d*)(\\-[0-5]?[0-9]?)?$";
			}else if(str.indexOf(".") != -1){
				 regEx = "(0|[1-9]\\d*)(\\.[0-5]?[0-9]?)?$";
			}
			if("".equals(regEx)){
				return false;
			}
			 Pattern pattern = Pattern.compile(regEx);
			 Matcher matcher = pattern.matcher(str);
			 boolean rs = matcher.matches();
			 return rs;
		}
		/**
		 * 
		 * @Description 判断日期时间格式是否正确
		 * @CreateTime 2017-10-16 上午10:48:58
		 * @CreateBy 孙霁
		 */
		public static boolean dateTimeReg(String str) {
			String regEx= "";
			if(str.indexOf(":") != -1){
				 regEx = "([0-9]|[0-1][0-9]|2[0-4]\\d*)(\\:[0-5]?[0-9]?)?$";
			}else if(str.indexOf("：") != -1){
				 regEx = "([0-9]|[0-1][0-9]|2[0-4]\\d*)(\\：[0-5]?[0-9]?)?$";
			}
			if("".equals(regEx)){
				return false;
			}
			 Pattern pattern = Pattern.compile(regEx);
			 Matcher matcher = pattern.matcher(str);
			 boolean rs = matcher.matches();
			 return rs;
		}
		/**
		 * 判断字符串是否为空，包括null、空字符串和空格
		 * 
		 * @param str
		 *            字符串
		 * @return 是或否
		 */
		public static boolean isEmpty(String str) {
			if (str == null || str.trim().equals("")
					|| str.trim().equalsIgnoreCase("null")) {
				return true;
			}
			return false;
		}

		
		/**
		 * 加1
		 * 
		 * @param i
		 *            s
		 * @return String
		 */
		public static String Plus(int i) {
			i++;
			String s = "0" + i;
			return s.substring(s.length() - 2);
		}

		/**
		 * 将null转为空字符串
		 * 
		 * @param str
		 *            字符串
		 * @return
		 */
		public static String nullToEmpty(String str) {
			if (str == null) {
				return "";
			}
			return str;
		}

		/**
		 * 返回字符串的字符数，无论为汉字、数字或字母，均为1个长度
		 * 
		 * @param str
		 *            字符串
		 * @return 字符个数
		 */
		public static int getLength(String str) {
			if (str == null || str.equals("")) {
				return 0;
			}
			return str.length();
		}

		/**
		 * 返回字符串的字节数，汉字为2个长度，字母或数字为1个长度
		 * 
		 * @param str
		 *            字符串
		 * @return 字节个数
		 */
		public static int getByteLength(String str) {
			if (str == null || str.equals("")) {
				return 0;
			}
			return str.getBytes().length;
		}

		/**
		 * 判断字符串是否为英文、数字和下划线组合，且以英文或下划线开头
		 * 
		 * @param str
		 * @return
		 */
		public static boolean isWord(String str) {
			if (str == null) {
				return false;
			}
			if (!str.matches("[A-Za-z_][A-Za-z0-9_]*")) {
				return false;
			}
			return true;
		}
		
		/**
		 * 完整的判断中文汉字和符号 
		 * @param strName
		 * @return
		 */
		public static boolean isChinese(String strName){
			char[] ch = strName.toCharArray();
	        for (int i = 0; i < ch.length; i++) {
	            char c = ch[i];
	            if (isChinese(c)) {
	                return true;
	            }
	        }
	        return false; 
		}
		
		/**
		 * 根据Unicode编码完美的判断中文汉字和符号 
		 * @param c
		 * @return
		 */
		private static boolean isChinese(char c) {
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
	                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
	                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
	                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
	            return true;
	        }
	        return false;
	    } 

		/**
		 * 判断字符串是否为整数
		 * 
		 * @param str
		 *            字符串
		 * @return 是或否
		 */
		public static boolean isInt(String str) {
			if (str == null) {
				return false;
			}
			if (!str.matches("([+-]?[1-9][0-9]*)|0")) {
				return false;
			}
			BigInteger bi = new BigInteger(str);
			BigInteger minValue = new BigInteger(
					String.valueOf(Integer.MIN_VALUE));
			BigInteger maxValue = new BigInteger(
					String.valueOf(Integer.MAX_VALUE));
			if (bi.compareTo(minValue) < 0 || bi.compareTo(maxValue) > 0) {
				return false;
			}

			return true;
		}

		/**
		 * 判断字符串是否为小数，且小数最多为几个，当maxFraction为-1时忽略小数位数的判断
		 * 
		 * @param str
		 *            字符串
		 * @param maxFraction
		 *            小数允许的最大个数
		 * @return 是或否
		 */
		public static boolean isDecimal(String str, int maxFraction) {
			if (str == null) {
				return false;
			}

			if (!str.matches("([+-]?[1-9][0-9]*)|([+-]?[1-9][0-9]*\\.[0-9]+)|([+-]?0\\.[0-9]+)")) {
				return false;
			}

			if (maxFraction != -1) {
				if (!str.matches("([+-]?[1-9][0-9]*)|([+-]?[1-9][0-9]*\\.[0-9]{1,"
						+ maxFraction
						+ "})|([+-]?0\\.[0-9]{1,"
						+ maxFraction
						+ "})")) {
					return false;
				}
			}

			return true;
		}
		
		/**
		 * @Description 字符串是否为有效范围内的,正数
		 * @CreateTime 2017-12-8 下午3:23:54
		 * @CreateBy 雷伟
		 * @param str
		 * @param maxFraction
		 * @return
		 */
		public static boolean isDecimalzs(String str, int maxFraction) {
			boolean flag = false;
			
			if(isDecimal(str, maxFraction)){
				double num = Double.parseDouble(str);
				if (num >= 0 && num <= 9999999.99) {
					flag = true;
				}
			}else{
				if(null != str && str.replaceAll("\\s*", "").equals("0")){
					flag = true;
				}
			}
			
			return flag;
		}

		/**
		 * 判断字符串是否为日期型
		 * 
		 * @param str
		 *            字符串
		 * @return 是或否
		 */
		public static boolean isDate(String str) {
			if (str == null) {
				return false;
			}

			Pattern pattern = Pattern
					.compile("(\\d{4})\\-(\\d{1,2})\\-(\\d{1,2})");
			Matcher matcher = pattern.matcher(str);
			if (!matcher.matches()) {
				return false;
			}

			int year = Integer.parseInt(matcher.group(1));
			int month = Integer.parseInt(matcher.group(2));
			int day = Integer.parseInt(matcher.group(3));
			boolean isLeap = false;

			if (year % 4 == 0 && year % 100 != 0) {
				isLeap = true;
			} else {
				if (year % 400 == 0) {
					isLeap = true;
				}
			}

			if (month < 1 || month > 12) {
				return false;
			} else {
				if (day < 1 || day > 31) {
					return false;
				} else {
					if (month == 4 || month == 6 || month == 9 || month == 11) {
						if (day == 31) {
							return false;
						}
					} else {
						if (month == 2) {
							if (isLeap) {
								if (day > 29) {
									return false;
								}
							} else {
								if (day > 28) {
									return false;
								}
							}
						}
					}
				}
			}

			return true;

		}

		/**
		 * 判断字符串是否为日期时间型
		 * 
		 * @param str
		 *            字符串
		 * @return 是或否
		 */
		public static boolean isDatetime(String str) {
			if (str == null) {
				return false;
			}

			Pattern pattern = Pattern
					.compile("(\\d{4})\\-(\\d{1,2})\\-(\\d{1,2})\\s(\\d{1,2}):(\\d{1,2})");
			Matcher matcher = pattern.matcher(str);
			if (!matcher.matches()) {
				return false;
			}

			int year = Integer.parseInt(matcher.group(1));
			int month = Integer.parseInt(matcher.group(2));
			int day = Integer.parseInt(matcher.group(3));
			int hour = Integer.parseInt(matcher.group(4));
			int minute = Integer.parseInt(matcher.group(5));
			boolean isLeap = false;

			if (year % 4 == 0 && year % 100 != 0) {
				isLeap = true;
			} else {
				if (year % 400 == 0) {
					isLeap = true;
				}
			}

			if (month < 1 || month > 12) {
				return false;
			} else {
				if (day < 1 || day > 31) {
					return false;
				} else {
					if (month == 4 || month == 6 || month == 9 || month == 11) {
						if (day == 31) {
							return false;
						}
					} else {
						if (month == 2) {
							if (isLeap) {
								if (day > 29) {
									return false;
								}
							} else {
								if (day > 28) {
									return false;
								}
							}
						}
					}
				}
			}

			if (hour > 23 || minute > 59) {
				return false;
			}

			return true;

		}

		/**
		 * 验证字符串是否为手机号
		 * 
		 * @param str
		 *            字符串
		 * @return 是或否
		 */
		public static boolean isMobileNumber(String str) {
			if (str == null) {
				return false;
			}

			return str.matches("1[3-8]\\d{9}");
		}
		
		/**
		 * 验证字符串是否为固定电话
		 * @param str
		 * @return
		 */
		public static boolean isLandlineTelephoneNumber(String str) {
			if (str == null) {
				return false;
			}

			return str.matches("(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +  
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)");
		}
		
		/**
		 * 验证字符串是否为QQ号
		 * @param str
		 * @return
		 */
		public static boolean isQQNumber(String str) {
			if (str == null) {
				return false;
			}
			return str.matches("[1-9][0-9]{4,14}");
		}

		/**
		 * 验证字符串是否为邮箱
		 * 
		 * @param str
		 *            字符串
		 * @return 是或否
		 */
		public static boolean isEmail(String str) {
			if (str == null) {
				return false;
			}

			return str.matches("\\w+@(\\w+\\.\\w+)+");
		}

		/**
		 * 验证字符串是否为身份证号
		 * 
		 * @param str
		 *            字符串
		 * @return 是或否
		 */
		public static boolean isIdcardNumber(String str) {
			if (str == null) {
				return false;
			}

			str = str.toUpperCase();
			if (!str.matches("(\\d{18})|(\\d{17}X)")) {
				return false;
			}

			String regionCode = str.substring(0, 6);
			String birthday = str.substring(6, 14);
			String verifyCode = str.substring(17);

			// 验证区域码
			if (regionCode.compareTo("110000") < 0) {
				return false;
			} else {
				if (regionCode.compareTo("659004") > 0) {
					if (!regionCode.equals("710000")
							&& !regionCode.equals("810000")
							&& !regionCode.equals("820000")) {
						return false;
					}
				}
			}

			// 验证生日
			String stdBirthday = birthday.substring(0, 4) + "-"
					+ birthday.substring(4, 6) + "-" + birthday.substring(6);
			if (!isDate(stdBirthday)) {
				return false;
			}

			// 验证校验码是否正确
			final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
					8, 4, 2 };
			int sum = 0;
			char[] chars = str.toCharArray();
			for (int i = 0; i < 17; i++) {
				sum = sum + Integer.parseInt(String.valueOf(chars[i]))
						* power[i];
			}

			int intVerifyCode = sum % 11;
			final String[] SEQCODES = new String[] { "1", "0", "X", "9", "8",
					"7", "6", "5", "4", "3", "2" };
			if (!SEQCODES[intVerifyCode].equals(verifyCode)) {
				return false;
			}

			return true;
		}

		/**
		 * 生成随机字符串
		 * 
		 * @param count
		 *            字符个数
		 * @return
		 */
		public static String rnd(int count) {
			if (count < 0) {
				return "";
			}

			char[] array = new char[] { '8', '5', '4', '0', '1', '2', '9', '3',
					'7', '6' };

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < count; i++) {
				int index = (int) (Math.random() * array.length);
				sb.append(array[index]);
			}

			return sb.toString();
		}

		/**
		 * 将HTML转成纯文本
		 * 
		 * @param str
		 *            HTML格式的字符串
		 * @return
		 */
		public static String htmlToText(String str) {
			// 含html标签的字符串
			String text = str;
			// 过滤script标签
			Pattern p = Pattern
					.compile(
							"<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>",
							Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(text);
			text = m.replaceAll("");
			// 过滤style标签
			p = Pattern
					.compile(
							"<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>",
							Pattern.CASE_INSENSITIVE);
			m = p.matcher(text);
			text = m.replaceAll("");
			// 过滤html标签
			p = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
			m = p.matcher(text);
			text = m.replaceAll("");

			return text;
		}

		/**
		 * 将文本域的字符串转成数据库字符串
		 * 
		 * @param str
		 *            字符串
		 * @return
		 */
		public static String fieldToDb(String str) {
			if(str==null){
				return "";
			}
			str=str.replaceAll(" ", "&nbsp;");
			str=str.replaceAll("<", "&lt;");
			str=str.replaceAll(">", "&gt;");
			str=str.replaceAll("\r", "<br />");
			return str;
		}

		/**
		 * 将数据库字符串转成文本域的字符串
		 * 
		 * @param str
		 *            字符串
		 * @return
		 */
		public static String dbToField(String str) {
			if(str==null){
				return "";
			}
			str=str.replaceAll("&nbsp;", " ");
			str=str.replaceAll("<br[\\s]*/>", "\r");
			str=str.replaceAll("&lt;", "<");
			str=str.replaceAll("&gt;", ">");
			return str;
		}

	}

	/**
	 * 内部类，Json操作
	 * 
	 * @author 裴本桢
	 * 
	 */
	public static class Json {
		/**
		 * 将对象转成Json
		 * 
		 * @param o
		 *            对象，包括单个对象和对象集合
		 * @return
		 */
		public static String toJson(Object o) {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = null;

			try {
				json = objectMapper.writeValueAsString(o);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			return json;
		}

		public static String toJson(Object o, String dft) {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = null;
			objectMapper.setDateFormat(new SimpleDateFormat(dft));
			try {
				json = objectMapper.writeValueAsString(o);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			return json;
		}

		public static String gsonToJson(Object o) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
					.create();
			String json = "";
			try {
				json = gson.toJson(o);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			return json;
		}

		/**
		 * 将json转为对象
		 * 
		 * @param json
		 *            json字符串
		 * @param clazz
		 *            单个对象的类型
		 * @return
		 */
		public static <T> T toObject(String json, Class<T> clazz) {
			T t = null;
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				t = objectMapper.readValue(json, clazz);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			return t;
		}

		/**
		 * 将json转为对象列表
		 * 
		 * @param json
		 *            json字符串
		 * @param clazz
		 *            单个对象的类型
		 * @return
		 */
		public static <T> List<T> toList(String json, Class<T> clazz) {
			ObjectMapper objectMapper = new ObjectMapper();
			JavaType javaType = objectMapper.getTypeFactory()
					.constructParametricType(List.class, clazz);
			List<T> list = null;
			try {
				list = objectMapper.readValue(json, javaType);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			return list;
		}

	}

	/**
	 * 内部类，获取IP
	 * 
	 * @author 裴本桢
	 * 
	 */
	public static class IP {
		/**
		 * 获取客户端真实IP
		 * 
		 * @param req
		 * @return
		 */
		public static String getRemoteIp(HttpServletRequest req) {
			String ip = req.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = req.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = req.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = req.getRemoteAddr();
			}
			return ip;

		}
		
		/**
		 * ip是否合法
		 * @param ip
		 * @return
		 */
		public static Boolean isLegitimate(String ip) {
			Boolean legitimate = Boolean.FALSE;
			if (StringUtils.isNotBlank(ip)) {
				Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
				Matcher matcher = pattern.matcher(ip);  
				legitimate = matcher.matches();
			}
			return legitimate;
		}
		
		
	}

	/**
	 * 内部类，向客户端打印文本、图像
	 * 
	 * @author 裴本桢
	 * 
	 */
	public static class IO {
		/**
		 * 将文本打印到WEB客户端
		 * 
		 * @param text
		 *            要打印的消息
		 * @param resp
		 */
		public static void writeText(String text, HttpServletResponse resp) {
			try {
				resp.getWriter().write(text);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		/**
		 * 将图片以流的方式发送到客户端
		 * 
		 * @param imagePath
		 *            图片完整路径
		 * @param resp
		 * @throws IOException
		 */
		public static void writeImage(BufferedImage bi, HttpServletResponse resp)
				throws IOException {
			try {
				ImageIO.write(bi, "JPEG", resp.getOutputStream());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		/**
		 * 将图片以流的方式发送到客户端
		 * 
		 * @param imagePath
		 *            图片完整路径
		 * @param resp
		 * @throws IOException
		 */
		public static void writeImage(String imagePath, HttpServletResponse resp)
				throws IOException {
			try {
				BufferedImage bi = ImageIO.read(new File(imagePath));
				String formatName = null;
				String[] arr = imagePath.split("\\.");
				if (arr != null && arr.length > 1) {
					String suffix = arr[arr.length - 1];
					if (suffix.equalsIgnoreCase("jpg")) {
						formatName = "JPEG";
					} else {
						if (suffix.equalsIgnoreCase("png")) {
							formatName = "PNG";
						} else {
							if (suffix.equalsIgnoreCase("bmp")) {
								formatName = "BMP";
							} else {
								if (suffix.equalsIgnoreCase("gif")) {
									formatName = "GIF";
								}
							}
						}
					}
					ImageIO.write(bi, formatName, resp.getOutputStream());
				}

			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}

	/**
	 * 图片工具类
	 * 
	 * @author 裴本桢
	 * 
	 */
	public static class ImgUtil {
		/**
		 * 根据指定的字符串生成验证码图片
		 * 
		 * @param str
		 *            字符串
		 * @return 图片对象
		 */
		public static BufferedImage createAuthcodeImage(String str) {
			int width = 70;
			int height = 28;

			// 生成白底空图片
			BufferedImage bi = new BufferedImage(width, height,
					BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, width, height);

			// 干扰线
			for (int i = 0; i < 10; i++) {
				int x1 = (int) (Math.random() * width);
				int y1 = (int) (Math.random() * height);
				int x2 = (int) (Math.random() * (width - 3));
				int y2 = (int) (Math.random() * (height - 3));

				Line2D line = new Line2D.Double(x1, y1, x2, y2);
				int r = (int) (Math.random() * 255);
				int g = (int) (Math.random() * 255);
				int b = (int) (Math.random() * 255);
				g2.setColor(new Color(r, g, b));
				g2.draw(line);
			}

			// 将字符拆散，设置随机大小和倾斜角度
			for (int i = 0; i < str.length(); i++) {
				String ch = String.valueOf(str.charAt(i));
				// 字体颜色
				int r = (int) (Math.random() * 128);
				int g = (int) (Math.random() * 128);
				int b = (int) (Math.random() * 128);
				// 字体大小
				int rnd = (int) (Math.random() * 5);
				int fontSize = 20;
				// 设置Y方向的偏移量
				int y = (int) (Math.random() * 6);
				if (y % 2 == 1) {
					y = y * (-1);
				}
				// 设置旋转角度
				AffineTransform trans = new AffineTransform();
				double angle = Math.random() * 0.2;
				rnd = (int) (Math.random() * 100);
				if (rnd % 2 == 1) {
					angle = angle * (-1);
				}
				trans.rotate(angle, 4 + i * 16, height / 2);
				trans.scale(1, 1);
				g2.setTransform(trans);
				g2.setFont(new Font("Arial", Font.BOLD, fontSize));
				g2.setColor(new Color(r, g, b));
				g2.drawString(ch, 5 + i * 16, 21 + y);
			}

			// 释放画笔对象
			g2.dispose();

			return bi;
		}

		/**
		 * 等比缩放图片
		 * 
		 * @param bi
		 *            原始图片
		 * @param maxWidth
		 *            最大宽度
		 * @param maxHeight
		 *            最大高度
		 * @return
		 * @throws IOException
		 */
		public static BufferedImage zoomImage(BufferedImage bi, int maxWidth,
				int maxHeight) {
			// 读取原始图片及尺寸
			int width = bi.getWidth();
			int height = bi.getHeight();
			// 计算缩放图片的宽和高
			// 如果上传的图片均小于等于允许的最大尺寸，则不缩放
			if (width <= maxWidth && height <= maxHeight) {
				return bi;
			}
			// 计算缩放后的实际宽和高（此时，宽度或高度大于允许的最大尺寸）
			double scaleW = (double) maxWidth / width;
			double scaleH = (double) maxHeight / height;
			double scale = 0;
			int w = 0;
			int h = 0;
			if (scaleW <= scaleH) {
				scale = scaleW;
				w = maxWidth;
				h = (int) (height * scaleW);
			} else {
				scale = scaleH;
				w = (int) (width * scaleH);
				h = maxHeight;
			}

			// 创建缩放后的新图片
			BufferedImage target = new BufferedImage(w, h, bi.getType());
			Graphics2D g = target.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.drawRenderedImage(bi,
					AffineTransform.getScaleInstance(scale, scale));
			g.dispose();

			return target;

		}

		/**
		 * 剪切图片
		 * 
		 * @param bi
		 *            图片
		 * @param x
		 *            起点X
		 * @param y
		 *            起点Y
		 * @param w
		 *            宽度
		 * @param h
		 *            高度
		 * @return
		 */
		public static BufferedImage cutImage(BufferedImage bi, int x, int y,
				int w, int h) {
			return bi.getSubimage(x, y, w, h);
		}

	}

	/**
	 * 日期时间工具类
	 * 
	 * @author 裴本桢
	 * 
	 */
	public static class DT {

		/**
		 * 将字符串转成日期时间
		 * 
		 * @param str
		 *            字符串
		 * @return
		 */
		public static Date strToDatetime(String str) {
			if (Str.isEmpty(str) || !Str.isDatetime(str)) {
				return null;
			}
			String[] arr = str.split("\\s+");
			String[] ymdArr = arr[0].split("-");
			String[] hmsArr = arr[1].split(":");
			Calendar c = new GregorianCalendar(Integer.parseInt(ymdArr[0]),
					Integer.parseInt(ymdArr[1]) - 1,
					Integer.parseInt(ymdArr[2]), Integer.parseInt(hmsArr[0]),
					Integer.parseInt(hmsArr[1]), 0);
			return new Date(c.getTimeInMillis());
		}

		/**
		 * 获取某一日期后经过多少天后的新日期
		 * 
		 * @param date
		 *            日期
		 * @param dx
		 *            经过的时间数量
		 * @return
		 */
		public static java.sql.Date nextDateAfterDays(java.sql.Date startDate,
				int dx) {
			Calendar c = new GregorianCalendar();
			c.setTime(startDate);
			c.add(Calendar.DAY_OF_MONTH, dx);
			java.sql.Date nextDate = new java.sql.Date(c.getTimeInMillis());

			return nextDate;
		}

		/**
		 * 获取某一日期后经过多少月后的新日期
		 * 
		 * @param date
		 *            日期
		 * @param dx
		 *            经过的时间数量
		 * @return
		 */
		public static java.sql.Date nextDateAfterMonths(
				java.sql.Date startDate, int dx) {
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(startDate);
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(startDate);
			endCalendar.add(Calendar.MONTH, dx);

			java.sql.Date nextDate = new java.sql.Date(
					endCalendar.getTimeInMillis());

			return nextDate;
		}

		/**
		 * 比较两个日期大小，date1较大时返回1，date2较大时返回-1，相等时返回0
		 * 
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static int compare(Date date1, Date date2) {
			long t1 = date1.getTime();
			long t2 = date2.getTime();
			if (t1 > t2) {
				return 1;
			} else {
				if (t1 < t2) {
					return -1;
				}
			}

			return 0;
		}

		/**
		 * 将字符串转成日期
		 * 
		 * @param str
		 *            字符串
		 * @return
		 */
		public static java.sql.Date toDate(String str) {
			String[] ymdArr = str.split("-");
			Calendar c = new GregorianCalendar(Integer.parseInt(ymdArr[0]),
					Integer.parseInt(ymdArr[1]) - 1,
					Integer.parseInt(ymdArr[2]));
			return new java.sql.Date(c.getTimeInMillis());
		}

		/**
		 * 将字符串转成日期时间
		 * 
		 * @param str
		 *            字符串
		 * @return
		 */
		public static Date toDatetime(String str) {
			String[] arr = str.split("\\s+");
			String[] ymdArr = arr[0].split("-");
			String[] hmsArr = arr[1].split(":");
			Calendar c = new GregorianCalendar(Integer.parseInt(ymdArr[0]),
					Integer.parseInt(ymdArr[1]) - 1,
					Integer.parseInt(ymdArr[2]), Integer.parseInt(hmsArr[0]),
					Integer.parseInt(hmsArr[1]), 0);
			return new Date(c.getTimeInMillis());
		}

		/**
		 * 将一个日期转换为时间字符串
		 * 
		 * @param d
		 * @return
		 */
		public static String formatDate(Date d) {
			if (d == null) {
				return null;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return sdf.format(d);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			return null;
		}

		/**
		 * 将一个日期转换为时间字符串
		 * 
		 * @param d
		 *            日期
		 * @param format
		 *            格式
		 * @return
		 */
		public static String formatDatetime(Date d) {
			if (d == null) {
				return null;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.format(d);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			return null;
		}

	}

	public static class FormatUtil {

		public static Integer parseInt(String rel) {
			Integer integer = 0;
			if (rel != null && !"".equals(rel)) {
				integer = Integer.parseInt(rel);
			}
			return integer;
		}

		public static double parseDouble(String rel) {
			double dou = 0.0;
			if (rel != null && !"".equals(rel)) {
				dou = Double.parseDouble(rel);
			}
			return dou;
		}

		/**
		 * 将一个日期转换为时间字符串
		 * 
		 * @param d
		 * @return
		 */
		public static String formatDate(Date d) {
			if (d == null) {
				return null;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return sdf.format(d);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			return null;
		}

		/**
		 * 将一个日期转换为时间字符串
		 * 
		 * @param d
		 *            日期
		 * @param format
		 *            格式
		 * @return
		 */
		public static String formatDatetime(Date d) {
			if (d == null) {
				return null;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.format(d);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			return null;
		}

		@SuppressWarnings("finally")
		public static Date parseDateTime(String rel) {
			Date datetime = null;
			try {
				if (rel != null && !"".equals(rel)) {
					SimpleDateFormat simple = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					if (rel.trim().length() == 10) {
						rel = rel + " 00:00:00";
					}
					datetime = simple.parse(rel);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} finally {
				return datetime;
			}
		}

		@SuppressWarnings("finally")
		public static Date parseTime(String rel) {
			Date datetime = null;
			try {
				if (rel != null && !"".equals(rel)) {
					SimpleDateFormat simple = new SimpleDateFormat("HH:mm:ss");
					datetime = simple.parse(rel);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} finally {
				return datetime;
			}
		}

		@SuppressWarnings("finally")
		public static Date parseDate(String rel) {
			Date datetime = null;
			try {
				if (rel != null && !"".equals(rel)) {
					SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
					datetime = simple.parse(rel);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} finally {
				return datetime;
			}
		}

		public static java.sql.Date toSqlDate(String str) {
			if (str == null || "".equals(str)) {
				return null;
			}
			String[] ymdArr = str.split("-");
			Calendar c = new GregorianCalendar(Integer.parseInt(ymdArr[0]),
					Integer.parseInt(ymdArr[1]) - 1,
					Integer.parseInt(ymdArr[2]));
			return new java.sql.Date(c.getTimeInMillis());
		}

	}
	
	public static class FileUtil {

		/** 
	     * byte(字节)根据长度转成kb(千字节)和mb(兆字节) 
	     *  
	     * @param bytes 
	     * @return ll
	     */  
	    public static String bytes2unitM(long bytes) {  
	        BigDecimal filesize = new BigDecimal(bytes);  
	        BigDecimal megabyte = new BigDecimal(1024 * 1024);  
	        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)  
	                .floatValue();  
	        if (returnValue > 1)  
	            return (returnValue + "MB");  
	        BigDecimal kilobyte = new BigDecimal(1024);  
	        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)  
	                .floatValue();  
	        return (returnValue + "KB");  
	    }  
	    
		/** 
	     * byte(字节)根据长度转成kb(千字节)
	     *  
	     * @param bytes 
	     * @return ll
	     */  
	    public static String bytes2unitK(long bytes) {  
	        BigDecimal filesize = new BigDecimal(bytes);  
	        BigDecimal kilobyte = new BigDecimal(1024);  
	        float  returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)  
	                .floatValue();  
	        return (returnValue + "");  
	    }
	    
	    /**
	     * 获取文件大小（带单位），最大单位G
	     * @param bytes
	     * @return
	     */
	    public static String bytes2unitG(long bytes) {
	    	BigDecimal kib= BigDecimal.valueOf(1024);
	    	BigDecimal mib= BigDecimal.valueOf(1048576);
	    	BigDecimal gib= BigDecimal.valueOf(1073741824);
	    	
	        BigDecimal filesize = new BigDecimal(bytes);  
	           
	        Float returnValue = filesize.divide(gib, 2, BigDecimal.ROUND_UP).floatValue();
	        if (returnValue < 1) {
	        	returnValue = filesize.divide(mib, 2, BigDecimal.ROUND_UP).floatValue();
	        	if (returnValue < 1) {
	            	returnValue = filesize.divide(kib, 2, BigDecimal.ROUND_UP).floatValue();
	            	return (returnValue + "KB"); 
	    		}
	    		return (returnValue + "MB"); 
			}
	        return (returnValue + "G"); 
	    }

	}
	
	public static class ObjUtil {
	    
	    @SuppressWarnings("unchecked")
		public static <T> T deepClone(Object src,Class<T> clazz) {
			T obj = null;
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(src);
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				ObjectInputStream ois = new ObjectInputStream(bais);
				obj = (T) ois.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return obj;
		}
	 

	}
	
}