<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>适航指令</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
 
</script>
</head>
<body class="page-header-fixed">
	 
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<div class="page-content">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body" >
		 		<div class="col-xs-12 " style='padding:0px;'>
					<!-- <div class="input-group-border"> -->
					
	                    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>文件类型</div>
								<div class="font-size-9">Doc. type</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " value="${erayFns:escapeStr(airworthiness.jswjlx)}" />
							</div>
						</div>
						
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>来源</div>
								<div class="font-size-9">Issued By</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " value="${erayFns:escapeStr(airworthiness.jswjly)}" />
							</div>
						</div>
						
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">状态</div>
								<div class="font-size-9">Status</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " id="zt" value="${erayFns:escapeStr(airworthiness.zt)}" />
							</div>
						</div>
						<div class="clearfix"></div>
	                    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>文件编号</div>
								<div class="font-size-9">Doc. No.</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " id="jswjwjbh" name="jswjwjbh" maxlength="20" value="${erayFns:escapeStr(airworthiness.jswjbh)}" >
							</div>
						</div>
						
	                    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>版本</div>
								<div class="font-size-9">Rev</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " id="jswjbb" name="jswjbb" maxlength="10"value="${erayFns:escapeStr(airworthiness.bb)}"  >
							</div>
							
						</div>
	                    <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修正案号</div>
								<div class="font-size-9">Amendment</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" class="form-control " id="xzah" name="xzah" maxlength="15" value="${erayFns:escapeStr(airworthiness.xzah)}" >
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">主题</div>
								<div class="font-size-9">Subject</div>
							</span>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<textarea id="zhut" name="zhut" class="form-control" maxlength="100" style="height: 55px">${erayFns:escapeStr(airworthiness.jswjzt)}</textarea>
							</div>
						</div>
						
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" id="wczjh" class="form-control" disabled="disabled" value=" ${erayFns:escapeStr(airworthiness.ZJ.zjh)}" >
							</div>
						</div>
						
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA英文描述</div>
								<div class="font-size-9">English Desc</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" id="ataywmc" class="form-control" disabled="disabled" value=" ${erayFns:escapeStr(airworthiness.ZJ.ywms)}">
							</div>
						</div>
						
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">ATA中文描述</div>
								<div class="font-size-9">Chinese Desc</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" id="atazwmc" class="form-control" disabled="disabled" value=" ${erayFns:escapeStr(airworthiness.ZJ.zwms)}">
							</div>
						</div>
						
						<div class="clearfix"></div>
						<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">关联适航性资料</div>
								<div class="font-size-9">Airworthiness</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input type="text" id="wczjhName" class="form-control" disabled="disabled" value=" ${erayFns:escapeStr(airworthiness.paramsMap.gljswjbh)}">
							</div>
						</div>
						
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">关联主题</div>
								<div class="font-size-9">Related Subject</div>
							</span>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								<textarea id="zhut" name="zhut" class="form-control" maxlength="100" style="height: 55px">${erayFns:escapeStr(airworthiness.paramsMap.gljswjzt)}</textarea>
							</div>
						</div>
						 
						
						<div class="clearfix"></div>
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">颁发日期</div>
								<div class="font-size-9">Issue Date</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control date-picker" id="jswjbfrq" name="jswjbfrq" data-date-format="yyyy-mm-dd" type="text" readonly="readonly"  
								value="<fmt:formatDate value='${airworthiness.bfrq}' pattern='yyyy-MM-dd' />"  >
							</div>
						</div>
						
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">生效日期</div>
								<div class="font-size-9">Effect Date</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							 	<input class="form-control date-picker" id="jswjsxrq" name="jswjsxrq" data-date-format="yyyy-mm-dd" type="text" readonly="readonly" 
							 	value="<fmt:formatDate value='${airworthiness.sxrq}' pattern='yyyy-MM-dd' />"  >
							</div>
						</div>
						
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">到期日期</div>
								<div class="font-size-9">Due Date</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control date-picker" id="jswjdqrq" name="jswjdqrq" data-date-format="yyyy-mm-dd" type="text" readonly="readonly" 
								value="<fmt:formatDate value='${airworthiness.dqrq}' pattern='yyyy-MM-dd' />"  >
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">备注</div>
								<div class="font-size-9">Note</div>
							</span>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="bz" name="bz" class="form-control" maxlength="100" style="height: 55px">${erayFns:escapeStr(airworthiness.bz)}</textarea>
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">源文件</div>
								<div class="font-size-9">File</div>
							</span>
						 
							<div class="col-lg-8 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 span-input-div margin-top-8">
								<span class="margin-top-8 "><a href="javascript:openddownfile('${erayFns:escapeStr(airworthiness.scfj.id)}');">${erayFns:escapeStr(airworthiness.scfj.wbwjm)}${airworthiness.scfj.hzm}</a></span>
							</div>
						</div>
						
						
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">是否评估</div>
								<div class="font-size-9">Is Estimate</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
								<span class="margin-right-5 span-input">
									<input name="Ispg" style=" vertical-align: middle;   margin-top: -1px;" type="radio" value="1" <c:if test="${airworthiness.xpgbs eq 1 }">checked</c:if> >&nbsp;需评估
								</span>
								<span class="span-input">
									<input name="Ispg" style=" vertical-align: middle;   margin-top: -1px;" type="radio" value="0" <c:if test="${airworthiness.xpgbs eq 0 }">checked</c:if> >&nbsp;无需评估
								</span>
							</div>
						</div>
						
						 <div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="jswjpgqxDiv">
							<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">评估期限</div>
								<div class="font-size-9">Assess Limit</div>
							</span>
							<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control date-picker" id="jswjpgqx" name="jswjpgqx" data-date-format="yyyy-mm-dd" type="text" readonly="readonly" 
								value="<fmt:formatDate value='${airworthiness.pgqx}' pattern='yyyy-MM-dd' />"  >
							</div>
						</div>
						<div class="clearfix"></div>	
						<c:forEach items="${airworthiness.technicalfileOrderList}" var="technicalfileOrder" varStatus="status">
							<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2  ${status.index > 0 ? "hidden":""}">评估范围</div>
									<div class="font-size-9 ${status.index > 0 ? "hidden":""}">Applicability</div>
								</span>
								<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
											<div class="input-group">
											    <label class="input-group-addon input-group-none" >
													 ${erayFns:escapeStr(technicalfileOrder.fjjx)}&nbsp;
												 </label>
													<input type="text"  id="wczjhName" class="form-control" disabled="disabled"
													value="${erayFns:escapeStr(technicalfileOrder.pgr_user.displayName)}" />
						                    </div>
									</div>
								</div>
							</div>
						</c:forEach>
						
						<div class="clearfix"></div>	
        	</div>
		</div>		
	</div>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/airworthiness/airworthiness_view.js"></script>
</body>
</html>
