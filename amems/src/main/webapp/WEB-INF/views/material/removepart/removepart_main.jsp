<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content ">
		<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
			
			<div class="panel-body padding-bottom-0">
					<!--------搜索框start-------->
				<div  class='searchContent row-height' >
				<div class=" pull-right padding-left-0 padding-right-0 form-group" >
		
					<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
						<input placeholder="件号/中英文/厂家件号" id="keyword_search" class="form-control " type="text">
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
				<!------------搜索框end------->
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">航材类型</div>
							<div class="font-size-9">Airmaterial type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='hclx'>
								<option value="">显示全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
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
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: scroll;">
					<table id="cxj" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:1500px">
						<thead>
							<tr>
								<th class="fixed-column colwidth-8" style="vertical-align: middle;">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='fixed-column colwidth-5'  >
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class='fixed-column sorting colwidth-15'  
										name="column_bjh" onclick="orderBy('bjh',this)" ><div class="important"><div
											class="font-size-12 line-height-18">件号</div></div>
										<div class="font-size-9 line-height-18">P/N</div></th>
									<th class='sorting colwidth-20' 
										name="column_ywms" onclick="orderBy('ywms',this)" ><div class="important"><div
											class="font-size-12 line-height-18">英文名称</div></div>
										<div class="font-size-9 line-height-18">F.Name</div></th>
									<th class='sorting colwidth-20' 
										name="column_zwms" onclick="orderBy('zwms',this)" ><div class="important"><div
											class="font-size-12 line-height-18">中文名称</div></div>
										<div class="font-size-9 line-height-18">CH.Name</div></th>
								
									<th  class="sorting colwidth-8"
										name="column_cjjh" onclick="orderBy('cjjh',this)" ><div class="important"><div
											class="font-size-12 line-height-18">厂家件号</div></div>
										<div class="font-size-9 line-height-18">MP/N</div></th>
									<th  class="sorting colwidth-10"
										name="column_hclx" onclick="orderBy('hclx',this)" ><div
											class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Airmaterial type</div></th>
									<th  class="sorting colwidth-10"
										name="column_gljb" onclick="orderBy('gljb',this)" ><div
											class="font-size-12 line-height-18">航材管理级别</div>
										<div class="font-size-9 line-height-18">Management level</div></th>
									<th class="colwidth-15" ><div
											class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div></th>
									<th class="colwidth-15" ><div
											class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">B/N</div></th>
									<th   class="sorting colwidth-8"
										name="column_shzh" onclick="orderBy('shzh',this)"><div
											class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Certificate</div></th>
									<th   class="sorting colwidth-5"
										name="column_kcsl" onclick="orderBy('kcsl',this)"><div
											class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
									<th   class="colwidth-5"><div
											class="font-size-12 line-height-18">可用数量</div>
										<div class="font-size-9 line-height-18">Num</div></th>
									<th  class="sorting colwidth-5"
										name="column_jldw" onclick="orderBy('jldw',this)" ><div
											class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div></th>
										<th   class="sorting colwidth-7"
										name="column_hjsm" onclick="orderBy('hjsm',this)"><div
											class="font-size-12 line-height-18">货架寿命</div>
										<div class="font-size-9 line-height-18">Shelf-Life</div></th>
									<th class="colwidth-13"><div
											class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div></th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination">
					
				</div>
				<div class="clearfix"></div>
		</div>
	</div>
	
	
	<!-- start报废提示框 -->
	<div class="modal fade" id="alertModalScrap" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">报废</div>
							<div class="font-size-9 ">Scrap</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
						 	<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>仓库</div>
									<div class="font-size-9">Store</div>
								</span>
								<input type="hidden" name="storageckid" id="storageckid">
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
											<select class='form-control' id='bfck' name="bfck" onchange="onchange1();">
											
											</select>
								</div>
							</div> 
			            	<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>库位号</div>
									<div class="font-size-9">Storage Location</div>
								</span>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
									<select class='form-control' id='bfkw' name="bfkw" >
									</select>
								</div>
							</div>
			            	<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<span class="pull-left col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>报废数量</div>
									<div class="font-size-9">Scrap Num</div>
								</span>
								<div class="col-xs-9 col-sm-9 col-lg-9 padding-left-8 padding-right-0">
									<input type="hidden" name="scrapid" id="scrapid">
									<input type="hidden" id="baofsl"   />
									<input type="text" class="form-control " id="scrapkcsl" name="scrapkcsl"  onkeyup="clearNoNum(this)" placeholder="长度最大为10"   maxlength="10" />
								</div>
							</div> 
			            	<div class="clearfix"></div>
			            	<span class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>报废原因</div>
								<div class="font-size-9 line-height-18">Scrap Cause</div>
							</span>
							<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="bfyy" name="bfyy" placeholder='长度最大为300'   maxlength="300"></textarea>
							</div>
					     	<div class="clearfix"></div>
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
					     	<button type="button" class="pull-right btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
								</button>&nbsp;&nbsp;
								<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="sbScrap()">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
		                   		</button>&nbsp;&nbsp;
                     			
                    		</div><br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end报废提示框 -->
	
	<!-- start入库提示框 -->
	<div class="modal fade" id="alertModalStorage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">入库</div>
							<div class="font-size-9 ">InStore</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
						 	<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<span class="pull-left col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>入库日期</div>
									<div class="font-size-9">InStore Date</div>
								</span>
								<div class="col-xs-9 col-sm-9 col-lg-9 padding-left-8 padding-right-0">
									<input type="hidden" name="storageid" id="storageid">
									<input class="form-control date-picker" id="rksj" name="rksj" readonly="readonly"
										data-date-format="yyyy-mm-dd" type="text" />
								</div>
							</div> 
							 	<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-0 padding-right-0 margin-bottom-10 ">
								<span class="pull-left col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>仓库</div>
									<div class="font-size-9">Store</div>
								</span>
								<input type="hidden" name="storageckid" id="storageckid">
								<div class="col-xs-9 col-sm-9 col-lg-9 padding-left-8 padding-right-0">
											<select class='form-control' id='ckid' name="ckid" onchange="onchange2();">
												
											</select>
								</div>
							</div> 
			            	<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-0 padding-right-0 margin-bottom-10 ">
								<span class="pull-left col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>库位号</div>
									<div class="font-size-9">Storage Location</div>
								</span>
								<div class="col-xs-9 col-sm-9 col-lg-9 padding-left-8 padding-right-0">
											<select class='form-control' id='kwid' name="kwid" >
												
											</select>
								</div>
							</div> 
							<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 margin-top-0 padding-right-0 margin-bottom-0 ">
								<span class="pull-left  col-xs-3 col-sm-3 col-lg-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>入库数量</div>
									<div class="font-size-9">InStore Num</div>
								</span>
								<div class="col-xs-9 col-sm-9 col-lg-9 padding-left-8 padding-right-0">
									<input type="hidden" id="rukusl" />
									<input type="text" class="form-control " id="storagekcsl" name="storagekcsl" onkeyup="clearNoNum(this)" placeholder="长度最大为10"   maxlength="10" />
								</div>
							</div> 
					     	<div class="clearfix"></div>
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
					     		<button type="button" class="pull-right btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
								</button>&nbsp;&nbsp;
								<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="sbStorage()">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
		                   		</button>&nbsp;&nbsp;
                     		
                    		</div><br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end入库提示框 -->
	
	<!-- start送修提示框 -->
	<div class="modal fade" id="alertModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:450px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">送待修库</div>
							<div class="font-size-9 ">InStore</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
						 	<div class="col-xs-12 col-sm-12 col-lg-12 padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>入库日期</div>
									<div class="font-size-9">InStore Date</div>
								</span>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
									<input type="hidden" name="storageid1" id="storageid1">
									<input class="form-control date-picker" id="rksj1" name="rksj1" readonly="readonly"
										data-date-format="yyyy-mm-dd" type="text" />
								</div>
							</div> 
						 	<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>仓库</div>
									<div class="font-size-9">Store</div>
								</span>
								<input type="hidden" name="storageckid" id="storageckid">
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
											<select class='form-control' id='ckh' name="ckh" onchange="onchange1();">
											
											</select>
								</div>
							</div> 
			            	<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>库位号</div>
									<div class="font-size-9">Storage Location</div>
								</span>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
											<select class='form-control' id='kwh' name="kwh" >
											</select>
								</div>
							</div> 
							<div class="col-xs-12 col-sm-12 col-lg-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>入库数量</div>
									<div class="font-size-9">InStore Num</div>
								</span>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="storagekcsl1" name="storagekcsl1" onkeyup="clearNoNum(this)" placeholder="长度最大为10"  disabled="disabled" maxlength="10" />
								</div>
							</div> 
					     	<div class="clearfix"></div>
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
					     		<button type="button" class="pull-right btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
								</button>&nbsp;&nbsp;
								<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="sbStorage1()">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
		                   		</button>&nbsp;&nbsp;
                     		
                    		</div><br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end送修提示框 -->
	
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/removepart/removepart_main.js"></script>
</body>
</html>