package com.eray.thjw.productionplan.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;

@SuppressWarnings("serial")
public class PlanTask extends BizEntity{
    private String id;

    private String rwdh;

    private String rwlx;

    private String rwzlx;

    private String fxjldid;

    private Integer zt;

    private Integer xszt;

    private String xggdid;

    private String rwxx;

    private String fjzch;

    private String bjh;

    private String xlh;

    private Date dysj;

    private String dybmid;

    private String dyrid;

    private BigDecimal jhgsRs;

    private BigDecimal jhgsXs;

    private BigDecimal sjgsRs;

    private BigDecimal sjgsXs;

    private Integer gsshZt;

    private String gsshDcbbh;

    private String gsshTjrid;

    private Date gsshTjrq;

    private String gsshBz;

    private String bz;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    private String zdjsrid;

    private String zdjsyy;

    private Date zdjsrq;
    
    private String gzxx;
    
    private String clcs;
     
    //扩展区域
    
    /**
     * 打印人
     */
    private User dyr;
    
    private User zdjsr;
    
    /**
     * 是否装机件 
     * 1:装机件
     * 0：非装机件
     */
    private String isLoadedParts;
    
    /**
     * 部件名称
     */
    private String bjmc;
    
    /**
     * 航材序号
     */
    private String hcxh;
    
    /**
     * 航材工具列表
     */
    private List<Map<String, String>> hcgjs ;
    
    /**
     * 装机清单ID
     */
    private String zjqdid;
    
    /**
     * 飞机初始化数据
     * 用于计算剩余天数
     */
    private PlaneData planeData;
    
    /**
     * 飞机日使用量
     * 用于计算剩余天数
     */
    private PlaneModelData planeModelData;
    /**
     * 打印时间
     */
    private String dysjstr;
    
    /**
     * 打印人
     */
    private String dyrstr;
    
    /**
     * id不等于
     */
    private List<String> idNotEquals;
    
    /**
     * id等于
     */
    private List<String> idEquals;
    
    /**
     * 状态集合
     */
    private List<String> zts;
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRwdh() {
        return rwdh;
    }

    public void setRwdh(String rwdh) {
        this.rwdh = rwdh == null ? null : rwdh.trim();
    }

    public String getRwlx() {
        return rwlx;
    }

    public void setRwlx(String rwlx) {
        this.rwlx = rwlx;
    }

    public String getRwzlx() {
        return rwzlx;
    }

    public void setRwzlx(String rwzlx) {
        this.rwzlx = rwzlx;
    }

    public String getFxjldid() {
        return fxjldid;
    }

    public void setFxjldid(String fxjldid) {
        this.fxjldid = fxjldid == null ? null : fxjldid.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getXszt() {
        return xszt;
    }

    public void setXszt(Integer xszt) {
        this.xszt = xszt;
    }

    public String getXggdid() {
        return xggdid;
    }

    public void setXggdid(String xggdid) {
        this.xggdid = xggdid == null ? null : xggdid.trim();
    }

    public String getRwxx() {
    	return rwxx;
    }

    public void setRwxx(String rwxx) {
        this.rwxx = rwxx == null ? null : rwxx.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public Date getDysj() {
        return dysj;
    }

    public void setDysj(Date dysj) {
        this.dysj = dysj;
    }

    public String getDybmid() {
        return dybmid;
    }

    public void setDybmid(String dybmid) {
        this.dybmid = dybmid == null ? null : dybmid.trim();
    }

    public String getDyrid() {
        return dyrid;
    }

    public void setDyrid(String dyrid) {
        this.dyrid = dyrid == null ? null : dyrid.trim();
    }

    public BigDecimal getJhgsRs() {
        return jhgsRs;
    }

    public void setJhgsRs(BigDecimal jhgsRs) {
        this.jhgsRs = jhgsRs;
    }

    public BigDecimal getJhgsXs() {
        return jhgsXs;
    }

    public void setJhgsXs(BigDecimal jhgsXs) {
        this.jhgsXs = jhgsXs;
    }

    public BigDecimal getSjgsRs() {
        return sjgsRs==null?BigDecimal.valueOf(0):sjgsRs;
    }

    public void setSjgsRs(BigDecimal sjgsRs) {
        this.sjgsRs = sjgsRs;
    }

    public BigDecimal getSjgsXs() {
    	return sjgsXs==null?BigDecimal.valueOf(0):sjgsXs;
    }

    public void setSjgsXs(BigDecimal sjgsXs) {
        this.sjgsXs = sjgsXs;
    }

    public Integer getGsshZt() {
        return gsshZt;
    }

    public void setGsshZt(Integer gsshZt) {
        this.gsshZt = gsshZt;
    }

    public String getGsshDcbbh() {
        return gsshDcbbh;
    }

    public void setGsshDcbbh(String gsshDcbbh) {
        this.gsshDcbbh = gsshDcbbh == null ? null : gsshDcbbh.trim();
    }

    public String getGsshTjrid() {
        return gsshTjrid;
    }

    public void setGsshTjrid(String gsshTjrid) {
        this.gsshTjrid = gsshTjrid == null ? null : gsshTjrid.trim();
    }

    public Date getGsshTjrq() {
        return gsshTjrq;
    }

    public void setGsshTjrq(Date gsshTjrq) {
        this.gsshTjrq = gsshTjrq;
    }

    public String getGsshBz() {
        return gsshBz;
    }

    public void setGsshBz(String gsshBz) {
        this.gsshBz = gsshBz == null ? null : gsshBz.trim();
    }

    public String getBz() {
        return  bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getWhdwid() {
        return whdwid;
    }

    public void setWhdwid(String whdwid) {
        this.whdwid = whdwid == null ? null : whdwid.trim();
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

	public String getIsLoadedParts() {
		return isLoadedParts ; 
		//StringUtils.isNotBlank(isLoadedParts)?isLoadedParts:"1";
	}

	public void setIsLoadedParts(String isLoadedParts) {
		this.isLoadedParts = isLoadedParts;
	}

	public User getDyr() {
		return dyr;
	}

	public void setDyr(User dyr) {
		this.dyr = dyr;
	}

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

	public Date getZdjsrq() {
		return zdjsrq;
	}

	public void setZdjsrq(Date zdjsrq) {
		this.zdjsrq = zdjsrq;
	}

	public User getZdjsr() {
		return zdjsr;
	}

	public void setZdjsr(User zdjsr) {
		this.zdjsr = zdjsr;
	}

	public String getBjmc() {
		return bjmc;
	}

	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}

	public String getHcxh() {
		return hcxh;
	}

	public void setHcxh(String hcxh) {
		this.hcxh = hcxh;
	}

	/**
	 * 航材工具列表
	 * @return
	 */
	public List<Map<String, String>> getHcgjs() {
		if (StringUtils.isNotBlank(hcxh)) {
			hcgjs = new ArrayList<Map<String,String>>();
			String [] hclines = hcxh.split(",");
			int len = hclines.length <=3?hclines.length:3;
			 
			for (int i = 0; i < len; i++) {
				Map<String, String> row = new HashMap<String, String>();
				String []hcCols = hclines[i].split("`##`");
				row.put("hc_lx", hcCols[0]);
				row.put("hc_mc", hcCols[1].replaceAll("`", ","));
				row.put("hc_sl", hcCols[2]);
				hcgjs.add(row);
			}
		}
		return hcgjs;
	}

	public void setHcgjs(List<Map<String, String>> hcgjs) {
		this.hcgjs = hcgjs;
	}
 
	public String getZjqdid() {
		return zjqdid;
	}

	public void setZjqdid(String zjqdid) {
		this.zjqdid = zjqdid;
	}

	public PlaneData getPlaneData() {
		return planeData;
	}

	public void setPlaneData(PlaneData planeData) {
		this.planeData = planeData;
	}

	public PlaneModelData getPlaneModelData() {
		return planeModelData;
	}

	public void setPlaneModelData(PlaneModelData planeModelData) {
		this.planeModelData = planeModelData;
	} 

	public String getDysjstr() {
		return dysjstr;
	}

	public void setDysjstr(String dysjstr) {
		this.dysjstr = dysjstr;
	}

	public String getDyrstr() {
		return dyrstr;
	}

	public void setDyrstr(String dyrstr) {
		this.dyrstr = dyrstr;
	}

	public List<String> getIdNotEquals() {
		return idNotEquals;
	}

	public void setIdNotEquals(List<String> idNotEquals) {
		this.idNotEquals = idNotEquals;
	}

	public List<String> getIdEquals() {
		return idEquals;
	}

	public void setIdEquals(List<String> idEquals) {
		this.idEquals = idEquals;
	}

	public List<String> getZts() {
		return zts;
	}

	public void setZts(List<String> zts) {
		this.zts = zts;
	}

	public String getGzxx() {
		return gzxx;
	}

	public void setGzxx(String gzxx) {
		this.gzxx = gzxx;
	}

	public String getClcs() {
		return clcs;
	}

	public void setClcs(String clcs) {
		this.clcs = clcs;
	}
	
}