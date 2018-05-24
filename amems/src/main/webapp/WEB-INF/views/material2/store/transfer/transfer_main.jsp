<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>移库</title>
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
						$('#workCardMainSearch').trigger('click'); //调用主列表页查询
					}
				}
			});
		});
	</script>	
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
<!-- 隐藏域 -->
<input type="hidden" value="${hclxType}" id="hclxType">
<!-- 隐藏域end -->
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class=" panel-body padding-bottom-0" id="transfer_main">
			<div class='searchContent margin-top-0 row-height' >
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick='transferOpen()'>
							<div class="font-size-12">移库</div>
							<div class="font-size-9">Transfer</div>
						</button>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">仓库</div>
							<div class="font-size-9">Warehouse</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<select onchange="transfer_main.initKw()" class='form-control' id="shelf_ck">
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">库位</div>
							<div class="font-size-9">Stock</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<select class='form-control selectpicker' data-live-search="true"  data-size="10" id="shelf_kw">
							</select>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='件号/序列号/名称/批次号/标签条码'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="workCardMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="transfer_main.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="moreSearch();">
								<div class='input-group input-group-search'>
								<div class="input-group-addon">
								<div class="font-size-12">更多</div>
								<div class="font-size-9 margin-top-5" >More</div>
								</div>
								<div class="input-group-addon">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
								</div>
					   			</button>
		                	</div>
						</div>
					</div>
					<!-- 搜索框end -->
				  <div class='clearfix'></div>
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">产权</div>
							<div class="font-size-9 ">Property Rights</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<div class="input-group col-xs-12">
								<input id="cqText_search" disabled="disabled" class="form-control readonly-style" ondblclick="transfer_main.openList()" type="text">
								<input id="cqid_search" class="form-control" type="hidden">
		                    	<span id="" class="input-group-addon btn btn-default" onclick="transfer_main.openList()">
		                    		<i class="icon-search"></i>
		                    	</span>
				            </div>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">物料类别</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="wllb_search">
								<option></option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">器材状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="qczt_search">
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="dprtcode_search"  name="dprtcode" onchange="dprtChange()">
								<c:forEach items="${accessDepartment}" var="type" >
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="transfer_main.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <!-- 需求追踪 -->
		   <div  class='table_pagination'>
		   <div id="transfer_main_top_div" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="transfer_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th class="viewCol fixed-column colwidth-7 selectAllImg" style="width: 75px;">
									<a href="#" onclick="SelectUtil.performSelectAll('transfer_main_top_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
									<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('transfer_main_top_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
								</th>
								<th class='colwidth-3' >
									<div class="font-size-12 line-height-18" >序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class='colwidth-10 sorting' onclick="transfer_main.orderBy('bjh','')" name="column_bjh">
									<div class="important">
										<div class="font-size-12 line-height-18">部件号</div>
										<div class="font-size-9 line-height-18">PN</div>
									</div>
								</th>
								<th class='colwidth-15 sorting' onclick="transfer_main.orderBy('ywms','')" name="column_ywms">
									  <div class="important"> 
									    <div class="font-size-12 line-height-18">名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									 </div>
							    </th>
								<th class='colwidth-10 sorting' onclick="transfer_main.orderBy('pch','')" name="column_pch">
									 <div class="important"> 
										<div class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">Batch No.</div>
									</div>
								</th>
								<th class='colwidth-10 sorting' onclick="transfer_main.orderBy('sn','')" name="column_sn">
									 <div class="important"> 	
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">SN</div>
							   		</div>
							   </th>
							   <th class='colwidth-15 sorting' onclick="transfer_main.orderBy('ckmc','')" name="column_ckmc">
									<div class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Position</div>
							   </th>
							   <th class='colwidth-7 sorting' onclick="transfer_main.orderBy('kcsl','')" name="column_kcsl">
									<div class="font-size-12 line-height-18">库存数量</div>
									<div class="font-size-9 line-height-18">QTY</div>
							   </th>
							   <th class='colwidth-10 sorting' onclick="transfer_main.orderBy('cqbh','')" name="column_cqbh">
									<div class="font-size-12 line-height-18">产权</div>
									<div class="font-size-9 line-height-18">Right</div>
							   </th>
<!-- 							   <th class='colwidth-5 sorting' onclick="transfer_main.orderBy('zt','')" name="column_zt"> -->
<!-- 									<div class="font-size-12 line-height-18">状态</div> -->
<!-- 									<div class="font-size-9 line-height-18">Status</div> -->
<!-- 							   </th> -->
							   <th class='colwidth-15 sorting' onclick="transfer_main.orderBy('cfyq','')" name="column_cfyq">
									<div class="font-size-12 line-height-18">存放要求</div>
									<div class="font-size-9 line-height-18">Requirements</div>
							   </th>
							   <th class='colwidth-7 sorting' onclick="transfer_main.orderBy('hclx','')" name="column_hclx">
									<div class="font-size-12 line-height-18">物料类别</div>
									<div class="font-size-9 line-height-18">Type</div>
							   </th>
							   <th class='colwidth-7 sorting' onclick="transfer_main.orderBy('qczt','')" name="column_qczt">
									<div class="font-size-12 line-height-18">器材状态</div>
									<div class="font-size-9 line-height-18">Status</div>
							   </th>
							</tr> 
						</thead>
						<tbody id="transfer_main_tbody"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="transfer_main_Pagination"></div>
				</div>
		   </div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/material2/store/transfer/transfer_open.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/store/transfer/transfer_main.js"></script>
	<%@ include file="/WEB-INF/views/common/produce/material_cq.jsp" %> 
</body>
</html>