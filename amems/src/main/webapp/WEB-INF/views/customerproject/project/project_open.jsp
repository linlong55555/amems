<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java"%>
<div class="modal fade in modal-new" id="AddEditAlertModal" tabindex="-1" role="dialog"  
       aria-labelledby="AddEditAlertModal" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width:85%;">
		<div class="modal-content">
		    <div class="modal-header modal-header-new" >
	               	<h4 class="modal-title" >
	           			<div class='pull-left'>
	               			<div class="font-size-12" id="materialHeadChina" ></div>
						<div class="font-size-9 " id="materialHeadEnglish"></div>
					 	</div>
						<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class='clearfix'></div>
					</h4>
          	</div>
			<div class="modal-body">
				<div class="col-xs-12 margin-top-8 ">
			        <form id="form_project">
				         <div class="input-group-border">
              					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">项目编号</div>
										<div class="font-size-9">Project No.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
									    <input type="hidden" id="project_id" value=''>
										<input type="text" class="form-control " id="xmbm" name="xmbm" maxlength="50" placeholder="不填写则自动生成"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>项目名称</div>
										<div class="font-size-9">P.Name</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control " id="xmmc" name="xmmc" maxlength="50" />
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">项目种类</div>
										<div class="font-size-9">Category</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<select class='form-control' id='xmzl' name="xmzl" >
											<option value="" ></option>
									    </select>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">客户种类</div>
										<div class="font-size-9">Category</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
										<input type="radio" value="0" name='pro_fl'  onclick="changeWbbs(this);"> 内部
										<input type="radio" value="1" checked="checked" name='pro_fl' onclick="changeWbbs(this);"> 外部 &nbsp; &nbsp;
									</div>
								</div>
								<div class='clearfix'></div>
								<!-- 客户 start -->
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="customer_No_1">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>客户编号</div>
										<div class="font-size-9 ">Customer No.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 " id="cust_add_20180115">
										<div class='input-group' style='width:100%;'>
											<input type="text" id="cust_no" name="cust_no" class="form-control readonly-style" readonly
											  ondblclick="openCustomerWin(1)" />
											<input type="hidden" class="form-control" id="cust_id" name="cust_id" />
											<span class="input-group-btn">
												<button type="button" class="btn btn-default form-control" 
												     data-toggle="modal" onclick="openCustomerWin(1)" >
													<i class="icon-search cursor-pointer"></i>
												</button>
											</span>
										</div>
									</div>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 " id="cust_edit_20180115">
										    <input type="text" class="form-control " id="cust_edit_no" name="cust_edit_no" maxlength="50" readonly="readonly"/>
								    </div>
								</div>
							    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="customer_Name_1">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">客户名称</div>
										<div class="font-size-9 ">C.Name</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control " id="cust_name" name="cust_name" maxlength="50" readonly="readonly"/>
									</div>
								</div>
								<!-- 客户 end -->
								<!-- 部门编号 start -->
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="departmentModalInput" style="display: none">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>部门编号</div>
										<div class="font-size-9 ">Dept No.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 " id="dept_add_20180115">
										<div class='input-group' style='width:100%;'>
											<input type="text" id="dept_no" name="dept_no" class="form-control readonly-style" readonly onclick="openzrdw(this);"/>
											<input type="hidden" class="form-control" id="dept_id" name="dept_id" />
											<span class="input-group-btn">
												<button type="button"  class="btn btn-default form-control" id="openDeptWin_20171011"
												     data-toggle="modal" onclick="openzrdw(this);">
													<i class="icon-search cursor-pointer"></i>
												</button>
											</span>
										</div>
									</div>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 " id="dept_edit_20180115" style="display: none">
										<input type="text" class="form-control " id="dept_edit_no" name="dept_edit_no" maxlength="50" readonly="readonly"/>
									</div>
								</div>
							    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="deptment_Name_1" style="display: none">
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">部门名称</div>
										<div class="font-size-9 ">D.Name</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control " id="dept_name" name="dept_name" maxlength="50" readonly="readonly"/>
									</div>
								</div>
								
								<!-- 部门名称 end -->
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>机型</div>
										<div class="font-size-9">Model</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<select class='form-control' id='fjjx' name="fjjx" onchange="changeFjjx()">
									    </select>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">飞机注册号</div>
										<div class="font-size-9 ">A/C REG</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control " id="fjzch" name="fjzch" maxlength="100"  onblur="getProjectAircraftInfo();"/>
									</div>
								</div>
								<div class='clearfix'></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">MSN</div>
										<div class="font-size-9 ">MSN</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control" id="fjxlh" name="fjxlh" maxlength="100" />
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">飞机描述</div>
										<div class="font-size-9 ">Desc</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control" id="fjbzm" name="fjbzm" maxlength="100" />
									</div>
								</div>
								<!-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">飞机序列号</div>
										<div class="font-size-9 ">Serial NO.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control" id="fjxlh" name="fjxlh" maxlength="100" />
									</div>
								</div> -->
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">航班号</div>
										<div class="font-size-9 ">Flight NO.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control" id="hbh" name="hbh" maxlength="100" />
									</div>
								</div>
								
								<!-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2"><span class="identifying" style="color: red">*</span>机型</div>
										<div class="font-size-9 ">Type</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control " id="fjjx" name="fjjx" maxlength="16" />
									</div>
								</div> -->
								
								<!-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">飞机描述</div>
										<div class="font-size-9 ">Desc</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input class="form-control" id="gg" name="gg" maxlength="16" />
									</div>
								</div> -->
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">计划进场时间</div>
											<div class="font-size-9">E.T.A.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control date-picker" maxlength="10" id="jh_ksrq" name="jh_ksrq"  data-date-format="yyyy-mm-dd"/>
									</div>
								</div>
								<div class='clearfix'></div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">计划出场时间</div>
										<div class="font-size-9">E.T.D.</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control date-picker" maxlength="10" id="jh_jsrq" name="jh_jsrq" data-date-format="yyyy-mm-dd"/>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">实际进场时间</div>
											<div class="font-size-9">Actual In</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control date-picker" maxlength="10" id='sj_ksrq' name="sj_ksrq" data-date-format="yyyy-mm-dd"/>
									</div>
								</div>
								<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">实际出场时间</div>
											<div class="font-size-9">Actual Out</div>
									</label>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<input type="text" class="form-control date-picker" maxlength="10" id='sj_jsrq' name="sj_jsrq" data-date-format="yyyy-mm-dd"/>
									</div>
								</div>
								
								<div class='clearfix'></div>
							</div>
							
							<!-- 飞机其他信息 -->
							<div  class="panel panel-default padding-right-0 " id="fjInfo_div">
						        <div class="panel-heading bg-panel-heading">
					        		<div class="font-size-12">飞机其他信息</div>
								     <div class="font-size-9 ">Aircraft Other Info</div>
							    </div>
								<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
									<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">飞机飞行小时</div>
													<div class="font-size-9 ">A/C FH</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" maxlength="10" id="fxsj" name="fxsj"/>
											</div>
										</div>
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">飞机飞行循环</div>
													<div class="font-size-9 ">A/C FC</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" maxlength="10" name="fxxh" id="fxxh" onkeyup="clearNoNumber(this)"/>
											</div>
										</div>
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">IPC有效性号</div>
													<div class="font-size-9 ">IPC Effectivity</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" maxlength="50" id="ipcyxxh"/>
											</div>
										</div>
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">滑油牌号</div>
													<div class="font-size-9 ">Oil P/N</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" maxlength="100" id="rhyzph"/>
											</div>
										</div>
										<div class='clearfix'></div>
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">液压油牌号</div>
													<div class="font-size-9 ">HYD Fluid P/N</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" maxlength="100" id="yyyph"/>
											</div>
										</div>
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">飞机状态</div>
													<div class="font-size-9 ">Aircraft Status</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<select class='form-control' id='fjzt'>
													<option value="" ></option>
												</select>
											</div>
										</div>
									</div>

									<div class='col-xs-12 padding-leftright-8 margin-top-8 margin-bottom-8'>
									<div class="col-xs-12 padding-left-0 padding-right-0" style='overflow:auto;'>
									<table class="table table-thin table-bordered table-striped table-hover table-set">

									    <thead>
									     <tr>
										     <th class="colwidth-10">
										       <div class="font-size-12 line-height-18">装机位置</div>
										       <div class="font-size-12 line-height-18">Location</div>
										     </th>
										     <th class="colwidth-10">
										        <div class="font-size-12 line-height-18">件号</div>
										        <div class="font-size-12 line-height-18">P/N</div>
										        </th>
										     <th class="colwidth-10">
										        <div class="font-size-12 line-height-18">型号</div>
										        <div class="font-size-12 line-height-18">Model No.</div>
										      </th>
										     <th class="colwidth-10">
										        <div class="font-size-12 line-height-18">序号</div>
										        <div class="font-size-12 line-height-18">S/N</div>
										     </th>
										     <th class="colwidth-10">
										        <div class="font-size-12 line-height-18">时间</div>
										        <div class="font-size-12 line-height-18">HRS</div>
										     </th>
										     <th class="colwidth-10">
										         <div class="font-size-12 line-height-18">循环</div>
										         <div class="font-size-12 line-height-18">Cycle</div>
										     </th>
									     </tr>
									  </thead>
									  <tbody id="engine_list_info">
									     <tr>
									        <td class='text-center'>APU
									            <input type="hidden" name="apu_id_h" id="apu_id_h" value=""/>
									            <input type="hidden" name="wz" value="31"/>  <!-- 位置代码 -->
									            <input type="hidden" name="apu_id_c" id="apu_id_c" value=""/>
									        </td>
									        <td><input type="text" id="apu_jh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="apu_xh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="apu_xlh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="apu_hv" class="form-control " name='init_time_apuh' maxlength="10" 
									              style="width: 130px ;float: left">APUH
									            <input type="hidden" name="jklbh" value="2_20_AH">  <!-- 监控类编号 -->
									            <input type="hidden" name="jkflbh" value="2T"> <!-- 监控分类编号 --> 
									        </td>
									        <td><input type="text" class="form-control" maxlength="16" id="apu_cv" name='apu_cv_k' onkeyup="clearNoNumber(this)"
									              style="width: 130px ;float: left"/>APUC
									              <input type="hidden" name="jklbh" value="3_20_AC">  
									              <input type="hidden" name="jkflbh" value="3C">
									        </td>
									     </tr>
									     <tr>
									        <td class='text-center'>1#发
									            <input type="hidden" name="e1_id_h" id="e1_id_h" value=""/> <!-- 数据库主键 -->
									            <input type="hidden" name="wz" value="21"/>  <!-- 位置代码 -->
									            <input type="hidden" name="e1_id_c" id="e1_id_c" value=""/> <!-- 数据库主键 -->
									        </td> 
									        <td><input type="text" id="e1_jh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="e1_xh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="e1_xlh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="e1_hv" class="form-control" maxlength="16" name='init_time_1eh'
									             style="width: 130px ;float: left"/>EH
									            <input type="hidden" name="jklbh" value="2_30_EH">  <!-- 监控类编号 -->
									            <input type="hidden" name="jklbh" value="2T">  <!-- 监控类编号 -->
									        </td>
									        <td><input type="text" class="form-control" maxlength="16" id="e1_hc" name='e1_hc_k' onkeyup="clearNoNumber(this)"
									             style="width: 130px ;float: left"/>EC
									             <input type="hidden" name="jklbh" value="3_30_EC">  <!-- 监控类编号 -->
									             <input type="hidden" name="jklbh" value="3C">        <!-- 监控类编号 -->
									        </td>
									     </tr>
									     <tr>
									        <td class='text-center'>2#发
									            <input type="hidden" name="e2_id_h" id="e2_id_h" value=""/> <!-- 数据库主键 -->
									            <input type="hidden" name="wz" value="22"/>  <!-- 位置代码 -->
									            <input type="hidden" name="e2_id_c" id="e2_id_c" value=""/> <!-- 数据库主键 -->
									        </td>
									        <td><input type="text" id="e2_jh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="e2_xh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="e2_xlh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="e2_hv" class="form-control" maxlength="16" name='init_time_2eh' 
									            style="width: 130px ;float: left"/>EH
									            <input type="hidden" name="jklbh" value="2_30_EH">  <!-- 监控类编号 -->
									            <input type="hidden" name="jklbh" value="2T">  <!-- 监控类编号 -->
									        </td>
									        <td><input type="text" class="form-control" maxlength="16" id="e2_hc" name='e2_hc_k' onkeyup="clearNoNumber(this)"
									            style="width: 130px ;float: left"/>EC
									            <input type="hidden" name="jklbh" value="3_30_EC">  <!-- 监控类编号 -->
									            <input type="hidden" name="jklbh" value="3C">        <!-- 监控类编号 -->
									        </td>
									     </tr>
									     <tr>
									        <td class='text-center'>3#发
									            <input type="hidden" name="e3_id_h" id="e3_id_h" value=""/> <!-- 数据库主键 -->
									            <input type="hidden" name="wz" value="23"/>  <!-- 位置代码 -->
									            <input type="hidden" name="e3_id_c" id="e3_id_c" value=""/> <!-- 数据库主键 -->
									        </td>
									        <td><input type="text"  id="e3_jh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text"  id="e3_xh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text"  id="e3_xlh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text"  id="e3_hv"  class="form-control" maxlength="16" name='init_time_3eh' 
									            style="width: 130px ;float: left"/>EH
									            <input type="hidden" name="jklbh" value="2_30_EH">  <!-- 监控类编号 -->
									            <input type="hidden" name="jklbh" value="2T">  <!-- 监控类编号 -->
									        </td>
									        <td><input type="text" class="form-control" maxlength="16" id="e3_hc" name='e3_hc_k' onkeyup="clearNoNumber(this)"
									            style="width: 130px ;float: left"/>EC
									            <input type="hidden" name="jklbh" value="3_30_EC">  <!-- 监控类编号 -->
									            <input type="hidden" name="jklbh" value="3C">        <!-- 监控类编号 -->
									       </td>
									     </tr>
									     <tr>
									        <td class='text-center'>4#发
									            <input type="hidden" name="e4_id_h" id="e4_id_h" value=""/> <!-- 数据库主键 -->
									            <input type="hidden" name="wz" value="24"/>  <!-- 位置代码 -->
									            <input type="hidden" name="e4_id_c" id="e4_id_c" value=""/> <!-- 数据库主键 -->
									        </td>
									        <td><input type="text" id="e4_jh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="e4_xh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="e4_xlh" class="form-control" maxlength="16" /></td> 
									        <td><input type="text" id="e4_hv" class="form-control" maxlength="16"  name='init_time_4eh' 
									            style="width: 130px ;float: left"/>EH
									            <input type="hidden" name="jklbh" value="2_30_EH">  <!-- 监控类编号 -->
									            <input type="hidden" name="jklbh" value="2T">  <!-- 监控类编号 -->
									        </td>
									        <td><input type="text" class="form-control" maxlength="16" id="e4_hc" name='e4_hc_k' onkeyup="clearNoNumber(this)"
									            style="width: 130px ;float: left"/>EC
									         <input type="hidden" name="jklbh" value="3_30_EC">  <!-- 监控类编号 -->
									         <input type="hidden" name="jklbh" value="3C">        <!-- 监控类编号 -->   
									        </td>
									     </tr>
									  </tbody>
								</table>
									</div>
									</div>
								</div>
							</div>
							<!-- 销售订单信息 -->
							<div  class="panel panel-default padding-right-0 ">
						        <div class="panel-heading bg-panel-heading">
					        		<div class="font-size-12">销售订单信息</div>
									<div class="font-size-9 ">Sales Infos</div>
							    </div>
								<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
									<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
							           <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">销售订单号</div>
													<div class="font-size-9">Sales Order</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" name="xsddh" id="xsddh" maxlength="16" />
											</div>
										</div>
										<!-- <div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
											<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12  margin-topnew-2">主要条款</div>
											    <div class="font-size-9">Primary Special Terms</div>
											</label>
											<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
												<textarea class="form-control padding-left-3 padding-right-3"  style="height:55px" maxlength="300" disabled="disabled" id="shxzljswjzt"></textarea>
											</div>
									    </div> -->
										<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
											<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12  margin-topnew-2">主要条款</div>
												<div class="font-size-9">Main Terms</div>
											</label>
											<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
												<textarea class="form-control padding-left-3 padding-right-3"  style="height:55px" maxlength="300" id="fstk">
												</textarea>
											</div>
									    </div>
									    <!-- <div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
											<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12  margin-topnew-2">合同附件</div>
												<div class="font-size-9 ">Attachment</div>
											</label>
											<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
												<div class="input-group" id='uploadarea'>
												<span class="input-group-addon inputgroupbordernone" style="">
											    	<label class="margin-left-0 label-input">
											    		<input  type="checkbox" onclick="allowFileUpload(this);"> &nbsp;
											    	</label>
											    </span>
											    <span  class="singlefile input-group-btn uploaderDiv" id="uploaderDiv_t">
												</span>
												<div class="font-size-12 line-height-30" id="scwjWbwjm" style="display: none"></div>
										      </div>
											</div>
									    </div> -->
									</div>
								</div>
							</div>
							<!-- 项目执行信息 -->
							<div  class="panel panel-default padding-right-0 ">
						        <div class="panel-heading bg-panel-heading">
					        		<div class="font-size-12">项目执行信息</div>
									<div class="font-size-9 ">Project Performance Info</div>
							    </div>
								<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
									<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">项目经理</div>
													<div class="font-size-9 ">P/M</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" id="xmjl" name="xmjl" maxlength="16" />
											</div>
										</div>
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">计划主控</div>
													<div class="font-size-9 ">Plan Master</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" id="jhzk" name="jhzk" maxlength="jhzk" />
											</div>
										</div>
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">控制室</div>
													<div class="font-size-9 ">Phone Number</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" id="kzs" name="kzs" maxlength="16" />
											</div>
										</div>
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">起算工时</div>
													<div class="font-size-9 ">Cap</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" id="qsgs" name="qsgs" maxlength="16" />
											</div>
										</div>
										<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
											<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
													<div class="font-size-12  margin-topnew-2">控制室电话</div>
													<div class="font-size-9 ">Control Room Landline</div>
											</label>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
												<input type="text" class="form-control" id="kzsdh" name="kzsdh" maxlength="16" />
											</div>
										</div>
										<!-- <div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
											<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">其他说明</div>
												<div class="font-size-9">Other Instructions</div>
											</label>
											<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
												<textarea class="form-control padding-left-3 padding-right-3"  style="height:55px" maxlength="300" id="qtsm"></textarea>
											</div>
									    </div> -->
									</div>
								</div>
							</div>
							<!-- 附件 -->
							<div  class="panel panel-default padding-right-0 ">
						        <div class="panel-heading bg-panel-heading">
					        		<div class="font-size-12">合同附件</div>
									<div class="font-size-9 ">Attachment List</div>
							    </div>
								<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
									<div class="col-xs-12 margin-top-8 padding-leftright-8" style='overflow:auto;'>
									  <table class="table table-thin table-bordered table-striped table-hover table-set" id="table_procet_20171011">
										<thead>
											<tr>
												<th class=" colwidth-4">
												    <div class="font-size-12 line-height-18 " id='uploadarea'>
											        <span  class="singlefile input-group-btn uploaderDiv" id="uploaderDiv_t">
												    </span>
												   <div class="font-size-12 line-height-30" id="scwjWbwjm" style="display: none"></div>
												    </div>
													<!-- <div class="font-size-9 line-height-18">Operation</div> -->
												</th>	
												<th class=" colwidth-20">
												<div class="font-size-12 line-height-18">文件说明</div>
													<div class="font-size-9 line-height-18">File Explanation</div>
												</th>
												
												<th class=" colwidth-5">
												<div class="font-size-12 line-height-18">文件大小</div>
													<div class="font-size-9 line-height-18">File Size</div>
												</th>
												<th class=" colwidth-7"><div class="font-size-12 line-height-18">上传人</div>
													<div class="font-size-9 line-height-18">Uploader </div></th>
												<th class=" colwidth-7"><div class="font-size-12 line-height-18">上传时间</div>
													<div class="font-size-9 line-height-18">Upload Time</div></th>					
											</tr>
										</thead>
										    <tbody id="filelist">
												 
											</tbody>
									</table>
									</div>
								</div>
							</div>
	                    </form>
					</div>
			      <div class="clearfix"></div>
			 </div>
			 <div class="modal-footer">
	          		<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
							<span class="input-group-addon modalfootertip" >
		                      <i class='glyphicon glyphicon-info-sign alert-info'></i>
		                      <p class="alert-info-message"></p>
					        </span>
		                   	<span class="input-group-addon modalfooterbtn">
		                     	<button id="materialSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:saveProject();">
				                   	<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
				              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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