<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>飞机基本信息</title>
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
			<div class="panel-body padding-bottom-0" >
		 		<div class="col-xs-12 " style='padding:0px;'>
					<div class="input-group-border">					
						<!-- start隐藏input -->
						<input class="form-control" id="gjdjzfjid" name="gjdjzfjid" type="hidden" >
						<input class="form-control" id="shzfjid" name="shzfjid" type="hidden"  >
						<input class="form-control" id="wxdtzzfjid" name="wxdtzzfjid" type="hidden" >
						<input class="form-control" id="aircraftinfoFjzch" value="${aircraftinfo.fjzch}" type="hidden" >
						<input class="form-control" id="aircraftinfoDprtcode" value="${aircraftinfo.dprtcode}" type="hidden" >
						<!-- end隐藏input -->
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">机型</div>
								<div class="font-size-9">A/C Type</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="fjjx" name="fjjx" type="text" maxlength="20" >
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">飞机注册号</div>
								<div class="font-size-9">A/C REG</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="fjzch" name="fjzch" type="text" maxlength="20">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">MSN</div>
								<div class="font-size-9">MSN</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="xlh" name="xlh" type="text" maxlength="20">
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">备注名</div>
								<div class="font-size-9">Note Name</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="bzm" name="bzm" type="text" maxlength="16">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">基地</div>
								<div class="font-size-9">Base</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="jd" name="jd" type="text" maxlength="16">
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">归属</div>
								<div class="font-size-9">Attribution</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input style=" vertical-align: middle;   margin-top: -1px;" name="attachBox" type='checkbox' id="isSsbdw" />
							    		&nbsp;本单位
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="gkSelect">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">客户名称</div>
								<div class="font-size-9">Name</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="khText" name="khText" type="text" maxlength="16" >
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">生产日期</div>
								<div class="font-size-9">PD</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control date-picker-tb" id="scrq" name="scrq" type="text" />
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group ">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">出厂日期</div>
								<div class="font-size-9">MFD</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control " id="ccrq" name="ccrq" type="text"  />
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">购买日期</div>
								<div class="font-size-9">PFD</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control " id="gmrq" name="gmrq" type="text"  />
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">滑油牌号</div>
								<div class="font-size-9">Grease No.</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="rhyzph" name="rhyzph" type="text" maxlength="100">
								
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">液压油牌号</div>
								<div class="font-size-9">Hydraulic Oil No.</div>
							</span>
							<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<input class="form-control" id="yyyph" name="yyyph" type="text" maxlength="100" >
							</div>
						</div>
						<div class="clearfix"></div>	
					</div>
					<div class="clearfix"></div>   
					
					<div class="panel panel-primary">
				<div id="attachHead" class="panel-heading bg-panel-heading" >
					<div class="font-size-12" id="chinaHead">飞机重点监控信息</div>
					<div class="font-size-9" id="englishHead">A/C Type Monitoring</div>
				</div>
				<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
				
					<div  class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<span id="left_column" class=" text-right padding-left-0 padding-right-8">
							&nbsp;&nbsp;<b style="font-weight:bold">飞机当前值</b>&nbsp;<small></small>
						</span>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">初始化日期</div>
							<div class="font-size-9">Init Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="jscshrq" name="jscshrq" data-date-format="yyyy-mm-dd" type="text" />
							<input class="form-control" type="hidden" />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞行时间</div>
							<div class="font-size-9">Flight Hours</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<div class="input-group"> 
								<input class="form-control input-sm fxyz" maxlength="8" id="jsfxxs" name="jsfxxs" type="text" maxlength="20" >
								<input class="form-control" type="hidden" />
								<span class="input-group-addon dw" name="zq_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FH</span>
							</div>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">飞行循环</div>
							<div class="font-size-9">Flight Cycle</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
							<div class="input-group"> 
								<input class="form-control input-sm" maxlength="8" id="jsfxxh" name="jsfxxh" type="text" maxlength="20">
								<input class="form-control" type="hidden" />
								<span class="input-group-addon dw" name="zq_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FC</span>
							</div>
						</div>
					</div>
					
					<div  class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" >
						<label id="left_column" class=" text-right padding-left-0 padding-right-8">
							&nbsp;&nbsp;<b style="font-weight:bold">三证信息</b>
						</label>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">国籍登记证号</div>
							<div class="font-size-9">Nationality No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control " id="gjdjzh" name="gjdjzh" type="text" maxlength="100" />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">颁发日期</div>
							<div class="font-size-9">Issue Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="gjdjzjkrq" name="gjdjzjkrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">有效期</div>
							<div class="font-size-9">Valid Period </div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="gjdjzyxq" name="gjdjzyxq" data-date-format="yyyy-mm-dd" type="text" />
							                                                             
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">适航证号</div>
							<div class="font-size-9">Airworthiness No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control " id="shzh" name="shzh" type="text" maxlength="100" />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">颁发日期</div>
							<div class="font-size-9">Issue Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="shzjkrq" name="shzjkrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">有效期</div>
							<div class="font-size-9">Valid Period </div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="shzzjkrq" name="shzzjkrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">无线电台执照号</div>
							<div class="font-size-9">Radio No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control " id="wxdtxkzh" name="wxdtxkzh" type="text" maxlength="100" />
						</div>
					</div>
					
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">颁发日期</div>
							<div class="font-size-9">Issue Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="wxdtbfrq" name="wxdtbfrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">有效期</div>
							<div class="font-size-9">Valid Period</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control date-picker" id="dtzzjkrq" name="dtzzjkrq" data-date-format="yyyy-mm-dd" type="text" />
						</div>
					</div>
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8">
						<span id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
							<div class="font-size-12 margin-topnew-2">附件列表</div>
							<div class="font-size-9">File List</div>
						</span>
						<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
							<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:600px">
								<thead>
									<tr>
										<th class="colwidth-10">
											<div class="font-size-12 line-height-12">文件类型</div>
											<div class="font-size-9 line-height-12">File Type</div>
										</th>
									   	<th class="colwidth-30">
											<div class="font-size-12 line-height-12" id="chinaColFileTitle">文件说明</div>
											<div class="font-size-9 line-height-12" id="englishColFileTitle">File desc</div>
										</th>
										<th class="colwidth-13">
											<div class="font-size-12 line-height-12">文件大小</div>
											<div class="font-size-9 line-height-12">File size</div>
										</th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">上传人</div>
											<div class="font-size-9 line-height-12">Operator</div></th>
										<th class="colwidth-15">
											<div class="font-size-12 line-height-12">上传时间</div>
											<div class="font-size-9 line-height-12">Operate Time</div>
										</th>			
									</tr>
								</thead>
							   <tbody id="attachments_list_tbody">
							   		 <tr class="non-choice text-center"><td colspan="5">暂无数据 No data.</td></tr>
								</tbody>
							</table>
							</div>
						</div>
				</div>
			</div>
			
			<div class="panel panel-primary">
				<div id="attachHead" class="panel-heading bg-panel-heading" >
					<div class="font-size-12" id="chinaHead">关键部件初始信息</div>
					<div class="font-size-9" id="englishHead">Init Parts</div>
				</div>
				<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">发动机数量</div>
							<div class="font-size-9">Engine Number</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input class="form-control" id="fdjsl" name="fdjsl" type="number" value="2" onchange="bjUpdate()" maxlength="1" />
						</div>
					</div>
				
				
			
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8" style="overflow-x: auto;">
						<table class="table table-thin table-hover text-center table-set" id="bjcsTable" style="min-width:600px">
							<thead>
								<tr>
									<th class="colwidth-6">
									</th>
									<th class="colwidth-13">
										<div class="font-size-12 line-height-12">件号</div>
										<div class="font-size-9 line-height-12">P/N</div>
									</th>
									<th class="colwidth-7">
										<div class="font-size-12 line-height-12">序列号</div>
										<div class="font-size-9 line-height-12">S/N</div>
									</th>
								   <th class="colwidth-7">
										<div class="font-size-12 line-height-12">型号</div>
										<div class="font-size-9 line-height-12">Model</div>
									</th>
								   	<th class="colwidth-10">
										<div class="font-size-12 line-height-12">英文名称</div>
										<div class="font-size-9 line-height-12">Name</div>
									</th>
								   	<th class="colwidth-10">
										<div class="font-size-12 line-height-12">中文名称</div>
										<div class="font-size-9 line-height-12">Name</div>
									</th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">初始化日期</div>
										<div class="font-size-9 line-height-12">Init Date</div></th>
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">初始化小时</div>
										<div class="font-size-9 line-height-12">Init Hours</div>
									</th>			
									<th class="colwidth-10">
										<div class="font-size-12 line-height-12">初始化循环</div>
										<div class="font-size-9 line-height-12">Init Cycle</div>
									</th>			
								</tr>
							</thead>
						    <tbody id="bj_list_tbody">
							</tbody>
							<tfoot id="bj_list_tfoot" class="tfoot-hover">
								 <tr id="tableTr5">
									 <td class="text-right">APU</td>
									 <td>
									 	<div class='input-group ' style="min-width:100%">
											<input type="text"  value="" name="bj_jh" class="form-control" maxlength="100" id="ff">
										</div>
									</td>
									 <td><input type="text" class="form-control" name="bj_xlh" maxlength="50" ></td>
									 <td class="text-center"></td>
									 <td class="text-center"></td>
									 <td class="text-center"></td>
									 <td><input class="form-control date-picker-tb" name="rq" data-date-format="yyyy-mm-dd" type="text" /><input class="form-control" type="hidden" /></td>
									 <td><div class="input-group"><input class="form-control fxyz" name="xs" type="text" maxlength="20" /><input class="form-control" type="hidden" /><span name="xs" class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">APUH</span></div></td>
									 <td><div class="input-group"><input class="form-control fxzsyz" name="xh" type="text" maxlength="20" /><input class="form-control" type="hidden" /><span name="xh" class="input-group-addon dw"  style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">APUH</span></div></td>
								 </tr>
							</tfoot>
							
						</table>
					</div>
					
				</div>
			</div>
			
			<div class="panel panel-primary">
				<div id="attachHead" class="panel-heading bg-panel-heading" >
					<div class="font-size-12" id="chinaHead">其他信息</div>
					<div class="font-size-9" id="englishHead">Other Information</div>
				</div>
				<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-8">
						<span id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
							<div class="font-size-12 margin-topnew-2">日使用量</div>
							<div class="font-size-9">Daily Usage</div>
						</span>
						<div class="col-lg-7 col-md-7 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
							<table id="rsylTable" class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:600px">
								<thead>
									<tr>
										<th class="colwidth-5">
											<div class="font-size-12 line-height-12">监控项</div>
											<div class="font-size-9 line-height-12">Item</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-12">飞机</div>
											<div class="font-size-9 line-height-12">A/C REG</div>
										</th>
										<th class="colwidth-7">
											<div class="font-size-12 line-height-12">发动机</div>
											<div class="font-size-9 line-height-12">Engine</div>
										</th>
									   	<th class="colwidth-7">
											<div class="font-size-12 line-height-12">APU</div>
											<div class="font-size-9 line-height-12">APU</div>
										</th>
									</tr>
								</thead>
							    <tbody >
								    <tr>
										 <td class="text-center">时间</td>
										 <td >
										 	<div class="input-group"> 
												<input class="form-control input-sm fxyz" id="rq_fj" maxlength="20" name="zq" type="text">
												<input class='form-control' type='hidden' />
												<span class="input-group-addon dw" name="rq_fj" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FH</span>
											</div>
										</td>
										 <td>
											<div class="input-group "> 
												<input class="form-control input-sm fxyz" id="rq_fdj" maxlength="20" name="zq" type="text">
												<input class='form-control' type='hidden' />
												<span class="input-group-addon dw" name="rq_fdj" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">EH</span>
											</div>
										</td>
										 <td>
											<div class="input-group "> 
												<input class="form-control input-sm fxyz" id="rq_apu" maxlength="20" name="zq" type="text">
												<input class='form-control' type='hidden' />
												<span class="input-group-addon dw" name="rq_apu" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">APUH</span>
											</div>
										</td>
									 </tr>
								    <tr>
										 <td class="text-center">循环</td>
										 <td>
										 	<div class="input-group "> 
												<input class="form-control input-sm fxzsyz" id="xh_fj" maxlength="20" name="zq" type="text">
												<input class='form-control' type='hidden' />
												<span class="input-group-addon dw" name="xh_fj" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">FC</span>
											</div>
										</td>
										 <td>
											<div class="input-group "> 
												<input class="form-control input-sm fxzsyz" id="xh_fdj" maxlength="20" name="zq" type="text">
												<input class='form-control' type='hidden' />
												<span class="input-group-addon dw" name="xh_fdj" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">EC</span>
											</div>
										</td>
										 <td>
											<div class="input-group "> 
												<input class="form-control input-sm fxzsyz" id="xh_apu" maxlength="20" name="zq" type="text">
												<input class='form-control' type='hidden' />
												<span class="input-group-addon dw" name="xh_apu" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">APUC</span>
											</div>
										</td>
									 </tr>
								 
								</tbody>
							</table>
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">机身改装记录</div>
								<div class="font-size-9">Fuselage Record</div>
							</span>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="jsgzjl" name="jsgzjl" class="form-control" maxlength="100" style="height:55px"></textarea>
							</div>
						</div>
							
						<div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">备注</div>
								<div class="font-size-9">Note</div>
							</span>
							<div class="col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
								<textarea id="bz" name="bz" class="form-control" maxlength="100" style="height:55px"></textarea>
							</div>
						</div>	
						</div>
					</div>
					
						 
        	</div>
		</div>		
	</div>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/aircraftinfo/aircraftinfo_view.js"></script>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>
