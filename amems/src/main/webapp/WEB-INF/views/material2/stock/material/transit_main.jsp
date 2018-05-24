<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<style type="text/css">
	.spacing {
		margin-left:3px;
		margin-right:3px;
	}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				transit_main.init();
			}
		});
	});
</script>
<div id="transit_main">
	<div class='searchContent margin-top-0 row-height'>
					<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group  ">
						<a href="javascript:transit_main.exportExcel();" type="button" class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode='project2:airworthiness:main:04'>
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a>
					</div>
					
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					    <div class='text-right'>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input id="shsj_search" checked="checked" style="vertical-align:text-bottom;" type="checkbox">&nbsp;仅显示未全部收货数据&nbsp;&nbsp;
						</label>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">产权</div>
							<div class="font-size-9 ">Property Rights</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
								<input id="cqText_search" class="form-control readonly-style" ondblclick="transit_main.openList()" type="text">
								<input id="cqid_search" class="form-control" type="hidden">
		                    	<span id="" class="input-group-addon btn btn-default" onclick="transit_main.openList()">
		                    		<i class="icon-search"></i>
		                    	</span>
				            </div>
					  </div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='件号/名称/合同编号/型号/规格/厂家件号/制造厂商'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="outsideStockMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="transit_main.init();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="transit_main.moreSearch();">
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
					<div class="clearfix"></div>
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">合同编号</div>
								<div class="font-size-9">Contract No.</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input id="htbh_search" class='form-control'type="text" >
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">部件</div>
								<div class="font-size-9">P/N</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input id='bj_search' class='form-control'type="text" placeholder='部件号/中英文名称'>
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select class='form-control' id="dprtcode_search"  name="dprtcode" onchange="transit_main.dprtChange()">
									<c:forEach items="${accessDepartment}" var="type" >
											<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					    <div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
							<button type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1"
								onclick="transit_main.searchreset();">
								<div class="font-size-12">重置</div>
								<div class="font-size-9">Reset</div>
							</button>
						</div>
					</div>
						<div class="clearfix"></div>
				</div>
	<div id="transit_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height">
		<table id="transit_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
				<tr>
					<th class="fixed-column colwidth-7">
						<div class="font-size-12 line-height-18">序号</div>
						<div class="font-size-9 line-height-18">No.</div>
					</th>
					<th class="fixed-column colwidth-15 sorting" onclick="transit_main.orderBy('hth','g2012')" name="column_hth">
						<div class="important">
							<div class="font-size-12 line-height-18">合同编号</div>
							<div class="font-size-9 line-height-18">Contract No.</div>
						</div>
					</th>
					<th class="fixed-column colwidth-15 sorting" onclick="transit_main.orderBy('bjh','g2012')" name="column_bjh">
						<div class="important">
							<div class="font-size-12 line-height-18">部件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</div>
					</th>
					<th class="colwidth-15 sorting" onclick="transit_main.orderBy('bjmc','g2012')" name="column_bjmc">
						<div class="important">
							<div class="font-size-12 line-height-18">名称</div>
							<div class="font-size-9 line-height-18">Name</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="transit_main.orderBy('sl','g2012')" name="column_sl">
						<div class="font-size-12 line-height-18">合同数量</div>
						<div class="font-size-9 line-height-18">QTY</div>
					</th>
					<th class="colwidth-7 sorting" onclick="transit_main.orderBy('rks','g2012')" name="column_rks">
						<div class="font-size-12 line-height-18">收货数量</div>
						<div class="font-size-9 line-height-18">QTY</div>
					</th>
					<th class="colwidth-10 sorting" onclick="transit_main.orderBy('cqbh','s2902')" name="column_cqbh">
						<div class="font-size-12 line-height-18">产权</div>
						<div class="font-size-9 line-height-18">Property Rights</div>
					</th>
					<th class="colwidth-10 sorting" onclick="transit_main.orderBy('hclx','s2902')" name="column_hclx">
						<div class="font-size-12 line-height-18">物料类别</div>
						<div class="font-size-9 line-height-18">Material Type</div>
					</th>
					<th class="colwidth-10 sorting" onclick="transit_main.orderBy('xgfms','s2902')" name="column_xgfms">
						<div class="font-size-12 line-height-18">供应商</div>
						<div class="font-size-9 line-height-18">Supplier</div>
					</th>
					<th class="colwidth-10 sorting" onclick="transit_main.orderBy('cjjh','s2902')" name="column_cjjh">
						<div class="important">
							<div class="font-size-12 line-height-18">厂家件号</div>
							<div class="font-size-9 line-height-18">MP/N</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="transit_main.orderBy('xingh','s2902')" name="column_xingh">
						<div class="important">
							<div class="font-size-12 line-height-18">型号</div>
							<div class="font-size-9 line-height-18">Model</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="transit_main.orderBy('gg','s2902')" name="column_gg">
						<div class="important">
							<div class="font-size-12 line-height-18">规格</div>
							<div class="font-size-9 line-height-18">Specifications</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="transit_main.orderBy('cqbh','s2902')" name="column_cqbh">
						<div class="font-size-12 line-height-18">合同类型</div>
						<div class="font-size-9 line-height-18">Contract Type</div>
					</th>
				</tr>
			</thead>
			<tbody id="transit_main_tbody">
			</tbody>
		</table>
	</div>
	<div class="col-xs-12 text-center page-height" id="transit_main_Pagination"></div>
</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/stock/material/transit_main.js"></script>
