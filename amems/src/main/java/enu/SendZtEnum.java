package enu;

/**
 * @author sunji
 * @description 发送表Send状态枚举
 * @develop date 2016.08.18
 */
public enum SendZtEnum {
	  NOT_READING("未读", 0)
		,READING("已读", 1);
	
	 private String name;
	 private Integer id;

	    
	    private SendZtEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (SendZtEnum c : SendZtEnum.values()) {
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
