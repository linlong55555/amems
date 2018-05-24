<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script src="${ctx}/static/js/thjw/training/plan/teacher_user.js"></script>
<div class="modal modal-new"  id="lectureruserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
		    <div class="modal-header modal-header-new">
				<h4 class="modal-title" >
                     	<div class='pull-left'>
                    		<div class="font-size-12" >讲师列表</div>
					<div class="font-size-9" >Lecturer List</div>
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
							
							<label class=" pull-left padding-left-0 padding-right-10" style='margin-top:6px;margin-left:5px;font-weight:normal;' >
								授权：<input onclick="lecturer_user.search()" id='sqrqcheck' name='sqrqcheck' type='checkbox' style='vertical-align:text-bottom' />
							</label>
							
							<div class=" pull-left padding-left-10 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='教员编号/姓名/联系电话/电子邮箱' id="u_realname_search" class="form-control ">
							</div>
			                <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="lecturer_user.search()">
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
											<div class="font-size-12 line-height-18">选择</div>
											<div class="font-size-9 line-height-18">Choice</div>
										</th>
									<th class="colwidth-10 " >
										<div class="important">
											<div class="font-size-12 line-height-18">教员编号</div>
											<div class="font-size-9 line-height-18">Faculty No.</div>
										</div>
									</th>
									<th class="colwidth-10 " >
										<div class="important">
											<div class="font-size-12 line-height-18">姓名</div>
											<div class="font-size-9 line-height-18">Name</div>
										</div>
									</th>
									<th class="colwidth-5 " >
										<div>
											<div class="font-size-12 line-height-18">内/外</div>
											<div class="font-size-9 line-height-18">within/outside</div>
										</div>
									</th>
									<th class="colwidth-5 " >
										<div class="font-size-12 line-height-18">性别</div>
										<div class="font-size-9 line-height-18">Sex</div>
									</th>
									<th class="colwidth-10 " >
										<div class="important">
											<div class="font-size-12 line-height-18">联系电话</div>
											<div class="font-size-9 line-height-18">Phone</div>
										</div>
									</th>
									<th class="colwidth-10 " >
										<div class="important">
											<div class="font-size-12 line-height-18">电子邮箱</div>
											<div class="font-size-9 line-height-18">Email</div>
										</div>
									</th>
									<th class="colwidth-15" >
										<div class="font-size-12 line-height-18">家庭成员</div>
										<div class="font-size-9 line-height-18">Family</div>
									</th>
							 		 </tr>
								</thead>
								<tbody id="teacheruserlist">
								
								</tbody>
							</table>
							</div>
							<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="teacheruser_pagination"></div>
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
		                   <button type="button" onclick="lecturer_user.setUser()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" onclick="lecturer_user.clearUser()" id="lectureruserModal_btn_clear"
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
