<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';

//来源ID(评估单ID)
var todo_lyid = "${param.todo_lyid}";
var todo_fjjx = "${param.todo_fjjx}";
var todo_ywid = "${param.todo_ywid}";
var todo_dprtcode = "${param.todo_dprtcode}";
var todo_zt = "${param.todo_zt}";

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
					$('#wxxmItemSearch').trigger('click');//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<input type="hidden" id="type" value="1" />
	<input type="hidden" id="pageType" value="1" />
	<input type="hidden" id="ajustHeight" value="90" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
  

	<div class="page-content" >
	<div class='col-xs-12 ' style='background:white;padding-left:0px;padding-right:0px;padding-top:0px;'>
	<div class='col-sm-3 col-xs-12' style='padding-left:0px;padding-right:10px;' id="left_div">
	<!-- 维修方案版本 -->
    <div class="panel panel-primary left_first_content" style="height:50%">
		    <!-- panel-heading -->
			<div class="panel-heading">
				<div class="font-size-12 line-height-12">维修方案</div>
				<div class="font-size-9 line-height-9">Maintenance Program</div>
			</div>
			<div class="panel-body padding-left-0 padding-right-0 left_first_body" style='padding-top:0px;' >
				<!-- 搜索 -->
				<div style='margin-bottom:10px;' >
				  
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group input-group-new">
						<div class="input-group-addon" style='padding-top:0px;padding-right:5px;'>
							<div class="font-size-12 line-height-12 text-left">机型</div>
							<div class="font-size-9 line-height-12 text-left">A/C Type</div>
						</div>
						<select class='form-control' id="fjjx" onchange="maintenancePlan.reload()">
							<option>请选择</option>
						</select>
	                    <div class="input-group-addon" style='padding-top:0px;padding-right:0px;'>
		                    <a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left hidden checkPermission" 
		                    	id="add_btn" onclick="maintenancePlan.showAddModal()" href="javascript:void(0);"  
		                    	permissioncode='project2:maintenanceproject:main:01'>
								<div class="font-size-12">新增</div>
								<div class="font-size-9">Add</div>
							</a>
							<a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left hidden checkPermission"
								 id="revision_btn" onclick="maintenancePlan.showRevsionModal()" href="javascript:void(0);" 
								 permissioncode='project2:maintenanceproject:main:04'>
								<div class="font-size-12">改版</div>
								<div class="font-size-9">Revision</div>
							</a>
							<a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left hidden checkPermission" 
								id="edit_btn" onclick="maintenancePlan.showEditModal()" href="javascript:void(0);" 
								permissioncode='project2:maintenanceproject:main:02'>
								<div class="font-size-12">修改</div>
								<div class="font-size-9">Edit</div>
							</a>
							<a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left hidden checkPermission" 
								id="production_btn" onclick="maintenancePlan.showSubmitProductionModal()" href="javascript:void(0);" 
								permissioncode='project2:maintenanceproject:main:05'>
								<div class="font-size-12">通知生产确认</div>
								<div class="font-size-9">Confirm</div>
							</a> 
	                    </div>
	                    <div class="input-group-addon hidden checkPermission" permissioncode='project2:maintenanceproject:main:03' 
	                    	style='padding-top:0px;padding-right:0px;' id="submit_btn">
	                    	<a href="javascript:void(0);"  class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left" onclick="maintenancePlan.showSubmitModal()">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</a>
	                    </div>
                	</div><!-- /input-group -->
				</div>
				<div class='clearfix'></div>
				<hr style="margin-top: 4px;margin-bottom: 0px;">
				</div>
			    <table class='table text-center table-thin table-hover' style='border-bottom:1px solid #d5d5d5;'>
			    <thead>
			    <tr>
			    <th class="colwidth-15">
				    <div class="font-size-12 line-height-12">版本</div>
					<div class="font-size-9 line-height-12">Rev.</div>
				</th>
			    <th class="colwidth-15">
				    <div class="font-size-12 line-height-12">生效日期</div>
					<div class="font-size-9 line-height-12">Effective Date</div>
			    </th>
			    <th class="colwidth-15">
				    <div class="font-size-12 line-height-12">状态</div>
					<div class="font-size-9 line-height-12">Status</div>
			    </th>
			    </tr>
			    </thead>
			    <tbody id='maintenance_plan_version'>
			   <!--  <tr class='cursor-pointer'>
				    <td>
				    1.1&nbsp;&nbsp;<a href='#' style='text-decoration:none;'><i class='icon-eye-open color-red'></i></a>
				    </td>
				    <td>
				    2017-01-01
				    </td>
			    </tr>
			     <tr class='cursor-pointer'>
				    <td>
				    0.9&nbsp;&nbsp;<a href='#' style='text-decoration:none;'><i class='icon-eye-open'></i></a>
				    </td>
				    <td>
				    2017-01-01
				    </td>
			    </tr> -->
			    </tbody>
			    </table>
			</div>
    </div>
     <%@ include file="/WEB-INF/views/project2/maintenance/maintenance_info.jsp"%>
    </div>
    <div class='col-sm-9 col-xs-12 padding-right-0 padding-left-0' id="right_div">
    	<div style="position:absolute;left:-8px;" id="maintenance_toggle_div">
			<i id="maintenance_toggle_btn" class="icon-caret-left cursor-pointer" style="font-size: 22px;" onclick="maintenancePlan.toggleMaintenance(this)"></i>
		</div>
    <%@ include file="/WEB-INF/views/project2/maintenance/maintenance_item_list.jsp"%>
    </div>
    <div class='clearfix'></div>
    
    </div>	
	</div>
	
	<!-- 编辑维修方案模态框Start -->
	<div class="modal fade modal-new" id="maintenance_schedule_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false"  data-backdrop="static">
		<div class="modal-dialog modal-lg" style="width:80%;">
			<div class="modal-content">
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 ">
	                       		<span class="hidden addView">新增维修方案</span>
	                       		<span class="hidden editView1">修改维修方案</span>
	                       		<span class="hidden editView2">提交维修方案</span>
								<span class="hidden submitView">通知生产确认维修方案</span>
								<span class="hidden revisionView">改版维修方案</span>
						   </div>
							<div class="font-size-12">
								<span class="hidden addView">Add Maintenance Program</span>
								<span class="hidden editView1">Edit Maintenance Program</span>
								<span class="hidden editView2">Submit Maintenance Program</span>
								<span class="hidden submitView">Notice Maintenance Program</span>
								<span class="hidden revisionView">Revision Maintenance Program</span>
							</div>
						  </div>
						  <div class='pull-right'>
					  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					  	  </div>
						  <div class='clearfix'></div>
                       </h4>
                </div>
				<div class="modal-body margin-top-0">
					<form id="maintenance_scheme_form">
					<input type="hidden" id="maintenance_schedule_modal_id">
					<input type="hidden" id="maintenance_schedule_modal_type">
					<div class="col-xs-12 margin-top-8">
						<div class="input-group-border" style="padding-top: 15px;padding-bottom: 5px;">
							<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">机型</div>
									<div class="font-size-9">A/C Type</div>
								</label>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_schedule_modal_jx" type="text" disabled="disabled">
								</div>
							</div>
							<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group hidden revisionView editView submitView">
								<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">维修方案编号</div>
									<div class="font-size-9">Code</div>
								</label>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_schedule_modal_wxfabh" type="text" disabled="disabled">
								</div>
							</div>
							<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2"><span style="color: red" class="addView editView revisionView">*</span>维修方案描述</div>
									<div class="font-size-9">Description</div>
								</label>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
								</div>
							</div>
							<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2"><span style="color: red" class="addView editView">*</span> 版本</div>
									<div class="font-size-9">Version</div>
								</label>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group hidden revisionView">
								<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 新版本</div>
									<div class="font-size-9">Version</div>
								</label>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<input class="form-control" id="maintenance_schedule_modal_bb_new" name="bbNew" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
								</div>
							</div>
							<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group hidden submitView">
								<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 计划生效日期</div>
									<div class="font-size-9">Plan Date</div>
								</label>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<input id="maintenance_schedule_modal_jhSxrq" name="jhSxrq" class="form-control datepicker " data-date-format="yyyy-mm-dd" type="text" placeholder="请选择日期">
								</div>
							</div>
							
							<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- 技术评估单 -->
						
							<div class="clearfix"></div>
							<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">改版依据</div>
									<div class="font-size-9">Based On</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="form-control" id="maintenance_schedule_modal_gbyj" rows="5" maxlength="1000"></textarea>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">备注</div>
									<div class="font-size-9">Note</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="form-control" id="maintenance_schedule_modal_bz" rows="5" maxlength="300"></textarea>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
					</form>
					<div class="clearfix"></div>   
				</div>
				
				<div class="modal-footer">
					<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
							<span class="input-group-addon modalfootertip" >
				                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
							</span>
							<span class="input-group-addon modalfooterbtn">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 hidden addView editView1" onclick="maintenancePlan.save(1)">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 hidden editView2" onclick="maintenancePlan.submit(2)">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 hidden revisionView" onclick="maintenancePlan.revision()">
								<div class="font-size-12">改版</div>
								<div class="font-size-9">Revision</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 hidden submitView" onclick="maintenancePlan.doSubmitProduction()">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
							</span>
						</div>
					</div>
					<div class="clearfix"></div> 
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<!-- 编辑维修方案模态框End -->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%><!-------日志 -------->
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%><!-------日志差异 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/maintenance/maintenance_plan.js"></script>
<%@ include file="/WEB-INF/views/open_win/selectEvaluation.jsp"%><!-- -选择评估单列表 -->
</body>
</html>
