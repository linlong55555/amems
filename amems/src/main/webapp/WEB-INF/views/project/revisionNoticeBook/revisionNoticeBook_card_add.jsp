<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>添加修订工卡</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script type="text/javascript">
	var pageParam = '${param.pageParam}';
	</script>
</head>
	<body class="page-header-fixed">
		<!-------导航Start--->
		<!-- BEGIN CONTENT -->
		<div class="page-content ">
			<div class="panel panel-primary">
				<div class="panel-heading">
				<div id="NavigationBar"></div>
				</div>
			<div class="panel-body">
					<input type="hidden" id="zdrid" value="${user.id}" />
				    <input class="form-control " value="${revisionNoticeBook.id}"  id="id" type="hidden" />  
				    <input id="dprtcode" type="hidden" value="${user.jgdm}" />
				    <input type="hidden" name="jgdm" id="jgdm" value="${user.jgdm}" />
				    <form id="form" >
				    
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>机型</div>
							<div class="font-size-9 line-height-18">Model</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jx' name="jx" onchange="jxgcs()">
						    </select>
						</div>
					</div>
					
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>修订人</div>
							<div class="font-size-9 line-height-18">Revised By</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id='xdrid' name="xdrid" >
						    </select>
						</div>
					</div>
					
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>修订期限</div>
							<div class="font-size-9 line-height-18">Revision Period</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="xdqx"  name="xdqx" class="form-control datepicker " style="width: 100%"  data-date-format="yyyy-mm-dd"  placeholder="请选择日期"   />
						</div>
					</div>
					
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">版本</div>
							<div class="font-size-9 line-height-18">Revision</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="bb"  name="bb" class="form-control datepicker " style="width: 100%" maxlength="5" />
						</div>
					</div>
					
				    <div class="clearfix"></div>
				    
				
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group ">
									<div class="font-size-12 line-height-18"><span style="color: red">*</span>
										关联评估单</div>
									<div class="font-size-9 line-height-18">Evaluation</div>
								</label>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x:auto;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px">
											<thead>
											<tr>
											<th class="colwidth-3">
											<button class="line6 " onclick="openPgd()" type="button">
												<i class="icon-search cursor-pointer color-blue cursor-pointer" ></i>
											</button>
											</th>
											<th class="colwidth-7"><div class="font-size-12 line-height-18" >评估单号 </div><div class="font-size-9 line-height-18" >Assessment No.</div></th>
											<th class="colwidth-5"><div class="font-size-12 line-height-18" >来源 </div><div class="font-size-9 line-height-18" >Source</div></th>
											<th class="colwidth-15"><div class="font-size-12 line-height-18" >参考资料</div><div class="font-size-9 line-height-18" >Reference Material</div></th>
											<th class="colwidth-13"><div class="font-size-12 line-height-18" >生效日期</div><div class="font-size-9 line-height-18" >Effective date</div></th>
											<th class="colwidth-13"><div class="font-size-12 line-height-18" >机型工程师</div><div class="font-size-9 line-height-18" >Engineer</div></th>
											<th class="colwidth-7"><div class="font-size-12 line-height-18" >评估期限</div><div class="font-size-9 line-height-18" >Assess period</div></th>
											<th class="colwidth-5"><div class="font-size-12 line-height-18" >评估状态</div><div class="font-size-9 line-height-18" >State</div></th>
											</tr>  
							         		 </thead>
											<tbody id="Annunciatelist">
							         		 </thead>
								</table>
								</div>
							</div>
			
				<div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0 form-group ">
						
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right  padding-right-0"><div class="font-size-12 line-height-18"><span style="color: red">*</span>修订主题</div>
							<div class="font-size-9 line-height-18">Subject</div></label>
						 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-20 padding-left-8 padding-right-0">
							<input maxlength="100" placeholder="长度最大为100" name="xdzt" id="xdzt" class="form-control date-picker" data-bv-field="xdzt" type="text">
						<small style="display: none;" class="help-block" data-bv-validator="stringLength" data-bv-for="xdzt" data-bv-result="NOT_VALIDATED">Please enter a value with valid length</small></div>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12 line-height-18">修订内容</div>
							<div class="font-size-9 line-height-18">Revision Contents</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 form-group padding-right-0">
							<textarea class="form-control" id="xdnr" name="xdnr" placeholder='长度最大为300'   maxlength="300"></textarea>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 form-group padding-right-0">
							<textarea class="form-control" id="bz" name="bz" placeholder='长度最大为300'   maxlength="300"></textarea>
					</div>
				</div>
				 <div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
						
						<label  class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File Explanation</div>
						</label>
						<div class=" col-xs-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
						<div class='input-group'>
							<input class="form-control date-picker"  id="wbwjm" name="wbwjm" type="text" maxlength="90" />
							<span class='input-group-btn'>
							 <div id="fileuploader"   ></div>
							</span>
					    </div>
					</div>
						
					</div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group">
									<div class="font-size-12 line-height-18">
										文件列表</div>
									<div class="font-size-9 line-height-18">List of Files</div>
								</label>

								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x:auto;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px">
										<thead>
											<tr>
												<th class="colwidth-3">
													<div class="font-size-12 line-height-18 " >操作</div>
													<div class="font-size-9 line-height-18">Operation</div>
												</th>
												<th class="colwidth-30">
													<div class="font-size-12 line-height-18">文件说明</div>
													<div class="font-size-9 line-height-18">File Explanation</div>
												</th>
												<th class="colwidth-5">
													<div class="font-size-12 line-height-18">文件大小</div>
													<div class="font-size-9 line-height-18">File Size</div>
												</th>
												<th class="colwidth-10">
													<div class="font-size-12 line-height-18">上传人</div>
													<div class="font-size-9 line-height-18">Uploader</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 line-height-18">上传时间</div>
													<div class="font-size-9 line-height-18">Upload Time</div>
												</th>					
											</tr>
										</thead>
									    <tbody id="filelist">
											 
										</tbody>
									</table>
								</div>
							</div>
					<div class="clearfix"></div>
					<div class="col-lg-12 text-right padding-left-0 padding-right-0">
                     <button id="edit" name="edit" class="btn btn-primary padding-top-1 padding-bottom-1" type="button">
                    	 <div class="font-size-12">保存</div>
					 	 <div class="font-size-9">Save</div>
					 </button>
					  <button class="btn btn-primary padding-top-1 padding-bottom-1" id="submit" name="submit" type="button">
                     	 <div class="font-size-12">提交</div>
						 <div class="font-size-9">Submit</div>
					 </button>
					   <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:back()" type="button">
                     	<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					 </button>
                    </div>
					
				<div class="clearfix"></div>
				</form>
			</div>
		</div>

		<!-- 编辑维修方案End -->
		

		 

		<!-------alert对话框 Start-------->
		<div class="modal fade" id="alertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="alertModalBody"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-------alert对话框 End-------->

</div>
<div class="modal fade" id="alertModalPgd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:70%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">选择技术评估单</div>
							<div class="font-size-9 ">Operation</div>
						</div>
							<div class="panel-body padding-top-0 padding-bottom-0">
								<div class="col-lg-9 pull-right padding-right-0">
									<div class=" pull-right padding-left-0 padding-right-0 padding-top-10">
										<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
											<input type="text" placeholder="文件编号/评估单号/文件主题" id="keyword_search" class="form-control ">
										</div>
					                    <div class=" pull-right padding-left-5 padding-right-0 ">   
											<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
					                   		</button>
					                    </div> 
									</div>
					            </div>
					            
					            
					            
					            <div class="clearfix"></div>
					            
				                
								<!-- start:table -->
								<div style="margin-top: 10px " >
								<div style="overflow-x: auto;width: 100%;">
									<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width: 1600px !important">
												<thead>
												<tr>
												<th class="colwidth-3"><div class="font-size-12 line-height-18" >选择</div><div class="font-size-9 line-height-18" >Choice</div></th>
												<th class="colwidth-15"><div class="important"><div class="font-size-12 line-height-18" >文件编号 </div><div class="font-size-9 line-height-18" >File No.</div></div></th>
												<th class="colwidth-7"><div class="important"><div class="font-size-12 line-height-18" >评估单号</div><div class="font-size-9 line-height-18" >Assessment No.</div></div></th>
												<th class="colwidth-5"><div class="font-size-12 line-height-18" >来源</div><div class="font-size-9 line-height-18" >Source</div></th>
												<th class="colwidth-3"><div class="font-size-12 line-height-18" >机型</div><div class="font-size-9 line-height-18" >Model</div></th>
												<th class="colwidth-3"><div class="font-size-12 line-height-18" >分类</div><div class="font-size-9 line-height-18" >Category</div></th>
												<th class="colwidth-3"><div class="font-size-12 line-height-18" >版本</div><div class="font-size-9 line-height-18" >Revision</div></th>
												<th class="colwidth-25"><div class="important"><div class="font-size-12 line-height-18" >文件主题</div><div class="font-size-9 line-height-18" >Subject</div></div></th>
												<th class="colwidth-7"><div class="font-size-12 line-height-18" >生效日期</div><div class="font-size-9 line-height-18" >Effective Date</div></th>
												<th class="colwidth-13"><div class="font-size-12 line-height-18" >机型工程师</div><div class="font-size-9 line-height-18" >Engineer</div></th>
												<th class="colwidth-13"><div class="font-size-12 line-height-18" >评估期限</div><div class="font-size-9 line-height-18" >Assess period</div></th>
												<th class="colwidth-5"><div class="font-size-12 line-height-18" >评估状态</div><div class="font-size-9 line-height-18" >State</div></th>
												</tr>  
								         		 </thead>
												<tbody id="Pgdlist">
												</tbody>
									</table>
									</div>
									<!-- <div class="col-xs-12 text-center"  style="margin-top: 20px;">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" id="pagination">
										</ul>
									</div> -->
									</div>
								<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
									</div>
								<!-- end:table -->
			                	<div class="modal-footer  padding-right-0  padding-top-0" style="border-top: medium none ! important;">
									<button type="button" onclick="appendPgd()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
								</div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
		<!-------alert对话框 End-------->
	</div>
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/revisionNoticeBook/revisionNoticeBook_card_add.js"></script>
</body>
</html>