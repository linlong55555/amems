<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>EO管理</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
 
</script>
</head>
<body class="page-header-fixed">
	 
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<div class="page-content">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body" >
			 		<div class="col-xs-12 " style='padding:0px;'>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>版本</div>
								<div class="font-size-9">Version</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms"  value="" type="text" maxlength="100">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red"></span>Eo编号</div>
								<div class="font-size-9">TODO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red"></span>下发生产</div>
								<div class="font-size-9">TODO</div>
							</span>
							
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input  checked="checked"  id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >是&nbsp;&nbsp;
								<input   id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >否
							</div>
						</div>
						 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>机型</div>
								<div class="font-size-9">TODO</div>
							</span>
						 
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
								<option>SY6D</option>
								<option>S76D</option>
								</select>
							</div>
						</div>
						 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red">*</span>ATA 章节号</div>
								<div class="font-size-9">ATA</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
							</div>
						</div>
						
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span style="color: red"></span>颁发日期</div>
								<div class="font-size-9">TODO</div>
							</span>
							
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control datepicker" id="bfrq" name="bfrq" type="text" maxlength="8" >
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>首期技术支援</div>
								<div class="font-size-9">TODO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input  checked="checked"  id="sqjszy1" name="sqjszy" type="radio" maxlength="8"  >是&nbsp;&nbsp;
								<input   id="sqjszy2" name="sqjszy" type="radio" maxlength="8"  >否
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>级别</div>
								<div class="font-size-9">Level</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input  checked="checked"  id="level1" name="level" type="radio" maxlength="8"  >是&nbsp;&nbsp;
								<input  type="radio"  id="level2" name="bb" type="level" maxlength="8"  >否
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>工作类型</div>
								<div class="font-size-9">Level</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
								<option></option>
								<option>类型1</option>
								<option>类型2</option>
								</select>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span> 标题</div>
								<div class="font-size-9">Version</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
							</div>
						</div>
						  
						 
						<div class="clearfix"></div>
						<!-- 历史图片
						<i class="glyphicon glyphicon glyphicon-list" ></i>
						 -->
					 
						
						<!-- 评估单开始 -->
						<%@ include file="/WEB-INF/views/project2/engineering/assessment_sheet.jsp"%>
						<!-- 评估单结束 -->
						
						<!-- 适用设置开始 -->
						<%@ include file="/WEB-INF/views/project2/engineering/applicable_settings.jsp"%>
						<!-- 适用设置结束 -->
						 
					 	<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">工作概述</div>
								<div class="font-size-9 ">TODO</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<textarea class="form-control" id="ckzl" name="ckzl" placeholder="" maxlength="300"></textarea>
							</div>
						</div>	
						 
						<div class="clearfix"></div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>复合性</div>
								<div class="font-size-9">TODO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="20"/>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>类别</div>
								<div class="font-size-9">TODO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
								<option></option>
								<option>类型1</option>
								<option>类型2</option>
								</select>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>分级</div>
								<div class="font-size-9">Work type</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<select class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
								<option></option>
								<option>类型1</option>
								<option>类型2</option>
								</select>
							</div>
						</div>
						<div class="clearfix"></div>
					 
					
					<!-- 维修改装对象开始 -->
					<%@ include file="/WEB-INF/views/project2/engineering/maintenance_modification_obj.jsp"%>
					<!-- 维修改装对象结束 -->
					
					<!-- 重量与平衡开始 -->
					<%@ include file="/WEB-INF/views/project2/engineering/weight_balance.jsp"%>
					<!-- 重量与平衡结束 -->
					
					<!-- 参考文件开始 -->
					<%@ include file="/WEB-INF/views/project2/engineering/reference_file.jsp"%>
					<!-- 参考文件结束 -->
					
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">变化内容</div>
								<div class="font-size-9 ">TODO</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<textarea class="form-control" id="ckzl" name="ckzl" placeholder="" maxlength="300"></textarea>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">工种工时</div>
								<div class="font-size-9 ">TODO</div>
							</span>
							<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-8">
							<div class="panel panel-primary">
								<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
									<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-height" style="overflow-x: auto;">
										<table  class="table table-thin table-bordered table-striped table-hover table-set text-center" >
											<thead>
												<tr>
												   <th class="colwidth-5">
													   <button onclick="addTr()" class="line6 ">
													       <i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
												        </button>
												   </th>
												   <th>
													   <div class="font-size-12 line-height-12">工种</div>
											           <div class="font-size-9 line-height-12">TODO</div>
												   </th>
												   <!-- class='sorting' -->
												   <th >
													   <div class="font-size-12 line-height-12">工时</div>
											           <div class="font-size-9 line-height-12">TODO</div>
												   </th>
												     
												</tr>
											</thead>
											<tbody >
												<tr >
												<td class="colwidth-5" style="vertical-align:middle">
												<button onclick="addTr()" class="line6 ">
													       <i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
												        </button>
												</td>
												<td>111</td>
												<td>111</td>
												</tr>
												<tr>
												<td class="colwidth-5" style="vertical-align:middle">
												<button class="line6" onclick="del_tr(this)"><i class="fa glyphicon glyphicon-minus color-blue cursor-pointer"></i></button>
												</td>
												<td>111</td>
												<td>111</td>
												</tr>
											</tbody>
									</table>
								</div>
								<div class='clearfix'></div>
							</div>
						</div>
							</div>
						</div>
						
						
						<!-- 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span> Eo编号</div>
								<div class="font-size-9">TODO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
							</div>
						</div>
						 -->
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>器材价格</div>
								<div class="font-size-9">TODO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group">
									<input type="text" id="jswjbh" class="form-control padding-left-3 padding-right-3" >
									<input type="hidden" id="jswjid" class="form-control">
									<div class="input-group-btn">
										 <button type="button" style="height: 34px;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">币种 <span class="caret"></span></button>
									        <ul class="dropdown-menu dropdown-menu-right">
									          <li><a href="#">RMB</a></li>
									          <li><a href="#">RMB</a></li>
									        </ul>
									</div>
							    </div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>颁发器材准备通知单</div>
								<div class="font-size-9">TODO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								 <input  checked="checked"  id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >是&nbsp;&nbsp;
								 <input  id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >否
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span></div>
								<div class="font-size-9"></div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								 <input   class="form-control"  id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8"  />
							</div>
						</div>
						<!-- 
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
							 <input   class="form-control"  id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8"  />
						</div> -->
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>RII</div>
								<div class="font-size-9">TODO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								 <input  checked="checked"  id="maintenance_schedule_modal_bb" name="bb" type="checkbox" maxlength="8"  >是&nbsp;&nbsp;
							</div>
						</div>
						<div class="clearfix"></div>
					
					<!-- 监控项设置开始 -->
					 <%@ include file="/WEB-INF/views/project2/engineering/monitoritem_settings.jsp"%> 
					<!-- 监控项设置结束 -->
					
					
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">工程建议</div>
								<div class="font-size-9 ">TODO</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<textarea class="form-control" id="ckzl" name="ckzl" placeholder="" maxlength="300"></textarea>
							</div>
						</div>	 
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
								<div class="font-size-12 ">生产安排</div>
								<div class="font-size-9 ">TODO</div>
							</span>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
									<textarea class="form-control" id="ckzl" name="ckzl" placeholder="" maxlength="300"></textarea>
							</div>
						</div>
					 
					<div class="clearfix"></div>
			
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
							<div class="font-size-12 ">原因及描述</div>
							<div class="font-size-9 ">TODO</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id="ckzl" name="ckzl" placeholder="" maxlength="300"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
							<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
							<div class="font-size-12 ">处理措施</div>
							<div class="font-size-9 ">TODO</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id="ckzl" name="ckzl" placeholder="" maxlength="300"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<!-- 改版记录开始 -->
					 <%@ include file="/WEB-INF/views/project2/engineering/revision_record.jsp"%> 
					<!-- 改版记录结束 -->
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>是否影响出版物</div>
							<div class="font-size-9">TODO</div>
						</span>
						<div class="col-sm-8 col-xs-8 padding-leftright-8">
							<input  checked="checked"  id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >是&nbsp;&nbsp;
							<input   id="maintenance_schedule_modal_bb" name="bb" type="radio" maxlength="8"  >否
						</div>
					</div>
					<div class="clearfix"></div>
					
					<!-- 受影响的出版物      开始 -->
					 <%@ include file="/WEB-INF/views/project2/engineering/publication.jsp"%> 
					<!-- 受影响的出版物      结束 -->
				
				    <div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
							<div class="font-size-12 ">受影响的机载软件清单</div>
							<div class="font-size-9 ">TODO</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id="ckzl" name="ckzl" placeholder="" maxlength="300"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					
					<!-- 工时/停场时间              开始 -->
					 <%@ include file="/WEB-INF/views/project2/engineering/stopping_time.jsp"%> 
					<!-- 工时/停场时间              结束 -->
					
					 <div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0">
							<div class="font-size-12 ">电气负载数据</div>
							<div class="font-size-9 ">TODO</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
								<textarea class="form-control" id="ckzl" name="ckzl" placeholder="" maxlength="300"></textarea>
						</div>
					</div>
					<div class="clearfix"></div>
					
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>维修项目的相关性</div>
							<div class="font-size-9">TODO</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="maintenance_schedule_modal_bb" name="bb" type="text" maxlength="8" onkeyup='clearNoNumTwo(this)'>
						</div>
					</div>
					
					 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>反馈要求</div>
							<div class="font-size-9">TODO</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>航材需求申请单</div>
							<div class="font-size-9">TODO</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="maintenance_schedule_modal_zwms" name="zwms" type="text" maxlength="100">
						</div>
					</div>
					<div class="clearfix"></div>
					
					<!-- 器材清单             开始 -->
					 <%@ include file="/WEB-INF/views/project2/engineering/equipment_list.jsp"%> 
					<!-- 器材清单           结束 -->
					
					<!-- 索赔       开始 -->
					 <%@ include file="/WEB-INF/views/project2/engineering/claim_demage.jsp"%> 
					<!-- 索赔       开始 -->
					
					<!-- 工作内容       开始 -->
					 <%@ include file="/WEB-INF/views/project2/engineering/job_content.jsp"%> 
					<!-- 工作内容       开始 -->
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
							<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2"><span style="color: red"></span>分发</div>
								<div class="font-size-9">TODO</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<div class="input-group">
									<input type="text" id="jswjbh" class="form-control padding-left-3 padding-right-3" disabled="disabled">
									<input type="hidden" id="jswjid" class="form-control">
									<span class="input-group-btn">
										 <button class="btn btn-primary form-control" data-toggle="modal" onclick="openTech();" type="button">
											<i class="icon-search cursor-pointer"></i>
										 </button>
									</span>
							    </div>
							</div>
						</div>
						
					<div class="clearfix"></div>
					
					<!-- 工作内容       开始 -->
					 <%@ include file="/WEB-INF/views/open_win/introduce_process_info_find.jsp"%> 
					<!-- 工作内容       开始 -->
					
					
				</div>
        	</div>
		</div>		
	</div>
     
	<script>
	 
	$(function(){
		Navigation(menuCode, '', '', 'GC-3-2 ', '朱超', '2017-08-2', '朱超', '2017-08-2');//加载导航栏
		eoViewComp.load();
		authHeight();//点击行收缩
		
		 
	});
	
	var eoViewComp  = {
		load : function(option){
			var setting = option||{};
			//load data for ajax
			this.readOnlys();
		},
		readOnlys : function(){
		 
			/* $('.page-content input').attr('disabled',true);
			$('.page-content textarea').attr('disabled',true);
			$('.page-content select').attr('disabled',true); */
		}
		 
	};
	 
	
	</script>
</body>
</html>
