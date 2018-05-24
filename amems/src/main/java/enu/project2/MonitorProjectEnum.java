package enu.project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 监控项枚举
 * @CreateTime 2017年8月30日 下午5:17:37
 * @CreateBy 韩武
 */
public enum MonitorProjectEnum {
	
	  CAL("1_10", "日历", "1D", "")
	  
	, FH("2_10_FH", "飞行小时", "2T", "FH")
	, EH("2_30_EH", "发动机小时", "2T", "EH")
	, APUH("2_20_AH", "APU小时", "2T", "APUH")
	
	, FC("3_10_FC", "飞行循环", "3C", "FC")
	, EC("3_30_EC", "发动机循环", "3C", "EC")
	, APUC("3_20_AC", "APU循环", "3C", "APUC")
	;
	
	private String code;
    private String name;
    private String fl;
    private String unit;

    
    private MonitorProjectEnum(String code, String name, String fl, String unit) {
    	this.code = code;
        this.name = name;
        this.fl = fl;
        this.unit = unit;
    }

    public static String getName(String code) {
        for (MonitorProjectEnum c : MonitorProjectEnum.values()) {
            if (c.getCode().equals(code.toUpperCase())) {
                return c.name;
            }
        }
        return "";
    }
    
    /**
     * @Description 获取单位
     * @CreateTime 2017年12月18日 下午2:48:54
     * @CreateBy 韩武
     * @param code
     * @return
     */
    public static String getUnit(String code) {
        for (MonitorProjectEnum c : MonitorProjectEnum.values()) {
            if (c.getCode().equals(code.toUpperCase())) {
                return c.unit;
            }
        }
        return "";
    }
    
    /**
     * @Description 获取单位（包含日月）
     * @CreateTime 2017年12月18日 下午2:48:54
     * @CreateBy 韩武
     * @param code
     * @return
     */
    public static String getUnit(String code, String value) {
		if("1_10".equals(code) && "11".equals(value)){
			return "D";
		}else if("1_10".equals(code) && "12".equals(value)){
			return "M";
		}else{
			return getUnit(code);
		}
    }
    
    /**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	MonitorProjectEnum[] enums = MonitorProjectEnum.values();
    	
    	for (MonitorProjectEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("code", enumItem.getCode());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFl() {
		return fl;
	}

	public void setFl(String fl) {
		this.fl = fl;
	}

	/**
	 * @Description 是否日历
	 * @CreateTime 2017年8月30日 下午5:30:30
	 * @CreateBy 韩武
	 * @param jklbh
	 * @return
	 */
	public static boolean isCalendar(String jklbh) {
		return jklbh.toUpperCase().equals(CAL.getCode());
	}

	/**
	 * @Description 是否时间类型监控项目
	 * @CreateTime 2017年8月30日 下午5:30:44
	 * @CreateBy 韩武
	 * @param jklbh
	 * @return
	 */
	public static boolean isTime(String jklbh) {
		return jklbh.toUpperCase().equals(FH.getCode())
				|| jklbh.toUpperCase().equals(EH.getCode())
				|| jklbh.toUpperCase().equals(APUH.getCode());
	}

	/**
	 * @Description 是否循环类型监控项目
	 * @CreateTime 2017年8月30日 下午5:31:33
	 * @CreateBy 韩武
	 * @param jklbh
	 * @return
	 */
	public static boolean isLoop(String jklbh) {
		return jklbh.toUpperCase().equals(FC.getCode())
				|| jklbh.toUpperCase().equals(EC.getCode())
				|| jklbh.toUpperCase().equals(APUC.getCode());
	}
	
	/**
	 * 
	 * @Description 是发动机
	 * @CreateTime 2017年10月11日 上午11:46:27
	 * @CreateBy 朱超
	 * @param jklbh
	 * @return
	 */
	public static boolean isEngine(String jklbh) {
		return jklbh.toUpperCase().equals(EH.getCode())
				|| jklbh.toUpperCase().equals(EC.getCode());
	}
	
	/**
	 * 
	 * @Description 是否APU的专属监控项
	 * @CreateTime 2017年10月11日 上午11:46:27
	 * @CreateBy 朱超
	 * @param jklbh
	 * @return
	 */
	public static boolean isApu(String jklbh) {
		return jklbh.toUpperCase().equals(APUH.getCode())
				|| jklbh.toUpperCase().equals(APUC.getCode());
	}
	
    
}