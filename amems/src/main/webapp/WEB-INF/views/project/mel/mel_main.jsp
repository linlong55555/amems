<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>修订MEL</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
	</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="drpt" value="${user.jgdm}" />
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
				<div class="panel-body padding-bottom-0" >
				
				<div class="col-xs-3 col-md-3 padding-left-0 row-height">
					<a permissioncode='project:mel:main:01' href="#" onclick="add()" data-toggle="modal"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a>
				</div>
				
				<div class=" pull-right padding-left-0 padding-right-0">
				
					<div class="pull-left padding-left-5 padding-right-0 margin-right-5" style="width: 250px;">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机型</div>
							<div class="font-size-9">Model</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="jx_search" class="form-control" onchange="searchRevision()">
								<option value="" selected="selected">显示全部All</option>
							</select>
						</div>
					</div>	
				
					<!-- 搜索框start -->
					<div class=" pull-right padding-left-0 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input title="文件编号/项目号/修改内容/修改原因" placeholder="文件编号/项目号/修改内容/修改原因" id="keyword_search" class="form-control " type="text">
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
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
				
				</div>
				
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 display-none border-cccccc margin-top-10  search-height" id="divSearch">
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
								<div class="font-size-9">State</div></span>
							<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="zt_search" class="form-control ">
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
					
					<div class="col-lg-3 col-sm-6 col-xs-12	  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control " name="dprtcode_search" >
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
						<table id="main_list_table" class="table table-thin table-bordered table-set" style="min-width: 2000px !important">
							<thead>
								<tr>
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
										<div class="font-size-9 line-height-18">Assessment NO.</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('JX')" id="JX_order">
										<div class="font-size-12 line-height-18">机型 </div>
										<div class="font-size-9 line-height-18">Model</div>
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
										<div class="font-size-9 line-height-18">Covered</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('ZY')" id="ZY_order">
										<div class="font-size-12 line-height-18">中/英</div>
										<div class="font-size-9 line-height-18">Chinese/English</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">修改前版本</div>
										<div class="font-size-9 line-height-18">Revised Version</div>
									</th>
									<th class=" colwidth-10">
										<div class="font-size-12 line-height-18">修改后版本</div>
										<div class="font-size-9 line-height-18">Revised Version</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">修改标记</div>
										<div class="font-size-9 line-height-18">Modify Mark</div>
									</th>
									<th class="sorting colwidth-30" onclick="orderBy('XDNR')" id="XDNR_order">
										<div class="important">
										<div class="font-size-12 line-height-18">修改内容</div>
										<div class="font-size-9 line-height-18">Modify Content</div>
										</div>
									</th>
									<th class="sorting colwidth-30" onclick="orderBy('XGYY')" id="XGYY_order">
										<div class="important">
										<div class="font-size-12 line-height-18">修改原因</div>
										<div class="font-size-9 line-height-18">Reason For Modification</div>
										</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('XDQX')" id="XDQX_order">
										<div class="font-size-12 line-height-18">修订页</div>
										<div class="font-size-9 line-height-18">Revision page</div>
									</th>
									<th class="sorting colwidth-7" onclick="orderBy('ZT')" id="ZT_order">
										<div class="font-size-12 line-height-18">状态 </div>
										<div class="font-size-9 line-height-18">State</div>
									</th>
									<th class="colwidth-13" >
										<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator </div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('ZDSJ')" id="ZDSJ_order">
										<div class="font-size-12 line-height-18">制单时间</div>
										<div class="font-size-9 line-height-18">Create Time</div>
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
	<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
	<div class="modal fade" id="alertModalClose" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18">关闭</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
			            	
			            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 ">
								<span class="pull-left col-lg-2 text-right padding-left-0 padding-right-0" style="width:30%;font-weight:bold">
								<div class="font-size-12">修订MEL编号</div>
									<div class="font-size-9">Amendment Notice No.</div>
									</span>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0" style="width:70%">
									<input type="hidden" name="zlid2" id="zlid2">
									<input type="text" class="form-control " id="zlbh2" name="zlbh2" disabled="disabled"/>
								</div>
								</div>
								<div class="clearfix"></div>
								<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<span class="pull-left col-lg-2 text-right padding-left-0 padding-right-0" style="width:30%;font-weight:bold">
								<div class="font-size-12"><span style="color: red">*</span>实际完成日期</div>
									<div class="font-size-9">Finished Date</div>
									</span>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0" style="width:70%">
									<input type="text" id="wcrq"  name="wcrq" class="form-control datepicker " data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            	<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 " style="width:30%">
								<div class="font-size-12 line-height-18">说明</div>
								<div class="font-size-9 line-height-18">Explanation</div>
							</label>
							<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 " style="width:70%">
								<textarea class="form-control" id="zdjsyy2" name="zdjsyy2"  maxlength="150"></textarea>
							</div>
							    
					     		<div class="clearfix"></div>
					     		<div class="text-center margin-top-10 padding-buttom-10 ">
                     <button onclick="CloseOver()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal"><div
										class="font-size-12">提交</div>
									<div class="font-size-9">Submit</div></button>&nbsp;&nbsp;&nbsp;&nbsp;
                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">取消</div>
										<div class="font-size-9">Cancel</div>
									</button>&nbsp;&nbsp;&nbsp;&nbsp;
                    			 </div><br/>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript" src="${ctx}/static/js/thjw/project/mel/mel_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
</body>
</html>