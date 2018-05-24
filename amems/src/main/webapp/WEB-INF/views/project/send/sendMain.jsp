<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维护提示传阅</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">

<input type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode);
});
</script>
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
					
					<div class=" pull-right padding-left-0 padding-right-0">	
				
						<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">状态</div>
								<div class="font-size-9">State</div>
							</span>
							<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="zt" class="form-control "  name="zt" >
									<option value="">显示全部</option>
									<option value="0">未接收</option>
									<option value="1">已接收</option>
								</select>
							</div>
						</div>	
						
						
						<!-- 搜索框start -->
						<div class=" pull-right padding-left-10 padding-right-0 row-height">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='通告编号'  id="keyword_search" class="form-control ">
							</div>
		                    <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                   		</button>
		                    </div> 
						</div>
						<!-- 搜索框end -->
					</div>
					
					<div class="clearfix"></div>

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" >
						<table id="quality_check_list_table"
							class="table table-thin table-bordered table-striped table-hover text-center table-set"
							>
							<thead>
							<tr>
							<th  class="sorting colwidth-5" onclick="orderBy('mainid')" id="mainid_order"><div class="important"><div class="font-size-12 line-height-18" >维护提示编号</div><div class="font-size-9 line-height-18" >Technical Bulletin No.</div></div></th>
							<th class="sorting colwidth-5" onclick="orderBy('zt')" id="zt_order"><div class="font-size-12 line-height-18" >状态</div><div class="font-size-9 line-height-18" >State</div></th>
							
							<th class="sorting colwidth-5" onclick="orderBy('dyzt')" id="dyzt_order"><div class="font-size-12 line-height-18" >打印状态 </div><div class="font-size-9 line-height-18" >Print State</div></th>
							<th class="sorting colwidth-5" onclick="orderBy('zdrid')" id="zdrid_order"><div class="font-size-12 line-height-18" >制单人 </div><div class="font-size-9 line-height-18" >Creator</div></th>
							<th class="sorting colwidth-5" onclick="orderBy('zdsj')" id="zdsj_order"><div class="font-size-12 line-height-18" >制单时间 </div><div class="font-size-9 line-height-18" >Create Time</div></th>
							<th class="sorting colwidth-5" onclick="orderBy('dprtcode')" id="dprtcode_order"><div class="font-size-12 line-height-18" >组织机构</div><div class="font-size-9 line-height-18" >Organization</div></th>
							</tr> 
			         		 </thead>
							<tbody id="Annunciatelist">
							</tbody>
							
						</table>
					</div>
					
				
				<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
	</div>
	
	<script type="text/javascript" src="${ctx }/static/js/thjw/project/send/sendList.js"></script>
</body>
</html>