<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
.tr_border_none th,.tr_border_none td{
border-top:0px !important;
}

.project_comparison_left_one tr td:first-child{
text-align:right;
padding-right:10px;
}
.project_comparison_left_one tr{
height:25px;
}
.tr_border_bottom{
height:30px;
border-bottom:1px solid #000000;
}
.project_comparison_second tr{
height:25px;
}
.monitoring_items tr{
height:25px;
}
.monitoring_items tr:first-child td{
border-top:0px;
}
.project_comparison_second tr td{
padding-left:50px;
}
.project_comparison_left_two tr td{
padding-right:15px;
}
.project_comparison_right_two tr td{
padding-left:15px;
}
#maintenance_plan_modal tr:first-child td{
border:0px;
}
</style>
<div class="modal fade" id="maintenancePlanModal" tabindex="-1" role="dialog" aria-labelledby="maintenancePlanModal" aria-hidden="true">
			<div class="modal-dialog modal-lg" style="width: 1350px;">
				<div class="modal-content">
					<div class="modal-body padding-bottom-0" id="mountComponentModalBody">
						<div class="panel panel-primary">
							<div class="panel-heading  padding-top-3 padding-bottom-1">
								<div class="font-size-16 line-height-18">维修差异</div>
								<div class="font-size-9 ">Maintenance differences</div>
							</div>
							<div class="panel-body padding-top-0 padding-bottom-0">
				            	<div class="col-lg-12 col-xs-12">
									<div class='modal_tab_header margin-top-10'>
									<label style='margin-top:8px;'><span>机型</span><span style='margin-left:8px;'>XXXXXXX</span>
									<span style='margin-left:15px;'>维修方案</span><span style='margin-left:8px;'>XXXXXXX(描述)</span>
									<span style='margin-left:15px;'>版本</span><span style='margin-left:8px;'>1.1 &nbsp;&lt;&nbsp;0.9</span>
									</label>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-right" >
											<div class="font-size-12">查看差异表</div>
											<div class="font-size-9">View difference table</div>
									</button>
									<div class='clearfix'></div>
									</div>
									<div class='modal_tab_content margin-top-10' >
										<div class='modal_tab_content_left col-xs-3' style='padding-left:0px;padding-right:5px;'>
										      <div class="panel panel-primary left_second_content">
											    <!-- panel-heading -->
												<div class="panel-heading">
													<div class="font-size-12 line-height-12">维修项目清单</div>
													<div class="font-size-9 line-height-9">Maintenance item list</div>
												</div>
												<div class="panel-body padding-left-0 padding-right-0 padding-top-0 padding-bottom-0">
												<table class='table' >
													<tbody id='maintenance_plan_modal'>
													</tbody>
												</table>
												</div>
												</div>
										</div>
										<div class='modal_tab_content_right col-xs-9' style='padding-left:5px;padding-right:0px;'>
										<%@ include file="/WEB-INF/views/project/maintenance/maintenance_plan_right_modal.jsp"%>
										</div>
										<div class='clearfix'></div>
									</div>
				                	<div class="text-right margin-top-10 margin-bottom-10">
										<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
											<div class="font-size-12">关闭</div>
											<div class="font-size-9">Close</div>
										</button>
					                </div>
						     		<div class="clearfix"></div>
							 	 </div>
							 </div> 
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
 <script>
	 $(document).ready(function(){
		 maintenancePlanModal.goPage(1,"auto","desc"); 
	 })

    var maintenancePlanModal={
			goPage:function(pageNumber,sortType,sequence){
				this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
			},
			AjaxGetDatasWithSearch:function(){
				 var obj ={};
				 AjaxUtil.ajax({
			 		url:basePath+"/static/js/thjw/project2/maintenance/maintence_plan_modal.json",
			 		/*  contentType:"application/json;charset=utf-8", */
			 		dataType:"json",
			 		success:function(data){
			 			if(data.total >0){
			 				 appendContentHtmlModal(data.rows); 
			 			
			 			}else{
				 			$("#maintenance_plan_modal").empty();
							$("#maintenance_plan_modal").append("<tr><td class='text-center' >暂无数据 No data.</td></tr>");
			 			}
			 		
			 		}
			     });
			}
	}
	// 表格拼接
	 function appendContentHtmlModal(list){
			var htmlContent = '';
			 $.each(list,function(index,row){
				var parent=row;
				htmlContent +="<tr>"
				htmlContent += "<td >"+row.zjh+"<span class='badge' style='background:#3598db;'>"+row.sm+"</span></td>";
				htmlContent +="</tr>"
				$.each(row.children,function(index,row){
				    if(index==0){
				    	htmlContent +="<tr class='tr_border_none'>"
				    }else{
				    	htmlContent +="<tr>"
				    }
				    htmlContent += "<td >"+row.xmbh+"+"+row.ms+"</td>";
					htmlContent +="</tr>"
				})
		   $("#maintenance_plan_modal").empty();
		   $("#maintenance_plan_modal").html(htmlContent);
		 }) 
  }
	//获取预警颜色
	 function getWarningColor(bgcolor,syts,zt){
	 	if(!(zt == 0 || zt==5 || zt==6)){
	 		return bgcolor;
	 	}
	 	if(yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2){
	 		bgcolor = warningColor.level2;//黄色
	 	}
	 	if(Number(syts) <= yjtsJb1){
	 		bgcolor = warningColor.level1;//红色
	 	}
	 	return bgcolor;
	 }	
    </script>		