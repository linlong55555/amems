<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生产监控预警设置</title>
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var fjzch = "${param.fjzch}";
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
					searchFjgzRecord();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<input type="hidden" id="fjzch" >
	<div class="page-content table-table-type">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			  <div  class='searchContent' >
				<div class='margin-top-0'>
					<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">机型</div>
							<div class="font-size-9 ">A/C Type</div>
						</span>
						<div id="jxdiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="jx"  class="form-control " onchange="searchFjgzRecord()">
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div id="jxdiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
						    <select id="dprtcode" class="form-control " onchange="onchangeFjgzRecord()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				   <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>	
						<div class=" col-xs-12 input-group input-group-search row-height">
					     	<input type="text" placeholder='飞机注册号/MSN'  id="keyword_search" class="form-control ">
                            <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchFjgzRecord();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                	</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class='table_pagination'>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" c-height="50%" id="tableId" style="overflow-x: auto;">
					<table  id="scjkyj_table" class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="colwidth-10  sorting" onclick="orderBy('fjzch')" id="fjzch_order">
									<div class="important">
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('fjjx')" id="fjjx_order">
									
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('xlh')" id="xlh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">MSN</div>
										<div class="font-size-9 line-height-18">msn</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('bzm')" id="bzm_order">
									<div class="font-size-12 line-height-18">备注名</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
								<th class="colwidth-5 sorting" onclick="orderBy('jdms')" id="jdms_order">
									<div class="font-size-12 line-height-18">基地</div>
									<div class="font-size-9 line-height-18">Base</div>
								</th>
								<th class="colwidth-9 ">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="scjkyj_list">
						</tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="scjkyj_pagination">
				</div>
				<div class='clearfix'></div>
			</div>	
			<%@ include file="/WEB-INF/views/produce/aircraftmonitorsetting/aircraftmonitor_info.jsp"%>  <!--监控信息  -->
		</div>
	</div>
</div>	
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/aircraftmonitorsetting/aircraftmonitorsetting_main.js"></script><!--当前js  -->
</body>
</html>