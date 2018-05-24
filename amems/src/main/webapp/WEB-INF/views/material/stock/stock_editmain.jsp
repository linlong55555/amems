<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="fazhi" value="${threshold}" />
	<input type="hidden" id="fazhi1" value="${threshold1}" />
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content ">
		<!-- BEGIN PAGE TITLE & BREADCRUMB-->

		<div class="panel panel-primary">
		
<div class="panel-heading" id="NavigationBar"></div>
			<div class="clearfix"></div>

			<div class="panel-body padding-bottom-0" >
				<!-----标签导航start---->
				<!-- <ul class="nav nav-tabs" role="tablist" id="tablist">
					<li role="presentation" class="active"><a href="#stock">在库航材列表 List Of Library Materials</a></li>
					<li role="presentation"><a href="#fieldmaterial">外场航材列表 List Of Field Equipment</a></li>
					<li role="presentation"><a href="#assage">在途航材列表 List Of Way Materials</a></li>
				</ul> -->

				<!-----标签内容start---->
				<!-- <div class="tab-content "> -->

					<div class="tab-pane fade in active" id="stock">
                        <div  class='searchContent row-height' >
								
				<!--------搜索框start-------->
				<!-- <div style="float: left;margin-left: -10px">
				<button type="button" onclick="stockOutPDF();"  style="margin-left:10px"
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
						<div class="font-size-12">打印</div>
						<div class="font-size-9">Print</div>
				</button>
						<button type="button" onclick="showImportModal();"  style="margin-left:10px"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
					<div class="font-size-12">导入</div>
					<div class="font-size-9">Import</div>
				</button>
				</div> -->
				<div class=" pull-right padding-left-0 padding-right-0" >
					<div class=" pull-left form-group">
					<div class="pull-left  text-right padding-left-0 padding-right-0">
						  <div class="font-size-12">仓库</div>
							<div class="font-size-9">Store</div>
							</div>
									
						<div class=" padding-left-8 pull-left" style="width: 200px; margin-right:10px;">
								<select class='form-control' id='ckh' name="ckh" onchange="searchRevision()">
								
								</select>
							</div>
					</div>
				
					<div class=" pull-left padding-left-0 padding-right-0 form-group" style="width:250px;" >
						<input placeholder="件号/中英文/厂家件号/ID" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="searchBtn" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
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
				<div class='clearfix'></div>
				<!------------搜索框end------->
							<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0"
								id="divSearch">

								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">

									<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
										<div
											class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12">航材类型</div>
											<div class="font-size-9">Airmaterial type</div>
										</div>
										<div
											class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
											<select id="hclx" class="form-control " name="hclx">
												<option value="">显示全部All</option>
												<c:forEach items="${materialTypeEnum}" var="item">
												  	<option value="${item.id}" >${item.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
										<div
											class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12">状态</div>
											<div class="font-size-9">State</div>
										</div>
										<div
											class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
											<select id="zt" class="form-control " name="zt">
												<option value="">显示全部All</option>
												<option value="1">收货</option>
												<option value="2">正常</option>
												<option value="3">冻结</option>
												<option value="4">待报废</option>
											</select>
										</div>
									</div>
								

									<div
										class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10  ">
										<span
											class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">组织机构</div>
											<div class="font-size-9">Organization</div>
										</span>
										<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
											<select id="dprtcode" class="form-control " name="dprtcode">
												<c:choose>
											<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}"
													<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}
												</option>
											</c:forEach>
											</c:when>
											<c:otherwise>
												<option value="">暂无组织机构 No Organization</option>
											</c:otherwise>
										</c:choose>
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
							<div class='clearfix'></div>
						</div>
						<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height"  style="overflow-x:scroll">

						<table id="zkhc" class="table table-thin table-bordered table-set" style="min-width:1000px">
							<thead>
								<tr>
									<th class='fixed-column colwidth-5'  style="vertical-align: middle;">
									    <div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>  
									</th>
									<th class='fixed-column colwidth-5' ><div
											class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th class='fixed-column sorting colwidth-15' 
										onclick="orderBy('bjh')" id="bjh_order"><div class="important"><div
											class="font-size-12 line-height-18">件号</div></div>
										<div class="font-size-9 line-height-18">P/N</div></th>
									<th class='sorting colwidth-20' 
										onclick="orderBy('ywms')" id="ywms_order"><div class="important"><div
											class="font-size-12 line-height-18">英文名称</div></div>
										<div class="font-size-9 line-height-18">F.Name</div></th>
									<th class='sorting colwidth-15' 
										onclick="orderBy('zwms')" id="zwms_order"><div class="important"><div
											class="font-size-12 line-height-18">中文名称</div></div>
										<div class="font-size-9 line-height-18">CH.Name</div></th>
									<th  class="sorting colwidth-15"
										onclick="orderBy('cjjh')" id="cjjh_order"><div class="important"><div
											class="font-size-12 line-height-18">厂家件号</div></div>
										<div class="font-size-9 line-height-18">MP/N</div></th>
									<th  class="sorting colwidth-7"
										onclick="orderBy('hclx')" id="hclx_order"><div
											class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">type</div></th>
									<th  class="sorting colwidth-7"
										onclick="orderBy('gljb')" id="gljb_order"><div
											class="font-size-12 line-height-18">管理级别</div>
										<div class="font-size-9 line-height-18">Level</div></th>
									<th  class="sorting colwidth-15"
										onclick="orderBy('xlh')" id="gljb_order"><div
											class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div></th>
									<th  class="sorting colwidth-15"
										onclick="orderBy('pch')" id="gljb_order"><div
											class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">B/N</div></th>
									<th  id="shzh_order" class="sorting colwidth-15"
										onclick="orderBy('shzh')"><div
											class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Certificate Of No.</div></th>
									<th  id="kcsl_order" class="sorting colwidth-5"
										onclick="orderBy('kcsl')"><div
											class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
									<th  class="sorting colwidth-5"
										onclick="orderBy('jldw')" id="jldw_order"><div
											class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div></th>
									<th  id="ckmc_order" class="sorting colwidth-10"
										onclick="orderBy('ckmc')"><div
											class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Store</div></th>
									<th  id="kwh_order" class="sorting colwidth-10"
										onclick="orderBy('kwh')"><div
											class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage Location</div></th>
									<th  id="hjsm_order" class="sorting colwidth-9"
										onclick="orderBy('hjsm')"><div
											class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div></th>
									<th  id="syts_order" class="sorting colwidth-10"
										onclick="orderBy('syts')"><div class="font-size-12 line-height-18">剩余货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life(day)</div></th>
									<th  id="spqx_order" class="sorting colwidth-7"
										onclick="orderBy('spqx')"><div
											class="font-size-12 line-height-18">索赔期限</div>
										<div class="font-size-9 line-height-18">Claim Period</div></th>
									<th  id="sytss_order" class="sorting colwidth-9"
										onclick="orderBy('sytss')"><div class="font-size-12 line-height-18">剩余索赔天数</div>
										<div class="font-size-9 line-height-18">Claim Period(day)</div></th>
									<th  id="zt_order" class="sorting colwidth-5" onclick="orderBy('zt')">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">State</div></th>
									<th  class="colwidth-13 sorting"  id="rkrid_order" onclick="orderBy('rkrid')">
										<div 	class="font-size-12 line-height-15">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div></th>
									<th   class=" colwidth-13 sorting"  id="rksj_order" onclick="orderBy('rksj')">
										<div class="font-size-12 line-height-15">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th  id="dprtcode_order"
										class="sorting colwidth-15" onclick="orderBy('dprtcode')"><div
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
					</div>

					<%-- <div class="tab-pane fade" id="fieldmaterial">
						<div class="row feature">
						<%@ include file="/WEB-INF/views/material/stock/fieldmaterial_main.jsp"%></div>
					</div>

					<div class="tab-pane fade" id="assage">
						<div class="row feature">
							<%@ include file="/WEB-INF/views/material/stock/assage_main.jsp"%>
						</div>
					</div> --%>

				<!-- </div> -->

			</div>
		</div>
	</div>

	</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/material/stock/stock_editmain.js"></script>
		<%@ include file="/WEB-INF/views/material/stock/stock_edit.jsp"%>
	 <%@ include file="/WEB-INF/views/open_win/import.jsp"%> 
	
	
</body>
</html>