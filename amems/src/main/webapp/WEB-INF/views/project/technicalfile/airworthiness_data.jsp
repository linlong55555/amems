<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>适航性资料</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content">
		<div class="panel panel-primary">
		    <!-- panel-heading -->
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body">
			<!-- 搜索框 -->
			    <div  class='searchContent'>
			    <!-- 上传按钮  -->
				<div class="pull-left padding-left-0">
					<a href="javascript:void(0);"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" >
						<div class="font-size-12">上传</div>
						<div class="font-size-9">Upload</div>
					</a> 
				</div>
				<div class="pull-left padding-left-5 padding-right-0" >
						<span class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">文件类型</div>
							<div class="font-size-9">File Type</div>
						</span>
						<div class="pull-left padding-left-8 padding-right-0" style='width:200px;'>
							<select class='form-control'>
							<option>请选择</option>
							</select>
						</div>
				</div>
				<div class="pull-left padding-left-5 padding-right-0" >
					<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' style='vertical-align:text-bottom'/>&nbsp;保存&nbsp;&nbsp;</label>
					<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' style='vertical-align:text-bottom;'/>&nbsp;下发&nbsp;&nbsp;</label>
					<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'><input type='checkbox' style='vertical-align:text-bottom;'/>&nbsp;关闭&nbsp;&nbsp;</label>
				</div>
				<div class="pull-left padding-left-5 padding-right-0" >
						<span class="pull-left text-right padding-left-0 padding-right-0">
							<div class="font-size-12">分类</div>
							<div class="font-size-9">Classification</div>
						</span>
						<div class="pull-left padding-left-8 padding-right-0" style='width:200px;'>
							<select class='form-control'>
							<option>请选择</option>
							</select>
						</div>
				</div>
				<!-- 关键字搜索 -->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class="pull-right padding-left-10 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='文件编号/文件名称/ATA/主题/备注'  class="form-control ">
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
	                   		<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="search();">
								<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
								<div class="pull-left padding-top-5">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
					   		</button>
	                    </div> 
					</div>
				</div>
				<div class='clearfix'></div>
				<!-- 更多的搜索框 -->
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none search-height border-cccccc" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源</div>
							<div class="font-size-9">Source</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control'>
								<option value="" selected="true">显示全部All</option>
						    </select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">ATA章节</div>
							<div class="font-size-9">ATA</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">生效日期</div>
							<div class="font-size-9">Effective date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control'>
								<option value="" selected="true">显示全部All</option>
						    </select>
						</div>
					</div>
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				<!-- 搜索框End -->
				
				<div class='table_pagination'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-10 table-height" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-set">
							<thead>
								<tr>
									<th class="fixed-column colwidth-10" >
										<div class="font-size-12 line-height-18" >操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th width='50' >
										<div class="font-size-12 line-height-18"></div>
										<div class="font-size-9 line-height-18"></div>
									</th>
									<th class="colwidth-10 sorting" >
										<div class="font-size-12 line-height-18">来源</div>
										<div class="font-size-9 line-height-18">Source</div>
									</th>
									<th class="colwidth-10 sorting" >
										<div class="important">
											<div class="font-size-12 line-height-18">文件类型</div>
											<div class="font-size-9 line-height-18">File Type</div>
										</div>
									</th>
									<th class="colwidth-10 sorting">
										<div class="font-size-12 line-height-18">文件编号</div>
										<div class="font-size-9 line-height-18">Training Type</div>
									</th>
									<th class="colwidth-10 sorting" >
										<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Host</div>
									</th>
									<th class="colwidth-10 sorting" >
										<div class="font-size-12 line-height-18">主题</div>
										<div class="font-size-9 line-height-18">Object</div>
									</th>
									<th class="colwidth-10 sorting" >
										<div class="font-size-12 line-height-18">关联技术文件</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
									<th class="colwidth-20" >
										<div class="font-size-12 line-height-18">状态</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="colwidth-13 sorting" >
										<div class="important">
											<div class="font-size-12 line-height-18">分类</div>
											<div class="font-size-9 line-height-18">Teacher</div>
										</div>
									</th>
									<th class="colwidth-30 sorting" >
										<div class="important">
											<div class="font-size-12 line-height-18">评估期限</div>
											<div class="font-size-9 line-height-18">Address</div>
										</div>
									</th>
									<th class="colwidth-9 sorting" >
										<div class="font-size-12 line-height-18">剩余(天)</div>
										<div class="font-size-9 line-height-18">Whether</div>
									</th>
									<th class="colwidth-10 sorting" >
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">Form</div>
									</th>
									<th class="colwidth-10 sorting" >
										<div class="font-size-12 line-height-18">ATA</div>
										<div class="font-size-9 line-height-18">Form</div>
									</th>
									<th class="colwidth-10 sorting">
										<div class="font-size-12 line-height-18">生效日期</div>
										<div class="font-size-9 line-height-18">Hour</div>
									</th>
									<th class="colwidth-10 sorting" >
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Day</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Interval</div>
									</th>
									<th class="colwidth-7 sorting" >
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Budget</div>
									</th>
									<th class="colwidth-20">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									
								</tr>
							</thead>
							<tbody id="airworthiness_list"></tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class='clearfix'></div>
				</div>	
				<!-- 隐藏部门的div -->
				<div class='bottom_hidden_content' style='display:none;'>
				      <div class='col-lg-4 col-sm-6 col-xs-12' style='padding-left:0px;padding-right:8px;'>
				        <div class="col-lg-12" style='padding-left:0px;padding-right:0px;'>
							<div class="panel panel-primary">
							    <!-- panel-heading -->
								<div class="panel-heading bottom_hidden_left_header">
								        <div class="font-size-12 line-height-12">评估技术单号</div>
										<div class="font-size-9 line-height-9">Number of single evaluation technology</div>
								</div>
								<div class="panel-body bottom_hidden_left_content" style='overflow:auto;padding-left:0px;padding-right:0px;padding-top:0px;'>
									<table class='table table-striped table-hover text-center' style='border-bottom:1px solid #dddddd;'>
									<thead>
									<tr>
									<th >
										<div class="font-size-12 line-height-12">机型</div>
										<div class="font-size-9 line-height-9">Models</div>
									</th>
									<th  >
										<div class="font-size-12 line-height-12">评估人</div>
										<div class="font-size-9 line-height-9">Evaluator</div>
									</th>
									<th  >
										<div class="font-size-12 line-height-12">评估单</div>
										<div class="font-size-9 line-height-9">Evaluator</div>
									</th>
									<th >
										<div class="font-size-12 line-height-12">下达指令</div>
										<div class="font-size-9 line-height-9">Oder</div>
									</th>
									<th>
										<div class="font-size-12 line-height-12">当前状态</div>
										<div class="font-size-9 line-height-9">Current state</div>
									</th>
									</tr>
									</thead>
									<tbody>
									<tr>
									<td>机型</td>
									<td>张三</td>
									<td><a href='#'>EO-201706-001</a></td>
									<td>EO/修订维修方案/NRC</td>
									<td>提交</td>
									</tr>
									<tr>
									<td>机型</td>
									<td>张三</td>
									<td><a href='#'>EO-201706-001</a></td>
									<td>EO/修订维修方案/NRC</td>
									<td>提交</td>
									</tr>
									<tr>
									<td>机型</td>
									<td>张三</td>
									<td><a href='#'>EO-201706-001</a></td>
									<td>EO/修订维修方案/NRC</td>
									<td>提交</td>
									</tr>
									<tr>
									<td>机型</td>
									<td>张三</td>
									<td><a href='#'>EO-201706-001</a></td>
									<td>EO/修订维修方案/NRC</td>
									<td>提交</td>
									</tr>
									<tr>
									<td>机型</td>
									<td>张三</td>
									<td><a href='#'>EO-201706-001</a></td>
									<td>EO/修订维修方案/NRC</td>
									<td>提交</td>
									</tr>
									<tr>
									<td>机型</td>
									<td>张三</td>
									<td><a href='#'>EO-201706-001</a></td>
									<td>EO/修订维修方案/NRC</td>
									<td>提交</td>
									</tr>
									<tr>
									<td>机型</td>
									<td>张三</td>
									<td><a href='#'>EO-201706-001</a></td>
									<td>EO/修订维修方案/NRC</td>
									<td>提交</td>
									</tr>
									</tbody>
									</table>
								</div>
						    </div>
						 </div>
				      </div>
				      <div class='col-lg-8 col-sm-6 col-xs-12' style='padding-left:8px;padding-right:0px;'>
				      	 <div class="col-lg-12" style='padding-left:0px;padding-right:0px;'>
							<div class="panel panel-primary">
							    <!-- panel-heading -->
								<div class="panel-heading bottom_hidden_left_header">
									<div class="font-size-12 line-height-12">下达指令</div>
									<div class="font-size-9 line-height-9">An order</div>
								</div>
								<div class="panel-body bottom_hidden_left_content" style='overflow:auto;padding-left:0px;padding-right:0px;padding-top:0px;'>
								<table class='table table-striped table-hover text-center' style='border-bottom:1px solid #dddddd;'>
									<thead>
									<tr>
									<th  width='150'>
										<div class="font-size-12 line-height-12">下达指令</div>
										<div class="font-size-9 line-height-9">Order</div>
									</th>
									<th  width='150'>
										<div class="font-size-12 line-height-12">指派人</div>
										<div class="font-size-9 line-height-9">Designated person</div>
									</th>
									<th  width='150'>
										<div class="font-size-12 line-height-12">反馈状态</div>
										<div class="font-size-9 line-height-9">Feedback</div>
									</th>
									<th  width='150'>
										<div class="font-size-12 line-height-12">产出</div>
										<div class="font-size-9 line-height-9">Output</div>
									</th>
									<th  >
										<div class="font-size-12 line-height-12">内容</div>
										<div class="font-size-9 line-height-9">Content</div>
									</th>
									</tr>
									</thead>
									<tbody>
									<tr>
									<td>EO</td>
									<td>张三</td>
									<td><a href='#'>已反馈</a></td>
									<td><a href='#'>EO-201706-001</a></td>
									<td></td>
									</tr>
									<tr>
									<td>订单维修方案</td>
									<td>李四</td>
									<td>未反馈</td>
									<td>-</td>
									<td></td>
									</tr>
									<tr>
									<td>修订MEL</td>
									<td>王五</td>
									<td>未反馈</td>
									<td>-</td>
									<td></td>
									</tr>
									</tbody>
									</table>
								</div>
						    </div>
						 </div>
				      </div>
				      <div class='clearfix'></div>
				</div>
			</div>
		</div>		
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/project/technicalfile/airworthiness_data.js"></script>
</body>
</html>
