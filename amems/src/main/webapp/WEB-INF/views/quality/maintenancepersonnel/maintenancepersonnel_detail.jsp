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
<link rel="stylesheet" href="${ctx}/static/lib/cropper/cropper.css"> 
<script type="text/javascript" src="${ctx}/static/lib/cropper/cropper.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">

	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<div class="page-content">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			
			<div class="panel-body">
				<input type="hidden" id="personnelId" value="${personnel.id}" />
				<input type="hidden" id="type" value="${type}" />
				<input type="hidden" id="current_id" value=""/>
				<input type="hidden" id="current_rowid" value=""/>
				<input type="hidden" id="adjustHeight" value="90"/>
				<input type="hidden" id="dprtcode" value="${personnel.dprtcode==null?user.jgdm:personnel.dprtcode}" />
				<div id="detail_div" class="col-xs-12 widget-body padding-left-0 padding-right-0 padding-top-0">
                	<ul class="nav nav-tabs tabNew-style">
                    	<li class="active"><a href="#archive_basic" data-toggle="tab" aria-expanded="true" id="tab_archive_basic">基本信息 General Info</a></li>
                    	<li class=""><a href="#archive_post" data-toggle="tab" aria-expanded="false">岗位信息 Position</a></li>
                    	<li class=""><a href="#archive_license" data-toggle="tab" aria-expanded="false">执照信息 License</a></li>
                    	<li class=""><a href="#archive_maintenance" data-toggle="tab" aria-expanded="false">技术等级 Tech level</a></li>
                    	<li class=""><a href="#archive_train" data-toggle="tab" aria-expanded="false">培训记录 Training</a></li>
                    	<li class=""><a href="#archive_award" data-toggle="tab" aria-expanded="false">个人奖惩 Performance</a></li>
                    	<li class=""><a href="#archive_reputation" data-toggle="tab" aria-expanded="false">诚信记录 Reputation</a></li>
                      	<li class=""><a href="#archive_attachment" data-toggle="tab" aria-expanded="false" onclick="loadBasicAttachments()">附件 Attachment</a></li>
                    	<li class=""><a href="" data-toggle="tab" aria-expanded="false" onclick="exportExcel()">导出 Export</a></li>
                    </ul>
                    <div class="tab-content " style='padding:0px;'>
                    	<!----------------------------------- 基本信息begin ---------------------------------->
                    	<div class="tab-pane fade active in table-h margin-top-0 padding-leftright-8" id="archive_basic">
                    		<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_basic.jsp"%>
                      	</div>
                      	<!----------------------------------- 基本信息end ---------------------------------->
                      	
                      	<!----------------------------------- 岗位信息begin ---------------------------------->
                      	<div class="tab-pane fade  margin-top-0 padding-leftright-8" id="archive_post">
                      		<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_post.jsp"%>
                      	</div>
                      	<!----------------------------------- 岗位信息end ---------------------------------->
                      	
                      	<!----------------------------------- 执照信息begin ---------------------------------->
	                    <div class="tab-pane fade table-h margin-top-0 padding-leftright-8" id="archive_license">
	                    	<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_license.jsp"%>
	                    </div>
	                    <!----------------------------------- 执照信息end ---------------------------------->
	                    
	                    <!----------------------------------- 维修等级begin ---------------------------------->
	                    <div class="tab-pane fade table-h margin-top-0 padding-leftright-8" id="archive_maintenance">
	                    	<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_maintenance.jsp"%>
	                    </div>
	                    <!----------------------------------- 维修等级end ---------------------------------->
	                    
	                    <!----------------------------------- 培训记录begin ---------------------------------->
	                    <div class="tab-pane fade table-h margin-top-0 padding-leftright-8" id="archive_train">
	                    	<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_train.jsp"%>
	                    </div>
	                    <!----------------------------------- 培训记录end ---------------------------------->
	                    
	                    <!----------------------------------- 个人奖惩begin ---------------------------------->
	                    <div class="tab-pane fade table-h margin-top-0 padding-leftright-8" id="archive_award">
	                    	<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_award.jsp"%>
	                    </div>
	                    <!----------------------------------- 个人奖惩end ---------------------------------->
	                    
	                    <!----------------------------------- 诚信记录begin ---------------------------------->
	                    <div class="tab-pane fade table-h margin-top-0 padding-leftright-8" id="archive_reputation">
	                    	<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_reputation.jsp"%>
	                    </div>
	                    <!----------------------------------- 诚信记录end ---------------------------------->
                      	
                      	<!----------------------------------- 附件begin ---------------------------------->
                      	<div class="tab-pane fade table-h margin-top-0 padding-leftright-8" id="archive_attachment">
                      		<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_attachment.jsp"%>
                      	</div>
                      	<!----------------------------------- 附件end ---------------------------------->
                  </div>
              </div>
              
			  <div class="text-right">
					<button id="saveBtn" type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-top-10 editTable" onclick="save()">
                    		<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					
                   	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-top-10" onclick="javascript:goToMainPage()">
                    		<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
               </div>
               
			</div>
		</div>
	</div>
	<link href="${ctx}/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx}/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
	<link rel="stylesheet" href="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel.css">
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_detail.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_obj.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_attachment_common.js"></script>
    <%@ include file="/WEB-INF/views/open_win/import.jsp"%>
   	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
   	<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
   	<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
</body>
</html>