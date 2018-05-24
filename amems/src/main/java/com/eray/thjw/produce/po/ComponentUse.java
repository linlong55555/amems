package com.eray.thjw.produce.po;

import com.eray.thjw.po.BaseEntity;

import enu.produce.InstalledPositionEnum;
import enu.project2.MonitorProjectEnum;

/**
 * @Description 部件使用量
 * @CreateTime 2017年10月19日 上午11:13:08
 * @CreateBy 徐勇
 */
public class ComponentUse extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String bjh;//部件号
	
	private String xlh;//序列号
	
	private Integer wz;//位置
	
	private String zjqdid;//装机清单ID
	
	private Integer fxsjFz;//飞行时间-分钟
	
	private Integer fxxh;//飞行循环
	
	private Integer f1SjFz;//1#发时间-分钟
	
	private Integer f1Xh;//1#发循环
	
	private Integer f2SjFz;//2#发时间-分钟
	
	private Integer f2Xh;//2#发循环
	
	private Integer f3SjFz;//3#发时间-分钟
	
	private Integer f3Xh;//3#发循环
	
	private Integer f4SjFz;//4#发时间-分钟
	
	private Integer f4Xh;//4#发循环
	
	private Integer apuSjFz;//apu时间-分钟
	
	private Integer apuXh;//apu循环
	
	private Integer sj;//时间
	
	private Integer xh;//循环

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}

	public String getZjqdid() {
		return zjqdid;
	}

	public void setZjqdid(String zjqdid) {
		this.zjqdid = zjqdid;
	}

	public Integer getFxsjFz() {
		return fxsjFz;
	}

	public void setFxsjFz(Integer fxsjFz) {
		this.fxsjFz = fxsjFz;
	}

	public Integer getFxxh() {
		return fxxh;
	}

	public void setFxxh(Integer fxxh) {
		this.fxxh = fxxh;
	}

	public Integer getF1SjFz() {
		return f1SjFz;
	}

	public void setF1SjFz(Integer f1SjFz) {
		this.f1SjFz = f1SjFz;
	}

	public Integer getF1Xh() {
		return f1Xh;
	}

	public void setF1Xh(Integer f1Xh) {
		this.f1Xh = f1Xh;
	}

	public Integer getF2SjFz() {
		return f2SjFz;
	}

	public void setF2SjFz(Integer f2SjFz) {
		this.f2SjFz = f2SjFz;
	}

	public Integer getF2Xh() {
		return f2Xh;
	}

	public void setF2Xh(Integer f2Xh) {
		this.f2Xh = f2Xh;
	}

	public Integer getF3SjFz() {
		return f3SjFz;
	}

	public void setF3SjFz(Integer f3SjFz) {
		this.f3SjFz = f3SjFz;
	}

	public Integer getF3Xh() {
		return f3Xh;
	}

	public void setF3Xh(Integer f3Xh) {
		this.f3Xh = f3Xh;
	}

	public Integer getF4SjFz() {
		return f4SjFz;
	}

	public void setF4SjFz(Integer f4SjFz) {
		this.f4SjFz = f4SjFz;
	}

	public Integer getF4Xh() {
		return f4Xh;
	}

	public void setF4Xh(Integer f4Xh) {
		this.f4Xh = f4Xh;
	}

	public Integer getApuSjFz() {
		return apuSjFz;
	}

	public void setApuSjFz(Integer apuSjFz) {
		this.apuSjFz = apuSjFz;
	}

	public Integer getApuXh() {
		return apuXh;
	}

	public void setApuXh(Integer apuXh) {
		this.apuXh = apuXh;
	}
	
	public Integer getWz() {
		return wz;
	}

	public void setWz(Integer wz) {
		this.wz = wz;
	}

	/**
	 * @Description 更新监控项及部件安装位置获取使用累计使用值
	 * @CreateTime 2017年10月20日 下午4:29:20
	 * @CreateBy 徐勇
	 * @param jklbh 监控项编辑
	 * @param wz 安装位置
	 * @return
	 */
	public Integer getByJklbh(String jklbh, Integer wz){
		if(MonitorProjectEnum.FH.getCode().equals(jklbh)){
			return fxsjFz;
		}else if(MonitorProjectEnum.FC.getCode().equals(jklbh)){
			return fxxh;
		}else if(MonitorProjectEnum.EH.getCode().equals(jklbh)){
			if(wz == InstalledPositionEnum.ENG1.getId()){
				return f1SjFz;
			}else if(wz == InstalledPositionEnum.ENG2.getId()){
				return f2SjFz;
			}else if(wz == InstalledPositionEnum.ENG3.getId()){
				return f3SjFz;
			}else if(wz == InstalledPositionEnum.ENG4.getId()){
				return f4SjFz;
			}
		}else if(MonitorProjectEnum.EC.getCode().equals(jklbh)){
			if(wz == InstalledPositionEnum.ENG1.getId()){
				return f1Xh;
			}else if(wz == InstalledPositionEnum.ENG2.getId()){
				return f2Xh;
			}else if(wz == InstalledPositionEnum.ENG3.getId()){
				return f3Xh;
			}else if(wz == InstalledPositionEnum.ENG4.getId()){
				return f4Xh;
			}
		}else if(MonitorProjectEnum.APUH.getCode().equals(jklbh)){
			return apuSjFz;
		}else if(MonitorProjectEnum.APUC.getCode().equals(jklbh)){
			return apuXh;
		}
		return 0;
	}

	public Integer getSj() {
		return sj;
	}

	public void setSj(Integer sj) {
		this.sj = sj;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}
	
}