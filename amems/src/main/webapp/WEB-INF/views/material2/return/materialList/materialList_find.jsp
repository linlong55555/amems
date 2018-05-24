<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看航材退料清单</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-content">
<input type="hidden" id="viewid" value="${viewid}">
<input type="hidden" id="id" value="${viewid}">
<input type="hidden" id="dprtcode" >
	<div class="panel panel-primary" id="materialList_find_modal">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0 padding-left-8 padding-right-8  padding-top-0">	 
	            <div class="col-xs-12 margin-top-8 ">
		            <div class="input-group-border"> 
		            <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">退料日期</div>
							<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="tlrq" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">退料人</div>
							<div class="font-size-9 ">Retreating</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="tlr" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">部件号</div>
							<div class="font-size-9 ">PN</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="bjh" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				     <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">部件名称</div>
							<div class="font-size-9 ">PN name</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="bjmc" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批次号</div>
							<div class="font-size-9 ">Batch No.</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="pch" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">序列号</div>
							<div class="font-size-9 ">SN</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="xlh" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">退料数量</div>
							<div class="font-size-9 ">Amount</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="tlsl" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">是否可用</div>
							<div class="font-size-9 ">Available</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="sfky" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">部件来源</div>
							<div class="font-size-9 ">Original</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="bjly" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				     <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">物料类别</div>
							<div class="font-size-9 ">Category</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="wllb" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">说明</div>
							<div class="font-size-9 ">Instruction</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea id="sm" class="form-control" style="height:55px;" readonly></textarea>
						</div>
				    </div>
			
				    <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">收货信息</div>
							<div class="font-size-9 ">Info</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea id="sjhw" class="form-control" style="height:55px;" readonly></textarea>
						</div>
				    </div>
				    <div class='clearfix'></div>
		            </div>
	            </div> 
			</div>
		<div class="clearfix"></div>
	</div>	
</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/return/materialList/materialList_find.js"></script>
</body>
</html>