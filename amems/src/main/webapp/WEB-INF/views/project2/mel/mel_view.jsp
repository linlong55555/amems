<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看MEL变更单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	//来源ID(评估单ID)
	var todo_lyid = "${param.todo_lyid}";
	var todo_ywid = "${param.todo_ywid}";
</script>
	
</head>
<body class="page-header-fixed">
	 
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<input type="hidden" value="${melChangeSheet.xgbj}" id="xgbj">
	<input type="hidden" value="${melChangeSheet.id}" id="melId">
	<input type="hidden" value="${melChangeSheet.zt}" id="zt">
	<input type="hidden" id="zdsj" value="<fmt:formatDate value='${melChangeSheet.zdsj }' pattern='yyyy-MM-dd hh:mm:ss' />">
	<input type="hidden" value="${melChangeSheet.zdr.displayName}" id="zdrName">
	<input type="hidden" value="${melChangeSheet.shyj}" id="shyj">
	<input type="hidden" value="${melChangeSheet.shr.displayName}" id="shrName">
	<input type="hidden" id="shsj"  value="<fmt:formatDate value='${melChangeSheet.shsj }' pattern='yyyy-MM-dd hh:mm:ss' />">
	<input type="hidden" value="${melChangeSheet.shjl}" id="shjl">
	<input type="hidden" value="${melChangeSheet.pyr.displayName}" id="pfrName">
	<input type="hidden"  id="pfsj" value="<fmt:formatDate value='${melChangeSheet.pfsj }' pattern='yyyy-MM-dd hh:mm:ss' />">
	<input type="hidden" value="${melChangeSheet.pfjl}" id="pfjl">
	<input type="hidden" value="${melChangeSheet.pfyj}" id="pfyj">
	
	<div class="page-content">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body" >
			 		<div class="col-xs-12 " style='padding:0px;'>
						<div class="input-group-border">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">文件编号</div>
								<div class="font-size-9">File No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="ggdbh" name="ggdbh" type="text" maxlength="50" value="${erayFns:escapeStr(melChangeSheet.ggdbh)}" >
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">机型</div>
								<div class="font-size-9">A/C Type</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="jx" name="jx" type="text" maxlength="50" value="${erayFns:escapeStr(melChangeSheet.jx)}">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">项目号</div>
								<div class="font-size-9">Project No.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xmh" name="xmh" type="text" maxlength="50" value="${erayFns:escapeStr(melChangeSheet.xmh)}">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">所属部分</div>
								<div class="font-size-9">Owned Part</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="ssbf" name="ssbf" type="text" maxlength="100" value="${erayFns:escapeStr(melChangeSheet.ssbf)}">
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">所属章节</div>
								<div class="font-size-9">Chapter</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="sszj" name="sszj" type="text" maxlength="100" value="${erayFns:escapeStr(melChangeSheet.sszj)}">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">中/英</div>
								<div class="font-size-9">Chinese/English</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="zy" name="zy" type="text" maxlength="100" value="${erayFns:escapeStr(melChangeSheet.zy)}">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改前版本</div>
								<div class="font-size-9">Old Rev.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xgqBb" name="xgqBb" type="text" maxlength="16" value="${erayFns:escapeStr(melChangeSheet.xgqBb)}">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改后版本</div>
								<div class="font-size-9">New Rev.</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xghBb" name="xghBb" type="text" maxlength="16" value="${erayFns:escapeStr(melChangeSheet.xghBb)}">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">状态</div>
								<div class="font-size-9">Status</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="ztText" name="ztText" type="text" value="">
							</div>
						</div>
						
						<div class="clearfix"></div>
						<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- -评估单列表 -->
						 <div class="clearfix"></div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改依据</div>
								<div class="font-size-9">Basis</div>
							</label>
							<div id="xgyjDiv" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<c:forEach items="${melChangeSheet.melChangeSheetAndBasiList}" var="basi" varStatus="status">
									<div class="input-group input-group-new form-group">
						            	<label class="input-group-addon">
						            		${erayFns:escapeStr(basi.yjlx)}
						            	</label>
						            	<input type="text" class="xgyjValue form-control"  maxlength="1300" value="${erayFns:escapeStr(basi.yjnr)}">
					            	</div>
								</c:forEach>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修订页</div>
								<div class="font-size-9">Revision page</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xdqx" name="xdqx" type="text" maxlength="100" value="${erayFns:escapeStr(melChangeSheet.xdqx)}">
							</div>
						</div>
						
						<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改标记</div>
								<div class="font-size-9">Modify Mark</div>
							</label>
							<div class="col-lg-10 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div" >
								<label class='margin-right-5 label-input' ><input type='checkbox' name='xgbj' value="A" />&nbsp;A新增</label>
								<label class='margin-right-10 label-input'><input type='checkbox' name='xgbj' value="R" />&nbsp;R修订</label>
								<label class='margin-right-10 label-input'><input type='checkbox' name='xgbj' value="D" />&nbsp;D删除</label>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改内容</div>
								<div class="font-size-9">Content</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 75px;" id="xdnr" class='form-control' >${erayFns:escapeStr(melChangeSheet.xdnr)}</textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">修改原因</div>
								<div class="font-size-9">Reason</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea style="height: 75px;" id="xgyy"  class='form-control' >${erayFns:escapeStr(melChangeSheet.xgyy)}</textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">MEL清单</div>
								<div class="font-size-9">Mel Detailed</div>
							</span>
						 
							<div class="col-lg-8 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 span-input-div margin-top-8">
								<span class="margin-top-8 "><a href="javascript:openddownfile('${erayFns:escapeStr(melChangeSheet.attachment.id)}');">${erayFns:escapeStr(melChangeSheet.attachment.wbwjm)}${melChangeSheet.attachment.hzm}</a></span>
							</div>
						</div>
						
						<div class="clearfix"></div>
					</div>	
						<div class="clearfix"></div>
					
					<div id="attachments_list_edit" style="display:none">
					
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					
					</div>	
						<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp" %> <!--流程信息 -->		
							
							
				</div>	
				<div class="clearfix"></div>  	
		</div>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/mel/mel_view.js"></script>
		<%@ include file="/WEB-INF/views/open_win/selectEvaluation.jsp"%><!-- -选择评估单列表 -->
</body>
</html>
