package com.eray.thjw.log.po;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eray.thjw.po.BaseEntity;

import enu.TableEnum;
import enu.TableType;
/**
 * 日志配置
 * @author zhuchao
 *
 */

@SuppressWarnings("serial")
public class LogAccessRule extends BaseEntity{
	/**
	 * 日志代码
	 */
	private String code;
	/**
	 * 日志主表
	 */
    private Table master;
    
    /**
     * 日志从表列表
     */
    private List<Table> slaves;
    
     
    private List<Table> tables;
    
    /**
     * 扩展参数
     */
    private String id;
    
    private String space;
    
    
	public LogAccessRule() {}
    
	 public LogAccessRule(String code, List<Table> tables) {
		super();
		this.setCode(code);
		for (Table table : tables) {
			if (table.getType()==null || table.getType().equals(TableType.MASTER) ) {
				this.setMaster(table);
			}
			else{
				this.addSlave(table);
			}
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Table getMaster() {
		return master;
	}

	public void setMaster(Table master) {
		this.master = master;
	}

	public List<Table> getSlaves() {
		return slaves;
	}

	public void setSlaves(Table[] slaves) {
		this.slaves = Arrays.asList(slaves);
	}
	
	public void addSlave(Table slave) {
		if (this.slaves==null) {
			this.slaves = new ArrayList<Table>();
		}
		this.slaves.add(slave);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		for (Table table : tables) {
			if (table.getType()==null || table.getType().equals(TableType.MASTER) ) {
				this.setMaster(table);
			}
			else{
				this.addSlave(table);
			}
		}
		this.tables = tables;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

}