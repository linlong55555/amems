<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>年度计划</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
	var todo_ywid = "${param.todo_ywid}";
	var todo_jd = "${param.todo_jd}";
	var todo_nf = "${param.todo_nf}";
	var todo_dprtcode = "${param.todo_dprtcode}";
	
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
					annual_plan.searchRevision();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
 <input type="hidden"   id="id">
 <input type="hidden"   id="oldzt">
	<div class="page-content tabcontent-main">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			  <div  class='searchContent row-height margin-top-0'>
				<div class="col-lg-8 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					<a id="tj" href="javascript:Annualplan_Edit_Modal.submit();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="quality:annualplan:main:03" style="float: left;">
						<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</a>
					<a href="javascript:course_list_table_div.exportExcel();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="quality:annualplan:main:05" style="float: left; margin-left: 10px;">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a>
					<div class="pull-left padding-left-0 padding-right-0 margin-top-0 margin-right-10" >
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">年度</div>
							<div class="font-size-9">Year</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-8" >
							<input class="form-control" id="year_search" name="year_search"  data-date-format="yyyy" type="text" onchange="annual_plan.onchangeDprtcode()" style='height:33px;' />
						</div>
					</div>
					<div class="pull-left padding-left-0 padding-right-0 margin-top-0  margin-right-10" >
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">版本</div>
							<div class="font-size-9">Version</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-8" >
							<select class='form-control' id="bbh" onchange="annual_plan.changeDate()">
								<option value="">-</option>
							</select>
						</div>
					</div>	
					<div class="pull-left padding-left-0 padding-right-0 margin-top-0 " >
						<p class='pull-left' style='border:1px solid #cccccc;height:33px;line-height:33px;padding-left:10px;padding-right:10px;border-top-right-radius:0px;border-bottom-right-radius:0px;margin-bottom:0px;'><i class=' icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:annualplan:main:03' onClick="Annualplan_Edit_Modal.open()" title='修改 Edit'></i></p>
					</div>
					<span class="padding-top-0 padding-bottom-0 pull-left checkPermission "  style="float: left;text-decoration:none;position:relative; margin-left: 10px;">
						<i class=" pull-left" style="font-size:16px;margin-top:8px;margin-right:3px;" id="ndjhsm" ></i>
						<span  class="badge" style="position: absolute; background:red ! important;" id="fjnum"></span>
						<i class=" pull-left" style="font-size:16px;margin-top:8px;margin-right:4px;margin-left:30px;" id="zt" ></i>
					</span>				
				</div>
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
					<!-- 搜索框start -->
					<div class="col-xs-12 input-group input-group-search">
							<input type="text"  class="form-control "  placeholder='审核对象/责任审核人' id="keyword_search">
		                    <div class="input-group-addon btn-searchnew">
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="annual_plan.searchRevision();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   			</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="annual_plan.search();">
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
					<!-- 搜索框end -->
				</div>
				<div class="clearfix"></div>
				<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none border-cccccc" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">审核对象</div>
							<div class="font-size-9">Object</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<div class='input-group' style='width:100%'>
							<input type="hidden"  id="annual_plan_module_shdx_searchid">
							<input type="hidden"  id="annual_plan_module_shdx_search">
						    <input type="text" id="annual_plan_module_shdx_searchbh" class="form-control"   maxlength="20"  />
							<span class="input-group-btn">
								<button type="button"  class="btn btn-default" data-toggle="modal" onclick="annual_plan_module.openzrdw('shdx_search',null)">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						    </div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">责任审核人</div>
							<div class="font-size-9">Auditor</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<div class='input-group' style='width:100%'>
							<input type="hidden"   id="annual_plan_module_shzrr_searchid">
						    <input type="text" id="annual_plan_module_shzrr_search" class="form-control "  maxlength="20"  />
							<span class="input-group-btn">
								<button type="button" id="zjh_search_btn" class="btn btn-default" data-toggle="modal" onclick="annual_plan_module.openUser('shzrr_search')">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						    </div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control "  name="dprtcode" onchange="dprtChange()" >
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
					<div class='clearfix'></div>
				</div>
				<!-- 表格 -->
				<div class="col-xs-12 padding-left-0 padding-right-0 margin-top-0 first-tab-style"  >
				    <div id="hideIcon" class="pull-right" style="height:1px;padding-right:10px;margin-top:6px;">
						 <a href="javascript:annual_plan_alert_Add.open();" class="btn btn-primary btn-xs pull-left checkPermission" permissioncode="quality:annualplan:main:01" style="float: left;">
							<div class="font-size-12">新增审核计划</div>
						</a>
				    </div>
                	<ul id="myTab" class="nav nav-tabs tabNew-style ">
                    	<li class="active" id="pxjhExport">
	                    	<a href="#payStatistics" data-toggle="tab" aria-expanded="true">
		                    	<div class="font-size-12 line-height-12" style='width:90px;'>年度审核计划 </div>
							    <div class="font-size-9 line-height-9">Annual Plan</div>
							    <span  class="badge" style="position: absolute; background:red ! important;right:3px;top:5px " id="ndjhnums"></span>
	                    	</a>
                    	</li>
                      	<li class="" id="ndstExport">
	                      	<a href="#paymentDetail" data-toggle="tab" aria-expanded="false">
		                      	<div class="font-size-12 line-height-12">年度视图 </div>
						        <div class="font-size-9 line-height-9">Year View</div>
	                      	</a>
                      	</li>
                      	<li class="" >
	                      	<a href="#processRecord" data-toggle="tab">
								<div class="font-size-12 line-height-12">流程记录</div>
			                    <div class="font-size-9 line-height-9">Process record</div>
							</a>
                      	</li>
                      	<li class="">
			                <a href="#changeRecord" data-toggle="tab">
								<div class="font-size-12 line-height-12">变更记录</div>
			                    <div class="font-size-9 line-height-9">Change record</div>
							</a>
                      	</li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade active in" id="payStatistics">
                     	<%@ include file="/WEB-INF/views/quality/annualplan/annual_plan_main.jsp"%>
                    </div>
                    <div class="tab-pane fade" id="paymentDetail" style="margin-top: -5px;">
                      	 <%@ include file="/WEB-INF/views/quality/annualplan/annual_view.jsp"%>
                    </div>
                    <div class="tab-pane fade" id="processRecord">
                     	<%@ include file="/WEB-INF/views/quality/annualplan/process_record.jsp"%>
                    </div>
                    <div class="tab-pane fade" id="changeRecord">
                     	<%@ include file="/WEB-INF/views/quality/annualplan/change_record.jsp"%>
                    </div>
                  </div>
              </div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/quality/annualplan/annualplan_edit.jsp"%><!-------维护年度计划 -------->
	 
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/annualplan/annualplan.js"></script><!--当前界面js  -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/annualplan/annualplan_module.js"></script><!--当前界面公共js  -->
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/annualplan/annual_plan_main.js"></script><!--年度审核计划js  -->
	<%@ include file="/WEB-INF/views/quality/annualplan/annual_plan_open.jsp"%><!-------年度审核计划弹出框 -------->
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/annualplan/annual_view.js"></script><!--年度视图js  -->
	
	<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
	<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
</body>
</html>