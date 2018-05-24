<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>航材检验</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
<input maxlength="20" type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<div class="page-content ">
			<!-- from start -->
	<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			<input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
				 <!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0 row-height" style="width: 250px;">
						<input type="text" placeholder='检验入库单号/中英文/件号/序列批次号/ID' id="keyword_search" class="form-control ">
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
				
				
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc search-height" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">航材类型</div>
							<div class="font-size-9">Airmaterial type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='hclx_search'>
								<option value="" selected="true" >查看全部All</option>
								<c:forEach items="${materialTypeEnum}" var="type">
									<option value="${type.id}">${type.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">管理级别</div>
							<div class="font-size-9">Management level</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='gljb_search'>
								<option value="" selected="true" >查看全部All</option>
								<c:forEach items="${supervisoryLevelEnum}" var="type">
									<option value="${type.id}">${type.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">航材来源</div>
							<div class="font-size-9">Aircraft Source</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='hcly_search'>
								<option value="" selected="true" >查看全部All</option>
								<c:forEach items="${materialSrouceEnum}" var="type">
									<option value="${type.id}">${type.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">检验结果</div>
							<div class="font-size-9">Inspection Result</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id='jyjg_search'>
								<option value="" selected="true" >查看全部All</option>
								<c:forEach items="${materialCheckResultEnum}" var="type">
									<option value="${type.id}">${type.name}</option>
								</c:forEach>
						    </select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">检验日期</div>
							<div class="font-size-9">Inspection Date</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
						  <input type="text" class="form-control " name="date-range-picker" id="jyrq_search" readonly />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
						       <select  id="dprtcode_search"  class="form-control">
					                 <c:forEach items="${accessDepartment}" var="type">
									   <option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
							     	</c:forEach>
							   </select>
						</div>
					</div>

					<div class="col-xs-12 col-sm-1 pull-right text-right padding-right-0" style="margin-bottom: 10px;">

						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1 "
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
			</div>	
				<div class="clearfix"></div>

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px;overflow-x:scroll ;">
						<table id="quality_check_list_table"
							class="table table-thin table-bordered table-striped table-hover table-set"
								style=" min-width: 2500px !important;"
							>
							<thead>
								<tr>
									<th class="fixed-column colwidth-7"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th class="fixed-column sorting colwidth-10" onclick="orderBy('jydh')" id="jydh_order">
									     <div class="important">
									     <div class="font-size-12 line-height-18">检验单号</div>
										 <div class="font-size-9 line-height-18">Check No.</div>
										 </div>
									</th>
									<th class="fixed-column sorting colwidth-10" onclick="orderBy('rkdh')" id="rkdh_order">
									    <div class="important">
								     	 <div class="font-size-12 line-height-18">入库单号</div>
										 <div class="font-size-9 line-height-18">Instock No.</div>
										 </div>
									</th>
									<th class="sorting colwidth-15" onclick="orderBy('bjh')" id="bjh_order">
										<div class="important">
									     <div class="font-size-12 line-height-18">部件号</div>
										 <div class="font-size-9 line-height-18">P/N</div>
										 </div>
									</th>	
									<th class="sorting colwidth-25" onclick="orderBy('ywms')" id="ywms_order">
										<div class="important">	
									     <div class="font-size-12 line-height-18">英文描述</div>
										<div class="font-size-9 line-height-18">F.Name</div>
										</div>
									</th>	
									<th class="sorting colwidth-20" onclick="orderBy('zwms')" id="zwms_order">
										<div class="important">
									     <div class="font-size-12 line-height-18">中文描述</div>
										 <div class="font-size-9 line-height-18">CH.Name</div>
										 </div>
									</th>	
									<th class="sorting colwidth-15" onclick="orderBy('cjjh')" id="cjjh_order">
										<div class="important">
									     <div class="font-size-12 line-height-18">厂家件号</div>
										<div class="font-size-9 line-height-18">MP/N</div>
										</div>
									</th>	
									<th class="colwidth-20"  id="sn_order">
										<div class="important">
									     <div class="font-size-12 line-height-18">序列号/批次号</div>
										 <div class="font-size-9 line-height-18">S/N(B/N)</div>
										 </div>
									</th>	
									<th class="sorting colwidth-10" onclick="orderBy('hclx')" id="hclx_order">
									     <div class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Airmaterial type</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('gljb')" id="gljb_order">
									     <div class="font-size-12 line-height-18">管理级别</div>
										<div class="font-size-9 line-height-18">Management level</div>
									</th>
									<th class="sorting colwidth-7" onclick="orderBy('jldw')" id="jldw_order">
									     <div class="font-size-12 line-height-18">计量单位</div>
										<div class="font-size-9 line-height-18">Unit</div>
									</th>
									<th class="sorting colwidth-7" onclick="orderBy('kcsl')" id="kcsl_order">
									     <div class="font-size-12 line-height-18">库存数量</div>
										<div class="font-size-9 line-height-18">Num</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('hcly')" id="hcly_order">
									     <div class="font-size-12 line-height-18">航材来源</div>
										<div class="font-size-9 line-height-18">Aircraft Source</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('shzh')" id="shzh_order">
									     <div class="font-size-12 line-height-18">适航证号</div>
										<div class="font-size-9 line-height-18">Certificate</div>
									</th>
									<th class="sorting colwidth-10" onclick="orderBy('xkzh')" id="xkzh_order">
									     <div class="font-size-12 line-height-18">许可证号</div>
										<div class="font-size-9 line-height-18">License</div>
									</th>
									<th class="sorting colwidth-7" onclick="orderBy('jyjg')" id="jyjg_order">
									     <div class="font-size-12 line-height-18">检验结果</div>
										<div class="font-size-9 line-height-18">Result</div>
									</th>
									<th class="sorting colwidth-9" onclick="orderBy('jyrq')" id="jyrq_order">
									     <div class="font-size-12 line-height-18">检验日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="sorting colwidth-7" onclick="orderBy('zt')" id="zt_order">
									     <div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">State</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('zdrid')" id="zdrid_order">
									     <div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintenance</div>
									</th>
									<th class="sorting colwidth-13" onclick="orderBy('zdsj')" id="zdsj_order">
									     <div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="sorting colwidth-15" onclick="orderBy('dprtcode')" id="dprtcode_order">
									     <div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
							
						</table>
					</div>
					
					<div class="col-xs-12 text-center page-height" id="pagination">
					</div>
					<div class="clearfix"></div>
			</div>
      </div>
</div>

  <!--检验不合格提示框 -->
	<div class="modal fade" id="alertModalResult" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:600px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
							<div class="font-size-16 line-height-18">检验结果说明</div>
							<div class="font-size-9 ">Inspection desc</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
			            	<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">检验单号</div>
								<div class="font-size-9 line-height-18">Check No.</div>
							</label>
							<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0" style="margin-bottom: 10px">
								<input class="form-control" id="jydh"  disabled="disabled"/>
							</div>
			            	
			            	
			            	<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">说明</div>
								<div class="font-size-9 line-height-18">Desc</div>
							</label>
							<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
								<textarea class="form-control" id="jgsm"  disabled="disabled"></textarea>
							</div>
				     		<div class="clearfix"></div>
				     		<div class=" margin-top-8  text-right">
                    	             <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
                   			 </div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/qualitycheck/qualitychecklist.js"></script> 
</body>
</html>