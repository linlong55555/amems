<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script src="${ctx}/static/js/thjw/quality/postapplication/post_list.js"></script>
<div class="modal modal-new"  id="Post_Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
		    <div class="modal-header modal-header-new">
				<h4 class="modal-title" >
                     	<div class='pull-left'>
                    		<div class="font-size-12" >岗位列表</div>
					<div class="font-size-9" >Post List</div>
		  		</div>
		  		<div class='pull-right' >
				  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				</div>
		  		<div class='clearfix'></div>
              	</h4>
			</div>
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			   <div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
                       <div class="col-xs-12 padding-left-0 padding-leftright-8 margin-top-0 modalSearch">	
							<div class=" pull-right padding-left-0 padding-right-0 margin-top-10">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='岗位编号名称/备注' id="u_realname_search1" class="form-control ">
							</div>
			                <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="post_list.search()">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                  		</button>
			                </div> 
						</div>
						</div>
		            	
		         		<div class="clearfix"></div>
		         		<div class="margin-top-10 padding-leftright-8 ">
						<div class="overflow-auto">
							<table class="table table-bordered table-striped table-hover text-center table-set" style="">
								<thead>
							   		<tr>
										<th width="50px">
											<div class="font-size-12 notwrap">选择</div>
											<div class="font-size-9 notwrap">Choice</div>
										</th>
										<th>
											<div class="important">
												<div class="font-size-12 notwrap">岗位编号</div>
												<div class="font-size-9 notwrap">Post No.</div>
											</div>
										</th>
										<th>
											<div class="important">
												<div class="font-size-12 notwrap">岗位名称</div>
												<div class="font-size-9 notwrap">Post Name</div>
											</div>
										</th>
										<th>
											<div class="important">
												<div class="font-size-12 notwrap">备注</div>
												<div class="font-size-9 notwrap">Note</div>
											</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="postlist">
								
								</tbody>
							</table>
							</div>
							<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="post_pagination"></div>
						</div>
						 <div class='clearfix'></div>
			     </div>
			     <div class='clearfix'></div>
			</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
						</span>
	                    <span class="input-group-addon modalfooterbtn">
		                   <button type="button" onclick="post_list.setUser()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" onclick="post_list.clearUser()" id="Post_Modal_btn_clear"
										class="btn btn-primary padding-top-1 padding-bottom-1">
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
