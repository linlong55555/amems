<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<style type="text/css">
.bootstrap-tagsinput {
  width: 100% !important;
}
</style>
<div class="modal fade in modal-new" id="Personel_Tree_Multi_Modal" tabindex="-1" role="dialog"  aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style='width:50%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
					<div class='pull-left'>
						<div class="font-size-14 " id="titleName">人员列表</div>
						<div class="font-size-12" id="titleEname">Personel List</div> 
					</div>
					<div class='pull-right' >
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
			  		<div class='clearfix'></div>
				</h4>
			</div>
            <div class="modal-body" id="AssignEndModalBody">
   		      
   		        <div class="input-group-border margin-top-8 padding-left-0">
					<!-- 搜索框start -->
					<div class=" pull-right padding-left-8 padding-right-8">
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
 					<div class="clearfix"></div>
						<div class="col-lg-12 col-xs-12 padding-bottom-10 margin-top-8  padding-leftright-8">
			            	<div class="panel panel-primary">
			            		<div class="col-lg-12 col-sm-12 col-xs-12 pull-left " style='padding:0px;'>
				                    <div class="ibox " style='padding:0px;' >
				                       <div class="ibox-content" style='padding-left:10px;' > 
				                         <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id='ibox-contentdiv' style="overflow: auto;max-height:400px;height:300px;">
				                           	 <div id=dprt_json></div>
				                         </div> 
				                       </div>
				                    </div>
				                 </div>   
				         		<div class="clearfix"></div>
							</div>	
				         		<div class="clearfix"></div>
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 ">
									<div class="font-size-12 line-height-18">已选人员</div>
									<div class="font-size-9 line-height-18">Selected Personel</div>
								</label>
								<div class="col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
									<input class="form-control" id="um_selectUser" readonly />
								</div>
							</div>
						</div>
					
						<div class='clearfix'></div>
						
				
						
						<div class='table_pagination padding-leftright-8'>
						</div>
						  <div class="clearfix"></div>  
					</div>
                      <div class="clearfix"></div>       
           	</div>
   			<div class="modal-footer ">
				<div class="col-xs-12 padding-left-8 padding-right-0" >
					<span class="pull-left modalfootertip" >
		                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
					<div class="pull-right margin-right-8">
					   <button id="baocun" type="button" onclick="Personel_Tree_Multi_Modal.setData();" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
					   <button id="Personel_Tree_Multi_Modal_btn_clear" type="button" onclick="Personel_Tree_Multi_Modal.clearUser();" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">清空</div>
							<div class="font-size-9">Clear</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
	 		</div>
		</div>
   </div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/users_tree_multi.js"></script>