<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>客户信息</title>
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
					search();//调用主列表页查询
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
				 <div class='searchContent row-height' >
					 <div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<button class="btn btn-primary padding-top-1 padding-bottom-1 row-height checkPermission"
						permissioncode='customerproject:customer:main:add'
						 type="button" onclick="add();">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</button>
						<button class="btn btn-primary padding-top-1 padding-bottom-1 row-height checkPermission"
						permissioncode='customerproject:customer:main:export'
						 type="button" onclick="exportExcel();">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</button>
					 </div>
				      <div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
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
				
					  
					 <!-- 关键字搜索 -->
				  <div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='客户编号/名称' id="keyword_search" class="form-control ">
                    <div class="input-group-addon btn-search-nomore" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="search();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
                   </div>
                   </div>
					<%--  <div class=" pull-right padding-left-0 padding-right-0" >
			
						 <div class="pull-left">
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</div>
							<div class="pull-left text-right padding-left-0 padding-right-0">
								<div class="padding-left-8 pull-left" style="width: 230px; margin-right:5px;">
								   <select id="dprtcode" class="form-control "  name="dprtcode" onchange="changeList()">
									<c:forEach items="${accessDepartment}" var="type">
											<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
									</select>
								</div>
							</div>
	                     </div>
					
						 <div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='客户编号/名称' id="keyword_search"
								class="form-control ">
						 </div>
	                     <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button type="button"
								class=" btn btn-primary padding-top-1 padding-bottom-1"
								onclick="search();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
							</button>
	                     </div> 
					 </div> --%>

					 <div class="clearfix"></div>
				</div>
				<!-- 搜索框end -->
				
				
			<div class="clearfix"></div>
				
			<div class='table_pagination' id="searchTable">
				<!-- 表格 -->
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-h" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set" id="customer_list_table">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('khbm')" id="khbm_order">
									    <div class="important">
											<div class="font-size-12 line-height-18">客户编号</div>
											<div class="font-size-9 line-height-18">Customer No.</div>
										</div>
									</th>									
									<th class="colwidth-10 sorting" onclick="orderBy('khmc')" id="khmc_order">
										<div class="important">
											<div class="font-size-12 line-height-18">客户名称</div>
											<div class="font-size-9 line-height-18">Name</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('lxdh')" id="lxdh_order">
											<div class="font-size-12 line-height-18">联系电话</div>
											<div class="font-size-9 line-height-18">Tel</div>
									</th>
									
									<th class="colwidth-5 sorting" onclick="orderBy('gj')" id="gj_order">
											<div class="font-size-12 line-height-18">国家</div>
											<div class="font-size-9 line-height-18">Country</div>
									</th>
									
									<th class="colwidth-5 sorting" onclick="orderBy('cs')" id="cs_order">
										<div class="font-size-12 line-height-18">城市</div>
										<div class="font-size-9 line-height-18">City</div>
									</th>
									<th class="colwidth-8 sorting" onclick="orderBy('yb')" id="yb_order">
										<div class="font-size-12 line-height-18">邮编</div>
										<div class="font-size-9 line-height-18">Post Code</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('bz')" id="bz_order">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('khfl')" id="khfl_order">
										<div class="font-size-12 line-height-18">客户分类</div>
										<div class="font-size-9 line-height-18">Category</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('khjc')" id="khjc_order">
										<div class="font-size-12 line-height-18">客户简称</div>
										<div class="font-size-9 line-height-18">For Short</div>
									</th>
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
							<tbody id="customer_list">
							     <tr><td colspan="12" align="center">数据加载中……</td></tr>
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
	<%@ include file="/WEB-INF/views/customerproject/customer/customer_open.jsp" %> 

	<script type="text/javascript" src="${ctx}/static/js/thjw/customerproject/customer/customer_main.js"></script>
</body>
</html>
