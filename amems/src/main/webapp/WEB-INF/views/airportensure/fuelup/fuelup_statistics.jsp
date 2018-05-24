<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>加油统计</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">

<input type="hidden" id="userId" value="" />
<input type="hidden" id="selectType" value="1" />
<input type="hidden" id="selectDate" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode);
});
</script>
<style type="text/css">
.sel{
  background: #dbe2f7;
}
</style>
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
				<div class="col-lg-12 pull-right padding-right-0 row-height">
					
					<div class="col-lg-3 col-sm-3  padding-left-0 padding-right-0 pull-left" >
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12" ><span style="color: red">*</span>日期</div>
							<div class="font-size-9">Date</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker" id="jyrq" readonly />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-3  padding-left-0 padding-right-0 pull-left" >
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12" >基地</div>
							<div class="font-size-9">Base</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jd' name="jd">
										<option value="">显示全部</option>
										<c:choose>
											<c:when test="${baseList!=null && fn:length(baseList) > 0}">
												<c:forEach items="${baseList}" var="base">
													<option value="${base.id}">${erayFns:escapeStr(base.jdms)}</option>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<option value="">暂无基地</option>
											</c:otherwise>
										</c:choose>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-3  padding-left-0 padding-right-0 pull-left" >
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12" >组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" name="dprtcode" class="form-control " onchange="dprtcodeChange()">
								<c:choose>
											<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}"
													<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}
												</option>
											</c:forEach>
											</c:when>
											<c:otherwise>
												<option value="">暂无组织机构 No Organization</option>
											</c:otherwise>
										</c:choose>
							</select>
						</div>
					</div>
					<!-- <div class="col-xs-3 pull-right padding-right-0">
						<div style="padding-right:6px!important;" class="col-xs-10 pull-left row-height" >
							<input type="text" placeholder='飞机机型/飞机注册号/发油人'  id="keyword_search" class="form-control ">
						</div>
	                    <div style="padding-left:0!important;" class="col-xs-2 pull-left">   
							<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
	                    </div>  
					</div> -->
					<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 200px;">
						<input type="text" placeholder='飞机机型/飞机注册号/发油人'  id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div> 
				</div>
					
					
					</div>
					<div class="clearfix"></div>
					
					<div class="pull-left padding-left-0 padding-right-0" >   
                   		<label style=' font-weight:normal'><input type="radio" name="viewType" id="viewType" value="1" checked='checked'  onclick="selectFl()" />
                   		飞机加油量</label>
                   		&nbsp;
                   		&nbsp;
                   		<label style=' font-weight:normal'><input type="radio" name="viewType" id="viewType" value="2"    onclick="selectFl()" />
                   		发油人发油量</label>
                   		&nbsp;
                   		&nbsp;
                    </div>

					<div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-10">
                	<ul id="myTab" class="nav nav-tabs">
                      	<li class="active"><a id="aaa" href="#Dropdown" data-toggle="tab" aria-expanded="false" onclick="searchRevisionDetailYf()">明细</a></li>
                      	<li class=""><a href="#profile" data-toggle="tab" aria-expanded="false" onclick="selectSummary()">汇总</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content" >
                      	
                      	<div class="tab-pane active" id="Dropdown"  >
                      		<div class="padding-left-0 padding-right-1 pull-left" style="display: block; overflow: hidden; float: left; width: 85px;" >
								<table class="table table-set"  >
									<thead id="DetailDateTitle">
									<th class="colwidth-7">月份</th>
									</thead>
									<tbody id="DetailDate">
									</tbody>
								</table>
							</div>
                      	
                      	
							<div class="col-lg-11 col-md-11 padding-left-0 padding-right-0 pull-right margin-bottom-10" style="overflow-x: auto;float: left;">
								<table class="table table-thin table-bordered"  style="min-width: auto !important" id="DetailTb">
									<thead id="DetailTh">
									</thead>
									<tbody id="DetailTd">
									</tbody>
								</table>
							</div>
                      	</div>
                      	
                      	<div class="tab-pane fade" id="profile" style="overflow-x: auto;width: 100%;">
                      		<table class="table table-thin table-bordered" style="min-width: auto !important" id="SummaryTb">
									<thead id="SummaryTh">
									</thead>
									<tbody id="SummaryTd">
									</tbody>
							</table>
                      	</div>
		                  </div>
		              </div>
					
				
				<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
					</div>
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	</div>
	
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/airportensure/fuelup/fuelup_statistics.js"></script>
</body>
</html>