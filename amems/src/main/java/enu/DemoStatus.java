package enu;

/**
 * 状态枚举
 * @author zhuchao
 *
 */
public enum DemoStatus {
	
	TAKE_EFFECT("生效", 1)
	, LOSE_EFFECT("失效", 0)
	 
	;
     
    private String name;
    private Integer id;

    
    private DemoStatus(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (DemoStatus c : DemoStatus.values()) {
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