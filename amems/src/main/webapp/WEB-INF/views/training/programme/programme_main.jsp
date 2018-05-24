<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>培训大纲</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style>
.sjzdTable tbody tr{
cursor:pointer;
}
.sjzdTable thead tr th{
border:0px;
}
.sjzdTable tbody tr td{
padding-top:5px;
padding-right:5px;
border-left:0px !important;
border-right:0px !important;
}
.list-group-item:last-child {
	border-bottom-left-radius: 0px;
    border-bottom-right-radius: 0px;
}
.list-group-item{
cursor:pointer;
border-left:0px;
border-right:0px;
}
.list-group-item:hover{
background:#f5f5f5;
}
.list-group-item.active{
background:#dbe2f7;
}


</style>
</head>
<body class="page-header-fixed">
	<script type="text/javascript">
	var pageParam = '${param.pageParam}';
	$(document).ready(function(){
	//导航
	Navigation(menuCode);
	});
	</script>
<input maxlength="20" type="hidden" id="userId" value="" />
<input type="hidden" id="viewKcbh" value="" />
<div class="page-content ">
 <div class='col-xs-12 ' style='background:white;padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;'>
     <!-- 左部  -->
	 <div class='col-sm-3 col-xs-12' style='padding-left:0px;padding-right:10px;padding-top:0px;padding-bottom:0px;' id="left_div">
	  	<div class="panel panel-primary">
					<div class="panel-heading">
					<div class="pull-left">
						<div class="font-size-12 line-height-12">岗位列表</div>
	                    <div class="font-size-9 line-height-9">Post List</div>
	                </div>
	                <div class="pull-right">   
	                    <a href="#" onclick="exportExcel()"  class="btn btn-primary padding-top-1 margin-bottom-5 margin-left-10 padding-bottom-1 checkPermission" permissioncode='training:programme:main:07' >
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a>
						 </div>
						 <div class="clearfix"></div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" >
					<input type="hidden" value="" id="gwid">
					<div class="list-group" >
					<div class='row-height padding-leftright-8' >
					<div class="col-lg-12 pull-right padding-top-1 padding-bottom-1" style="padding-left: 0px;padding-right: 0px">
					    <span class=" col-lg-3 pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						  <div class="font-size-12">组织机构</div>
						   <div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-9  padding-left-8 padding-right-0">
								<select id="dprtcode" class="form-control " name="dprtcode" onchange="changeDprtcode()">
									<c:if test="${dprtcode==user.orgcode}">
											<option value="-1">公共</option>
									</c:if>
									<c:forEach items="${accessDepartments}" var="type">
										<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>								
									</c:forEach>
								</select>
						</div>
					</div>
					<div class="col-lg-12 pull-right padding-top-10 padding-bottom-1" style="padding-left: 0px;padding-right: 0px">
					    <span class=" col-lg-3 pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						  <div class="font-size-12">岗位信息</div>
						   <div class="font-size-9">Information</div>
						</span>
						<div class="col-xs-9  padding-left-8 padding-right-0">
								<input type="text" class="form-control" onkeyup="query_gjz()" id="gwxx" placeholder='编号/名称' >
						</div>
					</div>
					<div class='clearfix'></div>
					</div>
					<div class='col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-10' >
						<ul class="list-group col-xs-12"  id="gwList" style='padding-right:0px;border-radius:0px;margin-bottom:0px;'>
						</ul>
					</div>
					</div>
					</div>
	    </div>
	 </div>
	 <!-- 右边 -->
	 <div class='col-sm-9 col-xs-12' style='padding-left:0px;padding-right:0px;' id="right_div">
	 	<!-- 收缩效果 -->
		 <div id="" style="position: absolute; left: -8px; top: 250px;">
		 <i class="cursor-pointer icon-caret-left"   style="font-size: 22px;" onclick="toggleIcon(this)"></i>
		 </div>
		  <%@ include file="/WEB-INF/views/training/programme/programme_list.jsp"%> 
	 </div>
 </div>

</div>
<%@ include file="/WEB-INF/views/training/programme/programme_modal.jsp"%> 
<%@ include file="/WEB-INF/views/training/programme/programme_open.jsp"%> 
<%@ include file="/WEB-INF/views/training/programme/programme_workrequire.jsp"%> 
<%-- <%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%>  --%>
<script type="text/javascript" src="${ctx}/static/js/thjw/training/programme/programme_main.js"></script>
</body>
</html>
