<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade modal-new in active" id="alertModalZl" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog" >
	<div class="modal-content">
		<div class="modal-header modal-header-new">
			<h4 class="modal-title">
	            <div class="pull-left">
	                <div class="font-size-12">工单信息</div>
					<div class="font-size-9">Instruction info</div>
		  		</div>
		  		<div class="pull-right">
				  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				</div>
		  		<div class="clearfix"></div>
            </h4>
		</div>
		<div class="modal-body padding-bottom-0 margin-top-10" >
			<div class="panel panel-primary">
				<div class="panel-body padding-top-0 padding-bottom-0">
					   <div  class="col-xs-12 margin-top-0  padding-right-0" >
						   <div class=" pull-right padding-left-0 padding-right-0 margin-top-10 form-group">
							     <div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;">
								   <input type="text" placeholder="工单编号" id="keyword_search1" class="form-control ">
								   <input type="hidden" id="gdid">
			                       </div>
			                       
			                      <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="alertModalZl.search()();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                     </div>
						   </div>
		               </div>
			           
		                <div class="clearfix"></div>	     
						<div  style="overflow-x: auto;" >
							<table class="table-set table table-thin table-bordered table-striped table-hover " style='margin-bottom:0px !important;'>
								<thead>
									<tr>
									<th class="colwidth-3"><div class="font-size-12 line-height-18" >序号</div>
										<div class="font-size-9 line-height-18" >No.</div></th>
									<th class="colwidth-13">	
									<div class="important">
									<div class="font-size-12 line-height-18">工单编号</div>
									<div class="font-size-9 line-height-16">Instruction No.</div>
									</div>											
										
									</th>
									<th class="colwidth-13">												
										<div class="font-size-12 line-height-18">来源分类</div>
										<div class="font-size-9 line-height-16">Type</div>
									</th>
									<th class="colwidth-13">												
										<div class="font-size-12 line-height-18">工单主题</div>
										<div class="font-size-9 line-height-16">Title</div>
									</th>
									<th class="colwidth-13">												
										<div class="font-size-12 line-height-18">任务号</div>
										<div class="font-size-9 line-height-16">No.</div>
									</th>
									<th class="colwidth-15">												
										<div class="font-size-12 line-height-18">制单日期</div>
										<div class="font-size-9 line-height-16">Date</div>
									</th>
									<th class="colwidth-15">												
										<div class="font-size-12 line-height-18">计划结束日期</div>
										<div class="font-size-9 line-height-16">End Date</div>
									</th>
									<th class="colwidth-13">												
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-16">Status</div>
									</th>
									</tr> 
				         		 </thead>
								<tbody id="zlhlist">
								</tbody>
							</table>
							</div>
							<div class="col-xs-12 text-center" id="zlhpagination">
							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group " style='border-top:1px solid #d5d5d5;margin-top:5px;padding-top:8px;'>
								<div class="col-xs-12 input-group">
							       <div class="input-group-addon" style='background:none;width:50px;padding-right:8px;color:#333;border:0px;text-align:right'>
				                       <div class="font-size-12 margin-topnew-2">工单编号</div>
									   <div class="font-size-9 line-height-9">W/O No.</div>
				                    </div>
				                    <input class="form-control" id="alertModalZl_selectzlh" type="text"  />
		                       </div>
							</div>
			     		<div class="clearfix"></div>
				 	 </div>
				 </div> 
			</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
						<span class="input-group-addon modalfootertip">
						</span>
	                    <span class="input-group-addon modalfooterbtn">
		                   		<button type="button" onclick="alertModalZl.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/planefaultmonitoring/zlh.js"></script><!--  -->
