<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>销毁</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
	var tabId = '${param.tabId}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="fazhi" value="${threshold}" />
	<input type="hidden" id="adjustHeight" value="72" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<!-- BEGIN CONTENT -->
	<script type="text/javascript">
	$(document).ready(function(){
		//导航
		Navigation(menuCode);
		
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				var selectTab = $("#tablist").find("li.active").index();
				
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					if(selectTab == 0){
						$('#requisitioSearch').trigger('click');
					}else if(selectTab == 1){
						$('#requisitionHistorySearch').trigger('click');
					}
				}
			}
		});
		
	});
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
	</script>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="clearfix"></div>
			<div class="panel-body padding-bottom-0">
				<!-----标签导航start---->
				<ul class="nav nav-tabs" role="tablist" id="tablist">
				  <li role="presentation" class="active"><a id="tab_main" onclick="loadMain();" href="#requisition" data-toggle="tab">销毁 Destruction</a></li>
				  <li role="presentation"><a id="tab_history" onclick="loadHistory();" href="#requisitionHistory" data-toggle="tab">销毁记录单 Destruction record sheet</a></li>
				</ul>
				<!-----标签内容start---->
		      	<div class="tab-content margin-bottom-10">
					<div class="tab-pane fade in active"  id="requisition">
						<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
							
							<div class="col-xs-2 col-md-3 padding-left-0 ">
								<a href="javascript:void(0);" onclick="goEdit();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"
								permissioncode="aerialmaterial:destruction:main:01">
									<div class="font-size-12">销毁</div>
									<div class="font-size-9">Destruction</div>
								</a>
							</div>
							
							<!-- 搜索框start -->
							<div class=" pull-right padding-left-0 padding-right-0">
							
							<div class="pull-left ">
									<span class="pull-left  text-right padding-left-0 padding-right-0">
									<div class="font-size-12">组织机构</div>
										<div class="font-size-9">Organization</div></span>
									<div class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
										<select id="zzjg" class="form-control " name="zzjg" onchange="searchRevision();">
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
											</c:forEach>
										</select>
									</div>
							</div>
							
							
							
								<div class=" pull-left padding-left-0 padding-right-0 row-height" style="width: 250px;">
									<input type="text" placeholder='件号/英文名称/中文名称/厂家件号' id="keyword_search" class="form-control ">
								</div>
			                    <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button id="requisitioSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                    </div> 
							</div>
							
								<div class="clearfix"></div>
					
							<div class="alert alert-success col-xs-2 col-md-3 text-center" style="padding-top: 5px;padding-bottom: 5px; margin-bottom:0px;vertical-align: middle;display:none;" role="alert" id="requisiton_message"></div>
						</div>
						<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:scroll;">
							<table id="lingliao" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 2600px !important;">
								<thead>
									<tr>
									<th class='fixed-column colwidth-7'  style="vertical-align: middle;">
									<a href="javascript:performSelectAll();" class="pull-left margin-left-10 margin-bottom-10" id='checkAll' ><img src="${ctx}/static/assets/img/d1.png" alt="全选" title="全选" /></a>
									<a href="javascript:performSelectClear();" class="pull-left margin-left-10 margin-bottom-10" id='cancelAll'><img src="${ctx}/static/assets/img/d2.png" alt="不全选" title="不全选" /></a> 
									</th>
									<th class='fixed-column colwidth-5' ><div
											class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th class='fixed-column sorting colwidth-15' 
										onclick="orderBy('bjh')" id="bjh_order"><div class="important"><div
											class="font-size-12 line-height-18">件号</div></div>
										<div class="font-size-9 line-height-18">P/N</div></th>
									<th class='sorting colwidth-30' 
										onclick="orderBy('ywms')" id="ywms_order"><div class="important"><div
											class="font-size-12 line-height-18">英文名称</div></div>
										<div class="font-size-9 line-height-18">F.Name</div></th>
									<th class='sorting colwidth-25' 
										onclick="orderBy('zwms')" id="zwms_order"><div class="important"><div
											class="font-size-12 line-height-18">中文名称</div></div>
										<div class="font-size-9 line-height-18">CH.Name</div></th>
									<th  class="sorting colwidth-8"
										onclick="orderBy('cjjh')" id="cjjh_order"><div class="important"><div
											class="font-size-12 line-height-18">厂家件号</div></div>
										<div class="font-size-9 line-height-18">MP/N</div></th>
									<th  class="sorting colwidth-6"
										onclick="orderBy('hclx')" id="hclx_order"><div
											class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Type</div></th>
									<th  class="sorting colwidth-15"
										onclick="orderBy('gljb')" id="gljb_order"><div
											class="font-size-12 line-height-18">航材管理级别</div>
										<div class="font-size-9 line-height-18">Aircraft Management Level</div></th>
									<th  class="sorting colwidth-15"
										onclick="orderBy('gljb')" id="gljb_order"><div
											class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div></th>
									<th  class="sorting colwidth-15"
										onclick="orderBy('gljb')" id="gljb_order"><div
											class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">B/N</div></th>
									<th  id="shzh_order" class="sorting colwidth-6"
										onclick="orderBy('shzh')"><div
											class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th  id="kcsl_order" class="sorting colwidth-5"
										onclick="orderBy('kcsl')"><div
											class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
									<th  class="sorting colwidth-5"
										onclick="orderBy('jldw')" id="jldw_order"><div
											class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div></th>
									<th  id="ckmc_order" class="sorting colwidth-6"
										onclick="orderBy('ckmc')"><div
											class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Store</div></th>
									<th  id="kwh_order" class="sorting colwidth-8"
										onclick="orderBy('kwh')"><div
											class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage Location</div></th>
									<th  id="hjsm_order" class="sorting colwidth-8"
										onclick="orderBy('hjsm')"><div
											class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div></th>
									<th  id="syts_order" class="sorting colwidth-10"
										onclick="orderBy('syts')"><div class="font-size-12 line-height-18">剩余货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life(day)</div></th>
									<th  id="spqx_order" class="sorting colwidth-8"
										onclick="orderBy('spqx')"><div
											class="font-size-12 line-height-18">索赔期限</div>
										<div class="font-size-9 line-height-18">Claim Period</div></th>
								<!-- 	<th  id="sytss_order" class="sorting colwidth-10"
										onclick="orderBy('sytss')"><div class="font-size-12 line-height-18">剩余索赔天数</div>
										<div class="font-size-9 line-height-18">Claim Period(day)</div></th> -->
									<th   class=" colwidth-15" ><div
											class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div></th>
											<th   class=" colwidth-13"
										><div
											class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div></th>
									<th  id="dprtcode_order"
										class="sorting colwidth-8" onclick="orderBy('dprtcode')"><div
											class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
								</thead>
								<tbody id="list">
								</tbody>
							</table>
						</div>
					</div>
			        <div class="tab-pane fade" id="requisitionHistory">
		          		<%@ include file="/WEB-INF/views/material/destruction/destruction_list.jsp"%>
			        </div>
				</div>
			</div>
		</div>
	</div>
		<script type="text/javascript"
		src="${ctx}/static/js/thjw/material/destruction/destruction_main.js"></script>
</body>
</html>