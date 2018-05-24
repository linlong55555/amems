<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>退料申请</title>
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
						$('#applyMainSearch').trigger('click'); //调用主列表页查询
					}
				}
			});
		});
	</script>	
		<script>
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
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
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick='applyOpen()'>
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</button>
                    </div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">退料日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<input type='text' id='tlrq_search' class='form-control' name='date-range-picker'/>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					    <div class='text-right'>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input id="isSh1" checked="checked" style="vertical-align:text-bottom;" type="checkbox">&nbsp;待收货&nbsp;&nbsp;
						</label>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input id="isSh2" style="vertical-align:text-bottom;" type="checkbox">&nbsp;已收货&nbsp;&nbsp;
						</label>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='部件号/部件名称/批次号/序列号/说明'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="applyMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="applyMain.reload();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="moreSearch();">
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
							<div class="font-size-12">部件来源</div>
							<div class="font-size-9">PN Origin</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id="bjly_search">
								<option value="">显示全部 All</option>
								<option value=1>库房</option>
								<option value=2>飞机</option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C Reg</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="fjzch_search">
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">是否可用</div>
							<div class="font-size-9">Available</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="sfky_search">
								<option value="">显示全部 All</option>
								<option value="0">不可用</option>
								<option value="1">可用</option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="dprtcode_search"  name="dprtcode" onchange="dprtChange()">
								<c:forEach items="${accessDepartment}" var="type" >
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				    <div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <!-- 需求追踪 -->
		   <div  class='table_pagination'>
		   <div id="work_card_main_table_top_div" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="meteriallist_table" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th  class='fixed-column colwidth-5'>
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='colwidth-9 sorting' onclick="orderBy('tlrq')" id="tlrq_order">
									<div class="font-size-12 line-height-18" >日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class='colwidth-9 sorting' onclick="orderBy('whrid')" id="whrid_order">
									<div class="font-size-12 line-height-18">退料人</div>
									<div class="font-size-9 line-height-18">Retreating person</div>
								</th>
								<th class='colwidth-9 sorting' onclick="orderBy('bjh')" id="bjh_order">
									 <div class="important">
									    <div class="font-size-12 line-height-18">部件号</div>
										<div class="font-size-9 line-height-18">PN</div>
									 </div>
							    </th>
								<th class='colwidth-15 sorting' onclick="orderBy('bjmc')" id="bjmc_order">
									 <div class="important">
										<div class="font-size-12 line-height-18">部件名称</div>
										<div class="font-size-9 line-height-18">PN name</div>
									</div>
								</th>
								<th class='colwidth-9 sorting' onclick="orderBy('pch')" id="pch_order">
									 <div class="important">
										<div class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">Batch No.</div>
									 </div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="orderBy('xlh')" id="xlh_order">
								   <div class="important">
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">SN</div>
								   </div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="orderBy('bjly')" id="bjly_order">
									<div class="font-size-12 line-height-18">部件来源</div>
									<div class="font-size-9 line-height-18">Original</div>
							   </th>
							   <th class='colwidth-6 sorting'onclick="orderBy('tlsl')" id="tlsl_order">
									<div class="font-size-12 line-height-18">退料数量</div>
									<div class="font-size-9 line-height-18">Amount</div>
							   </th>
							   <th class='colwidth-7 sorting' onclick="orderBy('sfky')" id="sfky_order">
									<div class="font-size-12 line-height-18">是否可用</div>
									<div class="font-size-9 line-height-18">Avaliable</div>
							   </th>
							   <th class='colwidth-20 sorting' onclick="orderBy('sm')" id="sm_order">
								    <div class="important">
										<div class="font-size-12 line-height-18">说明</div>
										<div class="font-size-9 line-height-18">Instruction</div>
									</div>
							   </th>
							    <th class='colwidth-40 '>
									<div class="font-size-12 line-height-18">收货信息</div>
									<div class="font-size-9 line-height-18">Info </div>
							   </th>
							</tr> 
						</thead>
						<tbody id="apply_list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="apply_pagination"></div>
				</div>
		   </div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/material2/return/apply/apply_open.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/materialAndOutfield_modal.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/return/apply/apply_main.js"></script>
	</body>
</html>