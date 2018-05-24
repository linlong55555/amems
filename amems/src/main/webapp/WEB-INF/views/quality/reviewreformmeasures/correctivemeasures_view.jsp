<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看问题整改措施</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<input type="hidden" id="id" name="id" value='${id}' />
	<div class="page-content" id="correctivemeasures_view">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0" id='correctivemeasure_body'>
              	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题编号</div>
								<div class="font-size-9 ">Audited unit</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" id='shwtbh'  maxlength="20"  readonly/>
						</div>
				  </div>
				  <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">实际审核日期</div>
								<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" id="sjShrq" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">类型</div>
								<div class="font-size-9 ">Type</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" id="lx" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核类别</div>
								<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" id="shlb" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核对象</div>
								<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" id="shdx" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">责任人</div>
								<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" id="zrr" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题等级</div>
								<div class="font-size-9 ">Problem level</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" id="wtdj" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题分类</div>
								<div class="font-size-9 ">Classification</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" id="wtfl" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">要求反馈日期</div>
								<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" id="yqfkrq" maxlength="10" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">附件</div>
								<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<span class='input-group-btn' >
								<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"  style="float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
							</span>
						</div>
					</div>
					<div class='clearfix'></div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题描述及依据</div>
								<div class="font-size-9 ">Audit Findings & Reference</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class="form-control" rows="2" cols="34" id="wtms" maxlength="100" readonly></textarea>
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">整改建议</div>
								<div class="font-size-9 ">Advice (requirements)</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class="form-control" id="zgjy" rows="2" cols="34" maxlength="100" readonly></textarea>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题发现人</div>
								<div class="font-size-9 ">Finding person</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" maxlength="15" id="wtfxr" readonly/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">问题提报时间</div>
								<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control" id="wttbr" maxlength="10" readonly/>
						</div>
					</div>
				  <div class="clearfix"></div>
				
				  <%@ include file="/WEB-INF/views/quality/reviewreformmeasures/preventive_measure.jsp"%>
				 <%@ include file="/WEB-INF/views/quality/reviewreformmeasures/measures_assessment.jsp"%>
				 <%@ include file="/WEB-INF/views/quality/reviewreformmeasures/measures_assessment_verification.jsp"%>
				<%@ include file="/WEB-INF/views/quality/reviewreformmeasures/measures_assessment_close.jsp"%>
				 
				 
				 <!-- 附件列表 -->
				 <div id="attachments_list_close" >
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
				<!-- 流程记录 -->
				<%@ include file="/WEB-INF/views/quality/reviewreformmeasures/measures_record.jsp"%>
				<div class="clearfix"></div>
			
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/reviewreformmeasures/correctivemeasures_view.js"></script>
		<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>