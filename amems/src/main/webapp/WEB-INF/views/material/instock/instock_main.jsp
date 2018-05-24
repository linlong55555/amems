<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<!-- 入库管理 -->
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
var tabId = '${param.tabId}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="adjustHeight" value="80" />
	<div class="clearfix"></div>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>
		
			<div class="clearfix"></div>
			
			<div class="panel-body padding-bottom-0">
				<!-----标签导航start---->
				<ul class="nav nav-tabs">
					<li class="active">
						<a id="tab_purchase" onclick="loadPurchase();" href="#purchase" data-toggle="tab">
							<div class="font-size-12 line-height-18">采购入库</div>
							<div class="font-size-9 line-height-18">Purchase InStock</div>
						</a>
					</li>
					<li>
						<a id="tab_repair" onclick="loadRepair();" href="#repair" data-toggle="tab">
							<div class="font-size-12 line-height-18">送修入库</div>
							<div class="font-size-9 line-height-18">Repair InStock</div>
						</a>
					</li>
					<li>
						<a id="tab_lendReturn" onclick="loadLendReturn();" href="#lendReturn" data-toggle="tab">
							<div class="font-size-12 line-height-18">借出归还入库</div>
							<div class="font-size-9 line-height-18">Lending return</div>
						</a>
					</li>
					<li>
						<a id="tab_borrow" onclick="loadBorrow();" href="#borrow" data-toggle="tab">
							<div class="font-size-12 line-height-18">借入入库</div>
							<div class="font-size-9 line-height-18">Lending InStock</div>
						</a>
					</li>
					<li>
						<a id="tab_handwork" href="#handwork" data-toggle="tab">
							<div class="font-size-12 line-height-18">手工入库</div>
							<div class="font-size-9 line-height-18">Manual InStock</div>
						</a>
					</li>
					<li>
						<a id="tab_history" onclick="loadInstockHistory();" href="#history" data-toggle="tab">
							<div class="font-size-12 line-height-18">入库历史</div>
							<div class="font-size-9 line-height-18">InStock history</div>
						</a>
					</li>
				</ul>
				<!-----标签内容start---->
		      	<div class="tab-content">
					<div class="tab-pane fade in active"  id="purchase">
		          		<%@ include file="/WEB-INF/views/material/instock/purchase_instock.jsp"%>
					</div>
			        <div class="tab-pane fade" id="repair">
		          		<%@ include file="/WEB-INF/views/material/instock/repair_instock.jsp"%>
					</div>
			        <div class="tab-pane fade" id="lendReturn">
		          		<%@ include file="/WEB-INF/views/material/instock/lend_return_instock.jsp"%>
					</div>
			        <div class="tab-pane fade" id="borrow">
		          		<%@ include file="/WEB-INF/views/material/instock/borrow_instock.jsp"%>
		        	</div>
			        <div class="tab-pane fade" id="handwork">
		          		<%@ include file="/WEB-INF/views/material/instock/handwork_instock_view.jsp"%>
		        	</div>
			        <div class="tab-pane fade" id="history">
		          		<%@ include file="/WEB-INF/views/material/instock/instock_history.jsp"%>
		        	</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/instock/instock_main.js"></script>
</body>
</html>