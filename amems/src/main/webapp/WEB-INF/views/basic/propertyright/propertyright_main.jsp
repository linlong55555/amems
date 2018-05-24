<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>区域管理</title>
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
				$('#propertyrightButtom').trigger('click');;//调用主列表页查询
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
					 <div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group checkPermission">
						<a href="javascript:add();" permissioncode='basic:station:main:add'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a> 
					</div>
					 
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">飞机注册号</div>
							<div class="font-size-9 ">A/C REG</div>
						</span>
						<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="fjzch_search" class="form-control"></select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">公司</div>
							<div class="font-size-9 ">Company</div>
						</span>
						<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<input type="text" placeholder="编号/名称" class="form-control" id="gs_search">
						</div>
					</div>
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
						<input type="text" placeholder='产权编号'  class="form-control" id="keyword_search" >
	                    <div class="input-group-addon btn-searchnew" >
	                    	<button type="button"  id="propertyrightButtom" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="propertyrightMain.reload();" style='margin-right:0px !important;'>
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
	                  		</button>
	                    </div>
	                    <div class="input-group-addon btn-searchnew-more">
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
	                	</div>
					</div>
				</div>					
				
				<!-- 搜索框end -->
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
						<div class="col-lg-3 col-sm-6 col-xs-12	  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div></span>
							<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="dprtChange()" >
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					
					<div class="col-lg-3 col-sm-3 col-xs-12  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
					 <div class="clearfix"></div>
				</div>
			<div class="clearfix"></div>
			<div class='table_pagination' id="searchTable">
				<!-- 表格 -->
					<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height" style="overflow-x: auto;">
						<table id="quality_check_list_table" class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="fixed-column colwidth-10 sorting" onclick="orderBy('cqbh')" id="cqbh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">产权编码</div>
											<div class="font-size-9 line-height-18">No</div>
										</div>
									</th>									
									<th class="colwidth-10 sorting" onclick="orderBy('fjzch')" id="fjzch_order">
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</th>
									
									<th class="colwidth-15 sorting" onclick="orderBy('gsid')" id="gsid_order">
										<div class="font-size-12 line-height-18">公司</div>
										<div class="font-size-9 line-height-18">Company</div>
									</th>
									<th class="colwidth-15 sorting" onclick="orderBy('bz')" id="bz_order">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Desc</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('whrid')" id="whrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('whbmid')" id="whbmid_order">
										<div class="font-size-12 line-height-18">维护部门</div>
										<div class="font-size-9 line-height-18">Maintainer Dept</div>
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
							<tbody id="list"></tbody>
					</table>
				</div>	
				<div class='clearfix'></div>
			</div>	
			<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0"  id="pagination"></div>
		</div>
	</div>
</div>
	<%@ include file="/WEB-INF/views/basic/propertyright/propertyright_open.jsp" %> 
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
    <%@ include file="/WEB-INF/views/common/produce/customer_modal.jsp"%><!-- -客户 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/basic/propertyright/propertyright_main.js"></script>
</body>
</html>
