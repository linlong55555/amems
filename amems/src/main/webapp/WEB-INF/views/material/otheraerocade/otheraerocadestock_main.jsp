<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">


		<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode);
});
</script>
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
					<!-- 搜索框start -->
				<div  class='searchContent row-height' >
				<div class=" pull-right padding-left-0 padding-right-0 form-group" >
					<div class=" pull-left padding-left-0 padding-right-0 " style="width: 250px;">
						<input type="text" placeholder='件号/GRN/中文名称/英文名称/厂家件号' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                        <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				
				<!-- 搜索框end -->
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">航材类型</div>
							<div class="font-size-9">Airmaterial type</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="hclx" class="form-control " name="hclx">
								<option value="">显示全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<!-- <div class="col-xs-12 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">仓库</div>
							<div class="font-size-9">Store</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='ckh' name="ckh">
							</select>
						</div>
					</div> -->
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">其他飞行队</div>
							<div class="font-size-9">Otheraerocade</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="fxd" class="form-control " name="fxd">
							<option value="">显示全部 All</option>
							<c:forEach items="${newRecordList}" var="item" varStatus="status">
										<option value="${item.jddxbh}">${erayFns:escapeStr(item.jddxms)}</option>
							</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				
					<div class="clearfix"></div>
                </div>
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: scroll;width: 100%;">
						<table  id="quality_check_list_table" class="table table-thin table-bordered table-set" style="min-width: 2600px !important">
							<thead>
								<tr>

									<th  class="fixed-column colwidth-3"><div
											class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th  class="fixed-column colwidth-10 sorting"
										onclick="orderBy('bjh')" id="bjh_order"><div class="important"><div
											class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div></div></th>
									<th  class=" colwidth-10 sorting"
										onclick="orderBy('grn')" id="grn_order"><div class="important"><div
											class="font-size-12 line-height-18">GRN</div>
										<div class="font-size-9 line-height-18">GRN</div></div></th>
									<th  class="colwidth-20 sorting"
										onclick="orderBy('ywms')" id="ywms_order"><div class="important"><div
											class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div></div></th>
									<th class="colwidth-20 sorting"
										onclick="orderBy('zwms')" id="zwms_order"><div class="important"><div
											class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div></div></th>
									<th class=" colwidth-15 sorting"
										onclick="orderBy('cjjh')" id="cjjh_order"><div class="important"><div
											class="font-size-12 line-height-18">厂家件号</div>
										<div class="font-size-9 line-height-18">MP/N</div></div></th>
									<th  class="colwidth-5 sorting"
										onclick="orderBy('hclx')" id="hclx_order"><div
											class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Airmaterial type</div></th>
									<th class="colwidth-10 sorting"
										onclick="orderBy('gljb')" id="gljb_order"><div
											class="font-size-12 line-height-18">航材管理级别</div>
										<div class="font-size-9 line-height-18">Aircraft Management Level</div></th>
									<th class="colwidth-13 sorting"
										onclick="orderBy('sn')" id="sn_order"><div
											class="font-size-12 line-height-18">序列号/批次号</div>
										<div class="font-size-9 line-height-18">S/N</div></th>
									<th  id="shzh_order" class="colwidth-13 sorting"
										onclick="orderBy('shzh')"><div
											class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Certificate Of No.</div></th>
									<th  id="kcsl_order" class="colwidth-3 sorting"
										onclick="orderBy('kcsl')"><div
											class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
									<th  class="colwidth-3 sorting"
										onclick="orderBy('jldw')" id="jldw_order"><div
											class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div></th>
									<th  id="ckmc_order" class="colwidth-5 sorting"
										onclick="orderBy('ckmc')"><div
											class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Store</div></th>
									<th id="kwh_order" class="colwidth-10 sorting"
										onclick="orderBy('kwh')"><div
											class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage Location</div></th>
									<th  id="hjsm_order" class="colwidth-5 sorting"
										onclick="orderBy('hjsm')"><div
											class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div></th>
									<th  id="syts_order" class="colwidth-5 sorting"
										onclick="orderBy('syts')"><div class="font-size-12 line-height-18">剩余天数</div>
										<div class="font-size-9 line-height-18">Surplus(day)</div></th>
									<th  id="zt_order" class="colwidth-5 sorting"
										onclick="orderBy('zt')"><div
											class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">State</div></th>
									<th id="dprtcode_order"
										class="colwidth-13 sorting" onclick="orderBy('dprtcode')"><div
											class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
							
						</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
					</div>
					
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	
	</div>
	
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/otheraerocade/otheraerocadestock_main.js"></script>
</body>
</html>