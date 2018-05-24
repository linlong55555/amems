<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工具快速借用/归还</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<link href="${ctx}/static/lib/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-switch/js/bootstrap-switch.min.js"></script>
<link rel="stylesheet"
	href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<style>
.borrow-p{
	margin-bottom:0px;
	line-height:18px;
	min-height:18px;
}
.table-set>tbody>tr>td>p { overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.statusbar_boder_none tbody tr td{
border-top:0px;
}
.bootstrap-switch{
border-radius:0px;
}
.bootstrap-switch .bootstrap-switch-container{
border-radius:0px;
}
.bootstrap-switch .bootstrap-switch-handle-on{
border-radius:0px;
}
.bootstrap-switch .bootstrap-switch-handle-off{
border-radius:0px;
}
.bootstrap-tagsinput{
border-radius:0px;
}
</style>
</head>
<body class="page-header-fixed">
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<div class="col-md-7 col-sm-7 col-xs-7 padding-left-0 padding-right-0 margin-top-0 row-height" >
				<div class="col-xs-12 input-group input-group-search form-group">
				   <input type="text" placeholder='请输入待借用工具编号'  class="form-control" id="keyword_tool">
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" onclick="borrowReturn.search()" class=" btn btn-primary padding-top-1 padding-bottom-1"  style='margin-right:0px !important;'>
						<div class="font-size-12">匹配</div>
						<div class="font-size-9">Matching</div>
                  		</button>
                    </div>
                    <div class="input-group-addon btn-searchnew-more" style='text-align:center;'>
						<div>
							<input id="borrowOrReturn" type="checkbox" checked="checked" data-on-text="借用" data-off-text="归还" data-on-color="success" data-off-color="warning" />
						</div>
                	</div>
				</div>
			</div>
			<div class='clearfix'></div>
			<div class='col-xs-12 padding-left-0 padding-right-0 margin-top-0'  id='fastBorrowReturnBodyHeight'>
				<div class='col-xs-7 padding-left-0 childContent' style='padding-right:3px;'>
					<!-- 待借用工具 -->
					<div  class="panel panel-primary" >
					<div class="panel-heading bg-panel-heading" id='borrowed_tool_header'>
						<div class="font-size-12 chinese_title" >待借用工具</div>
						<div class="font-size-9 english_title" > Tools to be borrowed</div>
					</div>
					<div class="panel-body padding-leftright-8" >
					<div  class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-set" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
							<thead>                              
								<tr>
									<th class="colwidth-3 notView">
										<button class="line6 line6-btn" onclick="borrowReturn.chooseTool()" type="button">
											<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
									    </button>
									</th>
									<th class="selectAllImg" id="checkAll" style='text-align:center;vertical-align:middle;width:50px;'>
										<a href="#" onclick="SelectUtil.performSelectAll('borrow_return_tool_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
										<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('borrow_return_tool_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
									</th>
									<th class="colwidth-12">
										<div class="font-size-12 line-height-18" >件号/序列号</div>
										<div class="font-size-9 line-height-18">PN/SN</div>
									</th>
									<th class="colwidth-11">
										<div class="font-size-12 line-height-18" >型号/规格</div>
										<div class="font-size-9 line-height-18">Model/Spec</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18" >名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									</th>
									<th class="colwidth-9">
										<div class="font-size-12 line-height-18" >仓库/库位</div>
										<div class="font-size-9 line-height-18">Store</div>
									</th>
								</tr> 
							</thead>
							<tbody id="borrow_return_tool_list">
								<tr class="no-data"><td class='text-center' colspan="6">暂无数据 No data.</td></tr>
							</tbody>
						</table>
					</div>
					<div class='clearfix'></div>
					<!-- 长期文本框 -->
					<div  class='row-height' style='padding-top:8px;border-top:1px dotted #ddd;'>
					<div class="col-xs-12 input-group input-group-search">
					     <div class="input-group-addon" >
	                    	<label  style='color:#333;font-weight:normal;'>
	                    	<input type='checkbox' style="vertical-align:text-bottom;" id='longCheckbox' onclick='borrowReturn.toggleLongBorrow(this)' />&nbsp;长期借用&nbsp;&nbsp;</label>
	                    </div>
					    <input type="text" class="form-control" id='longReturn' style='visibility:hidden;' placeholder='备注说明' >
	                </div>
					</div>
					</div>
				</div>
				</div>
				<div class='col-xs-5 padding-right-0 childContent' style='padding-left:3px;' >
					<!-- 借用人 -->
					<div  class="panel panel-primary" >
					<div class="panel-heading bg-panel-heading" id='borrower_header'>
						<div class="font-size-12 chinese_title">借用人</div>
						<div class="font-size-9 english_title">Borrower</div>
					</div>
					<div class="panel-body padding-leftright-8">
					<div  class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-set" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
							<thead>                              
								<tr >
									<th class="colwidth-3 notView">
										<button class="line6 line6-btn" onclick="borrowReturn.chooseUser()" type="button">
											<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
									    </button>
									</th>
									<th class="colwidth-3">
										<div class="font-size-12 line-height-18" >选择</div>
										<div class="font-size-9 line-height-18">Choose</div>
									</th>
									<th class="colwidth-12">
										<div class="font-size-12 line-height-18" >编号/姓名</div>
										<div class="font-size-9 line-height-18">No./Name</div>
									</th>
									<th class="colwidth-12">
										<div class="font-size-12 line-height-18" >所在部门</div>
										<div class="font-size-9 line-height-18">Department</div>
									</th>
									<th class="colwidth-3">
										<div class="font-size-12 line-height-18" >性别</div>
										<div class="font-size-9 line-height-18">Sex</div>
									</th>
								</tr> 
							</thead>
							<tbody id="borrow_return_user_list">
								<tr class="no-data"><td class='text-center' colspan="5">暂无数据 No data.</td></tr>
							</tbody>
						</table>
					</div>
					</div>
				</div>
				</div>
			<div class='clearfix'></div>
			</div>
			<div class='clearfix'></div>
			<div class='footConfirm'>
			<div class='col-xs-12 padding-left-0 padding-right-0' >
			<div id="alert_msg_div" class="alert alert-success" style='margin-bottom:8px;border-radius:0px;display: none;'>
			 <button type="button" onclick='borrowReturn.closeAlertMessage(this)' class='close'>
                &times;
            </button>
			</div>	
			</div>
			<div class='col-xs-12 padding-left-0 padding-right-0 text-right padding-bottom-5'>
				 <button onclick="borrowReturn.submitData()" class="btn btn-primary padding-top-1 padding-bottom-1 form-group"  >
					 	<div class="font-size-12 line-height-12">确定</div>
						<div class="font-size-9">Confirm</div>
				 </button>
			</div>
			<div class='clearfix'></div>
			</div>
			</div>
		</div>
	</div>
	<script	src="${ctx}/static/js/thjw/material2/toolborrowreturn/toolborrowreturn_main.js"	type="text/javascript"></script>
	<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
	<%@ include file="/WEB-INF/views/material2/store/check/stock_open.jsp"%><!--选择库存  -->
	<%@ include file="/WEB-INF/views/common/produce/material_cq.jsp" %> <!-- 产权弹出框 -->
</body>
</html>