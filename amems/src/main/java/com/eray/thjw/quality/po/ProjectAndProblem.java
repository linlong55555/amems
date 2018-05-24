package com.eray.thjw.quality.po;

/**
 * 
 * @Description b_z_012审核项目单与审核问题单关系
 * @CreateTime 2018年1月11日 上午10:50:57
 * @CreateBy 岳彬彬
 */
public class ProjectAndProblem {
    private String shwtdid;

    private String shxmdid;

    public String getShwtdid() {
        return shwtdid;
    }

    public void setShwtdid(String shwtdid) {
        this.shwtdid = shwtdid == null ? null : shwtdid.trim();
    }

    public String getShxmdid() {
        return shxmdid;
    }

    public void setShxmdid(String shxmdid) {
        this.shxmdid = shxmdid == null ? null : shxmdid.trim();
    }
}