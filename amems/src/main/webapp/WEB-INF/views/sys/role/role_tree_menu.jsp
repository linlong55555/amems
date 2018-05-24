<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
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
	<input type="hidden" id="adjustHeight" value="40" />
	<div class="page-content">
	<input id="menuId" type="hidden"/> 
	<input id="menus" type="hidden"/> 
	<input id="btns" type="hidden"/> 
	<input id="id" type="hidden" value="${id}"/> 
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar">
			</div>
			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 table-h" style="margin-bottom:10px;">
					 <!-- 菜单树-->
					<div id="tab_trade" class="tab-pane active col-xs-12">
						<div class="zTreeDemoBackground">
							<ul id="treeDemo" class="ztree" ></ul>
						</div>
					</div>
				</div>
				<div class="text-right">
                     <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="save()">
                     	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					 </button>
                     <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:window.history.go(-1)">
                     	<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
                     </div>
                     
				<div class="clearfix"></div>
				
			</div>
		</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/sys/role_tree_menu.js"></script>
</body>
</html>