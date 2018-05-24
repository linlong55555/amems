package enu;

/**
 * 文件类型字典
 * @author zhuchao
 *
 */
public enum FileType {
	
	DEFAULT("DEFAULT", "DEFAULT")
	;
     
    private String name;
    private String code;

    private FileType(String name, String code) {
        this.name = name;
        this.setCode(code);
    }

    public static String getName(String code) {
        for (FileType c : FileType.values()) {
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
 
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    
}