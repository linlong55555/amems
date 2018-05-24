<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">

	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<!-- 搜索框start -->
				<div class="pull-right padding-left-0 padding-right-0 padding-bottom-0">
					<div class="pull-left padding-left-0 padding-right-0 row-height" style="width:300px;">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞行队</div>
							<div class="font-size-9">Flying team</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="borrow_return_outstock_fxd" class="form-control" name="borrow_return_outstock_fxd">
								<option value="">显示全部All</option>
								<c:forEach items="${newRecordList}" var="item" varStatus="status">
										<option value="${item.jddxbh}">${erayFns:escapeStr(item.jddxms)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="pull-left padding-left-5 padding-right-0" style="width:250px;">
						<input type="text" class="form-control " id="borrow_return_outstock_keyword_search" placeholder="出库单号/负责人"/>
					</div>
                    <div class="pull-right padding-left-5 padding-right-0">   
                   		<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 resizeHeight" onclick="borrow_return_outstock.load();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
						</button>
                    </div>    
				</div>
				<!-- 搜索框end -->
			
				<div class="clearfix"></div>

				<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:auto;">
					<table class="table table-thin table-bordered table-hover table-set" id="borrow_return_outstock_table" style="min-width:750px">
						<thead>
							<tr>
								<th class="colwidth-7  fixed-column">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-3">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="sorting colwidth-13" name="column_dprtcode" onclick="borrow_return_outstock.orderBy('dprtcode', this)">
									<div class="font-size-12 line-height-18">飞行队</div>
									<div class="font-size-9 line-height-18">Flying team</div>
								</th>
								<th class="sorting colwidth-10" name="column_ckdh" onclick="borrow_return_outstock.orderBy('ckdh', this)">
									<div class="important">
										<div class="font-size-12 line-height-18">出库单号</div>
										<div class="font-size-9 line-height-18">Outstock No.</div>
									</div>
								</th>
								<th class="sorting colwidth-13" name="column_cksj"  onclick="borrow_return_outstock.orderBy('cksj', this)">
									<div class="font-size-12 line-height-18">出库日期</div>
									<div class="font-size-9 line-height-18">Outstock Date</div>
								</th>
								<th class="sorting colwidth-13" name="column_jdfzr"  onclick="borrow_return_outstock.orderBy('jdfzr', this)">
									<div class="important">
										<div class="font-size-12 line-height-18">负责人</div>
										<div class="font-size-9 line-height-18">Seconded Operator</div>
									</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">入库单号</div>
									<div class="font-size-9 line-height-18">Instock No.</div>
								</th>
							</tr>
						</thead>
						<tbody id="borrow_return_outstock_list">
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center padding-right-0 page-height" id="borrow_return_outstock_pagination"></div>
			</div>
		</div>
	</div>
	<!-- 外飞行队出库航材弹窗 start-->
	<div class="modal fade" id="borrow_return_outstock_detail_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0 ">
					<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="modal-title font-size-16 line-height-18">出库清单</div>
						<div class="modal-title font-size-9 ">Outstock List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
						<div class="margin-top-10 overflow-auto">
							<table class="table table-thin table-bordered table-hover table-set" id="borrow_return_outstock_detail_table">
								<thead>
									<tr>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-18">No.</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-18">英文名称</div>
											<div class="font-size-9 line-height-18">F.Name</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-18">中文名称</div>
											<div class="font-size-9 line-height-18">CH.Name</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">航材类型</div>
											<div class="font-size-9 line-height-18">Airmaterial type</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-18">序列号/批次号</div>
											<div class="font-size-9 line-height-18">SN/BN</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">数量</div>
											<div class="font-size-9 line-height-18">Num</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">计量单位</div>
											<div class="font-size-9 line-height-18">Unit</div>
										</th>
									</tr>
								</thead>
								<tbody id="borrow_return_outstock_detail_list">
								</tbody>
							</table>
						</div>
						<div class="text-right margin-top-10 margin-bottom-10">
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
	<!-- 外飞行队出库航材弹窗 end-->
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/otheraerocade/borrow_return_outstock_main.js"></script>
</body>
</html>