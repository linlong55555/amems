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
	var operateType = '${operateType}';
	var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="clearfix"></div>
			<div class="panel-body padding-bottom-0">
				<div id="lend_return_view_form">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">基本信息</div>
							<div class="font-size-9 ">Basic Info</div>
							<input type="hidden" id="id" value="${outstock.id}"/>
							<input type="hidden" id="operateType" value="${operateType}"/>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">借调对象</div>
								<div class="font-size-9">Seconded obj</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="lend_return_view_jddx" id="lend_return_view_jddx" value="" readonly />
								<input type="hidden" id="lend_return_view_jddxid" value="${outstock.dprtcode}" />
								<c:forEach items="${departments}" var="item" varStatus="status">
									<c:if test="${item.id == outstock.dprtcode}">
										<script>
											$("#lend_return_view_jddx").val('${erayFns:escapeStr(item.dprtname)}');
										</script>
									</c:if>
								</c:forEach>
							</div>
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">归还人</div>
								<div class="font-size-9 line-height-18">Return</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="lend_return_view_jdfzr" id="lend_return_view_jdfzr" maxlength="10" />
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">接收人</div>
								<div class="font-size-9 line-height-18">Recipient</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="lend_return_view_sqrmc" id="lend_return_view_sqrmc"  readonly />
								<input type="hidden" id="lend_return_view_sqrid" />
								<input type="hidden" id="lend_return_view_sqbmid" />
								<span class="input-group-btn">
									<button class="btn btn-primary" onclick="javascript:lend_return_view.selectUser1();">
										<i class="icon-search" ></i>
									</button>
								</span>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库人</div>
								<div class="font-size-9 line-height-18">Operator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="lend_return_view_rkrmc" id="lend_return_view_rkrmc" value="${sessionScope.user.username} ${sessionScope.user.realname}" readonly />
								<input type="hidden" id="lend_return_view_rkrid" value="${sessionScope.user.id}"/>
								<input type="hidden" id="lend_return_view_rkbmid" value="${sessionScope.user.bmdm}"/>
								<span class="input-group-btn">
									<button class="btn btn-primary" onclick="javascript:lend_return_view.selectUser();">
										<i class="icon-search" ></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库日期</div>
								<div class="font-size-9 line-height-18">Instock date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" name="lend_return_view_rksj" id="lend_return_view_rksj" data-date-format="yyyy-mm-dd" readonly />
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-11 col-xs-8 padding-left-8 padding-right-0">
								<textarea class="form-control" id="lend_return_view_bz" name="lend_return_view_bz" maxlength="300" ></textarea>
							</div>
						</div>
					</div>	
				</div>
				<!-- 航材列表 start -->
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
					<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-16">入库航材</div>
							<div class="font-size-9 ">InStock Aircraft</div>
						</div>
					</div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x:auto;">
					<table class="table table-thin table-bordered table-hover table-set" style="min-width:1600px">
						<thead>
							<tr>
								<th class="colwidth-3" id="lend_return_view_column_operate">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
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
									<div class="font-size-12 line-height-18">仓库</div>
									<div class="font-size-9 line-height-18">Store</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">库位</div>
									<div class="font-size-9 line-height-18">Storage location</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">序列号/批次号</div>
									<div class="font-size-9 line-height-18">SN/BN</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Num</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">库存成本</div>
									<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">借出件号</div>
									<div class="font-size-9 line-height-18">Lend P/N</div>
								</th>
							</tr>
						</thead>
						<tbody id="lend_return_view_list">
						</tbody>
					</table>
				</div>
				<div class="clearfix"></div>
				<div class="text-right margin-top-10 margin-bottom-0">
					<button id="lend_return_view_distribution" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:lend_return_view.distribution();">
	                   	<div class="font-size-12">分配</div>
						<div class="font-size-9">Distribution</div>
					</button>
					<button id="lend_return_view_submit" class="btn btn-primary padding-top-1 padding-bottom-1" disabled=true onclick="javascript:lend_return_view.submit();">
	                   	<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
	          		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:lend_return_view.back();">
	              			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
	           	</div>
	           	<!-- 借出列表 start -->
				<div id="lend_return_view_lend_div" class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
					<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-16">借出航材</div>
							<div class="font-size-9 ">Lend Aircraft</div>
						</div>
					</div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x:auto;">
					<table id="lend_return_view_lend_table" class="table table-thin table-bordered table-set" style="min-width:800px">
						<thead>
							<tr>
								<th class="colwidth-3" id="lend_return_view_column_operate">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
								</th>
								<th class="colwidth-25">
									<div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-18">F.Name</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Num</div>
								</th>
								<th class="colwidth-9">
									<div class="font-size-12 line-height-18">借出日期</div>
									<div class="font-size-9 line-height-18">Lend Date</div>
								</th>
							</tr>
						</thead>
						<tbody id="lend_return_view_lend_list">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/contract_cost.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/otheraerocade/lend_return_instock_view.js"></script>
</body>
</html>