package com.eray.util.format;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class FormatUtil {
	
	/**
	 * 百分比换算
	 * 
	 * @param total
	 * @param worked
	 * @return
	 */
	public static String formatData(Long total, Long worked) {
		if(total==0)return "0%";
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format((float) worked / (float) total * 100) + "%";
	}
	/**
	 * 时间格式化
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		if (date == null) return null;
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * 格式化工包编号,工包编号固定为8位
	 * @param rid 工包编号
	 * @return 格式化后的工包编号
	 */
	public static String formatRid(String rid) {
		if (StringUtils.isEmpty(rid)) {
			return null;
		}
		if (!StringUtils.isNumeric(rid)) { //非数字
			return rid;
		}
		if (rid.length() > 8) {
			return rid;
		}
		return String.valueOf((Long.valueOf(rid) + 100000000L)).substring(1) ;
	}

}
