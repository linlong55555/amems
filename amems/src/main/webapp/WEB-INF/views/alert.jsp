<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/alert.js"></script>
<!-------alert对话框 Start-------->
<div class="modal fade modal-new" id="alertModal" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true" data-keyboard="false" data-backdrop="static" style="z-index: 100000 ! important;">
	<div class="modal-dialog">
		<div class="modal-content">
	     	<div class="modal-header modal-header-new">
				<h4 class="modal-title">
					<div class='pull-left'>
						<div class="font-size-14">提示信息</div>
						<div class="font-size-12">Prompt Info</div>
					</div>
					<div class='pull-right'>
						<button type="button" class="icon-remove modal-close"
							data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
			</div>
			<div class="modal-body">
				<div class="input-group-border" style="padding-top: 5px; margin-top: 8px;">
					<i class="glyphicon glyphicon-info-sign alert-modalbody-icon"></i>
					<label id="alertModalBody" class="paddind-bottom-5"></label>
				</div>
			</div>
			<div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8" >
			     	<div class="input-group">
	                    <span class="input-group-addon modalfooterbtn">
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
<!-------alert对话框 End-------->
	
<!-------alert refreshPage对话框 Start-------->
<div class="modal fade" id="alertAfterModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static" style="z-index: 100000 ! important;">
	<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header alert-modal-header" >
                      <h4 class="modal-title" >
                         <div class='pull-left'>
                       <div class="font-size-12 ">提示信息</div>
						<div class="font-size-9">Prompt Info</div>
					  </div>
					  <div class='pull-right' style='padding-top:10px;'>
					  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
					  </div>
					  <div class='clearfix'></div>
                      </h4>
           </div>
			<div class="modal-body alert-modal-body" id="alertAfterModalBody"></div>
			<div class="modal-footer">
				<button type="button" onclick="refreshPage();" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
					<div class="font-size-12">关闭</div>
					<div class="font-size-9">Close</div>
				</button>
			</div>
		</div>
	</div>
</div>
<!-------alert refreshPage对话框 End-------->
<!-------alert error对话框 Start-------->
<div class="modal fade modal-new" id="alertErrorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static" style="z-index: 100000 ! important;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
					<div class='pull-left'>
						<div class="font-size-14">提示信息</div>
						<div class="font-size-12">Prompt Info</div>
					</div>
					<div class='pull-right'>
						<button type="button" class="icon-remove modal-close"
							data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
			</div>
			<div class="modal-body">
				<div class="input-group-border" style="padding-top: 5px; margin-top: 8px;">
					<i class="glyphicon glyphicon-info-sign alert-modalbody-icon"></i>
					<label id="alertErrorModalBody" class="paddind-bottom-5"></label>
				</div>
			</div>
			<div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8" >
			     	<div class="input-group">
	                    <span class="input-group-addon modalfooterbtn">
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
	<!-------alert error对话框 End-------->
	
<!-------alert error对话框 Start-------->
<div class="modal fade modal-new" id="alertConfirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true" data-backdrop='static' data-keyboard= false style="z-index: 100000 ! important;">
    <input type="hidden" value="" id="url"/>
	<div class="modal-dialog" style="width:40%;">
		<div class="modal-content">
			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
					<div class='pull-left'>
						<div class="font-size-14">提示信息</div>
						<div class="font-size-12">Prompt Info</div>
					</div>
					<div class='clearfix'></div>
				</h4>
			</div>
			<div class="modal-body">
				<div class="input-group-border" style="padding-top: 5px; margin-top: 8px;">
					<i class="fa fa-question-circle-o alert-modalbody-icon"></i>
					<label id="alertConfirmModalBody" class="paddind-bottom-5"></label>
				</div>
			</div>
			<div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8" >
			     	<div class="input-group">
	                    <span class="input-group-addon modalfooterbtn">
	                      <button id="confirmY" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12 lin-height-12">是</div>
								<div class="font-size-9 ">Yes</div>
						 </button>
		                 <button id="confirmN" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12 lin-height-12">否</div>
								<div class="font-size-9 ">No</div>
						 </button>
	                    </span>
	               	</div>
               	</div>
			</div>
		</div>
	</div>
</div>
<!-------alert error对话框 End-------->

<!-------图片预览对话框-------->
<div class="modal fade" id="imageModal" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true" data-keyboard="false" data-backdrop="static" style="z-index: 70000 ! important;">
	<div class="modal-dialog" style="text-align:center;">
		<div class="modal-content" style="border: none;">
		    <div class="modal-header alert-modal-header" >
                      <h4 class="modal-title" >
                         <div class='pull-left text-left'>
                       <div class="font-size-12 ">图片预览</div>
						<div class="font-size-9">Preview Picture</div>
					  </div>
					  <div class='pull-right' style='padding-top:10px;'>
					  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
					  </div>
					  <div class='clearfix'></div>
                      </h4>
           </div>
           <div class="modal-body alert-modal-body text-left">
				<img alt="预览失败" src="" id="previewImage"/>
			</div>
			<div class="modal-footer alert-modal-footer">
			     <div class="input-group">
                    <span class="input-group-addon modalfooterbtn">
                      <button  type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-12 lin-height-12">关闭</div>
							<div class="font-size-9 ">Close</div>
					 </button>
                    </span>
               	</div>
			</div>
		</div>
	</div>
</div>
<!-------图片预览对话框-------->
