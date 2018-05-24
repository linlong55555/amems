package com.eray.thjw.po;

import java.util.List;

/**
 * @author liub
 * @description 菜单表t_menu
 * @develop date 2016.07.26
 */
public class Menu  extends BizEntity{

	private static final long serialVersionUID = 1L;
	
	private String id;                           //菜单id
    private String  menuCode;            // 菜单编号
    private String menuName;           // 菜单名称
    private String menuFname; //菜单英文名称
    private Integer menuType;          //菜单类型 
    private String parentId;              //菜单父节
    private Integer menuOrder;         //菜单顺序
    private String path;                        //菜单访问路径
    private String remark;                  //备注
    private String fullOrder;              // 排序
    private String iconPath;              //图标路径
    private String parentName;     //父节点名称
    private String xtlx;     //系统类型

	//扩展属性
    private List<Menu> children;
    
    private String menuCodeFormat;   
    
    public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getMenuFname() {
		return menuFname;
	}
	public void setMenuFname(String menuFname) {
		this.menuFname = menuFname;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public String getXtlx() {
		return xtlx;
	}
	public void setXtlx(String xtlx) {
		this.xtlx = xtlx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Integer getMenuType() {
		return menuType;
	}
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFullOrder() {
		return fullOrder;
	}
	public void setFullOrder(String fullOrder) {
		this.fullOrder = fullOrder;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	public String getMenuCodeFormat() {
		menuCodeFormat = menuCode==null?"":menuCode.replaceAll(":", "_");
		return menuCodeFormat;
	}
	public void setMenuCodeFormat(String menuCodeFormat) {
		this.menuCodeFormat = menuCodeFormat;
	}
	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuCode=" + menuCode + ", menuName=" + menuName + ", menuFname=" + menuFname
				+ ", menuType=" + menuType + ", parentId=" + parentId + ", menuOrder=" + menuOrder + ", path=" + path
				+ ", remark=" + remark + ", fullOrder=" + fullOrder + ", iconPath=" + iconPath + ", parentName="
				+ parentName + ", xtlx=" + xtlx + ", children=" + children + ", menuCodeFormat=" + menuCodeFormat + "]";
	}
}
