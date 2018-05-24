<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<input type="hidden" id="adjustHeight" value="20"/>
<input type="hidden" id="fmlid" value="" />
<input type="hidden" id="mkdm" value="${mkdm}" />
<input type="hidden" id="id" value="" />

<input id="newFileName" type="hidden"/>
<input id="relativePath" type="hidden"/>
<input id="fileName" type="hidden"/>
<input id="fileSize" type="hidden"/>
<input id="ids" type="hidden"/>

	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content">
			
		 <div class="panel panel-primary" >
		<div class="panel-heading">
		<div class="panel-heading" id="NavigationBar"></div>
		</div>

				<div class="panel-body padding-top-0 padding-bottom-0">	
<!-- 				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="border:1px solid red; margin-top: 10px; padding-bottom:1px;"> -->
                 <div class="col-lg-4 col-sm-3 col-xs-12 pull-left padding-right-0 padding-left-0 border-r">
                    <div class="ibox float-e-margins padding-top-10"  id="seperateLine">
                      <div class="ibox-title line1 ">
                     <label class=" col-lg-2 pull-left  padding-right-0 padding-left-0  padding-top-8">
				          <div class="font-size-14 line-height-16">目录</div>
						 <div class="font-size-9 line-height-12">Directory</div>
					</label>
                     <div class="col-lg-10 pull-right padding-right-0 padding-left-0" >
				      <div class=" col-lg-12 col-xs-12 pull-right padding-right-0 padding-left-0">
				    <button class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 checkPermission " permissioncode='maintenance:maintenancemanuals:manage:01,maintenance:maintenancemanuals:manage1:01' onclick="AssignStore()">
				          <div class="font-size-12">新增文件夹</div>
						 <div class="font-size-9">New Directory</div>
					 </button>
					<button class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 margin-left-3 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:02,maintenance:maintenancemanuals:manage1:02'onclick="updateFile()">
						<div class="font-size-12">文件夹更名</div>
						<div class="font-size-9">Rename Directory</div>
					</button>
					<button class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 margin-left-3 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:03,maintenance:maintenancemanuals:manage1:03'onclick="deleteFile()">
						<div class="font-size-12">删除文件夹</div>
						<div class="font-size-9">Delete Directory</div>
					</button>
				      </div>
				 </div>
                            </div>
                            
                             <div class="ibox-content"> 
                            <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0"  style="overflow: auto;" id="leftDiv">

                                <div id="using_json"></div>

                            </div> 
                            </div>
                        </div>
                    </div>     
                     
				<div class="col-lg-8 col-sm-9 col-xs-12  padding-right-0 pull-right padding-top-10">
						
						<div class="col-lg-12 padding-left-0 padding-right-0">
						
						<div class="col-lg-12  ibox-title line1 padding-right-0 padding-left-0">
						
						<label class=" col-lg-2 pull-left  padding-right-0  padding-top-10 padding-left-0">
				          <div class="font-size-14 line-height-16">文件列表</div>
						 <div class="font-size-9 line-height-12">File List</div>
						</label>
						
					<div class="col-lg-4 pull-left padding-right-0 padding-left-0 " >
				    <button class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:04,maintenance:maintenancemanuals:manage1:04' onclick="uploading()">
				          <div class="font-size-12">上传</div>
						 <div class="font-size-9">Upload</div>
					 </button>

					<button class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 margin-left-3  checkPermission" permissioncode='maintenance:maintenancemanuals:manage:05,maintenance:maintenancemanuals:manage1:05'onclick="updtaeuploading()">
						<div class="font-size-12">文件更名</div>
						<div class="font-size-9">Rename File</div>
					</button>
					<button class="btn btn-xs btn-primary padding-top-1 padding-bottom-1 margin-left-3  checkPermission" permissioncode='maintenance:maintenancemanuals:manage:06,maintenance:maintenancemanuals:manage1:06'onclick="deleteuploading()">
						<div class="font-size-12">删除文件</div>
						<div class="font-size-9">Delete File</div>
					</button>
				 </div>
				<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
		
					<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
						<input placeholder="文件名称/备注/上传时间" id="keyword_search" class="form-control  row-height" type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 margin-top-2">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div> 
				</div>
				<!------------搜索框end------->
					
						</div>
					 	
					<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 man_file" style="overflow-x: auto;" id="rightDiv">
					<table id="wjlb" class="table table-thin table-bordered table-hover table-set margin-botton-0" style="min-width:1050px"  >
							<thead>
								<tr>
									<th class="colwidth-5" style="vertical-align:middle;">
									<a href="javascript:checkAll();" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll' ><img src="${ctx}/static/assets/img/d1.png" alt="全选" title="全选" /></a>
									<a href="javascript:notCheckAll();" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll'><img src="${ctx}/static/assets/img/d2.png" alt="不全选" title="不全选" /></a> 
									</th>
									<th class="colwidth-3"><div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th class="colwidth-20"><div class="important"><div class="font-size-12 line-height-18">文件名称</div></div>
										<div class="font-size-9 line-height-18">File Name</div></th>
									<th  class="sorting colwidth-10" onclick="orderBy('wjdx')" id="wjdx_order"><div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File Size</div></th>
									<th  class="colwidth-13"><div class="font-size-12 line-height-18">上传人</div>
									<div class="font-size-9 line-height-18">Upload Person</div></th>
									<th  class="sorting colwidth-13" onclick="orderBy('czsj')" id="czsj_order"><div class="important"><div class="font-size-12 line-height-18">上传时间</div></div>
										<div class="font-size-9 line-height-18">Upload Time</div></th>
									<th class="colwidth-30"><div class="important"><div class="font-size-12 line-height-18">备注</div></div>
										<div class="font-size-9 line-height-18">Remark</div></th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
							
						</table>
				
					
							
				</div>		 
			
					</div> 	 
               </div>
               
               
<!-- 				</div> -->
				<div class="clearfix"></div>
			</div> 
	</div>
	
		<!-- start 新增文件夹 -->
	<div class="modal fade in modal-new" id="alertModalStore" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">             
                <div class="modal-header modal-header-new">
                       <h4 class="modal-title">
                          <div class="pull-left">
	                       <div class="font-size-12 ">新增文件夹</div>
							<div class="font-size-9">New Directory</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						  <div class="clearfix"></div>
                       </h4>
                  </div>    
			  <div class="modal-body" style="max-height:413px !important;overflow: auto;margin-top:0px;">
			            <form id="form">
							<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 form-group">
								<span class="pull-left col-lg-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>目录名称</div>
									<div class="font-size-9">Directory Name</div>
									</span>
								<div class="col-lg-9 col-sm-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="mlmc" name="mlmc" value="" maxlength="100"/>
								</div>
							</div> 
						</form>
			 <div class="clearfix"></div>
			  </div>
			
			<div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8">
				<div class="input-group">
				<span class="input-group-addon modalfootertip">
	                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>     
				</span>
                    <span class="input-group-addon modalfooterbtn">
                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="saveFile()">
							<div class="font-size-12">提交</div>
							<div class="font-size-9">Submit</div>
						</button>
	                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
					    </button>
                    </span>
               	</div><!-- /input-group -->
			</div>
			</div>
				
			</div>
		</div>
	</div>
	
	
	
		<!-- start 修改文件夹 -->
		
	<div class="modal fade in modal-new" id="Modifyfolder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
			      <div class="modal-header modal-header-new">
                       <h4 class="modal-title">
                          <div class="pull-left">
	                       <div class="font-size-12 ">文件夹更名</div>
							<div class="font-size-9">Rename Directory</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						  <div class="clearfix"></div>
                       </h4>
                  </div>
			
			 <div class="modal-body" style="max-height:413px !important;overflow: auto;margin-top:0px;">
			            <form id="form1">
							<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 form-group">
								<span class="pull-left col-lg-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>目录名称</div>
									<div class="font-size-9">Directory Name</div>
									</span>
								<div class="col-lg-9 col-sm-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="mlmc1" name="mlmc1" value="" maxlength="100"/>
								</div>
							</div> 
						</form>
			 <div class="clearfix"></div>
			  </div>
		<div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8">
				<div class="input-group">
				<span class="input-group-addon modalfootertip">
	                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>           
				</span>
                    <span class="input-group-addon modalfooterbtn">
	                   <button onclick="ModifyFile()" class="btn btn-primary padding-top-1 padding-bottom-1" >
                        <div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
						</button>
	                     <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
                    </span>
               	</div><!-- /input-group -->
			</div>
			</div>	
			</div>
		</div>
	</div>
	
		<!-- start 上传文件 -->
	<div class="modal fade modal-new in" id="uploading" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:700px!important;">
			<div class="modal-content">
			      <div class="modal-header modal-header-new">
                       <h4 class="modal-title">
                          <div class="pull-left">
	                       <div class="font-size-12 ">上传</div>
							<div class="font-size-9">Upload</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class="clearfix"></div>
                       </h4>
                  </div>
                  
			 <div class="modal-body" style="max-height:413px !important;overflow: auto;margin-top:0px;">
			            <form id="form2">
			            	<div class="col-lg-12 col-xs-12">
			            	
						
							<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 form-group">
								<span class="pull-left col-lg-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>文件</div>
									<div class="font-size-9">File</div>
									</span>
								<div class="col-lg-9 col-sm-9 padding-left-8 padding-right-0">
								<input type="hidden"  id="wbwjm" name="wbwjm" value=""/>
									<div id="fileuploader"  style="overflow: hidden; cursor: default; font-size: 12px; position: relative; height: 25px; padding-left: 10px;"></div>	
								</div>
							</div> 
					<div class="clearfix"></div>
							
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0">
						<table class="table table-thin table-bordered table-striped table-hover text-center">
							<thead>
								<tr>
									<td style="width:110px;"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></td>
									<td >
									<div class="font-size-12 line-height-18">文件名</div>
										<div class="font-size-9 line-height-18">File Name</div>
									</td>
									
									<td>
									<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File Size</div>
									</td>
								</tr>
							</thead>
							    <tbody id="filelist">
									 
									 
								</tbody>
						</table>
					</div>
				<div class="clearfix"></div>
						 	 </div>
						</form>
			      <div class="clearfix"></div>
			  </div> 
			  
			  
		<div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8">
				<div class="input-group">
				<span class="input-group-addon modalfootertip">
	                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> 
				</span>
                    <span class="input-group-addon modalfooterbtn">
                    				<button type="button" onclick="uploadingFile();" class="btn btn-primary padding-top-1 padding-bottom-1" />
                    				 <div class="font-size-12">上传</div>
									<div class="font-size-9">Upload</div>
									</button>
					     			<button type="button" onclick="Close();" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
                       </span>
                 </div><!-- /input-group -->
			   </div>
			</div>	
			</div>
		</div>
	</div>
	
	
<!-- start 文件更名 -->
	<div class="modal fade in modal-new" id="updtaeuploading1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:500px!important;">
			<div class="modal-content">
			
				<div class="modal-header modal-header-new" >
	               	<h4 class="modal-title" >
	           			<div class='pull-left'>
	               			<div class="font-size-12 ">文件更名</div>
							<div class="font-size-9">Rename File</div>
					 	</div>
						<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class='clearfix'></div>
					</h4>
	          	</div>
			
				<div class="modal-body" style='padding-top:0px;'>
					<form id="form3">
	            	<div class="col-lg-12 col-xs-12">
	            	
						<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 form-group">
						<span class="pull-left col-lg-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12"><span style="color: red">*</span>文件名称</div>
							<div class="font-size-9">File Name</div>
							</span>
						<div class="col-lg-9 col-sm-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control " id="wbwjms" name="wbwjms" value="" maxlength="100"/>
						</div>
						</div> 

			     	 <div class="clearfix"></div>
	            	 <span class="pull-left col-lg-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">备注</div>
							<div class="font-size-9">Remark</div>
					</span>
				 	<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0 form-group">
						<textarea class="form-control" id="description" name="description" placeholder='长度最大为60'   maxlength="60"></textarea>
					</div>  
					 <div class="clearfix"></div>
			     		
				 	 </div>
				 	 </form>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
		        <div class="modal-footer">
	          		<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
							<span class="input-group-addon modalfootertip" >
				                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
							</span>
		                   	<span class="input-group-addon modalfooterbtn">
		                     	<button id="save_btn" type="button" onclick="updtaeuplFile()" class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
		                    	<button type="button" onclick="close()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
						    	</button>
		                   	</span>
	              		</div><!-- /input-group -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script type="text/javascript" src="${ctx}/static/js/thjw/common/select.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/maintenancemanuals_main.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>