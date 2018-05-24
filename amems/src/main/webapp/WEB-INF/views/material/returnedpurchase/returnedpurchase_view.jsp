<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" value="${returnedPurchase.id}" id="id">
	<input type="hidden" value="${returnedPurchase.dprtcode}" id="dprtcode" />
	<div class="page-content">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>

			<div class="panel-body">
				<form id="form">
					<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">基本信息</div>
						<div class="font-size-9 ">Basic Info</div>
					</div>
				
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
						
							<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0  ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">退货单号</div>
								<div class="font-size-9">Returned No.</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
									<input id="fhdw" class="form-control" type="text"   value="${erayFns:escapeStr(returnedPurchase.thdh)}"  disabled="disabled"/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">退货人</div>
								<div class="font-size-9 line-height-18">Returnee</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" value="${erayFns:escapeStr(returnedPurchase.thr.username)}" name="username" id="username" readonly />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">退货日期</div>
								<div class="font-size-9 line-height-18">Returned Date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="date-picker form-control" id="thrq" name="thrq" value="${erayFns:escapeStr(returnedPurchase.thrq)}" data-date-format="yyyy-mm-dd" type="text" disabled="disabled"/>
							</div>
						</div>
				
						<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0  ">
						<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">发货单位</div>
							<div class="font-size-9">Ship Dprt</div>
						</label>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
								<input id="fhdw" class="form-control" type="text"  disabled="disabled" value="${erayFns:escapeStr(returnedPurchase.fhdw)}" />
						</div>
					</div>
						<div class="clearfix"></div>
						
								<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0  ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">出库单号</div>
								<div class="font-size-9">Delivery No.</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
									<input id="fhdw" class="form-control" type="text"   value="${returnedPurchase.outstock.ckdh}"  disabled="disabled"/>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0  ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">状态</div>
								<div class="font-size-9">State</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
									<input id="state" class="form-control" type="text"   value="${erayFns:escapeStr(returnedPurchase.zt)}"  disabled="disabled"/>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0  ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">制单人</div>
								<div class="font-size-9">Creator</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
							
							
									<input id="fhdw" class="form-control" type="text"   value="${erayFns:escapeStr(returnedPurchase.zdrid)}"  disabled="disabled"/>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0  ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">制单时间</div>
								<div class="font-size-9">Create Time</div>
							</label>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0 form-group">
								<input class="form-control" type="text" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${returnedPurchase.zdsj}"/>" data-date-format="yyyy-MM-dd HH:mm:ss"   readonly />
							</div>
						</div>
						
					 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0  form-group">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="bz" name="bz" disabled="disabled" >${erayFns:escapeStr(returnedPurchase.bz)}</textarea>
							</div>
						</div>
					</div>
				</form>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					<div class="clearfix"></div>
					
					<div class="panel-heading padding-left-16 padding-top-3  col-xs-12  " style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-18">退货航材</div>
							<div class="font-size-9 ">Return Material</div>
						</div>	
					</div>
					
	            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">

						<!-- start:table -->
						<div class="margin-top-10 ">
						<div class="overflow-auto">
							<table class="table table-bordered table-striped table-hover table-set table-thin" style="min-width:1500px">
								<thead>
							   		<tr>
										<th class="colwidth-3">
											<div class="font-size-12 notwrap">序号</div>
											<div class="font-size-9 notwrap">NO.</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">英文名称</div>
											<div class="font-size-9 notwrap">F.Name</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">中文名称</div>
											<div class="font-size-9 notwrap">CH.Name</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 notwrap">件号</div>
											<div class="font-size-9 notwrap">P/N</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 notwrap">厂家件号</div>
											<div class="font-size-9 notwrap">MP/N</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 notwrap">航材类型</div>
											<div class="font-size-9 notwrap">Airmaterial type</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 notwrap">管理级别</div>
											<div class="font-size-9 notwrap">Level</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 notwrap">序列号</div>
											<div class="font-size-9 notwrap">S/N</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 notwrap">批次号</div>
											<div class="font-size-9 notwrap">B/N</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 notwrap">退货数量</div>
											<div class="font-size-9 notwrap">Returned Num</div>
										</th>
										<th class="colwidth-5">
											<div class="font-size-12 notwrap">单位</div>
											<div class="font-size-9 notwrap">Unit</div>
										</th >
							 		 </tr>
								</thead>
								<tbody id="reserveTable">
								
								</tbody>
							</table>
							</div>
						</div>
						<!-- end:table -->
			     		<div class="clearfix"></div>
					 </div> 
				</div>
				<div class="clearfix"></div>
				<div class="text-right margin-bottom-10">
               		<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:goreturn()" >
               			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
					
            	</div>
			</div>
		</div>

		<!-- 基本信息 End -->
</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/returnedpurchase/returnedpurchase_view.js"></script>
</body>
</html>