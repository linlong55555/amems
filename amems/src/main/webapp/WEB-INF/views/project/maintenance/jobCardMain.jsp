<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">

<input type="hidden" id="userId" name="userId" value=${user.id} />
<input type="hidden" id="userType" value=${user.userType} />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode);
});
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<!-- from start -->
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body">
				<a href="#" onclick="add()" data-toggle="modal"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission"
					permissioncode="project:jobCard:main:01"
					><div class="font-size-12">新增</div>
					<div class="font-size-9">Add</div>
				</a>
				
				<button id="batchReview" type="button" onclick="batchApproveWin(false);"  style="margin-left:10px"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"
					permissioncode="project:jobCard:main:05">
					<div class="font-size-12">批量审核</div>
					<div class="font-size-9">Review</div>
				</button>
				<button id="batchApprove" type="button" onclick="batchApproveWin(true);"  style="margin-left:10px"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"
					permissioncode="project:jobCard:main:06">
					<div class="font-size-12">批量批准</div>
					<div class="font-size-9">Approve</div>
				</button>
					
					<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input title="工卡编号/定检编号机型/工作主题/备注/制单人" placeholder="工卡编号/定检编号/机型/工作主题/备注/制单人" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!-- 搜索框end -->
				
				<div class="col-xs-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height " id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4  text-right padding-left-0 padding-right-0">
						<div class="font-size-12">有效性</div>
							<div class="font-size-9">Effectivity</div></span>
						<div class="col-xs-8  padding-left-8 padding-right-0">
							<select id="yxx" class="form-control " name="yxx">
								<option value="">显示全部</option>
								<option value="0">无效</option>
								<option value="1">有效</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt" class="form-control "  name="zt">
									<option value="">显示全部</option>
									<option value="0">保存</option>
									<option value="1">提交</option>
									<option value="2">已审核</option>
									<option value="3">已批准</option>
									<option value="4">中止(关闭)</option>
									<option value="5">审核驳回</option>
									<option value="6">批准驳回</option>
									 <!-- <option value="8">作废</option>  -->
									 <option value="9">指定结束</option> 
									<!-- <option value="10">完成</option> -->
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">专业</div>
							<div class="font-size-9">Skill</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zy" class="form-control " name="zy">
								<option value="">显示全部</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">工卡类型</div>
							<div class="font-size-9">JobCard Type</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="gklx" class="form-control " name="gklx">
								<option value="" >显示全部</option>
								<option value="1" >定检工卡</option>
								<option value="2" >非定检工卡</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">工作组</div>
							<div class="font-size-9">JobCard Type</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="gzzid" class="form-control " name="gzzid" >
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zzjg" class="form-control " name="zzjg" onchange="changeContent()">
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

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px;overflow-x: scroll;width: 100%;">
						<table id="quality_check_list_table"
							class="table table-thin table-bordered table-striped table-hover text-center table-set"  style="min-width:1800px !important"
							>
							<thead>
							<tr>
							<th class="fixed-column colwidth-5" style='text-align:center;vertical-align:middle'>
									<a href="#" onclick="performSelectAll('JobCardlistlist')" ><img src="${ctx}/static/assets/img/d1.png" title="全选" /></a>
									<a href="#" class="margin-left-5" onclick="performSelectClear('JobCardlistlist')" ><img src="${ctx}/static/assets/img/d2.png" title="不全选" /></a>
							</th>
							
							<th class="fixed-column colwidth-7"><div class="font-size-12 line-height-18" >操作</div><div class="font-size-9 line-height-18" >Operation</div></th>
							<th class="fixed-column sorting colwidth-13" onclick="orderBy('gdbh')" id="gdbh_order"><div class="important"><div class="font-size-12 line-height-18" >工卡编号</div><div class="font-size-9 line-height-18" >W/O No.</div></div></th>
							<th class="sorting colwidth-13" onclick="orderBy('djbh')" id="djbh_order"><div class="important"><div class="font-size-12 line-height-18" >定检编号</div><div class="font-size-9 line-height-18" >Fixed Check No.</div></div></th>
							<th class="sorting colwidth-13" onclick="orderBy('gklx')" id="gklx_order"><div class="font-size-12 line-height-18" >工卡类型</div><div class="font-size-9 line-height-18" >JobCard Type</div></th>
							<th class="sorting colwidth-13" onclick="orderBy('gzzid')" id="gzzid_order"><div class="font-size-12 line-height-18" >工作组</div><div class="font-size-9 line-height-18" >Work Group</div></th>
							<th class="sorting colwidth-5" onclick="orderBy('zy')" id="zy_order"><div class="font-size-12 line-height-18" >专业 </div><div class="font-size-9 line-height-18" >Skill</div></th>
							<th class="colwidth-13" ><div class="font-size-12 line-height-18" >标准工时 </div><div class="font-size-9 line-height-18" >MHRS</div></th>
							<th class="sorting colwidth-9" onclick="orderBy('jx')" id="jx_order"><div class="important"><div class="font-size-12 line-height-18" >机型 </div><div class="font-size-9 line-height-18" >Model</div></div></th>
							<th class="sorting colwidth-30" onclick="orderBy('gzzt')" id="gzzt_order"><div class="important"><div class="font-size-12 line-height-18" >工作主题</div><div class="font-size-9 line-height-18" >Subject</div></div></th>
							<th class="sorting colwidth-9" onclick="orderBy('yxx')" id="yxx_order"><div class="font-size-12 line-height-18" >有效性</div><div class="font-size-9 line-height-18" >Effectivity</div></th>
							<th class="sorting colwidth-30" onclick="orderBy('bz')" id="bz_order"><div class="important"><div class="font-size-12 line-height-18" >备注</div><div class="font-size-9 line-height-18" >Remark</div></div></th>
							<th class="sorting colwidth-7" onclick="orderBy('zt')" id="zt_order"><div class="font-size-12 line-height-18" >状态 </div><div class="font-size-9 line-height-18" >State</div></th>
							<th class="sorting colwidth-13" onclick="orderBy('zdrid')" id="zdrid_order"><div class="important"><div class="font-size-12 line-height-18" >制单人</div><div class="font-size-9 line-height-18" >Creator</div></div></th>
							<th class="sorting colwidth-13" onclick="orderBy('zdsj')" id="zdsj_order"><div class="font-size-12 line-height-18" >制单时间</div><div class="font-size-9 line-height-18" >Create Time</div></th>
							<th class="sorting colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order"><div class="font-size-12 line-height-18" >组织机构</div><div class="font-size-9 line-height-18" >Organization</div></th>
							</tr> 
			         		 </thead>
							<tbody id="JobCardlistlist">
							</tbody>
							
						</table>
					</div>
					
					<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
					</div>
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	
	<%@ include file="/WEB-INF/views/open_win/batchApprovel.jsp"%><!-------批量审批对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
	
 	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%>  
	<script type="text/javascript" src="${ctx }/static/js/thjw/project/maintenance/jobCardlist.js"></script>
</body>
</html>