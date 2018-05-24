<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>物料履历</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var type = '${param.type}';
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	 
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<div class="page-content">
	 
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0" >
		 		<div class="col-xs-12 " style='padding:0px;'>
						<!-- start隐藏input -->
						<!-- end隐藏input -->
						 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group pull-right">
							 <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-leftright-8">
								 <a href="javascript:refresh();"  class="btn btn-primary padding-top-1 padding-bottom-1  pull-right">
									<div class="font-size-12">刷新</div>
									<div class="font-size-9">Refresh</div>
								</a>
							</div>
							<!-- <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 padding-leftright-8">
								<a href="javascript:add();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-right">
									<div class="font-size-12">导出</div>
									<div class="font-size-9">Export</div>
								</a>  
							</div> -->
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">件号</div>
								<div class="font-size-9 ">P/N</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="jh" value="">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">序列号</div>
								<div class="font-size-9">S/N</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="xlh" value="">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">型号</div>
								<div class="font-size-9">Model</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="xh" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">厂家件号</div>
								<div class="font-size-9 ">MP/N</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="cjjh" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">名称</div>
								<div class="font-size-9 ">Name</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="mc" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA章节号</div>
								<div class="font-size-9 ">ATA</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="ata" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">履历卡号</div>
								<div class="font-size-9 ">CV</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="llkh" >
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">出厂日期</div>
								<div class="font-size-9 ">Factory Date</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="ckrq" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">CAL</div>
								<div class="font-size-9 ">CAL</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="cal" >
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">TSN</div>
								<div class="font-size-9 ">TSN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="tsn" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">TSO</div>
								<div class="font-size-9 ">TSO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="tso" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">CSN</div>
								<div class="font-size-9 ">CSN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="csn" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">CSO</div>
								<div class="font-size-9 ">CSO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control" id="cso" >
							</div>
						</div>
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">改装记录</div>
								<div class="font-size-9">Record</div>
							</span>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="gzjl" name="gzjl" class="form-control" maxlength="100" style="height:55px"></textarea>
							</div>
						</div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8">
						<span id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
							<div class="font-size-12 margin-topnew-2">部件列表</div>
							<div class="font-size-9">Component List</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
							<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:600px">
								<thead>
									<tr>
										<th class=" fixed-column colwidth-3" >
										<div class="font-size-12 line-height-18 ">序号<div>
										<div class="font-size-9 line-height-18">No</div>
									</th>
									<th  class=" fixed-column colwidth-10">
										<div class="font-size-12 line-height-18">日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class=" colwidth-10" >
										<div class="font-size-12 line-height-18">类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th>
									<th class=" colwidth-13">
										<div class="font-size-12 line-height-18">单据编号</div>
										<div class="font-size-9 line-height-18">Documents No</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">仓库/飞机号</div>
										<div class="font-size-9 line-height-18">P/N Source</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">库位/装机位置</div>
										<div class="font-size-9 line-height-18">Location</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">上级部件</div>
										<div class="font-size-9 line-height-18">Superior Components</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">操作者</div>
										<div class="font-size-9 line-height-18">Operator</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">部件使用值</div>
										<div class="font-size-9 line-height-18">Components Vlaue</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">拆装原因</div>
										<div class="font-size-9 line-height-18">Reason</div>
									</th>
									<th class=" colwidth-10" >
										<div class="font-size-12 line-height-18">在机使用</div>
										<div class="font-size-9 line-height-18">Machine</div>
									</th>		
									</tr>
								</thead>
							   <tbody id="list">
							   		 <tr class="non-choice text-center"><td colspan="11">暂无数据 No data.</td></tr>
								</tbody>
							</table>
							</div>
						</div>
					</div>
					
						 
        	</div>
		</div>		
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/materialhistory/materialhistory_view.js"></script>
		<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>
