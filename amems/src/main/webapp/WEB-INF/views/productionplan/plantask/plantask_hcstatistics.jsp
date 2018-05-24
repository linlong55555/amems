<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>航材缺件统计</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

</head>
<body class="page-header-fixed">

<select class='form-control' id='planTaskIds' name="planTaskIds" style="display: none;" >
	<c:forEach items="${planTaskIds}" var="item">
	  <option value="${item}" >${item}</option>
	</c:forEach>
</select>
							    
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<div class="page-content">
	<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="font-size-16 line-height-12">航材工具缺件统计列表</div>
						<div class="font-size-9 ">Missing parts list</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
						<div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-10" >
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label class="col-xs-2 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18"><span style="color: red">*</span>统计类型</div>
									<div class="font-size-9 line-height-18">Statistics Type</div>
								</label>
								<div class="col-xs-10 padding-left-8 padding-right-0" >
										&nbsp;<input type="radio" id='tjlx_qj' name='tjlx' value='Lack' checked="checked"/>  &nbsp;缺件统计
										&nbsp;<input type="radio" id='tjlx_xj' name='tjlx' value='Requirement'/>  &nbsp;需求统计
								</div>
							</div>
		                   <div class="clearfix"></div>
		                	<div href="#" class="div_qh  col-xs-12 col-lg-3 "   style="overflow-y: auto; height: 600px;">
								<c:forEach items="${tasks}" var="item">
									<div href="#" class="div_qh  col-xs-12 ">
		                	         
								    <p>
										<em>任务单号：</em><em>${item.rwdh}</em>
									</p>
									<p>
										<em>任务信息 :</em><em>${item.rwxx}</em>
									</p>
									</div>
								</c:forEach>
							</div>
		                  
		                  <div class="col-lg-9 pull-right col-xs-12  padding-right-0" style="overflow-y: auto; height: 600px;"> 
							<ul id="myTab" class="nav nav-tabs">
								<li id="HC_DEFECT" class="active"><a href="#home" data-toggle="tab"
									aria-expanded="true">航材列表</a></li>
								<li id="TOOL_DEFECT" class=""><a href="#profile" data-toggle="tab"
									aria-expanded="false">工具列表</a></li>
								<!-- <li class=""><a href="#Dropdown" data-toggle="tab"
									aria-expanded="false">缺件计划信息 </a></li> -->
							</ul>
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane fade active in" id="home">

									<div class="col-lg-12  col-xs-12 padding-left-0 padding-right-0"
										style="margin-top: 10px; overflow-x: scroll;"  >
										<table style="min-width: 1500px;" 
											class="table table-thin table-bordered table-striped table-hover text-center">
											<thead>
												<tr>
													 <th class=" colwidth-10" ><div class="font-size-12 line-height-18">航材类型</div>
														<div class="font-size-9 line-height-18">Airmaterial type</div></th>
													<th class=" colwidth-15" ><div class="font-size-12 line-height-18">件号</div>
														<div class="font-size-9 line-height-18">P/N</div></th>
													<th  class=" colwidth-25"><div
															class="font-size-12 line-height-18">中文名称</div>
														<div class="font-size-9 line-height-18">CH.Name</div></th>
													<th  class=" colwidth-20">
														<div
															class="font-size-12 line-height-18">英文名称</div>
														<div class="font-size-9 line-height-18">F.Name</div></th>
													<th  class=" colwidth-7"><div
															class="font-size-12 line-height-18">需求数量</div>
														<div class="font-size-9 line-height-18">Demand Num</div></th>
													<th class=" colwidth-7" ><div
															class="font-size-12 line-height-18">库存</div>
														<div class="font-size-9 line-height-18">Stock Num</div></th>
													<th class=" colwidth-7"><div
															class="font-size-12 line-height-18">缺件</div>
														<div class="font-size-9 line-height-18">Missing Num</div></th>
													<th class=" colwidth-30"><div
															class="font-size-12 line-height-18">任务/工单相关信息</div>
														<div class="font-size-9 line-height-18">Task</div></th>
													 
												</tr>
											</thead>
											<tbody id="hc_list">

											</tbody>

										</table>
									</div>

									<div class="col-xs-12 text-center">
										<ul class="pagination "
											style="margin-top: 0px; margin-bottom: 0px;" id="hc_pagination">
										</ul>
									</div>

								</div>
								<div class="tab-pane fade" id="profile">

									<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0"
										style="margin-top: 10px; overflow-x: scroll;">
										<table style="min-width: 1200px;"  
											class="table table-thin table-bordered table-striped table-hover text-center">
											<thead>
												<tr>
													 <th ><div class="font-size-12 line-height-18">航材类型</div>
														<div class="font-size-9 line-height-18">Airmaterial type</div></th>
													<th ><div class="font-size-12 line-height-18">件号</div>
														<div class="font-size-9 line-height-18">P/N</div></th>
													<th  ><div
															class="font-size-12 line-height-18">中文名称</div>
														<div class="font-size-9 line-height-18">CH.Name</div></th>
													<th  >
														<div
															class="font-size-12 line-height-18">英文名称</div>
														<div class="font-size-9 line-height-18">F.Name</div></th>
													<th  ><div
															class="font-size-12 line-height-18">需求数量</div>
														<div class="font-size-9 line-height-18">Demand Num</div></th>
													<th  ><div
															class="font-size-12 line-height-18">库存</div>
														<div class="font-size-9 line-height-18">Stock Num</div></th>
													<th ><div
															class="font-size-12 line-height-18">缺件</div>
														<div class="font-size-9 line-height-18">Missing Num</div></th>
													
													<th ><div
															class="font-size-12 line-height-18">任务相关信息</div>
														<div class="font-size-9 line-height-18">Task</div></th>
												</tr>
											</thead>
											<tbody id="tool_list">
												 
											</tbody>
										</table>
									</div>

									<div class="col-xs-12 text-center">
										<ul class="pagination "
											style="margin-top: 0px; margin-bottom: 0px;" id="gj_pagination">
										</ul>
									</div>
								</div>
							</div>
							</div>
							
							<div class="col-xs-12 text-center">
								<ul class="pagination "
									style="margin-top: 0px; margin-bottom: 0px;" id="hcpagination">
								</ul>
							</div>
							<div class="clearfix"></div>
						</div>

					</div>
					<div class="clearfix"></div>
				</div>		 
</div>
	 <script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/plantask/plantask_hcstatistics.js"></script> 
	  
</body>
</html>