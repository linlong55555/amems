<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看定检任务单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
<div class="page-content">
			<!-- from start -->
	<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			           <input type="hidden" id="id" value="${checklist.id}">
			           
			           
			           
			   <div class="panel panel-default">
		             <div class="panel-heading">
							    <h3 class="panel-title">基本信息 Basic Info</h3>
			   </div>
				<div id="display_div" class="panel-body">
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 padding-top-10">
					
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">定检任务编号</div>
								<div class="font-size-9 line-height-18">Scheduled No.</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" value="${erayFns:escapeStr(checklist.djrwbh)}" class="form-control " readonly />
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">维修方案编号</div>
								<div class="font-size-9 line-height-18">Maintenance No.</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text"  value="${erayFns:escapeStr(checklist.wxfabh)}" class="form-control"  readonly/>
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">定检编号</div>
								<div class="font-size-9 line-height-18">Check No.</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text"value="${erayFns:escapeStr(checklist.djbh)}" class="form-control"  readonly/>
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									    <div class="font-size-12 line-height-18">飞机注册号</div>
										<div class="font-size-9 line-height-18">A/C REG</div></label>
								<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
								    <input type="text"   class="form-control" value="${erayFns:escapeStr(checklist.fjzch)}"  disabled="disabled"/>
								</div>
							</div>
					</div>
					
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
							
					   	<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">部件号</div>
								<div class="font-size-9 line-height-18">P/N</div></label>
							   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
									<input type="text"  class="form-control"  value="${erayFns:escapeStr(checklist.bjh)}" disabled="disabled"/>
							 </div>
					  </div>
					  
					  	<div class=" col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">部件序列号</div>
								<div class="font-size-9 line-height-18">S/N</div></label>
							   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
									<input type="text"  class="form-control"  value="${erayFns:escapeStr(checklist.xlh)}" disabled="disabled"/>
							 </div>
					  </div>
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									    <div class="font-size-12 line-height-18">定检描述</div>
										<div class="font-size-9 line-height-18">ChekItem Name</div></label>
								<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
								    <input type="text" class="form-control" value="${erayFns:escapeStr(checklist.zwms)}"  disabled="disabled"/>
								</div>
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">标准工时</div>
									<div class="font-size-9 line-height-18">PalneTime</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="jhgsRs" name="jhgsRs" value="${checklist.jhgsRs}" class="form-control pull-left" style="width: 25%" onkeyup='clearNoNum(this)' readonly/>
									<span style="float:left; height:25px; line-height:30px;">人X</span>
									<input type="text" id="jhgsXss" name="jhgsXss" value="${checklist.jhgsXss}" class="form-control pull-left" style="width: 25%"  onkeyup='clearNoNum(this)' readonly/>
									<span style="float:left; height:25px; line-height:30px;">小时</span>
									<span style="margin-left:1px; float:left; height:25px; line-height:30px;">=<span id="bzgs">0</span></span>
								</div>
					    	</div>
					  
					</div>
					
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
					  	<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">版本号</div>
								<div class="font-size-9 line-height-18">Version No.</div></label>
							   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
									<input type="text"  class="form-control"  value="${erayFns:escapeStr(checklist.bb)}" disabled="disabled"/>
							 </div>
					  </div>
					  	<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">重要系数 </div>
								<div class="font-size-9 line-height-18">Important No.</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" value="${erayFns:escapeStr(checklist.zyxs)}" class="form-control " readonly />
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creator</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="zdr" name="zdr" value="${erayFns:escapeStr(checklist.username)}  ${erayFns:escapeStr(checklist.realname)}" class="form-control " readonly />
								</div>
							</div>
							
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="zdsj" name="zdsj" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${checklist.zdsj}"/>" data-date-format="yyyy-MM-dd HH:mm:ss" class="form-control " readonly />
								</div>
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="zt" value="${checklist.zt}" class="form-control " readonly />
								</div>
							</div>
					</div>
					 
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
					 <label class="pull-left col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">备注</div>
						<div class="font-size-9">Remark</div>
					 </label>
					 <div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
					  <textarea class="form-control" id="bz" name="readonly" disabled="disabled">${erayFns:escapeStr(checklist.bz)}</textarea>
					 </div>
					 </div>
				</div>
			</div>	
				
				
				
				          

				
				
					          
			   <div class="panel panel-default">
		             <div class="panel-heading">
							    <h3 class="panel-title">工作内容 Work Content</h3>
			  		 </div>
					  <div id="display_div3" class="panel-body">
						<div class="col-lg-12  padding-right-0 form-group " style="margin-top: 10px;overflow-x:scroll ;">
							<table class="table-set table table-thin table-bordered table-striped table-hover" style=" min-width: 2500px !important;" >
								<thead>
									<tr>
										<th class="colwidth-3"><div class="font-size-12 line-height-18">序号</div><div class="font-size-9 line-height-16">No.</div></th>
										<th class="colwidth-10"><div class="font-size-12 line-height-18">ATA章节号</div><div class="font-size-9 line-height-16">ATA</div></th>
										<th class="colwidth-7"><div class="font-size-12 line-height-18">项目来源</div><div class="font-size-9 line-height-16">Project Srocuse</div></th>
										<th class="colwidth-10"><div class="font-size-12 line-height-18">工作类型</div><div class="font-size-9 line-height-16">Work Type</div></th>
										<th class="colwidth-7"><div class="font-size-12 line-height-18">工作地点</div><div class="font-size-9 line-height-16">Station</div></th>
										<th class="colwidth-15"><div class="font-size-12 line-height-18">中文描述</div><div class="font-size-9 line-height-16">CH.Name</div></th>
										<th class="colwidth-15"><div class="font-size-12 line-height-18">英文描述</div><div class="font-size-9 line-height-16">F.Name</div></th>
										<th class="colwidth-5"><div class="font-size-12 line-height-18">检查类型</div><div class="font-size-9 line-height-16">Check Type</div></th>
										<th class="colwidth-18"><div class="font-size-12 line-height-18">适用性</div><div class="font-size-9 line-height-16">A/C REG</div></th>
										<th class="colwidth-5"><div class="font-size-12 line-height-18">工作站位</div><div class="font-size-9 line-height-16">Location</div></th>
										<th class="colwidth-7"><div class="font-size-12 line-height-18">厂家手册及版本</div><div class="font-size-9 line-height-16">Reference Card</div></th>
										<th class="colwidth-7"><div class="font-size-12 line-height-18">厂家工卡及版本</div><div class="font-size-9 line-height-16">Reference Manual</div></th>
										<th class="colwidth-10"><div class="font-size-12 line-height-18">相关定检工单</div><div class="font-size-9 line-height-16">Work Order</div></th>
										<th class="colwidth-3"><div class="font-size-12 line-height-18">必检</div><div class="font-size-9 line-height-16">Check</div></th>
										<th class="colwidth-3"><div class="font-size-12 line-height-18">MI</div><div class="font-size-9 line-height-16">MI</div></th>
										<th class="colwidth-15"><div class="font-size-12 line-height-18">备注</div><div class="font-size-9 line-height-16">Remark</div></th>
										<th class="colwidth-3"><div class="font-size-12 line-height-18">有效性</div><div class="font-size-9 line-height-16">Effectivity</div></th>
									</tr>
								</thead>     
								<tbody  id="CheckingContentList">
								</tbody>	
							</table>
					   </div>
				    </div>
				  <div class="clearfix"></div>
				</div>
				
				   <div class="panel panel-default" >
		             <div class="panel-heading">
							    <h3 class="panel-title">监控项目 CheckMonitoring</h3>
			  		 </div>
				   <div id="display_div2" class="panel-body">
				    <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0"   >
							
							<div id="calendar_dlg1" style="padding-left: 15px;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
					           <div class="well">
										<div class="row padding-top-10">
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													    <div class="font-size-12 line-height-18">监控项目：</div>
												</label>
												<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
												       <span id="jkxmbhF">${checkingmonitoring.jkxmbhF}</span>
												</div><br>
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													    <div class="font-size-12 line-height-18">监控值：</div>
													    <div class="font-size-9 line-height-12">MonitorValue</div>
												</label>
												 <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
													<input type="text" id="jkzF"  class="form-control"  value="${checkingmonitoring.jkzF}" disabled="disabled"/>
											     </div>
										</div>
							   </div>
						  </div>
						  
						  <div id="calendar_dlg2" style="padding-left: 15px;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
					           <div class="well">
										<div class="row padding-top-10">
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													    <div class="font-size-12 line-height-18">监控项目：</div>
												</label>
												<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
												       <span id="jkxmbhS">${checkingmonitoring.jkxmbhS}</span>
												</div><br>
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													    <div class="font-size-12 line-height-18">监控值：</div>
													    <div class="font-size-9 line-height-12">MonitorValue</div>
												</label>
												 <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
													<input type="text" id="jkzS" class="form-control"  value="${checkingmonitoring.jkzS}" disabled="disabled"/>
											     </div>
										</div>
							   </div>
						  </div>
						  <div id="calendar_dlg3" style="padding-left: 15px;" class="col-lg-4 col-sm-6 col-xs-12 margin-bottom-10">
					           <div class="well">
										<div class="row padding-top-10">
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													    <div class="font-size-12 line-height-18">监控项目：</div>
												</label>
												<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
												    <span  id="jkxmbhT">${checkingmonitoring.jkxmbhT}</span>
												</div><br>
												<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
													    <div class="font-size-12 line-height-18">监控值：</div>
													    <div class="font-size-9 line-height-12">MonitorValue</div>
												</label>
												 <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
													<input type="text" id="jkzT" class="form-control"  value="${checkingmonitoring.jkzT}" disabled="disabled"/>
											     </div>
										</div>
							   </div>
						  </div>
					</div>	
				</div>   
				<div class="clearfix"></div>
				</div>
			</div>
		</div>
		<!-- 基本信息 End -->
		
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/checklist/view_checklist.js"></script>
</body>
</html>