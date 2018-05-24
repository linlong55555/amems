<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>航材移库维护</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
    <input type="hidden" id="adjustHeight" value="60">

	<input type="hidden" id="fazhi" value="${threshold}" />
	<input type="hidden" id="fazhi1" value="${threshold1}" />
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<div class="page-content ">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>

			<div class="panel-body padding-bottom-0">
				<!-----标签导航start---->
				<ul class="nav nav-tabs" role="tablist" id="tablist">
					<li role="presentation" class="active"><a href="#transfer" onclick="viewCklist()">航材移库维护列表</a></li>
					<li role="presentation"><a href="#transferHistory">移库历史记录</a></li>
				</ul>

				<!-----标签内容start---->
				<div class="tab-content  padding-bottom-0">
					<div class="tab-pane fade in active" id="transfer">
						<div class="col-xs-12 padding-left-0 padding-right-0 margin-bottom-0">
							 <div class="col-xs-6 padding-left-0 row-height">
								<a href="javascript:void(0)" data-toggle="modal" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='aerialmaterial:transfer:main:01' onclick="addTransfer()">
									<div class="font-size-12">移库</div>
									<div class="font-size-9">Transfer</div>
								</a> 
							</div>
							<div class=" pull-right padding-left-0 padding-right-0">
								<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
									<input type="text" class="form-control " id="keyword_search" placeholder="部件号/中英文/厂家件号" />
								</div>
								<div class=" pull-right padding-left-5 padding-right-0 "> 
									<button id="hctransfer" type="button"class="btn btn-primary padding-top-1 padding-bottom-1"
										onclick="searchRevision();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
									</button>
									<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
										<div class="pull-left text-center">
											<div class="font-size-12">更多</div>
											<div class="font-size-9">More</div>
										</div>
										<div class="pull-left padding-top-5">
										  &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
										</div>
									</button>
								</div>
							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc search-height" id="divSearch">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
										<div
											class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12">航材类型</div>
											<div class="font-size-9">Airmaterial type</div>
										</div>
										<div
											class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select id="hclx" class="form-control " name="hclx">
												<option value="">显示全部All</option>
												<c:forEach items="${materialTypeEnum}" var="item">
												  	<option value="${item.id}" >${item.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="col-lg-3 col-sm-6 col-xs-12 margin-bottom-10 padding-left-0 padding-right-0">
										<span
											class="pull-left col-xs-4 col-sm-4  col-lg-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">仓库</div>
											<div class="font-size-9">State</div>
										</span>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select class='form-control' id='ckh' name="ckh">
											</select>
										</div>
									</div>
									<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 margin-bottom-10  ">
										<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">组织机构</div>
											<div class="font-size-9">Organization</div>
										</span>
										<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
											<select id="dprtcode" class="form-control " name="dprtcode" onchange="changeOrg()">
												<c:forEach items="${accessDepartment}" var="type">
													<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-lg-2 pull-right text-right padding-right-0"
										style="margin-bottom: 10px;">
										<button type="button"
											class="btn btn-primary padding-top-1 padding-bottom-1"
											onclick="searchreset();">
											<div class="font-size-12">重置</div>
											<div class="font-size-9">Reset</div>
										</button>
									</div>
								</div>
							</div>
						</div>
	                   <div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:scroll">
						<table id="tansfer_list_table" class="table-set table table-thin table-bordered table-striped table-hover" style=" min-width: 1700px !important;">
							<thead>
								<tr>
								    <th class="fixed-column colwidth-5"><div
											class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th class="colwidth-5"><div
											class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th class="sorting colwidth-10" onclick="orderBy('bjh')" id="bjh_order">
										 <div class="important">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
										</div>
									</th>
									<th class="sorting colwidth-20" onclick="orderBy('ywms')" id="ywms_order">
									    <div class="important">
									    <div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
										</div>
										</th>
									<th class="sorting colwidth-10" onclick="orderBy('zwms')" id="zwms_order">
									    <div class="important">
									    <div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
										</div>
										</th>
									<th class="sorting colwidth-10" onclick="orderBy('cjjh')" id="cjjh_order">
									   <div class="important">
									    <div class="font-size-12 line-height-18">厂家件号</div>
										<div class="font-size-9 line-height-18">MP/N</div>
										</div>
										</th>
									<th class="sorting colwidth-7"
										onclick="orderBy('hclx')" id="hclx_order"><div
											class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Aircraft Type</div></th>
									<th class="sorting colwidth-9"
										onclick="orderBy('gljb')" id="gljb_order"><div
											class="font-size-12 line-height-18">管理级别</div>
										<div class="font-size-9 line-height-18">Level</div></th>
									<th class="colwidth-10"><div
											class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div></th>
									<th class="colwidth-10"><div
											class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">B/N</div></th>
									<th class="sorting colwidth-7" id="shzh_order" 
										onclick="orderBy('shzh')"><div
											class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Certificate</div></th>
									<th class="sorting colwidth-5" id="kcsl_order" 
										onclick="orderBy('kcsl')"><div
											class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
									<th class="sorting colwidth-5"
										onclick="orderBy('jldw')" id="jldw_order"><div
											class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div></th>
										
									<th class="sorting colwidth-7" id="ckmc_order" 
										onclick="orderBy('ckmc')"><div
											class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Store</div></th>
									<th class="sorting colwidth-10" id="kwh_order" 
										onclick="orderBy('kwh')"><div
											class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage Location</div></th>
									<th class="sorting colwidth-10" id="hjsm_order"
										onclick="orderBy('hjsm')"><div
											class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div></th>
									<th class="sorting colwidth-7" id="syts_order" 
										onclick="orderBy('syts')"><div
											class="font-size-12 line-height-18">剩余天数</div>
										<div class="font-size-9 line-height-18">Surplus(day)</div></th>
								</tr>
								</thead>
								<tbody id="list">
								</tbody>
							</table>
						</div>
				    <div class="col-xs-12 page-height text-center" id="pagination">
				   </div>
			</div>
			<div class="tab-pane fade" id="transferHistory">
				<div class="row feature">
					<%@ include file="/WEB-INF/views/material/transfer/transferhistorylist.jsp"%>
				</div>
			</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="${ctx}/static/js/thjw/material/transfer/transferlist.js">
</script>
</body>
</html>