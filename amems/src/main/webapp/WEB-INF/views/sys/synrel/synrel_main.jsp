<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统同步关系</title>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
var deptType = '${deptType}';
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">

	<div class="clearfix"></div>
	<div class="page-content">
	<input type="hidden" id="userid" value="${user.id}">
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
			<div class="panel-body padding-bottom-0">
			<div  class='searchContent margin-top-0 row-height' >
			   <div class='margin-top-0'>
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<a href="javascript:synrel_main.popAdd();" permissioncode='sys:synrel:add'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission  ">
							<div class="font-size-12">新增</div>
							<div class="font-size-9">Add</div>
						</a> 
						<a href="javascript:synrel_main.dels();" type="button" class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode='sys:synrel:delete'>
							<div class="font-size-12">删除</div>
							<div class="font-size-9">Delete</div>
						</a>
						 
					</div>
					
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">关系类型</div>
							<div class="font-size-9">Relation type</div>
						</span>
						<div id="jxdiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="synrel_main_gxlx" class="form-control "  name="synrel_main_gxlx" onchange="synrel_main.search();" >
								<option value=""  > 全部</option>
								<option value="1"  > 外委供应商</option>
								<option value="2"  > 客户</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select id="synrel_main_dprtCode" class="form-control "  name="synrel_main_dprtCode" onchange="synrel_main.search();" >
								<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
				   </div>
					<!-- 搜索框start -->
				   <div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>	
						<!-- 搜索框start -->
						<div class=" col-xs-12 input-group input-group-search" >
						    <input type="text" placeholder='对象'  id="synrel_main_keyword" class="form-control">
                            <div class="input-group-addon btn-searchnew " style="padding-right: 0px !important;">
			                    <button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="synrel_main.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                	</div>
						</div>
				   </div>
				<!-- 搜索框end -->
				</div>
			<div class="clearfix"></div>
				</div>
			<div class='table_pagination'>
				<!-- 表格 -->
					<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height" style="overflow-x: auto;">
						<table  id='synrel_main_table' class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
									<th class="colwidth-5">
										<div class="font-size-12 line-height-18 ">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th  class="sorting  colwidth-10" onclick="synrel_main.orderBy('r-gxlx')" id="r-gxlx_order">
										<div class="important"><div class="font-size-12 line-height-18">关系类型</div>
										<div class="font-size-9 line-height-18">Relation type.</div></div>
									</th>
									<th class="   colwidth-10"    >
										<div class="font-size-12 line-height-18">对象</div>
										<div class="font-size-9 line-height-18">Object</div>
									</th>
									<th  class="sorting  colwidth-10" onclick="synrel_main.orderBy('r-gljgdm')" id="r-gljgdm_order">
										<div class="font-size-12 line-height-18">关联机构</div>
										<div class="font-size-9 line-height-18">Associated organization</div>
									</th>	
									
									<th  class=" colwidth-15"  >
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>	
									 
								</tr>
							</thead>
							<tbody id="synrel_main_list">
							</tbody>
					</table>
				</div>	
				
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="synrel_main_pagination">
				</div>
				<div class='clearfix'></div>
			</div>	
		</div>
	</div>
</div>	
<!--日志END  -->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<%@ include file="/WEB-INF/views/sys/synrel/synrel_open.jsp" %> 
<script type="text/javascript" src="${ctx}/static/js/thjw/sys/synrel/synrel_main.js"></script><!--当前界面js  -->
<script type="text/javascript">

$(function(){
	Navigation(menuCode, '', '', 'GC-2-5', '朱超', '2017-09-27');// 加载导航栏
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				synrel_main.search();//调用主列表页查询
			}
		}
	});
	
	refreshPermission();
	synrel_main.search();
	
});

</script>
</body>
</html>
