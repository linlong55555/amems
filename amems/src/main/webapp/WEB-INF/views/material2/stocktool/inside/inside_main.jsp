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
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Download</div>
						</button>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">产权</div>
							<div class="font-size-9">Right</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<input type='text' id='' class='form-control' />
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">仓库</div>
							<div class="font-size-9">Warehouse</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<input type='text' id='' class='form-control' name='date-range-picker'/>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='件号/序列号/名称/型号/规格/批次号/厂家件号/制造厂商/库位/存放要求/GRN/备注'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="workCardMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="work_card_main.search();" style='margin-right:0px !important;'>
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
							<input type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">批次号</div>
							<div class="font-size-9">Batch No.</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">序列号</div>
							<div class="font-size-9">SN</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">规格/型号</div>
							<div class="font-size-9">Specification/model</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control'>
								<option></option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">物料类别</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control'>
								<option></option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">管理级别</div>
							<div class="font-size-9">Level</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control'>
								<option></option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">库位</div>
							<div class="font-size-9">Library</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">GRN</div>
							<div class="font-size-9">GRN</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源</div>
							<div class="font-size-9">Source</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control'>
							
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">上架日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">生产日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">制造厂商</div>
							<div class="font-size-9">Manufacturer</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">厂家件号</div>
							<div class="font-size-9">Manufacturer's no.</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' class='form-control' />
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="work_card_main.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <!-- 需求追踪 -->
		   <div  class='table_pagination'>
		   <div id="" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th  class='colwidth-5'>
									<div class="font-size-12 line-height-18" >序号</div>
									<div class="font-size-9 line-height-18">Item</div>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18" >标识</div>
									<div class="font-size-9 line-height-18">Identification</div>
								</th>
								<th class='colwidth-9' onclick="" name="">
									<div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-18">PN</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									    <div class="font-size-12 line-height-18">名称</div>
										<div class="font-size-9 line-height-18">Name</div>
							    </th>
								<th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">批次号</div>
									<div class="font-size-9 line-height-18">Batch no.</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">SN</div>
							   </th>
							   <th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Position</div>
							   </th>
							   <th class='colwidth-9' onclick="" name="" >
									<div class="font-size-12 line-height-18">库存数量</div>
									<div class="font-size-9 line-height-18">Quantity</div>
							   </th>
							   <th class='colwidth-20' onclick="" name="" >
									<div class="font-size-12 line-height-18">货架寿命</div>
									<div class="font-size-9 line-height-18">Life</div>
							   </th>
							   <th class='colwidth-7' onclick="" name="" >
									<div class="font-size-12 line-height-18">产权</div>
									<div class="font-size-9 line-height-18">Right</div>
							   </th>
							   <th class='colwidth-20' onclick="" name="" >
									<div class="font-size-12 line-height-18">GRN</div>
									<div class="font-size-9 line-height-18">GRN</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">来源</div>
									<div class="font-size-9 line-height-18">Source</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">存放要求</div>
									<div class="font-size-9 line-height-18">Requirements</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">上架日期</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">上架人</div>
									<div class="font-size-9 line-height-18">Shelf</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">生产日期</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">TSN</div>
									<div class="font-size-9 line-height-18">TSN</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">CSN</div>
									<div class="font-size-9 line-height-18">CSN</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">TSO</div>
									<div class="font-size-9 line-height-18">TSO</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">SCO</div>
									<div class="font-size-9 line-height-18">SCO</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">物料类别</div>
									<div class="font-size-9 line-height-18">Category</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">Manufacturer's no.</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Specifications</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">管理级别</div>
									<div class="font-size-9 line-height-18">Level</div>
							   </th>
							   <th class='colwidth-12' onclick="" name="" >
									<div class="font-size-12 line-height-18">制造厂商</div>
									<div class="font-size-9 line-height-18">Manufacturer</div>
							   </th>
							  
							</tr> 
						</thead>
						<tbody id="">
							<tr  id='1'>
							<td class='text-center'>
								<a href='javascript:;' onclick='showInsideOpen()'>查看</a>
							</td>
							<td class='text-center'>
							</td>
							<td class='text-center'></td>
							<td></td>
							<td></td>
							<td class='text-center'>
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							</tr>
							<tr id='2'>
							<td class='text-center'>
								归还
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							
							</tr>
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id=""></div>
				</div>
		   </div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/material2/stocktool/inside/inside_open.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/stocktool/inside/inside_main.js"></script>
</body>
</html>