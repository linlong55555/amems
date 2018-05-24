<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>维护提示传阅</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					searchRevision();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
<input type="hidden" id="zdbmid" name="zdbmid" value="${user.bmdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				
				<div class="panel-body padding-bottom-0">
				<div class='margin-top-0 searchContent row-height'>
					<div class="col-lg-2 col-md-3 col-sm-6 col-xs-12 padding-left-8 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">机型</div>
							<div class="font-size-9 ">A/C Type</div>
						</span>
						<div id="jxdiv" class="col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
							
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12  padding-left-8 padding-right-0  form-group">
						<span class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">提示传阅期限</div>
							<div class="font-size-9">Bulletin Limit</div>
						</span>
						<div class="col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="tgqx_search" readonly />
						</div>
					</div>
					<!--  
					<div class="col-lg-2 col-md-3 col-sm-6 col-xs-12  padding-left-8 padding-right-0  form-group">
						<span class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						
						<div class="col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
							<select id="bb_search" class="form-control "  name="bb_search" onchange="searchRevision();">
								<option value="current">当前</option>
								<option value="history">历史</option>
							</select>
						</div>
						
					</div>	
					-->	
					<div class="col-lg-2 col-md-3 col-sm-6 col-xs-12  padding-left-8 padding-right-0  form-group">
						<span class="col-lg-4 col-md-4 col-sm-4 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">类型</div>
							<div class="font-size-9">Type</div>
						</span>
						
						<div id="lxdiv" class="col-lg-8 col-md-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">

						</div>
						
					</div>
					<div class="col-lg-2 col-md-3 col-sm-6 col-xs-12 text-right padding-left-0 padding-right-10  form-group">
					<span class='margin-right-5 label-input ' >	<input type="checkbox"  name="cy" value="1" checked="checked" onclick="searchRevision();"/>&nbsp;已阅</span>
					<span class='label-input'>	<input type="checkbox" name="cy" value="0" checked="checked" onclick="searchRevision();"/>&nbsp;未阅</span>
					</div>														
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0  form-group" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='编号/主题'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
                    <div class="input-group-addon btn-searchnew-more">
	                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight"  id="btn" onclick="search();">
						<div class='input-group'>
						<div class="input-group-addon">
						<div class="font-size-12">更多</div>
						<div class="font-size-9 margin-top-5" >More</div>
						</div>
						<div class="input-group-addon">
						 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
						</div>
						</div>
			   			</button>
                	</div>
				</div>
				</div>
					<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
						 <div class="col-lg-8 col-sm-0 col-xs-0   padding-left-0 padding-right-0 margin-bottom-8 "></div>															
						<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 margin-bottom-8 ">
							<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div></span>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<select id="zzjg" class="form-control " name="zzjg" onchange="searchRevision();">
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-lg-1  text-right   padding-right-0" style="margin-bottom: 10px;">
							<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
								<div class="font-size-12">重置</div>
								<div class="font-size-9">Reset</div>
							</button>
						</div>				
				</div>
				<div class="clearfix"></div>
				</div>
					<div class="clearfix"></div>
					<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" style="overflow-x: auto;width: 100%;">
						<table id="quality_check_list_table"
							class="table table-thin table-bordered table-striped table-hover table-set"
							>
							<thead>
							<tr>
							<!-- 
					        	<th class=" colwidth-5"  ><div class="font-size-12 line-height-18" >操作</div><div class="font-size-9 line-height-18" >Operation</div></th>
							
							 -->
							<th class=" colwidth-5"  ><div class="font-size-12 line-height-18" >标识</div><div class="font-size-9 line-height-18" >Mark</div></th>
							<th  class="sorting colwidth-9" onclick="orderBy('ywlx')" id="ywlx_order"><div class="font-size-12 line-height-18" >类型</div><div class="font-size-9 line-height-18" >Type</div></th>
							<th  class="sorting colwidth-10" onclick="orderBy('jstgh')" id="jstgh_order"><div class="important"><div class="font-size-12 line-height-18" >编号</div><div class="font-size-9 line-height-18" >TB No.</div></div></th>
							<th class="sorting colwidth-7" onclick="orderBy('bb')" id="bb_order"><div class="font-size-12 line-height-18" >版本</div><div class="font-size-9 line-height-18" >Rev.</div></th>
							<th  class="sorting colwidth-10" onclick="orderBy('jx')" id="jx_order"><div class="font-size-12 line-height-18" >机型</div><div class="font-size-9 line-height-18" >A/C Type</div></th>
							<th class="sorting colwidth-9" onclick="orderBy('tgqx')" id="tgqx_order"><div class="font-size-12 line-height-18" >传阅期限 </div><div class="font-size-9 line-height-18" >Bulletin Limit</div></th>
							<th class="colwidth-5" ><div class="font-size-12 line-height-18" >剩余(天)</div><div class="font-size-9 line-height-18" >Remain</div></th>
							<th class="sorting colwidth-15" onclick="orderBy('zhut')" id="zhut_order"><div class="important"><div class="font-size-12 line-height-18" >主题</div><div class="font-size-9 line-height-18" >Subject</div></div></th>
							<th class="sorting colwidth-10" onclick="orderBy('lysm')" id="lysm_order"><div class="font-size-12 line-height-18" >来源说明 </div><div class="font-size-9 line-height-18" >Desc</div></th>
							<th class="downward colwidth-13"  onclick="vieworhidePgd()" name="pgd" ><div class="font-size-12 line-height-18" >关联技术评估单</div><div class="font-size-9 line-height-18" >Related Evaluation</div></th>
							<th class="sorting colwidth-9" onclick="orderBy('bfrq')" id="bfrq_order"><div class="font-size-12 line-height-18" >颁发日期 </div><div class="font-size-9 line-height-18" >Issue Date</div></th>
							<th class="sorting colwidth-7" onclick="orderBy('isjs')" id="isjs_order"><div class="font-size-12 line-height-18" >状态</div><div class="font-size-9 line-height-18" >Status</div></th>
							<th class="sorting colwidth-13" onclick="orderBy('zdrid')" id="zdrid_order"><div class="font-size-12 line-height-18" >制单人</div><div class="font-size-9 line-height-18" >Creator</div></th>
							<th class="sorting colwidth-13" onclick="orderBy('zdsj')" id="zdsj_order"><div class="font-size-12 line-height-18" >制单时间</div><div class="font-size-9 line-height-18" >Create Time</div></th>
							<th class="sorting colwidth-10" onclick="orderBy('dprtcode')" id="dprtcode_order"><div class="font-size-12 line-height-18" >组织机构</div><div class="font-size-9 line-height-18" >Organization</div></th>
							</tr> 
			         		 </thead>
							<tbody id="circulation_list">
							</tbody>
							
						</table>
					</div>
					
				
				<div class="col-xs-12 text-center padding-right-0 padding-left-0  page-height " id="pagination">
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
	</div>
	
	<script type="text/javascript" src="${ctx }/static/js/thjw/project2/circulation/circulation_main.js"></script>
</body>
</html>