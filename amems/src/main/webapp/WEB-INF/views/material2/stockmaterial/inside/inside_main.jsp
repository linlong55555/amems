<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>库内查询</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
	<script type="text/javascript">
		$(document).ready(function(){
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					search();
				}
			});
		});
	</script>	
</head>
<body class="page-header-fixed">
<input type="hidden" id="isTool" value="${isTool}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class=" panel-body padding-bottom-0" >
			<div class='searchContent margin-top-0 row-height' >
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="exportExcel();">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='material:stock:material:inside:02,material:stock:tool:inside:02' onclick="importExcel();">
							<div class="font-size-12">导入</div>
							<div class="font-size-9">Import</div>
						</button>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">产权</div>
							<div class="font-size-9">Right</div>
						</span>
						<div class=" input-group col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<input type='hidden' id='cqid_search'  class='form-control' />
							<input type='text' id='cqbh_search'  ondblclick="inside_Modal.loadCqView()" class='form-control readonly-style' readonly="readonly"/>
							<span id="" class="input-group-addon btn btn-default" onclick="loadCq()">
		                    		<i class="icon-search"></i>
		                    </span>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">仓库</div>
							<div class="font-size-9">Warehouse</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
	                        <select  id='ck_search' class='form-control' onchange="initKw()">
							</select>						
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='件号/序列号/部件名称/型号/规格/批次号/厂家件号/制造厂商/库位/存放要求/GRN/备注'  class="form-control" id="keyword" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="workCardMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="search();" style='margin-right:0px !important;'>
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
							<div class="font-size-12">部件</div>
							<div class="font-size-9">PN</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' id="bjh_search" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">批次号</div>
							<div class="font-size-9">Batch No.</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' id="pch_search" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">序列号</div>
							<div class="font-size-9">SN</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' id="sn_search" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">规格/型号</div>
							<div class="font-size-9">Specification/model</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' id="gg_xh_search"/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">物料类别</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="wllb_search" >
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">管理级别</div>
							<div class="font-size-9">Level</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="gljb_search" >
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">						
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">库位</div>
							<div class="font-size-9">Library</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
						    <select class='form-control selectpicker' data-live-search="true"  data-size="10" id="kwh_search">
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">GRN</div>
							<div class="font-size-9">GRN</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' id="grn_search"/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源</div>
							<div class="font-size-9">Source</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="hcly_search">
							
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">上架日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' name='date-range-picker' id="sjrq_search" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">生产日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' name='date-range-picker' id="scrq_search" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">制造厂商</div>
							<div class="font-size-9">Manufacturer</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' id="zzcs_search" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
					    <div class="important">
							<div class="font-size-12">厂家件号</div>
							<div class="font-size-9">MP/N</div>
						</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' id="cjjh_search"/>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">器材状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="qczt_search"></select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">合同编号</div>
							<div class="font-size-9">Contact No.</div>			
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' id="htbh_search"/>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3   padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control " onchange="searchByDprtcode()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
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
				<div class='clearfix'></div>
				</div>
		

		   <div  class='table_pagination'>
		   <div id="" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="inner_store_list_table" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th  class='colwidth-4'>
									<div class="font-size-12 line-height-18" >序号</div>
									<div class="font-size-9 line-height-18">No</div>
								</th>
								<th class='colwidth-8'>
									<div class="font-size-12 line-height-18" >寿命标识</div>
									<div class="font-size-9 line-height-18">HJ Mark</div>
								</th>
								<%  if("true".equals(request.getAttribute("isTool").toString())){%>
								
								<th class='colwidth-8'>
									<div class="font-size-12 line-height-18" >校验标识</div>
									<div class="font-size-9 line-height-18">Vali Mark</div>
								</th>
								   <%}%>
								<th class='colwidth-7 sorting' onclick="orderBy('qczt')" id="qczt_order" >
									<div class="font-size-12 line-height-18">器材状态</div>
									<div class="font-size-9 line-height-18">Status</div>
							   </th>
								<th class='colwidth-10 sorting' onclick="orderBy('bjh')" id="bjh_order">
								<div class="important">
									<div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-18">PN</div>
							    </div>
								</th>								
								<th class='colwidth-25 sorting' onclick="orderBy('ywms')" id="ywms_order" >
								<div class="important">
									    <div class="font-size-12 line-height-18">部件名称</div>
										<div class="font-size-9 line-height-18">Parts Name</div>
							    </div>
							    </th>
								<th class='colwidth-10 sorting' onclick="orderBy('pch')" id="pch_order" >
								<div class="important">
									<div class="font-size-12 line-height-18">批次号</div>
									<div class="font-size-9 line-height-18">Batch no.</div>
								</div>
								</th>
								<th class='colwidth-10 sorting' onclick="orderBy('sn')" id="sn_order">
								<div class="important">
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">SN</div>
								</div>
							    </th>
							   <th class='colwidth-20 sorting' onclick="orderBy('ckh')" id="ckh_order" >
									<div class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Position</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="orderBy('kcsl')" id="kcsl_order">
									<div class="font-size-12 line-height-18">库存数量</div>
									<div class="font-size-9 line-height-18">Quantity</div>
							   </th>
							   
							   
							   <th class='colwidth-9 sorting' onclick="orderBy('material_cb')" id="material_cb_order">
									<div class="font-size-12 line-height-18">成本</div>
									<div class="font-size-9 line-height-18">Cost</div>
							   </th>   
<!-- 							   <th class='colwidth-9 sorting' onclick="orderBy('material_jz')" id="material_jz_order"> -->
<!-- 									<div class="font-size-12 line-height-18">价值</div> -->
<!-- 									<div class="font-size-9 line-height-18">Value</div> -->
<!-- 							   </th> -->
							   
							   
							   
							   
							   <th class='colwidth-9 sorting' onclick="orderBy('hjsm')" id="hjsm_order" >
									<div class="font-size-12 line-height-18">货架寿命</div>
									<div class="font-size-9 line-height-18">Life</div>
							   </th>
							   <th class='colwidth-15 sorting' onclick="orderBy('cqbh')" id="cqbh_order" >
									<div class="font-size-12 line-height-18">产权</div>
									<div class="font-size-9 line-height-18">Right</div>
							   </th>
							   <th class='colwidth-10 sorting' onclick="orderBy('grn')" id="grn_order" >
							   <div class="important">
									<div class="font-size-12 line-height-18">GRN</div>
									<div class="font-size-9 line-height-18">GRN</div>
								</div>
							   </th>
							     <th class='colwidth-12 sorting' onclick="orderBy('htbh_cg')" id="htbh_cg_order" >
									<div class="font-size-12 line-height-18">合同编号</div>
									<div class="font-size-9 line-height-18">Contact No.</div>
							   </th>
<!-- 							   <th class='colwidth-5 sorting' onclick="orderBy('zt')" id="zt_order" > -->
<!-- 									<div class="font-size-12 line-height-18">状态</div> -->
<!-- 									<div class="font-size-9 line-height-18">Status</div> -->
<!-- 							   </th> -->
							   <th class='colwidth-8 sorting' onclick="orderBy('hcly')" id="hcly_order" >
									<div class="font-size-12 line-height-18">来源</div>
									<div class="font-size-9 line-height-18">Source</div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="orderBy('cfyq')" id="cfyq_order" >
							   <div class="important">
									<div class="font-size-12 line-height-18">存放要求</div>
									<div class="font-size-9 line-height-18">Requirements</div>
								</div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="orderBy('bz')" id="bz_order">
							   <div class="important">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="orderBy('rksj')" id="rksj_order">
									<div class="font-size-12 line-height-18">上架日期</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="orderBy('rkrid')" id="rkrid_order">
									<div class="font-size-12 line-height-18">上架人</div>
									<div class="font-size-9 line-height-18">Shelf</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="orderBy('scrq')" id="scrq_order">
									<div class="font-size-12 line-height-18">生产日期</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class='colwidth-8 sorting' onclick="orderBy('tsn')" id="tsn_order">
									<div class="font-size-12 line-height-18">TSN</div>
									<div class="font-size-9 line-height-18">TSN</div>
							   </th>
							   <th class='colwidth-8 sorting' onclick="orderBy('csn')" id="csn_order">
									<div class="font-size-12 line-height-18">CSN</div>
									<div class="font-size-9 line-height-18">CSN</div>
							   </th>
							   <th class='colwidth-8 sorting' onclick="orderBy('tso')" id="tso_order" >
									<div class="font-size-12 line-height-18">TSO</div>
									<div class="font-size-9 line-height-18">TSO</div>
							   </th>
							   <th class='colwidth-8 sorting' onclick="orderBy('cso')" id="cso_order">
									<div class="font-size-12 line-height-18">CSO</div>
									<div class="font-size-9 line-height-18">CSO</div>
							   </th>
							   <th class='colwidth-8 sorting' onclick="orderBy('hclx')" id="hclx_order" >
									<div class="font-size-12 line-height-18">物料类别</div>
									<div class="font-size-9 line-height-18">Category</div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="orderBy('cjjh')" id="cjjh_order" >
							   <div class="important">
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">MP/N</div>
								</div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="orderBy('xingh')" id="xingh_order" >
							   <div class="important">
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
							   </div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="orderBy('gg')" id="gg_order" >
							   <div class="important">
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Specifications</div>
								</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="orderBy('gljb')" id="gljb_order">
									<div class="font-size-12 line-height-18">管理级别</div>
									<div class="font-size-9 line-height-18">Level</div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="orderBy('zzcs')" id="zzcs_order" >
							   <div class="important">
									<div class="font-size-12 line-height-18">制造厂商</div>
									<div class="font-size-9 line-height-18">Manufacturer</div>
								</div>
							   </th>
							</tr> 
						</thead>
						<tbody id="storeInnerList">
						
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="inner_search_pagination"></div>
				</div>
		   </div>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/views/material2/stockmaterial/inside/inside_open.jsp"%>
	<%@ include file="/WEB-INF/views/common/produce/material_cq.jsp"%>  <!--产权弹框  -->
	<%@ include file="/WEB-INF/views/material2/stockmaterial/inside/frozen_history.jsp"%>  <!--库存冻结履历查看弹框  -->	
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->	
    <%@ include file="/WEB-INF/views/material2/stockmaterial/inside/inside_certificate.jsp"%><!-----证书对话框 -------->
    <script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/stockmaterial/inside/inside_main.js"></script>
		<%@ include file="/WEB-INF/views/open_win/import.jsp"%>
</body>
</html>