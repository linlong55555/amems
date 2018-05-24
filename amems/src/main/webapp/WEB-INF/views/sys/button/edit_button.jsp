<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改按钮</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>

	<div class="page-content ">
		<input maxlength="20" type="hidden" id="isScheduler" value="${isScheduler }"/>
		<input maxlength="20" type="hidden" id="isDuration" value="${isDuration }"/>
		<input maxlength="20" type="hidden" id="isActualhours" value="${isActualhours }"/>
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
				    <div class="col-lg-7 col-sm-12 col-xs-12  padding-right-0" >
				     <form id="form">
							<div class="col-lg-8 col-sm-8 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<label  class="col-xs-2 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18"><span style="color: red">*</span>按钮编号</div>
									<div class="font-size-9 line-height-18">Button Code</div></label>
									<div class=" col-xs-10 padding-left-8 padding-right-0">
									<input  type="text"  id="buttonCode" maxlength="50" name="buttonCode" class="form-control"  value="${erayFns:escapeStr(button.buttonCode)}" readOnly="true" >
								</div>
							</div>
							
					       <div class="clearfix"></div>
							<div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group" >
								<label class="col-xs-2 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18"><span style="color: red">*</span>按钮名称</div>
									<div class="font-size-9 line-height-18">Button Name</div></label>
								 <div class="col-xs-10 padding-left-8 padding-right-0" >
									<input type="text" class="form-control " maxlength="30" name="buttonName" id="buttonName" value="${erayFns:escapeStr(button.buttonName)}" >
								</div>
							</div>
							<div class="clearfix"></div>
							<div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group" >
								<label class="col-xs-2 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18">访问路径</div>
									<div class="font-size-9 line-height-18">Path</div></label>
								 <div class="col-xs-10 padding-left-8 padding-right-0" >
									<input  type="text" class="form-control " maxlength="100"name="path"id="path" value="${erayFns:escapeStr(button.path)}" >
								</div>
							</div>
					       <div class="clearfix"></div>
					      <div class=" col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group" >
								<label class="col-lg-2  col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0 "  >
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div></label>
								<div class="col-lg-10  col-sm-10 col-xs-10 padding-left-8  padding-right-0">
									<textarea class="form-control"name="remark" maxlength="100" id="remark">${erayFns:escapeStr(button.remark)}</textarea>
								</div>
							</div>
					        <div class="clearfix"></div>
					  </form>
				</div>
				<input maxlength="20" id="buttonId"  type="hidden" value="${id}"/> 
				<div style="display: none"><input maxlength="20" id="id"  type="hidden" value="${button.menuId}"/> </div>
				<div style="display: none"><input maxlength="20" id="menuId"  type="text" name="menuId"/> </div>
				<!-- 菜单树-->
				<div class="col-lg-5 col-sm-12 col-xs-12  padding-right-0">
					<label>
							<div class="font-size-13 line-height-18">所属菜单名称</div>
							<div class="font-size-9 line-height-18">Menu Name</div>
						</label>
					<div id="tab_trade" class="tab-pane active col-xs -4" style="padding:10px;">
						<div class="zTreeDemoBackground">
							<ul id="treeDemo" class="ztree" style=" height:300px; overflow-y:scroll; "></ul>
						</div>
					</div>
				</div>
				<div class=" pull-right" style="margin-bottom: 10px;">
				       <a href="javascript:void()" data-toggle="modal"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left"
						id="saveUpdateButton"   style="float: left; margin-left: 10px;"><div class="font-size-12">保存</div>
						<div class="font-size-9">save</div></a>
                        &nbsp;
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()"><div
								class="font-size-12">返回</div>
						<div class="font-size-9">Back</div></button>&nbsp;&nbsp;
                 </div>
			</div>
		</div>
		</div>
   <script type="text/javascript" src="${ctx}/static/js/thjw/sys/edit_button.js"></script>
</body>
</html>

