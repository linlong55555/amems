<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
		<input readOnly="true" maxlength="10" type="hidden" id="isScheduler"
			value="${isScheduler }" /> <input readOnly="true" maxlength="10" type="hidden"
			id="isDuration" value="${isDuration }" /> <input readOnly="true" maxlength="20"
			type="hidden" id="isActualhours" value="${isActualhours }" />
		<!-------导航Start--->
<div class="page-content">
			<!-- from start -->
	<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0">
					<div class="panel panel-default">
				        <div class="panel-heading">
							    <h3 class="panel-title">飞机机型 Model</h3>
					   </div>
					   <input type="hidden" id="dprtcode_parma" value="${record.dprtcode}" class="form-control " readonly />
					   <div class="panel-body">
					   
								<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0">
									<form id="form">
										<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0"  >
											<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
													class="font-size-12 line-height-18">飞机型号</div>
												<div class="font-size-9 line-height-18">Model</div></label>
												<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
												<input  type="text"  id="fjjx"   readOnly="true"  name="fjjx"  value="${erayFns:escapeStr(record.fjjx)}" class="form-control"  >
											</div>
										</div>
								   </form>
								   
									<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0"  >
											<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
													class="font-size-12 line-height-18">特殊情况</div>
												<div class="font-size-9 line-height-18">S/C</div></label>
												<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
												<select id="isTsqk" class="form-control" disabled="disabled">
												       <option  value="0" <c:if test="${record.isTsqk == 0}">selected="true"</c:if> >无</option>
												       <option value="1" <c:if test="${record.isTsqk == 1}">selected="true"</c:if>>有</option>
												</select>
											</div>	
									</div>
									
									<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0"  >
											<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
													class="font-size-12 line-height-18">定检计划公式</div>
												<div class="font-size-9 line-height-18">C/M</div></label>
												<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
												<select class='form-control' id='gsDjjh' name="gsDjjh" disabled="disabled" >
													<c:forEach items="${formulaTypeEnum}" var="item">
													  <option value="${item.id}" <c:if test="${record.gsDjjh == item.id }">selected="true"</c:if> >${item.name}</option>
													</c:forEach>
											    </select>
											</div>	
									</div>
							</div>
						</div>
					</div>		
					
					
					<div class="clearfix"></div>
				<div class="panel panel-default">
						        <div class="panel-heading">
									    <h3 class="panel-title">日使用量设置 Setting</h3>
							   </div>
				  <div class="panel-body">
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0">
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">机身飞行时间</div>
								<div class="font-size-9 line-height-18">Flight HRS.</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control "
									id="rJsfxsj" value="${record.rJsfxsj}" onkeyup='clearNoNum(this)'>
							</div>
						</div>

						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">搜索灯时间</div>
								<div class="font-size-9 line-height-18">Search Time</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control "
									id="rSsdsj" value="${record.rSsdsj}" onkeyup='clearNoNum(this)'>
							</div>
						</div>

						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 " >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">绞车时间</div>
								<div class="font-size-9 line-height-18">Winch Time</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control "
									id="rJcsj"  value="${record.rJcsj}"  onkeyup='clearNoNum(this)'>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">起落循环</div>
								<div class="font-size-9 line-height-18">Flight CYCS.</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control "
									id="rQljxh" value="${record.rQljxh}"  onkeyup='clearNoNum(this)'>
							</div>
						</div>
					</div>
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0" >
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">绞车循环</div>
								<div class="font-size-9 line-height-18">Winch CYCS.</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control "
									id="rJcxh" value="${record.rJcxh}" onkeyup='clearNoNum(this)'>
							</div>
						</div>

						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">外吊挂循环</div>
								<div class="font-size-9 line-height-18">E/S CYCS.</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control "
									id="rWdgxh" value="${record.rWdgxh}" onkeyup='clearNoNum(this)'>
							</div>
						</div>

						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N1</div>
								<div class="font-size-9 line-height-18">N1</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control " id="rN1"
									id="rSsdxh" value="${record.rN1}" onkeyup='clearNoNum(this)'>
							</div>
						</div>

						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N2</div>
								<div class="font-size-9 line-height-18">N2</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control " id="rN2"
									value="${record.rN2}" onkeyup='clearNoNum(this)'>
							</div>
						</div>
					</div>
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0" >	
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N3</div>
								<div class="font-size-9 line-height-18">N3</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control " id="rN3"
									value="${record.rN3}"  onkeyup='clearNoNum(this)'>
							</div>
						</div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N4</div>
								<div class="font-size-9 line-height-18">N4</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control " id="rN4"
									value="${record.rN4}" onkeyup='clearNoNum(this)'>
							</div>
						</div>
						<div  class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N5</div>
								<div class="font-size-9 line-height-18">N5</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control " id="rN5"
									value="${record.rN5}"  onkeyup='clearNoNum(this)'>
							</div>
						</div>
						<div  class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N6</div>
								<div class="font-size-9 line-height-18">N6</div></label>
							<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
								<input readOnly="true" maxlength="10" type="text" class="form-control " id="rN6"
									value="${record.rN6}" onkeyup='clearNoNum(this)'>
							</div>
						</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0" >	
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18">特殊监控1</div>
									<div class="font-size-9 line-height-18">TS1</div></label>
								<div class="col-xs-8 padding-left-8 padding-right-0 form-group">
									<input readOnly="true" maxlength="10" type="text" class="form-control "
										id="rTsjk1" value="${record.rTsjk1}"  onkeyup='clearNoNum(this)'>
								</div>
							</div>
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18">特殊监控2</div>
									<div class="font-size-9 line-height-18">TS2</div></label>
								<div class="col-xs-8 padding-left-8 padding-right-0">
									<input readOnly="true" maxlength="10" type="text" class="form-control "
										id="rTsjk2" value="${record.rTsjk2}"  onkeyup='clearNoNum(this)'>
								</div>
							</div>
						</div>
				</div>
			</div>		
					
					
					<div>
					<div class="panel panel-default">
				        <div class="panel-heading">
							    <h3 class="panel-title">关联部件 Related P/N</h3>
					   </div>
					<div class="panel-body">	 	
					<div class="col-lg-12 col-md-12 padding-left-2 padding-right-0" style="overflow-x: auto;">
						<table class="table table-thin table-bordered table-striped table-hover text-center" id="addtr" style="min-width:900px">
							<thead>
								<tr>
									<th><div class="font-size-12 line-height-18">飞机部件号</div>
										<div class="font-size-9 line-height-18">P/N</div></th>
									<th><div class="font-size-12 line-height-18">英文名称</div>
										<div class="font-size-9 line-height-18">F.Name</div></th>
										<th><div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div></th>
									<th><div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div></th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
							
						</table>
					</div>
				</div>
					 </div>  
				</div>
				<div style="display: none">
					<input readOnly="true" maxlength="20" id="planedataId" type="text" name="menuId" />
				</div>
				<div class="clearfix"></div>

			</div>

		</div>
</div>
	</div>
     <%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript"
		src="${ctx}/static/js/thjw/project/maintenance/view_planemodel.js"></script>

</body>
</html>
