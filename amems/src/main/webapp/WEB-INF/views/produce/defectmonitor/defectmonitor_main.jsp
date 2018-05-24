<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>缺陷保留监控</title>
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
var paramJgdm = '${param.dprtcode}';
var paramFjzch = '${param.fjzch}';
</script>

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
					search();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>

<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content table-table-type">
	<input type="hidden" id="userid" value="${user.id}">
	<input type="hidden" id="id"  />
	<input type="hidden" id="djzt"  />
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			  <div  class='searchContent row-height' >
				<div class='margin-top-0'>
					<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">飞机注册号</div>
							<div class="font-size-9 ">A/C REG</div>
						</span>
						<div id="jxdiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="fjzch"  class="form-control " onchange="search()">
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">申请人</div>
							<div class="font-size-9">Applicant</div>
						</span>
						<div id="pgridDiv" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class='form-control' id="sqr1" />
						</div>
					</div>
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='保留单号/缺陷描述'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="search();" style='margin-right:0px !important;'>
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="openOrHide();">
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
					<div class="clearfix"></div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">申请日期</div>
							<div class="font-size-9">Application Date</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly id="sqrqDate" />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">修复期限</div>
							<div class="font-size-9">Repair Date</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly id="xfqxDate" />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="onchangeDprtcode()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-lg-3  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>
			<div class='table_pagination'>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" c-height="55%" id="tableId" style="overflow-x: auto;">
					<table  id="defrctkeepTable" class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th width='50' class="fixed-column">
									<div class="font-size-12 line-height-18">标识</div>
									<div class="font-size-9 line-height-18">Mark</div>
								</th>
								<th  class="fixed-column sorting  colwidth-15" onclick="orderBy('bldh')" id="bldh_order">
									<div class="important"><div class="font-size-12 line-height-18">保留单编号</div>
									<div class="font-size-9 line-height-18">DD No.</div></div>
								</th>
								<th class="sorting  colwidth-10" onclick="orderBy('fjzch')" id="fjzch_order">
									<div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C REG</div>
								</th>
								<th class="sorting  colwidth-18" onclick="orderBy('GDBH')" id="GDBH_order">
									<div class="font-size-12 line-height-18">来源工单</div>
									<div class="font-size-9 line-height-18">W/O</div>
								</th>
								<th  class="sorting  colwidth-13" onclick="orderBy('qxms')" id="qxms_order">
									<div class="important">
										<div class="font-size-12 line-height-18">缺陷描述</div>
										<div class="font-size-9 line-height-18">Desc</div>
									</div>
								</th>	
								<th  class=" colwidth-10"  >
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">File</div>
								</th>
								<th  class="sorting  colwidth-15" onclick="orderBy('sqr')" id="sqr_order">
									<div class="font-size-12 line-height-18">申请人</div>
									<div class="font-size-9 line-height-18">Applicant</div>
								</th>	
								<th  class="sorting  colwidth-15" onclick="orderBy('sqrq')" id="sqrq_order">
									<div class="font-size-12 line-height-18">申请日期</div>
									<div class="font-size-9 line-height-18">Application  Date</div>
								</th>	
								<th  class="sorting  colwidth-15" onclick="orderBy('xfqx')" id="xfqx_order">
									<div class="font-size-12 line-height-18">修复期限</div>
									<div class="font-size-9 line-height-18">Repair Limit</div>
								</th>	
								<th  class="sorting  colwidth-10" onclick="orderBy('pzyj')" id="pzyj_order">
									<div class="font-size-12 line-height-18">授权工程师意见</div>
									<div class="font-size-9 line-height-18">Opinion</div>
								</th>
								<th  class="sorting  colwidth-15" onclick="orderBy('yxxz')" id="yxxz_order">
									<div class="font-size-12 line-height-18">飞行运行限制</div>
									<div class="font-size-9 line-height-18">Flight Limit</div>
								</th>
								<th  class="sorting  colwidth-18" onclick="orderBy('gdid')" id="gdid_order">
									<div class="font-size-12 line-height-18">下达NRC</div>
									<div class="font-size-9 line-height-18">NRC</div>
								</th>
								<th class="sorting  colwidth-7" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
						 		<th class="sorting  colwidth-10" onclick="orderBy('gbyy')" id="gbyy_order">
						 			<div class="font-size-12 line-height-18">关闭保留缺陷措施</div>
									<div class="font-size-9 line-height-18">Measure</div>
								</th> 
						 		<th class="sorting  colwidth-13" onclick="orderBy('gzz')" id="gzz_order">
						 			<div class="font-size-12 line-height-18">工作者</div>
									<div class="font-size-9 line-height-18">Worker</div>
								</th> 
								<th  class="sorting  colwidth-13" onclick="orderBy('gzrq')" id="gzrq_order" >
									<div class="font-size-12 line-height-18">完成日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th  class="sorting  colwidth-13" onclick="orderBy('whrid')" id="whrid_order">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th  class="sorting  colwidth-15" onclick="orderBy('whsj')" id="whsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th  class="colwidth-13">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="defrctkeep_list">
						</tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
				</div>
				<div class='clearfix'></div>
			</div>	
		</div>
	</div>
</div>	
<!--日志STATR -->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<!--日志END  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/defectmonitor/defectmonitor_main.js"></script><!--当前界面js  -->
<%@ include file="/WEB-INF/views/produce/failurekeep/filesDown.jsp" %> <!--附件列表界面  -->
<%@ include file="/WEB-INF/views/produce/defectkeep/shutDown.jsp" %> <!--关闭 -->
<%@ include file="/WEB-INF/views/produce/projectkeep/Confirm.jsp"%><!-- 完成 -->
</body>
</html>
