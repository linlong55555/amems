<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/inventory_distribution_details_view.js"></script>
<div class="modal fade in modal-new" id="open_win_inventory_distribution_details" tabindex="-1" role="dialog"  aria-labelledby="open_win_access" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 40%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">库存分布详情</div>
						<div class="font-size-9">Inventory Distribution Details</div>
				 	</div>
					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
           	<div class="modal-body" style='padding-top:0px;'>
             	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
                   	
                   	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">件号</div>
							<div class="font-size-9">P/N</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<input class="form-control" id="bjh_v" type="text" readonly="readonly"/>
						</div>
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">型号</div>
							<div class="font-size-9">Model</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<input class="form-control" id="xh_v" type="text" readonly="readonly"/>
						</div>
					</div>
					<div class="clearfix"></div>
	            	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">名称</div>
							<div class="font-size-9">Name</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<input class="form-control" id="mc_v" type="text" readonly="readonly"/>
						</div>
					</div>
	            	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">单位</div>
							<div class="font-size-9">Unit</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
							<input class="form-control" id="dw_v" type="text" readonly="readonly"/>
						</div>
					</div>
	            	
		         	<div class="clearfix"></div>
		         		
					<!-- start:table -->
					<div class="margin-left-8 margin-right-8" style='margin-top:5px;margin-bottom:5px;'>
						<div class='table-content col-xs-12 padding-left-0 padding-right-0' id="open_win_inventory_distribution_details_top_div">
							<table id="open_win_inventory_distribution_details_table" class="table table-thin table-bordered table-striped table-hover table-set">
								<thead>
							   		<tr>
							   			<th class="colwidth-3">
											<div class="font-size-12 line-height-12">序号</div>
											<div class="font-size-9 line-height-12">No.</div>
										</th>
										<th>
											<div class="font-size-12 line-height-12">仓库</div>
											<div class="font-size-9 line-height-12">Warehouse</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">库位</div>
											<div class="font-size-9 line-height-12">Stock Location</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-12">库存数量</div>
											<div class="font-size-9 line-height-12">QTY</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="open_win_inventory_distribution_details_list">
								</tbody>
							</table>
						</div>
						<div class="clearfix"></div>  
					</div>
				</div>
               <div class="clearfix"></div>              
          </div>
          <div class="clearfix"></div>  
          <div class="modal-footer">
          		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
	                    	<button onclick="open_win_inventory_distribution_details.close();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
					    	</button>
	                   	</span>
              		</div><!-- /input-group -->
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->