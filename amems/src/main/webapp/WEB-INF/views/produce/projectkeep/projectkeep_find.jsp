<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看项目保留</title>
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
			<form id="Project_Open_Modal" >
				<div class="panel-body padding-bottom-0 padding-leftright-8  padding-top-0">	 
					<div class="input-group-border margin-top-8 padding-left-0">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">保留单编号</div>
								<div class="font-size-9 ">WD No.</div>
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
								<select class="form-control noteditable" name="fjzch" id="Project_Open_Modal_fjzch" onchange="Project_Open_Modal.onchangeFjzch(this)">
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
						<div class='clearfix'></div>
					</div>
 				<div class="clearfix"></div>
				<div class="panel panel-default padding-right-0">
			        <div class="panel-heading bg-panel-heading">
			        	<div class="font-size-12 ">申请信息</div>
						<div class="font-size-9">Application Info</div>
				    </div>
					<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
						<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
					 		<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>
										申请日期
									</div>
									<div class="font-size-9 ">Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
									<input type="text"  id="sqrq" name="sqrq"  class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
								</div>
							</div>
							<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">申请人</div>
									<div class="font-size-9 ">Applicant</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class="input-group" style="width: 100%">
										<input type="hidden"  name="sqrid"  id="sqrid">
										<input type="text"  name="sqr" id="sqr" class="form-control noteditable readonly-style colse"  readonly="readonly" ondblclick="Project_Open_Modal.openUser('sqr');">
										<span class="required input-group-btn" >
											<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Project_Open_Modal.openUser('sqr');">
												<i class="icon-search cursor-pointer" ></i>
											</button>
										</span>
				                	</div>
								</div>
							</div>
						 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										申请部门
									</div>
									<div class="font-size-9 ">Department</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="hidden" id="sqrbmid" >
									<input type="text"  id="sqrbm"  class="noteditable form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
								</div>
							</div>
			      			<div id="lybhdiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>工单编号
									</div>
									<div class="font-size-9 ">W/O No.</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class="input-group" style="width: 100%">
										<input type="hidden" id="addupdatebs" value="1">
										<input type="hidden" id="bs145">
										<input type="hidden"  name="gdid"  id="gdid">
										<input type="text"   id="gd" name='gd' class="form-control noteditable readonly-style colse" readonly="readonly" ondblclick='Project_Open_Modal.initSourceData();'>
										<span class="required input-group-btn" id="wxrybtn" >
											<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="Project_Open_Modal.initSourceData()">
												<i class="icon-search cursor-pointer" ></i>
											</button>
										</span>
				                	</div>
								</div>
							</div>
							<div class="clearfix"></div>
	         					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										计划完成日期
									</div>
									<div class="font-size-9 ">Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="jhJsrq"  class="noteditable form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
								</div>
							</div>
	         					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										工卡号
									</div>
									<div class="font-size-9 ">W/C No.</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="gkbh"  class="noteditable form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
								</div>
							</div>
	         					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										工卡定检间隔
									</div>
									<div class="font-size-9 ">Interval</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="gkbz"  class="noteditable form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
								</div>
							</div>
							<div class="clearfix"></div>
		    				<div id="sylbDiv" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="pull-left" style='width:350px;padding-left:8px;'>
									<div class="font-size-12 margin-top-5">涉及发动机、起落架和飞行操纵系统？</div>
								</span>
								<div class="pull-left" >
									<div >
					                    <span  style="padding-left:0px">
					                    	<span><input type='radio' class="noteditable" name='fjbs1' value="1" style='vertical-align:-2px;' />&nbsp;是&nbsp;</span>
					                    </span>
					                    <span >
					                    	 <span><input type='radio' class="noteditable" name='fjbs1' value="0" style='vertical-align:-2px;' checked/>&nbsp;否&nbsp;</span>
										</span>
				                	</div>
								</div>
							</div>
							<div class="clearfix"></div>
	    					<div id="sylbDiv" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="pull-left" style='width:350px;padding-left:8px;'>
									<div class="font-size-12 margin-top-5">影响飞行机组正常操作或增加其工作负荷的维修工作项目？</div>
								</span>
								<div class="pull-left" >
									<div >
					                    <span  style="padding-left:0px">
					                    	<span><input type='radio' class="noteditable" name='fjbs2' value="1" style='vertical-align:-2px;'/>&nbsp;是&nbsp;</span>
					                    </span>
					                    <span >
					                    	 <span><input type='radio' class="noteditable" name='fjbs2' value="0" style='vertical-align:-2px;' checked/>&nbsp;否&nbsp;</span>
										</span>
				                	</div>
								</div>
							</div>
							<div class="clearfix"></div>
		    				<div id="sylbDiv" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="pull-left" style='width:350px;padding-left:8px;'>
									<div class="font-size-12 margin-top-5">该项目涉及的系统/设备/零部件的技术性能是否符合规定要求？</div>
								</span>
								<div class="pull-left" >
									<div >
					                    <span  style="padding-left:0px">
					                    	<span><input type='radio' class="noteditable" name='fjbs3' value="1" style='vertical-align:-2px;'/>&nbsp;是&nbsp;</span>
					                    </span>
					                    <span >
					                    	 <span><input type='radio' class="noteditable" name='fjbs3' value="0" style='vertical-align:-2px;' checked/>&nbsp;否&nbsp;</span>
										</span>
				                	</div>
								</div>
							</div>
							<div class="clearfix"></div>
	    				 	 	<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">MEL/CDL对相关系统要求</div>
									<div class="font-size-9 ">MEL/CDL Claim</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea  style="height:55px" class="noteditable form-control padding-left-3 padding-right-3" id="dxgxtyq"    maxlength="1000"></textarea>
								</div>
							 </div>
							 <div class="clearfix"></div>
							 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">保留期间采取措施</div>
									<div class="font-size-9 ">Measures</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="blqjcqcs" style="height:55px" maxlength="1000"></textarea>
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
				<div class="panel panel-default padding-right-0">
			        <div class="panel-heading bg-panel-heading">
			        	<div class="font-size-12 ">生产计划评估</div>
						<div class="font-size-9">Production Plan Evaluation</div>
				    </div>
					<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
						<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
							<div id="sylbDiv" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " >
								<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-left padding-left-10 padding-right-0">
									<div class="font-size-12 margin-topnew-2">上次完成该工作飞行后数据</div>
									<div class="font-size-9">Data</div>
								</span> 
								<div class="col-lg-3 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 form-group" >
			                   		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">飞行小时</div>
										<div class="font-size-9 ">Flight Hours</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
										<div class="input-group"> 
											<input class="form-control fxyz noteditable" id="fxsj" type="text" >
											<input class="form-control" type="hidden" />
											<span class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">HRS</span>
										</div>
									</div>
								</div>	
								<div class="col-lg-3 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 form-group" >
				               		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">
											飞行循环
										</div>
										<div class="font-size-9 ">Flight Cycle</div>
									</span>
									<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
										<div class="input-group"> 
											<input class="form-control noteditable" id="fxxh" type="text" name="fxxh">
											<input class="form-control" type="hidden" />
											<span class="input-group-addon dw "  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FC</span>
										</div>
									</div> 
								</div>
								<div class="col-lg-4 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								</div>
							</div>
						 	<div class="clearfix"></div>	
							<div id="sylbDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">CMR项目</div>
									<div class="font-size-9 ">CMR Case</div>
								</span>	
								<div class="col-lg-4 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
									<div >
					                    <span  style="padding-left:0px">
					                    	<span class="label-input"><input type='radio' class="noteditable" name='fjbs4' value="1" />&nbsp;是&nbsp;</span>
					                    </span>
					                     <span >
					                    	 <span class="label-input"><input type='radio' class="noteditable" name='fjbs4' value="0" checked/>&nbsp;否&nbsp;</span>
										 </span>
				                	</div>
								</div>
								<div class="col-lg-4 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								</div>
							</div>
							<div id="sylbDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">AWL项目</div>
									<div class="font-size-9 ">AWL Case</div>
								</span>	
								<div class="col-lg-4 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
									<div>
					                    <span  style="padding-left:0px">
					                    	<span class="label-input"><input type='radio' class="noteditable" name='fjbs5' value="1" />&nbsp;是&nbsp;</span>
					                    </span>
					                     <span>
					                    	 <span class="label-input"><input type='radio' class="noteditable" name='fjbs5' value="0" checked/>&nbsp;否&nbsp;</span>
										 </span>
				                	</div>
								</div>
								<div class="col-lg-4 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								</div>
							</div>
		    				<div id="sylbDiv" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="pull-left" style='padding-left:8px;'>
									<div class="font-size-12 margin-top-5">保留期内是否有计划维修工作能够接近该项目工作？</div>
								</span>
								<div class="pull-left padding-leftright-8" >
									<div >
					                    <span  style="padding-left:0px">
					                    	<span><input type='radio' class="noteditable" name='fjbs6' value="1" style='vertical-align:-2px;'/>&nbsp;是&nbsp;</span>
					                    </span>
					                    <span >
					                    	 <span><input type='radio' class="noteditable" name='fjbs6' value="0" style='vertical-align:-2px;' checked/>&nbsp;否&nbsp;</span>
										</span>
				                	</div>
								</div>
							</div>
							<div class="clearfix"></div>	
						 	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">
										<span class="required" style="color: red">*</span>
										申请保留期限
									</div>
									<div class="font-size-9 ">Expiry Date Period</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">
									<input type="text"  id="blqx"  name="blqx" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
								</div>
							</div>
						 	<div class="col-lg-9 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">规章保留限制</div>
									<div class="font-size-9 ">Retention Restrictions</div>
								</span>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="gzblxz" maxlength="100"  class="noteditable form-control padding-left-3 padding-right-3 "  />
								</div>
							</div>
							 <div class="clearfix"></div>
						 	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">计划完成地点</div>
									<div class="font-size-9 ">Completion Place</div>
								</span>
								<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="jhwcdd"  maxlength="100" class="noteditable form-control padding-left-3 padding-right-3 "  />
								</div>
							</div>
			       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">评估人</div>
									<div class="font-size-9">Assessor</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text"  id="pgr"  maxlength="100" class="noteditable form-control padding-left-3 padding-right-3 " />
								</div>
							</div>
          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">评估日期</div>
									<div class="font-size-9 ">Assessment Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text" onchange="Project_Open_Modal.onketup(this)"  id="pgrq" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
								</div>
							</div>
	     					 <div class="clearfix"></div>
						</div>
					</div>
			   	</div>
				<div class="clearfix"></div>
				<div class="panel panel-default padding-right-0">
			        <div class="panel-heading bg-panel-heading">
			        	<div class="font-size-12 ">技术评估</div>
						<div class="font-size-9">Technical Assessment</div>
				    </div>
					<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
						<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
							<div id="sylbDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">核实准确</div>
									<div class="font-size-9 ">Verify Accuracy</div>
								</span>	
								<div class="col-lg-4 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
									<div >
					                    <span  style="padding-left:0px">
					                    	<span class="label-input"><input type='radio' class="noteditable" name='fjbs7' value="1" checked/>&nbsp;是&nbsp;</span>
					                    </span>
					                     <span >
					                    	 <span class="label-input"><input type='radio' class="noteditable" name='fjbs7' value="0"/>&nbsp;否&nbsp;</span>
										 </span>
				                	</div>
								</div>
								<div class="col-lg-4 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								</div>
							</div>
							<div id="sylbDiv" class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">同意保留期限</div>
									<div class="font-size-9 ">Expiry Date Period</div>
								</span>	
								<div class="col-lg-4 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" >
									<div >
					                    <span style="padding-left:0px">
					                    	<span class="label-input"><input type='radio' class="noteditable" name='fjbs8' value="1" checked/>&nbsp;是&nbsp;</span>
					                    </span>
					                     <span >
					                    	 <span class="label-input"><input type='radio' class="noteditable" name='fjbs8' value="0"/>&nbsp;否&nbsp;</span>
										 </span>
				                	</div>
								</div>
								<div class="col-lg-4 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" >
								</div>
							</div>
							<div id="sylbDiv" class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="pull-left" style='padding-left:8px;'>
									<div class="font-size-12 margin-top-5">所采取措施是否符合要求？</div>
								</span>
								<div class="pull-left padding-leftright-8" >
									<div >
					                    <span  style="padding-left:0px">
					                    	<span><input type='radio' class="noteditable" name='fjbs9' value="1" style='vertical-align:-2px;' />&nbsp;是&nbsp;</span>
					                    </span>
					                    <span >
					                    	 <span><input type='radio' class="noteditable" name='fjbs9' value="0"  style='vertical-align:-2px;' checked/>&nbsp;否&nbsp;</span>
										</span>
				                	</div>
								</div>
							</div>
							<div class="clearfix"></div>
			       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">评估人</div>
									<div class="font-size-9">Assessor</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text"  id="jspgr"   maxlength="100"  class="noteditable form-control padding-left-3 padding-right-3 "  />
								</div>
							</div>
          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">评估日期</div>
									<div class="font-size-9 ">Assess Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="jspgrq" onchange="Project_Open_Modal.onketup(this)" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
								</div>
							</div>
	     					 <div class="clearfix"></div>
						</div>
					</div>
			   	</div>
		   		<div class='clearfix'></div>
				<div class="panel panel-default padding-right-0">
			        <div class="panel-heading bg-panel-heading">
			        	<div class="font-size-12 ">审核信息</div>
						<div class="font-size-9">Audit Info</div>
				    </div>
					<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
						<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
			       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">审核人</div>
									<div class="font-size-9">Auditor</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text"  id="shr" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3 "  />
								</div>
							</div>
          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">审核日期</div>
									<div class="font-size-9 ">Review Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="shrq" onchange="Project_Open_Modal.onketup(this)" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
								</div>
							</div>
							 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">审核意见</div>
									<div class="font-size-9 ">Audit Opinion</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="shyj"  
										style="height:55px" maxlength="150"></textarea>
								</div>
							 </div>
	     					 <div class="clearfix"></div>
						</div>
					</div>
			   	</div>
				<div class="clearfix"></div>
				<div class="panel panel-default padding-right-0">
			        <div class="panel-heading bg-panel-heading">
			        	<div class="font-size-12 ">批准信息</div>
						<div class="font-size-9">Approval Info</div>
				    </div>
					<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
						<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
			       			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">批准人</div>
									<div class="font-size-9">Approved By Caac</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<input type="text"  id="pzr" maxlength="100" class="noteditable form-control padding-left-3 padding-right-3 "  />
								</div>
							</div>
          					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">批准日期</div>
									<div class="font-size-9 ">Approved Date</div>
								</span>
								<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
									<input type="text"  id="pzrq"  onchange="Project_Open_Modal.onketup(this)" class="noteditable form-control padding-left-3 padding-right-3 date-picker"  />
								</div>
							</div>
							 <div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group" >
								<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">批准意见</div>
									<div class="font-size-9 ">Approval Advice</div>
								</span>
								<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
									<textarea class="noteditable form-control padding-left-3 padding-right-3" id="pzyj"  
										style="height:55px" maxlength="150"></textarea>
								</div>
							 </div>
	     					 <div class="clearfix"></div>
						</div>
					</div>
			   	</div>
				<!--参考文件END  -->		
				<div id="attachments_list_edit" style="display:none">
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
				</div>
			</form>
			<div class="clearfix"></div>
	</div>	
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/projectkeep/projectkeep_open.js"></script><!--项目保留弹窗的js  -->
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/projectkeep/projectkeep_find.js"></script><!--查看项目保留弹窗的js  -->
</body>
</html>