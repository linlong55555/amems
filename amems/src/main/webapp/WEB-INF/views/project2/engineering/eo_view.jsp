<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>EO管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style>
label{
font-weight:normal;
}
</style>
<script type="text/javascript">
var todo_ywid = "${param.todo_ywid}";
var todo_lyid = "${param.todo_lyid}";

$(document).ready(function(){
	//导航
	Navigation(menuCode,"查看工程指令","View Bulletin");
	$("#NavigationBar ul li:last-child")
	.after("<a href='javascript:printEO();' class=' btn btn-primary padding-top-0 padding-bottom-0 pull-right  margin-right-8' ><div class='font-size-12'>打印</div><div class='font-size-9'>Print</div></a>");
});
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="id" value="${id}" >
	 <div class="page-content" id="EOViewModal">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body">       		
	              		<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			                    <div class="font-size-12 margin-topnew-2">EO编号</div>
								<div class="font-size-9">EO No.</div>
		                    </span>
		                   <div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
		                    	<input  class="form-control" id="eobh_add"  name="" disabled="disabled" type="text" maxlength="">
		                  </div>
		                </div>
		                <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		                   <span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			                    <div class="font-size-12 margin-topnew-2">版本</div>
								<div class="font-size-9" >Rev.</div>
							</span>
							 <div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							 <div class='input-group input-group-new'>
								<input class="form-control" id="bb_add"  name="" disabled="disabled" type="text" maxlength="" onpaste="return false">
							
								<div class="input-group-addon" id="new_bbh_span" style="display: none" >
		                     		<span id="new_bbh"></span>
		                     		← 
		                     		<a id="old_bbh" href="#" onclick="showHistoryView()" ></a>
		                     		&nbsp;&nbsp;&nbsp;
		                     	</div>
		                     	<div class='input-group-addon' title="历史列表  List" id="old_bbhlist_span" style="display:none ;margin-left:10px;">
									<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"  style="float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
								</div>
							</div>
							</div>
						</div>	
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		                   <span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			                    <div class="font-size-12 margin-topnew-2">下发生产</div>
			                    <div class="font-size-9" >To Prod</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
		                        <label class='margin-right-5 label-input' style='color:#333;'><input type='radio' disabled="disabled" name='isXfsc_add' checked="checked" value="1"/>&nbsp;是</label>
								<label class='label-input' style='color:#333;'><input type='radio' name='isXfsc_add' disabled="disabled" value="0"/>&nbsp;否</label>
							</div>
	                	</div>
	                	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<input  id="rii_add" type="checkbox" maxlength="8" disabled="disabled" style='vertical-align:-4px;'>&nbsp; RII
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
							</div>
						</div>	
	                	<div class="col-xs-12 input-group input-group-new form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			                    <div class="font-size-12 margin-topnew-2">标题</div>
								<div class="font-size-9" style='margin-top:4px;'>Title</div>
		                    </span>
		                    <div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-0">
	                   		 <textarea class='form-control' id="eozt_add" disabled="disabled" style='height:55px;'></textarea>
	                    	</div>
	                    </div>
	              		<div class='clearfix'></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">机型</div>
								<div class="font-size-9">A/C Type</div>
							</span>						 
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">					
								<input type="text" id="jx_add" disabled="disabled" class="form-control" disabled="disabled" />
							</div>
						</div>								 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">ATA章节号</div>
								<div class="font-size-9">ATA</div>
							</label>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">								
									<input type="text"  id="zjhName_add" class="form-control" disabled="disabled"/>													   
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">颁发日期</div>
								<div class="font-size-9">Issue Date </div>
							</span>
							
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control datepicker" id="bfrq_add" disabled="disabled"  data-date-format="yyyy-mm-dd" readonly="readonly" type="text" maxlength="" >
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">首次技术支援</div>
								<div class="font-size-9">First Support</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
								<label class='margin-right-5 label-input'>&nbsp;<input  checked="checked" name="scjszy_add" disabled="disabled" type="radio" maxlength="8" value="1" >&nbsp;是&nbsp;&nbsp;</label>
								<label class='label-input'>&nbsp;<input disabled="disabled"   name="scjszy_add" type="radio" maxlength="8" value="0" >&nbsp;否</label>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">级别</div>
								<div class="font-size-9">Class</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
								<label class='margin-right-5 label-input'>&nbsp;<input  checked="checked"   type="radio" maxlength="8" name="jb_add" value="9" >&nbsp;紧急&nbsp;&nbsp;</label>
								<label class='label-input'>&nbsp;<input disabled="disabled" type="radio" maxlength="8"  name="jb_add" value="1" >&nbsp;一般</label>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">工作类别</div>
								<div class="font-size-9">Job Type</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="gzlx_add" disabled="disabled" />					
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		                   <span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			                    <div class="font-size-12 margin-topnew-2">状态</div>
								<div class="font-size-9" >Status</div>
							</span>
							 <div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="zt_add"  name="" disabled="disabled" type="text" maxlength="" onkeyup='eo_add_alert_Modal.clearNoNumTwo(this)' onpaste="return false">
							</div>
						</div>
						<div class="clearfix"></div>
						<div  class="col-xs-12 padding-left-0 padding-right-0 form-group" id="EOViewModal_gbyy_div">
							<label class="col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">关闭原因</div>
								<div class="font-size-9">Close the reason</div>
							</label>
							<div class="col-xs-11 padding-leftright-8">
								<textarea class='form-control' style="height:75px" id="ezdjsyy" name="ezdjsyy" disabled="disabled"  maxlength="150">
								</textarea>
							</div>
						</div>
						
						<div class="clearfix"></div>
							<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- 技术评估单 -->
						<div class="clearfix"></div>
		
						<%@ include file="/WEB-INF/views/project2/engineering/applicable_settings.jsp"%>
						<!-- 工作概述 -->
						 <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#summaryCollapsed" class="collapsed">
							<div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div>
							<div class="pull-left">
							<div class="font-size-12">工作概述</div>
							<div class="font-size-9 ">Summary</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="summaryCollapsed" class="panel-collapse collapse" >
						<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">工作概述</div>
								<div class="font-size-9 ">Summary</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<textarea class="form-control" id="gzgs_add"  disabled="disabled" name="" maxlength=""style='height:55px;'></textarea>
							</div>
						</div>							 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">符合性</div>
								<div class="font-size-9">Compliance</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="fhx_add" disabled="disabled"  name="" type="text" maxlength=""/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">类别</div>
								<div class="font-size-9">Category</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="eoLB_add" type="text" disabled="disabled"/>							
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">分级</div>
								<div class="font-size-9">Classification</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="eofj_add" type="text" disabled="disabled" />								
							</div>
						</div>
						<div class="clearfix"></div>
						</div>
						</div>
						</div>
						<!-- 工作概述 -->
						<!-- 改版记录开始 -->
						 <%@ include file="/WEB-INF/views/project2/engineering/revision_record.jsp"%> 
						<!-- 改版记录结束 -->
						
						<!-- 维修改装对象开始 -->
						<%@ include file="/WEB-INF/views/project2/engineering/maintenance_modification_obj.jsp"%>
						<!-- 维修改装对象结束 -->
						
						<!-- 重量与平衡开始 -->
						<%@ include file="/WEB-INF/views/project2/engineering/weight_balance.jsp"%>
						<!-- 重量与平衡结束 -->
						
						<!-- 参考文件开始 -->
						<div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#referenceCollapsed" class="collapsed">
							<div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div>
							<div class="pull-left">
							<div class="font-size-12">参考文件 </div>
							<div class="font-size-9 ">Reference</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="referenceCollapsed" class="panel-collapse collapse" >
							<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
							<%@ include file="/WEB-INF/views/common/project/reference_list_edit.jsp"%>
						    </div>
						</div>
						</div>
						
						<!-- 参考文件结束 -->
						<!-- 影响手册 -->
					 	<div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#manualsCollapsed" class="collapsed">
							<div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div>
							<div class="pull-left">
							<div class="font-size-12">影响手册</div>
							<div class="font-size-9 ">Manuals Affect</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="manualsCollapsed" class="panel-collapse collapse" >
						<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">影响手册</div>
								<div class="font-size-9 ">Manuals Affect</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id="yxsc_add" disabled="disabled"  maxlength="" style='height:55px;'></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">参考工时</div>
								<div class="font-size-9">MHRs</div>
							</label>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-9 padding-leftright-8">
								<div class="input-group input-group-new" >
									<input id="jhgsRs" name="jhgsRs" type="text" class="form-control" disabled="disabled"   onpaste="return false"/>
			                    	<span class="input-group-addon">人&nbsp;x</span>
			                    	<input id="jhgsXss" name="jhgsXss" type="text" class="form-control" disabled="disabled"  onpaste="return false"/>
			                     	<span class="input-group-addon">时=<span id="bzgs">0</span>时</span>		                 
			                	</div>
							</div>
						</div>
						
						<div class="clearfix"></div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">器材价格</div>
								<div class="font-size-9">Material Price</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group">
									<input type="text" id="qcjg_add" disabled="disabled" class="form-control padding-left-3 padding-right-3" >
									<div class="input-group-btn">
										 <button type="button" id="btqcjgdw" style="height: 34px;color:#555 !important;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
									        <ul class="dropdown-menu dropdown-menu-right" id="qcjgdw_add">
										         
									        </ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">颁发器材准备通知单</div>
								<div class="font-size-9">MON Distributed</div>
							</span>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group input-group-new">
			                    <span class="input-group-addon">
			                     	<label>
			                     		<input  checked="checked" value="1" name="bfqczb_add" type="radio" maxlength="8" disabled="disabled"  >&nbsp;是&nbsp;&nbsp;
			                     	</label>
			                    </span>
			                    <span class="input-group-addon">
				                     <label>
				                    	 <input  value="0" name="bfqczb_add" type="radio" maxlength="8" disabled="disabled" >&nbsp;否
				                     </label>
			                    </span>
			                    <input type="text" id="bfqczbtzd_add" class="form-control" disabled="disabled" />
			                    <!-- <span class="input-group-addon">
			                    <label>
			                     	<input  id="rii_add" type="checkbox" maxlength="8" disabled="disabled" >&nbsp; RII
			                    </label>
			                    </span> -->
			                	</div><!-- /input-group -->
							</div>
						</div>
						<div class="clearfix"></div>
						</div>
						</div>
						</div>
						<!-- 影响手册 -->
						<!-- 受影响的出版物      开始 -->
						<%@ include file="/WEB-INF/views/project2/engineering/publication.jsp"%> 
						<!-- 受影响的出版物      结束 -->
						<!-- 受影响的机载软件清单  -->
					   <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#softwareCollapsed" class="collapsed">
							<div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div>
							<div class="pull-left">
							<div class="font-size-12">受影响的机载软件清单 </div>
							<div class="font-size-9 ">Onboard Software List Affected</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="softwareCollapsed" class="panel-collapse collapse" >
						<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						 <div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">受影响的机载软件清单</div>
								<div class="font-size-9 ">Onboard Software List Affected</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id="sycjzrjqd_add" disabled="disabled"  name=""  maxlength="" style='height:55px;'></textarea>
							</div>
						</div>
						<div class="clearfix"></div>					
					</div>
					</div>
					</div>
					<!-- 受影响的机载软件清单  -->
					<!-- 电气负载数据 -->
					 <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#electricalCollapsed" class="collapsed">
							<div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div>
							<div class="pull-left">
							<div class="font-size-12">电气负载数据</div>
							<div class="font-size-9 ">Electrical Load Data</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="electricalCollapsed" class="panel-collapse collapse" >
						<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">电气负载数据变化</div>
							<div class="font-size-9">Electrical Load Data Change</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
                        	<input type='radio' name='is_dqfzsj_add' disabled="disabled" checked="checked" value="1"/>&nbsp;是
							<input type='radio' name='is_dqfzsj_add' disabled="disabled" value="0"/>&nbsp;否
						</div>
					 </div>
					 <div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
							<div class="font-size-12 ">电气负载数据</div>
							<div class="font-size-9 ">Electrical Load Data</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
							<textarea class="form-control" id="dqfzsj_add"  disabled="disabled" name=""  maxlength="" style='height:55px;' ></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">维修项目的相关性</div>
							<div class="font-size-9">Item Related</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="wxxmxgx_add" disabled="disabled" type="text" maxlength="">
						</div>
					</div>
					
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">反馈要求</div>
							<div class="font-size-9">Feedback Request</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="fkyq_add" type="text" maxlength="" disabled="disabled" />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>航材需求申请单</div>
							<div class="font-size-9">Material order notice</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="hcxqsqd_add"  type="text" maxlength="" disabled="disabled">
						</div>
					</div>
					<div class="clearfix"></div>
					</div>
					</div>
					</div>
					<!-- 电气负载数据 -->
					<!-- 原有零件处理 -->
					<div class="panel panel-primary">
					<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
						<a data-toggle="collapse" data-parent="#accordion" href="#disposalCollapsed" class="collapsed">
						<div class="pull-left">
						<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
						</div>
						<div class="pull-left">
						<div class="font-size-12">原有零件处理 </div>
						<div class="font-size-9 ">Disposal of Removed Parts</div>
						</div>
						<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
						<div class="clearfix"></div>
						</a>
					</div>
					<div id="disposalCollapsed" class="panel-collapse collapse" >
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">原有零件处理</div>
							<div class="font-size-9">Disposal of Removed Parts</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="yyljcl_add"  name="" type="text" maxlength="" disabled="disabled">
						</div>
					</div>					
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">互换信息</div>
							<div class="font-size-9">Exchange Info</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="hhxx_add"  name="" type="text" maxlength="" disabled="disabled" >
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>其他</div>
							<div class="font-size-9">Others</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="qt_add"  name="" type="text" maxlength="" disabled="disabled">
						</div>
					</div>
					<div class="clearfix"></div>
					</div>
					</div>
					</div>
					<!-- 原有零件处理 -->
				
				    <!-- 分发 -->
					<div class="panel panel-primary">
					<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
						<a data-toggle="collapse" data-parent="#accordion" href="#distributionCollapsed" class="collapsed">
						<div class="pull-left">
						<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
						</div>
						<div class="pull-left">
						<div class="font-size-12">分发 </div>
						<div class="font-size-9 ">Distribution</div>
						</div>
						<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
						<div class="clearfix"></div>
						</a>
					</div>
					<div id="distributionCollapsed" class="panel-collapse collapse" >
					<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">分发</div>
								<div class="font-size-9">Distribution</div>
							</label>
							<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input type="text"  value="" name="ff" class="form-control readonly-style" placeholder='' maxlength="100" readonly="readonly" id="ff" ondblclick="eo_add_alert_Modal.openzrdw(this);">
									<input type="hidden" value="" name="ffid" class="form-control " placeholder='' maxlength="" id="ffid">
							    </div>
							</div>
						</div>
						
						<div class="clearfix"></div>
					</div>
					</div>
					</div>
					
				    <!-- 分发 -->
				
					<%-- <div id="attachments_list_edit" >
					<div  class="panel panel-primary" >
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="#accordion" href="#attachmentCollapsed" class="collapsed">
							<div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div>
							<div class="pull-left">
							<div class="font-size-12">附件</div>
							<div class="font-size-9 ">Attachment</div>
							</div>
							<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div>
							</a>
						</div>
						<div id="attachmentCollapsed" class="panel-collapse collapse" >
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%>
						</div>
						</div>
						
					</div> --%>
					
                   <!-- 监控项设置 -->
              		<div id="jkxsz_frame_package"></div>
              	   <!-- 监控项设置 -->
              	   <!-- 计划 -->
              	   <%@ include file="/WEB-INF/views/project2/engineering/eo_plan.jsp"%> 
              	   <!-- 计划 -->
              	   <!-- 工时/停场时间 -->
              	   <%@ include file="/WEB-INF/views/project2/engineering/stopping_time.jsp"%> 
              	   <!-- -工时/停场时间 --> 
              		
              		<div class="clearfix" style="padding-bottom: 10px;"></div>
              		
              		<!-- 器材清单列表 -->	
              	    <%@ include file="/WEB-INF/views/common/project/equipment_list_edit.jsp"%>
					<!-- 工具设备列表 -->
					<%@ include file="/WEB-INF/views/common/project/tools_list_edit.jsp"%>
            	     <!-- 工具 -->
            	     <!-- 索赔  -->
					 <%@ include file="/WEB-INF/views/project2/engineering/claim_demage.jsp"%>
					 <!-- 索赔--> 
              		
              		
              	     <!-- 工作内容开始 -->
              	     <div  class="panel panel-primary" >
					 <div class="panel-heading bg-panel-heading" >
							<div class="font-size-12 ">工作内容</div>
							<div class="font-size-9">Work Content</div>
						</div>
						<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
							
							<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2"></div>
									<div class="font-size-9"></div>
								</label>
								
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-2 col-md-2 col-sm-3 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">工作内容附件</div>
										<div class="font-size-9">Attachment</div>
									</label>
									<div id="work_content_attachments_single_edit" class="col-lg-10 col-md-10 col-sm-9 col-xs-8 padding-leftright-8">
										<input id="gznrfjid" name="gznrfjid" type="hidden" />
										<%@ include file="/WEB-INF/views/common/attachments/attachments_single_edit.jsp"%><!-- 加载附件信息 -->
									</div>
								</div>
							</div>
							
							<div class='clearfix'></div>
							
							<%@ include file="/WEB-INF/views/common/project/work_content_list_edit.jsp"%><!-- 工作内容 -->
							
							<div class='clearfix'></div>
				
						</div>
						</div>
						 <!-- 工作内容结束 -->
						 
						 
						 <!-- 加载附件信息 -->
              	 	<div id="attachments_list_edit" style="display:none">
              	    <div  class="panel panel-primary" style='margin-bottom:0px;'>
              	     	<div class="panel-heading bg-panel-heading" >
						<!-- <div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style"> -->
							<!-- <a data-toggle="collapse" data-parent="#accordion" href="#attachmentCollapsed" class="collapsed"> -->
							<!-- <div class="pull-left">
							<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
							</div> -->
							<!-- <div class="pull-left"> -->
							<div class="font-size-12">附件</div>
							<div class="font-size-9 ">Attachment</div>
							<!-- </div> -->
							<!-- <i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
							<div class="clearfix"></div> -->
							<!-- </a> -->
						</div>
						<!-- <div id="attachmentCollapsed" class="panel-collapse collapse" > -->
						
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%>
						
						<!-- </div> -->
					</div>
					</div>
					<!-- 加载附件信息 -->
						<div class='clearfix'></div> 
	              	   	<!-- 流程 开始 -->
	              	   	 <div  style='margin-top:10px;'>
						 	<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp"%> 
						 </div>
						<!-- 流程 结束 -->
		</div>
	</div>
</div>
    <script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/eo_view.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/jkx_setting.js"></script> <!-- 监控项设置 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/monitor/monitor_unit.js"></script> <!-- 监控项单位设置 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/engineeringworkorder_main.js"></script> <!-- 当前页脚本 -->
	
	<%@ include file="/WEB-INF/views/project2/engineering/engineeringOrder_choosepn.jsp"%><!--  部件 -->
    <%@ include file="/WEB-INF/views/project2/engineering/engineeringOrder_chooseplane.jsp"%> <!-- 飞机 --> 
    <%@ include file="/WEB-INF/views/project2/engineering/eo_history_win.jsp"%><!------EO历史版本对话框 -------->
    <%@ include file="/WEB-INF/views/open_win/inventory_distribution_details_view.jsp"%><!-------库存分布详情 -------->
	<%@ include file="/WEB-INF/views/open_win/equivalent_substitution_view.jsp"%><!-------等效替代 -------->
    <script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 历史版本控件对话框 -->
</body>
</html>
