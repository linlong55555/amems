<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>年度计划</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

<script type="text/javascript">

var todo_ywid = "${param.todo_ywid}";
var todo_jd = "${param.todo_jd}";
var todo_nf = "${param.todo_nf}";
var todo_dprtcode = "${param.todo_dprtcode}";

	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					searchRevision();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<div class="page-content tabcontent-main">
	   <div class='col-xs-12 ' style='background:white;padding-left:0px;padding-right:0px;padding-top:0px;'>
	<div class='col-sm-3 col-xs-12' style='padding-left:0px;padding-right:10px;' id="left_div">
	<!-- 维修方案版本 -->
    <div class="panel panel-primary left_first_content" >
    <input id="bbh" type="hidden" >
    <input id="year_search" type="hidden">
		    <!-- panel-heading -->
			<div class="panel-heading" style='border-bottom:0px;'>
				<div class="font-size-12 line-height-12">待审核</div>
				<div class="font-size-9 line-height-9">To be audited</div>
			</div>
			<div class="panel-body padding-left-0 padding-right-0 left_first_body"  style='padding-top:0px;' >
				<!-- 搜索 -->
				<div style='height:110px;overflow:auto;border-bottom:1px solid #d5d5d5;;'>
				    <table class='table text-center table-thin' style='margin-bottom:0px !important'>
					    <tbody id="dshsj" >
					    </tbody>
				    </table>
			    </div>
			    <div class="col-xs-12 padding-leftright-8 margin-top-8" >
					<div class="input-group input-group-new">
						<div class="input-group-addon" style='padding-top:0px;padding-right:5px;color:#333;text-align:right;'>
							<div class="font-size-12 line-height-12 ">审核意见</div>
							<div class="font-size-9 line-height-12 ">Audit opinion</div>
						</div>
						<textarea id="shyj" class="form-control" style='height: 55px;' maxlength="100"></textarea>
                	</div><!-- /input-group -->
				</div>
				<div class="col-xs-12 padding-leftright-8 margin-top-8" >
				   <div class='pull-right'>
					<a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left checkPermission" 
                    	id="add_btn" onclick="annual_plan_approval_main.auditBh()" href="javascript:void(0);"  
                    	permissioncode='quality:annualplan:main:06'>
						<div class="font-size-12">审核驳回</div>
						<div class="font-size-9">Audit Reject</div>
					</a>
					<a class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-3 pull-left checkPermission"
						 id="revision_btn" onclick="annual_plan_approval_main.audit()" href="javascript:void(0);" 
						 permissioncode='quality:annualplan:main:06'>
						<div class="font-size-12">审核通过</div>
						<div class="font-size-9">Audited</div>
					</a>
					</div>
				</div>
			</div>
    </div>
     <%@ include file="/WEB-INF/views/quality/annualplan/annual_plan_basic.jsp"%>
    </div>
    <div class='col-sm-9 col-xs-12 padding-right-0 padding-left-0' id="right_div">
    	<div style="position:absolute;left:-8px;" id="maintenance_toggle_div">
			<i id="maintenance_toggle_btn" class="icon-caret-left cursor-pointer" style="font-size: 22px;" onclick="toggleAnnual(this)"></i>
		</div>
        <%@ include file="/WEB-INF/views/quality/annualplan/annual_plan_info.jsp"%>
    </div>
    <div class='clearfix'></div>
    
    </div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/annualplan/annual_plan_audit.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/annualplan/annual_plan_main.js"></script><!--年度审核计划js  -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/annualplan/annual_view.js"></script><!--年度视图js  -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 附件对话框 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
	<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
</body>
</html>