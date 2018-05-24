package com.eray.thjw.po;

import java.util.List;

/**
 * @author meizhiliang
 * @depretion  工单的补充类
 */
@SuppressWarnings("serial")
public class WorkOrderSupplement extends BaseEntity {
         
	     // 新增工单时获取的关联工单
	    private List<String > gdids ;        //已选择的工单id集合 
	    private String fjzch;                       //获取相关工单时需要在相同机型下
	    private String jx;					//机型
	    private String keyword;					//机型
	 	private String pgdh;     //评估单号
	 	private String pgdid;     //评估单号
		private String zxfl;       //执行分类
		private String bjh;     //部件号
		private String  bjxlh ;    //部件序列号
		private String  zhuanye;     //   联合查询专业
		private String  username;     // 用户名
		private String realname;      //真是姓名
		private String gdlx2;             
		private String dprtname;
		private String  pfusername;     // 用户名
		private String pfrealname;      //真是姓名
		private Integer gddlx;               //工单大类型   任务类型：4定检执行任务、2非例行工单任务、3EO工单任务
		private String jdms;               // 基地描述
		private String zwms;               // 章节名称
		
		private String displayname;
		
		private String gkbh;
		private String gldjxm;
		
		private String lx;
		
		
		
		public String getLx() {
			return lx;
		}
		public void setLx(String lx) {
			this.lx = lx;
		}
		public String getGkbh() {
			return gkbh;
		}
		public void setGkbh(String gkbh) {
			this.gkbh = gkbh;
		}
		public String getGldjxm() {
			return gldjxm;
		}
		public void setGldjxm(String gldjxm) {
			this.gldjxm = gldjxm;
		}
		public String getDisplayname() {
			return displayname;
		}
		public void setDisplayname(String displayname) {
			this.displayname = displayname;
		}
		public String getPgdid() {
			return pgdid;
		}
		public void setPgdid(String pgdid) {
			this.pgdid = pgdid;
		}
		public String getKeyword() {
			return keyword;
		}
		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}
		public String getZwms() {
			return zwms;
		}
		public void setZwms(String zwms) {
			this.zwms = zwms;
		}
		public String getJdms() {
			return jdms;
		}
		public void setJdms(String jdms) {
			this.jdms = jdms;
		}
		public Integer getGddlx() {
			return gddlx;
		}
		public void setGddlx(Integer gddlx) {
			this.gddlx = gddlx;
		}
		public String getPfusername() {
			return pfusername;
		}
		public void setPfusername(String pfusername) {
			this.pfusername = pfusername;
		}
		public String getPfrealname() {
			return pfrealname;
		}
		public void setPfrealname(String pfrealname) {
			this.pfrealname = pfrealname;
		}
		public String getPgdh() {
			return pgdh;
		}
		public void setPgdh(String pgdh) {
			this.pgdh = pgdh;
		}
		public String getZxfl() {
			return zxfl;
		}
		public void setZxfl(String zxfl) {
			this.zxfl = zxfl;
		}
		public String getBjh() {
			return bjh;
		}
		public void setBjh(String bjh) {
			this.bjh = bjh;
		}
		public String getBjxlh() {
			return bjxlh;
		}
		public void setBjxlh(String bjxlh) {
			this.bjxlh = bjxlh;
		}
		public String getZhuanye() {
			return zhuanye;
		}
		public void setZhuanye(String zhuanye) {
			this.zhuanye = zhuanye;
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
		
		public String getGdlx2() {
			return gdlx2;
		}
		public void setGdlx2(String gdlx2) {
			this.gdlx2 = gdlx2;
		}
		public String getDprtname() {
			return dprtname;
		}
		public void setDprtname(String dprtname) {
			this.dprtname = dprtname;
		}
		public String getFjzch() {
			return fjzch;
		}
		public void setFjzch(String fjzch) {
			this.fjzch = fjzch;
		}
	     
	     public String getJx() {
			return jx;
		}
		public void setJx(String jx) {
			this.jx = jx;
		}
		public List<String> getGdids() {
			return gdids;
		}
		public void setGdids(List<String> gdids) {
			this.gdids = gdids;
		}
		
} 
