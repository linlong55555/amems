<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看航材移库记录</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
<div class="page-content">
			<!-- from start -->
	<div class="panel panel-primary">
	<div class="panel-heading" id="NavigationBar"></div>
	     <div class="panel-body">
	          <!--    基本信息 start -->
				 <div class="panel panel-default">
				        <div class="panel-heading">
						  <h3 class="panel-title">移库基本信息 Transfer Information</h3>
					   </div>
				 <div class="panel-body">
				 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">移库人</div>
								<div class="font-size-9 line-height-18"> Transferer</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="ykr" readOnly value="${erayFns:escapeStr(transferlist[0].username)} ${erayFns:escapeStr(transferlist[0].realname)}" class="form-control "/>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">移库日期</div>
								<div class="font-size-9 line-height-18">Transfer Date</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input type="text"  id="ykrq" readOnly value="<fmt:formatDate value="${transferlist[0].ykrq}" pattern="yyyy-MM-dd"/> " class="form-control "/>
							</div>
						</div>
					</div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
							<label class=" col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							   <div class="font-size-12 line-height-18">移库原因</div>
								<div class="font-size-9 line-height-18">Transfer Reason</div></label>
							   <div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
									<textarea class="form-control" name="ykyy" readOnly>${erayFns:escapeStr(transferlist[0].ykyy)}</textarea>
							  </div>
						</div>	 	
				 </div>
			     </div>
					<!-- end -->
					
				    <!-- 执行对象start	 -->
				  <div class="panel panel-default">
				        <div class="panel-heading">
						   <h3 class="panel-title">移库航材 Transfer Material</h3>
					    </div>
					<div class="panel-body">
					 <div class="col-lg-12 col-md-12 padding-left-2 padding-right-0" style="overflow-x:scroll ;">
						 <table class="table-set table table-thin table-bordered table-striped table-hover text-center" style="min-width: 1600px !important;" >
							<thead>
								<tr>
									<th class="colwidth-10"><div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-16">P/N</div></th>
									<th class="colwidth-13"><div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-16">CH.Name</div></th>
									<th class="colwidth-13"><div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-16">F.Name</div></th>
									<th class="colwidth-10"><div class="font-size-12 line-height-18">原始仓库号</div>
									<div class="font-size-9 line-height-16">O-Store</div></th>
									<th class="colwidth-13"><div class="font-size-12 line-height-18">原始仓库名称</div>
									<div class="font-size-9 line-height-16">O-Name</div></th>
									<th class="colwidth-10"><div class="font-size-12 line-height-18">原始库位</div>
									<div class="font-size-9 line-height-16">O-Storage</div></th>
									<th class="colwidth-5"><div class="font-size-12 line-height-18">库存数量</div>
									<div class="font-size-9 line-height-16">O-Num</div></th>
									<th class="colwidth-10"><div class="font-size-12 line-height-18">目标仓库号</div>
									<div class="font-size-9 line-height-16">T-Store</div></th>
									<th class="colwidth-13"><div class="font-size-12 line-height-18">目标仓库名称</div>
									<div class="font-size-9 line-height-16">T-Name</div></th>
									<th class="colwidth-10"><div class="font-size-12 line-height-18">目标库位</div>
									<div class="font-size-9 line-height-16">T-Storage</div></th>
									<th class="colwidth-5"><div class="font-size-12 line-height-18">移库数量</div>
									<div class="font-size-9 line-height-16">T-Num</div></th>
								</tr>
							</thead>     
							<tbody>
							<c:forEach items="${transferlist}" var="transferlist">
								<tr>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(transferlist.bjh)}'><c:out value="${transferlist.bjh}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(transferlist.zwms)}'><c:out value="${transferlist.zwms}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(transferlist.ywms)}'><c:out value="${transferlist.ywms}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(transferlist.ysCkh)}'><c:out value="${transferlist.ysCkh}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(transferlist.ysCkmc)}'><c:out value="${transferlist.ysCkmc}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(transferlist.ysKwh)}'><c:out value="${transferlist.ysKwh}" /></td>
						          <td style='vertical-align: middle;'class='text-right'><c:out value="${transferlist.ysSl}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(transferlist.mbCkh)}'><c:out value="${transferlist.mbCkh}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(transferlist.mbCkmc)}'><c:out value="${transferlist.mbCkmc}" /></td>
						          <td style='vertical-align: middle;'class='text-left' title='${erayFns:escapeStr(transferlist.mbKwh)}'><c:out value="${transferlist.mbKwh}" /></td>
						          <td style='vertical-align: middle;'class='text-right'><c:out value="${transferlist.mbSl}" /></td>
								</tr>
							</c:forEach>
							</tbody>	
						</table>
					  </div>
			        </div>
			      </div>
	     </div>
	</div>
</div>
<script type="text/javascript">
        Navigation(menuCode,"查看移库记录","View Transfer");
        $(".panel-heading").not(":first").click(function(event){         //隐藏和显示
    		if($(event.target).attr("id")!= "copy_wo"){
    			$(this).next().slideToggle("fast");
    		}
    	});
</script>
</body>
</html>