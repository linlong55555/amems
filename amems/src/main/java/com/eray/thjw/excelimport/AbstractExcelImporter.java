package com.eray.thjw.excelimport;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.AttrType;
import enu.ThreadVar;

public abstract class AbstractExcelImporter<K> implements BaseExcelImporter<K>{
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	/** 整型类型错误 */
	protected final static Integer INTEGER_TYPE_ERROR = Integer.MAX_VALUE;
	
	/** 时间类型错误 */
	protected final static Integer TIME_TYPE_ERROR = Integer.MAX_VALUE;
	
	/** 长度超长错误 */
	protected final static Integer LENGTH_TYPE_ERROR = Integer.MAX_VALUE;
	
	/** 小数类型错误 */
	protected final static BigDecimal  BIGDECIMAL_TYPE_ERROR = new BigDecimal(Integer.MAX_VALUE);
	
	/** 日期类型错误 */
	protected final static Date DATE_TYPE_ERROR = new Date();
	
	/** 参数map */
	protected final static Map<String, Map<String, Object>> paramMap = new HashMap<String, Map<String,Object>>();
	
	/** 文件名称 */
	public final static String FILE_NAME = "excelFile";
	
	/** 起始行名称 */
	public final static String STARTINDEX_NAME = "startIndex";
	
	/** 默认起始行 */
	public static Integer START_INDEX = 2;
	
/*	// 配置
	private final Map<Integer, AttrBean> config = new HashMap<Integer, AttrBean>();
	
	// 实体类
	protected final List<K> entityList = new ArrayList<K>();
	
	// 实体类对应class
	private final Class<K> entityClass;
	
	@SuppressWarnings("unchecked")
	public AbstractExcelImporter() {  
		
		// 获取实体class
        Type genType = getClass().getGenericSuperclass();  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
        entityClass = (Class<K>) params[0];  
        
        // 自定义日期类型转换器
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class  type, Object value) { //type:目前需要转换的数据类型 value：目前参数的值
                //目标：将字符串转换为日期

                if(type != Date.class)  return null;
                
                String valueStr = String.valueOf(value);

                if(StringUtils.isBlank(valueStr)) {
                    return null;
                }
                return convertToDate(valueStr);
            }
        }, Date.class);
    }  */
	
	/**
	 * 开始excel导入
	 * @throws ExcelImportException 
	 */
	@Override
	public final void doImport(MultipartFile file) throws ExcelImportException {
		// 导入之前执行
		before();
		// 初始化配置
		// initConfig();
		// 解析excel
		Map<Integer, List<K>> datas = parseExcel(file);
		// 验证数据
		validateParam(datas);
		// 检查错误
		checkError();
		// 写入数据到数据库
		writeToDB(datas);
		// 导入之后执行
		after();
	} 
	
	
	
	/**
	 * 初始化配置
	 */
//	protected abstract void initConfig();
	
	/**
	 * 检查错误
	 */
	@Override
	public void checkError() throws ExcelImportException{
		List<String> errorMessages = ThreadVarUtil.getExcelErrorMessage();
		if(errorMessages != null && !errorMessages.isEmpty()){
			// 组装错误数据，并直接抛出异常
			throw new ExcelImportException(assemleErrorMessage(errorMessages));
		}
	}



	/**
	 * 导入之前操作
	 */
	protected void before() {
		log.info("--------------------excel导入开始------------------------");
		// 重置错误信息
		ThreadVarUtil.set(ThreadVar.EXCEL_ERROR_MESSAGE.name(), null);
	}
	
	/**
	 * 导入之后操作
	 */
	protected void after() {
		log.info("--------------------excel导入结束------------------------");
		// 重置用户参数
		paramMap.put(ThreadVarUtil.getUser().getId(), null);
	}
	
	/**
	 * 获取工作簿
	 * @param path
	 * @return
	 * @throws ExcelImportException
	 */
	protected Workbook getWorkbook(MultipartFile file) throws ExcelImportException{
    	try {
			return WorkbookFactory.create(file.getInputStream());
		} catch (EncryptedDocumentException e) {
			throw new ExcelImportException("该文件被加密，解析失败", e);
		} catch (InvalidFormatException e) {
			throw new ExcelImportException("文件格式错误，解析失败", e);
		} catch (IOException e) {
			throw new ExcelImportException("无法获取文件，解析失败", e);
		}
	}

	/**
	 * 拼接错误消息
	 */
	@Override
	public String assemleErrorMessage(List<String> errorMessages) {
		StringBuilder errorContent = new StringBuilder("Excel导入失败！<br/>"); 
		for (String msg : errorMessages) {
			errorContent.append(msg).append("<br/>");
		}
		return errorContent.toString();
	}
	
	/**
	 * 添加错误信息
	 */
	protected void addErrorMessage(String errorMessage) {
		ThreadVarUtil.setExcelErrorMessage(errorMessage);
	}
	
	/**
	 * 获取cell中的值-String类型
	 * @param cell
	 * @return
	 */
	protected String getStringValue(Cell cell){
		if(cell == null){
			return null;
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		String str = cell.getStringCellValue();
		if(str != null){
			str = str.trim();
		}
		return str;
	}
	
	/**
	 * 获取cell中的值-Integer类型
	 * @param cell
	 * @return
	 */
	protected Integer getIntegerValue(Cell cell){
		if(cell == null)	
			return null;
		return Integer.parseInt(getStringValue(cell));
	}
	
	
	/**
	 * String转Date
	 * @param str
	 * @param rowIndex
	 * @param des
	 * @return
	 */
	protected Date convertToDate(String str){
		return convertToDate(str, null);
	}
	
	/**
	 * String转Date
	 * @param str
	 * @param rowNo
	 * @param des
	 * @param defaultValue
	 * @return
	 */
	protected Date convertToDate(String str, Date defaultValue){
		if(StringUtils.isBlank(str)){
			return defaultValue;
		}
		String pattern;
		if(str.contains(".")){
			pattern = "yyyy.MM.dd";
		}else if(str.contains("-")){
			pattern = "yyyy-MM-dd";
		}else if(str.contains("/")){
			pattern = "yyyy/MM/dd";
		}else{
			pattern = "yyyyMMdd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setLenient(false);//全匹配，强验证
		try {
			return format.parse(str);
		} catch (ParseException e) {
			return DATE_TYPE_ERROR;
		}
	}

	/**
	 * String转Date
	 * @param str
	 * @param rowIndex
	 * @param des
	 * @return
	 */
	protected Date convertToDates(String str){
		return convertToDates(str, null);
	}
	
	/**
	 * String转时间
	 * @param str
	 * @param rowNo
	 * @param des
	 * @param defaultValue
	 * @return
	 */
	protected Date convertToDates(String str, Date defaultValue){
		if(StringUtils.isBlank(str)){
			return defaultValue;
		}
		String pattern;
		if(str.contains(".")){
			pattern = "yyyy.MM.dd HH:mm:ss";
		}else if(str.contains("-")){
			pattern = "yyyy-MM-dd HH:mm:ss";
		}else if(str.contains("/")){
			pattern = "yyyy/MM/dd HH:mm:ss";
		}else{
			pattern = "yyyyMMdd HH:mm:ss";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setLenient(false);//全匹配，强验证
		try {
			return format.parse(str);
		} catch (ParseException e) {
			return DATE_TYPE_ERROR;
		}
	}
	
	
	/**
	 * 验证出生年月
	 * @param str
	 * @param rowIndex
	 * @param des
	 * @return
	 */
	protected Date convertToDateny(String str){
		return convertToDateny(str, null);
	}
	
	/**
	 * String转Date
	 * @param str
	 * @param rowNo
	 * @param des
	 * @param defaultValue
	 * @return
	 */
	protected Date convertToDateny(String str, Date defaultValue){
		if(StringUtils.isBlank(str)){
			return defaultValue;
		}
		String pattern;
		if(str.contains(".")){
			pattern = "yyyy.MM";
			str=str+".01";
		}else if(str.contains("-")){
			pattern = "yyyy-MM";
			str=str+"-01";
		}else if(str.contains("/")){
			pattern = "yyyy/MM";
			str=str+"/01";
		}else{
			pattern = "yyyyMM";
			str=str+"01";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setLenient(false);//全匹配，强验证
		try {
			return format.parse(str);
		} catch (ParseException e) {
			return DATE_TYPE_ERROR;
		}
	}
	/**
	 * String转Integer
	 * @param str
	 * @param rowIndex
	 * @param des
	 * @return
	 */
	protected Integer convertToInteger(String str){
		return convertToInteger(str, null);
	}
	
	/**
	 * String转Integer
	 * @param str
	 * @param rowNo
	 * @param des
	 * @param defaultValue
	 * @return
	 */
	protected Integer convertToInteger(String str, Integer defaultValue){
		if(StringUtils.isBlank(str)){
			return defaultValue;
		}
		if(!Utils.Str.isInt(str)){
			return INTEGER_TYPE_ERROR;
		}
		return Integer.parseInt(str);
	}
	/**
	 * 判断小时数和分钟数是否正确
	 * @param str
	 * @param rowNo
	 * @param des
	 * @param defaultValue
	 * @return
	 */
	protected Integer convertToMoutin(String str, Integer defaultValue){
		//判空
		if(StringUtils.isBlank(str)){
			return defaultValue;
		}
		//判长
		if(Utils.Str.getLength(str) > 15){
			return LENGTH_TYPE_ERROR;
		}
		//判格式
		if(!Utils.Str.timeReg(str)){
			return TIME_TYPE_ERROR;
		}
		return Integer.parseInt(str);
	}
	/**
	 * 判断小时数和分钟数是否正确
	 * @param str
	 * @param rowNo
	 * @param des
	 * @param defaultValue
	 * @return
	 */
	/*protected String convertToTime(String str){
		//判空
		if(StringUtils.isBlank(str)){
			return "00:00";
		}
		//判格式
		if(!Utils.Str.dateTimeReg(str)){
			return TIME_TYPE_ERROR;
		}
		
		return convertToDate(str, null);
	}*/
	
	/**
	 * String转BigDecimal
	 * @param str
	 * @param rowNo
	 * @param des
	 * @param defaultValue
	 * @return
	 */
	protected BigDecimal convertToBigDecimal(String str){
	
		try {
			return new BigDecimal(str);
		} catch (Exception e) {
			return BIGDECIMAL_TYPE_ERROR;
		}
	}
	
	
	/**
	 * 配置属性
	 * @param index
	 * @param description
	 * @param attrType
	 * @return
	 */
	/*protected AttrBean configureAttr(int index, String javaName, String dbName,
			String description, AttrType javaType){
		
		AttrBean attrBean = new AttrBean(index, javaName, dbName, description, javaType);
		config.put(index, attrBean);
		return attrBean;
		
	}*/
	
	
	/*@Override
	public List<K> parseExcel(MultipartFile file) throws ExcelImportException {
		// 获取工作簿
		Workbook workbook = getWorkbook(file);
		// 对应工作表
		Iterator<Sheet> iter = workbook.sheetIterator();
		// 循环工作表
		while (iter.hasNext()) {
			Sheet sheet = iter.next();
			// 循环行
			for (int i = 2; i < sheet.getLastRowNum(); i++) {
				
				Row row = sheet.getRow(i);
				
				try {
					// 实例化实体类
					K bean = entityClass.newInstance();
					
					for (Entry<Integer,AbstractExcelImporter<K>.AttrBean> entry : config.entrySet()) {
						
						Cell cell = row.getCell(entry.getKey(), MissingCellPolicy.RETURN_BLANK_AS_NULL);
						
						AttrBean attrBean = entry.getValue();
						
						attrBean.setValue(getStringValue(cell));
						
						BeanUtils.setProperty(bean, attrBean.getJavaName(), getStringValue(cell));
					}
					entityList.add(bean);
					
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
			}
		}
		return entityList;
	}*/
	
	@Override
	public Map<Integer, List<K>> parseExcel(MultipartFile file) throws ExcelImportException {
		// key:工作表index value:对应数据
		Map<Integer, List<Map<Integer, String>>> resultMap = new TreeMap<Integer, List<Map<Integer,String>>>();
		// 获取工作簿
		Workbook workbook = getWorkbook(file);
		// 对应工作表
		Iterator<Sheet> iter = workbook.sheetIterator();
		int sheetIndex = 0;
		boolean flag = false;
		// 循环工作表
		while (iter.hasNext()) {
			// 实体类集合
			List<Map<Integer, String>> beanList = new ArrayList<Map<Integer, String>>();
			Sheet sheet = iter.next();
			String sheetName = sheet.getSheetName();
			// 循环行
			if(null != this.getParam(STARTINDEX_NAME)){
				START_INDEX = (Integer) this.getParam(STARTINDEX_NAME);
			}
			for (int i = START_INDEX; i <= sheet.getLastRowNum(); i++) {
				Map<Integer, String> bean = new HashMap<Integer, String>();
				flag = false;
				Row row = sheet.getRow(i);
				if(row != null){
					// 循环单元格
					for (int j = 0; j <= row.getLastCellNum(); j++) {
						String cell = getStringValue(row.getCell(j, MissingCellPolicy.RETURN_BLANK_AS_NULL));
						if(StringUtils.isNotBlank(cell)){
							flag = true;
						}
						// key:列号	value:对应值
						bean.put(j, cell);
					}
					
					// key = -1 为 sheetName
					if(flag){
						bean.put(-1, sheetName);
						beanList.add(bean);
					}
				}
			}
			resultMap.put(sheetIndex, beanList);
			sheetIndex++;
		}
		// map集合转化为相应的实体类集合
		return convertBean(resultMap);
	}
	
	/**
	 * map集合转化为相应的实体类集合
	 * @param mapList
	 * @return
	 * @throws ExcelImportException 
	 */
	public abstract Map<Integer, List<K>> convertBean(Map<Integer, List<Map<Integer, String>>> mapList) throws ExcelImportException;

	/**
	 * 设置参数
	 * @param key
	 * @param value
	 */
	@Override
	public void setParam(String key, Object value){
		String userId = ThreadVarUtil.getUser().getId();
		Map<String, Object> userParamMap = paramMap.get(userId);
		if(userParamMap == null){
			userParamMap = new HashMap<String, Object>();
		}
		userParamMap.put(key, value);
		paramMap.put(userId, userParamMap);
	}
	
	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public Object getParam(String key){
		String userId = ThreadVarUtil.getUser().getId();
		Map<String, Object> userParamMap = paramMap.get(userId);
		if(userParamMap != null){
			return userParamMap.get(key);
		}
		return null;
	}





	public class AttrBean{  
		
		/** 序号 */
		private int index;
		
		/** java名称 */
		private String javaName;
		
		/** 数据库名称 */
		private String dbName;
		
		/** 描述 */
		private String description;
		
		/** 类型 */
		private AttrType javaType;
		
		/** 值 */
		private Object value;
		
		/** 能否为空 */
		private Boolean notNull;
		
		/** 最大长度 */
		private Integer maxLength;
		
		/** 正则表达式 */
		private String reg;
		
		/** 允许值 */
		private int[] allowValue;

		public AttrBean(int index, String javaName, String dbName,
				String description, AttrType javaType) {
			super();
			this.index = index;
			this.javaName = javaName;
			this.dbName = dbName;
			this.description = description;
			this.javaType = javaType;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getJavaName() {
			return javaName;
		}

		public void setJavaName(String javaName) {
			this.javaName = javaName;
		}

		public String getDbName() {
			return dbName;
		}

		public void setDbName(String dbName) {
			this.dbName = dbName;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public AttrType getJavaType() {
			return javaType;
		}

		public void setJavaType(AttrType javaType) {
			this.javaType = javaType;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public Boolean getNotNull() {
			return notNull;
		}

		public void setNotNull(Boolean notNull) {
			this.notNull = notNull;
		}

		public Integer getMaxLength() {
			return maxLength;
		}

		public void setMaxLength(Integer maxLength) {
			this.maxLength = maxLength;
		}

		public String getReg() {
			return reg;
		}

		public void setReg(String reg) {
			this.reg = reg;
		}

		public int[] getAllowValue() {
			return allowValue;
		}

		public void setAllowValue(int[] allowValue) {
			this.allowValue = allowValue;
		}
		
	}  
	
	
}
