<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>适航状态维护</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = '${param.id}';
//var pageParam = '${param.pageParam}';
</script>

</head>
<body class="page-header-fixed">
	<input type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->

	<div class="page-content">
		<!-- from start -->
		<div class="panel panel-primary">
		    <div class="panel-heading">
			<div id="NavigationBar"></div>
			</div>
			<div class="panel-body padding-left-0 padding-bottom-0">
				<form id="aftersaveForm" action="${ctx }/production/airworthinessdirective/manage" method="post">
			    <input type="hidden" id="curfjzch" name="fjzch" value="${erayFns:escapeStr(fjzch)}"/>
			    <input type="hidden" id="curheight" name="curheight" value="${curheight}"/>
			    <input type="hidden" id="dprtcode" name="dprtcode" />
			    <input type="hidden" id="id" name="id" />
			    </form>
			    
				<div class="col-lg-3 col-sm-3 col-xs-12 pull-left padding-left-0 border-r" >
					<div class="col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 pull-right">
						<span class="pull-left col-xs-3 col-sm-3 text-right  padding-right-0">
							<div class="font-size-14">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-9 col-sm-9 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control " name="dprtcode_search" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${ (user.jgdm eq type.id and  empty dprtcode) or dprtcode eq type.id  }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					

					<div  id="div_qh_scorll" class="col-xs-12 margin-bottom-10" style="overflow-y: auto; height:800px;">
						<c:forEach items="${planes}" var="item">
							 <c:forEach items="${userACRegList}" var="ac">
							  <c:if test="${ac.FJZCH eq  item.fjzch and ac.DPRTCODE eq item.dprtcode }">   
								<a href="javascript:void(0);" id="${erayFns:escapeStr(item.fjzch)}"
									class="div_qh  col-xs-12">
									<p>
										<b>${erayFns:escapeStr(item.fjzch)}</b><b>${erayFns:escapeStr(item.xlh)}</b>
									</p>
									<p>
										<em>${erayFns:escapeStr(item.bzm)}</em><em>${erayFns:escapeStr(item.jdms)}</em>
									</p>
									<p>
										<em>停场时间 :</em> 
										<fmt:formatDate value='${item.bshRq}' pattern='yyyy-MM-dd HH:mm' />
									</p>
									<p>
										<em>适航时间 :</em> 
										<fmt:formatDate value='${item.shRq}' pattern='yyyy-MM-dd HH:mm' />
									</p>
									 <i> <c:choose>
											<c:when test="${item.shzt eq 0 }">
												<img src="${ctx}/static/assets/img/right.png" alt="ok">
											</c:when>
											<c:otherwise>
												<img src="${ctx}/static/assets/img/wrong.png" alt="finished">
											</c:otherwise>
										</c:choose>
								</i>
								</a>
							  </c:if>
							</c:forEach>  
						</c:forEach>
						</div>	
						
				</div>


				<div class="col-lg-9 col-sm-9 col-xs-12  padding-right-0 pull-right">
					<div class="col-lg-12 padding-left-0 padding-right-0" >
					<div class="col-lg-12 padding-right-0 padding-left-0" id="divSave">
						<div class="col-lg-12  ibox-title line1 padding-right-0 padding-left-0">
							<div class="col-lg-8 padding-right-0 padding-left-0 sh_list">
								<p>
									<span ><em>指令流水号 :</em><i id="lsh"></i></span>
									<span ><em>申请人:</em><i id="zdrrealname"></i></span>
									<span ><em>申请时间 :</em><i id="zdsj"></i></span>
								</p>
							</div>

							<div class=" pull-right">
								<button id="btnSave"
									class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission"
									permissioncode='production:airworthinessdirective:saveorupdate'
									>
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
									</button>
									
									<button id="btnClear"class="btn btn-primary padding-top-1 padding-bottom-1 ">
									<div class="font-size-12">重置</div>
									<div class="font-size-9">Reset</div>
									</button>
							</div>
						</div>
						
						<div class="col-lg-12 padding-left-0 padding-right-0">
						<form id="saveForm">
						       <input type="hidden" id="id"/>
								<div class="col-lg-4 col-sm-4 padding-left-0 ">
									 <span class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">计划停场时间</div>
										<div class="font-size-9 line-height-18">Planned Time of Stop</div>
									</span>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<div class='input-group' style="position:relative;z-index:1;">
										  <input type="text" class='form-control datetimepicker' id="bshYjrq" name="bshYjrq" />
										  <span class='input-group-btn'> </span>
										  <input class='form-control datetimepicker' type='text' style='width:60px;' id='bshYjsj' name='bshYjsj' readonly/>
										</div>
									</div>
								</div>

								<div class="col-lg-4 col-sm-4 padding-left-0 ">
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12  ">实际停场时间</div>
										<div class="font-size-9  ">Actual Time of Stop</div>
									</span>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<div class='input-group' style="position:relative;z-index:1;">
										  <input type="text" class='form-control datetimepicker' id="bshSjrq" name="bshSjrq" />
										  <span class='input-group-btn'> </span>
										  <input class='form-control datetimepicker' type='text' style='width:60px;' id='bshSjsj' name='bshSjsj' readonly/>
										</div>
									</div>
									
								</div>

								 <div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0">
									<span
										class="pull-left col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12">不适航审批单号</div>
										<div class="font-size-9">Unairworthiness No.</div>
									</span>
									<div
										class="form-group col-lg-8 col-sm-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="bshSpdh" maxlength="20" name="bshSpdh" />
									</div>
								</div>  
								
								<div class="clearfix"></div>
								<div class="col-lg-4 col-sm-4 padding-left-0 margin-bottom-10">
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">计划适航时间</div>
										<div class="font-size-9 line-height-18">Planned Time of Airworthiness</div>
									</span>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<div class='input-group' style="position:relative;z-index:1;">
										  <input type="text" class='form-control datetimepicker' id="shYjrq" name="shYjrq" />
										  <span class='input-group-btn'> </span>
										  <input class='form-control datetimepicker' type='text' style='width:60px;' id='shYjsj' name='shYjsj' readonly/>
										</div>
									</div>
									
								</div>
								<div class="col-lg-4 col-sm-4 padding-left-0  ">
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">实际适航时间</div>
										<div class="font-size-9 line-height-18">Actual Time of Airworthiness</div>
									</span>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<div class='input-group' style="position:relative;z-index:1;">
										  <input type="text" class='form-control datetimepicker' id="shSjrq" name="shSjrq" />
										  <span class='input-group-btn'> </span>
										  <input class='form-control datetimepicker' type='text' style='width:60px;' id='shSjsj' name='shSjsj' readonly/>
										</div>
									</div>
									
								</div>
								<div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0">
									<span class="pull-left col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12">适航审批单号</div>
										<div class="font-size-9">Airworthiness No.</div>
									</span>
									<div
										class="col-lg-8 col-sm-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " maxlength="20" id="shSpdh" name="shSpdh" />
									</div>
								</div>
								<div class="clearfix"></div>

								<div class=" col-lg-4 col-sm-4 col-xs-12  padding-left-0" >
									 
									<span class="col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">不适航批准人</div>
										<div class="font-size-9">Unairworthiness Reviewer</div>
									</span>
									<div class="padding-left-8 padding-right-0 input-group">
										<input type="text" class="form-control" name="bshPzrmc" id="bshPzrmc"  readonly />
										<input type="hidden" id="bshPzrid" />
										<span class="input-group-btn">
											<button id="bshBtn" class="btn btn-primary">
												<i class="icon-search" ></i>
											</button>
										</span>
									</div>
								</div> 
								
								  <div class=" col-lg-4 col-sm-4 col-xs-12  padding-left-0  " >
									<span class="col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">适航批准人</div>
										<div class="font-size-9">Airworthiness Reviewer</div>
									</span>
									<div class="padding-left-8 padding-right-0 input-group">
										<input type="text" class="form-control" name="shPzrmc" id="shPzrmc"  readonly />
										<input type="hidden" id="shPzrid" />
										<span class="input-group-btn">
											<button id="shBtn" class="btn btn-primary">
												<i class="icon-search" ></i>
											</button>
										</span>
									</div>
								</div>  
								
								<div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0 margin-bottom-10">
									<span class="pull-left col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12">停场原因</div>
										<div class="font-size-9">Reason of Stop</div>
									</span>
									<div
										class="col-lg-8 col-sm-8 padding-left-8 padding-right-0">
										<select id="bshyy" class="form-control " name="bshyy">
											 <option value="1" >定检原因</option>
											 <option value="2" >故障原因</option>
											 <option value="3" >缺件原因(航材、工具)</option>
											 <option value="4" >机组原因</option>
											 <option value="5" >其他原因</option>
											 
										</select>
									</div>
								</div>
								 
								
								<div class="clearfix"></div>
								<div class="col-lg-8 col-sm-8 col-xs-12 padding-left-0 form-group" >
									<span class="pull-left col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">备注</div>
										<div class="font-size-9">Remark</div>
									</span>
									 <div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0" >
										 <textarea class="form-control" id="bz" name="bz" placeholder='长度最大为300'   maxlength="300"></textarea>
									</div>
								</div>
					
								
								<div class="clearfix"></div>
								
								</form>
							</div>
						</div>
						<div class="col-lg-12 padding-left-0 padding-right-0">
							<div
								class="col-lg-12 widget-body clearfix padding-left-0 padding-right-0 ">
								<div class="nav nav-tabs">
									<div class="col-lg-2 pull-left padding-left-0">
										<div class="font-size-14 line-height-18 font-weight-bold">
											历史记录</div>
										<div class="font-size-9 ">Historic Records</div>
									</div>

									<div class="pull-right padding-right-10">
										<a href="javascript:void(0);" id="more"
											class="font-size-14 line-height-18 font-weight-bold more">更多</a>
									</div>
								</div>

								<div class="tab-content" style="overflow-x: auto;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:1000px;margin-bottom:10 !important;">
										<thead>
											<tr>
												<th class="colwidth-5" >
												<div
														class="font-size-12 line-height-18 ">操作</div>
													<div class="font-size-9 line-height-18">Operation</div>
												</th>
												<th class="colwidth-3">
												<div
														class="font-size-12 line-height-18">序号</div>
													<div class="font-size-9 line-height-18">No</div>
												</th>
												<th class="colwidth-10"><div
													class="font-size-12 line-height-18">指令流水号</div>
												<div class="font-size-9 line-height-18">Serial No</div></th>
												
												 
												<th class="colwidth-13" ><div
														class="font-size-12 line-height-18">停场时间</div>
													<div class="font-size-9 line-height-18">Stop Time</div></th>
												<th class="colwidth-13" ><div
														class="font-size-12 line-height-18">适航时间</div>
													<div class="font-size-9 line-height-18">Airworthiness Time</div></th>
												
													
												<th class="colwidth-15"><div
														class="font-size-12 line-height-18">不适航审批单号</div>
													<div class="font-size-9 line-height-18">Unairworthiness No</div>
													</th>
												<th class="colwidth-10"><div
														class="font-size-12 line-height-18">适航审批单号</div>
													<div class="font-size-9 line-height-18">Airworthiness No</div>
													</th>	
												<th class="colwidth-10"><div
														class="font-size-12 line-height-18">停场原因</div>
													<div class="font-size-9 line-height-18">Reason of Stop</div>
													</th>	
											</tr>
										</thead>
										<tbody id="list">
										</tbody>
									</table>
								</div>
								<div class="col-xs-12 text-center">
									<ul class="pagination "
										style="margin-top: 0px; margin-bottom: 0px;" id="pagination">
									</ul>
								</div>
							</div>
						</div>
						<input id="hasResult" type="hidden" />
						
						
					</div>
				</div>

			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	
	

<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true" style="z-index: 100000 ! important; ">
<div class="modal-dialog" style="width: 1000px;">
		<div class="modal-content" >
			<div class="modal-body padding-bottom-0" id="searchModalBody" >
				<div class="panel panel-primary" >
					<div class="panel-heading  padding-top-3 padding-bottom-1 ">
						<div class="font-size-16 line-height-18">适航状态列表</div>
						<div class="font-size-9 ">Airworthiness List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
					<div class="col-lg-12 padding-left-0 padding-right-0 margin-top-10" id="divSearch">
					    <input type="hidden" id="fjzch" />
						<div class=" pull-right padding-left-0 padding-right-0">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input placeholder="适航审批单号/不适航审批单号/不适航批准人/适航批准人" id="keyword" class="form-control " type="text">
							</div>
		                    <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="popModal.search();">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                   		</button>
		                    </div> 
						</div>
						
					</div>
					
					<div class="col-lg-12 padding-left-0 padding-right-0 margin-top-10" style="height: 300px  ! important; overflow-y: scroll; overflow-x: scroll;">
						<table
							class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:1700px;" >
							<thead>
								<tr>
									<th class="colwidth-3"><div
											class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th  class="colwidth-10"><div
												class="font-size-12 line-height-18">指令流水号</div>
											<div class="font-size-9 line-height-18">Serial No.</div></th>
									<th class="colwidth-13"><div
												class="font-size-12 line-height-18">计划停场时间</div>
											<div class="font-size-9 line-height-18">Plan Stop Time</div>
									</th>
									<th class="colwidth-13"><div
												class="font-size-12 line-height-18">实际停场时间</div>
											<div class="font-size-9 line-height-18">Actual Stop Time</div>
									</th>
									
									<th class="colwidth-20"><div
												class="font-size-12 line-height-18">计划适航时间</div>
											<div class="font-size-9 line-height-18">Plan Airworthiness Time</div>
									</th>
									<th class="colwidth-20"><div
												class="font-size-12 line-height-18">实际适航时间</div>
											<div class="font-size-9 line-height-18">Actual Airworthiness Time</div>
									</th>
											
									
									<th class="colwidth-15">
										<div class="important">
											<div class="font-size-12 line-height-18">不适航审批单号</div>
											<div class="font-size-9 line-height-18">Unairworthiness No</div>
									    </div>
									</th>
									<th class="colwidth-15">
										<div class="important">
										<div
											class="font-size-12 line-height-18">适航审批单号</div>
										<div class="font-size-9 line-height-18">Airworthiness No</div>
										</div>
									</th>	
									<th class="colwidth-20">
										<div class="important">
											<div class="font-size-12 line-height-18">适航批准人</div>
											<div class="font-size-9 line-height-18">Airworthiness Approver</div>
										</div>
									</th>
									<th class="colwidth-20">
										<div class="important">
												<div class="font-size-12 line-height-18">不适航批准人</div>
												<div class="font-size-9 line-height-18">Unairworthiness Approver</div>
										</div>
									</th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">停场原因</div>
										<div class="font-size-9 line-height-18">Reason of Stop</div>
									</th>
									<th class="colwidth-20"><div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</th>		
									<th class="colwidth-13"><div
												class="font-size-12 line-height-18">制单人</div>
											<div class="font-size-9 line-height-18">Creator</div>
									</th>
									<th class="colwidth-13"><div
												class="font-size-12 line-height-18">制单时间</div>
											<div class="font-size-9 line-height-18">Create Time</div>
									</th>	
									<th class="colwidth-15"><div
												class="font-size-12 line-height-18">组织机构</div>
											<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
						</table>
					</div>
						<div class="col-xs-12 text-center padding-right-0 page-height" id="air_pagination">
						</div>
					</div>
				</div>
						
					
					
				 
			</div>
			
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/production/airworthinessdirective/airworthinessdirective_manage.js"></script>

</body>
</html>