<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>
			<div class="clearfix"></div>
			<div class="panel-body">
				<div id="lend_return_view_form">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">基本信息</div>
							<div class="font-size-9 ">Basic Info</div>
							<input type="hidden" id="id" value="${id}"/>
							<input type="hidden" id="operateType" value="${operateType}"/>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">件号</div>
								<div class="font-size-9 line-height-18">P/N</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="hidden" id="lend_return_view_bjid" value="${item.hcMainData.id}" />
								<input type="text" class="form-control" name="lend_return_view_bjh" id="lend_return_view_bjh" value="${erayFns:escapeStr(item.hcMainData.bjh)}" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">中文名称</div>
								<div class="font-size-9 line-height-18">CH.Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="lend_return_view_zwms" id="lend_return_view_zwms" value="${erayFns:escapeStr(item.hcMainData.zwms)}" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">英文名称</div>
								<div class="font-size-9 line-height-18">F.Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="lend_return_view_ywms" id="lend_return_view_ywms" value="${erayFns:escapeStr(item.hcMainData.ywms)}" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">管理级别</div>
								<div class="font-size-9 line-height-18">Management level</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='lend_return_view_gljb' name="lend_return_view_gljb" disabled>
									<option value = ""></option>
									<c:forEach items="${gljb}" var="row">
									  <option value="${row.id}" <c:if test="${item.hcMainData.gljb eq row.id }">selected='selected'</c:if>>${row.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">借调对象</div>
								<div class="font-size-9">Seconded obj</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="lend_return_view_jddx" id="lend_return_view_jddx" value="${erayFns:escapeStr(item.secondment.jddxms)}" readonly />
								<input type="hidden" id="lend_return_view_wpdxid" value="${item.secondment.id}" />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">待归还数量</div>
								<div class="font-size-9 line-height-18">To be returned</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="lend_return_view_dghsl" id="lend_return_view_dghsl" value="${item.dghsl}" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库人</div>
								<div class="font-size-9 line-height-18">Operator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="lend_return_view_rkrmc" id="lend_return_view_rkrmc" value="${erayFns:escapeStr(sessionScope.user.username)} ${erayFns:escapeStr(sessionScope.user.realname)}" readonly />
								<input type="hidden" id="lend_return_view_rkrid" value="${sessionScope.user.id}"/>
								<input type="hidden" id="lend_return_view_rkbmid" value="${sessionScope.user.bmdm}"/>
								<span class="input-group-btn">
									<button onclick="javascript:lend_return_view.selectUser();"  class="btn btn-primary">
										<i class="icon-search" ></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">接收人</div>
								<div class="font-size-9 line-height-18">Recipient</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="lend_return_view_sqrmc" id="lend_return_view_sqrmc"  readonly />
								<input type="hidden" id="lend_return_view_sqrid" />
								<input type="hidden" id="lend_return_view_sqbmid" />
								<span class="input-group-btn">
									<button onclick="javascript:lend_return_view.selectUser1();"  class="btn btn-primary">
										<i class="icon-search" ></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">归还人</div>
								<div class="font-size-9 line-height-18">Return</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="lend_return_view_jdfzr" id="lend_return_view_jdfzr" />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库日期</div>
								<div class="font-size-9 line-height-18">Operate date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" name="lend_return_view_rksj" id="lend_return_view_rksj" data-date-format="yyyy-mm-dd" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">归还数量</div>
								<div class="font-size-9 line-height-18">Return Num</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="lend_return_view_sl" id="lend_return_view_sl"/>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">库存成本</div>
								<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="lend_return_view_kccb" id="lend_return_view_kccb" onkeyup="onkeyup4Num(this);" />
								<span class="input-group-btn">
									<button onclick="javascript:lend_return_view.showContractCost();"  class="btn btn-primary">
										<i class="icon-list" ></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
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
						<div class="pull-left ">
							<button id="lend_return_view_add" class="btn btn-primary " onclick="lend_return_view.add();">
								<i class="icon-plus cursor-pointer" ></i>
							</button>
			         	</div>	
					</div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
					<table class="table table-thin table-bordered table-hover table-set" style="min-width:1350px;">
						<thead>
							<tr>
								<th class="colwidth-7" id="lend_return_view_column_operate">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
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
									<div class="font-size-9 line-height-18">S/N</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Num</div>
								</th>
							</tr>
						</thead>
						<tbody id="lend_return_view_list">
						</tbody>
					</table>
				</div>
				<div class="clearfix"></div>
				<div class="text-right margin-top-10 margin-bottom-0">
					<button id="lend_return_view_submit" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:lend_return_view.submit();">
	                   	<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
	          		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:lend_return_view.back();">
	              			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
	           	</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
		<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%>
		<%@ include file="/WEB-INF/views/open_win/contract_cost.jsp"%>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/instock/lend_return_instock_view.js"></script>
</body>
</html>