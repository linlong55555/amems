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
				outfield_main.search();
			}
		});
	});
</script>
<div id="outfield_main">
<input type="hidden" id="isTool" value="${isTool}">
	<div class='searchContent margin-top-0 row-height'>
					<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group  ">
						<a href="javascript:outfield_main.exportExcel();" type="button" class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode='project2:airworthiness:main:04'>
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">产权</div>
							<div class="font-size-9 ">Property Rights</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
								<input id="cqText_search" disabled="disabled" class="form-control readonly-style" ondblclick="outfield_main.openList()" type="text">
								<input id="cqid_search" class="form-control" type="hidden">
		                    	<span id="" class="input-group-addon btn btn-default" onclick="outfield_main.openList()">
		                    		<i class="icon-search"></i>
		                    	</span>
				            </div>
					  </div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">物料来源</div>
							<div class="font-size-9">Material Source</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0 label-input-div" >
							<select id="wlly_search" class="form-control" >
								<option value="" >显示全部  All</option>
								<option value="1" >库房</option>
								<option value="2" >飞机</option>
							</select>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='件号/序列号/名称/型号/规格/批次号/厂家件号/制造厂商/GRN'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="outsideStockMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="outfield_main.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="outfield_main.moreSearch();">
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
								<div class="font-size-12">部件</div>
								<div class="font-size-9">P/N</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input id='bj_search' class='form-control'type="text" placeholder='部件号/中英文名称'>
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">批次号</div>
								<div class="font-size-9">Batch No.</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input id="pch_search" class='form-control'type="text" >
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">序列号</div>
								<div class="font-size-9">SN</div>
							</span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input id="xlh_search" class='form-control'type="text" >
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">规格/型号</div>
								<div class="font-size-9">Specifications/Model</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<input id="gg__search" class='form-control'type="text" >
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">物料类别</div>
								<div class="font-size-9">Material Type</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select class='form-control' id="wllb_search">
									<option value="" selected="true">显示全部All</option>
									<option value="1" >航材</option>
									<option value="4" >化工品</option>
									<option value="5" >低值易耗品</option>
									<option value="6" >松散件</option>
									<option value="0" >其他</option>
								</select>
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">管理级别</div>
								<div class="font-size-9">Level</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select class='form-control' id="gljb_search">
									<option value="" selected="true">显示全部All</option>
								<c:forEach items="${supervisoryLevelEnum}" var="item">
								  <option value="${item.id}" >${item.name}</option>
								</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">厂家件号</div>
								<div class="font-size-9">MP/N</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<input id="cjjh_search" class='form-control'type="text" >
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">制造厂商</div>
								<div class="font-size-9">Manufacturers</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<input id='zzcs_search' class='form-control'type="text" >
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
								<div class="font-size-12">合同编号</div>
								<div class="font-size-9">Contact No.</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<input id='htbh_search' class='form-control'type="text" >
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
								onclick="outfield_main.searchreset();">
								<div class="font-size-12">重置</div>
								<div class="font-size-9">Reset</div>
							</button>
						</div>
					</div>
						<div class="clearfix"></div>
				</div>
	
	<div id="outfield_main_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height">
		<table id="outfield_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1350px;">
			<thead>
				<tr>
					<th class="fixed-column colwidth-5">
						<div class="font-size-12 line-height-18">序号</div>
						<div class="font-size-9 line-height-18">No.</div>
					</th>
					<th class="colwidth-7 sorting" onclick="outfield_main.orderBy('qczt','s2902')" name="column_qczt">
						<div class="font-size-12 line-height-18">器材状态</div>
						<div class="font-size-9 line-height-18">Status</div>
					</th>
					<th class="fixed-column colwidth-10 sorting" onclick="outfield_main.orderBy('bjh','g2012')" name="column_bjh">
						<div class="important">
							<div class="font-size-12 line-height-18">部件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</div>
					</th>
					<th class="colwidth-15 sorting" onclick="outfield_main.orderBy('ywms','g2012')" name="column_ywms">
						<div class="important">
							<div class="font-size-12 line-height-18">名称</div>
							<div class="font-size-9 line-height-18">Name</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('pch','g2012')" name="column_pch">
						<div class="important">
							<div class="font-size-12 line-height-18">批次号</div>
							<div class="font-size-9 line-height-18">Batch No.</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('sn','g2012')" name="column_sn">
						<div class="important">
							<div class="font-size-12 line-height-18">序列号</div>
							<div class="font-size-9 line-height-18">SN</div>
						</div>
					</th>
					<th class="colwidth-7 sorting" onclick="outfield_main.orderBy('sjly','g2012')" name="column_sjly">
						<div class="font-size-12 line-height-18">物料来源</div>
						<div class="font-size-9 line-height-18">Original</div>
					</th>
					<th class="colwidth-7 sorting" onclick="outfield_main.orderBy('kcsl','s2902')" name="column_kcsl">
						<div class="font-size-12 line-height-18">数量</div>
						<div class="font-size-9 line-height-18">QTY</div>
					</th>
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('kccb','d008')" name="column_kccb">
						<div class="font-size-12 line-height-18">库存成本</div>
						<div class="font-size-9 line-height-18">Inventory Cost</div>
					</th>
<!-- 					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('jz','d008')" name="column_kccb"> -->
<!-- 						<div class="font-size-12 line-height-18">库存价值</div> -->
<!-- 						<div class="font-size-9 line-height-18">Inventory Price</div> -->
<!-- 					</th> -->
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('cqbh','s2902')" name="column_cqbh">
						<div class="font-size-12 line-height-18">产权</div>
						<div class="font-size-9 line-height-18">Property Rights</div>
					</th>
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('hclx','s2902')" name="column_hclx">
						<div class="font-size-12 line-height-18">物料类别</div>
						<div class="font-size-9 line-height-18">Material Type</div>
					</th>
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('cjjh','s2902')" name="column_cjjh">
						<div class="important">
							<div class="font-size-12 line-height-18">厂家件号</div>
							<div class="font-size-9 line-height-18">MP/N</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('xh','s2902')" name="column_xh">
						<div class="important">
							<div class="font-size-12 line-height-18">型号</div>
							<div class="font-size-9 line-height-18">Model</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('gg','s2902')" name="column_gg">
						<div class="important">
							<div class="font-size-12 line-height-18">规格</div>
							<div class="font-size-9 line-height-18">Specifications</div>
						</div>
					</th>
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('grn','s2902')" name="column_grn">
						<div class="important">
							<div class="font-size-12 line-height-18">GRN号</div>
							<div class="font-size-9 line-height-18">GRN</div>
						</div>
					</th>
					<th class='colwidth-12 sorting' onclick="outfield_main.orderBy('htbh_cg','s2902')" id="column_htbh_cg" >
						<div class="font-size-12 line-height-18">合同编号</div>
						<div class="font-size-9 line-height-18">Contact No.</div>
				   </th>
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('gljb','s2902')" name="column_gljb">
						<div class="font-size-12 line-height-18">管理级别</div>
						<div class="font-size-9 line-height-18">Level</div>
					</th>
					<th class="colwidth-10 sorting" onclick="outfield_main.orderBy('zzcs','s2902')" name="column_zzcs">
						<div class="important">
						<div class="font-size-12 line-height-18">制造厂商</div>
						<div class="font-size-9 line-height-18">Manufacturers</div>
						</div>
					</th>
				</tr>
			</thead>
			<tbody id="outfield_main_tbody">
			</tbody>
		</table>
	</div>
	<div class="col-xs-12 text-center page-height" id="outfield_main_Pagination"></div>
</div>
	<%@ include file="/WEB-INF/views/material2/stock/material/outsideStock_open.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/stock/material/outfield_main.js"></script>
	<%@ include file="/WEB-INF/views/material2/stockmaterial/inside/frozen_history.jsp"%>  <!--库存冻结履历查看弹框  -->
	<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_certificate.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
