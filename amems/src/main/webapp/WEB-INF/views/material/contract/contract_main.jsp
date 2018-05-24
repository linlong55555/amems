<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>合同管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content " id="contract_main" >
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<div class="col-xs-2 col-md-3 padding-left-0">
					<a href="javascript:void(0);" onclick="add()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="aerialmaterial:contract:main:01">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a>
				</div>
				<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0"
						style="width: 250px;">
						<input type="text" placeholder='合同号/合同编号/供应商编号名称/备注'
							id="keyword_search" class="form-control ">
					</div>
					<div class=" pull-right padding-left-5 padding-right-0 ">
						<button type="button"
							class=" btn btn-primary padding-top-1 padding-bottom-1 "
							onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
						</button>
						<button type="button"
							class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight"
							id="btn" onclick="search();">
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
				<!-- 搜索框end -->

				<div
					class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc"
					id="divSearch">


					<div
						class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">合同类型</div>
							<div class="font-size-9">Contract Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='htlx_search'
								onchange="changeContractType()">
								<option value="" selected="selected">显示全部All</option>
								<c:forEach items="${contractTypeEnum}" var="item">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div
						class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">合同签订日期</div>
							<div class="font-size-9">Date of signing</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker"
								id="htqdrq_search" readonly />
						</div>
					</div>

					<div
						class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">紧急程度</div>
							<div class="font-size-9">Emergency</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jjcd_search'>
								<option value="" selected="selected">显示全部All</option>
								<c:forEach items="${urgencyEnum}" var="item">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div
						class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">供应商</div>
							<div class="font-size-9">Supplier</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control selectpicker' data-live-search="true" 
								data-container="body" data-size="10" id="gys_search">
							</select>
						</div>
					</div>

					<div class="clearfix"></div>

					<div
						class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">制单人</div>
							<div class="font-size-9">Creator</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " id="zdr_search" />
						</div>
					</div>

					<div
						class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">制单日期</div>
							<div class="font-size-9">Create Time</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker"
								id="zdrq_search" readonly />
						</div>
					</div>

					<div
						class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">合同到货状态</div>
							<div class="font-size-9">Arrival State</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='dhzt_search'>
								<option value="" selected="selected">显示全部All</option>
								<c:forEach items="${contractDeliveryStatusEnum}" var="item">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div
						class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search"
								class="form-control " onchange="changeContractType()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="col-lg-2  text-right padding-right-0 pull-right"
						style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>

				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0"
					style="margin-top: 10px;overflow-x:auto;">
					<table id="contract_main_table" class="table table-thin table-bordered table-set" style="min-width: 2030px;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-10">
									<div class="font-size-12 line-height-18 ">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-3">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('htlsh')" id="htlsh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">合同号</div>
										<div class="font-size-9 line-height-18">Contract No.</div>
									</div>
								</th>
								
								<th class="colwidth-15 sorting" onclick="orderBy('hth')"
									id="hth_order">
									<div class="important">
										<div class="font-size-12 line-height-18">合同编号</div>
										<div class="font-size-9 line-height-18">Order No.</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('htlx')"
									id="htlx_order">
									<div class="font-size-12 line-height-18">合同类型</div>
									<div class="font-size-9 line-height-18">Contract Type</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('jjcd')"
									id="jjcd_order">
									<div class="font-size-12 line-height-18">紧急程度</div>
									<div class="font-size-9 line-height-18">Emergency</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('gysbm')"
									id="gysbm_order">
									<div class="important">
										<div class="font-size-12 line-height-18">供应商编号</div>
										<div class="font-size-9 line-height-18">Supplier No.</div>
									</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('gysmc')"
									id="gysmc_order">
									<div class="important">
										<div class="font-size-12 line-height-18">供应商名称</div>
										<div class="font-size-9 line-height-18">Supplier name</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('zt')"
									id="zt_order">
									<div class="font-size-12 line-height-18">合同状态</div>
									<div class="font-size-9 line-height-18">Contract state</div>
								</th>
								<th class="colwidth-9 sorting" 
									onclick="orderBy('htqdrq')" id="htqdrq_order">
									<div class="font-size-12 line-height-18">合同签订日期</div>
									<div class="font-size-9 line-height-18">Date of signing</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('dhzt')" id="dhzt_order">
									<div class="font-size-12 line-height-18">合同到货状态</div>
									<div class="font-size-9 line-height-18">Arrival state</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('htfy')"
									id="htfy_order">
									<div class="font-size-12 line-height-18">合同费用(人民币：元)</div>
									<div class="font-size-9 line-height-18">Contract cost(RMB:YUAN)</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('bz')"
									id="bz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('zdrid')"
									id="zdrid_order">
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creator</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('zdsj')"
									id="zdsj_order">
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
								</th>
								<th class="colwidth-15 sorting" 
									onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>

				<div class="clearfix"></div>
				<div id="selectContract"></div>
				<div
					class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0">
					<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a href="#home" data-toggle="tab"
							aria-expanded="true">航材 Material</a></li>
						<li class=""><a href="#Dropdown" data-toggle="tab"
							aria-expanded="false">留言 Message</a></li>
						<li class=""><a href="#profile" data-toggle="tab"
							aria-expanded="false">附件 Attachments</a></li>
						<li class=""><a href="#pay" data-toggle="tab"
							aria-expanded="false">付款 Payment</a></li>
					</ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade active in" id="home">

							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width: 1600px;">
									<thead id="contractDetailHead">
										<tr>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class="colwidth-10" >
												<div class="font-size-12 line-height-18">提订单号</div>
												<div class="font-size-9 line-height-18">Order No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">送修单号</div>
												<div class="font-size-9 line-height-18">Repair No.</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">件号</div>
												<div class="font-size-9 line-height-18">P/N</div>
											</th>
											<th class="colwidth-25">
												<div class="font-size-12 line-height-18">英文名称</div>
												<div class="font-size-9 line-height-18">F.Name</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">中文名称</div>
												<div class="font-size-9 line-height-18">CH.Name</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">厂家件号</div>
												<div class="font-size-9 line-height-18">MP/N</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">管理级别</div>
												<div class="font-size-9 line-height-18">Management
													level</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">序列号</div>
												<div class="font-size-9 line-height-18">S/N</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">数量</div>
												<div class="font-size-9 line-height-18">Num</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">单位</div>
												<div class="font-size-9 line-height-18">Unit</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">单价(人民币：元)</div>
												<div class="font-size-9 line-height-18">Price(RMB:YUAN)</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">材料费(人民币：元)</div>
												<div class="font-size-9 line-height-18">Material
													cos(RMB:YUAN)</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">工时费(人民币：元)</div>
												<div class="font-size-9 line-height-18">Time
													cost(RMB:YUAN)</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">其它费(人民币：元)</div>
												<div class="font-size-9 line-height-18">Other
													cost(RMB:YUAN)</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">总价(人民币：元)</div>
												<div class="font-size-9 line-height-18">Total(RMB:YUAN)</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">到货数量</div>
												<div class="font-size-9 line-height-18">Arrival Num</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">入库数量</div>
												<div class="font-size-9 line-height-18">InStore Num</div>
											</th>
										</tr>
									</thead>
									<tbody id="contractDetailList">

									</tbody>

								</table>
							</div>
						</div>

						<div class="tab-pane fade" id="Dropdown">
							<%@ include
								file="/WEB-INF/views/common/message/message_list_view.jsp"%>
						</div>

						<div class="tab-pane fade" id="profile">
							<%@ include
								file="/WEB-INF/views/common/attachments/attachments_list_view.jsp"%>
						</div>

						<div class="tab-pane fade" id="pay">
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0"
								style="overflow-x:auto; ">
								<table class="table table-thin table-bordered table-striped table-hover text-left table-set" style="min-width:800px">
									<thead>
										<tr>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">付款日期</div>
												<div class="font-size-9 line-height-18">Payment Date</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">付款方式</div>
												<div class="font-size-9 line-height-18">Payment method</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">付款金额(人民币：元)</div>
												<div class="font-size-9 line-height-18">Payment amount(RMB:YUAN)</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">付款方式说明</div>
												<div class="font-size-9 line-height-18">Pay desc</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">操作人</div>
												<div class="font-size-9 line-height-18">Operator</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">操作时间</div>
												<div class="font-size-9 line-height-18">Operate time</div>
											</th>
										</tr>
									</thead>
									<tbody id="payList"></tbody>
								</table>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>

	</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/material/contract/contract_main.js"></script>
<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
</body>
</html>