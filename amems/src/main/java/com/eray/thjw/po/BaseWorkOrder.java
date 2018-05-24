package com.eray.thjw.po;
/**
 * @author meizhiliang
 * @disable 基础工单实体 （b_g_006）
 */
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class BaseWorkOrder extends BizEntity{
   
	private String id;

    private String gdbh;

    private String zddwid;

    private String zdrid;

    private Date zdsj;

    private String dprtcode;

    private Integer dyzt;
    

    //  基础工单与下面的实体是一对多的关系
    
    private List<OrderSource> orderSourceList;             // 指令来源数据

	private List<WOAirMaterial> woAirMaterial;                           	//航材工具信息
    
    private List<WOJobContent> woJobContent;                           	//工单工作内容
    
    private List<WOJobEnclosure> woJobenclosure;                       //工单附件
    
    private WOActionObj  wOActionObj;                           	           //工单执行对象
    
    private  List<NonWOCard> nonwocardList;                                 //工单相关工单工卡
    
   
    
    public List<WOAirMaterial> getWoAirMaterial() {
		return woAirMaterial;
	}
    public List<OrderSource> getOrderSourceList() {
    	return orderSourceList;
    }
    
    public void setOrderSourceList(List<OrderSource> orderSourceList) {
    	this.orderSourceList = orderSourceList;
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

	public WOActionObj getwOActionObj() {
		return wOActionObj;
	}

	public void setwOActionObj(WOActionObj wOActionObj) {
		this.wOActionObj = wOActionObj;
	}

	public List<NonWOCard> getNonwocardList() {
		return nonwocardList;
	}

	public void setNonwocardList(List<NonWOCard> nonwocardList) {
		this.nonwocardList = nonwocardList;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGdbh() {
        return gdbh;
    }

    public void setGdbh(String gdbh) {
        this.gdbh = gdbh == null ? null : gdbh.trim();
    }

    public String getZddwid() {
        return zddwid;
    }

    public void setZddwid(String zddwid) {
        this.zddwid = zddwid == null ? null : zddwid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Integer getDyzt() {
        return dyzt;
    }

    public void setDyzt(Integer dyzt) {
        this.dyzt = dyzt;
    }

	@Override
	public String toString() {
		return "BaseWorkOrder [id=" + id + ", gdbh=" + gdbh + ", zddwid="
				+ zddwid + ", zdrid=" + zdrid + ", zdsj=" + zdsj
				+ ", dprtcode=" + dprtcode + ", dyzt=" + dyzt
				+ ", woAirMaterial=" + woAirMaterial + ", woJobContent="
				+ woJobContent + ", woJobenclosure=" + woJobenclosure
				+ ", wOActionObj=" + wOActionObj + ", nonwocardList="
				+ nonwocardList + "]";
	}
}