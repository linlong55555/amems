<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看缺陷保留</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-content">
<input type="hidden" id="viewid" value="${viewid}">
<input type="hidden" id="id" value="${viewid}">
<input type="hidden" id="dprtcode" >
	<div class="panel panel-primary" >
		<div class="panel-heading" id="NavigationBar"></div>
		<form id="Defectkeep_Open_Modal" >
			<div class="panel-body padding-bottom-0 padding-leftright-8  padding-top-0">	 
				<div class="input-group-border margin-top-8 padding-left-0">
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">保留单编号</div>
							<div class="font-size-9 ">DD No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text" placeholder='不填写则自动生成'  maxlength="50" id="bldh" name="bldh" class="form-control padding-left-3 padding-right-3 bldh"  />
						</div>
					</div>
           			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">
								<span class="required" style="color: red">*</span>
								飞机注册号
							</div>
							<div class="font-size-9 ">A/C REG</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
							<select class="form-control noteditable" name="fjzch" id="Defectkeep_Open_Modal_fjzch" onchange="Project_Open_Modal.onchangeFjzch(this)">
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">机型</div>
							<div class="font-size-9 ">A/C Type</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
							<input type="text"   id="jx" class="form-control padding-left-3 padding-right-3"  disabled="disabled"/>
						</div>
					</div>
	   				<div class="div-hide col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">状态</div>
							<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
							<input type="text"  id="ztms" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
						</div>
					</div>
	     			<div id="lybhdiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">
								<span class="required" style="color: red">*</span>工单编号
							</div>
							<div class="font-size-9 ">Order No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group" style="width: 100%">
								<input type="hidden" id="addupdatebs" value="1">
								<input type="hidden" id="bs145">
								<input type="hidden"  name="gdid"  id="gdid">
								<input type="text"   id="gd" name='gd' class="form-control noteditable readonly-style colse" readonly="readonly" ondblclick='Defectkeep_Open_Modal.initSourceData();'>
								<span class="required input-group-btn" id="wxrybtn" >
									<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Defectkeep_Open_Modal.initSourceData()">
										<i class="icon-search cursor-pointer" ></i>
									</button>
								</span>
		                	</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">
								<span class="required" style="color: red">*</span>申请人
							</div>
							<div class="font-size-9 ">Applicant</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class="input-group" style="width: 100%">
								<input type="hidden"  name="sqrid"  id="sqrid">
								<input type="hidden"  name="sqrbmdm"  id="scSqrbmdm">
								<input type="text"  name="sqr" id="sqr" class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="Defectkeep_Open_Modal.openUser('sqr');">
								<span class="required input-group-btn" >
									<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Defectkeep_Open_Modal.openUser('sqr');">
										<i class="icon-search cursor-pointer" ></i>
									</button>
								</span>
		                	</div>
						</div>
					</div>
				 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">
								<span class="required" style="color: red">*</span>申请日期
							</div>
							<div class="font-size-9 ">Application Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
							<input type="text"  id="sqrq"  name="sqrq" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
						</div>
					</div>
				 	<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">缺陷描述</div>
							<div class="font-size-9 ">Defect Desc</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="noteditable form-control padding-left-3 padding-right-3" id="qxms"  
								style="height:55px" maxlength="1000"></textarea>
						</div>
				 	</div>
					<div class='clearfix'></div>
				</div>
				<div class="clearfix"></div>
				<%@ include file="/WEB-INF/views/produce/defectkeep/approval_info.jsp" %> <!--批准流程 -->
				<div class="clearfix"></div>
				<div id="attachments_list_edit" style="display:none">
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
				<div class="panel panel-default" id="wanchengxinxi_Model">
					<div class="panel-heading bg-panel-heading">
					 	<div class="font-size-12 ">关闭信息</div>
						<div class="font-size-9">Colse Info</div>
				    </div>
					<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	  	 	
						<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
							<div  class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">关闭保留缺陷措施</div>
									<div class="font-size-9">Measure</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class='form-control disabledgb' style="height:75px" id="gbyy" name="gbyy"  disabled="disabled" maxlength="1000">
									</textarea>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>
										工作者
									</div>
									<div class="font-size-9 ">Worker</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input class="form-control disabledgb"   disabled="disabled" id="gzz" name="gzz"  maxlength="100">
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>
										完成日期
									</div>
									<div class="font-size-9 ">Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input class="form-control disabledgb"   disabled="disabled" id="gzrq" name="gzrq"  maxlength="100">
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>
										关闭人
									</div>
									<div class="font-size-9 ">Close the person</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input class="form-control disabledgb"   disabled="disabled" id="gbrg" name="gbrg"  maxlength="100">
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>
										关闭时间
									</div>
									<div class="font-size-9 ">Closing Time</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input class="form-control disabledgb"   disabled="disabled" id="gbsjg" name="gbsjg"  maxlength="100">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default" id="zhidingjieshu_Model">
					<div class="panel-heading bg-panel-heading">
					 	<div class="font-size-12 ">指定结束信息</div>
						<div class="font-size-9">Close Info</div>
				    </div>
					<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	  	 	
						<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
							<div  class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">指定结束原因</div>
									<div class="font-size-9">Close the Reason</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class='form-control disabledgb' style="height:75px" id="gbyyy" name="gbyyy"  disabled="disabled" maxlength="1000">
									</textarea>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>
										指定结束人
									</div>
									<div class="font-size-9 ">Close the person</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input class="form-control disabledgb"   disabled="disabled" id="gbr" name="gbr"  maxlength="100">
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>
										指定结束时间
									</div>
									<div class="font-size-9 ">Closing Time</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input class="form-control disabledgb"   disabled="disabled" id="gbsj" name="gbsj"  maxlength="100">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="clearfix"></div>
	</div>	
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/defectkeep/defectkeep_open.js"></script><!--项目保留弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/defectkeep/defectkeep_find.js"></script><!--查看项目保留弹窗的js  -->
</body>
</html>