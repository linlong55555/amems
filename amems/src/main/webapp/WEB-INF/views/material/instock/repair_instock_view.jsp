<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<!-- 送修入库单 -->
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
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">送修件信息</div>
						<div class="font-size-9 ">Repair Info</div>
						<input type="hidden" id="id" value="${id}"/>
						<input type="hidden" id="operateType" value="${operateType}"/>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="repair_view_original_bjh" id="repair_view_original_bjh" readonly />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">中文名称</div>
							<div class="font-size-9 line-height-18">CH.Name</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="repair_view_zwms" id="repair_view_zwms" readonly />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">英文名称</div>
							<div class="font-size-9 line-height-18">F.Name</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="repair_view_ywms" id="repair_view_ywms" readonly />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">序列号</div>
							<div class="font-size-9 line-height-18">S/N</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="repair_view_original_sn" id="repair_view_original_sn" readonly />
						</div>
					</div>
				</div>	
				<div id="repair_view_form">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
						<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class=" pull-left margin-right-10" >
								<div class="font-size-16 line-height-16">入库信息</div>
								<div class="font-size-9 ">InStock info</div>
							</div>	
						</div>
						<div class="display-none col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库单号</div>
								<div class="font-size-9 line-height-18">InStock No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="repair_view_rkdh" id="repair_view_rkdh" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库人</div>
								<div class="font-size-9 line-height-18">Operator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="repair_view_rkrmc" id="repair_view_rkrmc"  readonly />
								<input type="hidden" id="repair_view_rkrid" />
								<input type="hidden" id="repair_view_rkbmid" />
								<span class="input-group-btn">
									<button onclick="javascript:repair_view.selectUser();" class="btn btn-primary">
										<i class="icon-search"></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库日期</div>
								<div class="font-size-9 line-height-18">InStock date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" name="repair_view_rksj" id="repair_view_rksj" data-date-format="yyyy-mm-dd" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">成本(人民币:元)</div>
								<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="repair_view_kccb" id="repair_view_kccb" onkeyup="onkeyup4Num(this);"/>
								<span class="input-group-btn">
									<button onclick="javascript:repair_view.showContractCost();" class="btn btn-primary">
										<i class="icon-list"></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库件号</div>
								<div class="font-size-9 line-height-18">P/N</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="repair_view_bjh" id="repair_view_bjh" readonly="readonly"/>
								<input type="hidden" name="repair_view_bjid" id="repair_view_bjid" />
								<span class="input-group-btn">
									<button onclick="javascript:repair_view.showMaterial();" class="btn btn-primary">
										<i class="icon-list"></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库序列号</div>
								<div class="font-size-9 line-height-18">S/N</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="repair_view_sn" id="repair_view_sn" onkeyup="onkeyup4Code(this);"/>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">仓库</div>
								<div class="font-size-9 line-height-18">Store</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class="form-control" name="repair_view_ck" id="repair_view_ck" ></select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">库位</div>
								<div class="font-size-9 line-height-18">Storage location</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class="form-control" name="repair_view_kw" id="repair_view_kw" ></select>
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
								<textarea class="form-control" id="repair_view_bz" name="repair_view_bz" maxlength="300" ></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="text-right margin-top-10 margin-bottom-0">
	                <button id="repair_view_save" style="display:none" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:repair_view.save()">
	                   	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					<button id="repair_view_submit" style="display:none" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:repair_view.submit()">
	                   	<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
	              		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:repair_view.back();">
	              			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
	           	</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
		<%@ include file="/WEB-INF/views/open_win/contract_cost.jsp"%>
		<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/instock/repair_instock_view.js"></script>
</body>
</html>