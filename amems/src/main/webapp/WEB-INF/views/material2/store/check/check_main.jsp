<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>盘点</title>
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
						if($("#scan_search", $("#check_main_body")).is(":focus")){
							check_main.scanSearch();   
						}else{
							$('#aircraftinfoMainSearch').trigger('click'); //调用主列表页查询
						}
					}
				}
			});
		});
	</script>	
</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div id="check_main" class="page-content">
	<input id="type" type="hidden" value="${type}" />
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class=" panel-body padding-bottom-0 padding-left-0 padding-right-0" id="check_main_body">
		<div class=''>
		<!-- 搜索框start -->
			<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
				<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='条码扫描区'  class="form-control" style="margin-top: 2px;" id="scan_search"  >
                    <div class="input-group-addon btn-searchnew" >
                    	<button onclick="check_main.openStockWin()" id="" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" style='margin-right:0px !important;'>
						<div class="font-size-12">选择库存</div>
						<div class="font-size-9">Stock</div>
                  		</button>
                    </div>
                    <div class="input-group-addon btn-searchnew-more">
	                    <button id="" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 checkPermission" onclick="check_main.openWinAdd()">
						<div class="font-size-12">新建库存</div>
						<div class="font-size-9">New stock</div>
                  		</button>
                	</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div style='padding-left:15px;padding-right:15px;'>
			<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span style="width:70px;padding-right:8px;" class="pull-left text-right">
					<div class="font-size-12  margin-topnew-2">部件号</div>
					<div class="font-size-9 ">PN</div>
				</span>
				<div style="margin-left:70px;">
					<div class=" input-group col-xs-12">
				    <input id="bjh_m" class="form-control" type="text" readonly>
					<span id="kcxq_btn" class="input-group-addon" style="padding-left:5px;background:none;border:0px;">
                    	<a href='javascript:;' onclick="check_main.openStorageDetailWin()" >库存详细信息</a>
                    </span>
					</div>
				</div>
		    </div>
		    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span style="width:70px;padding-right:8px;" class="pull-left text-right">
					<div class="font-size-12  margin-topnew-2">名称</div>
					<div class="font-size-9 ">Name</div>
				</span>
				<div style="margin-left:70px;">
					<input id="zywms_m" type="text" class="form-control" readonly/>
				</div>
		    </div>
		    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span style="width:70px;padding-right:8px;" class="pull-left text-right">
					<div class="font-size-12  margin-topnew-2">批次号</div>
					<div class="font-size-9 ">Batch NO.</div>
				</span>
				<div style="margin-left:70px;">
					<input id="pch_m" type="text" class="form-control" readonly/>
				</div>
		    </div>
		     <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span style="width:70px;padding-right:8px;" class="pull-left text-right">
					<div class="font-size-12  margin-topnew-2">序列号</div>
					<div class="font-size-9 ">SN</div>
				</span>
				<div style="margin-left:70px;">
					<input id="sn_m" type="text" class="form-control" readonly/>
				</div>
		    </div>
		    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span style="width:70px;padding-right:8px;" class="pull-left text-right">
					<div class="font-size-12  margin-topnew-2">产权</div>
					<div class="font-size-9 ">Right</div>
				</span>
				<div style="margin-left:70px;">
					<input id="cqbh_m" type="text" class="form-control" readonly/>
				</div>
		    </div>
		    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span style="width:70px;padding-right:8px;" class="pull-left text-right">
					<div class="font-size-12  margin-topnew-2">位置</div>
					<div class="font-size-9 ">Position</div>
				</span>
				<div style="margin-left:70px;">
					<input id="wz_m" type="text" class="form-control" readonly/>
				</div>
		    </div>
		    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span style="width:70px;padding-right:8px;" class="pull-left text-right">
					<div class="font-size-12  margin-topnew-2">库存数量</div>
					<div class="font-size-9 ">QTY</div>
				</span>
				<div style="margin-left:70px;">
					<div class=" input-group col-xs-12">
				    <input id="kcsl_m" class="form-control" type="text" readonly>
					<span id="djsl_dw" class="input-group-addon" style="padding-left:5px;background:none;border:0px;">
                    	 
                    </span>
					</div>
				</div>
		    </div>
		    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span style="width:70px;padding-right:8px;" class="pull-left text-right">
					<div class="font-size-12  margin-topnew-2"><span class="color-red">*</span>实际数量</div>
					<div class="font-size-9 ">QTY</div>
				</span>
				<div style="margin-left:70px;">
					<div class=" input-group col-xs-12">
				    <input id="sjsl_m" class="form-control" type="text" onkeyup="check_main.changeNum2(this)">
					<span id="dw_btn" class="dw_m input-group-addon" style="padding-left:5px;background:none;border:0px;">
                    	单位
                    </span>
					</div>
				</div>
		    </div>
		    <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span style="width:70px;padding-right:8px;" class="pull-left text-right">
					<div class="font-size-12  margin-topnew-2">登记备注</div>
					<div class="font-size-9 ">Remark</div>
				</span>
				<div style="margin-left:70px;">
					<textarea id="djbz_m" rows="" class="form-control" style="height:55px;" maxlength="1300"></textarea>
				</div>
		    </div>
		</div>
		<div class='clearfix'></div>
		<div style='border-top:1px solid #d5d5d5;padding-top:8px;padding-left:15px;padding-right:15px;'>
		<p><b>请仔细阅读登记说明：</b></p>
		<p style="text-indent:2em;">1、如果只需要在原库存上修改库存数量时，请先确认库存记录。有两种方式选择库存记录：</p>
		<p style="text-indent:4em;">A 点击&nbsp;<a href='javascript:;' onclick="check_main.openStockWin()">选择库存</a>&nbsp;按钮；</p>
		<p style="text-indent:4em;">B 在点击“条码扫描区”文本框后，使用条码枪扫描“可用件标签”上的条码，可支持条形码或者二维条码。</p>
		<p style="text-indent:4em;">但如果无库存数据，需要增加库存时，请点击&nbsp;<a href='javascript:;'  onclick="check_main.openWinAdd()">新建库存</a>&nbsp;按钮。</p>
		<p style="text-indent:2em;">2、在仔细核对完库存信息后，请填写盘点的实际数量和登记备注。实际盘点数量可填写0（支持两位小数），但不可不填写或者填写负数或者不能小于冻结数据。</p>
		<p style="text-indent:2em;">3、确认无误后，请点击“确认”按钮，之后系统会自动根据实际数量更新库存数据，记录操作人和操作时间。</p>
		</div>
		</div>
		<div class="panel-footer" >
			<div class="col-xs-12 padding-left-0 padding-right-0" >
				<div class="input-group">
					<span class="input-group-addon modalfootertip" >
			        	<i class='glyphicon glyphicon-info-sign alert-info' style="display: none;"></i><p class="alert-info-message"></p>
					</span>
			        <span class="input-group-addon modalfooterbtn">
			        	<button id="" onclick="check_main.save();" type="button"  class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission"  >
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
			        </span>
		        </div>
			</div>
			<div class='clearfix'></div>
		</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/material2/store/check/check_open.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/store/check/check_main.js"></script>
	<%@ include file="/WEB-INF/views/material2/stockmaterial/inside/inside_certificate.jsp"%><!-----证书对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script><!-----证书使用 -------->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->	
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	<%@ include file="/WEB-INF/views/material2/store/check/stock_open.jsp"%><!--选择库存  -->
	<%@ include file="/WEB-INF/views/common/produce/material_cq.jsp" %> <!-- 产权弹出框 -->
	<%@ include file="/WEB-INF/views/open_win/inventory_distribution_details_view.jsp"%><!-------库存分布详情 -------->
	<%@ include file="/WEB-INF/views/material2/stockmaterial/inside/frozen_history.jsp"%>  <!--库存冻结履历查看弹框  -->	
</body>
</html>