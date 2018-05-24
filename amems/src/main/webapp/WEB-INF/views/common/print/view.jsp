<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script type="text/javascript">
	var pageParam = '${param.pageParam}';
	</script>
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode,"打印","Print");
});
</script>
</head>
	<body class="page-header-fixed">
	<div class="clearfix"></div>
	<input type="hidden" name="bulletinId" id="type" value='${type}' >
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body">
			<!--startprint1-->
				<div style="height:288px;width:415px;">
					<div class="col-lg-12 padding-top-10" >
						<div class="col-lg-12 padding-left-0 padding-right-0">
							<div id="logo" class="pull-left padding-left-0 padding-right-0" style='width:119px;margin-top:13px;'>
								<img alt="" src="${ctx }/${images_path }"  style="width:100%" >&nbsp;&nbsp;
							</div>
							<div class="padding-left-0 padding-right-0 pull-left text-center" style='width:120px;margin-top:13px;'>
								<p style="font-size:20px;margin-bottom:0px;">可用件标签</p>
								<p style="font-size:20px;">Usable</p>
							</div>
							<div class="padding-left-0 padding-right-0 pull-left"  id="ewm">
								<img  alt="" src="${ctx}/material/stock/material/getErWeiCode?id=${id}&width=130&height=130">
							</div>
							<div class="clearfix"></div>
						</div>
						<div class=" col-lg-12" style="border-bottom:1px solid #333; height:1px;padding-top:3px"></div>
						<div class="col-lg-12" style='margin-top:8px;'><label class='pull-left' style="font-size:12px;color:#000000;width:38px;font-weight:normal;margin-top:10px;">Desc</label>
						<div class='pull-left'><p style="font-size:12px;color:#49494b;font-weight:400;margin-bottom:0px;">${stock.hCMainData eq null?'':stock.hCMainData.ywms }</p>
						<p style="font-size:10px;margin-bottom:0px;">${stock.hCMainData eq null ?'':stock.hCMainData.zwms }</p>
						</div>
						<div class='clearfix'></div>
						</div>
						<div class="col-lg-12 padding-top-6" id="pr"><label style="font-size:12px;color:#000000;width:38px;font-weight:normal;font-family:arial;">PR</label><span  style="font-size:12px;color:#49494b;font-family:arial;">${stock.propertyRight eq null ?'':stock.propertyRight.cqbh}</span></div>
						<div class="col-lg-12 padding-top-6" id="pn"><label style="font-size:12px;color:#000000;width:38px;font-weight:normal;font-family:arial;">PN</label><span  style="font-size:12px;color:#49494b;font-family:arial;">${stock.bjh }</span></div>
						<div class="col-lg-12 padding-top-6" id="sn"><label style="font-size:12px;color:#000000;width:38px;font-weight:normal;font-family:arial;">SN</label><span  style="font-size:12px;color:#49494b;font-family:arial;">${stock.sn }</span></div>
						<div class="col-lg-12 padding-top-6" id="bn">
							<div style="width:180px" class="pull-left">
								<label style="font-size:12px;color:#000000;width:38px;font-weight:normal;font-family:arial;">BN</label><span style="font-size:12px;color:#49494b;font-family:arial;">${stock.pch }</span>
							</div>
							<div style="width:150px" class="pull-left">
								<label style="font-size:12px;color:#000000;width:38px;font-weight:normal;font-family:arial;">EXP</label><span style="font-size:12px;color:#49494b;font-family:arial;"><fmt:formatDate value='${stock.hjsm}' pattern='yyyy-MM-dd ' /></span>
							</div>
						</div>
						<div class="col-lg-12 padding-top-8 text-center" ><img id="txm"  alt="" src="${ctx}/material/stock/material/encodeBarCode?contents=${stock.uuiddm}"></div>
					</div>
				</div>
				<!--endprint1-->
				<div class="clearfix"></div>
				<div>																						
				<input type=button class="btn btn-primary padding-top-1 padding-bottom-1" id="printBtn" name='button_export' title='打印1' onclick='preview(1)' value="打印">
				</div>
			</div>	
		</div>
	</div>			
<script type="text/javascript" src="${ctx}/static/js/thjw/common/print/printview.js"></script>
</body>
</html>