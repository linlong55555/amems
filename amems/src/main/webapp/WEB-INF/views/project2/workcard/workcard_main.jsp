<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工卡管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%> 
<style type="text/css">
	.spacing {
		margin-left:3px;
		margin-right:3px;
	}
</style>

<script type="text/javascript">
	//来源ID(评估单ID)
	var todo_lyid = "${param.todo_lyid}";
	var todo_fjjx = "${param.todo_fjjx}";
	var todo_ywid = "${param.todo_ywid}";
	var todo_dprtcode = "${param.todo_dprtcode}";
	var todo_jd = "${param.todo_jd}";
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
					$('#workCardMainSearch').trigger('click');//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content" id="work_card_main">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<div class='searchContent margin-top-0 row-height' >
					<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
						<a href="javascript:work_card_main.openWinAdd();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission" permissioncode="project2:workcard:main:01" >
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a>
						<a href="javascript:work_card_main.batchApproveWin(false);"  class="btn btn-primary padding-top-1  padding-bottom-1 pull-left checkPermission" permissioncode="project2:workcard:main:03"  style='margin-left:6px;'>
							<div class="font-size-12">批量审核</div>
							<div class="font-size-9">Check</div>
						</a>
						<a href="javascript:work_card_main.batchApproveWin(true);"  class="btn btn-primary padding-top-1  padding-bottom-1 pull-left checkPermission" permissioncode="project2:workcard:main:04" style='margin-left:6px;'>
							<div class="font-size-12">批量批准</div>
							<div class="font-size-9">Approve</div>
						</a>
						<a href="javascript:work_card_main.showImportModal();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="project2:workcard:main:08" style='margin-left:6px;'>
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>
						</a>
						<a href="javascript:work_card_main.exportExcel();"  class="btn btn-primary padding-top-1  padding-bottom-1 pull-left checkPermission" permissioncode="project2:workcard:main:09" style='margin-left:6px;'>
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a>
					</div>
					
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">机型</div>
							<div class="font-size-9 ">A/C Type</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
							<select id="jx_search" class="form-control" onchange="work_card_main.changeModel()">
								<option value="" selected="selected">显示全部All</option>
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">版本</div>
							<div class="font-size-9">Rev.</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0 label-input-div" >
							<select id="bb_search" class="form-control " onchange="work_card_main.search()" >
								<option value="current" selected="selected">当前</option>
								<option value="" >历史</option>
							</select>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='工卡编号/工卡标题/维修项目'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="workCardMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="work_card_main.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="work_card_main.more();">
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
				  <div class='clearfix'></div>
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">ATA章节号</div>
							<div class="font-size-9">ATA</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
							<input type="text" id="zjh_search" class="form-control readonly-style" ondblclick="work_card_main.openChapterWin()"  maxlength="20"  readonly/>
							<span class="input-group-btn">
								<button type="button" id="zjh_search_btn" class="btn btn-default" data-toggle="modal" onclick="work_card_main.openChapterWin()">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">工卡类型</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='gklx_search'></select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">工作类别</div>
							<div class="font-size-9">Category</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='gzlx_search'></select>
						</div>
					</div>
					
					<!-- <div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">区域</div>
							<div class="font-size-9">Zone</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='qy_search'></select>
						</div>
					</div> -->
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">专业</div>
							<div class="font-size-9">Skill</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zy_search" class="form-control" >
							</select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">必检</div>
							<div class="font-size-9">RII</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='isBj_search'>
								<option value="" selected="selected">显示全部All</option>
								<option value="1" >是</option>
								<option value="0" >否</option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">接近</div>
							<div class="font-size-9">Access</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
							<input type="text" id="jj_search" class="form-control readonly-style" ondblclick="work_card_main.openAccessWin()" maxlength="10" readonly/>
							<span class="input-group-btn">
								<button type="button" id="jj_search_btn" class="btn btn-default" data-toggle="modal" onclick="work_card_main.openAccessWin()">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">间隔/重复</div>
							<div class="font-size-9">Interval/Repeat</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left">
							<input type="text" id="bz_search" class="form-control" maxlength="100" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control" onchange="work_card_main.changeDprt()" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="work_card_main.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<div class="clearfix"></div>

				<div id="work_card_main_table_top_div" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="work_card_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1400px;">
						<thead>                              
							<tr>
								<th class="viewCol fixed-column colwidth-7 selectAllImg">
									<a href="#" onclick="SelectUtil.performSelectAll('work_card_main_table_top_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
									<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('work_card_main_table_top_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
								</th>
								<th class="fixed-column colwidth-7">
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-15 sorting" onclick="work_card_main.orderBy('jx','')" name="column_jx">
									<div class="font-size-12 line-height-18">机型</div>
									<div class="font-size-9 line-height-18">A/C Type</div>
								</th>
								<th class="colwidth-15 sorting" onclick="work_card_main.orderBy('gkh')" name="column_gkh">
									<div class="important">
										<div class="font-size-12 line-height-18">工卡编号</div>
										<div class="font-size-9 line-height-18">Work Card No.</div>
									</div>
								</th>
								<th class="colwidth-5 sorting" onclick="work_card_main.orderBy('bb')" name="column_bb" >
									<div class="font-size-12 line-height-18">版本</div>
									<div class="font-size-9 line-height-18">Rev.</div>
								</th>
								<th class="colwidth-30 sorting" onclick="work_card_main.orderBy('gzbt')" name="column_gzbt" >
									<div class="important">
										<div class="font-size-12 line-height-18">工卡标题</div>
										<div class="font-size-9 line-height-18">Title</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="work_card_main.orderBy('zt')" name="column_zt" >
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class="colwidth-9" >
									<div class="font-size-12 line-height-18">附件</div>
									<div class="font-size-9 line-height-18">Attachment</div>
								</th>
								<th class="colwidth-9 sorting" onclick="work_card_main.orderBy('gklx')" name="column_gklx" >
									<div class="font-size-12 line-height-18">工卡类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-10 sorting" onclick="work_card_main.orderBy('wxxmbh')" name="column_wxxmbh" >
									<div class="important">
										<div class="font-size-12 line-height-18">维修项目</div>
										<div class="font-size-9 line-height-18">Project</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="work_card_main.orderBy('zjh')" name="column_zjh" >
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</th>
								<th class="colwidth-30 sorting" onclick="work_card_main.orderBy('bz')" name="column_bz" >
									<div class="font-size-12 line-height-18">间隔/重复</div>
									<div class="font-size-9 line-height-18">Interval/Repeat</div>
								</th>
								<th class="colwidth-9 sorting" onclick="work_card_main.orderBy('gzlx')" name="column_gzlx" >
									<div class="font-size-12 line-height-18">工作类别</div>
									<div class="font-size-9 line-height-18">Category</div>
								</th>
								<th class="colwidth-9">
									<div class="font-size-12 line-height-18">参考工时</div>
									<div class="font-size-9 line-height-18">MHRs</div>
								</th>
								<th class="colwidth-7 sorting" onclick="work_card_main.orderBy('zy')" name="column_zy" >
									<div class="font-size-12 line-height-18">专业</div>
									<div class="font-size-9 line-height-18">Skill</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">工作组</div>
									<div class="font-size-9 line-height-18">Work Group</div>
								</th>
								<th class="colwidth-5 sorting" onclick="work_card_main.orderBy('is_bj')" name="column_is_bj" >
									<div class="font-size-12 line-height-18">必检</div>
									<div class="font-size-9 line-height-18">RII</div>
								</th>
								<th class="colwidth-9">
									<div class="font-size-12 line-height-18">区域</div>
									<div class="font-size-9 line-height-18">Zone</div>
								</th>
								<th class="colwidth-9">
									<div class="font-size-12 line-height-18">接近</div>
									<div class="font-size-9 line-height-18">Access</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">飞机站位</div>
									<div class="font-size-9 line-height-18">Aircraft Stations</div>
								</th>
								<th class="colwidth-13 sorting" onclick="work_card_main.orderBy('username')" name="column_username" >
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="colwidth-13 sorting" onclick="work_card_main.orderBy('whsj')" name="column_whsj" >
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-15" >
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr> 
						</thead>
						<tbody id="work_card_main_table_list">
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="work_card_main_table_pagination"></div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

<%@ include file="/WEB-INF/views/open_win/log.jsp"%><!-------日志 -------->
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%><!-------日志差异 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/workcard/workcard_main.js"></script>
<%@ include file="/WEB-INF/views/open_win/batchApprovel.jsp"%><!-------批量审批对话框 -------->
<%@ include file="/WEB-INF/views/project2/workcard/workcard_edit.jsp"%><!-------编辑对话框 -------->
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!-- ATA章节对话框 -->
<%@ include file="/WEB-INF/views/open_win/access_list_edit.jsp"%><!-- 接近对话框 -->
<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>