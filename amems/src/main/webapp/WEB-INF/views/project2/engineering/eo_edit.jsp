<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade" id="EOAddModal" tabindex="-1" role="dialog"  aria-labelledby="EOAddModal" aria-hidden="true" data-keyboard="false" data-backdrop="static"
 >
      <div class="modal-dialog" style='width:90%;'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 ">EO新增</div>
							<div class="font-size-12">Eo add</div>
						  </div>
						  <div class='pull-right' style='padding-top:10px;'>
						  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style='font-size:30px !important;' >&times;</span></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12" >
              	<div class='col-xs-12  EoModalHeader' style='padding:0px;border-bottom:2px solid #b6e3f9;'>
              	<div class='col-xs-7' style='padding-left:0px;padding-right:0px;padding-top:0px;'>
              		<div class="input-group-border padding-leftright-8" style='padding-bottom:8px;margin-bottom:5px;'>
              		<div class="input-group input-group-new col-xs-12">
					<span class="input-group-addon" style='width:80px;text-align:right;padding-right:8px;padding-top:0px;color:#333;'>
                    <div class="font-size-12 margin-topnew-2">EO编号</div>
					<div class="font-size-9" style='margin-top:4px;'>EO No.</div>
                    </span>
                    <input  class="form-control" id=""  name="" type="text" maxlength="">
                    <span class="input-group-addon" style='padding-top:0px;color:#333;'>
                    <div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>版本</div>
					<div class="font-size-9" style='margin-top:4px;'>Rev.</div>
					</span>
					<input class="form-control" id=""  name="" type="text" maxlength="">
					<span class="input-group-addon" style='padding-top:0px;color:#333;'>
                    <div class="font-size-12 margin-topnew-2">下发生产</div>
					<div class="font-size-9" style='margin-top:4px;'>To Prod</div>
					</span>
					<span class="input-group-addon" style='padding-top:0px;'>
                        <label class='margin-right-5 label-input' style='color:#333;'><input type='radio' name='isYes' checked="checked"/>&nbsp;是</label>
						<label class='label-input' style='color:#333;'><input type='radio' name='isYes'/>&nbsp;否</label>
					</span>
                     
                	</div>
                	<div class="col-xs-12 input-group input-group-new margin-top-8">
					<span class="input-group-addon" style='width:80px;text-align:right;padding-right:8px;padding-top:2px;vertical-align:top;color:#333;'>
                    <div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>标题</div>
					<div class="font-size-9" style='margin-top:4px;'>Title</div>
                    </span>
                    <textarea class='form-control' id=""  name=""  style='height:55px;'></textarea>
                    </div>
              		<div class='clearfix'></div>
				</div>
              	</div>
             <!-- 分屏显示 -->
 			<div class='col-xs-5' style='padding-left:0px;padding-right:0px;'>
				<ul id="myTab" class="earyWizard" style='background:none;height:80px;padding-top:8px;'>
				    <li class="active" >
				        <a href="#Fir" data-toggle="tab">
				            <span class="step">1</span> 
				            <span class="title">
				            <div class="font-size-14 margin-topnew-2">综述</div>
								<div class="font-size-12">General</div>
				            </span>
				        </a>
				    </li>
				    <li>
					    <a href="#Sec" data-toggle="tab">
					    	 <span class="step">2</span> 
					    	 <span class="title">
				                <div class="font-size-14 margin-topnew-2">计划</div>
								<div class="font-size-12">Plan</div>
							</span>
					    </a>
				    </li>
				    <li >
				        <a href="#Thi" data-toggle="tab">
				        	 <span class="step">3</span> 
				            <span class="title">
					            <div class="font-size-14 margin-topnew-2">器材/工具</div>
								<div class="font-size-12">Material/Tool</div>
							</span>
				        </a>
				    </li>
				     <li >
				        <a href="#Fourth" data-toggle="tab">
				        	 <span class="step">4</span> 
				            <span class="title">
				                <div class="font-size-14 margin-topnew-2">工作内容</div>
								<div class="font-size-12">Job Desc</div>
				            </span>
				        </a>
				    </li>
				    <li >
				        <a href="#Five" data-toggle="tab">
				        	 <span class="step">5</span> 
				            <span class="title">
				                <div class="font-size-14 margin-topnew-2">流程信息</div>
								<div class="font-size-12">Process</div>
				            </span>
				        </a>
				    </li>
				</ul>
				</div>
				<!-- 分屏显示 -->
				<div class='clearfix'></div>
				</div>
				<div class='clearfix'></div>
              	<div id="myTabContent" class="tab-content">
              	<div class="tab-pane fade in active" id="Fir" style="padding-top:0px;padding-bottom:0px;">
              	    <div class='input-group-border' >
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>机型</div>
								<div class="font-size-9">A/C Type</div>
							</span>
						 
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id=""  name=""   >
								<option>SY6D</option>
								<option>S76D</option>
								</select>
							</div>
						</div>
						 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>ATA 章节号</div>
								<div class="font-size-9">ATA</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id=""  name="" type="text" maxlength="">
							</div>
						</div>
						
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">颁发日期</div>
								<div class="font-size-9">Issue Date </div>
							</span>
							
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control datepicker" id=""  name="" type="text" maxlength="" >
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">首次技术支援</div>
								<div class="font-size-9">First Support</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
								<label class='margin-right-5 label-input'>&nbsp;<input  checked="checked" name="bb1" type="radio" maxlength="8"  >&nbsp;是&nbsp;&nbsp;</label>
								<label class='label-input'>&nbsp;<input    name="bb1" type="radio" maxlength="8"  >&nbsp;否</label>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">级别</div>
								<div class="font-size-9">Class</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
								<label class='margin-right-5 label-input'>&nbsp;<input  checked="checked"   type="radio" maxlength="8"  >&nbsp;紧急&nbsp;&nbsp;</label>
								<label class='label-input'>&nbsp;<input  type="radio" maxlength="8"  >&nbsp;一般</label>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作类型</div>
								<div class="font-size-9">Job Type</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id=""  name="" >
								<option></option>
								<option>类型1</option>
								<option>类型2</option>
								</select>
							</div>
						</div> 
						<div class="clearfix"></div>
						<!-- 评估单开始 -->
						<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- 技术评估单 -->
						<div class="clearfix"></div>
						<!-- 评估单结束 -->
						</div>
						<!-- 适用设置开始 -->
						<%@ include file="/WEB-INF/views/project2/engineering/applicable_settings.jsp"%>
						<!-- 适用设置结束 -->
						 <div class='input-group-border' >
					 	<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">工作概述</div>
								<div class="font-size-9 ">Summary</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<textarea class="form-control" id=""  name="" maxlength=""style='height:55px;'></textarea>
							</div>
						</div>	
						 
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">符合性</div>
								<div class="font-size-9">Compliance</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id=""  name="" type="text" maxlength=""/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">类别</div>
								<div class="font-size-9">Category</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id=""  name="" >
								<option></option>
								<option>类型1</option>
								<option>类型2</option>
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">分级</div>
								<div class="font-size-9">Classification</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id=""  name="" >
								<option></option>
								<option>类型1</option>
								<option>类型2</option>
								</select>
							</div>
						</div>
						<div class="clearfix"></div>
					 </div>
					 
					<!-- 改版记录开始 -->
					 <%@ include file="/WEB-INF/views/project2/engineering/revision_record.jsp"%> 
					<!-- 改版记录结束 -->
					
					<!-- 维修改装对象开始 -->
					<%@ include file="/WEB-INF/views/project2/engineering/maintenance_modification_obj.jsp"%>
					<!-- 维修改装对象结束 -->
					
					<!-- 重量与平衡开始 -->
					<%@ include file="/WEB-INF/views/project2/engineering/weight_balance.jsp"%>
					<!-- 重量与平衡结束 -->
					
					<!-- 参考文件开始 -->
					<div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading" >
							<div class="font-size-12 ">参考文件</div>
							<div class="font-size-9">Reference</div>
						</div>
						<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<%@ include file="/WEB-INF/views/common/project/reference_list_edit.jsp"%>
					    </div>
					</div>
					<!-- 参考文件结束 -->
					
					<div class='input-group-border'>
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">影响手册</div>
								<div class="font-size-9 ">Manuals Affect</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<textarea class="form-control" id=""  name=""  maxlength="" style='height:55px;'></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工种工时</div>
								<div class="font-size-9">MAN-HOURS</div>
							</span>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-9 padding-leftright-8">
								<div class="input-group input-group-new">
								<input type="text" class="form-control" >
			                    <span class="input-group-addon">人&nbsp;x</span>
			                    <input type="text" class="form-control" >
			                     <span class="input-group-addon">时=8时</span>
			                      <span class="input-group-addon padding-top-0 padding-bottom-0">
			                      <button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
								<div class="font-size-12">工种工时</div>
								<div class="font-size-9">MAN-HOURS</div>
	                   		   </button>
			                      </span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">器材价格</div>
								<div class="font-size-9">Material Price</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group">
									<input type="text" id="jswjbh" class="form-control padding-left-3 padding-right-3" >
									<input type="hidden" id="jswjid" class="form-control">
									<div class="input-group-btn">
										 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">币种 <span class="caret"></span></button>
									        <ul class="dropdown-menu dropdown-menu-right">
									          <li><a href="#">RMB</a></li>
									          <li><a href="#">RMB</a></li>
									        </ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">颁发器材准备通知单</div>
								<div class="font-size-9">MON Distributed</div>
							</span>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group input-group-new">
			                    <span class="input-group-addon">
			                     <label><input  checked="checked"  id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >&nbsp;是&nbsp;&nbsp;</label>
			                    </span>
			                    <span class="input-group-addon">
			                     <label>
			                     <input  id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >&nbsp;否
			                     </label>
			                    </span>
			                    <input type="text" class="form-control" >
			                    <span class="input-group-addon">
			                   
			                     <label>
			                     <input  id="maintenance_schedule_modal_bb" name="bb" type="checkbox" maxlength="8"  >&nbsp; RII
			                     </label>
			                     
			                    </span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="clearfix"></div>
					</div>
					
					<!-- 受影响的出版物      开始 -->
					<%@ include file="/WEB-INF/views/project2/engineering/publication.jsp"%> 
					<!-- 受影响的出版物      结束 -->
					
				   <div class='input-group-border'>
				    <div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">受影响的机载软件清单</div>
							<div class="font-size-9 ">Onboard Software List Affected</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id=""  name=""  maxlength="" style='height:55px;'></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					</div>
					<div class='input-group-border' >
					 <div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
							<div class="font-size-12 ">电气负载数据</div>
							<div class="font-size-9 ">Electrical Load Data</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id=""  name=""  maxlength="" style='height:55px;'></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">维修项目的相关性</div>
							<div class="font-size-9">Item Related</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id=""  name="" type="text" maxlength="">
						</div>
					</div>
					
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">反馈要求</div>
							<div class="font-size-9">Feedback Request</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id=""  name="" type="text" maxlength="">
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>航材需求申请单</div>
							<div class="font-size-9">Material order notice</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id=""  name="" type="text" maxlength="">
						</div>
					</div>
					<div class="clearfix"></div>
					</div>
					<div class='input-group-border' >
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">原有零件处理</div>
							<div class="font-size-9">Disposal of Removed Parts</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id=""  name="" type="text" maxlength="">
						</div>
					</div>
					
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">互换信息</div>
							<div class="font-size-9">Exchange Info</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id=""  name="" type="text" maxlength="">
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>其他</div>
							<div class="font-size-9">Others</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id=""  name="" type="text" maxlength="">
						</div>
					</div>
					<div class="clearfix"></div>
					</div>
					
					<!-- 分发 -->
					<div class='input-group-border' >
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">分发</div>
								<div class="font-size-9">Distribution</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group">
									<input type="text" id="jswjbh" class="form-control padding-left-3 padding-right-3" disabled="disabled">
									<input type="hidden" id="jswjid" class="form-control">
									<span class="input-group-btn">
										 <button class="btn btn-primary form-control" data-toggle="modal" onclick="openTech();" type="button">
											<i class="icon-search cursor-pointer"></i>
										 </button>
									</span>
							    </div>
							</div>
						</div>
						
					<div class="clearfix"></div>
					</div>
				<!-- 分发 -->
              	      
              	      
              	</div>
              	 
              	<div class="tab-pane fade  " id="Sec" >
                   <!-- 监控项设置 -->
              	   <div id="monitor_frame_package"></div>
              	   <!-- 监控项设置 -->
              	   <!-- 计划 -->
              	   <%@ include file="/WEB-INF/views/project2/engineering/eo_plan.jsp"%> 
              	   <!-- 计划 -->
              	   <!-- 工时/停场时间 -->
              	   <%@ include file="/WEB-INF/views/project2/engineering/stopping_time.jsp"%> 
              	   <!-- -工时/停场时间 --> 
              	</div>
              	
              	<div class="tab-pane fade " id="Thi">
              	    <!-- -器材清单列表 -->
            	    <%@ include file="/WEB-INF/views/common/project/equipment_list_edit.jsp"%>
            	    <!-- -器材清单列表 -->
            	     <!-- 工具 -->
            	    <%@ include file="/WEB-INF/views/project2/engineering/tool.jsp"%>
            	     <!-- 工具 -->
            	     <!-- 索赔  -->
					 <%@ include file="/WEB-INF/views/project2/engineering/claim_demage.jsp"%>
					 <!-- 索赔--> 
              	</div>
              	
              	<div class="tab-pane fade  " id="Fourth" >
              	     <!-- 工作内容开始 -->
					 <%@ include file="/WEB-INF/views/project2/engineering/job_content.jsp"%> 
					 <!-- 工作内容开始 -->
              	</div>
              	
              	<div class="tab-pane fade  " id="Five" >
              	   	<!-- 流程 开始 -->
					 <%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp"%> 
					<!-- 流程 结束 -->
              	</div>
              	
                </div>
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
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
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