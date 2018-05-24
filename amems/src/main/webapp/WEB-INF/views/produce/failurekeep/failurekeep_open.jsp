<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="FailureKeep_Open_Modal" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" aria-labelledby="Assessment_Open_Modal" data-backdrop="static" >
	<div class="modal-dialog" style='width:90%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" > 
                	<div class='pull-left'>
                    	<div class="font-size-12 " id="modalName"></div>
						<div class="font-size-9 " id="modalEname"></div>
					</div>
				  	<div class='pull-right' >
		  		  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
		  		  	</div>
				  	<div class='clearfix'></div>
              	</h4>
            </div>
            <div class='clearfix'></div>
		    <div class="modal-body  " >
	           	<div class="col-xs-12  ">
 					<form id="failurekeepForm" >
						<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 
							<div class="input-group-border margin-top-8 padding-left-0">
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group " >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">保留单编号</div>
										<div class="font-size-9 ">DD No.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" id="bldh" name="bldh" class="form-control padding-left-3 padding-right-3 bldh" placeholder='不填写则自动生成'  maxlength="50"/>
									</div>
								</div>
			           			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<label class="required" style="color: red">*</label>飞机注册号
										</div>
										<div class="font-size-9 ">A/C REG</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<select class="form-control noteditable"  id="FailureKeep_Open_Modal_fjzch" name="fjzch" onchange="FailureKeep_Open_Modal.onchangeFjzch(this)">
										</select>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">机型</div>
											<div class="font-size-9 ">A/C Type</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"   id="jx" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
									</div>
								</div>
				   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group div-hide">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">状态</div>
										<div class="font-size-9 ">Status</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"  id="ztms" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
									</div>
								</div>
								<div class='clearfix'></div>
							</div>
						</div>
	     				<div class="clearfix"></div>
   						<div class="panel panel-default padding-right-0">
					        <div class="panel-heading bg-panel-heading">
					        	<div class="font-size-12 ">基础信息</div>
								<div class="font-size-9">Basis Info</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
					       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">ATA章节号</div>
											<div class="font-size-9">ATA</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden"  name="zjh"  id="zjh">
												<input type="text"  name="zjhms" class="form-control noteditable" id="zjhms">
												<span class="required input-group-btn" >
													<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="FailureKeep_Open_Modal.initFixChapter()">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>航站
											</div>
											<div class="font-size-9 ">Station</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input type="text" id="hz" name="hz" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3" />
										</div>
								 	</div>
				 	     			<div id="sylbDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">来源类型</div>
											<div class="font-size-9 ">From</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
											<div class="input-group input-group-new">
							                    <span class="input-group-addon" style="padding-left:0px">
							                    	<label><input type='radio' class="noteditable" name='blly' onclick="FailureKeep_Open_Modal.onchangeblly();" value="1" />&nbsp;FLB&nbsp;</label>
							                    </span>
							                     <span class="input-group-addon">
							                    	 <label><input type='radio' class="noteditable" name='blly' onclick="FailureKeep_Open_Modal.onchangeblly();" value="2"/>&nbsp;NRC&nbsp;</label>
												 </span>
							                     <span class="input-group-addon">
							                    	 <label><input type='radio' class="noteditable" name='blly' onclick="FailureKeep_Open_Modal.onchangeblly();" value="9" checked/>&nbsp;N/A&nbsp;</label>
												 </span>
												 <input type="text" class="form-control" style="visibility: hidden;">
						                	</div>
										</div>
									</div>
									<div id="lybhdiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												来源编号
											</div>
											<div class="font-size-9 ">Source NO.</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden" id="addupdatebs" value="1">
												<input type="hidden" id="bs145">
												<input type="hidden"  name="bllyid"  id="bllyid"><!-- ondblclick="FailureKeep_Open_Modal.initSource();" -->
												<input type="text"   id="blly" class="form-control noteditable readonly-style colse" readonly="readonly" >
												<span class="required input-group-btn checkPermission"  permissioncode='produce:reservation:fault:main:062' id="wxrybtn1" >
													<button type="button" class="btn btn-default form-control" title="135工单" style='height:34px;' data-toggle="modal" onclick="FailureKeep_Open_Modal.initSource(135);">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
												<span class="required input-group-btn checkPermission"  permissioncode='produce:reservation:fault:main:063' id="wxrybtn2" >
													<button type="button" class="btn btn-default form-control"  title="145工单" style='height:34px;' data-toggle="modal" onclick="FailureKeep_Open_Modal.initSource(145);">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
									
									<div class="clearfix"></div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>报告人
											</div>
											<div class="font-size-9 ">Applicant</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden"  name="scSqrid"  id="scSqrid">
												<input type="hidden"  name="scSqrbmdm"  id="scSqrbmdm">
												<input type="text"  name="scSqr" id="scSqr"  class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="FailureKeep_Open_Modal.openUser('scSqr');">
												<span class="required input-group-btn" >
													<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="FailureKeep_Open_Modal.openUser('scSqr');">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>
												报告日期
											</div>
											<div class="font-size-9 ">Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input type="text"  id="scSqrq" name="scSqrq" onchange="FailureKeep_Open_Modal.onchangebllx(2)" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
								 	
		            			 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">报告人执照号</div>
												<div class="font-size-9 ">Applicant No</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input id="scSqrzzh" type="text" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3"  />
										</div>
									</div>
									
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">工时</div>
											<div class="font-size-9">MHRs</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<div class="input-group input-group-new" >
												<input id="sxgsRs" name="sxgsRs" type="text" class="form-control noteditable" onchange='FailureKeep_Open_Modal.changeRs(this)' style="min-width: 10%;"/>
						                    	<span class="input-group-addon">人&nbsp;x</span>
						                    	<input id="sxgsXs" name="sxgsXs" type="text" class="form-control noteditable" onchange='FailureKeep_Open_Modal.changeXss(this)' style="min-width: 10%;" />
						                     	<span class="input-group-addon">时&nbsp;=&nbsp;<span id="bzgs">0</span>时</span>
						                	</div>
										</div>
									</div>
									
									<div class='clearfix'></div>
									
									 <!--部件号 -->
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">部件号</div>
												<div class="font-size-9 ">P/N</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input id="gzblBjh" name="gzblBjh"  type="text" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3"  />
										</div>
									</div>
									<!--序列号 -->
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">序列号</div>
												<div class="font-size-9 ">S/N</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input id="gzblXlh" name="gzblXlh" type="text" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3"  />
										</div>
									</div>
									
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="fhDiv">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red"></label>
												飞行时间
											</div>
											<div class="font-size-9 ">FH</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										     <div class="input-group" style='width:100%;'> 
										     	<input class="form-control input-sm noteditable" maxlength="10" onkeyup="FailureKeep_Open_Modal.validateFH(this)" type="text" id="fhInput">
										     	<span class="input-group-addon dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FH</span>
										     </div>
										</div>
									</div>
									
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group"  id="fcDiv">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red"></label>
												飞行循环
											</div>
											<div class="font-size-9 ">FC</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										     <div class="input-group" style='width:100%;'> 
										     <input class="form-control input-sm noteditable" maxlength="10" onkeyup="FailureKeep_Open_Modal.validateFC(this)" type="text" id="fcInput">
										     <span class="input-group-addon dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FC</span>
										     </div>
										</div>
									</div>
									
									<div class='clearfix'></div>
  				 		     		<div id="bllbDiv" class="col-lg-12 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-2 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">保留类别</div>
												<div class="font-size-9 ">Type</div>
										</label>
										<div class="col-lg-11 col-md-10 col-sm-9 col-xs-10 padding-leftright-8" >
					                    	 <label class='label-input'><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx(1);" class="noteditable" name='bllx' value="1" checked />&nbsp;A&nbsp;</label>
					                    	 <label class='label-input'><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx(1);" class="noteditable" name='bllx' value="2"/>&nbsp;B&nbsp;</label>
					                    	 <label class='label-input'><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx(1);" class="noteditable" name='bllx' value="3" />&nbsp;C&nbsp;</label>
					                    	 <label class='label-input'><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx(1);" class="noteditable" name='bllx' value="4" />&nbsp;D&nbsp;</label>
					                    	 <label class='label-input'><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx(1);" class="noteditable" name='bllx' value="5" />&nbsp;CDL&nbsp;</label>
					                    	 <label class='label-input'><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx(1);" class="noteditable" name='bllx' value="9" />&nbsp;其他&nbsp;</label>
										</div>
									</div>
									
									<div class='clearfix'></div>
									
								    <div class="col-lg-11 col-sm-11 col-xs-11 form-group" style="padding-left: 9%;padding-right: 55px; ">
								     <div class="col-lg-10 col-sm-10 col-xs-10 form-group settingFd" >
								    		<p class="settingFd-p" name="danciChongfuP" style="font-weight: normal;margin-left:1px">保留期限</p>
											<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
												<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">
														<label class="required" style="color: red"></label>
														日历
													</div>
													<div class="font-size-9 ">Date</div>
												</label>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
													<input type="text" onchange="FailureKeep_Open_Modal.clearRedColor()"  id="scBlqx"   name="scBlqx" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
												</div>
											</div>
											
											 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="blfhDiv">
												<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">
														<label class="required" style="color: red"></label>
														飞行时间
													</div>
													<div class="font-size-9 ">FH</div>
												</label>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
												     <div class="input-group" style='width:100%;'> 
												     	<input class="form-control input-sm noteditable" maxlength="10" onkeyup="FailureKeep_Open_Modal.validateFH(this)" type="text" id="blfhInput">
												     	<span class="input-group-addon dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FH</span>
												     </div>
												</div>
											</div>
											
											<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group"  id="blfcDiv">
												<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12 margin-topnew-2">
														<label class="required" style="color: red"></label>
														飞行循环
													</div>
													<div class="font-size-9 ">FC</div>
												</label>
												<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
												     <div class="input-group" style='width:100%;'> 
												     <input class="form-control input-sm noteditable" maxlength="10" onkeyup="FailureKeep_Open_Modal.validateFC(this)" type="text" id="blfcInput">
												     <span class="input-group-addon dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FC</span>
												     </div>
												</div>
											</div>
										</div>
									</div>	
									
									<div class="clearfix"></div>
									 
									 
					 				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>保留依据
											</div>
											<div class="font-size-9 ">Doc Ref</div>
										</label>
											<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
											<input type="text" id="blyj" name="blyj" maxlength="1000" class="noteditable form-control padding-left-3 padding-right-3" />
										</div>
									 </div>
									 
									 <div class="clearfix"></div>
									
			     				 	 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">故障描述</div>
											<div class="font-size-9 ">Defect Desc</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea  id="gzms"  maxlength="1000" style="height:55px" class="noteditable form-control padding-left-3 padding-right-3"    ></textarea>
										</div>
									 </div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">保留原因</div>
											<div class="font-size-9 ">Reason</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea id="blyy"  maxlength="1000" class="noteditable form-control padding-left-3 padding-right-3" style="height:55px;" ></textarea>
											<span id="blyyReasondic">
												
											</span>
										</div>
									 </div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">临时措施</div>
											<div class="font-size-9 ">Temp Action</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea id="lscs" class="noteditable form-control padding-left-3 padding-right-3" style="height:55px"  maxlength="1000"></textarea>
										</div>
									 </div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">影响服务程度</div>
											<div class="font-size-9 ">Effect service degree </div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<span id="serviceDegreedic">
												
											</span>
										</div>
									 </div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">涉及部门</div>
											<div class="font-size-9 ">Involved departments</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<span id="involvedDeptdic">
												
											</span>
										</div>
									 </div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<input class="noteditable" type='checkbox' name='mbs' id='mbs'onclick="FailureKeep_Open_Modal.onchangembs('m')"  style='vertical-align:text-bottom;' />&nbsp;执行(M)程序
											<div class="font-size-9 ">Execute (M) Pro</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<input  id="msm" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3" disabled="disabled" />
										</div>
									 </div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<input class="noteditable" type='checkbox' name='obs' id='obs' onclick="FailureKeep_Open_Modal.onchangembs('o')" style='vertical-align:text-bottom;' />&nbsp;执行(O)程序
											<div class="font-size-9 ">Execute (O) Pro</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<input id="osm" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3"  disabled="disabled"/>
										</div>
									 </div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<input class="noteditable" type='checkbox' name='yxxzbs' id='yxxzbs' onclick="FailureKeep_Open_Modal.onchangembs('yxxz')" style='vertical-align:text-bottom;' />&nbsp;运行限制
											<div class="font-size-9 ">Flt Limited</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<input id="yxxzsm" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3"  disabled="disabled"/>
											<span id="fltLimitedDic">
												
											</span>
										</div>
									 </div>
			     					 <div class="clearfix"></div>
								</div>
							</div>
					   	</div>
			   		</form>
					<div class="clearfix"></div>
						<%@ include file="/WEB-INF/views/common/project/equipment_list_edit.jsp"%><!-- 器材清单列表 -->
					<div class="clearfix"></div>
						<%@ include file="/WEB-INF/views/common/project/tools_list_edit.jsp"%><!-- 工具设备列表 -->
					<div class="clearfix"></div>
					<!--参考文件END  -->		
					<div id="attachments_list_edit" style="display:none">
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>
					<div class="clearfix"></div>
						<%@ include file="/WEB-INF/views/open_win/approval_process_info.jsp" %> <!--批准流程 -->	
				</div>
			</div>	
			<div class="clearfix"></div>
			<div class="modal-footer ">
				<div class="col-xs-12 padding-left-8 padding-right-0" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
						   <button id="sptg" type="button" onclick="FailureKeep_Approval_Modal.passed();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">批准通过</div>
								<div class="font-size-9">Approved</div>
							</button>
							<button id="spbh" type="button" onclick="FailureKeep_Approval_Modal.turnDown();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">批准驳回</div>
								<div class="font-size-9">Rejected</div>
							</button>
						   	<button id="baocuns" type="button" onclick="FailureKeep_Add_Modal.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="tijiao" type="button" onclick="FailureKeep_Add_Modal.submit();" class="btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/failurekeep/failurekeep_open.js"></script><!--故障保留通用弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/failurekeep/failurekeep_add.js"></script><!--新增故障保留弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/failurekeep/failurekeep_update.js"></script><!--修改故障保留弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/failurekeep/failurekeep_approval.js"></script><!--批准故障保留弹窗的js  -->
