<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维修计划</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = '${param.id}';
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
		<div class="page-content">
			<div class="panel panel-primary" >
				<div class="panel-heading  "> 
					<div id="NavigationBar"></div>
				</div>
				<div class="panel-body">
				 
					<div class=" pull-left padding-right-0" style="width: 310px;">
							<button id="hcStatisticsBtn" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 checkPermission row-height"
							 	permissioncode='productionplan:plantask:manage:04'>
								<div class="font-size-12">航材工具缺件统计</div>
								<div class="font-size-9">Shortage Statistics</div>
	                  		</button>
	                  		<button id="export" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1  row-height">
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
	                  		</button>
					</div>
					
               		<!-- 搜索框start -->
					<div class=" pull-right padding-left-0 padding-right-0 search-height" >
						<div class=" pull-left padding-left-5 padding-right-0" style="width: 200px;">
					        <span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
					        	<div class="font-size-12">飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</span>
							<div class=" col-xs-8 col-sm-8 padding-left-5 padding-right-0">
									<select class='form-control' id='fjzch' name="fjzch">
								    </select>
							</div>
						</div>	
						
						<div class=" pull-left padding-left-5 padding-right-0" style="width: 200px;">
					        <span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
					        	<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class=" col-xs-8 col-sm-8 padding-left-5 padding-right-0">
									<select id="dprtcode" class="form-control " name="dprtcode">
										<c:choose>
											<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}"
													<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}
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
							
								
						<div class=" pull-left padding-left-5 padding-right-0" style="width: 200px;">
							<input title="任务单号/任务信息/飞机注册号/部件号/序列号" placeholder="任务单号/任务信息/飞机注册号/部件号/序列号" id="keyword" class="form-control " type="text">
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button id="searchBtn" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 resizeHeight" >
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                  		</button>
	                    </div> 
					</div>
					<div class="clearfix"></div>
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-10" style='overflow-x:auto;' id="plantask_table_div">
													
					<!-- table table-thin table-bordered table-striped table-hover text-center table-set -->
						<table id="plantask" class="table table-thin table-bordered text-center table-set" style="min-width: 2000px !important">
							<thead>
								<tr>
									<th class="fixed-column" style="vertical-align: middle;width:50px; ">
										<!-- <input  id="chk-att" type="checkbox"/> -->
										<a href="javascript:checkAll();" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll' ><img src="${ctx}/static/assets/img/d1.png" alt="全选" title="全选" /></a>
									    <a href="javascript:notCheckAll();" class="pull-left margin-left-10 margin-bottom-10" id='CancelAll'><img src="${ctx}/static/assets/img/d2.png" alt="不全选" title="不全选" /></a>
									
										</th>
									<th class="fixed-column colwidth-7"  >
										<div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									
									<th class="sorting colwidth-15" onclick="orderBy('rwxx')" id="rwxx_order">
										<div class="important">
										<div class="font-size-12 line-height-18">任务相关信息</div>
										<div class="font-size-9 line-height-18">Related Info</div>
										</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('rwlx')" id="rwlx_order">
										<div class="font-size-12 line-height-18">来源类型</div>
										<div class="font-size-9 line-height-18">Source Type</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('fjzch')" id="fjzch_order">
										<div class="important">
											<div class="font-size-12 line-height-18">飞机注册号</div>
											<div class="font-size-9 line-height-18">A/C REG</div>
										</div>
									</th>
									
									<th class="colwidth-20">
										<div class="font-size-12 line-height-18">计划</div>
										<div class="font-size-9 line-height-18">Plan</div>
									</th>
									<th class="colwidth-20">
										<div class="font-size-12 line-height-18">剩余</div>
										<div class="font-size-9 line-height-18">Remain</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('sydays')" id="sydays_order">
										<div class="font-size-12 line-height-18">剩余(天)</div>
										<div class="font-size-9 line-height-18">Remain(Day)</div>
									</th>
										
									<th class="colwidth-30">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</th>	
									<th class="sorting colwidth-13" onclick="orderBy('dysj')" id="dysj_order"  >
										<div class="font-size-12 line-height-18">打印时间</div>
										<div class="font-size-9 line-height-18">Print Time</div>
									</th>
									<th class="colwidth-13">
										<div class="font-size-12 line-height-18">打印人</div>
										<div class="font-size-9 line-height-18">Printer</div>
									</th>	
										
									<th class="sorting colwidth-10" onclick="orderBy('zt')" id="zt_order">
										<div class="font-size-12 line-height-18">计划状态</div>
										<div class="font-size-9 line-height-18">Plan State</div>
									</th>
																			
									<th class="sorting colwidth-13" onclick="orderBy('xszt')" id="xszt_order">
										<div class="font-size-12 line-height-18">执行状态</div>
										<div class="font-size-9 line-height-18">Execution State</div>
									</th>
										
									<th class="sorting colwidth-15" onclick="orderBy('bjh')" id="bjh_order" >
										<div class="important">
											<div class="font-size-12 line-height-18 " >部件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</div>
									</th>
									<th class="sorting colwidth-20" onclick="orderBy('xlh')" id="xlh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">部件序列号</div>
											<div class="font-size-9 line-height-18">S/N</div>
										</div>
									</th>	
									<th class="sorting colwidth-13" onclick="orderBy('rwdh')" id="rwdh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">单号</div>
											<div class="font-size-9 line-height-18">Task No.</div>
										</div>
									</th>	
									<th class="colwidth-13">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>				
								</tr>
							</thead>
							<tbody id="list">
							
							</tbody>
							
						</table>
						
					</div>
					<div class='clearfix'></div>
					<!-- 20170518 修改-->
					<div id='zyxx_div' style='display:none;margin-top:10px;overflow:auto;'>
						<%@ include file="/WEB-INF/views/productionplan/plantask/plantask_managedetail.jsp" %>
					</div>
				</div>
					<div class="clearfix"></div>
			</div>
	
	<!-- END CONTENT -->
 
	<!-----alert提示框Start--------->
	<div class="modal fade" id="alertModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body" id="alertModalText"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-----alert提示框End--------->
	<div class="modal fade" id="remarkModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important; ">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="remarkModalBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">监控备注</div>
							<div class="font-size-9 ">Remark</div>
						</div>
						<form id="remarkform" >
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
			            	
			            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<input type="hidden" name="id" id="id">
							</div> 
							<div class="clearfix"></div> 
			            	<label id="rwdh_label" class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red"></span>任务单号</div>
								<div class="font-size-9 line-height-18">Scheduled No</div>
							</label>
							<label id="gdh_label"  class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red"></span>工单编号</div>
								<div class="font-size-9 line-height-18">W/O No</div>
							</label>
							<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group ">
								<input class="form-control" id="rwdh" name="rwdh"  readonly="readonly" ></input>
							</div>
			            	<div class="clearfix"></div>
			            	<label class="col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>监控备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</label>
							<div class="col-lg-9 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group ">
								<textarea class="form-control" id="bz" name="bz" placeholder='长度最大为300'   maxlength="300"></textarea>
							</div>
							    
					     		<div class="clearfix"></div>
					     		<div class="text-right margin-top-10 padding-buttom-10 ">
                               <button  id='remarkbtn' class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal"><div
										class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
                     	      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
                    			 </div><br/>
						 	 </div>
						 </div> 
						 </form>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="alertModalZdjs" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:60%!important; ">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">指定结束</div>
							<div class="font-size-9 ">Specified End</div>
						</div>
						<form id="form" >
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
			            	
			            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<input type="hidden" name="id" id="id">
							</div> 
							<div class="clearfix"></div> 
			            	<label id="rwdh_label" class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red"></span>任务单号</div>
								<div class="font-size-9 line-height-18">Scheduled No</div>
							</label>
							<label id="gdh_label"  class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red"></span>工单编号</div>
								<div class="font-size-9 line-height-18">W/O No</div>
							</label>
							<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group ">
								<input class="form-control" id="rwdh" name="rwdh"  readonly="readonly" ></input>
							</div>
			            	<div class="clearfix"></div>
			            	<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>指定结束原因</div>
								<div class="font-size-9 line-height-18">Reason</div>
							</label>
							<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group ">
								<textarea class="form-control" id="zdjsyy" name="zdjsyy" placeholder='长度最大为150'   maxlength="150"></textarea>
							</div>
							
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								 <label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								        <div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">File Desc</div>
								 </label>
								 <div class="col-lg-9 col-sm-8 col-xs-8  padding-left-8 padding-right-0" >
								  <div class="col-lg-11 col-sm-10 col-xs-9  padding-left-0 padding-right-0">							  	
									<input type="text" id="wbwjm" name="wbwjm" placeholder='' class="form-control"  ></div>
									<div id="fileuploader" class="col-lg-1 col-sm-2 col-xs-1 "  style="margin-left: 0;padding-left:6px; display:block;"></div> 
								 </div>
								 
							</div> 
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow: auto;" >
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:600px">
									<thead>
										<tr>
											<th class="colwidth-5"><div class="font-size-12 line-height-18 " >操作</div>
												<div class="font-size-9 line-height-18">Operation</div></th>
											<th class="colwidth-20">
											<div class="font-size-12 line-height-18">文件说明</div>
												<div class="font-size-9 line-height-18">File desc</div>
											</th>
											
											<th class="colwidth-10">
											<div class="font-size-12 line-height-18">文件大小</div>
												<div class="font-size-9 line-height-18">File Size</div>
											</th>
											
											<th  class="colwidth-10"><div class="font-size-12 line-height-18">上传人</div>
												<div class="font-size-9 line-height-18">Operator</div></th>
											<th  class="colwidth-13"><div class="font-size-12 line-height-18">上传时间</div>
												<div class="font-size-9 line-height-18">Operate time</div></th>					
										</tr>
									</thead>
									    <tbody id="filelist">
										</tbody>
								</table>
							</div>
							    
					     		<div class="clearfix"></div>
					     		<div class="text-right margin-top-10 padding-buttom-10 ">
                               <button  id='endbtn' class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal"><div
										class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
                     	      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
                    			 </div><br/>
						 	 </div>
						 </div> 
						 </form>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
 <link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	

	 <script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/plantask/plantask_manage.js"></script> 
</body>
</html>