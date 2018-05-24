package com.eray.thjw.baseinfo.po;

/** 
 * @Description 目录结构
 * @CreateTime 2018-3-29 下午2:18:35
 * @CreateBy 雷伟	
 */
public class DirectoryStructure {
	private String id; //主键id
	private String name;//名称
	private String type;//文件类型 WJJ:文件夹  WJ:文件
	private String path;//路径
	private String parentId;//父ID
	private String parentPath;//父路径
	private String nbwjm;//内部文件名
	
	public String getNbwjm() {
		return nbwjm;
	}
	public void setNbwjm(String nbwjm) {
		this.nbwjm = nbwjm;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	@Override
	public String toString() {
		return "DirectoryStructure [id=" + id + ", name=" + name + ", type="
				+ type + ", path=" + path + ", parentId=" + parentId
				+ ", parentPath=" + parentPath + "]";
	}
}
