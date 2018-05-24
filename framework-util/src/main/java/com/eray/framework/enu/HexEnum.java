package com.eray.framework.enu;

/**
 * @Description 进制枚举
 * @CreateTime 2018-1-5 下午3:41:34
 * @CreateBy 刘兵
 */
public enum HexEnum {
	
	D("十进制")
	, H("十六进制")
	;
     
    private String name;
    
    private HexEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}