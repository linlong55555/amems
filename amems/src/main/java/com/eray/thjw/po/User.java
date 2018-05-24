package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;

public class User extends BaseEntity{
    private String id;
    
    private String drzhid;
    
    private String username;
    
    private String password;
    
    private String newpassword;

    private String realname;

    private String cellphone;

    private String phone;

    private Integer sex;

    private Integer state;
    

    private Date lastvisit;

    private String lastip;

    private String bmdm;
    
    private String jgdm;
    
    private String departmentId;
    
    private List<String> rolesId;
    
    private List<String> modelRolesId;
    
    private List<String> warehouseRolesId;
    
    private List<String> oldRolesId;
    
    private List<String> oldModelRolesId;
    
    private List<String> oldWarehouseRolesId;
    
    private Department department;
    
    private String institutionsName;
    
    private String departmentName;
    
    private String keyword;

    private String accountName;
    //扩展区域
    private String displayName;
    
    private String xm;
    
    private String lx;
    
    //用户所属组织机构的编码
    private String orgcode;
    
    private Department dprt_bm;
    
    private Department dprt_jg;
    
    //用户类型（admin,""）
    private String userType; 
    
    //机型
    private String jx;
    
    //基地代码
    private String jddm;
    
    //电子签名
    private Attachment attachment;
    
    //电子签名id
    private String attId;
    
    private String email; //email
    
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAttId() {
		return attId;
	}

	public void setAttId(String attId) {
		this.attId = attId;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public String getJddm() {
		return jddm;
	}

	public void setJddm(String jddm) {
		this.jddm = jddm;
	}

	public String getJx() {
		return jx;
	}

	public void setJx(String jx) {
		this.jx = jx;
	}

	public List<String> getOldModelRolesId() {
		return oldModelRolesId;
	}

	public void setOldModelRolesId(List<String> oldModelRolesId) {
		this.oldModelRolesId = oldModelRolesId;
	}

	public List<String> getOldWarehouseRolesId() {
		return oldWarehouseRolesId;
	}

	public void setOldWarehouseRolesId(List<String> oldWarehouseRolesId) {
		this.oldWarehouseRolesId = oldWarehouseRolesId;
	}

	public List<String> getModelRolesId() {
		return modelRolesId;
	}

	public void setModelRolesId(List<String> modelRolesId) {
		this.modelRolesId = modelRolesId;
	}

	public List<String> getWarehouseRolesId() {
		return warehouseRolesId;
	}

	public void setWarehouseRolesId(List<String> warehouseRolesId) {
		this.warehouseRolesId = warehouseRolesId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Department getDprt_bm() {
		return dprt_bm;
	}

	public void setDprt_bm(Department dprt_bm) {
		this.dprt_bm = dprt_bm;
	}

	public Department getDprt_jg() {
		return dprt_jg;
	}

	public void setDprt_jg(Department dprt_jg) {
		this.dprt_jg = dprt_jg;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getInstitutionsName() {
		return institutionsName;
	}

	public void setInstitutionsName(String institutionsName) {
		this.institutionsName = institutionsName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}


	public List<String> getRolesId() {
		return rolesId;
	}

	public void setRolesId(List<String> rolesId) {
		this.rolesId = rolesId;
	}

	public List<String> getOldRolesId() {
		return oldRolesId;
	}

	public void setOldRolesId(List<String> oldRolesId) {
		this.oldRolesId = oldRolesId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getLastvisit() {
		return lastvisit;
	}

	public void setLastvisit(Date lastvisit) {
		this.lastvisit = lastvisit;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}


	
	public String getBmdm() {
		return bmdm;
	}

	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDrzhid() {
		return drzhid;
	}

	public void setDrzhid(String drzhid) {
		this.drzhid = drzhid;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", pagination=" + pagination + ", username="
				+ username + ", password=" + password + ", newpassword="
				+ newpassword + ", realname=" + realname + ", cellphone="
				+ cellphone + ", phone=" + phone + ", sex=" + sex + ", state="
				+ state + ", lastvisit=" + lastvisit + ", lastip=" + lastip
				+ ", bmdm=" + bmdm + ", jgdm=" + jgdm + ", departmentId="
				+ departmentId + ", rolesId=" + rolesId + ", oldRolesId="
				+ oldRolesId + ", department=" + department
				+ ", institutionsName=" + institutionsName
				+ ", departmentName=" + departmentName + ", keyword=" + keyword
				+ "]";
	}

	public String getDisplayName() {
		displayName = (username==null?"":username).concat(" ").concat(realname==null?"":realname);
		return displayName;
	}

	public void setDisplayName(String dispalyName) {
		this.displayName = dispalyName;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
  
}