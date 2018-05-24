<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞行记录单</title>
<script type="text/javascript">
	var id = '${param.id}';
	var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<link href="${ctx}/static/lib/BreakingNews/BreakingNews.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/lib/BreakingNews/BreakingNews.js"></script>
<link href="${ctx}/static/lib/webui-popover/jquery.webui-popover.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/lib/webui-popover/jquery.webui-popover.min.js"></script>

</head>
<body class="page-header-fixed">
<input type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content ">
			<div class="panel panel-primary" >
				<div class="panel-heading  "> 
				<div id="NavigationBar"></div>
				</div>
				<div class="panel-body col-xs-12 padding-bottom-0">
				
				<div class="padding-left-0 pull-left" style='margin-right:8px;'>
					<button class="btn btn-primary padding-top-1 padding-bottom-1 row-height" type="button" onclick="goToAddPage();">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
				</div>
				
				
				<div class="col-xs-3 padding-left-0" id="tz"  style="display:none;">
					<div class="BreakingNewsController easing pull-left" id="messageDiv" >
					<!-- <div class=" col-xs-1 info-label1 danger-bg"></div> -->
						<!-- <div class="bn-title cursor-pointer"></div> -->
						<div class="bn-title cursor-pointer" style='width:20px;height:20px;border-radius:10px;background:none;'>
						
						</div>
						<ul id="messageUl" style="left: 20px; margin-top: 0px;">
						</ul>
						<div class="bn-arrows"><span class="bn-arrows-left"></span><span class="bn-arrows-right"></span></div>	
					</div>	
						<div class="pull-left">
						<img src="${ctx}/static/images/help.png" id="tghelp" style='margin-top:5px;'>
						</div>
						<div class='clearfix'></div>
				</div>	
				
				<div class=" pull-right padding-left-0 padding-right-0" >
					<div class="pull-left">
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="padding-left-8 pull-left" style="width: 250px; margin-right:5px;">
							   <select id="fjzch" class="form-control " onchange="searchFlightRecord()">
								</select> 
								<input type="hidden" id="defaultPlane" value="${defaultPlane}">
							</div>
						</div>
					</div>
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='飞行记录单单号/记录本页码/制单人/修订人' id="keyword"
							class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button"
							class=" btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchFlightRecord();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
						</button>&nbsp;
                        <button type="button"
							class="btn btn-primary dropdown-toggle pull-right padding-top-1 padding-bottom-1 resizeHeight"
							id="btn" onclick="more()">
							<div class="pull-left text-center">
								<div class="font-size-12">更多</div>
								<div class="font-size-9">More</div>
							</div>
							<div class="pull-left padding-top-5">
								&nbsp;<i class="icon-caret-down font-size-15" id="icon"></i>
							</div>
						</button>
                    </div> 
				</div>


				<div
					class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height" 
					id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">飞行日期</div>
							<div class="font-size-9">Flight date</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control date-range-picker" id="fxrq"/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">状态</div>
							<div class="font-size-9">State</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt" class="form-control ">
							   <option value="-1">全部 All</option>
							   <option value="1">保存 Save</option>
							   <option value="2">提交 Submit</option>
							   <option value="12">修订 Revise</option>
							</select>
						</div>
					</div>
					
					<div
						class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " onchange="changeOrganization()">
							   <c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
									<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					

					<div class=" col-lg-2 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>

				</div>

				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h"
					style="margin-top: 10px;">
					<table
						class="table table-thin table-bordered text-center table-set" id="flight_record_sheet_table">
						<thead>
							<tr>
								<th class="colwidth-5 fixed-column">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>
								<th class="colwidth-13 fixed-column sorting" onclick="orderBy('fxjldh')" id="fxjldh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">飞行记录单单号</div>
										<div class="font-size-9 line-height-18">Flight Record No.</div></th>
									</div>
								<th class="colwidth-9 sorting" onclick="orderBy('fjzch')"
									id="fjzch_order">
									<div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C REG</div></th>
								<th class="colwidth-9 sorting" onclick="orderBy('jlbym')"
									id="jlbym_order">
									<div class="important">
										<div class="font-size-12 line-height-18">记录本页码</div>
										<div class="font-size-9 line-height-18">Flight record</div></th>
									</div>
								<th class="colwidth-9 sorting" onclick="orderBy('fxrq')"
									id="fxrq_order">
									<div class="font-size-12 line-height-18">飞行日期</div>
									<div class="font-size-9 line-height-18">Flight date</div></th>
								<th class="colwidth-5 sorting" onclick="orderBy('zt')"
									id="zt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div></th>
								<th class="colwidth-13">
									<div class="important">
										<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator</div></th>
									</div>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div></th>
								<th class="colwidth-13">
									<div class="important">
										<div class="font-size-12 line-height-18">修订人</div>
										<div class="font-size-9 line-height-18">Revised By</div></th>
									</div>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">修订时间</div>
									<div class="font-size-9 line-height-18">Revision Time</div></th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
							</tr>
						</thead>
						<tbody id="list">

						</tbody>

					</table>
				</div>

				<div class=" col-xs-12  text-center page-height padding-left-0 padding-right-0" style="margin-top: 0px; margin-bottom: 0px;" id="pagination"></div>
				<div class="clearfix"></div>

			</div>
			<div class="clearfix"></div>
			</div>
	
	<!-- END CONTENT -->
</div>
	 <%@ include file="/WEB-INF/views/open_win/log.jsp"%>
     <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	 <script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/flightrecordsheet/flightrecordsheet_main.js"></script> 
</body>
</html>