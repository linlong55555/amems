<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					searchRevision();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
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
		
         <div class='col-xs-12 ' style='padding-left:0px;padding-right:0px;padding-top:0px;'>
		 	<div class='col-lg-3 col-sm-4 col-xs-12' style='padding-left:0px;padding-right:10px;' id="left_div">
		 	<div class="panel panel-primary" >
				<div class="panel-heading">
					<div class="font-size-12 line-height-12">目录</div>
                    <div class="font-size-9 line-height-9">Directory</div>
				</div>
				<div class="panel-body padding-left-0 padding-right-0 padding-bottom-0 padding-top-0" id='document_leftBody'>
					<div style='border-bottom:1px dashed #d5d5d5;margin-bottom:8px;' class='row-height'>
		                <div class=" col-lg-12 col-xs-12 padding-leftright-8" style='padding-bottom:5px;'>
			                <div class="btn-group" >
				                <button class="btn btn-default padding-top-1 padding-bottom-1   margin-top-5 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:01,maintenance:maintenancemanuals:manage1:01,project2:document:main:01,produce:file:scmain:01,training:file:zlmain:01,material:file:hcmain:01' onclick="AssignStoreOne()" style='height:30px;line-height:30px;' title='新增一级目录'>
				                	<i class='iconnew-newdirectory' style='font-size:16px;'></i>
				                </button>
				                <button class="btn btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission  margin-top-5" permissioncode='maintenance:maintenancemanuals:manage:01,maintenance:maintenancemanuals:manage1:01,project2:document:main:01,produce:file:scmain:01,training:file:zlmain:01,material:file:hcmain:01' onclick="AssignStore()" style='height:30px;line-height:30px;' title='新增文件夹'>
									<i class='iconnew-newfolder' style='font-size:16px;'></i>  
								</button>
								<button class="btn btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission  margin-top-5" permissioncode='maintenance:maintenancemanuals:manage:02,maintenance:maintenancemanuals:manage1:02,project2:document:main:02,produce:file:scmain:02,training:file:zlmain:02,material:file:hcmain:02'onclick="updateFile()" style='height:30px;line-height:30px;' title='文件夹更名'>
									<i class='icon-pencil' style='font-size:16px;'></i>
								</button>
								<button class="btn btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission  margin-top-5" permissioncode='maintenance:maintenancemanuals:manage:03,maintenance:maintenancemanuals:manage1:03,project2:document:main:03,produce:file:scmain:03,training:file:zlmain:03,material:file:hcmain:03'onclick="deleteFile()" style='height:30px;line-height:30px;' title='删除文件夹'>
									<i class='iconnew-deletefolder' style='font-size:16px;'></i>
								</button>
								<button class="btn btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission  margin-top-5" permissioncode='maintenance:maintenancemanuals:manage:09,maintenance:maintenancemanuals:manage1:09,maintenance:maintenancemanuals:manage:09,produce:file:scmain:09,training:file:zlmain:09,material:file:hcmain:09'onclick="moveAlertModal.moveFile(1)" style='height:30px;line-height:30px;' title='移动文件夹'>
									<i class='icon-move' style='font-size:16px;'></i>
								</button>
								<button class="btn btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission  margin-top-5" permissioncode='maintenance:maintenancemanuals:manage:08,maintenance:maintenancemanuals:manage1:08,maintenance:maintenancemanuals:manage:08,produce:file:scmain:08,training:file:zlmain:08,material:file:hcmain:08'onclick="importAlertModal.importFile()" style='height:30px;line-height:30px;' title='导入文件夹'>
									<i class='icon-upload' style='font-size:16px;'></i>
								</button>
								<button class="btn btn-default padding-top-1 padding-bottom-1 margin-top-5 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:10,maintenance:maintenancemanuals:manage1:10,maintenance:maintenancemanuals:manage:10,produce:file:scmain:10,training:file:zlmain:10,material:file:hcmain:10' onclick="exportModal.exportFile()" style='height:30px;line-height:30px;' title='导出文件夹'>
									<i class='icon-download' style='font-size:16px;'></i>
								</button>
							</div>
						</div>
						<div class='clearfix'></div>
	                </div>
					<div class='padding-leftright-8' id='using_json_parent' >
		                <div id="using_json"></div>
		                <div class='clearfix'></div>
	                </div>
				</div>
				 <!-- 垃圾桶 -->
				<div class="panel-footer  checkPermission" permissioncode='maintenance:maintenancemanuals:manage:07,maintenance:maintenancemanuals:manage1:07,project2:document:main:07,produce:file:scmain:07,training:file:zlmain:07,material:file:hcmain:07'   style='padding-bottom:8px;'>
					 <p class='margin-bottom-0' >
						 <a href='javascript:;' onclick='showTrash()' style='position:relative;'>
							 <i class='fa fa-recycle' style='font-size:20px;'></i> 
							 <span class="badge" id='recycleCount' style='position:absolute;background:red;top:-9px;'>0</span>
							  <span class="badge" style='background:red;visibility:hidden;'>1</span>
						 </a>
						 <small>点击小图标查看回收站信息</small>
						  <a class="btn btn-default padding-top-1 padding-bottom-1 margin-left-3  margin-top-5  checkPermission" permissioncode='maintenance:maintenancemanuals:manage:10,maintenance:maintenancemanuals:manage1:10,maintenance:maintenancemanuals:manage:10,produce:file:scmain:10,training:file:zlmain:10,material:file:hcmain:10' onclick="taskModal.showTask()" style='height:30px;line-height:30px;' title='任务处理'>
									<i class='icon-download' style='font-size:16px;'></i>
						  </a>
					 </p>
				</div>
			</div>
	 	</div>
	 	<div class='col-lg-9 col-sm-8 col-xs-12' style='padding-left:0px;padding-right:0px;' id="right_div">
			<div id="document_toggle_div" style="position: absolute; left: -8px; top: 250px;">
			<i class="cursor-pointer icon-caret-left"   style="font-size: 22px;" onclick="documentToggle(this)"></i>
			</div>
			<div class="panel panel-primary" id='fileInfo'>
				<div class="panel-heading">
					<div class="font-size-12 line-height-12">文件列表</div>
				    <div class="font-size-9 line-height-9">File List</div>
				</div>
			<div class="panel-body padding-leftright-8 document_rightBody" >
				<div  class='searchContent margin-top-0 row-height' >
				<!-- 上传按钮  -->
					<div class=" pull-left form-group  ">
						<div class="btn-group">
							<button class="btn btn-xs btn-default padding-top-1 padding-bottom-1 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:04,maintenance:maintenancemanuals:manage1:04,project2:document:main:04,produce:file:scmain:04,training:file:zlmain:04,material:file:hcmain:04' onclick="uploading()" style='height:30px;line-height:30px;' title='上传'>
								<i class='fa fa-upload' style='font-size:16px;'></i>
							</button>
							<button class="btn btn-xs btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:05,maintenance:maintenancemanuals:manage1:05,project2:document:main:05,produce:file:scmain:05,training:file:zlmain:05,material:file:hcmain:05'onclick="updtaeuploading()" style='height:30px;line-height:30px;' title='文件更名'>
								<i class='icon-pencil' style='font-size:16px;'></i>
							</button>
							<button class="btn btn-xs btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:06,maintenance:maintenancemanuals:manage1:06,project2:document:main:06,produce:file:scmain:06,training:file:zlmain:06,material:file:hcmain:06'onclick="deleteuploading()" style='height:30px;line-height:30px;' title='删除文件'>
								<i class='iconnew-deletefile' style='font-size:16px;'></i>
							</button>
							<button class="btn btn-xs btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:09,maintenance:maintenancemanuals:manage1:09,maintenance:maintenancemanuals:manage:09,produce:file:scmain:09,training:file:zlmain:09,material:file:hcmain:09'onclick="moveAlertModal.moveFile(2)" style='height:30px;line-height:30px;' title='移动文件'>
								<i class='icon-move' style='font-size:16px;'></i>
							</button>
						</div>
					</div>
				<!-- 关键字搜索 -->
				<div class="pull-right form-group " style='padding-left:15px;width:270px;'>
					<div class="col-xs-12 input-group input-group-search">
						<input placeholder="文件名称/上传时间/备注" id="keyword_search" class="form-control" type="text">
						<div class="input-group-addon btn-searchnew" >
							<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
							</button>
						</div>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 man_file" style="overflow: auto;">
					<table id="wjlb" class="table table-thin table-bordered table-hover table-set margin-botton-0">
					<thead>
						<tr>
						<th class="colwidth-7 selectAllImg" style="vertical-align:middle;">
							<a href="javascript:checkAll();"  id='CancelAll' >
								<img src="${ctx}/static/assets/img/d1.png" alt="全选" title="全选" />
							</a>
							<a href="javascript:notCheckAll();" class="margin-left-5" id='CancelAll'>
								<img src="${ctx}/static/assets/img/d2.png" alt="不全选" title="不全选" />
							</a> 
						</th>
						<th class="colwidth-3">
							<div class="font-size-12 line-height-18">序号</div>
							<div class="font-size-9 line-height-18">No.</div>
						</th>
						<th class="sorting colwidth-20" onclick="orderBy('wbwjm')" id="wbwjm_order">
							<div class="important">
								<div class="font-size-12 line-height-18">文件名称</div>
							</div>
							<div class="font-size-9 line-height-18">File Name</div>
						</th>
						<th  class="sorting colwidth-10" onclick="orderBy('wjdx')" id="wjdx_order">
							<div class="font-size-12 line-height-18">文件大小</div>
							<div class="font-size-9 line-height-18">File Size</div>
						</th>
						<th  class="colwidth-13">
							<div class="font-size-12 line-height-18">上传人</div>
							<div class="font-size-9 line-height-18">Upload Person</div>
						</th>
						<th  class="sorting colwidth-13" onclick="orderBy('czsj')" id="czsj_order">
							<div class="important">
								<div class="font-size-12 line-height-18">上传时间</div>
							</div>
							<div class="font-size-9 line-height-18">Upload Time</div>
						</th>
						<th class="colwidth-30">
							<div class="important">
								<div class="font-size-12 line-height-18">备注</div>
							</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</th>
						</tr>
					</thead>
					<tbody id="list">
					</tbody>
					</table>
				</div>
			</div>
			</div>
		<!-- 回收站信息 -->
		<div class="panel panel-primary" id='removeInfo' style='display:none;'>
			<div class="panel-heading">
				<div class='pull-left'>
					<div class="font-size-12 line-height-12">回收站</div>
				    <div class="font-size-9 line-height-9">Recycle Bin</div>
			    </div>
			    <div class='pull-right'>
			    	<button type="button" class="fa fa-reply modal-close"  style='margin-top:3px;' onclick='showFile()'></button>
			    </div>
			    <div class='clearfix'></div>
			</div>
		<div class="panel-body padding-leftright-8 document_rightBody" >
			<div  class='searchContent margin-top-0 row-height' >
			<!-- 上传按钮  -->
				<div class=" pull-left form-group">
					<div class="btn-group">
						<button class="btn btn-xs btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:05,maintenance:maintenancemanuals:manage1:05' style='height:30px;line-height:30px;' title='清空全部' onclick="empty()">
							<i class='iconnew-clearAll' style='font-size:16px;'></i>
						</button>
						<button class="btn btn-xs btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:06,maintenance:maintenancemanuals:manage1:06'style='height:30px;line-height:30px;' title='删除选中文件' onclick="confirmDelete()">
							<i class='icon-trash' style='font-size:16px;'></i>
						</button>
						<button class="btn btn-xs btn-default padding-top-1 padding-bottom-1 margin-left-3 checkPermission" permissioncode='maintenance:maintenancemanuals:manage:06,maintenance:maintenancemanuals:manage1:06'style='height:30px;line-height:30px;' title='撤销' onclick="restore()">
							<i class='iconnew-undo' style='font-size:16px;'></i>
						</button>
					</div>
				</div>
			<!-- 关键字搜索 -->
			<div class="pull-right form-group " style='padding-left:15px;width:270px;'>
				<div class="col-xs-12 input-group input-group-search">
					<input placeholder="文件名称"  class="form-control" type="text" id="recycled_keyword_search">
					<div class="input-group-addon btn-searchnew" >
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="refreshRecycledData()">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
						</button>
					</div>
				</div>
			</div>
			<div class='clearfix'></div>
			</div>
		<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 man_file" style="overflow: auto;">
			<table class="table table-thin table-bordered table-hover table-striped table-set margin-botton-0" id="recycled_table">
							<thead>
								<tr>
									<th class="colwidth-7 selectAllImg" style="vertical-align:middle;">
										<a href="javascript:checkAll();"  id='CancelAll' >
											<img src="${ctx}/static/assets/img/d1.png" alt="全选" title="全选" />
										</a>
										<a href="javascript:notCheckAll();" class="margin-left-5" id='CancelAll'>
											<img src="${ctx}/static/assets/img/d2.png" alt="不全选" title="不全选" />
										</a> 
									</th>
									<th class="colwidth-3">
										<div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</th>
									<th class="sorting colwidth-8" onclick="recycledOrderBy('wjlx')" name="column_wjlx">
											<div class="font-size-12 line-height-18">类型</div>
										<div class="font-size-9 line-height-18">Type</div>
									</th>
									<th  class="sorting colwidth-20" onclick="recycledOrderBy('wjmc')" name="column_wjmc">
										<div class="font-size-12 line-height-18">文件名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									</th>
									<th  class="colwidth-20">
										<div class="font-size-12 line-height-18">原始位置</div>
										<div class="font-size-9 line-height-18">Original position</div>
									</th>
									<th  class="sorting colwidth-8" onclick="recycledOrderBy('username')" name="column_username">
										<div class="font-size-12 line-height-18">删除人</div>
										<div class="font-size-9 line-height-18">Deleting person</div>
									</th>
									<th class="sorting colwidth-11" onclick="recycledOrderBy('czsj')" name="column_czsj">
										<div class="font-size-12 line-height-18">删除时间</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
								</tr>
							</thead>
							<tbody id="recycled_list">
							</tbody>
						</table>
				     </div>
					</div>
				</div>
		 	</div>
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
						
					<div class="clearfix"></div>
							
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-10">
						<table class="table table-thin table-bordered table-striped table-hover text-center">
							<thead>
								<tr>
									<td style="width: 45px;text-align:center;vertical-align:middle;">
										<div id="fileuploader" ></div>
									<td >
									<div class="font-size-12 line-height-12">文件名</div>
										<div class="font-size-9 line-height-18">File Name</div>
									</td>
									
									<td>
									<div class="font-size-12 line-height-12">文件大小</div>
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
	
	<!-- start 移动文件夹 -->
	<div class="modal fade in modal-new" id="moveAlertModal" tabindex="-1" role="dialog" aria-labelledby="moveAlertModal" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">             
                <div class="modal-header modal-header-new">
                       <h4 class="modal-title">
                          <div class="pull-left">
	                       <div class="font-size-12 ">移动</div>
							<div class="font-size-9">Move</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						  <div class="clearfix"></div>
                       </h4>
                  </div>    
			  <div class="modal-body" style="max-height:413px !important;overflow: auto;margin-top:0px;">
					<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 form-group">
						<span class="pull-left col-lg-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12" id="moveCH">目录名称</div>
							<div class="font-size-9" id="moveEng">Directory Name</div>
							</span>
						<div id="bmlmc" class="col-lg-9 col-sm-9 padding-left-8 padding-right-0">
							
						</div>
					</div> 
			 		<div class="clearfix"></div>
			 	
			 		<div class='padding-leftright-8' id='using_json_parent' >
		                <div id="using_alert_json"></div>
		                <div class='clearfix'></div>
	                </div>
			 	
			 		<div class="clearfix"></div>
			  </div>
			
			<div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8">
				<div class="input-group">
				<span class="input-group-addon modalfootertip">
	                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>     
				</span>
                    <span class="input-group-addon modalfooterbtn">
                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="moveAlertModal.doMoveFile()">
							<div class="font-size-12">移动</div>
							<div class="font-size-9">Move</div>
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
	
	
	<!-- start 导入文件夹 -->
	<div class="modal fade in modal-new" id="importAlertModal" tabindex="-1" role="dialog" aria-labelledby="importAlertModal" aria-hidden="true">
		<div class="modal-dialog" style="width:550px!important;">
			<div class="modal-content">             
                <div class="modal-header modal-header-new">
                       <h4 class="modal-title">
                          <div class="pull-left">
	                       <div class="font-size-12 ">导入</div>
							<div class="font-size-9">Import</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						  <div class="clearfix"></div>
                       </h4>
                  </div>    
			  <div class="modal-body" style="max-height:413px !important;overflow: auto;margin-top:0px;">
					
					<div id="doczip_attachments_single" class="col-lg-12 col-md-12 col-sm-9 col-xs-8 padding-leftright-32">
						<div id="edit_attach_div" class="input-group col-xs-12">
						    <span class="input-group-addon inputgroupbordernone" style='padding-left: 30px;padding-right: 15px;'>
						    	<div class="font-size-12" id="moveCH">上传文件压缩包</div>
								<div class="font-size-9" id="moveEng">Upload zip(rar) file </div>
						    </span>
							<span id="fileuploaderSingle" class='singlefile input-group-btn'>
							</span>
						    <div class="wbwjmSingle padding-left-0 padding-right-0 font-size-12" ></div>
					    </div>
					    <div id="view_attach_div" class="tag-set" style="margin-top: 5px;">
					    	
					    </div>
					</div>
					
			 		<div class="clearfix"></div>
			 	
			 		<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 form-group">
						<span class="pull-left col-lg-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12" id="moveCH">选择存放的路径</div>
							<div class="font-size-9" id="moveEng">Select storage directory </div>
						</span>
						<div id="import_using_json_parent" class="col-lg-9 col-sm-9 padding-left-8 padding-right-0">
							<div id="import_using_alert_json"></div>
		                	<div class='clearfix'></div>
						</div>
					</div> 
			 	
			 		<div class="clearfix"></div>
			  </div>
			
			<div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8">
				<div class="input-group">
				<span class="input-group-addon modalfootertip">
	                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>     
				</span>
                    <span class="input-group-addon modalfooterbtn">
                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="importAlertModal.doImportFile()">
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>
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
		<!-- start 导出文件 -->
	<div class="modal fade in modal-new" id="exportModal" tabindex="-1" role="dialog" aria-labelledby="exportModal" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">             
                <div class="modal-header modal-header-new">
                       <h4 class="modal-title">
                          <div class="pull-left">
	                       <div class="font-size-12 ">导出</div>
							<div class="font-size-9">Export</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						  <div class="clearfix"></div>
                       </h4>
                  </div>    
			  <div class="modal-body" style="max-height:413px !important;overflow: auto;margin-top:0px;">			
			 		<div class='padding-leftright-8' id='using_json_parent' >
		                <div id="exportModal_using_alert_json"></div>
		                <div class='clearfix'></div>
	                </div>
			 	
			 		<div class="clearfix"></div>
			  </div>
			
			<div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8">
				<div class="input-group">
				<span class="input-group-addon modalfootertip">
	                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>     
				</span>
                    <span class="input-group-addon modalfooterbtn">
                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="exportModal.doSave()">
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
	<!-- 任务处理 -->
	<div class="modal fade in modal-new" id="taskModal" tabindex="-1" role="dialog" aria-labelledby="taskModal" aria-hidden="true">
		<div class="modal-dialog" style="width:750px!important;">
			<div class="modal-content">             
                <div class="modal-header modal-header-new">
                       <h4 class="modal-title">
                          <div class="pull-left">
	                       <div class="font-size-12 ">导出任务</div>
							<div class="font-size-9">Export Task</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						  <div class="clearfix"></div>
                       </h4>
                  </div>    
			  	  <div class="modal-body">
				  	  <div class="col-lg-12 col-md-12 padding-left-0 margin-top-10 padding-right-0 table-set"  id="task_table_div">			
				 			<table id="task_table" class="table table-bordered table-thin table-striped table-hover table-set" style="margin-bottom:0px !important;">
								<thead>
							   		<tr>
							   			<th class="colwidth-7" >
											<div class="font-size-12 line-height-12" >操作</div>
											<div class="font-size-9 line-height-12" >Operation</div>
										</th>														
										<th >
											<div class="font-size-12 line-height-12" >任务信息 </div>
											<div class="font-size-9 line-height-12" >Task Info</div>
										</th>
										<th class="colwidth-13" >
											<div class="font-size-12 line-height-12" >申请信息</div>
											<div class="font-size-9 line-height-12" >Apply Info</div>
										</th>			
										<th class="colwidth-13" >
											<div class="font-size-12 line-height-12" >状态</div>
											<div class="font-size-9 line-height-12" >Status</div>
										</th>
										<th class="colwidth-7" >
											<div class="font-size-12 line-height-12" >下载</div>
											<div class="font-size-9 line-height-12" >DownLoad</div>
										</th>	
							 		 </tr>
								</thead>
								<tbody id="taskModal_list">
								
								</tbody>
						</table>
				  </div>
				  <div class="col-xs-12 text-center padding-left-0 padding-right-0 page-height " id="pagination_list">
			</div>
			<div class="clearfix"></div>
			</div>
			<div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8">
				<div class="input-group">
				<span class="input-group-addon modalfootertip">
	                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>     
				</span>
                    <span class="input-group-addon modalfooterbtn">
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
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	
<link href="${ctx }/static/js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static/js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script type="text/javascript" src="${ctx}/static/js/thjw/common/select.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/document/document_main.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/attachments/attachments_single_zip.js"></script> <!-- 上传zip文件 -->

</body>
</html>