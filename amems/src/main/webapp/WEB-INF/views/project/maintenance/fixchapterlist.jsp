<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ATA章节号列表</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<input maxlength="20" type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<div class="page-content">
			<!-- from start -->
	<div class="panel panel-primary">
	  <div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			 <div  class='searchContent row-height' >
				    <div class="col-xs-3 col-md-3 padding-left-0 padding-right-0 form-group">
						<a href="javascript:openAddATA();" class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-10 pull-left checkPermission" permissioncode="project:fixchapter:main:01">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a> 
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="project:fixchapter:main:04"
							onclick="showImportModal()">
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>
						</button>
						<a href="#" onclick="exportExcel()"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode="project:fixchapter:main:05">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a>
					</div>
									<div class=" pull-right padding-left-0 padding-right-0 form-group">	
				
						<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode_search" class="form-control "  name="dprtcode_search" onchange="searchRevision()" >
									 <c:forEach items="${accessDepartment}" var="type">
									   <option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
							     	 </c:forEach>
								</select>
							</div>
						</div>	
						
						
						<!-- 搜索框start -->
						<div class=" pull-right padding-left-10 padding-right-0 ">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='ATA章节号/英文描述/中文描述'  id="keyword_search" class="form-control ">
							</div>
		                    <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button id="fixchapterMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                   		</button>
		                    </div> 
						</div>
						<!-- 搜索框end -->
					</div>
					<div class="clearfix"></div>
			    </div>
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height">
					<table id="fixchapter_table"
						class="table-set table table-thin table-bordered table-striped table-hover"
						>
						<thead>
							<tr>
								<th class="colwidth-5">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="sorting colwidth-10" onclick="orderBy('zjh')" id="zjh_order">
								   <div class="important">
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
									</div>
								</th>
								<th class="sorting colwidth-20" onclick="orderBy('ywms')" id="ywms_order">
								  <div class="important">
									<div class="font-size-12 line-height-18">英文描述</div>
									<div class="font-size-9 line-height-18">F.Name</div>
								  </div>	
								</th>
								<th class="sorting colwidth-20" onclick="orderBy('zwms')" id="zwms_order">
								   <div class="important">
									<div class="font-size-12 line-height-18">中文描述</div>
									<div class="font-size-9 line-height-18">CH.Name</div>
									</div>
								</th>
								<th class="colwidth-20" class="sorting" onclick="orderBy('r_Jcsj')" id="r_Jcsj_order" style="min-width:60px">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
								<th class="sorting colwidth-10" onclick="orderBy('cjrid')" id="cjrid_order">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Maintainer</div>
								</th>
								<th class="sorting colwidth-10" onclick="orderBy('cjsj')" id="cjsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="sorting colwidth-13" onclick="orderBy('dprtCode')" id="dprtCode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
</div>
<!--修改ATA章节模态框的模态框 -->
	<div class="modal fade in modal-new" id="alertModalUpdateATA" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 65%">
			<div class="modal-content" >
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" >修改ATA章节</div>
							<div class="font-size-12" >Edit ATA</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
        		
        		<div class="modal-body">
					<div class="col-xs-12 margin-top-8 ">
						<div class="input-group-border">
							<form id="form2">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-10"  >
									 <div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
									     <label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										   <div class="font-size-12 line-height-18">ATA章节号</div>
											<div class="font-size-9 line-height-18">ATA</div>
										</label>
										<div class="col-lg-9 col-sm-8 col-xs-9  padding-left-8 padding-right-0 form-group">
											<input type="text"  id="zjh2" name="zjh2"class="form-control" maxlength="16" readOnly>
									    </div>
								    </div>
									
									<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										    <div class="font-size-12 line-height-18">中文描述</div>
											<div class="font-size-9 line-height-18">CH.Name</div></label>
										 <div class="col-lg-9 col-sm-8 col-xs-9  padding-left-8 padding-right-0 form-group" >
											<input type="text" class="form-control "  id="zwms2" name="zwms2" maxlength="60">
										</div>
									</div>
									
									<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
										<label class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										    <div class="font-size-12 line-height-18">英文描述</div>
											<div class="font-size-9 line-height-18">F.Name</div></label>
										 <div class="col-lg-9 col-sm-8 col-xs-9  padding-left-8 padding-right-0 form-group" >
											<input type="text" class="form-control "  id="ywms2" name="ywms2" maxlength="180">
										</div>
									</div>
								</div>
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-10 margin-bottom-0">
							      	<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
										<label class="col-lg-1  col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 "  >
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></label>
										<div class="col-lg-11  col-sm-10 col-xs-9 padding-left-8  padding-right-0 form-group">
											<textarea class="form-control"  id="rJcsj2" name="rjcsj2" maxlength="300"></textarea>
										</div>
									</div>
								</div>
							</form>
							<div class="clearfix"></div>  
						</div>
					</div>
			        <input type="hidden" class="form-control " name="dprtcode2" id="dprtcode2" />
				 </div> 
        		<div class="modal-footer">
		           	<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
							<span class="input-group-addon modalfootertip" >
								<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
							</span>
		                    <span class="input-group-addon modalfooterbtn">
		                    	<button type="button" onclick="updateFixChapter()"class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
			                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
							    </button>
		                    </span>
		               	</div>
					</div>
				</div>
			 </div>
		</div>
   </div>
	<!-------修改ATA章节模态框End-------->
	
	<!-- 	新增ATA章节模态框的模态框 -->
	<div class="modal fade in modal-new" id="alertModalAddATA" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 65%">
			<div class="modal-content" >
				
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" >新增ATA章节</div>
							<div class="font-size-12" >Add ATA</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
				
				<div class="modal-body">
					<div class="col-xs-12 margin-top-8 ">
						<div class="input-group-border">
							<form id="form">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-10"  >
									<div class="col-lg-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
									     <label  class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										   <div class="font-size-12 line-height-18"><span style="color: red">*</span>ATA章节号</div>
											<div class="font-size-9 line-height-18">ATA</div>
										</label>
										<div class=" col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 form-group">
											<input type="text"  id="zjh" name="zjh"class="form-control" maxlength="16" >
									    </div>
								    </div>
									
									<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										    <div class="font-size-12 line-height-18">中文描述</div>
											<div class="font-size-9 line-height-18">CH.Name</div></label>
										 <div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 form-group" >
											<input type="text" class="form-control "  id="zwms" name="zwms" maxlength="60">
										</div>
									</div>
									
									<div class=" col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
										<label class="col-lg-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										    <div class="font-size-12 line-height-18">英文描述</div>
											<div class="font-size-9 line-height-18">F.Name</div></label>
										 <div class="col-lg-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 form-group" >
											<input type="text" class="form-control "  id="ywms" name="ywms" maxlength="180">
										</div>
									</div>
								</div>
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-10 margin-bottom-0">
							      	<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
										<label class="col-lg-1  col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 "  >
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></label>
										<div class="col-lg-11  col-sm-10 col-xs-9 padding-left-8  padding-right-0 form-group">
											<textarea class="form-control"  id="rJcsj" name="rjcsj" maxlength="300"></textarea>
										</div>
									</div>
								</div>
							</form>
							<div class="clearfix"></div>  
						</div>
					</div>
			        <input maxlength="30" id="planedataId"  type="hidden"  name="menuId"/>
			         <!-- 将按钮所属菜单的id储存起来 -->
				 </div> 
				 <div class="modal-footer">
		           	<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
							<span class="input-group-addon modalfootertip" >
								<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
							</span>
		                    <span class="input-group-addon modalfooterbtn">
		                    	<button type="button" onclick="saveFixChapter()"class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
			                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
							    </button>
		                    </span>
		               	</div>
					</div>
				</div>
			 </div>
		</div>
   </div>
	<!-------新增ATA章节模态框End-------->
 <%@ include file="/WEB-INF/views/open_win/log.jsp"%>
 <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 	
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/fixchapterlist.js"></script>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
</body>
</html>
