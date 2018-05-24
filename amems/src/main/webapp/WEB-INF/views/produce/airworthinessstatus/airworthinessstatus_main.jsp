<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>不适航状态维护</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = '${param.id}';
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
					//调用主列表页查询
				}
			}
		});
	});
</script>


</head>
<body class="page-header-fixed">
	<input type="hidden" id="userId" value="" />
	<div class="clearfix"></div>
    <!-- BEGIN CONTENT -->
	<div class="page-content" >
		<!-- from start -->
		<!-- <div class="panel panel-primary"> -->
		 <div class='col-xs-12 ' style='padding-left:0px;padding-right:0px;padding-top:0px;'>
		 	<div class='col-sm-3 col-xs-12' style='padding-left:0px;padding-right:10px;' id="left_div">
			 	<div class="panel panel-primary" >
					<div class="panel-heading">
						<div class="font-size-12 line-height-12">飞机列表</div>
	                    <div class="font-size-9 line-height-9">List</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0 padding-bottom-0" >
					<form id="aftersaveForm" action="${ctx }/production/airworthinessdirective/manage" method="post">
				   
				    </form>
				    <div class="col-xs-12 padding-leftright-8 padding-bottom-8 row-height">
							<span class="pull-left col-xs-3 col-sm-3 text-right  padding-right-0 padding-left-2">
								<div class="font-size-14">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-xs-9 col-sm-9 padding-left-8 padding-right-0">
								<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="airworthiness_manage.changeOrg();" >
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${ (user.jgdm eq type.id and  empty dprtcode) or dprtcode eq type.id  }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
					</div>
					<div  id="div_qh_scorll" class="col-xs-12  padding-leftright-8 table-height" style="overflow-y: auto; ">
					</div>	
					</div>
				</div>
		 	</div>
		 	<div class='col-sm-9 col-xs-12' style='padding-left:0px;padding-right:0px;' id="right_div">
		 	<div id="airworthinessstatus_toggle_div" style="position: absolute; left: -8px; top: 250px;">
		 	 
			<i class="cursor-pointer icon-caret-left"   style="font-size: 22px;" onclick="airworthiness_manage.airworthinessStatusToggle(this)"></i>
			</div>
		 	<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="font-size-12 line-height-12">不适航状态维护</div>
	                    <div class="font-size-9 line-height-9">Unairworthiness Maintenance</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" id='airworthinessstatus-body'>
		 		    <div class="col-lg-12 padding-left-0 padding-right-0" >
					<div class="col-lg-12 padding-right-0 padding-left-0" id="divSave">
						<div class="col-lg-12  ibox-title line1 padding-right-0 padding-left-0">
						 <div class='padding-leftright-8'>
							<div class="col-lg-8 padding-right-0 padding-left-0 sh_list">
								<p>
									<span ><em>指令流水号 :</em><i id="lsh"></i></span>
									<span ><em>申请人:</em><i id="zdrrealname"></i></span>
									<span ><em>申请时间 :</em><i id="zdsj"></i></span>
								</p>
							</div>

							<div class=" pull-right">
								<button id="btnSave" onclick="airworthiness_manage.saveForm();"
									class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission"
									permissioncode='aircraftinfo:airworthiness:saveorupdate'
									>
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
									</button>
									
									<button id="btnClear" onclick="airworthiness_manage.clearForm()" class="btn btn-primary padding-top-1 padding-bottom-1 ">
									<div class="font-size-12">重置</div>
									<div class="font-size-9">Reset</div>
									</button>
							</div>
							</div>
						</div>
						
						<div class="col-lg-12 padding-leftright-8">
						<form id="saveForm">
						       <input type="hidden" id="id"/>
								<div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group" >
									 <span class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">计划停场时间</div>
										<div class="font-size-9">Date</div>
									</span>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8" >
										<div class='input-group' style='z-index:1;'>
										  <input type="text" class='form-control datetimepicker' id="bshYjrq" name="bshYjrq" />
										  <span class='input-group-btn' >
										  <input class='form-control datetimepicker readonly-style' type='text' style='width:60px;margin-left:2px;' id='bshYjsj' name='bshYjsj' readonly/>
										   </span>
										  
										</div>
									</div>
								</div>

								<div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0  form-group" >
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12  ">实际停场时间</div>
										<div class="font-size-9  ">Date</div>
									</span>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
										<div class='input-group' style="z-index:1;">
										  <input type="text" class='form-control datetimepicker' id="bshSjrq" name="bshSjrq" />
										  <span class='input-group-btn'>
										   <input class='form-control datetimepicker readonly-style' type='text' style='width:60px;margin-left:2px;' id='bshSjsj' name='bshSjsj' readonly/>
										   </span>
										 
										</div>
									</div>
									
								</div>

								 <div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group " >
									<span
										class="pull-left col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12">不适航审批单号</div>
										<div class="font-size-9">Unairworthiness No.</div>
									</span>
									<div
										class="col-lg-8 col-sm-8 padding-leftright-8">
										<input type="text" class="form-control " id="bshSpdh" maxlength="20" name="bshSpdh" />
									</div>
								</div>  
								
								<div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0  form-group" >
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0" >
										<div class="font-size-12">计划适航时间</div>
										<div class="font-size-9">Date</div>
									</span>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8" >
										<div class='input-group' style='margin-bottom:0px !important;z-index:1;'>
										  <input type="text" class='form-control datetimepicker' id="shYjrq" name="shYjrq" />
										  <span class='input-group-btn'>
										  <input class='form-control datetimepicker readonly-style' type='text' style='width:60px;margin-left:2px;' id='shYjsj' name='shYjsj' readonly/>
										   </span>
										  
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group" >
									<span  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 ">实际适航时间</div>
										<div class="font-size-9 ">Date</div>
									</span>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
										<div class='input-group' style='z-index:1;' >
										  <input type="text" class='form-control datetimepicker' id="shSjrq" name="shSjrq" />
										  <span class='input-group-btn'> 
										   <input class='form-control datetimepicker readonly-style' type='text' style='width:60px;margin-left:2px;' id='shSjsj' name='shSjsj' readonly/>
										  </span>
										 
										</div>
									</div>
									
								</div>
								<div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group" >
									<span class="pull-left col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12">适航审批单号</div>
										<div class="font-size-9">Airworthiness No.</div>
									</span>
									<div
										class="col-lg-8 col-sm-8 padding-leftright-8">
										<input type="text" class="form-control " maxlength="20" id="shSpdh" name="shSpdh" />
									</div>
								</div>
							

								<div class=" col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
									 
									<span class="col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">不适航批准人</div>
										<div class="font-size-9">Reviewer</div>
									</span>
									<div class='col-xs-8 padding-leftright-8'>
									<div class=" input-group">
										 <input type="text" class="form-control readonly-style" name="bshPzrmc" id="bshPzrmc"  readonly />
										<input type="hidden" id="bshPzrid" />
										<span class="input-group-btn">
											<button id="bshBtn" class="btn btn-default">
												<i class="icon-search" ></i>
											</button>
										</span>  
									</div>
									</div>
								</div> 
								
								  <div class=" col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0  form-group " >
									<span class="col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">适航批准人</div>
										<div class="font-size-9">Reviewer</div>
									</span>
									<div class='col-xs-8 padding-leftright-8'>
									<div class="input-group">
										<input type="text" class="form-control readonly-style" name="shPzrmc" id="shPzrmc"  readonly />
										<input type="hidden" id="shPzrid" />
										<span class="input-group-btn">
											<button id="shBtn" class="btn btn-default">
												<i class="icon-search" ></i>
											</button>
										</span>
									</div>
									</div>
								</div>  
								
								<div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group">
									<span class="pull-left col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12">停场类型</div>
										<div class="font-size-9">Type</div>
									</span>
									<div class="col-lg-8 col-sm-8 padding-leftright-8">
										<select id="bshyy" class="form-control " name="bshyy">
											 <option value="1" >计划</option>
											 <option value="2" >非计划</option>
											 
										</select>
									</div>
								</div>
								 
								
								<div class="clearfix"></div>
								<div class="col-lg-4 col-sm-4 padding-left-0 padding-right-0 form-group">
									<span class="pull-left col-lg-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12">停场原因</div>
										<div class="font-size-9">Reason of Stop</div>
									</span>
									<div class="col-lg-8 col-sm-8 padding-leftright-8">
										<input id="tcyy" class="form-control " name="tcyy" maxlength="100">
									</div>
								</div>
								<div class="col-lg-8 col-sm-8 col-xs-12 padding-left-0 padding-right-0   form-group" >
									<span class="pull-left col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">备注</div>
										<div class="font-size-9">Remark</div>
									</span>
									 <div class="col-lg-10 col-sm-10 col-xs-8 padding-leftright-8" >
										 <textarea class="form-control" id="bz" name="bz" placeholder='长度最大为300'   maxlength="300"></textarea>
									</div>
								</div>
					
								
								<div class="clearfix"></div>
								
								</form>
							</div>
						</div>
						<div class="col-lg-12 padding-leftright-8" id='panelBodyHeight'>
							<div class="panel panel-primary">
								<div  class="panel-heading bg-panel-heading" >
									<div class="pull-left">
										<div class="font-size-12">
											历史记录</div>
										<div class="font-size-9 ">Historic Records</div>
									</div>

									<div class="pull-right" style='padding-right:15px;'>
										<a href="javascript:void(0);" onclick="airworthiness_manage.more();"
											class="more">
											 <div class="font-size-12">更多</div>
											 <div class="font-size-9 ">More</div>
											 
										</a>
									</div>
									<div class='clearfix'></div>
								</div>

								<div class="panel-body" style='padding-bottom:0px;' >
								   <div class='padding-left-0 padding-right-0' style='overflow:auto;'>
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
														class="font-size-12 line-height-18">停场类型</div>
													<div class="font-size-9 line-height-18">Type</div>
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
								<!-- <div class="col-xs-12 text-center">
									<ul class="pagination "
										style="margin-top: 0px; margin-bottom: 0px;" id="pagination">
									</ul>
								</div> -->
								</div>
							</div>
						</div>
						<input id="hasResult" type="hidden" />
						
						
					</div>
					</div>
		 	</div>
		 </div>
</div>
<div class='clearfix'></div>
</div>

<div class="modal fade in modal-new" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true" style="z-index: 100000 ! important; ">
	<div class="modal-dialog" style="width: 1000px;">
		<div class="modal-content" >
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
					<div class='pull-left'>
						<div class="font-size-14 " id="titleName">历史记录</div>
						<div class="font-size-12" id="titleEname">Historic Records</div> 
					</div>
					<div class='pull-right' >
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
			  		<div class='clearfix'></div>
				</h4>
			</div>
			
			<div class="modal-body " id="searchModalBody" >
					<div class="col-lg-12 padding-left-0 padding-right-0 margin-top-8" id="divSearch">
					    <!-- <input type="hidden" id="fjzch" /> -->
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
					
					<div class="col-lg-12 padding-left-0 padding-right-0 margin-top-10" style=" overflow-y:hidden; overflow-x: scroll;">
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
											<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="colwidth-13"><div
												class="font-size-12 line-height-18">实际停场时间</div>
											<div class="font-size-9 line-height-18">Date</div>
									</th>
									
									<th class="colwidth-13"><div
												class="font-size-12 line-height-18">计划适航时间</div>
											<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="colwidth-13"><div
												class="font-size-12 line-height-18">实际适航时间</div>
											<div class="font-size-9 line-height-18">Date</div>
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
									<th class="colwidth-13">
										<div class="important">
											<div class="font-size-12 line-height-18">适航批准人</div>
											<div class="font-size-9 line-height-18">Reviewer</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="important">
												<div class="font-size-12 line-height-18">不适航批准人</div>
												<div class="font-size-9 line-height-18">Reviewer</div>
										</div>
									</th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">停场类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">停场原因</div>
										<div class="font-size-9 line-height-18">Reason of Stop</div>
									</th>
									<th class="colwidth-20"><div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</th>		
									<th class="colwidth-13"><div
												class="font-size-12 line-height-18">维护人</div>
											<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="colwidth-13"><div
												class="font-size-12 line-height-18">维护时间</div>
											<div class="font-size-9 line-height-18">Maintainer Time</div>
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
					<div class="clearfix"></div>   
					<div class="col-xs-12 text-center padding-right-0 page-height" id="air_pagination">
					</div>
					<div class="clearfix"></div>   
			</div>
			<div class="clearfix"></div>   
			<div class="modal-footer ">
				<div class="col-xs-12 padding-left-8 padding-right-0" >
					<div class="pull-right margin-right-8">
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
	 		</div>
		</div>
	</div>
</div> 

<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
 
 
 <script type="text/javascript" src="${ctx}/static/js/thjw/produce/airworthinessstatus/airworthinessstatus_main.js"></script>  
  


</body>
</html>