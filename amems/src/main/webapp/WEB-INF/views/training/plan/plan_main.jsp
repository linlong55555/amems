<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>培训计划</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>

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
					searchRevision();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-content tab-tab-type">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			    <div  class='searchContent row-height margin-top-0'>
				<div class="pull-left padding-left-0 form-group ">
					<a href="javascript:void(0);" onclick="openWinAdd()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="training:plan:main:01" >
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
					<a href="#" onclick="showImportModal()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left "  style="float: left; margin-left: 10px;">
						<div class="font-size-12">导入</div>
						<div class="font-size-9">Import</div>
					</a>
					<a href="javascript:exportExcel();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="training:plan:main:06" style="float: left; margin-left: 10px;">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a>
					
					<div class="pull-left padding-left-5 padding-right-0" style="width: 150px;">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">年度</div>
							<div class="font-size-9">Year</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="hidden" id="ndId" value="" />
							<input type="hidden" id="ndjhmcView" value="" />
							<input class="form-control" id="year_search" name="year_search" data-date-format="yyyy" type="text" onchange="ndChange()" readonly />
						</div>
					</div>	
					
					<div class="pull-left padding-left-10 padding-right-1 margin-right-1 padding-button-10" >
						<p id="ndjhmcDiv" class='pull-left' style='border:1px solid #cccccc;height:34px;line-height:34px;padding-left:8px;padding-right:8px;border-top-left-radius:5px;border-bottom-left-radius:5px;margin-top:0px;margin-bottom:0px;'></p>
						<p class='pull-left' style='border:1px solid #cccccc;border-left:0px;height:34px;line-height:34px;padding-left:5px;padding-right:5px;border-top-right-radius:5px;border-bottom-right-radius:5px;margin-bottom:0px;'><i class=' icon-edit color-blue cursor-pointer checkPermission' permissioncode='training:plan:main:01' onClick="openNdWinEdit()" title='修改 Edit'></i></p>
					</div>
					
					<a href="javascript:openAttachmentWinEdit();" title="附件 Attachment" class="padding-top-1 padding-bottom-1 pull-left checkPermission " permissioncode="training:plan:main:01" style="float: left;text-decoration:none;position:relative; margin-left: 10px;">
						<i class="icon-download-alt pull-left" style="font-size:25px"></i>
						<span id="fileCount" class="badge" style="position: absolute; background:red ! important; color:; margin: -3px -8px 0px ! important;">0</span>
					</a>				
				</div>
				
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>

					<!-- 搜索框start -->
					<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='课程代码/课程名称/主办/讲师/培训地点/备注' id="keyword_search" class="form-control " >
		                    <div class="input-group-addon btn-searchnew">
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   			</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
		                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1" id="btn" onclick="search();">
								<div class='input-group'>
								<div class="input-group-addon">
								<div class="font-size-12">更多</div>
								<div class="font-size-9 margin-top-5" >More</div>
								</div>
								<div class="input-group-addon">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
								</div>
					   		</button>
		                	</div>
						</div>
					<!-- 搜索框end -->
				
				</div>
				<div class='clearfix'></div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">计划类型</div>
							<div class="font-size-9">Plan Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jhlx_search'>
								<option value="" selected="true">显示全部All</option>
								<c:forEach items="${trainingPlanTypeEnum}" var="item">
								  <option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">计划日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="jhrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">培训类别</div>
							<div class="font-size-9">Training Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='pxlb_search' name="pxlb_search" >
								<option value="" selected="true">显示全部All</option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-1 col-sm-1 text-right padding-left-0 padding-right-0">
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							  	 初训
						    	<input name="fxbs_search" type="checkbox" value="1" checked="checked"/>
						    	&nbsp;
								复训
								<input name="fxbs_search" type="checkbox" value="2"  checked="checked"/> 
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机型</div>
							<div class="font-size-9">A/C Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class="form-control" id="fjjx_search" name="fjjx_search" ></select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='zt_search'>
								<option value="" selected="true">显示全部All</option>
								<c:forEach items="${trainingPlanStatusEnum}" var="item">
								  	<c:if test="${item.id != 8}">
										<option value="${item.id}" >${item.name}</option>
									</c:if>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control " onchange="dpartmentChange()">
								<c:forEach items="${accessDepartment}" var="type" >
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
					<div class="clearfix"></div>
					
					
				</div>
				<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				
				
				<div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-0 tab-tab-type_parentdiv" >
                	<ul id="myTab" class="nav nav-tabs tabNew-style bottom_hidden_Ul">
                    	<li class="active" id="pxjhExport"><a href="#payStatistics" data-toggle="tab" aria-expanded="true">培训计划 Training Plan</a></li>
                      	<li class="" id="ndstExport"><a href="#paymentDetail" data-toggle="tab" aria-expanded="false">年度视图 Year View</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                    	<div class="tab-pane fade active in" id="payStatistics">
                      
                     <div id="course_list_table_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 tab-first-height" style="margin-top: 10px;overflow-x: auto;">
						<table id="course_main_table" class="table table-thin table-bordered table-set" style="min-width: 1000px;">
							<thead>
								<tr>
									<th class="fixed-column colwidth-9" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('jhlx')" id="jhlx_order">
										<div class="font-size-12 line-height-18">计划类型</div>
										<div class="font-size-9 line-height-18">Plan Type</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('kcbh')" id="kcbh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">课程代码</div>
											<div class="font-size-9 line-height-18">Course No.</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('kcmc')" id="kcmc_order">
										<div class="important">
											<div class="font-size-12 line-height-18">课程名称</div>
											<div class="font-size-9 line-height-18">Course Name</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('fjjx')" id="fjjx_order">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('pxlb')" id="pxlb_order">
										<div class="font-size-12 line-height-18">培训类别</div>
										<div class="font-size-9 line-height-18">Training Type</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('ks')" id="ks_order">
									<div class="important">
										<div class="font-size-12 line-height-18">主办</div>
										<div class="font-size-9 line-height-18">Host</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('pxdx')" id="pxdx_order">
										<div class="font-size-12 line-height-18">培训对象</div>
										<div class="font-size-9 line-height-18">Object</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('pxjg')" id="pxjg_order">
										<div class="font-size-12 line-height-18">培训机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
									<th class="colwidth-20" >
										<div class="font-size-12 line-height-18">计划日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('jsxm')" id="jsxm_order">
										<div class="important">
											<div class="font-size-12 line-height-18">讲师</div>
											<div class="font-size-9 line-height-18">Lecturer</div>
										</div>
									</th>
									<th class="colwidth-30 sorting" onclick="orderBy('kcdd')" id="kcdd_order">
										<div class="important">
											<div class="font-size-12 line-height-18">培训地点</div>
											<div class="font-size-9 line-height-18">Address</div>
										</div>
									</th>
									<th class="colwidth-9 sorting" onclick="orderBy('FXBS')" id="FXBS_order">
										<div class="font-size-12 line-height-18">初/复训标识</div>
										<div class="font-size-9 line-height-18">Whether</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('pxxs')" id="pxxs_order">
										<div class="font-size-12 line-height-18">培训形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('KSXS')" id="KSXS_order">
										<div class="font-size-12 line-height-18">考试形式</div>
										<div class="font-size-9 line-height-18">Form</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('KS')" id="KS_order">
										<div class="font-size-12 line-height-18">课时</div>
										<div class="font-size-9 line-height-18">Hour</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('XT')" id="XT_order">
										<div class="font-size-12 line-height-18">学天</div>
										<div class="font-size-9 line-height-18">Day</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">复训间隔</div>
										<div class="font-size-9 line-height-18">Interval</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('PXYS')" id="PXYS_order">
										<div class="font-size-12 line-height-18">培训预算</div>
										<div class="font-size-9 line-height-18">Budget</div>
									</th>
									<th class="colwidth-20">
										<div class="font-size-12 line-height-18">实际日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-18">课件</div>
										<div class="font-size-9 line-height-18">Courseware</div>
									</th>
									<th class="colwidth-5 sorting" onclick="orderBy('ZT')" id="ZT_order">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-30 sorting" onclick="orderBy('bz')" id="bz_order">
										<div class="important">
											<div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-18">Note</div>
										</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('username')" id="username_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('whsj')" id="whsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="colwidth-15 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="list"></tbody>
						</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
                      
                      	</div>
                      	
                      	<div class="tab-pane fade" id="paymentDetail" style="margin-top: -5px;">
                      		<small class="text-muted">
                      			列表中表示月的字段以周为单位,
                      			<i class='icon-circle-blank color-black'></i>表示计划开始日期
                      			<i class='icon-circle color-green'></i>表示实际开始日期
                      			<i class='icon-adjust color-green'></i>表示计划开始日期和实际开始日期在同一周
                      		</small>
                      		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 tab-first-height" style="overflow-x: auto;">
								<table id="yearTable" class="table table-thin table-bordered table-hover text-left table-set"  style="min-width: 500px;">
									<thead>
										<tr>
											<th rowspan='2' class="fixed-column colwidth-10">
												<div class="font-size-12 line-height-18">计划类型</div>
												<div class="font-size-9 line-height-18">Plan Type</div>
											</th>
											<th rowspan='2' class="fixed-column colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">课程代码</div>
													<div class="font-size-9 line-height-18">Course No.</div>
												</div>
											</th>
											<th rowspan='2' class="fixed-column colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">课程名称</div>
													<div class="font-size-9 line-height-18">Course Name</div>
												</div>
											</th>
											<th rowspan='2' class="colwidth-10" onclick="orderBy('fjjx')" id="fjjx_order">
												<div class="font-size-12 line-height-18">机型</div>
												<div class="font-size-9 line-height-18">A/C Type</div>
											</th>
											<th rowspan='2' class="colwidth-10" >
												<div class="font-size-12 line-height-18">培训类别</div>
												<div class="font-size-9 line-height-18">Training Type</div>
											</th>
											<th rowspan='2' class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">主办</div>
													<div class="font-size-9 line-height-18">Host</div>
												</div>
											</th>
											<th rowspan='2' class="colwidth-10">
												<div class="font-size-12 line-height-18">培训对象</div>
												<div class="font-size-9 line-height-18">Object</div>
											</th>
											<th rowspan='2' class="colwidth-10">
												<div class="font-size-12 line-height-18">培训形式</div>
												<div class="font-size-9 line-height-18">Form</div>
											</th>
											<th rowspan='2' class="colwidth-13">
												<div class="important">
													<div class="font-size-12 line-height-18">讲师</div>
													<div class="font-size-9 line-height-18">Lecturer</div>
												</div>
											</th>
											<th rowspan='2' class="colwidth-10">
												<div class="font-size-12 line-height-18">课件</div>
												<div class="font-size-9 line-height-18">Courseware</div>
											</th>
											<th rowspan='2' class="colwidth-30">
												<div class="important">
													<div class="font-size-12 line-height-18">培训地点</div>
													<div class="font-size-9 line-height-18">Address</div>
												</div>
											</th>
											<th rowspan='2'  class="colwidth-30">
												<div class="important">
													<div class="font-size-12 line-height-18">备注</div>
													<div class="font-size-9 line-height-18">Note</div>
												</div>
											</th>
											<th rowspan='2' class="colwidth-10">
												<div class="font-size-12 line-height-18">计划开始日期</div>
												<div class="font-size-9 line-height-18">Date</div>
											</th>
											<th rowspan='2' class="colwidth-10">
												<div class="font-size-12 line-height-18">实际开始日期</div>
												<div class="font-size-9 line-height-18">Date</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">1月</div>
												<div class="font-size-9 line-height-18">January</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">2月</div>
												<div class="font-size-9 line-height-18">Feburary</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">3月</div>
												<div class="font-size-9 line-height-18">March</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">4月</div>
												<div class="font-size-9 line-height-18">April</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">5月</div>
												<div class="font-size-9 line-height-18">May</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">6月</div>
												<div class="font-size-9 line-height-18">June</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">7月</div>
												<div class="font-size-9 line-height-18">July</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">8月</div>
												<div class="font-size-9 line-height-18">Auguest</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">9月</div>
												<div class="font-size-9 line-height-18">Septemble</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">10月</div>
												<div class="font-size-9 line-height-18">October</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">11月</div>
												<div class="font-size-9 line-height-18">November</div>
											</th>
											<th class="month colwidth-20">
												<div class="font-size-12 line-height-18">12月</div>
												<div class="font-size-9 line-height-18">December</div>
											</th>
										</tr>
										<tr id="trHead">
										</tr>
									</thead>
									<tbody id="year_view_list"></tbody>
								</table>
							</div>
							<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="year_view_Pagination"></div>
                      	</div>
                  </div>
              </div>
				
				<div class="clearfix"></div>
				<!-- 点击后显示的内容 -->
				<div id="selectCourse"></div>
				<div id='fileDiv' class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-10" style='display:none;'>
                	<ul id="myChildTab" class="nav nav-tabs tabNew-style">
                      	<li class="active" ><a id="personTab" href="#Dropdown" data-toggle="tab" aria-expanded="false">人员 Personel</a></li>
                      	<li class="" ><a id="profileTab" href="#profile" data-toggle="tab" aria-expanded="false">课件信息 Courseware Information</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content tabchild_content">
                    	<div class="tab-pane fade active in" id="Dropdown">
							<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0" >
								<div style="overflow-x: auto;" class='tab-second-height'>
									<!-- start:table -->
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:510px">
										<thead>
									   		<tr>
												<th class="colwidth-3" >
													<div class="font-size-12 line-height-18">序号</div>
													<div class="font-size-9 line-height-18">No.</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 line-height-18">人员编号</div>
													<div class="font-size-9 line-height-18">No.</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 line-height-18">人员姓名</div>
													<div class="font-size-9 line-height-18">Name</div>
												</th>
												<th class="colwidth-30">
													<div class="font-size-12 line-height-18">部门</div>
													<div class="font-size-9 line-height-18">Dpartment</div>
												</th>
									 		 </tr>
										</thead>
										<tbody id="person_body">
										
										</tbody>
									</table>
									<!-- end:table -->
								</div>
								<div class="clearfix"></div>
							</div>
                      	</div>
                      	<div class="tab-pane fade" id="profile">
                      		<%@ include file="/WEB-INF/views/common/attachments/attachments_list_crud.jsp"%>
                      	</div>
                  </div>
              </div>
		</div>
	</div>
		
</div>

<!-------培训计划对话框 Start-------->
	
<div class="modal fade modal-new active in" id="alertForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:85%;">
		<div class="modal-content">

				<div class="modal-header modal-header-new">
					<h4 class="modal-title">
						<div class="pull-left" id="headChina">
							<div class="font-size-14 "><span id="modalName">新增</span></div>
							<div class="font-size-12"><span id="modalEname">Add</span></div>
						 </div>
						 <div class="pull-right">
						 	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						 </div>
						 <div class="clearfix"></div>
	                </h4>
	             </div>


			<div class="modal-body padding-bottom-0" id="alertBody">
				<div class="panel panel-primary margin-top-8">
					<div class="panel-body padding-top-10 padding-bottom-0" >
						
						<form id="form">
						
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<input type="hidden" class="form-control " id="id" />
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>计划类型</div>
										<div class="font-size-9">Plan Type</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<select class='form-control' id='jhlx'>
											<c:forEach items="${trainingPlanTypeEnum}" var="item">
											  <option value="${item.id}" >${item.name}</option>
											</c:forEach>
									    </select>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>培训类别</div>
										<div class="font-size-9">Training Type</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<select class='form-control' id='pxlb' name="pxlb" >
										</select>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>课程代码</div>
										<div class="font-size-9">Course No.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="hidden" class="form-control " id="kcid" />
										<div class="input-group">
											<input id="kcbh" name="kcbh" class="form-control readonly-style" readonly="readonly" type="text" ondblclick="openCourseWin()">
								            <span class="input-group-btn">
												<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openCourseWin()">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</span>
								       </div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>课程名称</div>
										<div class="font-size-9">Course Name</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="kcmc" name="kcmc" maxlength="100" readonly />
									</div>
								</div>
								 <div class="clearfix"></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>初/复训标识</div>
										<div class="font-size-9">Whether</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style="height:34px;">
										<label style="margin-right: 20px;font-weight: normal" onclick="checkFxbs()" >
									    	<input name="fxbs" type="radio" value="1" />初训
									    </label> 
										<label style="font-weight: normal" onclick="checkFxbs()">
											<input name="fxbs" type="radio" value="2" />复训
										</label> 
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>课时</div>
										<div class="font-size-9">HRS</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
										<div class="input-group">
											<input type="text" class="form-control " id="ks" name="ks" onkeyup='clearNoNumTwo(this)' maxlength="10" />
											<span name="xh" class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">时</span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>培训形式</div>
										<div class="font-size-9">Form</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<select class='form-control' id='pxxs' name="pxxs" ></select>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>考试形式</div>
										<div class="font-size-9">Form</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<select class='form-control' id='ksxs' name="ksxs" ></select>
									</div>
								</div>
								 <div class="clearfix"></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>计划开始日期</div>
										<div class="font-size-9">Start Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class='input-group' style="position:relative;z-index:1;">
										  	<input type="text" class='form-control datetimepicker' id="jhKsrq" name="jhKsrq" onchange="onchangeDate()"/>
										  	<span class='input-group-btn'>
										  		<input class='form-control datetimepicker readonly-style' type='text' style='width:45px;' id='jhKssj' name='jhKssj' readonly/>
										  	</span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">计划结束日期</div>
										<div class="font-size-9">End Date</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class='input-group' style="position:relative;z-index:1;">
										  	<input type="text" class='form-control datetimepicker' id="jhJsrq" name="jhJsrq" />
											 <span class='input-group-btn'>
											  	 <input class='form-control datetimepicker readonly-style' type='text' style='width:45px;' id='jhJssj' name='jhJssj' readonly/>
											 </span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">学天</div>
										<div class="font-size-9">Day</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group">
											<input type="text" class="form-control " id="xt" name="xt" onkeyup='clearNoNumber(this)' maxlength="10" />
											<span name="xh" class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">天</span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">主办</div>
										<div class="font-size-9">Host</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="zrr" name="zrr" maxlength="100" />
									</div>
								</div>
								 <div class="clearfix"></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训机构</div>
										<div class="font-size-9">Host</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="pxjg" name="pxjg" maxlength="100" />
									</div>
								</div>
								
					
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训预算</div>
										<div class="font-size-9">Budget</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class='input-group' style="position:relative;z-index:1;">
										  	<input type="text" class="form-control " id="pxys" name="pxys" placeholder='' onkeyup='clearNoNumTwo(this)' maxlength="10" />
										  	<span class='input-group-btn'>
										  		<select id='pxysBz' name="pxysBz" style='width:60px;'  class="form-control">
												</select>
										  	</span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训地点</div>
										<div class="font-size-9">Address</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<input type="text" class="form-control " id="kcdd" name="kcdd" maxlength="100" />
									</div>
								</div>

								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red"></span>教材发放</div>
										<div class="font-size-9">Grant</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style="height:34px">
										<label style="margin-right: 20px;font-weight: normal;" class="label-input">
									    	<input name="isJcff" type="radio" value="1"  />是
									    </label> 
										<label style="font-weight: normal;"  class="label-input">
											<input name="isJcff" type="radio" value="0" />否 
										</label> 
									</div>
								</div>
								
								 <div class="clearfix"></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">机型</div>
										<div class="font-size-9">A/C Type</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<!-- <select class="form-control" id="jx" name="jx"></select> -->
										<input type="text" class="form-control " id="jx" name="jx" readonly/>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">专业</div>
										<div class="font-size-9">Skill</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<select id="zy" class="form-control" name="zy"></select>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red"></span>讲师</div>
										<div class="font-size-9">Lecturer</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
										<input type="hidden" class="form-control " id="jsid" />
										<div class="input-group">
											<input id="jsxm" name="jsxm" class="form-control "  type="text" onchange="onLecturerChanged();">
								            <span class="input-group-btn">
												<button type="button" class="btn btn-default form-control" style="height:34px;" data-toggle="modal" onclick="openLecturerWin()">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</span>
								       </div>
								       <small style='position:absolute;'>在下发前必须指定讲师</small>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red"></span>是/否颁证</div>
										<div class="font-size-9">Certificate</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style="height:34px">
										<label style="margin-right: 20px;font-weight: normal" class="label-input">
									    	<input name="jsBz" type="radio" value="1" />是
									    </label> 
										<label style="font-weight: normal" class="label-input">
											<input name="jsBz" type="radio" value="0" />否 
										</label> 
									</div>
								</div>
	                           <div class="clearfix"></div>
								<div class="col-xs-12 padding-left-0 padding-right-0 form-group" style="margin-top:12px;">
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">培训对象</div>
										<div class="font-size-9">Object</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="pxdx" name="pxdx" maxlength="1000" style="height:55px"></textarea>					
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">人员</div>
										<div class="font-size-9">Personel</div>
									</label>
									<div id="fjzw_div_edit" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<div class="input-group" style="min-width:17%;">
											<div ondblclick="openPersonelWin()" id="personName" class='form-control base-color readonly-style' style='border-right:0px;border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
											</div> 
						                    <div id="fjzw_btn" class="input-group-addon btn btn-default" style='border-left:1px solid #d5d5d5;padding-left:0px;padding-right:0px;width:38px;' onclick="openPersonelWin()"><i class='icon-search'></i></div>
					                	</div>
									</div>
								</div>
							
								
								<div class="clearfix"></div>
								
								<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">课程内容</div>
										<div class="font-size-9">Content</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="kcnr" name="kcnr" maxlength="1000" style="height:55px"></textarea>
									</div>
								</div>
								
								<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">备注</div>
										<div class="font-size-9">Note</div>
									</label>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea class="form-control" id="bz" name="bz" maxlength="300" style="height:55px" ></textarea>
									</div>
								</div>
							</div>
						
							<div class="clearfix"></div>
						</form>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="modal-footer ">
					<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
					<span class="input-group-addon modalfootertip" >
		                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
	                    <span class="input-group-addon modalfooterbtn">
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="save()" id="baocunButton">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
						    </button>
	                    </span>
	               	</div><!-- /input-group -->
				</div>
           
				<div class="clearfix"></div> 
					             
	            </div>
		</div>
	</div>
</div>

<!-------培训计划对话框 End-------->

<!-------年度计划对话框 Start-------->
	
	<div class="modal fade" id="alertYearPlanModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:300px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertYearPlanBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">年度计划</div>
							<div class="font-size-9 ">Year Plan</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
				            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
									<span class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">名称</div>
										<div class="font-size-9">Name</div>
									</span>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="ndjhmc" name="ndjhmc" maxlength="100" />
									</div>
								</div> 
							</div>
							
						 	 <div class="clearfix"></div>
						 	 
						 	 <div class="text-right margin-top-0 margin-right-0" style="margin-bottom: 10px;">
								<button id="courseSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:saveYearPlan();">
				                   	<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
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
			</div>
		</div>
	</div>
	
	<!-------年度计划对话框 End-------->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/training/plan/plan_main.js"></script>
<%@ include file="/WEB-INF/views/open_win/attachments_list_edit.jsp"%><!-------附件对话框 -------->
<%@ include file="/WEB-INF/views/open_win/user.jsp"%><!-------用户对话框 -------->
<%@ include file="/WEB-INF/views/open_win/course.jsp"%><!-------课程对话框 -------->
<%@ include file="/WEB-INF/views/open_win/personel_tree_multi.jsp"%><!-------用户对话框 -------->
<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
<%@ include file="/WEB-INF/views/training/plan/teacher_user.jsp" %><!-- 讲师列表 -->

</body>
</html>