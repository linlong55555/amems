<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="tracking_open_alert" tabindex="-1" role="dialog"  aria-labelledby="tracking_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:80%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">已关闭需求</div>
							<div class="font-size-12" id="modalHeadENG">Closed demand</div>
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
		            <div class='row-height' >
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">飞机注册号</div>
							<div class="font-size-9 ">A/C Reg</div>
						</span>
						<div id="tracking_open_alert_fjzchDiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0">
							<select  id="tracking_open_alert_fjzch"  class="form-control " onchange="tracking_open_alert.search()">
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">需求类别</div>
							<div class="font-size-9">Type</div>
						</span>
						<div id="tracking_open_alert_xqlbDiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0 label-input-div" >
							<select id="tracking_open_alert_xqlb" class="form-control " onchange="tracking_open_alert.search()" >
							</select>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='需求原因/故障描述'  class="form-control" id="tracking_open_alert_keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="tracking_open_alert.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="moreSearchModal();">
								<div class='input-group'>
								<div class="input-group-addon">
								<div class="font-size-12">更多</div>
								<div class="font-size-9 margin-top-5" >More</div>
								</div>
								<div class="input-group-addon">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="iconModal"></i>
								</div>
								</div>
					   			</button>
		                	</div>
						</div>
					</div>
					<!-- 搜索框end -->
				  <div class='clearfix'></div>
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearchModal">
					
							<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">影响放行</div>
							<div class="font-size-9">Release</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input name="tracking_open_alert_isYxfx_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="1" checked="checked" />&nbsp;是
								&nbsp;
							<input name="tracking_open_alert_isYxfx_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="0"  checked="checked"  />&nbsp;否 
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">非计划停场</div>
							<div class="font-size-9">Unplanned P</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input name="tracking_open_alert_isFjhtc_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="1" checked="checked" />&nbsp;是
								&nbsp;
							<input name="tracking_open_alert_isFjhtc_search" style=" vertical-align: middle;   margin-top: -1px;" type="checkbox" value="0"  checked="checked"  />&nbsp;否 
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">计划使用日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' id='tracking_open_alert_jhsyrq_search' class='form-control' name='date-range-picker'/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">提报日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' id='tracking_open_alert_sqsj_search' class='form-control' name='date-range-picker'/>
						</div>
					</div>
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="tracking_open_alert.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <!-- 需求追踪 -->
		   <div  class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-set" style="overflow-x: auto;">
					<table id="work_card_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1400px;">
						<thead>                              
							<tr>
								<th class='colwidth-5'>
									<div class="font-size-12 line-height-18" >编号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class='colwidth-9'>
									<div class="font-size-12 line-height-18" >需求类别</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class='colwidth-5'>
									<div class="font-size-12 line-height-18">计划使用日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class='colwidth-5'>
										<div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C Reg</div>
								</th>
								<th class='colwidth-5' >
									<div class="font-size-12 line-height-18">影响放行</div>
									<div class="font-size-9 line-height-18">Release</div>
								</th>
								<th class='colwidth-6' >
									    <div class="font-size-12 line-height-18">非计划停场</div>
										<div class="font-size-9 line-height-18">Unplanned Parking</div>
							    </th>
								<th class='colwidth-9' >
									<div class="important">
									<div class="font-size-12 line-height-18">需求原因/故障描述</div>
									<div class="font-size-9 line-height-18">Reason/Description</div>
									</div>
								</th>
								<th class='colwidth-9' >
										<div class="font-size-12 line-height-18">购买建议/其它说明</div>
										<div class="font-size-9 line-height-18">Advice/Instructions</div>
							   </th>
								<th class='colwidth-9' >
									<div class="font-size-12 line-height-18">提报时间</div>
									<div class="font-size-9 line-height-18">Reporting time</div>
								</th>
								
							</tr> 
						</thead>
						<tbody id="tracking_open_alert_list">
							
						</tbody>
					</table>
				</div>
				 <div class='clearfix'></div>   
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="tracking_open_alert_pagination">
				</div>
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
<script type="text/javascript" src="${ctx }/static/js/thjw/material2/demand/tracking/tracking_open_close.js"></script>