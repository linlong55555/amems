<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
 <!-- 基本信息(版本) -->
    <div class="left_second_content" style='margin-bottom:0px;'>
     <!-- 附件清单 -->
    <div  class="panel panel-primary" style='border-top:0px;border-left:0px;border-right:0px;'>
	<div class="panel-heading" >
		<div class="font-size-12 ">附件清单</div>
		<div class="font-size-9">Attachment</div>
	</div>
	<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;padding-top:0px;'>
     <%@ include file="/WEB-INF/views/quality/annualplan/attachment_info.jsp"%>
    </div>
    </div>
     <!-- 流程记录 -->
    <div  class="panel panel-primary" style='border-left:0px;border-right:0px;'>
	<div class="panel-heading" >
		<div class="font-size-12 ">流程记录</div>
		<div class="font-size-9">Process record</div>
	</div>
	<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;padding-top:0px;'>
     <%@ include file="/WEB-INF/views/quality/annualplan/process_record.jsp"%>
    </div>
    </div>
     
     <!-- 变更记录 -->
     <div  class="panel panel-primary" style='border-left:0px;border-right:0px;'>
	<div class="panel-heading" >
		<div class="font-size-12 ">变更记录</div>
		<div class="font-size-9">Change record</div>
	</div>
	<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;padding-top:0px;'>
	<%@ include file="/WEB-INF/views/quality/annualplan/change_record.jsp"%>
    </div>
    </div>
     
     </div>