<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维修人员培训检测</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style>
.sjzdTable tbody tr{
cursor:pointer;
}
.sjzdTable thead tr th{
border:0px;
}
.sjzdTable tbody tr td{
padding-top:5px;
padding-right:5px;
border-left:0px !important;
border-right:0px !important;
}
.list-group-item:last-child {
	border-bottom-left-radius: 0px;
    border-bottom-right-radius: 0px;
}
.list-group-item{
cursor:pointer;
border-left:0px;
border-right:0px;
}
.list-group-item:hover{
background:#f5f5f5;
}
.list-group-item.active{
background:#dbe2f7;
}


</style>
</head>
<body class="page-header-fixed">
	<script type="text/javascript">
	var pageParam = '${param.pageParam}';
	$(document).ready(function(){
		//导航
		Navigation(menuCode);
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					$('#trainingTestingMainSearch').trigger('click');;//调用主列表页查询
				}
			}
		});
	});
	</script>
	
<input maxlength="20" type="hidden" id="userId" value="" />
<input type="hidden" id="viewKcbh" value="" />
<div class="page-content ">
 <div class='col-xs-12 ' style='background:white;padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;'>
     <!-- 左部  -->
	 <div class='col-sm-4 col-xs-12' style='padding-left:0px;padding-right:10px;padding-top:0px;padding-bottom:0px;' id="left_div">
	  	<div class="panel panel-primary">
					<div class="panel-heading">
					<div class="pull-left">
						<div class="font-size-12 line-height-12">检测</div>
	                    <div class="font-size-9 line-height-9">Detection</div>
	                </div>
	                <div class="pull-right" style="margin-top:-2px;">   
	                    <a href="#" id="trainingTestingMainSearch" onclick="trainingTesting_main.search()"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 "  >
							<div class="font-size-12" style="line-height:12px;">搜索</div>
							<div class="font-size-9" style="line-height:12px;">Search</div>
						</a>
					</div>
						 <div class="clearfix"></div>
					</div>
					<div class="panel-body padding-left-0 padding-right-0" >
					   <div class="row-height">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<!--  <span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">请选择维修人员</div>
								<div class="font-size-9 ">Personnel</div>
							</span>  -->
							<div class="col-sm-12 col-xs-12 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="ryText_search" placeholder="请选择人员"  disabled="disabled" class="form-control readonly-style" ondblclick="trainingTesting_main.openPersonelWin()" type="text">
									<input id="ryid_search" class="form-control" type="hidden">
			                    	<span id="" class="input-group-addon btn btn-default" onclick="trainingTesting_main.openPersonelWin()">
			                    		<i class="icon-search"></i>
			                    	</span>
					            </div>
						  </div>
						</div>
						
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
							<!--  <span  class="col-lg-3 col-md-3 col-sm-3 col-xs-1 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">请选择或输入岗位</div>
								<div class="font-size-9 ">Business</div>
							</span>  -->
							<div class="col-sm-12 col-xs-12 padding-leftright-8">
								<div class="input-group col-xs-12">
									<input id="gwText_search" onchange="trainingTesting_main.gwChange()" class="form-control" type="text" placeholder="请选择或输入岗位">
									<input id="gwid_search" class="form-control" type="hidden">
			                    	<span id="" class="input-group-addon btn btn-default" onclick="trainingTesting_main.openBusiness()">
			                    		<i class="icon-search"></i>
			                    	</span>
					            </div>
						  </div>
						</div>
						<div class="clearfix"></div>
						</div>
					
						<div id="triningTesting_main_top_div" class="col-lg-12 col-md-12 padding-left-8 padding-right-8" style="overflow-x: auto;width: 100%;">
								<table class="table table-thin table-bordered table-striped table-hover text-center table-set " id="trainingTesting_main_table" >
									<thead>
								   		<tr>
											<%-- <th class="viewCol fixed-column colwidth-5 selectAllImg" style="width: 75px;">
												<a href="#" onclick="SelectUtil.performSelectAll('triningTesting_main_top_div')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('triningTesting_main_top_div')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											</th> --%>
											<th class="colwidth-5" >
												<div class="font-size-12 line-height-18">操作</div>
												<div class="font-size-9 line-height-18">Operation</div>
											</th>
											<th class="colwidth-10" >
												<div class="font-size-12 line-height-18">岗位编号</div>
												<div class="font-size-9 line-height-18">Business No.</div>
											</th>
											<th class=" colwidth-20 ">
												<div class="font-size-12 line-height-18">岗位名称</div>
												<div class="font-size-9 line-height-18">Business Name</div>
											</th>
												
							 		 </tr>
									</thead>
									<tbody id="trainingTesting_main_tbody">
									
									</tbody>
								</table>
							</div>
					</div>
	    </div>
	 </div>
	 <!-- 右边 -->
	 <div class='col-sm-8 col-xs-12' style='padding-left:0px;padding-right:0px;' id="right_div">
	 	<!-- 收缩效果 -->
		 <div id="" style="position: absolute; left: -8px; top: 250px;">
		 <i class="cursor-pointer icon-caret-left"   style="font-size: 22px;" onclick="toggleIcon(this)"></i>
		 </div>
		 
	 	<div class="panel panel-primary">
				<div class="panel-heading">
				<div class="pull-left">
					<div class="font-size-12 line-height-12">检测结果</div>
                    <div class="font-size-9 line-height-9">Detection Results </div>
                </div>
                <div class="pull-right">   
                     <div class='text-right'>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input id="isAll" onchange="trainingTesting_list.isAllChange()" checked="checked" style="vertical-align:text-bottom;" type="checkbox">&nbsp;仅显示未培训课程&nbsp;&nbsp;
						</label>
						</div>
				</div>
					 <div class="clearfix"></div>
				</div>
				<div class="panel-body padding-left-0 padding-right-0" >
				
					<div class="col-lg-12 col-md-12 padding-left-8 padding-right-8 pull-right margin-bottom-10" style="overflow-x: auto;width: 100%;">
							<table id="trainingTesting_list_table" class="table table-thin table-bordered table-striped table-hover text-center table-set " id="quality_check_list_table" >
								<thead>
							   		<tr>
										<th class="colwidth-15" >
											<div class="font-size-12 line-height-18">岗位</div>
											<div class="font-size-9 line-height-18">Business</div>
										</th>
										<th class=" colwidth-7 ">
											<div class="font-size-12 line-height-18">课程代码</div>
											<div class="font-size-9 line-height-18">Course Code</div>
										</th>
										<th class=" colwidth-20 ">
											<div class="font-size-12 line-height-18">课程名称</div>
											<div class="font-size-9 line-height-18">Course Name</div>
										</th>
										<th class=" colwidth-5">
											<div class="font-size-12 line-height-18">培训情况</div>
											<div class="font-size-9 line-height-18">Training Situation</div>
										</th>
											
						 		 </tr>
								</thead>
								<tbody id="trainingTesting_list_tbody">
								
								</tbody>
							</table>
						</div>
				</div>
    	</div>
	 </div>
 </div>

</div>
<%@ include file="/WEB-INF/views/open_win/personel_tree_multi.jsp"%><!-------用户对话框 -------->
<%@ include file="/WEB-INF/views/open_win/business_win_list.jsp"%><!------选择岗位对话框 -------->
<%@ include file="/WEB-INF/views/open_win/trainingRecords_win.jsp"%><!------查看培训计划话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/training/testing/trainingTesting_main.js"></script>
</body>
</html>
