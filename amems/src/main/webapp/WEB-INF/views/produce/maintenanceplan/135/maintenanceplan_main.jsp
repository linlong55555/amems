<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维修计划</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style>
.webui-popover{
	background:white;
	padding-bottom:0px;
	min-height:0px !important;
}
</style>

<script type="text/javascript">
	var paramJgdm = '${param.dprtcode}';
	var paramFjzch = '${param.fjzch}';
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					maintencePlanList.searchGd();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<div class="page-content" id="maintencePlanList">	
	  <div class='col-xs-12 ' style='background:white;padding-left:0px;padding-right:0px;padding-top:0px;'>
	  	<div class='col-sm-new-22 col-xs-12' style='padding-left:0px;padding-right:10px;' id="left_div">
	  		<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="font-size-12 line-height-12">工包列表</div>
                    <div class="font-size-9 line-height-9">Work Package list</div>
				</div>
				<div class="panel-body padding-left-0 padding-right-0 padding-bottom-0" id="gb_body_div">
				   <div style='padding-bottom:8px;; ' class='searchContent'>
				  <!--      <div class="" style='padding-top:0px;padding-right:0px;'>
		                    <a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-8 pull-left"  >
								<div class="font-size-12">下发</div>
								<div class="font-size-9">Issue</div>
							</a>
							<a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left"
								href="javascript:closeWorkpackage()" >
								<div class="font-size-12">完工关闭</div>
								<div class="font-size-9">Complete</div>
							</a> 
							<a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left">
								<div class="font-size-12">指定结束</div>
								<div class="font-size-9">End</div>
							</a>
							<a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left" 
								 >
								<div class="font-size-12">打印</div>
								<div class="font-size-9">Print</div>
							</a>
							<div class='clearfix'></div>
						</div> -->
						<div class='padding-leftright-8'>
						<div class="input-group input-group-new col-xs-12" >
						<div class="input-group-addon" style='padding-top:0px;padding-right:5px;text-align:right;padding-bottom:0px;'>
							<div class="font-size-12 line-height-12" style='width:42px;'><span class='color-red'>*</span>飞机</div>
							<div class="font-size-9 line-height-12" style='width:42px;'>The plane</div>
						</div>
						<select id="fjzch_search" class="form-control" onchange="maintencePlanList.changeModel()">
							<option value="" selected="selected">显示全部All</option>
						</select>
						</div>
						<div class="input-group input-group-new margin-top-8">
						<div class="input-group-addon" style='padding-top:0px;padding-right:5px;text-align:right;padding-bottom:0px;'>
							<div class="font-size-12 line-height-12" style='width:42px;'>工包</div>
							<div class="font-size-9 line-height-12" style='width:42px;'>Packages</div>
						</div>
						<input id="keyword_wp_search" type='text' class='form-control' onkeyup="maintencePlanList.changeWP(this)">
						<!--  <div class="input-group-addon" style='padding-top:0px;padding-right:0px;padding-bottom:0px;'>
						 <a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left" 
								  href="#" >
								<div class="font-size-12">查询</div>
								<div class="font-size-9">Search</div>
							</a>
						 </div> -->
						</div>
						<!--  -->
						<div class="input-group input-group-new margin-top-8">
						<div class="input-group-addon" style='padding-top:0px;padding-right:5px;text-align:right;padding-bottom:0px;'>
							<div class="font-size-12 line-height-12" style='width:42px;'></div>
							<div class="font-size-9 line-height-12" style='width:42px;'></div>
						</div>
						<div class="pull-right" >
							<a href="javascript:maintencePlanList.openMaterialToolDetailGb();"  class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">航材工具需求清单</div>
								<div class="font-size-9">Material & Tools</div>
						    </a> 
						</div>
						</div>
						<!-- <div class="col-xs-12" style='margin-bottom:8px;'>
						  <label class='padding-left-5 ' style='margin-top:6px;font-weight:normal;' >
							<input type='checkbox' name='wxxmlx' value='4' style='vertical-align:text-bottom' checked="checked"/>&nbsp;未下发&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;' >
								<input type='checkbox' name='wxxmlx' value='1' style='vertical-align:text-bottom;' checked="checked"/>&nbsp;已下发&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;' >
								<input type='checkbox' name='wxxmlx' value='2' style='vertical-align:text-bottom;' checked="checked"/>&nbsp;已反馈&nbsp;&nbsp;
							</label>
						</div> -->
						<div class='clearfix'></div>
						</div>
						
				   </div>
					<div id="gb_table_top_div" >
					<table class='table table-thin table-nonbordered table-striped table-hover table-set' style='margin-bottom:0px !important;min-width:200px'>
						<thead>
						<tr>
							<th class="selectAllImg" style='width:46px;padding-left:0px;padding-right:0px;'>
								<a href="#" onclick="SelectUtil.performSelectAll('gb_table_top_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
								<a href="#" class="" onclick="SelectUtil.performSelectClear('gb_table_top_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
							</th>
							<th >
								<div class="font-size-12 line-height-12" >编号/描述</div>
		                        <div class="font-size-9 line-height-12" >No./Desc</div>
							</th>
							<th style='width:58px;' class='notwrap'>
								<div class="font-size-12 line-height-12">操作</div>
		                        <div class="font-size-9 line-height-12">Operation</div>
							</th>
						</tr>
						</thead>
						<tbody id="gb_list_tbody">
						<!-- <tr> -->
						<!-- <td class='text-center'><input type='checkbox'/></td> -->
						<!-- <td>
							<p class='margin-bottom-0'><a href='#' style='margin-right:5px;'>GB_2017_001</a>未下发</p>
							<p class='margin-bottom-0'>XXXXXXXXXXXXXXXX</p>
						</td>
						<td class='text-center'><button class="btn btn-primary btn-xs" >完工反馈</button></td>
						</tr>
						<tr> -->
						<!-- <td class='text-center'><input type='checkbox'/></td> -->
						<!-- <td>
							<p class='margin-bottom-0'><a href='#' style='margin-right:5px;'>GB_2017_001</a>已下发</p>
							<p class='margin-bottom-0'>XXXXXXXXXXXXXXXX</p>
						</td>
						<td class='text-center'>
						<button class="btn btn-primary btn-xs" >完工反馈</button>
						</td>
						</tr>
						<tr> -->
						<!-- <td class='text-center'><input type='checkbox'/></td> -->
						<!-- <td>
							<p class='margin-bottom-0'><a href='#' style='margin-right:5px;'>GB_2017_001</a>已下发</p>
							<p class='margin-bottom-0'>XXXXXXXXXXXXXXXX</p>
						</td>
						<td class='text-center'>
						<button class="btn btn-primary btn-xs" >完工反馈</button>
						</td>
						</tr>
						<tr> -->
						<!-- <td class='text-center'><input type='checkbox'/></td> -->
						<!-- <td>
							<p class='margin-bottom-0'><a href='#' style='margin-right:5px;'>GB_2017_001</a>未下发</p>
							<p class='margin-bottom-0'>XXXXXXXXXXXXXXXX</p>
						</td>
						<td class='text-center'><button class="btn btn-primary btn-xs" >完工反馈</button></td>
						</tr>
						<tr> -->
						<!-- <td class='text-center'><input type='checkbox'/></td> -->
						<!-- <td>
							<p class='margin-bottom-0'><a href='#' style='margin-right:5px;'>GB_2017_001</a>未下发</p>
							<p class='margin-bottom-0'>XXXXXXXXXXXXXXXX</p>
						</td>
						<td class='text-center'><button class="btn btn-primary btn-xs" >完工反馈</button></td>
						</tr> -->
						</tbody>
					</table>
					</div>
				</div>
		    </div>
	  	</div>
	  	<div class='col-sm-new-78 col-xs-12' style='padding-left:0px;padding-right:0px;' id="right_div">
		  	<!-- <div id="maintenance_toggle_div" style="position: absolute; left: -11px; top: 250px;">
			<i class="cursor-pointer icon-caret-left" style="font-size: 33px;color:#cd05a1;" onclick="maintencePlanList.toggleMaintenancePlan(this)"></i>
			</div> -->
	  	    <%@ include file="/WEB-INF/views/produce/maintenanceplan/135/maintenanceplan_workorderlist.jsp"%>
	  	</div>
	  </div>
	</div>
	<script src='${ctx}/static/js/thjw/produce/maintenanceplan/135/maintenanceplan_main.js'></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/135/workorder_main_tab.js"></script> <!-- 工单table信息 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/135/workorder_add.js"></script> <!-- 新增工单 -->
	<%@ include file="/WEB-INF/views/produce/maintenanceissuedplan/workorder_feedback.jsp"%><!-- -工单反馈 -->
	<%@ include file="/WEB-INF/views/open_win/hc_list_modal.jsp"%><!-- 航材工具清单列表 -->
	<%@ include file="/WEB-INF/views/produce/workpackage/135/workpackage_close.jsp"%><!-- -关闭工包 -->
	<%@ include file="/WEB-INF/views/produce/workorder/135/workorder_wg_close.jsp"%><!-- -关闭工单 -->
	<%@ include file="/WEB-INF/views/produce/workorder/135/workorder_zd_close.jsp"%><!-- -指定结束工单 -->
	<%@ include file="/WEB-INF/views/produce/workpackage/135/workpackage_end.jsp"%><!-- -指定结束工包 -->
	<%@ include file="/WEB-INF/views/produce/workpackage/135/workpackage_feedback.jsp"%><!-- -工包完工反馈 -->
	<%@ include file="/WEB-INF/views/produce/workorder/135/workorder_feedback.jsp"%><!-- -工单完工反馈 -->
	<%@ include file="/WEB-INF/views/produce/workpackage/135/workpackage_close.jsp"%><!-- -完工关闭工包 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
		<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
	 <%@ include file="/WEB-INF/views/open_win/firm_search.jsp"%><!-- 选择外委供应商-->
	 
	    <%@ include file="/WEB-INF/views/open_win/installationlist_replace.jsp"%><!-------拆换记录-------->
	<%@ include file="/WEB-INF/views/open_win/installationlist_installed.jsp"%><!-------装上件-------->
	<%@ include file="/WEB-INF/views/open_win/installationlist_removed.jsp"%><!-------拆下件-------->
	<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_parent.jsp"%><!-------父节点-------->
	<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%><!-------证书-------->
	<%@ include file="/WEB-INF/views/open_win/planePosition_search.jsp"%><!-------飞机站位 -------->
	
	<%@ include file="/WEB-INF/views/open_win/inventory_distribution_details_view.jsp"%><!-------库存分布详情 -------->
	<%@ include file="/WEB-INF/views/open_win/equivalent_substitution_view.jsp"%><!-------等效替代 -------->
	
	<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
	
	<%@ include file="/WEB-INF/views/common/produce/feedback_replacement_tab_view.jsp" %>  <!-- 完工反馈及拆换件记录详情弹窗 -->
	
	<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!-- ATA章节对话框 -->
	 <%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------报告人对话框 -------->
	 
</body>
</html>
