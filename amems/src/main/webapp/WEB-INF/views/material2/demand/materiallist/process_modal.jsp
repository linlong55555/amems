<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="process_modal" tabindex="-1" role="dialog"  aria-labelledby="process_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:550px;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">批量处理操作</div>
							<div class="font-size-12" id="modalHeadENG">Batch process</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
            	<div class="input-group-border" style="margin-top:8px;padding-top:5px;">
            	<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group padding-leftright-8">
					<span class="col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
						需求分析的样本数
					</span>
					<div class="col-sm-9 col-xs-9 padding-leftright-8">
					<span class='color-red margin-right-5' id="sjsl"></span>
				    A/C数量<span class='color-red margin-right-5 margin-left-5' id="fjzchsl"></span>
				            部件数<span class='color-red margin-right-5 margin-left-5' id="bjsl"></span>
					</div>
				</div>
            	
				<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">操作类型</div>
						<div class="font-size-9">Type</div>
					</span>
					<div class="col-sm-9 col-xs-9 padding-leftright-8">
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input value="2" style="vertical-align:text-bottom;" checked="checked" name='process_type' type="radio">&nbsp;处理中&nbsp;&nbsp;
						</label>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input value="3" style="vertical-align:text-bottom;" name='process_type' type="radio">&nbsp;完成&nbsp;&nbsp;
						</label>
				    </div>
				</div>
				<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">保障备注</div>
						<div class="font-size-9">Remark</div>
					</span>
					<div class="col-sm-9 col-xs-9 padding-leftright-8">
						<textarea style="height: 75px;" class="form-control" id="bzbz_process" name="" maxlength="1000" ></textarea>
				    </div>
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
	                    	<button id="save_btn" type="button" onclick="processSave();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
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