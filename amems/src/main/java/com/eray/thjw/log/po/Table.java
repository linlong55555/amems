package com.eray.thjw.log.po;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.po.BaseEntity;

import enu.TableEnum;
import enu.TableType;
/**
 * 日志表定义
 * @author zhuchao
 *
 */

@SuppressWarnings("serial")
public class Table extends BaseEntity{
	
	private static final String rec = "_rec";
	
	/**
	 * 标题
	 */
	private String title;
	
	private String etitle;
	
	
	/**
	 * 字段列表
	 */
	private List<Field> fields;
	/**
	 * 表名
	 */
	private TableEnum table;
	
	/**
	 * 日志表类型
	 */
	private TableType type;
	
	/**
	 * 查询日志总数
	 */
	private String countSqlId;
	
	/**
	 * 查询日志列表
	 */
	private String listSqlId;
	
	/**
	 * 查询日志差异
	 */
	private String diffSqlId;
	
	
	private String id;
	
	private List<Map<String, Object>> rows;
	
	private int total;
	
	private LogAccessRule config;
	
	private String style;
	
	public String getCountSqlId() {
		return  countSqlId ; 
	}

	public void setCountSqlId(String countSqlId) {
		this.countSqlId = countSqlId;
	}

	public String getListSqlId() {
		return listSqlId ;
	}

	public void setListSqlId(String listSqlId) {
		this.listSqlId = listSqlId;
	}

	private String tableName;
	
	private List<String> fieldInList;
	
	private String alias;
	
	private String sql;
	
	/**
	 * 存放数据集合
	 */
	private List<Map<String, Object>> data;
	
	public Table() {
		 
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public TableEnum getTable() {
		return table;
	}

	public void setTable(TableEnum table) {
		this.table = table;
		this.setTitle(table.getName());
		this.setTableName(table.toString().concat(rec));
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public List<Field> getFields() {
		if(fields!=null){
			Collections.sort(fields, new Comparator<Field>() {
				@Override
				public int compare(Field o1, Field o2) {
					return o1.getOrder().compareTo(o2.getOrder());
				}
			});
		}
		
		return fields;
	}
	
	public List<Field> getFields4Enum() {
		List<Field> result = new ArrayList<Field>();
		if(fields!=null){
			for (Field field : fields) {
				if (StringUtils.isNotBlank(field.getEnumClass())) {
					result.add(field);
				}
			}
		}
		return result;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
		for (Field field : this.fields) {
			if (field.getShowInList()!=null && field.getShowInList()) {
				if (fieldInList==null) {
					this.setFieldInList(new ArrayList<String>());
				}
				fieldInList.add(field.getField());
			}
		}
	}

	public TableType getType() {
		return type;
	}

	public void setType(TableType type) {
		this.type = type;
	}

	public List<String> getFieldInList() {
		return fieldInList;
	}

	public void setFieldInList(List<String> fieldInList) {
		this.fieldInList = fieldInList;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	public String getDiffSqlId() {
		return diffSqlId;
	}

	public void setDiffSqlId(String diffSqlId) {
		this.diffSqlId = diffSqlId;
	}

	public String getEtitle() {
		return etitle;
	}

	public void setEtitle(String etitle) {
		this.etitle = etitle;
	}

	public LogAccessRule getConfig() {
		return config;
	}

	public void setConfig(LogAccessRule config) {
		this.config = config;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
}