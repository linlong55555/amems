<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="alertModalCq" tabindex="-1" role="dialog"  aria-labelledby="alertModalHb" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" >
		<div class="modal-content">
			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
	               	<div class="pull-left">
	                   	<div class="font-size-12 ">产权信息</div>
						<div class="font-size-9 ">CQ Info</div>
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
								   <input type="text" placeholder="产权编号" id="keyword_material_search" class="form-control ">
								</div>
		                     	<div class=" pull-right padding-left-5 padding-right-0 ">   
									<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="cqModal.search();">
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
										<th class="colwidth-12">
										<div class="important">
											<div class="font-size-12 line-height-18">产权编号</div>
											<div class="font-size-9 line-height-16">Property Right No.</div>
										</div>
										</th>
										<th class="colwidth-6">
												<div class="font-size-12 line-height-18">飞机注册号</div>
												<div class="font-size-9 line-height-16">A/C REG</div>
										</th>
										<th class="colwidth-12">
											<div class="font-size-12 line-height-18">公司名称</div>
											<div class="font-size-9 line-height-16">Company Name</div>
										</th>
										<th class="colwidth-12">
											<div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-16">Note</div>
										</th>
										<!--
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">维护人</div>
											<div class="font-size-9 line-height-16">Maintenance Person.</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-18">组织机构</div>
											<div class="font-size-9 line-height-16">Organization.</div>
										</th>									
										  -->
										
									</tr> 
				         		 </thead>
								 <tbody id="cqlist">
								 </tbody>
							</table>
						</div>
						<div class="col-xs-12 text-center" id="cqpagination">
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
		                   		<button type="button" onclick="cqModal.setCq()" class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button id="jswj_btn_clear" type="button" onclick="cqModal.clearCq()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">清空</div>
									<div class="font-size-9">Clear</div>
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
<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/material_cq.js"></script><!--  -->