package enu;

/**
 * 飞机初始化数据枚举
 * @author hanwu
 *
 */
public enum PlaneInitDataEnum {
	
	INIT_DATE_RQ("系统初始化日期", "init_date_rq"),
	INIT_TIME_JSFX("系统初始化机身飞行时间", "init_time_jsfx"),
	INIT_TIME_SSD("系统初始化搜索灯时间", "init_time_ssd"),
	INIT_TIME_JC("系统初始化绞车时间", "init_time_jc"),
	INIT_LOOP_QLJ("系统初始化起落架循环", "init_loop_qlj"),
	INIT_LOOP_JC("系统初始化绞车循环", "init_loop_jc"),
	INIT_LOOP_WDG("系统初始化外吊挂循环", "init_loop_wdg"),
	INIT_LOOP_SSD("系统初始化搜索灯循环", "init_loop_ssd"),
	INIT_LOOP_L_N1("系统初始化发动机左发N1循环", "init_loop_l_n1"),
	INIT_LOOP_L_N2("系统初始化发动机左发N2循环", "init_loop_l_n2"),
	INIT_LOOP_L_N3("系统初始化发动机左发N3循环", "init_loop_l_n3"),
	INIT_LOOP_L_N4("系统初始化发动机左发N4循环", "init_loop_l_n4"),
	INIT_LOOP_L_N5("系统初始化发动机左发N5循环", "init_loop_l_n5"),
	INIT_LOOP_L_N6("系统初始化发动机左发N6循环", "init_loop_l_n6"),
	INIT_LOOP_R_N1("系统初始化发动机右发N1循环", "init_loop_r_n1"),
	INIT_LOOP_R_N2("系统初始化发动机右发N2循环", "init_loop_r_n2"),
	INIT_LOOP_R_N3("系统初始化发动机右发N3循环", "init_loop_r_n3"),
	INIT_LOOP_R_N4("系统初始化发动机右发N4循环", "init_loop_r_n4"),
	INIT_LOOP_R_N5("系统初始化发动机右发N5循环", "init_loop_r_n5"),
	INIT_LOOP_R_N6("系统初始化发动机右发N6循环", "init_loop_r_n6"),
	INIT_LOOP_TS1("系统初始化TS1", "init_loop_ts1"),
	INIT_LOOP_TS2("系统初始化TS2", "init_loop_ts2")
	;
	
	private String description;
    private String code;

    
    private PlaneInitDataEnum(String description, String code) {
        this.description = description;
        this.code = code;
         
    }

    public static String getDescription(String code) {
        for (PlaneInitDataEnum p : PlaneInitDataEnum.values()) {
            if (p.getCode().equals(code)) {
                return p.getDescription();
            }
        }
        return "";
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
