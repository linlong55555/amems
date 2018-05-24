<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机型到期监控</title>
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
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
					planemonitor.search();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>

<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content table-table-type">
		<input type="hidden" id="userid" value="${user.id}">
		<input type="hidden" id="id"  />
		<input type="hidden" id="technicalAttachedid"  />
		<input type="hidden" id="djzt"  />
		<input type="hidden" id="state"  />
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
			<div class="panel-body padding-bottom-0">
			  <div  class='searchContent row-height ' >
				<div class='margin-top-0'>
					<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
						<a href="#"  onclick="exportExcel()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission" permissioncode='quality:planemonitor:main:export,quality:planemonitor:list:export' >
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">有效期</div>
							<div class="font-size-9 ">Validity</div>
						</span>
						<div
							class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control date-picker"
								id="dqrq"  data-date-format="yyyy-mm-dd"/>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " onchange="planemonitor.search();">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
		
					<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>	
						<!-- 搜索框start -->
						<div class=" col-xs-12 input-group input-group-search">
						    <input type="text" placeholder='人员编号/姓名/颁发单位'  id="planemonitor_keyword_search" class="form-control ">
                            <div class="input-group-addon btn-searchnew " style="padding-right: 0px !important;">
			                    <button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="planemonitor.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                	</div>
						</div>
					</div>
					
					<div class="clearfix"></div>
				</div>
				<!-- 搜索框end -->
				
				</div>
			<div class="clearfix"></div>
				
			<div class='table_pagination'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0  table-height"  id="tableId" style="overflow-x: auto;">
						<table  id="planemonitor_Table" class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
									<th  class="fixed-column colwidth-5">
										<div class="font-size-12 line-height-18 ">标识</div>
										<div class="font-size-9 line-height-18">Mark</div>
									</th>
									<th class="sorting  colwidth-8" onclick="planemonitor.orderBy('b-rybh')" id="b-rybh_order" sortFiled="b.rybh">
										<div class="important">
											<div class="font-size-12 line-height-18">人员编号</div>
											<div class="font-size-9 line-height-18">No.</div>
										</div>
									</th>
									<th  class="sorting  colwidth-10" onclick="planemonitor.orderBy('b-xm')" id="b-xm_order">
										<div class="important">
											<div class="important"><div class="font-size-12 line-height-18">姓名</div>
											<div class="font-size-9 line-height-18">Name</div>
										</div>
									</th>
									<th class="   colwidth-10"    >
										<div class="important">
											<div class="font-size-12 line-height-18">颁发单位</div>
											<div class="font-size-9 line-height-18">Award Dept.</div>
										</div>
									</th>
									<th class=" colwidth-10"   >
											<div class="font-size-12 line-height-18">机型</div>
											<div class="font-size-9 line-height-18">Type</div>
									</th>
									<th  class="   colwidth-7"   >
										<div class="font-size-12 line-height-18">专业</div>
										<div class="font-size-9 line-height-18">Major</div>
									</th>	
									<th  class="   colwidth-10"   >
										<div class="font-size-12 line-height-18">执照号</div>
										<div class="font-size-9 line-height-18">Licence</div>
									</th>	
									<th class="  cursor-pointer colwidth-7"   >
										<div class="font-size-12 line-height-18">颁发日期</div>
										<div class="font-size-9 line-height-18">Issue Date</div>
									</th>	
									<th  class="   colwidth-7"   >
										<div class="font-size-12 line-height-18">有效期</div>
										<div class="font-size-9 line-height-18">Validity</div>
									</th>
									<th  class="   colwidth-7"   >
										<div class="font-size-12 line-height-18">剩余(天)</div>
										<div class="font-size-9 line-height-18">Remain</div>
									</th>
									<th class="sorting  colwidth-10" onclick="planemonitor.orderBy('b-wbbs')" id="b-wbbs_order">
										<div class="font-size-12 line-height-18">内/外</div>
										<div class="font-size-9 line-height-18">Outside</div>
									</th>
							 		<th class="   colwidth-10"   >
							 			<div class="font-size-12 line-height-18">岗位</div>
										<div class="font-size-9 line-height-18">Post</div>
									</th> 
							 		 
									<th  class="colwidth-9" >
											<div class="font-size-12 line-height-18">附件</div>
											<div class="font-size-9 line-height-18">Attachment</div>
									</th>
									<th  class="colwidth-10">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="planemonitor_list">
							</tbody>
					</table>
				</div>	
				
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="planemonitor_pagination">
				</div>
				<div class='clearfix'></div>
				
			</div>	
				<%@ include file="/WEB-INF/views/project2/assessment/assessment_give_order.jsp" %> <!--下达指令  -->
		</div>
	</div>
</div>	

<script type="text/javascript" src="${ctx}/static/js/thjw/quality/planemonitor/planemonitor_main.js"></script><!--当前界面js  -->
 
 <%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
  <script type="text/javascript" src="${ctx}/static/js/thjw/common/preview.js"></script><!-- 控件对话框 -->
 <script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>
