<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="FilesDownModal" tabindex="-1" role="dialog"  aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style='width:60%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
   				<h4 class="modal-title" >
             		<div class='pull-left'>
	          			<div class="font-size-14 " id="titleName">附件列表</div>
						<div class="font-size-12" id="titleEname">Files</div> 
					</div>
			 	  	<div class='pull-right' >
			  			<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
		  		  	</div>
					<div class='clearfix'></div>
  				</h4>
			</div>
            <div class="modal-body" id="AssignEndModalBody">
 		    <div class="col-xs-12 margin-top-8 padding-left-10 padding-right-8">
				<!--参考文件END  -->		
				<div id="attachments_list_edit" style="display:none">
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
				</div>
			    <div class="clearfix"></div>              
			</div>
        	 <div class="modal-footer ">
				<div class="col-xs-12 padding-left-8 padding-right-0" >
					<span class="pull-left modalfootertip" >
		                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
					</span>
					<div class="pull-right margin-right-8">
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
						</button>
					</div>
				</div>
			 </div>
   		</div>
	</div>
</div>
<%-- <script type="text/javascript" src="${ctx}/static/js/thjw/material2/toolcheck/toolcheck_filesDown.js"></script> --%>