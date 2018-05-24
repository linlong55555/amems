package enu.common;

/**
 * 
 * @Description 版本标识枚举
 * @CreateTime 2017-8-12 上午11:26:17
 * @CreateBy 孙霁
 */
public enum RevMarkEnum {
	
	INITIAL("初始", 1)
	, LATEST_VERSION("最新版本",2)
	, OLD_VERSION("老版本", 3)
	 
	;
     
    private String name;
    private Integer id;

    
    private RevMarkEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (RevMarkEnum c : RevMarkEnum.values()) {
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