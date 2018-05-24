<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:90%;'>
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
              	<div class="col-xs-12 margin-top-8">
              	<!-- <div class='col-xs-12' style='height:80px;padding:0px;'>
              	<div class='col-xs-6'>
              		
              	</div>
              	<div class='col-xs-6' style='padding-left:0px;padding-right:0px;'>
				<ul id="myTab" class="earyWizard" style='background:none;height:80px;border:1px solid red;padding-top:8px;'>
				    <li class="active" >
				        <a href="#home" data-toggle="tab">
				            <span class="step">1</span> 
				            <span class="title">Basic information</span>
				        </a>
				    </li>
				    <li>
					    <a href="#ios" data-toggle="tab">
					    	 <span class="step">2</span> 
				            <span class="title">Basic information</span>
					    </a>
				    </li>
				    <li >
				        <a href="#jmeter" data-toggle="tab">
				        	 <span class="step">3</span> 
				            <span class="title">Basic information</span>
				        </a>
				    </li>
				</ul>
				</div>
				<div class='clearfix'></div>
				</div> -->
              	<!-- 分屏 -->
              	<!-- <div id="myTabContent" class="tab-content" style='padding:0px;border:1px solid #d5d5d5;border-radius:0px;'> -->
              	<div class="tab-pane fade in active" id="home" style='margin-top:8px;'>
                   <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control padding-left-3 padding-right-3" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
								<label class='margin-top-8' ><span>XXXXXXXXXXXXXXXXXXXXXXX</span>&nbsp;&lt;--<a href='#'>1.0</a></label>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>来源</div>
								<div class="font-size-9">Source</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
								<div class="input-group input-group-new">
								
			                    <span class="input-group-addon">
			                    <label><input type='radio' name='shxzl' checked/>&nbsp;适航性资料</label>
			                    </span>
			                    <span class="input-group-addon">
			                     <label><input type='radio' name='shxzl'/>&nbsp;其他</label>
								 </span>
			                     <input type="text" class="form-control">
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
								<div class="input-group input-group-new">
								<input type="text" class="form-control">
			                    <span class="input-group-addon">人&nbsp;x</span>
			                    <input type="text" class="form-control">
			                     <span class="input-group-addon">时=8时</span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class='form-control' >
									<option>更多</option>
									<option>更多</option>
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input type='radio' name='radio4' checked="checked"/>&nbsp;A</label>
								<label class='label-input'><input type='radio' name='radio4'/>&nbsp;B</label>
							</div>
						</div>
						 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input type='checkbox' name='radio' />&nbsp;A</label>
								<label class='label-input'><input type='checkbox' name='radio'/>&nbsp;B</label>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" id='multipleselect-div' style='height:34px;'>
								<select id="searchstatus" class='form-control'  multiple="multiple" >
							    <option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								</select> 
							</div>
						</div>
						 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9  padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100" disabled='disabled' value='吴蕾'>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
							</div>
						</div> 
						 <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important'>
							<thead>
								<tr>
								   <th width='50'>
									   <button class="line6 line6-btn"  type="button">
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
									<button class="line6 line6-btn"  type="button">
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
									<button class="line6 line6-btn"  type="button">
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
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
							</div>
						</div>
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
							</div>
						</div>
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 下达指令</div>
								<div class="font-size-9">Give</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group input-group-new">
								<span class="input-group-addon">
			                    <label><input type='radio' name='shxzl' checked/>&nbsp;</label>
			                    </span>
			                    <div class="input-group-addon fixed-width-style">
			                     <div class="font-size-12 margin-topnew-2">修订维修方案</div>
								<div class="font-size-9 margin-top-4">Re Maintenance</div>
								 </div>
			                     <input type="text" class="form-control">
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 版本</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-lg-5 col-md-6 col-sm-6 col-xs-6 padding-leftright-8">
								<div class="input-group input-group-new">
								<input type="text" class="form-control">
			                    <span class="input-group-addon">人&nbsp;x</span>
			                    <input type="text" class="form-control">
			                     <span class="input-group-addon">时=8时</span>
			                      <span class="input-group-addon padding-top-0 padding-bottom-0">
			                      <button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="showOtherModal();">
								<div class="font-size-12">工种工时</div>
								<div class="font-size-9">work hours</div>
	                   		   </button>
			                      </span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>结论附件</div>
								<div class="font-size-9">Version</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group input-group-new" >
			                    <div class="input-group-addon" style='padding:0px;'>
			                     <label><input type='radio' name='shxzl' checked/>&nbsp;是</label>
			                    </div>
			                    <div class="input-group-addon" style='padding:0px;'>
			                     <label><input type='radio' name='shxzl' checked/>&nbsp;否&nbsp;</label>
			                    </div>
			                    <input type="text" class="form-control">
			                    <div class="input-group-addon input-group-addon-btn" style='padding:0px;'>
			                                       <button class='btn btn-default' style='height:34px;border-left:0px;'>上传</button>
			                    </div>
			                    <div class="input-group-addon" style='padding:0px;'>
			                        <a class="padding-top-1 padding-bottom-1 pull-left checkPermission " href="javascript:openFiveModal();" title="下载模板 Attachment" permissioncode="training:plan:main:01" style="float: left;text-decoration:none;position:relative; margin-left: 10px;">
									<i class="icon-download-alt pull-left" style="font-size:25px"></i>
									</a>
			                    </div>
			                   
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group">
								<input type="text" class="form-control">
			                     <span class="input-group-addon"><i class='icon-search'></i></span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group">
								<div class='form-control' style='border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
								Copyright @ 2010.All Rights Reserved 粤ICP备14007448号 版权所有 深圳市建和伟业智能卡技术有限公司 
								电话:0755-25241709 传真：0755-89484569 全国统一商务服务热线：400-088-7816 
								办公:深圳市宝安区石岩镇塘头宏发高新园大厦五楼
								厂址:深圳市宝安区石岩镇塘头宏发高新园一栋厂房
								</div>
			                     <span class="input-group-addon"><i class='icon-search'></i></span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class='form-control' rows='3' >
								</textarea>
							</div>
						</div>
						 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修方案描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
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
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 上传说明</div>
								<div class="font-size-9">Upload</div>
							</label>
							<div class="col-lg-8 col-lg-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group">
								<input type="text" class="form-control">
			                     <span class="input-group-addon">上传</span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class='clearfix'></div>
						<div class='table_pagination padding-leftright-8'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-height" style="overflow-x: auto;">
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
							<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
							
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set" >
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
				</div>
				<!-- 收缩Panel -->
				<div id="faq-list-3" class="panel-group margin-bottom-8" >
						<div class="panel panel-default">
							<div class="panel-heading" >
								<a href="#faq-1-3" data-toggle="collapse" class="collapsed"> 
								<i class="fa fa-chevron-left pull-right" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
									<div class='pull-left' >
									 	<div class="font-size-12 line-height-12">组织结构</div>
							            <div class="font-size-9 line-height-12">Organization</div>
							        </div>
							        <div class='clearfix'></div>
								</a>
							</div>

							<div class="panel-collapse collapse" id="faq-1-3">
								<div class="panel-body" style="padding-top: 10px;">
								
									
								     <div class="margin-top-10">
									<table class="table table-bordered table-striped table-hover text-center" >
										<thead>
											<tr>
												<th><div
										class="font-size-12">日期</div>
									<div class="font-size-9">Date</div></th>
												<th><div
										class="font-size-12">操作人</div>
									<div class="font-size-9">operating personnel</div></th>
												<th><div
										class="font-size-12">文件名</div>
									<div class="font-size-9">The file name </div></th>
												<th><div
										class="font-size-12">操作</div>
									<div class="font-size-9">Operation</div></th>
											</tr>
										</thead>
										<tbody id="edrfiles">
											
										</tbody>
									</table>
									</div>
								</div>
							</div>
						</div>

					</div>
					<!-- 收缩Panel -->
						 
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