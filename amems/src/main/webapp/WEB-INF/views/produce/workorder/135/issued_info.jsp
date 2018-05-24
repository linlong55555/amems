<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

<div id="issued_info_modal" style='display: none;padding:0px;' >
	<!-- 表格 -->
	<div class='pull-left' style='width:120px;'>
	<p style='margin-bottom:0px;height:30px;line-height:30px;background:#efefef;padding-left:8px;padding-right:8px;border-bottom:1px solid #d5d5d5;'><a href='#'><i class='fa fa-plus'></i>&nbsp;创建新工包</a></p>
	<div style='max-height:400px;overflow:auto;'>
	<ul class='issued_style' >
		<li class='active'><a href="#">50-2934</a></li>
		<li><a href="#">50-2935</a></li>
		<li><a href="#">50-2936</a></li>
		<li><a href="#">50-2937</a></li>
	</ul>
	</div>
	</div>
	<div class='pull-left' style='width:580px;border-left:1px solid #d5d5d5;'>
	<p style='margin-bottom:0px;height:30px;line-height:30px;background:#efefef;padding-left:8px;padding-right:8px;border-bottom:1px solid #d5d5d5;font-weight:700;'>50-2934工包</p>
	<div class="col-xs-12 padding-left-0 padding-right-0" style='max-height:400px;overflow:auto;padding-top:8px;'>
                    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞机注册号</div>
							<div class="font-size-9">A/C Reg</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select id="workpackage_modal_fjzch" class="form-control " onChange="workpackage_modal.changeFj()">
							</select>
						</div>
					</div>
					
					 <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">MSN</div>
							<div class="font-size-9">MSN</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id='workpackage_modal_msn' name="workpackage_modal_msn" disabled="disabled" maxlength="100">
						</div>
					</div>
					
                    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">机型</div>
							<div class="font-size-9">A/C Type</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="workpackage_modal_jx" name="workpackage_modal_jx" disabled="disabled" maxlength="50" />
						</div>
					</div>
					
                    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red" id="workpackage_modal_gbbhmark">*</span>工包编号</div>
							<div class="font-size-9">Packing No.</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="workpackage_modal_gbbh" name="workpackage_modal_gbbh" maxlength="50"/>
						</div>
					</div>
					<div clas="clearfix"></div>
                    <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red" id="workpackage_modal_msmark">*</span>描述</div>
							<div class="font-size-9">Desc</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="workpackage_modal_ms" name="workpackage_modal_ms" maxlength="100" />
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">下发单位</div>
							<div class="font-size-9">Issuing Unit</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<div class='input-group ' style="min-width:100%">
								<input type="text"  value="" name="workpackage_modal_xfdw" class="form-control readonly-style" placeholder='' maxlength="100" readonly="readonly"
									id="workpackage_modal_xfdw" ondblclick="workpackage_modal.openzrdw('xfdw',null)">
									<span class="input-group-btn" id="workpackage_modal_xfdwbtn" >
										<button type="button" class="btn btn-default "
										 data-toggle="modal"
										onclick="workpackage_modal.openzrdw('xfdw',null)">
										<i class="icon-search cursor-pointer"></i>
									</button></span>
							</div>
						</div>
						<input type="hidden" value="" name="workpackage_modal_xfdwid" class="form-control " placeholder='' maxlength=""
									id="workpackage_modal_xfdwid">
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">维修类型</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<select class="form-control" id="workpackage_modal_wxlx" name="workpackage_modal_wxlx"  maxlength="100">
							</select>
					    </div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">制单日期</div>
							<div class="font-size-9">Create Date</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_modal_zdrq" name="workpackage_modal_zdrq"  />
					    </div>
					</div>
					<div clas="clearfix"></div>
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">预计执行单位</div>
							<div class="font-size-9">Execution Unit</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-left-8 padding-right-8">
							<span class='margin-right-15 label-input'>	<input type="radio" name="workpackage_modal_yjzxdw_radio" value="0" checked="checked" />&nbsp;内部</span>
							<span class='label-input margin-left-15'>	<input type="radio" name="workpackage_modal_yjzxdw_radio" value="1"  />&nbsp;外委</span>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="workpackage_modal_yjzxdw_bmdiv">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">内部执行单位</div>
							<div class="font-size-9">Department </div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-left-8 padding-right-8">
							<div class='input-group ' style="min-width:100%">
								<input type="text"  value="" name="workpackage_modal_yjzxdw" class="form-control " placeholder='' maxlength="100" 
									id="workpackage_modal_yjzxdw" >
									<span class="input-group-btn" id="workpackage_modal_nbzxdwbtn" >
										<button type="button" class="btn btn-default " id="yjzxdwbtn"
										 data-toggle="modal"
										onclick="workpackage_modal.openzrdw('yjzxdw','1')">
										<i class="icon-search cursor-pointer"></i>
									</button></span>
							</div>
						</div>					
						<input type="hidden" value="" name="workpackage_modal_yjzxdwid" class="form-control " placeholder='' maxlength=""
									id="workpackage_modal_yjzxdwid">
						<input type="hidden" value="" name="workpackage_modal_yjzxdw_do" class="form-control " placeholder='' maxlength=""
									id="workpackage_modal_yjzxdw_do">
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="workpackage_modal_yjzxdw_gysdiv">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">外部执行单位</div>
							<div class="font-size-9">Supplier</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-left-8 padding-right-8">
							<div class='input-group ' style="min-width:100%">
								<input type="text"  value="" name="workpackage_modal_gys" class="form-control readonly-style" placeholder='' maxlength="100" readonly="readonly"
									id="workpackage_modal_gys" ondblclick="workpackage_modal.openGys()">
									<span class="input-group-btn" id="workpackage_modal_wbzxdwbtn" >
										<button type="button" class="btn btn-default " 
										 data-toggle="modal"
										onclick="workpackage_modal.openGys()">
										<i class="icon-search cursor-pointer"></i>
									</button></span>
							</div>
						</div>					
						<input type="hidden" value="" name="workpackage_modal_gysid" class="form-control " placeholder='' maxlength=""
									id="workpackage_modal_gysid">
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">计划开始日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_modal_jhksrq" name="workpackage_modal_jhksrq"  />
					    </div>
					</div>	
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">计划完成日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_modal_jhwcrq" name="workpackage_modal_jhwcrq"  />
					    </div>
					</div>
					</div>						
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="workpackage_modal_gbzt_div">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">工包状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " maxlength="10"  id="workpackage_modal_gbzt" name="workpackage_modal_gbzt" disabled="disabled" />
					    </div>
					</div>						
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">工作要求</div>
							<div class="font-size-9 ">Request</div>
						</span>
					 	<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="workpackage_modal_gzyq" name="workpackage_modal_gzyq" placeholder=''   maxlength="1000" style="height:55px"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div id="workpackage_modal_attachments_list_edit" style="display:none">							
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>
					<div class="clearfix"></div>	
				</div>
	</div>
	<div class='clearfix'></div>
	<div style='border-top:1px solid #d5d5d5;padding-top:5px;padding-bottom:5px;background:#F2F2F2'>
      <div class="pull-right padding-left-0 " style='margin-right:3px;'>
		<a href="#"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='produce:workorder:main:01' >
			<div class="font-size-12">下发</div>
			<div class="font-size-9">Issued</div>
		</a> 
	  </div>
	  <div class='clearfix'></div>
	</div>
</div>
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/135/issued_info.js"></script>