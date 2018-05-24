<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<style>
.titile_up{height:35px;  font-size:14px; font-weight:bold; padding:0; margin:0;}
.titile_up h1 { font-size: 14px; margin: 0; height: 35px; line-height: 35px;}
.titile_up span { font-size: 14px; margin: 0; height: 35px; line-height: 35px; padding-left:10px;}
.small_one { height: 50px;  border: 1px solid #d4d4d4; font-size: 16px; font-weight: bold; line-height: 50px; text-align: center; background: #d8ecf5;display: block;  overflow: hidden; text-overflow: ellipsis;white-space: nowrap;}
.engine_info{ }
.engine_info p{height: 20px; line-height: 20px; margin:0; white-space: nowrap;text-overflow: ellipsis; overflow: hidden;}
.engine_info span{ display: block;overflow: hidden; float: left;  white-space: nowrap; text-overflow: ellipsis;}
.font_weight{font-weight:bold;}
.border_line{border-bottom:1px solid #ccc;}
.color_green{color:#02af59;}
.color_red{color:#ff0033;}
</style>

<div id="current_monitor_data_alert_Modal" style='display: none;padding:0px;'>
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
		  		<table class="table table-thin text-center table-set" style="min-width:100px;margin-bottom:0px !important;">
			   		<tbody id="current_monitor_data_list">
					</tbody>
				</table>
		  	</div>
		</div>
	</div>
	<div class='clearfix'></div>
</div>
<!--  弹出框结束-->
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/current_monitor_data.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>