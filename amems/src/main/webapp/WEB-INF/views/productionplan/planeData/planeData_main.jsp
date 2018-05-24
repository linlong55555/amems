<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var pageParam = '${param.pageParam}';
</script>
</head>
<body >
	<input type ="hidden" id="fjzch" value='${erayFns:escapeStr(param.fjzch)}'>
	<input type ="hidden" id="dprtcode_hid" value='${erayFns:escapeStr(param.dprtcode)}'>
	<div class="page-content" >
		<div class="panel panel-primary">
			<div class="panel-heading " id="NavigationBar">
			</div>

			<div class="panel-body padding-bottom-0">
				<div>
				<div class="col-xs-3 col-md-2 padding-left-0">
					<button type="button"class="btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 row-height pull-left"
						onclick="goToAddPage();">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="planeData:planeRegister:main:03"
						onclick="showImportModal()">
						<div class="font-size-12">导入</div>
						<div class="font-size-9">Import</div>
					</button>
				</div>
				<div class="pull-right padding-right-0">
					
					<div class="pull-left">
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">基地</div>
							<div class="font-size-9">Station</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="padding-left-8 pull-left" style="width: 200px; margin-right:5px;">
							   <select class='form-control' id='jd_search' name="jd_search" onchange="searchPlane();">
								</select>
							</div>
						</div>
					</div>
					
					<div class="pull-left">
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="padding-left-8 pull-left" style="width: 200px; margin-right:5px;">
							   <select id="dprtcode" class="form-control " name="dprtcode" onchange="refreshBase(true)">
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					
					<div class="pull-left padding-left-5 padding-right-0" style="width: 250px;">
						<input type="text" class="form-control " id="keyword_search" placeholder="机型/飞机注册号/序列号/备注名/备注"/>
					</div>
              		<div class="pull-right padding-left-5 padding-right-0">
              			<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchPlane();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
						</button>     
						<!-- <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="icon-caret-down font-size-15" id="icon"></i>
							</div>
				   		</button> -->
                    </div>    
				</div>
				
				<div class="col-xs-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height" id="divSearch">
					<div class="col-xs-3  col-lg-4 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				</div>
				
				
				


				
				<div  class="col-xs-12 text-center padding-left-0 padding-right-0 table-h"  style="overflow-x:scroll">
		
					<table class="table table-thin table-bordered table-striped text-center tableRe" id="planeData_table">
						<thead>
							<tr class="nav-tabs">
							<th colspan="13"><div class="font-size-12 line-height-18">基本信息</div><div class="font-size-9 line-height-18" >Basic Info</div></th>
							<th colspan="7"><div class="font-size-12 line-height-18" >飞机进入系统时初始化信息</div><div class="font-size-9 line-height-18" >Aircraft Init Data</div></th>
							<th colspan="6"><div class="font-size-12 line-height-18" >系统初始化发动机循环(1#左发)</div><div class="font-size-9 line-height-18" >1# Init Data</div></th>
							<th colspan="6"><div class="font-size-12 line-height-18" >系统初始化发动机循环(2#右发)</div><div class="font-size-9 line-height-18" >2# Init Data</div></th>
							<th colspan="2"><div class="font-size-12 line-height-18" >系统初始化特殊监控</div><div class="font-size-9 line-height-18" >Special Init Data</div></th>
							</tr>
							
							<tr>
							<th style="min-width: 60px;" class="fixed-column"><div class="font-size-12 line-height-18" >操作</div><div class="font-size-9 line-height-18" >Operation</div></th>
							<th style="min-width: 100px;" class="sorting" onclick="orderBy('fjjx')" id="fjjx_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >机型</div>
									<div class="font-size-9 line-height-18" >Model</div>
								</div>
							</th>
							<th style="min-width: 100px;" class="sorting" onclick="orderBy('fjzch')" id="fjzch_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >飞机注册号</div>
									<div class="font-size-9 line-height-18" >A/C REG</div>
								</div>
							</th>
							<th style="min-width: 150px;" class="sorting" onclick="orderBy('xlh')" id="xlh_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >序列号</div>
									<div class="font-size-9 line-height-18" >S/N</div>
								</div>
							</th>
							<th style="min-width: 150px;" class="sorting" onclick="orderBy('bzm')" id="bzm_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >备注名</div>
									<div class="font-size-9 line-height-18" >Remark Name</div>
								</div>
							</th>
							<th style="min-width: 100px;" class="sorting" onclick="orderBy('ccrq')" id="ccrq_order"><div class="font-size-12 line-height-18" >出厂日期</div><div class="font-size-9 line-height-18" >PD</div></th>
							<th style="min-width: 100px;" class="sorting" onclick="orderBy('jd')" id="jd_order"><div class="font-size-12 line-height-18" >基地</div><div class="font-size-9 line-height-18" >Base</div></th>
							<th style="min-width: 250px;" id="gjdjzh" class="downward cursor-pointer" onclick="vieworhideWorkContentAll()" name="th_fjsz"><div class="font-size-12 line-height-18" >飞机三证</div><div class="font-size-9 line-height-18" >The three certificates</div></th>
							<!-- <th style="min-width: 150px;" id="shzh" class="downward" onclick="vieworhideWorkContentAll()"><div class="font-size-12 line-height-18" >三证有效期</div><div class="font-size-9 line-height-18" >Three certificates' validity</div></th> -->
							<!-- <th style="min-width: 150px;" id="wxdtxkzh"><div class="font-size-12 line-height-18" >无线电台许可证号</div><div class="font-size-9 line-height-18" >Station license</div></th>
							<th style="min-width: 120px;" id="dtzzjkrq"><div class="font-size-12 line-height-18" >电台执照监控日期</div><div class="font-size-9 line-height-18" >Exp.</div></th> -->
							<th style="min-width: 200px;" id="jsgzjl"><div class="font-size-12 line-height-18" >改装记录</div><div class="font-size-9 line-height-18" >Record</div></th>
							<th style="min-width: 200px;" id="bz">
								<div class="important">
									<div class="font-size-12 line-height-18" >备注</div>
									<div class="font-size-9 line-height-18" >Remark</div>
								</div>
							</th>
							<th style="min-width: 150px;"><div class="font-size-12 line-height-18" >创建人</div><div class="font-size-9 line-height-18" >Creator</div></th>
							<th style="min-width: 180px;"><div class="font-size-12 line-height-18" >创建时间</div><div class="font-size-9 line-height-18" >Create Time</div></th>
							<th style="min-width: 150px;"><div class="font-size-12 line-height-18" >组织机构</div><div class="font-size-9 line-height-18" >Organization</div></th>
							<th style="min-width: 100px;" id="init_date_rq"><div class="font-size-12 line-height-18" >日期</div><div class="font-size-9 line-height-18" >Date</div></th>
							<th style="min-width: 100px;" id="init_time_jsfx"><div class="font-size-12 line-height-18" >机身飞行时间</div><div class="font-size-9 line-height-18" >Flight HRS.</div></th>
							<th style="min-width: 100px;" id="init_time_ssd"><div class="font-size-12 line-height-18" >搜索灯时间</div><div class="font-size-9 line-height-18" >SearchLight Time</div></th>
							<th style="min-width: 100px;" id="init_time_jc"><div class="font-size-12 line-height-18" >绞车时间</div><div class="font-size-9 line-height-18" >Winch Time</div></th>
							<th style="min-width: 100px;" id="init_loop_qlj"><div class="font-size-12 line-height-18" >起落循环</div><div class="font-size-9 line-height-18" >Flight CYCS.</div></th>
							<th style="min-width: 100px;" id="init_loop_jc"><div class="font-size-12 line-height-18" >绞车循环</div><div class="font-size-9 line-height-18" >Winch CYCS.</div></th>
							<th style="min-width: 100px;" id="init_loop_wdg"><div class="font-size-12 line-height-18" >外吊挂循环</div><div class="font-size-9 line-height-18" >CYCS.</div></th>
							<th style="min-width: 60px;" id="init_loop_l_n1"><div class="font-size-12 line-height-18" >N1</div><div class="font-size-9 line-height-18" >N1</div></th>
							<th style="min-width: 60px;" id="init_loop_l_n2"><div class="font-size-12 line-height-18" >N2</div><div class="font-size-9 line-height-18" >N2</div></th>
							<th style="min-width: 60px;" id="init_loop_l_n3"><div class="font-size-12 line-height-18" >N3</div><div class="font-size-9 line-height-18" >N3</div></th>
							<th style="min-width: 60px;" id="init_loop_l_n4"><div class="font-size-12 line-height-18" >N4</div><div class="font-size-9 line-height-18" >N4</div></th>
							<th style="min-width: 60px;" id="init_loop_l_n5"><div class="font-size-12 line-height-18" >N5</div><div class="font-size-9 line-height-18" >N5</div></th>
							<th style="min-width: 60px;" id="init_loop_l_n6"><div class="font-size-12 line-height-18" >N6</div><div class="font-size-9 line-height-18" >N6</div></th>
							<th style="min-width: 60px;" id="init_loop_r_n1"><div class="font-size-12 line-height-18" >N1</div><div class="font-size-9 line-height-18" >N1</div></th>
							<th style="min-width: 60px;" id="init_loop_r_n2"><div class="font-size-12 line-height-18" >N2</div><div class="font-size-9 line-height-18" >N2</div></th>
							<th style="min-width: 60px;" id="init_loop_r_n3"><div class="font-size-12 line-height-18" >N3</div><div class="font-size-9 line-height-18" >N3</div></th>
							<th style="min-width: 60px;" id="init_loop_r_n4"><div class="font-size-12 line-height-18" >N4</div><div class="font-size-9 line-height-18" >N4</div></th>
							<th style="min-width: 60px;" id="init_loop_r_n5"><div class="font-size-12 line-height-18" >N5</div><div class="font-size-9 line-height-18" >N5</div></th>
							<th style="min-width: 60px;" id="init_loop_r_n6"><div class="font-size-12 line-height-18" >N6</div><div class="font-size-9 line-height-18" >N6</div></th>
							<th style="min-width: 70px;" id="init_loop_ts1"><div class="font-size-12 line-height-18" >TS1</div><div class="font-size-9 line-height-18" >TS1</div></th>
							<th style="min-width: 70px;" id="init_loop_ts2"><div class="font-size-12 line-height-18" >TS2</div><div class="font-size-9 line-height-18" >TS2</div></th>
							</tr>
			          </thead>
						<tbody id="planelist">
						    <!------ plane列表展示 ------>
						</tbody>
					</table>
				</div>
				<div class=" col-xs-12  text-center page-height padding-right-0 padding-left-0" style="margin-top: 0px; margin-bottom: 0px;" id="pagination"></div>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/planeData/planeData_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
</body>
</html>