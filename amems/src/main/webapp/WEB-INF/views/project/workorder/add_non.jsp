<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
	var pageParam = '${param.pageParam}';
	</script>
</head>
<body class="page-header-fixed">
    <input id="newFileName" type="hidden"/>
	<input id="relativePath" type="hidden"/>
	<input id="fileName" type="hidden"/>
		<input maxlength="20"  type="hidden" id="isScheduler" value="${isScheduler }"/>
		<input maxlength="20"  type="hidden" id="isDuration" value="${isDuration }"/>
		<input maxlength="20"  type="hidden" id="isActualhours" value="${isActualhours }"/>
<div class="page-content">
			<!-- from start -->
	<div class="panel panel-primary">
	  <div class="panel-heading" id="NavigationBar"></div>
            
            <input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
            
            <input type="hidden" id="djgkid" name="djgkid" value="${jobcard.id}"/>
	        
	        <div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0  padding-right-0">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">工单基本信息 Basic Information
							 <a href="javascript:copy();"  >
							 <i id="copy_wo" class='icon-copy color-blue cursor-pointer pull-right' title="复制 Copy"></i>
							 </a>
							</h3>
							<input type="hidden" id="gczlbh" value="用于复制工单是判断是否对执行对象进行复制"/>
						</div>
						<div class="panel-body">
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">工单类型</div>
											<div class="font-size-9 line-height-18">W/O Type</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
											<select id="gdlx" name="gdlx" class="form-control"
												onchange="changeLx()">
												<option value="2">附加工单</option>
												<option value="3">排故工单</option>
											</select>
										</div>
										<input type="hidden" id="gddlx" value="2" />
									</div>

									<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">飞机机型</div>
											<div class="font-size-9 line-height-18">Model</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
											<select class="form-control " id="fjjx" name="fjjx" onchange="changeJx()">
											</select>
											<input type="hidden" id="pgjx" name="pgjx"/>
										</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">基地</div>
											<div class="font-size-9 line-height-18">Station</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
											<select class="form-control " id="jd" name="jd">
												<c:forEach items="${baseList}" var="baseList">
													<option value="${baseList.id}">${erayFns:escapeStr(baseList.jdms)}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">专业</div>
											<div class="font-size-9 line-height-18">Skill</div></label>
										<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
											<select id="zy" class="form-control " name="zy"></select>
											<input type="hidden" id="temp_zy" value="${jobcard.zy}"/>
											<input type="hidden" id="gkid" value="${jobcard.id}"/>
											<input type="hidden" id="temp_gzzw" value="${jobcard.gzzw}"/>
											<input type="hidden" id="temp_zjh" value="${jobcard.zjh}"/>
											<input type="hidden" id="temp_zjhZwms" value="${jobcard.zjhZwms}"/>
											<input type="hidden" id="temp_gzz" value="${jobcard.gzz}"/>
										</div>
									</div>

								</div>
	                              <div class="clearfix"></div>
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">ATA章节号</div>
											<div class="font-size-9 line-height-18">ATA</div>
										</label>
										<div class=" col-xs-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group">
												<div class='input-group'>
													<input type="text" id="zjhandname" class="form-control"
															disabled="disabled" /> <input type="hidden" id="zjh"class="form-control" />
													<span class='input-group-btn'>
													 <a href="#" onclick="openChapterWin()" data-toggle="modal"
												  class="btn btn-primary padding-top-4 padding-bottom-4" > <i class="icon-search cursor-pointer"></i>
											</a>
													</span>
											    </div>
											</div>
										
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">工作组</div>
											<div class="font-size-9 line-height-18">Work Group</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
											<select class="form-control " id="gzz" name="gzz">
											</select>
										</div>
									</div>
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">工作站位</div>
											<div class="font-size-9 line-height-18">Location</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group" form-group>
											 <select class="form-control " id="gzzw" name="gzzw">
											</select>
										</div>
									</div>
									
									<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">需要技术支援</div>
											<div class="font-size-9 line-height-18">Need Support</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
											<label style="margin-right: 20px;font-weight: normal"><input name="isXyjszy" type="radio" value="1" checked/>是</label> 
											<label style="font-weight: normal"><input name="isXyjszy" type="radio" value="0" />否 </label> 
										</div>
									</div>
									
								<div class="clearfix"></div>	
									<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										    <div class="font-size-12 line-height-18"><span style="color: red">*</span>标准工时</div>
											<div class="font-size-9 line-height-18">MHRS</div>
										</label>
										
										<div class=" col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group">
												<input type="text" maxlength="8" class="form-control " 
													id="jhgsRs" value="${jobcard.jhgsRs}" onkeyup='clearNoNum(this)'>
												<span class='input-group-addon' style="padding-left:0px;border:0px;background:none;">人 ×</span>
												<input maxlength="6" type="text" class="form-control" id="jhgsXss" value="${jobcard.jhgsXss}" onkeyup='onkeyup4Num(this)' mouseup='onkeyup4Num(this)'>
												<span class='input-group-addon' style="padding-left:0px;padding-right:0px;border:0px;background:none;">时 ＝</span>
												<input maxlength="8" type="text" class="form-control " id="time" readOnly>
										</div>
									</div>
									<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" id="jobcard_demo">
										<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">来源工卡</div>
											<div class="font-size-9 line-height-18">Souce JobCard</div>
										</label>
										<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group padding-top-8">
										    <label><a id="view_gkid" href="#" style="font-weight: normal;" onclick="ViewJobCard()">${erayFns:escapeStr(jobcard.gdbh)}</a></label>
										    <input type="hidden" id="temp_id" value="${jobcard.id}"/>
										    <input type="hidden" id="temp_dprtcode" value="${jobcard.dprtcode}"/>
										</div>
									</div>
								</div>
								 <div class="clearfix"></div>
							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group">
									<div class="font-size-12 line-height-18">
										关联评估单</div>
									<div class="font-size-9 line-height-18">Evaluation</div>
								</label>

								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x:auto;">
									<table class="table-set table table-bordered table-striped table-hover text-center" style="min-width:900px">
										<thead>
											<tr>
												<td width="6%"><a href="javascript:openPgd();">
														<button  class="line6">
															<i class="icon-search cursor-pointer"></i>
														</button>
												</a></td>
												<th>
												   <div class="font-size-12 line-height-18">评估单号</div>
													<div class="font-size-9 line-height-18">Assessment No.</div>
												</th>
												<th>
												   <div class="font-size-12 line-height-18">来源</div>
													<div class="font-size-9 line-height-18">Source </div>
												</th>
												<th>
												   <div class="font-size-12 line-height-18">参考资料</div>
													<div class="font-size-9 line-height-18">Reference Material</div>
												</th>
												<th>
												    <div class="font-size-12 line-height-18">生效日期</div>
													<div class="font-size-9 line-height-18">Effective Date</div>
												</th>
												<th>
												    <div class="font-size-12 line-height-18">机型工程师</div>
													<div class="font-size-9 line-height-18">Engineer</div>
												</th>
												<th>
												    <div class="font-size-12 line-height-18">评估期限</div>
													<div class="font-size-9 line-height-18">Assess period</div>
												</th>
												<th>
												    <div class="font-size-12 line-height-18">评估状态</div>
													<div class="font-size-9 line-height-18">State</div>
												</th>
											</tr>
										</thead>
										<tbody id="Annunciatelist">
										</tbody>
									</table>
								</div>
							</div>
							<form id="form">
							<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
									<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label  class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18"><span style="color: red">*</span>工单主题</div>
											<div class="font-size-9 line-height-18">Subject</div>
										</label>
										<div class="col-lg-10 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
										    <textarea class="form-control" id="zhut" name="zhut" placeholder='长度最大为600' maxlength="600" style="min-height:60px" >${erayFns:escapeStr(jobcard.gzzt)}</textarea>
										</div>
									</div>
									<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">关联文件</div>
											<div class="font-size-9 line-height-18">Related Files</div>
										</label>
										<div class="col-lg-10 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
										   <textarea class="form-control" id="glxx" name="glxx" placeholder='关联飞行单/工卡/任务书号/其他' maxlength="100" style="min-height:60px" ></textarea>
										</div>
									</div>	
								</div>
							</div>
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
									<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">停场时间</div>
											<div class="font-size-9 line-height-18">Stop Time</div>
										</label>
										<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
											<input type="text"  class="form-control" 	name="tcsj" id="tcsj"   maxlength="50"/>
										</div>
									</div>

									<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">下发工单原因</div>
											<div class="font-size-9 line-height-18">Reason for W/O</div>
										</label>
										<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
											<textarea class="form-control" id="xfgdyy" name="xfgdyy" rows="2"
												maxlength="300" placeholder='最大长度不超过300'></textarea>
										</div>
									</div>
								</div>
								<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
									<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
										<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
												class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-18">Remark</div></label>
										<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
											<textarea class="form-control" id="bz" name="bz"
												maxlength="300" placeholder='最大长度不超过300'>${erayFns:escapeStr(jobcard.bz)}</textarea>
										</div>
									</div>
								</div>
							</form>
						</div>
			</div>
			
			<div class="panel panel-default">
					     <div class="panel-heading">
							<h3 class="panel-title"><span style="color: red">*</span>执行对象 Execute Object</h3>
						 </div>
			  <div class="panel-body">
			        <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: auto;">
				        <table class="table table-bordered table-striped table-hover text-center" 	style=" min-width: 1700px !important;">
							<thead>
							<tr>
							<th rowspan="2" width="150" style=" vertical-align:middle">
							        <div class="font-size-12 line-height-18"  >执行分类</div>
							         <div class="font-size-9 line-height-18" >Category</div>
					         </th>
							<th rowspan="2" width="10%" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >飞机注册号</div>
								<div class="font-size-9 line-height-18" >A/C REG</div>
							</th>
							<th colspan="3" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >飞机</div>
								<div class="font-size-9 line-height-18" >Aircraft</div>
							</th>
							<th rowspan="2" width="11%" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >部件名称</div>
								<div class="font-size-9 line-height-18" >Name</div>
							</th>
							
							<th rowspan="2" width="8%" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >部件件号</div>
								<div class="font-size-9 line-height-18" >P/N</div>
							</th>
							<th rowspan="2" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >部件序列号</div>
								<div class="font-size-9 line-height-18" >S/N</div>
							</th>
							<th colspan="5"  style=" vertical-align:middle">
							      <div class="font-size-12 line-height-18" >计划完成时限</div></th>
							</tr> 
							<tr>
							   <th style=" vertical-align:middle">
								       <div class="font-size-12 line-height-18" >序列号</div>
								      <div class="font-size-9 line-height-18" >S/N</div>
						      	</th>
								<th  style=" vertical-align:middle">
										<div class="font-size-12 line-height-18" >左发序号</div>
										<div class="font-size-9 line-height-18" >1# S/N</div>
								</th>
								<th style=" vertical-align:middle">
										<div class="font-size-12 line-height-18" >右发序号</div>
										<div class="font-size-9 line-height-18" >2# S/N</div>
								</th>
								<th width="10%" style=" vertical-align:middle">
								       <div class="font-size-12 line-height-18" >完成日期</div>
								      <div class="font-size-9 line-height-18" >Finish Date</div>
						      	</th>
								<th width="6%" style=" vertical-align:middle">
										<div class="font-size-12 line-height-18" >机身时间</div>
										<div class="font-size-9 line-height-18" >A/C HRS.</div>
								</th>
								<th width="6%" style=" vertical-align:middle">
										<div class="font-size-12 line-height-18" >起落循环</div>
										<div class="font-size-9 line-height-18" >A/C CYCS.</div>
								</th>
								<th width="6%" style=" vertical-align:middle">
										<div class="font-size-12 line-height-18" >绞车时间</div>
										<div class="font-size-9 line-height-18" >Winch Time</div>
								</th>
								<th width="6%" style=" vertical-align:middle">
										<div class="font-size-12 line-height-18" >绞车循环</div>
										<div class="font-size-9 line-height-18" >Winch CYCS.</div>
								</th>
							</tr>
			         		 </thead>
							<tr   id="zxdxtr">
			         		     <td width="150" >
			         		          <select  id="zxfl" name="zxfl" class="form-control" onchange="changeZXFL()">
			         		               <option value="" >选择执行分类Choice </option>
			         		               <option value="FJ" >机身 </option>
			         		               <option value="ZJJ" >飞机部件</option>
			         		               <option value="FZJJ" >非装机件</option>
						             </select>
			         		     </td>
			         		    <td>
			         		       <select  id="fjzch" name="fjzch" class="form-control"  >
			         		            <option value="">请选择Choice</option>
						           </select>
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control"  readonly onmouseover="this.title=this.value" id="fjxlh" >
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" readonly onmouseover="this.title=this.value" id="zfxh" >
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" readonly onmouseover="this.title=this.value" id="yfxh" >
			         		    </td>
			         		    <td>
			         		       <div class='input-group'>
										<span class='input-group-btn' id="searchBut" style="display: none">
										  <button onclick='openBjhList()' class='btn btn-primary'><i class='icon-search'></i>
										</button></span>
			         		            <input type="text" class="form-control" name="bjmc" readonly onmouseover="this.title=this.value" id="bjmc" >
								    </div>
			         		    </td>
			         		    <td>
								    <input type="text" id="bjh"  name="bjh" class="form-control " readonly/>
			         		        <input type="hidden"  id="zjqdid" name="zjqdid"/>
			         		    </td>
			         		    <td width="8%">
								    <input type="text" id="bjxlh"  name="bjxlh" class="form-control " readonly/>
								    <input type="hidden"  id="bjxlh_demo" name="bjxlh_demo"/>
			         		    </td>
			         		    <td>
			         		       <input type="text" class='form-control datepicker' name="wcrq" id="wcrq" data-date-format="yyyy-mm-dd" 
			         		       placeholder="please choose Date"  onchange="changeDate()"/>
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" name="jssj" id="jssj"  maxlength="8" onmouseout='judge()'  onkeyup='yanzhen(this,1)'>
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" name="qlxh" id="qlxh" maxlength="8"  onmouseout='judge()' onkeyup='yanzhen(this,2)'>
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" name="jcsj" id="jcsj" maxlength="8" onmouseout='judge()'  onkeyup='yanzhen(this,1)'>
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" name="jcxh" id="jcxh"  maxlength="8" onmouseout='judge()' onkeyup='yanzhen(this,2)' >
			         		    </td>
			         		 </tr>
				   			</table>
						 </div>		
					</div>
				</div>	   
				 <div class="panel panel-default">
				        <div class="panel-heading">
							    <h3 class="panel-title">工作内容 Contents</h3>
					   </div>
					<div class="panel-body">	 	
					<div class="col-lg-12 col-md-12 padding-left-2 padding-right-0" style="overflow-x: auto;">
						<table class="table  table-bordered table-striped table-hover text-center" id="addtr" style="min-width:1000px">
							<thead>
								<tr>
								   <td style="width: 40px;">
								         <button id="addTable"  class="line6" onclick="addTr()">
									     <i class="icon-plus cursor-pointer color-blue cursor-pointer'" ></i>
								        </button>
		 						   </td>	
									<th width="4%"><div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">No.</div></th>
									<th><div class="font-size-12 line-height-18">工作内容</div>
										<div class="font-size-9 line-height-18">Contents</div></th>
									<th width="10%"><div class="font-size-12 line-height-18">工作人</div>
										<div class="font-size-9 line-height-18">Worker</div></th>
									<th width="8%"><div class="font-size-12 line-height-18">是否必检</div>
										<div class="font-size-9 line-height-18">Is Check</div></th>
								</tr>
							</thead>
							<tbody id="listContent">
							</tbody>
						</table>
					</div>
				</div>
		 </div>  		
				
	       <div class="panel panel-default">
						        <div class="panel-heading">
									    <h3 class="panel-title">关联相关工单 Related Work Order</h3>
							   </div>
						<div class="panel-body">	 	   
						 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0"  style="overflow-x: auto;" id="fjgdModel">
								<table class="table-set table  table-bordered table-striped table-hover text-center" id="relatedWorkOrder" style="min-width:1100px">
									<thead><tr>
										<td width="4%">
											<a href="javascript:openGlgd();"  >
												<button  class="line6" type="button"><i class="icon-search cursor-pointer" ></i></button>
											</a>
										</td>
										<th width="4%"><div class="font-size-12 line-height-18" >序号</div><div class="font-size-9 line-height-18" >No.</div></th>
										<th><div class="font-size-12 line-height-18" >工单编号</div><div class="font-size-9 line-height-18" >W/O No.</div></th>
										<th><div class="font-size-12 line-height-18" >工单类型</div><div class="font-size-9 line-height-18" >Work Order Type</div></th>
										<th><div class="font-size-12 line-height-18" >专业</div><div class="font-size-9 line-height-18" >Skill</div></th>
										<th width="50%"><div class="font-size-12 line-height-18" >主题</div><div class="font-size-9 line-height-18" >Subject</div></th>
						         	</tr></thead>
									     <tbody id="glgdCardList">
									</tbody>
				                 </table>
							</div>
				  </div>
				 </div>  
	       
	
	       <div class="panel panel-default">
						        <div class="panel-heading">
									    <h3 class="panel-title">航材耗材工具信息 Air Material Tools</h3>
							   </div>
						<div class="panel-body">	 	   
						  <div class="col-lg-12 col-md-12 padding-left-2 padding-right-0" style="overflow-x: auto;" >
								<table class="table-set table table-bordered table-striped table-hover text-center" id="airMaterialTools" style="min-width:1000px">
									<thead>
										<tr>
											<td width="4%">
											<a href="javascript:openHcxxList();"  >
												<button  class="line6"><i class="icon-search cursor-pointer" ></i></button>
											</a>
											</td>
											<th width="4%"><div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-16">No.</div></th>
											<th ><div class="font-size-12 line-height-18">件号来源</div>
											<div class="font-size-9 line-height-16">Source </div></th>
											<th><div class="font-size-12 line-height-18">英文名称</div>
											<div class="font-size-9 line-height-16">F.Name</div></th>
											<th><div class="font-size-12 line-height-18">中文名称</div>
											<div class="font-size-9 line-height-16">CH.Name</div></th>
											<th><div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-16">P/N</div></th>
											<th width="9%"><div class="font-size-12 line-height-18">在库数量</div>
											<div class="font-size-9 line-height-16">Stock Quantity</div></th>
											<th width="9%"><div class="font-size-12 line-height-18">替代数量</div>
											<div class="font-size-9 line-height-16">Num</div></th>
											<th width="9%"><div class="font-size-12 line-height-18">使用数量</div>
											<div class="font-size-9 line-height-16">Use Quantity</div></th>
											<th><div class="font-size-12 line-height-18">类型</div>
											<div class="font-size-9 line-height-16">Type</div></th>
											<th width="30%"><div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-16">Remark</div></th>
										</tr>
									</thead>     
									<tbody id="CKlist"></tbody>	
								</table>
				   </div>
				  </div>
				 </div>  
			
			  <div class="panel panel-default">
				        <div class="panel-heading">
							    <h3 class="panel-title">附件上传 File Upload</h3>
					   </div>
					<div class="panel-body">	 	
					
					 <div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File Description</div>
						</label>
					   <div class=" col-xs-8 col-sm-7 col-xs-8 padding-left-8 padding-right-0 input-group">
						<div class='input-group'>
							<input type="text" id="wbwjm" name="wbwjm"  maxlength="100" class="form-control "  >
							<span class='input-group-btn'>
							 <div id="fileuploader"  ></div>
							</button>
							</span>
					    </div>
					    </div>
					</div>
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
						<table class="table-set table table-bordered table-striped table-hover text-center" style="min-width:900px">
							<thead>
								<tr>
									<th class="colwidth-3"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th class="colwidth-20">
									<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">Description</div>
									</th>
									<th class="colwidth-10">
									<div class="font-size-12 line-height-18">文件大小</div>
										<div class="font-size-9 line-height-18">File Size</div>
									</th>
									<th class="colwidth-13"><div class="font-size-12 line-height-18">上传人</div>
										<div class="font-size-9 line-height-18">Uploader</div></th>
									<th class="colwidth-13"><div class="font-size-12 line-height-18">上传时间</div>
										<div class="font-size-9 line-height-18">Upload Time</div></th>					
								</tr>
							</thead>
							    <tbody id="filelist">
									 
								</tbody>
						</table>
					</div>
					</div>
				</div>
		 </div>  		
			
				<div style="display: none"><input maxlength="20"  id="planedataId"  type="text"  name="menuId"/> </div>
			   <div class="clearfix"></div>
				<div class="col-lg-12 text-right pull-right padding-left-0 padding-right-0" style="margin-bottom: 10px">
					<button href="javascript:void()" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="woSave()" >
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
					</button>
					<button href="javascript:void()" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="submitSave()" >
							<div class="font-size-12">提交</div>
							<div class="font-size-9">Submit</div>
					</button>
					<button href="javascript:void()" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()" >
							<div class="font-size-12">返回</div>
							<div class="font-size-9">Back</div>
					</button>
                 </div>
			</div>
		</div>
		
<!--评估单的模态框 -->
 <div class="modal fade" id="alertModalPgd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:92%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">技术评估单列表</div>
							<div class="font-size-9 ">List of Technical Files</div>
			     	   </div>
						<div class="panel-body padding-top-0 padding-bottom-0">
								   <div class="pull-right padding-left-0 padding-right-0 margin-bottom-10" >
								   
									   <div class=" pull-left padding-left-0 padding-right-0 padding-top-10 padding-bottom-10" style="width:250px;">
									   <input type="text" placeholder="文件编号/评估单号/文件主题" id="keyword_search_pgd" class="form-control ">
				                       </div>
					                       
			                          <div class=" pull-right padding-left-5 padding-right-0 padding-top-10 padding-bottom-10">   
											<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision_pgd();">
															<div class="font-size-12">搜索</div>
															<div class="font-size-9">Search</div>
					                   		</button>
			                   		 </div> 
					               </div>
				                <div class="clearfix"></div>	     
								<!-- start:table -->
						<div  style="overflow-x: auto;" class=" padding-right-0">
						 <table class="table-set table table-thin table-bordered table-striped table-hover text-center padding-top-10" style="min-width:900px">
							<thead>
							<tr>
								<th style="width:3%;"><div class="font-size-12 line-height-18" >选择</div><div class="font-size-9 line-height-18" >Choice</div></th>
								<th style="width:8%;">
								 <div class="important">
								   <div class="font-size-12 line-height-18" >文件编号 </div><div class="font-size-9 line-height-18" >File No.</div>
								 </div>
								 </th>
								<th style="width:9%;">
								<div class="important">
								 <div class="font-size-12 line-height-18" >评估单号</div><div class="font-size-9 line-height-18" >Assessment NO.</div>
								 </div>
								</th>
								<th style="width:6%;"><div class="font-size-12 line-height-18" >来源</div><div class="font-size-9 line-height-18" >Source</div></th>
								<th style="width:6%;"><div class="font-size-12 line-height-18" >机型</div><div class="font-size-9 line-height-18" >Model</div></th>
								<th style="width:6%;"><div class="font-size-12 line-height-18" >分类</div><div class="font-size-9 line-height-18" >Category</div></th>
								<th style="width:4%;"><div class="font-size-12 line-height-18" >版本</div><div class="font-size-9 line-height-18" >Revision</div></th>
								<th style="width:26%;">
								 <div class="important">
								  <div class="font-size-12 line-height-18" >文件主题</div><div class="font-size-9 line-height-18" >Subject</div>
								 </div>
								 </th>
								<th style="width:8%;"><div class="font-size-12 line-height-18" >生效日期</div><div class="font-size-9 line-height-18" >Effective Date</div></th>
								<th style="width:8%;"><div class="font-size-12 line-height-18" >机型工程师</div><div class="font-size-9 line-height-18" >Engineer</div></th>
								<th style="width:8%;"><div class="font-size-12 line-height-18" >评估期限</div><div class="font-size-9 line-height-18" >Assess period</div></th>
								<th style="width:8%;"><div class="font-size-12 line-height-18" >评估状态</div><div class="font-size-9 line-height-18" >State</div></th>
							</tr> 
			         		 </thead>
							<tbody id="Pgdlist">
							</tbody>
				        	</table>
						  </div>
						  <div class="col-xs-12 text-center" id="pagination">
								<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" >
								</ul>
							</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="appendPgd()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
									
				                </div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
  </div>
<!-- 	end -->



<!-- 	航材耗材的模态框 -->

<div class="modal fade" id="alertModalHcxx" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:65%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">航材耗材工具列表</div>
							<div class="font-size-9 ">Material Info</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
					       
								   <div  class=" pull-right padding-left-0 padding-right-0 padding-top-10" >
									     <div class="pull-left ">
							            	    <label  class="pull-left  text-right padding-left-0 padding-right-0">
													<div class="font-size-12 line-height-18">类型</div>
													<div class="font-size-9 line-height-18">Type</div>
												</label>
							            	    <div class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
						            	            <select  id="hclx" name="hclx" class="form-control"  onchange="changeHclx()">
							         		            <option value="0,1,4,5" select="select">航材耗材</option>
							         		            <option value="2,3">工具设备</option>
										            </select>
											    </div>  
										   </div>
										
										   <div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;">
										   <input type="text" placeholder="中文名称/英文名称/部件号" id="keyword_search" class="form-control ">
					                       </div>
					                       
					                      <div class="pull-right padding-left-5 padding-right-0">   
											<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
					                   		</button>
					                     </div>
									    
					               </div>
					           
				           <div class="clearfix"></div>	     
								<div  style="overflow-x: auto;margin-top: 10px">
									<table class="table-set table table-thin table-bordered table-striped table-hover " style="min-width:700px">
									<thead>
									<tr>
									<th class="colwidth-3"><div class="font-size-12 line-height-18" >选择</div>
									<div class="font-size-9 line-height-18" >Choice</div></th>
									<th class="colwidth-20">
									<div class="important">
									<div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-16">F.Name</div>
									</div>
									</th>
									<th class="colwidth-13">
									<div class="important">
									<div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-16">CH.Name</div>
									</div>
									</th>
									<th class="colwidth-10">
									<div class="important">
									<div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-16">P/N</div>
									</div>
									</th>
									<th class="colwidth-9"><div class="font-size-12 line-height-18">在库数量</div>
									<div class="font-size-9 line-height-16">Stock Quantity</div></th>
									<th class="colwidth-7"><div class="font-size-12 line-height-18">替代数量</div>
									<div class="font-size-9 line-height-16">Num</div></th>
									<th class="colwidth-5"><div class="font-size-12 line-height-18">计量单位</div>
									<div class="font-size-9 line-height-16">Unit</div></th>
									<th class="colwidth-10"><div class="font-size-12 line-height-18">航材类型</div>
									<div class="font-size-9 line-height-16">Airmaterial type</div></th>
									</tr> 
					         		 </thead>
									<tbody id="Hcxxlist">
									</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center" id="pagination3">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" >
										</ul>
									</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="appendHcxx()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
									
				                </div>
					     		<div class="clearfix"></div>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	<!-- -----航材耗材 End------ -->
	
<!-- 工单的模态宽 模态框 -->
<div class="modal fade" id="alertModalGlgd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:65%">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
				<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">工单信息列表</div>
							<div class="font-size-9 ">List of Info for W/O</div>
						</div>
						
						<div class="panel-body padding-top-0 padding-bottom-0">
						       
				            	<div class=" pull-right padding-left-0 padding-right-0 padding-top-10" >
				            	
				            	 <div class="pull-left">
					            	    <label class="pull-left  text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">工单类型</div>
											<div class="font-size-9 line-height-18">Type</div>
										</label>
					            	    <div class="padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
				            	            <select  id="gddlx2" name="gddlx2" class="form-control"  onchange="changeGddlx()">
					         		            <option value="" select="select">查看全部</option>
					         		            <option value="1" >定检工单</option>
					         		            <option value="2">非例行工单</option>
					         		            <option value="3">EO工单</option>
								            </select>
									    </div>  
								   </div>
				            	
				            	 <div class="pull-left ">
					            	    <label class="pull-left  text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">专业</div>
											<div class="font-size-9 line-height-18">Skill</div>
										</label>
					            	    <div class="padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
				            	            <select id="zy2" class="form-control " name="zy2" onchange="changeZy()">
				             	                <option value="all" select="select">查看全部</option>
				             	               <option value="">无</option>
				            	            </select>
									    </div>  
								   </div>
				            	
								    <div class="pull-left padding-left-0 padding-right-0 " style="width:250px;" >
									  <input type="text" placeholder="编号/主题" id="keyword_search2" class="form-control ">
								    </div>
								    
			                      <div class="pull-right padding-left-5 padding-right-0 ">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision2();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                       </div>
			                    </div>  
				                <div class="clearfix"></div>	     
							<!-- 	start:table -->
								<div  style="margin-top: 10px;overflow-x: auto;">
									<table class="table-set table table-thin table-bordered table-striped table-hover text-center" style="min-width:700px">
										<thead>
										<tr>
										<th width="40"><div class="font-size-12 line-height-18" >选择</div>
										<div class="font-size-9 line-height-18" >Choice</div></th>
										<th width="20%">
										<div class="important">
										<div class="font-size-12 line-height-18">工单编号</div>
										<div class="font-size-9 line-height-16">W/O No.</div>
										</div>
										</th>
										<th width="15%"><div class="font-size-12 line-height-18">工单类型</div>
										<div class="font-size-9 line-height-16">W/O Type</div></th>
										<th><div class="font-size-12 line-height-18">专业</div>
										<div class="font-size-9 line-height-16">Skill</div></th>
										<th width="50%">
										<div class="important">
										<div class="font-size-12 line-height-18">主题</div>
										<div class="font-size-9 line-height-16">Subject</div>
										</div>
										</th>
										</tr> 
						         		 </thead>
										<tbody id="glgdCardlist2">
										</tbody>
									</table>
									</div>
									<div class="col-xs-12 text-center" id="pagination2">
										<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" >
										</ul>
									</div>
							<!-- 	end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="appendGlgd()"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
				                </div>
					     		<div class="clearfix"></div>
					   </div>
				</div>
			</div> 
		</div>
	</div>
</div> 
<!-------工单的模态宽 End-------->
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	 
 <script type="text/javascript" src="${ctx}/static/js/thjw/project/workorder/add_non.js"></script> 
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
<%@ include file="/WEB-INF/views/project/workorder/alertBjh.jsp"%>
<%@ include file="/WEB-INF/views/project/workorder/copy_wo.jsp"%>
<%@ include file="/WEB-INF/views/open_win/work_material_view.jsp"%><!-------替代部件对话框 -------->
</body>
</html>
