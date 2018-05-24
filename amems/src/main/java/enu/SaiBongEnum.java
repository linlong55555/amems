package enu;

/**
 * @author liub
 * @description 状态枚举
 * @develop date 2016.08.18
 */
public enum SaiBongEnum {
	/*
	 * 技术文件管理
	 */
	JSPG("JSPG"), //技术评估单号-PG-2016-流水号（0000）
	BLD("BLD"), //保留单号-PG-2016-流水号（0000）
	SQSQD("SQSQD"), //授权单号-PG-2016-流水号（0000）
	JSTB("JSTB"), //技术通告编号-TB-2016-流水号（000）
	JSZL("JSZL"), //技术指令单号-TO-2016-流水号（000）
	XDTZ("XDTZ"), //修订通知单编号-XD-2016-流水号（000）
	XDMEL("XDMEL"), //修订MEL编号-XD-2016-流水号（000）
	XDGK("XDGK"), //修订工卡编号-XD-2016-流水号（000）
	FLX("FLX"), //非例行工单编号（时控件、排故、附加）-NRC-2016-飞机注册号（后四位数字）-流水号（000）---带参数-飞机注册号
	LXGD("LXGD"), //例行工单编号（定检工卡）-RC-2016-飞机注册号（后四位数字）-流水号（000）---带参数-飞机注册号
	GCLZ("GCLZ"), //工程指令编号-EO-2016-流水号（000）
	EOGD("EOGD"), //EO工单编号-工程指令编号-流水号（00）
	DJXM("DJXM"), //定检项目编号-流水号（000）
	WXFA("WXFA"), //维修方案编号-流水号（000）
				//定检工卡编号-手工输入
	QSB("50_ZLSHBGBH"),//质量审核报告编号
	/*
	 * 培训计划编号
	 */
	PXJL("PXJL"), 
	/*
	 * 生产计划管理
	 */
	TSFX("TSFX"), //特殊飞行情况配置编号-流水号（00）
	DJRW("DJRW"), //定检任务单编号-DJ-2016-飞机注册号（后四位数字）-流水号（000）---带参数-飞机注册号
	FXJL("FXJL"), //飞行记录单号-FLB-飞机注册号（后4码：7136）-流水号（5码）---带参数-飞机注册号
	CJJL("CJJL"), //拆解记录单号-DR-飞机注册号（后4码：7136）-流水号（8码）---带参数-飞机注册号
	/*
	 * 航材管理
	 */
	
				//供应商编号-手工输入
	HCTD("HCTD"), //航材提订单编号-TD-2016-流水（5码）
	HTCG("HTCG"), //采购合同流水号-CHT-2016-流水（5码）	
	HCSX("HCSX"), //航材送修单编号-SX-2016-流水（5码）
	HCXJ("HCXJ"), //航材询价单号-XJ-2016-流水（8码）
	SXHT("SXHT"), //送修合同流水号-XHT-2016-流水（5码）
	LYSQ("LYSQ"), //领用申请单单号-LY-2016-流水（5码）
	CGRK("CGRK"), //采购入库单号-RK-201608-流水（3码）
	LYCK("LYCK"), //领用出库单号-CK-201608-流水（3码）
	JRGH("JRGH"), //借入归还出库单号-CK-201608-流水（3码）
	JCCK("JCCK"), //借出出库单号-CK-201608-流水（3码）
	BFCK("BFCK"), //报废出库单号-CK-201608-流水（3码）
	SXRK("SXRK"), //送修入库单号-RK-201608-流水（3码）
	JRRK("JRRK"), //借入入库单号-RK-201608-流水（3码）
	JCGHRK("JCGHRK"), //借出归还入库单号-RK-201608-流水（3码）
	QTRK("QTRK"), //其他入库单号-RK-201608-流水（3码）
	HCYK("HCYK"), //航材移库单号-YK-201608-流水（3码）
	YPKD("YPKD"), //盘盈亏单单号-PD-201608-流水（3码）
	JRSQ("JRSQ"), //借入申请
	HCJY("HCJY"), //航材检验
	SHZL("SHZL"),
	JCBZXJ("JCBZXJ"),//机场保障部巡检记录-JCWX-2016-流水(5码)
	JCBZWX("JCBZWX"),//机场保障部维修记录-JCWX-2016-流水(5码)
	JCBZJY("JCBZJY"),//飞机加油单号-JCJY-2016-流水（5码）
	GJSY("GJSY"),//工具借用
	XHD("XHD"),//销毁单号
	SHD("SHD"),//收货单号
	THD("THD"),//退货单号
	BFD("BFD"),//报废单号
	ZBGD("ZBGD"),//组包生成工单
	ZWZLSC("ZWZLSC"),//自我质量审查单号
	GDNRC("44_145GDNRC"),//145工单-NRC
	GDRTN("45_145GDRTN"),//145工单-RTN
	WTDBH("48_WTDBH"),//问题单编号
	WTBH("49_WTBH"),//问题编号
	SHXMDBH("47_SHXMDBH"),//审核项目单编号
	JCDBH("46_JCDBH"),//审核项目单编号
	
	DDBLD("DDBLD"),//故障保留单编号
	GB135("GB135"),//工单135编号
	GB145("GB145"), //工包145编号
	XMBLD("XMBLD"), //项目保留单编号
	QXBLD("QXBLD"), //缺陷保留单编号
	GD135("GD135"), //工单135编号
	GD145("GD145"), //工单145编号
	
	
	/*
	 * 航材工具管理
	 */
	HC_XQD("1_HC_XQD"), //航材需求单
	HC_SHD("2_HC_SHD"), //航材收货单
	HC_PCH("3_HC_PCH"), //航材批次号
	HC_CKD("4_HC_CKD"), //出库单号
	HC_ZJD("7_HC_ZJD"), //航材质检单
	HC_GRN("8_HC_GRN"), //航材GRN
	
	/*
	 *工程技术
	 */
	GC_SCZL("10_GC_SCZL"), //生产指令
	;
    private String name;

    
    private SaiBongEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




	

	
}