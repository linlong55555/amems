<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>航材领用申请单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
.bootstrap-tagsinput {
  width: 90% ;
 
}

</style>
<link rel="stylesheet" href="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<script type="text/javascript">
	var pageParam = '${param.pageParam}';
</script>

</head>
<body>

	<div class="page-content ">

		<!-- 页面操作区 Start -->
		<div class="panel panel-primary">
			<div class="panel-heading"  id="NavigationBar"></div>
			<input type="hidden" id="operateType" value="${operateType}">
			<input type="hidden" id="dprtcode" value="${user.jgdm}">
			<input type="hidden" id="id" value="${id}">
			
			<div class="panel-body">
				<form id="form">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">基本信息</div>
							<div class="font-size-9 ">Basic Info</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">领用申请单号</div>
								<div class="font-size-9 line-height-18">Requisition No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="lysqdh" id="lysqdh" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">飞机注册号</div>
								<div class="font-size-9 line-height-18">A/C REG</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select class='form-control' id='fjzch' name="fjzch" >
								</select>
								<input id="fjzchyc" class="form-control" name="fjzchyc" />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">申请人</div>
								<div class="font-size-9 line-height-18">Applicant</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<input type="text" class="form-control" name="sqrmc" id="sqrmc"  readonly />
								<input type="hidden" name="sqbmid" id="sqbmid" />
								<input type="hidden"  name="sqrid" id="sqrid" />
								<span id="selectUserBtn" class="input-group-btn">
									<button onclick="javascript:selectUser();" type='button' class="btn btn-primary">
										<i class="icon-search"></i>
									</button>
								</span>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">申请日期</div>
								<div class="font-size-9 line-height-18">Application Date</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" name="sqrq" id="sqrq" data-date-format="yyyy-mm-dd" readonly />
							</div>
						</div>
						<div class="view-input display-none col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">状态</div>
								<div class="font-size-9 line-height-18">State</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="zt" id="zt" readonly />
							</div>
						</div>
						<div class="view-input display-none col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单人</div>
								<div class="font-size-9 line-height-18">Creator</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="zdr" id="zdr" readonly />
							</div>
						</div>
						<div class="view-input display-none col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control" name="zdsj" id="zdsj" readonly />
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color:red" id="remark">*</span>领用原因</div>
								<div class="font-size-9 line-height-18">Cause</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
								<textarea class="form-control" id="lyyy" name="lyyy" maxlength="150" ></textarea>
							</div>
						</div>
					</div>
				</form>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
					<div class="clearfix"></div>
					
					<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class=" pull-left margin-right-10" >
							<div class="font-size-16 line-height-18">领用清单</div>
							<div class="font-size-9 ">Requisition list</div>
						</div>	
					 	<div class="pull-left ">
							<button id="btnGoAdd" style="display:none" title="增加 Add" class="btn btn-primary " onclick="javascript:window.history.go(-1)">
								<i class="icon-plus cursor-pointer"></i>
							</button>
			          	</div>
					</div>
					
	            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">

						<!-- start:table -->
						<div class="margin-top-10 ">
						<div class="overflow-auto">
							<table id="detailTable" class="table table-thin table-bordered table-striped table-hover" style="min-width:2400px">
								<thead>
							   		<tr>
										<th class="colwidth-7" style="display:none">
											<div class="font-size-12 notwrap">操作</div>
											<div class="font-size-9 notwrap">Operation</div>
										</th>
										<th class="colwidth-3">
											<div class="font-size-12 notwrap">序号</div>
											<div class="font-size-9 notwrap">No.</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 notwrap">件号</div>
											<div class="font-size-9 notwrap">P/N</div>
										</th>
										<th class="colwidth-25">
											<div class="font-size-12 notwrap">英文名称</div>
											<div class="font-size-9 notwrap">F.Name</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">中文名称</div>
											<div class="font-size-9 notwrap">CH.Name</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 notwrap">厂家件号</div>
											<div class="font-size-9 notwrap">MP/N</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 notwrap">航材类型</div>
											<div class="font-size-9 notwrap">Airmaterial type</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">序列号/批次号</div>
											<div class="font-size-9 notwrap">S/N</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 notwrap">适航证号</div>
											<div class="font-size-9 notwrap">Certificate</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 notwrap">仓库</div>
											<div class="font-size-9 notwrap">Store</div>
										</th>
										<th class="colwidth-20">
											<div class="font-size-12 notwrap">库位</div>
											<div class="font-size-9 notwrap">Storage location</div>
										</th>
										<th id="kcslTh" class="colwidth-7">
											<div class="font-size-12 notwrap">库存数</div>
											<div class="font-size-9 notwrap">Store Num</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 notwrap">单位</div>
											<div class="font-size-9 notwrap">Unit</div>
										</th>
										<th class="colwidth-10">
											<div class="font-size-12 notwrap">领用数</div>
											<div class="font-size-9 notwrap">Requisition Num</div>
										</th>
										<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideZjqkContentAll()">
											<div class="font-size-12 notwrap">关联工单</div>
											<div class="font-size-9 notwrap">W/O</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="detailTBody">
								
								</tbody>
							</table>
							</div>
						</div>
						<!-- end:table -->
			     		<div class="clearfix"></div>
					 </div> 
				</div>
				<div class="clearfix"></div>
				<div class="text-right margin-bottom-10">
                    <button id="btnSave" style="display:none" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save()">
                    	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div>
					</button>
					<button id="btnSubmit" style="display:none" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:submit()">
                    	<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div>
					</button>
               		<button class="btn btn-primary padding-top-1 padding-bottom-1 " onclick="pageback();">
               			<div class="font-size-12">返回</div>
						<div class="font-size-9">Back</div>
					</button>
            	</div>
			</div>
		</div>
		<!-- 页面操作区 End -->
	<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/work_order.jsp"%>
	
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/requisition/requisition_view.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>