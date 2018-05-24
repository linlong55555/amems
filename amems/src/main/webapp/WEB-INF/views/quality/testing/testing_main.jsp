<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>航材/工具检验</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
	<script type="text/javascript">
		var id = "${param.id}";
		var pageParam = '${param.pageParam}';
		var todo_ywid = "${param.todo_ywid}";
		var todo_jd = "${param.todo_jd}";
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
							<input type="text" id="shrq_search" class="form-control" name="date-range-picker" onchange="testing.search();"/>
						</div>
					</div>
					<div id="ztDiv" class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
				          <div class="pull-right">
						 	<label class="padding-left-5" style="margin-left:5px;margin-top:6px;font-weight:normal;" onchange="testing.search();">
								<input name="zt_search" style="vertical-align:text-bottom" checked="checked" type="checkbox" value="1">&nbsp;待检&nbsp;&nbsp;
							</label>
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;" onchange="testing.search();">
								<input name="zt_search" style="vertical-align:text-bottom;" type="checkbox" value="2">&nbsp;已检&nbsp;&nbsp;
							</label>
						</div>
				   </div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='检验单/部件号/部件名称/批次号/序列号/收货单号/结果说明'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="aircraftinfoMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="testing.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="testing.moreSearch();">
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
							<div class="font-size-12">检验日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' id="jyrq_search" class='form-control' name="date-range-picker"/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">检验结果</div>
							<div class="font-size-9">Result</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="jyjg_search" class='form-control' >
								<option value="">显示全部</option>
								<option value="1" >合格Qualified</option>
								<option value="2">不合格Unqualified</option>
								<option value="3">让步接收Compromise</option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="onchangeDprtcode()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="testing.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <!-- 需求追踪 -->
		   <div  class='table_pagination'>
		   <div id="" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="testing_table" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th class='colwidth-5'>
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='colwidth-5 sorting' onclick="testing.orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class='colwidth-10 sorting' onclick="testing.orderBy('jydh')" id="jydh_order">
								<div class="important">
									    <div class="font-size-12 line-height-18">检验单号</div>
										<div class="font-size-9 line-height-18">Check No.</div>
										</div>
							    </th>
								<th class='colwidth-9 sorting' onclick="testing.orderBy('bjh')" id="bjh_order">
									<div class="important">
									<div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-18">PN</div>
									</div>
								</th>
								<th class='colwidth-15 sorting' onclick="testing.orderBy('zwms')" id="zwms_order">
								<div class="important">
										<div class="font-size-12 line-height-18">部件名称</div>
										<div class="font-size-9 line-height-18">PN name</div>
										</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="testing.orderBy('pch')" id="pch_order">
							   <div class="important">
									<div class="font-size-12 line-height-18">批次号</div>
									<div class="font-size-9 line-height-18">Batch No.</div>
									</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="testing.orderBy('sn')" id="sn_order">
							   <div class="important">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">SN</div>
									</div>
							   </th>
							   <th class='colwidth-5 sorting' onclick="testing.orderBy('kysl')" id="kysl_order">
									<div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Qty</div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="testing.orderBy('shdh')" id="shdh_order">
							   	<div class="important">
									<div class="font-size-12 line-height-18">收货单</div>
									<div class="font-size-9 line-height-18">Receipt</div>
									</div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="testing.orderBy('shrq')" id="shrq_order">
									<div class="font-size-12 line-height-18">收货日期</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class='colwidth-15'>
									<div class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Position</div>
							   </th>
							   <th class='colwidth-9' >
									<div class="font-size-12 line-height-18">产权</div>
									<div class="font-size-9 line-height-18">Right</div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="testing.orderBy('jyrq')" id="jyrq_order">
									<div class="font-size-12 line-height-18">检验日期</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class='colwidth-20 sorting' onclick="testing.orderBy('jyjg')" id="jyjg_order">
									<div class="font-size-12 line-height-18">检验结果</div>
									<div class="font-size-9 line-height-18">Result</div>
							   </th>
							   <th class='colwidth-20 sorting' onclick="testing.orderBy('jgsm')" id="jgsm_order">
							   <div class="important">
									<div class="font-size-12 line-height-18">结果说明</div>
									<div class="font-size-9 line-height-18">Description</div>
									</div>
							   </th>
							   <th class='colwidth-12 ' >
									<div class="font-size-12 line-height-18">检验人</div>
									<div class="font-size-9 line-height-18">Inspector</div>
							   </th>
							   <th class='colwidth-12' >
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Guardian</div>
							   </th>
							   <th class='colwidth-13' >
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							  
							</tr> 
						</thead>
						<tbody id="testing_list">
							
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="testing_pagination"></div>
				</div>
		   </div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/quality/testing/testing_open.jsp"%><!--检验单弹出框  -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/testing/testing_main.js"></script><!--当前js  -->
</body>
</html>