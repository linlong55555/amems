<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>历史任务清单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<input type="hidden" id="userId" value="" />
<input type="hidden" id="user_jgdm" value="${user.jgdm}"> 	

	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary" >
				<div class="panel-heading  "> 
					<div id="NavigationBar"></div>
				</div>
				<div class="panel-body">
					<div class=" pull-left padding-right-0" >
							<button type="button" onclick="historyOutExcel();"
								class="btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height" >
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</button>
					</div>
					<div class=" pull-right padding-left-0 padding-right-0">
						<div class=" pull-left padding-left-5 padding-right-0" style="width: 220px;">
							 <span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div class="font-size-12">飞机注册号</div>
										<div class="font-size-9">A/C REG</div>
									</span>
									<div class=" col-xs-8 col-sm-8 padding-left-5 padding-right-0">
											<select class='form-control' id='fjzch' name="fjzch">
										    </select>
									</div>
						</div>	
								
							<div class=" pull-left padding-left-5 padding-right-0" style="width: 410px;">
								<input   placeholder="来源单号/任务信息/飞机注册号/部件号/序列号/记录本页码/责任人/备注" id="keyword" class="form-control " type="text">
							</div>
		                    <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button type="button"
										class=" btn btn-primary padding-top-1 padding-bottom-1"
										onclick="search();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
								</button>
								<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight"  onclick="more();">
									<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
									<div class="pull-left padding-top-5">
									 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
									</div>
						   		</button>
		                    </div> 
					</div>
					 
					<!-- 搜索框end -->
				 
					<div class="clearfix"></div>  
				
					<div
					class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  display-none border-cccccc margin-top-10 search-height"
					id="divSearch">
					<div
						class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">来源类型</div>
							<div class="font-size-9">Source Type</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='rwlx' name="rwlx" >
									<option value="" >全部</option>
									<c:forEach items="${planTaskType}" var="item">
									  <option value="${item.id}" >${item.name}</option>
									</c:forEach>
						    </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">来源子类型</div>
							<div class="font-size-9">Source Sub Type</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<select class='form-control' id='rwzlx' name="rwzlx" >
											<option value="" >全部</option>
											<c:forEach items="${planTaskSubType}" var="item">
											  <option value="${item.id}" >${item.name}</option>
											</c:forEach>
								    </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">状态</div>
							<div class="font-size-9">State</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<select class='form-control' id='zt' name="zt" >
											<option value="" >全部</option>
											<option value="9" >指定结束</option>
											<option value="10" >完成</option>
								    </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">完成日期</div>
							<div class="font-size-9">Finished Date</div>
						</span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control date-range-picker" readonly="readonly"
								id="wcrq" />
						</div>
					</div>
					<div class="clearfix"></div> 
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0">
						<span
							class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12">打印日期</div>
							<div class="font-size-9">Print Date</div></span>
						<div
							class="form-group col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control date-range-picker" readonly="readonly"
								id="dysj" />
						</div>
					</div>
					<div class=" col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0" >
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
					<div class="col-lg-2   text-right pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1 pull-right"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>

				</div>
					<div class="clearfix"></div>	
					 <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px; overflow-x: scroll;">
						<table id="plantaskhistory_list_table" class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width: 2200px;"> 
							<thead>
								<tr>
									<th class="colwidth-3" >
										    <div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-18">No</div>
									</th>
									<th    class="sorting colwidth-13" onclick="orderBy('rwdh')" id="rwdh_order">
									    <div class="important">
									    <div class="font-size-12 line-height-18">来源单号</div>
										<div class="font-size-9 line-height-18">Source No.</div>
										</div>
									</th>
									<th  class="colwidth-15"   >
										<div class="important">
										    <div class="font-size-12 line-height-18">任务相关信息</div>
											<div class="font-size-9 line-height-18">Related Info</div>
										</div>
									</th>
									<th   class="sorting colwidth-13" onclick="orderBy('rwlx')" id="rwlx_order">
										<div class="font-size-12 line-height-18">来源类型</div>
										<div class="font-size-9 line-height-18">Source Type</div>
									</th>
									<th  class="colwidth-10"  ><div class="important"><div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div></div>
									</th>
									<th  class="colwidth-15"  ><div ><div class="font-size-12 line-height-18">飞行记录单单号</div>
										<div class="font-size-9 line-height-18">Flight Record Number</div></div>
									</th>
									<th  class="colwidth-10" ><div class="important">
										<div class="font-size-12 line-height-18">记录本页码</div>
										<div class="font-size-9 line-height-18">Flight Record</div></div>
									</th>
									<th   class="sorting colwidth-15" onclick="orderBy('bjh')" id="bjh_order" >
										<div class="important">
											<div class="font-size-12 line-height-18 " >部件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</div>
									</th>
									<th   class="sorting colwidth-20" onclick="orderBy('xlh')" id="xlh_order">
									    <div class="important">
										    <div class="font-size-12 line-height-18">部件序列号</div>
											<div class="font-size-9 line-height-18">S/N</div>
										</div>
									</th>
										
									<th  class="colwidth-13"  >
										<div class="font-size-12 line-height-18">打印人</div>
										<div class="font-size-9 line-height-18">Printer</div>
									</th>
									<th  class="colwidth-13">
										<div class="font-size-12 line-height-18">打印时间</div>
										<div class="font-size-9 line-height-18">Print Time</div>
									</th>
									 <th class="colwidth-7" >
									 	<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">State</div>
									</th>
									 <th   class="colwidth-13" >
									 	<div class="important">
										 	<div class="font-size-12 line-height-18">责任人</div>
											<div class="font-size-9 line-height-18">PIC</div>
										</div>
									</th>
									<th  class="colwidth-10">
										<div class="font-size-12 line-height-18">完成日期</div>
										<div class="font-size-9 line-height-18">Finished Date</div>
									</th>
										
									<th  class="colwidth-20">
										<div class="font-size-12 line-height-18">故障信息</div>
										<div class="font-size-9 line-height-18">Fault Info</div>
									</th>
									<th  class="colwidth-20" >
										<div class="font-size-12 line-height-18">处理措施</div>
										<div class="font-size-9 line-height-18">Treatment Measures</div>
									</th>	
									<th  class="colwidth-20" >
										<div class="font-size-12 line-height-18">计划</div>
										<div class="font-size-9 line-height-18">Plan</div>
									</th>
									<th  class="colwidth-20" >
										<div class="font-size-12 line-height-18">实际</div>
										<div class="font-size-9 line-height-18">Actual</div>
									</th>
									 <th class="colwidth-13">
										<div class="font-size-12 line-height-18">计划工时</div>
										<div class="font-size-9 line-height-18">Plan Hrs</div>
									</th>	
									<th class="colwidth-13">
										<div class="font-size-12 line-height-18">实际工时</div>
										<div class="font-size-9 line-height-18">Actual Hrs</div>
									</th>
									
									<th class="colwidth-30"><div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></div>
									</th>
									<th class="colwidth-30 downward cursor-pointer" onclick="vieworhideFj()" name="fj">
										<div class="font-size-12 line-height-18">附件</div>
										<div class="font-size-9 line-height-18">Attachments</div>
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
					<div class="col-xs-12 text-center padding-right-0 page-height" id="pagination">
					</div>
					
					<div class="clearfix"></div>
				</div>
				
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
	
	<div class="modal fade" id="alertModalZdjsReson" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:800px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18">指定结束</div>
							<div class="font-size-9 ">Specified End</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12">
			            	
			            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">工单编号</div>
									<div class="font-size-9">W/O No.</div>
									</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="gdbh2" disabled="disabled"/>
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            	<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-bottom-10 ">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">结束人</div>
									<div class="font-size-9">End Person</div>
									</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="zdjsr" disabled="disabled"/>
								</div>
							</div> 
							<div class="clearfix"></div>
			            	<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-bottom-10 ">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">结束时间</div>
									<div class="font-size-9">End Time</div>
									</label>
								<div class="col-lg-10 col-sm-6 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="zdjsrq" disabled="disabled"/>
								</div>
							</div> 
							<div class="clearfix"></div>
			            	<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">指定结束原因</div>
								<div class="font-size-9 line-height-18">Reason</div>
							</label>
							<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="zdjsyy2"  disabled="disabled"></textarea>
							</div>
							<div class="clearfix"></div>
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 padding-top-10" >
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
									<thead>
										<tr>
											<th class="colwidth-5"><div class="font-size-12 line-height-18 " >操作</div>
												<div class="font-size-9 line-height-18">Operation</div></th>
											<th colwidth-20>
											<div class="font-size-12 line-height-18">文件说明</div>
												<div class="font-size-9 line-height-18">File desc</div>
											</th>
											
											<th colwidth-10>
											<div class="font-size-12 line-height-18">文件大小</div>
												<div class="font-size-9 line-height-18">File Size</div>
											</th>
											
											
											<th colwidth-10 ><div class="font-size-12 line-height-18">上传人</div>
												<div class="font-size-9 line-height-18">Operator</div></th>
											<th  colwidth-13><div class="font-size-12 line-height-18">上传时间</div>
												<div class="font-size-9 line-height-18">Operate time</div></th>					
										</tr>
									</thead>
									    <tbody id="filelist">
										</tbody>
								</table>
							</div>
							
							    
					     		<div class="clearfix"></div>
					     		<div class="text-right margin-top-10 padding-buttom-10 ">
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
	
</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%>
    <script src="${ctx}/static/js/thjw/common/preview.js"></script> 
	 <script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/plantask/plantask_history.js"></script> 
	 <script src="${ctx}/static/js/thjw/common/preview.js"></script>
	 <!--  
	 
	 
	  -->
</body>
</html>