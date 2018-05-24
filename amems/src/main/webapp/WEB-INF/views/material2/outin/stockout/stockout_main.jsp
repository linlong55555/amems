<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>出库</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
	<script type="text/javascript">
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
<input type="hidden" id="id"  />
<input type="hidden" id="zt"  />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0 padding-top-0" id='stockout_body'>
		<form id="from1">
			<div class="col-lg-12 col-sm-12 col-xs-12 col-md-12 padding-left-0 padding-right-0">
			<div style='padding-top:10px;padding-left:0px;padding-right:0px;' id='scrollDiv'>
 				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">出库单号</div>
						<div class="font-size-9">Library No.</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<div class="input-group col-xs-12">
							<input id="shdhid" type="hidden" >
							<input id="shdh" placeholder='不填写则自动生成'  class="form-control "  type="text" >
							<span class=" input-group-btn"  >
								<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="stockout_body.outstock()" >
									<i class="icon-search cursor-pointer" ></i>
								</button>
							</span>
						</div>
					
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">出库类型</div>
						<div class="font-size-9">Type</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<select class='form-control noteditable' id='shlx' onchange="stockout_body.onchangeshlx()">
							<option value='70' selected="selected">发料</option>
						    <option value='20'>修理</option>
							<option value='31'>租进</option>
							<option value='32'>租出</option>
							<option value='40'>交换</option>
							<option value='50'>出售</option>
							<option value='10'>采购</option>
							<option value='90'>其他</option>
						</select>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>出库日期</div>
						<div class="font-size-9">Date</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control date-picker noteditable" id="shrq" name="shrq" type="text" name='date-picker'/>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">出库人</div>
						<div class="font-size-9">The library</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control" id="shr" type="text"  readonly="readonly"/>
					</div>
				</div>
				<div class='clearfix'></div>
				<div id="lyidDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">来源单据</div>
						<div class="font-size-9">Source</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<div class="input-group col-xs-12">
							<input id="lyid" type="hidden" >
							<input id="lybh" class="form-control readonly-style noteditable colse"  readonly="readonly" ondblclick="" type="text">
							<span class="required input-group-btn"  >
								<button type="button" class="btn btn-default form-control" id="lybutton" style='height:34px;' data-toggle="modal" >
									<i class="icon-search cursor-pointer" ></i>
								</button>
							</span>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">接收单位</div>
						<div class="font-size-9">Receiving unit</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control noteditable" id="lydw"  type="text" maxlength='300'/>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">飞机注册号</div>
						<div class="font-size-9">A/C Reg</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<select class='form-control noteditable' id="fjzch">
						
						</select>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>出库仓库</div>
						<div class="font-size-9">Storehouse</div>
					</span>
					<div id="ckDiv" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<select class='form-control noteditable' id="ckid" name="ckid" onchange="stockout_body.onchangeck()">
						</select>
						<input  class='form-control' id="ckids"  type="text" style="display:none;" readonly="readonly">
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">备注</div>
						<div class="font-size-9">Remark</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<textarea style="height: 75px;" class="form-control noteditable" id="bz"  maxlength="300" ></textarea>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">状态</div>
						<div class="font-size-9">Status</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control" id="ztName" type="text"  readonly="readonly"/>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">出库明细</div>
						<div class="font-size-9">Storehouse</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0  table-set" style="overflow-x: auto;">
					    <table id="receipt_table" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
								<th  class='colwidth-3 required'>
									<button class="line6 line6-btn" onclick="stockout_body.stockOutOpen()" type="button">
										<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
								    </button>
								</th>
								<th class='colwidth-8'>
									<div class="font-size-12 line-height-18" >物料类别</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class='colwidth-9' onclick="" name="">
									<div class="font-size-12 line-height-18">部件</div>
									<div class="font-size-9 line-height-18">PN</div>
								</th>
								<th class='colwidth-10' onclick="" name="">
										<div class="font-size-12 line-height-18">可用数</div>
										<div class="font-size-9 line-height-18">QTY</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">出库数量</div>
									<div class="font-size-9 line-height-18">QTY</div>
								</th>
								<th class='colwidth-5' onclick="" name="" >
									<div class="font-size-12 line-height-18">单位</div>
									<div class="font-size-9 line-height-18">Unit</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
										<div class="font-size-12 line-height-18">仓库</div>
										<div class="font-size-9 line-height-18">Warehouse</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">库位</div>
									<div class="font-size-9 line-height-18">Library</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">SN</div>
								</th>
								<th class='colwidth-20' onclick="" name="" >
									<div class="font-size-12 line-height-18">批次号</div>
									<div class="font-size-9 line-height-18">Bacth NO.</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">产权</div>
									<div class="font-size-9 line-height-18">Property</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Specifications</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">成本</div>
									<div class="font-size-9 line-height-18">Cost</div>
								</th>
<!-- 								<th class='colwidth-9' onclick="" name="" > -->
<!-- 									<div class="font-size-12 line-height-18">价值</div> -->
<!-- 									<div class="font-size-9 line-height-18">Cost</div> -->
<!-- 								</th> -->
								
							</tr> 
						</thead>
						<tbody id="stockout_main_list">
							<tr>
								
								
							</tr>
							
						</tbody>
					</table>
					</div>
					</div>
				</div>
				<div class='clearfix'></div>
				
				<div class='clearfix'></div>
			</div>
			</form>
		</div>
		<div class="panel-footer" >
			<div class="col-xs-12 padding-left-0 padding-right-0" >
				<div class="input-group">
					<span class="input-group-addon modalfootertip" >
			        	<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
			        <span class="input-group-addon modalfooterbtn">
			            <button type="button" id="cexiao" onclick="stockout_main.revoke()"  class="btn btn-primary padding-top-1 padding-bottom-1" >
							<div class="font-size-12">撤销</div>
							<div class="font-size-9">Revoke</div>
						</button>
						<button type="button" id="shanchu" onclick="stockout_main.deleteoutin()" class="btn btn-primary padding-top-1 padding-bottom-1" >
							<div class="font-size-12">删除</div>
							<div class="font-size-9">Delete</div>
						</button>
				        <button type="button" id="baocuns" onclick="stockout_main.save()" class="btn btn-primary padding-top-1 padding-bottom-1" >
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
				        <button type="button" id="tijiao" onclick="stockout_main.submit()"  class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">提交</div>
							<div class="font-size-9">Submit</div>
						</button>
			        </span>
		        </div>
			</div>
			<div class='clearfix'></div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/stockout/stockout_main.js"></script><!--初始js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/stockout/stockout_open.js"></script><!--公用组件js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/stockout/stockout_update.js"></script><!--修改初始化js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/stockout/stockout_initdata_main.js"></script><!--新增 初始化js  -->
<%@ include file="/WEB-INF/views/material2/outin/stockout/stockout_open.jsp"%><!--选择出库物料  -->
<%@ include file="/WEB-INF/views/material2/outin/stockout/select_contract.jsp"%><!--合同选择弹窗-->
<%@ include file="/WEB-INF/views/material2/outin/stockout/select_demand.jsp"%><!--需求选择弹窗-->
<%@ include file="/WEB-INF/views/material2/outin/stockout/chosen_stock_.jsp"%><!--已选择库存-->
<%@ include file="/WEB-INF/views/material2/outin/stockout/select_outStockOrder.jsp"%><!--出库单选择框-->
</body>
</html>