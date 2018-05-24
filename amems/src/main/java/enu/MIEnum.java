package enu;

/**
 * @author liub
 * @description MI枚举
 * @develop date 2016.08.25
 */
public enum MIEnum {
	  YES_MI("Y", 1)
	, NO_MI("N", 0)
	;
     
    private String name;
    private Integer id;

    
    private MIEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (MIEnum c : MIEnum.values()) {
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