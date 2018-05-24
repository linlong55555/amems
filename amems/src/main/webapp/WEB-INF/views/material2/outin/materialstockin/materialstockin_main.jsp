<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>航材入库上架</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
	<script type="text/javascript">
	
		var todo_ywid = "${param.todo_ywid}";
		var todo_dprtcode = "${param.todo_dprtcode}";
	
		$(document).ready(function(){
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
					}else{
						materialStockIn.search(); //调用主列表页查询
					}
				}
			});
		});
	</script>	
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class=" panel-body padding-bottom-0" >
			<div class='searchContent margin-top-0 row-height' >
					
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">收货日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<input type='text' class='form-control date-range-picker' id="shrq_search"/>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					   <span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">物料类别</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<select class='form-control' id="hclx_search" onchange="materialStockIn.search();">
								<option value="" selected="true">显示全部All</option>
								<option value="1">航材</option>
							    <option value="4">化工品</option>
							    <option value="5">低值易耗品</option>
							    <option value="6">松散件</option>
							    <option value="0">其它</option>
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					   <span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">仓库</div>
							<div class="font-size-9">Store</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<select class='form-control' id="store_search" onchange="materialStockIn.search();">
							</select>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='部件号/部件名称/批次号/序列号/收货单号/检验单号'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="materialStockIn.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1" onclick="materialStockIn.moreSearch();">
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
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="dprtcode_search" onchange="materialStockIn.buildStore()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
				    <div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="materialStockIn.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <!-- 需求追踪 -->
		   <div class='table_pagination'>
		   <div class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="meteriallist_table" class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>                              
							<tr>
								<th class='colwidth-6'>
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >物料类别</div>
									<div class="font-size-9 line-height-18">Category</div>
								</th>
								<th class='colwidth-15'>
									<div class="important">
										<div class="font-size-12 line-height-18">部件号</div>
										<div class="font-size-9 line-height-18">PN</div>
									</div>
								</th>
								<th class='colwidth-20'>
									<div class="important">
										<div class="font-size-12 line-height-18">部件名称</div>
										<div class="font-size-9 line-height-18">PN Name</div>
									</div>
								</th>
								<th class='colwidth-10'>
									<div class="important">
										<div class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">Batch No.</div>
									</div>
							   </th>
							   <th class='colwidth-10'>
							   		<div class="important">
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">SN</div>
									</div>
							   </th>
								<th class='colwidth-7'>
								    <div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Number</div>
							    </th>
							    <th class='colwidth-11'>
									<div class="font-size-12 line-height-18">仓库</div>
									<div class="font-size-9 line-height-18">Store</div>
								</th>
								<th class='colwidth-10'>
									<div class="important">
										<div class="font-size-12 line-height-18">收货单</div>
										<div class="font-size-9 line-height-18">Receipt List</div>
									</div>
								</th>
							   <th class='colwidth-9'>
									<div class="font-size-12 line-height-18">收货日期</div> 
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class='colwidth-13'>
									<div class="font-size-12 line-height-18">收货人</div>
									<div class="font-size-9 line-height-18">Consignee</div>
							   </th>
							   <th class='colwidth-5'>
									<div class="font-size-12 line-height-18">收货类型</div>
									<div class="font-size-9 line-height-18">Type</div>
							   </th>
							   <th class='colwidth-10'>
							   		<div class="important">
										<div class="font-size-12 line-height-18">检查单号</div>
										<div class="font-size-9 line-height-18">Check No.</div>
									</div>
							   </th>
							   <th class='colwidth-13'>
									<div class="font-size-12 line-height-18">质检人</div>
									<div class="font-size-9 line-height-18">Quality check</div>
							   </th>
							   <th class='colwidth-9'>
									<div class="font-size-12 line-height-18">质检日期</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							</tr> 
						</thead>
						<tbody id="marterial_stockin_list">
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="marterial_stockin_pagination"></div>
				</div>
		   </div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/material2/outin/materialstockin/materialstockin_shelves.jsp"%>
	<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%><!-------证书-------->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/materialstockin/materialstockin_main.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
</body>
</html>