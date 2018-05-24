<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/componenthistory/certificate_main.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<div class="modal fade in modal-new" id="history_certificate_alert_Modal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="open_win_evaluation_modal" aria-hidden="true">
	<div class="modal-dialog" style="width:50%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
                          	<div class="font-size-12 ">证书信息</div>
							<div class="font-size-9">Certificate Info</div>
						  </div>
						   <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
			  <div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
						<!-- start:table -->
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
							<div class="table-content" style="overflow-x:auto;" id="searchTable">
								<table class="table table-bordered table-striped table-hover table-set table-thin" >
									<thead>
								   		<tr>
											<th class="downward fixed-column colwidth-8 " onclick="history_certificate_alert_Modal.toggleAll()" name="ope_td">
												<div class="font-size-12 line-height-18">操作</div>
				           						<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">证书类型</div>
												<div class="font-size-9 line-height-18">Certificate Type</div>
											</th>
											<th class="colwidth-13 ">
												<div class="font-size-12 line-height-18">证书编号</div>
												<div class="font-size-9 line-height-18">Certificate No</div>
											</th>
											<th class="colwidth-10 ">
												<div class="font-size-12 line-height-18">存放位置</div>
												<div class="font-size-9 line-height-18">Location</div>
											</th>
											<th class="colwidth-13 ">
											   <div class="font-size-12 line-height-18">签署日期</div>
									           <div class="font-size-9 line-height-18">Date</div>
										   	</th>
										   	<th class="colwidth-10 ">
											   <div class="font-size-12 line-height-18">附件</div>
									           <div class="font-size-9 line-height-18">File</div>
										   	</th>
								 		 </tr>
									</thead>
									<tbody id="certigicateList">
									
									</tbody>
								</table>
								</div>
								<!-- <div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="certificatePagination"></div>  -->
							 <div class="clearfix"></div> 
							</div>
							</div>
							<div class="clearfix"></div>  
							</div>
							<!-- end:table -->
			                  <div class="modal-footer">
				           		<div class="col-xs-12 padding-leftright-8" >
									<div class="input-group">
					                    <span class="input-group-addon modalfooterbtn">
					                    </span>
					               	</div><!-- /input-group -->
								</div>
				           
								<div class="clearfix"></div> 
								
							</div>
		</div>
	</div>
</div>
