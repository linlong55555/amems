package com.eray.util.poi;

import java.util.Calendar;

import org.apache.poi.ss.usermodel.DateUtil;

public class XSSFDateUtil extends DateUtil {  
    protected static int absoluteDay(Calendar cal, boolean use1904windowing) {  
        return DateUtil.absoluteDay(cal, use1904windowing);  
    }  
}  