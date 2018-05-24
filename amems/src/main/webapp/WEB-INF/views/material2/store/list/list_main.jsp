<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>盈亏历史</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
	<script type="text/javascript">
		$(document).ready(function(){
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
					}else{
						$('#aircraftinfoMainSearch').trigger('click'); //调用主列表页查询
					}
				}
			});
		});
	</script>	
</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div id="list_main" class="page-content">
<input id="type" type="hidden" value="${type}" />
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class=" panel-body padding-bottom-0" >
			<div class='searchContent margin-top-0 row-height' >
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission"  onclick='list_main.exportExcel()'>
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</button>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">产权</div>
							<div class="font-size-9">Right</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<div class="input-group col-xs-12">
							<input id="cqid_search" type="hidden">
						    <input id="cqbh_search" class="form-control readonly-style" type="text" onchange="list_main.search();" ondblclick="list_main.openCqWin()" readonly>
							<span id="cq_search_btn" class="input-group-addon btn btn-default" onclick="list_main.openCqWin()">
		                    		<i class="icon-search"></i>
		                    </span>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">仓库</div>
							<div class="font-size-9">Warehouse</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<select id="ck_search" class="form-control" onchange="list_main.search();">
							</select>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='件号/序列号/名称/批次号/备注'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="workCardMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="list_main.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="list_main.more();">
								<div class='input-group input-group-search'>
								<div class="input-group-addon">
								<div class="font-size-12">更多</div>
								<div class="font-size-9 margin-top-5" >More</div>
								</div>
								<div class="input-group-addon">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
								</div>
					   			</button>
		                	</div>
						</div>
					</div>
					<!-- 搜索框end -->
				  <div class='clearfix'></div>
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">维护人</div>
							<div class="font-size-9">Maintainer</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input id="whr_search" type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">维护时间</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="zdrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control" onchange="list_main.changeDprt()" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="list_main.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <!-- 需求追踪 -->
		   <div class='table_pagination'>
		   <div id="list_main_table_top_div" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="list_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th class='colwidth-3'>
									<div class="font-size-12 line-height-18" >序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class='colwidth-9 sorting' onclick="list_main.orderBy('ykbs','')" name="column_ykbs" >
									<div class="font-size-12 line-height-18">盈亏</div>
									<div class="font-size-9 line-height-18">Profit and loss</div>
								</th>
								<th class='colwidth-5 sorting' onclick="list_main.orderBy('ykcsl')" name="column_ykcsl" >
									    <div class="font-size-12 line-height-18">原数量</div>
										<div class="font-size-9 line-height-18">Qty</div>
							    </th>
								<th class='colwidth-5 sorting' onclick="list_main.orderBy('kcsl')" name="column_kcsl" >
									<div class="font-size-12 line-height-18">盘点数量</div>
									<div class="font-size-9 line-height-18">Qty</div>
								</th>
								<th class='colwidth-13 sorting' onclick="list_main.orderBy('cqbh')" name="column_cqbh" >
										<div class="font-size-12 line-height-18">产权</div>
										<div class="font-size-9 line-height-18">Right</div>
							   </th>
							   <th class='colwidth-15 sorting' onclick="list_main.orderBy('bjh')" name="column_bjh" >
							   		<div class="important">
									<div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-18">PN</div>
									</div>
							   </th>
							   <th class='colwidth-20'>
							   	<div class="important">
									<div class="font-size-12 line-height-18">名称</div>
									<div class="font-size-9 line-height-18">Name</div>
									</div>
							   </th>
							   <th class='colwidth-30' onclick="" name="" >
									<div class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Position</div>
							   </th>
							   <th class='colwidth-30 sorting' onclick="list_main.orderBy('bz')" name="column_bz" >
							   	<div class="important">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Note</div>
									</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="list_main.orderBy('xlh')" name="column_xlh" >
							   	<div class="important">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">SN</div>
									</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="list_main.orderBy('pch')" name="column_pch" >
							   	<div class="important">
									<div class="font-size-12 line-height-18">批次号</div>
									<div class="font-size-9 line-height-18">Batch No.</div>
									</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="list_main.orderBy('username')" name="column_username" >
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
							   </th>
							   <th class='colwidth-13 sorting' onclick="list_main.orderBy('zdsj')" name="column_zdsj" >
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class="colwidth-15" >
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
							   </th>
							</tr> 
						</thead>
						<tbody id="list_main_table_list">
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="list_main_table_pagination"></div>
				</div>
		   </div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/store/list/list_main.js"></script>
	<%@ include file="/WEB-INF/views/common/produce/material_cq.jsp" %> <!-- 产权弹出框 -->
</body>
</html>