<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<link rel="stylesheet" href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/user_multi.js"></script>
<style type="text/css">
.bootstrap-tagsinput {
  width: 100% !important;
}
</style>
<div class="modal fade" id="UserMultiModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:750px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="UserMultiModalBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">用户列表</div>
						<div class="font-size-9 ">User List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
						    <!-- <div class="text-left margin-top-10">
						    	<div class="col-lg-10 col-xs-12 col-sm-4  padding-left-0 padding-right-0 margin-bottom-0 ">
									<span class="pull-left col-lg-2 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">用户名称</div>
										<div class="font-size-9">User Name</div>
									</span>
									<div class="col-lg-10 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="um_realname_search" />
										<input type="hidden" class="form-control " id="um_state" value="1"/>
									</div>
								</div>
								<button type="button" onclick="UserMultiModal.search()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									 style="float: left; margin-left: 10px;">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
								</button>
								<div class="clearfix"></div>			
			         		</div> -->
			         		
			         		<!-- 搜索框start -->
							<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">
								<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
									<input type="text" placeholder='用户名称' id="um_realname_search" class="form-control ">
								</div>
				                <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button   name="keyCodeSearch"  type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="UserMultiModal.search()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                  		</button>
				                </div> 
							</div>
							<!-- 搜索框end -->
			         		<div class="clearfix"></div>
			         		
							<!-- start:table -->
							<div class="margin-top-10 ">
							<div class="overflow-auto">
								<table class="table table-bordered table-striped table-hover text-left" style="">
									<thead>
								   		<tr>
								   			<th>
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th>
												<div class="important">
													<div class="font-size-12 notwrap">用户名称</div>
													<div class="font-size-9 notwrap">User Name</div>
												</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">机构部门</div>
												<div class="font-size-9 notwrap">Department</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="um_userlist">
									
									</tbody>
								</table>
								<div class=" col-xs-12  text-center " style="margin-top: 0px; margin-bottom: 0px;" id="um_pagination"></div>
								</div>
							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
									<div class="font-size-12 line-height-18">用户名称</div>
									<div class="font-size-9 line-height-18">User Name</div>
								</label>
								<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
									<input class="form-control" id="um_selectUser" readonly />
								</div>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="UserMultiModal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" onclick="UserMultiModal.clearUser()" id="UserMultiModal_btn_clear"
										class="btn btn-primary padding-top-1 padding-bottom-1">
									<div class="font-size-12">清空</div>
									<div class="font-size-9">Clear</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
			                </div>
			     			<div class="clearfix"></div>
						</div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
</div>
