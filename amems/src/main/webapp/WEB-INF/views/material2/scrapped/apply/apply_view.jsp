<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
	<body class="page-header-fixed" id="apply_open">
		
		<input type="hidden" name="bulletinId" id="bulletinId" value='${id}' >
		<input type="hidden" name="userId" id="userId" value='${user.id}' >
		<input type="hidden" name="zt" id="zt" value='${zt}' >		
		<!-------导航Start--->
		<!-- BEGIN HEADER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode,"查看报废申请","View TScrapped Apply");
});
</script>
	<div class="clearfix"></div>
	
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content" >
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2"><span class="color-red"></span>报废单编号</div>
							<div class="font-size-9 ">Scrap No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" id="apply_open_bfdh" name="apply_open_bfdh" class="form-control"/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">报废日期</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" id="apply_open_bfrq" disabled="disabled" name="apply_open_bfrq" class="form-control date-picker"  data-date-format="yyyy-mm-dd"/>
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">单据状态</div>
							<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input id="apply_open_zt" name="apply_open_zt" class="form-control" type="text" readonly >							
						</div> 
                 	</div>
                 	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">申请人</div>
							<div class="font-size-9 ">Applicant</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input type="hidden" name="apply_open_sqbmid" id="apply_open_sqbmid" class="form-control" readonly/>
							<input type="hidden" name="apply_open_sqrid" id="apply_open_sqrid" class="form-control" readonly/>
							<input type="text" name="apply_open_sqr" id="apply_open_sqr" class="form-control" readonly/>
						</div> 
                 	</div>
                 	<div class="clear"></div>
                 	 <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">说明</div>
							<div class="font-size-9 ">Desc</div>
						</span>
						<div class="ol-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea rows="" cols="" id="apply_open_sm" disabled="disabled" name="apply_open_sm" style="height:55px;" class="form-control"></textarea>
						</div> 
                 	</div>
                 	<div class='clearfix'></div>
	           
	            <div class="panel panel-primary" >
					<div class="panel-heading bg-panel-heading">
						   <div class="font-size-12 ">报废部件信息</div>
						  <div class="font-size-9">Scrapped Info</div>
					</div>
					<div class="panel-body" >
						<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-8 form-group ">
							<span class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12  margin-topnew-2">审核人</div>
									<div class="font-size-9 ">Auditor</div>
								</span>
								<div class="col-lg-6 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-8">
									<div class=" input-group col-xs-12" style="width:100%">
								    <input id="apply_open_shrid" class="form-control" type="hidden" >
								    <input id="apply_open_shr" disabled="disabled" class="form-control" type="text">
									</div>
								</div> 
	                 	</div>
	                 	 <div class="col-xs-12 padding-left-0 padding-left-8 padding-right-0 form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
								<div class="font-size-12  margin-topnew-2">部件列表</div>
								<div class="font-size-9 ">List</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0">
								<table id="apply_open_table" class="table table-thin table-bordered table-striped table-hover table-set ">
									<thead >
										<tr>											
											<th class="colwidth-7">
												<div class="font-size-12 line-height-18">物料类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 line-height-18">部件</div>
												<div class="font-size-9 line-height-18">PN</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">批次/序列号</div>
												<div class="font-size-9 line-height-18">BN/SN</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">规格/型号</div>
												<div class="font-size-9 line-height-18">Spec/Model</div>
											</th>
											<th class="colwidth-9"  >
												<div class="font-size-12 line-height-18">位置</div>
												<div class="font-size-9 line-height-18">Position</div>
											</th>
											<th class="colwidth-5"  >
												<div class="font-size-12 line-height-18">报废数量</div>
												<div class="font-size-9 line-height-18">Qty</div>
											</th>
											<th class="colwidth-9"  >
												<div class="font-size-12 line-height-18">产权</div>
												<div class="font-size-9 line-height-18">Right</div>
											</th>
											<th class="colwidth-9"  >
												<div class="font-size-12 line-height-18">成本</div>
												<div class="font-size-9 line-height-18">Cost</div>
											</th>
											<th class="colwidth-15"  >
												<div class="font-size-12 line-height-18">库存信息</div>
												<div class="font-size-9 line-height-18">Inventory info</div>
											</th>									
										</tr>
									</thead>
									<tbody id="apply_open_detail">
									</tbody>
								</table>
							</div> 
	                 	</div>
                 	</div>
	                <div class='clearfix'></div>  
	               </div> 
	               <div id="apply_open_attachments_list_edit"  >
						<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					</div>
					<%@ include file="/WEB-INF/views/quality/reviewreformmeasures/measures_record.jsp"%>
				    <div class='clearfix'></div>       
           	</div>
           	<div class='clearfix'></div>  
           	</div>																							
		</div>		
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/scrapped/apply/apply_view.js"></script>
</body>
</html>