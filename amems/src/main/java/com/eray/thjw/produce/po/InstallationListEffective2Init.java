package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description b_s2_00401 装机清单-生效区-部件初始化
 * @CreateTime 2017年9月22日 下午2:27:11
 * @CreateBy 韩武
 */
public class InstallationListEffective2Init extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private String jklbh;

    private String jkflbh;

    private String zssyy;

    private String zjsyl;

    private String cxsyy;

    private String whbmid;

    private String whrid;

    private Date whsj;

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

    public String getJklbh() {
        return jklbh;
    }

    public void setJklbh(String jklbh) {
        this.jklbh = jklbh == null ? null : jklbh.trim();
    }

    public String getJkflbh() {
        return jkflbh;
    }

    public void setJkflbh(String jkflbh) {
        this.jkflbh = jkflbh == null ? null : jkflbh.trim();
    }

    public String getZssyy() {
        return zssyy;
    }

    public void setZssyy(String zssyy) {
        this.zssyy = zssyy == null ? null : zssyy.trim();
    }

    public String getZjsyl() {
        return zjsyl;
    }

    public void setZjsyl(String zjsyl) {
        this.zjsyl = zjsyl == null ? null : zjsyl.trim();
    }

    public String getCxsyy() {
        return cxsyy;
    }

    public void setCxsyy(String cxsyy) {
        this.cxsyy = cxsyy == null ? null : cxsyy.trim();
    }

    public String getWhbmid() {
        return whbmid;
    }

    public void setWhbmid(String whbmid) {
        this.whbmid = whbmid == null ? null : whbmid.trim();
    }

    public String getWhrid() {
        return whrid;
    }

    public void setWhrid(String whrid) {
        this.whrid = whrid == null ? null : whrid.trim();
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }

	@Override
	public String toString() {
		return "InstallationListEffective2Init [id=" + id + ", mainid="
				+ mainid + ", jklbh=" + jklbh + ", jkflbh=" + jkflbh
				+ ", zssyy=" + zssyy + ", zjsyl=" + zjsyl + ", cxsyy=" + cxsyy
				+ ", whbmid=" + whbmid + ", whrid=" + whrid + ", whsj=" + whsj
				+ "]";
	}
}