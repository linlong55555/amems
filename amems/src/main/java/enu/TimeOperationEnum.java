package enu;

/**
 * 时间操作
 * @author hanwu
 *
 */
public enum TimeOperationEnum {
	
	ADD("加") , 
	SUBTRACT("减")
	; 
     
    private String describe;
    
    private TimeOperationEnum(String describe) {
        this.describe = describe;
    }

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

    
}