<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>合同收货</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" id="dprtId" value="${contract.dprtcode}" />
	<div class="page-content ">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>	
			<div class="panel-body padding-bottom-0">
				<form id="form">
					<input type="hidden" class="form-control" id="id" value="${contract.id}" />
					<input type="hidden" class="form-control" id="gysid" value="${contract.gysid}" />
					<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">基本信息</div>
						<div class="font-size-9 ">Basic Info</div>
					</div>
				
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>合同类型</div>
								<div class="font-size-9 line-height-18">Contract Type</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='htlx' name="htlx" onchange="changeContractType()" disabled=disabled >
									<c:forEach items="${contractTypeEnum}" var="item">
										<option value="${item.id}" <c:if test="${contract.htlx == item.id }">selected=true</c:if> >${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">合同号</div>
								<div class="font-size-9 line-height-18">Contract No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" id="htlsh"  value="${erayFns:escapeStr(contract.htlsh)}" readonly />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>合同编号</div>
								<div class="font-size-9 line-height-18">Contract No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" id="hth"  value="${erayFns:escapeStr(contract.hth)}" disabled=disabled />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>紧急程度</div>
								<div class="font-size-9 line-height-18">Emergency</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='jjcd' name="jjcd" disabled=disabled>
									<c:forEach items="${urgencyEnum}" var="item">
										<option value="${item.id}" <c:if test="${contract.jjcd == item.id }">selected=true</c:if> >${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>供应商</div>
								<div class="font-size-9 line-height-18">Supplier</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='gys' onchange = "changePrice()" disabled=disabled>
							    </select>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>合同签订日期</div>
								<div class="font-size-9 line-height-18">Date of signing</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control date-picker" id="htqdrq" value="<fmt:formatDate value='${contract.htqdrq}' pattern='yyyy-MM-dd' />" data-date-format="yyyy-mm-dd" type="text" disabled=disabled/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">附加费用</div>
								<div class="font-size-9 line-height-18">Surcharge</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="fjfy" name="fjfy" class="form-control " value="${contract.fjfy}"  disabled=disabled/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">合同费用</div>
								<div class="font-size-9 line-height-18">Contract cost</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="htfy" name="htfy" class="form-control " value="${contract.htfy}"  readonly />
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">合同状态</div>
								<div class="font-size-9 line-height-18">State</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='zt' name="zt" disabled="disabled">
									<c:forEach items="${contractStatusEnum}" var="item">
									  <option value="${item.id}" <c:if test="${contract.zt == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
							    </select>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单人</div>
								<div class="font-size-9 line-height-18">Creator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zdr" name="zdr" value="${erayFns:escapeStr(contract.zdr.displayName)}" class="form-control " readonly/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" id="zdsj" name="zdsj" value="<fmt:formatDate value='${contract.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" readonly type="text" />
							</div>
						</div>
						
						<div class="clearfix"></div>
						
					 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0  form-group">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="bz" name="bz" maxlength="300" disabled=disabled>${erayFns:escapeStr(contract.bz)}</textarea>
							</div>
						</div>
					</div>
				</form>
				
				<div class="clearfix"></div>
				
				<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
					<div class=" pull-left margin-right-10" >
						<div class="font-size-16 line-height-18">航材列表</div>
						<div class="font-size-9 ">Aircraft list</div>
					</div>	
				</div>
				
				<div class="panel-body padding-top-0 padding-bottom-0">
	            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
							<div style="overflow-x: auto;">
								<!-- start:table -->
								<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width: 1450px;">
									<thead id="contractHead">
								   		<tr>
											<th class="colwidth-3">
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">提订单号</div>
												<div class="font-size-9 notwrap">Order No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">送修单号</div>
												<div class="font-size-9 notwrap">Repair No.</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">件号</div>
												<div class="font-size-9 notwrap">S/N</div>
											</th>
											<th class="colwidth-25">
												<div class="font-size-12 notwrap">英文名称</div>
												<div class="font-size-9 notwrap">F.Name</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 notwrap">中文名称</div>
												<div class="font-size-9 notwrap">CH.Name</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">厂家件号</div>
												<div class="font-size-9 notwrap">MP/N</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">航材类型</div>
												<div class="font-size-9 notwrap">Airmaterial type</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 notwrap">序列号</div>
												<div class="font-size-9 notwrap">S/N</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">数量</div>
												<div class="font-size-9 notwrap">Num</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">到货数</div>
												<div class="font-size-9 notwrap">Arrival Num</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">入库数</div>
												<div class="font-size-9 notwrap">InStore Num</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">未到货数</div>
												<div class="font-size-9 notwrap">Not arriving Num</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">本次到货数</div>
												<div class="font-size-9 notwrap">This arriving Num</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">是否到货</div>
												<div class="font-size-9 notwrap">Is arriving</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="materialTable">
									
									</tbody>
								</table>
								<!-- end:table -->
							</div>
						<div class="clearfix"></div>
					</div>
				</div>
				
				<div class="text-right margin-top-10  margin-bottom-10">
					<button class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode="aerialmaterial:contract:main:08" onclick="javascript:submit()">
	                   	<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
	              	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()">
	              		<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
	           	</div>
	           	<div class="clearfix"></div>
           	</div>
		</div>
	</div>

		<!-- 基本信息 End -->
		
<script type="text/javascript" src="${ctx}/static/js/thjw/material/contract/contract_comeGood.js"></script>
</body>
</html>