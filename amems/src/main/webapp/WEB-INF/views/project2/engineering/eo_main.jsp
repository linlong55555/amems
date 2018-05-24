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


	<div class="page-content" id='engineer_mainpage'>
		<div class="panel panel-primary">
		    <!-- panel-heading -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-top-0 padding-bottom-0" >
			<!-- 搜索框 -->
			    <div  class='searchContent'>
				<div class="col-md-3 col-sm-12 col-xs-12 padding-left-0 margin-top-8">
					<a href="javascript:addEO();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left margin-right-8" >
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
					<a href="javascript:auditing();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission margin-right-8" >
						<div class="font-size-12">审核</div>
						<div class="font-size-9">TODO</div>
					</a>
					<a href="javascript:approval();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" >
						<div class="font-size-12">批准</div>
						<div class="font-size-9">TODO</div>
					</a> 
				</div>
				<div class="col-md-3 col-sm-6  col-xs-12 padding-left-0 padding-right-0 margin-top-8" >
						<span class="col-lg-3 col-md-4 col-sm-5 col-xs-6 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机型</div>
							<div class="font-size-9">Models</div>
						</span>
						<div class="col-lg-9 col-md-8 col-sm-7 col-xs-6 padding-left-8 padding-right-0">
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
						<div class="col-lg-9 col-md-8 col-sm-7 col-xs-6 padding-left-8 padding-right-0">
							<select class='form-control' >
							<option value="" >显示全部All</option>
							</select>
						</div>
				</div>
				<div class="col-md-3 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-8" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='EO/主题/ATA/备注'  class="form-control" id="keyword_search" >
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
				<div class='clearfix'></div>
				<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none search-height border-cccccc" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">编制日期</div>
							<div class="font-size-9">Prepare date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker"/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control "  name="dprtcode">
								<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				<div class="clearfix"></div>
				</div>
				<!-- 更多搜索 -->
				<div class='clearfix'></div>
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
									   <div class="font-size-12 line-height-18">关联技术评估单</div>
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
							<tbody id='engineering_list'>
							<!-- 	<tr>
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
								</tr> -->
							</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="engineering_pagination"></div>
				
				<div class='clearfix'></div>
				
				</div>
				<!-- 隐藏的div -->
				<div class='displayContent' style='display:none;'>
				   <div class='col-xs-3 padding-left-0' style='padding-right:4px;'>
				    <%@ include file="/WEB-INF/views/project2/engineering/engineering_topic.jsp"%>
				   </div>
				   <div class='col-xs-9 padding-right-0' style='padding-left:4px;'>
				   <%@ include file="/WEB-INF/views/project2/engineering/engineeringperform_list.jsp"%>
				   </div>
				   <div class='clearfix'></div>
				</div>
        	</div>
		</div>		
	</div>
    <%@ include file="/WEB-INF/views/project2/engineering/eo_close.jsp"%>
    <%@ include file="/WEB-INF/views/project2/engineering/eo_execution .jsp"%>
    <!--  审核批准 开始  朱超 -->
    <%@ include file="/WEB-INF/views/open_win/batchApprovel.jsp"%>
    <!--  审核批准 开始  朱超 -->
     <%@ include file="/WEB-INF/views/project2/engineering/eo_add.jsp"%>
    
    
    <script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/engineeringworkorder_main.js"></script>
	<script>
	$(document).ready(function(){
		
	    $(window).resize(function(){
	    	modalBodyHeight("myModal");
	    	modalBodyHeight("myModalSecond");
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
	
	function addEO(){
		$("#EOModal").modal("show");
		$('#EOModal').on('shown.bs.modal', function () {
			/* modalBodyHeight("EOModal") */
			})
	}
	/*  */
	function addOne(){
		$("#myModalOne").modal("show");
		$('#myModalOne').on('shown.bs.modal', function () {
			modalBodyHeight("myModalOne")
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
	
	
	
	//**********************朱超************************
	//审核
	function auditing(){
		 
		/* auditApprovalComp.show({
			title:'审核'
				,allow : [{value:'100011'},{value:'100011q'}]
				,notAllow : [{value:'b00011q'}]
				,allowCallback : function (){[]}
				,notAllowCallback : function (){[]}
				,exeCallback: function (){
					Eray.alert('执行操作。。。');
				}
		}); */

		var idArr = [];
		var approvalArr = [];
		var approvalNotArr = [];
		
		/* var spzt = isApprovel?2:1;
		$("#list").find("tr input:checked").each(function(){
			var index = $(this).attr("index");
			var fixedcheckitem = mainData[index];
			if(fixedcheckitem.zt == 1 && fixedcheckitem.spzt == spzt){
				idArr.push(fixedcheckitem.id);
				approvalArr.push(fixedcheckitem.djbh);
	   	 	}else{
	   	 		approvalNotArr.push(fixedcheckitem.djbh);
	   	 	}
		}); */
		
		BatchApprovelModal.show({
			approvalArr:approvalArr,//审核可操作的数据
			approvalNotArr:approvalNotArr,//审核不可操作的数据
			isApprovel:false,//判断审核还是批准
			callback: function(data){//回调函数
				if(idArr.length > 0){
					batchApproval(idArr,data,isApprovel);
				}
			}
		});
		
	}
	
	//批准
	function approval(){
		 
		/* auditApprovalComp.show({
			title:'批准'
				,allow : [{value:'300011'},{value:'500011q'}]
				,notAllow : [{value:'b0y0011'},{value:'tb00011q'}]
				,allowCallback : function (){[]}
				,notAllowCallback : function (){[]}
				,exeCallback: function (){
					Eray.alert('执行操作。。。');
				}
		}); */
		var idArr = [];
		var approvalArr = [];
		var approvalNotArr = [];
		
		/* var spzt = isApprovel?2:1;
		$("#list").find("tr input:checked").each(function(){
			var index = $(this).attr("index");
			var fixedcheckitem = mainData[index];
			if(fixedcheckitem.zt == 1 && fixedcheckitem.spzt == spzt){
				idArr.push(fixedcheckitem.id);
				approvalArr.push(fixedcheckitem.djbh);
	   	 	}else{
	   	 		approvalNotArr.push(fixedcheckitem.djbh);
	   	 	}
		}); */
		
		BatchApprovelModal.show({
			approvalArr:approvalArr,//审核可操作的数据
			approvalNotArr:approvalNotArr,//审核不可操作的数据
			isApprovel:true,//判断审核还是批准
			callback: function(data){//回调函数
				if(idArr.length > 0){
					batchApproval(idArr,data,isApprovel);
				}
			}
		});
		
	}
	
	//**********************朱超************************
	
	</script>
</body>
</html>
