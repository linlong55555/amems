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
		
				<div class=" pull-left padding-left-0 margin-right-10" style="width:250px;">
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
				
				<div class="col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc" id="divSearch">
					
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
				   		      <button id="btnGoAdd"  class="btn btn-xs btn-primary margin-left-10 float_an checkPermission" permissioncode='quality:lntegrity:manage:01' onclick="javascript:changebadness()">
								<i class=" cursor-pointer">新增Add</i>
							</button>
				        <div class="panel-heading4">
						    <label class="panel-title pull-left line-height-36 ">诚信记录 Credit</label>
					   </div>
					    </div>
						<div class="panel-body4">	 	   
						 <div class="col-lg-12 col-sm-4 col-xs-12 padding-left-0 padding-right-0"   id="fjgdModel">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:500px">
											<thead>
								<tr>
									<th class="colwidth-5" style="vertical-align: middle;">
										<div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th  class="colwidth-5">
										<div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th>
									<th  class="colwidth-30">
										<div class="font-size-12 line-height-18">发生时间描述</div>
										<div class="font-size-9 line-height-18">Occerence Time Remark</div>
									</th>
									<th class="colwidth-30">
										<div class="font-size-12 line-height-18">不良记录内容</div>
										<div class="font-size-9 line-height-18">Bad Record</div>
									</th>
								</tr>
							</thead>
							<tbody id="badnessList"></tbody>
									
				                 </table>
							</div>
				  </div>
				 </div>	
				 
				 
				<input type="hidden" class="form-control " id="badnessmainid" name="badnessmainid" />
	<input type="hidden" class="form-control " id="badnessId" name="badnessId" />
	<!-- start诚信记录记录新增修改提示框 -->
	<div class="modal fade" id="alertModalbadness" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:50%;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18" id="badness"></div>
							<div class="font-size-9 " id="badnessrec"></div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
							<form id="form3">
			            	<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 form-group">
								<label class="pull-left col-xs-4 col-sm-4 col-lg-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>发生时间描述</div>
									<div class="font-size-9">Occurrence Remark</div>
								</label>
								<div class="col-xs-8 col-sm-8 col-lg-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="fssj" name="fssj"  maxlength="30"/>
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            		<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 form-group">
			            	<label class="col-lg-4 col-sm-4 col-xs-4 col-sm-4 col-lg-4  text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>不良记录内容</div>
								<div class="font-size-9 line-height-18">Bad record</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="bljlnr" name="bljlnr" placeholder='长度最大为1300'   maxlength="1300"></textarea>
							</div>
							</div>
				     	<div class="clearfix"></div>
					     	
	     			 <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-top-10 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File Description</div></label>
						 <div class="col-xs-4 padding-left-8 padding-right-0" >
							<input type="text" id="wbwjm" name="wbwjm"  maxlength="100" class="form-control "  >
						</div>
					     <div class=" col-lg-4 col-sm-4 col-xs-4  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-12 "  style="margin-left: 5px ;padding-left: 0"></div>
						</div> 
					</div>
					
		     		<div class="clearfix"></div>
		     		
		     			<div class="col-lg-12 col-md-12 col-xs-12 padding-left-0 padding-right-0" style="overflow: auto;">
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
					     	
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
                     		<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-0 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
								</button>
								<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="savebadness()">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
		                   		</button>
                    		</div><br/>
                    		</form>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end诚信记录新增修改提示框--> 

				 
				</div>
				<div class="clearfix"></div>
		</div>
	</div>
	
	<input type="hidden" class="form-control " id="maintenanceId" name="maintenanceId" />
	<!-- start新增维修档案人员提示框 -->
	<div class="modal fade" id="alertmaintenance" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:80%;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18" id="maintenances"></div>
							<div class="font-size-9 " id="maintenancesrec"></div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
						<from id="form">
							
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 margin-top-10 padding-right-0  form-group">
								<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>人员编号</div>
									<div class="font-size-9">No.</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control" id="wxrybh" name="wxrybh"  maxlength="15"/>
								</div>
							</div> 
						
						
			            	<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 margin-top-10 padding-right-0  form-group">
								<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">内/外</div>
									<div class="font-size-9">I/O</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" id="wrap">
									<label style=" font-weight:normal"><input type="radio" name="reserveId" id="reserveId" value='1' checked="checked">
									内部人员</label>
									<label style=" font-weight:normal"><input type="radio" name="reserveId"  value='2' id="zdjsrid" >
									外部人员</label>
								</div>
							</div> 
							
			            	<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 margin-top-10 padding-right-0 form-group ">
									<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12"><span style="color: red">*</span>姓名</div>
										<div class="font-size-9">Name</div>
									</span>
									
									<div style="display: none;" id="usernamedisplaynone" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left">
										<input type="text" class="form-control"  name="wxxm" id="wxxm"  maxlength="10" /> 
									</div>
									
									<div id="usernamedisplayback">
									
									
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
							<div class='input-group'>
									<input type="text" class="form-control"  name="username" id="username"  onfocus="value =''"   maxlength="10" /> 
									<input type="hidden"  id="username1"   /> 
									<input	class="form-control " type="hidden" type="text" id="userid" name="userid" readonly="readonly" />
									<input	class="form-control " type="hidden" type="text" id="ckbmid" name="ckbmid" readonly="readonly" />
									
							
								<span class='input-group-btn'>
								  <button type="button" onclick='selectUser()' class='btn btn-primary'><i class='icon-search'></i>
								</button>
								</span>
						    </div>
					</div>
						      		 
						      		 </div>
							</div> 
							
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 margin-top-10 padding-right-0 form-group ">
								<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">岗位</div>
									<div class="font-size-9">Posts</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="gw" class="form-control " name="gw">
										<option value="">请选择Choice</option>
									</select>
								</div>
							</div> 
							<div class="clearfix"></div>
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>专业</div>
									<div class="font-size-9">Major</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="zy" class="form-control " name="zy">
										<option value="">请选择Choice</option>
									</select>
								</div>
							</div>
			            
			            	<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">学历</div>
									<div class="font-size-9">Degree</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<select id="xl" class="form-control " name="xl">
										<option value="">请选择Choice</option>
									</select>
								</div>
							</div> 
						
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>出生年月</div>
									<div class="font-size-9">Birth Date</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control date-picker" id="wxsr" name="wxsr" data-date-format="yyyy-mm" type="text" />
								</div>
							</div> 
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">入职日期</div>
									<div class="font-size-9">Entry Date</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control date-picker" id="rzrq" name="rzrq" data-date-format="yyyy-mm-dd" type="text" />
								</div>
							</div> 
							<div class="clearfix"></div>
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">职位</div>
									<div class="font-size-9">Position</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="wxzw" name="wxzw"  maxlength="15" />
								</div>
							</div> 
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-lg-4 col-sm-4 col-xs-4  text-right padding-left-0 padding-right-0">
									<div class="font-size-12">邮箱</div>
									<div class="font-size-9">E-mail</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="wxyxdz" name="wxyxdz" maxlength="30"/>
								</div>
							</div> 
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">身份证/执照号</div>
									<div class="font-size-9">ID/License No</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="wxzzh" name="wxzzh" maxlength="20" />
								</div>
							</div> 
							<div class="clearfix"></div>
							<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">技术简历</div>
									<div class="font-size-9">Technical Resume</div>
								</span>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 margin-bottom-0">
								<textarea class="form-control" id="gzfw" name="gzfw" placeholder='长度最大为600'   maxlength="600"></textarea>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-xs-4 col-sm-2 col-lg-1 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">照片</div>
									<div class="font-size-9">Photo</div>
								</span>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 margin-bottom-0">
								
							    <div class="row">
							      <div class="col-md-2" style="width:192px;height:285px">
							        <div id="imageCon" class="img-container" style="width:192px;height:285px">
							        <input type="hidden" id="urls">
							          <img id="image"  src="${ctx}/static/images/maintenanceTest.png" >
							        </div>
							      </div>
							      
							      <div class="col-md-8 margin-left-10">
							        <div class="docs-preview clearfix">
							          <div class="img-preview preview-lg"></div>
							          <div class="img-preview preview-md"></div>
							        </div>
							     
							      </div>
							    </div>
							     
								</div>
							</div>
						<div class="clearfix"></div>
					<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-0 padding-right-0  ">
								<span class="pull-left col-xs-4 col-sm-2 col-lg-1 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"></div>
									<div class="font-size-9"></div>
								</span>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 margin-bottom-10">
								
	        <div class="btn-group ">
		 
		          <label class="btn btn-primary btn-upload margin-right-10" for="inputImage" >
		            <input type="file" class="sr-only" id="inputImage" name="file" value="chose" accept="image/*">
		            <span class="docs-tooltip" data-toggle="tooltip">
		              <span class="">选择</span>
		            </span>
		          </label>
                  <button type="button" class="btn btn-primary " onclick="butEmpty()">
		          <span class="docs-tooltip" data-toggle="tooltip" >
	              <span class=" ">还原</span>
	           	 </span>
		          </button>
       		 </div>
<!-- 		            <input type="file"  id="inputImage1" name="file" accept="image/*"> -->

        <div class="modal fade docs-cropped" id="getCroppedCanvasModal" aria-hidden="true" aria-labelledby="getCroppedCanvasTitle" role="dialog" tabindex="-1">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="getCroppedCanvasTitle">Cropped</h4>
              </div>
              <div class="modal-body"></div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <a class="btn btn-primary" id="download" href="javascript:void(0);" download="cropped.jpg">Download</a>
              </div>
            </div>
          </div>
        </div>
								</div>
							</div>
							
							
							<div class="clearfix"></div>
			            	<span class="pull-left col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">备注</div>
									<div class="font-size-9">Remark</div>
							</span>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="wxbz" name="wxbz" placeholder='长度最大为600' maxlength="600"></textarea>
							</div>
					     	<div class="clearfix"></div>
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
					     	<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-0 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
								</button>
								<button  type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="savemaintenance()">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
		                   		</button>
                     			
                    		</div>
                    			</from>
						 </div> 
						 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end维修档案人员提示框 -->
	
</div>
<!-- end新增修改提示框-->
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/lntegrity/lntegrity_main.js"></script>
 <script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel.js"></script>
       <link rel="stylesheet" href="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel.css"> 
       	<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
       	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>