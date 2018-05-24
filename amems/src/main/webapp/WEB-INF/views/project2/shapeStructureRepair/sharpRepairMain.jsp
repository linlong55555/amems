<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>结构修理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>

<!-- 
           功        能：回车键 = 查询
           使用方法：
           1.主列表页加入以下代码
           2.子弹出窗口中，查询按钮加name=keyCodeSearch
           3.这样，主页面及其引用的子窗口，都有回车事件
           举例说明：
           1.工卡管理主页面workcard_main.jsp中加入以下代码
           2.章节号、区域、飞机站位等子弹出窗口，配置查询按钮name=keyCodeSearch
            注意事项:
           1.子窗口中不要加以下代码，只需配置查询按钮name=keyCodeSearch，否则按下回车会重复执行
 -->
<script type="text/javascript">
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口,查询方法(每个自查窗口的查询按钮，定义了name=keyCodeSearch)
				}else{
					searchRecord();//直接调用主列表页查询
					//$('#workCardMainSearch').trigger('click');//ID调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body>
	<input type="hidden" id="whbmid" value="${user.bmdm}" />
	<input type="hidden" id="whrid" value="${sessionScope.user.id}" />
	<input type="hidden" id="dprtcode" value="${user.jgdm}" />
	<input type="hidden" id="wc_type" value="1" />
	<div class="page-content">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>		
			<!-- <input type="hidden" id="adjustHeight" value="10"> -->
			<div class="panel-body padding-bottom-0">
				<div class='searchContent row-height margin-top-0' >
				<!-- <div class=" col-lg-6 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group  ">
					<a href="javascript:add(1);" permissioncode='project:shapeStructureRepair:main:01'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
				</div> -->
				
				<div class=" col-lg-7 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group  ">
					<a href="javascript:add(1);" permissioncode='project:shapeStructureRepair:main:01'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
					<!-- permissioncode='project:shapestructurerepair:main:03' -->
					<a href="javascript:exportExcel();" type="button" class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode='project:shapestructurerepair:main:03' >
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a>
				</div>
				
				<div class="col-lg-5 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
						<input type="text" placeholder='飞机注册号/章节号/缺陷描述/位置/修理依据'  class="form-control" id="keyword_search" >
	                    <div class="input-group-addon btn-searchnew" >
	                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRecord();" style='margin-right:0px !important;'>
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
	                  		</button>
	                    </div>
	                    <div class="input-group-addon btn-searchnew-more">
		                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="more();">
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
				<div class='clearfix'></div>
				<div
					class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0"
					id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">发现日期</div>
							<div class="font-size-9">Discovery Date</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly="readonly"
								id="fxrq_search" />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">修理日期</div>
							<div class="font-size-9">Repair Date</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly="readonly"
								id="xlrq_search" />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 col-lg-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">修理方式</div>
							<div class="font-size-9">Repair mode</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 col-lg-9 padding-left-8 padding-right-0">
							<select id="xlfs_search" class="form-control ">
								<option value="0">查询全部</option>
								<option value="1">永久修理</option>
								<option value="2">临时修理</option>
							</select>
						</div>
					</div>

					<div
						class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control ">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-lg-2  text-right  pull-right padding-right-0 margin-bottom-10">
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
				<div
					class="col-xs-12 text-center padding-left-0 padding-right-0 margin-top-0 table-height"
					style="overflow-x: auto">

					<table id="repairRecord_check_list_table" style="min-width:1200px"
						class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="fixed-column colwidth-7" >
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class=" sorting colwidth-9"  onclick="orderBy('fjzch')" id="fjzch_order"><div class="important"><div
										class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18" >A/C REG</div></div></th>
								<th class=" sorting colwidth-15" onclick="orderBy('zjh')" id="zjh_order"><div class="important">
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div></div>
								</th>
								<th class=" sorting colwidth-13" onclick="orderBy('qxms')" id="qxms_order">
									<div class="important">
										<div class="font-size-12 line-height-18">缺陷描述</div>
										<div class="font-size-9 line-height-18">Defect Desc</div>
									</div>
								</th>
								<th class="sorting colwidth-20 title" onclick="orderBy('wz')" id="wz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">位置</div>
										<div class="font-size-9 line-height-18">Position</div>
									</div>
								</th>
								<th class=" colwidth-13 cursor-pointer" name="fj"  >
									<div class="font-size-12 line-height-18">修理前照片</div>
									<div class="font-size-9 line-height-18">Photos Before Repairs</div>
								</th>
								<th class="sorting colwidth-10 title" onclick="orderBy('fxrq')" id="fxrq_order">
										<div class="font-size-12 line-height-18">发现日期</div>
										<div class="font-size-9 line-height-18">Discovery Date</div>
								</th>
								<th class="sorting colwidth-10" onclick="orderBy('xlrq')" id="xlrq_order">
										<div class="font-size-12 line-height-18">修理日期</div>
										<div class="font-size-9 line-height-18">Repair Date</div>
								</th>
								<th class="sorting colwidth-10" onclick="orderBy('xlfs')" id="xlfs_order">
										<div class="font-size-12 line-height-18">修理方式</div>
										<div class="font-size-9 line-height-18">Repair mode</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('xlyj')" id="xlyj_order">
									<div class="important">
										<div class="font-size-12 line-height-18">修理依据</div>
										<div class="font-size-9 line-height-18">Repair Basis</div>
									</div>	
								</th>
								<th class=" colwidth-13 cursor-pointer" name="fj">
									<div class="font-size-12 line-height-18">修理依据附件</div>
									<div class="font-size-9 line-height-18">Repair Basis Files</div>
								</th>
								<th class="sorting colwidth-15" onclick="orderBy('lxjcjg')" id="lxjcjg_order">
									<div class="font-size-12 line-height-18">例行检查间隔</div>
									<div class="font-size-9 line-height-18">Routine Inspection Interval</div>
								</th>
								<th class=" colwidth-13  cursor-pointer" name="fj" >
									<div class="font-size-12 line-height-18">修理后照片</div>
									<div class="font-size-9 line-height-18">Repaired photos</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('whrid')" id="whrid_order"><div
										class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('whsj')" id="whsj_order"><div
										class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-15"><div
										class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
							</tr>
						</thead>
						<tbody id="list">
							

						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center padding-left-0 padding-right-0 page-height " id="pagination"></div>

			</div>
		</div>
	</div>

	<input type="hidden" id="sjlx" value="1"/>
	<input  id="id" type="hidden" name="id" value="" />
	<input  id="dpt" type="hidden" name="dpt" value="" />
<%@ include file="/WEB-INF/views/project2/shapeStructureRepair/structureRepair_open.jsp" %> 
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<link
		href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css"
		rel="stylesheet">
	<script
		src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
	<script type="text/javascript"
		src="${ctx }/static/js/thjw/project2/shapeStructureRepair/repairMain.js"></script>
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!-- ATA章节弹出框 -->		
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/open_win/user.js"></script>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
		<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>