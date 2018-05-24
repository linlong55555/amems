<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
	</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="userId" value=${user.id } />
	<input type="hidden" id="userType" value=${user.userType} />
	<input type="hidden" id="zzjgid" value=${user.jgdm } />
	<div class="clearfix"></div>
	<div class="page-content">
		<!-- from start -->
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
				<div class="col-xs-2  padding-left-0 row-height">
					<a href="#" onclick="add()"
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left"><div
							class="font-size-12">上传文件</div>
						<div class="font-size-9">Upload File</div> </a>
					<button type="button" onclick="technicalFileUploadOutExcel();"  style="margin-left:10px"
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</button>
				</div>

				<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='评估单号/文件编号/文件主题/备注/制单人' id="keyword_search" class="form-control ">
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

				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 display-none border-cccccc margin-top-10 search-height"
					id="divSearch">

					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机型</div>
							<div class="font-size-9">Model</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class="form-control " id="jx">
								<option value="">显示全部</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">分类</div>
							<div class="font-size-9">Category</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="fl" class="form-control " name="fl">
								<option value="">显示全部</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">文件类型</div>
							<div class="font-size-9">File Type</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="wjlx" class="form-control " name="wjlx">
								<option value="">显示全部</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源</div>
							<div class="font-size-9">Source</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="ly" class="form-control " name="ly">
								<option value="">显示全部</option>
							</select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="zt" class="form-control " name="zt">
								<option value="">显示全部</option>
								<option value="0">未评估</option>
								<option value="1">已评估</option>
								<option value="2">已审核</option>
								<option value="3">已批准</option>
								<option value="4">中止（关闭）</option>
								<option value="5">审核驳回</option>
								<option value="6">批准驳回</option>
								<option value="9">指定结束</option>
							</select>
						</div>
					</div>

					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机型工程师</div>
							<div class="font-size-9">Engineer</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="jxgcs" class="form-control " name="jxgcs">
								<option value="">显示全部</option>
								<c:forEach items="${users}" var="type">
									<option value="${type.id}">${erayFns:escapeStr(type.displayName)}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">生效日期</div>
							<div class="font-size-9">Effective Date</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="sxrq" readonly />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">评估期限</div>
							<div class="font-size-9">Assess period</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="pgqx" readonly />
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="zzjg" class="form-control " name="zzjg" onchange="changeContent()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-sm-3 text-right  pull-right padding-right-0"
						style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				
				<div class="clearfix"></div>

				<div class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h" style="overflow-x: auto;">

					<table id="quality_check_list_table" class="table table-thin table-bordered table-set" 
						style="min-width: 2600px !important">
						<thead>
							<tr>
								<th  class="fixed-column colwidth-9"><div
										class="font-size-12 line-height-18 ">操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>
								<th  class="sorting fixed-column colwidth-10" onclick="orderBy('pgdh')" id="pgdh_order"><div class="important"><div
										class="font-size-12 line-height-18">评估单号</div>
									<div class="font-size-9 line-height-18">Assessment NO.</div></div></th>
								<th class="sorting  colwidth-10" onclick="orderBy('ly')" id="ly_order"><div
										class="font-size-12 line-height-18">来源</div>
									<div class="font-size-9 line-height-18">Source</div></th>
								<th class="sorting  colwidth-7" onclick="orderBy('jx')" id="jx_order"><div
										class="font-size-12 line-height-18">机型</div>
									<div class="font-size-9 line-height-18">Model</div></th>
								<th class="sorting  colwidth-5" onclick="orderBy('fl')" id="fl_order"><div
										class="font-size-12 line-height-18">分类</div>
									<div class="font-size-9 line-height-18">Category</div></th>
								<th class="sorting  colwidth-5" onclick="orderBy('wjlx')" id="wjlx_order"><div
										class="font-size-12 line-height-18">文件类型</div>
									<div class="font-size-9 line-height-18">File Type</div></th>
								<th  class="sorting  colwidth-15" onclick="orderBy('shzltgh')"
									id="shzltgh_order"><div class="important"><div
										class="font-size-12 line-height-18">文件编号</div>
									<div class="font-size-9 line-height-18">File No.</div></div></th>
								<th  class="sorting  colwidth-25" onclick="orderBy('wjzt')"
									id="wjzt_order"><div class="important"><div
										class="font-size-12 line-height-18">文件主题</div>
									<div class="font-size-9 line-height-18">Subject</div></div></th>
								<th  class="sorting  colwidth-5" onclick="orderBy('bb')" id="bb_order"><div
										class="font-size-12 line-height-18">版本</div>
									<div class="font-size-9 line-height-18">Revision</div></th>
								<th  class="sorting  colwidth-10" onclick="orderBy('sxrq')" id="sxrq_order"><div
										class="font-size-12 line-height-18">生效日期</div>
									<div class="font-size-9 line-height-18">Effective Date</div></th>
								<th  class="sorting  colwidth-10" onclick="orderBy('pgqx')" id="pgqx_order"><div
										class="font-size-12 line-height-18">评估期限</div>
									<div class="font-size-9 line-height-18">Assess period</div></th>
								<th  class="sorting  colwidth-5" onclick="orderBy('syts')" id="syts_order"><div
										class="font-size-12 line-height-18">剩余天数</div>
									<div class="font-size-9 line-height-18">Days</div></th>
								<th  class="sorting  colwidth-13" onclick="orderBy('pgrid')" id="pgrid_order"><div
										class="font-size-12 line-height-18">机型工程师</div>
									<div class="font-size-9 line-height-18">Engineer</div></th>
								<th  class="sorting  colwidth-7" onclick="orderBy('zt')" id="zt_order"><div
										class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div></th>
								<th  class="downward colwidth-16" onclick="vieworhideWorkContentAll()" name="th_return"><div
										class="font-size-12 line-height-18">评估结果</div>
									<div class="font-size-9 line-height-18">Result</div></th>
								<th  class="colwidth-30"><div class="font-size-12 line-height-18">原文件</div>
									<div class="font-size-9 line-height-18">File</div></th>
								<th  class="sorting  colwidth-30" onclick="orderBy('bz')" id="bz_order"><div class="important"><div
										class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div></div></th>
								<th  class="sorting  colwidth-13" onclick="orderBy('zdrid')" id="zdrid_order"><div class="important"><div
										class="font-size-12 line-height-18">制单人</div></div>
									<div class="font-size-9 line-height-18">Creator</div></th>
								<th  class="sorting  colwidth-13" onclick="orderBy('zdsj')" id="zdsj_order"><div
										class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div></th>
								<th  class=" colwidth-13"><div
										class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>

							</tr>
						</thead>
						<tbody id="list">
						</tbody>

					</table>
				</div>
					<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
					</div>

				<div class="clearfix"></div>
			</div>
		</div>

	</div>

	</div>

		<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
	    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript"
		src="${ctx }/static/js/thjw/project/technicalfile/technicalfile_upload_list.js"></script>
</body>
</html>