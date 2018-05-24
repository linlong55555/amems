package enu;

/**
 * @author liub
 * @description 最新标识枚举
 * @develop date 2016.08.18
 */
public enum LatestLogoEnum {
	
	INITIAL("初始", 0)
	, LATEST_VERSION("最新版本", 1)
	, OLD_VERSION("老版本", 2)
	 
	;
     
    private String name;
    private Integer id;

    
    private LatestLogoEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (LatestLogoEnum c : LatestLogoEnum.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    
}