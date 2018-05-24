<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<title>机型设置</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

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
	<div class="clearfix"></div>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			 <div  class='searchContent' >
			   <!-- 按钮START -->
				<div class=" col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group row-height">
					<a href="javascript:AddalertModal.inItAdd();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission" permissioncode='project2:actype:main:01'>
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
				</div>
				<!-- 按钮end -->
				<div class=" col-lg-3 col-md-6 col-sm-6 col-xs-12   padding-left-0 padding-right-0  form-group">
					<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
						<div class="font-size-9">Organization</div>
					</span>
					<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
						<select id="dprtcode" class="form-control "  name="dprtcode" onchange="search()">
							<c:forEach items="${accessDepartment}" var="type">
								<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>
									${erayFns:escapeStr(type.dprtname)}
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<!-- 搜索框START -->
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>	
					<!-- 搜索框start -->
					<div class=" col-xs-12 input-group input-group-search row-height">
						<input type="text" placeholder='机型/描述'  id="keyword_search" class="form-control ">
	                    <div class="input-group-addon btn-searchnew" >
	                    </div> 
                        <div class="input-group-addon btn-searchnew-more">
		                   <button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="search();" style='margin-right:0px !important;'>
							   <div class="font-size-12">搜索</div>
							   <div class="font-size-9">Search</div>
		                   </button>
		                </div>
					</div>
				</div>
				<!-- 搜索框END -->
				<div class="clearfix"></div>
				<div class='table_pagination'>
					<!-- 表格 STATR -->
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-h" style="overflow-x: auto;">
						<table id="fjjxid" class="table table-thin table-bordered table-set table-hover table-striped" style="min-width:900px">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-10 sorting" name="column_fjjx" onclick="orderBy('fjjx', this)">
										<div class="important">
											<div class="font-size-12 line-height-18">机型</div>
											<div class="font-size-9 line-height-18">A/C Type</div>
										</div>
									</th>
									<th class="colwidth-25 sorting" name="column_bz" onclick="orderBy('bz', this)">
										<div class="important">
											<div class="font-size-12 line-height-18">描述</div>
											<div class="font-size-9 line-height-18">Desc</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" name="column_WHR_ID" onclick="orderBy('WHR_ID', this)">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="colwidth-10 sorting" name="column_CJSJ" onclick="orderBy('CJSJ', this)">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="colwidth-13" >
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
						<tbody id="actypeList"></tbody>
					</table>
				</div>	
				<!-- 表格 END -->
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class='clearfix'></div>
			</div>	
		</div>
		</div>
	</div>
</div>	
<!--日志STATR -->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<!--日志END  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/actype/actype_main.js"></script><!--当前页面js  -->
<%@ include file="/WEB-INF/views/project2/actype/actype_open.jsp" %> <!--机型新增修改页面弹出窗  -->
</body>
</html>
