<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
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
	<input maxlength="20"  type="hidden" id="engineeringId" value="${engineering.id}"/>
	<input maxlength="20"  type="hidden" id="detailEngineeringId" value="${detailEngineering.id}"/>
 <div class="page-content ">
			<!-- from start -->
	<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
            <input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
            <input type="hidden" id="djgkid" name="djgkid" />
            <input type="hidden" id="gkid" />
            
			<div class="panel-body">
				    <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					<div class="panel panel-default">
				        <div class="panel-heading">
						    <h3 class="panel-title">工单基本信息 Basic Information
						     <a href="javascript:copy();"  >
							 <i id="copy_wo" class='icon-copy color-blue cursor-pointer pull-right' title="复制 Copy"></i>
							 </a>
						    </h3>
					   </div>
					   <input type="hidden" id="dprtcode" name="dprtcode" value="${user.jgdm}" />
					<div class="panel-body">
				       <div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0" >
				       
				           <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0"  >
				           
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">工单类型</div>
									<div class="font-size-9 line-height-18">W/O Type</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group" >	
								     <select  id="gddlx" name="gddlx" class="form-control" disabled="disabled">
								       <option value="3" >EO工单</option>
						             </select>
						         </div> 
							</div>
							
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18">专业</div>
									<div class="font-size-9 line-height-18">Skill</div></label>
								<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group" >
								    <select id="zy" class="form-control " name="zy"></select>
								</div>
							</div>
							
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									    <div class="font-size-12 line-height-18">基地</div>
										<div class="font-size-9 line-height-18">Station</div></label>
								<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8  form-group padding-right-0">
									<select class="form-control " id="jd" name="jd" >
									   <c:forEach items="${baseList}" var="baseList">
									          <option value="${baseList.id}">${erayFns:escapeStr(baseList.jdms)}</option>
									   </c:forEach>
									</select>
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
						
					   </div>	
						<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 " >
						    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">飞机机型</div>
									<div class="font-size-9 line-height-18">Model</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
									<select class="form-control " id="fjjx" name="fjjx" onchange="changeJx()">
									</select>
							   </div>
						   </div>
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							    <label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							       <div class="font-size-12 line-height-18">
							          <span style="color: red">*</span>工程指令编号
							       </div>
								   <div class="font-size-9 line-height-18">EO No.</div>
								 </label>  
								 <div class=" col-xs-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group">
									<div class='input-group'>
										 <input type="text"  class="form-control" name="gczlbh" id="gczlbh" value="${erayFns:escapeStr(engineering.gczlbh)}" disabled="disabled"/>
									     <input type="hidden"  name="gczlid" id="gczlid" value="${engineering.id}"/>
									     <input type="hidden"  name="gczlzxdxid" id="gczlzxdxid" value="${detailEngineering.id}"/>
											<span class='input-group-btn'>
											<a href="#" onclick="openGczl()" data-toggle="modal"
											class="btn btn-primary padding-top-4 padding-bottom-4" > <i class="icon-search cursor-pointer"></i>
											</a></span>
								    </div>
								</div>
							</div>
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</label>
								<div class=" col-xs-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group">
								<div class='input-group'>
										<input type="text"  id="zjhandname"  value="${erayFns:escapeStr(engineering.zjh)}" class="form-control" disabled="disabled"/>
							           <input type="hidden"  id="zjh"  value="${erayFns:escapeStr(engineering.zjh)}" class="form-control" />
										<span class='input-group-btn'>
										<a href="#" onclick="openChapterWin()" data-toggle="modal"
									class="btn btn-primary padding-top-4 padding-bottom-4" > <i class="icon-search cursor-pointer"></i>
								</a></span>
							    </div>
							</div>
							</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">工作站位</div>
										<div class="font-size-9 line-height-18">Location</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group" >
										 <select class="form-control " id="gzzw" name="gzzw">
										 </select>
									</div>
								</div>
						 </div>	
						 	
							<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18"><span style="color: red">*</span>标准工时</div>
									<div class="font-size-9 line-height-18">MHRS</div>
								</label>
								
								<div class=" col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group">
										<input type="text" maxlength="8" class="form-control " 
											id="jhgsRs" value="${detailEngineering.jhgsRs}" onkeyup='clearNoNum(this)'>
										<span class='input-group-addon' style="padding-left:0px;border:0px;background:none;">人 ×</span>
										<input maxlength="6" type="text" onkeyup='clearNoNum(this)' class="form-control" id="jhgsXss" value="${detailEngineering.jhgsXss}" sonkeyup='onkeyup4Num(this)'>
										<span class='input-group-addon' style="padding-left:0px;padding-right:0px;border:0px;background:none;">时 ＝</span>
										<input maxlength="8" type="text" class="form-control "  id="time" readOnly="true">
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
				    <form id="form">
						<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0">
								<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
									<label  class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red">*</span>工单主题</div>
										<div class="font-size-9 line-height-18">Subject</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-8  padding-left-8  form-group padding-right-0">
									    <textarea class="form-control" id="zhut" name="zhut" placeholder='长度最大为600' maxlength="600" rows="2">${erayFns:escapeStr(engineering.zhut)}</textarea>
									</div>
								</div>
								
								<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
									<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
										<div class="font-size-12 line-height-18">下发工单原因</div>
										<div class="font-size-9 line-height-18">Reason for W/O</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group">
										<textarea class="form-control" id="xfgdyy" name="xfgdyy" rows="2" maxlength="300" placeholder='最大长度不超过300'></textarea>
									</div>
								</div>
						  </div>
					   	   <div class=" col-lg-12 col-sm-12 col-xs-12  padding-right-0" >
							    <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></label>
									   <div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
											<textarea class="form-control" id="bz" name="bz" maxlength="300"  placeholder='最大长度不超过300'></textarea>
									  </div>
								</div>
							</div>
			        	</form>	
				</div>		
			</div>
			
			<div class="panel panel-default">
					        <div class="panel-heading">
								    <h3 class="panel-title"><span style="color: red">*</span>执行对象  Execute Object</h3>
						   </div>
			  <div class="panel-body">
			        <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0"  style="margin-top: 10px;overflow-x:auto ;">
				        <table class="table table-thin table-bordered table-striped table-hover text-center" style=" min-width: 1700px !important;">
							<thead>
							<tr>
							 <th rowspan="2" width="4%" style=" vertical-align:middle">
							 <a href="javascript:openZxdx();"  >
								<button  class="line6"><i class="icon-search cursor-pointer" ></i></button>
							</a>
							</th>
							<th rowspan="2" width="8%" style=" vertical-align:middle">
							        <div class="font-size-12 line-height-18"  >执行分类</div>
							         <div class="font-size-9 line-height-18" >Category</div>
					         </th>
							<th rowspan="2" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >飞机注册号</div>
								<div class="font-size-9 line-height-18" >A/C REG</div>
							</th>
							<th  colspan="3" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >飞机</div>
								<div class="font-size-9 line-height-18" >Plane</div>
							</th>
							<th rowspan="2" width="12%" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >部件名称</div>
								<div class="font-size-9 line-height-18" >Name</div>
							</th>
							<th rowspan="2" width="10%" style=" vertical-align:middle">
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
								<th width="11%" style=" vertical-align:middle">
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
			         		 <tr id="zxdxDiv">
			         		     <td></td>
			         		    <td>
			         		       <input type="text" class="form-control" name="zxflname" id="zxflname" disabled="disabled">
			         		       <input type="hidden" id="zxfl" name="zxfl" value="${detailEngineering.zxfl}">
			         		       <input type="hidden" id="zjqdid" name="zjqdid" value="${detailEngineering.zjqdid}">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" value="${erayFns:escapeStr(detailEngineering.fjzch)}" name="fjzch" id="fjzch" disabled="disabled">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" id="fjxlh" disabled="disabled" onmouseover="this.title=this.value">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control"  id="zfxh" disabled="disabled" onmouseover="this.title=this.value">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" id="yfxh" disabled="disabled" onmouseover="this.title=this.value">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" value="${erayFns:escapeStr(detailEngineering.bjms)}" name="bjmc" readonly onmouseover="this.title=this.value" id="bjmc" >
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" value="${erayFns:escapeStr(detailEngineering.bjh)}"  name="bjh" id="bjh" disabled="disabled">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" value="${erayFns:escapeStr(detailEngineering.bjxlh)}"  name="bjxlh" id="bjxlh" disabled="disabled">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" name="wcrq" id="wcrq" disabled="disabled">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" name="jssj" id="jssj" disabled="disabled">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" name="qlxh" id="qlxh" disabled="disabled">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" name="jcsj" id="jcsj" disabled="disabled">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" name="jcxh" id="jcxh" disabled="disabled">
					         		 
					         		 <input type="hidden" id="zxdxid" value="${detailEngineering.id}"/>
									 <input type="hidden" id="old_jkxmbhF" value="${detailEngineering.jkxmbhF}"/>
									 <input type="hidden" id="old_jkflbhF" value="${detailEngineering.jkflbhF}"/>
									 <input type="hidden" id="old_jkzF" value="${detailEngineering.jkzF}"/>
									 <input type="hidden" id="old_jkxmbhS" value="${detailEngineering.jkxmbhS}"/>
									 <input type="hidden" id="old_jkflbhS" value="${detailEngineering.jkflbhS}"/>
									 <input type="hidden" id="old_jkzS" value="${detailEngineering.jkzS}"/>
									 <input type="hidden" id="old_jkxmbhT" value="${detailEngineering.jkxmbhT}"/>
									 <input type="hidden" id="old_jkflbhT" value="${detailEngineering.jkflbhT}"/>
									 <input type="hidden" id="old_jkzT" value="${detailEngineering.jkzT}"/>
			         		    </td>
			         		 </tr>
				   </table>
			 </div>		
		</div>
	</div>	   
	
	  <div class="panel panel-default">
				        <div class="panel-heading">
						 <h3 class="panel-title">工作内容  Contents</h3>
					    </div>
					<div class="panel-body">	 	
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-2 padding-right-0" style="overflow-x: auto;">
						<table class="table table-thin table-bordered table-striped table-hover text-center" id="addtr" style="min-width:900px">
							<thead>
								<tr>
								   <th style="width: 4%;">
								         <button id="addTable"  class="line6"  onclick="addTr()">
									     <i class="icon-plus cursor-pointer color-blue cursor-pointer'" ></i>
								        </button>
		 						   </th>	
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
						 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0"  id="fjgdModel" style="overflow-x: auto;">
								<table class="table-set table table-thin table-bordered table-striped table-hover text-center" id="relatedWorkOrder" style="min-width:1100px">
									<thead><tr>
										<th width="4%">
											<a href="javascript:openGlgd();"  >
												<button  class="line6"><i class="icon-search cursor-pointer" ></i></button>
											</a>
										</th>
										<th width="4%"><div class="font-size-12 line-height-18" >序号</div><div class="font-size-9 line-height-18" >No.</div></th>
										<th><div class="font-size-12 line-height-18" >工单编号</div><div class="font-size-9 line-height-18" >W/O No.</div></th>
										<th><div class="font-size-12 line-height-18" >工单类型</div><div class="font-size-9 line-height-18" >Work Order Type</div></th>
										<th><div class="font-size-12 line-height-18" >专业</div><div class="font-size-9 line-height-18" >Skill</div></th>
										<th width="40%"><div class="font-size-12 line-height-18" >主题</div><div class="font-size-9 line-height-18" >Subject</div></th>
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
						  <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-2 padding-right-0"  style="overflow-x: auto;">
								<table class="table-set table table-thin table-bordered table-striped table-hover text-center" id="airMaterialTools" style="min-width:1100px">
									<thead>
										<tr>
											<th width="4%">
											<a href="javascript:openHcxxList();"  >
												<button  class="line6"><i class="icon-search cursor-pointer" ></i></button>
											</a>
											</th>
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
						<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">文件说明</div>
							<div class="font-size-9 line-height-18">File Description</div>
						</label>
						<div class=" col-xs-8 col-sm-7 col-xs-8 padding-left-8 padding-right-0 input-group form-group">
							<div class='input-group'>
									<input type="text" id="wbwjm" name="wbwjm" maxlength="100" class="form-control "  >
									<span class='input-group-btn'>
									<div id="fileuploader" ></div>
									</span>
						    </div>
						</div>
					</div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
						<table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
							<thead>
								<tr>
									<th class="colwidth-3"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
									<th class="colwidth-20">
									<div class="font-size-12 line-height-18">文件说明</div>
										<div class="font-size-9 line-height-18">File Description</div>
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
			
				<div style="display: none"><input maxlength="20"  id="planedataId"  type="text"  name="menuId"/> </div>
			   <div class="clearfix"></div>
			   
				
				<div class="col-lg-12 text-right padding-left-0 padding-right-0" style="margin-bottom: 10px">
                        <button href="javascript:void()" data-toggle="modal"  class="btn btn-primary padding-top-1 padding-bottom-1 "
					    onclick="woSave()"  ><div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
						</button>
						  <button href="javascript:void()" data-toggle="modal"  class="btn btn-primary padding-top-1 padding-bottom-1 "
						onclick="submitSave()"   >
						<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
						</button>
							<button class="btn btn-primary padding-top-1 padding-bottom-1"  onclick="back()">
                     	<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
						</button>
                    </div>
                    
			  <div class="clearfix"></div>
			</div>
		</div>
</div>
<!-- 	航材耗材的模态框 -->

	<div class="modal fade" id="alertModalHcxx" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 65%">
			<div class="modal-content" >
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
				<div class="panel panel-primary">
							<div class="panel-heading  padding-top-3 padding-bottom-1">
								<div class="font-size-16 line-height-18">航材耗材工具列表</div>
								<div class="font-size-9 ">Air Material Supplies</div>
							</div>
							<div class="panel-body padding-top-0 padding-bottom-0">
							
								<div class=" pull-right padding-left-0 padding-right-0 padding-top-10">
									
									<div class="pull-left ">
										<label class="pull-left  text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">类型</div>
											<div class="font-size-9 line-height-18">Type</div>
										</label>
										<div  class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
											<select id="hclx" name="hclx" class="form-control"
												onchange="changeLx()">
												<option value="0,1,4,5" selected="selected">航材耗材</option>
												<option value="2,3">工具设备</option>
											</select>
										</div>
									</div>
									
										<div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;">
											<input type="text" placeholder="中文名称/英文名称/部件号"
												id="keyword_search" class="form-control ">
										</div>
										
										<div class=" pull-right padding-left-5 padding-right-0 ">
											<button type="button"
												class=" btn btn-primary padding-top-1 padding-bottom-1"
												onclick="searchRevision();">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
											</button>
										</div>
								</div>

								<div class="clearfix"></div>

								<!-- start:table -->
								<div style="overflow-x: auto;margin-top: 10px">
									<table
										class="table-set table table-thin table-bordered table-striped table-hover " style="min-width:700px">
										<thead>
											<tr>
												<th class="colwidth-3"><div class="font-size-12 line-height-18">选择</div>
													<div class="font-size-9 line-height-18">Choice</div></th>
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
													<div class="font-size-9 line-height-16">Stock
														Quantity</div></th>
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
									<ul class="pagination "
										style="margin-top: 0px; margin-bottom: 0px;">
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
									<button type="button"
										class="btn btn-primary padding-top-1 padding-bottom-1"
										data-dismiss="modal">
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
	</div>
	<!-------航材耗材 End-------->
	
	<!-- 	工单的模态宽 模态框 -->
	<div class="modal fade" id="alertModalGlgd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:65%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">工单信息列表</div>
							<div class="font-size-9 ">List Of Work Order</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
						       
				            	<div class=" pull-right padding-left-0 padding-right-0 padding-top-10" >
				            	
				            		<div class="pull-left ">
					            	    <label class="pull-left  text-right padding-left-0 padding-right-0">
											<div class="font-size-12 line-height-18">工单类型</div>
											<div class="font-size-9 line-height-18">Type</div>
										</label>
					            	    <div class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
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
										
					            	    <div class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
				            	            <select id="zy2" class="form-control " name="zy2" onchange="changeZy()">
				             	                <option value="all" select="select">查看全部</option>
				             	               <option value="">无</option>
				            	            </select>
									    </div>  
								   </div>
				            	
								    <div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;" >
									  <input type="text" placeholder="编号/主题" id="keyword_search2" class="form-control ">
								    </div>
								    
			                      <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision2();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                       </div>
			                       
			                  </div> 
				                <div class="clearfix"></div>	
								<!-- start:table -->
								<div  style="overflow-x: auto;margin-top: 10px">
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
												<div class="font-size-9 line-height-16">Work Order Type</div></th>
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
								<!-- end:table -->
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
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	<!-------工单的模态宽 End-------->
	
<!-------工单的工程指令 End-------->
<div class="modal fade" id="alertModalGczl" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog" style="width: 70%">
	<div class="modal-content">
		<div class="modal-body padding-bottom-0" id="alertModalUserBody">
							  	<div class="panel panel-primary">
				<div class="panel-heading  padding-top-3 padding-bottom-1">
					<div class="font-size-16 line-height-18">工程指令列表</div>
					<div class="font-size-9 ">List of Engineering Order</div>
				</div>
				<div class="panel-body padding-top-0 padding-bottom-0">
				       <div class=" pull-right padding-left-0 padding-right-0 padding-top-10">
				       
						    <div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;">
							<input type="text" placeholder="工程指令编号" id="keyword_search3" class="form-control ">
						    </div>
						    
		                    <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision3();">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                   		</button>
		                     </div>
			          </div>
			            
		           <div class="clearfix"></div>	     
		                
						<!-- start:table -->
				<div  style="overflow-x: auto;margin-top: 10px">
					<table class="table-set table table-thin table-bordered table-striped table-hover " style="min-width:900px">
						<thead>
						<tr>
						<th style="width:5%;">
						   <div class="font-size-12 line-height-18" >选择</div>
						   <div class="font-size-9 line-height-18" >Choice</div>
					     </th>
						<th style="width:12%;"><div class="important">
							<div class="font-size-12 line-height-18">工程指令编号</div>
							<div class="font-size-9 line-height-16">EO No.</div>
						</div></th>
						<th style="width:20%;"><div class="font-size-12 line-height-18">参考资料</div>
						<div class="font-size-9 line-height-16">Reference Material</div></th>
						<th style="width:12%;"><div class="font-size-12 line-height-18">ATA章节号</div>
						<div class="font-size-9 line-height-16">ATA</div></th>
						<th style="width:14%;"><div class="font-size-12 line-height-18">相关改装号</div>
						<div class="font-size-9 line-height-16">MOD No.</div></th>
						<th style="width:30%;"><div class="font-size-12 line-height-18">主题</div>
						<div class="font-size-9 line-height-16">Subject</div></th>
						<th style="width:7%;"><div class="font-size-12 line-height-18">版本</div>
						<div class="font-size-9 line-height-16">Revision</div></th>
						</tr> 
		         		 </thead>
						<tbody id="Gczlbody">
						</tbody>
					</table>
					</div>
					<div class="col-xs-12 text-center" id="pagination4">
						<ul class="pagination " style="margin-top: 0px; margin-bottom: 0px;" >
						</ul>
					</div>
				<!-- end:table -->
               	<div class="text-right margin-top-10 margin-bottom-10">
					<button type="button" onclick="appendGczl()"
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
	<!-------工单的工程指令 End-------->		
	
	<!-------执行对象模态框 End-------->
		<div class="modal fade" id="alertModalZxdx" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">执行对象列表</div>
							<div class="font-size-9 ">List Of Execute Object</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
				                
								<!-- start:table -->
								<div  style="margin-top: 20px">
									<table class="table-set table table-thin table-bordered table-striped table-hover text-center">
												<thead>
												<tr>
												<th width="40"><div class="font-size-12 line-height-18" >选择</div>
												<div class="font-size-9 line-height-18" >Choice</div></th>
													<th><div class="font-size-12 line-height-18">执行分类</div>
													<div class="font-size-9 line-height-16">Category</div></th>
													<th><div class="font-size-12 line-height-18">飞机注册号</div>
													<div class="font-size-9 line-height-16">A/C REG</div></th>
													<th><div class="font-size-12 line-height-18">部件号</div>
													<div class="font-size-9 line-height-16">P/N</div></th>
													<th><div class="font-size-12 line-height-18">部件序列号</div>
													<div class="font-size-9 line-height-16">S/N</div></th>
												</tr> 
								         		 </thead>
												<tbody id="Zxdxbody">
												</tbody>
									</table>
									</div>
								<!-- end:table -->
			                	<div class="text-right margin-top-10 margin-bottom-10">
									<button type="button" onclick="appendZxdx()"
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

	<script src="${ctx}/static/js/workdetail.js"></script>
 <script type="text/javascript" src="${ctx}/static/js/thjw/project/workorder/add_eo.js"></script> 
 <%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
 <%@ include file="/WEB-INF/views/project/workorder/copy_wo.jsp"%>
 <%@ include file="/WEB-INF/views/open_win/work_material_view.jsp"%><!-------替代部件对话框 -------->
</body>
</html>
