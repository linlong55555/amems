package com.eray.thjw.aerialmaterial.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * b_h_00501 留言-接收人表
 * @author xu.yong
 *
 */
public class MessageRecipients extends BizEntity{
    private String id;

    private String mainid;

    private String jsbmid;

    private String jsrid;

    private Date jssj;

    private Integer zt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public String getJsbmid() {
        return jsbmid;
    }

    public void setJsbmid(String jsbmid) {
        this.jsbmid = jsbmid == null ? null : jsbmid.trim();
    }

    public String getJsrid() {
        return jsrid;
    }

    public void setJsrid(String jsrid) {
        this.jsrid = jsrid == null ? null : jsrid.trim();
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }
}