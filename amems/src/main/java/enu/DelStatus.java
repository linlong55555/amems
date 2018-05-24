package enu;

/**
 * @author liub
 * @description 删除状态枚举,用来记录数据是否删除
 * @develop date 2016.09.14
 */
public enum DelStatus {
	
	TAKE_EFFECT("有效", 1)
	, LOSE_EFFECT("无效", 0)
	 
	;
     
    private String name;
    private Integer id;

    
    private DelStatus(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (DelStatus c : DelStatus.values()) {
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