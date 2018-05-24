<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />
<input type="hidden" id="userId" name="userId" value=${user.id} />
<input type="hidden" id="userType" value=${user.userType} />
<!-- BEGIN HEADER -->
<div class="clearfix"></div>
<!-- BEGIN CONTAINER -->
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
$(document).ready(function(){
//导航
Navigation(menuCode);
});
</script>
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
				<div class="col-xs-3  padding-left-0 row-height">
				<a href="#" onclick="add()" data-toggle="modal"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission"
					permissioncode="project:engineering:main:01"
					><div class="font-size-12">新增</div>
					<div class="font-size-9">Add</div>
				</a>
				<button type="button" onclick="batchApproveWin(false);"  style="margin-left:10px"
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"
						permissioncode="project:engineering:main:05">
						<div class="font-size-12">批量审核</div>
						<div class="font-size-9">Review</div>
				</button>
				<button type="button" onclick="batchApproveWin(true);"  style="margin-left:10px"
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"
						permissioncode="project:engineering:main:06">
						<div class="font-size-12">批量批准</div>
						<div class="font-size-9">Approve</div>
				</button>
				</div>
					
					<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='指令编号/评估单号/参考资料/主题/描述/制单人' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                        <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!-- 搜索框end -->
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height " id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div></span>
						<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt" class="form-control "  name="zt">
									<option value="">显示全部</option>
									<option value="0">保存</option>
									<option value="1">提交</option>
									<option value="2">已审核</option>
									<option value="3">已批准</option>
									<option value="4">中止(关闭)</option>
									<option value="5">审核驳回</option>
									<option value="6">批准驳回</option>
									<option value="9">指定结束</option>
									<option value="10">完成</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zzjg" class="form-control " name="zzjg">
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

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="overflow-x: auto;width: 100%;">
						<table id="quality_check_list_table"
							class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 2300px !important"
							>
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" style='text-align:center;vertical-align:middle'>
									<a href="#" onclick="performSelectAll('engineeringlist')" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll' ><img src="${ctx}/static/assets/img/d1.png" alt="全选" title="全选" /></a>
									<a href="#" onclick="performSelectClear('engineeringlist')" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll'><img src="${ctx}/static/assets/img/d2.png" alt="不全选" title="不全选" /></a> 
<!--                                <input type='checkbox' name='CheckAll' id='CheckAll'  class="pull-left margin-left-10 margin-bottom-10" />
									<input type='checkbox' name='cancelAll' id='CancelAll'  class="pull-left margin-left-10 margin-bottom-10" />  -->
									</th>	
									<th class="fixed-column colwidth-7"><div class="font-size-12 line-height-18 " style="border-left:none;" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									
									<th class="sorting fixed-column colwidth-10" onclick="orderBy('gczlbh')" id="gczlbh_order">
									<div class="important">
									<div class="font-size-12 line-height-18">工程指令编号</div>
										<div class="font-size-9 line-height-18">EO No.</div></div></th>
										
									<th class="downward colwidth-13" onclick="vieworhideWorkContentAll()" name="th_return">
									<div class="important">
									<div class="font-size-12 line-height-18">关联评估单号</div>
										<div class="font-size-9 line-height-18">Assessment NO.</div></div></th>
										
									<th class="sorting colwidth-20" onclick="orderBy('ckzl')" id="ckzl_order">
									<div class="important">
									<div class="font-size-12 line-height-18">参考资料</div>
										<div class="font-size-9 line-height-18">Reference material</div></div></th>
										
									<th class="sorting colwidth-25" onclick="orderBy('zhut')" id="zhut_order">
									<div class="important">
									<div class="font-size-12 line-height-18">主题</div>
										<div class="font-size-9 line-height-18">Subject</div></div></th>
									
									<th class="sorting colwidth-5" onclick="orderBy('bb')" id="bb_order">
									<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Revision</div></th>
										
									<th class="sorting colwidth-15" onclick="orderBy('xggzh')" id="xggzh_order">
									<div class="font-size-12 line-height-18">相关改装号</div>
										<div class="font-size-9 line-height-18">MOD No.</div></th>
									
									<th class="sorting colwidth-30" onclick="orderBy('txyj')" id="txyj_order">
									<div class="important">
									<div class="font-size-12 line-height-18">描述</div>
										<div class="font-size-9 line-height-18">Description</div></div></th>
									
									<th class="sorting colwidth-15" onclick="orderBy('IS_CFZXSX')" id="IS_CFZXSX_order">
									<div class="font-size-12 line-height-18">重复执行</div>
										<div class="font-size-9 line-height-18">Repeated Execution Limit</div></th>
									
									<th class="sorting colwidth-15" onclick="orderBy('IS_ZLPHYX')" id="IS_ZLPHYX_order">
									<div class="font-size-12 line-height-18">重量与平衡影响</div>
										<div class="font-size-9 line-height-18">Weight And Balance</div></th>
										
									<th class="sorting colwidth-7" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">State</div></th>
										
									<th class="sorting colwidth-13" onclick="orderBy('ZDRID')" id="ZDRID_order">
									<div class="important">
									<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator </div></div></th>
										
									<th class="sorting colwidth-13" onclick="orderBy('ZDSJ')" id="ZDSJ_order">
									<div class="font-size-12 line-height-18">制单时间</div>
										<div class="font-size-9 line-height-18">Create Time</div></th>
										
									<th class="sorting colwidth-13" onclick="orderBy('DPRTCODE')" id="DPRTCODE_order">
									<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
							<tbody id="engineeringlist">
							</tbody>
							
						</table>
					</div>
					<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
					</div>
			
				
					<div class="clearfix"></div>
				</div>
			</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
<%@ include file="/WEB-INF/views/open_win/batchApprovel.jsp"%><!-------批量审批对话框 -------->
	<script type="text/javascript" src="${ctx }/static/js/thjw/project/engineering/engineeringlist.js"></script>
	
</body>
</html>