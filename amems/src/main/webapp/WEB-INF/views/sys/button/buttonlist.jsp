<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>按钮管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
<input maxlength="20" type="hidden" id="userId" value="" />
<div class="page-content ">
	<div class="panel panel-primary">
	        <div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			   <div class="col-xs-12 padding-left-0 padding-right-0">
				<div class="col-xs-2 padding-left-0 row-height">
					<a href="" onclick="add()" data-toggle="modal" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" onclick="searchUserName()">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
				</div>
				
				<div class=" pull-right padding-left-0 padding-right-0">
				    <div class=" pull-left padding-left-5 padding-right-0 row-height" style="width: 250px;">
						<input type="text" placeholder='按钮编号/按钮名称' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="buttonMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div> 
				  </div> 
				
				
				</div>
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px;">
						<table id="button_table"
							class="table-set table table-thin table-bordered table-striped table-hover"
							>
							<thead>
								<tr>
									<th style="width:60px;"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th class="sorting" onclick="orderBy('buttonCode')" id="buttonCode_order">
								     	 <div class="important">
								     	 <div class="font-size-12 line-height-18">按钮编号</div>
										 <div class="font-size-9 line-height-18">Button Code</div>
										 </div>
									</th>
									<th class="sorting" onclick="orderBy('buttonName')" id="buttonName_order">
									  <div class="important">
										<div class="font-size-12 line-height-18">按钮名称</div>
										<div class="font-size-9 line-height-18">Button Name</div>
									   </div>	
									</th>
									<th class="sorting" onclick="orderBy('menuId')" id="menuId_order">
									     <div class="font-size-12 line-height-18">所属菜单名称</div>
										 <div class="font-size-9 line-height-18">Menu Name</div>
									</th>
									<th class="sorting" onclick="orderBy('path')" id="path_order">
									     <div class="font-size-12 line-height-18">访问路径</div>
										 <div class="font-size-9 line-height-18"> Path</div>
									</th>	
									<th class="sorting" onclick="orderBy('remark')" id="remark_order">
									<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></th>	
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
							
						</table>
					</div>
					
					<div class="col-xs-12 text-center page-height" id="pagination">
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/sys/buttonlist.js"></script>
</body>
</html>
