package enu;

/**
 * @author liub
 * @description 数据库操作枚举
 * @develop date 2016.08.18
 */
public enum OptionEnum {
	
	ADD_SUCCESS("添加成功", 1)
	, ADD_FAIL("添加失败", 0)
	, UPDATE_SUCCESS("修改成功", 1)
	, UPDATE_FAIL("修改失败", 0)
	 
	;
     
    private String name;
    private Integer id;

    
    private OptionEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (OptionEnum c : OptionEnum.values()) {
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