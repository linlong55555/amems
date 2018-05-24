package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * b_h_005 留言消息
 * @author xu.yong
 *
 */
public class Message extends BizEntity{
    private String id;

    private Integer lx;

    private String djid;

    private String yswjm;

    private Date wbwjm;

    private String nbwjm;

    private Integer yxzt;

    private String dprtcode;
    
    private String jsrnames;
    
    private List<MessageRecipients> messageRecipientsList;
    
    private ReserveMain reserveMain;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getLx() {
        return lx;
    }

    public void setLx(Integer lx) {
        this.lx = lx;
    }

    public String getDjid() {
        return djid;
    }

    public void setDjid(String djid) {
        this.djid = djid == null ? null : djid.trim();
    }

    public String getYswjm() {
        return yswjm;
    }

    public void setYswjm(String yswjm) {
        this.yswjm = yswjm == null ? null : yswjm.trim();
    }

    public Date getWbwjm() {
        return wbwjm;
    }

    public void setWbwjm(Date wbwjm) {
        this.wbwjm = wbwjm;
    }

    public String getNbwjm() {
        return nbwjm;
    }

    public void setNbwjm(String nbwjm) {
        this.nbwjm = nbwjm == null ? null : nbwjm.trim();
    }

    public Integer getYxzt() {
        return yxzt;
    }

    public void setYxzt(Integer yxzt) {
        this.yxzt = yxzt;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

	public List<MessageRecipients> getMessageRecipientsList() {
		return messageRecipientsList;
	}

	public void setMessageRecipientsList(
			List<MessageRecipients> messageRecipientsList) {
		this.messageRecipientsList = messageRecipientsList;
	}

	public String getJsrnames() {
		return jsrnames;
	}

	public void setJsrnames(String jsrnames) {
		this.jsrnames = jsrnames;
	}

	public ReserveMain getReserveMain() {
		return reserveMain;
	}

	public void setReserveMain(ReserveMain reserveMain) {
		this.reserveMain = reserveMain;
	}

}