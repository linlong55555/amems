package com.eray.thjw.aerialmaterial.po;
/**
 * 航材主数据表 （D_008） 实体
 * 用于机型设置维护机型部件表（D_00401）数据
 */
import java.math.BigDecimal;
import java.security.cert.Certificate;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.Substitution;

import enu.SupervisoryLevelEnum;
import enu.MaterialTypeEnum;


@SuppressWarnings("serial")
public class HCMainData extends BizEntity{
    private String id;

    private String jhly;
    
    private String bzjh;
    
    private String gse;
    
    private String zzcs;
    
    private Integer zzjbs;
    
    private String bjh;

    private String zwms;

    private String ywms;

    private String jldw;

    private String cjjh;

    private String zjh;

    private Integer hcjz;

    private Integer gljb;

    private String gljbText;

    private Integer sxkz;

    private Integer hclx;
    
    private String hclxText;

    private Integer hclxEj;

    private Integer isMel;

    private BigDecimal minKcl;

    private BigDecimal maxKcl;

    private String bz;
    
    public String getBzjh() {
		return bzjh;
	}

	public void setBzjh(String bzjh) {
		this.bzjh = bzjh;
	}

	public String getGse() {
		return gse;
	}

	public void setGse(String gse) {
		this.gse = gse;
	}

	public Integer getZzjbs() {
		return zzjbs;
	}

	public void setZzjbs(Integer zzjbs) {
		this.zzjbs = zzjbs;
	}

	private String xh;
    
    private String gg;
    
    private String xingh;

    private Integer zt;

    private String bmid;

    private String cjrid;

    private Date cjsj;
    
    private Integer kykcsl;                //查出航材的可用数量
    
 //   private String hcsId;
    
   	private List<String>   hcids;
   	
    //<!-- 库存数量 -->
	private BigDecimal kcsl;
	
	private String  hclxs;           //用于检索航材类型
	
	private List<String>  hclxlist;           //用于检索航材类型
	
	private String isProduction;//判断是否维护生产信息
	
	private List<Certificate> certificates;	//证书列表
	
	
	public List<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
	}

	public String getJhly() {
		return jhly;
	}

	public void setJhly(String jhly) {
		this.jhly = jhly;
	}

	public String getGljbText() {
		return gljb==null?"":SupervisoryLevelEnum.getName(gljb);
	}

	public void setGljbText(String gljbText) {
		this.gljbText = gljbText;
	}

	public String getHclxText() {
		return hclx==null?"":MaterialTypeEnum.getName(hclx);
	}

	public void setHclxText(String hclxText) {
		this.hclxText = hclxText;
	}

	public String getIsProduction() {
		return isProduction;
	}

	public void setIsProduction(String isProduction) {
		this.isProduction = isProduction;
	}

	public List<String> getHclxlist() {
		return hclxlist;
	}

	public void setHclxlist(List<String> hclxlist) {
		this.hclxlist = hclxlist;
	}

	//扩展字段
	//<!-- 部件id -->
	private String bjid;
	//<!-- 需求数量 -->
	private String xqsl;
	
	//<!-- 缺件数量 -->
	private String qjsl;
	
	private List<HcTaskInfo> hcTaskInfos;
	
	private FixChapter fixChapter;
	
	//航材成本字段
	private BigDecimal juescb;

    private BigDecimal jiescb;

    private BigDecimal gscb;

    private String whrid;

    private Date cbwhsj;
    
    private String username;
    
    private String realname;
    
    private String dprtname;
    
    private String zjhms;
    
    private MaterialToStore materialToStore;
    
    private List<MaterialToPlaneModel> materialToPlaneModelList;
    
    private List<String> jxList;
    
    private List<Substitution> substitutionList;
    
    //扩展区域
    private String displayName;
    
    //工单ID
    private String gdid;
    
    private String dprtcode;
    
    public String getDisplayName() {
		displayName = (bjh==null?"":bjh).concat(" ").concat(ywms==null?"":ywms).concat(" ");
		return displayName;
	}

	public void setDisplayName(String dispalyName) {
		this.displayName = dispalyName;
	}
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}


	public String getHclxs() {
		return hclxs;
	}

	public void setHclxs(String hclxs) {
		this.hclxs = hclxs;
	}

	public String getZjhms() {
		return zjhms;
	}

	public void setZjhms(String zjhms) {
		this.zjhms = zjhms;
	}

	public BigDecimal getJuescb() {
		return juescb;
	}

	public void setJuescb(BigDecimal juescb) {
		this.juescb = juescb;
	}

	public BigDecimal getJiescb() {
		return jiescb;
	}

	public void setJiescb(BigDecimal jiescb) {
		this.jiescb = jiescb;
	}

	public BigDecimal getGscb() {
		return gscb;
	}

	public void setGscb(BigDecimal gscb) {
		this.gscb = gscb;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}


	public Date getCbwhsj() {
		return cbwhsj;
	}

	public void setCbwhsj(Date cbwhsj) {
		this.cbwhsj = cbwhsj;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	public Integer getKykcsl() {
		return kykcsl;
	}

	public void setKykcsl(Integer kykcsl) {
		this.kykcsl = kykcsl;
	}

	/*public String getHcsId() {
		return hcsId;
	}

	public void setHcsId(String hcsId) {
		this.hcsId = hcsId;
	}
*/
	public List<String> getHcids() {
		return hcids;
	}

	public void setHcids(List<String> hcids) {
		this.hcids = hcids;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getZwms() {
        return zwms;
    }

    public void setZwms(String zwms) {
        this.zwms = zwms == null ? null : zwms.trim();
    }

    public String getYwms() {
        return ywms;
    }

    public void setYwms(String ywms) {
        this.ywms = ywms == null ? null : ywms.trim();
    }

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw == null ? null : jldw.trim();
    }

    public String getCjjh() {
        return cjjh;
    }

    public void setCjjh(String cjjh) {
        this.cjjh = cjjh == null ? null : cjjh.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public Integer getHcjz() {
        return hcjz;
    }

    public void setHcjz(Integer hcjz) {
        this.hcjz = hcjz;
    }

    public Integer getGljb() {
        return gljb;
    }

    public void setGljb(Integer gljb) {
        this.gljb = gljb;
    }

    public Integer getSxkz() {
        return sxkz;
    }

    public void setSxkz(Integer sxkz) {
        this.sxkz = sxkz;
    }

    public Integer getHclx() {
        return hclx;
    }

    public void setHclx(Integer hclx) {
        this.hclx = hclx;
    }

    public Integer getHclxEj() {
        return hclxEj;
    }

    public void setHclxEj(Integer hclxEj) {
        this.hclxEj = hclxEj;
    }

    public Integer getIsMel() {
        return isMel;
    }

    public void setIsMel(Integer isMel) {
        this.isMel = isMel;
    }

    public BigDecimal getMinKcl() {
        return minKcl;
    }

    public void setMinKcl(BigDecimal minKcl) {
        this.minKcl = minKcl;
    }

    public BigDecimal getMaxKcl() {
        return maxKcl;
    }

    public void setMaxKcl(BigDecimal maxKcl) {
        this.maxKcl = maxKcl;
    }

    public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid == null ? null : bmid.trim();
    }

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid == null ? null : cjrid.trim();
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

	public List<String> getJxList() {
		return jxList;
	}

	public void setJxList(List<String> jxList) {
		this.jxList = jxList;
	}

	public MaterialToStore getMaterialToStore() {
		return materialToStore;
	}

	public void setMaterialToStore(MaterialToStore materialToStore) {
		this.materialToStore = materialToStore;
	}

	public List<MaterialToPlaneModel> getMaterialToPlaneModelList() {
		return materialToPlaneModelList;
	}

	public void setMaterialToPlaneModelList(
			List<MaterialToPlaneModel> materialToPlaneModelList) {
		this.materialToPlaneModelList = materialToPlaneModelList;
	}

	public BigDecimal getKcsl() {
		if(kcsl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(kcsl));
    	}
        return kcsl;
	}

	public void setKcsl(BigDecimal kcsl) {
		this.kcsl = kcsl;
	}

	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}

	public String getXqsl() {
		return xqsl;
	}

	public void setXqsl(String xqsl) {
		this.xqsl = xqsl;
	}

	public List<HcTaskInfo> getHcTaskInfos() {
		return hcTaskInfos;
	}

	public void setHcTaskInfos(List<HcTaskInfo> hcTaskInfos) {
		this.hcTaskInfos = hcTaskInfos;
	}

	public String getQjsl() {
		BigDecimal xqslInt = StringUtils.isNotBlank(this.getXqsl())?new BigDecimal(this.getXqsl()):BigDecimal.ZERO;
		BigDecimal kcslInt = this.getKcsl()!=null?this.getKcsl():BigDecimal.ZERO;
		qjsl = String.valueOf( xqslInt.subtract(kcslInt));
		return qjsl;
	}

	public void setQjsl(String qjsl) {
		this.qjsl = qjsl;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public String getGg() {
		return gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	public String getXingh() {
		return xingh;
	}

	public void setXingh(String xingh) {
		this.xingh = xingh;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public List<Substitution> getSubstitutionList() {
		return substitutionList;
	}

	public void setSubstitutionList(List<Substitution> substitutionList) {
		this.substitutionList = substitutionList;
	}

	public String getZzcs() {
		return zzcs;
	}

	public void setZzcs(String zzcs) {
		this.zzcs = zzcs;
	}

}