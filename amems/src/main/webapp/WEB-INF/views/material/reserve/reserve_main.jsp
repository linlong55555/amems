<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>航材提订</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
var bjh = '${bjh}';
var dprtcode = '${dprtcode}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="type" value="${type}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content table-tab-type">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<div class='searchContent'>
				<c:if test="${type == 'manage'}">
					<div class="col-xs-2 col-md-3 padding-left-0">
						<a href="javascript:void(0);" onclick="add()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"  permissioncode="aerialmaterial:reserve:manage:01" >
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a> 
					</div>
				</c:if>
				<!-- 搜索框start -->
				<div class="pull-right padding-left-0 padding-right-0 row-height">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='提订单号/提订名称' id="keyword_search" class="form-control ">
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
				<div class='clearfix'></div>
				<!-- 搜索框end -->
				
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">提订人</div>
							<div class="font-size-9">Operator</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " id="sqrrealname_search" value="${erayFns:escapeStr(username)}" maxlength="10"/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">提订日期</div>
							<div class="font-size-9">Operate date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="sqrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">提订类型</div>
							<div class="font-size-9">Order Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jjcd_search'>
								<option value="" selected="selected" >显示全部All</option>
								<c:forEach items="${urgencyEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">提订状态</div>
							<div class="font-size-9">State</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='zt_search'>
								<option value="" selected="selected" >显示全部All</option>
								<c:forEach items="${reserveStatusEnum}" var="item">
									<c:choose>
										<c:when test="${item.id == 9}">
											<option value="${item.id}" >指定结束</option>
										</c:when>
										<c:otherwise>
											<c:if test="${item.id != 8 && item.id != 4 && item.id != 10}">
												<option value="${item.id}" >${item.name}</option>
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						    </select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">件号</div>
							<div class="font-size-9">P/N</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " id="bjh" maxlength="10"/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control" onchange="initMonitorsettings()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
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
				
				<div class="clearfix"></div>
				<div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-10 table-tab-type_table" style="overflow-x: auto;">
					<table id="reserve_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1580px;">
						<thead>
							<tr>
								<th class="fixed-column colwidth-5" >
									<div class="font-size-12 line-height-18 " >选择</div>
									<div class="font-size-9 line-height-18">Choice</div>
								</th>
								<th class="fixed-column colwidth-7" >
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-3" >
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('sqdh')" id="sqdh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">提订单号</div>
										<div class="font-size-9 line-height-18">Order No.</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('tdmc')" id="tdmc_order">
									<div class="important">
										<div class="font-size-12 line-height-18">提订名称</div>
										<div class="font-size-9 line-height-18">Order Name</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('jjcd')" id="jjcd_order">
									<div class="font-size-12 line-height-18">提订类型</div>
									<div class="font-size-9 line-height-18">Order Type</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">提订状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
								<th th_class="cont-exp1" td_class="reserveDisplayFile" table_id="reserve_main_table" class="cont-exp1 downward colwidth-15" onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)" >
									<div class="font-size-12 line-height-18">关联工单</div>
									<div class="font-size-9 line-height-18">W/O</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('sqyy')" id="sqyy_order">
									<div class="font-size-12 line-height-18">提订原因</div>
									<div class="font-size-9 line-height-18">Cause</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('sqrid')" id="sqrid_order">
									<div class="font-size-12 line-height-18">提订人</div>
									<div class="font-size-9 line-height-18">Operator</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('sqsj')" id="sqsj_order">
									<div class="font-size-12 line-height-18">提订时间</div>
									<div class="font-size-9 line-height-18">Operate time</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('sprid')" id="sprid_order">
									<div class="font-size-12 line-height-18">审批人</div>
									<div class="font-size-9 line-height-18">Approval by</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('spsj')" id="spsj_order">
									<div class="font-size-12 line-height-18">审批时间</div>
									<div class="font-size-9 line-height-18">Approval time</div>
								</th>
								<!-- <th style="width:5%" class="sorting" onclick="orderBy('spyj')" id="spyj_order">
									<div class="font-size-12 line-height-18">审批意见</div>
									<div class="font-size-9 line-height-18">Opinion</div>
								</th> -->
								<th class="colwidth-5">
									<div class="font-size-12 line-height-18">留言</div>
									<div class="font-size-9 line-height-18">Message</div>
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
				</div>
				<div id='fileDiv' class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-10 " style='display:none;'>
                	<ul id="myTab" class="nav nav-tabs">
                    	<li class="active"><a href="#home" data-toggle="tab" aria-expanded="true">提订航材Aircraft</a></li>
                      	<li class=""><a href="#Dropdown" data-toggle="tab" aria-expanded="false">留言Message</a></li>
                      	<li class=""><a href="#profile" data-toggle="tab" aria-expanded="false">附件Attachments</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                    	<div class="tab-pane fade active in" id="home">
                      
                      		<!-- <div class="tab-search-content" style="border: 1px solid red;"> -->
                      		
                      		<div class="tab-search-content" >
                      
							<!-- 搜索框start -->
							<div class=" pull-right padding-left-0 padding-right-0">
								<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
									<input type="text" placeholder='提订单号/件号/中英文' id="keyword2_search" class="form-control ">
								</div>
			                    <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision2();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                    </div> 
							</div>
							<!-- 搜索框end -->
							
							<div class="col-xs-4 pull-right padding-right-1">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">航材进度</div>
									<div class="font-size-9">State</div>
								</span>
								<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
									<select class='form-control' id='materialProgress_search'>
										<option value="" selected="selected" >显示全部All</option>
										<c:forEach items="${materialProgressEnum}" var="item">
										  	<option value="${item.name}" >${item.name}</option>
										</c:forEach>
								    </select>
								</div>
							</div>
							<div class="clearfix"></div>
							</div>
				
                      		<div class="clearfix"></div>
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 tab-table-search-height" style="margin-top: 10px;overflow-x: auto;">
								<table id="reserve-detail-list" class="table table-thin table-bordered table-set"  style="min-width: 2350px;">
									<thead>
										<tr>
											<th class="colwidth-5">
												<div class="font-size-12 line-height-18">操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class="colwidth-10">
												<div class="important">
													<div class="font-size-12 line-height-18">提订单号</div>
													<div class="font-size-9 line-height-18">Order No.</div>
												</div>
											</th>
											<th class="colwidth-15">
												<div class="important">
													<div class="font-size-12 line-height-18">件号</div>
													<div class="font-size-9 line-height-18">P/N</div>
												</div>
											</th>
											<th class="colwidth-25">
												<div class="important">
													<div class="font-size-12 line-height-18">英文名称</div>
													<div class="font-size-9 line-height-18">F.Name</div>
												</div>
											</th>
											<th class="colwidth-20">
												<div class="important">
													<div class="font-size-12 line-height-18">中文名称</div>
													<div class="font-size-9 line-height-18">CH.Name</div>
												</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">厂家件号</div>
												<div class="font-size-9 line-height-18">MP/N</div>
											</th>
											<th class="colwidth-30">
												<div class="font-size-12 line-height-18">适用机型</div>
												<div class="font-size-9 line-height-18">Model</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 line-height-18">ATA章节号</div>
												<div class="font-size-9 line-height-18">ATA</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">航材类型</div>
												<div class="font-size-9 line-height-18">Airmaterial type</div>
											</th>
											<th class="colwidth-7" >
												<div class="font-size-12 line-height-18">进度状态</div>
												<div class="font-size-9 line-height-18">State</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">要求时限</div>
												<div class="font-size-9 line-height-18">Requested date</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">剩余天数</div>
												<div class="font-size-9 line-height-18">Surplus</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">预计到货日期</div>
												<div class="font-size-9 line-height-18">Arrival date</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">询价</div>
												<div class="font-size-9 line-height-18">Enquiry</div>
											</th>
											<th th_class="cont-exp2" td_class="contractDisplay" table_id="" class="cont-exp2 downward colwidth-13" onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
												<div class="font-size-12 line-height-18">合同号</div>
												<div class="font-size-9 line-height-18">Contract</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">提订数量</div>
												<div class="font-size-9 line-height-18">Num</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">审核数量</div>
												<div class="font-size-9 line-height-18">Audit Num</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">合同数量</div>
												<div class="font-size-9 line-height-18">Contract Num</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">到货数量</div>
												<div class="font-size-9 line-height-18">Arrival Num</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">入库数量</div>
												<div class="font-size-9 line-height-18">InStock Num</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">单位</div>
												<div class="font-size-9 line-height-18">Unit</div>
											</th>
										</tr>
									</thead>
									<tbody id="reserveDetailList">
									
									</tbody>
									
								</table>
							</div>
                      	</div>
                      	
                      	<div class="tab-pane fade" id="Dropdown">
                      		
                      		<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0 tab-second-height" style="margin-top: 10px;overflow-x: auto;">
								<!-- start:table -->
								<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width: 1050px;">
									<thead>
								   		<tr>
											<th class="colwidth-3">
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 notwrap">提订单号</div>
												<div class="font-size-9 notwrap">Order No.</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">留言时间</div>
												<div class="font-size-9 notwrap">Message time</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">留言人</div>
												<div class="font-size-9 notwrap">Message by</div>
											</th>
											<th class="colwidth-30">
												<div class="font-size-12 notwrap">留言内容</div>
												<div class="font-size-9 notwrap">Message</div>
											</th>
											<th class="colwidth-30">
												<div class="font-size-12 notwrap">提醒人</div>
												<div class="font-size-9 notwrap">Remind by</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="messageListViewTable">
									
									</tbody>
								</table>
								<!-- end:table -->
								<div class="clearfix"></div>
							</div>
                   
                      	</div>
                      	
                      	<div class="tab-pane fade" id="profile">
                      		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 tab-second-height" style="margin-top: 10px;overflow-x: auto;">
								<table class="table table-thin table-bordered table-striped table-hover text-left table-set" style="min-width: 850px;">
									<thead>
										<tr>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">提订单号</div>
												<div class="font-size-9 line-height-18">Order No.</div>
											</th>
											<th class="colwidth-30">
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
												<div class="font-size-9 line-height-18">Operate time</div>
											</th>
										</tr>
									</thead>
									<tbody id="profileList"></tbody>
								</table>
							</div>
                      	</div>
                  </div>
              </div>
		</div>
	</div>
	
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/material/reserve/reserve_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/message_list_view.jsp"%><!-------留言对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/materialEnquiry.jsp"%><!-- 航材询价列表 -->
	<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>