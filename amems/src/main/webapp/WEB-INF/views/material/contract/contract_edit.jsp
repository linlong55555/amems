<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改合同</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="dprtcode" value="${contract.dprtcode}" />
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
								<input type="text" class="form-control" id="htlsh" name="htlsh"  value="${erayFns:escapeStr(contract.htlsh)}" readonly />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>合同编号</div>
								<div class="font-size-9 line-height-18">Contract No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" id="hth" name="hth" maxlength="20"  value="${erayFns:escapeStr(contract.hth)}" />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>紧急程度</div>
								<div class="font-size-9 line-height-18">Emergency</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='jjcd' name="jjcd" >
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
							    <select class='form-control selectpicker' data-live-search="true" 
									data-container="body" data-size="10" id="gys" onchange = "changePrice()" >
								</select>
							</div>
						</div>
							
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>合同签订日期</div>
								<div class="font-size-9 line-height-18">Date of signing</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control date-picker" id="htqdrq" name="htqdrq" value="<fmt:formatDate value='${contract.htqdrq}' pattern='yyyy-MM-dd' />" data-date-format="yyyy-mm-dd" type="text" />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">附加费用</div>
								<div class="font-size-9 line-height-18">Surcharge</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="fjfy" name="fjfy" class="form-control " value="${contract.fjfy}"  placeholder='' onkeyup='clearNoNumTwo(this)' maxlength="10" />
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">合同费用</div>
								<div class="font-size-9 line-height-18">Contract cost</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="htfy" name="htfy" class="form-control " value="${contract.htfy}" readonly />
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
								<textarea class="form-control" id="bz" name="bz" maxlength="300" >${erayFns:escapeStr(contract.bz)}</textarea>
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
				
            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
						<div style="overflow-x: auto;">
							<!-- start:table -->
							<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width: 1700px;">
								<thead id="contractHead">
							   		<tr>
										<th class="colwidth-5">
											<button class="line6 " onclick="openMaterialWinAdd()">
												<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
											</button>
										</th>
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
											<div class="font-size-9 notwrap">P/N</div>
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
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">序列号</div>
											<div class="font-size-9 notwrap">S/N</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 notwrap">提订数量</div>
											<div class="font-size-9 notwrap">Num</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 notwrap">合同数量</div>
											<div class="font-size-9 notwrap">Num</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 notwrap">单价(人民币：元)</div>
											<div class="font-size-9 notwrap">Price(RMB:YUAN)</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 notwrap">材料费(人民币：元)</div>
											<div class="font-size-9 notwrap">Material cost(RMB:YUAN)</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 notwrap">工时费(人民币：元)</div>
											<div class="font-size-9 notwrap">Time cost(RMB:YUAN)</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 notwrap">其它费(人民币：元)</div>
											<div class="font-size-9 notwrap">Other cost(RMB:YUAN)</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 notwrap">总价(人民币：元)</div>
											<div class="font-size-9 notwrap">Total(RMB:YUAN)</div>
										</th>
										<th class="colwidth-9">
											<div class="font-size-12 notwrap">预计到货日期</div>
											<div class="font-size-9 notwrap">Expected arrival</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 notwrap">申请人</div>
											<div class="font-size-9 notwrap">Applicant</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 notwrap">申请时间</div>
											<div class="font-size-9 notwrap">Application Date</div>
										</th>
										<th class="colwidth-9">
											<div class="font-size-12 notwrap">要求期限</div>
											<div class="font-size-9 notwrap">Requested date</div>
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
				
				<%@ include file="/WEB-INF/views/common/message/message_list_edit.jsp"%><!-- 加载留言信息 -->
				
				<div class="clearfix"></div>
				
				<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit.jsp"%><!-- 加载附件信息 -->
				<div class="clearfix"></div>
				
				<div class="text-right margin-top-10  margin-bottom-10">
	                <button class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode="aerialmaterial:contract:main:05" onclick="javascript:save()">
	                   	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					<button class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode="aerialmaterial:contract:main:06" onclick="javascript:submit()">
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
		
<script type="text/javascript" src="${ctx}/static/js/thjw/material/contract/contract_edit.js"></script>
	<%@ include file="/WEB-INF/views/open_win/materialContractMulti.jsp"%><!-------合同航材对话框 -------->
</body>
</html>