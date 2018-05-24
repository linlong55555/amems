<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>MEL清单</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
	</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />
<input type="hidden" id="userId" name="userId" value=${user.id} />
<input type="hidden" id="userType" value=${user.userType} />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0" >
               <!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input title="修订MEL编号/机型/修订主题/修订内容/修订人/制单人" placeholder="修订MEL编号/机型/修订主题/修订内容/修订人/制单人" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 row-height">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight"  onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!-- 搜索框end -->
				
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 display-none border-cccccc margin-top-10  search-height" id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">修订期限</div>
							<div class="font-size-9">Revision Period</div>
						</span>
						<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="xdqx" readonly />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12	  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zzjg" class="form-control " name="zzjg" >
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

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="overflow-x: auto;width: 100%;">
						<table  id="quality_check_list_table" class="table table-thin table-bordered table-set" style="min-width: 2000px !important">
						<thead>
							<tr>
										<!-- class="sorting" onclick="orderEmpBy('jszlh')" -->
										<th class="sorting fixed-column colwidth-10" onclick="orderBy('xdbh')"
											id="xdbh_order">
											<div class="important">
											<div
												class="font-size-12 line-height-18">修订MEL编号</div>
											<div class="font-size-9 line-height-18">A/N No.</div>
											</div>
										</th>
										
										<!-- class="sorting" onclick="orderEmpBy('jszlh')" -->
									<!-- 	<th class="downward colwidth-13" onclick="vieworhideWorkContentAll()">
											<div class="important">
											<div
												class="font-size-12 line-height-18">关联评估单号 </div>
											<div class="font-size-9 line-height-18">Assessment NO.</div>
											</div>
										</th> -->
										<th class="sorting colwidth-7" onclick="orderBy('jx')"
											id="jx_order">
											<div class="important">
											<div
												class="font-size-12 line-height-18">机型 </div>
											<div class="font-size-9 line-height-18">Model</div>
											</div>
											</th>
										<th class="sorting colwidth-5" onclick="orderBy('jbb')"
											id="bb_order">
											<div
												class="font-size-12 line-height-18">版本</div>
											<div class="font-size-9 line-height-18">Revision</div>
											</th>
										<th class="sorting colwidth-25" onclick="orderBy('ckzl')"
											id="ckzl_order">
											<div
												class="font-size-12 line-height-18">参考资料</div>
											<div class="font-size-9 line-height-18">Resources</div>
										</th>
										
										<!-- class="sorting" onclick="orderEmpBy('xdzt')" -->
										<th class="sorting colwidth-30" onclick="orderBy('xdzt')"
											id="xdzt_order">
											<div class="important">
											<div
												class="font-size-12 line-height-18">修订主题 </div>
											<div class="font-size-9 line-height-18">Subject</div>
											</div>
										</th>
											<!-- class="sorting" onclick="orderEmpBy('xdnr')" -->
										<th class="sorting colwidth-30" onclick="orderBy('xdnr')"
											id="xdnr_order">
											<div class="important">
											<div
												class="font-size-12 line-height-18">修订内容 </div>
											<div class="font-size-9 line-height-18">Revision Contents</div>
											</div>
										</th>
										<!-- class="sorting" onclick="orderEmpBy('xdrid')" -->
										<th class="sorting colwidth-13" onclick="orderBy('xdrid')"
											id="xdrid_order">
											<div class="important">
											<div
												class="font-size-12 line-height-18">修订人 </div>
											<div class="font-size-9 line-height-18">Revised By</div>
											</div>
										</th>
										
										<!-- class="sorting" onclick="orderEmpBy('xdqx')" -->
										<th class="sorting colwidth-13" onclick="orderBy('xdsj')"
											id="xdsj_order">
											<div class="font-size-12 line-height-18">修订期限</div>
											<div class="font-size-9 line-height-18">Revision Period</div></th>
											
											<!-- class="sorting" onclick="orderEmpBy('xdnr')" -->
										<th class="sorting colwidth-25" onclick="orderBy('xdnr')"
											id="xdnr_order">
											<div class="important">
											<div
												class="font-size-12 line-height-18">备注 </div>
											<div class="font-size-9 line-height-18">Remark</div>
											</div>
										</th>
										
										<th class="colwidth-25">
											<div class="font-size-12 line-height-18">上传文件 </div>
											<div class="font-size-9 line-height-18">List of Files</div>
										</th>
										
										<th class="sorting colwidth-13" onclick="orderBy('shrid')"
											id="shrid_order">
											<div
												class="font-size-12 line-height-18">审核人</div>
											<div class="font-size-9 line-height-18">Reviewer</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('shrid')"
											id="shsj_order">
											<div
												class="font-size-12 line-height-18">审核时间</div>
											<div class="font-size-9 line-height-18">Review Time</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('pfrid')"
											id="pfrid_order">
											<div
												class="font-size-12 line-height-18">批准人</div>
											<div class="font-size-9 line-height-18">Appr. By</div>
										</th>
										<th class="sorting colwidth-13" onclick="orderBy('pfrid')"
											id="pfsj_order">
											<div
												class="font-size-12 line-height-18">批准时间</div>
											<div class="font-size-9 line-height-18">Approved Time</div>
										</th>
										
										<th class="sorting colwidth-13" onclick="orderBy('zdrid')"
											id="zdrid_order">
											<div class="important">
											<div
												class="font-size-12 line-height-18">制单人</div>
											<div class="font-size-9 line-height-18">Creator </div>
											</div>
										</th>
											
											
										<!-- class="sorting" onclick="orderEmpBy('zdsj')" -->
										<th class="sorting colwidth-13" onclick="orderBy('zdsj')"
											id="zdsj_order"><div
												class="font-size-12 line-height-18">制单时间</div>
											<div class="font-size-9 line-height-18">Create Time</div></th>
											
										<!-- class="sorting" onclick="orderEmpBy('zdsj')" -->
										<th class="sorting colwidth-13" onclick="orderBy('zdbmid')"
											id="zdbmid_order"><div
												class="font-size-12 line-height-18">制单部门</div>
											<div class="font-size-9 line-height-18">Department</div></th>
											
										<!-- class="sorting" onclick="orderEmpBy('zdsj')" -->
										<th class="sorting colwidth-13" onclick="orderBy('dprtcode')"
											id="dprtcode_order"><div
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

	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
    <script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project/minimumEquipmentList/minimumEquipmentList_main.js"></script>
</body>
</html>