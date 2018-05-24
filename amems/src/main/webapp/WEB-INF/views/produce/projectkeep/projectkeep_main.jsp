<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>项目保留</title>
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
var gdId = "${param.gdId}";  //工单ID
var gdLx = "${param.gdLx}";  //工单来源  135、145
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
		  <div  class='searchContent  row-height' >
			<div class='margin-top-0'>
				<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
					<a href="javascript:Project_Add_Modal.open();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='produce:reservation:item:main:01'>
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a>
					<a href="javascript:exportExcel();"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left row-height checkPermission" permissioncode='produce:reservation:item:main:08'>
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a>
				</div>
				<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 ">飞机注册号</div>
						<div class="font-size-9 ">A/C REG</div>
					</span>
					<div id="jxdiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
						<select id="fjzch"  class="form-control " onchange="search()">
						</select>
					</div>
				</div>
				<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
					</span>
					<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
							<input type='checkbox' id="ztList" name="ztList" style='vertical-align:text-bottom;' onchange="search()" checked="checked" />
							&nbsp;仅显示未关闭
						</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='保留单号'  class="form-control" id="keyword_search" >
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
						<input type="text" class="form-control" name="date-range-picker" readonly id="sqrq1" />
					</div>
				</div>
				<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
					<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">到期日期</div>
						<div class="font-size-9">Due Date</div>
					</span>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<input type="text" class="form-control" name="date-range-picker" readonly id="blqx1" />
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
				<table  id="ProjectKeepTable" class="table table-thin table-bordered table-striped table-hover table-set">
					<thead>
						<tr>
							<th  class="fixed-column colwidth-5">
								<div class="font-size-12 line-height-18 ">操作</div>
								<div class="font-size-9 line-height-18">Operation</div>
							</th>
							<th class="fixed-column" width='50' >
								<div class="font-size-12 line-height-18">标识</div>
								<div class="font-size-9 line-height-18">Mark</div>
							</th>
							<th  class="fixed-column sorting  colwidth-15" onclick="orderBy('bldh')" id="bldh_order">
								<div class="important">
									<div class="font-size-12 line-height-18">保留单编号</div>
									<div class="font-size-9 line-height-18">WD No.</div>
								</div>
							</th>
							<th class="sorting  colwidth-10" onclick="orderBy('fjzch')" id="fjzch_order">
								<div class="font-size-12 line-height-18">飞机注册号</div>
								<div class="font-size-9 line-height-18">A/C REG</div>
							</th>
							<th class="sorting  colwidth-10" onclick="orderBy('sqrq')" id="sqrq_order">
								<div class="font-size-12 line-height-18">申请日期</div>
								<div class="font-size-9 line-height-18">Application Date</div>
							</th>
							<th  class="sorting  colwidth-13" onclick="orderBy('sqrid')" id="sqrid_order">
								<div class="font-size-12 line-height-18">申请人</div>
								<div class="font-size-9 line-height-18">Applicant</div>
							</th>	
							<th  class="sorting  colwidth-18" onclick="orderBy('GDBH')" id="GDBH_order">
								<div class="font-size-12 line-height-18">工单编号</div>
								<div class="font-size-9 line-height-18">W/O No.</div>
							</th>	
							<th  class="sorting  colwidth-10" onclick="orderBy('JH_JSRQ')" id="JH_JSRQ_order">
								<div class="font-size-12 line-height-18">计划完成日期</div>
								<div class="font-size-9 line-height-18">Date</div>
							</th>
							<th  class="sorting  colwidth-15" onclick="orderBy('GKBH')" id="GKBH_order">
								<div class="font-size-12 line-height-18">工卡编号</div>
								<div class="font-size-9 line-height-18">W/C No.</div>
							</th>
							<th class="sorting  colwidth-10" onclick="orderBy('GKBZ')" id="GKBZ_order">
								<div class="font-size-12 line-height-18">工卡定检间隔</div>
								<div class="font-size-9 line-height-18">Interval</div>
							</th>
							<th  class=" colwidth-10"  >
								<div class="font-size-12 line-height-18">附件</div>
								<div class="font-size-9 line-height-18">File</div>
							</th>
					 		<th class="sorting  colwidth-12" onclick="orderBy('blqx')" id="blqx_order">
					 			<div class="font-size-12 line-height-18">保留期限</div>
								<div class="font-size-9 line-height-18">Expiry Date Period</div>
							</th> 
					 		<th class="sorting  colwidth-13" onclick="orderBy('shr')" id="shr_order">
					 			<div class="font-size-12 line-height-18">审核人</div>
								<div class="font-size-9 line-height-18">Checked by</div>
							</th> 
							<th  class="sorting  colwidth-13" onclick="orderBy('shrq')" id="shrq_order" >
								<div class="font-size-12 line-height-18">审核日期</div>
								<div class="font-size-9 line-height-18">Date</div>
							</th>
							<th class="sorting  colwidth-13" onclick="orderBy('pzr')" id="pzr_order" >
								<div class="font-size-12 line-height-18">批准人</div>
								<div class="font-size-9 line-height-18">Approved By</div>
							</th>
							<th  class="sorting  colwidth-10" onclick="orderBy('pzrq')" id="pzrq_order">
								<div class="font-size-12 line-height-18">批准日期</div>
								<div class="font-size-9 line-height-18">Date</div>
							</th>
							<th  class="sorting  colwidth-7" onclick="orderBy('zt')" id="zt_order">
								<div class="font-size-12 line-height-18">状态</div>
								<div class="font-size-9 line-height-18">Status</div>
							</th>
						
							<th  class="sorting  colwidth-30" onclick="orderBy('gbyy')" id="gbyy_order">
								<div class="font-size-12 line-height-18">完成说明</div>
								<div class="font-size-9 line-height-18">Note</div>
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
					<tbody id="ProjectKeep_list">
					</tbody>
				</table>
			</div>	
			<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
			<div class='clearfix'></div>
			</div>	
		</div>
	</div>
</div>	
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/projectkeep/projectkeep_main.js"></script><!--当前界面js  -->
<%@ include file="/WEB-INF/views/produce/projectkeep/projectkeep_open.jsp" %> <!--新增编辑界面  -->
<%@ include file="/WEB-INF/views/produce/failurekeep/filesDown.jsp" %> <!--附件列表界面  -->
<%@ include file="/WEB-INF/views/produce/projectkeep/shutDown.jsp" %> <!--关闭 -->
<%@ include file="/WEB-INF/views/produce/projectkeep/Confirm.jsp"%><!-- 完成 -->
<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
<%@ include file="/WEB-INF/views/open_win/material_tools.jsp"%><!-------航材对话框 -------->
<%@ include file="/WEB-INF/views/open_win/select_sourceorder.jsp"%><!--工单选择弹窗-->
<%@ include file="/WEB-INF/views/open_win/select_flb.jsp"%><!--飞行记录本选择弹窗-->
</body>
</html>
