package com.eray.thjw.po;

/**
 * @Description d_01801 文件目录-回收站子表
 * @CreateTime 2018年1月26日 上午10:34:01
 * @CreateBy 韩武
 */
public class FileRecycledChildren extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String mkdm;

    private String dprtcode;

    private String manid;

    private String wjid;

    private String wjlx;

    private String fjdid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMkdm() {
        return mkdm;
    }

    public void setMkdm(String mkdm) {
        this.mkdm = mkdm == null ? null : mkdm.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getManid() {
        return manid;
    }

    public void setManid(String manid) {
        this.manid = manid == null ? null : manid.trim();
    }

    public String getWjid() {
        return wjid;
    }

    public void setWjid(String wjid) {
        this.wjid = wjid == null ? null : wjid.trim();
    }

    public String getWjlx() {
        return wjlx;
    }

    public void setWjlx(String wjlx) {
        this.wjlx = wjlx == null ? null : wjlx.trim();
    }

    public String getFjdid() {
        return fjdid;
    }

    public void setFjdid(String fjdid) {
        this.fjdid = fjdid == null ? null : fjdid.trim();
    }
}