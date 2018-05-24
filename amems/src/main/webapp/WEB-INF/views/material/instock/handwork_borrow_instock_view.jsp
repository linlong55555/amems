<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<!-- 借入入库 -->
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
				<div id="borrow_view_form">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">基本信息</div>
							<div class="font-size-9 ">Basic Info</div>
						</div>
						<div class="display-none col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">入库单号</div>
								<div class="font-size-9">InStock No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="borrow_view_rkdh" id="borrow_view_rkdh" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">借调对象类型</div>
								<div class="font-size-9">Type</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class="form-control"  name="borrow_view_jddxlx" id="borrow_view_jddxlx" >
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">借调对象</div>
								<div class="font-size-9">Seconded obj</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class="form-control"  name="borrow_view_jddx" id="borrow_view_jddx" >
								</select>							
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">借调方负责人</div>
								<div class="font-size-9">Seconded operator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="borrow_view_jdfzr" id="borrow_view_jdfzr" maxlength="10"/>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class="form-control"  name="borrow_view_fjzch" id="borrow_view_fjzch" >
								</select>							
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">接收人</div>
								<div class="font-size-9">Recipient</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="borrow_view_sqrmc" id="borrow_view_sqrmc"  value="${erayFns:escapeStr(user.username)} ${erayFns:escapeStr(user.realname)}" readonly />
								<input type="hidden" id="borrow_view_sqrid"  value="${user.id}"/>
								<input type="hidden" id="borrow_view_sqbmid" value="${user.bmdm}"/>
								<span class="input-group-btn">
									<button onclick="javascript:borrow_view.selectUser1();" class="btn btn-primary">
										<i class="icon-search" ></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">接收日期</div>
								<div class="font-size-9">Receiving date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" name="borrow_view_sqsj" id="borrow_view_sqsj" data-date-format="yyyy-mm-dd" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">入库人</div>
								<div class="font-size-9">Operator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="borrow_view_rkrmc" id="borrow_view_rkrmc"  value="${erayFns:escapeStr(user.username)} ${erayFns:escapeStr(user.realname)}" readonly />
								<input type="hidden" id="borrow_view_rkrid" value="${user.id}"/>
								<input type="hidden" id="borrow_view_rkbmid"  value="${user.bmdm}"/>
								<span class="input-group-btn">
									<button onclick="javascript:borrow_view.selectUser();" class="btn btn-primary">
										<i class="icon-search" ></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">入库日期</div>
								<div class="font-size-9">InStock date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" name="borrow_view_rksj" id="borrow_view_rksj" data-date-format="yyyy-mm-dd" readonly />
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">备注</div>
								<div class="font-size-9">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
								<textarea class="form-control" id="borrow_view_bz" name="borrow_view_bz" maxlength="300" ></textarea>
							</div>
						</div>
					</div>	
				</div>
				<!-- 航材列表 start -->
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
					<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-16">入库列表</div>
							<div class="font-size-9 ">InStock list</div>
						</div>	
					 	<div class="pull-left ">
							<button id="borrow_view_add" style="display:none" class="btn btn-primary " onclick="borrow_view.add();">
								<i class="icon-plus cursor-pointer" ></i>
							</button>
			         	</div>
					</div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x:auto;">
					<table class="table table-thin table-bordered table-hover table-set" style="min-width:1600px;">
						<thead>
							<tr>
								<th class="colwidth-5" id="borrow_view_column_operate">
									<button class="line6 " onclick="borrow_view.add();">
										<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
									</button>
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
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">管理级别</div>
									<div class="font-size-9 line-height-18">Management level</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">仓库</div>
									<div class="font-size-9 line-height-18">Stock</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">库位</div>
									<div class="font-size-9 line-height-18">Storage location</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">序列号/批次号</div>
									<div class="font-size-9 line-height-18">S/N</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">库存成本</div>
									<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
								</th>
								<th  class="colwidth-7">
									<div class="font-size-12 line-height-18">入库数量</div>
									<div class="font-size-9 line-height-18">Num</div>
								</th>
							</tr>
						</thead>
						<tbody id="borrow_view_list">
						</tbody>
					</table>
				</div>
				<div class="clearfix"></div>
				<div class="text-right margin-top-10 margin-bottom-0">
	                <button id="borrow_view_save" style="display:none" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:borrow_view.save()">
	                   	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					<button id="borrow_view_submit" style="display:none" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:borrow_view.submit()">
	                   	<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
	              	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:borrow_view.back();">
	              			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
	           	</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
		<%@ include file="/WEB-INF/views/open_win/contract_cost.jsp"%>
		<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/instock/handwork_borrow_instock_view.js"></script>
</body>
</html>