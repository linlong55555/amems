<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}"/>
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content table-tab-type">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				
				<div class=" pull-right padding-left-0 padding-right-0">	
					<div class="pull-left">
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源</div>
							<div class="font-size-9">Source</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="padding-left-8 pull-left" style="width: 150px; margin-right:5px;">
							   <select class='form-control' id='bflx' onchange="refreshScrapData()">
							   		<option value="">显示全部All</option>
									<option value="1">库内报废</option>
									<option value="2">外场报废</option>
								</select> 
							</div>
						</div>
					</div>
					
					<div class="pull-left">
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="padding-left-8 pull-left" style="width: 150px; margin-right:5px;">
							   <select class='form-control' id='zt' onchange="refreshScrapData()">
									<option value="">显示全部All</option>
									<option value="2">提交</option>
									<option value="3">审批通过</option>
									<option value="11">撤销</option>
								</select> 
							</div>
						</div>
					</div>
					
					<div class="pull-left">
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<div class="padding-left-8 pull-left" style="width: 200px; margin-right:5px;">
							   <select id="dprtcode" class="form-control " onchange="refreshScrapData()">
								   <c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					
					
					<!-- 搜索框start -->
					<div class=" pull-right padding-left-0 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='报废单号/申请人/审批人/报废原因' id="keyword_search" class="form-control ">
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="refreshScrapData()">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
	                        <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" onclick="search();">
								<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
								<div class="pull-left padding-top-5">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
					   		</button>
	                    </div> 
					</div>
					<!-- 搜索框end -->
				
				</div>
				
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">申请日期</div>
							<div class="font-size-9">Apply Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="sqrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">审批日期</div>
							<div class="font-size-9">Approve Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="sprq_search" readonly />
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

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-10 table-tab-type_table" style="overflow-x:auto;">
					<table class="table table-thin table-bordered table-set" id="scrap_table">
						<thead>
							<tr>
								<th class="colwidth-5 fixed-column" >
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('bflx')" id="bflx_order">
									<div class="font-size-12 line-height-18">来源</div>
									<div class="font-size-9 line-height-18">Source</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('bfdh')" id="bfdh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">报废单号</div>
										<div class="font-size-9 line-height-18">Scrap No.</div>
									</div>
								</th>
								<th class="colwidth-20">
									<div class="important">
										<div class="font-size-12 line-height-18">报废原因</div>
										<div class="font-size-9 line-height-18">Scrap Reason</div>
									</div>
								</th>
								<th class="colwidth-5 sorting" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('sqr')" id="sqr_order">
									<div class="important">
										<div class="font-size-12 line-height-18">申请人</div>
										<div class="font-size-9 line-height-18">Proposer</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('bfsj')" id="bfsj_order">
									<div class="font-size-12 line-height-18">申请时间</div>
									<div class="font-size-9 line-height-18">Apply Time</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('spr')" id="spr_order">
									<div class="important">
										<div class="font-size-12 line-height-18">审批人</div>
										<div class="font-size-9 line-height-18">Approver</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('spsj')" id="spsj_order">
									<div class="font-size-12 line-height-18">审批时间</div>
									<div class="font-size-9 line-height-18">Approve Time</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">审批意见</div>
									<div class="font-size-9 line-height-18">Approve Opinion</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('whr')" id="whr_order">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Editor</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('zdsj')" id="zdsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Edit Time</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
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
				<div id="selectData"></div>
				<input type="hidden" id="current_id">
				<div id="scrapLoadingList_div" class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-10" style="display: none;">
                	<ul id="myTab" class="nav nav-tabs">
                    	<li class="active"><a href="#home" data-toggle="tab" aria-expanded="true" id="offer">报废部件清单ScrapLoadingList</a></li>
                      	<li class=""><a href="#profile" data-toggle="tab" aria-expanded="false" id="attachments">附件Attachments</a></li>
                    </ul>
                    <div class="tab-content">
                    	<div class="tab-pane fade active in" id="home">
                      		<div class="clearfix"></div>
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0  tab-second-height" style="overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set"  style="min-width: 900px;">
									<thead id="enquiryHead">
										<tr>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">件号</div>
												<div class="font-size-9 line-height-18">P/N</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">英文名称</div>
												<div class="font-size-9 line-height-18">F.Name</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">中文名称</div>
												<div class="font-size-9 line-height-18">CH.Name</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">报废数量</div>
												<div class="font-size-9 line-height-18">Scrap Num</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">报废仓库</div>
												<div class="font-size-9 line-height-18">Store</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">报废库位</div>
												<div class="font-size-9 line-height-18">Storage Location</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">航材价值</div>
												<div class="font-size-9 line-height-18">Value</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">库存成本(人民币:元)</div>
												<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">仓库</div>
												<div class="font-size-9 line-height-18">Store</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">库位</div>
												<div class="font-size-9 line-height-18">Storage Location</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">管理级别</div>
												<div class="font-size-9 line-height-18">Level</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">序列号</div>
												<div class="font-size-9 line-height-18">S/N</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">批次号</div>
												<div class="font-size-9 line-height-18">B/N</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">厂家件号</div>
												<div class="font-size-9 line-height-18">MP/N</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">航材类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</th>					
										<!-- 	<th class="colwidth-7">
												<div class="font-size-12 line-height-18">库存数量</div>
												<div class="font-size-9 line-height-18">Stock Num</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">可用数量</div>
												<div class="font-size-9 line-height-18">Available Num</div>
											</th> -->
										</tr>
									</thead>
									<tbody id="scrapList">
									
									</tbody>
									
								</table>
							</div>
							<div class="clearfix"></div>
                      	</div>
                      	
                      	<div class="tab-pane fade" id="profile">
                      		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" >
                      		
                      		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 tab-second-height" style="overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width: 750px;">
									<thead>
										<tr>
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
                      	</div>
                  </div>
              </div>
		</div>
	</div>
		
</div>
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script type="text/javascript" src="${ctx}/static/js/thjw/material/scrap/scrap_audit_main.js"></script>
<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>