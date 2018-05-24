package enu;

/**
 * 时间分隔符
 * @author hanwu
 *
 */
public enum TimeSeparatorEnum {
	
	COLON(":", ":") , 
	DOT(".", "\\.")
	; 
     
    private String describe;
    
    private String value;
    
    private TimeSeparatorEnum(String describe,String value) {
        this.describe = describe;
        this.value = value;
    }

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}