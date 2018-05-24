<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade" id="myModalOne" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:80%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 ">维修方案描述</div>
							<div class="font-size-12">Description</div>
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
                    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
							</div>
						</div>
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-ms-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
								<div class="input-group input-group-new">
								<input type="text" class="form-control">
			                    <span class="input-group-addon">人x</span>
			                    <input type="text" class="form-control">
			                     <span class="input-group-addon">时=8时</span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class='form-control' >
									<option>更多</option>
									<option>更多</option>
								</select>
							</div>
						</div>
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input type='radio' name='radio1' checked/>&nbsp;A</label>
								<label class='label-input'><input type='radio' name='radio1'/>&nbsp;B</label>
							</div>
						</div>
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input type='checkbox' name='radio'/>&nbsp;A</label>
								<label class='label-input'><input type='checkbox' name='radio'/>&nbsp;B</label>
							</div>
						</div>
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8" id='multipleselect-div' style='height:34px;'>
								<select id="searchstatusone" class='form-control'  multiple="multiple" >
							    <option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								</select> 
							</div>
						</div>
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9  padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
							</div>
						</div>
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
							</div>
						</div> 
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="text-right col-xs-4 col-sm-6 col-lg-3 text-right padding-left-0 padding-right-0" style='visibility:hidden;'>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group input-group-new">
							    <span class="input-group-addon"><input onchange="addUser(this)" type="checkbox"/></span>
								 <span class="input-group-addon">wwww</span>
			                    
			                    <!-- <input type="text" class="form-control" disabled="disabled"> -->
			                    <select class="form-control "    disabled="disabled" >
			                    </select>
			                    </div>
							</div>
						</div>
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
							</div>
						</div>
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
							</div>
						</div>
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important'>
							<thead>
								<tr>
								   <th width='50'>
									   <button class="line6  line6-btn"  type="button">
										<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
									   </button>
								   </th>
								   <th class='sorting'>
									   <div class="font-size-12 line-height-12">EO号</div>
							           <div class="font-size-9 line-height-12">EO</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">版本</div>
							           <div class="font-size-9 line-height-12">Version</div>
								   </th>
								   <th class='sorting'>
									   <div class="font-size-12 line-height-12">机型</div>
							           <div class="font-size-9 line-height-12">Models</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">关联评估单</div>
							           <div class="font-size-9 line-height-12">Associated</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">来源文件</div>
							           <div class="font-size-9 line-height-12">Source File</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">标题</div>
							           <div class="font-size-9 line-height-12">Title</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">状态</div>
							           <div class="font-size-9 line-height-12">State</div>
								   </th>
								   <th class='sorting'>
									   <div class="font-size-12 line-height-12">适用性</div>
							           <div class="font-size-9 line-height-12">Applicability</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">组织结构</div>
							           <div class="font-size-9 line-height-12">Organization</div>
								   </th>
								</tr>
							</thead>
							<tbody >
								<tr>
								<td class='text-center'>
									<button class="line6  line6-btn"  type="button">
										<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
									</button>
								</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								</tr>
								<tr>
								<td class='text-center'>
									<button class="line6  line6-btn"  type="button">
										<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
									</button>
								</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								</tr>
							</tbody>
					</table>
				</div>
							</div>
						</div>
						<div class="col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group">
								<input type="text" class="form-control">
			                     <span class="input-group-addon"><i class='icon-search'></i></span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
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
						<div class="input-group-border margin-top-8">
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 上传说明</div>
								<div class="font-size-9">Upload</div>
							</label>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group">
								<input type="text" class="form-control">
			                     <span class="input-group-addon">上传</span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class='clearfix'></div>
						<div class='table_pagination padding-leftright-8'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-10 table-height" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
								   <th>
									   <div class="font-size-12 line-height-12">操作</div>
							           <div class="font-size-9 line-height-12">Operation</div>
								   </th>
								   <th class='sorting'>
									   <div class="font-size-12 line-height-12">EO号</div>
							           <div class="font-size-9 line-height-12">EO</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">版本</div>
							           <div class="font-size-9 line-height-12">Version</div>
								   </th>
								   <th class='sorting'>
									   <div class="font-size-12 line-height-12">机型</div>
							           <div class="font-size-9 line-height-12">Models</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">关联评估单</div>
							           <div class="font-size-9 line-height-12">Associated</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">来源文件</div>
							           <div class="font-size-9 line-height-12">Source File</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">标题</div>
							           <div class="font-size-9 line-height-12">Title</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">状态</div>
							           <div class="font-size-9 line-height-12">State</div>
								   </th>
								   <th class='sorting'>
									   <div class="font-size-12 line-height-12">适用性</div>
							           <div class="font-size-9 line-height-12">Applicability</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">组织结构</div>
							           <div class="font-size-9 line-height-12">Organization</div>
								   </th>
								</tr>
							</thead>
							<tbody >
								<tr>
								<td><a href='#'>关联技术评估单AAAA</a></td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								</tr>
								<tr>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								</tr>
							</tbody>
					</table>
				</div>
				
				<div class='clearfix'></div>
				
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