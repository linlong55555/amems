<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>航材销毁下架</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%> 
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
					$('.destroy_click-btn button:visible').trigger('click');//调用主列表页查询
				}
			}
		});
	});
</script>
<script type="text/javascript">
		var id = "${param.id}";
		var pageParam = '${param.pageParam}';
	</script>
</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content" id="">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<div class='tab-only'>
				<ul class="nav nav-tabs tabNew-style">
				  	<li class="active" id="destroyli">
				  		<a id='aircraftMaintenanceTab' href="#toDestroy"  data-toggle="tab">
				  			<div class="font-size-12 line-height-12">待销毁</div>
			                <div class="font-size-9 line-height-9">To be destroyed</div>
				  		</a>
				  	</li>
				  	<li id="revoke" >
				  		<a id='EOmonitoringTab' href="#destroy"  data-toggle="tab">
				  			<div class="font-size-12 line-height-12">已销毁</div>
			                <div class="font-size-9 line-height-9">Destroyed</div>
				  		</a>
				  	</li>
				</ul>
				<!-----标签内容start---->
		      	<div class="tab-content">
					<div class="tab-pane fade in active"  id="toDestroy">
						<%@ include file="/WEB-INF/views/material2/destory/airmaterial/airmaterial_bedestory.jsp"%>
					</div>
			        <div class="tab-pane fade" id="destroy">
		          		<%@ include file="/WEB-INF/views/material2/destory/airmaterial/airmaterial_destory.jsp"%>
			        </div>
				</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/destory/tool/tool_main.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/destory/tool/tool_destroy_main.js"></script>
</body>
</html>