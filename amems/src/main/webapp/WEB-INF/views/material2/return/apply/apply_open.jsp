<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="apply_open_alert" tabindex="-1" role="dialog"  aria-labelledby="apply_open_alert" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:50%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalName">退料登记</div>
							<div class="font-size-12" id="modalEname">Registration</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
            	<!-- 隐藏域 -->
            	<input type="hidden" id="bjid" >
            	<input type="hidden" id="dprtcode" >
            	<input type="hidden" id="rejectedMaterialId" >
            	<input type="hidden" id="kcid" >
	            <div class="col-xs-12 margin-top-8 ">
		            <div class="input-group-border"> 
		           <form id="form">
		            <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>退料日期</div>
							<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="tlrq" class="form-control date-picker"   data-date-format="yyyy-mm-dd" type="text" name="tlrq">
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">退料人</div>
							<div class="font-size-9 ">Retreating</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="tlr" class="form-control" maxlength="16" placeholder="" type="text" readonly>
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>部件号</div>
							<div class="font-size-9 ">PN</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group " style="width:100% !impotant;">
								<input id="bjh" name="bjh" class="form-control" onblur="loadMaterialData()" type="text">
		                    	<span id="" class="input-group-addon btn btn-default" onclick="loadMaterialAndOutfield()">
		                    		<i class="icon-search"></i>
		                    	</span>
				            </div>
					  </div>
				    </div>
				     <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>部件名称</div>
							<div class="font-size-9 ">PN name</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="bjmc" name="bjmc" class="form-control" maxlength="199" placeholder="" type="text" >
						</div>
				    </div>	
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批次号</div>
							<div class="font-size-9 ">Batch No.</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="pch" name="pch" class="form-control" maxlength="50" placeholder="" type="text" >
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">序列号</div>
							<div class="font-size-9 ">SN</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input onblur="xlhChange()" id="xlh" name="xlh" class="form-control" maxlength="50" placeholder="" type="text" >
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>退料数量</div>
							<div class="font-size-9 ">Amount</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8 ">
							 <div class="input-group" style="width:100%;">
								<input id="tlsl" onblur="tlslChange()" class="form-control" name="tlsl" type="text" maxlength="12" >
								<input id="tlsl_hide"  class="form-control" type="hidden" >
		                    	<span id="" class="input-group-addon" style='background:none;padding-top:0px;padding-bottom:0px;border:0px;'  onclick="">
		                    		<select id="dw" class='form-control' style='width:80px;border-left:0px;'>
		                    		<option>单位</option>
		                    	    </select>
		                    	</span>
				            </div>
						</div>
				    </div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">是否可用</div>
							<div class="font-size-9 ">Available</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div class='text-left' style='height:34px;'>
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;" >
									<input value="1" checked="checked" style="vertical-align:text-bottom;" type="radio" name="sfky_radio" >&nbsp;可用&nbsp;&nbsp;
							</label>
							<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
									<input value="0" style="vertical-align:text-bottom;" type="radio" name="sfky_radio" >&nbsp;不可用&nbsp;&nbsp;
							</label>
							</div>
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class='color-red'>*</span>部件来源</div>
							<div class="font-size-9 ">Original</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
						    <div class="input-group col-xs-12">
								
		                    	<span id="" class="input-group-addon" style='background:none;padding-top:0px;padding-bottom:0px;border:0px;'  onclick="">
		                    		<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
										<input onclick="fjzchHide()" value="1" checked="checked" style="vertical-align:text-bottom;" type="radio" name="bjly_radio" >&nbsp;库房&nbsp;&nbsp;
									</label>
									<label class="padding-left-5" style="margin-top:6px;font-weight:normal;" >
										<input onclick="fjzchHide()" value="2" style="vertical-align:text-bottom;" type="radio" name="bjly_radio" >&nbsp;飞机&nbsp;&nbsp;
									</label>
		                    	</span>
		                    	<select class='form-control' id="fjzch" style="visibility: hidden;">
		                    		<option></option>
		                    	</select>
				            </div>
							
						</div>
				    </div>
				     <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">物料类别</div>
							<div class="font-size-9 ">Category</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select id="wllb" class="form-control">
								<option value="1">航材</option>
								<option value="2">工具设备</option>
								<option value="4">化工品</option>
								<option value="5">低值易耗品</option>
								<option value="6">松散件</option>
								<option value="0">其他</option>
							</select>
						</div>
				    </div>
				    <div class='clearfix'></div>
				    <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">说明</div>
							<div class="font-size-9 ">Instruction</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" style="height:55px;" id="sm" maxlength="1300" ></textarea>
						</div>
				    </div>
				    </form>
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
	                    	<button id="save_btn" type="button" onclick="add(false);" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="submit_btn" type="button" onclick="add(true);"  class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交并编写下一条</div>
								<div class="font-size-9">Submit and write the next</div>
							</button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->