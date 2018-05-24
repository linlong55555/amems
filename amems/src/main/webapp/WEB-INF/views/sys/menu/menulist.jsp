<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
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
					<div class="col-xs-6 padding-left-0 row-height">
						<a href="" onclick="add()" data-toggle="modal" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" onclick="add()">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a> 
					</div>
					
					
				<div class=" pull-right padding-left-0 padding-right-0">
						  <div class="pull-left  text-right padding-left-0 padding-right-0"><div
								class="font-size-12">系統</div>
							<div class="font-size-9">System</div>
							</div>
							<div class=" padding-left-8 pull-left" style="width: 200px; margin-right:10px;">
							<select id="xtlx" name="xtlx"  class="form-control "  onchange="onchangeSystem()">
										<option   value="AMEMS">AMEMS</option>
										<option   value="OSMS">OSMS</option>
						   </select>
							</div>
				
				    <div class=" pull-left padding-left-5 padding-right-0 row-height" style="width: 200px;">
						<input type="text" placeholder='菜单编号/中英文' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button id="menuMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
                    </div> 
				  </div> 
					
					
				<div class="clearfix"></div>

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px;overflow-x:scroll ;">
						<table id="menu_list_table" class="table-set table table-thin table-bordered table-striped table-hover "
							style=" min-width: 1900px !important;">
							<thead>
								<tr>
									<th class="fixed-column colwidth-7">
									    <div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="sorting colwidth-15" onclick="orderBy('menuCode')" id="menuCode_order">
									    <div class="important">
									    <div class="font-size-12 line-height-18">菜单编号</div>
										<div class="font-size-9 line-height-18">Menu Code</div>
									    </div>
									</th>
									<th class="sorting colwidth-15" onclick="orderBy('menuFname')" id="menuFname_order">
								       <div class="important">
								    	<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									   </div>
									</th>
									<th class="sorting colwidth-15" onclick="orderBy('menuName')" id="menuName_order">
									    <div class="important">
									    <div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
										</div>
										</th>
									<th class="sorting colwidth-7" onclick="orderBy('menuType')" id="menuType_order">
									<div class="font-size-12 line-height-18">菜单类型</div>
										<div class="font-size-9 line-height-18">Menu Type</div></th>
									<th class="sorting colwidth-15" onclick="orderBy('parentId')" id="parentId_order">
									<div class="font-size-12 line-height-18">父菜单名称</div>
										<div class="font-size-9 line-height-18"> Parent Menu</div></th>
								    <th class="sorting colwidth-10" onclick="orderBy('menuOrder')" id="menuOrder_order">
								    <div class="font-size-12 line-height-18">菜单顺序号</div>
										<div class="font-size-9 line-height-18">Menu Order No.</div></th>
									<th class="sorting colwidth-7" onclick="orderBy('path')" id="path_order">
									<div class="font-size-12 line-height-18">访问路径</div>
										<div class="font-size-9 line-height-18">Path</div></th>	
									<th class="sorting colwidth-30" onclick="orderBy('remark')" id="remark_order">
									<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></th>	
									<th class="sorting colwidth-10" onclick="orderBy('fullOrder')" id="fullOrder_order">
									<div class="font-size-12 line-height-18">模块排序号</div>
										<div class="font-size-9 line-height-18"> Module Order No.</div></th>
									<th class="sorting colwidth-10" onclick="orderBy('iconPath')" id="iconPath_order">
									<div class="font-size-12 line-height-18">图标路径</div>
										<div class="font-size-9 line-height-18">Icon Path</div></th>			
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
							
						</table>
					</div>
					
					<div class="col-xs-12 page-height text-center" id="pagination">
					</div>
					<div class="clearfix"></div>
				</div>
			</div>

			<div class="panel panel-primary" id="userManage"
				style="display: none;">
				<div class="panel-heading  padding-top-3 padding-bottom-1">
					<div class="font-size-16 line-height-18">菜单管理</div>
					<div class="font-size-9 ">Menu Management</div>
				</div>

				<div class="panel-body">
					<ul class="nav nav-tabs">
						<li id="li_userauth" class="active"><a href="#tab_userauth" data-toggle="tab"
							onclick="edit()"><div
									class="font-size-16 line-height-18">权限</div>
								<div class="font-size-9 ">Authority</div></a></li>
					</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab_userauth">
					<div style="margin-top: 10px; margin-bottom: 20px">
						 <a href="javascript:void(0)" data-toggle="modal"
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left"
						onclick="alert('')"
						style="float: left; margin-left: 10px;"><div
							class="font-size-12">保存</div>
						<div class="font-size-9">Save</div></a>
					</div>
						<div class="col-lg-12 col-md-12 padding-left-0"
							style="overflow: auto; border-right: 1px solid #ddd;">
							<div id="userauthpanel"></div>
						</div>
					</div>
				</div>
			</div>
	</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/sys/menulist.js"></script>
</body>
</html>
