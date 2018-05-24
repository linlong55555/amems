<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="adCloseModal" tabindex="-1" role="dialog"  aria-labelledby="transfer_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <input type="hidden" id="gbbh">
      <input type="hidden" id="gbxlh">
      <input type="hidden" id="gbjswjid">
      <input type="hidden" id="gbpgdid">
      <input type="hidden" id="gbzt">
      <input type="hidden" id="gbid">
      <div class="modal-dialog" style='width:50%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		 <div class="font-size-14 " id="gbcName" >关闭</div>
							<div class="font-size-12" id="gbeName"  >close</div> 
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
	            <div class="col-xs-12 margin-top-8 ">
		            <div class="input-group-border"> 
						
					<!-- <div class="col-xs-12 padding-left-0 padding-right-0 form-group" id="AssignEndModal_gblx">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">关闭类型</div>
							<div class="font-size-9">Close Type</div>
						</label>
						<div class="col-xs-9 padding-leftright-8">
							<div class="input-group input-group-new">
			                     <span class="input-group-addon" style="padding-left:0px">
			                    	<label><input class="closeModel"  name="gbzt" value="9" type="radio">&nbsp;关闭&nbsp;</label>
			                     </span>
			                     <span class="input-group-addon">
			                    	 <label><input class="closeModel"  name="gbzt" value="0" checked="checked" type="radio">&nbsp;不关闭&nbsp;</label>
								 </span>
			                     <input class="closeModel" style="visibility:hidden;" type="text">
		                	</div>
						</div>
					</div> -->
										    
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-md-1 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">备注</div>
								<div class="font-size-9">Desc</div>
							</label>
							<div class="col-md-11 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="gbbz" name="gbbz" placeholder=''   maxlength="300" style="height:75px"></textarea>
							</div>
						</div>
				     <div class='clearfix'></div>
	            </div> 
	             <div class='clearfix'></div>   
	              
           	</div>
           	<div class='clearfix'></div>  
           	</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button  type="button" onclick="adClose();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">确认</div>
								<div class="font-size-9">Confirm</div>
							</button>
							 <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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
<!--  弹出框结束-->