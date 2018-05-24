<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>添加MEL变更单</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script type="text/javascript">
		var pageParam = '${param.pageParam}';
	</script>
</head>
	<body class="page-header-fixed">
	<input type="hidden" id="dprtcode" value="${dprtcode}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<input type="hidden" id="mcsId" value="${id}" />
	<input type="hidden" id="melqdfjid" value="" />
		<!-------导航Start--->
		<!-- BEGIN CONTENT -->
		<div class="page-content ">
			<div class="panel panel-primary">
				<div class="panel-heading">
				<div id="NavigationBar"></div>
				</div>
			<div class="panel-body">
				<form id="form" >
				    
				    <div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>文件编号</div>
							<div class="font-size-9 line-height-18">File No.</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="ggdbh"  name="ggdbh" class="form-control" maxlength="50" />
						</div>
					</div>
				    
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>机型</div>
							<div class="font-size-9 line-height-18">Model</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jx' name="jx">
						    </select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">项目号</div>
							<div class="font-size-9 line-height-18">Project No.</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="xmh"  name="xmh" class="form-control" maxlength="50" />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">所属部分</div>
							<div class="font-size-9 line-height-18">Owned Part</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="ssbf"  name="ssbf" class="form-control" maxlength="100" />
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">所属章节</div>
							<div class="font-size-9 line-height-18">Covered</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="sszj"  name="sszj" class="form-control" maxlength="100" />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">中/英</div>
							<div class="font-size-9 line-height-18">Chinese/English</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="zy"  name="zy" class="form-control" maxlength="100" />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">修改前版本</div>
							<div class="font-size-9 line-height-18">Version</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="xgqBb"  name="xgqBb" class="form-control" maxlength="16" />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span style="color: red">*</span>修改后版本</div>
							<div class="font-size-9 line-height-18">Version</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="xghBb"  name="xghBb" class="form-control" maxlength="16" />
						</div>
					</div>
					
					<div class="clearfix"></div>
				    
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 padding-right-0">
				
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
						         		</tbody>
								</table>
						</div>
					</div>
					
					<div class="clearfix"></div>
				    
				    <div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-0 form-group" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">修改依据</div>
							<div class="font-size-9 line-height-18">Basis</div>
						</label>
						<div id="xgyjDiv" class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 input-group">
						</div>
					</div>
					
					<div class="clearfix"></div>
				    
				    <div class="col-lg-6 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">修订页</div>
							<div class="font-size-9 line-height-18">Revision page</div>
						</label>
						<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0 input-group">
							<input type="text" id="xdqx"  name="xdqx" class="form-control" maxlength="100" />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">修改标记</div>
							<div class="font-size-9 line-height-18">Modify Mark</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
						    <input name="xgbj" type="checkbox" value="A" />
						    A
						  	 新增
						  	 &nbsp;
						  	<input name="xgbj" type="checkbox" value="R" />
						    R
						  	 修订
						  	 &nbsp;
						  	<input name="xgbj" type="checkbox" value="D" />
						    D
						  	 删除
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">修改内容</div>
							<div class="font-size-9 line-height-18">Content</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="xdnr" name="xdnr" maxlength="1000" ></textarea>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10 form-group">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">修改原因</div>
							<div class="font-size-9 line-height-18">Reason</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="xgyy" name="xgyy" maxlength="1000" ></textarea>
						</div>
					</div>
					
				<div class="clearfix"></div>
				
				<div class=" col-lg-6 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
					<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">MEL清单</div>
						<div class="font-size-9 line-height-18">Mel Detailed</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-6 padding-left-8 padding-right-0">
						<div class="input-group">
							<input class="form-control"  id="wbwjmSingle" name="wbwjmSingle" type="text" maxlength="50" />
							<span class='input-group-btn'>
							 	<div id="fileuploaderSingle"   ></div>
							</span>
						</div>
					</div>
					<div id="melqdDiv" class="col-lg-2 col-sm-2 col-xs-2 padding-left-10 padding-right-0">
							
					</div>
				</div>
				
				<!-- <div id="melqdDiv" class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
				</div> -->
				<div class="clearfix"></div>
				<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit.jsp"%><!-- 加载附件信息 -->
					
				
							 <div class="clearfix"></div>
				 <div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
					<div class="font-size-16 line-height-18">审批</div>
					<div class="font-size-9 ">Approval</div>
				 </div>
				 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
				 
				 	<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"  >
						<div class="font-size-12 line-height-18">审核意见</div>
						<div class="font-size-9 line-height-18">Review Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
							<textarea class="form-control" id="shyj" name="shyj" maxlength="150" readonly></textarea>
					</div>
					<div class="clearfix"></div>
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
						<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
							<div class="font-size-12 line-height-18" style="font-weight:normal">审核人&nbsp;<label style="margin-right: 50px;font-weight:normal" id="shrname"></label></div>
							<div class="font-size-9 line-height-12">Reviewer</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18" style="font-weight:normal">审核时间 &nbsp;<label style="margin-right: 50px;font-weight:normal" id="shsj"></label></div>
						<div class="font-size-9 line-height-12">Review Time</div>
						</div>
				 	</div>
				 	
				 <div class="clearfix"></div><br/>
				 
				 <label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"  >
						<div class="font-size-12 line-height-18">批准意见</div>
						<div class="font-size-9 line-height-18">Approval Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
							<textarea class="form-control" id="pfyj" name="pfyj" maxlength="150" readonly></textarea>
					</div>
					<div class="clearfix"></div>
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
						<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18" style="font-weight:normal">批准人 &nbsp;<label style="margin-right: 50px;font-weight:normal" id="pyrname"></label></div>
						<div class="font-size-9 line-height-12">Appr. By</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18" style="font-weight:normal">批准时间    &nbsp;<label style="margin-right: 50px;font-weight:normal" id="pysj"></label></div>
						<div class="font-size-9 line-height-12">Approved Time</div>
						</div>
				 	</div>
					 <div class="clearfix"></div><br/>
				  </div>
				
				<div class="clearfix"></div>
					
					<div class="col-lg-12 text-right padding-left-0 padding-right-0">
                     <button onclick="javascript:save();" class="btn btn-primary padding-top-1 padding-bottom-1" type="button">
                    	 <div class="font-size-12">保存</div>
					 	 <div class="font-size-9">Save</div>
					 </button>
					  <button onclick="javascript:submt();" class="btn btn-primary padding-top-1 padding-bottom-1" type="button">
                     	 <div class="font-size-12">提交</div>
						 <div class="font-size-9">Submit</div>
					 </button>
                    </div>
					
				<div class="clearfix"></div>
				</form>
			</div>
		</div>

		<!-- 编辑维修方案End -->
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
<script type="text/javascript" src="${ctx}/static/js/thjw/project/mel/mel_edit.js"></script>
</body>
</html>