<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new" id="workorder_feedback_Modal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:80%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="modalName">工单完工反馈</div>
							  <div class="font-size-12" id="modalEname">Workorder Complete feedback</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
              	<div class="input-group-border">
					<form id="form">
					<div class="col-lg-12 col-md-12 padding-left-8 margin-top-0 padding-right-8 table-set form-group" style="overflow-x: auto;" >
						<table id="workorder_feedback_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 900px !important">
							<thead>
								<tr>
								<th class="colwidth-5"  >
									<div class="font-size-12 line-height-18" >类型</div>
									<div class="font-size-9 line-height-18" >Type</div>
								</th>
								<th class="colwidth-7"  >
									<div class="font-size-12 line-height-18" >工单编号</div>
									<div class="font-size-9 line-height-18" >WO No.</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18" >任务信息</div>
									<div class="font-size-9 line-height-18" >Task Info</div>
								</th>
								<th class=" colwidth-7" >
									<div class="font-size-12 line-height-18" >ATA章节号</div>
									<div class="font-size-9 line-height-18" >ATA</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18" >工单标题</div>
									<div class="font-size-9 line-height-18" >WO Title</div>
								</th>
								<th class="colwidth-9">
									<div class="font-size-12 line-height-18" >计划工时 </div>
									<div class="font-size-9 line-height-18" >Plan Hour</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18" >执行对象</div>
									<div class="font-size-9 line-height-18" >Executed Object</div>
								</th>							
								</tr> 
			         		 </thead>
							 <tbody id="workorder_feedback_list">
							 	<tr class='text-center'><td colspan='7'>暂无数据 No data.</td></tr>
							 </tbody>
							
						</table>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">实际执行单位</div>
							<div class="font-size-9">Execution Unit</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-8">
							<span class='margin-right-15 label-input'>	<input type="radio" name="workpackage_modal_yjzxdw_radio" value="1" checked="checked" />&nbsp;内部</span>
							<span class='label-input margin-left-15'>	<input type="radio" name="workpackage_modal_yjzxdw_radio" value="2"  />&nbsp;外委</span>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">部门</div>
							<div class="font-size-9">Department</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left ">
							<div class='input-group ' style="min-width:100%">
								<input type="text" value="" name="yjzxdw" class="form-control readonly-style"
								 id="yjzxdw" readonly="readonly"/>
								<span class="input-group-btn" id="yjzxdwbutton">
									<button type="button" class="btn btn-default " id="gzzbtn"
									 data-toggle="modal"
									onclick="openzrdw()">
									<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>				
						<input type="hidden" value="" name="yjzxdwid" class="form-control " placeholder='' maxlength="" id="yjzxdwid">
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">供应商</div>
							<div class="font-size-9">Supplier</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left ">
							<div class='input-group ' style="min-width:100%">
								<input type="text" value="" name="gys" class="form-control readonly-style"
								 id="gys" readonly="readonly"/>
								<span class="input-group-btn" id="gzzbutton">
									<button type="button" class="btn btn-default " id="gzzbtn"
									 data-toggle="modal"
									onclick="openGys()">
									<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>					
						<input type="hidden" value="" name="gysid" class="form-control " placeholder='' maxlength="" id="gysid"/>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">实际开始日期</div>
							<div class="font-size-9">Starting Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="sjwcrq" name="sjwcrq"  />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 "><span style="color:red">*</span>实际完成时间</div>
							<div class="font-size-9">Finished Time</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-0 padding-right-8">
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
							  <input type="text" class='form-control datetimepicker' id="sjwcrq" name="sjwcrq" />
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 padding-left-0 padding-right-0">
							  <input class='form-control datetimepicker' type='text'  id='sjwcsj' name='sjwcsj' readonly/>
							</div>
						</div>
					</div>               									
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">工作者</div>
							<div class="font-size-9">Worker</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left ">
							<div class='input-group ' style="min-width:100%">
								<input type="text" value="" name="gzzname" class="form-control readonly-style"
								 id="gzzname" readonly="readonly"/>
								<span class="input-group-btn" id="gzzbutton">
									<button type="button" class="btn btn-default " id="gzzbtn"
									 data-toggle="modal"
									onclick="openWinUser('fcr')">
									<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>
						<div style="display: none;">
							<input type="text" value="" name="gzzid" id="gzzid">											
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">检查者</div>
							<div class="font-size-9">Examiner</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left">
							<div class='input-group ' style="min-width:100%">
								<input type="text" value="" name="jczname" class="form-control readonly-style"
								 id="jczname" readonly="readonly"/>
								<span class="input-group-btn" id="jczbutton">
									<button type="button" class="btn btn-default " id="fcrbtn"
									 data-toggle="modal"
									onclick="openWinUser('jcz')">
									<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>
						<div style="display: none;">
							<input type="text" value="" name="jczid" id="jczid">											
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">实际工时</div>
							<div class="font-size-9">Hours</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input text id="gzzd" name="gzzd" class="form-control" maxlength="100" >
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">工作站点</div>
							<div class="font-size-9">Work Site</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input text id="gzzd" name="gzzd" class="form-control" maxlength="100" >
						</div>
					</div>
					<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
						<span class="col-lg-1 col-md-1 col-sm-1 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">故障信息</div>
							<div class="font-size-9 ">Fault Info</div>
						</span>
					 	<div class="col-lg-11 col-md-11  col-sm-11 col-xs-9 padding-left-8 padding-right-0">
								<textarea class="form-control" id="gzyq" name="gzyq" placeholder=''   maxlength="300" style="height:55px"></textarea>
						</div>
					</div>
					<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
						<span class="col-lg-1 col-md-1 col-sm-1 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">处理措施</div>
							<div class="font-size-9 ">Treatment Measures</div>
						</span>
					 	<div class="col-lg-11 col-md-11  col-sm-11 col-xs-9 padding-left-8 padding-right-0">
								<textarea class="form-control" id="gzyq" name="gzyq" placeholder=''   maxlength="300" style="height:55px"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>	
					</form>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				<%@ include file="/WEB-INF/views/produce/maintenanceissuedplan/replacementRecord.jsp"%><!-- 拆换件记录 -->
				<div class="clearfix"></div>
				<div id="attachments_list_workorderedit" style="display:none">							
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
				<div class="clearfix"></div>	
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
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="submitFeedback(0)" >
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">取消</div>
								<div class="font-size-9">Cancel</div>
						    </button>
	                    </span>
	               	</div><!-- /input-group -->
				</div>        
				<div class="clearfix"></div> 				
			</div>
          </div>
      </div>
</div>
<!--  弹出框结束-->