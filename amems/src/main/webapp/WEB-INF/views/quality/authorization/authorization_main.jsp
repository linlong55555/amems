<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

<link rel="stylesheet" href="${ctx}/static/lib/cropper/cropper.css"> 
<script type="text/javascript" src="${ctx}/static/lib/cropper/cropper.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/ajaxfileupload/ajaxfileupload.js"></script>
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
</head>
<body class="page-header-fixed">
	<input type="hidden" id="adjustHeight" value="90" />

<input type="hidden" id="zzjgid" value="${user.jgdm}" />
<input type="hidden" id="path" value="${path}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<div class="page-content">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			
			<div class="panel-body padding-bottom-0">
					<div class="col-xs-2 col-md-3 padding-left-0 margin-right-10 ">
			
					</div>
					<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0 margin-bottom-10" >
		
					<div class=" pull-left padding-left-0 margin-right-10 " style="width:250px;" >
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" name="dprtcode" class="form-control ">
								<c:choose>
											<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}"
													<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}
												</option>
											</c:forEach>
											</c:when>
											<c:otherwise>
												<option value="">暂无组织机构 No Organization</option>
											</c:otherwise>
										</c:choose>
							</select>
						</div>
					</div>
		
					<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
						<input placeholder="姓名" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    
                    </div> 
				</div>
				<!------------搜索框end------->
				

				<div class="clearfix"></div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0">
					<ul class="nav nav-tabs" role="tablist">
					  <li role="presentation" class="dropdown">
					    <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);" onclick="changeqb()">
					      	全部 
					    </a>
					  </li>
					  <li role="presentation" class="dropdown">
					    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
					      	专业 	<span class="caret"></span>
					    </a>
					    <ul class="dropdown-menu" role="menu" id="zyjz">
					    
					    </ul>
					  </li>
					  <li role="presentation" class="dropdown">
					    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
					      	内/外<span class="caret"></span>
					    </a>
					    <ul class="dropdown-menu" role="menu">
					    
					    
					    
					 <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" onclick="changeSelected(1)">内部人员</a></li>
	  			     <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" onclick="changeSelected(2)">外部人员</a></li>
					    </ul>
					  </li>
					  <li role="presentation" class="dropdown">
					    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
					      	在职年限<span class="caret"></span>
					    </a>
					    <ul class="dropdown-menu" role="menu">
	  			     	<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" onclick="changeSelect(1)">2年内</a></li>
	  			     	<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" onclick="changeSelect(2)">2年到5年</a></li>
					     <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" onclick="changeSelect(3)">5年以上</a></li>
					    </ul>
					  </li>
					</ul>
				</div>

				<div class="col-sm-3 padding-left-0 border-r">
			        <span id="editTakeStockButton" class="pull-right font-size-12"></span>
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px;overflow-x: scroll;">
						<table class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:250px">
							<thead>
								<tr>
									<th  class="">
										<div class="important"><div class="font-size-12 line-height-18">姓名</div></div>
										<div class="font-size-9 line-height-18">Name</div>
									</th>
									<th  class="colwidth-11">
										<div class="font-size-12 line-height-18">信息</div>
										<div class="font-size-9 line-height-18">Info</div>
									</th>
								</tr>
							</thead>
							<tbody id="List"></tbody>
						</table>
					</div>
	                	
					
				</div>
		<div id="personContentDiv" class="col-sm-9 padding-top-10 table-h" style="overflow-y:auto;">
				
			<div id="imgs" class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" alt="照片" style="padding: 1px; width: 100px; height: 149px; border: 1px solid #dcdcdc;display:block; overflow:hidden;">
			
			</div>
			<div
				class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label
					class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
						class="font-size-12 line-height-18">姓名</div>
					<div class="font-size-9 line-height-18">Name</div></label>
				<div
					class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<span id="xm"></span>
				</div>
			</div>
			<div
				class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label
					class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
						class="font-size-12 line-height-18">编号</div>
					<div class="font-size-9 line-height-18">No.</div></label>
				<div
					class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<span id="rybh"></span>
				</div>
			</div>
			<div
				class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label
					class="col-lg-5 col-sm-5 col-xs-4 text-right padding-left-0 padding-right-0"><div
						class="font-size-12 line-height-18">职位</div>
					<div class="font-size-9 line-height-18">Position</div></label>
				<div
					class="col-lg-7 col-sm-7 col-xs-8 padding-left-8 padding-right-0">
					<span id="zw"></span>
				</div>
			</div>
			<div
				class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label
					class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
						class="font-size-12 line-height-18">岗位</div>
					<div class="font-size-9 line-height-18">Posts</div></label>
				<div
					class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<span id="gwms"></span>
				</div>
			</div>
			<div
				class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label
					class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
						class="font-size-12 line-height-18">年龄</div>
					<div class="font-size-9 line-height-18">Age</div></label>
				<div
					class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<span id="sr"></span>
				</div>
			</div>
			<div
				class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label
					class="col-lg-5 col-sm-5 col-xs-4 text-right padding-left-0 padding-right-0"><div
						class="font-size-12 line-height-18">邮箱</div>
					<div class="font-size-9 line-height-18">E-mail</div></label>
				<div
					class="col-lg-7 col-sm-7 col-xs-8 padding-left-8 padding-right-0">
					<span id="yxdz"></span>
				</div>
			</div>
			<div
				class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label
					class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
						class="font-size-12 line-height-18">学历</div>
					<div class="font-size-9 line-height-18">Degree</div></label>
				<div
					class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<span id="xlms"></span>
				</div>
			</div>
			<div
				class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label
					class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
						class="font-size-12 line-height-18">专业</div>
					<div class="font-size-9 line-height-18">Major</div></label>
				<div
					class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<span id=zyms></span>
				</div>
			</div>
			<div
				class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label
					class="col-lg-5 col-sm-5 col-xs-4 text-right padding-left-0 padding-right-0"><div
						class="font-size-12 line-height-18">身份证/执照号</div>
					<div class="font-size-9 line-height-18">ID/License No</div></label>
				<div
					class="col-lg-7 col-sm-7 col-xs-8 padding-left-8 padding-right-0">
					<span id="zzh"></span>
				</div>
			</div>
				 
				 <div class="clearfix  line"></div>
				 	 <div class=" panel-default">
				      <div class="col-xs-12"  style="cursor: pointer;" >
				   		      <button id="btnGoAdd"  class="btn btn-xs btn-primary margin-left-10 float_an checkPermission" permissioncode='quality:authorization:manage:01' onclick="javascript:changeAccredit()">
								<i class=" cursor-pointer">新增Add</i>
							</button>
				        <div class="panel-heading3">
						    <label class="panel-title pull-left line-height-36 ">授权记录 Authorization</label>
					   </div>
					    </div>
						<div class="panel-body3">	 	   
						 <div class="col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 "  style="overflow-x:auto">
								<table id="sqjl" class="table table-thin table-bordered text-center table-set" style="min-width:1500px!important;">
											<thead>
								<tr>
									<th class="fixed-column colwidth-5"  style="vertical-align: middle;">
										<div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operaton</div>
									</th>
									<th class=" colwidth-5" >
										<div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th>
									<th class=" colwidth-25"  >
										<div class="font-size-12 line-height-18">证件编号</div>
										<div class="font-size-9 line-height-18">Cert No.</div>
									</th>
									<th  class="colwidth-15"  >
										<div class="font-size-12 line-height-18">证件名称</div>
										<div class="font-size-9 line-height-18">Cert Name</div>
									</th>
									<th  class="colwidth-10" >
										<div class="font-size-12 line-height-18">信息类型</div>
										<div class="font-size-9 line-height-18">Info Type</div>
									</th>
									<th  class="colwidth-10" >
										<div class="font-size-12 line-height-18">颁发日期</div>
										<div class="font-size-9 line-height-18">Award Date</div>
									</th>
									<th  class="colwidth-15" >
										<div class="font-size-12 line-height-18">颁发单位</div>
										<div class="font-size-9 line-height-18">Award Dept.</div>
									</th>
									<th  class="colwidth-15" >
										<div class="font-size-12 line-height-18">有效期-开始</div>
										<div class="font-size-9 line-height-18">Exp.-Strat</div>
									</th>
									<th class="colwidth-15" >
										<div class="font-size-12 line-height-18">有效期-结束</div>
										<div class="font-size-9 line-height-18">Exp.-End</div>
									</th>
									<th class="colwidth-25"  >
										<div class="font-size-12 line-height-18">证件等级</div>
										<div class="font-size-9 line-height-18">Cert Level</div>
									</th>
									<th class="colwidth-15"  >
										<div class="font-size-12 line-height-18">项次</div>
										<div class="font-size-9 line-height-18">Item</div>
									</th>
									<th class="colwidth-10"  >
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">Model</div>
									</th>
									<th class="colwidth-30" >
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</th>
								</tr>
							</thead>
							<tbody id="accreditList"></tbody>
									
				                 </table>
							</div>
				  </div>
				 </div> 
				 
				</div>
				<div class="clearfix"></div>
		</div>
	</div>
	
	 	<input type="hidden" class="form-control " id="accreditmainid" name="accreditmainid" />
	<input type="hidden" class="form-control " id="accreditId" name="accreditId" />
	<!-- start授权记录新增修改提示框 -->
	<div class="modal fade" id="alertModalAccredit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:60%;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18" id="accredit"></div>
							<div class="font-size-9 " id="accreditrec"></div>
						</div>
					
						<div class="panel-body padding-top-0 padding-bottom-0" >
						<form id="form2">
			         		<div class="col-xs-12 col-sm-6 col-lg-6 padding-left-0 margin-top-10 padding-right-0  form-group">
								<label class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>授权号</div>
									<div class="font-size-9">Authorization No.</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control" id="zjbh"  name="zjbh" type="text" maxlength="50"/>
								</div>
							</div> 
			         		<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-10 padding-right-0  form-group">
								<label class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>证件名称</div>
									<div class="font-size-9">Cert Name</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control" id="zjmc" name="zjmc" type="text" maxlength="60"/>
								</div>
							</div> 
							   <div class="clearfix"></div>
							<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-0 padding-right-0  ">
								<label class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0 ">
									<div class="font-size-12"><span style="color: red">*</span>信息类型</div>
									<div class="font-size-9">Info Type</div>
								</label>
								<div id="xxlxs" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="xxlx" class="form-control" name="xxlx">
										<option value="1" >授权</option>
										<option value="2">执照</option>
									</select>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-0 padding-right-0  ">
								<label class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">颁发日期</div>
									<div class="font-size-9">Award Date</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input class="date-picker form-control" id="bfrq" name="bfrq" data-date-format="yyyy-mm-dd" type="text" readonly/>
								</div>
							</div>
							   <div class="clearfix"></div>
							<div class="col-xs-12 col-sm-6 col-lg-6 padding-left-0 margin-top-10 padding-right-0  ">
								<label class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">颁发单位</div>
									<div class="font-size-9">Award Dept.</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control" id="bfdw" type="text" maxlength="60"/>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-10 padding-right-0  ">
								<label class="pull-left col-xs-4 col-sm-4 col-lg-4  text-right padding-left-0 padding-right-0">
									<div class="font-size-12">有效期</div>
									<div class="font-size-9">Exp.</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control " name="date-range-picker" id="flightDate_search" readonly />
								</div>
							</div>
							   <div class="clearfix"></div>
							<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-10 padding-right-0  ">
								<label class="pull-left col-xs-4 col-sm-4 col-lg-4  text-right padding-left-0 padding-right-0">
									<div class="font-size-12">证件等级</div>
									<div class="font-size-9">Cert Level</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control" id="sqdj" type="text" maxlength="15"/>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-10 padding-right-0  ">
								<label class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">项次</div>
									<div class="font-size-9">Item</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control" id="xc" type="text"  maxlength="15"/>
								</div>
							</div>
							   <div class="clearfix"></div>
							<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-10 padding-right-0  ">
								<label class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">机型</div>
									<div class="font-size-9">Model</div>
								</label>
								<div id="fjjxs" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select class='form-control' id='fjjx' name='fjjx'>
									
									</select>
								</div>
							</div>
							
					     <div class="clearfix"></div>
					     	
					  	<label class="pull-left col-xs-4 col-sm-2 col-lg-2 text-right padding-left-0 padding-top-10 padding-right-0">
									<div class="font-size-12">备注</div>
									<div class="font-size-9">Remark</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-top-10 padding-right-0 ">
								<textarea class="form-control" id="bz" name="bz" placeholder='长度最大为300'   maxlength="300"></textarea>
							</div>
							
					<div class="clearfix"></div>
							
					 <div class=" col-lg-12 col-sm-12 col-xs-12  padding-top-10 padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-2 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File Description</div>
						</label>
					 	<div class="col-lg-4 padding-left-8 padding-right-0" >
							<input type="text" id="wbwjm" name="wbwjm"  maxlength="100" class="form-control "  >
						</div>
					     <div class=" col-lg-4 col-sm-4 col-xs-4  padding-left-0 padding-right-0  " >
							<div id="fileuploader" class="col-lg-4 col-sm-2 col-xs-12 "  style="margin-left: 5px ;padding-left: 0"></div>
						</div> 
					</div>
					
					<div class="clearfix"></div>
					<div class="col-lg-12 col-md-12 col-xs-12 padding-left-0  padding-right-0" style="overflow: auto;">
						<table class="table-set table table-bordered table-striped table-hover text-center" style="min-width:500px">
							<thead>
								<tr>
									<th style="width:52px;"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th>
									<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">Description</div>
									</th>
									<th style="width:80px;">
									<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File Size</div>
									</th>
									<th style="width:100px;"><div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Uploader</div></th>
									<th style="width:140px;"><div class="font-size-12 line-height-18">上传时间</div>
										<div class="font-size-9 line-height-18">Upload Time</div></th>					
								</tr>
							</thead>
							    <tbody id="filelist">
									 
								</tbody>
						</table>
					</div>
							</form>
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
                     		<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-0 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
							</button>
							<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="saveAccredit()">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
	                   		</button>
                    		</div><br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end授权记录新增修改提示框-->
	
	
	
</div>
<!-- end新增修改提示框-->
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/authorization/authorization_main.js"></script>
 <script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel.js"></script>
       <link rel="stylesheet" href="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel.css"> 
       	<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
       	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>