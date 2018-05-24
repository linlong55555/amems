<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- todo -->
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
					$('#wxxmItemSearch').trigger('click');;//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<input type="hidden" id="type" value="2" />
	<input type="hidden" id="pageType" value="2" />
	<input type="hidden" id="ajustHeight" value="90" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
  

	<div class="page-content" >
	<div class='col-xs-12 ' style='background:white;padding-left:0px;padding-right:0px;'>
	<div class='col-sm-3 col-xs-12' style='padding-left:0px;padding-right:10px;' id="left_div">
	<!-- 维修方案版本 -->
    <div class="panel panel-primary left_first_content" style="height:50%">
		    <!-- panel-heading -->
			<div class="panel-heading">
				<div class="font-size-12 line-height-12">维修方案审核</div>
				<div class="font-size-9 line-height-9">Maintenance Program Audit</div>
			</div>
			<div class="panel-body padding-left-0 padding-right-0 left_first_body" style='padding-top:0px;' >
			    <table class='table text-center table-thin' style='border-bottom:1px solid #d5d5d5;'>
			    <thead>
			    <tr>
			    <th class="colwidth-10">
				    <div class="font-size-12 line-height-12">版本</div>
					<div class="font-size-9 line-height-12">Rev.</div>
				</th>
			    <th class="colwidth-15">
				    <div class="font-size-12 line-height-12">机型</div>
					<div class="font-size-9 line-height-12">A/C Type</div>
			    </th>
			    </tr>
			    </thead>
			    <tbody id='maintenance_plan_version'>
			    </tbody>
			    </table>
			</div>
    </div>
     <%@ include file="/WEB-INF/views/project2/maintenance/maintenance_info.jsp"%>
    </div>
    <div class='col-sm-9 col-xs-12 padding-right-0 padding-left-0' id="right_div">
    	<div style="position:absolute;left:-8px;" id="maintenance_toggle_div">
			<i id="maintenance_toggle_btn" class="icon-caret-left cursor-pointer" style="font-size: 22px;" onclick="maintenancePlanAudit.toggleMaintenance(this)"></i>
		</div>
    <%@ include file="/WEB-INF/views/project2/maintenance/maintenance_item_list.jsp"%>
    </div>
    <div class='clearfix'></div>
    
    </div>	
	</div>
	
	<!-- 维修方案审核模态框Start -->
	<div class="modal fade modal-new" id="auditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
		<div class="modal-dialog" style="width:80%;">
			<div class="modal-content">
			
				<div class="modal-header modal-header-new" >
	         		<h4 class="modal-title" >
	             		<div id="batchReviewHead" class='pull-left'>
	                 		<div class="font-size-14 ">维修方案审核</div>
							<div class="font-size-12">Maintenance Program Audit</div>
						</div>
		 				<div class='pull-right' style='padding-top:10px;'>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
						</div>
						<div class='clearfix'></div>
	               	</h4>
	          	</div>
			
				<div class="modal-body margin-top-0">
				  	<div class="col-xs-12 margin-top-8">
						<div class="input-group-border">
			            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">机型</div>
										<div class="font-size-9">A/C Type</div>
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<input class="form-control" id="maintenance_schedule_modal_jx" type="text" disabled="disabled">
									</div>
								</div>
								
				            	<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">维修方案编号</div>
										<div class="font-size-9">MP No.</div>
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<input class="form-control" id="maintenance_schedule_modal_wxfabh" type="text" disabled="disabled">
									</div>
								</div>
				            	<div class="clearfix"></div>
				            	
				            	<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">维修方案描述</div>
										<div class="font-size-9">Description</div>
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<input class="form-control" id="maintenance_schedule_modal_zwms" type="text" disabled="disabled">
									</div>
								</div>
								
				            	<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">版本</div>
										<div class="font-size-9">Rev.</div>
									</label>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<input class="form-control" id="maintenance_schedule_modal_bb" type="text" disabled="disabled" value="V1.0">
									</div>
								</div>
								
				            	<div class="clearfix"></div>
								
								<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- 技术评估单 -->
						
								<div class="clearfix"></div>
								<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">改版依据</div>
										<div class="font-size-9">Based On</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="maintenance_schedule_modal_gbyj" rows="5" maxlength="1000" readonly></textarea>
									</div>
								</div>
								<div class="clearfix"></div>
								
								<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">备注</div>
										<div class="font-size-9">Note</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="maintenance_schedule_modal_bz" rows="5" maxlength="300" readonly></textarea>
									</div>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
					<div class="col-xs-12">
						<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp" %> <!--流程信息 -->	
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div class="modal-footer">
					<span class="input-group-addon modalfootertip" >
		                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
					<span class="input-group-addon modalfooterbtn">
				    	<button type="button" onclick="maintenancePlanAudit.agree()" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">审核通过</div>
							<div class="font-size-9">Audited</div>
						</button>
				    	<button type="button" onclick="maintenancePlanAudit.reject()" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">审核驳回</div>
							<div class="font-size-9">Audit Reject</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</sapn>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- 维修方案审核模态框End -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/maintenance/maintenance_plan_audit.js"></script>
</body>
</html>
