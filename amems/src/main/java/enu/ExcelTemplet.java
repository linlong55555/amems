package enu;


public enum ExcelTemplet {
	
	/** 装机清单 */
	LOADINGLIST(1, "008_ZJQD.xlsx", "装机清单导入模板.xlsx"),
	ATA(2, "001_ATA.xlsx", "ATA导入模版.xlsx"),
	SUPPLIER(3, "004_GYSZSJ.xlsx", "供应商主数据导入模板.xlsx"),
	STORAGE(4, "005_HWXX.xlsx","货位导入模版.xlsx"),
	AIRCRAFT(5, "003_FJXX.xlsx", "飞机信息导入模版.xlsx"),
	PERSONNEL(6, "007_RYDA.xlsx", "人员档案导入模板.xlsx"),
	MATERIAL(7, "002_BJZSJ.xlsx", "部件主数据导入模板.xlsx"),
	STOCK(8, "006_KCXX.xlsx", "库存导入模板.xlsx"),
	CONTACTPERSON(9, "009_LXR.xlsx", "联系人导入模板.xlsx"),
	AERIALMATERIALFIRM(10, "009_LXR.xlsx", "航材供应商导入模板.xlsx"),
	BJCZJL(11, "011_BJCZJL.xlsx", "拆换件记录导入模板.xlsx"),
	FLIGHTSHEET(12, "012_FXLL.xlsx", "飞行履历导入模板.xlsx"),
	MAINTENANCE(13,"013_WXXMDL.xlsx","维修项目大类导入模板.xlsx"),
	QY(14, "014_QY.xlsx", "区域导入模板.xlsx"),
	FJZW(15, "015_FJZW.xlsx", "飞机站位导入模板.xlsx"),
	GB(16, "016_GB.xlsx", "盖板导入模板.xlsx"),
	YHXX(17, "017_YHXX.xlsx", "用户信息导入模板.xlsx"),
	GW(18, "018_GW.xlsx", "岗位导入模板.xlsx"),
	KCXX(19, "019_KCXX.xlsx", "课程信息导入模板.xlsx"),
	GKXX(20, "020_GKXX.xlsx", "工卡信息导入模板.xlsx"),
	WXXMQD(21, "010_WXXMQD.xlsx", "维修项目清单导入模板.xlsx"),
	PXJH(22, "021_PXJH.xlsx", "培训计划导入模板.xlsx"),
	PXCJ(23, "022_PXCJ.xlsx", "培训成绩模板.xlsx")
	;
	
	
	private Integer id;
    private String fileName;
    private String displayName;
    
    private ExcelTemplet(Integer id, String fileName, String displayName) {
    	this.id = id;
        this.fileName = fileName;
        this.displayName = displayName;
    }
    
    public static ExcelTemplet findByIndex(Integer id) {
        for (ExcelTemplet c : ExcelTemplet.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c;
            }
        }
        return null;
    }
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
