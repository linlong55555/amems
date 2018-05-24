package com.eray.thjw.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.po.AbstractEntity;
import com.eray.thjw.po.Pagination;
import com.github.pagehelper.Page;

/**
 * 
 * 分页构造工具
 * @author xu.yong
 *
 */
public class PageUtil {
	
	public static final String ITEM = "item";
	public static final String TOTAL = "total";
	public static final String ROWS = "rows";
	public static final String PAGEABLE = "pageable";
	public static final String PAGINATION = "pagination";
	public static final String DICTS = "dicts";
	public static final String EXT_PARAMS = "ext";
	
	public static Map<String, Object> pack4PageHelper(List<?> rowsList, Pagination pagination){
		Map<String, Object> resultMap = new HashMap<String, Object>(6);
		resultMap.put(TOTAL, ((Page) rowsList).getTotal());
		resultMap.put(ROWS, rowsList);
		resultMap.put(PAGEABLE, pagination);
		resultMap.put(PAGINATION, pagination);
		return resultMap;
	}
	
	/**
	 * 
	 * @param object 总记录数对象
	 * @return
	 */
	public static Map<String, Object> pack(List<?> rowsList){
		Map<String, Object> resultMap = new HashMap<String, Object>(5);
		resultMap.put(ROWS, rowsList);
		return resultMap;
	}
	
	/**
	 * 
	 * @param rowsList 查询结果集
	 * @param id 最近编辑记录的ID
	 * @return
	 */
	public static <T extends AbstractEntity> Map<String, Object> pack(List<T> rowsList, String id){
		if(StringUtils.isNotBlank(id) && rowsList != null && !rowsList.isEmpty()){
			for (int i = 0; i < rowsList.size(); i++) {
				if(id.equals(rowsList.get(i).getId())){
					if(i == 0){
						break;
					}
					T object = rowsList.get(i);
					rowsList.remove(i);
					rowsList.add(0, object);
					break;
				}
			}
		}
		return pack(rowsList);
	}
	
	/**
	 * 
	 * @param total 总记录数
	 * @param rowsList 分页记录
	 * @param pagination 分页对象
	 * @return
	 */
	public static Map<String, Object> pack(int total, List<?> rowsList, Pagination pagination){
		Map<String, Object> resultMap = new HashMap<String, Object>(6);
		resultMap.put(TOTAL, total);
		resultMap.put(ROWS, rowsList);
		resultMap.put(PAGEABLE, pagination);
		resultMap.put(PAGINATION, pagination);
		return resultMap;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param total 总记录数
	 * @param rowsList 分页记录
	 * @param object 最近编辑的记录
	 * @param pagination 分页对象
	 * @return
	 */
	public static <T extends AbstractEntity> Map<String, Object> pack(int total, List<T> rowsList, T object, Pagination pagination){
		if(object != null && rowsList != null && !rowsList.isEmpty()){
			//移除刚编辑的记录
			int i = 0;
			for (i = 0; i < rowsList.size(); i++) {
				if(rowsList.get(i).getId().equals(object.getId())){
					if(i == 0){
						break;
					}
					rowsList.remove(i);
					break;
				}
			}
			if(i != 0){
				//在结果集最前方插入 刚编辑 过的记录
				rowsList.add(0, object);
			}
		}
		return pack(total, rowsList, pagination);
	}
	
	/**
	 * 判断结果集中是否有相关记录，如果有则将该记录移动到第一条，并返回true, 否则返回false
	 * @param rowsList
	 * @param id
	 * @return
	 */
	public static <T extends AbstractEntity> boolean hasRecord(List<T> rowsList, String id){
		if(StringUtils.isBlank(id)){
			return true;
		}
		if(rowsList != null && !rowsList.isEmpty()){
			//移除刚编辑的记录
			int i = 0;
			for (i = 0; i < rowsList.size(); i++) {
				if(id.equals(String.valueOf(rowsList.get(i).getId()))){
					if(i != 0){
						T t = rowsList.get(i);
						rowsList.remove(i);
						rowsList.add(0, t);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断结果集中是否有相关记录，如果有则将该记录移动到第一条，并返回true, 否则返回false
	 * @param rowsList
	 * @param id
	 * @return
	 */
	public static boolean hasRecordMap(List<Map<String, Object>> rowsList, String id){
		if(StringUtils.isBlank(id)){
			return true;
		}
		if(rowsList != null && !rowsList.isEmpty()){
			//移除刚编辑的记录
			int i = 0;
			for (i = 0; i < rowsList.size(); i++) {
				if(id.equals(rowsList.get(i).get("ID"))){
					if(i != 0){
						Map<String, Object> t = rowsList.get(i);
						rowsList.remove(i);
						rowsList.add(0, t);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断结果集中是否有相关记录，如果有则将该记录移动到第一条，并返回true, 否则返回false
	 * @param rowsList
	 * @param idFieldName id字段列名
	 * @param id
	 * @return
	 */
	public static boolean hasRecordMap(List<Map<String, Object>> rowsList, String idFieldName, String id){
		if(StringUtils.isBlank(id)){
			return true;
		}
		if(rowsList != null && !rowsList.isEmpty()){
			//移除刚编辑的记录
			int i = 0;
			for (i = 0; i < rowsList.size(); i++) {
				if(id.equals(rowsList.get(i).get(idFieldName))){
					if(i != 0){
						Map<String, Object> t = rowsList.get(i);
						rowsList.remove(i);
						rowsList.add(0, t);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @Description 判断结果集中是否有相关记录，如果有则将该记录移动到第一条，并返回true, 否则返回false
	 * @CreateTime 2017年9月29日 上午10:29:22
	 * @CreateBy 徐勇
	 * @param rowsList 待处理数据 （list中必须是po）
	 * @param field 比对字段名
	 * @param value 比对字段值
	 * @return
	 */
	public static boolean hasRecord(List rowsList, String field, String value){
		if(StringUtils.isBlank(value)){
			return true;
		}
		
		if(rowsList != null && !rowsList.isEmpty()){
			Field fieldnum = null;
			try{
				fieldnum = rowsList.get(0).getClass().getDeclaredField(field);
				fieldnum.setAccessible(true);
			
				//移除刚编辑的记录
				int i = 0;
				for (i = 0; i < rowsList.size(); i++) {
					
					String strFieldValue = String.valueOf(fieldnum.get(rowsList.get(i)));
					if(value.equals(strFieldValue)){
						if(i != 0){
							Object o = rowsList.get(i);
							rowsList.remove(i);
							rowsList.add(0, o);
						}
						return true;
					}
				}
			}catch (Exception e) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 给返回值添加字典
	 * @param map 返回的Map
	 * @param dictName 字典名称
	 * @param dict 字典 Map|List
	 * @return
	 */
	public static void addDict(Map<String, Object> map, String dictName, Object dict){
		Map<String, Object> dicts = null;
		if(map.containsKey(DICTS)){
			dicts = (Map<String, Object>)map.get(DICTS);
			dicts.put(dictName, dict);
		}else{
			dicts = new HashMap<String, Object>();
			dicts.put(dictName, dict);
		}
		map.put(DICTS, dicts);
	}
	
	public static void addExt(Map<String, Object> map, String key, Object value){
		Map<String, Object> dicts = null;
		if(map.containsKey(EXT_PARAMS)){
			dicts = (Map<String, Object>)map.get(EXT_PARAMS);
			dicts.put(key, value);
		}else{
			dicts = new HashMap<String, Object>();
			dicts.put(key, value);
		}
		map.put(EXT_PARAMS, dicts);
	}
	
	/**
	 * 
	 * @param object 总记录数对象
	 * @return
	 */
	public static Map<String, Object> pack(Object object){
		Map<String, Object> resultMap = new HashMap<String, Object>(5);
		resultMap.put(ITEM, object);
		return resultMap;
	}
	
	
	
}
