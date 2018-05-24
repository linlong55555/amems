<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new active in" id="alertModalHb" tabindex="-1" role="dialog" aria-labelledby="alertModalHb" aria-hidden="true">
	<div class="modal-dialog" >
		<div class="modal-content">
			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
	               	<div class="pull-left">
	                   	<div class="font-size-12 ">航班信息</div>
						<div class="font-size-9 ">Flight Info</div>
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
							<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">
								<div class="pull-left padding-left-0 padding-right-0" style="width:250px;">
								   <input type="text" placeholder="航班号" id="keyword_search" class="form-control ">
								</div>
		                     	<div class=" pull-right padding-left-5 padding-right-0 ">   
									<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="hbModal.search();">
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
										<th class="colwidth-3"><div class="font-size-12 line-height-18" >选择</div>
											<div class="font-size-9 line-height-18" >Choice</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">飞行日期</div>
											<div class="font-size-9 line-height-16">Flight Date</div>
										</th>
										<th class="colwidth-10">
											<div class="important">
												<div class="font-size-12 line-height-18">航班号</div>
												<div class="font-size-9 line-height-16">Flight No.</div>
											</div>
										</th>
										<th class="colwidth-8">
											<div class="font-size-12 line-height-18">任务类型</div>
											<div class="font-size-9 line-height-16">Flight No.</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">起飞站</div>
											<div class="font-size-9 line-height-16">Flight No.</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">着陆站</div>
											<div class="font-size-9 line-height-16">Flight No.</div>
										</th>
										<th class="colwidth-12">
											<div class="font-size-12 line-height-18">开车</div>
											<div class="font-size-9 line-height-16">Flight No.</div>
										</th>
										<th class="colwidth-12">
											<div class="font-size-12 line-height-18">起飞</div>
											<div class="font-size-9 line-height-16">Flight No.</div>
										</th>
										<th class="colwidth-12">
											<div class="font-size-12 line-height-18">落地</div>
											<div class="font-size-9 line-height-16">Flight No.</div>
										</th>
										<th class="colwidth-12">
											<div class="font-size-12 line-height-18">停车</div>
											<div class="font-size-9 line-height-16">Flight No.</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">飞行记录号</div>
											<div class="font-size-9 line-height-16">Flight No.</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">记录本页码</div>
											<div class="font-size-9 line-height-16">Flight No.</div>
										</th>
									</tr> 
				         		 </thead>
								 <tbody id="Hblist">
								 </tbody>
							</table>
						</div>
						<div class="col-xs-12 text-center" id="hbpagination">
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
		                   		<button type="button" onclick="hbModal.setUser()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/planefaultmonitoring/hbh.js"></script><!--  -->