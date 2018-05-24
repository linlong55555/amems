<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>项目信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

<script>
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
					sreload();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	
	<input type="hidden" id="adjustHeight" value="0" />
	<div class="clearfix"></div>
	<div class="page-content">
	<input type="hidden" id="userid" value="${user.id}">
	<input type="hidden" id="dprtId" value="${user.jgdm}">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
			<div class="panel-body padding-bottom-0" >
			     <!-- 搜索开始 -->
				 <div class='searchContent row-height margin-top-0' >
					 <div class="col-lg-3 col-md-6 col-sm-12 col-xs-12  padding-left-0  padding-right-0 form-group ">
						<button class="btn btn-primary padding-top-1 padding-bottom-1  checkPermission"
						permissioncode='baseinfo:project:main:add' type="button" onclick="add();">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</button>
						<button class="btn btn-primary padding-top-1 padding-bottom-1  checkPermission"
						permissioncode='baseinfo:project:main:export' type="button" onclick="exportExcel();">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</button>
					 </div>
				
				   <!-- 项目种类 -->
					 <div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">项目种类</div>
							<div class="font-size-9">Category</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select id="proiect_zl" class="form-control " onchange="changeList()">
								
							</select>
						</div>
					</div>
				
				    <div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select id="dprtcode" class="form-control "  name="dprtcode" onchange="changedrpt_List()">
									<c:forEach items="${accessDepartment}" var="type">
											<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
							</select>
						</div>
					</div> 
				
					  
					 <!-- 关键字搜索 -->
				  <div class="col-lg-3 col-md-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='项目编号/项目名称/客户编号/飞机注册号' id="keyword_search" class="form-control ">
                    <div class="input-group-addon btn-search-nomore" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 row-height" onclick="sreload();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
                   <!--  <div class="input-group-addon btn-searchnew-more">
	                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="search();">
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
                	</div> -->
				</div>
				</div>
                <div class="clearfix"></div>
			    <!-- 更多的搜索框 -->
				<%-- <div class="col-xs-12 col-lg-12 col-sm-12 triangle padding-top-10 margin-bottom-10 display-none search-height border-cccccc" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">Aircraft No.</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="aircraft_no" class="form-control ">
								<option value="">显示全部All</option>
								
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机型</div>
							<div class="font-size-9">Model</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="aircraft_model" class="form-control ">
								
							</select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">分类</div>
							<div class="font-size-9">Model</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="cust_category" class="form-control" onchange="custCategorySelect();">
								<option value="">请选择客户分类</option>
								<option value="0">内部</option>
								<option value="1">外部</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="cust_select_1" style="display: none">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">客户编号</div>
							<div class="font-size-9 ">Customer No.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
						<div class='col-xs-12 input-group'>
							<input type="text" id="cust_no_1" name="cust_no" class="form-control " readonly />
							<input type="hidden" class="form-control" id="cust_id_1" name="cust_id_1" />
							<span class="input-group-btn">
								<button type="button" class="btn btn-primary form-control" 
								     data-toggle="modal" onclick="openCustomerWin(0)">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="dept_select_1"  style="display: none">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">部门编号</div>
							<div class="font-size-9 ">Dept No.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
						<div class='col-xs-12 input-group'>
							<input type="text" id="dept_no_1" name="dept_no_1" class="form-control " readonly />
							<input type="hidden" class="form-control" id="dept_id_1" name="dept_id_1" />
							<span class="input-group-btn">
								<button type="button"  class="btn btn-primary form-control" 
								     data-toggle="modal" onclick="openDeptWin(0)">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
						</div>
					</div>
					
					
					<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
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
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
			    <div class='clearfix'></div> --%>
				</div>
				<!-- 搜索框end -->
			<div class="clearfix"></div>
			<div class='table_pagination' id="searchTable">
				<!-- 表格 -->
					<div id="project_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set" id="project_list_table">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('xmbm')" id="xmbm_order">
									    <div class="important">
											<div class="font-size-12 line-height-18">项目编号</div>
											<div class="font-size-9 line-height-18">Project No.</div>
										</div>
									</th>									
									<th class="colwidth-10 sorting" onclick="orderBy('xmmc')" id="xmmc_order">
										<div class="important">
											<div class="font-size-12 line-height-18">项目名称</div>
											<div class="font-size-9 line-height-18">Name</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('khid')" id="khid_order">
									  <div class="important">
										<div class="font-size-12 line-height-18">客户编号</div>
										<div class="font-size-9 line-height-18">Customer No.</div>
									  </div>	
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('fjzch')" id="fjzch_order">
									    <div class="important">
											<div class="font-size-12 line-height-18">飞机注册号</div>
											<div class="font-size-9 line-height-18">A/C REG</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('fjjsh')" id="fjjsh_order">
										<div class="font-size-12 line-height-18">MSN</div>
										<div class="font-size-9 line-height-18">MSN</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('fjbzm')" id="fjbzm_order">
										<div class="font-size-12 line-height-18">飞机描述</div>
										<div class="font-size-9 line-height-18">Desc</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('xmzl')" id="xmzl_order">
										<div class="font-size-12 line-height-18">项目种类</div>
										<div class="font-size-9 line-height-18">Category</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('jh_ksrq')" id="jh_ksrq_order">
										<div class="font-size-12 line-height-18">计划进场时间</div>
										<div class="font-size-9 line-height-18">E.T.A.</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('jh_jsrq')" id="jh_jsrq_order">
										<div class="font-size-12 line-height-18">计划出场时间</div>
										<div class="font-size-9 line-height-18">E.T.D.</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('sj_ksrq')" id="sj_ksrq_order">
										<div class="font-size-12 line-height-18">实际进场时间</div>
										<div class="font-size-9 line-height-18">Actual In</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('sj_jsrq')" id="sj_jsrq_order">
										<div class="font-size-12 line-height-18">实际出场时间</div>
										<div class="font-size-9 line-height-18">Actual Out</div>
									</th>
									
									<th class="colwidth-13 sorting" onclick="orderBy('xsddh')" id="xsddh_order">
										<div class="font-size-12 line-height-18">销售订单号</div>
										<div class="font-size-9 line-height-18">Sale Order</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('fstk')" id="fstk_order">
										<div class="font-size-12 line-height-18">主要条款</div>
										<div class="font-size-9 line-height-18">Main Terms</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">附件</div>
										<div class="font-size-9 line-height-18">Attachment</div>
									</th>
									<!-- <th class="colwidth-5 sorting" onclick="orderBy('zt')" id="zt_order">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th> -->
									<th class="colwidth-13 sorting" onclick="orderBy('whrid')" id="whrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintenance</div>
									</th>
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
							<tbody id="project_list">
							     <tr><td colspan="19" align="center">数据加载中……</td></tr>
							</tbody>
					</table>
				</div>	
				
				<!-- <div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
				</div> -->
				<div class='clearfix'></div>
			</div>	
			<div class=" col-xs-12  text-center page-height padding-right-0 padding-left-0" style="margin-top: 0px; margin-bottom: 0px;" id="pagination"></div>
		</div>
	</div>
</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
    <%@ include file="/WEB-INF/views/baseinfo/project/project_open.jsp" %>  <!-- 项目信息弹窗 -->
    <%@ include file="/WEB-INF/views/baseinfo/project/customer_choose.jsp" %><!-- 选择客户 -->
	<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
    <%-- <%@ include file="/WEB-INF/views/baseinfo/project/dept_choose.jsp" %> --%>
    
    <%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->

	<script type="text/javascript" src="${ctx}/static/js/thjw/baseinfo/project/project_main.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>
