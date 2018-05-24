<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="workorder135_add_Modal" tabindex="-1" role="dialog"  aria-labelledby="workorder135_add_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:98%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">工单管理</div>
							<div class="font-size-12" id="modalHeadENG">WorkOrder</div>
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
              			<form id="workorder135_form">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>工单编号</div>
								<div class="font-size-9">W/O No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="wo135add_gdbh" name="wo135add_gdbh" type="text" maxlength="50" placeholder='不填写则自动生成'/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>飞机注册号</div>
								<div class="font-size-9">A/C REG.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="wo135add_fjzch" name="wo135add_fjzch" class="form-control" onChange="Workorder135AddWin.changeLoadDatasByJX()" >
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><!-- <span style="color: red">*</span> -->ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="wo135add_zjhName" type="text" class="form-control readonly-style" ondblclick="Workorder135AddWin.openChapterWin()" readonly />
									<input id="wo135add_zjhid" type="hidden" class="form-control" />
			                    	<span id="wo135add_zjhbtn" class="input-group-addon btn btn-default" onclick="Workorder135AddWin.openChapterWin()"><i class='icon-search'></i></span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">必检</div>
								<div class="font-size-9">RII</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input id="wo135add_bjbs" type="checkbox"/></label>
							</div>
						</div>
						
						
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">来源工单</div>
								<div class="font-size-9">Originating Task</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="wo135add_lyrwh" type="text" class="form-control readonly-style" ondblclick="Workorder135AddWin.openWinLyrwh()" readonly />
									<input id="wo135add_lyrwid" type="hidden" class="form-control" />
			                    	<span id="wo135add_lyrwbtn" class="input-group-addon btn btn-default" onclick="Workorder135AddWin.openWinLyrwh()"><i class='icon-search'></i></span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">参考工卡</div>
								<div class="font-size-9">Source Card</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="wo135add_gkbh" type="text" class="form-control readonly-style" ondblclick="Workorder135AddWin.openLygkWin()" readonly />
									<input id="wo135add_gkid" type="hidden" class="form-control" />
			                    	<span id="wo135add_gkbtn" class="input-group-addon btn btn-default" onclick="Workorder135AddWin.openLygkWin()"><i class='icon-search'></i></span>
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工包号</div>
								<div class="font-size-9">Package No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group " style="width:100%">
									<input class="form-control" id="wo135add_gbid" name="" type="hidden" disabled="disabled"/>
									<input class="form-control readonly-style" id="wo135add_gbbh" name="wo135add_gbbh" type="text" ondblclick="Workorder135AddWin.openWin135Package()" readonly />
									<span id="wo135add_gbbtn" class="input-group-addon btn btn-default" onclick="Workorder135AddWin.openWin135Package()"><i class='icon-search'></i></span>
								</div>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="wo135add_zt_div">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><!-- <span style="color: red">*</span> -->状态</div>
								<div class="font-size-9">Status</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="wo135add_zt" name="" type="text" maxlength="100" disabled="disabled"/>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>缺陷描述</div>
								<div class="font-size-9">Defect Desc</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="wo135add_gdbt" name="wo135add_gdbt" style="height: 55px;" class="form-control" onkeyup="Workorder135AddWin.calRwmsCount()"></textarea>
								<small id="rwms_des">已经输入<span id="rwms_count">0</span>个字节，最大4000</small>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">制单日期</div>
								<div class="font-size-9">Create Date</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="wo135add_kdrq" name="wo135add_kdrq" type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">计划开始日期</div>
								<div class="font-size-9">Plan Start Date</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="wo135add_jh_ksrq" name="wo135add_jh_ksrq" type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">计划结束日期</div>
								<div class="font-size-9">Plan End Date</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="wo135add_jh_jsrq" name="wo135add_jh_jsrq" type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd"/>
							</div>
						</div>
						
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="wo135add_bgrdiv">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">报告人</div>
								<div class="font-size-9">Reporter</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="wo135add_bgr" type="text" class="form-control readonly-style" ondblclick="Workorder135AddWin.openUser('wo135add_bgr')" readonly />
									<input id="wo135add_bgrid" type="hidden" class="form-control" />
			                    	<span id="wo135add_bgrbtn" class="input-group-addon btn btn-default" onclick="Workorder135AddWin.openUser('wo135add_bgr')"><i class='icon-search'></i></span>
			                	</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">专业</div>
								<div class="font-size-9">Skill</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="wo135add_zy" class="form-control">
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><!-- <span style="color: red">*</span> -->站点</div>
								<div class="font-size-9">Station</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="wo135add_jh_zd" name="" type="text" maxlength="100" />
							</div>
						</div>
						
						<!-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">主工种</div>
								<div class="font-size-9">Main Workcenter</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="gzlx_e" class="form-control">
								</select>
							</div>
						</div> -->
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作类别</div>
								<div class="font-size-9">Job Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select id="wo135add_gzlb" class="form-control">
								</select>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
<!-- 						<div id="eqydiv_edit" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group"> -->
<!-- 							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0"> -->
<!-- 								<div class="font-size-12 margin-topnew-2">区域</div> -->
<!-- 								<div class="font-size-9">Zone</div> -->
<!-- 							</label> -->
<!-- 							<div id="eqydiv"  class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8"> -->
<!-- 							</div> -->
<!-- 						</div> -->
						<div id="eqydiv_edit" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">区域</div>
								<div class="font-size-9">Zone</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<div class="input-group " style="width: 100%">
									<input id="eqy" type="text" name="eqy" maxlength="4000" class="form-control" />
			                    	<span id="eqy_btn" class="input-group-addon btn btn-default" onclick="Workorder135AddWin.openZoneWin()"><i class='icon-search'></i></span>
			                	</div>
							</div>
						</div>						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">接近</div>
								<div class="font-size-9">Access</div>
							</label>
							<div id="jj_div_edit" class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12" >
									<div ondblclick="Workorder135AddWin.openAccessWin()" id="jj_e" class='form-control base-color readonly-style' style='border-right:0px;border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'></div> 
				                    <div id="jj_btn" class="input-group-addon btn btn-default" style='border-left:1px solid #d5d5d5;padding-left:0px;padding-right:0px;width:38px;' onclick="Workorder135AddWin.openAccessWin()"><i class='icon-search'></i></div>
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-6 col-sm-6 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">飞机站位</div>
								<div class="font-size-9">Aircraft Stations</div>
							</label>
							<div id="fjzw_div_edit" class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group " style="width: 100%">
								<input id="efjzw" type="text" name="efjzw" maxlength="4000" class="form-control" />
<!-- 									<div ondblclick="Workorder135AddWin.openStationWin()" id="efjzw" class='form-control base-color readonly-style' style='border-right:0px;border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'></div>  -->
				                    <span id="fjzw_btn" class="input-group-addon btn btn-default" onclick="Workorder135AddWin.openStationWin()"><i class='icon-search'></i></span>
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">计划工时</div>
								<div class="font-size-9">MHRs</div>
							</label>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-9 padding-leftright-8">
							<!-- <div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" > -->
								<div class="input-group input-group-new" >
									<input id="wo135add_jhgs_rs" name="" type="text" class="form-control" onkeyup='Workorder135AddWin.changeRs(this)' style="min-width: 80px;"/>
			                    	<span class="input-group-addon">人&nbsp;x</span>
			                    	<input id="wo135add_jhgs_xss" name="jhgsXss" type="text" class="form-control" onkeyup='Workorder135AddWin.changeXss(this)' style="min-width: 80px;" />
			                     	<span class="input-group-addon">时=<span id="wo135add_bzgs">0</span>时</span>
			                     	<%@ include file="/WEB-INF/views/common/project/work_hour_edit.jsp"%><!-- 工种工时 -->
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<!-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">预计执行单位</div>
								<div class="font-size-9">Plan Execution dept</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class='input-group input-group-new' style="min-width:100%">
								    <label class='input-group-addon'>
								    	<input type="radio" name="ww" value="3" checked="checked" style='vertical-align:-3px;'/>&nbsp;<span style='color:#333;'>内部</span>
								    	<input type="radio" name="ww" value="3" style='vertical-align:-3px;'/>&nbsp;<span style='color:#333;'>外委</span>
								    </label>
									<input type="text"  value="" name="ff" class="form-control  readonly-style" placeholder='' maxlength="100" readonly="readonly" id="ff">
										<span class="input-group-btn " id="ffbmid">
											<button type="button" class="btn btn-default " id="wxrybtn" data-toggle="modal" onclick="openzrdw()" style='border-left:0px;'>
												<i class="icon-search cursor-pointer"></i>
										    </button>
									    </span>
										<input type="hidden" value="" name="ffid" class="form-control " placeholder='' maxlength="" id="ffid">
								</div>
							</div>
						</div> -->
						
						<div class="clearfix"></div>
						
						<!-- <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">完成时限</div>
								<div class="font-size-9">Completion time limit</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group ">
									<input id="zjh_name_e" type="text" class="form-control readonly-style" ondblclick="Workorder135AddWin.openWinWcsx()" readonly />
									<input id="zjh_e" type="hidden" class="form-control" />
			                    	<span id="zjh_e_btn" class="input-group-addon btn btn-default" onclick="Workorder135AddWin.openWinWcsx()"><i class='icon-search'></i></span>
			                	</div>
							</div>
						</div> -->

						<div class="clearfix"></div>
						<div id="wo135add_jspgddiv">
						 	<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- -评估单列表 -->
						</div>
						 <div class="clearfix"></div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">一般概述</div>
								<div class="font-size-9">General overview</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 95px;" class="form-control" id="wo135add_ybgs" name="" maxlength="1000" ></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group" id="wo135add_jyclfadiv">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">建议处理方案</div>
								<div class="font-size-9">Treatment scheme</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 75px;" class="form-control" id="wo135add_jyclfa" name="" maxlength="1000" ></textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						</form> 
					</div>
					
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
					
					<%@ include file="/WEB-INF/views/common/project/equipment_list_edit.jsp"%><!-- 器材清单列表 -->
					<%@ include file="/WEB-INF/views/common/project/tools_list_edit.jsp"%><!-- 工具设备列表 -->
					
					<!-- 工作内容 -->
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
					<!-- 工作内容 -->
					
					<!-- 加载附件信息 -->
					<div id="workorder135_add_attachments_list_edit" style="display:none">
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%>
					</div>
					<!-- 加载附件信息 -->
					
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
	                    	<button id="save_btn" type="button" onclick="Workorder135AddWin.setData(1);" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="submit_btn" type="button" onclick="Workorder135AddWin.setData(2);"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
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
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/nrc/135/nrc_add.js"></script> <!-- 新增工单 -->
<%@ include file="/WEB-INF/views/open_win/access_list_edit.jsp"%><!-- 接近对话框 -->
<%@ include file="/WEB-INF/views/open_win/work_hour_win.jsp"%><!-------工种工时对话框 -------->
<%@ include file="/WEB-INF/views/open_win/material_tools.jsp"%><!-------航材对话框(器材清单、工具设备) -------->
