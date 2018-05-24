package com.eray.thjw.project2.po;

import java.util.List;
import java.util.Map;

import com.eray.thjw.produce.po.Workorder;

/** 
 * @Description EO执行对象.数据库没有表与之对应
 * @CreateTime 2017-10-19 上午10:46:03
 * @CreateBy 雷伟	
 */
public class EOExecutionObj {

	//左侧执行对象
	private String zxdxid; //执行对象ID
	private boolean showYellow; //是否显示黄色（只要EO有效且b_g2_01001 EO-适用性表.确认状态=0未确认，就显示）
	private String exeName; //执行对象标头名称
	private int woNums; //工单数量
	private String qrZt; //确认状态（只要EO有效且b_g2_01001 EO-适用性表.确认状态=0未确认，就显示）
	private String gbZt; //关闭状态
	private List<Workorder> woList; //工单集合
	
	//右侧列表
	private String syx;//适用性
	private String fjzch;//工单飞机注册号
	private String jh;//件号
	private String xlh;//序列号
	private String bjName;//部件名称
	private String wobh; //工单编号
	private String wobt; //工单标题
	private String wozt; //工单主题
	private List<Map<String,String>> jhzxList; //计划执行
	private List<Map<String,String>> sjzxList; //实际执行
	private String woid; //工单ID
	
	public boolean isShowYellow() {
		return showYellow;
	}
	public void setShowYellow(boolean showYellow) {
		this.showYellow = showYellow;
	}
	public String getExeName() {
		return exeName;
	}
	public void setExeName(String exeName) {
		this.exeName = exeName;
	}
	public List<Workorder> getWoList() {
		return woList;
	}
	public void setWoList(List<Workorder> woList) {
		this.woList = woList;
	}
	public String getSyx() {
		return syx;
	}
	public void setSyx(String syx) {
		this.syx = syx;
	}
	public String getFjzch() {
		return fjzch;
	}
	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}
	public String getJh() {
		return jh;
	}
	public void setJh(String jh) {
		this.jh = jh;
	}
	public String getXlh() {
		return xlh;
	}
	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	public String getBjName() {
		return bjName;
	}
	public void setBjName(String bjName) {
		this.bjName = bjName;
	}
	public String getWobh() {
		return wobh;
	}
	public void setWobh(String wobh) {
		this.wobh = wobh;
	}
	public String getWobt() {
		return wobt;
	}
	public void setWobt(String wobt) {
		this.wobt = wobt;
	}
	public String getWozt() {
		return wozt;
	}
	public void setWozt(String wozt) {
		this.wozt = wozt;
	}
	public String getWoid() {
		return woid;
	}
	public void setWoid(String woid) {
		this.woid = woid;
	}
	public String getQrZt() {
		return qrZt;
	}
	public void setQrZt(String qrZt) {
		this.qrZt = qrZt;
	}
	public int getWoNums() {
		return woNums;
	}
	public void setWoNums(int woNums) {
		this.woNums = woNums;
	}
	public List<Map<String, String>> getJhzxList() {
		return jhzxList;
	}
	public void setJhzxList(List<Map<String, String>> jhzxList) {
		this.jhzxList = jhzxList;
	}
	public List<Map<String, String>> getSjzxList() {
		return sjzxList;
	}
	public void setSjzxList(List<Map<String, String>> sjzxList) {
		this.sjzxList = sjzxList;
	}
	public String getZxdxid() {
		return zxdxid;
	}
	public void setZxdxid(String zxdxid) {
		this.zxdxid = zxdxid;
	}
	public String getGbZt() {
		return gbZt;
	}
	public void setGbZt(String gbZt) {
		this.gbZt = gbZt;
	}
}