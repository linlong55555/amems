<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
<div class="page-content">
			<!-- from start -->
	<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
	     <div class="panel-body" id="disableDiv">
	          <!--    基本信息 start -->
				<div class="panel panel-default">
				        <div class="panel-heading">
							    <h3 class="panel-title">工单基本信息 Basic Information</h3>
							   <input type="hidden" id="dprtcode_parma" value="${woResult.dprtcode}" class="form-control " readonly />
							   <input type="hidden" id="id" value="${woResult.id}" class="form-control " readonly />
					           <input type="hidden" id="gkid" value="${woResult.djgkid}"/>
					           <input type="hidden" id="zzjgid" name="zzjgid" value="${woResult.dprtcode}" />
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
						
						 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 " id="demo5">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">工单子类</div>
								<div class="font-size-9 line-height-18">W/O Child Type</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
								<select id="gdlx" class="form-control" name="gdlx"disabled="disabled" >
									<c:forEach items="${minWorkOrderTypeEnum}" var="item" >
									  <option value="${item.id}" <c:if test="${woResult.gdlx == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
								 </select>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 "  id="demo1">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">工程指令编号</div>
								<div class="font-size-9 line-height-18">EO No.</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" id="gczlbh" name="gczlbh" value="${erayFns:escapeStr(woResult.gczlbh)}" class="form-control " readonly />
							  <input type="hidden" id="zxdxfjzch" value="${erayFns:escapeStr(wOActionObj.fjzch)}"/>
							     <input type="hidden"  name="gczlid" id="gczlid" value="${woResult.gczlid}"/>
							</div>
						 </div>
						 
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">工单编号</div>
								<div class="font-size-9 line-height-18">W/O No.</div>
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
								<input type="text" id="zy" name="zy" value="${woResult.zy}" class="form-control"  readonly/>
							    <input type="hidden" class="form-control " id="zy-wo" value="${woResult.zy}"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 " id="dj_Div1"  style="display: none">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">工卡编号</div>
								<div class="font-size-9 line-height-18">W/C No.</div>
							</label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" id="gkbh" name="gkbh" value="${erayFns:escapeStr(woResult.gkbh)}" class="form-control"  readonly/>
							</div>
						</div>
						
					</div>
					
					 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					  
					      <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 ">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">ATA章节号</div>
								<div class="font-size-9 line-height-18">ATA</div></label>
							   <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
									<input type="text"  id="zjh" class="form-control"  value="${erayFns:escapeStr(woResult.zjh)}  ${erayFns:escapeStr(woResult.zwms)}" disabled="disabled"/>
							 </div>
					     </div>
					     
						 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 ">
							 <label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">工作站位</div>
								<div class="font-size-9 line-height-18">Location</div></label>
							   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group" id="chonse">
									<input type="text" id="gzzw" name="gzzw" value="${woResult.gzzw}" class="form-control"  readonly/>
							        <input type="hidden" class="form-control " id="gzzw-wo" value="${woResult.gzzw}"/>
							  </div>
						 </div>
						 
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 ">
							<label class="col-lg-4 col-sm-4 col-xs-4  text-right padding-left-0 padding-right-0">
								    <div class="font-size-12 line-height-18">基地</div>
									<div class="font-size-9 line-height-18">Station</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
							    <input type="text"  id="jd" class="form-control" value="${erayFns:escapeStr(woResult.jdms)}"  disabled="disabled"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group"  id="dj_Div2"  style="display: none">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">关联定检项目</div>
								<div class="font-size-9 line-height-18">Fixed Check Item</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="gldjxm" name="gldjxm" value="${erayFns:escapeStr(woResult.gldjxm)}" class="form-control"  readonly/>
							</div>
					     </div>
						
						  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 " id="demo6">
							 <label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">是否需要支援</div>
								<div class="font-size-9 line-height-18">Need Support</div></label>
							   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group" id="chonse" >
									<input type="text" id="isXyjszy" name="isXyjszy" value="${woResult.isXyjszy}" class="form-control " readonly />
							  </div>
						  </div>
						
				
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
						
						     <div class="col-lg-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
								<label class="col-lg-2 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">标准工时</div>
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
							 	
						 	 	<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0  ">
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator</div>
									</label>
									<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
										<input type="text" id="zdr" name="zdr" value="${erayFns:escapeStr(woResult.displayname)}" class="form-control " readonly />
									</div>
								</div>
							
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
									<input type="text" id="zdsj" name="zdsj" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${woResult.zdsj}"/>" data-date-format="yyyy-MM-dd HH:mm:ss" class="form-control " readonly />
								</div>
							</div>
							
						</div>	
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">工单状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
									<input type="text" id="gdzt" value="${woResult.zt}" class="form-control " readonly />
								</div>
							</div>
							
							 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 ">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									    <div class="font-size-12 line-height-18">飞机机型</div>
										<div class="font-size-9 line-height-18">Model</div></label>
								<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
									<input type="text" name="fjjx" id="fjjx" class="form-control " readonly/>									
								</div>
							 </div>
							 
							 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 text-right form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">工作组</div>
									<div class="font-size-9 line-height-18">Work Group</div>
								</label>
								<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 ">
									<input type="text" id="gzz" value="${erayFns:escapeStr(woResult.displaygzz)}" class="form-control " readonly />
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
					   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" id="demo2">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 form-group">
								<div class="font-size-12 line-height-18">关联评估单</div>
								<div class="font-size-9 line-height-18">Evaluation</div>
							</label>
	
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" style="overflow-x: auto;">
								<table class="table-set table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
								<thead>
									<tr>
										<th><div class="font-size-12 line-height-18">评估单编号</div>
										<div class="font-size-9 line-height-16">Assessment No.</div></th>
										<th><div class="font-size-12 line-height-18">来源</div>
										<div class="font-size-9 line-height-16">Source</div></th>
										<th><div class="font-size-12 line-height-18">参考资料</div>
										<div class="font-size-9 line-height-16">Reference Material</div></th>
										<th><div class="font-size-12 line-height-18">生效日期</div>
										<div class="font-size-9 line-height-16">Effective Date</div></th>
										<th><div class="font-size-12 line-height-18">机型工程师</div>
										<div class="font-size-9 line-height-16">Engineer</div></th>
										<th><div class="font-size-12 line-height-18">评估期限</div>
										<div class="font-size-9 line-height-16">Assess period</div></th>
										<th><div class="font-size-12 line-height-18">评估状态</div>
										<div class="font-size-9 line-height-16">State</div></th>
									</tr>
									
								</thead>     
								<tbody>
								<c:forEach items="${orderSource}" var="orderSource">
									<tr>
							          <td class='text-center'>
							          	<a href="${ctx}/project/technicalfile/findApprovalFileUpload/${orderSource.pgdid}/${orderSource.dprtcode}" target="_blank">${erayFns:escapeStr(orderSource.pgdh)}</a> 
							          </td>
							          
							          <td class='text-left'><c:out value="${orderSource.ly}" /></td>
									          <td class='text-left' title="${erayFns:escapeStr(orderSource.shzltgh)}"><c:out value="${orderSource.shzltgh}" /></td>
									          <td class='text-left'><c:out value="${fn:substring(orderSource.sxrq, 0, 10)}" /></td>
									          <td class='text-left'><c:out value="${orderSource.displayname}" /></td>
									          <td class='text-center'><c:out value="${fn:substring(orderSource.pgqx, 0, 10)}" /></td>
									          <td class='text-left'>
										           <c:if test="${orderSource.zt== '1'}"><c:out value="已评估" /></c:if>
										           <c:if test="${orderSource.zt== '2'}"><c:out value="已审核" /></c:if>
										           <c:if test="${orderSource.zt== '3'}"><c:out value="已批准" /></c:if> 
										      </td>   
									</tr>
								</c:forEach>
								</tbody>	
							   </table>
							</div>
					   </div>
					   
					   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						  <div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">工单主题 </div>
								<div class="font-size-9 line-height-18">Subject</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0 form-group" title="${erayFns:escapeStr(woResult.zhut)}">
								<textarea class="form-control" id="zhut" name="zhut" rows="3" readonly>${erayFns:escapeStr(woResult.zhut)}</textarea>
							</div>
						</div>
						 <div class=" col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12 line-height-18">关联文件</div>
									<div class="font-size-9 line-height-18">Related Files</div></label>
							<div class="col-lg-10 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group" title="${erayFns:escapeStr(woResult.glxx)}"> 
								<textarea class="form-control" id="glxx" name="glxx" rows="3" readonly="readonly">${erayFns:escapeStr(woResult.glxx)}</textarea>
							</div>
						 </div>
					   </div>	
					   
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0"  id="demo3">
							 <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
									    <div class="font-size-12 line-height-18">停场时间</div>
										<div class="font-size-9 line-height-18">Stop Time</div></label>
								<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group" title="${erayFns:escapeStr(woResult.tcsj)}">
									  <input type="text" class='form-control ' name="tcsj" id="tcsj" value="${erayFns:escapeStr(woResult.tcsj)}" disabled="disabled"/>
								</div>
							 </div>
							 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">下发工单原因</div>
								<div class="font-size-9 line-height-18">Reason for W/O</div>
							   </label>
								<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0" title="${erayFns:escapeStr(woResult.xfgdyy)}">
									<textarea class="form-control" id="xfgdyy" rows="1" readonly name="xfgdyy">${erayFns:escapeStr(woResult.xfgdyy)}</textarea>
								</div>
						     </div>
						</div> 
						
						<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							   <div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div></label>
							   <div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group" >
									<textarea class="form-control" id="bz" name="bz" disabled="disabled">${erayFns:escapeStr(woResult.bz)}</textarea>
							  </div>
						</div>
					</div>
			      </div>
					<!-- end -->
					
				    <!-- 执行对象start	 -->
				  <div class="panel panel-default">
				        <div class="panel-heading">
							 <h3 class="panel-title">执行对象  Execute Object</h3>
					    </div>
					<div class="panel-body">	 	
					<div class="col-lg-12  padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: auto ;">
				        <table class="table table-thin table-bordered table-striped table-hover text-center" style=" min-width: 1700px !important;" >
							<thead>
							<tr>
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
								<div class="font-size-9 line-height-18" >Aircraft</div>
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
			         		     <td width="150" >
								    <input type="text" class="form-control " id="zxfl" value="${wOActionObj.zxfl}" />
			         		     </td>
			         		    <td>
						             <input type="text" id="fjzch" name="fjzch" class="form-control" value="${erayFns:escapeStr(wOActionObj.fjzch)}" >  
			         		    </td>
			         		     <td>
			         		       <input type="text" class="form-control"  id="fjxlh" onmouseover="this.title=this.value">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control"  id="zfxh" onmouseover="this.title=this.value">
			         		    </td>
			         		    <td>
			         		       <input type="text" class="form-control" id="yfxh" onmouseover="this.title=this.value">
			         		    </td>
			         		     <td id="td_bjmc">
			         		       <input type="text" class="form-control" name="bjmc" onmouseover="this.title=this.value" readonly id="bjmc" value="${erayFns:escapeStr(wOActionObj.bjName)}" >
			         		    </td>	
			         		    <td width="10%">
						             <input type="text" id="bjh" name="bjh" class="form-control" value="${erayFns:escapeStr(wOActionObj.bjh)}" >
			         		         <input type="hidden"  id="zjqdid" name="zjqdid"/>
			         		    </td>
			         		    <td width="10%">
						           <input type="text" id="bjxlh" name="bjxlh" class="form-control" value="${erayFns:escapeStr(wOActionObj.bjxlh)}" >
			         		    </td>
			         		    <td>
			         		       <input type="text" class='form-control datepicker' name="wcrq" id="wcrq" data-date-format="yyyy-mm-dd" />
			         		    </td>
			         		    <td id="th_8">
			         		       <input type="text" class="form-control" name="jssj" id="jssj" onkeyup='clearNoNum(this)'>
			         		    </td>
			         		    <td id="th_7">
			         		       <input type="text" class="form-control" name="qlxh" id="qlxh" onkeyup='clearNoNum(this)' >
			         		    </td>
			         		    <td id="th_3">
			         		       <input type="text" class="form-control" name="jcsj" id="jcsj" onkeyup='clearNoNum(this)'>
			         		    </td>
			         		    <td id="th_4">
			         		       <input type="text" class="form-control" name="jcxh" id="jcxh" onkeyup='clearNoNum(this)' >
			         		    </td>
			         		 </tr>
				   			</table>
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
					      <table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
							<thead>
								<tr>
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
							<tbody>
							<c:forEach items="${wOJobContent}" var="wOJobContent" varStatus="status">
								<tr> 
									  <td class='text-center' style='vertical-align:middle'><c:out value="${status.index+1}" /></td>
							          <td class='text-left' style='vertical-align:middle'>
							          <textarea type="text" rows="3" readonly="readonly" class="form-control" >${erayFns:escapeStr(wOJobContent.gznr)}</textarea>
							          </td>
							          <td class='text-left' style='vertical-align:middle'><c:out value="${wOJobContent.gzz}" /></td>
							          <td style='vertical-align:middle'>
							          <input type="checkbox" style='width: 20px;height: 20px;' name="isBj"  id="isBj" value="${wOJobContent.isBj}" 
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
							<table class="table-set table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
								<thead>
									<tr>
										<th width="5%"><div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-16">No.</div></th>
										<th width="20%"><div class="font-size-12 line-height-18">工单编号</div>
										<div class="font-size-9 line-height-16">W/O No.</div></th>
										<th><div class="font-size-12 line-height-18">工单类型</div>
										<div class="font-size-9 line-height-16">Work Order Type</div></th>
										<th><div class="font-size-12 line-height-18">专业</div>
										<div class="font-size-9 line-height-16">Skill</div></th>
										<th width="40%"><div class="font-size-12 line-height-18">主题</div>
										<div class="font-size-9 line-height-16">Subject</div></th>
									</tr>
								</thead>     
								<tbody>
								<c:forEach items="${nonWOCardd}" var="nonWOCardd" varStatus="status">
									<tr>
									  <td class='text-center'><c:out value="${status.index+1}" /></td>
							          <td class='text-left'>
							          	<a href="${ctx}/project/workorder/Looked?id=${nonWOCardd.xggdid}&gddlx=${nonWOCardd.xggdLx}" target="_blank">${erayFns:escapeStr(nonWOCardd.gdbh)}</a>
							          </td>
							          <td class='text-left'  id="xggdlx">
							            <c:if test="${nonWOCardd.xggdLx == '1'}"><c:out value="定检工单" /></c:if> 
							            <c:if test="${nonWOCardd.xggdLx  == '2'}"><c:out value="非例行工单-" /></c:if>
							           <c:if test="${nonWOCardd.xggdLx  == '3'}"><c:out value="EO工单" /></c:if>
							           <c:if test="${nonWOCardd.xggdZlx == '1'}"><c:out value="时控件工单" /></c:if> 
							            <c:if test="${nonWOCardd.xggdZlx  == '2'}"><c:out value="附加工单" /></c:if>
							           <c:if test="${nonWOCardd.xggdZlx  == '3'}"><c:out value="排故工单" /></c:if>
							          </td> 
							          <td class='text-center' ><c:out value="${nonWOCardd.zy}" /></td> 
							          <td class='text-left' title='${erayFns:escapeStr(nonWOCardd.zhut)}'><c:out value="${nonWOCardd.zhut}" /></td> 
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
					  <div class="col-lg-12 padding-left-2 padding-right-0" style="overflow-x: auto;">
						 <table class="table-set table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
							<thead>
								<tr>
									<th width="4%"><div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-16">No.</div></th>
									<th><div class="font-size-12 line-height-18">件号来源</div>
									<div class="font-size-9 line-height-16">Source</div></th>
									<th><div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-16">F.Name</div></th>
									<th><div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-16">CH.Name</div></th>
									<th><div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-16">P/N</div></th>
									<th width="8%"><div class="font-size-12 line-height-18">在库数量</div>
									<div class="font-size-9 line-height-16">Stock Quantity</div></th>
									<th width="9%"><div class="font-size-12 line-height-18">替代数量</div>
									<div class="font-size-9 line-height-16">Num</div></th>
									<th width="8%"><div class="font-size-12 line-height-18">使用数量</div>
									<div class="font-size-9 line-height-16">UseNumber</div></th>
									<th width="10%"><div class="font-size-12 line-height-18">航材类型 </div>
									<div class="font-size-9 line-height-16">Airmaterial type</div></th>
									<th width="30%"><div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-16">Remark</div></th>
								</tr>
							</thead>     
							<tbody>
							<c:forEach items="${woAirMaterial}" var="woAirMaterial" varStatus="status">
								<tr>
									  <td class='text-center'><c:out value="${status.index+1}" /></td>
							          <td class='text-left' title='${erayFns:escapeStr(woAirMaterial.refJhly)}'><c:out value="${woAirMaterial.refJhly}" /></td>
							          <td class='text-left' title='${erayFns:escapeStr(woAirMaterial.ywmc)}'><c:out value="${woAirMaterial.ywmc}" /></td>
							          <td class='text-left' title='${erayFns:escapeStr(woAirMaterial.zwmc)}'><c:out value="${woAirMaterial.zwmc}" /></td>
							          <td class='text-left' title='${erayFns:escapeStr(woAirMaterial.jh)}'><c:out value="${woAirMaterial.jh}" /></td>
							          <td class='text-right'>
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
									  
							          <td class='text-right'><c:out value="${woAirMaterial.sl}" /></td>
							           <td class='text-left'>
							           	  <c:if test="${woAirMaterial.lx == '0'}"><c:out value="其他" /></c:if> 
									      <c:if test="${woAirMaterial.lx == '4'}"><c:out value="化工品" /></c:if> 
								          <c:if test="${woAirMaterial.lx =='6'}"><c:out value="松散件" /></c:if>
								          <c:if test="${woAirMaterial.lx =='1'}"><c:out value="航材" /></c:if> 
								          <c:if test="${woAirMaterial.lx == '2'}"><c:out value="工具设备" /></c:if>
								          <c:if test="${woAirMaterial.lx == '5'}"><c:out value="低值易耗品" /></c:if></td>
							          <td class='text-left' title='${erayFns:escapeStr(woAirMaterial.bz)}'><c:out value="${woAirMaterial.bz}" /></td>
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
					<div class="col-lg-12  padding-left-2 padding-right-0" style="overflow-x: auto;">
					   <table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
						  <thead> <tr>
					  	     <th><div class="font-size-12 line-height-18">文件说明</div>
								 <div class="font-size-9 line-height-18">File Description</div>
							 </th>
							 <th><div class="font-size-12 line-height-18">文件大小</div>
								 <div class="font-size-9 line-height-18">File Size</div>
							 </th>
							 <th><div class="font-size-12 line-height-18">上传人</div>
								 <div class="font-size-9 line-height-18">Uploader</div>
							 </th>
							 <th><div class="font-size-12 line-height-18">上传时间</div>
								 <div class="font-size-9 line-height-18">Upload Time</div>
							 </th>
						  </tr></thead>     
						  <tbody>
							<c:forEach items="${woJobEnclosure}" var="woJobEnclosure">
								<tr>
							      <td class='text-left'><a href='javascript:void(0);' onclick="downloadfile('${woJobEnclosure.id}')">
							      <c:out value="${woJobEnclosure.wbwjm}" /></a></td>
							      <td class='text-left'><c:out value="${woJobEnclosure.wjdxStr}" /></td>
							      <td class='text-left'><c:out value="${woJobEnclosure.czr_user.displayName}" /></td>
							      <td class='text-center'>
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
			    
			    
			    <!--  审核意见 -->
			    <div class="panel panel-default">
				        <div class="panel-heading">
							    <h3 class="panel-title">审批  Approval</h3>
					   </div>
					<div class="panel-body">	 	
					   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
						 <label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8" >
							 <div class="font-size-12 line-height-18">审核意见</div>
							 <div class="font-size-9 line-height-18">Review Opinion</div>
						 </label>
						  <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 margin-bottom-10 padding-right-0">
							 <textarea class="form-control" id="shyj" name="shyj" maxlength="150" disabled="disabled">${woResult.shyj}</textarea>
						      <div class="pull-left padding-right-10">
						      <div class="font-size-12 line-height-18">审核人　<label style="margin-right: 50px; font-weight:normal">${erayFns:escapeStr(woResult.username)} 　${erayFns:escapeStr(woResult.realname)}</label></div>
						      <div class="font-size-9 line-height-12">Reviewer</div>
						      </div>
					      	  <div class="pull-left">
							  <div class="font-size-12 line-height-18">审核时间 &nbsp;&nbsp;<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${woResult.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
							  <div class="font-size-9 line-height-12">Review Time</div>
							  </div>
						  </div>
					  </div>
				  
					  <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
						 <label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8" >
							 <div class="font-size-12 line-height-18">批准意见</div>
							 <div class="font-size-9 line-height-18">Approval Opinion</div>
						 </label>
						 
						  <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 margin-bottom-10 padding-right-0">
							 <textarea class="form-control" id="pfyj" name="pfyj" maxlength="150" disabled="disabled">${woResult.pfyj}</textarea>
						      <div class="pull-left padding-right-10">
						      <div class="font-size-12 line-height-18">批准人　<label style="margin-right: 50px; font-weight:normal">${erayFns:escapeStr(woResult.pfusername)} 　${erayFns:escapeStr(woResult.pfrealname)}</label></div>
						      <div class="font-size-9 line-height-12">Appr. By</div>
						      </div>
					      	  <div class="pull-left">
							  <div class="font-size-12 line-height-18">批准时间 &nbsp;&nbsp;<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${woResult.pfsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
							  <div class="font-size-9 line-height-12">Approved Time</div>
							  </div>
						  </div>
					  </div>
				      <input  id="id" type="hidden" value="${woResult.id}" />
				 </div>
		       </div>
		       <!-- end -->
		       <div class="clearfix"></div>
		       <div class="text-right">
					<c:if test="${woResult.gddlx eq '2' }">
						<button type="button" style="margin-bottom: 10px;" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:printNonroutine()">
	                    	<div class="font-size-12">打印非例行工单</div>
							<div class="font-size-9">Print Non-Routine W/O</div>
						</button>
					</c:if>
               </div>
               
               <div class="clearfix"></div>
	     </div>
	     
	</div>
</div>
    <%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/workorder/view_wo.js"></script>
<%@ include file="/WEB-INF/views/open_win/work_material_view.jsp"%><!-------替代部件对话框 -------->
</body>
</html>