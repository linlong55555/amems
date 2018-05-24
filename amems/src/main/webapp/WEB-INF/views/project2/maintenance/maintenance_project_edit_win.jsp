<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>

<style type="text/css">
	#plane_monitor_panel .input-group-addon.dw{
		padding: 0 0 0 0;
		font-size: 12px;
	}
	
	#monitor_setting_modal .input-group-addon.dw{
		padding: 0 0 0 0;
	}
</style>
<!-- 维修项目 -->
<div class="modal fade modal-new" id="open_win_maintenance_project_modal" tabindex="-1" role="dialog" aria-labelledby="open_win_maintenance_project_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:80%">
			<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                	<h4 class="modal-title" >
                  	<div class='pull-left'>
	                    <div class="font-size-14" id="chinaHead">维修项目</div>
						<div class="font-size-12" id="englishHead">Maintenance Item</div>
				  	</div>
				  	<div class='pull-right'>
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  	</div>
				  	<div class='clearfix'></div>
                	</h4>
              </div>
			<div class="modal-body padding-bottom-0">
			
				<div class="col-xs-12 margin-top-8">
              	<div class="input-group-border">
				<form id="maintenance_project_form">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0" >
						<input type="hidden" class="form-control " id="eid" />
						<input type="hidden" class="form-control " id="ebb" />
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">版本</div>
							<div class="font-size-9">Rev.</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<div class="input-group margin-top-8">
								<span id="bbSpan" class="base-color"></span> 
								<span name="lastBbSpan" style="display: inline;">  ←  </span>
								<span name="lastBbSpan" id="lastBbData"></span>
								<span name="lastBbSpan"class="input-group-btn" style="display: table-cell;">
									<i class="attachment-view3 glyphicon glyphicon glyphicon-list color-blue cursor-pointer" style="font-size:15px"></i>
								</span>
						    </div>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">分类</div>
							<div class="font-size-9">category</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8 label-input-div">
							<label class="margin-right-5 label-input djbRadio">
								<input name="ewxxmlx" type="radio" value="4">&nbsp;定检包
							</label>
							<label class="label-input wxxmRadio" onclick="open_win_maintenance_project_modal.switchMonitorType('1')">
								<input name="ewxxmlx" type="radio" value="1">&nbsp;一般项目
							</label>
							<label class="label-input wxxmRadio" onclick="open_win_maintenance_project_modal.switchMonitorType('2')">
								<input name="ewxxmlx" type="radio" value="2">&nbsp;时控项目
							</label>
							<label class="label-input wxxmRadio" onclick="open_win_maintenance_project_modal.switchMonitorType('3')">
								<input name="ewxxmlx" type="radio" value="3">&nbsp;时寿项目
							</label>
						</div>  
					</div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
	                    <label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">有效性</div>
							<div class="font-size-9">Effectivity</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8 label-input-div">
							<label class="margin-right-5 label-input"><input name="ezt" checked="checked" type="radio" value="1">&nbsp;有效</label>
							<label class="label-input"><input name="ezt" type="radio" value="0">&nbsp;无效</label>
						</div>
					</div>
					
					<div class="clearfix"></div>
				
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<input type="hidden" class="form-control" id="ezjh" name="ezjh" />
							<div class="input-group">
								<input id="ezjhName" class="form-control readonly-style" readonly="" type="text" ondblclick="open_win_maintenance_project_modal.openChapterWin()">
			                    <span class="input-group-addon btn btn-default" id="ezjhbtn" onclick="open_win_maintenance_project_modal.openChapterWin()" >
			                    	<i class="icon-search cursor-pointer"></i>
			                    </span>
		                	</div>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18"><span class="identifying" style="color: red">*</span>维修项目大类</div>
							<div class="font-size-9 line-height-18">Class</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8 pull-left">
		                	<select id="edlbh" name="edlbh" class="form-control"></select>
						</div>
					</div>
					
					<div class=" col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0 form-group wxxmDiv" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"></div>
							<div class="font-size-9"></div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<label class="margin-right-5 label-input"><input id="eisBj" name="radio" type="checkbox">&nbsp;RII</label>
							<label class="label-input"><input id="eali" name="radio" type="checkbox">&nbsp;ALI</label>
						</div>
					</div>
					
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>任务号</div>
							<div class="font-size-9">Task No.</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="erwh" name="erwh" maxlength="50" />
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">CMP/CAMP号</div>
							<div class="font-size-9">CMP No.</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<input class="form-control" id="ecmph" name="ecmph" maxlength="50" />
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group wxxmDiv" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">区域</div>
							<div class="font-size-9">Zone</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
								<input id="eqy" class="form-control" type="text">
		                    	<span id="eqy_btn" class="input-group-addon btn btn-default" onclick="open_win_maintenance_project_modal.openZoneWin()"><i class="icon-search"></i></span>
		                	</div>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">参考号</div>
							<div class="font-size-9">Ref No.</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="eckh" maxlength="300" style="height: 75px;"></textarea>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">参考文件</div>
							<div class="font-size-9">Ref Document</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="eckwj" maxlength="300" style="height: 75px;"></textarea>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">备注</div>
							<div class="font-size-9">Note</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="ebz" maxlength="300" style="height: 75px;"></textarea>
						</div>
					</div>
						
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-1 col-sm-1 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>任务描述</div>
							<div class="font-size-9">Task Description</div>
						</label>
						<div class="col-lg-11 col-sm-11 col-xs-9 padding-leftright-8">
							<textarea class="form-control" id="erwms" name="erwms" style="height: 75px;" onkeyup="open_win_maintenance_project_modal.calRwmsCount()"></textarea>
							<small id="rwms_des">已经输入<span id="rwms_count">0</span>个字节，最大4000</small>
						</div>
					</div>
					
				</div>
				<div class="clearfix"></div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 wxxmDiv">
					
					<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">接近</div>
							<div class="font-size-9">Access</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<div class="input-group cursor-pointer" style="min-width:26.4%;">
								<div id="jj_e" class='form-control base-color readonly-style' style='border-right:0px;border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;font-size:14px;background-color:#fff;' ondblclick="open_win_maintenance_project_modal.openAccessWin()">
								</div> 
			                    <div id="jj_btn" class="input-group-addon btn-default" style='border-left:1px solid #d5d5d5;border-right:1px solid #d5d5d5;padding-left:0px;padding-right:0px;width:40px;' onclick="open_win_maintenance_project_modal.openAccessWin()"><i class='icon-search'></i></div>
		                	</div>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-xs-12 col-sm-4 padding-left-0 padding-right-0 form-group">
						
						<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞机站位</div>
							<div class="font-size-9">Aircraft Stations</div>
						</label>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 padding-leftright-8">
							<div class="input-group col-xs-12">
								<input id="efjzw" class="form-control" type="text">
		                    	<span id="fjzw_btn" class="input-group-addon btn btn-default" onclick="open_win_maintenance_project_modal.openStationWin()"><i class="icon-search"></i></span>
		                	</div>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">工作类别</div>
							<div class="font-size-9">Work Type</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<select id="egzlx" class="form-control" >
							</select>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">项目类别</div>
							<div class="font-size-9">Project Type</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<select id="exmlx" class="form-control" >
							</select>
						</div>
					</div>
					
					<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" >
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">计划工时</div>
							<div class="font-size-9">MHRs</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
								
							<div class="input-group input-group-new">
								<input type="text" maxlength="8" class="form-control " 
									id="ejhgsRs" onkeyup='open_win_maintenance_project_modal.clearNoNumber(this)'>
		                    	<span class="input-group-addon">人&nbsp;x</span>
		                    	<input maxlength="6" type="text" class="form-control" 
									id="ejhgsXss" onkeyup='open_win_maintenance_project_modal.clearNoNumTwo(this)'>
		                        <span class="input-group-addon">时=
		                        	<span id="ebzgs">8</span>时
		                        </span>
		                	</div><!-- /input-group -->
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group" id="wordCard_div">
						<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">工卡</div>
							<div class="font-size-9">Work Card</div>
						</label>
						<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
							<input class="form-control" id="egkbh" maxlength="50" type="text" disabled="disabled">
						</div>
					</div>
					
					<div class="clearfix"></div>
					
				</div>
				<div class="clearfix"></div>
				</form>
				</div>
				
				<div id="monitor_frame_package"></div>
				
				<div id="monitor_frame_plane"></div>
				
				<div class="clearfix"></div>
				
				<div class="panel panel-primary padding-left-0 padding-right-0" id="component_monitor_panel">
					<div class="panel-heading bg-panel-heading" >
						<div class="font-size-12">适用性及监控项设置</div>
						<div class="font-size-9">Applicability And Monitor Setting</div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞机注册号</div>
							<div class="font-size-9">A/C REG</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-8">
							<div id="esyxdiv_component" class="col-lg-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0">
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0 padding-top-10">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">监控设置</div>
								<div class="font-size-9">Monitor Setting</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" style="overflow-x:auto;">
								<!-- start:table -->
								<table class="table table-bordered table-striped table-thin table-hover text-center table-set" style="min-width: 500px;">
									<thead>
								   		<tr>
								   			<th class="colwidth-5">
												<div class="text-center">
													<button class="line6 line6-btn" onclick="open_win_maintenance_project_modal.showComponentMonitorModal()">
														<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
													</button>
												</div>
											</th>
											<th style="width: 30px;">
												<div class="font-size-12 line-height-12">序号</div>
												<div class="font-size-9 line-height-12">No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">部件</div>
												<div class="font-size-9 line-height-12">Component</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">厂家/供应商</div>
												<div class="font-size-9 line-height-12">Manufacturer</div>
											</th>
											<th class="colwidth-10" id="maintenance_version_jkxm_modal">
												<div class="font-size-12 line-height-12">监控项目</div>
												<div class="font-size-9 line-height-12">Monitor Item</div>
											</th>
											<th class="colwidth-10" id="maintenance_version_sj_modal">
												<div class="font-size-12 line-height-12">首次</div>
												<div class="font-size-9 line-height-12">Inti</div>
											</th>
											<th class="colwidth-10" id="maintenance_version_zq_modal">
												<div class="font-size-12 line-height-12">周期</div>
												<div class="font-size-9 line-height-12">Cycle</div>
											</th>
											<th class="colwidth-10 upward detail_rc_th" id="maintenance_version_rc_modal" th_class="detail_rc_th" td_class="detail_rc_td" onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
												<div class="font-size-12 line-height-12">容差</div>
												<div class="font-size-9 line-height-12">Tolerance</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="component_monitor_list">
									</tbody>
								</table>
								<!-- end:table -->
							</div>
							<div class="clearfix"></div>
							
							<div class="col-sm-12 padding-left-0 padding-right-0">
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">计算公式</div>
										<div class="font-size-9">Formula</div>
									</label>
									<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 padding-leftright-8">
										<select class="form-control" id="monitor_frame_component_jsgs">
										</select>
									</div>
								</div>
								<div class="col-lg-1 col-md-2 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="label-input">
										<input name="isHdwz" type="checkbox">&nbsp;后到为准
									</label>
								</div>
								<div class="clearfix"></div>
								<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">间隔描述</div>
										<div class="font-size-9">Remark</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 padding-leftright-8">
									<textarea class="form-control" id="monitor_frame_component_jgms" maxlength="300" style="height: 75px;"></textarea>		
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="clearfix"></div>
				
				<div class="panel panel-primary">
					<div class="panel-heading bg-panel-heading">
						<div class="font-size-12" id="associateCnHead"></div>
						<div class="font-size-9" id="associateEngHead"></div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<div class="col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group" id="ssdjb_div">
							<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">所属定检包</div>
								<div class="font-size-9">Package</div>
							</label>
							<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
								<input class="form-control" disabled="disabled" type="text">
							</div>
						</div>
						<div class="clearfix"></div>
						<div id="qdeo_div">
							<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group">
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">取代EO</div>
									<div class="font-size-9 line-height-18">Replace EO</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
									<div class="input-group">
										<input id="eeobh" class="form-control readonly-style" readonly="" type="text" ondblclick="open_win_maintenance_project_modal.openEOWin()">
										<input id="eeoid" type="hidden">
					                    <span class="input-group-addon btn btn-default" onclick="open_win_maintenance_project_modal.openEOWin()" >
					                    	<i class="icon-search cursor-pointer"></i>
					                    </span>
				                	</div>
								</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group" id="eo_title_div" style="display: none;">
								<label class="col-lg-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">EO标题</div>
									<div class="font-size-9">Title</div>
								</label>
								<div class="col-lg-11 col-sm-11 col-xs-11 padding-leftright-8">
									<label class="label-input margin-top-10" id="eo_title"></label>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2" id="associateColCnHead"></div>
								<div class="font-size-9" id="associateColEngHead"></div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" style="overflow-x:auto;">
								<!-- start:table -->
								<table class="table table-bordered table-striped table-thin table-hover text-center table-set" style="min-width: 500px;">
									<thead>
								   		<tr>
								   			<th style="width: 30px;">
												<div class="text-center">
													<button class="line6 line6-btn" onclick="open_win_maintenance_project_modal.openProjectWinAdd()">
														<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
													</button>
												</div>
											</th>
											<th style="width: 30px;">
												<div class="font-size-12 line-height-12">序号</div>
												<div class="font-size-9 line-height-12">No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">版本</div>
												<div class="font-size-9 line-height-12">Rev.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">任务号</div>
												<div class="font-size-9 line-height-12">Task No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">参考号</div>
												<div class="font-size-9 line-height-12">Ref No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">适用性</div>
												<div class="font-size-9 line-height-12">Applicability</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">参考文件</div>
												<div class="font-size-9 line-height-12">Ref Document</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">工时</div>
												<div class="font-size-9 line-height-12">MHRs</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="maintenance_project_list">
										
									</tbody>
								</table>
								<!-- end:table -->
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				
				<div class="clearfix"></div>
				<div id="attachments_list_edit" style="display:none">
					
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				
				</div>
			 	<div class="clearfix"></div>
			 	</div>
			</div>
			
			<div class="clearfix"></div>
			<div class="modal-footer">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
						<span class="input-group-addon modalfooterbtn">
							<button id="formSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="open_win_maintenance_project_modal.save();">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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


<!-- 监控项设置模态框Start -->
<div id="monitor_modal_component" class="modal fade modal-new" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard= false></div>
<!-- 监控项设置模态框End -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/maintenance/maintenance_project_edit_win.js"></script>
<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_setting.js"></script>
<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/maintenance/maintenance_project_history.js"></script>
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
<%@ include file="/WEB-INF/views/open_win/maintenance_class.jsp"%>
<%@ include file="/WEB-INF/views/open_win/access_list_edit.jsp"%><!-- 接近弹出框 -->
<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%><!-------航材对话框 -------->
<%@ include file="/WEB-INF/views/open_win/maintenance_project.jsp"%><!-------维修项目对话框 -------->
<%@ include file="/WEB-INF/views/open_win/planePosition_search.jsp"%><!-------飞机站位 -------->
<%@ include file="/WEB-INF/views/open_win/eo_win.jsp"%><!-------eo -------->
