<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
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
						stock_open_alert.searchkc();//调用主列表页查询
					}
				}
			});
		});
	</script>
<div class="modal fade in modal-new" id="stock_open_alert" tabindex="-1" role="dialog"  aria-labelledby="stock_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:90%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">选择库存</div>
							<div class="font-size-12" id="modalHeadENG">Stock</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
		            <div class='col-xs-12 margin-left-0 margin-right-0' style=' padding-top:8px;' id='modalPanelRight'>
		            <div class='searchContent margin-top-0 row-height' >
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">产权</div>
							<div class="font-size-9">Property</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
								<div class="input-group col-xs-12">
							<input id="stock_open_alert_cqid" type="hidden" >
							<input id="stock_open_alert_cqbh" class="form-control readonly-style" readonly="readonly" ondblclick="stock_open_alert.loadCq();" type="text">
							<span class="required input-group-btn"  >
								<button type="button" class="btn btn-default form-control" id="lybutton" style='height:34px;' onclick="stock_open_alert.loadCq();" data-toggle="modal" >
									<i class="icon-search cursor-pointer" ></i>
								</button>
							</span>
						</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					   <span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">仓库</div>
							<div class="font-size-9">Warehouse</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<select class='form-control' id="stock_open_alert_ckid" onchange="stock_open_alert.changeStore()" >
							</select>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='件号/序列号/名称/型号/规格/批次号/厂家件号/制造厂商/存放要求/GRN/备注'  class="form-control" id="stock_open_alert_keyword" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="stock_open_alert.searchkc();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="stock_open_alert.more();">
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
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="stock_open_alert_divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">部件</div>
							<div class="font-size-9">PN</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control" id="stock_open_alert_bjh" type="text" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">批次号</div>
							<div class="font-size-9">Batch No.</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control" id="stock_open_alert_pch"  type="text" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">序列号</div>
							<div class="font-size-9">SN</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control" id="stock_open_alert_xlh" type="text" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">规格/型号</div>
							<div class="font-size-9">S/M</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control" id="stock_open_alert_ggxh"  type="text" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">物料类别</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="stock_open_alert_hclx"  class="form-control " >
								<option value="" selected="selected">显示全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">管理级别</div>
							<div class="font-size-9">Level</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="stock_open_alert_gljb">
								<option value="">显示全部</option>
								<option value="1">无</option>
								<option value="2">批次号管理</option>
								<option value="3">序列号管理</option>
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">库位</div>
							<div class="font-size-9">Library</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control selectpicker' data-live-search="true" 
								data-container="#stock_open_alert" data-size="10" id="stock_open_alert_kwid"></select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">GRN</div>
							<div class="font-size-9">GRN</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control" id="stock_open_alert_grn" name="" type="text" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源</div>
							<div class="font-size-9">Source</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="stock_open_alert_hcly" name="stock_open_alert_hcly" class="form-control"  >
						     </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">上架日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control " id="stock_open_alert_rksj"   name="date-range-picker" type="text" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">生产日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control " id="stock_open_alert_scrq"  name="date-range-picker" type="text" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">制造厂商</div>
							<div class="font-size-9">Manufacturer</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control" id="stock_open_alert_zzcs" type="text" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">厂家件号</div>
							<div class="font-size-9">No.</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input class="form-control" id="stock_open_alert_cjjh" name="" type="text" />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">器材状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="stock_open_alert_qczt">
							</select>
						</div>
					</div>
					
				    <div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="stock_open_alert.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<!-- 查询条件结束 -->
				<div  class='table_pagination'>
		          <div id="work_card_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0" style="overflow-x: auto;">
					<table id="stocklist_table" class="table table-thin table-bordered table-striped table-hover table-set" style="margin-bottom:0px !important;">
						<thead>                              
							<tr>
								<th  class="selectAllImg" id="checkAll" style='text-align:center;vertical-align:middle;width:60px;'>
									<a href="#" onclick="SelectUtil.performSelectAll('stock_open_alert_stocklist')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
									<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('stock_open_alert_stocklist')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
								</th>
								<th class='colwidth-10'  >
									<div class="important">
									    <div class="font-size-12 line-height-18">部件号</div>
										<div class="font-size-9 line-height-18">PN</div>
									</div>
							    </th>
								<th class='colwidth-20'  >
									<div class="important">
									<div class="font-size-12 line-height-18">部件名称</div>
									<div class="font-size-9 line-height-18">PN name</div>
									</div>
								</th>
								<th class='colwidth-9' >
									<div class="important">
										<div class="font-size-12 line-height-18">批次号</div>
										<div class="font-size-9 line-height-18">Batch No.</div>
									</div>
							    </th>
							    <th class='colwidth-9' >
							    	<div class="important">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">SN</div>
									</div>
							    </th>
							    <th class='colwidth-20'>
									<div class="font-size-12 line-height-18" >位置</div>
									<div class="font-size-9 line-height-18">Position</div>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18" >库存数量</div>
									<div class="font-size-9 line-height-18">Number</div>
								</th>
								<th class='colwidth-15'>
									<div class="font-size-12 line-height-18">货架寿命/天</div>
									<div class="font-size-9 line-height-18">Shelf Life/day</div>
								</th>
								<th class='colwidth-9'  >
									<div class="font-size-12 line-height-18">库存成本</div>
									<div class="font-size-9 line-height-18">Cost</div>
							   </th>
<!-- 								<th class='colwidth-9'  > -->
<!-- 									<div class="font-size-12 line-height-18">价值</div> -->
<!-- 									<div class="font-size-9 line-height-18">Cost</div> -->
<!-- 							   </th> -->
							   <th class='colwidth-20'  >
									<div class="font-size-12 line-height-18">产权</div>
									<div class="font-size-9 line-height-18">Right</div>
							   </th>
							   <th class='colwidth-7'  >
									<div class="font-size-12 line-height-18">GRN</div>
									<div class="font-size-9 line-height-18">GRN</div>
							   </th>
<!-- 							   <th class='colwidth-9'  > -->
<!-- 									<div class="font-size-12 line-height-18">状态</div> -->
<!-- 									<div class="font-size-9 line-height-18">Status</div> -->
<!-- 							   </th> -->
							   <th class='colwidth-12'  >
									<div class="font-size-12 line-height-18">来源</div>
									<div class="font-size-9 line-height-18">Source</div>
							   </th>
							   <th class='colwidth-20'  >
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark </div>
							   </th>
							   <th class='colwidth-12'  >
									<div class="font-size-12 line-height-18">上架日期</div>
									<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class='colwidth-12'  >
									<div class="font-size-12 line-height-18">物料类别</div>
									<div class="font-size-9 line-height-18">Type</div>
							   </th>
							   <th class='colwidth-12'  >
							   	<div class="important">
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">Manufacturer No.</div>
									</div>
							   </th>
							    <th class='colwidth-12'  >
							    	<div class="important">
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
									</div><div class="important">
							   </th>
							    <th class='colwidth-12' >
							    <div class="important">
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Specifications</div>
									</div>
							   </th>
							    <th class='colwidth-12'  >
							    	<div class="important">
									<div class="font-size-12 line-height-18">制造厂商</div>
									<div class="font-size-9 line-height-18">Manufacturer</div>
									</div>
							   </th>
							   <th class='colwidth-7'>
									<div class="font-size-12 line-height-18">器材状态</div>
									<div class="font-size-9 line-height-18">Status</div>
							   </th>
							</tr> 
						</thead>
						<tbody id="stock_open_alert_stocklist">
							
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="stock_open_alert_pagination">
				</div>
				</div>
		        </div>
	            <div class='clearfix'></div>          
           	<div class='clearfix'></div>  
           	</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	               
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button id="save_btn" type="button" onclick="stock_open_alert.setData()" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">确认</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" onclick="stock_open_alert.close()" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/scrapped/apply/stock_choose.js"></script>
