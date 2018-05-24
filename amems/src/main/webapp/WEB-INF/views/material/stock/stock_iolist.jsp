<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<link >
<script type="text/javascript">
	var startDate = '${startDate}';
	var endDate = '${endDate}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="fazhi" value="${threshold}" />
	<input type="hidden" id="adjustHeight" value="104" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<!-- BEGIN CONTENT -->
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="clearfix"></div>
			<div class="panel-body padding-bottom-0">
				<!-----标签导航start---->
				<ul class="nav nav-tabs" role="tablist" id="tablist">
				  <li role="presentation"   class="active"><a href="#ilist" data-toggle="tab">入库清单 In Stock List</a></li>
				  <li role="presentation"   ><a id="loadhis" onclick="searchOut();" href="#olist" data-toggle="tab">出库清单  Out Stock List</a></li>
				</ul>
				<!-----标签内容start---->
		      	<div class="tab-content margin-bottom-10">
					<div class="tab-pane fade in active"  id="ilist">
							<div class="alert alert-success col-xs-2 col-md-3 text-center" style="padding-top: 5px;padding-bottom: 5px; margin-bottom:0px;vertical-align: middle;display:none;" role="alert" id="requisiton_message"></div>
							<div class=" pull-right padding-left-0 padding-right-0">
						 		<div class=" pull-left padding-left-0 padding-right-0" style="width: 300px;">
									<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">入库日期</div>
										<div class="font-size-9">In Stock Date</div>
									</span>
									<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " name="date-range-picker" id="inDate" readonly />
									</div>
								</div>
								
						 		<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
									<span class="pull-left col-lg-4 col-xs-4 col-sm-4  text-right padding-left-0 padding-right-0">
									<div class="font-size-12">组织机构</div>
										<div class="font-size-9">Organization</div></span>
									<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
										<select id="dprtcode1" class="form-control " name="dprtcode" >
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								
							
								<div class=" pull-right padding-left-0 padding-right-0">
									<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
										<input class="form-control " id="keyword_search" placeholder="入库单号/部件号/中英文/序列号/GRN" type="text">
									</div>
									<div class=" pull-right padding-left-5 padding-right-0 "> 
										<button id="InStockList" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="search();">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
										</button>
									</div>
								</div>
							</div>
							
							
							<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h" >
								<table id="instock" class="table table-thin table-bordered text-center table-set" >
									<thead>
										<tr>
											<!-- --出入库单号，人，时间，件号，英，中文，数量，机构 -->
											<th class="sorting colwidth-10" onclick="orderBy('T3.RKDH',this)" name="column_T3.RKDH">
												<div class="important">
													<div class="font-size-12 line-height-18">入库单号</div>
												</div>
												<div class="font-size-9 line-height-18">InStock No.</div>
											</th>
											<th class="sorting colwidth-15" onclick="orderBy('T1.BJH',this)" name="column_T1.BJH">
												<div class="important">
													<div class="font-size-12 line-height-18">件号</div>
												</div>
												<div class="font-size-9 line-height-18">P/N</div>
											</th>
											<th class="sorting colwidth-25" onclick="orderBy('T1.YWMS',this)" name="column_T1.YWMS">
												<div class="important">
													<div class="font-size-12 line-height-18">英文名称</div>
													<div class="font-size-9 line-height-18">F.Name</div>
												</div>
											</th>
											<th class="sorting colwidth-20" onclick="orderBy('T1.ZWMS',this)" name="column_T1.ZWMS">
												<div class="important">
													<div class="font-size-12 line-height-18">中文名称</div>
													<div class="font-size-9 line-height-18">CH.Name</div>
												</div>
											</th>
											
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">航材管理级别</div>
												<div class="font-size-9 line-height-18">Aircraft Level</div>
											</th>
												
											<th class="sorting colwidth-20" onclick="orderBy('B3.SN',this)" name="column_B3.SN">
												<div class="important">
												<div class="font-size-12 line-height-18">序列号</div></div>
												<div class="font-size-9 line-height-18">S/N</div>
												</div>
											</th>
											<th class="sorting colwidth-20" onclick="orderBy('B3.PCH',this)" name="column_B3.PCH">
												<div class="important">
												<div class="font-size-12 line-height-18">批次号</div></div>
												<div class="font-size-9 line-height-18">B/N</div>
												</div>
											</th>
											<th class="sorting colwidth-20" onclick="orderBy('B3.GRN',this)" name="column_B3.GRN">
												<div class="important">
												<div class="font-size-12 line-height-18">GRN</div></div>
												<div class="font-size-9 line-height-18">GRN</div>
												</div>
											</th>
											
											<th  class="sorting colwidth-7" onclick="orderBy('kcsl',this)" name="column_kcsl">
												<div class="font-size-12 line-height-18">数量</div>
												<div class="font-size-9 line-height-18">Num</div>
											</th>
											
											 <th  class="sorting colwidth-13" onclick="orderBy('T3.RKSJ',this)" name="column_T3.RKSJ">
												<div class="font-size-12 line-height-18">入库日期</div>
												<div class="font-size-9 line-height-18">In Stock Date</div>
											</th>
											
											 <th  class="sorting colwidth-15" onclick="orderBy('T3.DPRTCODE',this)" name="column_T3.DPRTCODE">
												<div class="font-size-12 line-height-18">组织机构</div>
												<div class="font-size-9 line-height-18">Organization</div>
											</th>
										 
										</tr>
									</thead>
									<tbody id="list">
									</tbody>
								</table>
							</div>
							<div class=" col-xs-12 text-center page-height" id="instock_pagination">
							</div>
					</div>
			        <div class="tab-pane fade" id="olist">
		          		   <%@ include file="/WEB-INF/views/material/stock/stock_olist.jsp"%>  
			        </div>
				</div>
				 
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/stock/stock_iolist.js"></script>
	 
</body>
</html>