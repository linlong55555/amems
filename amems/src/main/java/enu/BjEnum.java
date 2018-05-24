package enu;

/**
 * @author liub
 * @description 必检枚举
 * @develop date 2016.08.25
 */
public enum BjEnum {
	  YES_BJ("Y", 1)
	, NO_BJ("N", 0)
	;
     
    private String name;
    private Integer id;

    
    private BjEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (BjEnum c : BjEnum.values()) {
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