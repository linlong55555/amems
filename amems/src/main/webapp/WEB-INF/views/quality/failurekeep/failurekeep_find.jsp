<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看故障保留</title>
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
	    <div class="modal-body  " >
			<form id="FailureKeep_Open_Modal" >
				<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 
					<div class="input-group-border margin-top-8 padding-left-0">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group " >
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">
									<span class="required icon-bldh" style="color: red">*</span>保留单编号
								</div>
								<div class="font-size-9 ">DD No.</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
								<input type="text" id="bldh" name="bldh" class="form-control padding-left-3 padding-right-3 bldh" placeholder='不填写则自动生成'  maxlength="50"/>
							</div>
						</div>
	           			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">
									<span class="required" style="color: red">*</span>飞机注册号
								</div>
								<div class="font-size-9 ">A/C REG</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
								<select class="form-control noteditable"  id="FailureKeep_Open_Modal_fjzch" name="fjzch" onchange="FailureKeep_Open_Modal.onchangeFjzch(this)">
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
		   				<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group div-hide">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">状态</div>
								<div class="font-size-9 ">Status</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
								<input type="text"  id="ztms" class="form-control padding-left-3 padding-right-3 "  disabled="disabled"/>
							</div>
						</div>
						<div class='clearfix'></div>
					</div>
     				<div class="clearfix"></div>
  						<div class="panel panel-default padding-right-0">
					        <div class="panel-heading bg-panel-heading">
					        	<div class="font-size-12 ">基础信息</div>
								<div class="font-size-9">Basis Info</div>
						    </div>
							<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
								<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
					       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												ATA章节号
											</div>
											<div class="font-size-9">ATA</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden"  name="zjh"  id="zjh">
												<input type="text"  name="zjhms" class="form-control noteditable readonly-style colse" id="zjhms" readonly="readonly" ondblclick='FailureKeep_Open_Modal.initFixChapter();'>
												<span class="required input-group-btn" >
													<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="FailureKeep_Open_Modal.initFixChapter()">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<span class="required" style="color: red">*</span>航站
											</div>
											<div class="font-size-9 ">Station</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input type="text" id="hz" name="hz" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3" />
										</div>
								 	</div>
				 	     			<div id="sylbDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">来源类型</div>
											<div class="font-size-9 ">From</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
											<div class="input-group input-group-new">
							                    <span class="input-group-addon" style="padding-left:0px">
							                    	<span><input type='radio' class="noteditable" name='blly' onclick="FailureKeep_Open_Modal.onchangeblly();" value="1" />&nbsp;FLB&nbsp;</span>
							                    </span>
							                     <span class="input-group-addon">
							                    	 <span><input type='radio' class="noteditable" name='blly' onclick="FailureKeep_Open_Modal.onchangeblly();" value="2"/>&nbsp;NRC&nbsp;</span>
												 </span>
							                     <span class="input-group-addon">
							                    	 <span><input type='radio' class="noteditable" name='blly' onclick="FailureKeep_Open_Modal.onchangeblly();" value="9" checked/>&nbsp;N/A&nbsp;</span>
												 </span>
												<input type="text" class="form-control" style="visibility: hidden;">
						                	</div>
										</div>
									</div>
									<div id="lybhdiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">来源编号</div>
											<div class="font-size-9 ">From NO.</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden" id="addupdatebs" value="1">
												<input type="hidden" id="bs145">
												<input type="hidden"  name="bllyid"  id="bllyid">
												<input type="text"   id="blly"class="form-control noteditable readonly-style colse" readonly="readonly" ondblclick='FailureKeep_Open_Modal.initSource();'>
												<span class="required input-group-btn" id="wxrybtn" >
													<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="FailureKeep_Open_Modal.initSource()">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<span class="required" style="color: red">*</span>申请人
											</div>
											<div class="font-size-9 ">Applicant</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
											<div class="input-group" style="width: 100%">
												<input type="hidden"  name="scSqrid"  id="scSqrid">
												<input type="hidden"  name="scSqrbmdm"  id="scSqrbmdm">
												<input type="text"  name="scSqr" id="scSqr" class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="FailureKeep_Open_Modal.openUser('scSqr');">
												<span class="required input-group-btn" >
													<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="FailureKeep_Open_Modal.openUser('scSqr');">
														<i class="icon-search cursor-pointer" ></i>
													</button>
												</span>
						                	</div>
										</div>
									</div>
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<span class="required" style="color: red">*</span>
												申请日期
											</div>
											<div class="font-size-9 ">Date</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input type="text"  id="scSqrq" name="scSqrq" onchange="FailureKeep_Open_Modal.onchangebllx()" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
								 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">
												<span class="required" style="color: red">*</span>
												保留期限
											</div>
											<div class="font-size-9 ">Expiry Date</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input type="text"  id="scBlqx"   name="scBlqx" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
										</div>
									</div>
		            			 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
												<div class="font-size-12 margin-topnew-2">申请人执照号</div>
												<div class="font-size-9 ">Applicant No</div>
										</span>
										<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
											<input id="scSqrzzh" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3"  />
										</div>
									</div>
									<div class='clearfix'></div>
 				 		     		<div id="sylbDiv" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">保留类别</div>
										<div class="font-size-9 ">Cat</div>
									</span>
									<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										<div class="input-group input-group-new">
						                    <span class="input-group-addon" style="padding-left:0px">
						                    	<span><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx();" class="noteditable" name='bllx' value="1" checked />&nbsp;A&nbsp;</span>
						                    </span>
						                     <span class="input-group-addon">
						                    	 <span><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx();" class="noteditable" name='bllx' value="2"/>&nbsp;B&nbsp;</span>
											 </span>
						                     <span class="input-group-addon">
						                    	 <span><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx();" class="noteditable" name='bllx' value="3" />&nbsp;C&nbsp;</span>
											 </span>
						                     <span class="input-group-addon">
						                    	 <span><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx();" class="noteditable" name='bllx' value="4" />&nbsp;D&nbsp;</span>
											 </span>
						                     <span class="input-group-addon">
						                    	 <span><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx();" class="noteditable" name='bllx' value="5" />&nbsp;CDL&nbsp;</span>
											 </span>
						                     <span class="input-group-addon">
						                    	 <span><input type='radio' onclick="FailureKeep_Open_Modal.onchangebllx();" class="noteditable" name='bllx' value="9" />&nbsp;其他&nbsp;</span>
											 </span>
											 <input class=" form-control" id="bllxsm" maxlength="100" style="visibility: hidden;"/>
					                	</div>
									</div>
								</div>
								<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">工时</div>
										<div class="font-size-9">MHRs</div>
									</span>
									<div class="col-lg-6 col-md-6 col-sm-6 col-xs-9 padding-leftright-8">
										<div class="input-group input-group-new" >
											<input id="sxgsRs" name="sxgsRs" type="text" class="form-control noteditable" onchange='FailureKeep_Open_Modal.changeRs(this)' style="min-width: 80px;"/>
					                    	<span class="input-group-addon">人&nbsp;x</span>
					                    	<input id="sxgsXs" name="sxgsXs" type="text" class="form-control noteditable" onchange='FailureKeep_Open_Modal.changeXss(this)' style="min-width: 80px;" />
					                     	<span class="input-group-addon">时&nbsp;=&nbsp;<span id="bzgs">0</span>时</span>
					                	</div>
									</div>
								</div>
				 				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<span class="required" style="color: red">*</span>保留依据
										</div>
										<div class="font-size-9 ">Doc Ref</div>
									</span>
										<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
										<input type="text" id="blyj" name="blyj" maxlength="1000" class="noteditable form-control padding-left-3 padding-right-3" />
									</div>
								 </div>
		     				 	 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 ">故障描述</div>
										<div class="font-size-9 ">Defect Des</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea  id="gzms"  maxlength="1000" style="height:55px" class="noteditable form-control padding-left-3 padding-right-3"    ></textarea>
									</div>
								 </div>
								 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 ">保留原因</div>
										<div class="font-size-9 ">Reason</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea id="blyy"  maxlength="1000" class="noteditable form-control padding-left-3 padding-right-3" style="height:55px" ></textarea>
									</div>
								 </div>
								 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 ">临时措施</div>
										<div class="font-size-9 ">Temp Action</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<textarea id="lscs" class="noteditable form-control padding-left-3 padding-right-3" style="height:55px"  maxlength="1000"></textarea>
									</div>
								 </div>
								 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<input class="noteditable" type='checkbox' name='mbs' id='mbs'onclick="FailureKeep_Open_Modal.onchangembs('m')"  style='vertical-align:text-bottom;' />&nbsp;执行(M)程序
										<div class="font-size-9 ">Execute (M) Pro</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<input  id="msm" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3" disabled="disabled" />
									</div>
								 </div>
								 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<input class="noteditable" type='checkbox' name='obs' id='obs' onclick="FailureKeep_Open_Modal.onchangembs('o')" style='vertical-align:text-bottom;' />&nbsp;执行(O)程序
										<div class="font-size-9 ">Execute (O) Pro</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<input id="osm" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3"  disabled="disabled"/>
									</div>
								 </div>
								 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
									<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
										<input class="noteditable" type='checkbox' name='yxxzbs' id='yxxzbs' onclick="FailureKeep_Open_Modal.onchangembs('yxxz')" style='vertical-align:text-bottom;' />&nbsp;运行限制
										<div class="font-size-9 ">Flt Limited</div>
									</span>
									<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
										<input id="yxxzsm" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3"  disabled="disabled"/>
									</div>
								 </div>
		     					 <div class="clearfix"></div>
							</div>
						</div>
				   	</div>
				<div class="clearfix"></div>
				<%@ include file="/WEB-INF/views/common/project/equipment_list_edit.jsp"%><!-- 器材清单列表 -->
				<div class="clearfix"></div>
				<%@ include file="/WEB-INF/views/common/project/tools_list_edit.jsp"%><!-- 工具设备列表 -->
				<div class="clearfix"></div>
				<!--参考文件END  -->		
				<div id="attachments_list_edit" style="display:none">
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
				<div class="clearfix"></div>
				<%@ include file="/WEB-INF/views/open_win/approval_process_info.jsp" %> <!--批准流程 -->	
				<div class="clearfix"></div>
				<div id="zcbl">
				<div class="panel panel-default padding-right-0  margin-top-10">
			        <div class="panel-heading bg-panel-heading">
			        	<div class="font-size-12 ">再次保留</div>
						<div class="font-size-9">Again Keep</div>
				    </div>
					<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
						<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>办理人
									</div>
									<div class="font-size-9 ">Lae</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class="input-group" style="width: 100%">
										<input type="hidden"  name="zcSqrid"  id="zcSqrid">
										<input type="hidden"  name="zcSqrbmid"  id="zcSqrbmid" >
										<input type="text"  name="zcSqr" id="zcSqr" class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="again_keep_open.openUser('zcSqr');">
										<span class="required input-group-btn" >
											<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="again_keep_open.openUser('zcSqr');">
												<i class="icon-search cursor-pointer" ></i>
											</button>
										</span>
				                	</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>办理日期
									</div>
									<div class="font-size-9 ">Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
									<input type="text"  id="zcSqrq" name="zcSqrq"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
								</div>
							</div>
						 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											<span class="required icon-zcblqx" style="color: red">*</span>再次保留期限
										</div>
										<div class="font-size-9 ">Again Expiry Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
									<input type="text"  id="zcBlqx" name="zcBlqx"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
								</div>
							</div>
	     				 	<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">
										<span class="required" style="color: red">*</span>再次保留原因
									</div>
									<div class="font-size-9 ">Redeferred Reason</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="zcBlyy" name="zcBlyy"   maxlength="1000"></textarea>
								</div>
							 </div>
							 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">再次保留批准人
									</div>
									<div class="font-size-9 ">Approved By Caac Again</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class="input-group" style="width: 100%">
										<input type="hidden"  name="zcPzrid"  id="zcPzrid">
										<input type="hidden"  name="zcPzrbmid"  id="zcPzrbmid">
										<input type="text"  name="zcPzr" id="zcPzr" class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="again_keep_open.openUser('zcPzr');">
										<span class="required input-group-btn" >
											<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="again_keep_open.openUser('zcPzr');">
												<i class="icon-search cursor-pointer" ></i>
											</button>
										</span>
				                	</div>
								</div>
							</div>
						 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											再次保留批准日期
										</div>
										<div class="font-size-9 ">Approval Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="zcPzrq"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
								</div>
							</div>
     						<div class="clearfix"></div>
						</div>
					</div>
			   	</div>
				<div class="clearfix"></div>
				<div class="panel panel-default padding-right-0">
			        <div class="panel-heading bg-panel-heading">
			        	<div class="font-size-12 ">局方批准</div>
						<div class="font-size-9">Board Approval</div>
				    </div>
					<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
						<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
						 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">批准人</div>
									<div class="font-size-9 ">Approved By Caac</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="jfpzr"  maxlength="100" class="noteditable form-control padding-left-3 padding-right-3 "  />
								</div>
							</div>
						 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">批准日期</div>
									<div class="font-size-9 ">Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="jfpzrq"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
								</div>
							</div>
					 		<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">批准意见</div>
									<div class="font-size-9 ">Approval Advice</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea id="jfpzyj"  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3"   maxlength="150"></textarea>
								</div>
							 </div>
     						<div class="clearfix"></div>
						</div>
					</div>
			   	</div>
				<div class="clearfix"></div>
			</div>
			</form>
			<div class="clearfix"></div>
		</div>
	</div>	
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/failurekeep/failurekeep_open.js"></script><!--故障保留通用弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/failurekeep/failurekeep_find.js"></script><!--查看故障保留弹窗的js  -->
</body>
</html>