<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade double-pop-up" id="myModalFour" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" >
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-12 ">维修方案描述</div>
							<div class="font-size-9">Description</div>
						  </div>
						  <div class='pull-right' style='padding-top:10px;'>
						  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
              	<div class="input-group-border">
                    <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-xs-9 padding-leftright-8" >
								<div class="input-group input-group-new">
								<input type="text" class="form-control" />
			                    <span class="input-group-addon">人x</span>
			                    <input type="text" class="form-control" />
			                     <span class="input-group-addon">时=8时</span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<select class='form-control' >
									<option>更多</option>
									<option>更多</option>
								</select>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input type='radio' name='radio2' checked/>&nbsp;A</label>
								<label class='label-input'><input type='radio' name='radio2'/>&nbsp;B</label>
							</div>
						</div>
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input type='checkbox' name='radio'/>&nbsp;A</label>
								<label class='label-input'><input type='checkbox' name='radio'/>&nbsp;B</label>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-xs-9 padding-leftright-8" id='multipleselect-div' style='height:34px;'>
								<select id="searchstatusthree" class='form-control'  multiple="multiple" >
							    <option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								</select> 
							</div>
						</div>
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-xs-9  padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
							</div>
						</div> 
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
							</div>
						</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<div class="input-group">
								<input type="text" class="form-control">
			                     <span class="input-group-addon"><i class='icon-search'></i></span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-xs-9 padding-leftright-8">
								<div class="input-group">
								<input type="text" class="form-control">
			                     <span class="input-group-addon"><i class='icon-search'></i></span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						  <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-leftright-8" >
								<div class="input-group input-group-new">
								 <span class="input-group-addon"><input type='checkbox'/> &nbsp;vvvvvvvvvvvvvvv</span>
			                    <input type="text" class="form-control">
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						 <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-leftright-8" >
								<div class="input-group input-group-new">
								 <span class="input-group-addon padding-left-35"><input type='checkbox'/> &nbsp;vvvvvvvvvvvvvvvxxxxxxxxxxx</span>
			                    <input type="text" class="form-control">
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-leftright-8" >
								<div class="input-group input-group-new">
								 <span class="input-group-addon padding-left-35"><input type='checkbox'/> &nbsp;维修方案</span>
			                    <input type="text" class="form-control">
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="clearfix"></div>
						</div>
						
						<div class="panel panel-primary">
					    	<!-- panel-heading -->
							<div class="panel-heading bg-panel-heading" >
								<div class="font-size-12 ">维修方案描述</div>
								<div class="font-size-9">Description</div>
							</div>
							<div class="panel-body">
							</div>
						</div>
						 <!-- t提示 -->
						 
				</div>
                <div class="clearfix"></div>              
           </div>
           <div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8" >
				<div class="input-group">
				<span class="input-group-addon modalfootertip" >
	                   <i class='glyphicon glyphicon-info-sign alert-info'></i>警告！请不要提交。
				</span>
                    <span class="input-group-addon modalfooterbtn">
                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">提交</div>
							<div class="font-size-9">Submit</div>
						</button>
	                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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