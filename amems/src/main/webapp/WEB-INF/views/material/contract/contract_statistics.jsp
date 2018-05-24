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
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<input type="hidden" id="adjustHeight" value="70">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<div class="col-xs-2 col-md-3 padding-left-0 row-height" >
					<button type="button" data-toggle="modal" onclick="cotractOutExcel();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" >
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</button> 
				</div>
				<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='合同编号' id="keyword_search" class="form-control ">
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
				
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 search-height display-none border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">合同类型</div>
							<div class="font-size-9">Contract Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='htlx_search' onchange="changeContractType()">
								<option value="" selected="selected" >显示全部All</option>
								<c:forEach items="${contractTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">合同签订日期</div>
							<div class="font-size-9">Date of signing</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="htqdrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">紧急程度</div>
							<div class="font-size-9">Emergency</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jjcd_search'>
								<option value="" selected="selected" >显示全部All</option>
								<c:forEach items="${urgencyEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">服务商</div>
							<div class="font-size-9">Supplier</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='gys_search'>
							</select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control " onchange="changeContractType()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				
				<div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-10">
                	<ul id="myTab" class="nav nav-tabs">
                    	<li class="active"><a href="#payStatistics" data-toggle="tab" aria-expanded="true">付款统计 Pay Statistics</a></li>
                      	<li class=""><a href="#paymentDetail" data-toggle="tab" aria-expanded="false">付款明细 Payment details</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                    	<div class="tab-pane fade active in" id="payStatistics">
                    	
                    	
                    	
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
								<table id="pay_main_table" class="table table-thin table-bordered table-hover text-center table-set"  style="min-width: 500px;">
									<thead>
										<tr>
											<th class="colwidth-3" >
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class="sorting colwidth-7 " onclick="orderByStatistics('HTLX')" id="HTLX_order">
												<div class="font-size-12 line-height-18">合同类型</div>
												<div class="font-size-9 line-height-18">Contract Type</div>
											</th>
											<th class="sorting colwidth-10 " onclick="orderByStatistics('HTH')" id="HTH_order">
												<div class="important">
													<div class="font-size-12 line-height-18">合同编号</div>
													<div class="font-size-9 line-height-18">Order No.</div>
												</div>
											</th>
											<th class="sorting colwidth-10 " onclick="orderByStatistics('GYSMC')" id="GYSMC_order">
												<div class="font-size-12 line-height-18">服务商</div>
												<div class="font-size-9 line-height-18">Supplier name</div>
											</th>
											<th class="sorting colwidth-9 " onclick="orderByStatistics('HTQDRQ')" id="HTQDRQ_order">
												<div class="font-size-12 line-height-18">合同签订日期</div>
												<div class="font-size-9 line-height-18">Date of signing</div>
											</th>
											<th class="sorting colwidth-7 " onclick="orderByStatistics('JJCD')" id="JJCD_order">
												<div class="font-size-12 line-height-18">紧急程度</div>
												<div class="font-size-9 line-height-18">Emergency</div>
											</th>
											<th class="sorting colwidth-15 " onclick="orderByStatistics('YF')" id="YF_order">
												<div class="font-size-12 line-height-18">应付(人民币：元)</div>
												<div class="font-size-9 line-height-18">Accounts payable(RMB:YUAN)</div>
											</th>
											<th class="sorting colwidth-10 " onclick="orderByStatistics('WF')" id="WF_order">
												<div class="font-size-12 line-height-18">未付(人民币：元)</div>
												<div class="font-size-9 line-height-18">Unpaid(RMB:YUAN)</div>
											</th>
											<th class="sorting colwidth-10" onclick="orderByStatistics('YFBL')" id="YFBL_order">
												<div class="font-size-12 line-height-18">已付比例(次数)</div>
												<div class="font-size-9 line-height-18">Paid(Second)</div>
											</th>
											<th class="sorting colwidth-10 " onclick="orderByStatistics('DHBL')" id="DHBL_order">
												<div class="font-size-12 line-height-18">到货比例</div>
												<div class="font-size-9 line-height-18">Arrival ratio</div>
											</th>
											<th class="sorting colwidth-10" onclick="orderByStatistics('DPRTCODE')" id="DPRTCODE_order">
												<div class="font-size-12 line-height-18">组织机构</div>
												<div class="font-size-9 line-height-18">Organization</div>
											</th>
										</tr>
									</thead>
									<tbody id="payStatisticsList"></tbody>
								</table>
							</div>
							<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="payStatisticsPagination"></div>
                      	</div>
                      	
                      	<div class="tab-pane fade" id="paymentDetail">
                      		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
								<table id="pay_detail_table" class="table table-thin table-bordered table-hover text-left table-set"  style="min-width: 500px;">
									<thead>
										<tr>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class="sorting colwidth-7 " onclick="orderByDetail('HTLX')" id="HTLX_dorder">
												<div class="font-size-12 line-height-18">合同类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</th>
											<th class="sorting colwidth-10 " onclick="orderByDetail('HTH')" id="HTH_dorder">
												<div class="important">
													<div class="font-size-12 line-height-18">合同编号</div>
													<div class="font-size-9 line-height-18">Order No.</div>
												</div>
											</th>
											<th class="sorting colwidth-10 " onclick="orderByDetail('GYSMC')" id="GYSMC_dorder">
												<div class="font-size-12 line-height-18">服务商</div>
												<div class="font-size-9 line-height-18">Supplier</div>
											</th>
											<th class="sorting colwidth-9 " onclick="orderByDetail('HTQDRQ')" id="HTQDRQ_dorder">
												<div class="font-size-12 line-height-18">合同签订日期</div>
												<div class="font-size-9 line-height-18">Date</div>
											</th>
											<th class="sorting colwidth-7 " onclick="orderByDetail('JJCD')" id="JJCD_dorder">
												<div class="font-size-12 line-height-18">紧急程度</div>
												<div class="font-size-9 line-height-18">Emergency</div>
											</th>
											<th class="sorting colwidth-9 " onclick="orderByDetail('FKRQ')" id="FKRQ_dorder">
												<div class="font-size-12 line-height-18">付款日期</div>
												<div class="font-size-9 line-height-18">Pay Date</div>
											</th>
											<th class="sorting colwidth-15 " onclick="orderByDetail('FKJE')" id="FKJE_dorder">
												<div class="font-size-12 line-height-18">付款金额(人民币：元)</div>
												<div class="font-size-9 line-height-18">Payment(RMB:YUAN)</div>
											</th>
											<th class="sorting colwidth-10 " onclick="orderByDetail('FKFS')" id="FKFS_dorder">
												<div class="font-size-12 line-height-18">付款方式</div>
												<div class="font-size-9 line-height-18">Pay desc</div>
											</th>
											<th class="sorting colwidth-10 " onclick="orderByDetail('BZ')" id="BZ_dorder">
												<div class="font-size-12 line-height-18">备注</div>
												<div class="font-size-9 line-height-18">Remark</div>
											</th>
											<th class="sorting colwidth-10 " onclick="orderByDetail('DPRTCODE')" id="DPRTCODE_dorder">
												<div class="font-size-12 line-height-18">组织机构</div>
												<div class="font-size-9 line-height-18">Organization</div>
											</th>
										</tr>
									</thead>
									<tbody id="payDetailList"></tbody>
								</table>
							</div>
							<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="payDetailPagination"></div>
                      	</div>
                  </div>
              </div>
		</div>
	</div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/material/contract/contract_statistics.js"></script>
</body>
</html>