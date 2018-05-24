package enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuchao
 * @description 日志操作
 *  
 */
public enum LogOperationEnum {
	 SAVE_WO(0, "创建"),
	 SUBMIT_WO(1, "提交"),
	YISHENHE_WO(2, "已审核"),
	YIPIZHUN_WO(3, "已批准"),
	SHOUTDOWN_WO(4, "中止（关闭）"),
	SHENHEBOHUI_WO(5, "审核驳回"),
	SHENPIBOHUI_WO(6, "批准驳回"),
	ZUOFEI(8, "作废"),
	GUANBI(9, "指定结束"),
	WANCHEN(10, "完成"),
	REVOKE(11, "撤销"),
	EDIT(12, "修改"),
	REVISION(13, "改版"),
	COME_INTO_EFFECT(14, "已生效"),
	SUBMIT_PRODUCTION_CONFIRM(15, "提交生产确认"),
	BORROW_CONFIRM(16, "借出确认"),
	RETURNED(17, "归还"),
	IMPORT(18, "导入"),
	PINGGU(19, "已评估"),
	UPDATE_SAVE(20, "修改-已新增了文件"),
	UPDATE_UPDATE(21, "修改-已修改了文件"),
	UPDATE_DELETE(22, "修改-已删除了文件"),
	WANGONG(23, "完工关闭"),
	AUDIT(24, "工单工时确认"),
	Close(25, "关闭"),
	DELETE(26, "删除"),
	INVALID(27, "失效"),
	ENABLED(28, "启用"),
	WRITEOFF(29, "注销"),
	FEEDBACK(30,"完工反馈"),
	ISSUED(31,"下发"),
	REVISE(32,"修订"),
	RELATIONCHANGE(33,"取代关系变更");
	;
	
	private Integer id;
    private String name;
    
    private LogOperationEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (LogOperationEnum c : LogOperationEnum.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return "";
    }
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	 /**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	LogOperationEnum[] enums = LogOperationEnum.values();
    	
    	for (LogOperationEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
}
