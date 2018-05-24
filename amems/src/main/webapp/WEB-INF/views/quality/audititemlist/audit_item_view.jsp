<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>审核项目单</title>
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
			<div class="panel-body padding-bottom-0" >
		 		<div class="col-xs-12 " style='padding:0px;'>
		 		<input class="form-control" id="audititemId" value="${audititemId}"  type="hidden" >
				<div class="input-group-border">
              	 <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">检查单号</div>
								<div class="font-size-9 ">Check No.</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shxmdbh" type="text"  class="form-control" />
						</div>
				  </div>
				  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">计划审核日期</div>
								<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="sjShrq" type="text" class="form-control" maxlength="10" name='date-picker' data-date-format="yyyy-mm-dd"/>
						</div>
					</div>
				  <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">类型</div>
								<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input name="lx" type='radio' onchange="shdxChange(true)" value='1' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;内部&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input name="lx" type='radio'  onchange="shdxChange(false)" value='2' style='vertical-align:text-bottom;'/>&nbsp;外部&nbsp;&nbsp;
							</label>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核类别</div>
								<div class="font-size-9 ">Category</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input name="shlb" type='radio' value='11' style='vertical-align:text-bottom'  checked="checked"/>&nbsp;初次审核&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input name="shlb" type='radio' ' value='12' style='vertical-align:text-bottom;'/>&nbsp;复审&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input name="shlb" type='radio'  value='21' style='vertical-align:text-bottom;'/>&nbsp;专项审核&nbsp;&nbsp;
							</label>
						</div>
					</div>
					 <div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核对象</div>
								<div class="font-size-9 ">Object</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input id="shdxmc" type="text" class="form-control" name='date-picker' />
						</div>
					</div>
					
					<div  class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">状态</div>
								<div class="font-size-9 ">Status</div>
						</label>
						<div class="col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" id="zt" maxlength="10" />
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">审核成员</div>
							<div class="font-size-9">Audit member</div>
						</label>
						<div class="col-sm-10 col-xs-9 padding-leftright-8">
							<%@ include file="/WEB-INF/views/quality/creatingauditnotice/creating_audit_member.jsp"%>
						</div>	
					</div>
				  <div class="clearfix"></div>
				</div>
					<div id="attachments_list_edit" style="display:none">
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
			 	<%-- <div >
					 <%@ include file="/WEB-INF/views/quality/creatingauditnotice/creating_acceptance_info.jsp"%>
				</div>  --%>
        	</div>
		</div>		
	</div>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/audititemlist/audit_item_view.js"></script>
</body>
</html>
