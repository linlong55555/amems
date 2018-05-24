<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var pageParam = '${param.pageParam}';
</script>
</head>
<body>

	<div class="page-content">
		<form id="receiptForm">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>

			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom:10px;">
					
					<input type="hidden" id="receiptId" value="${receipt.id}">
					<input type="hidden" id="dprtcode" value="${receipt.dprtcode==null?user.jgdm:receipt.dprtcode}">
					<input type="hidden" id="type" value="${type}">
					
					<div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc; margin-top: 0px;cursor:pointer">
						<div class="font-size-16 line-height-18">收货单信息</div>
						<div class="font-size-9 ">ReceiptSheet Infomation</div>
						<div class="clearfix"></div>
				   </div>
				   <div class="clearfix"></div>
					<div class="col-lg-12 padding-left-0 padding-right-0">
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">收货类型</div>
								<div class="font-size-9 line-height-18">Receipt Type</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='shlx' name='shlx' onchange="switchType()">
									<option value="1">采购</option>
									<option value="2">送修</option>
									<option value="3">借入</option>
									<option value="4">借出归还</option>
									<option value="99">其它</option>
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group" id="contract_div">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">合同</div>
								<div class="font-size-9 line-height-18">contract</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" id="ht_div">
								<div class="input-group">
									<input class="form-control" id="hth" disabled="disabled" type="text">
									<input class="form-control" id="htid" name="htid" type="hidden">
									<span class="input-group-btn">
										<button class="btn btn-primary form-control" type="button" onclick="chooseContract()">
											<i class="icon-search"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
						
						<div id="jddx_div" style="display: none;">
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">借调对象类型</div>
									<div class="font-size-9 line-height-18">S/O Type</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="jddxlx" name="jddxlx" class="form-control" onchange="changeJddxlx()">
										<option value="1">飞行队 Flying team</option>
										<option value="2">航空公司 Airline.com</option>
										<option value="0">其它 Other</option>
								   </select> 
								</div>
							</div>
							
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">借调对象</div>
									<div class="font-size-9 line-height-18">Seconded Obj</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="jddx" name="jddx" class="form-control" onchange="changeJddx()">
								   		<option value="">显示所有 All</option>
								   </select> 
								</div>
							</div>
							
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">借调负责人</div>
									<div class="font-size-9 line-height-18">Seconded Obj</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" id="jdfzr" maxlength="10">
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-right-0 form-group" id="borrowApply_div" style="display: none;">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">借入申请单</div>
								<div class="font-size-9 line-height-18">Apply No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" id="jrsq_div">
								<div class="input-group">
									<input class="form-control" id="jrsqbh" disabled="disabled" type="text">
									<input class="form-control" id="jrsqid" name="jrsqid" type="hidden">
									<span class="input-group-btn">
										<button class="btn btn-primary form-control" type="button" onclick="chooseBorrowApply()">
											<i class="icon-search"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">收货人</div>
								<div class="font-size-9 line-height-18">Receiver</div>
							</label>
							 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" id="shr_div">
								<div class="input-group">
									<input class="form-control" id="shr" disabled="disabled" type="text">
									<input class="form-control" id="shrid" name="shrid" value="" type="hidden">
									<input class="form-control" id="shbmid" value="" type="hidden">
									<span class="input-group-btn">
										<button class="btn btn-primary form-control" type="button" onclick="chooseShr()">
											<i class="icon-search"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">收货日期</div>
								<div class="font-size-9 line-height-18">Receipt Date</div> 
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" name="shrq" id="shrq" data-date-format="yyyy-mm-dd"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-right-0 form-group" id="fhdw_div">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">发货单位</div>
								<div class="font-size-9 line-height-18">Delivery</div> 
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control"  maxlength="160" name="fhdw" id="fhdw" disabled="disabled"/>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-12 padding-left-0 padding-right-0">
						<div class="col-sm-12 col-xs-12 padding-right-0 form-group">
							<label class="col-xs-1 text-right padding-left-0 padding-right-0" style="width: 8%">
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-xs-11 padding-left-8 padding-right-0" style="width: 92%">
								<textarea rows="3" name="bz" class="form-control" id="bz" maxlength="300"></textarea>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-12 padding-left-0 padding-right-0">
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-right-0 form-group" id="shdh_div" style="display: none;">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">收货单号</div>
								<div class="font-size-9 line-height-18">Receipt No.</div>
							</label>
							 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" class="form-control" id="shdh" disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-right-0 form-group" id="zt_div" style="display: none;">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">状态</div>
								<div class="font-size-9 line-height-18">State</div>
							</label>
							 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" class="form-control" id="zt" disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-right-0 form-group" id="zdr_div" style="display: none;">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单人</div>
								<div class="font-size-9 line-height-18">Creator</div>
							</label>
							 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" class="form-control" id="zdr" disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-right-0 form-group" id="zdsj_div" style="display: none;">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" class="form-control" id="zdsj" disabled="disabled"/>
							</div>
						</div>
						
						<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
					<div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc; margin-top: 10px;cursor:pointer">
						<div class="col-lg-4 padding-left-0">
							<div class="col-lg-4  padding-left-0 padding-right-0">
								<div class="font-size-16 line-height-18">收货单详细</div>
								<div class="font-size-9 ">ReceiptSheet Detail</div>
							</div>
							<div class="col-lg-8 padding-bottom-5 padding-left-0 padding-right-0">
								<button type="button" id="onekey_btn" style="display: none;" class="btn btn-primary pull-left padding-top-1 padding-bottom-1 notView" onclick="showAutoFillStoreModal()">
		                     		<div class="font-size-12">一键填写仓库</div>
									<div class="font-size-9">Onekey Fill</div>
								</button>
							</div>
						</div>
						<div class="clearfix"></div>
				   </div>
				   <div class="clearfix"></div>
					<small class="text-muted notView">多个序列号请以“,”分割</small>
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
						<table id="receipt_detail_table" class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
									<th class="colwidth-5 notView" style="vertical-align: middle;">
										<button class="line6" onclick="chooseComponent()" style="padding:0px 6px;">
									    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
								        </button>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">管理级别</div>
										<div class="font-size-9 line-height-18">Level</div>
									</th>
									<th class="colwidth-15" style="display: none" id="shjh_th">
										<div class="font-size-12 line-height-18">收货件号</div>
										<div class="font-size-9 line-height-18">Receipt P/N</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">序列号/批次号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</th>
									<th class="colwidth-13">
										<div class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Store</div>
									</th>
									<th class="colwidth-13">
										<div class="font-size-12 line-height-18">库位</div>
										<div class="font-size-9 line-height-18">Storage Location</div>
									</th>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Quantity</div>
									</th>
									<th class="colwidth-7">
										<div class="font-size-12 line-height-18">是否质检</div>
										<div class="font-size-9 line-height-18">Inspection</div>
									</th>
								</tr>
							</thead>
							<tbody id="list">
								<tr class="non-choice"><td class="text-center" colspan="10">暂无数据 No data.</td></tr>
							</tbody>
						</table>
					</div>
							
					<div class="text-right">
						<button id="saveBtn" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="save()">
                     		<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
						
                     	<button id="submitBtn" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="takeEffect()">
                     		<div class="font-size-12">提交</div>
							<div class="font-size-9">Submit</div>
						</button>
						
                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:goToMainPage()">
                     		<div class="font-size-12">返回</div>
							<div class="font-size-9">Back</div>
						</button>
						
                     </div>
				</div>

					</div>
				</div>
				
				</form>
			</div>

	</div>
	
	<div class="modal fade" id="oneKeyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:400px;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
				  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">仓库</div>
							<div class="font-size-9 ">Store</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0 margin-top-10">
				         		<div class="col-lg-12 padding-left-0 padding-right-0">
				         			<div class="col-lg-11 col-sm-11 col-xs-11 padding-right-0">
										<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">仓库</div>
											<div class="font-size-9 line-height-18">Store</div>
										</label>
										<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
											<select class="form-control" id="ck" onchange="changeStoreByOnekey()">
											</select>
										</div>
									</div>
									
				         		</div>
				         		
				         		<div class="col-lg-12 padding-left-0 padding-right-0 padding-top-10">
				         			<div class="col-lg-11 col-sm-11 col-xs-11 padding-right-0">
										<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">库位</div>
											<div class="font-size-9 line-height-18">Storage</div>
										</label>
										<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
											<select class="form-control" id="kw">
											</select>
										</div>
									</div>
				         		</div>
				         		
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10 padding-right-0 col-lg-11">
									<button type="button" onclick="autoFillStore()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
									
				                </div>
				     			<div class="clearfix"></div>
							</div> 
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
	<%@ include file="/WEB-INF/views/material/receipt/contract.jsp"%>
	<%@ include file="/WEB-INF/views/material/receipt/borrowApply.jsp"%>
	<%@ include file="/WEB-INF/views/material/receipt/lendReturn.jsp"%>
	<%@ include file="/WEB-INF/views/material/receipt/receipt_component.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%>
	<script src="${ctx}/static/js/thjw/material/receipt/receipt_detail.js"></script>
</body>
</html>