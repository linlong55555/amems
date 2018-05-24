<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<link rel="stylesheet" href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/personel_tree_multi.js"></script>
<style type="text/css">
.bootstrap-tagsinput {
  	width: 100% !important;
  	border-radius:0px;
}
.bootstrap-tagsinput input{
	border-radius:0px !important;
}
</style>
<div class="modal fade modal-new" id="Personel_Tree_Multi_Modal" tabindex="3"  style="z-index: 99999 ! important;" role="dialog" aria-labelledby="Personel_Tree_Multi_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:750px!important;">
		<div class="modal-content">
		<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >人员列表</div>
							<div class="font-size-9" >Personel List</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
			<div class="modal-body padding-bottom-0" id="Personel_Tree_Multi_Modal_Body">
			  	<div class="col-xs-12 margin-top-8 ">
	  			  	<div class=" pull-right padding-left-0 padding-right-0 margin-top-0 modalSearch">	
       		
						<!-- 搜索框start -->
						<div class=" pull-right padding-left-0 padding-right-0">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='账号/姓名'  class="form-control" id="user_keyword_search" >
							</div>
		                   	<div class=" pull-right padding-left-5 padding-right-0 ">   
							<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="Personel_Tree_Multi_Modal.searchRevision()">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                  	</div> 
						</div>
					<!-- 搜索框end -->
					</div>
	            	
<!-- 		         	<div class="clearfix"></div> -->
			  	
              		<div class="col-lg-12 col-xs-12 padding-bottom-10 margin-top-8 padding-left-0 padding-right-0">
			            	<div class="panel panel-primary">
			            		<div class="col-lg-6 col-sm-6 col-xs-12 pull-left border-r" style='padding:0px;'>
				                    <div class="ibox " style='padding:0px;' >
				                      <div class="ibox-title" style='padding:0px;height:25px;background:#ececec;'>
					                     <label class=" pull-left" style='height:25px;line-height:25px;padding-left:10px;'>
									           	按部门/Dpartment
										 </label>
				                       </div> 
				                       <div class="ibox-content" style='padding-left:10px;' > 
				                         <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id='ibox-contentdiv' style="overflow: auto;max-height:400px;height:300px;">
				                           	 <div id=dprt_json></div>
				                         </div> 
				                       </div>
				                    </div>
				                 </div>   
				                 
				                 <div class="col-lg-6 col-sm-6 col-xs-12 pull-right" style='padding:0px;'>
	               					<div class="ibox " style='padding:0px;' >
				                      <div class="ibox-title" style='padding:0px;height:25px;background:#ececec;'>
					                     <label class=" pull-left" style='height:25px;line-height:25px;padding-left:10px;'>
									           	按岗位/Station
										 </label>
				                       </div> 
				                       <div class="ibox-content" style='padding-left:10px;' > 
				                         <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id='ibox-contentdiv' style="overflow: auto;max-height:400px;height:300px;">
				                           	 <div id="station_json"></div>
				                         </div> 
				                       </div>
				                    </div>
	               					
	               				 </div> 
			       	     			
				         		<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>
						<div class="input-group-border">
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 padding-bottom-5">
							<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
								<div class="font-size-12 line-height-18">已选人员</div>
								<div class="font-size-9 line-height-18">Check Name</div>
							</label>
							<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-8 ">
								<input class="form-control" id="um_selectUser" readonly  style='border-radius:5px;'/>
							</div>
						</div>
						<div class="clearfix"></div>
						</div>
						<!-- end:table -->
	                	
		     			<div class="clearfix"></div>
					</div>
			<div class="clearfix"></div>
		
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
	                   <button type="button" onclick="Personel_Tree_Multi_Modal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" onclick="Personel_Tree_Multi_Modal.clearUser()" id="Personel_Tree_Multi_Modal_btn_clear"
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
