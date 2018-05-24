package com.eray.pbs.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eray.util.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户登录信息
 * 
 * @author ganqing
 * 
 */
@Entity
@Table(name = "pbs_logon")
public class Logon extends BaseEntity{
	private String logonuser; //登录账号
	private String logonpas;  //登录密码
	private String userid;  //用户编号
	private String username; //用户名
	private String usertype;
	private String userstatus; //是否锁定,1为正常 0为锁定
	private String userprofile;
	private String userparameter;
	private Date creationdate;
	private Date modificationdate; //修改时间
	private String lastchangeby;
	private Date lastlogondate; //最后登录时间
	private String accumulatedlogontime;
	private String equipmentid; //部门ID
	
	private Long roleId;		//角色id  2016.8.19 add

	@Column(name = "logonuser_")
	public String getLogonuser() {
		return logonuser;
	}

	public void setLogonuser(String logonuser) {
		this.logonuser = logonuser;
	}

	@Column(name = "logonpass_")
	public String getLogonpas() {
		return logonpas;
	}

	public void setLogonpas(String logonpas) {
		this.logonpas = logonpas;
	}

	@Column(name = "userid_")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "username_")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "usertype_")
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	@Column(name = "userstatus_")
	public String getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}

	@Column(name = "userprofile_")
	public String getUserprofile() {
		return userprofile;
	}

	public void setUserprofile(String userprofile) {
		this.userprofile = userprofile;
	}

	@Column(name = "userparameter_")
	public String getUserparameter() {
		return userparameter;
	}

	public void setUserparameter(String userparameter) {
		this.userparameter = userparameter;
	}

	@Column(name = "creationdate_")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	@Column(name = "modificationdate_")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getModificationdate() {
		return modificationdate;
	}

	public void setModificationdate(Date modificationdate) {
		this.modificationdate = modificationdate;
	}

	@Column(name = "lastchangeby_")
	public String getLastchangeby() {
		return lastchangeby;
	}

	public void setLastchangeby(String lastchangeby) {
		this.lastchangeby = lastchangeby;
	}

	@Column(name = "lastlogondate_")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getLastlogondate() {
		return lastlogondate;
	}

	public void setLastlogondate(Date lastlogondate) {
		this.lastlogondate = lastlogondate;
	}

	@Column(name = "accumulatedlogontime_")
	public String getAccumulatedlogontime() {
		return accumulatedlogontime;
	}

	public void setAccumulatedlogontime(String accumulatedlogontime) {
		this.accumulatedlogontime = accumulatedlogontime;
	}

	/**
	 * @return the equipmentid
	 */
	@Column(name = "equipmentid_")
	public String getEquipmentid()
	{
		return equipmentid;
	}

	/**
	 * @param equipmentid the equipmentid to set
	 */
	public void setEquipmentid(String equipmentid)
	{
		this.equipmentid = equipmentid;
	}

	@Column(name = "roleid_")
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "Logon [logonuser=" + logonuser + ", logonpas=" + logonpas
				+ ", userid=" + userid + ", username=" + username
				+ ", usertype=" + usertype + ", userstatus=" + userstatus
				+ ", userprofile=" + userprofile + ", userparameter="
				+ userparameter + ", creationdate=" + creationdate
				+ ", modificationdate=" + modificationdate + ", lastchangeby="
				+ lastchangeby + ", lastlogondate=" + lastlogondate
				+ ", accumulatedlogontime=" + accumulatedlogontime
				+ ", equipmentid=" + equipmentid + ", roleId=" + roleId + "]";
	}

}
