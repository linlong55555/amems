<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="work_card_edit_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="work_card_edit_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:90%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">工卡管理</div>
							<div class="font-size-12" id="modalHeadENG">Work Card</div>
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
              			<form id="work_card_form">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>工卡编号</div>
								<div class="font-size-9">Work Card No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="gkh" name="gkh" type="text" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>版本</div>
								<div class="font-size-9">Rev.</div>
							</label>
							<div id="bbViewHistoryDiv" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group input-group-new" >
									<input class="form-control" id="new_bb" name="new_bb" type="text" onkeyup='work_card_edit_alert_Modal.changeBb(this)' />
			                     	<span class="input-group-addon">
			                     		← <a id="new_bb_a" href="javascript:void(0);" onclick="work_card_edit_alert_Modal.view()" ></a>
			                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                     	</span>
			                     	<span class='input-group-btn' title="历史列表  List">
										<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"  style="float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
									</span>
			                	</div>
							</div>
							<div id="bbNoViewHistoryDiv" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="bb" name="bb" type="text" onkeyup='work_card_edit_alert_Modal.changeBb(this)' />
							</div>
							<div id="bbViewHistoryDiv_edit" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group input-group-new" >
									<input class="form-control" id="edit_bb" name="edit_bb" type="text" onkeyup='work_card_edit_alert_Modal.changeBb(this)' />
			                     	<span class="input-group-addon">
			                     		← <a id="edit_bb_a" href="javascript:void(0);" onclick="work_card_edit_alert_Modal.viewOld()" ></a>
			                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                     	</span>
			                     	<span class='input-group-btn' title="历史列表  List">
										<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"  style="float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
									</span>
			                	</div>
							</div>
							<div id="bbViewHistoryDiv_view" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-8 margin-top-5" style="display: none;">
								<div class="input-group" >
									<span id="bb_view"></span>
									<span>  ←  </span>
			                     	<a id="old_bb_a" href="javascript:void(0);" onclick="work_card_edit_alert_Modal.viewOld()" ></a>
			                     	<span  class='input-group-btn' title="历史列表  List" >
										<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"  style="float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
									</span>
			                	</div>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>机型</div>
								<div class="font-size-9">A/C Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="jx" class="form-control" onChange="work_card_edit_alert_Modal.changeModel()" >
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">必检</div>
								<div class="font-size-9">RII</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input id="isBj" name='isBj' type='checkbox' /></label>
							</div>
						</div>
						
						</form> 
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">维修项目</div>
								<div class="font-size-9">Project</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="wxxmid" type="hidden" class="form-control" >
									<input id="wxxmbh" type="hidden" class="form-control" >
									<input id="wxxm" type="text" class="form-control" onBlur="work_card_edit_alert_Modal.loadMtRel()" />
			                    	<span id="wxxm_search_btn" class="input-group-addon btn btn-default" onclick="work_card_edit_alert_Modal.openProjectWinAdd()"><i class='icon-search'></i></span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">任务单号</div>
								<div class="font-size-9">Task No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="rwdh" name="rwdh" type="text" maxlength="50" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作单号</div>
								<div class="font-size-9">Work No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="gzdh" name="gzdh" type="text" maxlength="50" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">控制号</div>
								<div class="font-size-9">Control No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="kzh" name="kzh" type="text" maxlength="50" />
							</div>
						</div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">任务描述</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<input id="rwms" name="rwms" type="text" class="form-control" readonly />
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="zjh_name_e" type="text" class="form-control readonly-style" ondblclick="work_card_edit_alert_Modal.openChapterWin()" readonly />
									<input id="zjh_e" type="hidden" class="form-control" />
			                    	<span id="zjh_e_btn" class="input-group-addon btn btn-default" onclick="work_card_edit_alert_Modal.openChapterWin()"><i class='icon-search'></i></span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">CMP号</div>
								<div class="font-size-9">CMP No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="cmph" name="cmph" type="text" maxlength="50" />
							</div>
						</div>
						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工卡附件</div>
								<div class="font-size-9">Attachment</div>
							</label>
							<input id="gkfjid" name="gkfjid" type="hidden" />
							<div id="work_card_attachments_single_edit" class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<%@ include file="/WEB-INF/views/common/attachments/attachments_single_edit.jsp"%><!-- 加载附件信息 -->
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工卡标题</div>
								<div class="font-size-9">Title</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="gzbt" name="gzbt" style="height: 55px;" class="form-control" maxlength="300" ></textarea>
							</div>
						</div>
						
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作类别</div>
								<div class="font-size-9">Category</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="gzlx_e" class="form-control">
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工卡类型</div>
								<div class="font-size-9">Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="gklx_e" class="form-control">
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">专业</div>
								<div class="font-size-9">Skill</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="zy_e" class="form-control">
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作组</div>
								<div class="font-size-9">Work Group</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="gzz_e" class="form-control">
								</select>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">间隔/重复</div>
								<div class="font-size-9">Interval/Repeat</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<input id="bz" name="bz" type="text" class="form-control"  value="" maxlength="300" />
							</div>
						</div>
						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">参考工时</div>
								<div class="font-size-9">MHRs</div>
							</label>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-9 padding-leftright-8">
							<!-- <div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" > -->
								<div class="input-group input-group-new" >
									<input id="jhgsRs" name="jhgsRs" type="text" class="form-control" onkeyup='work_card_edit_alert_Modal.changeRs(this)' style="min-width: 80px;"/>
			                    	<span class="input-group-addon">人&nbsp;x</span>
			                    	<input id="jhgsXss" name="jhgsXss" type="text" class="form-control" onkeyup='work_card_edit_alert_Modal.changeXss(this)' style="min-width: 80px;" />
			                     	<span class="input-group-addon">时=<span id="bzgs">0</span>时</span>
			                     	<%@ include file="/WEB-INF/views/common/project/work_hour_edit.jsp"%><!-- 工种工时 -->
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<!-- <div id="eqydiv_edit" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">区域</div>
								<div class="font-size-9">Zone</div>
							</label>
							<div id="eqydiv"  class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							</div>
						</div>
						
						<div id="eqydiv_view" class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">区域</div>
								<div class="font-size-9">Zone</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group" style="min-width:17%;">
									<div id="eqy_view" class='form-control base-color div-readonly-style' style='border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
									</div> 
			                	</div>
							</div>
						</div> -->
						
						<div id="eqydiv_edit" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">区域</div>
								<div class="font-size-9">Zone</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<div class="input-group col-xs-12">
									<input id="eqy" type="text" class="form-control" />
			                    	<span id="eqy_btn" class="input-group-addon btn btn-default" onclick="work_card_edit_alert_Modal.openZoneWin()"><i class='icon-search'></i></span>
			                	</div>
							</div>
						</div>
						
						<div id="eqydiv_view" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">区域</div>
								<div class="font-size-9">Zone</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<input id="eqy_view" type="text" class="form-control" readonly/>
							</div>
						</div>
						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">飞机站位</div>
								<div class="font-size-9">Aircraft Stations</div>
							</label>
							<div id="fjzw_div_edit" class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<div class="input-group col-xs-12">
									<input id="efjzw" type="text" class="form-control" />
			                    	<span id="fjzw_btn" class="input-group-addon btn btn-default" onclick="work_card_edit_alert_Modal.openStationWin()"><i class='icon-search'></i></span>
			                	</div>
							</div>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<input id="fjzw_div_view" type="text" class="form-control" readonly/>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">接近</div>
								<div class="font-size-9">Access</div>
							</label>
							<div id="jj_div_edit" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group" style="min-width:17%;">
									<div ondblclick="work_card_edit_alert_Modal.openAccessWin()" id="jj_e" class='form-control base-color readonly-style' style='border-right:0px;border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
									</div> 
				                    <div id="jj_btn" class="input-group-addon btn btn-default" style='border-left:1px solid #d5d5d5;padding-left:0px;padding-right:0px;width:38px;' onclick="work_card_edit_alert_Modal.openAccessWin()"><i class='icon-search'></i></div>
			                	</div>
							</div>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group" style="min-width:17%;">
									<div id="jj_div_view" class='form-control base-color div-readonly-style' style='border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
									</div> 
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<!-- <div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">飞机站位</div>
								<div class="font-size-9">Aircraft Stations</div>
							</label>
							<div id="fjzw_div_edit" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group" style="min-width:17%;">
									<div ondblclick="work_card_edit_alert_Modal.openStationWin()" id="efjzw" class='form-control base-color readonly-style' style='border-right:0px;border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
									</div> 
				                    <div id="fjzw_btn" class="input-group-addon btn btn-default" style='border-left:1px solid #d5d5d5;padding-left:0px;padding-right:0px;width:38px;' onclick="work_card_edit_alert_Modal.openStationWin()"><i class='icon-search'></i></div>
			                	</div>
							</div>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group" style="min-width:17%;">
									<div id="fjzw_div_view" class='form-control base-color div-readonly-style' style='border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
									</div> 
			                	</div>
							</div>
						</div> -->
						
						<div id="esydwdiv_edit" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">适用单位</div>
								<div class="font-size-9">Applicable Unit</div>
							</label>
							<div id="esydwdiv"  class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							</div>
						</div>
						
						<div id="esydwdiv_view" class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">适用单位</div>
								<div class="font-size-9">Applicable Unit</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group" style="min-width:17%;">
									<div id="esydw_view" class='form-control base-color div-readonly-style' style='border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
									</div> 
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">是否清洁</div>
								<div class="font-size-9">Is Clean</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input id="worktype" name='worktype' type='checkbox' /></label>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">依据文件/版本</div>
								<div class="font-size-9">REF/Rev.</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<input id="yjwj" name="yjwj" type="text" class="form-control"  value="" maxlength="1000" />
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">特别适用性说明</div>
								<div class="font-size-9">Description</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 75px;" class="form-control" id="tbsysm" name="tbsysm" maxlength="1000" ></textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作概述</div>
								<div class="font-size-9">Summary</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 95px;" class="form-control" id="gzgs" name="gzgs" maxlength="1000" ></textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- 技术评估单 -->
						
						<div class="clearfix"></div>
						
						<%@ include file="/WEB-INF/views/common/project/reference_list_edit.jsp"%><!-- 参考文件 -->
						
						<div class="clearfix"></div>
						<!-- 
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">适用性</div>
								<div class="font-size-9">Applicability</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
								<div class="input-group" style="min-width:17%;">
									<div readonly id="syx_div_view" class='form-control' style='border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
									</div> 
			                	</div>
							</div>
						</div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group" style="margin-bottom: 5px;">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">维修项目相关信息</div>
								<div class="font-size-9">Project</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<table class="table table-bordered table-striped table-hover text-center table-set table-thin" style="min-width: 500px;width: 70%">
					        		<thead>
								   		<tr>
										<th class="colwidth-7" id="maintenance_version_bj">
											<div class="font-size-12 line-height-12">部件</div>
											<div class="font-size-9 line-height-12">Component</div>
										</th>
										<th class="colwidth-9" id="maintenance_version_jkxm">
											<div class="font-size-12 line-height-12">监控项目</div>
											<div class="font-size-9 line-height-12">Monitor Item</div>
										</th>
										<th class="colwidth-9" id="maintenance_version_sj">
												<div class="font-size-12 line-height-12">首检</div>
												<div class="font-size-9 line-height-12">INTI</div>	
										</th>
										<th class="colwidth-7" id="maintenance_version_zq">
											<div class="font-size-12 line-height-12">周期</div>
											<div class="font-size-9 line-height-12">Cycle</div>
										</th>
										<th class="colwidth-15" id="maintenance_version_rc">
											<div class="font-size-12 line-height-12">容差(-/+)</div>
											<div class="font-size-9 line-height-12">Tolerance(-/+)</div>
										</th>
										 </tr>
									</thead>
					        		<tbody id="maintenance_version_list">
					           		</tbody>
				           		</table>
							</div>
						</div>
						
						<div class="clearfix"></div> -->
						
						<%@ include file="/WEB-INF/views/common/project/work_card_list_edit.jsp"%><!-- 相关工卡 -->
						
						<div class="clearfix"></div>
						
					</div>
					
					<div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading" >
							<div class="font-size-12 ">维修项适用性及监控条件</div>
							<div class="font-size-9">Project</div>
						</div>
						<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
							
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
									<div class="font-size-12 margin-topnew-2">适用性</div>
									<div class="font-size-9">Applicability</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0">
									<div class="input-group" style="min-width:17%;">
										<div readonly id="syx_div_view" class='form-control' style='border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
										</div> 
				                	</div>
								</div>
							</div>
							
							<div class='clearfix'></div>
							
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
							
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
									<div class="font-size-12 margin-topnew-2">维修项及监控条件</div>
									<div class="font-size-9">Project</div>
								</label>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0">
									
									<!-- start:table -->
									<table class="table table-bordered table-striped table-hover text-center table-set table-thin" style="min-width: 500px;width: 70%">
						        		<thead>
									   		<tr>
											<th class="colwidth-7" id="maintenance_version_bj">
												<div class="font-size-12 line-height-12">部件</div>
												<div class="font-size-9 line-height-12">Component</div>
											</th>
											<th class="colwidth-9" id="maintenance_version_jkxm">
												<div class="font-size-12 line-height-12">监控项目</div>
												<div class="font-size-9 line-height-12">Monitor Item</div>
											</th>
											<th class="colwidth-9" id="maintenance_version_sj">
													<div class="font-size-12 line-height-12">首检</div>
													<div class="font-size-9 line-height-12">INTI</div>	
											</th>
											<th class="colwidth-7" id="maintenance_version_zq">
												<div class="font-size-12 line-height-12">周期</div>
												<div class="font-size-9 line-height-12">Cycle</div>
											</th>
											<th class="colwidth-15" id="maintenance_version_rc">
												<div class="font-size-12 line-height-12">容差(-/+)</div>
												<div class="font-size-9 line-height-12">Tolerance(-/+)</div>
											</th>
											 </tr>
										</thead>
						        		<tbody id="maintenance_version_list">
						           		</tbody>
					           		</table>
									<!-- end:table -->
									
								</div>
							
							</div>
							
							<div class='clearfix'></div>
				
						</div>
					</div>
					
					<%@ include file="/WEB-INF/views/common/project/equipment_list_edit.jsp"%><!-- 器材清单列表 -->
					
					<%@ include file="/WEB-INF/views/common/project/tools_list_edit.jsp"%><!-- 工具设备列表 -->
					
					<div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading" >
							<div class="font-size-12 ">工作内容</div>
							<div class="font-size-9">Work Content</div>
						</div>
						<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
							
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
									<div class="font-size-12 margin-topnew-2">工作内容附件</div>
									<div class="font-size-9">Attachment</div>
								</label>
								<div id="work_content_attachments_single_edit" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0">
									<input id="gznrfjid" name="gznrfjid" type="hidden" />
									<%@ include file="/WEB-INF/views/common/attachments/attachments_single_edit.jsp"%><!-- 加载附件信息 -->
								</div>
							</div>
							
							<div class='clearfix'></div>
							
							<%@ include file="/WEB-INF/views/common/project/work_content_list_edit.jsp"%><!-- 工作内容 -->
							
							<div class='clearfix'></div>
				
						</div>
					</div>
					
					<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp" %> <!--流程信息 -->	
					
					<div id="attachments_list_edit" style="display:none">
					
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					
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
	                    	<button id="save_btn" type="button" onclick="javascript:work_card_edit_alert_Modal.setData('');" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="submit_btn" type="button" onclick="javascript:work_card_edit_alert_Modal.setData('submit');"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button id="audited_btn" type="button" onclick="javascript:work_card_edit_alert_Modal.audit('audit');" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">审核通过</div>
								<div class="font-size-9">Audited</div>
							</button>
							<button id="audit_reject_btn" type="button" onclick="javascript:work_card_edit_alert_Modal.audit();" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">审核驳回</div>
								<div class="font-size-9">Audit Reject</div>
							</button>
							<button id="approved_btn" type="button" onclick="javascript:work_card_edit_alert_Modal.approve('approve');" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">批准通过</div>
								<div class="font-size-9">Approved</div>
							</button>
							<button id="approved_reject_btn" type="button" onclick="javascript:work_card_edit_alert_Modal.approve();" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">批准驳回</div>
								<div class="font-size-9">Rejected</div>
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
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/workcard/workcard_edit.js"></script>
<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
<%@ include file="/WEB-INF/views/open_win/selectEvaluation.jsp"%><!-- -选择评估单列表 -->
<%@ include file="/WEB-INF/views/project2/workcard/history_view_win.jsp"%><!------历史版本对话框 -------->
<%@ include file="/WEB-INF/views/open_win/material_tools.jsp"%><!-------航材对话框 -------->
<%@ include file="/WEB-INF/views/open_win/work_hour_win.jsp"%><!-------工种工时对话框 -------->
<%@ include file="/WEB-INF/views/open_win/maintenance_project.jsp"%><!-------维修项目对话框 -------->
<%@ include file="/WEB-INF/views/open_win/work_card_win.jsp"%><!-------工卡对话框 -------->
<%@ include file="/WEB-INF/views/open_win/planePosition_search.jsp"%><!-------飞机站位 -------->
<%@ include file="/WEB-INF/views/open_win/inventory_distribution_details_view.jsp"%><!-------库存分布详情 -------->
<%@ include file="/WEB-INF/views/open_win/equivalent_substitution_view.jsp"%><!-------等效替代 -------->