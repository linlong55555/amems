<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="Project_Open_Modal" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" aria-labelledby="Assessment_Open_Modal" data-backdrop="static" >
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
 					<form id="projectForm" >
						<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 
							<div class="input-group-border margin-top-8 padding-left-0">
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">保留单编号</div>
										<div class="font-size-9 ">WD No.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" placeholder='不填写则自动生成'  maxlength="50" id="bldh" name="bldh" class="form-control padding-left-3 padding-right-3 bldh"  />
									</div>
								</div>
			           			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<label class="required" style="color: red">*</label>
											飞机注册号
										</div>
										<div class="font-size-9 ">A/C REG</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<select class="form-control" name="fjzch" id="Project_Open_Modal_fjzch" onchange="Project_Open_Modal.onchangeFjzch(this)">
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
				   				<div class="div-hide col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
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
					        	<div class="font-size-12 ">申请信息</div>
								<div class="font-size-9">Application Info</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
							 		<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>
												申请日期
											</div>
											<div class="font-size-9 ">Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input type="text"  id="sqrq" name="sqrq"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">申请人</div>
											<div class="font-size-9 ">Applicant</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden"  name="sqrid"  id="sqrid">
												<input type="text"  name="sqr" id="sqr" class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="Project_Open_Modal.openUser('sqr');">
												<span class="required input-group-btn" >
													<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Project_Open_Modal.openUser('sqr');">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">申请部门</div>
											<div class="font-size-9 ">Department</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="hidden" id="sqrbmid" >
											<input type="text"  id="sqrbm"  class="noteditable form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
										</div>
									</div>
					      			<div id="lybhdiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>工单编号
											</div>
											<div class="font-size-9 ">W/O No.</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden" id="addupdatebs" value="1">
												<input type="hidden" id="bs145">
												<input type="hidden"  name="gdid"  id="gdid">
												<input type="text"   id="gd" name='gd' class="form-control noteditable readonly-style colse" readonly="readonly" >
												<span class="required input-group-btn checkPermission" id="wxrybtn"  permissioncode='produce:reservation:item:main:09,produce:reservation:item:main:091'>
													<button type="button" class="btn btn-default form-control"  title="135工单" style='height:34px;' data-toggle="modal" onclick="Project_Open_Modal.initSourceData('135')">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
												<span class="required input-group-btn checkPermission" id="wxrybtn"  permissioncode='produce:reservation:item:main:091'>
													<button type="button" class="btn btn-default form-control" title="145工单"  style='height:34px;' data-toggle="modal" onclick="Project_Open_Modal.initSourceData('145')">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
					      	
									<div class="clearfix"></div>
		          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">计划完成日期</div>
											<div class="font-size-9 ">Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="jhJsrq"  class="noteditable form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
										</div>
									</div>
		          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">工卡号</div>
											<div class="font-size-9 ">W/C No.</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="gkbh"  class="noteditable form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
										</div>
									</div>
		          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">工卡定检间隔</div>
											<div class="font-size-9 ">Interval</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="gkbz"  class="noteditable form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
										</div>
									</div>
									<div class="clearfix"></div>
  				    				<div id="sylbDiv" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="pull-left text-left" style='width:350px;padding-left:8px;'>
											<div class="font-size-12 margin-top-5">涉及发动机、起落架和飞行操纵系统？</div>
										</label>
										<div class="pull-left" >
											<div class="">
							                    <span class="" style="padding-left:0px">
							                    	<label ><input type='radio' class="noteditable" name='fjbs1' value="1" style='vertical-align:-2px;' />&nbsp;是&nbsp;</label>
							                    </span>
							                    <span class="">
							                    	 <label><input type='radio' class="noteditable" name='fjbs1' value="0" style='vertical-align:-2px;'  checked/>&nbsp;否&nbsp;</label>
												</span>
						                	</div>
										</div>
									</div>
									<div class="clearfix"></div>
  				    				<div id="sylbDiv" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="pull-left  text-left" style='width:350px;padding-left:8px;'>
											<div class="font-size-12 margin-top-5">影响飞行机组正常操作或增加其工作负荷的维修工作项目？</div>
										</label>
										<div class="pull-left" >
											<div class="">
							                    <span class="" style="padding-left:0px">
							                    	<label ><input type='radio' class="noteditable" name='fjbs2' value="1" style='vertical-align:-2px;' />&nbsp;是&nbsp;</label>
							                    </span>
							                    <span class="">
							                    	 <label><input type='radio' class="noteditable" name='fjbs2' value="0" style='vertical-align:-2px;'  checked/>&nbsp;否&nbsp;</label>
												</span>
						                	</div>
										</div>
									</div>
									<div class="clearfix"></div>
  				    				<div id="sylbDiv" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="pull-left text-left" style='width:350px;padding-left:8px;'>
											<div class="font-size-12 margin-top-5">该项目涉及的系统/设备/零部件的技术性能是否符合规定要求？</div>
										</label>
										<div class="pull-left" >
											<div >
							                    <span  style="padding-left:0px">
							                    	<label ><input type='radio' class="noteditable" name='fjbs3' value="1" style='vertical-align:-2px;'  />&nbsp;是&nbsp;</label>
							                    </span>
							                    <span >
							                    	 <label ><input type='radio' class="noteditable" name='fjbs3' value="0" style='vertical-align:-2px;'  checked/>&nbsp;否&nbsp;</label>
												</span>
						                	</div>
										</div>
									</div>
									<div class="clearfix"></div>
		     				 	 	<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">
												MEL/CDL对相关系统要求
											</div>
											<div class="font-size-9 ">MEL/CDL Claim</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="dxgxtyq"    maxlength="1000"></textarea>
										</div>
									 </div>
									 <div class="clearfix"></div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">保留期间采取措施</div>
											<div class="font-size-9 ">Measures</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="noteditable form-control padding-left-3 padding-right-3" id="blqjcqcs" style="height:55px" maxlength="1000"></textarea>
										</div>
									 </div>
			     					 <div class="clearfix"></div>
								</div>
							</div>
					   	</div>
						<div class="clearfix"></div>
						<%@ include file="/WEB-INF/views/common/project/equipment_list_edit.jsp"%><!-- 器材清单列表 -->
						<div class="clearfix"></div>
						<%@ include file="/WEB-INF/views/common/project/tools_list_edit.jsp"%><!-- 工具设备列表 -->
						<div class="clearfix"></div>
   						<div class="panel panel-default padding-right-0">
					        <div class="panel-heading bg-panel-heading">
					        	<div class="font-size-12 ">生产计划评估</div>
								<div class="font-size-9">Production Plan Evaluation</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
									<div id="sylbDiv" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " >
										<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-left padding-left-10 padding-right-0">
											<div class="font-size-12 margin-topnew-2">上次完成该工作飞行后数据</div>
											<div class="font-size-9">Data</div>
										</label> 
										<div class="col-lg-3 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 form-group" >
					                   		<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">飞行时间</div>
												<div class="font-size-9 ">Flight Hours</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
												<div class="input-group"> 
													<input class="form-control fxyz" id="fxsj" type="text" >
													<input class="form-control" type="hidden" />
													<span class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FH</span>
												</div>
											</div>
										</div>	
										<div class="col-lg-3 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 form-group" >
						               		<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">飞行循环</div>
												<div class="font-size-9 ">Flight Cycle</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
												<div class="input-group"> 
													<input class="form-control" id="fxxh" type="text" name="fxxh">
													<input class="form-control" type="hidden" />
													<span class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FC</span>
												</div>
											</div> 
										</div>
										<div class="col-lg-4 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										</div>
									</div>
								 	<div class="clearfix"></div>	
									<div id="sylbDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">CMR项目</div>
											<div class="font-size-9 ">CMR Case</div>
										</label>	
										<div class="col-lg-4 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
											<div >
							                    <span  style="padding-left:0px">
							                    	<label class="label-input"><input type='radio' class="noteditable" name='fjbs4' value="1" />&nbsp;是&nbsp;</label>
							                    </span>
							                     <span >
							                    	 <label class="label-input"><input type='radio' class="noteditable" name='fjbs4' value="0" checked/>&nbsp;否&nbsp;</label>
												 </span>
						                	</div>
										</div>
										<div class="col-lg-4 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										</div>
									</div>
									<div id="sylbDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">AWL项目</div>
											<div class="font-size-9 ">AWL Case</div>
										</label>	
										<div class="col-lg-4 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
											<div >
							                    <span  style="padding-left:0px">
							                    	<label class="label-input"><input type='radio' class="noteditable" name='fjbs5' value="1" />&nbsp;是&nbsp;</label>
							                    </span>
							                     <span >
							                    	 <label class="label-input"><input type='radio' class="noteditable" name='fjbs5' value="0" checked/>&nbsp;否&nbsp;</label>
												 </span>
						                	</div>
										</div>
										<div class="col-lg-4 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										</div>
									</div>
  				    				<div id="sylbDiv" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="pull-left text-left" style='padding-left:8px;'>
											<div class="font-size-12 margin-top-5">保留期内是否有计划维修工作能够接近该项目工作？</div>
										</label>
										<div class="pull-left padding-leftright-8" >
											<div >
							                    <span  style="padding-left:0px">
							                    	<label  ><input type='radio' class="noteditable" name='fjbs6' value="1" style='vertical-align:-2px;'/>&nbsp;是&nbsp;</label>
							                    </span>
							                    <span >
							                    	 <label ><input type='radio' class="noteditable" name='fjbs6' value="0" style='vertical-align:-2px;' checked/>&nbsp;否&nbsp;</label>
												</span>
						                	</div>
										</div>
									</div>
										<div class="clearfix"></div>	
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<label class="required" style="color: red">*</label>
												申请保留期限
											</div>
											<div class="font-size-9 ">Expiry Date Period</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input type="text"  id="blqx"  name="blqx" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
								 	<div class="col-lg-9 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">规章保留限制</div>
											<div class="font-size-9 ">Retention Restrictions</div>
										</label>
										<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="gzblxz" maxlength="100"  class="noteditable form-control padding-left-3 padding-right-3 "  />
										</div>
									</div>
									 <div class="clearfix"></div>
								 	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">计划完成地点</div>
											<div class="font-size-9 ">Completion Place</div>
										</label>
										<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="jhwcdd"  maxlength="100" class="noteditable form-control padding-left-3 padding-right-3 "  />
										</div>
									</div>
					       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">评估人</div>
											<div class="font-size-9">Assessor</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input type="text"  id="pgr"  maxlength="100" class="noteditable form-control padding-left-3 padding-right-3 " />
										</div>
									</div>
		          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">评估日期</div>
											<div class="font-size-9 ">Assessment Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text" onchange="Project_Open_Modal.onketup(this)"  id="pgrq" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
			     					 <div class="clearfix"></div>
								</div>
							</div>
					   	</div>
    					<div class="clearfix"></div>
   						<div class="panel panel-default padding-right-0">
					        <div class="panel-heading bg-panel-heading">
					        	<div class="font-size-12 ">技术评估</div>
								<div class="font-size-9">Technical Assessment</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
									<div id="sylbDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">核实准确</div>
											<div class="font-size-9 ">Verify Accuracy</div>
										</label>	
										<div class="col-lg-4 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
											<div >
							                    <span style="padding-left:0px">
							                    	<label class="label-input"><input type='radio' class="noteditable" name='fjbs7' value="1" checked/>&nbsp;是&nbsp;</label>
							                    </span>
							                     <span>
							                    	 <label class="label-input"><input type='radio' class="noteditable" name='fjbs7' value="0"/>&nbsp;否&nbsp;</label>
												 </span>
						                	</div>
										</div>
										<div class="col-lg-4 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										</div>
									</div>
									<div id="sylbDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">同意保留期限</div>
											<div class="font-size-9 ">Expiry Date Period</div>
										</label>	
										<div class="col-lg-4 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
											<div >
							                    <span  style="padding-left:0px">
							                    	<label class="label-input"><input type='radio' class="noteditable" name='fjbs8' value="1" checked/>&nbsp;是&nbsp;</label>
							                    </span>
							                     <span >
							                    	 <label class="label-input"><input type='radio' class="noteditable" name='fjbs8' value="0"/>&nbsp;否&nbsp;</label>
												 </span>
						                	</div>
										</div>
										<div class="col-lg-4 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										</div>
									</div>
									<div id="sylbDiv" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="pull-left" style='margin-left:8px;'>
											<div class="font-size-12 margin-top-5">所采取措施是否符合要求？</div>
										</label>
										<div class="pull-left padding-leftright-8" >
											<div >
							                    <span  style="padding-left:0px">
							                    	<label ><input type='radio' class="noteditable" name='fjbs9' value="1" style='vertical-align:-2px;' />&nbsp;是&nbsp;</label>
							                    </span>
							                    <span >
							                    	 <label ><input type='radio' class="noteditable" name='fjbs9' value="0" style='vertical-align:-2px;' checked/>&nbsp;否&nbsp;</label>
												</span>
						                	</div>
										</div>
									</div>
									<div class="clearfix"></div>
					       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">评估人</div>
											<div class="font-size-9">Assessor</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input type="text"  id="jspgr"   maxlength="100"  class="noteditable form-control padding-left-3 padding-right-3 "  />
										</div>
									</div>
		          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">评估日期</div>
											<div class="font-size-9 ">Assess Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="jspgrq" onchange="Project_Open_Modal.onketup(this)" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
			     					 <div class="clearfix"></div>
								</div>
							</div>
					   	</div>
				   		<div class='clearfix'></div>
   						<div class="panel panel-default padding-right-0">
					        <div class="panel-heading bg-panel-heading">
					        	<div class="font-size-12 ">审核信息</div>
								<div class="font-size-9">Audit Info</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
					       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">审核人</div>
											<div class="font-size-9">Auditor</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input type="text"  id="shr" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3 "  />
										</div>
									</div>
		          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">审核日期</div>
											<div class="font-size-9 ">Review Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="shrq" onchange="Project_Open_Modal.onketup(this)" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">审核意见</div>
											<div class="font-size-9 ">Audit Opinion</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="noteditable form-control padding-left-3 padding-right-3" id="shyj"  
												style="height:55px" maxlength="150"></textarea>
										</div>
									 </div>
			     					 <div class="clearfix"></div>
								</div>
							</div>
					   	</div>
     					<div class="clearfix"></div>
   						<div class="panel panel-default padding-right-0">
					        <div class="panel-heading bg-panel-heading">
					        	<div class="font-size-12 ">批准信息</div>
								<div class="font-size-9">Approval Info</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
					       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">批准人</div>
											<div class="font-size-9">Approved By Caac</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<input type="text"  id="pzr" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3 "  />
										</div>
									</div>
		          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">批准日期</div>
											<div class="font-size-9 ">Approved Date</div>
										</label>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<input type="text"  id="pzrq"  onchange="Project_Open_Modal.onketup(this)" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
									 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
										<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 ">批准意见</div>
											<div class="font-size-9 ">Approval Advice</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
											<textarea class="noteditable form-control padding-left-3 padding-right-3" id="pzyj"  
												style="height:55px" maxlength="150"></textarea>
										</div>
									 </div>
			     					 <div class="clearfix"></div>
								</div>
							</div>
					   	</div>
						<div id="attachments_list_edit" style="display:none">
							<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
						</div>
					</form>
				</div>
			</div>	
		    <div class="clearfix"></div>
			<div class="modal-footer ">
				<div class="col-xs-12 padding-left-8 " >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
		                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
						   	<button id="baocuns" type="button" onclick="Project_Add_Modal.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="tijiao" type="button" onclick="Project_Add_Modal.submit();" class="btn btn-primary padding-top-1 padding-bottom-1">
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
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/projectkeep/projectkeep_open.js"></script><!--项目保留弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/projectkeep/projectkeep_add.js"></script><!--新增项目保留弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/projectkeep/projectkeep_update.js"></script><!--修改项目保留弹窗的js  -->
