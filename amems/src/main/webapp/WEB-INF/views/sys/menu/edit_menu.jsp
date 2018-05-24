<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
<script type="text/javascript">
	$(document).ready(function(){
			var menuType='${menu.menuType}';
			if(menuType == '1') {
				$("#statue1").attr("selected", true);
			} else if(menuType == '2') {
				$("#statue2").attr("selected", true);
			}
			var xtlx='${menu.xtlx}';
			if(xtlx == 'AMEMS') {
				$("#statue3").attr("selected", true);
			} else if(xtlx == 'OSMS') {
				$("#statue4").attr("selected", true);
			}
	});
</script>
</head>
<body>

	<div class="page-content ">
		<input maxlength="20" type="hidden" id="isScheduler" value="${isScheduler }"/>
		<input maxlength="20" type="hidden" id="isDuration" value="${isDuration }"/>
		<input maxlength="20" type="hidden" id="isActualhours" value="${isActualhours }"/>
		<!-------导航Start--->
		
		<!-------导航End--->
		<!-- 查看工单Start -->
		<div class="panel panel-primary">
       	 <div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
				<div class="col-lg-7 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
			
	         		<div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">系统类型</div>
							<div class="font-size-9 line-height-18">System Type</div></label>
						 <div class="col-xs-9 padding-left-8 padding-right-0" >
							<select id="xtlx" name="xtlx"  class="form-control " onchange="onchangeSystem()" disabled="disabled">
										<option id="statue3"  value="AMEMS">AMEMS</option>
										<option  id="statue4" value="OSMS">OSMS</option>
						   </select>
						</div>
					</div>
					<div class="col-lg-8 col-sm-8 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<label  class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>菜单编号</div>
							<div class="font-size-9 line-height-18">Menu Code</div></label>
							<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input  type="text" class="form-control" readOnly="readOnly" maxlength="50" id="menuCode" name="menuCode" value="${erayFns:escapeStr(menu.menuCode)}" >
						</div>
					</div>
					<div class="clearfix"></div>
					<form id="form">
					<div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>菜单中文名称</div>
							<div class="font-size-9 line-height-18">CH.Name</div></label>
						 <div class="col-xs-9 padding-left-8 padding-right-0" >
							<input  type="text" class="form-control "  maxlength="30" id="menuName" name="menuName" value="${erayFns:escapeStr(menu.menuName)}" >
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>菜单英文名称</div>
							<div class="font-size-9 line-height-18">F.Name</div></label>
						 <div class="col-xs-9 padding-left-8 padding-right-0" >
							<input type="text" class="form-control "  maxlength="30" id="menuFname" name="menuFname" value="${erayFns:escapeStr(menu.menuFname)}" >
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">访问路径</div>
							<div class="font-size-9 line-height-18">Path</div></label>
						 <div class="col-xs-9 padding-left-8 padding-right-0" >
							<input  type="text" class="form-control " maxlength="100"  id="path" name="path" value="${erayFns:escapeStr(menu.path)}" >
						</div>
					</div>
					 <div class="clearfix"></div>
					 
					<div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">图标路径</div>
							<div class="font-size-9 line-height-18">Icon Path</div></label>
						 <div class="col-xs-9 padding-left-8 padding-right-0" >
							<input  type="text" class="form-control "   maxlength="100" id="iconPath" name="iconPath" value="${erayFns:escapeStr(menu.iconPath)}" >
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">菜单顺序号</div>
							<div class="font-size-9 line-height-18">Menu Order No.</div></label>
						 <div class="col-xs-9 padding-left-8 padding-right-0" >
							<input placeholder='请输入数字...' onkeyup='clearNoNum(this)' maxlength="9" type="text" class="form-control "  id="menuOrder" name="menuOrder" value="${menu.menuOrder}" >
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">模块排序号</div>
							<div class="font-size-9 line-height-18">Module Order No.</div></label>
						 <div class="col-xs-9 padding-left-8 padding-right-0" >
							<input placeholder='请输入数字...' onkeyup='clearNoNum(this)' maxlength="9" type="text" class="form-control "   id="fullOrder" name="fullOrder" value="${menu.fullOrder}" >
						</div>
					</div>
					 <div class="clearfix"></div>
					 
		           <div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">菜单类型</div>
							<div class="font-size-9 line-height-18">Menu Type</div></label>
						 <div class="col-xs-9 padding-left-8 padding-right-0" >
							<select id="menuType" name="menuType" class="form-control ">
								<option  id="statue1" value="1">父节点</option>
								<option  id="statue2" value="2">子节点</option>
						    </select>
						</div>
					</div>
					 <div class="clearfix"></div>
					 <div style="display: none"><input maxlength="20" id="id"  type="hidden" value="${menu.parentId}"/> </div>
					 
					 <div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0 "  >
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div></label>
							<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8  padding-right-0">
								<textarea class="form-control"  id="remark" name="remark">${erayFns:escapeStr(menu.remark)}</textarea>
							</div>
					</div>	
					 <div class="clearfix"></div>
					 </form>
				</div>
				<input maxlength="20" id="menuId"  type="hidden" value="${id}"/> 
				<div style="display: none"><input maxlength="20" id="id"  type="hidden" value="${menu.parentId}"/> </div>
				<div style="display: none"><input maxlength="20" id="parentId"  type="text" name="parentId"/> </div>
				<!-- 菜单树-->
				<div class="col-lg-5 col-sm-12 col-xs-12  padding-right-0">
						<label   >
								<div class="font-size-13 line-height-18">选择父菜单</div>
								<div class="font-size-9 line-height-18">Parent Menu</div>
						</label>
						<div id="tab_trade" class="tab-pane active col-xs -4" style="padding:10px;">
							<div class="zTreeDemoBackground">
								<ul id="treeDemo" class="ztree" style=" height:480px; overflow-y:scroll; "></ul>
							</div>
						</div>
				</div>
				
				<div class=" pull-right" style="margin-bottom: 10px;">
				       <a href="javascript:void()" data-toggle="modal"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left"
						id="saveUpdateMenu"   style="float: left; margin-left: 10px;"><div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div></a>
                        
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-5" onclick="back()"><div
										class="font-size-12">返回</div>
						<div class="font-size-9">Back</div></button>&nbsp;&nbsp;
                 </div>
			</div>
		</div>
	</div>
	 <script type="text/javascript" src="${ctx }/static/js/thjw/sys/edit_menu.js"></script>  
</body>
</html>

