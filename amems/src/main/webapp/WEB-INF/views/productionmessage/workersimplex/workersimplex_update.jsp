<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content">
	<input type="hidden" value="${scheduledTasklist.id}"  id="id"/>
	<input type="hidden" value="${erayFns:escapeStr(scheduledTasklist.fjzch)}"  id="fjzch"/>
		<!-------导航Start--->
	<div class="panel panel-primary">
		<div class="panel-heading " id="NavigationBar"></div>
		<!-------导航End--->
		<!-- 查看工单Start -->

			<div class="panel-body padding-bottom-0">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<form id="form">
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">任务单号</div>
							<div class="font-size-9 line-height-18">Task No.</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<span class="form-control " readonly="readonly">${erayFns:escapeStr(scheduledTasklist.rwdh)}</span>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">来源类型</div>
							<div class="font-size-9 line-height-18">Source Type</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
						 
						 <c:if test="${scheduledTasklist.rwlx==1 }"><span class="form-control " readonly="readonly">定检执行任务</span> </c:if>
						 <c:if test="${scheduledTasklist.rwlx==2 }">
						  	<c:if test="${scheduledTasklist.rwzlx==1 }">
							 <span class="form-control " readonly="readonly">非例行-时控件工单</span>
						 	</c:if>
						 	<c:if test="${scheduledTasklist.rwzlx==2 }">
							 <span class="form-control " readonly="readonly">非例行-附加工单</span>
						 	</c:if>
						 	<c:if test="${scheduledTasklist.rwzlx==3 }">
							 <span class="form-control " readonly="readonly">非例行-排故工单</span>
						 	</c:if>
						</c:if>
						 <c:if test="${scheduledTasklist.rwlx==3 }">
							 <span class="form-control " readonly="readonly">EO工单</span>
						</c:if>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">完成日期</div>
							<div class="font-size-9 line-height-18">Completion Date</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<span class="form-control " readonly="readonly">${scheduledTasklist.fxrq}</span>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">飞机注册号</div>
							<div class="font-size-9 line-height-18">A/C REG</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<span class="form-control " readonly="readonly">${erayFns:escapeStr(scheduledTasklist.fjzch)}</span>
						</div>
					</div>
					<div class="clearfix"></div>
					<label class="pull-left col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">任务相关信息</div>
									<div class="font-size-9">Task Description</div>
							</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 margin-bottom-10">
						<textarea class="form-control"   readonly="readonly" >${erayFns:escapeStr(scheduledTasklist.rwxx)}</textarea>
					</div>
					
					<div class="clearfix"></div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">标准工时</div>
							<div class="font-size-9 line-height-18">Plan Man-Hours</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
						 
						<span class="form-control " readonly="readonly">
						<c:choose>
							<c:when  test="${scheduledTasklist.jhgsRs!=0&& scheduledTasklist.jhgsXs!=0}">
							${scheduledTasklist.jhgsRs}人x${scheduledTasklist.jhgsXs}时=${scheduledTasklist.jhgsRs*scheduledTasklist.jhgsXs}时</span>
							</c:when>
							 <c:otherwise>  
						     0
						   </c:otherwise>
						</c:choose>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">实际工时</div>
							<div class="font-size-9 line-height-18">Actual Man-Hours</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<span class="form-control " readonly="readonly">
							
							<c:choose>
							<c:when  test="${scheduledTasklist.sjgsRs!=0&& scheduledTasklist.sjgsXs!=0}">
							${scheduledTasklist.sjgsRs}人x${scheduledTasklist.sjgsXs}时=${scheduledTasklist.sjgsRs*scheduledTasklist.sjgsXs}时</span>
							</c:when>
							 <c:otherwise>  
						     0
						   </c:otherwise>
						</c:choose>
							
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">偏差值(%)</div>
							<div class="font-size-9 line-height-18">Deviation Value(%)</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<span class="form-control " readonly="readonly">${scheduledTasklist.pcl}</span>
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">调查表编号</div>
							<div class="font-size-9 line-height-18">Survey NO.</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="dcbbh" value="${erayFns:escapeStr(scheduledTasklist.dcbbh)}" name="dcbbh"  maxlength="20" class="form-control"  >
						</div>
					</div>
					<div class="clearfix"></div>
				 	<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">统计人</div>
							<div class="font-size-9 line-height-18">Statistical Person</div></label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
							<select id="tjrid" class="form-control " name="tjrid"
									 onchange="showOrHide(this.value);">
							<c:forEach items="${userToRole}" var="userToRole">
							<option value="${userToRole.id}" <c:if test="${scheduledTasklist.tjrid eq userToRole.id }">selected='selected'</c:if>>${erayFns:escapeStr(userToRole.displayName)}</option>
							</c:forEach>
							</select>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">统计日期</div>
							<div class="font-size-9 line-height-18">Statistical Date</div></label>
						 <div class="col-xs-8 padding-left-8 padding-right-0" >
								<input class="form-control date-picker" value="${scheduledTasklist.tjrq}" id="tjrq" name="tjrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div> 
				
					<div class="clearfix"></div>
					<label class="pull-left col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">备注</div>
									<div class="font-size-9">Remark</div>
							</label>
					<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 padding-bottom-0">
						<textarea class="form-control" id="gsshBz" placeholder='长度最大为300'   maxlength="300">${erayFns:escapeStr(scheduledTasklist.gsshBz)}</textarea>
					</div>
					
					<div class="clearfix"></div>
					 </form>
                    <br/>
				</div>
				  <div class="text-right">
                     <button onclick="btnSave()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" ><div
										class="font-size-12">保存</div>
									<div class="font-size-9">Save</div></button>
									 <button onclick="btnSub()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" ><div
										class="font-size-12">确认</div>
									<div class="font-size-9">Confirm</div></button>
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1  margin-bottom-10" onclick="javascript:back()"><div
										class="font-size-12">返回</div>
									<div class="font-size-9">Back</div></button>
                     </div>
				<div class="clearfix"></div>
			</div>
		</div>
		
	</div>
<script type="text/javascript" src="${ctx }/static/js/thjw/productionmessage/workersimplex/workersimplex_update.js"></script>
</body>
</html>