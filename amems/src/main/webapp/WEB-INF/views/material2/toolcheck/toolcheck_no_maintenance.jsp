<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="alertModalNoMaintenance" tabindex="-1" role="dialog"  aria-labelledby="alertModalHb" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width:70%;">
		<div class="modal-content">
			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
	               	<div class="pull-left">
	                   	<div class="font-size-12 ">未维护数据</div>
						<div class="font-size-9 ">NoMaintenance Data</div>
					</div>
				  	<div class="pull-right">
		  		  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
		  		  	</div>
				  	<div class="clearfix"></div>
	             </h4>
		    </div>
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
				<div class="panel panel-primary margin-top-10">
					<div class="panel-body padding-top-0 padding-bottom-0">
					  	<div  class="col-xs-12 padding-left-0 padding-right-0 margin-top-0 modalSearch" >
							<div class=" pull-left padding-left-0 padding-right-0 margin-top-10">
								<a href="javascript:exportTool();"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left" >
							    <div class="font-size-12">导出</div>
							    <div class="font-size-9">Export</div>
						        </a>
		               		</div>					  	
							<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">
								<div class="pull-left padding-left-0 padding-right-0" style="width:250px;">
								   <input type="text" placeholder="件号,英文名称,中文名称,序列号,批次号,型号,规格" id="keyword_no_maintenance_search" class="form-control ">
								</div>
		                     	<div class=" pull-right padding-left-5 padding-right-0 ">   
									<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="NoMaintenanceModal.search();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			               		</div>
		               		</div>
		              	</div>
					   	<div class="clearfix"></div>	     
						<div  style="overflow-x: auto;margin-top: 10px">
							<table class="table-set table table-thin table-bordered table-striped table-hover ">
								<thead>
									<tr>
										<th class="colwidth-5"><div class="font-size-12 line-height-18" >选择</div>
											<div class="font-size-9 line-height-18" >Choice</div>
										</th>
										<th class="colwidth-15">
										<div class="important">
											<div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
										</div>
										</th>
										<th class="colwidth-20">
										<div class="important">
												<div class="font-size-12 line-height-18">英文名称</div>
												<div class="font-size-9 line-height-18">English Desc</div>
										</div>
										</th>
										<th class="colwidth-15">
										<div class="important">
											<div class="font-size-12 line-height-18">中文名称</div>
											<div class="font-size-9 line-height-18">Chinese Desc</div>
										</div>
										</th>
										<th class="colwidth-10">
										<div class="important">
											<div class="font-size-12 line-height-18">序列号</div>
											<div class="font-size-9 line-height-18">SN</div>
										</div>
										</th>
										
										<th class="colwidth-10">
										<div class="important">
											<div class="font-size-12 line-height-18">批次号</div>
											<div class="font-size-9 line-height-18">Batch No</div>
										</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">航材类型</div>
											<div class="font-size-9 line-height-18">Type</div>
										</th>									
										 <th class="colwidth-10">
										 <div class="important">
											<div class="font-size-12 line-height-18">型号</div>
											<div class="font-size-9 line-height-18">Model</div>
										</div>
										</th>
										<th class="colwidth-10">
										<div class="important">
											<div class="font-size-12 line-height-18">规格</div>
											<div class="font-size-9 line-height-18">Spec</div>
										</div>
										</th> 
										
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">在库数量</div>
											<div class="font-size-9 line-height-18">Qty</div>
										</th> 
										
										<th class="colwidth-5">
											<div class="font-size-12 line-height-18">单位</div>
											<div class="font-size-9 line-height-18">Unit</div>
										</th> 
										
									</tr> 
				         		 </thead>
								 <tbody id="NoMaintenanceList">
								 </tbody>
							</table>
						</div>
						<div class="col-xs-12 text-center" id="NoMaintenanceListPagination">
						</div>
						<!-- end:table -->
			     		<div class="clearfix"></div>
				 	 </div>
				 </div> 
			</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
						<span class="input-group-addon modalfootertip">
							<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
						</span>
	                    <span class="input-group-addon modalfooterbtn">
		                   		<button type="button" onclick="NoMaintenanceModal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1">
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

<script type="text/javascript" src="${ctx}/static/js/thjw/material2/toolcheck/toolcheck_no_maintenance.js"></script>