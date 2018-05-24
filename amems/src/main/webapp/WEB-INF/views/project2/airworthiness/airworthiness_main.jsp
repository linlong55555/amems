<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>适航性资料</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
.multiselect{
overflow:hidden;
}
</style>
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
				$('#airworthinessMainSearch').trigger('click');;//调用主列表页查询
			}
		}
	});
});
</script>

</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtment" value="${user.bmdm}" />
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<input type="hidden" id="userDisplayName" value="${user.displayName}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content" id='airworthiness_mainpage'>
		<div class="panel panel-primary">
		  <!-- panel-heading -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<!-- 搜索框 -->
			    <div  class='searchContent margin-top-0 row-height' >
			    <!-- 上传按钮  -->
				<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group  ">
					<a href="javascript:save();" permissioncode='project2:airworthiness:main:01'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
						<div class="font-size-12">上传</div>
						<div class="font-size-9">Upload</div>
					</a> 
					<a href="javascript:exportExcel();" type="button" class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode='project2:airworthiness:main:04'>
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a>
				</div>
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">文件类型</div>
							<div class="font-size-9">Doc. type</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" id="wjlxHtml">
						</div>
				</div>
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源</div>
							<div class="font-size-9">Issued By</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select class='form-control' id="ly">
								<option value="" >显示全部All</option>
						    </select>
						</div>
				</div> 
				<!-- 关键字搜索 -->
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='文件编号/主题/ATA章节号/备注'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" id="airworthinessMainSearch" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="airworthinessMain.reload();" style='margin-right:0px !important;'>
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
				<div class='clearfix'></div>
				<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none border-cccccc" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt" class="form-control "  name="zt">
							<option value="">显示全部All</option>
							<option value="0" >保存</option>
							<option value="1">提交</option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">评估期限</div>
							<div class="font-size-9">Assess Limit</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly id="pgqx" />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">生效日期</div>
							<div class="font-size-9">Effect Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly id="sxrq" />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">是否评估</div>
							<div class="font-size-9">Is Estimate</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' id="xpgbs1" style='vertical-align:text-bottom;' checked="checked" />&nbsp;是&nbsp;&nbsp;</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' id="xpgbs2" style='vertical-align:text-bottom;' checked="checked" />&nbsp;否&nbsp;&nbsp;</label>
						</div>
					</div>
					<div class='clearfix'></div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control "  name="dprtcode" onchange="dprtChange()" >
								<c:forEach items="${accessDepartment}" var="type">
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
				</div>
					<div class='clearfix'></div>
				</div>
				<!-- 搜索框End -->
				
				<div class='table_pagination'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" c-height="45%" id="tableId" style="overflow-x: auto;width: 100%;">
						<table  id="quality_check_list_table" class="table table-thin table-bordered table-set table-hover table-striped">
							<thead>
								<tr>
									<th class="fixed-column colwidth-7" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th  class="colwidth-7" >
										<div class="font-size-12 line-height-18">标识</div>
										<div class="font-size-9 line-height-18">Mark</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('jswjlx')" id="jswjlx_order">
										<div class="font-size-12 line-height-18">文件类型</div>
										<div class="font-size-9 line-height-18">Doc. type</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('jswjly')" id="jswjly_order">
										<div class="font-size-12 line-height-18">来源</div>
										<div class="font-size-9 line-height-18">Issued By</div>
									</th>
									<th class="colwidth-20 sorting" onclick="orderBy('jswjbh')" id="jswjbh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">文件编号</div>
											<div class="font-size-9 line-height-18">Doc. No.</div>
										</div>
									</th>
									<th class="colwidth-5 sorting" onclick="orderBy('bb')" id="bb_order">
										<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Rev.</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('xzah')" id="xzah_order">
										<div class="font-size-12 line-height-18">修正案号</div>
										<div class="font-size-9 line-height-18">Amendment</div>
									</th>
									<th class="colwidth-30 sorting" onclick="orderBy('jswjzt')" id="jswjzt_order">
										<div class="important">
											<div class="font-size-12 line-height-18">主题</div>
											<div class="font-size-9 line-height-18">Subject</div>
										</div>
									</th>
									<th class="colwidth-7" onclick="orderBy('xpgbs')" id="xpgbs_order">
										<div class="font-size-12 line-height-18">需评估</div>
										<div class="font-size-9 line-height-18">Is Estimate</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('pgqx')" id="pgqx_order">
										<div class="font-size-12 line-height-18">评估期限</div>
										<div class="font-size-9 line-height-18">Assess Limit</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('syts')" id="syts_order">
										<div class="font-size-12 line-height-18">剩余(天)</div>
										<div class="font-size-9 line-height-18">Remain(Day)</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">评估范围</div>
										<div class="font-size-9 line-height-18">Applicability</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('gljswjid')" id="gljswjid_order">
										<div class="font-size-12 line-height-18">关联适航性资料</div>
										<div class="font-size-9 line-height-18">Related Doc.</div>
									</th>
									<th class="colwidth-5 sorting" onclick="orderBy('zt')" id="zt_order">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									
									<th class="colwidth-10 sorting" onclick="orderBy('ata')" id="ata_order">
										<div class="important">
											<div class="font-size-12 line-height-18">ATA章节号</div>
											<div class="font-size-9 line-height-18">ATA</div>
										</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('bfrq')" id="bfrq_order">
										<div class="font-size-12 line-height-18">颁发日期</div>
										<div class="font-size-9 line-height-18">Issue Date</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('sxrq')" id="sxrq_order">
										<div class="font-size-12 line-height-18">生效日期</div>
										<div class="font-size-9 line-height-18">Effect Date</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('dqrq')" id="dqrq_order">
										<div class="font-size-12 line-height-18">到期日期</div>
										<div class="font-size-9 line-height-18">Due Date</div>
									</th>
									<th class="colwidth-15 sorting" onclick="orderBy('bz')" id="bz_order">
										<div class="important">
											<div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-18">Note</div>
										</div>
									</th>
									<th class="colwidth-30" >
										<div class="font-size-12 line-height-18">文件</div>
										<div class="font-size-9 line-height-18">File</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('zdrid')" id="zdrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('zdsj')" id="zdsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
									
								</tr>
							</thead>
							<tbody id="airworthiness_list"></tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="airworthiness_pagination"></div>
				<div class='clearfix'></div>
				</div>	
				<!-- 隐藏的div -->
				<div class='displayContent' style='display:none;'>
				   <div class='col-xs-5 padding-left-0' style='padding-right:4px;'>
				    <%@ include file="/WEB-INF/views/project2/airworthiness/airworthiness_pgd.jsp"%>
				   </div>
				   <div class='col-xs-7 padding-right-0' style='padding-left:4px;'>
				   <%@ include file="/WEB-INF/views/project2/airworthiness/airworthiness_xdzl.jsp" %> 
				   </div>
				   
				   <div class='clearfix'></div>
				</div>
        	</div>
		</div>		
	</div>
    <%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%>
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
	<%@ include file="/WEB-INF/views/project2/airworthiness/airworthiness_open.jsp" %> 
	<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/search_technicalfile.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/airworthiness/airworthiness_main.js"></script>
	<%@ include file="/WEB-INF/views/project2/assessment/todo_feedback.jsp"%><!-- 反馈对话框 -->
</body>
</html>
