<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>收货</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
	<script type="text/javascript">
	var type = '${param.type}';
		$(document).ready(function(){
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
					}else{
						$('#aircraftinfoMainSearch').trigger('click'); //调用主列表页查询
					}
				}
			});
		});
	</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0 padding-top-0" id='demand_apply_body'>
			<input type="hidden" id="id" value="${param.id}"/>
			<input type="hidden" id="type" value="${param.type}"/>
			<input type="hidden" id="zt"/>
			<div class="col-lg-12 col-sm-12 col-xs-12 col-md-12 padding-left-0 padding-right-0">
			<div style='padding-top:10px;padding-left:0px;padding-right:0px;' id='scrollDiv'>
 				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">收货单号</div>
						<div class="font-size-9">Receipt No.</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<div class="input-group col-xs-12">
							<input id="shdh" class="form-control readonly-style" ondblclick="receipt.chooseReceipt()" readonly="readonly" type="text">
							<span id="shdh_btn" class="input-group-addon btn btn-default" onclick="receipt.chooseReceipt()" style="display: table-cell;">
							 	<i class="icon-search"></i>
							</span>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">收货类型</div>
						<div class="font-size-9">Type</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<select id="shlx" class='form-control' onchange="receipt.switchShlx()">
							<option value="10" selected="selected">采购</option>
							<option value="20">修理</option>
							<option value="31">租进</option>
							<option value="32">租出</option>
							<option value="40">交换</option>
							<option value="60">退料</option>
							<option value="90">其他</option>
						</select>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">收货日期</div>
						<div class="font-size-9">Date</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control date-picker" id="shrq" name="" data-date-format="yyyy-mm-dd" type="text"/>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">收货人</div>
						<div class="font-size-9">Consignee</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control" id="shr" type="text" disabled="disabled"/>
						<input id="shrid" type="hidden" />
						<input id="shbmid" type="hidden" />
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">来源单据</div>
						<div class="font-size-9">Source Documents</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input id="lyid" type="hidden" />
						<div class="input-group col-xs-12">
							<input id="lybh" class="form-control readonly-style" readonly="readonly" ondblclick="receipt.chooseSourceDoc()" type="text">
							<span id="lybh_btn" class="input-group-addon btn btn-default notView" onclick="receipt.chooseSourceDoc()" style="display: table-cell;">
							 	<i class="icon-search"></i>
							</span>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">来源单位</div>
						<div class="font-size-9">Source Unit</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control" id="lydw" maxlength="300" type="text" />
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group notView">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">收货仓库</div>
						<div class="font-size-9">Receipt Store</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<select id="shck" class='form-control' onchange="receipt.switchShck()">
						</select>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">备注</div>
						<div class="font-size-9">Remark</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<textarea style="height: 75px;" class="form-control" id="bz" maxlength="300" ></textarea>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">收货明细</div>
						<div class="font-size-9">Detail</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0  table-set" style="overflow-x: auto;">
					    <table id="receipt_table" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
								<th class='colwidth-3 notView'>
									<button class="line6 line6-btn" onclick="receipt.receiptOpen()" type="button">
										<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
								    </button>
								</th>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >物料类别</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class='colwidth-10' onclick="" name="">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">PN</div>
								</th>
								<th class='colwidth-20' onclick="" name="">
										<div class="font-size-12 line-height-18">名称/描述</div>
										<div class="font-size-9 line-height-18">Name/Desc</div>
								</th>
								<th class='colwidth-11' onclick="" name="" >
									<div class="font-size-12 line-height-18">批次号</div>
									<div class="font-size-9 line-height-18">Batch No.</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">SN</div>
								</th>
								<th class='colwidth-7' onclick="" name="" >
									<div class="font-size-12 line-height-18">收货数量</div>
									<div class="font-size-9 line-height-18">QTY</div>
								</th>
								<th class='colwidth-4' onclick="" name="" >
										<div class="font-size-12 line-height-18">质检</div>
										<div class="font-size-9 line-height-18">Testing</div>
								</th>
								<th class='colwidth-18' onclick="" name="" >
									<div class="font-size-12 line-height-18">仓库库位</div>
									<div class="font-size-9 line-height-18">Location</div>
								</th>
								<th class='colwidth-11' onclick="" name="" >
									<div class="font-size-12 line-height-18">产权</div>
									<div class="font-size-9 line-height-18">Right</div>
									<button onclick="receipt.batchSetPropertyRight()" type="button" style="position:absolute; right: 4px;top:8px;" class="line6 line6-btn notView">
										<i class="icon-edit cursor-pointer color-blue cursor-pointer"></i>
								    </button>
								</th>
								
							</tr> 
						</thead>
						<tbody id="receipt_list">
							<tr class="no-data"><td class="text-center" colspan="10" title="暂无数据 No data.">暂无数据 No data.</td></tr>
						</tbody>
					</table>
					</div>
					</div>
				</div>
				<div class='clearfix'></div>
				
				<div class='clearfix'></div>
			</div>
		</div>
		<div class="panel-footer" >
			<div class="col-xs-12 padding-left-0 padding-right-0" >
				<div class="input-group">
					<span class="input-group-addon modalfootertip" >
			        	<i class='glyphicon glyphicon-info-sign alert-info' style="display: none;"></i><p class="alert-info-message"></p>
					</span>
			        <span class="input-group-addon modalfooterbtn">
			        	<button id="save_btn" type="button" onclick="receipt.saveData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
				        <button id="submit_btn" type="button" onclick="receipt.submitData()" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">提交</div>
							<div class="font-size-9">Submit</div>
						</button>
						<button id="delete_btn" type="button" onclick="receipt.deleteData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
							<div class="font-size-12">删除</div>
							<div class="font-size-9">Delete</div>
						</button>
			            <button id="revoke_btn" type="button" onclick="receipt.revokeData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
							<div class="font-size-12">撤销</div>
							<div class="font-size-9">Revoke</div>
						</button>
			        </span>
		        </div>
			</div>
			<div class='clearfix'></div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/material2/outin/receipt/receipt_add.jsp"%>
<%@ include file="/WEB-INF/views/material2/outin/receipt/shelves_open.jsp"%>
<%@ include file="/WEB-INF/views/material2/outin/receipt/position_open.jsp"%>
<%@ include file="/WEB-INF/views/material2/outin/receipt/shelves_view.jsp"%>
<%@ include file="/WEB-INF/views/material2/outin/receipt/contract_list.jsp"%>
<%@ include file="/WEB-INF/views/material2/outin/receipt/receipt_list.jsp"%>
<%@ include file="/WEB-INF/views/common/produce/material_cq.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/receipt/receipt_main.js"></script>
</body>
</html>