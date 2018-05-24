<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>计量工具</title>
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
var paramJgdm = '${param.dprtcode}';
var paramYjbs = '${param.yjbs}';
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
// 					search();//调用主列表页查询
					selecttoolequipmentModal.search()
					toolcheck.search();
				}
			}
		});
	});
</script>

</head>

<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content table-table-type">
	<input type="hidden" id="userid" value="${user.id}" />
	<input type="hidden" id="jldj" value='${jldj}' />
	<input type="hidden" id="id"  />
	<input type="hidden" id="djzt"  />
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0" id="toolcheckBody">
			  <div  class='searchContent row-height' >
				<div class='margin-top-0'>
					<div class=" col-lg-5 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
						<a href="javascript:toolcheck_add_modal.open();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission" permissioncode='produce:reservation:defect:main:01'>
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a>
						
						&nbsp;&nbsp;&nbsp;<a href="javascript:showData();" style="height:34px;line-height:34px;">检查未维护数据</a>
						
						<!-- <a href="javascript:toolcheck.exportExcel();"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left  checkPermission" permissioncode='produce:reservation:defect:main:08'>
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a> -->
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " >
						<span  class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">下次校验日期</div>
							<div class="font-size-9">Next Date</div>
						</span>
						<div id="jxdiv" class="col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly id="jyXcrq" />
						</div>
					</div>
					<div class="col-lg-4 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group row-height" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='编号/名称/规格/型号/工具编号/部件号'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="toolcheck.search();" style='margin-right:0px !important;'>
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="toolcheck.openOrHide();">
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
					<div class="clearfix"></div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">最近校验日期</div>
							<div class="font-size-9">Recent Date</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly id="jyScrq" />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="toolcheck.onchangeDprtcode()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-lg-3  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="toolcheck.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>
			<div class='table_pagination'>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" c-height="55%" id="tableId" style="overflow-x: auto;">
					<table  id="toolcheck_Table" class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th  class="fixed-column colwidth-8">
									<div class="font-size-12 line-height-18 ">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th width='50' class="fixed-column">
									<div class="font-size-12 line-height-18">标识</div>
									<div class="font-size-9 line-height-18">Mark</div>
								</th>
								<th  class="fixed-column sorting  colwidth-10" onclick="toolcheck.orderBy('bjxlh')" id="bjxlh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">编号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</div>
								</th>
								<th class="sorting  colwidth-14" onclick="toolcheck.orderBy('zwms')" id="zwms_order">
									<div class="important">
										<div class="font-size-12 line-height-18">名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									</div>
								</th>
								<th class="sorting  colwidth-12" onclick="toolcheck.orderBy('gg')" id="gg_order">
									<div class="important">
										<div class="font-size-12 line-height-18">规格</div>
										<div class="font-size-9 line-height-18">Spec</div>
									</div>
								</th>
								<th  class="sorting  colwidth-13" onclick="toolcheck.orderBy('xh')" id="xh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">型号</div>
										<div class="font-size-9 line-height-18">Model</div>
									</div>
								</th>	
								<th  class="sorting  colwidth-13" onclick="toolcheck.orderBy('jlfs')" id="jlfs_order">
									<div class="font-size-12 line-height-18">计量方式</div>
									<div class="font-size-9 line-height-18">Mode</div>
								</th>	
								<th  class="sorting  colwidth-8" onclick="toolcheck.orderBy('jldj')" id="jldj_order">
									<div class="font-size-12 line-height-18">计量等级</div>
									<div class="font-size-9 line-height-18">Grade</div>
								</th>	
								<th  class="sorting  colwidth-8" onclick="toolcheck.orderBy('jy_zq')" id="jy_zq_order">
									<div class="font-size-12 line-height-18">周期/单位</div>
									<div class="font-size-9 line-height-18">Cycle/Unit</div>
								</th>	
								<th  class="sorting  colwidth-10" onclick="toolcheck.orderBy('jy_scrq')" id="jy_scrq_order">
									<div class="font-size-12 line-height-18">最近校验日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>	
								<th  class="sorting  colwidth-10" onclick="toolcheck.orderBy('jy_xcrq')" id="jy_xcrq_order">
									<div class="font-size-12 line-height-18">下次校验日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>	
								<th  class="sorting  colwidth-8" onclick="toolcheck.orderBy('syts')" id="syts_order">
									<div class="font-size-12 line-height-18">剩余天数</div>
									<div class="font-size-9 line-height-18">Remain</div>
								</th>	
								<th  class="sorting  colwidth-15" onclick="toolcheck.orderBy('bz')" id="bz_order">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Desc</div>
								</th>	
								<th  class=" colwidth-8" >
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div>
								</th>	
								<th  class="sorting  colwidth-13" onclick="toolcheck.orderBy('MES_bjxlh')" id="MES_bjxlh_order" >
									<div class="important">
										<div class="font-size-12 line-height-18">工具编号</div>
										<div class="font-size-9 line-height-18">Tool No.</div>
									</div>
								</th>
								<th  class="sorting  colwidth-13"  onclick="toolcheck.orderBy('MES_bjid')" id="MES_bjid_order">
									<div class="important">
										<div class="font-size-12 line-height-18">部件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
								<th  class="  colwidth-15" >
									<div class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Position</div>
								</th>
								<th  class="sorting  colwidth-10" onclick="toolcheck.orderBy('rksj')" id="rksj_order" >
									<div class="font-size-12 line-height-18">入库日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th  class="sorting  colwidth-13" onclick="toolcheck.orderBy('WHR_ID')" id="WHR_ID_order">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th  class="sorting  colwidth-15" onclick="toolcheck.orderBy('whsj')" id="whsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th  class="colwidth-13">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="toolcheck_list">
						</tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="toolcheck_pagination">
				</div>
				<div class='clearfix'></div>
				
			</div>
			<div class='displayContent' style='display:none;' id='bottom_hidden_content'>
					<%@ include file="/WEB-INF/views/material2/toolcheck/toolcheck_log.jsp"%>
				</div>	
		</div>
	</div>
</div>	
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/toolcheck/toolcheck_main.js"></script><!--当前界面js  -->
<%@ include file="/WEB-INF/views/material2/toolcheck/toolcheck_open.jsp" %> <!--新增编辑界面  -->
<%@ include file="/WEB-INF/views/material2/toolcheck/toolcheck_no_maintenance.jsp" %> <!--新增检查未维护数据界面  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->

</body>
</html>
