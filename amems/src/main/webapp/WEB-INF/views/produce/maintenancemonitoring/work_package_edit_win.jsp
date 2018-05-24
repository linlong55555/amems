<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="work_package_edit_Modal" class='col-xs-12 ' style="display: none;">
    <div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-0">
             	<ul id="myTab" class="nav nav-tabs tabNew-style">
                   	<li class="active">
                   		<a id="workPD_win_tab" href="#workPD" data-toggle="tab" aria-expanded="false" >
                   			<div class="font-size-12 line-height-12">工包明细</div>
                  		<div class="font-size-9 line-height-9">Work Package Detail</div>
                   		</a>
                   	</li>
                   	<!-- <li id='tool_win_tab' class="" >
				<a href='#toolWinTab' data-toggle="tab"  >
					<div class="font-size-12 line-height-12">航材工具</div>
                    <div class="font-size-9 line-height-9">Tool chain</div>
                 </a> -->
			</li>
                 </ul>
                 <div id="myTabContent" class="tab-content">
                  	
                   	<div class="tab-pane active" id="workPD"  >
                   		<%@ include file="/WEB-INF/views/common/produce/work_package_detail.jsp" %> <!-- 工包明细 -->	
                   	</div>
                   	<%-- <div class="tab-pane fade " id="toolWinTab" >
                   		<%@ include file="/WEB-INF/views/common/produce/material_tool_list.jsp" %> <!-- 航材工具 -->	
                   	</div> --%>
               </div>
                 
                </div>
   <div class='clearfix'></div>
</div>
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancemonitoring/work_package_edit_win.js"></script>
