<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="analysis_open_alert" tabindex="-1" role="dialog"  aria-labelledby="analysis_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:70%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">需求统计分析</div>
							<div class="font-size-12" id="modalHeadENG">Analysis of demand</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
	            <div class="col-xs-12 margin-top-8 ">
		            <div class="input-group-border padding-leftright-8" > 
		           <div class='searchContent margin-top-0 row-height' >
			<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
				<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 ">飞机注册号</div>
					<div class="font-size-9 ">A/C Reg</div>
				</span>
				<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
					<select id="a_fjzch" class="form-control" onchange="analysis_open_alert.search()">
						<option value="" selected="selected">显示全部All</option>
					</select>
				</div>
	        </div>
	         <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
	            <div class='padding-leftright-8 pull-right'>
				<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
					<input value="1" onchange="analysis_open_alert.search()" name="isWarning_search" style="vertical-align:text-bottom;" type="checkbox">&nbsp;缺件&nbsp;&nbsp;
				</label>
				<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
					<input value="0" onchange="analysis_open_alert.search()" name="isWarning_search" style="vertical-align:text-bottom;" type="checkbox">&nbsp;库存满足&nbsp;&nbsp;
				</label>
				</div>
	        </div>
	         <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group ">
				<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='部件号/部件名称'  class="form-control" id="analysis_open_alert_keyword_search" >
                    <div class="input-group-addon btn-searchnew-more" style='padding-left:5px !important;'>
	                   <button  name="keyCodeSearch" onclick="analysis_open_alert.search()" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" >
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                	</div>
				</div>
	        </div>
			<div class='padding-leftright-8 margin-bottom-5'>
			            需求分析的样本数：<span class='color-red margin-right-5' id="a_ybs">0</span>
			     A/C Reg数量：<span class='color-red margin-right-5' id="a_fjs">0</span>
			             部件数：<span class='color-red margin-right-5' id="a_bjs">0</span>
			</div>
			<div class='clearfix'></div>
			</div>
		
		   <!-- 需求追踪 -->
		   <div id="analysis_open_alert_table_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-set" style="overflow-x: auto;">
					<table id="" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 800px;">
						<thead>                              
							<tr>
								<th class='colwidth-3'>
									<div class="font-size-12 line-height-18" >序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th  class='colwidth-5'>
									<div class="font-size-12 line-height-18" >缺件</div>
									<div class="font-size-9 line-height-18">M/P</div>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18" >飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C Reg</div>
								</th>
								<th class='colwidth-13' onclick="" name="">
									<div class="important">
									<div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
								<th class='colwidth-20' onclick="" name="">
									<div class="important">
										<div class="font-size-12 line-height-18">部件名称</div>
										<div class="font-size-9 line-height-18">Name</div>
										</div>
								</th>
								<th class='colwidth-5' onclick="" name="" >
									<div class="font-size-12 line-height-18">需求数量</div>
									<div class="font-size-9 line-height-18">QTY</div>
								</th>
								<th class='colwidth-9' onclick="" name="" >
									    <div class="font-size-12 line-height-18">库存可用数量</div>
										<div class="font-size-9 line-height-18">QTY</div>
							    </th>
								<th class='colwidth-30' onclick="" name="" >
									<div class="font-size-12 line-height-18">互换信息</div>
									<div class="font-size-9 line-height-18">Swap Info</div>
								</th>
							</tr> 
						</thead>
						<tbody id="analysis_open_alert_Tbody">
						</tbody>
					</table>
				</div> 
				<div class='clearfix'></div>
		        </div>
		        
	            </div> 
	             <div class='clearfix'></div>          
           	</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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
<script type="text/javascript" src="${ctx }/static/js/thjw/material2/demand/materiallist/analysis_open.js"></script>
<%@ include file="/WEB-INF/views/open_win/inventory_distribution_details_view.jsp"%><!-------库存分布详情 -------->
<%@ include file="/WEB-INF/views/open_win/equivalent_substitution_view.jsp"%><!-------等效替代 -------->