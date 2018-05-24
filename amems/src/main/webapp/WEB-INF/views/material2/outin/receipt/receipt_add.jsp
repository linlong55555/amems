<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="open_win_receipt_info" tabindex="-1" role="dialog"  aria-labelledby="receipt_add_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:80%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">信息</div>
							<div class="font-size-12" id="modalHeadENG">Info</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
           		<div class="col-xs-12 margin-top-8 margin-bottom-8">
            	<ul class="nav nav-tabs tabNew-style" role="tablist" style="border-radius:0px;padding-top:0px !important;">
	            	<li role="presentation" class="active">
	            		<a href="#receipt_origin" data-toggle="tab" id="receipt_tab_origin">来源 Origin</a>
	            	</li>
	            	<li role="presentation">
	            		<a href="#receipt_materiel" data-toggle="tab"  id="receipt_tab_materiel">物料 Materiel</a>
	            	</li>
            	</ul>
            	<div class="tab-content " style="border-radius:0px">
            	<div class="tab-pane fade in active" id="receipt_origin">
            	<div style='margin-bottom:8px;'>
            	 <div class=" pull-right padding-left-0 padding-right-0">
            	 	 <div class="pull-left text-right padding-left-0 padding-right-0">
						<div class="font-size-12">物料类别</div>
						<div class="font-size-9">Material Type</div>
					 </div>
					 <div class="pull-left text-right padding-left-0 padding-right-0">
						<div class="padding-left-8 pull-left" style="width: 150px; margin-right:5px;">
						   <select id="open_win_receipt_info_source_hclx" class="form-control" onchange="open_win_receipt_info.load()">
						   </select> 
						</div>
					 </div>
					 <div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
					 	<input placeholder="件号/序列号/供应商/合同编号/产权" id="open_win_receipt_info_keyword_search" class="form-control" type="text">
					 </div>
		             <div class=" pull-right padding-left-5 padding-right-0 ">   
						 <button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_receipt_info.load()">
							 <div class="font-size-12">搜索</div>
							 <div class="font-size-9">Search</div>
			              </button>
		              </div> 
	              </div>
	              <div class='clearfix'></div>
	              </div>
            	  <div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 " style="overflow-x: auto;margin-bottom:8px;">
					    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
							    <th class='colwidth-5'>
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >物料类别</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class='colwidth-15' onclick="" name="">
									<div class="important">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="important">
										<div class="font-size-12 line-height-18">序列号</div>
										<div class="font-size-9 line-height-18">S/N</div>
									</div>
								</th>
								<th class='colwidth-20' onclick="" name="">
									<div class="font-size-12 line-height-18">名称/描述</div>
									<div class="font-size-9 line-height-18">Name/Desc</div>
								</th>
								<th class='colwidth-5' onclick="" name="" >
									<div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Count</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Spec</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">件号来源</div>
									<div class="font-size-9 line-height-18">P/N Source</div>
								</th>
								<th class='colwidth-15' onclick="" name="" >
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">标准件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="important">
										<div class="font-size-12 line-height-18">产权</div>
										<div class="font-size-9 line-height-18">Right</div>
									</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="important">
										<div class="font-size-12 line-height-18">合同编号</div>
										<div class="font-size-9 line-height-18">Contract No.</div>
									</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="important">
										<div class="font-size-12 line-height-18">供应商</div>
										<div class="font-size-9 line-height-18">Supplier</div>
									</div>
								</th>
							</tr> 
						</thead>
						<tbody id="open_win_receipt_info_list">
						</tbody>
					</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_receipt_info_pagination">
					</div>
            	</div>
            	<div class="tab-pane fade" id="receipt_materiel">
            	<div style='margin-bottom:8px;'>
            	 <div class=" pull-right padding-left-0 padding-right-0">
            	 	<div class="pull-left text-right padding-left-0 padding-right-0">
						<div class="font-size-12">物料类别</div>
						<div class="font-size-9">Material Type</div>
					 </div>
					 <div class="pull-left text-right padding-left-0 padding-right-0">
						<div class="padding-left-8 pull-left" style="width: 150px; margin-right:5px;">
						   <select id="open_win_receipt_info_material_hclx" class="form-control" onchange="open_win_receipt_info_material.load()">
						   </select> 
						</div>
					 </div>
					 <div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
					 	<input placeholder="件号/名称/厂家件号" id="open_win_receipt_info_material_keyword_search" class="form-control" type="text">
					 </div>
		             <div class=" pull-right padding-left-5 padding-right-0 ">   
						 <button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="open_win_receipt_info_material.load()">
							 <div class="font-size-12">搜索</div>
							 <div class="font-size-9">Search</div>
			              </button>
		              </div> 
	              </div>
	              <div class='clearfix'></div>
	              </div>
            	 <div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 " style="overflow-x: auto;margin-bottom:8px;">
					    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
						<thead>                              
							<tr>
							    <th class='colwidth-5'>
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class='colwidth-7'>
									<div class="font-size-12 line-height-18" >物料类别</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class='colwidth-15' onclick="" name="">
									<div class="important">
										<div class="font-size-12 line-height-18">件号</div>
										<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
								<th class='colwidth-20' onclick="" name="">
									<div class="important">
										<div class="font-size-12 line-height-18">名称/描述</div>
										<div class="font-size-9 line-height-18">Name/Desc</div>
									</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="important">
										<div class="font-size-12 line-height-18">厂家件号</div>
										<div class="font-size-9 line-height-18">MP/N</div>
									</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">规格</div>
									<div class="font-size-9 line-height-18">Spec</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">件号来源</div>
									<div class="font-size-9 line-height-18">P/N Source</div>
								</th>
								<th class='colwidth-15' onclick="" name="" >
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</th>
								<th class='colwidth-10' onclick="" name="" >
									<div class="font-size-12 line-height-18">标准件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
								</th>
							</tr> 
						</thead>
						<tbody id="open_win_receipt_info_material_list">
						</tbody>
					</table>
					</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="open_win_receipt_info_material_pagination">
					</div>
            	</div>
            	</div>
            	</div>
	             <div class='clearfix'></div>          
           	</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info' style="display: none;"></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button type="button" onclick="open_win_receipt_info.save()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
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
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/receipt/receipt_info_origin.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/receipt/receipt_info_material.js"></script>