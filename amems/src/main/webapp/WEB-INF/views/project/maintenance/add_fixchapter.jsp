<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增ATA章节号</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
<div class="page-content">
		<input maxlength="30" type="hidden" id="isScheduler" value="${isScheduler }"/>
		<input maxlength="30" type="hidden" id="isDuration" value="${isDuration }"/>
		<input maxlength="30" type="hidden" id="isActualhours" value="${isActualhours }"/>
		<!-------导航Start--->
  <div class="panel panel-primary">
	   <div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
				    <div class="panel panel-default">
					        <div class="panel-heading">
							  <h3 class="panel-title">基本信息  Basic Information</h3>
						   </div>
			         <div class="panel-body">
				     <form id="form">
				          <div class="col-lg-12 col-sm-4 col-xs-12 padding-left-0 padding-right-0"  >
							<div class="col-lg-3 col-sm-4 col-xs-12 padding-left-0 padding-right-0 form-group">
							     <label  class="col-xs-4 text-right padding-left-0 padding-right-0">
								   <div class="font-size-12 line-height-18"><span style="color: red">*</span>ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
									<input type="text"  id="zjh" name="zjh"class="form-control" maxlength="16" >
							    </div>
						    </div>
							
							<div class=" col-lg-3 col-sm-4 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12 line-height-18">中文描述</div>
									<div class="font-size-9 line-height-18">CH.Name</div></label>
								 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
									<input type="text" class="form-control "  id="zwms" name="zwms" maxlength="60">
								</div>
							</div>
							
							<div class=" col-lg-3 col-sm-4 col-xs-12  padding-left-0 padding-right-0" >
								<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12 line-height-18">英文描述</div>
									<div class="font-size-9 line-height-18">F.Name</div></label>
								 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
									<input type="text" class="form-control "  id="ywms" name="ywms" maxlength="180">
								</div>
							</div>
						</div>
						
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					      <div class=" col-lg-12 col-sm-4 col-xs-12  padding-left-0 padding-right-0" >
								<label class="col-lg-1  col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div></label>
								<div class="col-xs-11 padding-left-8  padding-right-0 form-group">
									<textarea class="form-control"  id="rJcsj" name="rjcsj" maxlength="300"></textarea>
								</div>
							</div>
						</div>	
					  </form>   
				   </div>
			 </div>
			 <div style="display: none"><input maxlength="30" id="planedataId"  type="text"  name="menuId"/> </div>
			 <!-- 将按钮所属菜单的id储存起来 -->
		 	
			 <div  class=" pull-right">
		       <a href="javascript:void()" data-toggle="modal"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left"
				id="btnSave"   style="float: left; margin-left: 10px;"><div class="font-size-12">保存</div>
				<div class="font-size-9">Save</div></a>
                   
                    &nbsp;&nbsp;&nbsp;&nbsp;
                   	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:window.history.go(-1)"><div
								class="font-size-12">返回</div>
				<div class="font-size-9">Back</div></button>
               </div>
		  </div>
		</div>
	</div>
	<script src="${ctx}/static/js/workdetail.js"></script>
<%@ include file="/WEB-INF/views/alert.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/add_fixchapter.js"></script>
		
</body>
</html>
