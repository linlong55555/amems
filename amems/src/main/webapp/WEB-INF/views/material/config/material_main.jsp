<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>部件主数据</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="" />
	<input type="hidden" id="dprtcode" value="${user.jgdm}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<div class='row-height searchContent'>
				<div class="col-xs-2 col-md-3 padding-left-0 form-group">
					<a href="javascript:void(0);" class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-10 pull-left checkPermission" permissioncode="material:material:main:01" onclick="openMaterialAdd()" >
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
					<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission " permissioncode="material:material:main:04"
							onclick="showImportModal()">
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>
					</button>
					<a href="#" onclick="exportExcel()"  class="btn btn-primary padding-top-1 margin-left-6 padding-bottom-1 pull-left checkPermission" permissioncode="material:material:main:06">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
					</a>
				</div>
				
				<div class=" pull-right padding-left-0 padding-right-0 form-group">	
				
					<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">类型</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="hclx_search" class="form-control" onchange="changeType()">
								<option value="" selected="selected">显示全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>	
					
					<!-- 搜索框start -->
					<div class=" pull-right padding-left-10 padding-right-0 row-height">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='件号/厂家件号/中英文/ATA章节号/备注/维护人' id="keyword_search" class="form-control ">
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
				</div>
				
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10 display-none border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">维护日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="whrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">管理级别</div>
							<div class="font-size-9">Level</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='gljb_search'>
								<option value="" selected="true">显示全部All</option>
								<c:forEach items="${supervisoryLevelEnum}" var="item">
								  <option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">时效控制</div>
							<div class="font-size-9">Control</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='sxkz_search'>
								<option value="" selected="true">显示全部All</option>
								<c:forEach items="${agingControlEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
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
					
					<div class="clearfix"></div>
					
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
                </div>
				<div id="material_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="material_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 2400px;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-7" >	
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('bjh')" id="bjh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('cjjh')" id="cjjh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">厂家件号</div>
										<div class="font-size-9 line-height-18">MP/N</div>
									</div>
								</th>
								<th class="colwidth-25 sorting" onclick="orderBy('ywms')" id="ywms_order">
									<div class="important">
										<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('zwms')" id="zwms_order">
									<div class="important">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('jhly')" id="jhly_order">
									
										<div class="font-size-12 line-height-18">件号来源</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('bzjh')" id="bzjh_order">
									
										<div class="font-size-12 line-height-18">标准件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('gse')" id="gse_order">
									
										<div class="font-size-12 line-height-18">GSE</div>
										<div class="font-size-9 line-height-18">GSE</div>
									
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('zzjbs')" id="zzjbs_order">
									
										<div class="font-size-12 line-height-18">自制件</div>
										<div class="font-size-9 line-height-18">P/N</div>
									
								</th>
								<th th_class="cont-exp2" td_class="affectedDisplayFile" table_id="material_main_table"  class="cont-exp2 downward colwidth-13" onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
									<div class="font-size-12 line-height-18">等效替代件号</div>
									<div class="font-size-9 line-height-18">T/B No.</div>
								</th>
								<th class="colwidth-30" >
									<div class="font-size-12 line-height-18">适用机型</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('gljb')" id="gljb_order">
									<div class="font-size-12 line-height-18">管理级别</div>
									<div class="font-size-9 line-height-18">Level</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('sxkz')" id="sxkz_order">
									<div class="font-size-12 line-height-18">时效控制</div>
									<div class="font-size-9 line-height-18">Control</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('IS_MEL')" id="IS_MEL_order">
									<div class="font-size-12 line-height-18">是否定检</div>
									<div class="font-size-9 line-height-18">Is P/I</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('zjh')" id="zjh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">ATA章节号</div>
										<div class="font-size-9 line-height-18">ATA</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('hclx')" id="hclx_order">
									<div class="font-size-12 line-height-18">类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('jldw')" id="jldw_order">
									<div class="font-size-12 line-height-18">单位</div>
									<div class="font-size-9 line-height-18">Unit</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('bz')" id="bz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('username')" id="username_order">
									<div class="important">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('cjsj')" id="cjsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
			</div>
		</div>
	</div>
	
<!-------部件详情对话框 Start-------->
	<%@ include file="/WEB-INF/views/material/config/material_open.jsp" %> 
<%-- <div class="modal fade" id="alertMaterialForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:85%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertMaterialFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18" id="materialHeadChina" >新增部件</div>
						<div class="font-size-9 " id="materialHeadEnglish">Add</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
						
						<form id="form">
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">基本信息</div>
								<div class="font-size-9 ">Basic Information</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="bjh" name="bjh" maxlength="50" />
										<input type="hidden" class="form-control " id="id" />
									</div>
								</div>
							
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>单位</div>
										<div class="font-size-9 line-height-18">Unit</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select id="jldw" class="form-control " name="jldw"></select>
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">厂家件号</div>
										<div class="font-size-9 line-height-18">MP/N</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="cjjh" name="cjjh" maxlength="50" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">ATA章节号</div>
										<div class="font-size-9 line-height-18">ATA</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left input-group">
										<input type="text" id="zjhName" name="zjhName" class="form-control " readonly />
										<input type="hidden" class="form-control" id="zjh" name="zjh"  />
										<span class="input-group-btn">
											<button type="button" id="zjhbtn" class="btn btn-primary form-control" data-toggle="modal" onclick="openChapterWin()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="zwms" name="zwms" maxlength="100" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input class="form-control" id="ywms" name="ywms" maxlength="100" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">型号</div>
										<div class="font-size-9 line-height-18">Model</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="xingh" name="xingh" maxlength="16" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">规格</div>
										<div class="font-size-9 line-height-18">Spec</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input class="form-control" id="gg" name="gg" maxlength="16" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">MEL</div>
										<div class="font-size-9 line-height-18">MEL</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="checkbox" id="isMel" name="isMel" checked="checked" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">适用机型</div>
										<div class="font-size-9 line-height-18">Model</div>
									</label>
									<div id="jxdiv" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class="clearfix"></div>
							
							 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
										<textarea class="form-control" id="bz" name="bz" maxlength="300" ></textarea>
									</div>
								</div>
							</div>
						
							<div class="clearfix"></div>
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">生产信息</div>
								<div class="font-size-9 ">Production Information</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">建议仓库</div>
										<div class="font-size-9 line-height-18">Warehouse</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="hidden" class="form-control " id="msId" />
										<select class='form-control' id='ckh' name="ckh" onchange="changeStore()" >
									    </select>
									</div>
								</div>
							
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">建议库位</div>
										<div class="font-size-9 line-height-18">Stock Location</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class='form-control' id='kwh' name="kwh" ></select>
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">航材价值</div>
										<div class="font-size-9 line-height-18">Aircraft Value</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class='form-control' id='hcjz' name="hcjz" >
											<option value="" ></option>
											<c:forEach items="${materialPriceEnum}" var="item">
											  <option value="${item.id}" <c:if test="${1 == item.id }">selected="true"</c:if> >${item.name}</option>
											</c:forEach>
									    </select>
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">管理级别</div>
										<div class="font-size-9 line-height-18">Level</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class='form-control' id='gljb' name="gljb" >
											<c:forEach items="${supervisoryLevelEnum}" var="item">
											  <option value="${item.id}" <c:if test="${3 == item.id }">selected="true"</c:if> >${item.name}</option>
											</c:forEach>
									    </select>
									</div>
								</div>
							
								<div class="clearfix"></div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">最低库存量</div>
										<div class="font-size-9 line-height-18">Minimum</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="minKcl" name="minKcl" class="form-control " value="${material.minKcl}"  placeholder='' onkeyup='clearNoNumTwo(this)' maxlength="10" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">最高库存量</div>
										<div class="font-size-9 line-height-18">Maximum</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" id="maxKcl" name="maxKcl" class="form-control " value="${material.maxKcl}"  placeholder='' onkeyup='clearNoNumTwo(this)' maxlength="10" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class='form-control' id='hclx' name="hclx" onchange="changeMaterialType()">
											<c:forEach items="${materialTypeEnum}" var="item">
											  <option value="${item.id}" <c:if test="${1 == item.id }">selected="true"</c:if> >${item.name}</option>
											</c:forEach>
									    </select>
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">时效控制</div>
										<div class="font-size-9 line-height-18">Control</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									    <label style="margin-right: 20px;font-weight: normal">
									    	<input name="sxkz" type="radio" value="1" />是
									    </label> 
										<label style="font-weight: normal">
											<input name="sxkz" type="radio" value="0" />否 
										</label> 
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div id="materialType" class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">航材二级类型</div>
										<div class="font-size-9 line-height-18">Second Type</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<select class='form-control' id='hclxEj' name="hclxEj" >
											<c:forEach items="${materialSecondTypeEnum}" var="item">
											  <option value="${item.id}" <c:if test="${100 == item.id }">selected="true"</c:if> >${item.name}</option>
											</c:forEach>
									    </select>
									</div>
								</div>
							</div>
							
							<div class="clearfix"></div>
						</form>
						
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit.jsp"%><!-- 加载附件信息 -->
					 	 
						<div id="subListView" style="display:none">		
					
							<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class=" pull-left margin-right-10" >
									<div class="font-size-16 line-height-18">等效替代</div>
									<div class="font-size-9 ">Equivalent Substitution</div>
								</div>	
							</div>
					
						   	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0" >
								<div style="overflow-x: auto;">
									<!-- start:table -->
									<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width:910px">
										<thead>
									   		<tr>
												<th class="colwidth-3" >
													<div class="font-size-12 notwrap">序号</div>
													<div class="font-size-9 notwrap">No.</div>
												</th>
												<th class="colwidth-15">
													<div class="font-size-12 line-height-18" >件号</div>
													<div class="font-size-9 line-height-18" >T/B No.</div>
												</th>
												<th class="colwidth-15">
													<div class="font-size-12 line-height-18" >件号描述</div>
													<div class="font-size-9 line-height-18" >T/B No.</div>
												</th>
												<th class="colwidth-15">
													<div class="font-size-12 line-height-18" >替代件号</div>
													<div class="font-size-9 line-height-18" >T/B No.</div>
												</th>
												<th class="colwidth-15">
													<div class="font-size-12 line-height-18" >替代描述</div>
													<div class="font-size-9 line-height-18" >T/B No.</div>
												</th>
												<th class="colwidth-15">
													<div class="font-size-12 line-height-18" >机型适用性</div>
													<div class="font-size-9 line-height-18" >T/B No.</div>
												</th>
												<th class="colwidth-15">
													<div class="font-size-12 line-height-18" >工卡适用性</div>
													<div class="font-size-9 line-height-18" >T/B No.</div>
												</th>
												<th class="colwidth-7">
													<div class="font-size-12 line-height-18" >是否可逆性</div>
													<div class="font-size-9 line-height-18" >T/B No.</div>
												</th>
											</tr>
										</thead>
										<tbody id="subListViewTable">
										
										</tbody>
									</table>
									<!-- end:table -->
								</div>
								<div class="clearfix"></div>
							</div>
						</div>	
					 	 
					 	 <div class="text-right margin-top-10 margin-right-0">
							<button id="materialSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save();">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
			           	</div>
                  		<br/>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div> --%>

<!-------部件详情对话框 End-------->

<!-- 等效替代部件查看弹窗 -->

<div class="modal fade" id="alertModalView" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:50%!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalViewBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">查看</div>
						<div class="font-size-9 line-height-18">View</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
		            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
		            	<form id="form" >
		            	<div class="col-lg-6 col-sm-6 col-xs-12 padding-left-0 margin-top-10 padding-right-0 form-group">
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">件号</div>
								<div class="font-size-9">T/B No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
								<input class="form-control"  id="subjh" name="subjh" type="text" readonly />
							</div>
						</div> 
						
						<div class="col-lg-6 col-sm-6 col-xs-12 padding-left-0 margin-top-10 padding-right-0 form-group">
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">替代件号</div>
								<div class="font-size-9">T/B No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control"  id="sutdjh" name="sutdjh" type="text" readonly />
							</div>
						</div> 
						
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">是否可逆</div>
								<div class="font-size-9">Start Date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
								<select id="suknxbs" class="form-control" name="suknxbs" disabled = "true" >
									<option value="2">是</option>
									<option value="1">否</option>
								</select>
							</div>
						</div> 
						
						<div class="clearfix"></div>
						
						<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group" >
							<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
									<div class="font-size-12 line-height-18">描述</div>
									<div class="font-size-9 line-height-18">Desc</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
									<textarea class="form-control" id="sums" name="sums" placeholder='长度最大为300' maxlength="300" readonly ></textarea>
							</div>
						</div>
		            	<div class="clearfix"></div>
		            	
						<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group" >
							<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
								<div class="font-size-12 line-height-18">替代描述</div>
								<div class="font-size-9 line-height-18">Replace Desc</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
								<textarea class="form-control" id="sutdjms" name="sutdjms" placeholder='长度最大为300' maxlength="300" readonly ></textarea>
							</div>
						</div>
						
		            	<div class="clearfix"></div>
		            	
		            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
								<div class="font-size-12">机型适用性</div>
								<div class="font-size-9">Start Date</div>
							</label>
							<div id="sujxbs" class="col-lg-10 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
							</div>
						</div> 
		            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 margin-top-10 padding-right-0 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
								<div class="font-size-12">工卡适用性</div>
								<div class="font-size-9">Start Date</div>
							</label>
							<div id="sugkbs" class="col-lg-10 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
							</div>
						</div> 
		            	</form>
						    
				     		<div class="clearfix"></div>
				     		<div class="text-center margin-top-10 padding-buttom-10 pull-right">
		                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
                   			 </div><br/>
					 	 </div>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>
	
<!-- 等效替代部件查看弹窗 -->

<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/config/material_main.js"></script>
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%><!-- ATA章节弹出框 -->
<%@ include file="/WEB-INF/views/open_win/equivalent_substitution_view.jsp"%><!-------等效替代 -------->
<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/jqueryactual/jquery.actual.min.js"></script>
</body>
</html>