<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>审核报告</title>
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

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: auto;">
					<table class="table table-thin table-bordered table-set" style="min-width: 1000px;">
						<thead>
							<tr>
								<th class="colwidth-13" >	
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">审核报告编号</div>
									<div class="font-size-9 line-height-18">Audit report No.</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">审核项目</div>
									<div class="font-size-9 line-height-18">Audit items</div>
								</th>
								<th class="colwidth-9" >
									<div class="font-size-12 line-height-18">审核类别</div>
									<div class="font-size-9 line-height-18">Audit Type</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">被审核单位</div>
									<div class="font-size-9 line-height-18">Audit No.</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">负责人</div>
									<div class="font-size-9 line-height-18">Person liable</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 line-height-18">责任审核员</div>
									<div class="font-size-9 line-height-18">auditor</div>
								</th>
								<th class="colwidth-9" >
									<div class="font-size-12 line-height-18">审核日期</div>
									<div class="font-size-9 line-height-18">Audit Date</div>
								</th>
								<th class="colwidth-30" >
									<div class="font-size-12 line-height-18">审核综述</div>
									<div class="font-size-9 line-height-18">Audit review</div>
								</th>
							</tr>
						</thead>
						<tbody id="report_list"></tbody>
					</table>
				</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>

<!-------详情对话框 Start-------->
	
<div class="modal fade" id="alertsummaryForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:85%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertsummaryFormBody">
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
							
							<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核报告编号</div>
										<div class="font-size-9 line-height-18">Audit report No.</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="shbgbh" name="shbgbh" maxlength="50" />
										<input type="hidden" class="form-control " id="id" />
									</div>
								</div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核项目</div>
										<div class="font-size-9 line-height-18">Audit items</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="shxm" name="shxm" maxlength="50" />
									</div>
								</div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="zdr" name="zdr" value="小张" readonly />
									</div>
								</div>
							
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">制单日期</div>
										<div class="font-size-9 line-height-18">Create Date</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control " id="zdrq" name="zdrq" value="2017-04-18" readonly/>
									</div>
								</div>
							
								<div class="clearfix"></div>
								
									<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">被审核单位</div>
										<div class="font-size-9 line-height-18">Audit No.</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
										<input type="text" class="form-control " id="bshdw" name="bshdw" readonly/>
										<span class="input-group-btn">
											<button type="button" id="deptbtn" class="btn btn-primary form-control" data-toggle="modal" onclick="openDeptWin()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
									</div>
								</div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">负责人</div>
										<div class="font-size-9 line-height-18">Person liable</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
										<input type="text" class="form-control " id="fzr" name="fzr" readonly />
										<span class="input-group-btn">
											<button type="button" id="fzrbtn" class="btn btn-primary form-control" data-toggle="modal" onclick="openUserWin(1)">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
									</div>
								</div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">责任审核员</div>
										<div class="font-size-9 line-height-18">auditor</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
										<input type="text" class="form-control " id="zrshy" name="zrshy" readonly/>
										<span class="input-group-btn">
											<button type="button" id="zrshybtn" class="btn btn-primary form-control" data-toggle="modal" onclick="openUserWin(2)">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</span>
									</div>
								</div>
							
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核日期</div>
										<div class="font-size-9 line-height-18">Audit Date</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<input class="form-control date-picker" id="shrq" name="shrq" data-date-format="yyyy-mm-dd" type="text" />
									</div>
								</div>
							
								<div class="clearfix"></div>
								
								<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">审核类别</div>
										<div class="font-size-9 line-height-18">Audit Type</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
										<label style="margin-right: 20px;font-weight: normal">
									    	<input name="shlb" type="radio" value="系统审核" />系统审核
									    </label> 
										<label style="margin-right: 20px;font-weight: normal">
											<input name="shlb" type="radio" value="生产审核" />生产审核
										</label> 
										<label style="font-weight: normal">
											<input name="shlb" type="radio" value="专项审核" />专项审核
										</label> 
									</div>
								</div>
								
								<div class="clearfix"></div>
							
							 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18">审核综述</div>
										<div class="font-size-9 line-height-18">Audit review</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
										<textarea class="form-control" id="shzs" name="shzs" maxlength="300" ></textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">附件</div>
										<div class="font-size-9 line-height-18">Enclosure</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0 input-group">
										<label style="font-weight:normal;margin-right: 121px;" >
											<input class="pull-left margin-right-10 " type="checkbox" id="shjcd" name="shjcd"  />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">《审核检查单》</div>
											</div> 
										</label> 
										<label style='font-weight:normal;text-align:center;vertical-align:middle;'>
											<input type="text" id="shjcde" name="shjcde" class="form-control " readonly/>
										</label>
										<label style='text-align:center;vertical-align:middle;'>
											<button id="shjcdeBtn" type="button" class="btn btn-primary form-control" data-toggle="modal" onclick="openShjcdWin()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</label>
										
										<div class="clearfix"></div>
										
										<label style="font-weight:normal;margin-right: 20px;" >
											<input class="pull-left margin-right-10 " type="checkbox" id="shhzd" name="shhzd" />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">《质量/安全审核发现问题汇总单》</div>
											</div> 
										</label> 
										<label style='font-weight:normal;text-align:center;vertical-align:middle;'>
											<input type="text" id="shhzde" name="shhzde" class="form-control " readonly/>
										</label>
										<label style='text-align:center;vertical-align:middle;'>
											<button id="shhzdeBtn" type="button" class="btn btn-primary form-control" data-toggle="modal" onclick="openShhzdWin()">
												<i class="icon-search cursor-pointer"></i>
											</button>
										</label>
									</div>
								</div>
								
							 	<div class="clearfix"></div>
								
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">报</div>
										<div class="font-size-9 line-height-18">newspaper</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0 input-group">
										<label style="font-weight:normal;margin-right: 20px;" >
											<input class="pull-left margin-right-10 " type="checkbox" id="wxfzjl" name="wxfzjl" />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">维修副总经理</div>
											</div> 
										</label> 
										<label style="font-weight:normal">
											<input class="pull-left margin-right-10 " type="checkbox" id="jwgcbz" name="jwgcbz" />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">机务工程部长</div>
											</div> 
										</label>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label class="col-lg-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">分发</div>
										<div class="font-size-9 line-height-18">distribute</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
										<label style="font-weight:normal;margin-right: 20px;" >
											<input class="pull-left margin-right-10" type="checkbox" id="scc" name="scc" />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">生产处</div>
											</div> 
										</label> 
										<label style="font-weight:normal;margin-right: 20px;">
											<input class="pull-left margin-right-10 " type="checkbox" id="gcc" name="gcc" />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">工程处</div>
											</div> 
										</label>
										<label style="font-weight:normal;margin-right: 20px;">
											<input class="pull-left margin-right-10 " type="checkbox" id="hcc" name="hcc" />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">航材处</div>
											</div> 
										</label>
										<label style="font-weight:normal;margin-right: 20px;">
											<input class="pull-left margin-right-10 " type="checkbox" id="zlc" name="zlc" />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">质量处</div>
											</div> 
										</label>
										<label style="font-weight:normal;margin-right: 20px;">
											<input class="pull-left margin-right-10 " type="checkbox" id="pxc" name="pxc" />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">培训处</div>
											</div> 
										</label>
										<label style="font-weight:normal;margin-right: 20px;">
											<input class="pull-left margin-right-10 " type="checkbox" id="wxdw" name="wxdw" />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">供应商/委托维修单位</div>
											</div> 
										</label>
										
										<div class="clearfix"></div>
										
										<label style="font-weight:normal;margin-right: 20px;">
											<input class="pull-left margin-right-10 " type="checkbox" id="qt" name="qt" />
											<div class="pull-right">
												<div class="font-size-12 line-height-18">其它</div>
											</div> 
										</label>
										<label style='font-weight:normal;text-align:center;vertical-align:middle;'>
											<input type="text" id="qte" name="qte" class="form-control "/>
										</label>
									</div>
									
								</div>
								
								<div class="clearfix"></div>
								
				 				<div id="pzdiv" class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
				 
								 <label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"  >
										<div class="font-size-12 line-height-18">批准意见</div>
										<div class="font-size-9 line-height-18">Approval Opinion</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
											<textarea class="form-control" id="pfyj" name="pfyj" maxlength="150" ></textarea>
									</div>
									 <div class="clearfix"></div>
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
										<div class="font-size-12 line-height-18"></div>
										<div class="font-size-9 line-height-18"></div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
										<label style="margin-right: 50px"></label>　
										<div class="pull-left padding-right-10">
										<div class="font-size-12 line-height-18" style="font-weight:bold">批准人 &nbsp;<label style="margin-right: 50px;font-weight:normal">王晓</label></div>
										<div class="font-size-9 line-height-12">Appr. By</div>
										</div>
										<div class="pull-left">
										<div class="font-size-12 line-height-18" style="font-weight:bold">批准日期    &nbsp;<label style="margin-right: 50px;font-weight:normal">2017-04-13</label></div>
										<div class="font-size-9 line-height-12">Approved Date</div>
										</div>
								 	</div>
								 <div class="clearfix"></div>
								</div>
								
									<div class="clearfix"></div>
								
				 				<div id="gbdiv" class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
				 
								 <label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"  >
										<div class="font-size-12 line-height-18">审核报告关闭意见</div>
										<div class="font-size-9 line-height-18">Close Opinion</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
											<textarea class="form-control" id="bgyj" name="bgyj" maxlength="150" >所有整改措施均已验证落实,详见《审核与纠正措施处理表》,关闭审核未法宣问题,关闭审核报告
											</textarea>
									</div>
									 <div class="clearfix"></div>
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
										<div class="font-size-12 line-height-18"></div>
										<div class="font-size-9 line-height-18"></div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
										<label style="margin-right: 50px"></label>　
										<div class="pull-left padding-right-10">
										<div class="font-size-12 line-height-18" style="font-weight:bold">责任审核员 &nbsp;<label style="margin-right: 50px;font-weight:normal">小刘</label></div>
										<div class="font-size-9 line-height-12">auditor</div>
										</div>
										<div class="pull-left">
										<div class="font-size-12 line-height-18" style="font-weight:bold">日期   &nbsp;<label style="margin-right: 50px;font-weight:normal">2017-04-18</label></div>
										<div class="font-size-9 line-height-12">Date</div>
										</div>
								 	</div>
								 <div class="clearfix"></div>
								</div>
								
							<div class="clearfix"></div>
						</form>
						
					 	 <div class="text-right margin-top-10 margin-right-0">
							<button id="save" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="submit" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			                   	<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
							</button>
							<button id="audit" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			                   	<div class="font-size-12">审核</div>
								<div class="font-size-9">Audit</div>
							</button>
							<button id="approve" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			                   	<div class="font-size-12">通过</div>
								<div class="font-size-9">adopt</div>
							</button>
							<button id="close" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			                   	<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
							<button id="reject" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			                   	<div class="font-size-12">驳回</div>
								<div class="font-size-9">Reject</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">取消</div>
								<div class="font-size-9">cancel</div>
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

<!------- 详情对话框 End-------->

<!------- 部门对话框 Start-------->

<div class="modal fade" id="deptModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:750px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="deptBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">部门列表</div>
						<div class="font-size-9 ">Department List</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12">
		            	
			         		<div class="clearfix"></div>
							<!-- start:table -->
							<div class="margin-top-10 overflow-auto">
								<table class="table table-bordered table-striped table-hover text-center table-set" style="min-width: 400px;">
									<thead>
										<tr>
											<th width="50px">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">机构部门</div>
												<div class="font-size-9 notwrap">Department</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="deptList">
									</tbody>
								</table>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="closeDept()">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="closeDept()">
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


<!------- 部门对话框 End-------->

<!------- 用户对话框 Start-------->

<div class="modal fade" id="UserMultiModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:750px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
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
											<th width="50px">
												<div class="font-size-12 notwrap">选择</div>
												<div class="font-size-9 notwrap">Choice</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">用户名称</div>
												<div class="font-size-9 notwrap">User Name</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">机构部门</div>
												<div class="font-size-9 notwrap">Department</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="userList">
									</tbody>
								</table>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="closeUser()">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="closeUser()">
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


<!------- 用户对话框 End-------->

<!------- 审核检查单对话框 Start-------->

<div class="modal fade" id="ShjcdMultiModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:750px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="ShjcdModalBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">审核检查单</div>
						<div class="font-size-9 ">Audit checklist</div>
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
												<div class="font-size-12 notwrap">制表日期</div>
												<div class="font-size-9 notwrap">Create Date</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">制表人</div>
												<div class="font-size-9 notwrap">Creator</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">单号</div>
												<div class="font-size-9 notwrap">No.</div>
											</th>
											<th>
												<div class="font-size-12 notwrap">备注</div>
												<div class="font-size-9 notwrap">Remark</div>
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


<!------- 审核检查单对话框 End-------->

<!------- 质量/安全审核发现问题汇总单对话框 Start-------->
<div class="modal fade" id="ShhzdMultiModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:750px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="ShhzdModalBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">质量/安全审核发现问题汇总单</div>
						<div class="font-size-9 ">Summary</div>
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
												<a href="#" onclick="SelectUtil.performSelectAll('ShhzdList')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('ShhzdList')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											</th>
											<th>
												<div class="font-size-12 line-height-18">审核项目</div>
												<div class="font-size-9 line-height-18">Audit items</div>
											</th>
											<th>
												<div class="font-size-12 line-height-18">审核编号</div>
												<div class="font-size-9 line-height-18">Audit No.</div>
											</th>
											<th>
												<div class="font-size-12 line-height-18">审核类别</div>
												<div class="font-size-9 line-height-18">Audit Type</div>
											</th>
											<th>
												<div class="font-size-12 line-height-18">备注</div>
												<div class="font-size-9 line-height-18">Remark</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="ShhzdList">
									</tbody>
								</table>
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="closeShhzd()">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="closeShhzd()">
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


<!------- 质量/安全审核发现问题汇总单对话框 End-------->


<script type="text/javascript">

	var alertFormId = 'alertsummaryForm';
	var headChinaId = 'headChina';
	var headEnglishId = 'headEnglish';
	var list = [];
	var xh = 1;
	var rlx = 1;
	
	$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		initData();
		loadContentHtml();
	});
	
	function loadContentHtml(){
		var htmlContent = '';
		$.each(list,function(index,row){
	   		if (index % 2 == 0) {
				htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
	  		} else {
		   		htmlContent += "<tr bgcolor=\"#fefefe\" >";
	  		}
	   		
	   	 	htmlContent += "<td class='text-center'>";
	   		htmlContent += "<i class='icon-foursquare color-blue cursor-pointer' onClick=\"openAudit('"+ row.id + "')\" title='审核 Review'>&nbsp;&nbsp;</i>";
	   		htmlContent += "<i class='icon-check color-blue cursor-pointer' onClick=\"openApprove('"+ row.id + "')\" title='批准 Approved'>&nbsp;&nbsp;</i>";
	   		htmlContent += "<i class='icon-power-off color-blue cursor-pointer' onClick=\"openClose('"+ row.id + "')\" title='关闭 close'>&nbsp;&nbsp;</i>";
	   		htmlContent += "<i class='icon-edit color-blue cursor-pointer' onClick=\"openEdit('"+ row.id + "')\" title='编辑 Edit'>&nbsp;&nbsp;</i>";
		   	htmlContent += "<i class='icon-trash color-blue cursor-pointer' onClick=\"del('"+ row.id + "')\" title='作废 Invalid'></i>";  
		   	htmlContent += "</td>";  

	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.shbgbh)+"' class='text-left' >"+StringUtil.escapeStr(row.shbgbh)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.shxm)+"' class='text-left' >"+StringUtil.escapeStr(row.shxm)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.shlb)+"' class='text-left' >"+StringUtil.escapeStr(row.shlb)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.bshdw)+"' class='text-left' >"+StringUtil.escapeStr(row.bshdw)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.fzr)+"' class='text-left' >"+StringUtil.escapeStr(row.fzr)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.fzshy)+"' class='text-left' >"+StringUtil.escapeStr(row.fzshy)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.shrq)+"' class='text-center' >"+StringUtil.escapeStr(row.shrq)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.shzs)+"' class='text-left' >"+StringUtil.escapeStr(row.shzs)+"</td>";
	   		
	  		htmlContent += "</tr>";  
	    
	   });
	   $("#report_list").empty();
	   if(htmlContent == ''){
		   htmlContent = "<tr><td colspan=\"9\" class='text-center'>暂无数据 No data.</td></tr>";
	   }
	   $("#report_list").html(htmlContent);
 	}
	
	//打开新增弹出框
	function openAdd(){
		$("#save", $("#"+alertFormId)).show();
		$("#submit", $("#"+alertFormId)).show();
		$("#audit", $("#"+alertFormId)).hide();
		$("#approve", $("#"+alertFormId)).hide();
		$("#close", $("#"+alertFormId)).hide();
		$("#reject", $("#"+alertFormId)).hide();
		$("#pzdiv", $("#"+alertFormId)).hide();
		$("#gbdiv", $("#"+alertFormId)).hide();
		$("#"+headChinaId, $("#"+alertFormId)).html('新增');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Add');
		var obj = {};
		obj.id = '';
		obj.shbgbh = '';
		obj.shxm = '';
		obj.shlb = '系统审核';
		obj.bshdw = '';
		obj.fzr = '';
		obj.zrshy = '';
		obj.shrq = '';
		obj.shzs = '';
		obj.zdr = '小张';
		obj.zdrq = '2017-04-18';
		obj.shjcd = 0;
		obj.shjcde = "";
		obj.shhzd = 0;
		obj.shhzde = "";
		obj.qte = "";
		setData(obj);
		$("#"+alertFormId).modal("show");
		setEdit();
	}
	
	//打开修改弹出框
	function openEdit(id){
		$("#save", $("#"+alertFormId)).show();
		$("#submit", $("#"+alertFormId)).show();
		$("#audit", $("#"+alertFormId)).hide();
		$("#approve", $("#"+alertFormId)).hide();
		$("#close", $("#"+alertFormId)).hide();
		$("#reject", $("#"+alertFormId)).hide();
		$("#pzdiv", $("#"+alertFormId)).hide();
		$("#gbdiv", $("#"+alertFormId)).hide();
		$("#"+headChinaId, $("#"+alertFormId)).html('修改');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Edit');
		var obj = getRowData(id);
		setData(obj);
		$("#"+alertFormId).modal("show");
		setEdit();
	}
	
	//打开审核弹出框
	function openAudit(id){
		$("#save", $("#"+alertFormId)).hide();
		$("#submit", $("#"+alertFormId)).hide();
		$("#audit", $("#"+alertFormId)).show();
		$("#approve", $("#"+alertFormId)).hide();
		$("#close", $("#"+alertFormId)).hide();
		$("#reject", $("#"+alertFormId)).hide();
		$("#pzdiv", $("#"+alertFormId)).hide();
		$("#gbdiv", $("#"+alertFormId)).hide();
		$("#"+headChinaId, $("#"+alertFormId)).html('审核');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Audit');
		var obj = getRowData(id);
		setData(obj);
		$("#"+alertFormId).modal("show");
		setEdit();
	}
	
	//打开批准弹出框
	function openApprove(id){
		$("#save", $("#"+alertFormId)).hide();
		$("#submit", $("#"+alertFormId)).hide();
		$("#audit", $("#"+alertFormId)).hide();
		$("#approve", $("#"+alertFormId)).show();
		$("#close", $("#"+alertFormId)).hide();
		$("#reject", $("#"+alertFormId)).show();
		$("#pzdiv", $("#"+alertFormId)).show();
		$("#gbdiv", $("#"+alertFormId)).hide();
		$("#"+headChinaId, $("#"+alertFormId)).html('批准');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Approve');
		var obj = getRowData(id);
		setData(obj);
		$("#"+alertFormId).modal("show");
		setView();
		$("#pfyj", $("#"+alertFormId)).removeAttr("readonly");
	}
	
	//打开关闭弹出框
	function openClose(id){
		$("#save", $("#"+alertFormId)).hide();
		$("#submit", $("#"+alertFormId)).hide();
		$("#audit", $("#"+alertFormId)).hide();
		$("#approve", $("#"+alertFormId)).hide();
		$("#close", $("#"+alertFormId)).show();
		$("#reject", $("#"+alertFormId)).show();
		$("#pzdiv", $("#"+alertFormId)).show();
		$("#gbdiv", $("#"+alertFormId)).show();
		$("#"+headChinaId, $("#"+alertFormId)).html('批准');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Approve');
		var obj = getRowData(id);
		setData(obj);
		$("#"+alertFormId).modal("show");
		setView();
	}
	
	function openDeptWin(){
		var list = [{dept:"研发"},
					{dept:"人事"}
				];
		var htmlContent = '';
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='checkUser(this)'>";
			   } else {
				   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='checkUser(this)'>";
			   }
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' dept='"+row.dept+"' index='"+index+"' /></td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.dept +"</td>";
			
			htmlContent += "</tr>";  
		});
		$("#deptList", $("#deptModal")).empty();
		$("#deptList", $("#deptModal")).html(htmlContent);
		$("#deptModal").modal("show");
	}
	
	function closeDept(){
		var $checkedRadio = $("#deptList", $("#deptModal")).find("input:checked");
		var dept = $checkedRadio.attr("dept");	
		$("#bshdw", $("#"+alertFormId)).val(dept);
		$("#deptModal").modal("hide");
	}
	
	function openUserWin(type){
		rlx = type;
		var list = [{username:"孙然",dept:"研发"},
		            {username:"小张",dept:"总经办"},
					{username:"张兵",dept:"人事"}
				];
		var htmlContent = '';
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='checkUser(this)'>";
			   } else {
				   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='checkUser(this)'>";
			   }
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' username='"+row.username+"' index='"+index+"' /></td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.username +"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.dept +"</td>";
			
			htmlContent += "</tr>";  
		});
		$("#userList", $("#UserMultiModal")).empty();
		$("#userList", $("#UserMultiModal")).html(htmlContent);
		$("#UserMultiModal").modal("show");
	}
	
	function checkUser(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	}
	
	function closeUser(){
		var $checkedRadio = $("#userList", $("#UserMultiModal")).find("input:checked");
		var username = $checkedRadio.attr("username");	
		if(rlx == 1){
			$("#fzr", $("#"+alertFormId)).val(username);
		}else{
			$("#zrshy", $("#"+alertFormId)).val(username);
		}
		$("#UserMultiModal").modal("hide");
	}
	
	function openShjcdWin(){
		var list = [{zbrq:"2017-03-01",zbr:"李明",dh:"SMS-20170301",bz:""},
		            {zbrq:"2017-04-05",zbr:"张胜男",dh:"SMS-20170405",bz:""},
		            {zbrq:"2017-05-03",zbr:"王炳森",dh:"SMS-20170503",bz:""}
				];
		var htmlContent = '';
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='clickRow(this)' >";
			} else {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='clickRow(this)' >";
		  	}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' dh='"+row.dh+"' index='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','shjcdList')\" /></td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.zbrq +"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.zbr +"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.dh +"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.bz +"</td>";
			
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
		$("#shjcdList", $("#ShjcdMultiModal")).find("tr input:checked").each(function(){
			dh += $(this).attr("dh")+",";	
		});
		if(dh != ''){
			dh = dh.substring(0,dh.length - 1);
		}
		$("#shjcde", $("#"+alertFormId)).val(dh);
		$("#ShjcdMultiModal").modal("hide");
	}
	
	function openShhzdWin(){
		var list = [{shxm:"设立农药生产企业",shbh:"SH-001",shlb:"生产审核",bz:"审核通过"},
		            {shxm:"建设规划",shbh:"SH-002",shlb:"专项审核",bz:""}
				];
		var htmlContent = '';
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='clickhzRow(this)' >";
			} else {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='clickhzRow(this)' >";
		  	}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' shbh='"+row.shbh+"' index='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','ShhzdList')\" /></td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.shxm +"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.shbh +"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.shlb +"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+row.bz +"</td>";
			
			htmlContent += "</tr>";  
		});
		$("#ShhzdList", $("#ShhzdMultiModal")).empty();
		$("#ShhzdList", $("#ShhzdMultiModal")).html(htmlContent);
		$("#ShhzdMultiModal").modal("show");
	}
	
	function clickhzRow(row){
		SelectUtil.checkRow($(row).find("input[type='checkbox']"),'selectAllId','ShhzdList');
	}
	
	function closeShhzd(){
		var shbh = '';
		$("#ShhzdList", $("#ShhzdMultiModal")).find("tr input:checked").each(function(){
			shbh += $(this).attr("shbh")+",";	
		});
		if(shbh != ''){
			shbh = shbh.substring(0,shbh.length - 1);
		}
		$("#shhzde", $("#"+alertFormId)).val(shbh);
		$("#ShhzdMultiModal").modal("hide");
	}
	
	//设置表单数据
	function setData(obj){
		$("#id", $("#"+alertFormId)).val(obj.id);
		$("#shbgbh", $("#"+alertFormId)).val(obj.shbgbh);
		$("#shxm", $("#"+alertFormId)).val(obj.shxm);
		$("input:radio[name='shlb'][value='"+obj.shlb+"']", $("#"+alertFormId)).attr("checked",true); 
		$("#bshdw", $("#"+alertFormId)).val(obj.bshdw);
		$("#fzr", $("#"+alertFormId)).val(obj.fzr);
		$("#zrshy", $("#"+alertFormId)).val(obj.zrshy);
		$("#shrq", $("#"+alertFormId)).val(obj.shrq);
		$("#shzs", $("#"+alertFormId)).val(obj.shzs);
		$("#zdr", $("#"+alertFormId)).val(obj.zdr);
		$("#zdrq", $("#"+alertFormId)).val(obj.zdrq);
		if(obj.shjcd == 1){
			$("#shjcd", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#shjcd", $("#"+alertFormId)).removeAttr("checked");
		}
		if(obj.shhzd == 1){
			$("#shhzd", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#shhzd", $("#"+alertFormId)).removeAttr("checked");
		}
		if(obj.wxfzjl == 1){
			$("#wxfzjl", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#wxfzjl", $("#"+alertFormId)).removeAttr("checked");
		}
		if(obj.jwgcbz == 1){
			$("#jwgcbz", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#jwgcbz", $("#"+alertFormId)).removeAttr("checked");
		}
		if(obj.scc == 1){
			$("#scc", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#scc", $("#"+alertFormId)).removeAttr("checked");
		}
		if(obj.gcc == 1){
			$("#gcc", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#gcc", $("#"+alertFormId)).removeAttr("checked");
		}
		if(obj.hcc == 1){
			$("#hcc", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#hcc", $("#"+alertFormId)).removeAttr("checked");
		}
		if(obj.zlc == 1){
			$("#zlc", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#zlc", $("#"+alertFormId)).removeAttr("checked");
		}
		if(obj.pxc == 1){
			$("#pxc", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#pxc", $("#"+alertFormId)).removeAttr("checked");
		}
		if(obj.wxdw == 1){
			$("#wxdw", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#wxdw", $("#"+alertFormId)).removeAttr("checked");
		}
		if(obj.qt == 1){
			$("#qt", $("#"+alertFormId)).attr("checked","true");
		}else{
			$("#qt", $("#"+alertFormId)).removeAttr("checked");
		}
		$("#shjcde", $("#"+alertFormId)).val(obj.shjcde);
		$("#shhzde", $("#"+alertFormId)).val(obj.shhzde);
		$("#qte", $("#"+alertFormId)).val(obj.qte);
	}
	
	//设置只读/失效/隐藏
	function setView(){
		$("#shbgbh", $("#"+alertFormId)).attr("readonly","readonly");
		$("#shxm", $("#"+alertFormId)).attr("disabled","true");
		$("#shrq", $("#"+alertFormId)).attr("disabled","true");
		$("input:radio[name='shlb']", $("#"+alertFormId)).attr("disabled","true");
		$("#shzs", $("#"+alertFormId)).attr("readonly","readonly");
		$("#shjcd", $("#"+alertFormId)).attr("disabled","true");
		$("#shhzd", $("#"+alertFormId)).attr("disabled","true");
		$("#wxfzjl", $("#"+alertFormId)).attr("disabled","true");
		$("#jwgcbz", $("#"+alertFormId)).attr("disabled","true");
		$("#scc", $("#"+alertFormId)).attr("disabled","true");
		$("#gcc", $("#"+alertFormId)).attr("disabled","true");
		$("#hcc", $("#"+alertFormId)).attr("disabled","true");
		$("#zlc", $("#"+alertFormId)).attr("disabled","true");
		$("#pxc", $("#"+alertFormId)).attr("disabled","true");
		$("#wxdw", $("#"+alertFormId)).attr("disabled","true");
		$("#qt", $("#"+alertFormId)).attr("disabled","true");
		$("#qte", $("#"+alertFormId)).attr("disabled","true");
		$("#pfyj", $("#"+alertFormId)).attr("readonly","readonly");
		$("#deptbtn", $("#"+alertFormId)).hide();
		$("#fzrbtn", $("#"+alertFormId)).hide();
		$("#zrshybtn", $("#"+alertFormId)).hide();
		$("#shjcdeBtn", $("#"+alertFormId)).hide();
		$("#shhzdeBtn", $("#"+alertFormId)).hide();
	}
	
	//设置只读/失效/隐藏
	function setEdit(){
		$("#shbgbh", $("#"+alertFormId)).removeAttr("readonly");
		$("#shxm", $("#"+alertFormId)).removeAttr("disabled");
		$("#shrq", $("#"+alertFormId)).removeAttr("disabled");
		$("input:radio[name='shlb']", $("#"+alertFormId)).removeAttr("disabled"); 
		$("#shzs", $("#"+alertFormId)).removeAttr("readonly");
		$("#shjcd", $("#"+alertFormId)).removeAttr("disabled");
		$("#shhzd", $("#"+alertFormId)).removeAttr("disabled");
		$("#wxfzjl", $("#"+alertFormId)).removeAttr("disabled");
		$("#jwgcbz", $("#"+alertFormId)).removeAttr("disabled");
		$("#scc", $("#"+alertFormId)).removeAttr("disabled");
		$("#gcc", $("#"+alertFormId)).removeAttr("disabled");
		$("#hcc", $("#"+alertFormId)).removeAttr("disabled");
		$("#zlc", $("#"+alertFormId)).removeAttr("disabled");
		$("#pxc", $("#"+alertFormId)).removeAttr("disabled");
		$("#wxdw", $("#"+alertFormId)).removeAttr("disabled");
		$("#qt", $("#"+alertFormId)).removeAttr("disabled");
		$("#qte", $("#"+alertFormId)).removeAttr("disabled");
		$("#pfyj", $("#"+alertFormId)).removeAttr("readonly");
		$("#deptbtn", $("#"+alertFormId)).show();
		$("#fzrbtn", $("#"+alertFormId)).show();
		$("#zrshybtn", $("#"+alertFormId)).show();
		$("#shjcdeBtn", $("#"+alertFormId)).show();
		$("#shhzdeBtn", $("#"+alertFormId)).show();
	}
	
	//移除一行
	function del(id){
		
		AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		
			$.each(list,function(index,row){
		    	if(row.id == id){
		    		list.pop(row);
		    		refreshPage();
		    		return false;
		    	}  
			});
		
		}});
	}
	
	//获取行数据
	function getRowData(id){
		var result = {};
		$.each(list, function(i, row) {
			if(id == row.id){
				result = row;
				return false
			}
		});
		return result;
	}
	
	//刷新页面
	function refreshPage(){
		loadContentHtml();
	}
	
	//初始化数据
	function initData(){
		list = [
		        {
		        	id:getUuid(),
		        	shbgbh:"BG001",
		        	shxm:"设立农药生产企业",
		        	shlb:"生产审核",
		        	bshdw:"研发",
		        	fzr:"小张",
		        	zrshy:"张缤",
		        	shrq:"2017-04-18",
		        	shzs:"管理者代表组织由公司内审小组按计划进行的例行审核",
		        	zdr:'小张',
		        	zdrq:'2017-04-18',
		        	shjcd:1,
		        	shjcde:"SMS-20170301,SMS-20170405",
		        	shhzd:1,
		        	shhzde:"SH-001",
		        	wxfzjl:1,
		        	jwgcbz:0,
		        	scc:1,
		        	gcc:0,
		        	hcc:1,
		        	zlc:1,
		        	pxc:0,
		        	wxdw:1,
		        	qt:0,
		        	qte:""
		        }
		];
	}
	
	$('.date-picker').datepicker({
		autoclose : true
	}).next().on("click", function() {
		$(this).prev().focus();
	});
	
	//获取uuid
	function getUuid() {
	    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	        return v.toString(16);
	    });
	}
	
</script>
</body>
</html>