package com.eray.thjw.excelimport;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.eray.thjw.exception.ExcelImportException;

public interface BaseExcelImporter<K> {
	
	/**
	 * 解析excel
	 * @param file
	 * @return
	 */
	Map<Integer, List<K>> parseExcel(MultipartFile file) throws ExcelImportException;
	
	/**
	 * 验证数据
	 * @param datas
	 * @return
	 */
	void validateParam(Map<Integer, List<K>> datas) throws ExcelImportException ;
	
	/**
	 * 拼接错误信息
	 * @param errorMessages
	 * @return
	 */
	String assemleErrorMessage(List<String> errorMessages) throws ExcelImportException ;
	
	/**
	 * 写入到数据库
	 * @param datas
	 * @return
	 */
	int writeToDB(Map<Integer, List<K>> datas) throws ExcelImportException ;
	
	/**
	 * 开始excel导入
	 * @param file
	 */
	void doImport(MultipartFile file) throws ExcelImportException ;
	
	/**
	 * 检查错误
	 */
	void checkError() throws ExcelImportException ;
	
	/**
	 * 设置参数
	 * @param key
	 * @param value
	 */
	void setParam(String key, Object value);
	
}
