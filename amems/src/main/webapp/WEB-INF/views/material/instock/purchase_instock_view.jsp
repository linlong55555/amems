<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>采购入库</title>
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
				<ul class="page-breadcrumb breadcrumb">
					
					<li>
						<a onclick="javascript:void(0)" class="pull-left">
							<div class="font-size-12 line-height-12">采购入库</div>
							<div class="font-size-9 ">Purchase InStock</div>
						</a>
					</li>
				</ul>
			</div>
			<div class="clearfix"></div>
			<div class="panel-body">
				<div id="purchase_view_form">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">基本信息</div>
							<div class="font-size-9 ">Basic Info</div>
							<input type="hidden" id="id" value="${id}"/>
							<input type="hidden" id="operateType" value="${operateType}"/>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库单号</div>
								<div class="font-size-9 line-height-18">InStock No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="purchase_view_rkdh" id="purchase_view_rkdh" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">件号</div>
								<div class="font-size-9 line-height-18">P/N</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="purchase_view_bjh" id="purchase_view_bjh" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">中文名称</div>
								<div class="font-size-9 line-height-18">CH.Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="purchase_view_zwms" id="purchase_view_zwms" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">英文名称</div>
								<div class="font-size-9 line-height-18">F.Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="purchase_view_ywms" id="purchase_view_ywms" readonly />
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">管理级别</div>
								<div class="font-size-9 line-height-18">Management level</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='purchase_view_gljb' name="purchase_view_gljb" disabled>
									<c:forEach items="${gljb}" var="item">
									  <option value="${item.id}" >${item.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库数量</div>
								<div class="font-size-9 line-height-18">InStock Num</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="purchase_view_sl" id="purchase_view_sl" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">索赔期限</div>
								<div class="font-size-9 line-height-18">Claim period</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" name="purchase_view_spqx" id="purchase_view_spqx" data-date-format="yyyy-mm-dd" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">库存成本</div>
								<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="purchase_view_kccb" onkeyup="onkeyup4Num(this);" id="purchase_view_kccb" />
								<span class="input-group-btn">
									<button onclick="javascript:purchase_view.showContractCost();" class="btn btn-primary">
										<i class="icon-list"></i>
									</button>
								</span>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库人</div>
								<div class="font-size-9 line-height-18">Operator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="purchase_view_rkrmc" id="purchase_view_rkrmc"  readonly />
								<input type="hidden" id="purchase_view_rkrid" />
								<input type="hidden" id="purchase_view_rkbmid" />
								<span class="input-group-btn">
									<button onclick="javascript:purchase_view.selectUser();" class="btn btn-primary">
										<i class="icon-search"></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库日期</div>
								<div class="font-size-9 line-height-18">InStock Date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" name="purchase_view_rksj" id="purchase_view_rksj" data-date-format="yyyy-mm-dd" readonly />
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
								<textarea class="form-control" id="purchase_view_bz" name="purchase_view_bz" maxlength="300" ></textarea>
							</div>
						</div>
					</div>	
				</div>
				<!-- 航材列表 start -->
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px; overflow-x: auto;">
					<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-16">入库列表</div>
							<div class="font-size-9 ">InStock list</div>
						</div>	
					</div>
				
				
				<table class="table table-bordered table-striped table-hover" style="min-width:900px;">
					<thead>
						<tr>
							<th width="50px" id="purchase_view_column_operate">
								<button class="line6 " onclick="purchase_view.add();">
									<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
								</button>
							</th>
							<th >
								<div class="font-size-12 line-height-18">仓库</div>
								<div class="font-size-9 line-height-18">Stock</div>
							</th>
							<th class="colwidth-25">
								<div class="font-size-12 line-height-18">库位</div>
								<div class="font-size-9 line-height-18">Storage location</div>
							</th>
							<th class="colwidth-25">
								<div class="font-size-12 line-height-18">序列号/批次号</div>
								<div class="font-size-9 line-height-18">S/N</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">数量</div>
								<div class="font-size-9 line-height-18">Num</div>
							</th>
						</tr>
					</thead>
					<tbody id="purchase_view_list">
					</tbody>
				</table>
				</div>
				<div class="clearfix"></div>
				<div class="text-right margin-top-10 margin-bottom-0">
	                <button id="purchase_view_save" style="display:none" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:purchase_view.save()">
	                   	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					<button id="purchase_view_submit" style="display:none" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:purchase_view.submit()">
	                   	<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
	              		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:purchase_view.back();">
	              			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
	           	</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
		<%@ include file="/WEB-INF/views/open_win/contract_cost.jsp"%>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/instock/purchase_instock_view.js"></script>
</body>
</html>