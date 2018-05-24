<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var tdbjh = "${bjh}";
var tddprtcode = "${dprtcode}";
</script>
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

			<div class="panel-body padding-bottom-0 " style="padding-top: 10px;">
				<!-----标签导航start---->
				<ul class="nav nav-tabs" role="tablist" id="tablist">
					<li role="presentation" class="active"><a href="#stock">在库航材列表 List Of Library Materials</a></li>
					<li role="presentation"><a href="#fieldmaterial">外场航材列表 List Of Field Equipment</a></li>
					<li role="presentation"><a href="#assage">在途航材列表 List Of Way Materials</a></li>
				</ul>

				<!-----标签内容start---->
				<div class="tab-content ">

					<div class="tab-pane fade in active" id="stock">

						<div class="col-xs-12 padding-left-0 padding-right-0 margin-bottom-0">
								
				<!--------搜索框start-------->
				<div style="float: left;margin-left: -10px">
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
				</div>
				<div class=" pull-right padding-left-0 padding-right-0" >
					<div class=" pull-left">
					<div class="pull-left  text-right padding-left-0 padding-right-0">
						  <div class="font-size-12">仓库</div>
							<div class="font-size-9">Store</div>
							</div>
									
						<div class=" padding-left-8 pull-left" style="width: 200px; margin-right:10px;">
								<select class='form-control' id='ckh' name="ckh" onchange="searchRevision()">
								
								</select>
							</div>
					</div>
				
					<div class=" pull-left padding-left-0 padding-right-0 row-height " style="width:250px;" >
						<input placeholder="件号/中英文/厂家件号/ID" id="keyword_search" class="form-control " type="text">
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
				</div>
				<!------------搜索框end------->
							<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc search-height"
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
						
							<input type="hidden" id="adjustHeight" value="60">
	<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:scroll">

						<table id="zkhc" class="table table-thin table-bordered table-set" style="min-width:3000px">
							<thead>
								<tr>
									<th class='fixed-column colwidth-7'  style="vertical-align: middle;">
									<a href="javascript:checkAll();" class="pull-left margin-left-10 margin-bottom-10" id='checkAll' ><img src="${ctx}/static/assets/img/d1.png" alt="全选" title="全选" /></a>
									<a href="javascript:notCheckAll();" class="pull-left margin-left-10 margin-bottom-10" id='cancelAll'><img src="${ctx}/static/assets/img/d2.png" alt="不全选" title="不全选" /></a> 
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
									<th  class="sorting colwidth-15"
										onclick="orderBy('cjjh')" id="cjjh_order"><div class="important"><div
											class="font-size-12 line-height-18">厂家件号</div></div>
										<div class="font-size-9 line-height-18">MP/N</div></th>
									<th  class="sorting colwidth-9"
										onclick="orderBy('hclx')" id="hclx_order"><div
											class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Airmaterial type</div></th>
									<th  class="sorting colwidth-10"
										onclick="orderBy('gljb')" id="gljb_order"><div
											class="font-size-12 line-height-18">航材管理级别</div>
										<div class="font-size-9 line-height-18">Level</div></th>
									<th  class="sorting colwidth-15"
										onclick="orderBy('sn')" id="sn_order"><div
											class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div></th>
									<th  class="sorting colwidth-15"
										onclick="orderBy('pch')" id="pch_order"><div
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
									<th class="colwidth-5">
										<div class="font-size-12 line-height-18">替代数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
									<th  class="sorting colwidth-5"
										onclick="orderBy('jldw')" id="jldw_order"><div
											class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div></th>
									<th  id="ckmc_order" class="sorting colwidth-9"
										onclick="orderBy('ckmc')"><div
											class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Store</div></th>
									<th  id="kwh_order" class="sorting colwidth-10"
										onclick="orderBy('kwh')"><div
											class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage Location</div></th>
									<th  id="hcly_order" class="sorting colwidth-10"
										onclick="orderBy('hcly')"><div
											class="font-size-12 line-height-18">来源</div>
										<div class="font-size-9 line-height-18">Source</div></th>
									<th  id="hjsm_order" class="sorting colwidth-10"
										onclick="orderBy('hjsm')"><div
											class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div></th>
									<th  id="syts_order" class="sorting colwidth-10"
										onclick="orderBy('syts')"><div class="font-size-12 line-height-18">剩余货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life(day)</div></th>
									<th  id="spqx_order" class="sorting colwidth-9"
										onclick="orderBy('spqx')"><div
											class="font-size-12 line-height-18">索赔期限</div>
										<div class="font-size-9 line-height-18">Claim Period</div></th>
									<th  id="sytss_order" class="sorting colwidth-10"
										onclick="orderBy('sytss')"><div class="font-size-12 line-height-18">剩余索赔天数</div>
										<div class="font-size-9 line-height-18">Claim Period(day)</div></th>
									<th   id="rkrid_order" onclick="orderBy('rkrid')" class="sorting colwidth-15" ><div
											class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div></th>
									<th  id="rksj_order" onclick="orderBy('rksj')" class="sorting colwidth-13" >
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div></th>
									<th  id="zt_order" class="sorting colwidth-5"
										onclick="orderBy('zt')"><div
											class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">State</div></th>
									<th  id="dprtcode_order"
										class="sorting colwidth-20" onclick="orderBy('dprtcode')"><div
											class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
						</table>
						</div>
						<div class=" col-xs-12  text-center page-height padding-left-0 padding-right-0" id="pagination">
						</div>
					</div>

					<div class="tab-pane fade" id="fieldmaterial">
						<div class="row feature">
						<%@ include file="/WEB-INF/views/material/stock/fieldmaterial_main.jsp"%></div>
					</div>

					<div class="tab-pane fade" id="assage">
						<div class="row feature">
							<%@ include file="/WEB-INF/views/material/stock/assage_main.jsp"%>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>

	</div>
	
	<!-------替代部件对话框 Start-------->
<div class="modal fade" id="AlertModalWinAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 80%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">替代库存</div>
						<div class="font-size-9 ">Stock Substitution</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
		            	
		            		<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">	
					
								<%-- <div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
									<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">航材类型</div>
										<div class="font-size-9">Airmaterial type</div>
									</span>
									<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
										<select class='form-control' id='hclx_search' onchange="MaterialReserveModal.changeType();">
											<option value="" >显示全部All</option>
											<c:forEach items="${materialTypeEnum}" var="item">
												<option value="${item.id}" <c:if test="${100 == item.id }">selected=true</c:if> >${item.name}</option>
											</c:forEach>
									    </select>
									</div>
								</div>	 --%>
								
								
								<!-- 搜索框start -->
								<%-- <div class=" pull-right padding-left-5 padding-right-0">
									<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
										<input type="text" placeholder='件号/中英文/厂家件号' id="keyword_material_reserve_search" class="form-control ">
										<input type="hidden" id="dprtId" value="${user.jgdm}" />
									</div>
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="AlertModalWinAddModal.search()">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div> 
								</div> --%>
								<!-- 搜索框end -->
							</div>
		            	
			         		<div class="clearfix"></div>
							<!-- start:table -->
							<div class="margin-top-10 overflow-auto">
								<table class="table table-bordered table-striped table-hover text-center table-set" >
									<thead>
										<tr>
											<th class='colwidth-5' >
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class='colwidth-15' >
												<div class="font-size-12 line-height-18">件号</div>
												<div class="font-size-9 line-height-18">P/N</div>
											</th>
											<th class='colwidth-30'>
												<div class="font-size-12 line-height-18">英文名称</div>
												<div class="font-size-9 line-height-18">F.Name</div>
											</th>
											<th class='colwidth-25'>
												<div class="font-size-12 line-height-18">中文名称</div>
												<div class="font-size-9 line-height-18">CH.Name</div>
											</th>
											<th class="colwidth-8">
												<div class="font-size-12 line-height-18">厂家件号</div>
												<div class="font-size-9 line-height-18">MP/N</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">航材类型</div>
												<div class="font-size-9 line-height-18">Airmaterial type</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">航材管理级别</div>
												<div class="font-size-9 line-height-18">Aircraft Management Level</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">序列号</div>
												<div class="font-size-9 line-height-18">S/N</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">批次号</div>
												<div class="font-size-9 line-height-18">B/N</div>
											</th>
											<th class="colwidth-6">
												<div class="font-size-12 line-height-18">适航证号</div>
												<div class="font-size-9 line-height-18">Certificate Of No.</div>
											</th>
											<th class="colwidth-5">
												<div class="font-size-12 line-height-18">数量</div>
												<div class="font-size-9 line-height-18">Num</div>
											</th>
											<th class="colwidth-5">
												<div class="font-size-12 line-height-18">单位</div>
												<div class="font-size-9 line-height-18">Unit</div>
											</th>
											<th class="colwidth-6">
												<div class="font-size-12 line-height-18">仓库</div>
												<div class="font-size-9 line-height-18">Store</div>
											</th>
											<th class="colwidth-8">
												<div class="font-size-12 line-height-18">库位</div>
												<div class="font-size-9 line-height-18">Storage Location</div>
											</th>
											<th class="colwidth-8">
												<div class="font-size-12 line-height-18">货架寿命</div>
												<div class="font-size-9 line-height-18">Shelf-Life</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">剩余货架寿命</div>
												<div class="font-size-9 line-height-18">Shelf-Life(day)</div>
											</th>
											<th class="colwidth-8">
												<div class="font-size-12 line-height-18">索赔期限</div>
												<div class="font-size-9 line-height-18">Claim Period</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">剩余索赔天数</div>
												<div class="font-size-9 line-height-18">Claim Period(day)</div>
											</th>
											<th class="colwidth-15" >
												<div class="font-size-12 line-height-18">维护人</div>
												<div class="font-size-9 line-height-18">Maintainer</div>
											</th>
											<th class="colwidth-13" >
												<div class="font-size-12 line-height-18">维护时间</div>
												<div class="font-size-9 line-height-18">Maintenance Time</div>
											</th>
											<th class="colwidth-5">
												<div class="font-size-12 line-height-18">状态</div>
												<div class="font-size-9 line-height-18">State</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="stockSubList">
									</tbody>
								</table>
							</div>
							<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="stockSubPagination"></div>
							<!-- end:table -->
		                	<div class="text-right margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
			                </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	<!-------替代部件对话框 End-------->
	
	
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/material/stock/stock_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
	
</body>
</html>