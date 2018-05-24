<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>合同管理</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<style type="text/css">
		.spacing {
			margin-left:3px;
			margin-right:3px;
		}
	</style>
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
<div class="page-content" id="mgnt_main">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class=" panel-body padding-bottom-0" >
			<div class='searchContent margin-top-0 row-height'>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<div class="btn-group">
						    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" style='height:34px;' onclick='mgnt_main.openWinAdd(10)'>
						    	<div class="font-size-12" style=''>采购</div>
								<div class="font-size-9 " style='margin-top:0px;'>Purchase</div>
                            </button>
						    <button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-new" data-toggle="dropdown" >
						       <i class="font-size-15 icon-caret-down" id="icon"></i>
						    </button>
						    <ul class="dropdown-menu text-left dropdown-toggle-menu-new" >
								<li ><a href="JavaScript:mgnt_main.openWinAdd(20)" >修理</a></li>
								<li ><a href="JavaScript:mgnt_main.openWinAdd(31)" >租进</a></li>
								<li ><a href="JavaScript:mgnt_main.openWinAdd(32)" >租出</a></li>
								<li ><a href="JavaScript:mgnt_main.openWinAdd(40)" >交换</a></li>
								<li ><a href="JavaScript:mgnt_main.openWinAdd(50)" >出售</a></li>
							</ul>
						</div> 
						
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">合同类型</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							
							<select class='form-control' id='htlx_search' onchange="mgnt_main.changeDprt()">
								<option value="" selected="selected">显示全部All</option>
								<c:forEach items="${contractMgntTypeEnum}" var="item">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
							</select>
							
							
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">合同日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<input type='text' id='htrq_search' class='form-control' name='date-range-picker' />
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='合同号/相关方/备注'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="workCardMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="mgnt_main.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="mgnt_main.more();">
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
							<select id="dprtcode_search" class="form-control" onchange="mgnt_main.changeDprt()" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div id="zt_div" class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id='zt_search' class='form-control'>
						    </select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="mgnt_main.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <!-- 需求追踪 -->
		   <div  class='table_pagination'>
		   <div id="mgnt_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" c-height="40%" style="overflow-x: auto;">
					<table id="mgnt_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='colwidth-7 sorting' onclick="mgnt_main.orderBy('zt','')" name="column_zt">
									<div class="font-size-12 line-height-18" >状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class='colwidth-7 sorting' onclick="mgnt_main.orderBy('htlx')" name="column_htlx">
									<div class="font-size-12 line-height-18">合同类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class='colwidth-10 sorting' onclick="mgnt_main.orderBy('hth')" name="column_hth" >
									<div class="important">
									    <div class="font-size-12 line-height-18">合同号</div>
										<div class="font-size-9 line-height-18">Contract No.</div>
									</div>
							    </th>
								<th class='colwidth-9 sorting' onclick="mgnt_main.orderBy('htrq')" name="column_htrq" >
									<div class="font-size-12 line-height-18">合同日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">币种</div>
									<div class="font-size-9 line-height-18">Currency</div>
							   </th>
							   <th class='colwidth-13 sorting' onclick="mgnt_main.orderBy('jffs')" name="column_jffs" >
									<div class="font-size-12 line-height-18">交付方式</div>
									<div class="font-size-9 line-height-18">Delivery Mode</div>
							   </th>
							   <th class='colwidth-13 sorting' onclick="mgnt_main.orderBy('xgfms')" name="column_xgfms" >
							   		<div class="important">
										<div class="font-size-12 line-height-18">相关方</div>
										<div class="font-size-9 line-height-18">Relevant Party</div>
									</div>
							   </th>
							   <th class='colwidth-13 sorting' onclick="mgnt_main.orderBy('htje')" name="column_htje" >
									<div class="font-size-12 line-height-18">合同金额</div>
									<div class="font-size-9 line-height-18">Contract Amount</div>
							   </th>
							   <th class='colwidth-15 sorting' onclick="mgnt_main.orderBy('yjfje')" name="column_yjfje" >
									<div class="font-size-12 line-height-18">已交付金额</div>
									<div class="font-size-9 line-height-18">Paid Amount</div>
							   </th>
							   <th class='colwidth-30 sorting' onclick="mgnt_main.orderBy('bz')" name="column_bz" >
							   		<div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Note</div>
									</div>
							   </th>
							   <th class='colwidth-9' >
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div>
							   </th>
							   <th class='colwidth-13 sorting' onclick="mgnt_main.orderBy('username')" name="column_username" >
									<div class="font-size-12 line-height-18">编制人</div>
									<div class="font-size-9 line-height-18">Compactor</div>
							   </th>
							   <th class='colwidth-15 sorting' onclick="mgnt_main.orderBy('whsj')" name="column_whsj" >
									<div class="font-size-12 line-height-18">编制时间</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class="colwidth-15" >
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
							   </th>
							</tr> 
						</thead>
						<tbody id="mgnt_main_table_list">
							</tr>
							
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="mgnt_main_table_pagination"></div>
				<div class='clearfix'></div>
				</div>
				<!-- 底部显示内容 -->
				<div id='bottom_hidden_content' class='displayContent col-xs-12  padding-left-0 padding-right-0 padding-top-0 ' style='display:none;'>
				 <div id="hideIcon" class="pull-right" style='height:1px;padding-right:8px;margin-top:1px;'>
						<img src="${ctx}/static/images/down.png" onclick='mgnt_main.hideBottom()' style="width:33px;cursor:pointer;">
					</div>
				<ul id="myChildTab" class="nav nav-tabs tabNew-style" style="padding-top:0px !important;">
			    <li class="active" >
				<a id='mgntDetail' href='#mgntDetailTab' data-toggle="tab"  >
					<div class="font-size-12 line-height-12">明细</div>
                    <div class="font-size-9 line-height-9">Detail</div>
                 </a>
				</li>
				<li class=""  >
				<a href='#mgntPaymentTab' data-toggle="tab"  >
					<div class="font-size-12 line-height-12">收付款</div>
                    <div class="font-size-9 line-height-9">Feedback</div>
                  </a>
				</li>
				</ul>
				<div id="" class="tab-content" style='padding:0px;'>
					<div id="mgntDetailTab" class="tab-pane fade active in " style="" >
					     <!-- 合同明细 -->
						<%@ include file="/WEB-INF/views/material2/contract/mgnt/contract_detail.jsp" %>
					</div>
					<div id="mgntPaymentTab" class="tab-pane fade">
					<%@ include file="/WEB-INF/views/material2/contract/mgnt/mgnt_payment.jsp" %>
					</div>
				</div>
				</div>
		   </div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/material2/contract/mgnt/mgnt_add.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/contract/mgnt/mgnt_main.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>