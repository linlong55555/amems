<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/choosePn.js"></script>
<div class="modal fade modal-new" id="open_win_choosePnModal" tabindex="-1" role="dialog"  aria-labelledby="open_win_choosePnModal" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:65%;'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-12 ">部件列表</div>
							<div class="font-size-9">Part List</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
                        <div class="col-xs-12 padding-left-0 padding-leftright-8 margin-top-0 modalSearch">	
									<div class="col-xs-12 input-group">
									<div class="input-group-addon" style='padding-left:0px;padding-right:10px;padding-bottom:0px;padding-top:0px;background:none;border:0px;' >
				                    	<div class="font-size-12">部件</div>
										<div class="font-size-9">Part</div>
				                    </div>
									<input type="text" placeholder='件号/英文名称/中文名称/型号'  class="form-control" id="open_win_choosePnModal_keyword_search" >
				                    <div class="input-group-addon" style='padding-left:10px;padding-right:0px;padding-bottom:0px;padding-top:0px;background:none;border:0px;'>
				                    	<button type="button" name="keyCodeSearch" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_choosePnModal.search();" style='margin-right:0px !important;'>
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
				                  		</button>
				                    </div>
				                     <div class="input-group-addon" style='padding-left:10px;padding-right:0px;padding-bottom:0px;padding-top:0px;background:none;border:0px;'>
				                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_choosePnModal.reset();" >
										<div class="font-size-12">重置</div>
										<div class="font-size-9">Reset</div>
				                  		</button>
				                    </div>
								</div>
							</div>
		            	
			         		<div class="clearfix"></div>
			         		
						<!-- start:table -->
						<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
							<div style="overflow-x:auto;padding-right:3px;" id="leftPNTable" class='col-xs-6 padding-left-0 ' >
							   <div style='border:1px solid #d5d5d5;'>
								<table class="table table-thin table-striped table-hover table-set" style='margin-bottom:0px !important'>
									<thead>
								   		<tr>
								   			<th class=" colwidth-3">
												<div class="font-size-12 line-height-12">件号</div>
												<div class="font-size-9 line-height-12">PN</div>
											</th>
											<th class=" colwidth-3">
												<div class="font-size-12 line-height-12">型号</div>
												<div class="font-size-9 line-height-12">Model</div>
											</th>
											<th class="colwidth-10">
													<div class="font-size-12 line-height-12">名称(中文/英文)</div>
													<div class="font-size-9 line-height-12">Name</div>
											</th>
											
								 		 </tr>
									</thead>
									<tbody id="open_win_choosePnModal_leftlist">
																		
								    </tbody>
								</table>
								<div class="col-xs-12 text-center padding-left-0 padding-right-0 page-height " id="open_win_choosePnModal_leftpagination">
								</div>
								</div>
								</div>
								
								<div style="overflow-x:auto;padding-left:3px;" id="rightPNTable" class='col-xs-6  padding-right-0' >
								<div style='border:1px solid #d5d5d5;'>
								<table class="table table-thin  table-striped table-hover table-set" style='margin-bottom:0px !important'>
									<thead>
								   		<tr>
								   			<th  style='text-align:center;vertical-align:middle;width:30px;'>
												<div class="font-size-12 line-height-12">操作</div>
												<div class="font-size-9 line-height-12">Operation</div>
											</th>
											<th class="colwidth-5">
													<div class="font-size-12 line-height-12">序列号</div>
													<div class="font-size-9 line-height-12">SN</div>
												
											</th>
											<th class="colwidth-13">
													<div class="font-size-12 line-height-12">所在位置</div>
													<div class="font-size-9 line-height-12">Location</div>
											</th>
											
								 		 </tr>
									</thead>
									<tbody id="open_win_choosePnModal_rightlist">									
								    </tbody>
								</table>
								</div>
								</div>
							  <div class="clearfix"></div>  
							</div>
							<div class="margin-left-8 margin-right-8" style='border:1px solid #d5d5d5;margin-top:18px;margin-bottom:8px;'>
							<p style='height:20px;margin-top:-10px;background:white;margin-bottom:0px;padding-top:0px;margin-left:8px;width:100px;text-align:center;'>本次已经选择</p>
							<div style="overflow-x:auto;" id='bottomPNTable'>
								<table class="table table-thin table-striped table-hover table-set" style='margin-bottom:0px !important;'>
									<thead>
								   		<tr>
								   		    <th width='50'>
												<div class="font-size-12 line-height-12">操作</div>
												<div class="font-size-9 line-height-12">Operation</div>
											</th>
								   			<th >
												<div class="font-size-12 line-height-12">件号</div>
												<div class="font-size-9 line-height-12">PN</div>
											</th>
											<th >
													<div class="font-size-12 line-height-12">名称(中文/英文)</div>
													<div class="font-size-9 line-height-12">Name</div>
											</th>
											<th >
											<div class="font-size-12 line-height-12">型号</div>
													<div class="font-size-9 line-height-12">Model</div>
											</th>
											<th >
													<div class="font-size-12 line-height-12">序列号</div>
													<div class="font-size-9 line-height-12">SN</div>						
											</th>
											<th >											
													<div class="font-size-12 line-height-12">所在位置</div>
													<div class="font-size-9 line-height-12">Location</div>												
											</th>
											
								 		 </tr>
									</thead>
									<tbody id="open_win_choosePnModal_downlist">
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
		                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
					<span class="input-group-addon modalfooterbtn">
                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1"
                       onclick="open_win_choosePnModal.save()">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
	                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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