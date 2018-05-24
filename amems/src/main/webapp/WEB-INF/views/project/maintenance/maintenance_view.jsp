<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维修方案差异信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body >
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="dprtcode" value="${maintenance.dprtcode}" />
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>		

			<div class="panel-body">
			
				<!-- 搜索框start -->
				<div class=" pull-right padding-left-10 padding-right-0 margin-bottom-10 row-height">
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="viewDifference()">
							<div class="font-size-12">查看差异表</div>
							<div class="font-size-9">Difference</div>
                   		</button>
                    </div> 
				</div>
				<!-- 搜索框end -->
			
				<form id="form">
					
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">维修方案编号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="wxfabh"  name="wxfabh" value="${erayFns:escapeStr(maintenance.wxfabh)}" class="form-control " readonly/>
								<input type="hidden" id="wxfaid" value="${maintenance.id}" />
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">维修方案名称</div>
								<div class="font-size-9 line-height-18">Name</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " value="${erayFns:escapeStr(maintenance.zwms)}" id="wxfazwms" name="wxfazwms" readonly/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">版本号</div>
								<div class="font-size-9 line-height-18">Revision</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " value="${erayFns:escapeStr(maintenance.bb)}" id="wxfabb" name="wxfabb" readonly/>
							</div>
						</div>
					
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
							<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">前版本号</div>
								<div class="font-size-9 line-height-18">Revision</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control " value="${erayFns:escapeStr(maintenanceOld.bb)}" id="wxfafbb" name="wxfafbb" readonly/>
							</div>
						</div>
						
					</div>
				</form>
				
				<div class="clearfix"></div>
				
				<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
					<div class="font-size-16 line-height-18">定检项目</div>
					<div>Fixed Check List</div>
				</div>
				
				<div id="left_div1" class="col-sm-2" style="border-right: 1px solid #ccc;padding-left:5px;padding-right:5px;">
					<div style="overflow: auto;height: 500px;">
						<div class="list-group"  id="fixedCheckItem">
						</div>
					</div>
				</div>
				
				<div id="right_div1" class="col-sm-10">
					<div id="left_div2" class="col-sm-6" style="border-right: 1px solid #ccc">
						<input type="hidden" id="fixId1" name="fixId1" />
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10" style="border-bottom: 1px solid #ccc;">
							<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10" >
								<label class="col-lg-6 col-sm-6 col-xs-6 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">定检编号</div>
									<div class="font-size-9 line-height-18">Fixed No.</div>
								</label>
								<div id="fixCode1" class="col-lg-6 col-sm-6 col-xs-6 padding-left-8 padding-right-0">
								</div>
							</div>
							<div class="col-lg-9 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">定检名称 版本</div>
									<div class="font-size-9 line-height-18">Name/Revision</div>
								</label>
								<div id="fixNameBb1" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;padding: 5px 5px 5px 5px;">
								</div>
							</div>
						</div>
						<div class=" col-lg-1 col-sm-4 col-xs-12  padding-left-10 padding-right-0  margin-bottom-10" >
							<div class="pull-left" onclick="vieworhideWorkContent();">
							 	<i class="icon-caret-up font-size-16 cursor-pointer" id="iconWorkContent1"></i>
							</div>
						</div>
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">工作内容</div>
							<div class="font-size-9 ">Contents</div>
						</div>
		            	<div id="workContent1" class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10" onscroll="document.getElementById('workContent2').scrollLeft = this.scrollLeft;">
							<!-- start:table -->
							<div class="margin-top-0 ">
							<div id="scroll1" style="overflow-x: scroll;" onscroll="document.getElementById('scroll2').scrollLeft = this.scrollLeft;">
								<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width: 1800px;">
									<thead>
								   		<tr>
											<th class="colwidth-5">
												<div class="font-size-12 notwrap"></div>
												<div class="font-size-9 notwrap"></div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">ATA章节号</div>
												<div class="font-size-9 notwrap">ATA</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">项目来源</div>
												<div class="font-size-9 notwrap">Source</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">工作类型</div>
												<div class="font-size-9 notwrap">Work Type</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">工作地点</div>
												<div class="font-size-9 notwrap">Workplace</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12">中文描述</div>
												<div class="font-size-9">CH.Name</div>
											</th>
											<th class="colwidth-25">
												<div class="font-size-12">英文描述</div>
												<div class="font-size-9">F.Name</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">检查类型</div>
												<div class="font-size-9 notwrap">Check Type</div>
											</th>
											<th class="colwidth-30">
												<div class="font-size-12 notwrap">适用性</div>
												<div class="font-size-9 notwrap">Applicability</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">工作站位</div>
												<div class="font-size-9 notwrap">Location</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 notwrap">厂家手册及版本</div>
												<div class="font-size-9 notwrap">Manual and Revision</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 notwrap">厂家工卡及版本</div>
												<div class="font-size-9 notwrap">W/C and Revision</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 notwrap">关联定检工卡</div>
												<div class="font-size-9 notwrap">Related Fixed Check W/C</div>
											</th>
											<th class="colwidth-5">
												<div class="font-size-12 notwrap">必检</div>
												<div class="font-size-9 notwrap">Check</div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 notwrap">MI</div>
												<div class="font-size-9 notwrap">MI</div>
											</th>
											<th class="colwidth-30">
												<div class="font-size-12 notwrap">备注</div>
												<div class="font-size-9 notwrap">Remark</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">有效性</div>
												<div class="font-size-9 notwrap">Effectivity</div>
											</th>
							 		 </tr>
									</thead>
									<tbody id="rotatable1">
									
									</tbody>
								</table>
								</div>
							</div>
							<!-- end:table -->
					 	</div>

					 	<div class=" col-lg-1 col-sm-4 col-xs-12  padding-left-10 padding-right-0  margin-bottom-10" >
					   		<div class="pull-left" onclick="vieworhideMonitor();">
							 	<i class="icon-caret-up font-size-16 cursor-pointer" id="iconMonitor1"></i>
							</div>
						</div>
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">循环周期监控设置</div>
							<div class="font-size-9 ">Monitor Setting</div>
						</div>
						<div id="monitoring1" style="padding-left: 15px;" class="col-sm-12">
						</div>

						<div class=" col-lg-1 col-sm-4 col-xs-12  padding-left-10 padding-right-0  margin-bottom-10" >
					   		<div class="pull-left" onclick="vieworhideForm();">
							 	<i class="icon-caret-up font-size-16 cursor-pointer" id="iconForm1"></i>
							</div>
						</div>
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">定检基本信息</div>
							<div class="font-size-9 ">Basic Information</div>
						</div>
						<div class="" id="fixForm1">
							<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">重要系数</div>
									<div class="font-size-9 line-height-18">Coefficient</div>
								</label>
								<div id="fixZyxs1" class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								</div>
							</div>
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">标准工时</div>
									<div class="font-size-9 line-height-18">MHRS</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0 bzgsdiv">
									<span id="jhgsRs1"></span>人
									&nbsp;X
									<span id="jhgsXss1"></span>时
									=&nbsp;<span id="bzgs1">0</span>	
								</div>
							</div>
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">有效性</div>
									<div class="font-size-9 line-height-18">Effectivity</div>
								</label>
								<div id="fixYxx1" class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								</div>
							</div>
							<!-- <div class=" col-lg-12 col-sm-4 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label  class="col-lg-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">排序号</div>
									<div class="font-size-9 line-height-18">Order No.</div>
								</label>
								<div id="fixPxh1" class="col-lg-9 padding-left-8 padding-right-0">
								</div>
							</div> -->
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " style="height:70px;" >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</label>
								<div id="fixBz1" class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								</div>
							</div>
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 margin-top-10 " >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creator</div>
								</label>
								<div id="fixZdr1" class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								</div>
							</div>
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Time</div>
								</label>
								<div id="fixZdrq1" class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								</div>
							</div>
						</div>
					</div>
				
					<div id="right_div2" class="col-sm-6" style="border-right: 1px solid #ccc">
						<input type="hidden" id="fixId1" name="fixId1" />
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10" style="border-bottom: 1px solid #ccc;">
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10" >
								<label class="col-lg-6 col-sm-6 col-xs-6 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">定检编号</div>
									<div class="font-size-9 line-height-18">Fixed No.</div>
								</label>
								<div id="fixCode2" class="col-lg-6 col-sm-6 col-xs-6 padding-left-8 padding-right-0">
								</div>
							</div>
							<div class="col-lg-9 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">定检名称 版本</div>
									<div class="font-size-9 line-height-18">Name/Revision</div>
								</label>
								<div id="fixNameBb2" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;padding: 5px 5px 5px 5px;">
								</div>
							</div>
						</div>
						<div class=" col-lg-1 col-sm-4 col-xs-12  padding-left-10 padding-right-0  margin-bottom-10" >
					   		<div class="pull-left" onclick="vieworhideWorkContent();">
							 	<i class="icon-caret-up font-size-16 cursor-pointer" id="iconWorkContent2"></i>
							</div>
						</div>
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">工作内容</div>
							<div class="font-size-9 ">Contents</div>
						</div>
		            	<div id="workContent2" class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10" >
							<!-- start:table -->
							<div class="margin-top-0 ">
							<div id="scroll2" style="overflow-x: scroll;" onscroll="document.getElementById('scroll1').scrollLeft = this.scrollLeft;">
								<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width: 1800px;">
									<thead>
								   		<tr>
											<th class="colwidth-5">
												<div class="font-size-12 notwrap"></div>
												<div class="font-size-9 notwrap"></div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 notwrap">序号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">ATA章节号</div>
												<div class="font-size-9 notwrap">ATA</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">项目来源</div>
												<div class="font-size-9 notwrap">Source</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">工作类型</div>
												<div class="font-size-9 notwrap">Work Type</div>
											</th>
											<th class="colwidth-15">
												<div class="font-size-12 notwrap">工作地点</div>
												<div class="font-size-9 notwrap">Workplace</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12">中文描述</div>
												<div class="font-size-9">CH.Name</div>
											</th>
											<th class="colwidth-25">
												<div class="font-size-12">英文描述</div>
												<div class="font-size-9">F.Name</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">检查类型</div>
												<div class="font-size-9 notwrap">Check Type</div>
											</th>
											<th class="colwidth-30">
												<div class="font-size-12 notwrap">适用性</div>
												<div class="font-size-9 notwrap">Applicability</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">工作站位</div>
												<div class="font-size-9 notwrap">Location</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 notwrap">厂家手册及版本</div>
												<div class="font-size-9 notwrap">Manual and Revision</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 notwrap">厂家工卡及版本</div>
												<div class="font-size-9 notwrap">W/C and Revision</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 notwrap">关联定检工卡</div>
												<div class="font-size-9 notwrap">Related Fixed Check W/C</div>
											</th>
											<th class="colwidth-5">
												<div class="font-size-12 notwrap">必检</div>
												<div class="font-size-9 notwrap">Check</div>
											</th>
											<th class="colwidth-3">
												<div class="font-size-12 notwrap">MI</div>
												<div class="font-size-9 notwrap">MI</div>
											</th>
											<th class="colwidth-30">
												<div class="font-size-12 notwrap">备注</div>
												<div class="font-size-9 notwrap">Remark</div>
											</th>
											<th class="colwidth-7">
												<div class="font-size-12 notwrap">有效性</div>
												<div class="font-size-9 notwrap">Effectivity</div>
											</th>
							 		 	</tr>
									</thead>
									<tbody id="rotatable2">
									
									</tbody>
								</table>
								</div>
							</div>
							<!-- end:table -->
					 	</div>

					 	<div class=" col-lg-1 col-sm-4 col-xs-12  padding-left-10 padding-right-0  margin-bottom-10" >
							<div class="pull-left" onclick="vieworhideMonitor();">
							 	<i class="icon-caret-up font-size-16 cursor-pointer" id="iconMonitor2"></i>
							</div>
						</div>
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">循环周期监控设置</div>
							<div class="font-size-9 ">Monitor Setting</div>
						</div>
						<div id="monitoring2" style="padding-left: 15px;" class="col-sm-12">
						</div>

						<div class=" col-lg-1 col-sm-4 col-xs-12  padding-left-10 padding-right-0  margin-bottom-10" >
							<div class="pull-left" onclick="vieworhideForm();">
							 	<i class="icon-caret-up font-size-16 cursor-pointer" id="iconForm2"></i>
							</div>
						</div>
						<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
							<div class="font-size-16 line-height-18">定检基本信息</div>
							<div class="font-size-9 ">Basic Information</div>
						</div>
						<div class="" id="fixForm2">
							<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">重要系数</div>
									<div class="font-size-9 line-height-18">Coefficient</div>
								</label>
								<div id="fixZyxs2" class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								</div>
							</div>
							<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">标准工时</div>
									<div class="font-size-9 line-height-18">MHRS</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0 bzgsdiv">
									<span id="jhgsRs2"></span>人
									&nbsp;X
									<span id="jhgsXss2"></span>时
									=&nbsp;<span id="bzgs2">0</span>	
								</div>
							</div>
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">有效性</div>
									<div class="font-size-9 line-height-18">Effectivity</div>
								</label>
								<div id="fixYxx2" class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								</div>
							</div>
							<!-- <div class=" col-lg-12 col-sm-4 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label  class="col-lg-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">排序号</div>
									<div class="font-size-9 line-height-18">Order No.</div>
								</label>
								<div id="fixPxh2" class="col-lg-9 padding-left-8 padding-right-0">
								</div>
							</div> -->
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 "  style="height:70px;">
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</label>
								<div id="fixBz2" class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								</div>
							</div>
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 margin-top-10 " >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creator</div>
								</label>
								<div id="fixZdr2" class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								</div>
							</div>
							<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 " >
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Time</div>
								</label>
								<div id="fixZdrq2" class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					<!-- <div class=" col-lg-10 "></div>
					<div class=" col-lg-2 ">
			           	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()"  style="float: left; margin-left: 100px;">
				           	<div class="font-size-12">返回</div>
							<div class="font-size-9">Back</div>
						</button>
			        </div> -->
				</div>
			</div>
		</div>
	</div>
	
<!-------差异表对话框 Start-------->
	
<div class="modal fade" id="alertDiffForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:40%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertDiffFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18" >差异表</div>
						<div class="font-size-9">Difference</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
						
						<form id="form">
						
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-10">
								<div class="col-lg-4 col-sm-4 col-xs-4  padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-6 col-sm-6 col-xs-6 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">维修方案编号</div>
										<div class="font-size-9 line-height-18">No.</div>
									</label>
									<div id="vwxfabh" class="col-lg-6 col-sm-6 col-xs-6 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<div class="col-lg-6 col-sm-6 col-xs-6  padding-left-0 padding-right-0 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">维修方案名称</div>
										<div class="font-size-9 line-height-18">Name</div>
									</label>
									<div id="vwxfazwms" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
								
								<!-- 搜索框start -->
								<div class="col-lg-2 col-sm-2 col-xs-2  padding-left-0 padding-right-0 form-group">
				                    <div class=" pull-right padding-left-5 padding-right-0 ">   
										<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="outMaintenance()">
											<div class="font-size-12">导出</div>
											<div class="font-size-9">Export</div>
				                   		</button>
				                    </div> 
								</div>
								<!-- 搜索框end -->
								
								<div class="clearfix"></div>
								
								<div class="col-lg-6 col-sm-6 col-xs-6  padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">改版</div>
										<div class="font-size-9 line-height-18">Modify</div>
									</label>
									<div id="vwxfabb" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										
									</div>
								</div>
							
								<div class="clearfix"></div>
							</div>
						</form>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							
							<!-- start:table -->
							<div style="overflow-x: auto;">
								<table class="table table-bordered table-striped table-hover table-set" style="min-width: 450px;">
									<thead>
								   		<tr>
											<th class="colwidth-3">
												<div class="font-size-12 line-height-18">序号</div>
												<div class="font-size-9 line-height-18">No.</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">定检编号</div>
												<div class="font-size-9 line-height-18">Fixed No.</div>
											</th>
											<th class="colwidth-20">
												<div class="font-size-12 line-height-18">定检项目名称</div>
												<div class="font-size-9 line-height-18">Fixed Name</div>
											</th>
											<th class="colwidth-9">
												<div class="font-size-12 line-height-18">修订内容项</div>
												<div class="font-size-9 line-height-18">Revise Item</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-18">修订内容</div>
												<div class="font-size-9 line-height-18">Revise Content</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="diffTable">
									
									</tbody>
								</table>
								</div>
								<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="diff_pagination"></div>
							<!-- end:table -->
				     		<div class="clearfix"></div>
						</div>
						
					 	 <div class="text-right margin-top-10 margin-right-0">
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

<!-------差异表对话框 End-------->
	
	
<script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/maintenance_view.js"></script>	
</body>
</html>