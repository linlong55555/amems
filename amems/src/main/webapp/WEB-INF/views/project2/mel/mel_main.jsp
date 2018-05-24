<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>修订MEL</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
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
						searchRevision();//调用主列表页查询
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
				<div class="panel-body  padding-bottom-0" >
				
					<div class=' margin-top-0 searchContent row-height'>
						<div class=" col-lg-6 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
							<a href="javascript:add();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='project:mel:main:01'>
								<div class="font-size-12">新增</div>
								<div class="font-size-9">Add</div>
							</a>
							<a id="batchReview" type="button" onclick="batchApproveWin(false);" 
								class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission"  permissioncode='project:mel:main:05'>
								<div class="font-size-12">批量审核</div>
								<div class="font-size-9">Review</div>
							</a>
							<a id="batchApprove" type="button" onclick="batchApproveWin(true);" 
								class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission"  permissioncode='project:mel:main:06'>
								<div class="font-size-12">批量批准</div>
								<div class="font-size-9">Approve</div>
							</a>
						</div>
						
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">机型</div>
								<div class="font-size-9 ">A/C Type</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
								<select id="jx_search" class="form-control" onchange="searchRevision()">
									<option value="" selected="selected">显示全部All</option>
								</select>
							</div>
						</div>
						
						<!-- 搜索框start -->
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
							<div class="col-xs-12 input-group input-group-search">
								<input type="text" placeholder='文件编号/项目号/修改内容/修改原因'  class="form-control" id="keyword_search" >
			                    <div class="input-group-addon btn-searchnew" >
			                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" style='margin-right:0px !important;'>
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
					
					<!-- 搜索框end -->
				
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
								<div class="font-size-9">Status</div></span>
							<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="zt_search" class="form-control ">
										<option value="">显示全部</option>
										<option value="1">保存</option>
										<option value="2">提交</option>
										<option value="3">已审核</option>
										<option value="4">已批准</option>
										<option value="5">审核驳回</option>
										<option value="6">批准驳回</option>
										<option value="9">指定结束</option>
										<option value="10">完成</option>
								</select>
							</div>
						</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12	  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="jxChange()" >
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
					<div id="mel_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" style="overflow-x: auto;width: 100%;">
						<table id="main_list_table" class="table table-thin table-striped table-bordered table-set table-hover" style="min-width: 2000px !important">
							<thead>
								<tr>
									<th class="viewCol fixed-column colwidth-7 selectAllImg">
										<a href="#" onclick="SelectUtil.performSelectAll('mel_main_table_top_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
										<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('mel_main_table_top_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
									</th>
									<th class="fixed-column colwidth-7">
										<div class="font-size-12 line-height-18 ">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('GGDBH')" id="GGDBH_order">
										<div class="important">
											<div class="font-size-12 line-height-18">文件编号</div>
											<div class="font-size-9 line-height-18">File No.</div>
										</div>
									</th>
									<th th_class="cont-exp" td_class="melDisplayFile" table_id="main_list_table"   class="cont-exp downward colwidth-13" onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
										<div class="font-size-12 line-height-18">关联评估单号 </div>
										<div class="font-size-9 line-height-18">Relate Evaluation</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('JX')" id="JX_order">
										<div class="font-size-12 line-height-18">机型 </div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('XMH')" id="XMH_order">
										<div class="important">
											<div class="font-size-12 line-height-18">项目号</div>
											<div class="font-size-9 line-height-18">Project No.</div>
										</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('SSBF')" id="SSBF_order">
										<div class="font-size-12 line-height-18">所属部分</div>
										<div class="font-size-9 line-height-18">Owned Part</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('SSZJ')" id="SSZJ_order">
										<div class="font-size-12 line-height-18">所属章节</div>
										<div class="font-size-9 line-height-18">Chapter</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('ZY')" id="ZY_order">
										<div class="font-size-12 line-height-18">中/英</div>
										<div class="font-size-9 line-height-18">Chinese/English</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">修改前版本</div>
										<div class="font-size-9 line-height-18">Old Rev.</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">修改后版本</div>
										<div class="font-size-9 line-height-18">New Rev.</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">修改标记</div>
										<div class="font-size-9 line-height-18">Modify Mark</div>
									</th>
									<th class="sorting colwidth-30" onclick="orderBy('XDNR')" id="XDNR_order">
										<div class="important">
										<div class="font-size-12 line-height-18">修改内容</div>
										<div class="font-size-9 line-height-18">Content</div>
										</div>
									</th>
									<th class="sorting colwidth-30" onclick="orderBy('XGYY')" id="XGYY_order">
										<div class="important">
										<div class="font-size-12 line-height-18">修改原因</div>
										<div class="font-size-9 line-height-18">Reason</div>
										</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('XDQX')" id="XDQX_order">
										<div class="font-size-12 line-height-18">修订页</div>
										<div class="font-size-9 line-height-18">Revision page</div>
									</th>
									<th class="sorting colwidth-7" onclick="orderBy('ZT')" id="ZT_order">
										<div class="font-size-12 line-height-18">状态 </div>
										<div class="font-size-9 line-height-18">Status</div>
									</th>
									<th class="colwidth-13" >
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('ZDSJ')" id="ZDSJ_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
						<tbody id="list">
						</tbody>
							
						</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/mel/mel_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/batchApprovel.jsp"%><!-------批量审批对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
	<%@ include file="/WEB-INF/views/project2/mel/mel_edit_win.jsp"%><!-------编辑对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/selectEvaluation.jsp"%><!-- -选择评估单列表 -->
</body>
</html>