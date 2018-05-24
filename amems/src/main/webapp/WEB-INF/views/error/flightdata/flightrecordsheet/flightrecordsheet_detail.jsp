<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞行记录单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var pageParam = '${param.pageParam}';
</script>
<style type="text/css">
#flightDataList > tr > td  {
    border-top: 0px;
}
small {
	line-height: 14px;
}
.border-left {
    border-left: 1px solid #ddd;
}
.border-right {
    border-right: 1px solid #ddd;
}
.border-bottom {
    border-bottom: 1px solid #ddd;
}
.border-top {
    border-top: 1px solid #ddd;
}
.dropdown-menu li{
	padding-left: 0 !important;
}
.ajax-file-upload-statusbar{
	display: none;
}
.btn-xs .badge {
    top: 0;
    padding: 1px 5px;
    position: relative;
    top: -13px;
    right: 2px;
    cursor: pointer;
    background: #0091d9;
    display: inline-block;
    font-size: 8px;
    box-shadow: inset 1px 1px 0 rgba(0,0,0,.1),inset 0 -1px 0 rgba(0,0,0,.07);
    color: #fff;
    font-weight: 700;
    border-radius: 50%;
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;
    padding: 1px 5px 1px;
    text-align: center;
    line-height: normal;
}
.line {
    border-bottom: 0px;
}
#flightDataList>tr>td>small {
    white-space: nowrap;
}
</style>
</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content ">
		<div class="panel panel-primary" >
				<input type ="hidden" id="fjzch_add" value='${erayFns:escapeStr(param.fjzch)}'>
				<div class="panel-heading  "> 
				<div id="NavigationBar"></div>
				</div>
				<div class="panel-body col-xs-12">
					<form id="flightRecordSheetForm">
					<input type="hidden" id="forwordType" value="${type}">
					<input type="hidden" id="frsId" value="${flightrecordsheet.id}">
					<input type="hidden" id="frsZt" value="${flightrecordsheet.zt}">
					<div class="row" style="margin-left: 0px;margin-right: 0px;">
						
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group frs-head">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red" class="view">*</span>组织机构</div>
								<div class="font-size-9 line-height-18">Organization</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select id="dprtcode" class="form-control" onchange="changeOrganization()">
								   <c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${flightrecordsheet.dprtcode == null ? (user.jgdm eq type.id) : (flightrecordsheet.dprtcode eq type.id)}">selected='selected'</c:if>>${type.dprtname}</option>
								   </c:forEach>
								</select> 
							</div>
						</div>
					
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group frs-head">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red" class="view">*</span>飞机注册号</div>
								<div class="font-size-9 line-height-18">A/C REG</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<select id="fjzch" class="form-control" name="fjzch" onchange="changePlane()">
								</select> 
								<input type="hidden" id="fjzch_hid" value="${erayFns:escapeStr(flightrecordsheet.fjzch)}">
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group frs-head">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red" class="view">*</span>记录本页码</div>
								<div class="font-size-9 line-height-18">Flight record</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input id="jlbym" class="form-control" name="jlbym" type="text" value="${flightrecordsheet.jlbym}" maxlength="20">
							</div>
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group frs-head">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red" class="view">*</span>飞行日期</div>
								<div class="font-size-9 line-height-18">Flight date</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input id="fxrq" class="form-control date-picker" data-date-format="yyyy-mm-dd"  name="fxrq" type="text" maxlength="10"  value="<fmt:formatDate value='${flightrecordsheet.fxrq}' pattern='yyyy-MM-dd'/>" ${flightrecordsheet.zt==2||flightrecordsheet.zt==12?'disabled="disabled"':''}>
							</div>
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group frs-head">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">航班号</div>
								<div class="font-size-9 line-height-18">Flight No.</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input id="hbh" class="form-control" name="hbh" type="text" value="${flightrecordsheet.hbh}" maxlength="20">
							</div>
						</div>
						<div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 padding-right-0 form-group frs-head pull-right" id="fxjldhDiv" style="display: none;">
							<div class="col-xs-offset-3 col-xs-9 padding-left-8 padding-right-0">
								<span id="fxjldh" class="font-size-20">${flightrecordsheet.fxjldh}</span>
							</div>
						</div>
					</div>
					
					<div class=row style="margin-left: 0px;margin-right: 0px;">
						<div class="col-lg-12 padding-left-0 padding-right-0" style="overflow-x:scroll;">
							<table class="table table-thin text-center" id="flightDataTable">
								<thead class="nav-tabs">
									<tr>
										<th colspan="2" rowspan="2" style="min-width: 160px;vertical-align:middle;">
											<div class="pull-left padding-left-10 margin-right-10">
												<div class="font-size-12 line-height-18">飞行数据</div>
												<div class="font-size-9">Flight Data</div>
											</div>
											<div class="pull-left">
												<button title="新增航次" onclick="addRow()" class="btn btn-default btn-sm pull-left margin-left-5" type="button">
												<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
												</button>
												<button title="删除航次" onclick="deleteRow()" class="btn btn-default btn-sm pull-left margin-left-5" type="button">
												<i class="icon-minus cursor-pointer color-blue cursor-pointer'"></i>
												</button>
											</div>
										</th>
										<th style="min-width: 60px;vertical-align:middle;" rowspan="2" name="fxsj">
											<div class="font-size-12 line-height-18">飞行小时</div>
											<div class="font-size-9 line-height-18">Flight HRS</div>
										</th>
										<th style="min-width: 80px;vertical-align:middle;" rowspan="2" name="f1sj">
											<div class="font-size-12 line-height-18">1#左发小时</div>
											<div class="font-size-9 line-height-18">L/Engine HRS</div>
										</th>
										<th style="min-width: 80px;vertical-align:middle;" rowspan="2" name="f2sj">
											<div class="font-size-12 line-height-18">2#右发小时</div>
											<div class="font-size-9 line-height-18">R/Engine HRS</div>
										</th>
										<th style="min-width: 80px;vertical-align:middle;" rowspan="2" name="qljxh">
											<div class="font-size-12 line-height-18">起落循环</div>
											<div class="font-size-9 line-height-18">A/C CYCS.</div>
										</th>
										<th style="min-width: 60px;vertical-align:middle;" rowspan="2" name="jcsj">
											<div class="font-size-12 line-height-18">绞车时间</div>
											<div class="font-size-9 line-height-18">Winch Time</div>
										</th>
										<th style="min-width: 80px;vertical-align:middle;" class="border-right" rowspan="2" name="jcxh">
											<div class="font-size-12 line-height-18">绞车循环</div>
											<div class="font-size-9 line-height-18">Winch CYCS.</div>
										</th>
										<th colspan="2" style="border: 1px solid #ddd;" children="ylfxq,ylfxh">
											<div class="font-size-12 line-height-18">油量统计</div>
											<div class="font-size-9 line-height-18">Oil</div>
										</th>
										<th colspan="9" style="border: 1px solid #ddd;" children="f1n1,f1n2,f1n3,f1n4,f1n5,f1n6,f1hy,f1wdyd,f1glyd">
											<div class="font-size-12 line-height-18">1#左发</div>
											<div class="font-size-9 line-height-18">L/Engine</div>
										</th>
										<th colspan="9" style="border: 1px solid #ddd;" children="f2n1,f2n2,f2n3,f2n4,f2n5,f2n6,f2hy,f2wdyd,f2glyd">
											<div class="font-size-12 line-height-18">2#右发</div>
											<div class="font-size-9 line-height-18">R/Engine</div>
										</th>
										<th style="min-width: 80px;vertical-align:middle;" rowspan="2"  name="ssdsj">
											<div class="font-size-12 line-height-18">搜索灯时间</div>
											<div class="font-size-9 line-height-18">Search Time</div>
										</th>
										<th style="min-width: 80px;vertical-align:middle;" rowspan="2"  name="dgxh">
											<div class="font-size-12 line-height-18">外吊挂循环</div>
											<div class="font-size-9 line-height-18">E/S CYCS.</div>
										</th>
										<th style="min-width: 60px;vertical-align:middle;" rowspan="2"  name="ts1">
											<div class="font-size-12 line-height-18">TS1</div>
											<div class="font-size-9 line-height-18">TS1</div>
										</th>
										<th style="min-width: 60px;vertical-align:middle;" rowspan="2"  name="ts2">
											<div class="font-size-12 line-height-18">TS2</div>
											<div class="font-size-9 line-height-18">TS2</div>
										</th>
										<th style="min-width: 150px;vertical-align:middle;" name="tsqk" rowspan="2"  name="tsqk">
											<div class="font-size-12 line-height-18">特殊情况</div>
											<div class="font-size-9 line-height-18">Special Circumstances</div>
										</th>
										<th style="min-width: 60px;vertical-align:middle;" rowspan="2"  name="mgb">
											<div class="font-size-12 line-height-18">MGB</div>
											<div class="font-size-9 line-height-18">MGB</div>
										</th>
										<th style="min-width: 60px;vertical-align:middle;" rowspan="2"  name="igb">
											<div class="font-size-12 line-height-18">IGB</div>
											<div class="font-size-9 line-height-18">IGB</div>
										</th>
										<th style="min-width: 60px;vertical-align:middle;" class="border-right" rowspan="2" name="tgb" >
											<div class="font-size-12 line-height-18">TGB</div>
											<div class="font-size-9 line-height-18">TGB</div>
										</th>
										<th style="min-width: 80px;vertical-align:middle;" rowspan="2"  name="avFxr">
											<div class="font-size-12 line-height-18">电子放行人</div>
											<div class="font-size-9 line-height-18">AV Engineer</div>
										</th>
										<th style="min-width: 80px;vertical-align:middle;" rowspan="2"  name="avZzh">
											<div class="font-size-12 line-height-18">电子执照号</div>
											<div class="font-size-9 line-height-18">AV License</div>
										</th>
										<th style="min-width: 80px;vertical-align:middle;" rowspan="2"  name="meFxr">
											<div class="font-size-12 line-height-18">机械放行人</div>
											<div class="font-size-9 line-height-18">ME Engineer</div>
										</th>
										<th style="min-width: 80px;vertical-align:middle;" rowspan="2"  name="meZzh">
											<div class="font-size-12 line-height-18">机械执照号</div>
											<div class="font-size-9 line-height-18">ME License</div>
										</th>
										<th style="min-width: 100px;vertical-align:middle;" class="border-right" rowspan="2" name="jzshrk" >
											<div class="font-size-12 line-height-18">机长适航认可</div>
											<div class="font-size-9 line-height-18">Captain</div>
										</th>
									</tr>
									<tr>
										<th style="min-width: 60px;" name="ylfxq">
											<div class="font-size-12 line-height-18">飞行前</div>
											<div class="font-size-9 line-height-18">Preflight</div>
										</th>
										<th style="min-width: 60px;" class="border-right" name="ylfxh">
											<div class="font-size-12 line-height-18">飞行后</div>
											<div class="font-size-9 line-height-18">After flight</div>
										</th>
										<th style="min-width: 60px;" name="f1n1">
											<div class="font-size-12 line-height-18">N1</div>
											<div class="font-size-9 line-height-18">N1</div>
										</th>
										<th style="min-width: 60px;" name="f1n2">
											<div class="font-size-12 line-height-18">N2</div>
											<div class="font-size-9 line-height-18">N2</div>
										</th>
										<th style="min-width: 60px;" name="f1n3">
											<div class="font-size-12 line-height-18">N3</div>
											<div class="font-size-9 line-height-18">N3</div>
										</th>
										<th style="min-width: 60px;" name="f1n4">
											<div class="font-size-12 line-height-18">N4</div>
											<div class="font-size-9 line-height-18">N4</div>
										</th>
										<th style="min-width: 60px;" name="f1n5">
											<div class="font-size-12 line-height-18">N5</div>
											<div class="font-size-9 line-height-18">N5</div>
										</th>
										<th style="min-width: 60px;" name="f1n6">
											<div class="font-size-12 line-height-18">N6</div>
											<div class="font-size-9 line-height-18">N6</div>
										</th>
										<th style="min-width: 60px;" name="f1hy">
											<div class="font-size-12 line-height-18">滑油消耗</div>
											<div class="font-size-9 line-height-18">Oil</div>
										</th>
										<th style="min-width: 60px;" name="f1wdyd">
											<div class="font-size-12 line-height-18">温度余度</div>
											<div class="font-size-9 line-height-18">Temperature</div>
										</th>
										<th style="min-width: 60px;" class="border-right" name="f1glyd">
											<div class="font-size-12 line-height-18">功率余度</div>
											<div class="font-size-9 line-height-18">Watts</div>
										</th>
										<th style="min-width: 60px;" name="f2n1">
											<div class="font-size-12 line-height-18">N1</div>
											<div class="font-size-9 line-height-18">N1</div>
										</th>
										<th style="min-width: 60px;" name="f2n2">
											<div class="font-size-12 line-height-18">N2</div>
											<div class="font-size-9 line-height-18">N2</div>
										</th>
										<th style="min-width: 60px;" name="f2n3">
											<div class="font-size-12 line-height-18">N3</div>
											<div class="font-size-9 line-height-18">N3</div>
										</th>
										<th style="min-width: 60px;" name="f2n4">
											<div class="font-size-12 line-height-18">N4</div>
											<div class="font-size-9 line-height-18">N4</div>
										</th>
										<th style="min-width: 60px;" name="f2n5">
											<div class="font-size-12 line-height-18">N5</div>
											<div class="font-size-9 line-height-18">N5</div>
										</th>
										<th style="min-width: 60px;" name="f2n6">
											<div class="font-size-12 line-height-18">N6</div>
											<div class="font-size-9 line-height-18">N6</div>
										</th>
										<th style="min-width: 60px;" name="f2hy">
											<div class="font-size-12 line-height-18">滑油消耗</div>
											<div class="font-size-9 line-height-18">Oil</div>
										</th>
										<th style="min-width: 60px;" name="f2wdyd">
											<div class="font-size-12 line-height-18">温度余度</div>
											<div class="font-size-9 line-height-18">Temperature</div>
										</th>
										<th style="min-width: 60px;" class="border-right" name="f2glyd">
											<div class="font-size-12 line-height-18">功率余度</div>
											<div class="font-size-9 line-height-18">Watts</div>
										</th>
									</tr>
								</thead>
								<tbody id="flightDataList">
									<tr id="hc1" hcms="航次1" hc="2">
										<td rowspan="1" style="vertical-align:middle;min-width: 100px;"  class="border-left border-right">
											当日飞行数据
										</td>
										<td style="min-width: 60px;" name="title">
											<small name="jh" class="text-warning hidden" style="display: block;">件号P/N</small>
											<small class="text-warning" style="display: block;">序列号S/N</small>
											<small class="text-muted" style="display: block;">飞行前数据</small>
											<div>航次1</div>
										</td>
										<td name="fxsj">
											<small name="jh" class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1sj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2sj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="qljxh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="jcsj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="jcxh" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ylfxq">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ylfxh" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n3">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n4">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n5">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n6">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1hy">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1wdyd">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1glyd" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n3">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n4">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n5">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n6">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2hy">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2wdyd">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2glyd" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ssdsj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="dgxh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ts1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ts2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="tsqk">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<select class="form-control input-sm">
												<option value="">无 Nothing</option>
												<c:forEach items="${conditions}" var="condition">
													<option value="${condition.id}">${condition.ms}</option>
												</c:forEach>
											</select>
										</td>
										<td name="mgb">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="igb">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="tgb" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="avFxr">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="avZzh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="50">
										</td>
										<td name="meFxr">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="meZzh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="50">
										</td>
										<td name="jzshrk" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<input type="hidden" name="frid">
									</tr>
									<tr id="hc2"  hcms="航次2" hc="4" style="display: none">
										<td name="title">
											<small name="jh" class="text-warning hidden" style="display: block;">件号P/N</small>
											<small class="text-warning" style="display: block;">序列号S/N</small>
											<small class="text-muted" style="display: block;">飞行前数据</small>
											<div>航次2</div>
										</td>
										<td name="fxsj">
											<small name="jh" class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1sj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2sj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="qljxh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="jcsj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="jcxh" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ylfxq">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ylfxh" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n3">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n4">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n5">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n6">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1hy">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1wdyd">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1glyd" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n3">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n4">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n5">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n6">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2hy">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2wdyd">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2glyd" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ssdsj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="dgxh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ts1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ts2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="tsqk">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<select class="form-control input-sm">
												<option value="">无 Nothing</option>
												<c:forEach items="${conditions}" var="condition">
													<option value="${condition.id}">${condition.ms}</option>
												</c:forEach>
											</select>
										</td>
										<td name="mgb">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="igb">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="tgb" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="avFxr">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="avZzh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="50">
										</td>
										<td name="meFxr">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="meZzh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="50">
										</td>
										<td name="jzshrk" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<input type="hidden" name="frid">
									</tr>
									<tr id="hc3"  hcms="航次3" hc="6" style="display: none">
										<td name="title">
											<small name="jh" class="text-warning hidden" style="display: block;">件号P/N</small>
											<small class="text-warning" style="display: block;">序列号S/N</small>
											<small class="text-muted" style="display: block;">飞行前数据</small>
											<div>航次3</div>
										</td>
										<td name="fxsj">
											<small name="jh" class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1sj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2sj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="qljxh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="jcsj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="jcxh" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ylfxq">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ylfxh" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n3">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n4">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n5">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n6">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1hy">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1wdyd">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1glyd" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n3">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n4">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n5">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n6">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2hy">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2wdyd">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2glyd" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ssdsj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="dgxh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ts1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ts2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="tsqk">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<select class="form-control input-sm">
												<option value="">无 Nothing</option>
												<c:forEach items="${conditions}" var="condition">
													<option value="${condition.id}">${condition.ms}</option>
												</c:forEach>
											</select>
										</td>
										<td name="mgb">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="igb">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="tgb" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="avFxr">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="avZzh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="50">
										</td>
										<td name="meFxr">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="meZzh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="50">
										</td>
										<td name="jzshrk" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<input type="hidden" name="frid">
									</tr>
									<tr id="hc4"  hcms="航次4" hc="8" style="display: none">
										<td name="title">
											<small name="jh" class="text-warning hidden" style="display: block;">件号P/N</small>
											<small class="text-warning" style="display: block;">序列号S/N</small>
											<small class="text-muted" style="display: block;">飞行前数据</small>
											<div>航次4</div>
										</td>
										<td name="fxsj">
											<small name="jh" class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1sj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2sj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="qljxh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="jcsj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="jcxh" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ylfxq">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ylfxh" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n3">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n4">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n5">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1n6">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1hy">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1wdyd">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f1glyd" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n3">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n4">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n5">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2n6">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2hy">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2wdyd">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="f2glyd" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ssdsj">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="dgxh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ts1">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="ts2">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="tsqk">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<select class="form-control input-sm">
												<option value="">无 Nothing</option>
												<c:forEach items="${conditions}" var="condition">
													<option value="${condition.id}">${condition.ms}</option>
												</c:forEach>
											</select>
										</td>
										<td name="mgb">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text">
										</td>
										<td name="igb">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text">
										</td>
										<td name="tgb" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text">
										</td>
										<td name="avFxr">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="avZzh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="50">
										</td>
										<td name="meFxr">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<td name="meZzh">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="50">
										</td>
										<td name="jzshrk" class="border-right">
											<small name="jh"  class="text-warning hidden" style="display: block;">&nbsp;</small>
											<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
											<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
											<input class="form-control input-sm" type="text" maxlength="10">
										</td>
										<input type="hidden" name="frid">
									</tr>
								</tbody>
								<tfoot>
									<tr class="border-bottom">
										<th colspan="2" style="padding-top: 10px;" class="border-left border-top">
											<div class="text-right" style="font-weight:normal;">累计总数 Total</div>
										</th>
										<th name="fxsj" style="padding-top: 10px;">
											<span></span>
										</th>
										<th name="f1sj" style="padding-top: 10px;">
											<span></span>
										</th>
										<th name="f2sj" style="padding-top: 10px;">
											<span></span>
										</th>
										<th name="qljxh" style="padding-top: 10px;">
											<span></span>
										</th>
										<th name="jcsj" style="padding-top: 10px;">
											<span></span>
										</th>
										<th name="jcxh" style="padding-top: 10px;" class="border-right">
											<span></span>
										</th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;" class="border-right"></th>
										<th name="f1n1" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF1N1" type="hidden">
										</th>
										<th name="f1n2" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF1N2" type="hidden">
										</th>
										<th name="f1n3" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF1N3" type="hidden">
										</th>
										<th name="f1n4" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF1N4" type="hidden">
										</th>
										<th name="f1n5" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF1N5" type="hidden">
										</th>
										<th name="f1n6" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF1N6" type="hidden">
										</th style="padding-top: 10px;">
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;" class="border-right"></th>
										<th name="f2n1" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF2N1" type="hidden">
										</th>
										<th name="f2n2" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF2N2" type="hidden">
										</th>
										<th name="f2n3" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF2N3" type="hidden">
										</th>
										<th name="f2n4" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF2N4" type="hidden">
										</th>
										<th name="f2n5" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF2N5" type="hidden">
										</th>
										<th name="f2n6" style="padding-top: 10px;">
											<input class="form-control input-sm" type="text" maxlength="10">
											<input id="adjustF2N6" type="hidden">
										</th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;" class="border-right"></th>
										<th name="ssdsj" style="padding-top: 10px;">
											<span></span>
										</th>
										<th name="dgxh" style="padding-top: 10px;">
											<span></span>
										</th>
										<th name="ts1" style="padding-top: 10px;">
											<span></span>
										</th>
										<th name="ts2" style="padding-top: 10px;">
											<span></span>
										</th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;"class="border-right"></th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;"></th>
										<th style="padding-top: 10px;"  class="border-right"></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
					
					<br/>
					
					<div class="panel-heading">
						<div class="col-lg-2" style="width: 25%;">
							<div class="col-lg-4  padding-left-0 padding-right-0">
								<div class="font-size-16 line-height-18">完成工作</div>
								<div class="font-size-9 margin-bottom-10 ">Finished Task</div>
								
							</div>
							<div class="col-lg-8 padding-bottom-5  padding-left-0 padding-right-0">
								<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="showAssociateWorkModal()">
									<div class="font-size-12">选择</div>
									<div class="font-size-9">Choose</div>
								</button>
								
								<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="showFinishedWorkByHandModal()">
									<div class="font-size-12">手工输入</div>
									<div class="font-size-9">Manual</div>
								</button>
							</div>
						</div>
						
						<div class="col-lg-2 col-sm-2 col-xs-12 padding-left-0 padding-right-0" id="hq_jcr" name="jcr" hc="1" style="width:15%">
							<div class="col-xs-5 padding-left-0 padding-right-0 text-right">
								<div class="font-size-12 line-height-18">航前检查</div>
								<div class="font-size-9 line-height-18">P/F-Checker</div>
								</div>
							<div class=" col-xs-6 padding-left-8 padding-right-0">
							<input class="form-control" type="text" name="show" maxlength="10">
							<input type="hidden" name="jcrid">
							</div>
						</div>
						
						<div class="col-lg-2 col-sm-2 col-xs-12 padding-left-0 padding-right-0" id="hc1_jcr" style="display: none;width:15%" name="jcr" hc="3">
							<div class="col-xs-5 padding-left-0 padding-right-0 text-right">
								<div class="font-size-12 line-height-18">航次1检查</div>
								<div class="font-size-9 line-height-18">F1-Checker</div>
								</div>
							<div class=" col-xs-6 padding-left-8 padding-right-0">
							<input class="form-control" type="text" name="show" maxlength="10">
							<input type="hidden" name="jcrid">
							</div>
						</div>
						
						<div class="col-lg-2 col-sm-2 col-xs-12 padding-left-0 padding-right-0" id="hc2_jcr" style="display: none;width:15%" name="jcr" hc="5">
							<div class="col-xs-5 padding-left-0 padding-right-0 text-right">
								<div class="font-size-12 line-height-18">航次2检查</div>
								<div class="font-size-9 line-height-18">F2-Checker</div>
								</div>
							<div class=" col-xs-6 padding-left-8 padding-right-0">
							<input class="form-control" type="text" name="show" maxlength="10">
							<input type="hidden" name="jcrid">
							</div>
						</div>
						
						<div class="col-lg-2 col-sm-2 col-xs-12 padding-left-0 padding-right-0" id="hc3_jcr" style="display: none;width:15%" name="jcr" hc="7">
							<div class="col-xs-5 padding-left-0 padding-right-0 text-right">
								<div class="font-size-12 line-height-18">航次3检查</div>
								<div class="font-size-9 line-height-18">F3-Checker</div>
								</div>
							<div class=" col-xs-6 padding-left-8 padding-right-0">
							<input class="form-control" type="text" name="show" maxlength="10">
							<input type="hidden" name="jcrid">
							</div>
						</div>
						
						<div class="col-lg-2 col-sm-2 col-xs-12 padding-left-0 padding-right-0" id="hh_jcr" name="jcr" hc="99" style=";width:15%">
							<div class="col-xs-5 padding-left-0 padding-right-0 text-right">
								<div class="font-size-12 line-height-18">航后检查</div>
								<div class="font-size-9 line-height-18">A/F-Checker</div>
								</div>
							<div class=" col-xs-6 padding-left-8 padding-right-0">
							<input class="form-control" type="text" name="show" maxlength="10">
							<input type="hidden" name="jcrid">
							</div>
						</div>
					</div>
					
					<div class="clearfix"></div>
					<hr class=" margin-top-0 margin-bottom-5"/>
					
					<div>
						<small class="text-muted">双击完成工作可以进行编辑</small>
						<div class="col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0">
							<table class="table table-thin table-bordered table-striped text-center table-set" id="finishedWorkTable">
								<thead>
									<th  width="110px">
										<div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th width="9%">
										<div class="font-size-12 line-height-18">来源类型</div>
										<div class="font-size-9 line-height-18">Source Type</div>
									</th>
									<th width="11%">
										<div class="font-size-12 line-height-18">单号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th>
									<th width="10%">
										<div class="font-size-12 line-height-18">任务相关信息</div>
										<div class="font-size-9 line-height-18">Task Desc</div>
									</th>
									<th width="7%">
										<div class="font-size-12 line-height-18">航段</div>
										<div class="font-size-9 line-height-18">Leg</div>
									</th>
									<th width="15%">
										<div class="font-size-12 line-height-18">故障信息</div>
										<div class="font-size-9 line-height-18">Fault Message</div>
									</th>
									<th width="15%">
										<div class="font-size-12 line-height-18">处理措施</div>
										<div class="font-size-9 line-height-18">Measure</div>
									</th>
									<th width="12%">
										<div class="font-size-12 line-height-18">实际工时</div>
										<div class="font-size-9 line-height-18">Actual Hrs</div>
									</th>
									<th width="10%">
										<div class="font-size-12 line-height-18">工作项目保留单号</div>
										<div class="font-size-9 line-height-18">Work retention No.</div>
									</th>
									<th width="10%">
										<div class="font-size-12 line-height-18">责任人</div>
										<div class="font-size-9 line-height-18">Operator</div>
									</th>
								</thead>
								<input type="hidden" id="hid_current_row"/>
								<tbody id="finishedWorkList">
									<tr class="non-choice">
										<td colspan="11">请选择完成工作.Please choose to finish work.</td>
									</tr>
								</tbody>
							</table>
							
						</div>
					</div>
					<div class="clearfix"></div>
					
				   <div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc; margin-top: 20px;cursor:pointer"  id="fileUpload_title">
						<span>
							<div class="font-size-16 line-height-18">
								<span id="fileUpload_badge">-</span>
								 附件上传
								 <span class="badge danger-bg" id="fileUpload_count">0</span>
							</div>
							<div class="font-size-9 " style="margin-left: 15px;">Attachment Upload</div>
						</span>
				   </div>
				   <div id="FileUploadDiv">	 	
					 <div class=" col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-3 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File Description</div></label>
						 <div class="col-xs-9 padding-left-8 padding-right-0" >
							<input type="text" id="wbwjm" name="wbwjm"  maxlength="90" class="form-control "  >
						</div>
					 </div>
				     <div class=" col-lg-1 col-sm-4 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-12 "  style="margin-left: 5px ;padding-left: 0"></div>
					 </div> 
					
					 <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0">
						<table class="table-set table table-bordered table-striped table-hover text-center">
							<thead>
								<tr>
									<th style="width:40px;"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th style="width:500px;">
									<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">Description</div>
									</th>
									<th style="width:100px;">
									<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File Size</div>
									</th>
									<th style="width:100px;"><div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Uploader</div></th>
									<th style="width:100px;"><div class="font-size-12 line-height-18">上传时间</div>
										<div class="font-size-9 line-height-18">Upload Time</div></th>					
								</tr>
							</thead>
						    <tbody id="filelist">
								 <tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>
							</tbody>
						</table>
					  </div>
					</div>
					
					<div class="clearfix"></div>
					<div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc; margin-top: 20px;cursor:pointer" id="DDF_title">
						<span>
							<div class="font-size-16 line-height-18">
								<span id="DDF_badge">-</span>
								  故障保留单
								<span class="badge danger-bg" id="DDF_count">0</span>
							</div>
							<div class="font-size-9 " style="margin-left: 15px;">Fault Retain</div>
						</span>
					</div>
					
					<div id="DDF_div">
						<div class="col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 padding-top-10">
							<table class="table table-thin table-bordered table-striped text-center table-set" id="DDFTable">
								<thead>
									<th width="10%">
										<div class="font-size-12 line-height-18">故障保留单号</div>
										<div class="font-size-9 line-height-18">DD NO.</div>
									</th>
									<th width="10%">
										<div class="font-size-12 line-height-18">MEL类型</div>
										<div class="font-size-9 line-height-18">MEL type</div>
									</th>
									<th width="10%">
										<div class="font-size-12 line-height-18">到期日期</div>
										<div class="font-size-9 line-height-18">Expire</div>
									</th>
									<th width="10%">
										<div class="font-size-12 line-height-18">关闭日期</div>
										<div class="font-size-9 line-height-18">Close date</div>
									</th>
									<th width="20%">
										<div class="font-size-12 line-height-18">首次保留内容</div>
										<div class="font-size-9 line-height-18">First Retain</div>
									</th>
									<th width="20%">
										<div class="font-size-12 line-height-18">再次保留内容</div>
										<div class="font-size-9 line-height-18">Again Retain</div>
									</th>
									<th width="20%">
										<div class="font-size-12 line-height-18">故障描述</div>
										<div class="font-size-9 line-height-18">Fault Desc</div>
									</th>
								</thead>
								<tbody id="DDFList">
								</tbody>
							</table>
						</div>
					</div>
					
					
					<div class="col-lg-12 margin-bottom-10 padding-right-0">
						<div class="col-lg-8">
							<div class="col-lg-5 col-lg-offset-7">
								<ul class="pager" style="width:200px;margin: 3px 0 0 0;">
									<li class="previous">
										<a href="javascript:void(0);" id="previousPage" onclick="goToPreviousPage()" title="上一页" pageid="${previousPage}">
											←&nbsp;Previous
										</a>
									</li>
									<li class="next">
										<a href="javascript:void(0);" id="nextPage" onclick="goToNextPage()" title="下一页" pageid="${nextPage}">
											&nbsp;&nbsp;&nbsp;Next&nbsp;&nbsp;&nbsp;→
										</a>
									</li>
								</ul>
							</div>
						</div>
						
						<div class="col-lg-4 pull-right text-right padding-right-0">
							<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="saveTemp('0')" id="saveBtn">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="takeEffect('0')" id="submitBtn">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="showCancelModal()" id="cancelBtn">
								<div class="font-size-12">撤销</div>
								<div class="font-size-9">Cancel</div>
							</button>
							<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="goToAddPage()" id="addAgainBtn">
								<div class="font-size-12">再次新增</div>
								<div class="font-size-9">Add Again</div>
							</button>
							<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="goToMainPage()" id="backBtn">
								<div class="font-size-12">返回</div>
								<div class="font-size-9">Back</div>
							</button>
						</div>
						
					</div>
					
					
				</div>
				</form>
				<div class="clearfix"></div>
			</div>
	
	<!-- END CONTENT -->
	
	<!-------关联工作Start-------->
	<div class="modal fade" id="associateWorkModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="associateWorkModal_body">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
					</button>
					<h4> 选择计划项目 </h4>
					<hr class="margin-top-0 margin-bottom-10" />
					<div class="col-xs-12 margin-bottom-10 padding-right-0">
						<div class=" pull-right padding-left-0 padding-right-0">
							<div class="pull-left">
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="font-size-12">任务类型</div>
									<div class="font-size-9">Task type</div>
								</div>
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="padding-left-5 pull-left" style="width: 150px; margin-right:3px;">
									   <select id="associateWorkType" class="form-control" onchange="changeAssociateWorkType()">
									   		<option value="0">全部</option>
											<option value="1">装机件任务</option>
											<option value="2">非装机件任务</option>
									   </select> 
									</div>
								</div>
							</div>
							<div class="pull-left">
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="font-size-12">状态</div>
									<div class="font-size-9">Status</div>
								</div>
								<div class="pull-left text-right padding-left-0 padding-right-0">
									<div class="padding-left-5 pull-left" style="width: 150px; margin-right:3px;">
									   <select id="associateWorkStatus" class="form-control" onchange="changeAssociateWorkType()">
									   		<option value="3">完工待关闭</option>
											<option value="">全部</option>
									   </select> 
									</div>
								</div>
							</div>
							<div class="pull-left">
								<div class="pull-left text-right padding-left-0 padding-right-0" style="width: 150px;margin-right:3px;">
									<input placeholder="任务单号/任务相关信息" id="associateWorkKeyword" class="form-control" type="text">
								</div>
							</div>
							<div class="pull-right padding-left-0 padding-right-0 ">   
								<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="changeAssociateWorkType()">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                  		</button>
			                </div>
						</div>
					</div>
					<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
							<thead>
							<tr>
							<th width="8%"><div class="font-size-12 line-height-18" >操作</div><div class="font-size-9 line-height-18" >Operation</div></th>
							<th width="6%"><div class="font-size-12 line-height-18" >序号</div><div class="font-size-9 line-height-18" >No.</div></th>
							<th width="20%"><div class="font-size-12 line-height-18" >单号</div><div class="font-size-9 line-height-18" >Task No.</div></th>
							<th width="48%"><div class="font-size-12 line-height-18" >任务相关信息</div><div class="font-size-9 line-height-18" >Task Desc</div></th>
							<th width="18%"><div class="font-size-12 line-height-18" >来源类型</div><div class="font-size-9 line-height-18" >Source type</div></th>
							</tr> 
			         		 </thead>
							<tbody id="associateWorkModalList">
							</tbody>
					</table>
				<div class=" col-xs-12  text-center " style="margin-top: 0px; margin-bottom: 0px;" id="mpagination">
				</div>
			
					<div class="clearfix"></div>
					<div class="modal-footer">
						<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="addAssociateWork()">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-------关联工作 End-------->
	
		
		<!-------拆下件放大镜 Start-------->
		<div class="modal fade" id="disassemblyComponentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg" style="width: 1350px;">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="disassemblyComponentModalBody">
						<div class="panel panel-primary">
							<div class="panel-heading  padding-top-3 padding-bottom-1">
								<div class="font-size-16 line-height-18">拆下件列表</div>
								<div class="font-size-9 ">Disassembly List</div>
							</div>
							<div class="panel-body padding-top-0 padding-bottom-0">
				            	<div class="col-lg-12 col-xs-12">
								    <div class="text-left margin-top-10">
								    	<input type="hidden" id="hidden_disassemblyComponent" />
								    	<div class="pull-right padding-right-0">
								    		<div class="pull-left">
												<div class="pull-left text-right padding-left-0 padding-right-0">
													<div class="font-size-12">位置</div>
													<div class="font-size-9">Location</div>
												</div>
												<div class="pull-left text-right padding-left-0 padding-right-0">
													<div class="padding-left-8 pull-left" style="width: 250px; margin-right:5px;">
													   <select id="wz_tableView" class="form-control" onchange="searchDisassemblyComponent()">
													   		<option value="">显示全部All</option>
															<option value="0">机身 Fuselage</option>
															<option value="1">1#左发 L/Engine</option>
															<option value="2">2#右发 R/Engine</option>
															<option value="5">外吊挂 E/S</option>
															<option value="4">搜索灯 Search</option>
															<option value="3">绞车 Winch</option>
													   </select> 
													</div>
												</div>
											</div>
											<div class="pull-left padding-left-5 padding-right-0" style="width: 250px;">
												<input type="text" class="form-control " id="component_disassembly_search" placeholder="件号/序列号/中英文名称/ATA章节号/内部识别码/厂家件号"/>
											</div>
						              		<div class="pull-right padding-left-5 padding-right-0">
						              			<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchDisassemblyComponent();">
													<div class="font-size-12">搜索</div>
													<div class="font-size-9">Search</div>
												</button>     
												<!-- <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1" id="btn" onclick="moreDisassemblyComponent();">
													<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
													<div class="pull-left padding-top-5">
													 &nbsp;<i class="icon-caret-down font-size-15" id="iconDisassemblyComponent"></i>
													</div>
										   		</button> -->
						                    </div>    
										</div>
								    	
										<div class="col-xs-12 triangle  padding-top-10 margin-bottom-10 margin-top-10 display-none border-cccccc" id="divSearchDisassemblyComponent">
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">开始生产日期</div>
													<div class="font-size-9">Manufacture date start</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<input class="form-control date-picker" data-date-format="yyyy-mm-dd" type="text"  id="beginScrq_tableView"/>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">结束生产日期</div>
													<div class="font-size-9">Manufacture date end</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<input class="form-control date-picker" data-date-format="yyyy-mm-dd" type="text"  id="endScrq_tableView"/>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">安装记录单号</div>
													<div class="font-size-9">Assembly Record</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<input type="text" class="form-control " id="azjldh_tableView"/>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">开始安装日期</div>
													<div class="font-size-9">Install date start</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<input class="form-control date-picker" data-date-format="yyyy-mm-dd" type="text"  id="beginAzrq_tableView"/>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">结束安装日期</div>
													<div class="font-size-9">Install date end</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<input class="form-control date-picker" data-date-format="yyyy-mm-dd" type="text"  id="endAzrq_tableView"/>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">拆除记录单号</div>
													<div class="font-size-9">Disassembly Record</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<input type="text" class="form-control " id="ccjldh_tableView"/>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">开始拆除日期</div>
													<div class="font-size-9">Remove date start</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<input class="form-control date-picker" data-date-format="yyyy-mm-dd" type="text"  id="beginCcrq_tableView"/>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">结束拆除日期</div>
													<div class="font-size-9">Remove date end</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<input class="form-control date-picker" data-date-format="yyyy-mm-dd" type="text"  id="endCcrq_tableView"/>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">履历卡类型</div>
													<div class="font-size-9">Career type</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<select class='form-control' id='llklx_tableView'>
														<option value="">请选择Choice</option>
														<option value="1">无履历卡 No Career</option>
														<option value="2">原装履历卡 Original</option>
														<option value="3">自制履历卡 Self-control</option>
													</select>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">履历卡编号</div>
													<div class="font-size-9">Career No.</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<input type="text" class="form-control " id="llkbh_tableView"/>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">备注</div>
													<div class="font-size-9">Remark</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<input type="text" class="form-control " id="bz_tableView"/>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">控制类型</div>
													<div class="font-size-9">Control type</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<select class='form-control' id='kzlx_tableView'>
														<option value="">请选择Choice</option>
														<option value="1">时控件 Time</option>
														<option value="2">时寿件 Life</option>
														<option value="3">非控制件 Non control</option>
													</select>
												</div>
											</span>
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">是否定检</div>
													<div class="font-size-9">Is P/I</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<select class='form-control' id='isDj_tableView'>
														<option value="">请选择Choice</option>
														<option value="1">是 Yes</option>
														<option value="0">否 No</option>
													</select>
												</div>
											</span>
											<div class="col-xs-12  text-right" style="margin-bottom: 10px;">
												<button type="button"
													class="btn btn-primary padding-top-1 padding-bottom-1"
													onclick="disassemblyComponentSearchreset();">
													<div class="font-size-12">重置</div>
													<div class="font-size-9">Reset</div>
												</button>
											</div> 
										</div>		
					         		</div>
								<!-- start:table -->
								<div class="clearfix"></div>
									<div class="margin-top-10 ">
										<div class="overflow-auto">
											<table class="table table-bordered table-striped table-hover table-set">
												<thead>
											   		<tr>
														<th width="50px">
															<div class="font-size-12 notwrap">选择</div>
															<div class="font-size-9 notwrap">Choice</div>
														</th>
														<th width="50px">
															<div class="font-size-12 notwrap">节点</div>
															<div class="font-size-9 notwrap">Node</div>
														</th>
														<th width="100px">
															<div class="important">
																<div class="font-size-12 notwrap">ATA章节号</div>
																<div class="font-size-9 notwrap">ATA</div>
															</div>
														</th>
														<th width="200px">
															<div class="important">
																<div class="font-size-12 notwrap">英文名称</div>
																<div class="font-size-9 notwrap">F.Name</div>
															</div>
														</th>
														<th width="100px">
															<div class="important">
																<div class="font-size-12 notwrap">中文名称</div>
																<div class="font-size-9 notwrap">CH.Name</div>
															</div>
														</th>
														<th width="100px">
															<div class="important">
																<div class="font-size-12 notwrap">件号</div>
																<div class="font-size-9 notwrap">P/N</div>
															</div>
														</th>
														<th width="100px">
															<div class="important">
																<div class="font-size-12 notwrap">序列号</div>
																<div class="font-size-9 notwrap">S/N</div>
															</div>
														</th>
														<th width="100px">
															<div class="important">
																<div class="font-size-12 notwrap">内部识别码</div>
																<div class="font-size-9 notwrap">I/N</div>
															</div>
														</th>
														<th width="100px">
															<div class="important">
																<div class="font-size-12 notwrap">厂家件号</div>
																<div class="font-size-9 notwrap">MP/N</div>
															</div>
														</th>
														<th width="50px">
															<div class="font-size-12 notwrap">批次号</div>
															<div class="font-size-9 notwrap">B/N</div>
														</th>
														<th width="50px">
															<div class="font-size-12 notwrap">数量</div>
															<div class="font-size-9 notwrap">Num</div>
														</th>
														<th width="100px">
															<div class="font-size-12 notwrap">部件装机位置</div>
															<div class="font-size-9 notwrap">Location</div>
														</th>
											 		 </tr>
												</thead>
												<tbody id="disassemblyComponentList">
												
												</tbody>
											</table>
											</div>
											<div class=" col-xs-12  text-center" style="margin-top: 0px; margin-bottom: 0px;" id="disassemblyComponentPagination"></div>
										</div>
										<!-- end:table -->
										
									
						     		<div class="clearfix"></div>
						     		<div class="row">
										<div class="col-sm-6 col-xs-12 padding-left-12" style="margin-top: 10px;">
											<span class="col-sm-2 col-xs-3 padding-left-0 padding-right-0">
												<div class="font-size-12 ">拆下单号</div>
												<div class="font-size-9 line-height-18">Record No</div>
											</span>
											<div class="col-sm-8 col-xs-8">
												<input class="form-control" type="text" id="disassemblyComponent_cxdh" maxlength="50"/>
											</div>
										</div>
						     		</div>
									<div class="row">
										<div class="col-sm-12 col-xs-12 padding-left-12" style="margin-top: 10px;">
											<span class="col-sm-1 padding-left-0 padding-right-0">
												<div class="font-size-12 ">拆下备注</div>
												<div class="font-size-9 line-height-18">Disassembly Remark</div>
											</span>
											<div class="col-sm-11 padding-right-0" style="margin-left: -2px;">
												<textarea class="form-control" rows="2" maxlength="300" id="disassemblyComponent_cxbz"></textarea>
											</div>
										</div>
						     		</div>
									
							 	 </div>
							 </div> 
							 
							 <div class="modal-footer" style="text-align: right">
							 	<button type="button" onclick="setLoadingList()" class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
		<!-------拆下件放大镜 End-------->
		
		
		<!-------装上件放大镜 Start-------->
		<div class="modal fade" id="mountComponentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg" style="width: 1350px;">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="mountComponentModalBody">
						<div class="panel panel-primary">
							<div class="panel-heading  padding-top-3 padding-bottom-1">
								<div class="font-size-16 line-height-18">装上件信息</div>
								<div class="font-size-9 ">Assembly List</div>
							</div>
							<div class="panel-body padding-top-0 padding-bottom-0">
				            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
								    <div class="text-left margin-top-10">
								    	<input type="hidden" id="hidden_mountComponent" />
								    	<input type="hidden" id="hidden_hcId" />
								    	<input type="hidden" id="tso_zs" />
								    	<input type="hidden" id="tsn_zs" />
								    	<div class="row margin-bottom-10  margin-left-0 margin-right-0">
								    		<div class="col-sm-3">
										   </div>
								    		<div class="col-xs-5 col-xs-offset-4">
										    	<div class="input-group" style="display: none;">
												  <input type="text" class="form-control " id="component_mount_search" placeholder="中英文名称/件号/序列号/批次号"/>
											      <span class="input-group-btn">
											        <button class="btn btn-primary  padding-top-1 padding-bottom-1 pull-right" type="button" onclick="searchOutStock()">
											        	<div class="font-size-12">搜索</div>
														<div class="font-size-9">Search</div>
													</button>
											      </span>
											    </div>
											    
											    <button class="btn btn-primary padding-top-1 padding-bottom-1 pull-right" type="button" onclick="searchOutStockModal()">
						                     		<div class="font-size-12">选择外场库存</div>
													<div class="font-size-9">Choose Out Stock</div>
												</button>
								    		</div>
								    	</div>
					         		</div>
									<div class="clearfix"></div>
									<div class="col-lg-12">
										<div class="tab-pane fade in active" id="planeLoadingList">
								         <div id="treeView">
								          <div class="row">
											<div class="col-sm-12 padding-left-0 padding-right-0">
											<form id="componentForm">
											<div class="row">
											<div class="col-sm-12">
												<div class="panel panel-default">
												  <div class="panel-heading col-xs-12 margin-bottom-10 padding-right-0">
													    <h3 class="panel-title" >
													    部件基本信息 Base info
													    </h3>
												  </div>
												  <div class="panel-body">
												  	 
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">机型</div>
																<div class="font-size-9 line-height-18">Model</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="fjjx_zs" name="fjjx_zs" disabled="disabled">
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">飞机注册号</div>
																<div class="font-size-9 line-height-18">A/C REG</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="fjzch_zs" name="fjzch_zs" disabled="disabled">
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18"><span style="color: red">*</span>ATA章节号</div>
																<div class="font-size-9 line-height-18">ATA</div></label>
															<div class=" col-xs-8 padding-left-8 padding-right-0">
																<div class="input-group">
																	<input class="form-control" id="zjh_show_zs" name="zjh_show_zs" type="text" disabled="disabled">
																	<input id="zjh_zs" name="zjh_zs" type="hidden">
												                    <span class="input-group-btn">
																		<button id="main_chapter_btn" class="btn btn-primary form-control" type="button" onclick="openChapterWin();">
																		<i class="icon-search"></i>
																		</button>
												                    </span>
												                </div>
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18"><span style="color: red">*</span>件号</div>
																<div class="font-size-9 line-height-18">P/N</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="jh_zs" name="jh_zs" maxlength="50">
															</div>
														</div>
														<div class="clearfix"></div>
														
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">厂家件号</div>
																<div class="font-size-9 line-height-18">MP/N</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="cjjh_zs" name="cjjh_zs" maxlength="50">
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">序列号</div>
																<div class="font-size-9 line-height-18">S/N</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="xlh_zs" name="xlh_zs" maxlength="50" onkeyup="limitCount()">
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">英文名称</div>
																<div class="font-size-9 line-height-18">F.Name</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="ywms_zs" name="ywms_zs" maxlength="300">
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">中文名称</div>
																<div class="font-size-9 line-height-18">CH.Name</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="zwms_zs" name="zwms_zs" maxlength="100">
															</div>
														</div>
														<div class="clearfix"></div>
														
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">内部识别码</div>
																<div class="font-size-9 line-height-18">I/N</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="nbsbm_zs" name="nbsbm_zs" maxlength="50">
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">位置</div>
																<div class="font-size-9 line-height-18">Location</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<select class='form-control' id='wz_zs' name="wz_zs">
																	<option value="0">机身 Fuselage</option>
																	<option value="1">1#左发 L/Engine</option>
																	<option value="2">2#右发 R/Engine</option>
																	<option value="5">外吊挂 E/S</option>
																	<option value="4">搜索灯 Search</option>
																	<option value="3">绞车 Winch</option>
																</select>
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">许可证号</div>
																<div class="font-size-9 line-height-18">Licence</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="xkzh_zs" name="xkzh_zs" maxlength="50">
															</div>
														</div>
													 	<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">适航证号</div>
																<div class="font-size-9 line-height-18">Certificate</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="shzh_zs" name="shzh_zs" maxlength="50">
															</div>
														</div>
														<div class="clearfix"></div>
														
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">批次号</div>
																<div class="font-size-9 line-height-18">B/N</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="pch_zs" name="pch_zs" maxlength="50">
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">数量</div>
																<div class="font-size-9 line-height-18">Num</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="zjsl_zs" name="zjsl_zs" maxlength="6">
																<input type="hidden" id="zjsl_max">
															</div>
														</div>
														<div class="clearfix"></div>
													 	<div class="col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group">
															<label class="col-xs-1 text-right padding-left-0 padding-right-0">
																<div class="font-size-12 line-height-18">改装记录</div>
																<div class="font-size-9 line-height-18">Record</div>
															</label>
															<div class="col-xs-11 padding-left-8 padding-right-0">
																<textarea rows="1" name="bjgzjl_zs" class="form-control" id="bjgzjl_zs" maxlength="500"></textarea>
															</div>
														</div>
														<div class="col-sm-12 col-xs-12  padding-left-0 padding-right-0">
															<label class="col-xs-1 text-right padding-left-0 padding-right-0">
																<div class="font-size-12 line-height-18">备注</div>
																<div class="font-size-9 line-height-18">Remark</div>
															</label>
															<div class="col-xs-11 padding-left-8 padding-right-0">
																<textarea rows="1" id="bz_zs" name="bz_zs" class="form-control" maxlength="300"></textarea>
															</div>
														</div>
												  </div>
												</div>
								          </div>
								          
								          <div class="col-sm-12">
											  <div class="panel panel-default">
											  <div class="panel-heading">
											    <h3 class="panel-title">设置上下级部件关联 Set Relation Node</h3>
											  </div>
											  <div class="panel-body">
											  	<div class="col-sm-6 padding-left-0 padding-right-0 form-group" >
													<label  class="col-xs-2 text-right padding-left-0 padding-right-0"><div
															class="font-size-12 line-height-18"><span style="color: red">*</span>上级部件</div>
														<div class="font-size-9 line-height-18">Parent</div></label>
													<div class=" col-xs-10 padding-left-8 padding-right-0">
										                <input style="border:none;border-bottom:#ccc solid 1px;height:24px;
																width : 100%;margin-top:10px;" id="parent_show_zs" type="text" readonly="readonly">
														<input id="parent_zs" name="parent_zs" type="hidden">
													</div>
												</div>
												<div class="col-sm-6 padding-left-10 padding-right-0 form-group" >
													<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="openChooseParent()" id="main_parent_btn_zs">
														<div class="font-size-12">选择</div>
														<div class="font-size-9">Choose</div>
													</button>
												</div>
												<div class="clearfix"></div>
												<div class="col-sm-6 padding-left-0 padding-right-0 form-group" >
													<label class="col-xs-2 text-right padding-left-0 padding-right-0">
														<div class="font-size-12 line-height-18">下级部件</div>
														<div class="font-size-9 line-height-18">Children</div></label>
													<div class="col-xs-10 padding-left-8 padding-right-0" id="childenList_show">
														<input style="border:none;border-bottom:#ccc solid 1px;height:24px;
																width : 100%;margin-top:10px;" id="" type="text" readonly="readonly">
													</div>
												</div>
												<div class="col-sm-6 padding-left-10 padding-right-0 form-group" >
													<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" onclick="showSubcomponentModal()" id="showSubcomponentBtn">
														<div class="font-size-12">选择</div>
														<div class="font-size-9">Choose</div>
													</button>
												</div>
											  </div>
											  </div>
										  </div>
								          
								          <div class="col-sm-12">
												<div class="panel panel-default">
												  <div class="panel-heading">
												    <h3 class="panel-title">设置监控信息 Set up Monitor</h3>
												  </div>
												  <div class="panel-body padding-bottom-0">
												 
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">生产日期</div>
																<div class="font-size-9 line-height-18">Manufacture date</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																	<input class="form-control date-picker" name="scrq_zs" data-date-format="yyyy-mm-dd" type="text"  id="scrq_zs" maxlength="10"/>
																</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">安装日期</div>
																<div class="font-size-9 line-height-18">Install date</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input class="form-control date-picker" name="azrq_zs" data-date-format="yyyy-mm-dd" type="text"  id="azrq_zs" maxlength="10"/>
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">安装记录单号</div>
																<div class="font-size-9 line-height-18">Assembly Record</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="azjldh_zs" name="azjldh_zs" maxlength="50">
															</div>
														</div>
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group">
															<label class="col-xs-4 text-right padding-left-0 padding-right-0">
																<div class="font-size-12 line-height-18">控制类型</div>
																<div class="font-size-9 line-height-18">Control type</div>
															</label>
															<div class="col-xs-8 padding-left-8 padding-right-0">
																<select class='form-control' id='kzlx_zs'  onchange="reValidateXlh()">
																	<option value="1">时控件 Time</option>
																	<option value="2">时寿件 Life</option>
																	<option value="3">非控制件 Non control</option>
																</select>
															</div>
														</div>
														<!-- <div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">拆除日期</div>
																<div class="font-size-9 line-height-18">Remove date</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input class="form-control date-picker" name="ccrq_zs" data-date-format="yyyy-mm-dd" type="text"  id="ccrq_zs" maxlength="10"/>
															</div>
														</div> -->
														<div class="clearfix"></div>
														
														<!-- <div class="col-sm-3 padding-left-0 padding-right-0 form-group" >
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																	class="font-size-12 line-height-18">拆除记录单号</div>
																<div class="font-size-9 line-height-18">Disassembly Record</div></label>
																<div class=" col-xs-8 padding-left-8 padding-right-0">
																<input type="text" class="form-control"  id="ccjldh_zs" name="ccjldh_zs" maxlength="50">
															</div>
														</div> -->
														<div class="col-sm-3 padding-left-0 padding-right-0 form-group">
															<label class="col-xs-4 text-right padding-left-0 padding-right-0">
																<div class="font-size-12">履历卡类型</div>
																<div class="font-size-9">Career type</div>
															</label>
															<div
																class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
																<select class='form-control' id='llklx_zs'>
																	<option value="1">无履历卡 No Career</option>
																	<option value="2">原装履历卡 Original</option>
																	<option value="3">自制履历卡 Self-control</option>
																</select>
															</div>
														</div>
														<div class="col-xs-3 padding-left-0 padding-right-0 form-group">
															<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
																class="font-size-12 line-height-18">履历卡编号</div>
															<div class="font-size-9 line-height-18">Career No.</div></label>
															<div class=" col-xs-8 padding-left-8 padding-right-0">
															<input class="form-control" name="llkbh_zs" type="text"  id="llkbh_zs" maxlength="50"/>
															</div>
														</div>
														<div class="col-xs-3 padding-left-0 padding-right-0 form-group">
															<label class="col-xs-4 text-right padding-left-0 padding-right-0">
																<div class="font-size-12">是否定检</div>
																<div class="font-size-9">Is P/I</div>
															</label>
															<div
																class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
																<select class='form-control' id='isDj_zs'  onchange="reValidateXlh()">
																	<option value="1">是 Yes</option>
																	<option value="0">否 No</option>
																</select>
															</div>
														</div>
														<div class="clearfix"></div>
												
												
												  </div>
												  
												  </div>
												</div>
								          </div>
								          </div>
								          </form>
								        </div>
								        </div>
			        				</div>
									
									</div>
				                	<div class="text-right margin-top-10 margin-bottom-10">
										<button type="button" onclick="setMountComponent()" class="btn btn-primary padding-top-1 padding-bottom-1">
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
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
		<!-------装上件放大镜 End-------->
		
		
		<!-------ATA章节号对话框 Start-------->
		<div class="modal fade" id="alertModalChapterList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 50000 ! important;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalChapterListBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">ATA章节列表</div>
							<div class="font-size-9 ">ATA list</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-xs-12">
			            	
			                    <div class="clearfix"></div>	
							    <div class="text-left margin-top-10">
							    	<div style="padding-right:9px!important;" class="col-xs-8 pull-left">
										<input type="text" placeholder='Please enter a keyword...' id="keyword_chapter_search" class="form-control ">
									</div>
				                    <div style="padding-left:0!important;" class="col-xs-1 pull-left">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchChapterRevision();">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   		</button>
				                    </div>
							    
									<div class="clearfix"></div>			
				         		</div>
				                
								<!-- start:table -->
								<div class="margin-top-10 ">
								<div class="overflow-auto">
									<table class="table table-bordered table-striped table-hover" style="">
										<thead>
									   		<tr>
												<th width="50px">
													<div class="font-size-12 notwrap">选择</div>
													<div class="font-size-9 notwrap">Choice</div>
												</th>
												<th>
													<div class="font-size-12 notwrap">ATA章节号</div>
													<div class="font-size-9 notwrap">ATA</div>
												</th>
												<th>
													<div class="font-size-12 notwrap">ATA章节名称</div>
													<div class="font-size-9 notwrap">ATA Name</div>
												</th>
									 		 </tr>
										</thead>
										<tbody id="chapterList">
										
										</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="chapterPagination">
										</ul>
									</div>
								</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="setModelChapterData()"
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
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-------ATA章节号对话框 End-------->
	
	
	<!-------维护子部件关联 Start-------->
	<div class="modal fade" id="subcomponentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" style="width: 1200px;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">待选择子部件</div>
						<div class="font-size-9 ">Choice Sub component</div>
					</div>
					
					<div class="text-left">
				    	
				    	<div class="pull-right padding-right-0 margin-bottom-10 padding-top-10">
							<div class="pull-left padding-left-5 padding-right-0" style="width: 250px;">
								<input type="text" class="form-control " id="subcomponent_search" placeholder="件号/序列号/中英文名称/ATA章节号/内部识别码/厂家件号"/>
							</div>
		              		<div class="pull-right padding-left-5 padding-right-0">
		              			<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchSubcomponent();">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
								</button>     
								<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1" id="btn" onclick="moreSubcomponent();">
									<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
									<div class="pull-left padding-top-5">
									 &nbsp;<i class="icon-caret-down font-size-15" id="iconSubcomponent"></i>
									</div>
						   		</button>
		                    </div>    
						</div>
						
						<div class="col-xs-12 triangle  padding-top-10 margin-bottom-10 margin-top-10 display-none border-cccccc" id="divSearchSubcomponent">
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">位置</div>
									<div class="font-size-9">Location</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<select class='form-control' id='wz_main'>
										<option value="">请选择 Choice</option>
									</select>
								</div>
							</span>
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">生产日期</div>
									<div class="font-size-9">Manufacture date</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<input class="form-control date-range-picker" data-date-format="yyyy-mm-dd" type="text"  id="scrq_main"/>
								</div>
							</span>
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">安装记录单号</div>
									<div class="font-size-9">Assembly record</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="azjldh_main"/>
								</div>
							</span>
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">安装日期</div>
									<div class="font-size-9">Install date</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<input class="form-control date-range-picker" data-date-format="yyyy-mm-dd" type="text"  id="azrq_main"/>
								</div>
							</span>
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">拆除记录单号</div>
									<div class="font-size-9">Disassembly record</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="ccjldh_main"/>
								</div>
							</span>
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">拆除日期</div>
									<div class="font-size-9">Remove date</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<input class="form-control date-range-picker" data-date-format="yyyy-mm-dd" type="text"  id="ccrq_main"/>
								</div>
							</span>
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">履历卡类型</div>
									<div class="font-size-9">Choice type</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<select class='form-control' id='llklx_main'>
										<option value="">请选择 Choice</option>
										<option value="1">无履历卡 No Choice</option>
										<option value="2">原装履历卡 Original</option>
										<option value="3">自制履历卡 Self-control</option>
									</select>
								</div>
							</span>
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">履历卡编号</div>
									<div class="font-size-9">Choice No.</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="llkbh_main"/>
								</div>
							</span>
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">备注</div>
									<div class="font-size-9">Remark</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="bz_main"/>
								</div>
							</span>
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">控制类型</div>
									<div class="font-size-9">Control type</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<select class='form-control' id='kzlx_main'>
										<option value="">请选择 Choice</option>
										<option value="1">时控件 Time</option>
										<option value="2">时寿件 Life</option>
										<option value="3">非控制件 Non control</option>
									</select>
								</div>
							</span>
							<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">是否定检</div>
									<div class="font-size-9">Is P/I</div>
								</span>
								<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<select class='form-control' id='isDj_main'>
										<option value="">请选择 Choice</option>
										<option value="1">是 Yes</option>
										<option value="0">否 No</option>
									</select>
								</div>
							</span>
							<div class="col-xs-12  text-right" style="margin-bottom: 10px;">
								<button type="button"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									onclick="subcomponentSearchreset();">
									<div class="font-size-12">重置</div>
									<div class="font-size-9">Reset</div>
								</button>
							</div> 
						</div>		
	         		</div>
					
					<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
							<thead>
							<tr>
								<th width="6%">
									<div class="font-size-12 line-height-18" >选择</div>
									<div class="font-size-9 line-height-18" >Choice</div>
								</th>
								<th width="12%">
									<div class="important">
										<div class="font-size-12 line-height-18" >ATA章节号 </div>
										<div class="font-size-9 line-height-18" >ATA</div>
									</div>
								</th>
								<th width="22%">
									<div class="important">
										<div class="font-size-12 line-height-18" >英文名称</div>
										<div class="font-size-9 line-height-18" >F.Name</div>
									</div>
								</th>
								<th width="14%">
									<div class="important">
										<div class="font-size-12 line-height-18" >中文名称</div>
										<div class="font-size-9 line-height-18" >CH.Name</div>
									</div>
								</th>
								<th width="12%">
									<div class="important">
										<div class="font-size-12 line-height-18" >件号</div>
										<div class="font-size-9 line-height-18" >P/N</div>
									</div>
								</th>
								<th width="12%">
									<div class="important">
										<div class="font-size-12 line-height-18" >序列号</div>
										<div class="font-size-9 line-height-18" >S/N</div>
									</div>
								</th>
								<th width="12%">
									<div class="important">
										<div class="font-size-12 line-height-18" >厂家件号</div>
										<div class="font-size-9 line-height-18" >MP/N</div>
									</div>
								</th>
								<th width="4%">
									<div class="font-size-12 line-height-18" >数量</div>
									<div class="font-size-9 line-height-18" >Num</div>
								</th>
								<th width="6%">
									<div class="font-size-12 line-height-18" >位置</div>
									<div class="font-size-9 line-height-18" >Location</div>
								</th>
							</tr> 
			         		 </thead>
							<tbody id="subcomponentList">
							</tbody>
					</table>
					<div class="col-xs-12  text-center page-height padding-right-0 padding-left-0" style="margin-top: 0px; margin-bottom: 0px;" id="subcomponentPagination"></div>
					<hr/>
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">已选择子部件</div>
						<div class="font-size-9 ">Selected Sub component</div>
					</div>
					<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
							<thead>
							<tr>
							<th width="6%"><div class="font-size-12 line-height-18" >选择</div><div class="font-size-9 line-height-18" >Choice</div></th>
							<th width="12%"><div class="font-size-12 line-height-18" >ATA章节号 </div><div class="font-size-9 line-height-18" >ATA</div></th>
							<th width="22%"><div class="font-size-12 line-height-18" >英文名称</div><div class="font-size-9 line-height-18" >F.Name</div></th>
							<th width="14%"><div class="font-size-12 line-height-18" >中文名称</div><div class="font-size-9 line-height-18" >CH.Name</div></th>
							<th width="12%"><div class="font-size-12 line-height-18" >件号</div><div class="font-size-9 line-height-18" >P/N</div></th>
							<th width="12%"><div class="font-size-12 line-height-18" >序列号</div><div class="font-size-9 line-height-18" >S/N</div></th>
							<th width="12%"><div class="font-size-12 line-height-18" >厂家件号</div><div class="font-size-9 line-height-18" >MP/N</div></th>
							<th width="4%"><div class="font-size-12 line-height-18" >数量</div><div class="font-size-9 line-height-18" >Num</div></th>
							<th width="6%"><div class="font-size-12 line-height-18" >位置</div><div class="font-size-9 line-height-18" >Location</div></th>
							</tr> 
			         		 </thead>
							<tbody id="chooseList">
							</tbody>
					</table>
				<div class="modal-footer">
					<button type="button" onclick="chooseSubcomponent()"
						class="btn btn-primary padding-top-1 padding-bottom-1">
						<div class="font-size-12">确定</div>
						<div class="font-size-9">Confirm</div>
					</button>
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1"
						data-dismiss="modal">
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
			</div>
		</div>
	</div>
	<!-------维护子部件关联框 End-------->
	
	<!-------时控件设置 Start-------->
	<%@ include file="/WEB-INF/views/flightdata/flightrecordsheet/timeMonitor.jsp"%>
	<!-------时控件设置 End-------->
	
	<!-------定检件设置 Start-------->
	<%@ include file="/WEB-INF/views/flightdata/flightrecordsheet/fixedMonitor.jsp"%>
	<!-------定检件设置 End-------->
	
	<!-------弱验证消息框 Start-------->
	<div class="modal fade" id="validateModal_weak" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="validateModalBody_weak">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger padding-top-1 padding-bottom-1" data-dismiss="modal" onclick="saveTemp()" id="continueBtn">
						<div class="font-size-12">继续</div>
						<div class="font-size-9">Close</div>
						<input type="hidden" id="warn_ensure_hid"/>
					</button>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------弱验证消息框 End-------->
	
	<!-------强验证消息框 Start-------->
	<div class="modal fade" id="validateModal_strong" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="validateModalBody_strong">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------强验证消息框 End-------->
	
	<!-------撤销模态框 Start-------->
	<div class="modal fade" id="cancelModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="cancelModalBody">
					<div class="row">
						<div class="col-sm-12 col-xs-12 form-group padding-right-0 margin-left-10 padding-top-10">
							<label class="col-xs-2 text-left padding-left-10 padding-right-0" style="vertical-align:middle">
								<div class="font-size-12 line-height-18">飞行记录单号</div>
								<div class="font-size-9 line-height-18">Flight Record No.</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<span class="font-size-16">${flightrecordsheet.fxjldh}</span>
							</div>
						</div>
						<div class="col-sm-12 col-xs-12 form-group padding-right-0 margin-left-10">
							<label class="col-xs-2 text-left padding-left-10 padding-right-0" style="vertical-align:middle">
								<div class="font-size-12 line-height-18">撤销原因</div>
								<div class="font-size-9 line-height-18">Reason</div>
							</label>
							<div class="col-xs-9 padding-left-8 padding-right-0">
								<textarea id="zdjsyy" class="form-control" rows="3" maxlength="166"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" onclick="cancel()">
						<div class="font-size-12">确定</div>
						<div class="font-size-9">Confirm</div>
					</button>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------撤销模态框 End-------->
	
	
	<!-------附件模态框 Start-------->
	<div class="modal fade" id="attachmentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">维护附件</div>
							<div class="font-size-9 ">Maintain Attachment</div>
						</div>
						<input type="hidden" id="attachment_id">
						<input type="hidden" id="attachment_rwdid">
						<div class="panel-body padding-top-10 padding-bottom-0" >
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<span class="col-xs-3 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
									<div class="font-size-9 line-height-18">File Description</div></span>
								 <div class="col-xs-5 padding-left-8 padding-right-0" >
									<input type="text" id="wbwjm_task" name="wbwjm"  maxlength="90" class="form-control "  >
								</div>
							     <div class=" col-lg-4 col-sm-4 col-xs-4  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<div id="fileuploader_task" class="col-lg-2 col-sm-2 col-xs-12 "  style="margin-left: 5px ;padding-left: 0"></div>
								</div> 
							</div>
							
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0">
								<table class="table-set table table-bordered table-striped table-hover text-center">
									<thead>
										<tr>
											<th style="width:52px;"><div class="font-size-12 line-height-18 " >操作</div>
												<div class="font-size-9 line-height-18">Operation</div></th>
											<th style="width:260px;" id="task_sp">
												<div class="font-size-12 line-height-18">文件说明</div>
												<div class="font-size-9 line-height-18">Description</div>
											</th>
											<th style="width:80px;">
											<div class="font-size-12 line-height-18">文件大小</div>
												<div class="font-size-9 line-height-18">File Size</div>
											</th>
											<th style="width:100px;"><div class="font-size-12 line-height-18">上传人</div>
												<div class="font-size-9 line-height-18">Uploader</div></th>
											<th style="width:140px;"><div class="font-size-12 line-height-18">上传时间</div>
												<div class="font-size-9 line-height-18">Upload Time</div></th>					
										</tr>
									</thead>
									    <tbody id="filelist_task">
											 <tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>
										</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-------附件模态框 End-------->
	
	<!-- 修改完成工作模态框Start -->
	<div class="modal fade" id="editFinishedWorkModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<div class="font-size-16 line-height-18">完成工作</div>
					<div class="font-size-9 ">Finished Task</div>
				</div>
				<div class="modal-body">
					<input type="hidden" id="editFinishedWorkModal_rowId"/>
					<div class="row margin-top-10 margin-right-10">
						<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0">
							<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">来源类型</div>
								<div class="font-size-9 line-height-18">Source Type</div>
							</span>
							<div class="col-sm-8 col-xs-8 ">
								<input class="form-control" disabled="disabled" type="text" id="finishedWorkModal_lylx">
								<select class="form-control" id="finishedWorkModal_lylx_select">
									<option value="2">非例行-附加工单</option>
									<option value="3">非例行-排故工单</option>
								</select>
							</div>
						</div>
						<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0" id="rwdh_div">
							<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">任务单号</div>
								<div class="font-size-9 line-height-18">Task No.</div>
							</span>
							<div class="col-sm-8 col-xs-8 ">
								<input class="form-control" disabled="disabled" type="text" id="finishedWorkModal_rwdh" maxlength="20">
							</div>
						</div>
					</div>
					<div class="row margin-top-10 margin-right-10">
						<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0">
							<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">工作项目保留单号</div>
								<div class="font-size-9 line-height-18">Work retention No.</div>
							</span>
							<div class="col-sm-8 col-xs-8 ">
								<input class="form-control" type="text" id="finishedWorkModal_gzxmbldh" maxlength="50">
							</div>
						</div>
						<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0">
							<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">责任人</div>
								<div class="font-size-9 line-height-18">Operator</div>
							</span>
							<div class="col-sm-8 col-xs-8 ">
								<div class="input-group">
									<input class="form-control" id="finishedWorkModal_zrr" disabled="disabled" type="text">
									<input class="form-control" id="finishedWorkModal_zrrid" type="hidden">
									<span class="input-group-btn">
										<button class="btn btn-primary form-control" type="button" onclick="showUserList();">
											<i class="icon-search"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="row margin-top-10 margin-right-10">
						<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0">
							<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">航段</div>
								<div class="font-size-9 line-height-18">Leg</div>
							</span>
							<div class="col-sm-8 col-xs-8 ">
								<select class="form-control" id="finishedWorkModal_leg">
								</select>
							</div>
						</div>
						<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0">
							<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">实际工时</div>
								<div class="font-size-9 line-height-18">Actual Hrs</div>
							</span>
							<div class="col-sm-8 col-xs-8 ">
								<input class="form-control pull-left input-sm" name="sjgsRs" id="finishedWorkModal_rx" onkeyup="sjrsKeyup(this)" style="width: 50px" maxlength="10" type="text">
								<span style="float:left; height:25px; line-height:30px;">人x</span>
								<input class="form-control pull-left input-sm" name="sjgsXss" id="finishedWorkModal_xss" onkeyup="sjrsKeyup(this)" style="width: 50px" maxlength="10" type="text">
								<span style="padding-right:10px; margin-left:1px; float:left; line-height:30px;">时  = </span>
								<span name="totalTime" id="finishedWorkModal_total" style="float:left; line-height:30px;"></span>
							</div>
						</div>
					</div>
					<div class="row margin-top-10 margin-right-10">
						<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">任务相关信息</div>
							<div class="font-size-9 line-height-18">Task Desc</div>
						</span>
						<div class="col-sm-10 col-xs-8">
							<textarea class="form-control" id="finishedWorkModal_rwxgxx" disabled="disabled" rows="5" maxlength="1000"></textarea>
						</div>
					</div>
					<div class="row margin-top-10 margin-right-10">
						<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">故障信息</div>
							<div class="font-size-9 line-height-18">Fault Message</div>
						</span>
						<div class="col-sm-10 col-xs-8">
							<textarea class="form-control" id="finishedWorkModal_gzxx" rows="5" maxlength="1000"></textarea>
						</div>
					</div>
					<div class="row margin-top-10 margin-right-10">
						<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">处理措施</div>
							<div class="font-size-9 line-height-18">Measure</div>
						</span>
						<div class="col-sm-10 col-xs-8">
							<textarea class="form-control" id="finishedWorkModal_clcs" rows="5" maxlength="1000"></textarea>
						</div>
					</div>
					<div class="row margin-top-10 margin-left-10 margin-right-10">
						<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<span class="col-xs-3 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
								<div class="font-size-9 line-height-18">File Description</div></span>
							 <div class="col-xs-5 padding-left-8 padding-right-0" >
								<input type="text" id="wbwjm_task_all" name="wbwjm"  maxlength="90" class="form-control">
							</div>
						     <div class=" col-lg-4 col-sm-4 col-xs-4  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<div id="fileuploader_task_all" class="col-lg-2 col-sm-2 col-xs-12 "  style="margin-left: 5px ;padding-left: 0"></div>
							</div> 
						</div>
						
						<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0">
							<table class="table-set table table-bordered table-striped table-hover text-center">
								<thead>
									<tr>
										<th style="width:26px;"><div class="font-size-12 line-height-18 " >操作</div>
											<div class="font-size-9 line-height-18">Operation</div></th>
										<th style="width:130px;" id="task_sp_all">
											<div class="font-size-12 line-height-18">文件说明</div>
											<div class="font-size-9 line-height-18">Description</div>
										</th>
										<th style="width:40px;">
										<div class="font-size-12 line-height-18">文件大小</div>
											<div class="font-size-9 line-height-18">File Size</div>
										</th>
										<th style="width:50px;"><div class="font-size-12 line-height-18">上传人</div>
											<div class="font-size-9 line-height-18">Uploader</div></th>
										<th style="width:70px;"><div class="font-size-12 line-height-18">上传时间</div>
											<div class="font-size-9 line-height-18">Upload Time</div></th>					
									</tr>
								</thead>
								    <tbody id="filelist_task_all">
										 <tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>
									</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" onclick="saveFinishedWork()">
						<div class="font-size-12">确定</div>
						<div class="font-size-9">Confirm</div>
					</button>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
						<div class="font-size-12">关闭</div>
						<div class="font-size-9">Close</div>
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<!-- 修改完成工作模态框End -->
	
	<!-------外场库存放大镜 Start-------->
		<div class="modal fade" id="outStockModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg" style="width: 1350px;">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="outStockModalBody">
						<div class="panel panel-primary">
							<div class="panel-heading  padding-top-3 padding-bottom-1">
								<div class="font-size-16 line-height-18">外场库存列表</div>
								<div class="font-size-9 ">Out Stock List</div>
							</div>
							<div class="panel-body padding-top-0 padding-bottom-0">
				            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
								    <div class="text-left margin-top-10">
								    	<div class="pull-right padding-right-0">
											<div class="pull-left padding-left-5 padding-right-0" style="width: 250px;">
												<input type="text" class="form-control" id="out_stock_search" placeholder="件号/序列号/中英文名称/批次号"/>
											</div>
						              		<div class="pull-right padding-left-5 padding-right-0">
						              			<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchOutStockList();">
													<div class="font-size-12">搜索</div>
													<div class="font-size-9">Search</div>
												</button>     
												<button style="display: none;" type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1" onclick="moreOutStock();">
													<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
													<div class="pull-left padding-top-5">
													 &nbsp;<i class="icon-caret-down font-size-15" id="iconOutStock"></i>
													</div>
										   		</button>
						                    </div>    
										</div>
								    	
										<div class="col-xs-12 triangle  padding-top-10 margin-bottom-10 margin-top-10 display-none border-cccccc" id="divSearchOutStock">
											<span class="col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
												<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
													<div class="font-size-12">位置</div>
													<div class="font-size-9">Location</div>
												</span>
												<div class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
													<select class='form-control' id='wz_outStock'>
														<option value="">请选择Choice</option>
														<option value="0">机身 Fuselage</option>
														<option value="1">1#左发 L/Engine</option>
														<option value="2">2#右发 R/Engine</option>
														<option value="5">外吊挂 E/S</option>
														<option value="4">搜索灯 Search</option>
														<option value="3">绞车 Winch</option>
													</select>
												</div>
											</span>
											<div class="col-xs-12  text-right" style="margin-bottom: 10px;">
												<button type="button"
													class="btn btn-primary padding-top-1 padding-bottom-1"
													onclick="outStockSearchreset();">
													<div class="font-size-12">重置</div>
													<div class="font-size-9">Reset</div>
												</button>
											</div> 
										</div>		
					         		</div>
								<!-- start:table -->
								<div class="clearfix"></div>
									<div class="margin-top-10 ">
										<div class="overflow-auto">
											<table class="table table-bordered table-striped table-hover table-set">
												<thead>
											   		<tr>
														<th width="50px">
															<div class="font-size-12 notwrap">选择</div>
															<div class="font-size-9 notwrap">Choice</div>
														</th>
														<th width="200px">
															<div class="important">
																<div class="font-size-12 notwrap">英文名称</div>
																<div class="font-size-9 notwrap">F.Name</div>
															</div>
														</th>
														<th width="100px">
															<div class="important">
																<div class="font-size-12 notwrap">中文名称</div>
																<div class="font-size-9 notwrap">CH.Name</div>
															</div>
														</th>
														<th width="100px">
															<div class="important">
																<div class="font-size-12 notwrap">件号</div>
																<div class="font-size-9 notwrap">P/N</div>
															</div>
														</th>
														<th width="100px">
															<div class="important">
																<div class="font-size-12 notwrap">序列号</div>
																<div class="font-size-9 notwrap">S/N</div>
															</div>
														</th>
														<th width="50px">
															<div class="font-size-12 notwrap">批次号</div>
															<div class="font-size-9 notwrap">B/N</div>
														</th>
														<th width="50px">
															<div class="font-size-12 notwrap">数量</div>
															<div class="font-size-9 notwrap">Num</div>
														</th>
											 		 </tr>
												</thead>
												<tbody id="outStockList">
												
												</tbody>
											</table>
											</div>
											<div class=" col-xs-12  text-center" style="margin-top: 0px; margin-bottom: 0px;" id="outStockPagination"></div>
										</div>
										<!-- end:table -->
										
							 	 </div>
							 </div> 
							 
							 <div class="modal-footer" style="text-align: right">
							 	<button type="button" onclick="setOutStock()" class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
		<!-------外场库存放大镜 End-------->
		
		<!-------选择父节点 Start-------->
		<div class="modal fade" id="chooseParent" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria- hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="chooseParentBody">
						<!-- 装机清单树-->
						<div class="col-sm-12"  style="padding-right: 0px;">
							<div class="panel panel-default">
								<div class="panel-heading">
								<h3 class="panel-title">选择父节点 Choose Parent</h3>
								</div>
								<div class="panel-body">
									<div class="col-sm-12  padding-left-0 padding-right-0 margin-bottom-10 ">
										<div class="input-group">
											<input class="form-control " id="keyword_search" placeholder="件号/序列号/中英文名称/ATA章节号/内部识别码/厂家件号" type="text"> <span class="input-group-btn">
												<button class="btn btn-default  padding-top-1 padding-bottom-1 pull-right" type="button" onclick="refreshTree('', true)">
													<div class="font-size-12">搜索</div>
													<div class="font-size-9">Search</div>
												</button>
											</span>
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="zTreeDemoBackground">
										<ul id="loadingList_tree" class="ztree" style=" height:430px; overflow: auto; "></ul>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal" onclick="chooseParent()">
							<div class="font-size-12">确认</div>
							<div class="font-size-9">Confirm</div>
							<input type="hidden" id="warn_ensure_hid" />
						</button>
						<button  type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-------选择父节点end-------->
		
</div>
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
<link href="${ctx}/static/js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx}/static/js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/flightrecordsheet/flightrecordsheet_detail.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/flightrecordsheet/searchLoadingList.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/flightrecordsheet/searchMountComponent.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/flightrecordsheet/searchOutStock.js"></script>
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/subTable.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>