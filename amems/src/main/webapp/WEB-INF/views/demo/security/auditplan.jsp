<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>质量安全审核计划列表</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">

	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			
				<div class="col-xs-2 col-md-3 padding-left-0">
					<a href="javascript:void(0);" class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-10 pull-left" onclick="openAdd()" >
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
				</div>
				
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px">
					<table class="table table-thin table-bordered table-set">
						<thead>
							<tr>
								<th class="colwidth-7" >	
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">审核年份</div>
									<div class="font-size-9 line-height-18">Audit Year</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">审核计划编号</div>
									<div class="font-size-9 line-height-18">AuditPlan No.</div>
								</th>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">制表人</div>
									<div class="font-size-9 line-height-18">Greator</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">制表日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">审核状态</div>
									<div class="font-size-9 line-height-18">Audit State</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Reamrk</div>
								</th>
							</tr>
						</thead>
						<tbody>
						   <tr>
						       <td class='text-center'>
						       <i class='icon-foursquare color-blue cursor-pointer' onClick="openAudit(this)" title='审核 Audit'>&nbsp;&nbsp;</i>
						       <i class='icon-edit color-blue cursor-pointer' onClick="openEdit(this)" title='编辑 Edit'>&nbsp;&nbsp;</i>
						       <i class='icon-trash color-blue cursor-pointer' onClick="del()" title='作废 Invalid'></i> 
		                       </td>
						       <td class='text-left' name="shnf">2017</td>
						       <td class='text-left' name="shxm">DH-2113</td>
						       <td class='text-left' name="zbr">admin mzl</td>
						       <td class='text-left' name="zbrq">2017-06-15</td>
						       <td class='text-left' name="zt">审核通过</td>
						       <td class='text-left' name="bz">提供流程监控,流程管理优化分析,流程预警,流程催办</td>
						   </tr>
						   <tr>
						       <td class='text-center'>
						       <i class='icon-foursquare color-blue cursor-pointer' onClick="openAudit(this)" title='审核 Audit'>&nbsp;&nbsp;</i>
						       <i class='icon-edit color-blue cursor-pointer' onClick="openEdit(this)" title='编辑 Edit'>&nbsp;&nbsp;</i>
						       <i class='icon-trash color-blue cursor-pointer' onClick="del()" title='作废 Invalid'></i> 
		                       </td>
						       <td class='text-left' name="shnf">2017</td>
						       <td class='text-left' name="shxm">DH-0021</td>
						       <td class='text-left' name="zbr">admin 林龙</td>
						       <td class='text-left' name="zbrq">2017-03-11</td>
						       <td class='text-left' name="zt">审核驳回</td>
						       <td class='text-left' name="bz">19年反垃圾数据沉淀,智能甄别,精准过滤.一键申请</td>
						   </tr>
						   <tr>
						       <td class='text-center'>
						       <i class='icon-foursquare color-blue cursor-pointer' onClick="openAudit(this)" title='审核 Audit'>&nbsp;&nbsp;</i>
						       <i class='icon-edit color-blue cursor-pointer' onClick="openEdit(this)" title='编辑 Edit'>&nbsp;&nbsp;</i>
						       <i class='icon-trash color-blue cursor-pointer' onClick="del()" title='作废 Invalid'></i> 
		                       </td>
						       <td class='text-left' name="shnf">2017</td>
						       <td class='text-left' name="shxm">DH-0082</td>
						       <td class='text-left' name="zbr">user 徐娇</td>
						       <td class='text-left' name="zbrq">2016-12-14</td>
						       <td class='text-left' name="zt">未审核</td>
						       <td class='text-left' name="bz">QIJNKDJHJASLJLSKJ</td>
						   </tr>
						   <tr>
						       <td class='text-center'>
						       <i class='icon-foursquare color-blue cursor-pointer' onClick="openAudit(this)" title='审核 Audit'>&nbsp;&nbsp;</i>
						       <i class='icon-edit color-blue cursor-pointer' onClick="openEdit(this)" title='编辑 Edit'>&nbsp;&nbsp;</i>
						       <i class='icon-trash color-blue cursor-pointer' onClick="del()" title='作废 Invalid'></i> 
		                       </td>
						       <td class='text-left' name="shnf">2017</td>
						       <td class='text-left' name="shxm">DH-2113</td>
						       <td class='text-left' name="zbr">admin mzl</td>
						       <td class='text-left' name="zbrq">2017-09-21</td>
						       <td class='text-left' name="zt">未审核</td>
						       <td class='text-left' name="bz">Importer and exporter sign goods contract2/</td>
						   </tr>
						   <tr>
						       <td class='text-center'>
						       <i class='icon-foursquare color-blue cursor-pointer' onClick="openAudit(this)" title='审核 Audit'>&nbsp;&nbsp;</i>
						       <i class='icon-edit color-blue cursor-pointer' onClick="openEdit(this)" title='编辑 Edit'>&nbsp;&nbsp;</i>
						       <i class='icon-trash color-blue cursor-pointer' onClick="del()" title='作废 Invalid'></i> 
		                       </td>
						       <td class='text-left' name="shnf">2017</td>
						       <td class='text-left' name="shxm">DH-2113</td>
						       <td class='text-left' name="zbr">user 孙霁</td>
						       <td class='text-left' name="zbrq">2017-09-21</td>
						       <td class='text-left' name="zt">未审核</td>
						       <td class='text-left' name="bz">GuangzhouTravelGuideGuangzhou,theSouthGateofChina</td>
						   </tr>
						</tbody>
					</table>
				</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>

<!------新增 Start-------->
<div class="modal fade" id="AddAlert" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:80%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertHousingfacilitiesFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18" id="headChina" >新增</div>
						<div class="font-size-9 " id="headEnglish">Add</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
						
						<form id="form">
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">基本信息</div>
								<div class="font-size-9 ">Basic Information</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核年份</div>
										<div class="font-size-9 line-height-18">Audit Year</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="add_shnf" name="add_shnf" />
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核项目单号</div>
										<div class="font-size-9 line-height-18">SafetyAudit</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="add_shxm" name="add_shxm" />
									</div>
								</div>
							
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制表人</div>
										<div class="font-size-9 line-height-18">Grteator</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="add_zbr" name="add_zbr" maxlength="50" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制表日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class='form-control datepicker' data-date-format="yyyy-mm-dd"  id="add_zbrq" name="add_zbrq" />
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">系统审核</div>
								<div class="font-size-9 ">System Audit</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " style="overflow: auto;">
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 1200px">
									<thead>
								   		<tr>
											<th class="colwidth-5" >
												<button type="button" class="line6" onclick="addRotatableSystemCol()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">审核项目</div>
												<div class="font-size-9 notwrap">Audit Project</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">1月</div>
												<div class="font-size-9 notwrap">January </div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">2月</div>
												<div class="font-size-9 notwrap">February</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">3月</div>
												<div class="font-size-9 notwrap">March</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">4月</div>
												<div class="font-size-9 notwrap">April</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">5月</div>
												<div class="font-size-9 notwrap">May</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">6月</div>
												<div class="font-size-9 notwrap">June</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">7月</div>
												<div class="font-size-9 notwrap">July</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">8月</div>
												<div class="font-size-9 notwrap">August</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">9月</div>
												<div class="font-size-9 notwrap">September</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">10月</div>
												<div class="font-size-9 notwrap">October</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">11月</div>
												<div class="font-size-9 notwrap">November</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">12月</div>
												<div class="font-size-9 notwrap">December</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="rotatable_system">
									
									</tbody>
								</table>
					     		<div class="clearfix"></div>
							 </div> 
							 <div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">生产审核</div>
								<div class="font-size-9 ">Production Audit</div>
							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " style="overflow: auto;">
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 1200px">
									<thead>
								   		<tr>
											<th class="colwidth-5" >
												<button type="button" class="line6" onclick="addRotatableProductionCol()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">审核项目</div>
												<div class="font-size-9 notwrap">Audit Project</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">1月</div>
												<div class="font-size-9 notwrap">January </div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">2月</div>
												<div class="font-size-9 notwrap">February</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">3月</div>
												<div class="font-size-9 notwrap">March</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">4月</div>
												<div class="font-size-9 notwrap">April</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">5月</div>
												<div class="font-size-9 notwrap">May</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">6月</div>
												<div class="font-size-9 notwrap">June</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">7月</div>
												<div class="font-size-9 notwrap">July</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">8月</div>
												<div class="font-size-9 notwrap">August</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">9月</div>
												<div class="font-size-9 notwrap">September</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">10月</div>
												<div class="font-size-9 notwrap">October</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">11月</div>
												<div class="font-size-9 notwrap">November</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">12月</div>
												<div class="font-size-9 notwrap">December</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="rotatable_production">
									
									</tbody>
								</table>
					     		<div class="clearfix"></div>
							 </div> 
							<div class="clearfix"></div>
						</form>
						
					 	 <div class="text-right margin-top-10 margin-right-0">
							<button id="save" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save();">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
			           	</div>
                  		<br/>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>
<!-------新增对话框 End-------->
<!------修改 Start-------->
<div class="modal fade" id="EditAlert" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:80%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertHousingfacilitiesFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18" id="headChina" >修改</div>
						<div class="font-size-9 " id="headEnglish">Edit</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
						
						<form id="form">
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">基本信息</div>
								<div class="font-size-9 ">Basic Information</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核年份</div>
										<div class="font-size-9 line-height-18">Audit Year</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class='form-control datetimepicker' id="edit_shnf" name="edit_shnf" />
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核项目单号</div>
										<div class="font-size-9 line-height-18">SafetyAudit</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="edit_shxm" name="edit_shxm" />
									</div>
								</div>
							
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制表人</div>
										<div class="font-size-9 line-height-18">Grteator</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="edit_zbr" name="edit_zbr" maxlength="50" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制表日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class='form-control datepicker' data-date-format="yyyy-mm-dd"  id="edit_zbrq" name="edit_zbrq" />
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">系统审核</div>
								<div class="font-size-9 ">System Audit</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " style="overflow: auto;">
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 1200px">
									<thead>
								   		<tr>
											<th class="colwidth-5" >
												<button type="button" class="line6" onclick="addRotatableSystemCol2()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">审核项目</div>
												<div class="font-size-9 notwrap">Audit Project</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">1月</div>
												<div class="font-size-9 notwrap">January </div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">2月</div>
												<div class="font-size-9 notwrap">February</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">3月</div>
												<div class="font-size-9 notwrap">March</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">4月</div>
												<div class="font-size-9 notwrap">April</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">5月</div>
												<div class="font-size-9 notwrap">May</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">6月</div>
												<div class="font-size-9 notwrap">June</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">7月</div>
												<div class="font-size-9 notwrap">July</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">8月</div>
												<div class="font-size-9 notwrap">August</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">9月</div>
												<div class="font-size-9 notwrap">September</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">10月</div>
												<div class="font-size-9 notwrap">October</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">11月</div>
												<div class="font-size-9 notwrap">November</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">12月</div>
												<div class="font-size-9 notwrap">December</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="rotatable_system2">
									
									</tbody>
								</table>
					     		<div class="clearfix"></div>
							 </div> 
							 <div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">生产审核</div>
								<div class="font-size-9 ">Production Audit</div>
							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " style="overflow: auto;">
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 1200px">
									<thead>
								   		<tr>
											<th class="colwidth-5" >
												<button type="button" class="line6" onclick="addRotatableProductionCol2()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">审核项目</div>
												<div class="font-size-9 notwrap">Audit Project</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">1月</div>
												<div class="font-size-9 notwrap">January </div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">2月</div>
												<div class="font-size-9 notwrap">February</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">3月</div>
												<div class="font-size-9 notwrap">March</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">4月</div>
												<div class="font-size-9 notwrap">April</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">5月</div>
												<div class="font-size-9 notwrap">May</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">6月</div>
												<div class="font-size-9 notwrap">June</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">7月</div>
												<div class="font-size-9 notwrap">July</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">8月</div>
												<div class="font-size-9 notwrap">August</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">9月</div>
												<div class="font-size-9 notwrap">September</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">10月</div>
												<div class="font-size-9 notwrap">October</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">11月</div>
												<div class="font-size-9 notwrap">November</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">12月</div>
												<div class="font-size-9 notwrap">December</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="rotatable_production2">
									
									</tbody>
								</table>
					     		<div class="clearfix"></div>
							 </div> 
							<div class="clearfix"></div>
						</form>
						
					 	 <div class="text-right margin-top-10 margin-right-0">
							<button id="save" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save();">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
			           	</div>
                  		<br/>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>
<!-------修改对话框 End-------->
<!------审核 Start-------->
<div class="modal fade" id="AuditAlert" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:80%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertHousingfacilitiesFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18" id="headChina" >审核</div>
						<div class="font-size-9 " id="headEnglish">Audit</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
						
						<form id="form">
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">基本信息</div>
								<div class="font-size-9 ">Basic Information</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核年份</div>
										<div class="font-size-9 line-height-18">Audit Year</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="audit_shnf" name="audit_shnf" />
									</div>
								</div>
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核项目单号</div>
										<div class="font-size-9 line-height-18">SafetyAudit</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="audit_shxm" name="audit_shxm" />
									</div>
								</div>
							
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制表人</div>
										<div class="font-size-9 line-height-18">Grteator</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="audit_zbr" name="audit_zbr" maxlength="50" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制表日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</label>
									<div class="col-lg-8 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
										<input type="text" class='form-control datepicker' data-date-format="yyyy-mm-dd"  id="audit_zbrq" name="audit_zbrq" />
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">系统审核</div>
								<div class="font-size-9 ">System Audit</div>
							</div>
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " style="overflow: auto;">
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 1200px">
									<thead>
								   		<tr>
											<th class="colwidth-5" id="demo1">
												<button type="button" class="line6" onclick="addRotatableSystemCol2()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">审核项目</div>
												<div class="font-size-9 notwrap">Audit Project</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">1月</div>
												<div class="font-size-9 notwrap">January </div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">2月</div>
												<div class="font-size-9 notwrap">February</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">3月</div>
												<div class="font-size-9 notwrap">March</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">4月</div>
												<div class="font-size-9 notwrap">April</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">5月</div>
												<div class="font-size-9 notwrap">May</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">6月</div>
												<div class="font-size-9 notwrap">June</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">7月</div>
												<div class="font-size-9 notwrap">July</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">8月</div>
												<div class="font-size-9 notwrap">August</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">9月</div>
												<div class="font-size-9 notwrap">September</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">10月</div>
												<div class="font-size-9 notwrap">October</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">11月</div>
												<div class="font-size-9 notwrap">November</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">12月</div>
												<div class="font-size-9 notwrap">December</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="rotatable_system3">
									
									</tbody>
								</table>
					     		<div class="clearfix"></div>
							 </div> 
							 <div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
								<div class="font-size-16 line-height-18">生产审核</div>
								<div class="font-size-9 ">Production Audit</div>
							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 " style="overflow: auto;">
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 1200px">
									<thead>
								   		<tr>
											<th class="colwidth-5"  id="demo2">
												<button type="button" class="line6" onclick="addRotatableProductionCol2()">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
												</button>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">审核项目</div>
												<div class="font-size-9 notwrap">Audit Project</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">1月</div>
												<div class="font-size-9 notwrap">January </div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">2月</div>
												<div class="font-size-9 notwrap">February</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">3月</div>
												<div class="font-size-9 notwrap">March</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">4月</div>
												<div class="font-size-9 notwrap">April</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">5月</div>
												<div class="font-size-9 notwrap">May</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">6月</div>
												<div class="font-size-9 notwrap">June</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">7月</div>
												<div class="font-size-9 notwrap">July</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">8月</div>
												<div class="font-size-9 notwrap">August</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">9月</div>
												<div class="font-size-9 notwrap">September</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">10月</div>
												<div class="font-size-9 notwrap">October</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">11月</div>
												<div class="font-size-9 notwrap">November</div>
											</th>
											<th class="colwidth-13">
												<div class="font-size-12 notwrap">12月</div>
												<div class="font-size-9 notwrap">December</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="rotatable_production3">
									
									</tbody>
								</table>
					     		<div class="clearfix"></div>
							 </div> 
							<div class="clearfix"></div>
							<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0" style="margin-top: 10px;">
								<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div
											class="font-size-12 line-height-18">审核意见</div>
										<div class="font-size-9 line-height-18">Review Opinion</div>
								</label>
							</div>
							<div class=" col-lg-12 col-sm-12 col-xs-12 padding-right-0" style="margin-top: 10px;">
								<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
									<textarea class="form-control" id="audit_shyj" name="audit_shyj"></textarea>
								</div>
							</div>
							<div class="clearfix"></div>
						</form>
						
					 	 <div class="text-right margin-top-10 margin-right-0">
							<button id="save" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:audit();">
			                   	<div class="font-size-12">审核通过</div>
								<div class="font-size-9">Approved</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:rejectedAudit()">
			                   	<div class="font-size-12">审核驳回</div>
								<div class="font-size-9">Rejected</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
			           	</div>
                  		<br/>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>
<!-------修改对话框 End-------->
<!--用户的模态框 -->
<div class="modal fade" id="ShjcdMultiModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:750px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="ShjcdModalBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">用户列表</div>
						<div class="font-size-9 ">User List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12">
		            	
			         		<div class="clearfix"></div>
							<!-- start:table -->
							<div class="margin-top-10 overflow-auto">
								<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width: 400px;">
									<thead>
										<tr>
											<th class="colwidth-5" style='text-align:center;vertical-align:middle;'>
												<a href="#" onclick="SelectUtil.performSelectAll('shjcdList')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('shjcdList')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											</th>
											<th>
												<div class="font-size-12 notwrap">用户名</div>
												<div class="font-size-9 notwrap">UserName</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">真实姓名</div>
												<div class="font-size-9 notwrap">RealName</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="shjcdList">
									</tbody>
								</table>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="closeShjcd()">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="closeShjcd()">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
			                </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-------用户 End-------->
<script type="text/javascript">

	var list = [];
	var system_xh = 1;
	var production_xh = 1;
	var mainId = '';
	
	$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		
		$('.datepicker').datepicker({
			 autoclose: true,
			 clearBtn:true
	    });
	});
	
	//打开新增弹出框
	function openAdd(){
		$("#AddAlert").modal("show");
		$("#add_shnf").val("");
		$("#add_shxm").val("");
		$("#add_zbr").val("");
		$("#add_zbrq").val("");
		$("#rotatable_system").empty();
		$("#rotatable_production").empty();
		$("#rotatable_system").append("<tr><td colspan='15' class='text-center'>暂无数据 No data.</td></tr>");
		$("#rotatable_production").append("<tr><td colspan='15' class='text-center'>暂无数据 No data.</td></tr>");
	}
	
	//打开修改弹出框
	function openEdit(obj){
		$("#EditAlert").modal("show");
	    $(obj).parent().parent().find("td").each(function(){
	        var name=$(this).attr("name");
	    	$("#edit_"+name).val($(this).text());
	    });
	    $("#rotatable_system2").html(initEdit());
	    $("#rotatable_production2").html(initEdit());
	}
	//打开审核弹出框
	function openAudit(obj){
		var zt=$(obj).parent().parent().find("[name=zt]").text();
		if(zt=="未审核"){
			$("#AuditAlert").modal("show");
		    $(obj).parent().parent().find("td").each(function(){
		        var name=$(this).attr("name");
		    	$("#audit_"+name).val($(this).text());
		    });
		    $("#rotatable_system3").html("");
		    $("#rotatable_production3").html("");
		    $("#rotatable_system3").append(initEdit());
		    $("#rotatable_system3").append(initEdit());
		    $("#rotatable_production3").append(initEdit());
		    $("#rotatable_production3").append(initEdit());
		    $("#rotatable_production3").append(initEdit());
		    $("#AuditAlert").find("input").attr("disabled",true);
		    $("#demo1").hide();
		    $("#demo2").hide();
		    $("#AuditAlert").find(".display").hide();
		}else{
			AlertUtil.showErrorMessage('该状态不能审核!');
		}
	}
	//保存成功
	function save(){
		AlertUtil.showMessage('保存成功!');
		$("#AddAlert").modal("hide");
		$("#EditAlert").modal("hide");
	}
	//审核成功
	function audit(){
		AlertUtil.showMessage('审核通过成功!');
		$("#AuditAlert").modal("hide");
	}
	//审核驳回成功
	function rejectedAudit(){
		AlertUtil.showMessage('审核驳回成功!');
		$("#AuditAlert").modal("hide");
	}
	//移除一行
	function del(id){
		AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		    AlertUtil.showMessage('作废成功!');
		}});
	}
	
	//向table新增一行
	function addRotatableSystemCol() {
		// 先移除暂无数据一行
		var len = $("#rotatable_system").length;
		if (len == 1) {
			if ($("#rotatable_system").find("td").length == 1) {
				$("#rotatable_system").empty();
			}
		}
		var obj = {};
		obj.id = "";
		$("#rotatable_system").append(addRow(obj));
		//生成多选下拉框动
	 	$("select").multiselect({
	 		buttonClass: 'btn btn-default',
	 	    buttonWidth: 'auto',
	 	    numberDisplayed:200,
	 	    onChange:function(element,checked){
	 	   }
	    });
	}
	//向table新增一行
	function addRotatableProductionCol() {
		// 先移除暂无数据一行
		var len = $("#rotatable_production").length;
		if (len == 1) {
			if ($("#rotatable_production").find("td").length == 1) {
				$("#rotatable_production").empty();
			}
		}
		var obj = {};
		obj.id = "";
		$("#rotatable_production").append(addRow(obj));
		//生成多选下拉框动
	 	$("select").multiselect({
	 		buttonClass: 'btn btn-default',
	 	    buttonWidth: 'auto',
	 	    numberDisplayed:200,
	 	    onChange:function(element,checked){
	 	   }
	    });
	}
	//向table新增一行
	function addRotatableSystemCol2() {
		// 先移除暂无数据一行
		var len = $("#rotatable_system2").length;
		if (len == 1) {
			if ($("#rotatable_system2").find("td").length == 1) {
				$("#rotatable_system2").empty();
			}
		}
		var obj = {};
		obj.id = "";
		$("#rotatable_system2").append(addRow(obj));
		//生成多选下拉框动
	 	$("select").multiselect({
	 		buttonClass: 'btn btn-default',
	 	    buttonWidth: 'auto',
	 	    numberDisplayed:200,
	 	    onChange:function(element,checked){
	 	   }
	    });
	}
	//向table新增一行
	function addRotatableProductionCol2() {
		// 先移除暂无数据一行
		var len = $("#rotatable_production2").length;
		if (len == 1) {
			if ($("#rotatable_production2").find("td").length == 1) {
				$("#rotatable_production2").empty();
			}
		}
		var obj = {};
		obj.id = "";
		$("#rotatable_production2").append(addRow(obj));
		//生成多选下拉框动
	 	$("select").multiselect({
	 		buttonClass: 'btn btn-default',
	 	    buttonWidth: 'auto',
	 	    numberDisplayed:200,
	 	    onChange:function(element,checked){
	 	   }
	    });
	}
	
	function openUserList(){
		var list = [{username:"liMing",realname:"李明"},
		            {username:"liub",realname:"刘兵"},
		            {username:"suj",realname:"孙霁"}
				];
		var htmlContent = '';
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='clickRow(this)' >";
			} else {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='clickRow(this)' >";
		  	}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' dh='"+row.dh+"' index='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','shjcdList')\" /></td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' name='username'>"+row.username +"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' name='realname'>"+row.realname +"</td>";
			htmlContent += "</tr>";  
		});
		$("#shjcdList", $("#ShjcdMultiModal")).empty();
		$("#shjcdList", $("#ShjcdMultiModal")).html(htmlContent);
		$("#ShjcdMultiModal").modal("show");
	}
	
	function clickRow(row){
		SelectUtil.checkRow($(row).find("input[type='checkbox']"),'selectAllId','shjcdList');
	}
	
	function closeShjcd(){
		var dh = '';
		$("#shjcdList").find("tr td input:checked").each(function(){
			dh += $(this).attr("dh")+",";	
		});
		if(dh != ''){
			dh = dh.substring(0,dh.length - 1);
		}
		$("#shjcde").val(dh);
		$("#ShjcdMultiModal").modal("hide");
	}
	
	//向table新增一行
	function addRow(obj) {
		var tr = "";
		tr += "<tr>";
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "<button type='button' class='line6' onclick='removeCol(this)'>";
		tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
		tr += "</button>";
		tr += "<input type='hidden' class='form-control' name='hiddenid' value='"+ obj.id + "'/>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "<input name='whzq' type='text' class='form-control' />";
		tr += "</td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		tr += "<td><div class='input-group'>";
		tr += "<input class='form-control' type='text' name='bjh'/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		
		tr += "</tr>";
		return tr;
	}
	
	// 移除一行
	function removeCol(obj) {
		$(obj).parent().parent().remove();
	}
	function initEdit(){
		var obj = {};
		obj.id = "";
		var tr = "";
		tr += "<tr>";

		tr += "<td style='text-align:center;vertical-align:middle;' class='display'>";
		tr += "<button type='button' class='line6' onclick='removeCol(this)'>";
		tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
		tr += "</button>";
		tr += "<input type='hidden' class='form-control' name='hiddenid' value='"+ obj.id + "'/>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "<input name='whzq' type='text' class='form-control' value='机务航行仪表 检查审核' readOnly/>";
		tr += "</td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='mzl 梅志亮' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='sj 孙霁' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='mzl 梅志亮,sj 孙霁' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='mzl 梅志亮,sj 孙霁' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='lb 刘兵' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='sj 孙霁,lb 刘兵' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='mzl 梅志亮 '/>";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='sj 孙霁,lb 刘兵' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='mzl 梅志亮,lb 刘兵' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='lb 刘兵' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='sj 孙霁,lb 刘兵' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='mzl 梅志亮,lb 刘兵' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";
		
		tr += "<td><div class='input-group'>";
		tr += "<input name='whzq' type='text' class='form-control' value='mzl 梅志亮,lb 刘兵' />";
		tr += "<span class='input-group-btn'><button type='button' onclick='openUserList(this)' class='btn btn-primary'>";
		tr += "<i class='icon-search'></i></button></span></div></td>";

		tr += "</tr>";
		
		return tr;
	}
	
	//向table新增一行
	function addRotatableCol2() {
		// 先移除暂无数据一行
		var len = $("#rotatable2").length;
		if (len == 1) {
			if ($("#rotatable2").find("td").length == 1) {
				$("#rotatable2").empty();
			}
		}
		var obj = {};
		obj.id = "";
		obj.xh = xh++;
		obj.whlx = "";
		obj.whzq = "";
		obj.scwhrq = "";
		obj.xcwhrq = "";
		obj.syts = "";
		obj.whgzdh = "";
		$("#rotatable2").append(addRow(obj));
	}
</script>
</body>
</html>