<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>岗位授权申请</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

</head>
<body >
	<div class="page-content" >
		<div class="panel panel-primary"  id='panel_content'>
			<div class="panel-heading" id="NavigationBar"></div>
            <div class="panel-body">
                <div class="col-xs-2 col-md-3 padding-left-0">
					<a href="${ctx}/demo/auth/applyjob" class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-10 pull-left" >
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
				</div>
				
				<div class="clearfix"></div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;height:450px;overflow-x: auto;">
					<table class="table table-thin table-bordered table-set table-striped" style="min-width: 1000px;">
						<thead>
							<tr>
								<th class="colwidth-7" >	
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-3">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">类别</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">姓名</div>
									<div class="font-size-9 line-height-18">Name</div>
								</th>
								
								<th class="colwidth-5" >
									<div class="font-size-12 line-height-18">性别</div>
									<div class="font-size-9 line-height-18">Sex</div>
								</th>
								
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">工号</div>
									<div class="font-size-9 line-height-18">Work number</div>
								</th>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">处室</div>
									<div class="font-size-9 line-height-18">Office</div>
								</th>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">出生日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">参加工作日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">加入公司日期</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">专业</div>
									<div class="font-size-9 line-height-18">Professional</div>
								</th>
								<th class="colwidth-7" >
									<div class="font-size-12 line-height-18">有效期</div>
									<div class="font-size-9 line-height-18">validity</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">申请类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">申请授权岗位</div>
									<div class="font-size-9 line-height-18">Job</div>
								</th>
							</tr>
						</thead>
						<tbody>
						<tr>
						<td>
						<a href='${ctx}/demo/auth/assessment'><i class="icon-edit color-blue cursor-pointer" title="评估">  </i></a>
						<a href='${ctx}/demo/auth/audit'><i class="icon-tags  color-green cursor-pointer" onclick="" title="审核"></i></a>
						<a href='${ctx}/demo/auth/main'><i class="icon-check color-red cursor-pointer" onclick="" title="批准"></i></a>
						</td>
						<td>1</td>
						<td><a href='#' class='color-blue'>已申请</a></td>
						<td>内部</td>
						<td>Eray</td>
						<td>男</td>
						<td>001</td>
						<td>处室1</td>
						<td>1992-12</td>
						<td>2015-6</td>
						<td>2016-3</td>
						<td>维修</td>
						<td>3</td>
						<td>初次</td>
						<td>项目1</td>
						</tr>
						<tr>
						<td>
						<a href='${ctx}/demo/auth/assessment' style='visibility:hidden'><i class="icon-edit color-blue cursor-pointer" title="评估">  </i></a>
						<a href='${ctx}/demo/auth/audit'><i class="icon-tags  color-green cursor-pointer" onclick="" title="审核"></i></a>
						<a href='${ctx}/demo/auth/main'><i class="icon-check color-red cursor-pointer" onclick="" title="批准"></i></a>
						</td>
						<td>1</td>
						<td><a href='#' class='color-green'>已评估</a></td>
						<td>内部</td>
						<td>Eray01</td>
						<td>男</td>
						<td>002</td>
						<td>处室1</td>
						<td>1992-12</td>
						<td>2015-6</td>
						<td>2016-3</td>
						<td>维修</td>
						<td>3</td>
						<td>初次</td>
						<td>项目1</td>
						</tr>
						<tr>
						<td>
						<a href='${ctx}/demo/auth/assessment' style='visibility:hidden'><i class="icon-edit color-blue cursor-pointer" title="评估">  </i></a>
						<a href='${ctx}/demo/auth/audit' style='visibility:hidden'><i class="icon-tags  color-green cursor-pointer" onclick="" title="审核"></i></a>
						<a href='${ctx}/demo/auth/main'><i class="icon-check color-red cursor-pointer" onclick="" title="批准"></i></a>
						</td>
						<td>1</td>
						<td><a href='#' class='color-red'>已审核</a></td>
						<td>内部</td>
						<td>Eray02</td>
						<td>男</td>
						<td>003</td>
						<td>处室1</td>
						<td>1992-12</td>
						<td>2015-6</td>
						<td>2016-3</td>
						<td>维修</td>
						<td>3</td>
						<td>初次</td>
						<td>项目1</td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
     <script>
       $(function(){
    	   Navigation(menuCode,"","");//初始化导航
       })
     </script>
</body>
</html>