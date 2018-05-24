<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="EOAddModal" tabindex="-1" role="dialog"  aria-labelledby="EOAddModal" aria-hidden="true" data-keyboard="false" data-backdrop="static">
      <div class="modal-dialog" style='width:98%;'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                        <div class="font-size-14 " id="modalHeadCN">EO新增</div>
							<div class="font-size-12" id="modalHeadENG">EO add</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12" >
              	<div class='col-xs-12  EoModalHeader fixedModalHeader' style='padding:0px;border-bottom:2px solid #b6e3f9;' >
              	<div class='col-xs-7' style='padding-left:0px;padding-right:0px;padding-top:0px;'>
              		<div class="input-group-border padding-leftright-8" style='padding-bottom:8px;margin-bottom:5px;'>
	              		<div class="input-group input-group-new col-xs-12">
							<span class="input-group-addon" style='width:80px;text-align:right;padding-right:8px;padding-top:0px;color:#333;'>
			                    <div class="font-size-12 margin-topnew-2">EO编号</div>
								<div class="font-size-9" style='margin-top:4px;'>EO No.</div>
		                    </span>
		                    <input placeholder='不填写则自动生成' class="form-control" id="eobh_add"  name="" type="text" maxlength="50"  onkeyup='eo_add_alert_Modal.clearCodeValidator(this)'>
		                    <span class="input-group-addon" style='padding-top:0px;color:#333;width:60px;text-align:right;padding-right:5px;'>
			                    <div class="font-size-12 margin-topnew-2"><span style="color: red" class='control-important'>*</span>版本</div>
								<div class="font-size-9" style='margin-top:4px;'>Rev.</div>
							</span>
							<input class="form-control" id="bb_add"  name="" type="text" maxlength="12" onkeyup='eo_add_alert_Modal.clearNoNumTwo(this)'>
							<span class="input-group-addon" id="new_bbh_span" style="display: none" >
	                     		<a id="new_bbh" ></a>
	                     		← 
	                     		<a id="old_bbh" href="#" onclick="eo_add_alert_Modal.showHistoryView()" ></a>
	                     		&nbsp;&nbsp;&nbsp;
	                     	</span>
	                     	<span class='input-group-btn' title="历史列表  List" id="old_bbhlist_span" style="display: none;margin-left:10px;">
								<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"  style="float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
							</span>
							
							<span class="input-group-addon" style='padding-top:0px;color:#333;width:80px;text-align:right;padding-right:5px;'>
			                    <div class="font-size-12 margin-topnew-2">下发生产</div>
								<div class="font-size-9" style='margin-top:4px;'>To Prod</div>
							</span>
							<span class="input-group-addon" style='padding-top:0px;'>
		                        <label class='margin-right-5 label-input' style='color:#333;'><input type='radio' name='isXfsc_add' checked="checked" value="1"/>&nbsp;是&nbsp;&nbsp;</label>
								<label class='label-input' style='color:#333;'><input type='radio' name='isXfsc_add' value="0"/>&nbsp;否</label>
							</span>
	                	</div>
	                	<div class="col-xs-12 input-group input-group-new margin-top-8">
							<span class="input-group-addon" style='width:80px;text-align:right;padding-right:8px;padding-top:2px;vertical-align:top;color:#333;'>
			                    <div class="font-size-12 margin-topnew-2"><span style="color: red" class='control-important'>*</span>标题</div>
								<div class="font-size-9" style='margin-top:4px;'>Title</div>
		                    </span>
	                   		 <textarea class='form-control' id="eozt_add" style='height:55px;' maxlength="600"  onkeyup="eo_add_alert_Modal.changeInput(this)"></textarea>
	                    </div>
	              		<div class='clearfix'></div>
					</div>
              	</div>
             <!-- 分屏显示 -->
 			<div class='col-xs-5' style='padding-left:0px;padding-right:0px;'>
				<ul id="myTab" class="earyWizard fourStep" style='background:none;height:80px;padding-top:8px;'>
				    <li class="active" >
				        <a id="oneA" href="#Fir" data-toggle="tab">
				            <span class="step">1</span> 
				            <span class="title">
				            <div class="font-size-14 margin-topnew-2">综述</div>
								<div class="font-size-12">General</div>
				            </span>
				        </a>
				    </li>
				    <li>
					    <a id="secA" href="#Sec" data-toggle="tab">
					    	 <span class="step">2</span> 
					    	 <span class="title">
				                <div class="font-size-14 margin-topnew-2">计划</div>
								<div class="font-size-12">Plan</div>
							</span>
					    </a>
				    </li>
				    <li>
				        <a id="thiA" href="#Thi" data-toggle="tab">
				        	 <span class="step">3</span> 
				            <span class="title">
					            <div class="font-size-14 margin-topnew-2">器材/工具</div>
								<div class="font-size-12">Material/Tool</div>
							</span>
				        </a>
				    </li>
				     <li >
				        <a id="fourthA"  href="#Fourth" data-toggle="tab">
				        	 <span class="step">4</span> 
				            <span class="title">
				                <div class="font-size-14 margin-topnew-2">工作内容</div>
								<div class="font-size-12">Job Desc</div>
				            </span>
				        </a>
				    </li>
			<!-- 	    <li id="fiveLi">
				        <a id="fiveA" href="#Five" data-toggle="tab">
				        	 <span class="step">5</span> 
				            <span class="title">
				                <div class="font-size-14 margin-topnew-2">流程信息</div>
								<div class="font-size-12">Process</div>
				            </span>
				        </a>
				    </li>  -->
				</ul>
				</div>
				<!-- 分屏显示 -->
				<div class='clearfix'></div>
				</div>
				<div class='clearfix'></div>
              	<div id="myTabContent" class="tab-content nonFixedContent">
              	<div class="tab-pane fade in active" id="Fir" style="padding-top:0px;padding-bottom:0px;">
              	    <div class='input-group-border' >
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red" class='control-important'>*</span>机型</div>
								<div class="font-size-9">A/C Type</div>
							</span>
						 
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id="jx_add">
								</select>
							</div>
						</div>
						 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
							<label class="col-xs-4 col-sm-4 col-xs-8 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red" class='control-important'>*</span>ATA章节号</div>
								<div class="font-size-9 line-height-18">ATA</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class='input-group col-xs-12'>
									<input type="hidden"  id="zjhid_add" class="form-control"/>
									<input type="text"  id="zjhName_add" class="form-control readonly-style" readonly="readonly" ondblclick="eo_add_alert_Modal.openChapterWin(this)" />
									<span  id="zjhbt_add" class='input-group-addon btn btn-default' onclick="eo_add_alert_Modal.openChapterWin(this)">
									 	<i class="icon-search"></i>
									</span>
							    </div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red" class='control-important'>*</span>颁发日期</div>
								<div class="font-size-9">Issue Date </div>
							</span>
							
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control datepicker" id="bfrq_add"  data-date-format="yyyy-mm-dd"  type="text" maxlength="10"   onchange="eo_add_alert_Modal.changeInput(this)">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">首次技术支援</div>
								<div class="font-size-9">First Support</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
								<label class='margin-right-5 label-input'>&nbsp;<input  checked="checked" name="scjszy_add" type="radio" maxlength="8" value="1" >&nbsp;是&nbsp;&nbsp;</label>
								<label class='label-input'>&nbsp;<input    name="scjszy_add" type="radio" maxlength="8" value="0" >&nbsp;否</label>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">级别</div>
								<div class="font-size-9">Class</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
								<label class='margin-right-5 label-input'>&nbsp;<input  checked="checked"   type="radio" maxlength="8" name="jb_add" value="9" >&nbsp;紧急&nbsp;&nbsp;</label>
								<label class='label-input'>&nbsp;<input  type="radio" maxlength="8"  name="jb_add" value="1" >&nbsp;一般</label>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作类别</div>
								<div class="font-size-9">Job Type</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id="gzlx_add" >
								</select>
							</div>
						</div> 
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<input  id="rii_add" type="checkbox" maxlength="8"  style='vertical-align:-4px;'>&nbsp; RII
							</span>
							
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
							
							</div>
						</div>
						
						<div class="clearfix"></div>
						<!-- 评估单开始 -->
						<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- 技术评估单 -->
						<div class="clearfix"></div>
						<!-- 评估单结束 -->
						</div>
						<!-- 适用性设置开始 -->
						<%@ include file="/WEB-INF/views/project2/engineering/applicable_settings.jsp"%>
						<!-- 适用性设置结束 -->
						
						<!-- 工作概述 -->
						 <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#summaryCollapsed" class="collapsed">
							<div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div>
							<div class="pull-left">
							<div class="font-size-12">工作概述</div>
							<div class="font-size-9 ">Summary</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="summaryCollapsed" class="panel-collapse collapse" >
						<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<!--  <div class='input-group-border' > -->
					 	<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">工作概述</div>
								<div class="font-size-9 ">Summary</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<textarea class="form-control" id="gzgs_add"  name="" maxlength="900" style='height:55px;'></textarea>
							</div>
						</div>	
						 
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">符合性</div>
								<div class="font-size-9">Compliance</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="fhx_add"  name="" type="text" maxlength="100"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">类别</div>
								<div class="font-size-9">Category</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id="eolb_add">
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">分级</div>
								<div class="font-size-9">Classification</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id="eofj_add">
								</select>
							</div>
						</div>
						<div class="clearfix"></div>
						</div>
						</div>
						</div>
						
					<!--  </div> -->
					 
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
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#referenceCollapsed" class="collapsed">
							<div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div>
							<div class="pull-left">
							<div class="font-size-12">参考文件 </div>
							<div class="font-size-9 ">Reference</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="referenceCollapsed" class="panel-collapse collapse" >
						<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<%@ include file="/WEB-INF/views/common/project/reference_list_edit.jsp"%>
					    </div>
					</div>
					</div>
					<!-- 参考文件结束 -->
					<!-- 影响手册 -->
					 <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#manualsCollapsed" class="collapsed">
							<div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div>
							<div class="pull-left">
							<div class="font-size-12">影响手册</div>
							<div class="font-size-9 ">Manuals Affect</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="manualsCollapsed" class="panel-collapse collapse" >
						<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
					<!-- <div class='input-group-border'> -->
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">影响手册</div>
								<div class="font-size-9 ">Manuals Affect</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id="yxsc_add"  maxlength="600" style='height:55px;'></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">参考工时</div>
								<div class="font-size-9">MHRs</div>
							</label>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-9 padding-leftright-8">
								<div class="input-group input-group-new" >
									<input id="jhgsRs" name="jhgsRs" type="text" maxlength="12" class="form-control" onkeyup='eo_add_alert_Modal.changeRs(this)'/>
			                    	<span class="input-group-addon">人&nbsp;x</span>
			                    	<input id="jhgsXss" name="jhgsXss" type="text" maxlength="12" class="form-control" onkeyup='eo_add_alert_Modal.changeXss(this)'/>
			                     	<span class="input-group-addon">时=<span id="bzgs">0</span>时</span>
			                     	<%@ include file="/WEB-INF/views/common/project/work_hour_edit.jsp"%><!-- 工种工时 -->
			                	</div>
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
									<input type="text" id="qcjg_add" maxlength="12" class="form-control padding-left-3 padding-right-3" onkeyup='eo_add_alert_Modal.clearNoNumTwo(this)'>
									<div class="input-group-btn">
										 <button type="button" id="btqcjgdw" style="height: 34px;color: #555!important;background-color:white;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
									        <ul class="dropdown-menu dropdown-menu-right" id="qcjgdw_add">
										         
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
			                     	<label>
			                     		<input  checked="checked" value="1" name="bfqczb_add" type="radio" maxlength="8"  >&nbsp;是&nbsp;&nbsp;
			                     	</label>
			                    </span>
			                    <span class="input-group-addon">
				                     <label>
				                    	 <input  value="0" name="bfqczb_add" type="radio" maxlength="8"  >&nbsp;否
				                     </label>
			                    </span>
			                    <input type="text" id="bfqczbtzd_add" class="form-control" maxlength="100" >
			                   <!--  <span class="input-group-addon">
			                    <label>
			                     	<input  id="rii_add" type="checkbox" maxlength="8"  >&nbsp; RII
			                    </label>
			                    </span> -->
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="clearfix"></div>
					</div>
					</div>
					</div>
					<!-- 影响手册 -->
					
					<!-- 受影响的出版物      开始 -->
					<%@ include file="/WEB-INF/views/project2/engineering/publication.jsp"%> 
					<!-- 受影响的出版物      结束 -->
					<!-- 受影响的机载软件清单  -->
				   <div class="panel panel-primary">
					<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
						<a data-toggle="collapse" data-parent="#accordion" href="#softwareCollapsed" class="collapsed">
						<div class="pull-left">
						<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
						</div>
						<div class="pull-left">
						<div class="font-size-12">受影响的机载软件清单 </div>
						<div class="font-size-9 ">Onboard Software List Affected</div>
						</div>
						<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
						<div class="clearfix"></div>
						</a>
					</div>
					<div id="softwareCollapsed" class="panel-collapse collapse" >
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
				    <div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">受影响的机载软件清单</div>
							<div class="font-size-9 ">Onboard Software List Affected</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
							<textarea class="form-control" id="sycjzrjqd_add"  name=""  maxlength="1000" style='height:55px;'></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					</div>
					</div>
					</div>
					<!-- 受影响的机载软件清单 -->
					<!-- 电气负载数据 -->
					 <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#electricalCollapsed" class="collapsed">
							<div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div>
							<div class="pull-left">
							<div class="font-size-12">电气负载数据</div>
							<div class="font-size-9 ">Electrical Load Data</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="electricalCollapsed" class="panel-collapse collapse" >
						<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
					<!-- <div class='input-group-border' > -->
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">电气负载数据变化</div>
							<div class="font-size-9">Electrical Load Data Change</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
                        	<input type='radio' name='is_dqfzsj_add' checked="checked" value="1"/>&nbsp;是
							<input type='radio' name='is_dqfzsj_add' value="0"/>&nbsp;否
						</div>
					 </div>
					 <div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
							<div class="font-size-12 ">电气负载数据</div>
							<div class="font-size-9 ">Electrical Load Data</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
							<textarea class="form-control" id="dqfzsj_add"  name=""  maxlength="1000" style='height:55px;' ></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">维修项目的相关性</div>
							<div class="font-size-9">Item Related</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="wxxmxgx_add" type="text" maxlength="100">
						</div>
					</div>
					
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">反馈要求</div>
							<div class="font-size-9">Feedback Request</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="fkyq_add" type="text" maxlength="100">
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>航材需求申请单</div>
							<div class="font-size-9">Material order notice</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="hcxqsqd_add"  type="text" maxlength="100">
						</div>
					</div>
					<div class="clearfix"></div>
					</div>
					</div>
					</div>
					<!-- 电气负载数据 -->
					<!-- 原有零件处理 -->
					<div class="panel panel-primary">
					<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
						<a data-toggle="collapse" data-parent="#accordion" href="#disposalCollapsed" class="collapsed">
						<div class="pull-left">
						<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
						</div>
						<div class="pull-left">
						<div class="font-size-12">原有零件处理 </div>
						<div class="font-size-9 ">Disposal of Removed Parts</div>
						</div>
						<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
						<div class="clearfix"></div>
						</a>
					</div>
					<div id="disposalCollapsed" class="panel-collapse collapse" >
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
					<!-- <div class='input-group-border' > -->
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">原有零件处理</div>
							<div class="font-size-9">Disposal of Removed Parts</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="yyljcl_add"  name="" type="text" maxlength="100">
						</div>
					</div>
					
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">互换信息</div>
							<div class="font-size-9">Exchange Info</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="hhxx_add"  name="" type="text" maxlength="100">
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>其他</div>
							<div class="font-size-9">Others</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="qt_add"  name="" type="text" maxlength="100">
						</div>
					</div>
					<div class="clearfix"></div>
					</div>
					</div>
					</div>
					
					<!-- 原有零件处理 -->
					<!-- 分发 -->
					<div class="panel panel-primary">
					<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
						<a data-toggle="collapse" data-parent="#accordion" href="#distributionCollapsed" class="collapsed">
						<div class="pull-left">
						<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
						</div>
						<div class="pull-left">
						<div class="font-size-12">分发 </div>
						<div class="font-size-9 ">Distribution</div>
						</div>
						<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
						<div class="clearfix"></div>
						</a>
					</div>
					<div id="distributionCollapsed" class="panel-collapse collapse" >
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
					<!-- <div class='input-group-border' > -->
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">分发</div>
								<div class="font-size-9">Distribution</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input type="text"  value="" name="ff" class="form-control readonly-style" placeholder='' maxlength="100" readonly="readonly" id="ff" ondblclick="eo_add_alert_Modal.openzrdw(this);">
									<span id="ffbt" class="input-group-addon btn btn-default" onclick="eo_add_alert_Modal.openzrdw(this);" style='border-left:0px;'>
										<i class="icon-search"></i>
									</span>
									<input type="hidden" value="" name="ffid" class="form-control " placeholder='' maxlength="" id="ffid">
							    </div>
							</div>
						</div>
						
						<div class="clearfix"></div>
					</div>
					</div>
					</div>
					
				<!-- 分发 -->
              	     
              	</div>
              	 
              	<div class="tab-pane fade  " id="Sec" style='padding-bottom:8px;'>
                   <!-- 监控项设置 -->
              	   <div id="jkxsz_frame_package"></div>
              	   <!-- 监控项设置 -->
              	   <!-- 计划 -->
              	   <%@ include file="/WEB-INF/views/project2/engineering/eo_plan.jsp"%> 
              	   <!-- 计划 -->
              	   <!-- 工时/停场时间 -->
              	   <%@ include file="/WEB-INF/views/project2/engineering/stopping_time.jsp"%> 
              	   <!-- -工时/停场时间 --> 
              	</div>
              	
              	<div class="tab-pane fade " id="Thi">
              		<!-- 器材清单列表 -->	
              	    <%@ include file="/WEB-INF/views/common/project/equipment_list_edit.jsp"%>
					<!-- 工具设备列表 -->
					<%@ include file="/WEB-INF/views/common/project/tools_list_edit.jsp"%>
            	     <!-- 工具 -->
            	     <!-- 索赔  -->
					 <%@ include file="/WEB-INF/views/project2/engineering/claim_demage.jsp"%>
					 <!-- 索赔--> 
              	</div>
              	
              	<div class="tab-pane fade  " id="Fourth" >
              	     <!-- 工作内容开始 -->
              	     <div class="panel panel-primary">
					 <div class="panel-heading bg-panel-heading" >
							<div class="font-size-12 ">工作内容</div>
							<div class="font-size-9">Work Content</div>
						</div>
						<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
							
							<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2"></div>
									<div class="font-size-9"></div>
								</label>
								
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-2 col-md-2 col-sm-3 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">工作内容附件</div>
										<div class="font-size-9">Attachment</div>
									</label>
									<div id="work_content_attachments_single_edit" class="col-lg-10 col-md-10 col-sm-9 col-xs-8 padding-leftright-8">
										<input id="gznrfjid" name="gznrfjid" type="hidden" />
										<%@ include file="/WEB-INF/views/common/attachments/attachments_single_edit.jsp"%><!-- 加载附件信息 -->
									</div>
								</div>
							</div>
							
							<div class='clearfix'></div>
							
							<%@ include file="/WEB-INF/views/common/project/work_content_list_edit.jsp"%><!-- 工作内容 -->
							
							<div class='clearfix'></div>
				
						</div>
						</div>
					 <!-- 工作内容开始 -->
					 
					<!-- 加载附件信息 -->
              	 	<div id="attachments_list_edit" style="display:none">
              	    <div  class="panel panel-primary" style='margin-bottom:0px;'>
              	     	<div class="panel-heading bg-panel-heading" >
						<!-- <div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style"> -->
							<!-- <a data-toggle="collapse" data-parent="#accordion" href="#attachmentCollapsed" class="collapsed"> -->
							<!-- <div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div> -->
							<!-- <div class="pull-left"> -->
							<div class="font-size-12">附件</div>
							<div class="font-size-9 ">Attachment</div>
							<!-- </div> -->
							<!-- <i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div> -->
							<!-- </a> -->
						</div>
						<!-- <div id="attachmentCollapsed" class="panel-collapse collapse" > -->
						
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%>
						
						<!-- </div> -->
					</div>
					</div>
					<!-- 加载附件信息 -->
					
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
						<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
                    <span class="input-group-addon modalfooterbtn">
                        <button id="save_btn"  type="button"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1" onclick="eo_add_alert_Modal.saveEO(1);" >
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
						<button id="submit_btn"  type="button" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1" onclick="eo_add_alert_Modal.saveEO(2);" >
							<div class="font-size-12">提交</div>
							<div class="font-size-9">Submit</div>
						</button>
						<button id="audited_btn" type="button" onclick="eo_add_alert_Modal.audit(3);" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">审核通过</div>
							<div class="font-size-9">Audited</div>
						</button>
						<button id="audit_reject_btn" type="button" onclick="eo_add_alert_Modal.audit(5)" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">审核驳回</div>
							<div class="font-size-9">Audit Reject</div>
						</button>
						<button id="approved_btn" type="button" onclick="eo_add_alert_Modal.approve(4);" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">批准通过</div>
							<div class="font-size-9">Approved</div>
						</button>
						<button id="approved_reject_btn" type="button" onclick="eo_add_alert_Modal.approve(6);" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">批准驳回</div>
							<div class="font-size-9">Rejected</div>
						</button>
	                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal"  onclick="eo_add_alert_Modal.clearDatas();">
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
<%@ include file="/WEB-INF/views/open_win/selectEvaluation.jsp"%> <!-- -选择评估单列表 -->
<%@ include file="/WEB-INF/views/open_win/work_hour_win.jsp"%> <!--工种工时对话框 -->
<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门:分发 -->
<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%><!-------航材对话框 -------->
<%@ include file="/WEB-INF/views/open_win/material_tools.jsp"%><!-------航材对话框 -------->
<%@ include file="/WEB-INF/views/open_win/planePosition_search.jsp"%><!-------飞机站位 -------->
<%@ include file="/WEB-INF/views/project2/engineering/eo_history_win.jsp"%><!------EO历史版本对话框 -------->
<%@ include file="/WEB-INF/views/open_win/inventory_distribution_details_view.jsp"%><!-------库存分布详情 -------->
<%@ include file="/WEB-INF/views/open_win/equivalent_substitution_view.jsp"%><!-------等效替代 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/eo_add.js"></script> <!-- 当前页脚本 -->

