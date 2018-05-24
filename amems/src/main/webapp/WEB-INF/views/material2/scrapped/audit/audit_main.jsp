<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>报废审核</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<style>
	.double-p-style{
	height:16px;
	line-height:16px;
	}
	</style>
	<script type="text/javascript">
		var id = "${param.id}";
		var pageParam = '${param.pageParam}';
	</script>
	<script type="text/javascript">
		$(document).ready(function(){
			//回车事件控制
			$(this).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyword_search']").trigger('click'); //调用当前窗口的查询
					}else{
						audit_main.searchRevision(); //调用主列表页查询
					}
				}
			});
		});
	</script>	
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class='col-xs-12 ' style='background:white;padding-left:0px;padding-right:0px;padding-top:0px;'>
	<div class='col-sm-3 col-xs-12' style='padding-left:0px;padding-right:10px;' id="left_div">
	<!-- 待审核 -->
    <div class="panel panel-primary left_first_content">
		    <!-- panel-heading -->
			<div class="panel-heading">
				<div class="font-size-12 line-height-12">待审核</div>
				<div class="font-size-9 line-height-9">To be audited</div>
			</div>
			<div class="panel-body padding-left-0 padding-right-0 left_first_body padding-top-0" >
				<table class='table text-center table-thin table-hover table-set' style='margin-bottom:0px !important' id="packing_list_table">
			    <tbody id='auditList'>
			    </tbody>
			    </table>
			</div>
			<div class="panel-footer" style="padding-top:5px;padding-bottom:5px;padding-right:8px;">
			<div class="col-xs-12 padding-left-0 padding-right-0 text-right" >
				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" id="audit_main_reject" onclick ="audit_main.reject()">
					<div class="font-size-12">审核驳回</div>
					<div class="font-size-9">Reject</div>
				</button>
				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-left-5" id="audit_main_pass" onclick="audit_main.pass()">
					<div class="font-size-12">审核通过</div>
					<div class="font-size-9">Pass</div>
				</button>
			</div>
			<div class="clearfix"></div>
			</div>
    </div>
    <div class="left_second_content">
    <div class="panel panel-primary" style="border:0px;">
		    <!-- panel-heading -->
			<div class="panel-heading" >
				<div class="font-size-12 line-height-12">附件清单</div>
				<div class="font-size-9 line-height-9">File List</div>
			</div>
			<div class="panel-body padding-left-0 padding-right-0 padding-top-0 padding-bottom-0" >
				<table class='table text-center table-thin table-hover' style='border-bottom:1px solid #d5d5d5;margin-bottom:0px !important;'>
			    <thead>
				    <tr>
					    <td>
						    <div class="font-size-12 line-height-18" >文件名称</div>
							<div class="font-size-9 line-height-18">Name</div>
						</td>
						<td>
						    <div class="font-size-12 line-height-18" >文件大小</div>
							<div class="font-size-9 line-height-18">Size</div>
						</td>
				    </tr>
			    </thead>
			    <tbody id='attachmentList'>
			    </tbody>
			    </table>
			</div>
    </div>
    <div class="panel panel-primary margin-bottom-0" style="border:0px;border-top:1px solid #d5d5d5;">
		    <div class="panel-heading">
				<div class="font-size-12 line-height-12">流程记录</div>
				<div class="font-size-9 line-height-9">Process record</div>
			</div>
			<div class="panel-body padding-left-0 padding-right-0 padding-top-0 padding-bottom-0" >
				<table class='table text-center table-thin table-hover' style='border-bottom:1px solid #d5d5d5;margin-bottom:0px !important;'>
			    <thead>
				    <tr>
					    <td>
						    <div class="font-size-12 line-height-18" >操作人</div>
							<div class="font-size-9 line-height-18">Operator</div>
						</td>
						<td>
						    <div class="font-size-12 line-height-18" >操作时间</div>
							<div class="font-size-9 line-height-18">Date</div>
						</td>
						<td>
						    <div class="font-size-12 line-height-18" >操作说明</div>
							<div class="font-size-9 line-height-18">Desc</div>
						</td>
				    </tr>
			    </thead>
			    <tbody id='processList'>
			    </tbody>
			    </table>
			</div>
    </div>
    <div class="clearfix"></div>
    </div>
	</div>
	<div class='col-sm-9 col-xs-12 padding-right-0 padding-left-0' id="right_div">
    	<div style="position:absolute;left:-8px;" class='toggle-btn-style'>
			<i id="" class="icon-caret-left cursor-pointer" style="font-size: 22px;" onclick="toggleBtnHandle(this)"></i>
		</div>
		<div class="panel panel-primary">
		    <!-- panel-heading -->
			<div class="panel-heading">
				<div class="font-size-12 line-height-12">审核信息</div>
				<div class="font-size-9 line-height-9">Audit info</div>
			</div>
			<div class="panel-body" id="">
			  <div class='searchContent margin-top-0 row-height' >
			  <!-- 搜索框start -->
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='部件号/部件名称/规格/型号/批次号/序列号'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" style="padding-right:0px !important;">
			                    <button id="" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="audit_main.searchRevision();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                	</div>
						</div>
					</div>
					<div class="clearfix"></div>
			  </div>
		   <!-- 审核列表 --> 
		   <div  class='table_pagination'>
		   <div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th class='colwidth-5'>
									<div class="font-size-12 line-height-18" >序号</div>
									<div class="font-size-9 line-height-18">Item</div>
								</th>
								<th class='colwidth-9 sorting' onclick="audit_main.orderBy('HCLX')" id="HCLX_order">
									<div class="font-size-12 line-height-18" >物料类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class='colwidth-15 sorting' onclick="audit_main.orderBy('BJH')" id="BJH_order">
									<div class="important">
									<div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-18">PN</div></div>
								</th>
								<th class='colwidth-13 sorting' onclick="audit_main.orderBy('PCH')" id="PCH_order" >
								<div class="important">
									<div class="font-size-12 line-height-18">批次/序列号</div>
									<div class="font-size-9 line-height-18">BN/SN</div></div>
								</th>
								<th class='colwidth-13 sorting' onclick="audit_main.orderBy('GG')" id="GG_order" >
								<div class="important">
										<div class="font-size-12 line-height-18">规格/型号</div>
										<div class="font-size-9 line-height-18">Spec/Model</div></div>
							   </th>
							   <th class='colwidth-15 sorting' onclick="audit_main.orderBy('CKH')" id="CKH_order" >
									<div class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Position</div>
							   </th>
							   <th class='colwidth-5' >
									<div class="font-size-12 line-height-18">报废数量</div>
									<div class="font-size-9 line-height-18">QTY</div>
							   </th>
							   <th class='colwidth-10 ' >
									<div class="font-size-12 line-height-18">航材成本</div>
									<div class="font-size-9 line-height-18">Cost</div>
							   </th>
							   <th class='colwidth-13 '  >
									<div class="font-size-12 line-height-18">库存信息</div>
									<div class="font-size-9 line-height-18">Inventory info</div>
							   </th>
							  
							</tr> 
						</thead>
						<tbody id="detailList">				
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination_list"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/material2/scrapped/audit/audit_modal.jsp"%><!--审批弹窗 -->
<script type="text/javascript" src="${ctx}/static/js/thjw/material2/scrapped/audit/audit_main.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>