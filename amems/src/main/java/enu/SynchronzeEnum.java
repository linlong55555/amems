package enu;

/**
 * 同步状态
 * @author hanwu
 *
 */
public enum SynchronzeEnum {
	
	
	NOT_NEED("无需同步", 0) , 
	TO_DO("修改待同步", 1)
	; 
	
     
    private String name;
    private Integer code;

    
    private SynchronzeEnum(String name, Integer code) {
        this.name = name;
        this.setCode(code);
    }

    public static String getName(Integer code) {
        for (SynchronzeEnum c : SynchronzeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

    
}