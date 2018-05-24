<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<input type="hidden" id="adjustHeight" value="50">
				<div class="col-xs-2 col-md-3 padding-left-0 row-height">
					   	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='sys:loginlimit:main:01'  onclick="addinto()">
									<div class="font-size-12">新增</div>
									<div class="font-size-9">Add</div>
                   		</button>
				</div>
				
			<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
					<div class=" pull-left padding-left-0 padding-right-0 row-height" style="width:250px;" >
						<input placeholder="值/备注" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="loginlimitMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                  
                    </div> 
				</div>
			<!------------搜索框end------->
			
				<div class="clearfix"></div>

				<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:scroll">
					<table id="sqzz" class="table table-thin table-bordered table-set" id="borrow_return_outstock_table" >
						<thead>
							<tr>
								<th class=" colwidth-5"  style="vertical-align: middle;">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class=" colwidth-5" style="vertical-align: middle;">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-10"   >
									<div class="font-size-12 line-height-18">类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								
								</th>
								<th  class="colwidth-20">
										<div class="important">
									<div class="font-size-12 line-height-18">值</div>
									<div class="font-size-9 line-height-18">Value</div>
									</div>
								</th>
								<th>
									<div class="important">
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								
							</tr>
						</thead>
						<tbody id="list">
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center padding-right-0 page-height" id="pagination"></div>
			</div>
		</div>
	</div>
	
	<input type="hidden" class="form-control " id="id" name="id" />
	<!-- start新增修改提示框 -->
	<div class="modal fade" id="alertModaladdupdate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:550px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18" id="accredit"></div>
							<div class="font-size-9 " id="accreditrec"></div>
						</div>
					
						<div class="panel-body padding-top-0 padding-bottom-0" >
						<form id="form1">

							<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0  form-group">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">类型</div>
									<div class="font-size-9">Type</div>
								</label>
								<div id="fjzchs" class="col-lg-10 col-sm-10 padding-left-8 padding-right-0">
									<select class="form-control" id="type" name="type"  type="text">
										<option value="1">IP</option>
										<option value="2">MAC</option>
									</select>
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
									<div class="font-size-12"><span style="color: red">*</span>值</div>
									<div class="font-size-9">Value</div>
								</label>
								<div class="col-lg-10 col-sm-10 padding-left-8 padding-right-0">
									<input class="form-control" id="value1" name="value1" maxlength="50" type="text"/>
								</div>
							</div>
					  
							<div class="clearfix"></div>
							
							<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<label class="pull-left col-lg-2 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">备注</div>
									<div class="font-size-9">Remark</div>
								</label>
								<div class="col-lg-10 col-sm-10 padding-left-8 padding-right-0">
									<textarea class="form-control" id="value2"   maxlength="15"></textarea>
								</div>
							</div>
								<div class="clearfix"></div>
					     	<div class="text-center margin-top-0 padding-buttom-10 ">
                     		<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-0 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
							</button>
							<button id="baocuns" type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="saveUpdate()">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
	                   		</button>
                    		</div>
							</form>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end新增修改提示框-->
	<script type="text/javascript" src="${ctx}/static/js/thjw/sys/loginlimit/loginlimit_main.js"></script>
</body>
</html>