<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生效维修方案</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="" />
	<input type="hidden" id="userLoginId" value="${user.id}" />
	<input type="hidden" id="userType" value="${user.userType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			
			<div class="panel-body">
			
				<div class=" pull-right padding-left-0 padding-right-0">
			
					<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div>
						</span>
						<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zxbs_search" class="form-control" onchange="changeStatus()">
								<option value="" >显示全部All</option>
								<c:forEach items="${latestLogoTwoEnum}" var="item">
									<option value="${item.id}" <c:if test="${item.id == 0}">selected='selected'</c:if> >${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>	
			
					<!-- 搜索框start -->
					<div class="row-height pull-right padding-left-10 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='编号/名称/机型/版本/确认人' id="keyword_search" class="form-control ">
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
				</div>
				
				<div class="clearfix"></div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc search-height" id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">计划生效日期</div>
							<div class="font-size-9">Plan Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker" id="jhSxrq_search" readonly />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">实际生效日期</div>
							<div class="font-size-9">Actual date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker" id="sjSxrq_search" readonly />
						</div>
					</div>
					
					<!-- <div class="col-xs-12 col-sm-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt_search" class="form-control">
								<option value=""></option>
								<option value="2">待生效</option>
								<option value="3">生效</option>
							</select>
						</div>
					</div> -->
					
					<!-- <div class="col-xs-12 col-sm-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">确认人</div>
							<div class="font-size-9">Confirmor</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " id="zdrrealname_search" />
						</div>
					</div> -->
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control ">
								<c:forEach items="${accessDepartment}" var="type">
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
				
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h"  style="overflow-x:auto;">
					<table id="effect_main_table" class="table table-thin table-bordered table-striped table-hover text-left table-set" style="min-width: 1200px;">
						<thead>
							<tr>
								<th class="colwidth-5">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('jx')" id="jx_order">
									<div class="important">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">Model</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('wxfabh')" id="wxfabh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">维修方案编号</div>
										<div class="font-size-9 line-height-18">Maintenance No.</div>
									</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('zwms')" id="zwms_order">
									<div class="important">
										<div class="font-size-12 line-height-18">维修方案名称</div>
										<div class="font-size-9 line-height-18">Maintenance Name</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('bb')" id="bb_order">
									<div class="important">
										<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Revision</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('zxbs')" id="zxbs_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('jh_sxrq')" id="jh_sxrq_order">
									<div class="font-size-12 line-height-18">计划生效日期</div>
									<div class="font-size-9 line-height-18">Plan Date</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('sj_sxrq')" id="sj_sxrq_order">
									<div class="font-size-12 line-height-18">实际生效日期</div>
									<div class="font-size-9 line-height-18">Actual date</div>
								</th>
								<!-- <th class="sorting" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">确认状态</div>
									<div class="font-size-9 line-height-18">Confirm State</div>
								</th> -->
								<th class="colwidth-13 sorting" onclick="orderBy('sxrid')" id="sxrid_order">
									<div class="important">
										<div class="font-size-12 line-height-18">确认人</div>
										<div class="font-size-9 line-height-18">Confirmor</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('sxjyrid')" id="sxjyrid_order">
									<div class="font-size-12 line-height-18">校验人</div>
									<div class="font-size-9 line-height-18">Checker</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
		<!-- start 确认生效对话框 -->
		<div class="modal fade" id="alertModalForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width:450px!important;">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="alertModalFormBody">
						<div class="panel panel-primary">
							<div class="panel-heading  padding-top-3 padding-bottom-1">
								<div class="font-size-16 line-height-18">确认生效</div>
								<div class="font-size-9 ">Confirm Effect</div>
							</div>
							<div class="panel-body padding-top-0 padding-bottom-0">
							
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								
									<div class=" col-lg-12 col-sm-2 col-xs-12 margin-top-10 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">确认人</div>
											<div class="font-size-9 line-height-18">Confirmor</div>
										</label>
										<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
											<input type="text" class="form-control " id="qrr" name="qrr" disabled="disabled" value="${user.displayName}"/>
										</div>
									</div>
									
									<div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
										<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">校验人</div>
											<div class="font-size-9 line-height-18">Checker</div>
										</label>
										<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0 input-group">
											<input class="form-control " id="sxjyr" name="sxjyr" value="" type="text" maxlength="10" >
											<input class="form-control " id="sxjyrid" name="sxjyrid" value="" type="hidden">
											<input class="form-control " id="wxfaId" name="wxfaId" value="385b1007-5eed-4de1-8f24-9658f32a24b7" type="hidden">
											<span class="input-group-btn">
												<button type="button" class="btn btn-primary form-control" data-toggle="modal" onclick="openUserList();">
													<i class="icon-search "></i>
												</button>
											</span>
										</div>
									</div>
					            	
								</div>
						     	<div class="clearfix"></div>
						     	<div class="text-right margin-top-10 margin-bottom-10 padding-right-1">
									<button type="button" onclick="sbEffective()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
				                </div>
	                    		<br/>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- end 确认生效对话框 -->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 	
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/confirmationofrevision_main.js"></script>
<%@ include file="/WEB-INF/views/open_win/user.jsp"%><!-------用户对话框 -------->
</body>
</html>