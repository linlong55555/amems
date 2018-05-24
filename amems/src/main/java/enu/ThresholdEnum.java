package enu;

/**
 * @author liub
 * @description 状态枚举
 * @develop date 2016.08.18
 */
public enum ThresholdEnum {
	JSWJ("JSWJ"), // 技术文件
	HCSM("HCSM"), // 货架寿命
	TGZL("TGZL"), // 通告指令
	GDXD("GDXD"), // 修订通知书
	GDGS("GDGS"), // 工单工时
	SCJK("SCJK"), // 生产监控
	GZBLD("GZBLD"), // 故障保留单
	SQZZ("SQZZ"), // 维修授权资质/执照
	PXTX("PXTX"), // 培训提醒
	TDYQ("TDYQ"), // 航次提定要求
	GJJK("GJJK"), // 工具监控
	DTZZ("DTZZ"), // 飞机三证
	GYSYXQ("GYSYXQ"), // 供应商有效期
	ZWZLSCQX("ZWZLSCQX"), // 自我质量审查期限预警
	RYKCPX("RYKCPX"),//维修人员培训跟踪
	GWDQJK("GWDQJK"),//岗位监控
	YXX_YPJH_YXQ("YXX_YPJH_YXQ"), //有效性设置-预排计划 :有效期    天数-预警级别1：数值
	YXX_YPJH_MAX("YXX_YPJH_MAX"), //有效性设置-预排计划 :最大预排范围    天数-预警级别1：数值 、天数-预警级别2：单位（11天、12月、13年）
	JXDQJK("JXDQJK"), // 机型授权资质/执照
	ZLWTFK("ZLWTFK");//问题整改措施

	private String name;

	private ThresholdEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}