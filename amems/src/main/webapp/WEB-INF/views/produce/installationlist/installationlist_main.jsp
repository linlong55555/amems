<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%> 

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
					$('#installationlistMainSearch').trigger('click');//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<!-- 搜索框end -->
				<div class='searchContent row-height'>
					<div class="row" style="margin-left: 0px; margin-right: 0px;">
						<div class="pull-left">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 130px; margin-right:5px;">
								   <select id="fjzch" class="form-control" onchange="installationlist.reload()">
								   </select> 
								</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">时控/时寿</div>
								<div class="font-size-9">Monitor Type</div>
							</div>
							<div class="pull-left padding-left-5 padding-right-0">
								<select class='form-control' id="kzlx" multiple='multiple'>
									<option value="1">时控件</option>
									<option value="2">时寿件</option>
									<option value="3">非控制件</option>
							    </select>
							</div>
							<div class="pull-left text-right padding-left-5 padding-right-0">
								<div class="font-size-12">装机件类型</div>
								<div class="font-size-9">Type</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 90px; margin-right:5px;">
								   <select id="zjjlx" class="form-control" onchange="installationlist.reload()">
								   </select> 
								</div>
							</div>
						</div>
						
						<div class="pull-right">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">分类</div>
								<div class="font-size-9">Category</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 100px; margin-right:5px;">
								   <select id="wz" class="form-control" onchange="installationlist.reload()">
								   </select> 
								</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">状态</div>
								<div class="font-size-9">Status</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 80px; margin-right:5px;">
								   <select id="zt" class="form-control" onchange="installationlist.switchStatus()">
								   		<option value="current">当前</option>
								   		<option value="history">历史</option>
								   </select> 
								</div>
							</div>
							<div class="pull-left padding-left-5 padding-right-0" id="contains_ineffective_div">
								<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
									<input onclick="installationlist.reload()" style="vertical-align:text-bottom" checked="checked" type="checkbox" id="containsIneffective">&nbsp;包含未生效数据&nbsp;&nbsp;
								</label>
							</div>
							<div class="pull-left padding-left-10 padding-right-0">
								<div class=" pull-left padding-left-0 padding-right-0" style="width: 210px;">
									<input placeholder="ATA/件号/名称/序列号/安装记录单号" class="form-control" id="keyword" type="text">
								</div>
			                    <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button id="installationlistMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="installationlist.reload()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                    </div> 
							</div>
						</div>
					</div>
					
					<div class="row margin-top-10" style="margin-left: 0px; margin-right: 0px;">
						<div class="pull-left form-group">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" 
								permissioncode="aircraftinfo:installationlist:main:01" style="margin-right:3px;" onclick="installationlist.modify()">
								<div class="font-size-12">新增</div>
								<div class="font-size-9">Add</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" 
								permissioncode="aircraftinfo:installationlist:main:05" style="margin-right:3px;" onclick="installationlist.showImportModal();">
								<div class="font-size-12">导入</div>
								<div class="font-size-9">Import</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" 
								permissioncode="aircraftinfo:installationlist:main:06" style="margin-right:3px;" onclick="installationlist_table.exportExcel()">
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" 
								permissioncode="aircraftinfo:installationlist:main:04" style="margin-right:3px;" onclick="installationlist.effect()">
								<div class="font-size-12">生效</div>
								<div class="font-size-9">Effect</div>
							</button>
						</div>
						
						<div class="pull-right form-group">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="installationlist.toggleView()">
								<div class="font-size-12">切换显示</div>
								<div class="font-size-9">Toggle View</div>
							</button>
						</div>
					</div>
				
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control" onchange="work_card_main.changeDprt()" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="work_card_main.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				
				<div class="clearfix"></div>
				</div>
				<!-- 搜索框end -->
				<!--------------------  表格视图 start -------------------->
				<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_table.jsp"%>
				<!--------------------  表格视图 end -------------------->
				<div class="clearfix"></div>
				<!--------------------  树装视图 start -------------------->
				<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_tree.jsp"%>
				<!--------------------  树装视图 end -------------------->
			</div>
		</div>
	</div>

<%@ include file="/WEB-INF/views/open_win/import.jsp"%> <!-- 导入模态框 -->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%><!-------日志 -------->
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%><!-------日志差异 -------->
<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_modify_win.jsp"%>
<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_parent.jsp"%>
<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%>
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
<%@ include file="/WEB-INF/views/open_win/planePosition_search.jsp"%><!-------飞机站位 -------->
<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<%@ include file="/WEB-INF/views/open_win/history_certificate_win.jsp"%><!-----证书对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/installationlist/installationlist_main.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项单位设置 -->
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
</body>
</html>