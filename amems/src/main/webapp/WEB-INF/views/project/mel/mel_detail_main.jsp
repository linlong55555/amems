<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>供应商主数据</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%> 
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			
				<div class="col-xs-3 col-md-3 padding-left-0 row-height">
					<a href="javascript:void(0);" class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-10 pull-left checkPermission" permissioncode="project:mel:main:02" onclick="openWinAdd()">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a>
				</div>
			
				<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0 row-height">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='机型/版本' id="keyword_search" class="form-control ">
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
				
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none search-height border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">State</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="zt_search" name="zt_search" class="form-control \">
								<option value="" selected="true">显示全部All</option>
								<option value="1" >有效</option>
								<option value="0" >无效</option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control ">
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
				
				<div class="clearfix"></div>

				<div class="col-lg-12 table-h col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;">
					<table class="table table-thin table-bordered table-set" >
						<thead>                              
							<tr>
								<th class="fixed-column colwidth-5">
									<div class="font-size-12 line-height-18 ">操作</div>
									<div class="font-size-9 line-height-18">Option</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('jx')" id="jx_order">
									<div class="important">
										<div class="font-size-12 line-height-18">机型 </div>
										<div class="font-size-9 line-height-18">Model</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('bb')" id="bb_order">
									<div class="important">
										<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Version</div>
									</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 line-height-18">MEL清单</div>
									<div class="font-size-9 line-height-18">MEL</div>
								</th>
								<th class="colwidth-5">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('username')" id="username_order">
										<div class="font-size-12 line-height-18">审核人</div>
										<div class="font-size-9 line-height-18">Audit</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">审核时间</div>
									<div class="font-size-9 line-height-18">Audit Time</div>
								</th>
								<th class="colwidth-13">
										<div class="font-size-12 line-height-18">批准人</div>
										<div class="font-size-9 line-height-18">Approve</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">批准时间</div>
									<div class="font-size-9 line-height-18">Approve Time</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('WHRID')" id="WHRID_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('WHSJ')" id="WHSJ_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

<!-------供应商详情对话框 Start-------->
	
<div class="modal fade" id="alertForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:30%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18" id="headChina" >新增</div>
						<div class="font-size-9 " id="headEnglish">Add</div>
					</div>
					<div class="panel-body padding-bottom-0" >
						<form id="form">
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>机型</div>
										<div class="font-size-9 line-height-18">Model</div>
									</label>
									<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class='form-control' id='jx' name="jx">
						    			</select>
										<input type="hidden" class="form-control " id="id" />
										<input type="hidden" id="melqdfjid" value="" />
									</div>
								</div>
								
								<div class="clearfix"></div>
							
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>版本</div>
										<div class="font-size-9 line-height-18">Version</div>
									</label>
									<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="bb"  name="bb" class="form-control" maxlength="16" />
									</div>
								</div>
								
								<div class="clearfix"></div>
							
								<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
						
									<label  class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">MEL清单</div>
										<div class="font-size-9 line-height-18">Mel</div>
									</label>
									<div class="col-lg-8 col-sm-6 col-xs-6 padding-left-8 padding-right-0">
										<div class="input-group">
											<input class="form-control"  id="wbwjmSingle" name="wbwjmSingle" type="text" maxlength="50" />
											<span class='input-group-btn'>
											 	<div id="fileuploaderSingle"   ></div>
											</span>
										</div>
									</div>
									<div id="melqdDiv" class="col-lg-2 col-sm-6 col-xs-6 padding-left-10 padding-right-0">
							
									</div>
								</div>
								
							</div>
						
							<div class="clearfix"></div>
						</form>
						
					 	 <div class="text-right margin-top-10 margin-right-0 margin-bottom-10">
							<button id="supplierSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save();">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
			           	</div>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>

<!-------供应商详情对话框 End-------->
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/project/mel/mel_detail_main.js"></script>
</body>
</html>