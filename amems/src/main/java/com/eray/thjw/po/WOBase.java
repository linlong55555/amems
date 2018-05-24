package com.eray.thjw.po;

import java.util.Date;
import java.util.List;
/**
 * 工单基础表实体  b_g_006   看做增加工单的实体类
 * @author Administrator
 *
 */
public class WOBase extends BizEntity {
	
   private List<OrderSource> orderSourceList;             // 指令来源数据
   
   private List<Nonroutine> nonroutine;                                 //非例行工单实体
   
   private  List<NonWOCard> nonwocardList;                                 //非例行工单   相关工卡实体
	   
   private WOActionObj wOActionObj;                           	    //执行对象数据
   
   private List<WOAirMaterial> woAirMaterial;                           	//航材工具信息
   
   private List<WOJobContent> woJobContent;                           	//工单工作内容
   
   private List<WOJobEnclosure> woJobenclosure;                           	   //附件内容实体
    
    private String id;                                //uuid

    private String gdbh;                         //工单编号
    
    private String zddwid;                    //制单单位id 

    private String zdrid;                          //制单人id

    private Date zdsj;                             //制单时间

    private Integer dyzt;                            //打印状态
      
    private Integer gdlx;                         //工单类型 
    
    private  String gdsbh;                    //前台查询过的工单编号
    
    private List<String > gdbhs ;       //工单编号数组     
    
    private String zy;
    
    private String bz;

	public List<OrderSource> getOrderSourceList() {
		return orderSourceList;
	}

	public void setOrderSourceList(List<OrderSource> orderSourceList) {
		this.orderSourceList = orderSourceList;
	}

	public List<Nonroutine> getNonroutine() {
		return nonroutine;
	}

	public void setNonroutine(List<Nonroutine> nonroutine) {
		this.nonroutine = nonroutine;
	}

	public List<NonWOCard> getNonwocardList() {
		return nonwocardList;
	}

	public void setNonwocardList(List<NonWOCard> nonwocardList) {
		this.nonwocardList = nonwocardList;
	}

	public WOActionObj getwOActionObj() {
		return wOActionObj;
	}

	public void setwOActionObj(WOActionObj wOActionObj) {
		this.wOActionObj = wOActionObj;
	}

	public List<WOAirMaterial> getWoAirMaterial() {
		return woAirMaterial;
	}

	public void setWoAirMaterial(List<WOAirMaterial> woAirMaterial) {
		this.woAirMaterial = woAirMaterial;
	}

	public List<WOJobContent> getWoJobContent() {
		return woJobContent;
	}

	public void setWoJobContent(List<WOJobContent> woJobContent) {
		this.woJobContent = woJobContent;
	}

	public List<WOJobEnclosure> getWoJobenclosure() {
		return woJobenclosure;
	}

	public void setWoJobenclosure(List<WOJobEnclosure> woJobenclosure) {
		this.woJobenclosure = woJobenclosure;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGdbh() {
		return gdbh;
	}

	public void setGdbh(String gdbh) {
		this.gdbh = gdbh;
	}

	public String getZddwid() {
		return zddwid;
	}

	public void setZddwid(String zddwid) {
		this.zddwid = zddwid;
	}

	public String getZdrid() {
		return zdrid;
	}

	public void setZdrid(String zdrid) {
		this.zdrid = zdrid;
	}

	public Date getZdsj() {
		return zdsj;
	}

	public void setZdsj(Date zdsj) {
		this.zdsj = zdsj;
	}


	public Integer getDyzt() {
		return dyzt;
	}

	public void setDyzt(Integer dyzt) {
		this.dyzt = dyzt;
	}

	public Integer getGdlx() {
		return gdlx;
	}

	public void setGdlx(Integer gdlx) {
		this.gdlx = gdlx;
	}

	public String getGdsbh() {
		return gdsbh;
	}

	public void setGdsbh(String gdsbh) {
		this.gdsbh = gdsbh;
	}

	public List<String> getGdbhs() {
		return gdbhs;
	}

	public void setGdbhs(List<String> gdbhs) {
		this.gdbhs = gdbhs;
	}

	public String getZy() {
		return zy;
	}

	public void setZy(String zy) {
		this.zy = zy;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Override
	public String toString() {
		return "WOBase [orderSourceList=" + orderSourceList + ", nonroutine="
				+ nonroutine + ", nonwocardList=" + nonwocardList
				+ ", wOActionObj=" + wOActionObj + ", woAirMaterial="
				+ woAirMaterial + ", woJobContent=" + woJobContent
				+ ", woJobenclosure=" + woJobenclosure + ", id=" + id
				+ ", gdbh=" + gdbh + ", zddwid=" + zddwid + ", zdrid=" + zdrid
				+ ", zdsj=" + zdsj + ", dyzt="
				+ dyzt + ", gdlx=" + gdlx + ", gdsbh=" + gdsbh + ", gdbhs="
				+ gdbhs + ", zy=" + zy + ", bz=" + bz + "]";
	}

    	
}