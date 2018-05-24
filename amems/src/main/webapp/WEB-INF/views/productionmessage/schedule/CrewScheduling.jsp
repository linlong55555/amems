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
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
.bootstrap-tagsinput {
  width: 100% !important;
}
</style>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" id="fazhi" value="${threshold}" />
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />

	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>

			<input type="hidden" id="adjustHeight" value="10">
			<div class="panel-body padding-bottom-0">
				
				<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 padding-right-0 ">
					<div class="col-xs-3 col-sm-3 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 row-height">
							<div class="font-size-12">
								排班日期
							</div>
							<div class="font-size-9">Schedule date</div>
						</span>
						<div
							class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker" readonly="readonly"
								 id="scheduleDate_search" />
						</div>
					</div>
					<div class="col-xs-3 col-sm-3 col-lg-3 padding-left-0 padding-right-0">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">
								基地
							</div>
							<div class="font-size-9">Station</div>
						</div>
						<div
							class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jd' name="jd">
								<c:choose>
									<c:when test="${baseList!=null && fn:length(baseList) > 0}">
										<c:forEach items="${baseList}" var="base">
											<option value="${base.id}"
												<c:if test="${jd eq base.id}">selected='selected' </c:if>>${base.jdms}</option>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<option value="">暂无基地</option>
									</c:otherwise>
								</c:choose>
								</select>
						</div>
					</div>
					<div
						class="col-xs-3 col-sm-3 col-lg-3  pull-left padding-left-0 padding-right-0">
						<div
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</div>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control ">								
									
										<c:forEach items="${accessDepartment}" var="type">
											<option value="${type.id}"
												<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>
												${type.dprtname}</option>
										</c:forEach>
									
							</select>
						</div>
					</div>
					<div class="col-xs-3 col-sm-3 col-lg-3 pull-right padding-left-10 padding-right-0">
						<div class="col-sm-9 col-lg-9 pull-left padding-left-0 padding-right-0" >
							<input type="text" class="form-control " id="keyword_search"
								placeholder="机械师/电子师/机械员/电子员" />
						</div>
						<div class="col-sm-2 col-lg-2 pull-right padding-left-0	 padding-right-0">
							<button type="button"
								class="btn pull-right btn-primary padding-top-1 padding-bottom-1 "
								onclick="searchSchedule();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
							</button>
						</div>
					</div>
					</div>
					<div
						class="col-xs-12 text-center padding-left-0 padding-right-0 table-h"
						style="overflow-x: auto">

						<table id="crewScheduling_check_list_table" style="min-width:1200px"
							class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
									<th class="fixed-column colwidth-7" >
										<div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="fixed-column colwidth-9" ><div
											class="font-size-12 line-height-18">日期</div>
										<div class="font-size-9 line-height-18">Date</div></th>
									<th class="colwidth-13" ><div class="important">
											<div class="font-size-12 line-height-18">机械师</div>
											<div class="font-size-9 line-height-18">Mechanical
												Engineer</div>
										</div></th>
									<th class="colwidth-13"><div class="important">
											<div class="font-size-12 line-height-18">电子师</div>
											<div class="font-size-9 line-height-18">Electronic
												Engineer</div>
										</div></th>
									<th class="colwidth-13"><div class="important">
											<div class="font-size-12 line-height-18">机械员</div>
											<div class="font-size-9 line-height-18">Mechanical
												Member</div>
										</div></th>
									<th class="colwidth-13"><div class="important">
											<div class="font-size-12 line-height-18">电子员</div>
											<div class="font-size-9 line-height-18">Electronic
												Member</div>
										</div></th>
									<th class="colwidth-13"><div class="important">
											<div class="font-size-12 line-height-18">机械师(备)</div>
											<div class="font-size-9 line-height-18">Mechanical
												Engineer</div>
										</div></th>
									<th class="colwidth-13"><div class="important">
											<div class="font-size-12 line-height-18">电子师(备)</div>
											<div class="font-size-9 line-height-18">Electronic
												Engineer</div>
										</div></th>
									<th class="colwidth-13"><div class="important">
											<div class="font-size-12 line-height-18">机械员(备)</div>
											<div class="font-size-9 line-height-18">Mechanical
												Member</div>
										</div></th>
									<th class="colwidth-13"><div class="important">
											<div class="font-size-12 line-height-18">电子员(备)</div>
											<div class="font-size-9 line-height-18">Electronic
												Member</div>
										</div></th>
									<th class="colwidth-13"><div class="important">
											<div class="font-size-12 line-height-18">MCC调度</div>
											<div class="font-size-9 line-height-18">MCC Dispatch</div>
										</div></th>
									<th class="colwidth-13"><div class="important">
											<div class="font-size-12 line-height-18">维护人</div>
											<div class="font-size-9 line-height-18">Maintainer</div>
										</div></th>
									<th class="colwidth-13"><div
											class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance time</div></th>
									<th class="colwidth-15"><div
											class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
							<tbody id="list">
								<!------ plane列

表展示 ------>

							</tbody>
						</table>
					</div>
					<!--  		<div class=" col-xs-12  text-center page-height" id="pagination"></div> -->
				
			</div>
		</div>
	</div>

	<!-------修改模态框 start-------->
	<div class="modal fade" id="xlhExistModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog" style="width: 65%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="xlhExistModalBody">
					<div class="panel-body">					
						<div
							class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">基础信息</h3>
								</div>
								<div class="panel-body">
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0">
										<div
											class="col-lg-6 col-sm-6 col-xs-6 padding-left-0 padding-right-0">
											<label
												class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0"><div>排班日期</div>
												<div class="font-size-9 line-height-18">Date</div></label>
											<div id="pbrq"
												class=" col-xs-8 col-lg-4 col-sm-4 padding-left-8 padding-right-0 form-group">
											</div>
										</div>
										<div
											class="col-lg-6 col-sm-6 col-xs-6 text-right padding-left-0 padding-right-0">
											<label
												class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
													class="font-size-12 line-height-18">上次维护信息</div>
												<div class="font-size-9 line-height-18">Last Maintenance Info</div></label>
											<div
												class="col-lg-8 col-sm-8 col-xs-8 text-left padding-left-8 padding-right-0">
												<div
													class="col-lg-4 col-sm-4 col-xs-4 text-left padding-left-0 padding-right-0"
													id="whr"></div>
												<div
													class="col-lg-8 col-sm-8 col-xs-8 padding-left-0 padding-right-0 text-left"
													id="whsj"></div>
											</div>
										</div>
									</div>
								
									<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-top-10">
									<div
										class="col-lg-6 col-sm-6 col-xs-6 padding-left-0 padding-right-0">
										<label
											class="col-xs-4 col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">组织机构</div>
											<div class="font-size-9">Organization</div>
										</label>
										<div
											class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group"
											id="dprtcodename" name="dprtcode"></div>
										<div style="display: none">
											<input type="text" id="dprtcodeid" value="" />
										</div>

									</div>
									

									<div
										class="col-lg-6 col-sm-6 col-xs-6 padding-left-0 padding-right-0">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">基地</div>
											<div class="font-size-9">Station</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8 text-left padding-left-8 padding-right-0"
											class='form-control' id='basename' name="base"></div>
										<div style="display: none">
											<input type="text" id="base" value="" />
										</div>
									</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">人员信息</h3>
								</div>
								<div class="panel-body  padding-left-0 padding-right-0">
								<div class="col-lg-12 col-sm-12 col-xs-12  margin-top-10">
									<div
										class="col-lg-6 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-0 margin-bottom-10">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">机械师</div>
										</label>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<div class="col-xs-9 col-sm-9 col-lg-10 padding-left-0 padding-right-0">
												<input type="text" value="" name="jxs" class="form-control " disabled="disabled" id="jxs" />
											</div>
											<div class="col-xs-3 col-sm-3 col-lg-2 padding-left-5 padding-right-0">
												<button class="btn btn-primary form-control"
													 data-toggle="modal" onclick="openUser('jxs')">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</div>
										</div>
										<div style="display: none">
											<input type="text" value="" name="jxsid" id="jxsid">
										</div>
									</div>
										
									<div class="col-lg-6 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-0 margin-bottom-10">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">机械师(备)</div>
										</label>
										<div>
											<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
												<div class="col-xs-9 col-sm-9 col-lg-10 padding-left-0 padding-right-0">
													<input type="text" value="" name="jxby" class="form-control " disabled="disabled" id="jxby"/>
												</div>
												<div class="col-xs-3 col-sm-3 col-lg-2 padding-left-5 padding-right-0">
													<button class="btn btn-primary form-control"  data-toggle="modal"
															onclick="openUser('jxby')">
														<i class="icon-search cursor-pointer"></i>
													</button>
												</div>
											</div>
											<div style="display: none">
												<input type="text" value="" name="jxbyid" id="jxbyid">
											</div>
										</div>
									</div>
								</div>
								<div class="col-lg-12 col-sm-12 col-xs-12 ">
									<div
										class="col-lg-6 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-0 margin-bottom-10">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">机械员</div>
										</label>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<div class="col-xs-9 col-sm-9 col-lg-10 padding-left-0 padding-right-0">
												<input type="text" value="" name="jxy" class="form-control " disabled="disabled" id="jxy"/>
											</div>
											<div class="col-lg-2 col-sm-3 col-xs-3 text-right padding-left-5 padding-right-0">
												<button class="btn btn-primary form-control"  data-toggle="modal"
															onclick="openUser('jxy')">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</div>
										</div>
										<div style="display: none">
											<input type="text" value="" name="jxyid" id="jxyid">
										</div>
									</div>
									<div
										class="col-lg-6 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-0 margin-bottom-10">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">机械员(备)</div>
										</label>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<div class="col-xs-9 col-sm-9 col-lg-10 padding-left-0 padding-right-0">
												<input type="text" value="" name="jxyby"
													class="form-control " disabled="disabled" id="jxyby"/>
											</div>
											<div class="col-lg-2 col-sm-3 col-xs-3 text-right padding-left-5 padding-right-0">
												<button class="btn btn-primary form-control"  data-toggle="modal" onclick="openUser('jxyby')">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</div>
										</div>
										<div style="display: none">
											<input type="text" value="" name="jxybyid" id="jxybyid">
										</div>										
									</div>
								</div>
								<div class="col-lg-12 col-sm-12 col-xs-12 ">
									<div
										class="col-lg-6 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-0 margin-bottom-10">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">电子师</div>
										</label>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<div class="col-xs-9 col-sm-9 col-lg-10 padding-left-0 padding-right-0">
												<input type="text" value="" name="dzs" class="form-control " disabled="disabled" id="dzs"/>
											</div>
											<div class="col-lg-2 col-sm-3 col-xs-3 text-right padding-left-5 padding-right-0">
												<button class="btn btn-primary form-control" data-toggle="modal" onclick="openUser('dzs')">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</div>
										</div>
										<div style="display: none">
											<input type="text" value="" name="dzsid" id="dzsid">
										</div>										
									</div>
									<div
										class="col-lg-6 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-0 margin-bottom-10">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">电子师(备)</div>
										</label>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<div class="col-xs-9 ol-sm-9 col-lg-10 padding-left-0 padding-right-0">
												<input type="text" value="" name="dzby" class="form-control "
													disabled="disabled" id="dzby"/>
											</div>
											<div class="col-lg-2 col-sm-3 col-xs-3 text-right padding-left-5 padding-right-0">
												<button class="btn btn-primary form-control" data-toggle="modal" onclick="openUser('dzby')">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</div>
										</div>
										<div style="display: none">
											<input type="text" value="" name="dzbyid" id="dzbyid">
										</div>						
									</div>
								</div>
								<div class="col-lg-12 col-sm-12 col-xs-12 ">
									<div
										class="col-lg-6 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-0 margin-bottom-10">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">电子员</div>
										</label>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<div class="col-xs-9 col-sm-9 col-lg-10 padding-left-0 padding-right-0">
												<input type="text" value="" name="dzy" class="form-control "
													disabled="disabled" id="dzy"/>
											</div>
											<div class="col-lg-2 col-sm-3 col-xs-3 text-right padding-left-5 padding-right-0">
												<button class="btn btn-primary form-control" data-toggle="modal" onclick="openUser('dzy')">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</div>		
										</div>
										<div style="display: none">
											<input type="text" value="" name="dzyid" id="dzyid">
										</div>
										
									</div>
									<div
										class="col-lg-6 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-0 margin-bottom-10">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">电子员(备)</div>
										</label>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<div class="col-xs-9 col-sm-9 col-lg-10 padding-left-0 padding-right-0">
												<input type="text" value="" name="dzyby"
													class="form-control " disabled="disabled" id="dzyby"/>
											</div>
											<div class="col-lg-2 col-sm-3 col-xs-3 text-right padding-left-5 padding-right-0">
												<button class="btn btn-primary form-control"  data-toggle="modal"  onclick="openUser('dzyby')">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</div>
										</div>
										<div style="display: none">
											<input type="text" value="" name="dzybyid" id="dzybyid">
										</div>
										
									</div>
								</div>
								<div class="col-lg-12 col-sm-12 col-xs-12 ">
									<div
										class="col-lg-6 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-0 margin-bottom-10">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">MCC调度</div>
										</label>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
										 	<div class="col-xs-9 col-sm-9 col-lg-10 padding-left-0 padding-right-0">
												<input type="text" value="" name="mccdd"
													class="form-control " disabled="disabled" id="mccdd"/>
											</div>
											<div class="col-lg-2 col-sm-3 col-xs-3 text-right padding-left-5 padding-right-0">
												<button class="btn btn-primary form-control" data-toggle="modal" onclick="openUser('mccdd')">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</div>
										</div>
										<div style="display: none">
											<input type="text" value="" name="mccddid" id="mccddid">
										</div>										
									</div>
									<div
										class="col-lg-6 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-0 margin-bottom-10">
										<label
											class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">状态</div>
										</label>
										<div class="col-xs-8 col-sm-8  col-lg-8 padding-left-6 padding-right-0">
											<label style=" font-weight:normal" class="pull-left"><input type="radio" name="zt" id="zt" value='1' checked="checked">有效</label>										
											<label style=" font-weight:normal" class="pull-left padding-left-10"><input type="radio" name="zt"  value='0' id="zt" >无效</label>
										</div>
									</div>
								</div>								
								</div>
								<div class="pull-right padding-top-10">
									<button type="button" onclick="saveCrewScheduleData()"
											class="btn btn-primary padding-top-1 padding-bottom-1">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" 
											class="btn btn-primary padding-top-1 padding-bottom-1"
											onclick="closeCrewScheduleData()">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
								</div>
								<div style="display: none">
									<input maxlength="20" id="id" type="text" name="id" value="" />
									<input type="text" id="userid" value="${sessionScope.user.id}" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		<!-------修改模态框end-------->
		<div class="modal fade" id="UserMultiModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 750px !important;">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="UserMultiModalBody">
						<div class="panel panel-primary">
							<div class="panel-heading  padding-top-3 padding-bottom-1">
								<div class="font-size-16 line-height-18">用户列表</div>
								<div class="font-size-9 ">User List</div>
							</div>
							<div class="panel-body padding-top-0 padding-bottom-0">
								<div class="col-lg-12 col-xs-12">
									
										<div
											class=" pull-right padding-left-0 padding-right-0 margin-top-10">
											<div
												class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
												<input type="text" class="form-control "placeholder='用户名称'
													id="um_realname_search" /> <input type="hidden"
													class="form-control " id="um_state" value="1" />
											</div>
										
										 <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button name="keyCodeSearch" type="button" onclick="UserMultiModal.search()"
											class=" btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
										</button>
										</div>
										</div>
										<div class="clearfix"></div>
									
									<!-- start:table -->
									<div class="margin-top-10 ">
										<div class="overflow-auto">
											<table
												class="table table-bordered table-striped table-hover text-left table-set"
												style="">
												<thead>
													<tr>
														<th>
															<div class="font-size-12 notwrap">序号</div>
															<div class="font-size-9 notwrap">No.</div>
														</th>
														<th>
														<div class="important">
															<div class="font-size-12 notwrap">用户名称</div>
															<div class="font-size-9 notwrap">User Name</div>
															</div>
														</th>
														<th>
															<div class="font-size-12 notwrap">机构部门</div>
															<div class="font-size-9 notwrap">Department</div>
														</th>
													</tr>
												</thead>
												<tbody id="um_userlist">

												</tbody>
											</table>
											<div class=" col-xs-12  text-center "
												style="margin-top: 0px; margin-bottom: 0px;"
												id="um_pagination"></div>
										</div>
									</div>
									<div
										class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
										<label
											class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12 line-height-18">用户名称</div>
											<div class="font-size-9 line-height-18">User Name</div>
										</label>
										<div
											class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
											<input class="form-control" id="um_selectUser" type="text"  />
										</div>
									</div>
									<!-- end:table -->
									<div class="text-right margin-top-10 margin-bottom-10 padding-right-0">
										<button type="button" onclick="UserMultiModal.setData()"
											class="btn btn-primary padding-top-1 padding-bottom-1">
											<div class="font-size-12">确定</div>
											<div class="font-size-9">Confirm</div>
										</button>
										<button type="button" onclick="UserMultiModal.clearUser()" id="userModal_btn_clear"
										class="btn btn-primary padding-top-1 padding-bottom-1">
										<div class="font-size-12">清空</div>
										<div class="font-size-9">Clear</div>
										</button>
										<button type="button"
											class="btn btn-primary padding-top-1 padding-bottom-1"
											onclick="closeUserMultiModal()">
											<div class="font-size-12">关闭</div>
											<div class="font-size-9">Close</div>
										</button>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
		<script type="text/javascript"
			src="${ctx }/static/js/thjw/productionmessage/schedule/crewSchedule.js"></script>
		<script type="text/javascript"
			src="${ctx }/static/js/thjw/productionmessage/schedule/userModal.js"></script>
</body>
</html>