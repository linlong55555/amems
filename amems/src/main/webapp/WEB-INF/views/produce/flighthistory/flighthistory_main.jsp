<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>飞行履历</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<style>
	.thead-new{
	    border: 1px solid #ddd;
	   /*  background-image: -webkit-linear-gradient(top, #f7f7f7, #ededed);
		background-image: -moz-linear-gradient(top, #f7f7f7, #ededed);
		background-image: -ms-linear-gradient(top, #f7f7f7, #ededed);
		background-image: -o-linear-gradient(top, #f7f7f7, #ededed);
		background-image: linear-gradient(top, #f7f7f7, #ededed); */
		/* background:#f3f3f3; */
		margin-bottom: 0;
   }
	.thead-bgtr{
		
	}
	.thead-total{
	background:#fff7f1;
	}
	.thead-total th{

	}
	.border-all{
	border:1px solid #d5d5d5;
	}
	.border-right{
	border-right:1px solid #d5d5d5;
	}
	.total-close{
	font-weight: 700;
	color: #000;
	font-size:21px;
	height:21px;
	display:inline-block;
	background:transparent;
	text-shadow: 0 1px 0 #fff;
	opacity: .2;
	filter: alpha(opacity = 20);
	border:0;
	padding:0px;
	cursor:pointer;
	vertical-align:-3px;
	
	}
	
	</style>
	
	
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
						flighthistoryMain.reload();//调用主列表页查询
					}
				}
			});
		});
	</script>
	
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading  ">
					<div id="NavigationBar"></div>
				</div>
				<div class="panel-body padding-bottom-0" >
				
					<div class='searchContent margin-top-0  row-height'>
						<div class=" col-lg-3 col-md-3 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
							<!-- <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" 
								style="margin-right:3px;" onclick="modify_tableView();">
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" 
							    permissioncode="produce:flighthistory:main:01" 
								style="margin-right:3px;" onclick="flighthistoryMain.showImportModal();">
								<div class="font-size-12">导入</div>
								<div class="font-size-9">Import</div>
							</button> -->
							
							
							<a href="javascript:flighthistoryMain.showImportModal();" permissioncode='produce:flighthistory:main:01'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
								<div class="font-size-12">导入</div>
								<div class="font-size-9">Import</div>
							</a> 
							<!-- permissioncode='project2:airworthiness:main:04' -->
							<a  permissioncode="produce:flighthistory:main:02"  href="javascript:exportExcel();" type="button" class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" >
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</a>
						</div>
						
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">飞机注册号</div>
								<div class="font-size-9 ">A/C Reg</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
								<select id="fjzch" class="form-control" onchange="flighthistoryMain.reload()">
									<option value="" selected="selected">暂无飞机</option>
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">飞行日期</div>
								<div class="font-size-9 ">Flight date</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="date-range-picker" readonly id="fxrq" />
							</div>
						</div>
						
						<!-- 搜索框start -->
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
							<div class="col-xs-12 input-group input-group-search">
								<input type="text" placeholder='飞行记录单号/记录本页码/航班号'  class="form-control" id="keyword_search" >
			                    <div class="input-group-addon btn-searchnew" >
			                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="flighthistoryMain.reload();" style='margin-right:0px !important;'>
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
			                  		</button>
			                    </div>
			                    <div class="input-group-addon btn-searchnew-more">
				                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="search();">
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
						</div>
					
				
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
						<!-- <div class="col-lg-6 col-sm-6 col-xs-12	  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
								<div class="font-size-9">Organization</div></span>
							<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' id="xpgbs1" style='vertical-align:text-bottom;' checked="checked" />&nbsp;保存&nbsp;&nbsp;</label>
								<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' id="xpgbs2" style='vertical-align:text-bottom;' checked="checked" />&nbsp;提交&nbsp;&nbsp;</label>
								<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' id="xpgbs1" style='vertical-align:text-bottom;' checked="checked" />&nbsp;修订&nbsp;&nbsp;</label>
								<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' id="xpgbs2" style='vertical-align:text-bottom;' checked="checked" />&nbsp;作废&nbsp;&nbsp;</label>
							</div>
						</div> -->
						
						<div class="col-lg-3 col-sm-6 col-xs-12	  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
								<div class="font-size-9">Status</div></span>
							<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="zt" class="form-control " >
									<option value="">显示全部All</option>
									<option value="2">提交</option>
									<option value="12">修订</option>
									<option value="99">历史</option>
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">航班号</div>
								<div class="font-size-9 ">FLT. No.</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
								<input type="text" class="form-control" id="hbh" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">记录本页码</div>
								<div class="font-size-9 ">Page</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
								<input type="text" class="form-control"  id="jlbym" />
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12	  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div></span>
							<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="dprtChange()" >
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					
					<div class="col-lg-3 col-sm-3 col-xs-12  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				<div class="clearfix"></div>
				</div>
					<!-- 搜索框end -->
					<div class="clearfix"></div>
                    <div style='position:relative;'>
                    <div style='position:absolute;width:13px;height:16px;top:74px;left:8px;z-index:50;display:none;' id='totalIcon'><i class='fa fa-angle-double-down cursor-pointer' style='font-size:20px;' onclick='showTotalTr()'></i></div>
					<div id="mel_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" style="overflow-x: auto;width: 100%;">
					
					<table id="main_list_table" class="table table-thin table-set table-striped table-hover" style='border:1px soldi #ddd;'>
					<thead class='thead-new' >
								<tr class='thead-bgtr'>
									<th class="fixed-column colwidth-7 border-all" onclick="orderBy('fxjlbzt')" id="fxjlbzt_order" rowspan="2" style='vertical-align:middle;' name='zt'>
										<div class="font-size-12 line-height-18 ">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th  class="sorting colwidth-10 border-all"  onclick="orderBy('fxrq')" id="fxrq_order" rowspan="2" style='vertical-align:middle;' name='fxrq'>
										<div class="font-size-12 line-height-18">飞行日期</div>
										<div class="font-size-9 line-height-18">Flight Date</div>
									</th>
									<th class="sorting colwidth-13 border-all" nclick="orderBy('fjzch')" id="fjzch_order" rowspan="2" style='vertical-align:middle;' name='fjzch'>
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C Reg</div>
									</th>
									<th class="sorting colwidth-13 border-all" onclick="orderBy('fxjlbh')" id="fxjlbh_order" rowspan="2" style='vertical-align:middle;' name='jldh'>
										<div class="important">
											<div class="font-size-12 line-height-18">记录单号</div>
											<div class="font-size-9 line-height-18">FLB No.</div>
										</div>
									</th>
									<th class="sorting colwidth-10 border-all" onclick="orderBy('hbh')" id="hbh_order" rowspan="2" style='vertical-align:middle;' name='hbh'>
										<div class="important">
											<div class="font-size-12 line-height-18">航班号</div>
											<div class="font-size-9 line-height-18">FLT. No.</div>
										</div>
									</th>
									<th class="sorting colwidth-7 border-all" onclick="orderBy('jlbym')" id="jlbym_order" rowspan="2" style='vertical-align:middle;' name='jlbym'>
										<div class="important">
											<div class="font-size-12 line-height-18">记录本页码</div>
											<div class="font-size-9 line-height-18">Page</div>
										</div>
									</th>
									<th class="sorting colwidth-7 border-all" onclick="orderBy('hc')" id="hc_order" rowspan="2" style='vertical-align:middle;' name='hc'>
										<div class="important">
											<div class="font-size-12 line-height-18">航次</div>
											<div class="font-size-9 line-height-18">Voyage</div>
										</div>
									</th>
									<th class="sorting colwidth-10 border-all" onclick="orderBy('fxrwlx')" id="fxrwlx_order" rowspan="2" style='vertical-align:middle;' name='fxrwlx'>
										<div class="font-size-12 line-height-18">任务类型</div>
										<div class="font-size-9 line-height-18">Task Type</div>
									</th>
									<th class=" colwidth-15 border-all" colspan="2" children="qfz,zlz">
										<div class="font-size-12 line-height-18">航站</div>
										<!-- <div class="font-size-9 line-height-18">Station</div> -->
									</th>
									<th class=" colwidth-70 border-all" colspan="6" children="tc,ld,kc,qf,bcsy,bcfc">
										<div class="font-size-12 line-height-18">飞行时间</div>
										<!-- <div class="font-size-9 line-height-18">Flight Hour</div> -->
									</th>
									<th class="sorting colwidth-12 border-all" rowspan="2" style='vertical-align:middle;' name='ljfc'> 
										<div class="font-size-12 line-height-18">本次/累计 FC</div>
										<div class="font-size-9 line-height-18">FLTHR</div>
									</th>
									<th class="sorting colwidth-12 border-all"  rowspan="2" style='vertical-align:middle;' name='lxqlcs'>
										<div class="font-size-12 line-height-18">连续起落次数</div>
										<div class="font-size-9 line-height-18">Touch GO</div>
									</th>
									<th class=" colwidth-50 border-all" id="fdjTh1" colspan="4" children="foneBJ,foneEH,foneEC,fonehy">
										<div class="font-size-12 line-height-18">1#发动机</div>
										<!-- <div class="font-size-9 line-height-18">ENG No.1</div> -->
									</th>
									<th class="colwidth-50 border-all" id="fdjTh2" colspan="4" children="ftwoBJ,ftwoEH,ftwoEC,ftwohy">
										<div class="font-size-12 line-height-18">2#发动机</div>
										<!-- <div class="font-size-9 line-height-18">ENG No.2</div> -->
									</th>
									<th class=" colwidth-50 border-all" id="fdjTh3" colspan="4" children="fthreeBJ,fthreeEH,fthreeEC,fthreehy">
										<div class="font-size-12 line-height-18">3#发动机</div>
										<!-- <div class="font-size-9 line-height-18">ENG No.3</div> -->
									</th>
									<th class=" colwidth-50 border-all" id="fdjTh4" colspan="4" children="ffourBJ,ffourEH,ffourEC,ffourhy">
										<div class="font-size-12 line-height-18">4#发动机</div>
										<!-- <div class="font-size-9 line-height-18">ENG No.4</div> -->
									</th>
									<th class=" colwidth-70 border-all"  colspan="4" children="apuBJ,apuh,apuc,apuhy">
										<div class="font-size-12 line-height-18">APU</div>
										<!-- <div class="font-size-9 line-height-18">APU</div> -->
									</th>
									<th class="colwidth-10 border-all" rowspan="2" style='vertical-align:middle;' name='idgtk'>
										<div class="font-size-12 line-height-18">IDG脱开时间</div>
										<div class="font-size-9 line-height-18">Search Time</div>
									</th>
									<th class="colwidth-10 border-all" rowspan="2" style='vertical-align:middle;' name='yyy' >
										<div class="font-size-12 line-height-18">液压油</div>
										<div class="font-size-9 line-height-18">HYD</div>
									</th>
									<th class=" colwidth-50 border-all" colspan="5" children="cyl,jyl,fxqzyl,fxhsyyl,xhyl">
										<div class="font-size-12 line-height-18">燃油油量</div>
										<!-- <div class="font-size-9 line-height-18">FUEL QTY</div> -->
									</th>
								<!-- 	<th class="sorting colwidth-7 border-all" onclick="orderBy('fxr')" id="fxr_order" rowspan="2" style='vertical-align:middle;' name='fxr'>
										<div class="font-size-12 line-height-18">放行人</div>
										<div class="font-size-9 line-height-18">Release</div>
									</th> -->
									<th class="sorting colwidth-10 border-all" onclick="orderBy('jz')" id="jz_order" rowspan="2" style='vertical-align:middle;' name='jc'>
										<div class="font-size-12 line-height-18">机长</div>
										<div class="font-size-9 line-height-18">Captain</div>
									</th>
									<th class="sorting colwidth-10 border-all" onclick="orderBy('zdrid')" id="zdrid_order" rowspan="2" style='vertical-align:middle;' name='zdrid'>
										<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator</div>
									</th>
									<th class="sorting colwidth-13 border-all" onclick="orderBy('zdsj')" id="zdsj_order" rowspan="2" style='vertical-align:middle;' name='zdsj'>
										<div class="font-size-12 line-height-18">制单时间</div>
										<div class="font-size-9 line-height-18">Create Time</div>
									</th>
									<th class="sorting colwidth-13 border-all" onclick="orderBy('dprtcode')" id="dprtcode_order" rowspan="2" style='vertical-align:middle;' name='dprtcode'>
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
								<tr class='thead-bgtr'>
									<th name='qfz'>
										<div class="font-size-12 line-height-18">起飞站</div>
										<!-- <div class="font-size-9 line-height-18">DEP</div> -->
									</th>
									<th class='border-right' name='zlz'>
										<div class="font-size-12 line-height-18">着陆站</div>
										<!-- <div class="font-size-9 line-height-18">DES</div> -->
									</th>
									<th name='kc'>
										<div class="font-size-12 line-height-18">开车</div>
										<!-- <div class="font-size-9 line-height-18">Start</div> -->
									</th>
									<th name='qf'>
										<div class="font-size-12 line-height-18">起飞</div>
										<!-- <div class="font-size-9 line-height-18">T.O</div> -->
									 </th>
									 <th name='ld'>
										<div class="font-size-12 line-height-18">落地</div>
										<!-- <div class="font-size-9 line-height-18">Land</div> -->
									</th>
									<th name='tc'>
										<div class="font-size-12 line-height-18">停车</div>
										<!-- <div class="font-size-9 line-height-18">Stop</div> -->
									</th>
									<th name='bcsy'>
										<div class="font-size-12 line-height-18">本次使用</div>
										<!-- <div class="font-size-9 line-height-18">Block</div> -->
									</th>
									<th name='bcfc'>
										<div class="font-size-12 line-height-18">本次/累计 FH</div>
										<!-- <div class="font-size-9 line-height-18">FLTHR</div> -->
									</th>
									<th name='foneBJ'>
										<div class="font-size-12 line-height-18">部件</div>
										<!-- <div class="font-size-9 line-height-18">Parts</div> -->
									</th>
									<th name='foneEH'>
										<div class="font-size-12 line-height-18">本次/累计 EH</div>
										<!-- <div class="font-size-9 line-height-18">EH</div> -->
									</th>
									<th name='foneEC'>
										<div class="font-size-12 line-height-18">本次 /累计 EC</div>
										<!-- <div class="font-size-9 line-height-18">EC</div> -->
									</th>
									<th class='border-right' name='fonehy'>
										<div class="font-size-12 line-height-18">滑油添加量</div>
										<!-- <div class="font-size-9 line-height-18">Oil</div> -->
									</th>
									<th name='ftwoBJ'>
										<div class="font-size-12 line-height-18">部件</div>
										<!-- <div class="font-size-9 line-height-18">Parts</div> -->
									</th>
									<th name='ftwoEH'>
										<div class="font-size-12 line-height-18">本次/累计 EH</div>
										<!-- <div class="font-size-9 line-height-18">EH</div> -->
									</th>
									<th name='ftwoEC'>
										<div class="font-size-12 line-height-18">本次 /累计  EC</div>
										<!-- <div class="font-size-9 line-height-18">EC</div> -->
									</th>
									<th class='border-right' name='ftwohy'>
										<div class="font-size-12 line-height-18">滑油添加量</div>
										<!-- <div class="font-size-9 line-height-18">Oil</div> -->
									</th>
									<th name='fthreeBJ'>
										<div class="font-size-12 line-height-18">部件</div>
										<!-- <div class="font-size-9 line-height-18">Parts</div> -->
									</th>
									<th name='fthreeEH'>
										<div class="font-size-12 line-height-18">本次/累计 EH</div>
										<!-- <div class="font-size-9 line-height-18">EH</div> -->
									</th>
									<th name='fthreeEC'>
										<div class="font-size-12 line-height-18">本次 /累计  EC</div>
										<!-- <div class="font-size-9 line-height-18">EC</div> -->
									</th>
									<th class='border-right' name='fthreehy'>
										<div class="font-size-12 line-height-18">滑油添加量</div>
										<!-- <div class="font-size-9 line-height-18">Oil</div> -->
									</th>
									<th name='ffourBJ'>
										<div class="font-size-12 line-height-18">部件</div>
										<!-- <div class="font-size-9 line-height-18">Parts</div> -->
									</th>
									<th name='ffourEH'>
										<div class="font-size-12 line-height-18">本次/累计 EH</div>
										<!-- <div class="font-size-9 line-height-18">EH</div> -->
									</th>
									<th name='ffourEC'>
										<div class="font-size-12 line-height-18">本次 /累计 EC</div>
										<!-- <div class="font-size-9 line-height-18">EC</div> -->
									</th>
									<th class='border-right' name='ffourhy'>
										<div class="font-size-12 line-height-18">滑油添加量</div>
										<!-- <div class="font-size-9 line-height-18">Oil</div> -->
									</th>
									<th name='apuBJ'>
										<div class="font-size-12 line-height-18">部件</div>
										<!-- <div class="font-size-9 line-height-18">Parts</div> -->
									</th>
									<th name='apuh'>
										<div class="font-size-12 line-height-18">本次/累计 APUH</div>
										<!-- <div class="font-size-9 line-height-18">APUH</div> -->
									</th>
									<th name='apuc'>
										<div class="font-size-12 line-height-18">本次 /累计  APUC</div>
										<!-- <div class="font-size-9 line-height-18">APUC</div> -->
									</th>
									<th class='border-right' name='apuhy'>
										<div class="font-size-12 line-height-18">滑油添加量</div>
										<!-- <div class="font-size-9 line-height-18">Oil</div> -->
									</th>
									<th name='cyl'>
										<div class="font-size-12 line-height-18">存油量</div>
										<!-- <div class="font-size-9 line-height-18">Fuel Remaining</div> -->
									</th>
									<th name='jyl'>
										<div class="font-size-12 line-height-18">加油量</div>
										<!-- <div class="font-size-9 line-height-18">Fuel Add</div> -->
									</th>
									<th name='fxqzyl'>
										<div class="font-size-12 line-height-18">飞行前总油量</div>
										<!-- <div class="font-size-9 line-height-18">Fuel Total</div> -->
									</th>
									<th name='fxhsyyl'>
										<div class="font-size-12 line-height-18">飞行后剩余油量</div>
										<!-- <div class="font-size-9 line-height-18">AFT. LDG</div> -->
									</th>
									<th name='xhyl'>
										<div class="font-size-12 line-height-18">消耗油量</div>
										<!-- <div class="font-size-9 line-height-18">CONS QTY</div> -->
									</th>
								</tr>
								
							</thead>
							<thead  id="xiaoji" style='border:1px solid #ddd;'>
							</thead>
							
							<tbody id="list" style='border:1px solid #ddd;' >
							</tbody>
					</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
    <%@ include file="/WEB-INF/views/produce/aircraftinfo/aircraftinfo_open.jsp" %> 
    <%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
    <%@ include file="/WEB-INF/views/open_win/import.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/flighthistory/flighthistory_main.js"></script>
</body>
</html>