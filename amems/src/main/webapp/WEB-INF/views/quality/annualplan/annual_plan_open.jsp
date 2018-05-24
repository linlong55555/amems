<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="annual_plan_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="annual_plan_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <input type="hidden"   id="annual_plan_alert_Modal_id">
      <div class="modal-dialog" style='width:55%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalName1"></div>
							<div class="font-size-12" id="modalEname1"></div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
            	<form id="annual_plan_alert_Modal_from">
              	<div class="col-xs-12 margin-top-8 ">
              	<div class="input-group-border">
          		<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">年度</div>
							<div class="font-size-9 ">Year</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control"   id="annual_plan_alert_Modal_nd" readonly/>
						</div>
				  </div>
				  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><label class="required" style="color: red">*</label>月份</div>
							<div class="font-size-9 ">Month</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control'  id="annual_plan_alert_Modal_yf" name="annual_plan_alert_Modal_yf">
								<option value="1">1月</option>
								<option value="2"> 2月</option>
								<option value="3">3月</option>
								<option value="4">4月</option>
								<option value="5">5月</option>
								<option value="6">6月</option>
								<option value="7">7月</option>
								<option value="8">8月</option>
								<option value="9">9月</option>
								<option value="10">10月</option>
								<option value="11">11月</option>
								<option value="12">12月</option>
							</select>
						</div>
				  </div>
				  
				  <div class="clearfix"></div>
				  
				  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
					<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12  margin-topnew-2"><label class="required" style="color: red">*</label>类型</div>
						<div class="font-size-9 ">Category</div>
					</label>
					<div class="col-sm-8 col-xs-9 padding-leftright-8">
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
							<input type='radio' name='annual_plan_alert_Modal_lx' onclick="annual_plan_module.changeType()" value='1' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;内部&nbsp;&nbsp;
						</label>
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
							<input type='radio' name='annual_plan_alert_Modal_lx' onclick="annual_plan_module.changeType()" value='2' style='vertical-align:text-bottom;'/>&nbsp;外部&nbsp;&nbsp;
						</label>
					</div>
				  </div>
				  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
					<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>审核对象 </div>
				        <div class="font-size-9">Object</div>
					</label>
					<div class="col-sm-8 col-xs-9 padding-leftright-8">
						<div class='input-group' style='width:100%'>
							<input type="hidden"  id="annual_plan_module_shdxid">
							<input type="hidden"  id="annual_plan_module_shdxbh">
							<input type="hidden"  id="annual_plan_module_shdxmc">
						    <input type="text" id="annual_plan_module_shdx" name="annual_plan_module_shdx" class="form-control"  ondblclick="annual_plan_module.openzrdw('shdx',null)""  maxlength="20"  />
							<span id="shdxBtn" class="input-group-btn">
								<button type="button"  class="btn btn-default" data-toggle="modal" onclick="annual_plan_module.openzrdw('shdx',null)">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						 </div>
					</div>
				  </div>
				  
				  <div class="clearfix"></div>
				  
					 <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">责任审核人 </div>
						        <div class="font-size-9">Auditor</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<div class='input-group' style='width:100%'>
							<input type="hidden"   id="annual_plan_module_shzrrid">
						    <input type="text" id="annual_plan_module_shzrr" ondblclick="annual_plan_module.openUser('shzrr')" readonly class="form-control  readonly-style"  maxlength="20"  />
							<span class="input-group-btn">
								<button type="button" id="zjh_search_btn" class="btn btn-default" data-toggle="modal" onclick="annual_plan_module.openUser('shzrr')">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						    </div>
						</div>
					</div>
					 <div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">备注</div>
						        <div class="font-size-9">Remark</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="annual_plan_alert_Modal_bz"  rows="3" cols="34" maxlength="100"></textarea>
						</div>
					</div>
				
					<div class="clearfix"></div>
				
				</div>
				<div class="clearfix"></div>
					<div id="attachments_list_plan_edit" style="display:none">
					
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					
					</div>
				
				</div>
				<div class="clearfix"></div>
				</form>
            </div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button id="save_btn" type="button" onclick="javascript:annual_plan_alert_Add.save();" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/annualplan/annual_plan_add.js"></script><!--当前新增界面js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/annualplan/annual_plan_update.js"></script><!--当前新增界面js  -->