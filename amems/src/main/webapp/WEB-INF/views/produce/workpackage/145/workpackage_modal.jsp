<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal modal-new" id="workpackage_modal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog" style='width:85%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-14 " id="workpackage_modal_name"></div>
							  <div class="font-size-12" id="workpackage_modal_ename"></div>
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
					<form id="workpackage_modal_form">
                    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>工包编号</div>
							<div class="font-size-9">Packing No.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="workpackage_modal_gbbh" name="workpackage_modal_gbbh" maxlength="50" placeholder='不填写则自动生成'/>
						</div>
					</div>
                    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>描述</div>
							<div class="font-size-9">Desc</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="workpackage_modal_ms" name="workpackage_modal_ms" maxlength="15" />
						</div>
					</div>
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color:red">*</span>机型</div>
							<div class="font-size-9">A/C Type</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control ' id="workpackage_modal_fjjx" name="workpackage_modal_fjjx" onchange="workpackage_modal.changeFjjx()">
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">下发单位</div>
							<div class="font-size-9">Down Unit</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<span class='margin-right-15 label-input'>	<input type="radio" name="workpackage_modal_xfdw_radio" value="0" checked="checked" />&nbsp;内部</span>
							<span class='margin-left-15 label-input'>	<input type="radio" name="workpackage_modal_xfdw_radio" value="1"  />&nbsp;外委</span>
						</div>						
					</div>
					<div class="clearfix"></div>
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="workpackage_modal_xfdw_bmdiv">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">部门</div>
							<div class="font-size-9">Department</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-8">
							<div class='input-group ' style="min-width:100%">
								<input type="text"  value="" name="workpackage_modal_xfdw" class="form-control readonly-style" placeholder='' maxlength="100" readonly="readonly"
									id="workpackage_modal_xfdw" ondblclick="workpackage_modal.openzrdw('xfdw',null)">
									<span class="input-group-btn" id="xfdwbmid" >
										<button type="button" class="btn btn-default " id="workpackage_modal_xfdwbtn"
										 data-toggle="modal"
										onclick="workpackage_modal.openzrdw('xfdw',null)">
										<i class="icon-search cursor-pointer"></i>
									</button></span>
							</div>
							<input type="hidden" value="" name="workpackage_modal_xfdwid" class="form-control " placeholder='' maxlength=""
									id="workpackage_modal_xfdwid">
						</div>					
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="workpackage_modal_xfdw_khxxdiv">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">客户信息</div>
							<div class="font-size-9">Customer Info</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left ">
							<div class='input-group ' style="min-width:100%">
								<input type="text" value="" name="workpackage_modal_khxx" class="form-control readonly-style"
								 id="workpackage_modal_khxx" readonly="readonly" ondblclick="workpackage_modal.openWinKhxx()" />
								<span class="input-group-btn" id="gzzbutton">
									<button type="button" class="btn btn-default " id="workpackage_modal_khxxbtn"
									 data-toggle="modal"
									onclick="workpackage_modal.openWinKhxx()">
									<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>
						<div style="display: none;">
							<input type="text" value="" name="workpackage_modal_khxxid" id="workpackage_modal_khxxid">											
						</div>
					</div>
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color:red">*</span>项目编号</div>
							<div class="font-size-9">Project No.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 pull-left ">
							<div class='input-group ' style="min-width:100%">
								<input type="text" value="" name="workpackage_modal_xmbh" class="form-control readonly-style"
								 id="workpackage_modal_xmbh" readonly="readonly" ondblclick="workpackage_modal.openWinXmbh()" />
								<span class="input-group-btn" id="gzzbutton">
									<button type="button" class="btn btn-default " 
									 data-toggle="modal"
									onclick="workpackage_modal.openWinXmbh()">
									<i class="icon-search cursor-pointer"></i>
									</button>
								</span>
							</div>
						</div>
						<div style="display: none;">
							<input type="text" value="" name="workpackage_modal_xmbhid" id="workpackage_modal_xmbhid">											
						</div>
					</div>
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞机注册号</div>
							<div class="font-size-9">A/C Reg</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="workpackage_modal_fjzch" name="workpackage_modal_fjzch" maxlength="20" />
						</div>
					</div>
					
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">MSN</div>
							<div class="font-size-9">MSN</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id='workpackage_modal_msn' maxlength="20" name="workpackage_modal_msn">
						</div>
					</div> 
					</div>
					<div class="clearfix"></div>
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">维修类型</div>
							<div class="font-size-9">Type</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class="form-control " id="workpackage_modal_wxlx" name="workpackage_modal_wxlx" >
								<option vlaue="">选择全部 All</option>
							</select>
						</div>
					</div>          
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">预计执行单位</div>
							<div class="font-size-9">Execution Unit</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-8">							
								<div class='input-group ' style="min-width:100%">
									<input type="text"  value="" name="workpackage_modal_yjzxdw" class="form-control readonly-style" placeholder='' maxlength="100" readonly="readonly"
										id="workpackage_modal_yjzxdw" ondblclick="workpackage_modal.openzrdw('yjzxdw','1')">
										<span class="input-group-btn" id="workpackage_modal_yjzxdwbmid" >
											<button type="button" class="btn btn-default " id="wxrybtn"
											 data-toggle="modal"
											onclick="workpackage_modal.openzrdw('yjzxdw','1')">
											<i class="icon-search cursor-pointer"></i>
										</button></span>
								</div>							
								<input type="hidden" value="" name="workpackage_modal_yjzxdwid" class="form-control " placeholder='' maxlength=""
										id="workpackage_modal_yjzxdwid">
						</div>
					</div>	
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">制单日期</div>
							<div class="font-size-9">Create Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_modal_zdrq" name="workpackage_modal_zdrq"  />
					    </div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">计划开始日期</div>
							<div class="font-size-9">Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_modal_jhksrq" name="workpackage_modal_jhksrq"  />
					    </div>
					</div>
					</div>	
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">计划完成日期</div>
							<div class="font-size-9">Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="workpackage_modal_jhwcrq" name="workpackage_modal_jhwcrq"  />
					    </div>
					</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="workpackage_modal_gbzt_div">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">工包状态</div>
							<div class="font-size-9">Status</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control " id="workpackage_modal_gbzt" name="workpackage_modal_gbzt"  disabled="disabled" />
						</div>
					</div>	
					</div>					
					<div class="col-lg-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 ">工作要求</div>
							<div class="font-size-9 ">Job Request</div>
						</span>
					 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
								<textarea class="form-control" id="workpackage_modal_gzyq" name="workpackage_modal_gzyq" placeholder=''   maxlength="300" style="height:55px"></textarea>
						</div>
					</div>
					</form>
					<div class="clearfix"></div>
					</div>
					<div id="workpackage_modal_attachments_list_edit" style="display:none">							
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
	                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="workpackage_modal.saveData()" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Save</div>
							</button>
		                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
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
    <script type="text/javascript" src="${ctx }/static/js/thjw/produce/workpackage/145/workpackage_modal.js"></script>