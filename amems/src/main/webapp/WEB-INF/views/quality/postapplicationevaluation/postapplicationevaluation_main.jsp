<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>岗位授权评估</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
	.spacing {
		margin-left:3px;
		margin-right:3px;
	}
</style>

<script type="text/javascript">
var todo_ywid = "${param.todo_ywid}";
var todo_jd = "${param.todo_jd}";
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					post_main_panel_content.search();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body >
	<div class="page-content" >
		<input type="hidden" id="post_main_panel_id"  />
		<div class="panel panel-primary"  id='post_main_panel_content'>
			<div class="panel-heading" id="NavigationBar"></div>
            <div class="panel-body padding-bottom-0">
             <!-- 搜索框 -->
			    <div  class='searchContent margin-top-0 row-height' >
			    <!-- 上传按钮  -->
				
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">申请岗位</div>
							<div class="font-size-9">Post</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<input type='text' placeholder='编号/描述' id="sqgw_search"  class='form-control'/>
						</div>
				</div>
				<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select class='form-control' id="zt_search" onchange="post_main_panel_content.search()">
								<option value="" selected="true">显示全部All</option>
								<c:forEach items="${postStatusEnum}" var="item">
										<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
				</div> 
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<!-- <div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div> -->
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div text-right" >
							<label style='margin-top:6px;font-weight:normal;' >
								<input onclick="post_main_panel_content.search();" id="yxq_search" name='yxq_search' type='checkbox' style='vertical-align:text-bottom' />&nbsp;仅显示未设置有效期
							</label>
						</div>
				</div> 
				<!-- 关键字搜索 -->
				<div class="pull-right col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group  " style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='授权单号/人员编号/姓名/部门'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" style='margin-right:0px !important;' onclick="post_main_panel_content.search();">
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
                    <div class="input-group-addon btn-searchnew-more">
	                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight"  id="btn"  onclick="post_main_panel_content.more();">
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
					<!-- 申请人 -->
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">申请人</div>
							<div class="font-size-9">Applicant</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input id="sqr_search" type='text' class='form-control'/>
						</div>
					</div>
					<!-- 组织机构 -->
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="post_main_panel_content.search()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="post_main_panel_content.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
					<div class='clearfix'></div>
				</div>
				<!-- 搜索框End -->
				<!-- 表格 -->
				<div id="post_main_Table_top_div" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" style="overflow-x: auto;width: 100%;">
					<table id="post_main_Table" class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="fixed-column colwidth-9" >
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="fixed-column colwidth-5" >
									<div class="font-size-12 line-height-18">标识</div>
									<div class="font-size-9 line-height-18">Mark</div>
								</th>
								<th class="fixed-column colwidth-13 sorting" onclick="post_main_panel_content.orderBy('sqsqdh','')" name="column_sqsqdh">
									<div class="important">
										<div class="font-size-12 line-height-18">授权单号</div>
										<div class="font-size-9 line-height-18">Authorized No.</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="post_main_panel_content.orderBy('rybh','')" name="column_rybh" >
									<div class="important">
										<div class="font-size-12 line-height-18">人员编号</div>
										<div class="font-size-9 line-height-18">Maintenance No.</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="post_main_panel_content.orderBy('xm','')" name="column_xm">
									<div class="important">
										<div class="font-size-12 line-height-18">姓名</div>
										<div class="font-size-9 line-height-18">Name</div>
									</div>
								</th>
								<th class="colwidth-15" >
									<div class="font-size-12 line-height-18">现任职务</div>
									<div class="font-size-9 line-height-18">Current Position</div>
								</th>
								<th class="colwidth-15 sorting" onclick="post_main_panel_content.orderBy('szdw','')" name="column_szdw">
									<div class="important">
										<div class="font-size-12 line-height-18">部门</div>
										<div class="font-size-9 line-height-18">Department</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="post_main_panel_content.orderBy('sqlx','')" name="column_sqlx" >
									<div class="font-size-12 line-height-18">申请类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-15 sorting" onclick="post_main_panel_content.orderBy('dgbh','')" name="column_dgbh" >
									<div class="font-size-12 line-height-18">申请授权项目</div>
									<div class="font-size-9 line-height-18">Post</div>
								</th>
								<th class="colwidth-10 sorting" onclick="post_main_panel_content.orderBy('zt','')" name="column_zt" >
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th th_class="cont-exp2" td_class="affectedDisplayFile" table_id="post_main_Table" class="cont-exp2 downward colwidth-30 " onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
									<div class="font-size-12 line-height-18">岗位有效期</div>
									<div class="font-size-9 line-height-18">Post Validity</div>
								</th>
								<th  class="sorting colwidth-13" onclick="post_main_panel_content.orderBy('whrid','')" name="column_whrid" >
									<div class="font-size-12 line-height-18">维护人</div>
								<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th  class="sorting colwidth-13" onclick="post_main_panel_content.orderBy('whsj','')" name="column_whsj" >
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th  class="colwidth-15">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="post_main_list">
						</tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="post_main_pagination"></div>
				<div class='clearfix'></div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/postapplicationevaluation/postapplicationevaluation_main.js"></script><!--当前界面js  -->
	<%@ include file="/WEB-INF/views/quality/postapplicationevaluation/postapplicationevaluation_open.jsp" %> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/attachments_list_edit.jsp"%><!-------附件对话框 -------->
	<%@ include file="/WEB-INF/views/produce/projectkeep/Confirm.jsp"%><!-- 指定结束 -->
	<%@ include file="/WEB-INF/views/quality/postapplicationevaluation/setting_effect_win.jsp" %> 
</body>
</html>