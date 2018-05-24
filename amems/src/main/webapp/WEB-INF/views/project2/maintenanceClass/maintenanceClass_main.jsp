<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维修项目大类</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<script type="text/javascript">
	$(document).ready(function(){
		//导航
		Navigation(menuCode, '', '', 'gc_006001', '孙霁', '2017-08-03', '孙霁', '2017-08-03');
		
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					searchRevision();//调用主列表页查询
				}
			}
		});
	});
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content">
	<input type="hidden" id="userid" value="${user.id}">
	<input type="hidden" id="dprtId" value="${user.jgdm}">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
			<div class="panel-body padding-bottom-0">
			   <div class='searchContent margin-top-0 row-height' >
				<!-- 上传按钮  -->
				<div class=" col-xs-2 col-md-3 padding-left-0 form-group ">
					<a href="javascript:add();"  class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-10 pull-left checkPermission " 
					>
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
					<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" 
							permissioncode="project:maintenanceclass:main:04"
							onclick="showImportModal()">
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>
					</button>
				</div>
				
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
					<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
						<div class="font-size-12">机型</div>
						<div class="font-size-9">A/C Type</div>
					</span>
					<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
						<select class='form-control' id="fjjx" onchange="searchRevision();">
					    </select>
					</div>
				</div> 
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select id="dprtcode" class="form-control "  name="dprtcode" onchange="changeList()">
								<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
				</div>
					<!-- 搜索框start -->
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='编号/英文描述/中文描述'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-search-nomore" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
				</div>
				</div>
				<div class="clearfix"></div>
				</div>
				<!-- 搜索框end -->
				
				
			<div class="clearfix"></div>
				
			<div class='table_pagination'>
				<!-- 表格 -->
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-h" style="overflow-x: auto;">
						<table id="quality_check_list_table" class="table table-thin table-bordered table-striped table-hover table-set  table-striped">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('jx')" id="jx_order">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="colwidth-18 sorting" onclick="orderBy('dlbh')" id="dlbh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">编号</div>
											<div class="font-size-9 line-height-18">No.</div>
										</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('xc')" id="xc_order">
										<div class="font-size-12 line-height-18">项次</div>
										<div class="font-size-9 line-height-18">Item No.</div>
									</th>
									<th class="colwidth-20 sorting" onclick="orderBy('dlywms')" id="dlywms_order">
										<div class="important">
											<div class="font-size-12 line-height-18">英文描述</div>
											<div class="font-size-9 line-height-18">English Desc</div>
										</div>
									</th>
									<th class="colwidth-20 sorting" onclick="orderBy('dlzwms')" id="dlzwms_order">
										<div class="important">
											<div class="font-size-12 line-height-18">中文描述</div>
											<div class="font-size-9 line-height-18">Chinese Desc</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('whrid')" id="whrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<!-- <th class="colwidth-13 sorting" onclick="orderBy('whdwid')" id="whdwid_order">
										<div class="font-size-12 line-height-18">维护单位</div>
										<div class="font-size-9 line-height-18">Dprt</div>
									</th> -->
									<th class="colwidth-13 sorting" onclick="orderBy('whsj')" id="whsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="maintenanceClass_list"></tbody>
					</table>
				</div>	
				
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
				</div>
				<div class='clearfix'></div>
			</div>	
		</div>
	</div>
</div>	
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<%@ include file="/WEB-INF/views/project2/maintenanceClass/maintenanceClass_open.jsp" %> 
	<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/maintenanceClass/maintenanceClass_main.js"></script>
</body>
</html>
