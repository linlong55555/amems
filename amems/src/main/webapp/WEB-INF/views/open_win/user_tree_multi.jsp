<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/user_tree_multi.js"></script>
<link rel="stylesheet" href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<div class="modal fade in modal-new" id="User_Tree_Multi_Modal" tabindex="-1" role="dialog"  aria-labelledby="User_Tree_Multi_Modal" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 40%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">人员选择</div>
						<div class="font-size-9">User</div>
				 	</div>
 					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
           	<div class="modal-body" style='padding-top:0px;'>
             	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
                   	<div class=" pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch">	
       		
						<!-- 搜索框start -->
						<div class=" pull-right padding-left-0 padding-right-0">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='账号/姓名'  class="form-control" id="user_keyword_search" >
							</div>
		                   	<div class=" pull-right padding-left-5 padding-right-0 ">   
							<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="User_Tree_Multi_Modal.searchRevision()">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                  	</div> 
						</div>
					<!-- 搜索框end -->
					</div>
	            	
		         	<div class="clearfix"></div>
		         		
					<!-- start:table -->
					<div class="margin-left-8 margin-right-8" style='margin-top:5px;margin-bottom:5px;'>
						<div class='col-xs-12 padding-left-0 padding-right-0' id="searchTable">
							<div class="col-lg-12 col-sm-12 col-xs-12 pull-left " style='padding:0px;'>
			                       <div class="ibox-content" > 
			                         <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id='ibox-contentdiv' style="overflow: auto;max-height:400px;height:300px;">
			                           	 <div id=dprt_json></div>
			                         </div> 
			                         <div class='clearfix'></div>
			                       </div>
			                 </div>   
			                 
			         		<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>  
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 padding-top-10">
							<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">已选人员</div>
								<div class="font-size-9 line-height-18">Check Name</div>
							</label>
							<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
								<input class="form-control" id="um_selectUser" />
							</div>
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
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
	                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="User_Tree_Multi_Modal.setData()">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
	                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="User_Tree_Multi_Modal.clear()">
								<div class="font-size-12">清空</div>
								<div class="font-size-9">Clear</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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