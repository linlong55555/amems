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
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
		<input type="hidden" id="adjustHeight" value="90">
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
				<ul class="nav nav-tabs" role="tablist" id="tablist">
					<li role="presentation" class="active"><a href="#borrowgiveback">借入未归还 Borrowed Not Returned</a></li>
					<li role="presentation"><a href="#loangiveback">借出未归还 Lended  Not Returned</a></li>
				</ul>

				<!-----标签内容start---->
				<div class="tab-content ">

					<div class="tab-pane fade in active" id="borrowgiveback">

						<div class="col-xs-12 padding-left-0 padding-right-0 margin-bottom-0 row-height">
							
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
		
					<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
						<input placeholder="件号/中英文" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="borrowgivebackSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
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
				<!------------搜索框end------->

							<div
								class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10  display-none border-cccccc"
								id="divSearch">

								<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 padding-right-0">

									<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 ">
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

									<div
										class="col-xs-12 col-sm-6 col-lg-3 margin-bottom-10 padding-left-0 padding-right-0">
										<span
											class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">借调对象类型</div>
											<div class="font-size-9">S/O Type</div>
										</span>
										<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
											<select class='form-control' id='jddxlx' name="jddxlx">
												<option value="">显示全部All</option>
												<option value="1">飞行队</option>
												<option value="2">航空公司</option>
												<option value="0">其它</option>
											</select>
										</div>
									</div>

									<div
										class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10  ">
										<span
											class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">组织机构</div>
											<div class="font-size-9">Organization</div>
										</span>
										<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
											<select id="dprtcode" class="form-control " name="dprtcode"
												onchange="changeSelectedPlane()">
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
						</div>
	<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0  table-h"  style="overflow-x:scroll">

						<table id="jrwgh" class="table table-thin table-bordered table-set ">
							<thead>
								<tr>
									<th class="colwidth-5" style="vertical-align: middle;"><div
											class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th  class="sorting colwidth-10" name="column_jddxms"	onclick="orderBy('jddxms',this)" ><div
											class="font-size-12 line-height-18">借调对象</div>
										<div class="font-size-9 line-height-18">Seconded Obj</div></th>
									<th  class="sorting colwidth-15" name="column_bjh" onclick="orderBy('bjh',this)"><div class="important"><div
											class="font-size-12 line-height-18">件号</div></div>
										<div class="font-size-9 line-height-18">P/N</div></th>
									<th  class="sorting colwidth-30"
									name="column_ywms"	onclick="orderBy('ywms',this)" ><div class="important"><div
											class="font-size-12 line-height-18">英文名称</div></div>
										<div class="font-size-9 line-height-18">F.Name</div></th>
									<th  class="sorting colwidth-25"
									name="column_zwms"	onclick="orderBy('zwms',this)" ><div class="important"><div
											class="font-size-12 line-height-18">中文名称</div></div>
										<div class="font-size-9 line-height-18">CH.Name</div></th>
									<th  class="sorting colwidth-15"
									name="column_cjjh"	onclick="orderBy('cjjh',this)" ><div
											class="font-size-12 line-height-18">厂家件号</div>
										<div class="font-size-9 line-height-18">MP/N</div></th>
									<th  class="sorting colwidth-9"
										name="column_hclx" onclick="orderBy('hclx',this)" ><div
											class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Airmaterial type</div></th>
									<th  class="sorting colwidth-9"
										name="column_gljb" onclick="orderBy('gljb',this)" ><div
											class="font-size-12 line-height-18">航材管理级别</div>
										<div class="font-size-9 line-height-18">Aircraft level</div></th>
									<th   class="sorting colwidth-7"
									name="column_dghsl"	onclick="orderBy('dghsl',this)"><div
											class="font-size-12 line-height-18">未归还数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
									<th class="colwidth-20"><div
											class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
						</table>
</div>
						<div class=" col-xs-12  text-center" id="pagination">
							
						</div>
					</div>

					<div class="tab-pane fade" id="loangiveback">
						<div class="row feature">
							<%@ include file="/WEB-INF/views/material/secondmentreturn/checkout_main.jsp"%>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>

	</div>
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/material/secondmentreturn/secondmentreturn_main.js"></script>
	
</body>
</html>