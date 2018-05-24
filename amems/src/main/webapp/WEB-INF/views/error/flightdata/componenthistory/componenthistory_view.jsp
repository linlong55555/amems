<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>部件履历</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
 
</head>
<body class="page-header-fixed">
<input type="hidden" id="userId" value="" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

		<!-- BEGIN CONTENT -->
		<div class="page-content ">
		<div class="panel panel-primary" >
				<div class="panel-heading  ">
			<div id="NavigationBar"></div>
			</div>
			<!-- END PAGE TITLE & BREADCRUMB-->
			<!-- from start -->
			<div class="panel-body padding-bottom-0">
				<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
						<div class="font-size-16 line-height-18">基本信息</div>
						<div class="font-size-9 ">Basic Info</div>
				</div>
					
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
					<form id="loadForm" action="">
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</label>
						<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="${erayFns:escapeStr(jh)}" name="jh" id="jh" />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">序列号</div>
							<div class="font-size-9 line-height-18">S/N</div>
						</label>
						<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="${erayFns:escapeStr(xlh)}" id="xlh" name="xlh" />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</label>
						<div class=" col-xs-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" >
								<c:choose>
									<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
										<c:forEach items="${accessDepartment}" var="type">
											<option value="${type.id}" >${type.dprtname}
											</option>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<option value="">暂无组织机构 No Organization</option>
									</c:otherwise>
								</c:choose>
							</select>
						</div>
					</div>
					
				 	<input type="hidden" class="form-control "   id="hide_dprtcode" name="hide_dprtcode" value="${erayFns:escapeStr(dprtcode)}" />
					</form>
					 
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						 <div class="col-xs-4 text-right padding-left-0 padding-right-0">
							 <button type="button" id="loadbtn"
								class=" btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">刷新</div>
								<div class="font-size-9">Refresh</div>
							</button>
						</div>
						<div class="col-xs-8 padding-left-8 padding-right-0">
							<button type="button" onclick="componenthistoryUploadOutExcel();"
								style="margin-left: 5px"
								class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
								<div class="font-size-12">导出</div>
								<div class="font-size-9">Export</div>
							</button>
						</div>
					</div>
				</div>
				
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					
					 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">飞机注册号</div>
							<div class="font-size-9 line-height-18">A/C REG</div>
						</label>
						<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="fjzch" readonly="readonly" />
						</div>
					</div>
						
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">中文名称</div>
							<div class="font-size-9 line-height-18">CH.Name</div>
						</label>
						<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="zwmc" readonly="readonly" />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">英文名称</div>
							<div class="font-size-9 line-height-18">F.Name</div>
						</label>
						<div class="col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="ywms" readonly="readonly"  />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">State</div>
						</label>
						<div class="col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="zt" readonly="readonly" />
						</div>
					</div>  
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
					  
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">厂家件号</div>
							<div class="font-size-9 line-height-18">MP/N</div>
						</label>
						<div class="col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="cjjh" readonly="readonly" />
						</div>
					</div>
					
					 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">上级部件</div>
							<div class="font-size-9 line-height-18">Parent Parts</div>
						</label>
						<div class="col-xs-8 padding-left-8 padding-right-0">
						    <a href="#" id="viewParent">
							<input type="text" class="form-control " value="" id="sjbj" readonly="readonly" />
							</a>
							<input type="hidden" id="fjdxlh"/>
							<input type="hidden" id="fjdjh"/>
						</div>
					</div>  
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">层级</div>
							<div class="font-size-9 line-height-18">Level</div>
						</label>
						<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="cj" readonly="readonly" />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">位置</div>
							<div class="font-size-9 line-height-18">Location</div>
						</label>
						<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="wz" readonly="readonly" />
						</div>
					</div>
				</div>		
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
				
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div>
						</label>
						<div class=" col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="zjh" readonly="readonly" />
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class=" col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0 ">
							<textarea class="form-control" id="bz" name="bz"  readonly="readonly" ></textarea>
						</div>	
				</div>
					
					<div class="clearfix"></div>
					
				<div class="panel-body col-xs-12">
				<div class="clearfix"></div>
				<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-0 " style="border-bottom: 1px solid #ccc;">
					<div class="font-size-16 line-height-18">拆装记录</div>
					<div class="font-size-9 ">D/A record</div>
				</div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0"
					style="margin-top: 10px; overflow-x:scroll; ">
					<table
						class="table table-thin table-bordered table-striped table-hover text-center" style="min-width: 2500px !important" >
						<thead>
							<tr>
							 	 
							    <th rowspan="2">
									<div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C REG</div></th>
								<th colspan="7" >
									<div class="font-size-12 line-height-18">装上</div>
									<div class="font-size-9 line-height-18">Assembly</div>
									</th>
								<th colspan="7">
									<div class="font-size-12 line-height-18">拆下</div>
									<div class="font-size-9 line-height-18">Disassembly</div>
									</th>
								 
								<th rowspan="2">
								<div class="font-size-12 line-height-18">在机使用量</div>
									<div class="font-size-9 line-height-18">In machine usage</div>
									</th>	
							</tr>
							
							<tr>
								 
								<th  >
									<div class="font-size-12 line-height-18">安装日期</div>
									<div class="font-size-9 line-height-18">Install date</div></th>
								<th  >
									<div class="font-size-12 line-height-18">记录单号</div>
									<div class="font-size-9 line-height-18">Record No.</div></th>
								<th  >
									<div class="font-size-12 line-height-18">工作者</div>
									<div class="font-size-9 line-height-18">Worker</div></th>
								<th  >
									<div class="font-size-12 line-height-18">规定时限</div>
									<div class="font-size-9 line-height-18">Prescribed limit</div></th>
								<th  ><div class="font-size-12 line-height-18">装机前已用</div>
									<div class="font-size-9 line-height-18">Has been used</div></th>
								<th ><div class="font-size-12 line-height-18">剩余寿命</div>
								<div class="font-size-9 line-height-18">Surplus</div></th>
								
								<th ><div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div></th>
								
								<th  >
									<div class="font-size-12 line-height-18">拆下日期</div>
									<div class="font-size-9 line-height-18">Remove date</div></th>
								<th  >
									<div class="font-size-12 line-height-18">记录单号</div>
									<div class="font-size-9 line-height-18">Record No.</div></th>
								<th  >
									<div class="font-size-12 line-height-18">工作者</div>
									<div class="font-size-9 line-height-18">Worker</div></th>
								<th  >
									<div class="font-size-12 line-height-18">已用</div>
									<div class="font-size-9 line-height-18">Has been used</div></th>
								<th ><div class="font-size-12 line-height-18">剩余</div>
								<div class="font-size-9 line-height-18">Surplus</div></th>
								
								<th ><div class="font-size-12 line-height-18">拆下原因</div>
								<div class="font-size-9 line-height-18">Remove cause</div></th>
								
								<th  ><div class="font-size-12 line-height-18">对应装上</div>
									<div class="font-size-9 line-height-18">Assembly part</div></th>
								
							</tr>
						</thead>
						<tbody id="list">

						</tbody>

					</table>
				</div>
			</div>
				
			<div id="currentbjDiv" class="panel-body col-xs-12 subBlock">
				<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-0 " style="border-bottom: 1px solid #ccc; ">
					<div class="font-size-16 line-height-18">子部件列表</div>
					<div class="font-size-9 ">Sub component list</div>
				</div>
				<div class="clearfix"></div>
				<div id="currentbj" class="col-lg-12 col-md-12 padding-left-0 padding-right-0"
					style="margin-top: 10px; overflow-x:scroll;">
					<table
						class="table table-thin table-bordered text-center table-set" style="width:1850px;">
						<thead>
							<tr>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">S/N</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">内部识别码</div>
									<div class="font-size-9 line-height-18">I/N</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">批次号</div>
									<div class="font-size-9 line-height-18">B/N</div>
								</th>
								<th class="colwidth-20">
									<div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div></th>
								<th class="colwidth-30" >
									<div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-18">F.Name</div>
								</th>
									
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
       
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">MP/N</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div>
								</th>
								 
								<th class="colwidth-5">
									<div class="font-size-12 line-height-18">层级</div>
									<div class="font-size-9 line-height-18">Level</div>
								</th>
								<th class="colwidth-7">
									<div
										class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Location</div></th>
								<th class="colwidth-7"><div
										class="font-size-12 line-height-18">装机数量</div>
									<div class="font-size-9 line-height-18">Num</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">安装日期</div>
									<div class="font-size-9 line-height-18">Install date</div>
								</th>
								<th class="colwidth-30">
									<div
										class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>	
							</tr>
						</thead>
						<tbody id="sublist">

						</tbody>
					</table>
				</div>
				<div class="clearfix"></div>
				<!-- <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 padding-top-10">
					<button type="button" id="goHistory" onclick="history.back(-1);"
						class=" btn btn-primary padding-top-1 padding-bottom-1 pull-right">
						<div class="font-size-12">返回</div>
						<div class="font-size-9">Return</div>
					</button>
				</div> -->
			</div>
			</div>
			
			 
	 
			
	<!-- END CONTENT -->
 
	<!-----alert提示框Start--------->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
	<div class="modal-dialog " style="width: 90%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0 " id="alertModalBody">
			<!-- zc -->
			<!-- <div class="page-content"> -->
			<div >
			<!-- BEGIN PAGE TITLE & BREADCRUMB-->
			
			<!-- END PAGE TITLE & BREADCRUMB-->
			<!-- from start -->
			<div class="panel-body">
			<div class="panel panel-primary" >
				<div class="panel-heading  padding-top-3 padding-bottom-1"> 
					<div class="font-size-16 line-height-18">基本信息</div>
					<div class="font-size-9 ">Basic Info</div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-10" >
					<form id="loadForm" action="">
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">件号</div>
							<div class="font-size-9 line-height-18">P/N</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control "   name="jh" id="jh" readonly="readonly"/>
						</div>
					</div>
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">序列号</div>
							<div class="font-size-9 line-height-18">S/N</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control "   id="xlh" name="xlh"  readonly="readonly"/>
						</div>
					</div>
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-10">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">飞机注册号</div>
							<div class="font-size-9 line-height-18">A/C REG</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="fjzch" readonly="readonly" />
						</div>
					</div>  	 
					</form>
					 
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-1 padding-right-0">
						  
					</div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
					  
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">中文名称</div>
							<div class="font-size-9 line-height-18">CH.Name</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="zwmc" readonly="readonly" />
						</div>
					</div>
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">英文名称</div>
							<div class="font-size-9 line-height-18">F.Name</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="ywms" readonly="readonly"  />
						</div>
					</div>
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-10">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">State</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="zt" readonly="readonly" />
						</div>
					</div>  
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
					  
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">厂家件号</div>
							<div class="font-size-9 line-height-18">MP/N</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="cjjh" readonly="readonly" />
						</div>
					</div>
					 <div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">上级部件</div>
							<div class="font-size-9 line-height-18">Parent Parts</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="sjbj" readonly="readonly" />
						</div>
					</div>  
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-10">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">层级</div>
							<div class="font-size-9 line-height-18">Level</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="cj" readonly="readonly" />
						</div>
					</div>
					
				</div>		
						
				
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">位置</div>
							<div class="font-size-9 line-height-18">Location</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="wz" readonly="readonly" />
						</div>
					</div>
					<div class="col-lg-4 col-sm-4 col-xs-12 padding-left-0 padding-right-0">
						<label class="col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div>
						</label>
						<div class=" col-xs-9 padding-left-8 padding-right-0">
							<input type="text" class="form-control " value="" id="zjh" readonly="readonly" />
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			
			<div class="panel panel-primary" >
				<div class="panel-heading  padding-top-3 padding-bottom-1"> 
					<div class="font-size-16 line-height-18">拆装记录</div>
					<div class="font-size-9 ">D/A record</div>
				</div>
				<div class="panel-body col-xs-12">
				<div class="clearfix"></div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-5"
					style="overflow-x: scroll">
					<table class="table table-thin table-bordered text-center table-set" style="min-width: 2500px;">
						<thead>
							<tr>
							    <th rowspan="2">
									<div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C REG</div></th>
								<th colspan="7" >
									<div class="font-size-12 line-height-18">装上</div>
									<div class="font-size-9 line-height-18">Assembly</div>
									</th>
								<th colspan="7">
									<div class="font-size-12 line-height-18">拆下</div>
									<div class="font-size-9 line-height-18">Disassembly</div>
									</th>
								 
								<th rowspan="2">
								<div class="font-size-12 line-height-18">在机使用量</div>
									<div class="font-size-9 line-height-18">In machine usage</div>
									</th>	
							</tr>
							
							<tr>
								 
								<th  >
									<div class="font-size-12 line-height-18">安装日期</div>
									<div class="font-size-9 line-height-18">Install date</div></th>
								<th  >
									<div class="font-size-12 line-height-18">记录单号</div>
									<div class="font-size-9 line-height-18">Record No.</div></th>
								<th  >
									<div class="font-size-12 line-height-18">工作者</div>
									<div class="font-size-9 line-height-18">Worker</div></th>
								<th  >
									<div class="font-size-12 line-height-18">规定时限</div>
									<div class="font-size-9 line-height-18">Prescribed limit</div></th>
								<th  ><div class="font-size-12 line-height-18">装机前已用</div>
									<div class="font-size-9 line-height-18">Has been used</div></th>
								<th ><div class="font-size-12 line-height-18">剩余寿命</div>
								<div class="font-size-9 line-height-18">Surplus</div></th>
								<th ><div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div></th>
								
								<th  >
									<div class="font-size-12 line-height-18">拆下日期</div>
									<div class="font-size-9 line-height-18">Remove date</div></th>
								<th  >
									<div class="font-size-12 line-height-18">记录单号</div>
									<div class="font-size-9 line-height-18">Record No.</div></th>
								<th  >
									<div class="font-size-12 line-height-18">工作者</div>
									<div class="font-size-9 line-height-18">Worker</div></th>
								<th  >
									<div class="font-size-12 line-height-18">已用</div>
									<div class="font-size-9 line-height-18">Has been used</div></th>
								<th ><div class="font-size-12 line-height-18">剩余</div>
								<div class="font-size-9 line-height-18">Surplus</div></th>
								
								<th ><div class="font-size-12 line-height-18">拆下原因</div>
								<div class="font-size-9 line-height-18">Remove cause</div></th>
								
								<th  ><div class="font-size-12 line-height-18">对应装上</div>
									<div class="font-size-9 line-height-18">Assembly part</div></th>
								
							</tr>
						</thead>
						<tbody id="list">

						</tbody>

					</table>
				</div>

				<div class="col-xs-12 text-center">
					<ul class="pagination "
						style="margin-top: 0px; margin-bottom: 0px;" id="pagination">
					</ul>
				</div>
				<div class="clearfix"></div>

				 
			</div>
				
			<div class="col-xs-12 text-center">
				<ul class="pagination "
					style="margin-top: 0px; margin-bottom: 0px;" id="pagination">
				</ul>
			</div>
			<div class="clearfix"></div>
			</div>
	
			<div class="panel panel-primary" id="relationbj">
				<div class="panel-heading  padding-top-3 padding-bottom-1"> 
					<div class="font-size-16 line-height-18">子部件列表</div>
					<div class="font-size-9 ">Sub component list</div>
				</div>
				<div class="panel-body col-xs-12">
				<div class="clearfix"></div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0"style="margin-top: 10px; ">
					<table class="table table-thin table-bordered table-striped table-hover text-center table-set">
						<thead>
							<tr>
								<th >
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div></th>
								<th >
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">S/N</div></th>
								<th >
								<div class="font-size-12 line-height-18">内部识别码</div>
								<div class="font-size-9 line-height-18">I/N</div></th>
								
								<th >
								<div class="font-size-12 line-height-18">批次号</div>
								<div class="font-size-9 line-height-18">B/N</div></th>
								
								<th >
									<div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div></th>
								<th class="sorting" onclick="orderBy('ywms')"
									id="ywms_order">
									<div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-18">F.Name</div></th>
									
									<th ><div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div></th>
       
								<th ><div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">MP/N</div></th>
								<th ><div class="font-size-12 line-height-18">ATA章节号</div>
									<div class="font-size-9 line-height-18">ATA</div></th>
								 
								<th ><div class="font-size-12 line-height-18">层级</div>
									<div class="font-size-9 line-height-18">Level</div></th>
								<th ><div
										class="font-size-12 line-height-18">位置</div>
									<div class="font-size-9 line-height-18">Location</div></th>
								<th ><div
										class="font-size-12 line-height-18">装机数量</div>
									<div class="font-size-9 line-height-18">Num</div></th>
								<th ><div
									class="font-size-12 line-height-18">安装日期</div>
								<div class="font-size-9 line-height-18">Install date</div></th>
								<th ><div
										class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div></th>	
							</tr>
						</thead>
						<tbody id="sublist">

						</tbody>

					</table>					
				</div>
				<div class="clearfix"></div>				
			</div>
			</div>
			<!-- END CONTENT -->
			</div>
			<!-- zc -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
					<div class="font-size-12">关闭</div>
					<div class="font-size-9">Close</div>
				</button>
			</div>
		</div>
	</div>
</div>
	<!-----alert提示框End--------->
</div>
<script type="text/javascript">
/* var params = {
		jh:'${jh}'
		,xlh:'${xlh}'
		,dprtcode:'${dprtcode}'
	}; */
	var params = {
			jh:$('#jh').val() 
			,xlh:$('#xlh').val() 
			,dprtcode:$('#hide_dprtcode').val()
		};	


</script>
	 <script type="text/javascript" src="${ctx}/static/js/thjw/flightdata/componenthistory/componenthistory_view.js"></script> 
	 
	 
</body>
</html>