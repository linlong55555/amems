<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/firm/contact_person_win.js"></script>
<div class="modal fade modal-new" id="Contact_Person_Win_Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 700px!important;">
		<div class="modal-content">
		      <div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">联系人维护</div>
							<div class="font-size-12" id="modalHeadENG">Contact Maintenance</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
			  <div class="col-xs-12 margin-top-8 ">
		            <div class="input-group-border"> 
		            <div class="col-lg-6 col-sm-6 col-xs-12 adding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>联系人</div>
								<div class="font-size-9">Contact Person</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input type="text"  name="lxr" class="form-control " maxlength="100" id="lxr">
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12 form-group padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">职位</div>
								<div class="font-size-9">Position</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input type="text" name="zw" class="form-control " maxlength="100" id="zw">
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12 form-group padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">手机</div>
								<div class="font-size-9">Phone</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input type="text"  name="sj" class="form-control " maxlength="11" id="sj">
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12 form-group padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">座机</div>
								<div class="font-size-9">Tel</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input type="text"  name="zj" class="form-control " maxlength="15" id="zj">
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12 form-group padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">传真</div>
								<div class="font-size-9">Fax</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input type="text" name="cz" class="form-control " maxlength="15" id="cz">
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12 form-group padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">邮箱</div>
								<div class="font-size-9">E-mail</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input type="text" name="yxdz" class="form-control " maxlength="35" id="yxdz">
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12 form-group padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">QQ</div>
								<div class="font-size-9">QQ</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input type="text" name="qq" class="form-control " maxlength="11" id="qq">
							</div>
						</div>
						<div class="col-lg-6 col-sm-6 col-xs-12 form-group padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">微信</div>
								<div class="font-size-9">Wechat</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
								<input type="text"  name="wx" class="form-control " maxlength="15" id="wx">
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 form-group padding-left-0 padding-right-0 margin-bottom-8">
							<label class="col-lg-2 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">备注</div>
								<div class="font-size-9">Remark</div></label>
							<div class="col-lg-10 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea type="text"  name="bz" class="form-control " maxlength="300" id="bz">
								</textarea>
							</div>
						</div>
						<div class="clearfix"></div>
		            </div>
		      </div>
			<div class="clearfix"></div>
			</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button type="button" onclick="Contact_Person_Win_Modal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="Contact_Person_Win_Modal.close()">
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
