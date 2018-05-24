package com.eray.common.jstl;

/**
 * jstl字符串处理
 * @author xu.yong
 *
 */
public class StringUtils {
	
	/**
	 * 转义
	 * @param input
	 * @return
	 */
	public static String escapeStr(String input){
		if (org.apache.commons.lang.StringUtils.isBlank(input)) {
			return "";
		}
		input = input.replaceAll("&", "&amp;");
		input = input.replaceAll("<", "&lt;");
		input = input.replaceAll(">", "&gt;");
//		input = input.replaceAll(" ", "&nbsp;");
		input = input.replaceAll("'", "&#39;");
		input = input.replaceAll("\"", "&quot;");
//		input = input.replaceAll("\n", "<br>");
		return input;

	}
}
