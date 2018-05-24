<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>指定结束修订通知书</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
	<div class="page-content">
		<!-------导航Start------>
		<ul class="page-breadcrumb breadcrumb"
			style="margin-bottom: 10px; padding-top: 2px; padding-bottom: 1px;">
			<li >
				<i class="fa fa-home pull-left" style="margin-top: 7px; font-size: 16px;"></i>
				<a href="${ctx}/demo/revisionlist" class="pull-left">
					<div class="font-size-12 line-height-18" >首页</div>
					<div class="font-size-9 " >Home</div>
				</a> 
				<i class="fa fa-angle-right pull-left" style="margin-top: 7px; font-size: 16px;"></i>
			</li>
		
			<li >
				<a href="${ctx}/productionplan/plantask/manage" class="pull-left">
					<div class="font-size-12 line-height-18">计划任务管理</div>
					<div class="font-size-9 ">Plan Task</div>
				</a> 
				<i class="fa fa-angle-right pull-left" style="margin-top: 7px; font-size: 16px;"></i>
			</li>
			<li >
				<a href="javascript:void(0)" class="pull-left">
					<div class="font-size-12 line-height-18">计划任务指定结束</div>
					<div class="font-size-9 ">Assign end</div>
				</a>
			</li>
		</ul>
		<!-------导航End------>
		<!-- 编辑维修方案Start -->
		<div class="panel panel-primary">
			<div class="panel-heading  padding-top-3 padding-bottom-1">
				<div class="font-size-16 line-height-18">指定结束计划任务</div>
				<div class="font-size-9 ">Assign end</div>
			</div>
			<div class="panel-body">
				<form id="form" >
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
						<div class="font-size-12 line-height-18">指定结束原因</div>
						<div class="font-size-9 line-height-18">Cause</div>
					</label>
					<div class="col-lg-10 col-sm-9 col-xs-8 padding-left-8 form-group">
						<textarea class="form-control" name="zdjsyy">${plantask.zdjsyy}</textarea>
					</div>
					 <input type="hidden" id="id" value="${plantask.id}"/>
					 <input id="userId" type="hidden" value="${user.id}" />
					 <input id="rwlx" type="hidden" value="${plantask.rwlx}" />
					 <input id="xggdid" type="hidden" value="${plantask.xggdid}" />
					<div class="clearfix"></div>
				</form>
			</div>
		</div>

		<!-- 编辑维修方案End -->
		
		<div class="clearfix"></div>
		<div style="margin-top:10px; text-align:right;">        
			 <button class="btn btn-primary padding-top-1 padding-bottom-1 " id="submit" type="button" >
        		<div class="font-size-12">提交</div>
				<div class="font-size-9">Submit</div>
        	</button>
		</div>

		<!-------alert对话框 Start-------->
		<div class="modal fade" id="alertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="alertModalBody"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-------alert对话框 End-------->
		
		<!-------alert对话框 Start-------->
		<div class="modal fade" id="alertAfterModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="alertAfterModalBody"></div>
					<div class="modal-footer">
						<button type="button" onclick="refreshPage();" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-------alert对话框 End-------->
	<!-------alert error对话框 Start-------->
	<div class="modal fade" id="alertErrorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertErrorModalBody">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------alert error对话框 End-------->
</div>
 <script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/plantask/plantask_end.js"></script> 
</body>
</html>