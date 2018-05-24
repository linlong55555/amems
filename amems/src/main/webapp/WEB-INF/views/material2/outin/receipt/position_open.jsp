<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="position_open_win" tabindex="-1" role="dialog"  aria-labelledby="storage_position_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:50%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">临时存放位置</div>
							<div class="font-size-12" id="modalHeadENG">Temporary storage position</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body">
	            <div class="col-xs-12 margin-top-8 ">
		            <div class="input-group-border"> 
		           <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">仓库</div>
							<div class="font-size-9 ">Warehouse</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control' id="position_ck" onchange="position.buildStorageLocation()">
							</select>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">库位</div>
							<div class="font-size-9 ">Library</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control selectpicker' data-live-search="true" 
								data-container="#position_open_win" data-size="10" id="position_kw"></select>
						</div>
				    </div>
				    <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">临时存放位置</div>
							<div class="font-size-9 ">Storage Position</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea id="position_lscfwz" class='form-control' style='height:95px;' maxlength="300"></textarea>
						</div>
				    </div>
				    <div class='clearfix'></div>
		            </div>
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
	                    	<button type="button" onclick="position.save();" class="btn btn-primary padding-top-1 padding-bottom-1 notView">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
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
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/outin/receipt/position_open.js"></script>