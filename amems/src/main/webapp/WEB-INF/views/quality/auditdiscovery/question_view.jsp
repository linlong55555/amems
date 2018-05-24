<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看发现问题通知单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="zdbmid" name="zdbmid" value="${user.bmdm}" />
<input type="hidden" id="id" name="id" value='${id}' />
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<input type="hidden" id="maintenanceListId">
            	<form id="auditDiscoveryAdd_form">
              	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题单号</div>
								<div class="font-size-9 ">Question No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" maxlength="50" name="shwtdbh" id="shwtdbh" readonly/>
						</div>
				  </div>
				  <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">实际审核日期</div>
								<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" maxlength="10" name='sjShrq' data-date-format="yyyy-mm-dd" id="sjShrq" readonly/>
						</div>
					</div>
					  <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">类型</div>
								<div class="font-size-9 ">Type</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0" style='height:34px;'>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='lx' value='1' style='vertical-align:text-bottom'  checked="checked" disabled/>&nbsp;内部&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='lx' value='2' style='vertical-align:text-bottom;' disabled/>&nbsp;外部&nbsp;&nbsp;
							</label>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核类别</div>
								<div class="font-size-9 ">Category</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0" style='height:34px;'>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='shlb' value='11' style='vertical-align:text-bottom' checked="checked" disabled/>&nbsp;初次审核&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='shlb' value='12' style='vertical-align:text-bottom;' disabled/>&nbsp;复审&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='shlb' value='21' style='vertical-align:text-bottom;' disabled/>&nbsp;专项审核&nbsp;&nbsp;
							</label>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核对象</div>
								<div class="font-size-9 ">Object</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 " >
							
							<input type="text"  class="form-control"  name="shdx" id="shdx" maxlength="20" readonly="readonly"/>
						</div>
						<!-- <div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 " >
							<input type="hidden" id="shdxid"/>
							<input type="hidden" id="shdxbh"/>
							<input type="hidden" id="shdxmc"/>
							<div class='input-group' style='width:100%'>
						    <input type="text"  class="form-control readonly-style"  name="shdx" id="shdx" maxlength="20" readonly="readonly"/>
							<span class="input-group-btn" id="shdxbtn">
								<button type="button"  class="btn btn-default" data-toggle="modal" onclick="auditDiscoveryAdd.chooseDepartment()">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
						</div> -->
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">责任人</div>
								<div class="font-size-9 ">Head</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0 " >
							 <input type="text"  class="form-control" name="zrr" maxlength="20" id="zrr" readonly/>
						</div>
						<!-- <div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="hidden" id="zrrbmid"/>
							<input type="hidden" id="zrrid"/>
							<input type="hidden" id="zrrbh"/>
							<input type="hidden" id="zrrmc"/>
							<div class='input-group' style='width:100%'>
						    <input type="text"  class="form-control readonly-style" name="zrr" maxlength="20" id="zrr" readonly/>
							<span class="input-group-btn">
								<button type="button"  class="btn btn-default" data-toggle="modal" onclick="auditDiscoveryAdd.chooseUser()">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
						</div> -->
					</div>
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核内容</div>
								<div class="font-size-9 ">Audit content</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class="form-control"  rows="2" cols="34" name="jcnr" maxlength="100" id="jcnr" readonly></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核发现问题</div>
								<div class="font-size-9 ">Finding</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
						    <%@ include file="/WEB-INF/views/quality/auditdiscovery/audit_discovery_problems.jsp"%>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题类型</div>
								<div class="font-size-9 ">Category</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='wtlx' value='1' style='vertical-align:text-bottom' disabled="disabled"  checked="checked"/>&nbsp;一般&nbsp;&nbsp;
							</label>
							<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
								<input type='radio' name='wtlx' value='9' disabled="disabled" style='vertical-align:text-bottom;'/>&nbsp;严重&nbsp;&nbsp;
							</label>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">要求反馈日期</div>
								<div class="font-size-9 ">Feedback Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" maxlength="10" data-date-format="yyyy-mm-dd" name="yqfkrq" id="yqfkrq" readonly/>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">整改建议</div>
								<div class="font-size-9 ">Advice</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class="form-control" rows="2" cols="34" maxlength="1000" name="zgjy" id="zgjy" readonly></textarea>
						</div>
					</div>
					</form>
				  <div class="clearfix"></div>
				 
				
				<div id="attachments_list_edit" >
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
				
				
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/auditdiscovery/audit_discovery_view.js"></script>
</body>
</html>