<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞机故障监控</title>
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
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
					searchFjgzRecord(); //调用主列表页查询
				}
			}
		});
	});
</script>

</head>

<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content table-table-type">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
			<div class="panel-body">
			  <div  class='searchContent row-height' >
				<div class='margin-top-0'>
					<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
						<a href="javascript:Planefaultmonitoring_Add_Modal.open();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission" permissioncode='produce:fault:monitoring:main:01'>
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a>
<!-- 						<a href="javascript:exportExcel();"  class="btn btn-primary padding-top-1 margin-left-10  padding-bottom-1 pull-left row-height " > -->
<!-- 							<div class="font-size-12">导出</div> -->
<!-- 							<div class="font-size-9">Export</div> -->
<!-- 						</a> -->
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</span>
						<div id="jxdiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							 <select class='form-control' id='fjzch' name="fjzch" onchange="searchFjgzRecord();">
							 </select>
						</div>
					</div>
				<!-- 搜索框start -->
					<!-- 搜索框start -->
				   <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>	
						<!-- 搜索框start -->
						<div class=" col-xs-12 input-group input-group-search row-height">
							
						     <input type="text" placeholder='飞机注册号/故障信息'  id="keyword" class="form-control ">
							
                             <div class="input-group-addon btn-searchnew" >
			                    <button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchFjgzRecord();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                	</div>
                	        <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="openOrHide();">
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
				<!-- 搜索框end -->
				<div class="clearfix"></div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">ATA章节号</div>
							<div class="font-size-9">ATA</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group" style="width: 100%">
								<input type="hidden"  name="zjhMax"  id="zjhMax">
								<input type="text"  name="zjhmsMax" class="form-control  readonly-style " id="zjhmsMax" readonly="readonly" ondblclick='initFixChapter();'>
								<span class=" input-group-btn" >
									<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="initFixChapter()">
										<i class="icon-search cursor-pointer" ></i>
									</button>
								</span>
		                	</div>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="changeDpr();">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-lg-3  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>
			<div class='table_pagination'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" c-height="35%" id="tableId" style="overflow-x: auto;">
					<table  id="fjgz_record_sheet_table" class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
									<th  class="fixed-column colwidth-10">
										<div class="font-size-12 line-height-18 ">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('fjzch')" id="fjzch_order">
										<div class="important">
											<div class="font-size-12 line-height-18">飞机注册号</div>
											<div class="font-size-9 line-height-18">A/C REG</div>
										</div>
									</th>
									<th class="colwidth-20 sorting" onclick="orderBy('zjh')" id="zjh_order">
											<div class="font-size-12 line-height-18">章节号</div>
											<div class="font-size-9 line-height-18">ATA</div>
									</th>
									<th class="colwidth-20 sorting" onclick="orderBy('gzxx')" id="gzxx_order">
										<div class="important">
											<div class="font-size-12 line-height-18">故障信息</div>
											<div class="font-size-9 line-height-18">Fault info</div>
										</div>
									</th>
									<th class="colwidth-10  sorting" onclick="orderBy('gzcs')" id="gzcs_order" >
										<div class="font-size-12 line-height-18">故障次数</div>
										<div class="font-size-9 line-height-18">Failure times</div>
									</th>
									<th class="colwidth-9 sorting" onclick="orderBy('zjfsrq')" id="zjfsrq_order">
										<div class="font-size-12 line-height-18">最近发生日期</div>
										<div class="font-size-9 line-height-18">Latest date</div>
									</th>
									<th class="colwidth-5 sorting" onclick="orderBy('gbzt')" id="gbzt_order">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-13 cursor-pointer" onclick="vieworhideZjqkContent()" name="fj">
										<div class="font-size-12 line-height-18">附件</div>
										<div class="font-size-9 line-height-18">Attachment</div>
									</th>
									<th class="colwidth-20 sorting" onclick="orderBy('bz')" id="bz_order">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('whrid')" id="whrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('whsj')" id="whsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="colwidth-9 ">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="fjgzjk_list">
							</tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="fjgzjk_pagination">
				</div>
				<div class='clearfix'></div>
			</div>	
			<%@ include file="/WEB-INF/views/produce/planefaultmonitoring/planefaultmonitoring_mainone.jsp"%>  <!--故障处理记录  -->
		</div>
	</div>
</div>	
	<!-- 关闭对话框start -->
	<div class="modal fade modal-new" id="alertModalFjgzjk" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:550px!important;">
			<div class="modal-content">
				<div class="modal-header modal-header-new">
					<h4 class="modal-title">
						<div class="pull-left">
							<div class="font-size-14 "><span id="modalName">关闭</span></div>
							<div class="font-size-12"><span id="modalEname">Close</span></div>
						</div>
						 <div class="pull-right">
						 	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						 </div>
						 <div class="clearfix"></div>
	                </h4>
	            </div>
				<div class="modal-body margin-top-10 padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-body padding-top-0 padding-bottom-0" >
							<div class="col-lg-12 col-xs-12 col-sm-12 padding-left-0 padding-right-0">
								<input id="gbyyid" type="hidden" />
								<div class="col-xs-12 col-sm-12 col-lg-12 padding-right-0 padding-left-0 margin-top-10 margin-bottom-10 ">
									<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">关闭人</div>
										<div class="font-size-9">Person</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<input type="text" id="gbrname" readonly="readonly" class="form-control " />
										
									</div>
								</div> 
								<div id="gb" class="col-xs-12 col-sm-12 col-lg-12 padding-right-0 padding-left-0  margin-bottom-10 ">
									<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">关闭时间</div>
										<div class="font-size-9">Date</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
										<input type="text" id="gbsj" readonly="readonly" class="form-control" />
										
									</div>
								</div> 
								<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0 ">
									<div class="font-size-12 line-height-18"><span style="color: red" id="gbyymark">*</span>关闭原因</div>
									<div class="font-size-9 line-height-18">Reason</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0 margin-bottom-10">
									<textarea class="form-control" id="gbyy"    maxlength="150" ></textarea>
								</div>
									
								<div class="clearfix"></div>
							</div> 
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<div class="modal-footer ">
					<div class="col-xs-12 padding-leftright-8">
						<div class="input-group">
							<span class="input-group-addon modalfootertip">
								<i class="glyphicon glyphicon-info-sign alert-info" style="display: none;"></i><p class="alert-info-message" title=""></p>
							</span>
							<span class="input-group-addon modalfooterbtn">
								<button onclick="zdjsOver()" id="qd" class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div></button>&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
							</span>
						</div>
					</div>	             
	             </div>
			</div>
		</div>
	</div>
	<!-- 关闭对话框end -->

<!--日志Start  -->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<!--日志End  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/planefaultmonitoring/planefaultmonitoring_main.js"></script><!--当前js  -->
<%@ include file="/WEB-INF/views/produce/planefaultmonitoring/planefaultmonitoring_open.jsp" %> <!--新增编辑界面  -->
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!--章节号弹窗-->
</body>
</html>
