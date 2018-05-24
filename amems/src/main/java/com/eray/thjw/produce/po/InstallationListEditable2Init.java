package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description b_s2_00101 装机清单-编辑区-部件初始化
 * @CreateTime 2017年9月22日 下午2:26:42
 * @CreateBy 韩武
 */
public class InstallationListEditable2Init extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private String jklbh;

    private String jkflbh;

    private String csz;

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

    public String getCsz() {
        return csz;
    }

    public void setCsz(String csz) {
        this.csz = csz == null ? null : csz.trim();
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
		return "InstallationListEditable2Init [id=" + id + ", mainid=" + mainid
				+ ", jklbh=" + jklbh + ", jkflbh=" + jkflbh + ", csz=" + csz
				+ ", whbmid=" + whbmid + ", whrid=" + whrid + ", whsj=" + whsj
				+ "]";
	}
	
	/**
	 * @Description 由编辑区初始化对象转换为生效区初始化对象
	 * @CreateTime 2017年10月20日 下午5:11:43
	 * @CreateBy 徐勇
	 * @return
	 */
	public InstallationListEffective2Init toEffective(){
		InstallationListEffective2Init init = new InstallationListEffective2Init();
		init.setId(id);
		init.setMainid(mainid);
		init.setJklbh(jklbh);
		init.setJkflbh(jkflbh);
		init.setZssyy(csz);
		init.setWhbmid(whbmid);
		init.setWhrid(whrid);
		init.setWhsj(whsj);
		return init;
	}
}