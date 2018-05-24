<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<input type="hidden" id="adjustHeight" value="70">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->


	<div class="page-content">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			
			<div class="panel-body padding-bottom-0">
				
				
					<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
		
					<div class="pull-left" >
						  <div class="pull-left  text-right padding-left-0 padding-right-0"><div
								class="font-size-12">飞行队</div>
							<div class="font-size-9">A/C REG</div>
							</div>
						<div id="fjzch_search_sel" class=" padding-left-8 pull-left" style="width: 200px; margin-right:10px;">
							<select id="fxd" class="form-control " name="fxd" onchange="searchRevision();">
								<option value="">显示全部All</option>
									<c:forEach items="${newRecordList}" var="item" varStatus="status">
											<option value="${item.jddxbh}">${erayFns:escapeStr(item.jddxms)}</option>
									</c:forEach>
							</select>
						</div>
					</div>
		
					<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
						<input placeholder="申请单号" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div> 
				</div>
			
				
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="margin-top: 10px;overflow-x: scroll;">
					<table id="qtjrsq" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:900px">
						<thead>
							<tr>
								<th class="colwidth-5" style="vertical-align: middle;">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th  class="colwidth-5">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th  name="column_sqdh" class="sorting colwidth-15" onclick="orderBy('sqdh',this)">
									<div class="important"><div class="font-size-12 line-height-18">申请单号</div></div>
									<div class="font-size-9 line-height-18">Apply No.</div>
								</th>
								<th  name="column_sqsj" class="sorting colwidth-15" onclick="orderBy('sqsj',this)" >
									<div class="font-size-12 line-height-18">申请日期</div>
									<div class="font-size-9 line-height-18">Application Date</div>
								</th>
								<th  name="column_jddxlx" class="sorting colwidth-15" onclick="orderBy('jddxlx',this)" >
									<div class="font-size-12 line-height-18">飞行队</div>
									<div class="font-size-9 line-height-18">Flying team</div>
								</th>
								
								<th  name="column_bz" class="sorting colwidth-30" onclick="orderBy('bz',this)" >
									<div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-18">Remark</div>
								</th>
								<th   class="colwidth-15">
									<div class="font-size-12 line-height-18">出库单号</div>
									<div class="font-size-9 line-height-18">Outstock No.</div>
								</th>
							
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
			
				<div class="col-xs-12 text-center padding-top-0" id="pagination">
					
				</div>
				<div class="clearfix"></div>
		</div>
	</div>
	
		<!-- start其他飞行队申请单提示框 -->
	<div class="modal fade" id="alertModalview" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1000px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">查看其他飞行队申请航材</div>
							<div class="font-size-9 ">Edit Monitoring Remark</div>
						</div>
						
						<div class="col-lg-4 col-sm-4 col-xs-4  padding-left-0 padding-right-0  padding-top-10  margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">申请单号</div>
								<div class="font-size-9 line-height-18">Application No.</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left">
								<input type="text" class="form-control" name="sqdh" id="sqdh"   readonly />
							</div>
							
						</div>
						
							<div class="col-lg-4 col-sm-4 col-xs-4  padding-left-0 padding-right-0 padding-top-10 margin-bottom-10 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">飞行队</div>
								<div class="font-size-9 line-height-18">Flying Team</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 pull-left">
								<input type="text" class="form-control" name="fxds" id="fxds" readonly />
							</div>
							
						</div>
						
						
						<div class="panel-body padding-top-0 padding-bottom-0" >
						
								<table class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:900px">
						<thead>
							<tr>
										
									<th class="colwidth-5" style="vertical-align: middle;" >
										<div class="font-size-12 line-height-18">序号</div>
										<div class="font-size-9 line-height-18">NO.</div>
									</th>
									<th class="colwidth-8">
											<div class="font-size-12 line-height-18">件号</div>
											<div class="font-size-9 line-height-18">P/N</div>
									</th>
									<th class="colwidth-15" >
											<div class="font-size-12 line-height-18">英文名称</div>
											<div class="font-size-9 line-height-18">F.Name</div>
									</th>
										<th class="colwidth-15" >
											<div class="font-size-12 line-height-18">中文名称</div>
											<div class="font-size-9 line-height-18">CH.Name</div>
									</th>
									<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">航材类型</div>
										<div class="font-size-9 line-height-18">Airmaterial type</div>
									</th>
									<th class="colwidth-8">
											<div class="font-size-12 line-height-18">序列号</div>
											<div class="font-size-9 line-height-18">S/N</div>
									</th>
									<th class="colwidth-8" >
										<div class="font-size-12 line-height-18">借入数量</div>
										<div class="font-size-9 line-height-18">Stock Count</div>
									</th>
									<th class="colwidth-8" >
										<div class="font-size-12 line-height-18">单位</div>
										<div class="font-size-9 line-height-18">Unit</div>
									</th>
									<th class="colwidth-20" >
										<div class="font-size-12 line-height-18">用途</div>
										<div class="font-size-9 line-height-18">Differ</div>
									</th>
							
							</tr>
						</thead>
						<tbody id="list2"></tbody>
					</table>
							
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
					     		<button type="button" class="pull-right btn btn-primary margin-right-10 padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
                     			
                    		</div><br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end其他飞行队申请单提示框 -->
	
	<%@ include file="/WEB-INF/views/alert.jsp"%>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/otherborrow/otherborrow_main.js"></script>
</body>
</html>