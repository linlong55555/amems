<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script src="${ctx}/static/js/thjw/training/faculty/faculty_user.js"></script>
<div class="modal modal-new"  id="userModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
		    <div class="modal-header modal-header-new">
				<h4 class="modal-title" >
                     	<div class='pull-left'>
                    		<div class="font-size-12" >人员列表</div>
					<div class="font-size-9" >User List</div>
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
								<input type="text" placeholder='姓名' id="u_realname_search" class="form-control ">
							</div>
			                <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="faculty_user.search()">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                  		</button>
			                </div> 
						</div>
						</div>
		            	
		         		<div class="clearfix"></div>
		         		<div class="margin-top-10 padding-leftright-8 ">
						<div class="table-content">
							<table class="table table-bordered table-striped table-hover text-center table-set" >
								<thead>
							   		<tr>
										<th width="50px">
											<div class="font-size-12 notwrap">选择</div>
											<div class="font-size-9 notwrap">Choice</div>
										</th>
										<th>
											<div class="important">
												<div class="font-size-12 notwrap">姓名</div>
												<div class="font-size-9 notwrap">Name</div>
											</div>
										</th>
										<th>
											<div class="font-size-12 notwrap">归属</div>
											<div class="font-size-9 notwrap">Ascription</div>
										</th>
										<th width="50px">
											<div class="font-size-12 notwrap">性别</div>
											<div class="font-size-9 notwrap">Sex</div>
										</th>
										<th>
											<div class="font-size-12 notwrap">归属</div>
											<div class="font-size-9 notwrap">Ascription</div>
										</th>
										<th width="50px">
											<div class="font-size-12 notwrap">状态</div>
											<div class="font-size-9 notwrap">State</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="userlist">
								
								</tbody>
							</table>
							</div>
			<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="user_pagination"></div>
						</div>
						 <div class='clearfix'></div>
			     </div>
			     <div class='clearfix'></div>
			</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
						</span>
	                    <span class="input-group-addon modalfooterbtn">
		                   <button type="button" onclick="faculty_user.setUser()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
		                   <button id="userModal_btn_clear" type="button" onclick="faculty_user.emptied()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">清空</div>
									<div class="font-size-9">Emptied</div>
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
