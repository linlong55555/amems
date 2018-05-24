<%@ page contentType="text/html; charset=utf-8" language="java" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看合同</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
		.spacing {
			margin-left:3px;
			margin-right:3px;
		}
	</style>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="id" value='${id}' >
		<!-------导航Start--->
		<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<!-- BEGIN CONTENT -->
	<div class="page-content" id="contract_view" >
		<div class="panel panel-primary ">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body panel-body-new">
				<div class="col-lg-12 col-sm-12 col-xs-12 col-md-12 padding-left-0 padding-right-0">
	 				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">合同号</div>
							<div class="font-size-9">Contract No.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input class="form-control" id="hth_v" type="text" readonly />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">合同日期</div>
							<div class="font-size-9">Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input class="form-control" id="htrq_v" type="text" readonly/>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">合同类型</div>
							<div class="font-size-9">Type</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input class="form-control" id="htlx_v" type="text" readonly/>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">编制人</div>
							<div class="font-size-9">Compactor</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input class="form-control" id="bzrstr_v" type="text" readonly/>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">币种</div>
							<div class="font-size-9">Currency</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input class="form-control" id="biz_v" type="text" readonly/>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">交付方式</div>
							<div class="font-size-9">Delivery Mode</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input class="form-control" id="jffs_v" type="text" readonly/>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">相关方</div>
							<div class="font-size-9">Relevant Party</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input class="form-control" id="xgfms_v" type="text" readonly/>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">状态</div>
							<div class="font-size-9">Status</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input class="form-control" id="zt" type="text" maxlength="50" readonly />
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">备注</div>
							<div class="font-size-9">Note</div>
						</label>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea id="bz" style="height: 55px;" class="form-control" readonly></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div class="clearfix"></div>	
				
				<div id="attachments_list_edit" style="display:none">
				
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				
				</div>
				<!-- 底部显示内容 -->
				<div id='bottom_hidden_content' class='displayContent' >
				<ul id="myChildTab" class="nav nav-tabs tabNew-style" style="padding-top:0px !important;">
			    <li class="active" >
				<a id='mgntDetail' href='#mgntDetailTab' data-toggle="tab"  >
					<div class="font-size-12 line-height-12">明细</div>
                    <div class="font-size-9 line-height-9">Detail</div>
                 </a>
				</li>
				<li class=""  >
				<a href='#mgntPaymentTab' data-toggle="tab"  >
					<div class="font-size-12 line-height-12">收付款</div>
                    <div class="font-size-9 line-height-9">Feedback</div>
                  </a>
				</li>
				</ul>
				<div id="" class="tab-content" style='padding:0px;'>
					<div id="mgntDetailTab" class="tab-pane fade active in " style="" >
					     <!-- 合同明细 -->
						<%@ include file="/WEB-INF/views/material2/contract/mgnt/contract_detail.jsp" %>
					</div>
					<div id="mgntPaymentTab" class="tab-pane fade">
					<%@ include file="/WEB-INF/views/material2/contract/mgnt/mgnt_payment.jsp" %>
					</div>
				</div>
				</div>
																								
			</div>
		</div>
	</div>		
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/contract/mgnt/contract_mgnt_view.js"></script>
</body>
</html>