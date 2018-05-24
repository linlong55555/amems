<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
	var tabId = '${param.tabId}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="fazhi" value="${threshold}" />
	<input type="hidden" id="adjustHeight" value="70" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="clearfix"></div>
			<div class="panel-body padding-bottom-0">
			
				<!-----标签导航start---->
				<ul class="nav nav-tabs" role="tablist" id="tablist">
				  <li role="presentation" class="active"><a id="tab_main" onclick="loadMain();" href="#requisition" data-toggle="tab">领料 Requisition</a></li>
				  <li role="presentation"><a id="tab_history" onclick="loadHistory();" href="#requisitionHistory" data-toggle="tab">领料历史 Requisition history</a></li>
				</ul>
				<!-----标签内容start---->
		      	<div class="tab-content margin-bottom-10">
					<div class="tab-pane fade in active"  id="requisition">
						<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
							
							<div class="col-xs-2 col-md-3 padding-left-0 ">
								<a href="javascript:void(0);" onclick="goEdit();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="aerialmaterial:requisition:main:01">
									<div class="font-size-12">领料申请单</div>
									<div class="font-size-9">Requisition</div>
								</a>
<!-- 								<span id="requisiton_message" class="pull-left text-center" style="padding-left:10px; line-height:35px;"></span>  -->
							</div>
							<div class="alert alert-success col-xs-4 col-md-4 text-center" style="padding-top: 5px;padding-bottom: 5px; margin-bottom:0px;vertical-align: middle;display:none;" role="alert" id="requisiton_message"></div>
							<div class="pull-right padding-left-0 padding-right-0">
								<div class="pull-left padding-left-0 padding-right-0" style="width:250px;">
									<input type="text" class="form-control row-height" id="keyword_search" placeholder="件号/GRN/中英文/厂家件号/序列号/批次号"/>
								</div>
			                    <div class="pull-right padding-left-5 padding-right-0">   
			                   		<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="search();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
									</button>
									<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="more_params();">
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
							<div class="col-xs-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc search-height" id="divSearch">
								<div class="col-lg-12  padding-left-0 padding-right-0">
									<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
										<div class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0 ">
											<div class="font-size-12">航材类型</div>
											<div class="font-size-9">Airmaterial type</div></div>
										<div class="form-group col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select id="hclx" class="form-control "  name="hclx">
												<option value="">显示全部All</option>
												<option value="1" >航材</option>
												<option value="4" >化工品</option>
												<option value="5" >低值易耗品</option>
												<option value="6" >松散件</option>
												<option value="0" >其他</option>
											</select>
										</div>
									</div>
									<div class="col-lg-3 col-sm-6 col-xs-12 margin-bottom-10 padding-left-0 padding-right-0">
										<div class="pull-left col-xs-4 col-sm-4 col-lg-4  text-right padding-left-0 padding-right-0">
										<div class="font-size-12">仓库</div>
										<div class="font-size-9">Store</div></div>
										<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
											<select class='form-control' id='ckh' name="ckh" >
												
											</select>
										</div>
									</div>
									<div class="col-lg-3 col-xs-3 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
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
						<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:scroll;">
							<table id="lingliao" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 2100px !important;">
								<thead>
									<tr>
										<th class="fixed-column colwidth-7">
											<div class="font-size-12 line-height-18">操作</div>
											<div class="font-size-9 line-height-18">Operation</div>
										</th>
										<th class="fixed-column colwidth-3">
											<div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-18">No.</div>
										</th>
										<th class="fixed-column colwidth-15 sorting" name="column_bjh" onclick="orderBy('bjh',this)">
											<div class="important">
												<div class="font-size-12 line-height-18">件号</div>
											</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</th>
										<th class="fixed-column colwidth-15 sorting" name="column_grn" onclick="orderBy('grn',this)">
											<div class="important">
												<div class="font-size-12 line-height-18">GRN</div>
											</div>
											<div class="font-size-9 line-height-18">GRN</div>
										</th>
										<th class="colwidth-25 sorting" name="column_ywms" onclick="orderBy('ywms',this)">
											<div class="important">
												<div class="font-size-12 line-height-18">英文名称</div>
											</div>
											<div class="font-size-9 line-height-18">F.Name</div>
										</th>
										<th class="colwidth-20 sorting" name="column_zwms" onclick="orderBy('zwms',this)">
											<div class="important">
												<div class="font-size-12 line-height-18">中文名称</div>
											</div>
											<div class="font-size-9 line-height-18">CH.Name</div>
										</th>
										<th class="sorting colwidth-15" name="column_cjjh" onclick="orderBy('cjjh',this)"><div class="important"><div class="font-size-12 line-height-18">厂家件号</div></div>
										<div class="font-size-9 line-height-18">MP/N</div></th>
										<th class="sorting colwidth-10" name="column_hclx" onclick="orderBy('hclx',this)"><div class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Airmaterial type</div></th>
										<th  class="sorting colwidth-13" name="column_gljb" onclick="orderBy('gljb',this)">
											<div class="font-size-12 line-height-18">航材管理级别</div>
											<div class="font-size-9 line-height-18">
											Management level
											</div>
										</th>
										<th class="colwidth-20">
											<div class="important"><div class="font-size-12 line-height-18">序列号</div></div>
											<div class="font-size-9 line-height-18">S/N</div>
										</th>
										<th class="colwidth-20">
											<div class="important"><div class="font-size-12 line-height-18">批次号</div></div>
											<div class="font-size-9 line-height-18">P/N</div>
										</th>
										<th  class="sorting colwidth-10" name="column_shzh" onclick="orderBy('shzh',this)"><div class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Certificate</div></th>
										<th  class="sorting colwidth-7" name="column_kcsl" onclick="orderBy('kcsl',this)"><div class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
										<th class="sorting colwidth-7" name="column_jldw" onclick="orderBy('jldw',this)"><div class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div></th>
										<th class="sorting colwidth-15" name="column_ckmc" onclick="orderBy('ckmc',this)" ><div class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Store</div></th>
										<th  class="sorting colwidth-15" name="column_kwh" onclick="orderBy('kwh',this)"><div class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage location</div></th>
										<th  class="sorting colwidth-9" name="column_hjsm" onclick="orderBy('hjsm',this)"><div class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-life</div></th>
										<th class="colwidth-7" ><div class="font-size-12 line-height-18">剩余天数</div>
										<div class="font-size-9 line-height-18">Surplus</div></th>
									</tr>
								</thead>
								<tbody id="list">
								</tbody>
							</table>
						</div>
						<div class=" col-xs-12 text-center page-height padding-left-0 padding-right-0" id="pagination">
						</div>
					</div>
			        <div class="tab-pane fade" id="requisitionHistory">
		          		<%@ include file="/WEB-INF/views/material/requisition/requisition_history.jsp"%>
			        </div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/requisition/requisition_main.js"></script>
</body>
</html>