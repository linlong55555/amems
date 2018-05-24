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
			 <div  class='searchContent margin-top-0 row-height' >
			<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0 form-group" >
		
					<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
						<input placeholder="操作人/操作人ip/操作名称/操作数据" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="loginlogMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                  
                    </div> 
				</div>
			<!------------搜索框end------->
			
				<div class="clearfix"></div>
				</div>

				<div  class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height"  style="overflow-x:scroll">
					<table id="sqzz" class="table table-thin table-bordered table-set" id="borrow_return_outstock_table" style="min-width:1300px!important;">
						<thead>
							<tr>
								<th class=" colwidth-5" style="vertical-align: middle;">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-10"   >
									<div class="important">
										<div class="font-size-12 line-height-18">操作人</div>
										<div class="font-size-9 line-height-18">Operator</div>
									</div>
								</th>
								<th class="sorting colwidth-10"  name="column_czip" onclick="orderBy('czip', this)" >
									<div class="important">
										<div class="font-size-12 line-height-18">操作人ip</div>
										<div class="font-size-9 line-height-18">Operator IP</div>
									</div>
								</th>
								<th  class="sorting colwidth-10" name="column_czmc" onclick="orderBy('czmc', this)">
									<div class="important">
										<div class="font-size-12 line-height-18">操作名称</div>
										<div class="font-size-9 line-height-18">Operation Name</div>
									</div>
								</th>
								<th  class="sorting colwidth-10" name="column_czsj" onclick="orderBy('czsj', this)">
									<div class="important">
										<div class="font-size-12 line-height-18">操作数据</div>
										<div class="font-size-9 line-height-18">Operational Data</div>
									</div>
								</th>
								<th  class="sorting colwidth-7" name="column_rzlx" onclick="orderBy('rzlx', this)">
									
									<div class="font-size-12 line-height-18">日志类型</div>
									<div class="font-size-9 line-height-18">Log Type</div>
								</th>
								<th   class="sorting colwidth-30" name="column_ycnr" onclick="orderBy('ycnr', this)">
										<div class="font-size-12 line-height-18">异常内容</div>
										<div class="font-size-9 line-height-18">Abnormal Content</div>
								</th>
								<th  class="sorting colwidth-10" name="column_casj" onclick="orderBy('casj', this)">
									<div class="font-size-12 line-height-18">操作时间</div>
									<div class="font-size-9 line-height-18">Operation Time</div>
								</th>
					
								<th  class="colwidth-10">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list">
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/sys/log/loginlog_main.js"></script>
</body>
</html>