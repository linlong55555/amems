<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>EO管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content">
		<div class="panel panel-primary">
		    <!-- panel-heading -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<!-- 搜索框 -->
			    <div  class='searchContent'>
			    <!-- 上传按钮  -->
				<div class="pull-left padding-left-0">
					<a href="javascript:add();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission margin-right-8" >
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
					<a href="javascript:addOne();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission margin-right-8" >
						<div class="font-size-12">三屏</div>
						<div class="font-size-9">Add</div>
					</a> 
					<a href="javascript:addTwo();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission margin-right-8" >
						<div class="font-size-12">二屏</div>
						<div class="font-size-9">Add</div>
					</a> 
					<a href="javascript:addThree();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission margin-right-8" >
						<div class="font-size-12">单屏</div>
						<div class="font-size-9">Add</div>
					</a> 
				</div>
				<div class="clearfix"></div>
				</div>
				<div class=''>
				<div class="col-md-3 col-sm-6 col-xs-12 padding-left-0 margin-top-8">
					<a href="javascript:add();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" 
					permissioncode="project2:airworthiness:main:01">
						<div class="font-size-12">上传</div>
						<div class="font-size-9">Upload</div>
					</a> 
				</div>
				<div class="col-md-3 col-sm-6  col-xs-12 padding-left-0 padding-right-0 margin-top-8" >
						<span class="col-lg-3 col-md-4 col-sm-5 col-xs-6 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">文件类型</div>
							<div class="font-size-9">File Type</div>
						</span>
						<div class="col-lg-9 col-md-8 col-sm-7 col-xs-6 padding-leftright-8">
							<select class='form-control' >
							<option value="" >显示全部All</option>
							</select>
						</div>
				</div>
				<div class="col-md-3 col-sm-6  col-xs-12 padding-left-0 padding-right-0 margin-top-8" >
						<span class="col-lg-3 col-md-4 col-sm-5 col-xs-6  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-lg-9 col-md-8 col-sm-7 col-xs-6 padding-leftright-8 label-input-div" >
							<label class='margin-right-5 label-input'><input type='radio' name='radio99' checked="checked"/>&nbsp;待处理</label>
							<label class='label-input'><input type='radio' name='radio99'/>&nbsp;已处理</label>
						</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-8" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='文件编号/ATA/主题/备注'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
                    <div class="input-group-addon btn-searchnew-more">
	                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="search();">
						<div class='input-group'>
						<div class="input-group-addon">
						<div class="font-size-12">更多</div>
						<div class="font-size-9 margin-top-5" >More</div>
						</div>
						<div class="input-group-addon">
						 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
						</div>
						</div>
			   			</button>
                	</div>
				</div>
				</div>
				
				<!-- <div class=" pull-right padding-left-0 padding-right-0">
					<div class="pull-right padding-left-10 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='文件编号/ATA/主题/备注'  class="form-control" id="keyword_search" >
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
	                   		<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="search();">
								<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
								<div class="pull-left padding-top-5">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
					   		</button>
	                    </div> 
					</div>
				</div> -->
				
				</div>
				<div class='table_pagination'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-10 table-height" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
								   <th>
									   <div class="font-size-12 line-height-18">操作</div>
							           <div class="font-size-9 line-height-18">Operation</div>
								   </th>
								   <th class='sorting'>
									   <div class="font-size-12 line-height-18">EO号</div>
							           <div class="font-size-9 line-height-18">EO</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-18">版本</div>
							           <div class="font-size-9 line-height-18">Version</div>
								   </th>
								   <th class='sorting'>
									   <div class="font-size-12 line-height-18">机型</div>
							           <div class="font-size-9 line-height-18">Models</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-18">关联评估单</div>
							           <div class="font-size-9 line-height-18">Associated</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-18">来源文件</div>
							           <div class="font-size-9 line-height-18">Source File</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-18">标题</div>
							           <div class="font-size-9 line-height-18">Title</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-18">状态</div>
							           <div class="font-size-9 line-height-18">State</div>
								   </th>
								   <th class='sorting'>
									   <div class="font-size-12 line-height-18">适用性</div>
							           <div class="font-size-9 line-height-18">Applicability</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-18">组织结构</div>
							           <div class="font-size-9 line-height-18">Organization</div>
								   </th>
								</tr>
							</thead>
							<tbody >
								<tr>
								<td><a href='#'>关联技术评估单AAAA</a></td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								</tr>
								<tr>
								<td><span class='pull-left'>111</span>
								<i class='icon-circle pull-right' style='font-size:25px;color:#ff9900;line-height:25px;vertical-align:middle;'></i>
								<div class='clearfix'></div>
								</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								<td>111</td>
								</tr>
							</tbody>
					</table>
				</div>
				
				<div class='clearfix'></div>
				
				</div>
				<!-- 隐藏的div -->
				<%-- <div class='displayContent'>
				   <div class='col-xs-3 padding-left-0' style='padding-right:4px;'>
				    <%@ include file="/WEB-INF/views/project2/engineering/engineering_topic.jsp"%>
				   </div>
				   <div class='col-xs-9 padding-right-0' style='padding-left:4px;'>
				   <%@ include file="/WEB-INF/views/project2/engineering/engineeringperform_list.jsp"%>
				   </div>
				</div> --%>
        	</div>
		</div>		
	</div>
    <%@ include file="/WEB-INF/views/project2/engineering/engineeringOrder_Add.jsp"%>
     <%@ include file="/WEB-INF/views/project2/engineering/engineeringOrder_Addone.jsp"%>
    <%@ include file="/WEB-INF/views/project2/engineering/engineeringOrder_AddTwo.jsp"%>
    <%@ include file="/WEB-INF/views/project2/engineering/engineeringOrder_AddThree.jsp"%>
     <%@ include file="/WEB-INF/views/project2/engineering/engineeringOrder_AddFour.jsp"%>
      <%@ include file="/WEB-INF/views/project2/engineering/engineeringOrder_AddFive.jsp"%>
    
<%--     <script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/engineeringworkorder_main.js"></script> --%>
	<script>
	$(document).ready(function(){
		
	    $(window).resize(function(){
	    	modalBodyHeight("myModal");
	    	modalBodyHeight("myModalSecond");
	    	searchModal("myModal","myModalFive","open_win_evaluation_modal_pagination");
	    })
	      $("#searchstatus").multiselect({
				   buttonClass: 'btn btn-default',
                   buttonWidth:'150px' ,
                   buttonHeight: '34px',
				   numberDisplayed:1,
                   includeSelectAllOption: true
	     })
	      $("#searchstatusone").multiselect({
				   buttonClass: 'btn btn-default',
                   buttonWidth:'150px' ,
                   buttonHeight: '34px',
				   numberDisplayed:1,
                   includeSelectAllOption: true
	     })
	      $("#searchstatustwo").multiselect({
				   buttonClass: 'btn btn-default',
                   buttonWidth:'150px' ,
                   buttonHeight: '34px',
				   numberDisplayed:1,
                   includeSelectAllOption: true
	     })
	      $("#searchstatusthree").multiselect({
				   buttonClass: 'btn btn-default',
                   buttonWidth:'150px' ,
                   buttonHeight: '34px',
				   numberDisplayed:1,
                   includeSelectAllOption: true
	     })
	     $("#searchstatusfour").multiselect({
			   buttonClass: 'btn btn-default',
               buttonWidth:'150px' ,
               buttonHeight: '34px',
			   numberDisplayed:1,
               includeSelectAllOption: true
     })
       $("#searchstatusfive").multiselect({
			   buttonClass: 'btn btn-default',
               buttonWidth:'150px' ,
               buttonHeight: '34px',
			   numberDisplayed:1,
               includeSelectAllOption: true
     })
	   
	})
	function add(){
		$("#myModal").modal("show");
		$('#myModal').on('shown.bs.modal', function () {
			modalBodyHeight("myModal")
			})
		
			/* alert(parseInt($(".modal").eq(0).css("width"))*0.66); */
	}
	/*  */
	function addOne(){
		$("#myModalOne").modal("show");
		$('#myModalOne').on('shown.bs.modal', function () {
			modalBodyHeight("myModalOne")
			})
	}
	function showOtherModal(){
		$("#myModalFour").modal("show");
		$('#myModalFour').on('shown.bs.modal', function () {
			modalSecondBodyHeight("myModal","myModalFour")
			})
	}
	function addTwo(){
		$("#myModalTwo").modal("show");
		$('#myModalTwo').on('shown.bs.modal', function () {
			modalBodyHeight("myModalTwo")
			})
	}
	function addThree(){
		$("#myModalThree").modal("show");
		$('#myModalThree').on('shown.bs.modal', function () {
			modalBodyHeight("myModalThree")
			})
	}
	function showModal(){
		$("#myModalSecond").modal("show");
		$('#myModalSecond').on('shown.bs.modal', function () {
			modalBodyHeight("myModalSecond");
			$("#searchstatus").multiselect({
				   buttonClass: 'btn btn-default',
	               buttonWidth:'400px' ,
	               buttonHeight: '34px',
				   numberDisplayed:1,
	               includeSelectAllOption: true
	     })
			})
			
		
		
	}
	function modalBodyHeight(id){
		//window高度
		var windowHeight = $(window).height()
	    //modal-footer的高度
		var modalFooterHeight=$("#"+id+" .modal-footer").outerHeight()||0;
		//modal-header 的高度
	    var modalHeaderHeight=$("#"+id+" .modal-header").outerHeight()||0;
		//modal-dialog的margin-top值
	    var modalDialogMargin=parseInt($("#"+id+" .modal-dialog").css("margin-top"))||0
	    //modal-body 的设置
		var modalBodyHeight=windowHeight-modalFooterHeight-modalHeaderHeight-modalDialogMargin*2-8;
	    $("#"+id+" .modal-body").attr('style', 'max-height:' + modalBodyHeight+ 'px !important;overflow: auto;margin-top:0px;');
	}
	function modalSecondBodyHeight(parentid,id){
		//window高度
		var windowHeight = $(window).height()
		
		var parentMargin=parseInt($("#"+parentid+" .modal-dialog").css("margin-top"))||0;
		
		var parentHeader=$("#"+parentid+" .modal-header").outerHeight()||0;
		
		var parentFooter=$("#"+parentid+" .modal-footer").outerHeight()||0;
		
		$("#"+id).css("margin-top",(parentHeader+10)+"px");
		//modal-footer的高度
		var modalFooterHeight=$("#"+id+" .modal-footer").outerHeight()||0;
		//modal-header 的高度
	    var modalHeaderHeight=$("#"+id+" .modal-header").outerHeight()||0;
		//modal-dialog的margin-top值
	    var modalDialogMargin=parseInt($("#"+id+" .modal-dialog").css("margin-top"))||0
	    //modal-body 的设置
	    var parentHeight=parseInt($("#"+parentid).css("height"))||0;
	    
	    var modalBodyHeight=parentHeight-parentHeader-10-parentFooter-10-modalFooterHeight-modalHeaderHeight-8-parentMargin*2;
	    
	    $("#"+id+" .modal-body").attr('style', 'max-height:' + modalBodyHeight+ 'px !important;overflow: auto;margin-top:0px;'); 
	}
	function openFiveModal(){
		$("#myModalFive").modal("show");
		$('#myModalFive').on('shown.bs.modal', function () {
			searchModal("myModal","myModalFive","open_win_evaluation_modal_pagination");
			})
	}
	
	</script>
</body>
</html>
