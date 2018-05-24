package enu.common;

/**
 * @Description 文档模块枚举
 * @CreateTime 2017-9-5 下午3:11:22
 * @CreateBy 刘兵
 */
public enum DocumentEnum {
	
	FD_ZLWD("FD_ZLWD", "质量文档")
	,FD_SCWD("FD_SCWD", "手册文档")
	,FD_GCWD("FD_GCWD", "工程文档")
	,WDGL_SC("WDGL_SC", "生产文档")
	,WDGL_PX("WDGL_PX", "培训文档")
	,WDGL_HC("WDGL_HC", "航材文档")
	;
     
	private String code;
    private String name;

    private DocumentEnum(String code, String name) {
    	this.code = code;
        this.name = name;
    }

    public static String getName(String code) {
        for (DocumentEnum c : DocumentEnum.values()) {
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