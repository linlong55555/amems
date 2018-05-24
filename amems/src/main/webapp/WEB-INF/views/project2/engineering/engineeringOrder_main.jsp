<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>EO管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
 
//来源ID(评估单ID)
var todo_lyid = "${param.todo_lyid}";
var todo_fjjx = "${param.todo_fjjx}";
var todo_ywid = "${param.todo_ywid}";
var todo_dprtcode = "${param.todo_dprtcode}";
var todo_jd = "${param.todo_jd}";

</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<div class="page-content" id='engineer_mainpage'>
		<div class="panel panel-primary">
		    <!-- panel-heading -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body" >
			<!-- 搜索框 -->
			<div  class='searchContent'>
				<div class="col-md-3 col-sm-12 col-xs-12 padding-left-0 form-group">
					<a href="javascript:eo_main.openWinAdd();" permissioncode='project2:eo:main:01'   class="checkPermission btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height margin-right-8" >
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
					<a href="javascript:exportEoList();"  permissioncode='project2:eo:main:09'  class="checkPermission btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height margin-right-8" >
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a> 
				 </div>
				 <div class="col-md-3 col-sm-6  col-xs-12 padding-left-0 padding-right-0 form-group" >
						<span class="col-lg-3 col-md-4 col-sm-5 col-xs-6 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机型</div>
							<div class="font-size-9">A/C Type</div>
						</span>
						<div class="col-lg-9 col-md-8 col-sm-7 col-xs-6 padding-left-8 padding-right-0">
							<select id="jx_search" class="form-control" onchange="eo_main.reload();">
								<option value="" selected="selected">显示全部All</option>
							</select>
						</div>
				  </div>
				  <div class="col-md-3 col-sm-6  col-xs-12 padding-left-0 padding-right-0 form-group" >
						<span class="col-lg-3 col-md-4 col-sm-5 col-xs-6  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-lg-9 col-md-8 col-sm-7 col-xs-6 padding-left-8 padding-right-0">
							<select id="zt_search" class='form-control' onchange="eo_main.reload();">
								<option value="" >显示全部All</option>
								<c:forEach items="${engineeringOrderStatusEnum}" var="item">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
							</select>
						</div>
				  </div>
				  <div class="col-md-3 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
						<input type="text" placeholder='EO编号/标题'  class="form-control" id="keyword_search">
	                    <div class="input-group-addon btn-searchnew" >
	                    	<button id="eoMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="eo_main.doSearch();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                  		</button>
	                    </div>
	                    <div class="input-group-addon btn-searchnew-more">
		                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="eo_main.search();">
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
					<!-- 更多的搜索框 -->
					<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none search-height border-cccccc" id="divSearch">
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">维护日期</div>
								<div class="font-size-9">Date</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="date-range-picker" id="bzrq_search"/>
							</div>
						</div>
					
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode_search" class="form-control "  name="dprtcode"  onchange="eo_main.changeDprt()">
									<c:forEach items="${accessDepartment}" var="type">
											<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					
						<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="eo_main.searchreset()" >
								<div class="font-size-12">重置</div>
								<div class="font-size-9">Reset</div>
							</button>
						</div>
						<div class="clearfix"></div>
					</div>
					<!-- 更多搜索 -->
					<div class='clearfix'></div>
				</div>
				<!-- 搜索框 -->
				
				<div class='table_pagination'>
					<!-- 表格 -->
					<div id="engineering_main_table_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: auto;">
							<table  class="table table-thin table-bordered  table-hover table-set" id="eo_main_table" >
								<thead>
									<tr>
									   <th class="colwidth-9 fixed-column">
										   <div class="font-size-12 line-height-18">操作</div>
								           <div class="font-size-9 line-height-18">Operation</div>
									   </th>
									   <th class='colwidth-15 sorting' onclick="eo_main.orderBy('eobh')" id="eobh_order">
									   	   <div class="important">
											   <div class="font-size-12 line-height-18">工程指令编号</div>
									           <div class="font-size-9 line-height-18">EO No.</div>
								           </div>
									   </th>
									   <th class='colwidth-9 sorting'  onclick="eo_main.orderBy('bb')" id="bb_order">
										   <div class="font-size-12 line-height-18">版本</div>
								           <div class="font-size-9 line-height-18">Rev.</div>
									   </th>
									   <th class='colwidth-18 sorting'  onclick="eo_main.orderBy('jx')" id="jx_order">
										   <div class="font-size-12 line-height-18">机型</div>
								           <div class="font-size-9 line-height-18">A/C Type</div>
									   </th>
									   <th class="downward colwidth-18" onclick="eo_main.viewAll()" name="pgd" >
									       <div class="">
											   <div class="font-size-12 line-height-18">关联技术评估单</div>
									           <div class="font-size-9 line-height-18">Related Evaluation</div>
								           </div>
									   </th>
									   <th class="colwidth-18" onclick="eo_main.viewAll()" name="lywj">
										   <div class="font-size-12 line-height-18">来源文件</div>
								           <div class="font-size-9 line-height-18">File</div>
									   </th>
									   <th class="colwidth-10" >
									   		<div class="font-size-12 line-height-18" >圈阅情况</div>
									   		<div class="font-size-9 line-height-18" >Redlining Case</div>
									   </th>							
									   <th class="colwidth-30 sorting" onclick="eo_main.orderBy('eozt')" id="eozt_order">
									   	   <div class="important">
											   <div class="font-size-12 line-height-18">标题</div>
									           <div class="font-size-9 line-height-18">Title</div>
								           </div>
									   </th>
									   <th class='colwidth-7 sorting'  onclick="eo_main.orderBy('zt')" id="zt_order">
										   <div class="font-size-12 line-height-18">状态</div>
								           <div class="font-size-9 line-height-18">Status</div>
									   </th>
									   <th class='colwidth-7 sorting'  onclick="eo_main.orderBy('sylb')" id="sylb_order">
										   <div class="font-size-12 line-height-18">适用类别</div>
								           <div class="font-size-9 line-height-18">Category</div>
									   </th>
									   <th class="colwidth-9" >
											<div class="font-size-12 line-height-18">附件</div>
											<div class="font-size-9 line-height-18">Attachment</div>
										</th>
									   <th class='colwidth-15 sorting'  onclick="eo_main.orderBy('whrid')" id="whrid_order">
										   <div class="font-size-12 line-height-18">维护人</div>
								           <div class="font-size-9 line-height-18">Maintainer</div>
									   </th>
									   <th class='colwidth-15 sorting'  onclick="eo_main.orderBy('whsj')" id="whsj_order">
										   <div class="font-size-12 line-height-18">维护时间</div>
								           <div class="font-size-9 line-height-18">Maintenance Time</div>
									   </th>
									   <th class='colwidth-9' >
										   <div class="font-size-12 line-height-18">组织结构</div>
								           <div class="font-size-9 line-height-18">Organization</div>
									   </th>
									</tr>
								</thead>
								<tbody id='engineering_list'>
								</tbody>
						</table>
					</div>
					<!-- 表格 -->
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="engineering_pagination"></div>
					<div class='clearfix'></div>
				</div>
				
				<!-- 隐藏的div -->
				<div class='displayContent' style='display:none;'>
				   <div class='col-xs-3 padding-left-0' style='padding-right:4px;'>
				    	<%@ include file="/WEB-INF/views/project2/engineering/engineering_topic.jsp"%>
				   </div>
				   <div class='col-xs-9 padding-right-0' style='padding-left:4px;'>
				   		<%@ include file="/WEB-INF/views/project2/engineering/engineeringperform_list.jsp"%>
				   </div>
				   <div class='clearfix'></div>
				</div>
				<!-- 隐藏的div -->
				
        	</div>
		</div>		
	</div>
  
  	<%@ include file="/WEB-INF/views/open_win/log.jsp"%> <!-- 日志 -->
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%>  <!-- 日志 -->
    
    <%@ include file="/WEB-INF/views/project2/engineering/eo_add.jsp"%><!--  新增EO-->
    <%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!--  ATA章节弹出框 -->
    <%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
    <%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
    <%@ include file="/WEB-INF/views/project2/bulletin/viewMarkupStatus.jsp"%><!-- 圈阅情况 -->
    
    <%@ include file="/WEB-INF/views/project2/engineering/engineeringOrder_choosepn.jsp"%><!--  部件 -->
    <%@ include file="/WEB-INF/views/project2/engineering/engineeringOrder_chooseplane.jsp"%> <!-- 飞机 --> 
          
    <script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/engineeringworkorder_main.js"></script> <!-- 当前页脚本 -->
    <script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/jkx_setting.js"></script> <!-- 监控项设置 -->
    <script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项单位设置 -->
    <script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 历史版本控件对话框 -->
    
	<script>
		$(document).ready(function() {
			$(window).resize(function() {
				modalBodyHeight("myModal");
				modalBodyHeight("myModalSecond");
			});
			$("#searchstatus").multiselect({
				buttonClass : 'btn btn-default',
				buttonWidth : '150px',
				buttonHeight : '34px',
				numberDisplayed : 1,
				includeSelectAllOption : true
			});
			$("#searchstatusone").multiselect({
				buttonClass : 'btn btn-default',
				buttonWidth : '150px',
				buttonHeight : '34px',
				numberDisplayed : 1,
				includeSelectAllOption : true
			});
			$("#searchstatustwo").multiselect({
				buttonClass : 'btn btn-default',
				buttonWidth : '150px',
				buttonHeight : '34px',
				numberDisplayed : 1,
				includeSelectAllOption : true
			});
			$("#searchstatusthree").multiselect({
				buttonClass : 'btn btn-default',
				buttonWidth : '150px',
				buttonHeight : '34px',
				numberDisplayed : 1,
				includeSelectAllOption : true
			});
			$("#searchstatusfour").multiselect({
				buttonClass : 'btn btn-default',
				buttonWidth : '150px',
				buttonHeight : '34px',
				numberDisplayed : 1,
				includeSelectAllOption : true
			});
			$("#searchstatusfive").multiselect({
				buttonClass : 'btn btn-default',
				buttonWidth : '150px',
				buttonHeight : '34px',
				numberDisplayed : 1,
				includeSelectAllOption : true
			});
			//打开时默认关闭collapse
			closeCollapseSetting();
			
			
			//执行待办
		    if(todo_ywid){
		    	if(todo_jd == 1 || todo_jd == 5 || todo_jd == 6){
		    		eo_main.openWinEdit(todo_ywid, todo_dprtcode);
		    	}else if(todo_jd == 2){
		    		eo_main.openWinAudit(todo_ywid, todo_dprtcode);
		    	}else if(todo_jd == 3){
		    		eo_main.openWinApprove(todo_ywid, todo_dprtcode);
		    	}else{
		    		eo_main.openWinAdd();
		    	}
		    	todo_ywid = null;
		    }
		    else if(todo_lyid) {
		    	eo_main.openWinAdd();
				//默认机型
				if(todo_fjjx) {
					todo_fjjx = decodeURIComponent(Base64.decode(todo_fjjx));
					if(todo_fjjx){
						$("#jx_add").val(todo_fjjx);
					}
					todo_fjjx = null;
				}
			}
			
		    
		    
		});
		function closeCollapseSetting(){
			/* 适用性设置 */
			$('.panel-collapse').collapse('hide');
		}
		function collapseSetting(){
			/* 适用性设置 */
			$('#appSettingCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#appSettingCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 适用性设置 */
			
			/* 工作概述 */
			$('#summaryCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#summaryCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 工作概述 */
			
			/* 改版记录 */
			$('#revRecordCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#revRecordCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 改版记录*/
			
			/* 维修改装对象 */
			$('#objectCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#objectCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 维修改装对象*/
			
			/* 重量与平衡 */
			$('#weightCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#weightCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 重量与平衡*/
			
			/* 参考文件 */
			$('#referenceCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#referenceCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 参考文件*/
			
		    /* 影响手册  */
			$('#manualsCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#manualsCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 影响手册 */
			
			/* 受影响的出版物 */
			$('#pulicationCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#pulicationCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 受影响的出版物*/
			
			/* 受影响的机载软件清单  */
			$('#softwareCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#softwareCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 受影响的机载软件清单 */
			
			/* 电气负载数据  */
			$('#electricalCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#electricalCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 电气负载数据 */
			
			/* 原有零件处理  */
			$('#disposalCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#disposalCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 原有零件处理 */
			
			/* 分发  */
			$('#distributionCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#distributionCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 分发 */
			
			/* 附件信息  */
			$('#attachmentCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#attachmentCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 附件信息 */
			
			/* 计划 */
			$('#planningCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#planningCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 计划 */
			
			/* 工时/停场时间 */
			$('#manpowerCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#manpowerCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 工时/停场时间 */
			/* 索赔 */
			$('#warrantyCollapsed').on('shown.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
			})
			$('#warrantyCollapsed').on('hidden.bs.collapse', function () {
				$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
			})
			/* 索赔 */
		}
	   
		function modalBodyHeight(id) {
			//window高度
			var windowHeight = $(window).height()
			//modal-footer的高度
			var modalFooterHeight = $("#" + id + " .modal-footer").outerHeight() || 0;
			//modal-header 的高度
			var modalHeaderHeight = $("#" + id + " .modal-header").outerHeight() || 0;
			//modal-dialog的margin-top值
			var modalDialogMargin = parseInt($("#" + id + " .modal-dialog").css("margin-top")) || 0
			//modal-body 的设置
			var modalBodyHeight = windowHeight - modalFooterHeight - modalHeaderHeight - modalDialogMargin * 2 - 8;
			$("#" + id + " .modal-body").attr('style','max-height:' + modalBodyHeight + 'px !important;overflow: auto;margin-top:0px;');
		}
	</script>
	<script type="text/javascript">
		/**初始化信息*/
		$(function(){
			Navigation(menuCode,'','','雷伟','2017-08-18','雷伟','2017-08-18');
			
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
					}else{
						$('#eoMainSearch').trigger('click');;//调用主列表页查询
					}
				}
			});
			
			$('input[name=date-range-picker]').daterangepicker().prev().on("click",
					function() {
				$(this).next().focus();
			});
			eo_main.decodePageParam();
			eo_main.initInfo();
			// 初始化日志功能
			logModal.init({
				code : 'B_G2_010'
			});
		});
	</script>
</body>
</html>
