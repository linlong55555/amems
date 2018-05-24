package enu.project2;


/**
 * 
 * @Description 技术文件评估单系统枚举
 * @CreateTime 2017年8月17日 下午10:38:25
 * @CreateBy 林龙
 */
public enum TechnicalSysEnum {
		  A01("A01", "A01")
		, A02("A02", "A02")

		;
		  private String code;
		    private String name;

		    private TechnicalSysEnum(String code, String name) {
		    	this.code = code;
		        this.name = name;
		    }

		    public static String getName(String code) {
		        for (TechnicalSysEnum c : TechnicalSysEnum.values()) {
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
