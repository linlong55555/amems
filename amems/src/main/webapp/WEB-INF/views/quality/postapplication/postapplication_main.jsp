<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>授权申请</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
var todo_ywid = "${param.todo_ywid}";
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
					search();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body >
	<div class="page-content" >
	<input type="hidden" id="userid" value="${user.id}">
		<input type="hidden" id="id"  />
		<input type="hidden" id="djzt"  />
		<div class="panel panel-primary"  id='panel_content'>
			<div class="panel-heading" id="NavigationBar"></div>
            <div class="panel-body padding-bottom-0">
             <!-- 搜索框 -->
			    <div  class='searchContent margin-top-0 row-height'>
			    <!-- 上传按钮  -->
				<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group  ">
					<a href="javascript:Post_Add_Modal.open();" permissioncode='quality:post:application:main:01'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
						<div class="font-size-12">新增申请</div>
						<div class="font-size-9">New Application</div>
					</a> 
				</div>
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">申请授权项目</div>
							<div class="font-size-9">Post</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<input type='text' placeholder='编号/描述' id="sqgw"  class='form-control'/>
						</div>
				</div>
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div id="ztsDiv" class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select class='form-control' id="zts"  onchange="search()">
								<option value="" selected="true">显示全部All</option>
								<c:forEach items="${postStatusEnum}" var="item">
										<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
				</div> 
				<!-- 关键字搜索 -->
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='授权单号/姓名'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1"  onclick="search();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
                    <div class="input-group-addon btn-searchnew-more">
	                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="openOrHide();">
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
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="onchangeDprtcode()">
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
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height"  c-height="55%" id="tableId" style="overflow-x: auto;">
						<table  id="postapplicationTable" class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th  class="fixed-column colwidth-10 sorting" onclick="orderBy('sqsqdh')" id="sqsqdh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">授权单号</div>
											<div class="font-size-9 line-height-18">Authorized No.</div>
										</div>
									</th>
									<th class="colwidth-12 sorting" onclick="orderBy('rybh')" id="rybh_order" >
										<div class="font-size-12 line-height-18">人员编号</div>
										<div class="font-size-9 line-height-18">Maintenance No.</div>
									</th>
									<th class="colwidth-12 sorting" onclick="orderBy('xm')" id="xm_order">
										<div class="important">
											<div class="font-size-12 line-height-18">姓名</div>
											<div class="font-size-9 line-height-18">Name</div>
										</div>
									</th>
									<th class="colwidth-5 sorting" onclick="orderBy('sqlx')" id="sqlx_order">
										<div class="font-size-12 line-height-18">申请类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th>
									<th class="colwidth-15 sorting" onclick="orderBy('dgbh')" id="dgbh_order">
										<div class="font-size-12 line-height-18">申请授权项目</div>
										<div class="font-size-9 line-height-18">Post</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('zt')" id="zt_order">
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th  class="sorting  colwidth-13" onclick="orderBy('whrid')" id="whrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th  class="sorting  colwidth-15" onclick="orderBy('whsj')" id="whsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th  class="colwidth-13">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="postapplication_list">
							</tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class='clearfix'></div>
				</div>	
			</div>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/postapplication/postapplication_main.js"></script><!--当前界面js  -->
	<%@ include file="/WEB-INF/views/quality/postapplication/postapplication_open.jsp" %> <!--新增编辑弹出框  -->
	<%@ include file="/WEB-INF/views/training/faculty/faculty_user.jsp" %><!-- 维修技术人员档案弹出框 -->
	<%@ include file="/WEB-INF/views/quality/postapplication/post_list.jsp" %><!-- 岗位列表 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
	<%@ include file="/WEB-INF/views/produce/projectkeep/Confirm.jsp"%><!-- 指定结束 -->
</body>
</html>