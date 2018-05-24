<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>询价管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content table-tab-type" id="enquiry_main">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				<div class='searchContent' >
					<button type="button" onclick="enquiryOutExcel();" id="export"
								class="btn btn-primary padding-top-1 padding-bottom-1 padding-right-5 ">
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</button>	
				<div class=" pull-right padding-left-0 padding-right-0">	
					
					<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">询价类型</div>
							<div class="font-size-9">Enquiry Type</div>
						</span>
						<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='djlx_search' onchange="changeType()">
								<option value="" selected="selected" >显示全部All</option>
								<c:forEach items="${formTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>	
					
					
					<!-- 搜索框start -->
					<div class=" pull-right padding-left-10 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='单号/件号/中英文/厂家件号/序列号/申请人' id="keyword_search" class="form-control ">
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
					<!-- 搜索框end -->
				
				</div>
				
				<div class='clearfix'></div>
				
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc" id="divSearch">
					
					<!-- <div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">申请人</div>
							<div class="font-size-9">Applicant</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " id="sqrname_search" />
						</div>
					</div> -->
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">申请日期</div>
							<div class="font-size-9">Application Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="sqrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">要求时限</div>
							<div class="font-size-9">Requested date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="yqqx_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">询价状态</div>
							<div class="font-size-9">Enquiry State</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='xjzt_search'>
								<option value="" selected="selected" >显示全部All</option>
								<c:forEach items="${enquiryStatusEnum}" var="item">
									<c:if test="${item.id != 0}">
										<option value="${item.id}" >${item.name}</option>
									</c:if>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control ">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
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
				</div>
				
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-tab-type_table" style="margin-top: 10px;overflow-x: auto;">
					<table class="table table-thin table-bordered table-set" style="min-width: 2000px;">
						<thead>
							<tr>
								<th class="colwidth-3" >
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('DJLX')" id="DJLX_order">
									<div class="font-size-12 line-height-18">询价类型</div>
									<div class="font-size-9 line-height-18">Enquiry Type</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('SQDH')" id="SQDH_order">
									<div class="important">
										<div class="font-size-12 line-height-18">提订/送修单号</div>
										<div class="font-size-9 line-height-18">Order No.</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('BJH')" id="BJH_order">
									<div class="important">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
								<th class="colwidth-25 sorting" onclick="orderBy('YWMS')" id="YWMS_order">
									<div class="important">
										<div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div>
									</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('ZWMS')" id="ZWMS_order">
									<div class="important">
										<div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('CJJH')" id="CJJH_order">
									<div class="important">
										<div class="font-size-12 line-height-18">厂家件号</div>
										<div class="font-size-9 line-height-18">MP/N</div>
									</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('SN')" id="SN_order">
									<div class="important">
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('SL')" id="SL_order">
									<div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Num</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('JLDW')" id="JLDW_order">
									<div class="font-size-12 line-height-18">单位</div>
									<div class="font-size-9 line-height-18">Unit</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('XJZT')" id="XJZT_order">
									<div class="font-size-12 line-height-18">询价状态</div>
									<div class="font-size-9 line-height-18">Enquiry State</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('YQQX')" id="YQQX_order">
									<div class="font-size-12 line-height-18">要求时限</div>
									<div class="font-size-9 line-height-18">Requested date</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('BJCLFMAX')" id="BJCLFMAX_order">
									<div class="font-size-12 line-height-18">总价(人民币：元)</div>
									<div class="font-size-9 line-height-18">Total(RMB:YUAN)</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('SQRUSERNAME')" id="SQRUSERNAME_order">
									<div class="important">
										<div class="font-size-12 line-height-18">申请人</div>
										<div class="font-size-9 line-height-18">Applicant</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('SQSJ')" id="SQSJ_order">
									<div class="font-size-12 line-height-18">申请时间</div>
									<div class="font-size-9 line-height-18">Application Date</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('DPRTCODE')" id="DPRTCODE_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class="clearfix"></div>
				<div id="selectRow"></div>
				<div id='tabDiv' class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-10" >
                	<ul id="myTab" class="nav nav-tabs">
                    	<li class="active"><a href="#home" data-toggle="tab" aria-expanded="true" id="offer">报价Offer</a></li>
                      	<li class=""><a href="#profile" data-toggle="tab" aria-expanded="false" id="attachments">附件Attachments</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                    	<div class="tab-pane fade active in" id="home">
                      		
		            	<div class="clearfix"></div>
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 tab-height-one" style="overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set"  style="min-width: 900px;">
									<thead id="enquiryHead">
										<tr>
											<th style="width: 50px;">
												<button class="line6 checkPermission" permissioncode="aerialmaterial:enquiry:main:01" onclick="openSupplierWinAdd()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th >
												<div class="font-size-12 line-height-18">供应商</div>
												<div class="font-size-9 line-height-18">Supplier</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">单价(人民币：元)</div>
												<div class="font-size-9 line-height-18">Price(RMB:YUAN)</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">材料费(人民币：元)</div>
												<div class="font-size-9 line-height-18">Material cost(RMB:YUAN)</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">工时费(人民币：元)</div>
												<div class="font-size-9 line-height-18">Time cost(RMB:YUAN)</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">其它费(人民币：元)</div>
												<div class="font-size-9 line-height-18">Other cost(RMB:YUAN)</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">总价(人民币：元)</div>
												<div class="font-size-9 line-height-18">Total(RMB:YUAN)</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">预计到货日期</div>
												<div class="font-size-9 line-height-18">Expected arrival</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">询价人</div>
												<div class="font-size-9 line-height-18">Enquiry</div>
											</th>
										</tr>
									</thead>
									<tbody id="enquiryList">
									
									</tbody>
									
								</table>
							</div>
							<div class="clearfix"></div>
                      	</div>
                      	
                      	<div class="tab-pane fade" id="profile">
                      		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" >
                      		
                      		
                      			<div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
									<label class="col-xs-4 col-sm-3 col-xs-12 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
										<div class="font-size-9 line-height-18">File Desc</div>
									</label>
									<div class="col-lg-6 col-sm-6 col-xs-12 padding-left-8 padding-right-0" >
										<input type="text" id="wbwjm" name="wbwjm" placeholder='' class="form-control "  >
									</div>
									<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-12 checkPermission" permissioncode="aerialmaterial:enquiry:main:02" style="margin-left: 0;padding-left: 0"></div>
								</div>
								<div class="clearfix"></div>
                      		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 tab-height-two" style="overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width: 750px;">
									<thead>
										<tr>
											<th class="colwidth-5" >
												<div class="font-size-12 line-height-18" >操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">文件说明</div>
												<div class="font-size-9 line-height-18">File desc</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">文件大小</div>
												<div class="font-size-9 line-height-18">File size</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">上传人</div>
												<div class="font-size-9 line-height-18">Operator</div></th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">上传时间</div>
												<div class="font-size-9 line-height-18">Operate Time</div>
											</th>
										</tr>
									</thead>
								    <tbody id="profileList">
										 
									</tbody>
								</table>
								</div>
							</div>
							<div class="clearfix"></div>
                      	</div>
                      	<div class="tab-height-search-last" >
                      		<div class="text-right margin-bottom-10 ">					
			                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode="aerialmaterial:enquiry:main:01" onclick="javascript:save()">
			                    	<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
			            	</div>
			            	<div class="clearfix"></div>
		            	</div>
                  </div>
              </div>
		</div>
	</div>
		
	<!-------供应商对话框 Start-------->
	<div class="modal fade" id="alertModalSupplierWinAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:80%;">
			<div class="modal-content">	
				<div class="modal-body padding-bottom-0" id="alertModalSupplierBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">供应商列表</div>
							<div class="font-size-9 ">Supplier List</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
						
			            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
			            	
			            		<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">	
					
									<!-- 搜索框start -->
									<div class=" pull-right padding-left-0 padding-right-0">
										<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
											<input type="text" placeholder='供应商/供应商编码' id="keyword_supplier_search" class="form-control ">
										</div>
					                    <div class=" pull-right padding-left-5 padding-right-0 ">   
											<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchSupplierWinAdd();">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
					                   		</button>
					                    </div> 
									</div>
									<!-- 搜索框end -->
								</div>
			            	<div class="clearfix"></div>
							<!-- start:table -->
							<div class="margin-top-10 ">
								<div style="overflow-x: auto;">
									<table class="table table-bordered table-striped table-hover table-set" style="min-width: 1000px;">
										<thead id="supplierHead">
									   		<tr>
												<th class="colwidth-5" style='text-align:center;vertical-align:middle;'>
													<a href="#" onclick="SelectUtil.performSelectAll('supplierWinAdd')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
													<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('supplierWinAdd')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
													<!-- <input id="selectAllId" type='checkbox' onclick="SelectUtil.selectAll('selectAllId','supplierWinAdd')" /> -->
												</th>
												<th class="colwidth-3">
													<div class="font-size-12 notwrap">序号</div>
													<div class="font-size-9 notwrap">No.</div>
												</th>
												<th class="colwidth-10">
													<div class="important">
														<div class="font-size-12 notwrap">供应商</div>
														<div class="font-size-9 notwrap">Supplier</div>
													</div>
												</th>
												<th class="colwidth-15">
													<div class="important">
														<div class="font-size-12 notwrap">供应商编码</div>
														<div class="font-size-9 notwrap">Supplier No.</div>
													</div>
												</th>
												<th class="colwidth-10">
													<div class="font-size-12 notwrap">单价(人民币：元)</div>
													<div class="font-size-9 notwrap">Price(RMB:YUAN)</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 notwrap">材料费(人民币：元)</div>
													<div class="font-size-9 notwrap">Material cost(RMB:YUAN)</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 notwrap">工时费(人民币：元)</div>
													<div class="font-size-9 notwrap">Time cost(RMB:YUAN)</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 notwrap">其它费(人民币：元)</div>
													<div class="font-size-9 notwrap">Other cost(RMB:YUAN)</div>
												</th>
												<th class="colwidth-9">
													<div class="font-size-12 notwrap">价格生效日期</div>
													<div class="font-size-9 notwrap">Effective date</div>
												</th>
												<th class="colwidth-9">
													<div class="font-size-12 notwrap">价格失效日期</div>
													<div class="font-size-9 notwrap">Expiration date</div>
												</th>
									 		 </tr>
										</thead>
										<tbody id="supplierWinAdd">
										
										</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="supplierWinAddPagination"></div>
								</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
			                		<button type="button" onclick="ManuallyAdd()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">手工新增</div>
										<div class="font-size-9">Manually Add</div>
									</button>
									<button type="button" onclick="setData()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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
	</div>
	<!-------供应商对话框 End-------->
</div>
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script type="text/javascript" src="${ctx}/static/js/thjw/material/enquiry/enquiry_main.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/js/thjw/common/auth_height_util.js"></script> --%>
</body>
</html>