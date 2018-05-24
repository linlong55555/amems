<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<script>
	var pageParam = '${param.pageParam}';
	</script>	
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
<div id="jjjj">
<div class="page-content ">
			<!-- from start -->
	<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			    <input type="hidden" id="zt"value="${woResult.zt}">                   <!--  储存工单的状态判断可修改内容 -->
			    <input type="hidden" id="id" value="${woResult.id}">                   <!--  储存工单的id判断可修改内容 -->
			    <input type="hidden" id="gdjcid" value="${woResult.gdjcid}">                   <!--  储存工单的id判断可修改内容 -->
				<input type="hidden" id="dprtcode_parma" value="${woResult.dprtcode}" class="form-control " readonly />
				<input type="hidden" id="zzjgid" name="zzjgid" value="${woResult.dprtcode}" />
				<input type="hidden" id="gkid" value="${woResult.djgkid}"/>
				 <!--    基本信息 start -->
				<div class="panel panel-default">
				        <div class="panel-heading">
							    <h3 class="panel-title">工单基本信息 Basic Information</h3>
					   </div>
				 <div class="panel-body">	 	
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">工单类型</div>
								<div class="font-size-9 line-height-18">W/O Type</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
								<select id="gddlx" class="form-control" name="gddlx" disabled="disabled">
									<c:forEach items="${maxWorkOrderTypeEnum}" var="item">
									  <option value="${item.id}" <c:if test="${woResult.gddlx == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
								 </select>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 "  id="demo1">
							
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">工程指令编号</div>
								<div class="font-size-9 line-height-18">EO No.</div>
							</label>
							<div class=" col-xs-8  padding-left-8 padding-right-0 input-group form-group">
									<div class='input-group'>
									    <input type="text" id="gczlbh" name="gczlbh" value="${erayFns:escapeStr(woResult.gczlbh)}" class="form-control " readonly />
									    <input type="hidden"  name="gczlid" id="gczlid" value="${woResult.gczlid}"/>
										<input type="hidden"  name="gczlzxdxid" id="gczlzxdxid" value="${woResult.gczlzxdxid}" />
										<span class='input-group-btn'>
										 <a  href="#"  onclick="openGczl()"  data-toggle="modal"  class="btn btn-primary  " >
											<i class="icon-search cursor-pointer" ></i>
										  </a>
										</span>
								    </div>
							</div>
						 </div>
						 
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">工单编号</div>
								<div class="font-size-9 line-height-18">W/O NO.</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" id="gdbh" name="gdbh" value="${erayFns:escapeStr(woResult.gdbh)}" class="form-control " readonly />
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">专业</div>
								<div class="font-size-9 line-height-18">Skill</div>
							</label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								 <select id="zy" class="form-control " name="zy"></select>
								 <input type="hidden" class="form-control " id="zy-wo" value="${woResult.zy}"/>
							</div>
						</div>
					</div>
						
					 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					      
					      <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">ATA章节号</div>
								<div class="font-size-9 line-height-18">ATA</div>
								</label>
								<div class=" col-xs-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group">
										<div class='input-group'>
												 <input type="text"  id="zjhandname" class="form-control"  value="${erayFns:escapeStr(woResult.zjh)}  ${erayFns:escapeStr(woResult.zwms)}" disabled="disabled"/>
							        <input type="hidden"  id="zjh" value="${erayFns:escapeStr(woResult.zjh)}" class="form-control" />
												<span class='input-group-btn'>
												 <a href="#" onclick="openChapterWin()"  data-toggle="modal"  class="btn btn-primary padding-top-4 padding-bottom-4 pull-left" >
													<i class="icon-search cursor-pointer" ></i>
												  </a>
												</span>
									    </div>
								</div>
					     </div>
						 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							 <label class="col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">工作站位</div>
								<div class="font-size-9 line-height-18">Location</div></label>
							   <div class="col-xs-8  padding-left-8 padding-right-0 form-group">
							        <select class="form-control " id="gzzw" name="gzzw" ></select>
								    <input type="hidden" class="form-control " id="gzzw-wo" value="${woResult.gzzw}"/>
							  </div>
						 </div>
						 
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12 line-height-18">基地</div>
									<div class="font-size-9 line-height-18">Station</div></label>
							<div class="col-xs-8  padding-left-8 padding-right-0 form-group">
							    <select class="form-control " id="jd" name="jd" >
								   <c:forEach items="${baseList}" var="baseList">
								          <option value="${baseList.id}"  <c:if test="${woResult.jd ==baseList.id }">selected="true"</c:if>>${erayFns:escapeStr(baseList.jdms)}</option>
								   </c:forEach>
								</select>
							</div>
						</div>
						
						  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" id="demo3">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0">
									    <div class="font-size-12 line-height-18">飞机机型</div>
										<div class="font-size-9 line-height-18">Model</div></label>
								<div class="col-xs-8  padding-left-8 padding-right-0 form-group">
									<input type="text" name="fjjx" id="fjjx" class="form-control " readonly/>									
								</div>
						 </div>
				
						</div>
						
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							
				 	     <div class="col-lg-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
							<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>标准工时</div>
								<div class="font-size-9 line-height-18">MHRS</div>
							</label>
							<div class=" col-lg-10 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group form-group">
								<input type="text" maxlength="8" class="form-control " 
									id="jhgsRs" value="${woResult.jhgsRs}" onkeyup='clearNoNum(this)'>
								<span class='input-group-addon' style="padding-left:0px;border:0px;background:none;">人 ×</span>
								<input maxlength="6" type="text" onkeyup='clearNoNum(this)' class="form-control" id="jhgsXss" value="${woResult.jhgsXss}" sonkeyup='onkeyup4Num(this)'>
								<span class='input-group-addon' style="padding-left:0px;padding-right:0px;border:0px;background:none;">时 ＝</span>
								<input maxlength="8" type="text" class="form-control "  id="time" readOnly="true">
							</div>
						 </div>
						 
						 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creator</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
									<input type="text" id="zdr" name="zdr" value="${erayFns:escapeStr(woResult.displayname)}" class="form-control " readonly />
								</div>
						  </div>
							
						  <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
									<input type="text" id="zdsj" name="zdsj" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${woResult.zdsj}"/>" data-date-format="yyyy-MM-dd HH:mm:ss" class="form-control " readonly />
								</div>
							</div>
							
					   </div>
					   
					   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					   
					   	<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">工单状态</div>
								<div class="font-size-9 line-height-18">State</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" id="gdzt" value="${woResult.zt}" class="form-control " readonly />
							</div>
						</div>
							
						 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" id="demo6">
							 <label class="col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">工作组</div>
								<div class="font-size-9 line-height-18">Work Group</div></label>
							   <div class="col-xs-8  padding-left-8 padding-right-0 form-group" >
							        <select class="form-control " id="gzz" name="gzz">
									</select>
									<input type="hidden" id="gzzid" value="${woResult.gzzid}">
							  </div>
						 </div>
						 <div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" id="jobcard_demo">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">来源工卡</div>
								<div class="font-size-9 line-height-18">Souce JobCard</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group padding-top-8">
							    <label><a id="view_gkid" href="#" style="font-weight: normal;" onclick="ViewJobCard()">${erayFns:escapeStr(woResult.gkbh)}</a></label>
							    <input type="hidden" id="temp_id" value="${woResult.djgkid}"/>
							    <input type="hidden" id="temp_dprtcode" value="${woResult.dprtcode}"/>
							</div>
						</div>
					   </div>
					   
					   <form id="form">
					   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						 <div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label  class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>工单主题</div>
								<div class="font-size-9 line-height-18">Subject</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-8  padding-left-8  form-group padding-right-0" title="${erayFns:escapeStr(woResult.zhut)}">
							    <textarea class="form-control" id="wjzt" name="wjzt" placeholder='长度最大为600' maxlength="600" rows="2">${erayFns:escapeStr(woResult.zhut)}</textarea>
							</div>
						</div>
						
						<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0" >
								<div class="font-size-12 line-height-18">下发工单原因</div>
								<div class="font-size-9 line-height-18">Reason for W/O</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" title="${erayFns:escapeStr(woResult.xfgdyy)}">
								<textarea class="form-control" id="xfgdyy" name="xfgdyy" rows="2" maxlength="300" placeholder='最大长度不超过300'>${erayFns:escapeStr(woResult.xfgdyy)}</textarea>
							</div>
						</div>
					   </div>	
					   
						<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							   <div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div></label>
							   <div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group" >
									<textarea class="form-control" id="bz" maxlength="300"  placeholder='最大长度不超过300' name="bz">${erayFns:escapeStr(woResult.bz)}</textarea>
							  </div>
						</div>
					</form>
					</div>
			      </div>
					<!-- end -->
					<!-- 执行对象strat -->
					<div class="panel panel-default">
				        <div class="panel-heading">
							 <h3 class="panel-title">执行对象  Execute Object</h3>
					    </div>
					<div class="panel-body">	 	
					<div class="col-lg-12  padding-left-0 padding-right-0" id="zxdxDiv" style="margin-top: 10px;overflow-x: auto ;">
				        <table id="zxdxTb" class="table table-thin table-bordered table-striped table-hover text-center" 	style=" min-width: 1700px !important;">
							<thead>
							<tr>
							<th rowspan="2" width="3%" style=" vertical-align:middle"  id="zxfl_demo1">
							 <a href="javascript:openZxdx();"  >
								<button  class="line6"><i class="icon-search cursor-pointer" ></i></button>
							 </a>
							</th>
							<th rowspan="2" width="150" style=" vertical-align:middle">
							        <div class="font-size-12 line-height-18"  >执行分类</div>
							         <div class="font-size-9 line-height-18" >Category</div>
					         </th>
							<th rowspan="2" width="8%" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >飞机注册号</div>
								<div class="font-size-9 line-height-18" >A/C REG</div>
							</th>
							<th  colspan="3" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >飞机</div>
								<div class="font-size-9 line-height-18" >Plane</div>
							</th>
							<th rowspan="2" width="9%" style=" vertical-align:middle" id="th_bjmc">
								<div class="font-size-12 line-height-18" >部件名称</div>
								<div class="font-size-9 line-height-18" >Name</div>
							</th>
							<th rowspan="2" width="9%" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >部件件号</div>
								<div class="font-size-9 line-height-18" >P/N</div>
							</th>
							<th rowspan="2" style=" vertical-align:middle">
								<div class="font-size-12 line-height-18" >部件序列号</div>
								<div class="font-size-9 line-height-18" >S/N</div>
							</th>
							<th id="div_1" colspan="5"  style=" vertical-align:middle">
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
								<th width="8%" style=" vertical-align:middle">
								       <div id="value_1" class="font-size-12 line-height-18" >完成日期</div>
								       <div id="value_4" class="font-size-9 line-height-18" >Finish Date</div>
						      	</th>
								<th id="th_6" width="8%" style=" vertical-align:middle">
										<div id="value_2" class="font-size-12 line-height-18" >机身时间</div>
										<div id="value_5" class="font-size-9 line-height-18" >A/C HRS.</div>
								</th>
								<th id="th_5" width="8%" style=" vertical-align:middle">
										<div id="value_3" class="font-size-12 line-height-18" >起落循环</div>
										<div id="value_6" class="font-size-9 line-height-18" >A/C CYCS.</div>
								</th>
								<th id="th_1" width="6%" style=" vertical-align:middle">
										<div class="font-size-12 line-height-18" >绞车时间</div>
										<div class="font-size-9 line-height-18" >Winch Time</div>
								</th>
								<th id="th_2" width="6%" style=" vertical-align:middle">
										<div class="font-size-12 line-height-18" >绞车循环</div>
										<div class="font-size-9 line-height-18" >Winch CYCS.</div>
								</th>
							</tr>
			         		 </thead>
							<tr   id="zxdxtr">
							    <td id="zxfl_demo2"></td>
			         		     <td width="150" >
			         		          <input  id="zxfl" name="zxfl" class="form-control"  disabled="disabled" />
								      <input type="hidden" class="form-control " id="zxfl-wo" value="${wOActionObj.zxfl}" />
			         		     </td>
			         		    <td>
			         		       <select  id="fjzch" name="fjzch" class="form-control" >
			         		         <option value="${erayFns:escapeStr(wOActionObj.fjzch)}" style="display: none;">${erayFns:escapeStr(wOActionObj.fjzch)}</option>
						           </select>
						           <input type="hidden" id="zxdxfjzch" value="${erayFns:escapeStr(wOActionObj.fjzch)}"/>
			         		    </td>
			         		     <td>
			         		       <input type="text" class="form-control" readonly id="fjxlh" onmouseover="this.title=this.value">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" readonly id="zfxh" onmouseover="this.title=this.value">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" readonly id="yfxh" onmouseover="this.title=this.value">
			         		    </td>
			         		    <td id="td_bjmc">
			         		       <input type="text" class="form-control" name="bjmc" readonly id="bjmc" onmouseover="this.title=this.value" value="${erayFns:escapeStr(wOActionObj.bjName)}" >
			         		    </td>
			         		    <td width="10%">
			         		         <select  id="bjh" name="bjh" class="form-control">
						                <option value="${wOActionObj.bjh}" style="display: none;">${erayFns:escapeStr(wOActionObj.bjh)}</option>
						             </select>
			         		         <input type="hidden"  id="zjqdid" name="zjqdid" value="${wOActionObj.zjqdid}"/>
			         		         <input type="hidden"  id="zjqdid_temp" value="${wOActionObj.zjqdid}"/>
			         		         <input type="hidden" id="zxdxbjh" value="${erayFns:escapeStr(wOActionObj.bjh)}"/>
			         		    </td>
			         		    <td width="10%">
			         		         <select  id="bjxlh" name="bjxlh" class="form-control" onchange="changeBjxlh()">
			         		             <option value="${erayFns:escapeStr(wOActionObj.bjxlh)}" style="display: none;">${erayFns:escapeStr(wOActionObj.bjxlh)}</option>  
						             </select>
						                <input type="hidden" id="zxdxbjxlh" value="${erayFns:escapeStr(wOActionObj.bjxlh)}"/>
			         		    </td>
			         		    <td>
			         		       <input type="text" class='form-control datepicker' name="wcrq" id="wcrq" data-date-format="yyyy-mm-dd"/>
			         		    </td>
			         		    <td id="th_8">
			         		       <input type="text" class="form-control" name="jssj" id="jssj"  onmouseout='judge()' onkeyup='yanzhen(this,1)'>
			         		    </td>
			         		    <td id="th_7">
			         		       <input type="text" class="form-control" name="qlxh" id="qlxh" onmouseout='judge()'  onkeyup='yanzhen(this,2)' >
			         		    </td>
			         		    <td id="th_3">
			         		       <input type="text" class="form-control" name="jcsj" id="jcsj"  onmouseout='judge()'  onkeyup='yanzhen(this,1)'>
			         		    </td>
			         		    <td id="th_4">
			         		       <input type="text" class="form-control" name="jcxh" id="jcxh"onmouseout='judge()'  onkeyup='yanzhen(this,2)'>
			         		    </td>
			         		 </tr>
				   			</table>
							 <input type="hidden" id="zxdxid" value="${wOActionObj.id}"/>
							 <input type="hidden" id="old_jkxmbhF" value="${wOActionObj.jkxmbhF}"/>
							 <input type="hidden" id="old_jkflbhF" value="${wOActionObj.jkflbhF}"/>
							 <input type="hidden" id="old_jkzF" value="${wOActionObj.jkzF}"/>
							 <input type="hidden" id="old_jkxmbhS" value="${wOActionObj.jkxmbhS}"/>
							 <input type="hidden" id="old_jkflbhS" value="${wOActionObj.jkflbhS}"/>
							 <input type="hidden" id="old_jkzS" value="${wOActionObj.jkzS}"/>
							 <input type="hidden" id="old_jkxmbhT" value="${wOActionObj.jkxmbhT}"/>
							 <input type="hidden" id="old_jkflbhT" value="${wOActionObj.jkflbhT}"/>
							 <input type="hidden" id="old_jkzT" value="${wOActionObj.jkzT}"/>
						 </div>
					</div>
			      </div>	
				 <!-- end -->
				 
				   <!-- 工作内容start -->
			     <div class="panel panel-default">
				        <div class="panel-heading">
							    <h3 class="panel-title">工作内容  Contents</h3>
					   </div>
					<div class="panel-body">	 	
					  <div class="col-lg-12  padding-left-2 padding-right-0" style="overflow-x: auto;">
					      <table class="table table-thin table-bordered table-striped table-hover text-center" id="addtr" style="min-width:900px">
							<thead>
								<tr>
									<td style="width: 40px;" name="hideTd">
							         <button id="addTable"  class="line6" onclick="addTr()">
								     <i class="icon-plus cursor-pointer color-blue cursor-pointer'" ></i>
							        </button>
	 							   </td>
	 							    <th width="4%"><div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div></th>
									<th><div class="font-size-12 line-height-18">工作内容</div>
									<div class="font-size-9 line-height-16">Contents</div></th>
									<th width="10%"><div class="font-size-12 line-height-18">工作人</div>
									<div class="font-size-9 line-height-16">Worker</div></th>
									<th width="8%"><div class="font-size-12 line-height-18">是否必检</div>
									<div class="font-size-9 line-height-16">Is Check</div></th>
								</tr>
							</thead>     
							<tbody id="list">
							<c:forEach items="${wOJobContent}" var="wOJobContent">
								<tr>
								      <td name="hideTd" style='vertical-align:middle'>
								        <button  class="line6" onclick="lineRemoveGznr(this)">
								        <i class='icon-minus color-blue cursor-pointer'></i>
								        </button>
								        </td>
								       <td style='vertical-align:middle' name='workId'></td>
							          <td style='vertical-align:middle'>
							          <input type="hidden" name="gznrid" value="${wOJobContent.id}">
							          <textarea type="text" rows="2" name="gznr" id="gznr" maxlength="300" class="form-control" >${erayFns:escapeStr(wOJobContent.gznr)}</textarea></td>
							          <td style='vertical-align:middle'><input type="text" name="gzz" id="gzz" class="form-control" maxlength="6" value="${erayFns:escapeStr(wOJobContent.gzz)}"></td>
							          <td style='vertical-align:middle'>
							          <input type="checkbox" style='width: 20px;height: 20px;'  name="isBj"  id="isBj" value="${wOJobContent.isBj}" 
							          <c:if test="${wOJobContent.isBj == '1'}">checked="checked"</c:if> > 
							          </td>
								</tr>
							</c:forEach>
							</tbody>	
						</table>
					  </div>
					</div>
				 </div>
				<!--  end -->
				  <!-- 相关工单strat -->
				 <div class="panel panel-default">
				        <div class="panel-heading">
							    <h3 class="panel-title">关联相关工单 Related Work Order</h3>
					   </div>
					<div class="panel-body">	 	
						<div class="col-lg-12  padding-left-2 padding-right-0" style="overflow-x: auto;">
							<table class="table-set table table-thin table-bordered table-striped table-hover text-center" id="relatedWorkOrder" style="min-width:900px">
									<thead>
										<tr>
											<td class="colwidth-3" name="hideTd">
											<a href="javascript:openGlgd();"  >
												<button  class="line6"><i class="icon-search cursor-pointer" ></i></button>
											</a>
										   </td>
										    <th class="colwidth-3"><div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-16">No.</div></th>
											<th class="colwidth-13"><div class="font-size-12 line-height-18">工单编号</div>
											<div class="font-size-9 line-height-16">W/O No.</div></th>
											<th class="colwidth-10"><div class="font-size-12 line-height-18">工单类型</div>
											<div class="font-size-9 line-height-16">Work Order Type</div></th>
											<th class="colwidth-7"><div class="font-size-12 line-height-18">专业</div>
											<div class="font-size-9 line-height-16">Skill</div></th>
											<th class="colwidth-25"><div class="font-size-12 line-height-18">主题</div>
											<div class="font-size-9 line-height-16">Subject</div></th>
										</tr>
									</thead>     
									<tbody id="glgdCardList">
									<c:forEach items="${nonWOCardd}" var="nonWOCardd">
										<tr>
										        <td name="hideTd" style='vertical-align:middle'>
										        <button class="line6" onclick="lineRemoveXggd(this)">
										        <i class='icon-minus color-blue cursor-pointer'></i>
										        </button>
										        </td>
										      <td class='text-center' name='glgdgknum' style='vertical-align:middle'></td>
									          <td class='text-left' name="gdbh1" style='vertical-align:middle'>
									          <input type="hidden" name="xggdid" value="${nonWOCardd.xggdid}"/>
									           <input type="hidden" name="xggdbh" value="${erayFns:escapeStr(nonWOCardd.gdbh)}"/>
									           <input type="hidden" name="xggdlx" value="${nonWOCardd.xggdLx}"/>
									            <input type="hidden" name="xggdzlx" value="${nonWOCardd.xggdZlx}"/>
									          <input type="hidden" name="xggdjcid" value="${nonWOCardd.xgjcgdid}"/>
									           <input type="hidden" name="id" value="${nonWOCardd.id}"/>
									          <a href="${ctx}/project/workorder/Looked?id=${nonWOCardd.xggdid}&gddlx=${nonWOCardd.xggdLx}" target="_blank">${erayFns:escapeStr(nonWOCardd.gdbh)}</a> 
									          </td>
									          <td class='text-left'  id="xggdlx" style='vertical-align:middle'>
									            <c:if test="${nonWOCardd.xggdLx == '1'}"><c:out value="定检工单" /></c:if> 
									            <c:if test="${nonWOCardd.xggdLx  == '2'}"><c:out value="非例行工单-" /></c:if>
									           <c:if test="${nonWOCardd.xggdLx  == '3'}"><c:out value="EO工单" /></c:if>
									           <c:if test="${nonWOCardd.xggdZlx == '1'}"><c:out value="时控件工单" /></c:if> 
									            <c:if test="${nonWOCardd.xggdZlx  == '2'}"><c:out value="附加工单" /></c:if>
									           <c:if test="${nonWOCardd.xggdZlx  == '3'}"><c:out value="排故工单" /></c:if>
									          </td> 
									          <td class='text-center' style='vertical-align:middle'><c:out value="${nonWOCardd.zy}" /></td> 
									          <td class='text-left' style='vertical-align:middle'title="${erayFns:escapeStr(nonWOCardd.zhut)}"><c:out value="${nonWOCardd.zhut}" /></td> 
										</tr>
									</c:forEach>
									</tbody>	
								</table>
						</div>
					</div>
				 </div>
				<!-- end -->
				<!-- 航材耗材start -->
				<div class="panel panel-default">
				        <div class="panel-heading">
						<h3 class="panel-title">航材耗材工具信息  Air Material Tools</h3>
					   </div>
					<div class="panel-body">	 	
					  <div class="col-lg-12  padding-left-2 padding-right-0" style="overflow-x: auto;">
						<table class="table-set table table-thin table-bordered table-striped table-hover text-center" id="airMaterialTools" style="min-width:1300px" >
									<thead>
										<tr>
											<td class="colwidth-3" name="hideTd">
											<a href="javascript:openHcxxList();"  >
												<button  class="line6"><i class="icon-search cursor-pointer" ></i></button>
											</a>
											</td>
											<th class="colwidth-3"><div class="font-size-12 line-height-18">序号</div>
											<div class="font-size-9 line-height-16">No.</div></th>
											<th class="colwidth-13"><div class="font-size-12 line-height-18">件号来源</div>
											<div class="font-size-9 line-height-16">Resouce</div></th>
											<th class="colwidth-13"><div class="font-size-12 line-height-18">英文名称</div>
											<div class="font-size-9 line-height-16">F.Name</div></th>
											<th class="colwidth-13"><div class="font-size-12 line-height-18">中文名称</div>
											<div class="font-size-9 line-height-16">CH.Name</div></th>
											<th class="colwidth-13"><div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-16">P/N</div></th>
											<th class="colwidth-7"><div class="font-size-12 line-height-18">在库数量</div>
											<div class="font-size-9 line-height-16">Stock Quantity</div></th>
											<th width="9%"><div class="font-size-12 line-height-18">替代数量</div>
											<div class="font-size-9 line-height-16">Num</div></th>
											<th class="colwidth-7"><div class="font-size-12 line-height-18">使用数量</div>
											<div class="font-size-9 line-height-16">Use Quantity</div></th>
											<th class="colwidth-5"><div class="font-size-12 line-height-18">类型</div>
											<div class="font-size-9 line-height-16">Type</div></th>
											<th class="colwidth-20"><div class="font-size-12 line-height-18">备注</div>
											<div class="font-size-9 line-height-16">Remark</div></th>
										</tr>
									</thead>     
									<tbody id="CKlist">
									<c:forEach items="${woAirMaterial}" var="woAirMaterial">
										<tr>
									      <td name="hideTd">
										      <button  class="line6" onclick="lineRemoveHc(this)">
										     	 <i class='icon-minus color-blue cursor-pointer'></i>
										      </button>
									      </td>
									      <td style='vertical-align:middle' name='hcnum'></td>
									      <td class='text-center' style='vertical-align:middle'>
								          <input type="text" name="refJhly_1"  maxlength="100" class="form-control" value="${erayFns:escapeStr(woAirMaterial.refJhly)}"></td>
								          <td class='text-center' name="bjid1" style="display: none">
								          <input type="hidden" name="bjid"  value="${woAirMaterial.bjid}">
								          <input type="hidden" name="wohcid"  value="${woAirMaterial.id}">
								          <c:out value="${woAirMaterial.bjid}" /></td>
								          <td class='text-left' style='vertical-align:middle' title='${erayFns:escapeStr(woAirMaterial.ywmc)}'>
								           <input type="hidden" name="ywms_1" value="${erayFns:escapeStr(woAirMaterial.ywmc)}">
								          <c:out value="${woAirMaterial.ywmc}" /></td>
								          <td class='text-left' style='vertical-align:middle' title='${erayFns:escapeStr(woAirMaterial.zwmc)}'>
								           <input type="hidden" name="zwms_1" value="${erayFns:escapeStr(woAirMaterial.zwmc)}">
								          <c:out value="${woAirMaterial.zwmc}" /></td>
								          <td class='text-left' style='vertical-align:middle' title='${erayFns:escapeStr(woAirMaterial.jh)}'>
								           <input type="hidden" name="bjh_1" value="${erayFns:escapeStr(woAirMaterial.jh)}">
								          <c:out value="${woAirMaterial.jh}" /></td>

								          <td class='text-right' style='vertical-align:middle'>
							                 <c:out value="${woAirMaterial.kykcsl ==null ? 0 :woAirMaterial.kykcsl}" />
									      </td> 
								          
										  <td class='text-right' style='vertical-align:middle'>
								          	 <c:choose>
								          	 	<c:when test="${empty woAirMaterial.paramsMap.dxtdsl && woAirMaterial.paramsMap.tdjczs > 0}">
								          	 		0
								          	 	</c:when>
								          	 	<c:otherwise>
								          	 		<a href='javascript:void(0);' bjh="${erayFns:escapeStr(woAirMaterial.jh)}" onclick="viewTdkc(this)">${woAirMaterial.paramsMap.dxtdsl}</a>
								          	 	</c:otherwise>
								          	 </c:choose>
									      </td> 

								          <td class='text-right' style='vertical-align:middle'>

								          <input type="text"  onkeyup='clearNoNum(this)' name="sl_1" class="form-control" value="${woAirMaterial.sl}"></td>
								         
								          <td class='text-left' name="hclx"  style='vertical-align:middle'>
								          <input type="hidden" name="hclx_1" value="${woAirMaterial.lx}">
								          <c:if test="${woAirMaterial.lx == '0'}"><c:out value="其他" /></c:if
								          <c:if test="${woAirMaterial.lx == '4'}"><c:out value="化工品" /></c:if> 
								          <c:if test="${woAirMaterial.lx =='6'}"><c:out value="松散件" /></c:if>
								          <c:if test="${woAirMaterial.lx =='1'}"><c:out value="航材" /></c:if> 
								          <c:if test="${woAirMaterial.lx == '2'}"><c:out value="工具设备" /></c:if>
								          <c:if test="${woAirMaterial.lx == '5'}"><c:out value="低值易耗品" /></c:if></td>
								          <td class='text-center' style='vertical-align:middle'>
								          <input type="text"  name="bz_1" maxlength="300" placeholder='最大长度不超过300' class="form-control" value="${erayFns:escapeStr(woAirMaterial.bz)}"></td>
										</tr>
									</c:forEach>
									</tbody>	
								</table>
					  </div>
					</div>
				 </div>
			     <!-- end -->   
				 <!--  工单附件start -->
			    <div class="panel panel-default">
				       <div class="panel-heading">
							<h3 class="panel-title">附件上传 File Upload</h3>
					   </div>
					<div class="panel-body">
					<div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File Discription</div>
						</label>
						<div class="col-lg-7 col-sm-6 col-xs-8 padding-left-8 padding-right-0 input-group form-group">
							<div class='input-group'>
								<input type="text" id="wbwjm"   maxlength="90" class="form-control "  >
								<span class='input-group-btn' id="fileuploaderBut">
								<div>
									<div id="fileuploader"></div>
								</div>
								</span>
						    </div>
						</div>
					</div>
					<div class="col-lg-12 col-md-12 padding-left-2 padding-right-0">
					   <table class="table table-thin table-bordered table-striped table-hover text-center table-set" >
						  <thead> <tr>
						     <th class="colwidth-3"><div class="font-size-12 line-height-18 " >操作</div>
								<div class="font-size-9 line-height-18">Operation</div>
							</th>
					  	     <th class="colwidth-20"><div class="font-size-12 line-height-18">文件说明</div>
								 <div class="font-size-9 line-height-18">File Description</div>
							 </th>
							 <th class="colwidth-10"><div class="font-size-12 line-height-18">文件大小</div>
								 <div class="font-size-9 line-height-18">File Size</div>
							 </th>
							 <th class="colwidth-13"><div class="font-size-12 line-height-18">上传人</div>
								 <div class="font-size-9 line-height-18">Uploader</div>
							 </th>
							 <th class="colwidth-13"><div class="font-size-12 line-height-18">上传时间</div>
								 <div class="font-size-9 line-height-18">Upload Time</div>
							 </th>
						  </tr></thead>     
						  <tbody id="filelist">
							<c:forEach items="${woJobEnclosure}" var="woJobEnclosure">
								<tr>
								     <td>
								       <div onclick="lineRemovefile(this)"><i class="icon-trash color-blue cursor-pointer" title="删除 Delete"></i></div>
								     </td>
							          <td class='text-left' title='${woJobEnclosure.wbwjm}'><input type="hidden" name="fjid" value="${woJobEnclosure.id}">
							          <input type="hidden" name="yswjm" value="${erayFns:escapeStr(woJobEnclosure.yswjm)}">
							          <input type="hidden" name="nbwjm" value="${erayFns:escapeStr(woJobEnclosure.nbwjm)}">
							          <input type="hidden" name="wbwjm" value="${erayFns:escapeStr(woJobEnclosure.wbwjm)}">
							          <input type="hidden" name="cflj" value="${erayFns:escapeStr(woJobEnclosure.cflj)}">
							          <input type="hidden" name="hzm" value="${woJobEnclosure.hzm}">
							          <a href='javascript:void(0);' onclick="downloadfile('${woJobEnclosure.id}')">
							          <c:out value="${woJobEnclosure.wbwjm}" /></a></td>
							          
							          <td class='text-left'><input type="hidden" name="wjdx" value="${woJobEnclosure.wjdx}">
							          <c:out value="${woJobEnclosure.wjdxStr}" /></td>
							          <td class='text-left' title='${erayFns:escapeStr(woJobEnclosure.czr_user.displayName)}'>
							           
							          <c:out value="${erayFns:escapeStr(woJobEnclosure.czr_user.displayName)}" /></td>
								      <td class='text-center'>
								       <input type="hidden" name="czsj" value="${woJobEnclosure.czsj}">
								       <fmt:formatDate value='${woJobEnclosure.czsj}'  var="czsj" pattern='yyyy-MM-dd HH:mm:ss' />
										<c:out  value="${czsj}"/>
								      </td>
								</tr>
							</c:forEach> 
						</tbody>	
						</table>
					</div>
				 </div>
		        </div> 
			    <!--  end -->
			     <div class="clearfix"></div>
			     
			    <div class="col-lg-12 text-right padding-left-0 padding-right-0" style="margin-bottom: 10px">
                   <button type="button"  name="pgdBut" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="woSave()">
						<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					<button type="button"  name="pgdBut" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="submitSave()">
						<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
					 <button  type="button"  name="pgdBut" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()">
                    	<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
                </div>
                
                 <div class="clearfix"></div>
                 
			  </div>
			</div>
		</div>
		<!-- 基本信息 End -->

<!-- 	航材耗材的模态框 -->

	<div class="modal fade" id="alertModalHcxx" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:65%">
			<div class="modal-content">
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
					            	    <div class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
				            	            <select  id="hclx" name="hclx" class="form-control"  onchange="changeHclx()">
					         		            <option value="0,1,4,5" select="select">航材耗材</option>
					         		            <option value="2,3">工具设备</option>
								            </select>
									    </div>  
									  </div>
									  
										   <div   class=" pull-left padding-left-0 padding-right-0 " style="width:250px;" >
										   <input type="text" placeholder="中文名称/英文名称/部件号" id="keyword_search" class="form-control ">
					                       </div>
					                       
					                       <div class=" pull-right padding-left-5 padding-right-0 ">   
											<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
												<div class="font-size-12">搜索</div>
												<div class="font-size-9">Search</div>
					                   		</button>
									  </div>
					            </div>
				           <div class="clearfix"></div>	     
				                
							<!-- start:table -->
							<div  style="overflow-x: auto;margin-top: 10px">
								<table class=" table-set table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
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
								<div class="font-size-9 line-height-16">Number</div></th>
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
								<div class="col-xs-12 text-center" id="pagination4">
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
	<!-------航材耗材 End-------->
	
	<!-- 	工单的模态宽 模态框 -->
	<div class="modal fade" id="alertModalGlgd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:65%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">工单信息列表</div>
							<div class="font-size-9 ">Word Order List</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
						          
				            	<div class=" pull-right padding-left-0 padding-right-0 padding-top-10">
				            	
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
				            	
								    <div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;">
									  <input type="text" placeholder="编号/主题" id="keyword_search2" class="form-control ">
								    </div>
								    
			                      <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision3();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                    </div>  
			                  </div>  
				           <div class="clearfix"></div>	     
				                
								<!-- start:table -->
								<div  style="margin-top: 10px">
									<table class=" table-set table table-thin table-bordered table-striped table-hover text-center">
									<thead>
									<tr>
										<th width="40"><div class="font-size-12 line-height-18" >选择</div>
										<div class="font-size-9 line-height-18" >Choice</div></th>
										<th>
										<div class="important">
										<div class="font-size-12 line-height-18">工单编号</div>
										<div class="font-size-9 line-height-16">SerialNumber</div>
										</div>
										</th>
										<th><div class="font-size-12 line-height-18">工单类型</div>
										<div class="font-size-9 line-height-16">WorkOrder Type</div></th>
										<th><div class="font-size-12 line-height-18">专业</div>
										<div class="font-size-9 line-height-16">Major</div></th>
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
									<div class="col-xs-12 text-center" id="pagination3">
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
	</div>
	
	<!-------工单的工程指令 End-------->
		<div class="modal fade" id="alertModalGczl" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 70%">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
									  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">工单工程指令列表</div>
							<div class="font-size-9 ">Word Order List</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
						
					    <div class=" pull-right padding-left-0 padding-right-0 padding-top-10">
					       
							    <div class=" pull-left padding-left-0 padding-right-0 " style="width:250px;">
								<input type="text" placeholder="工程指令编号" id="keyword_search3" class="form-control ">
							    </div>
							    
			                    <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision5();">
											<div class="font-size-12">搜索</div>
											<div class="font-size-9">Search</div>
				                   	</button>
			                     </div>
			            </div>
				           <div class="clearfix"></div>	     
				                
								<!-- start:table -->
								<div  style="overflow-x: auto;margin-top: 10px">
									<table class="table-set table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
										<thead>
										<tr>
										<th style="width:4%;"><div class="font-size-12 line-height-18" >选择</div>
										<div class="font-size-9 line-height-18" >Choice</div></th>
											<th style="width:12%;">
											<div class="important">
											<div class="font-size-12 line-height-18">工程指令编号</div>
											<div class="font-size-9 line-height-16">EO No.</div>
											</div>
											</th>
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
									<div class="col-xs-12 text-center" id="pagination5">
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
							<div class="font-size-9 ">Word Order List</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
				                
								<!-- start:table -->
								<div  style="overflow-x: auto;margin-top: 10px">
									<table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
												<thead>
												<tr>
												<th width="30"><div class="font-size-12 line-height-18" >选择</div>
												<div class="font-size-9 line-height-18" >Choice</div></th>
												<th><div class="font-size-12 line-height-18">执行分类</div>
												<div class="font-size-9 line-height-16">Major</div></th>
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
		
		
	<!-------执行对象模态框 End-------->		
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script> 	 
<script type="text/javascript" src="${ctx}/static/js/thjw/project/workorder/edit_eo.js"></script>
<%@ include file="/WEB-INF/views/open_win/search_fix_chapter.jsp"%>
<%@ include file="/WEB-INF/views/open_win/work_material_view.jsp"%><!-------替代部件对话框 -------->
</body>
</html>