<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>库存履历</title>
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
						$('#aircraftinfoMainSearch').trigger('click'); //调用主列表页查询
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
						<div class="col-xs-12 input-group input-group-search">
				             <div class="input-group-addon btn-searchnew">
		                    	<button onclick="stockinout.exportExcel()" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">导出</div>
									<div class="font-size-9">Download</div>
								</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew">
		                    	<div style="color:#333;text-align:right;padding-right:5px;margin-left:15px;">
		                    	<div class="font-size-12">操作日期</div>
								<div class="font-size-9 " style="line-height:15px;margin-top:1px;">Date</div>
		                    	</div>
		                    </div>
							<input type='text' class='form-control' id="czsj_search" onchange="stockinout.search();" name='date-range-picker'/>
		               </div>
                    </div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">类型</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<select class='form-control' id="czlx_search" onchange="stockinout.initStockHistorySubtype();">
								<option value="" selected="selected">显示全部All</option>
								<c:forEach items="${stockHistoryTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					   <span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">子类型</div>
							<div class="font-size-9">Subtype</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<select class='form-control' id="czzlx_search" onchange="stockinout.search();">
								
							</select>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='部件号/批次号/序列号'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="aircraftinfoMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="stockinout.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="stockinout.moreSearch();">
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
							<div class="font-size-12">产权</div>
							<div class="font-size-9">Property right</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<div class="input-group col-xs-12">
							<input id="cqid_search" type="hidden" >
							<input id="cqbh_search" class="form-control readonly-style" readonly="readonly" ondblclick="stockinout.loadCq();" type="text">
							<span class="required input-group-btn"  >
								<button type="button" class="btn btn-default form-control" id="lybutton" style='height:34px;' onclick="stockinout.loadCq();" data-toggle="modal" >
									<i class="icon-search cursor-pointer" ></i>
								</button>
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
							<select class='form-control' id="hclx_search">
								<option value="" selected="selected">显示全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">操作人</div>
							<div class="font-size-9">Operator</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control" id="czr_search" type="text" />
						</div>
					</div>
					
				<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="stockinout.onchangeDprtcode()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
				    <div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="stockinout.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <div  class='table_pagination'>
		   <div id="work_card_main_table_top_div" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="stockinout_Table" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th  class='colwidth-10 sorting' sorting onclick="stockinout.orderBy('czzlx')" id="czzlx_order">
									<div class="font-size-12 line-height-18" >类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class=' colwidth-8 sorting' onclick="stockinout.orderBy('hclx')" id="hclx_order">
									<div class="font-size-12 line-height-18" >物料类别</div>
									<div class="font-size-9 line-height-18">Category</div>
								</th>
								<th class=' colwidth-10 sorting' onclick="stockinout.orderBy('bjh')" id="bjh_order">
									<div class="important">
									<div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-18">PN</div></div>
								</th>
								<th class=' colwidth-5 sorting' onclick="stockinout.orderBy('kcsl')" id="kcsl_order">
									    <div class="font-size-12 line-height-18">数量</div>
										<div class="font-size-9 line-height-18">QTY</div>
							    </th>
								<th class=' colwidth-15 sorting' onclick="stockinout.orderBy('czsj')" id="czsj_order">
									<div class="font-size-12 line-height-18">操作时间</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class=' colwidth-10 sorting' onclick="stockinout.orderBy('pch')" id="pch_order">
									<div class="important">
										<div class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">Batch No.</div>
										</div>
							   </th>
							   <th class=' colwidth-10 sorting' sorting onclick="stockinout.orderBy('sn')" id="sn_order">
							   	<div class="important">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">SN</div>
									</div>
							   </th>
							   <th class='colwidth-20 sorting' sorting onclick="stockinout.orderBy('CQBH')" id="CQBH_order">
									<div class="font-size-12 line-height-18">产权</div>
									<div class="font-size-9 line-height-18">Property right</div>
							   </th>
							   <th class='colwidth-10 sorting' onclick="stockinout.orderBy('kccb')" id="kccb_order">
									<div class="font-size-12 line-height-18">成本</div>
									<div class="font-size-9 line-height-18">Cost</div>
							   </th>
<!-- 							   <th class='colwidth-10 sorting' onclick="stockinout.orderBy('jz')" id="jz_order"> -->
<!-- 									<div class="font-size-12 line-height-18">价值</div> -->
<!-- 									<div class="font-size-9 line-height-18">Value</div> -->
<!-- 							   </th> -->
							   <th class='colwidth-12 sorting' onclick="stockinout.orderBy('ywbh')" id="ywbh_order">
									<div class="font-size-12 line-height-18">单据编号</div>
									<div class="font-size-9 line-height-18">Document No.</div>
							   </th>
							   <th class='colwidth-12' >
									<div class="font-size-12 line-height-18">操作人</div>
									<div class="font-size-9 line-height-18">Operator</div>
							   </th>
							</tr> 
						</thead>
						<tbody id="stockinout_list">
					
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="stockinout_pagination"></div>
				</div>
		   </div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/stockinoutlist/stockinoutlist_main.js"></script>
	<%@ include file="/WEB-INF/views/common/produce/material_cq.jsp"%>  <!--产权弹框  -->
</body>
</html>