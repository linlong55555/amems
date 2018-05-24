<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%> 
<style>
.titile_up{height:35px;  font-size:14px; font-weight:bold; padding:0; margin:0;}
.titile_up h1 { font-size: 14px; margin: 0; height: 35px; line-height: 35px;}
.titile_up span { font-size: 14px; margin: 0; height: 35px; line-height: 35px; padding-left:10px;}
.small_one { height: 50px;  border: 1px solid #d4d4d4; font-size: 16px; font-weight: bold; line-height: 50px; text-align: center; background: #d8ecf5;display: block;  overflow: hidden; text-overflow: ellipsis;white-space: nowrap;}
.engine_info{ }
.engine_info p{height: 20px; line-height: 20px; margin:0; white-space: nowrap;text-overflow: ellipsis; overflow: hidden;}
.engine_info span{ display: block;overflow: hidden; float: left;  white-space: nowrap; text-overflow: ellipsis;}
.engine_w1{width:15%;}
.engine_w2{width:50%;}
.engine_w3{width:32%;}
.font_weight{font-weight:bold;}
.border_line{border-bottom:1px solid #ccc;}
.color_green{color:#02af59;}
.color_red{color:#ff0033;}
</style>
<script type="text/javascript">
	var paramJgdm = '${param.dprtcode}';
	var paramFjzch = '${param.fjzch}';
</script>
</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			<!-- 搜索条件 -->
			<div class="col-lg-12 col-md-12 col-xs-12 padding-left-0 padding-right-0 searchContent row-height" >
		          <div class="col-lg-3 col-md-3 col-xs-12 pull-left padding-left-0 padding-right-0 " >
					<span class="pull-left col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
						<div class="font-size-9">Organization</div>
					</span>
					<div class="col-xs-9 col-sm-9 padding-left-8 padding-right-0">
						<select id="dprtcode_search" class="form-control " name="dprtcode_search" onchange="dprtChange()" >
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
					</div>
				</div>
		          <div class="col-lg-3 col-md-3 col-xs-12 pull-left padding-left-0 padding-right-0 " >
					<span class="pull-left col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">飞机注册号</div>
						<div class="font-size-9">A/C Reg</div>
					</span>
					<div class="col-xs-9 col-sm-9 padding-left-8 padding-right-0">
						<select class='form-control' id="fjzch" onchange="aircraftstatus.reload()">
					    </select>
					</div>
				</div>
				 <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
						<div class="font-size-12">剩余</div>
						<div class="font-size-9">Remain</div>
					</span>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0 input-group" >
						<input type="text" id="sy_search" class="form-control readonly-style" ondblclick="aircraftstatus.openSurplusWin()" readonly/>
						<span class="input-group-btn">
							<button type="button" id="SY_search_btn" class="btn btn-default" data-toggle="modal" onclick="aircraftstatus.openSurplusWin()">
								<i class="icon-search cursor-pointer"></i>
							</button>
						</span>
					</div>
				</div>
				<!--  <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-8 margin-top-0 form-group" >
					<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 row-height" onclick="aircraftinfoMain.reload();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                 	</button>
				</div> -->
				
			</div>
			<!-- 适航情况 -->
			<div class="col-lg-12 col-md-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0" id="status_main_body" >
			<div class="col-sm-3 col-xs-12 padding-left-0 padding-right-0"  id='status_main_leftDiv'> 
		     <div class="panel panel-primary margin-bottom-0" id='status_main_leftFirstDiv'>
		     <!-- panel-heading -->
			 <div class="panel-heading padding-top-0">
			 <div class="titile_up"><h1 class="pull-left">当前状态/Status</h1><span id="status" class="pull-left font-size-16"><i class="icon-fighter-jet font-size-20 color_green"></i>适航</span></div>
             </div>
		    <div class="panel-body padding-left-0 padding-right-0 padding-top-5 padding-bottom-5" style=" overflow-x: auto;">
              <div class="col-lg-12 col-md-12 col-xs-12 " style='padding-left:5px;padding-right:5px;'> 
                <div class="col-lg-6  col-xs-12  small_one " id="jssj" >0FH</div>
                <div class="col-lg-6  col-xs-12  small_one " id="jsxh" >0FC</div>
			  </div>
			  <div class="clearfix"></div>
			  <div class="col-lg-12 col-md-12 col-xs-12 engine_info margin-top-5" style='padding-left:5px;padding-bottom:0px;padding-right:5px;'> 
			  	<table class="table table-thin   text-center table-set" style="min-width:100px;margin-bottom:0px !important;">
				   <tbody id="aircraftstatusList">
					</tbody>
				</table>
			  </div>
			</div>
           </div>
		   <div class="panel panel-primary margin-bottom-0" id='status_main_leftsecondDiv' style='border-top:0px;'>
		    <div class="panel-body padding-left-0 padding-right-0" >
               
                   <div id="using_json"></div>
                
			</div>
           </div>
	    </div>	    		    
		    <div class="col-lg-9 col-md-9 col-xs-9  padding-right-0"  id='status_main_rightDiv'>
            <div id="status_main_rightFirstDiv"  >		      
                   <div class="col-lg-12 col-xs-12 widget-body clearfix padding-left-0 padding-right-0 ">
                	<ul id="myTab" class="nav nav-tabs tabNew-style">
                      	<li class="active">
                      	<a id="aaa" href="#Dropdown" onclick="aircraftstatus.loadAircaraftMaintenanceMonitoring()" data-toggle="tab" aria-expanded="false">
                      		<div class="font-size-12 line-height-12">飞机维修项目监控</div>
			                <div class="font-size-9 line-height-9">Maintenance Monitoring</div>
                      	</a>
                      	</li>
                      	<li class="">
                      	<a href="#eo" data-toggle="tab" onclick="aircraftstatus.loadEOMonitoring()" aria-expanded="false">
                      		<div class="font-size-12 line-height-12">EO监控</div>
			                <div class="font-size-9 line-height-9">EO Monitoring</div>
			             </a>
                      	</li>
                      	<li class="">
                      	<a href="#po" data-toggle="tab" onclick="aircraftstatus.loadPOMonitoring()" aria-expanded="false">
                      		<div class="font-size-12 line-height-12">生产指令监控</div>
			                <div class="font-size-9 line-height-9">Production Order Monitoring</div>
			             </a>
                      	</li>
                      	<li class="">
                      	<a href="#nrc" data-toggle="tab" onclick="aircraftstatus.loadNRCWorkOrder()" aria-expanded="false">
                      		<div class="font-size-12 line-height-12">其他指令</div>
			                <div class="font-size-9 line-height-9">Other Command</div>
                      	</a>
                      	</li>
                      <div id="status_main_rightSecondDiv_block" class="pull-right" style='height:1px;padding-right:8px;margin-top:0px;display:none;'>
						<img src="${ctx}/static/images/up.png" onclick='aircraftstatus.blockBottom()' style="width:33px;cursor:pointer;"></div>
                    </ul>
                    <div id="myTabContent" class="tab-content" style='padding-bottom:5px;'>
                    
                      	<div class="tab-pane fade in active" id="Dropdown" style="overflow-x: auto;width: 100%;">
							<%@ include file="/WEB-INF/views/produce/aircraftstatus/aircraft_maintenance_status_main.jsp"%>
                      	</div>
                      	
                      	<div class="tab-pane fade" id="eo" style="overflow-x: auto;width: 100%;">
                      		<%@ include file="/WEB-INF/views/produce/aircraftstatus/eo_status_main.jsp"%>
                      	</div>
                      	
                      	<div class="tab-pane fade" id="po" style="overflow-x: auto;width: 100%;">
                      		<%@ include file="/WEB-INF/views/produce/aircraftstatus/po_status_main.jsp"%>
                      	</div>
                      	
                      	<div class="tab-pane fade" id="nrc" style="overflow-x: auto;width: 100%;">
                      		<%@ include file="/WEB-INF/views/produce/aircraftstatus/nrc_workOrder_status_main.jsp"%>
                      	</div>  	
		                  </div>
		              </div>
		              <div class='clearfix'></div>
			     </div>
				<div class="clearfix"></div>
				<div >
               		<%@ include file="/WEB-INF/views/produce/aircraftstatus/aircraftstatus_performHistory_main.jsp"%>
               	</div>


		</div>
				<div class='clearfix'></div>
				</div>
			</div>
		</div>
	</div>
	

<%@ include file="/WEB-INF/views/open_win/surplus.jsp"%><!-------剩余对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/aircraftstatus/aircraftstatus_main.js"></script>
<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>