<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看航材报废记录</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
<div class="page-content ">
			<!-- from start -->
			<input type="hidden" id="bflx" value="${scrappedList.bflx}" />
	<div class="panel panel-primary">
	<div class="panel-heading" id="NavigationBar"></div>
	     <div class="panel-body">
	          <!--    基本信息 start -->
				 <div class="panel panel-default">
				        <div class="panel-heading">
						  <h3 class="panel-title">报废审批基本信息 View Approval Information</h3>
					   </div>
				 <div class="panel-body">
				 <input type="hidden" id="zt" value="${scraplist.zt}"/>
				 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				      <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">报废单号</div>
								<div class="font-size-9 line-height-18">Scrap No.</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0">
								<input type="text"  value="${erayFns:escapeStr(scraplist.bfdh)}" class="form-control"  readonly/>
							</div>
						</div>
				         <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单人</div>
								<div class="font-size-9 line-height-18">Creator</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zdr" name="zdr" value="${erayFns:escapeStr(scraplist.zdrname)}" class="form-control " readonly />
							</div>
						</div>
				        <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zdsj" name="zdsj" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${scraplist.zdsj}"/>" data-date-format="yyyy-MM-dd HH:mm:ss" class="form-control " readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">报废状态</div>
								<div class="font-size-9 line-height-18">State</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="bfzt" value="${scraplist.zt}" class="form-control " readonly />
							</div>
						</div>
				      
				 </div>
				   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				        <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">报废人</div>
								<div class="font-size-9 line-height-18">Scrap Person</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="bfrid" readOnly value="${erayFns:escapeStr(scraplist.bfrname)}" class="form-control "/>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">报废时间</div>
								<div class="font-size-9 line-height-18">Scrap Time</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0">
								<input type="text"  id="bfsj" readOnly value="<fmt:formatDate value="${scraplist.bfsj}" pattern="yyyy-MM-dd HH:mm:ss"/> " class="form-control "/>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="demo1">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">审批人</div>
								<div class="font-size-9 line-height-18"> Audit Person</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0">
								<input type="text" id="sprid" readOnly value="${erayFns:escapeStr(scraplist.sprname)}" class="form-control"/>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="demo2">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">审批日期</div>
								<div class="font-size-9 line-height-18">Audit Date</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text"  id="spsj" readOnly value="<fmt:formatDate value="${scraplist.spsj}" pattern="yyyy-MM-dd"/> " class="form-control "/>
							</div>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
						   <div class="font-size-12 line-height-18">报废类型</div>
							<div class="font-size-9 line-height-18">Review Opinion</div></label>
						   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group" >
								<input type="text"  value="${scraplist.bflx eq 1 ?'库内报废':'外场报废'}" class="form-control " readonly />
						  </div>
					</div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						   <div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div></label>
					   <div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
							<textarea class="form-control" name="bz" readOnly>${erayFns:escapeStr(scraplist.bz)}</textarea>
				       </div>
					</div>	
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" id="demo3">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						   <div class="font-size-12 line-height-18">审核意见</div>
							<div class="font-size-9 line-height-18">Review Opinion</div></label>
					   <div class="col-lg-11 col-sm-10 col-xs-8   padding-left-8 padding-right-0 form-group">
							<textarea class="form-control" name="spyj" readOnly>${erayFns:escapeStr(scraplist.spyj)}</textarea>
				       </div>
					</div>	 	
				 </div>
			     </div>
					<!-- end -->
					
				    <!-- 执行对象start	 -->
				  <div class="panel panel-default">
				        <div class="panel-heading">
						   <h3 class="panel-title">报废航材 Scrap Material</h3>
					    </div>
					<div class="panel-body">
					 <div class="col-lg-12 col-md-12 padding-left-2 padding-right-0" style="overflow: auto;">
						 <table class="table-set table table-thin table-bordered table-striped table-hover" style="min-width:900px" >
							<thead>
								<tr>
									<th class="colwidth-7"><div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-16">P/N</div></th>
									<th class="colwidth-7"><div class="font-size-12 line-height-18">序列号/批次号</div>
									<div class="font-size-9 line-height-16">S/N</div></th>
									<th class="colwidth-10"><div class="font-size-12 line-height-18">仓库名称</div>
									<div class="font-size-9 line-height-16">WareHouse Name</div></th>
									<th class="colwidth-7"><div class="font-size-12 line-height-18">库位号</div>
									<div class="font-size-9 line-height-16">Storage Location</div></th>
									<th class="colwidth-10"><div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-16">F.Name</div></th>
									<th class="colwidth-7"><div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-16">CH.Name</div></th>
									<th class="colwidth-13"><div class="font-size-12 line-height-18">库存成本(人民币:元)</div>
									<div class="font-size-9 line-height-16">Stock Pay(RMB：YUAN)</div></th>
									<th class="colwidth-5"><div class="font-size-12 line-height-18">库存数量</div>
									<div class="font-size-9 line-height-16">Stock Num</div></th>
									<th class="colwidth-7"><div class="font-size-12 line-height-18">是否报废</div>
									<div class="font-size-9 line-height-16">Is Scrap</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-16">Remark</div></th>
								</tr>
							</thead>     
							<tbody id="YClist">
							<c:forEach items="${hasScrappedList}" var="hasScrappedList">
								<tr>
						          <td style='vertical-align: middle;' title="${erayFns:escapeStr(hasScrappedList.bjll.bjh)}"><c:out value="${hasScrappedList.bjll.bjh}" /></td>
						          <td style='vertical-align: middle;' title="${erayFns:escapeStr(hasScrappedList.bjll.sn)=='' ?erayFns:escapeStr(hasScrappedList.bjll.pch):erayFns:escapeStr(hasScrappedList.bjll.sn)}"><c:out value="${erayFns:escapeStr(hasScrappedList.bjll.sn)=='' ?erayFns:escapeStr(hasScrappedList.bjll.pch):erayFns:escapeStr(hasScrappedList.bjll.sn)}" /></td>
						          <td style='vertical-align: middle;' title="${erayFns:escapeStr(scraplist.bflx eq 1?hasScrappedList.bjll.ckmc:'外场')}"><c:out value="${erayFns:escapeStr(scraplist.bflx eq 1?hasScrappedList.bjll.ckmc:'外场')}" /></td>
						          <td style='vertical-align: middle;' title="${erayFns:escapeStr(scraplist.bflx eq 1?hasScrappedList.bjll.kwh:'外场')}"><c:out value="${erayFns:escapeStr(scraplist.bflx eq 1?hasScrappedList.bjll.kwh:'外场')}" /></td>
						          <td style='vertical-align: middle;' title="${erayFns:escapeStr(hasScrappedList.bjll.ywms)}"><c:out value="${hasScrappedList.bjll.ywms}" /></td>
						          <td style='vertical-align: middle;' title="${erayFns:escapeStr(hasScrappedList.bjll.zwms)}"><c:out value="${hasScrappedList.bjll.zwms}" /></td>
						          <td style='vertical-align: middle;'class='text-right'><c:out value="${hasScrappedList.bjll.kccb}" /></td>
						          <td style='vertical-align: middle;'class='text-right'><c:out value="${hasScrappedList.bjll.kcsl}" /></td>
						          <td name="yctd" style='vertical-align: middle;'class='text-center'> 
						             <c:choose>
										<c:when test="${hasScrappedList.zt == 2 }">是</c:when>
										<c:otherwise>否</c:otherwise>
									 </c:choose>
						          </td>
						          <td name="yctd" style='vertical-align: middle;'class='text-center' title="${erayFns:escapeStr(hasScrappedList.bz)}"><c:out value="${hasScrappedList.bz}" /></td>
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

    Navigation(menuCode,"查看审批报废","View Approval Scrap");
    
    $(".panel-heading").not(":first").click(function(){         //隐藏和显示
		$(this).next().slideToggle("fast");
	});
    
	var diczt=DicAndEnumUtil.getEnumName('contractStatusEnum',$("#bfzt").val());  //翻译报废状态
	$("#bfzt").val(diczt);
	
	 if($("#zt").val()==2){
		$("#demo1").css("display", "none");  
		$("#demo2").css("display", "none");  
		$("#demo3").css("display", "none");  
		$("#demo4").css("display", "none");  
		$("#demo5").css("display", "none");
		$("#YClist>tr>td[name='yctd']").hide();
	} 
</script>
</body>
</html>