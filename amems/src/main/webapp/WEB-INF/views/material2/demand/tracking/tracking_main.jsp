<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>需求跟踪</title>
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
						$('#workCardMainSearch').trigger('click'); //调用主列表页查询
					}
				}
			});
		});
	</script>
	<style>
	.tracking_main{
	border:1px solid #d5d5d5;
	padding-top:5px;
	padding-bottom:5px;
	margin-bottom:5px;
	margin-top:5px;
	}
	.tracking_first_content{
	border-bottom:1px solid #d5d5d5;
	padding-left:3px;
	padding-right:3px;
	}
	.tracking_label{
	height:25px;
	line-height:25px;
	font-weight:normal;
	}
	.tracking_second_content{
	padding-left:3px;
	padding-right:3px;
	}
	.margin-left-3{
	margin-left:3px;
	}
	</style>
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
					<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
						<div class="col-xs-12 input-group input-group-search">
				            <div class="input-group-addon btn-searchnew">
		                    	<a href="" onclick="tracking.add()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left " >
									<div class="font-size-12">新增</div>
									<div class="font-size-9">Add</div>
								</a> 
		                    </div>
					        <div class="input-group-addon btn-searchnew">
		                    	<div style="color:#333;text-align:right;padding-right:5px;margin-left:15px;">
		                    	<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9 " style="line-height:15px;margin-top:1px;">A/C Reg</div>
		                    	</div>
		                    </div>
					        <div id="fjzchDiv">
								<select  id="fjzch"  class="form-control " onchange="tracking.search()">
								</select>
							</div>
		               </div>
					</div>
					
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">状态</div>
							<div class="font-size-9 ">Status</div>
						</span>
						<div id="ztDiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
							<select id="zts" class="form-control" onchange="tracking.search()">
								<option value="">显示全部</option>
								<option value="1">保存</option>
								<option value="2">提交</option>
								<option value="4">已审批</option>
								<option value="6">审批驳回</option>
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">需求类别</div>
							<div class="font-size-9">Type</div>
						</span>
						<div id="xqlbDiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0 label-input-div" >
							<select id="xqlb" class="form-control " onchange="tracking.search()" >
							</select>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='编号/需求原因'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="workCardMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="tracking.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="moreSearch();">
								<div class='input-group'>
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
							<div class="font-size-12">影响放行</div>
							<div class="font-size-9">Release</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input name="isYxfx_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="1" checked="checked" />&nbsp;是
								&nbsp;
							<input name="isYxfx_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="0"  checked="checked"  />&nbsp;否 
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">非计划停场</div>
							<div class="font-size-9">Unplanned P</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input name="isFjhtc_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="1" checked="checked" />&nbsp;是
								&nbsp;
							<input name="isFjhtc_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="0"  checked="checked"  />&nbsp;否 
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">计划使用日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' id='jhsyrq_search' class='form-control' name='date-range-picker'/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">提报日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' id='sqsj_search' class='form-control' name='date-range-picker'/>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="tracking.onchangeDprtcode()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="tracking.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <!-- 需求追踪 -->
		   <div class="col-lg-12 col-md-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0" id="" >
			   <div class="col-sm-3 col-xs-12 padding-left-0 padding-right-0"  id='leftDiv' > 
			    <div class="panel panel-primary" style='margin-bottom:0px;'>
			   <div class="panel-heading">
			     <div class="font-size-12 line-height-12">跟踪信息</div>
			     <div class="font-size-9 ">Tracking Info</div>
               </div>
		       <div class="panel-body padding-top-0" style='padding-left:3px;padding-right:3px;' id="tracking_list">
		       
		       </div>
		       <div class="panel-footer" >
				<div class="col-xs-12 padding-left-0 padding-right-0 text-center" >
					<a href='javascript:closedDemand()'>已关闭需求</a>
				</div>
				<div class='clearfix'></div>
				</div>
		     </div>
		     </div>
			   <div class="col-lg-9 col-md-9 col-xs-9 padding-right-0"  id='rightDiv'>
			   <div id="" class="toggleLeftRight" style='left:2px;'>
		       <i class="cursor-pointer icon-caret-left" onclick="toggleIcon(this)"></i>
		       </div>
			   <div class="panel panel-primary" id=''>
			   <div class="panel-heading">
			     <div class="font-size-12 line-height-12">需求明细信息</div>
			     <div class="font-size-9 ">Demand Details Info</div>
               </div>
		       <div class="panel-body">
		       <div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" c-height='65%' id="tableId"  style="overflow-x: auto;">
					    <table id="tracking_table" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
								<th  class='colwidth-5'>
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='colwidth-7' >
									<div class="font-size-12 line-height-18" >需求标识</div>
									<div class="font-size-9 line-height-18">Mark</div>
								</th>
								<th class='colwidth-12 sorting' onclick="tracking.orderBy('bjh')" id="bjh_order">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
								</th>
								<th class='colwidth-12 sorting' onclick="tracking.orderBy('bjmc')" id="bjmc_order">
									<div class="font-size-12 line-height-18">名称</div>
									<div class="font-size-9 line-height-18">Name</div>
								</th>
								<th class='colwidth-7 sorting' onclick="tracking.orderBy('xqsl')" id="xqsl_order">
									<div class="font-size-12 line-height-18">需求数量</div>
									<div class="font-size-9 line-height-18">QTY</div>
								</th>
								<th class='colwidth-7' onclick="" name="" >
									    <div class="font-size-12 line-height-18">库存可用数</div>
										<div class="font-size-9 line-height-18">QTY</div>
							    </th>
								<th class='colwidth-9 sorting' onclick="tracking.orderBy('bzzt')" id="bzzt_order">
									<div class="font-size-12 line-height-18">保障状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class='colwidth-20 sorting' onclick="tracking.orderBy('bzbz')" id="bzbz_order">
										<div class="font-size-12 line-height-18">保障备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
							   </th>
								<th class='colwidth-10 sorting' onclick="tracking.orderBy('wllb')" id="wllb_order">
										<div class="font-size-12 line-height-18">物料类别</div>
										<div class="font-size-9 line-height-18">Type</div>
							   </th>
								<th class='colwidth-12 sorting' onclick="tracking.orderBy('jhsyrq')" id="jhsyrq_order">
										<div class="font-size-12 line-height-18">计划使用日期</div>
										<div class="font-size-9 line-height-18">Date</div>
							   </th>
								<th class='colwidth-9 sorting' onclick="tracking.orderBy('xingh')" id="xingh_order">
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class='colwidth-15 sorting' onclick="tracking.orderBy('gg')" id="gg_order">
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Spec</div>
								</th>
								<th class='colwidth-9 sorting' onclick="tracking.orderBy('xlh')" id="xlh_order">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">SN</div>
								</th>
								<th class='colwidth-9 sorting' onclick="tracking.orderBy('jhly')" id="jhly_order">
									<div class="font-size-12 line-height-18">件号来源</div>
									<div class="font-size-9 line-height-18">Original</div>
								</th>
								<th class='colwidth-15 sorting' onclick="tracking.orderBy('zjh')" id="zjh_order">
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</th>
								<th class='colwidth-9 sorting' onclick="tracking.orderBy('bzjh')" id="bzjh_order">
									<div class="font-size-12 line-height-18">标准件号</div>
									<div class="font-size-9 line-height-18">Normal PN</div>
								</th>
								<th class='colwidth-9 sorting' onclick="tracking.orderBy('thj')" id="thj_order">
									<div class="font-size-12 line-height-18">替代件</div>
									<div class="font-size-9 line-height-18">Replace PN</div>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18">替代可用数</div>
									<div class="font-size-9 line-height-18">Replace PN</div>
								</th>
								<th class='colwidth-12 sorting' onclick="tracking.orderBy('bh')" id="bh_order">
									<div class="font-size-12 line-height-18">需求编号</div>
									<div class="font-size-9 line-height-18">Demand No.</div>
								</th>
							</tr> 
						</thead>
						<tbody id="tracking_info_list"> 
							
							
						</tbody>
					</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="tracking_pagination">
					</div>
					<div class='clearfix'></div>
					<div class='displayContent' style='display:none;' id='bottom_hidden_content'>
					<%@ include file="/WEB-INF/views/material2/demand/tracking/tracking_log.jsp"%>
					</div>
			   </div>
		   </div>
		   
		</div>
	</div>
</div>
<!-- 已关闭需求 -->
<%@ include file="/WEB-INF/views/material2/demand/tracking/tracking_open.jsp"%>
<script type="text/javascript" src="${ctx }/static/js/thjw/material2/demand/tracking/tracking_main.js"></script>
<script type="text/javascript" src="${ctx }/static/js/thjw/material2/demand/tracking/demand_tracking_main.js"></script>
</body>
</html>