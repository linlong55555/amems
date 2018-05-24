<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>报废审核</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
<div class="page-content ">
			<!-- from start -->
			<input type="hidden" id="whrid" value="${sessionScope.user.id}" />
			<input type="hidden" id="whbmid" value="${user.bmdm}" />
			<input type="hidden" id="bflx" value="${scrappedList.bflx}" />
	<div class="panel panel-primary">
	<div class="panel-heading" id="NavigationBar"></div>
	     <div class="panel-body">
	          <!--    基本信息 start -->
				 <div class="panel panel-default">
				        <div class="panel-heading">
						  <h3 class="panel-title">报废基本信息 Transfer Information</h3>
					   </div>
				 <div class="panel-body">
				 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				        
				        <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">报废单号</div>
								<div class="font-size-9 line-height-18">Scrap No.</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input type="text"  value="${erayFns:escapeStr(scrappedList.bfdh)}" class="form-control" id="bfdh"  readonly/>
								<input type="hidden"  value="${scrappedList.id}" class="form-control" id="scrappedId"/>
							</div>
						</div>
				 
				 
				   
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>审批人</div>
								<div class="font-size-9 line-height-18"> Approval Person</div>
							</label>
							 <div class=" col-xs-8 padding-left-8 padding-right-0 input-group">
								<div class='input-group'>
								    <input type="text" id="spr" disabled="disabled"  value="${erayFns:escapeStr(scrappedList.sprname)}" class="form-control "/>
								 	<input type="hidden" class="form-control" id="sprid" value="${scrappedList.sprid}" name="sprid"/>
									<input type="hidden" class="form-control" id="sprbmid" value="${scrappedList.spbmid}" name="sprid"/>
									<input type="hidden" id="userspr"  value="${erayFns:escapeStr(user.username)}  ${erayFns:escapeStr(user.realname)}" class="form-control "/>
								 	<input type="hidden" class="form-control" id="usersprid" value="${user.id}" name="sprid"/>
									<span class='input-group-btn'>
									  <button onclick='openUserList()' class='btn btn-primary'><i class='icon-search'></i>
									</button></span>
							    </div>
							</div>
							 
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red">*</span>审批日期</div>
								<div class="font-size-9 line-height-18">Approval Date</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class='form-control datepicker' maxlength="10"  id="spsj"  value="<fmt:formatDate pattern="yyyy-MM-dd " value="${scrappedList.spsj}"/>" data-date-format="yyyy-mm-dd" 
			         		       placeholder="请选择日期"   />
							</div>
						</div>
						
						 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">制单人</div>
									<div class="font-size-9 line-height-18">Creator</div>
								</label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="zdr" name="zdr" value="${erayFns:escapeStr(scrappedList.zdrname)}" class="form-control " readonly />
								</div>
							</div>
						
					</div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
				       <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制单时间</div>
								<div class="font-size-9 line-height-18">Create Time</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zdsj" name="zdsj" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${scrappedList.zdsj}"/>" data-date-format="yyyy-MM-dd HH:mm:ss" class="form-control " readonly />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">报废状态</div>
								<div class="font-size-9 line-height-18">State</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="zt" value="${scrappedList.zt}" class="form-control " readonly />
							</div>
						</div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
						   <div class="font-size-12 line-height-18">报废类型</div>
							<div class="font-size-9 line-height-18">Review Opinion</div></label>
						   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group" >
								<input type="text"  value="${scrappedList.bflx eq 2 ?'外场报废':'库内报废'}" class="form-control " readonly />
						  </div>
					</div>
					</div>
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						   <div class="font-size-12 line-height-18">审核意见</div>
							<div class="font-size-9 line-height-18">Review Opinion</div></label>
						   <div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group" >
								<textarea class="form-control" id="spyj" name="spyj" maxlength="150" >${erayFns:escapeStr(scrappedList.spyj)}</textarea>
						  </div>
					</div>	 	
				 </div>
			     </div>
					<!-- end -->
					
				    <!-- 报废航材start	 -->
				  <div class="panel panel-default">
				        <div class="panel-heading">
						   <h3 class="panel-title">报废航材 Scrap Material</h3>
					    </div>
					<div class="panel-body">
					 <div class="col-lg-12 col-md-12 padding-left-2 padding-right-0" style="overflow: auto;">
						 <table class="table-set table table-thin table-bordered table-striped table-hover " style="min-width:900px">
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
									<div class="font-size-9 line-height-16">Stock Pay（RMB：YUAN）</div></th>
									<th class="colwidth-5"><div class="font-size-12 line-height-18">库存数量</div>
									<div class="font-size-9 line-height-16">Stock Num</div></th>
									<th class="colwidth-7"><div class="font-size-12 line-height-18">是否报废</div>
									<div class="font-size-9 line-height-16">Is Scrap</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">备注</div>
									<div class="font-size-9 line-height-16">Remark</div></th>
								</tr>
							</thead>     
							<tbody id="CKList">
							<c:forEach items="${hasScrappedList}" var="hasScrappedList">
								<tr>
						          <td style='vertical-align: middle;' title="${erayFns:escapeStr(hasScrappedList.bjll.bjh)}">
							          <input type="hidden" name='id'  value="${hasScrappedList.id}">
							          <input type="hidden" name='kcllid'  value="${hasScrappedList.kcllid}">
							          <input type="hidden" name='mainid'  value="${hasScrappedList.mainid}">
							          <input type="hidden" name='hasScrappedzt'  value="${hasScrappedList.zt}">
							          <c:out value="${hasScrappedList.bjll.bjh}" />
						          </td>
						          <td style='vertical-align: middle;' title="${erayFns:escapeStr(hasScrappedList.bjll.sn) eq'' ?erayFns:escapeStr(hasScrappedList.bjll.pch):erayFns:escapeStr(hasScrappedList.bjll.sn)}"><c:out value="${erayFns:escapeStr(hasScrappedList.bjll.sn)=='' ?erayFns:escapeStr(hasScrappedList.bjll.pch):erayFns:escapeStr(hasScrappedList.bjll.sn)}" /></td>
						          <td style='vertical-align: middle;' title="${erayFns:escapeStr(scrappedList.bflx eq 1?hasScrappedList.bjll.ckmc:'外场')}"><c:out value="${erayFns:escapeStr(scrappedList.bflx eq 1?hasScrappedList.bjll.ckmc:'外场')}" /></td>
						          <td style='vertical-align: middle;' title="${erayFns:escapeStr(scrappedList.bflx eq 1?hasScrappedList.bjll.kwh:'外场')}"><c:out value="${erayFns:escapeStr(scrappedList.bflx eq 1?hasScrappedList.bjll.kwh:'外场')}" /></td>
						          <td style='vertical-align: middle;' title="${hasScrappedList.bjll.ywms}"><c:out value="${hasScrappedList.bjll.ywms}" /></td>
						          <td style='vertical-align: middle;' title="${hasScrappedList.bjll.zwms}"><c:out value="${hasScrappedList.bjll.zwms}" /></td>
						          <td style='vertical-align: middle;'class='text-right'><c:out value="${hasScrappedList.bjll.kccb}" /></td>
						          <td style='vertical-align: middle;'class='text-right'><c:out value="${hasScrappedList.bjll.kcsl}" /></td>
						          <td width="8%"  style='vertical-align: middle;' class='text-center'>
						             <input name="zt_${hasScrappedList.id}" type="radio" value="2" />是
						             <input name="zt_${hasScrappedList.id}" type="radio" value="3" />否
						          </td>
						          <td width="40%"   style='vertical-align: middle;'class='text-center'>
						             <textarea style="height:35px" maxlength="300" placeholder='最大长度不超过300' id="bz" name='bz' class="form-control ">${hasScrappedList.bz}</textarea>
						          </td>
								</tr>
							</c:forEach>
							</tbody>	
						</table>
					  </div>
			        </div>
			      </div>
			      
			      
			      <div class=" pull-right" style="margin-bottom: 10px">
                      	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="save()"><div
										class="font-size-12">保存</div>
						<div class="font-size-9">Save</div></button>
                       <button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="submitSave()"><div
										class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div></button>
                     	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()"><div
										class="font-size-12">返回</div>
						<div class="font-size-9">Back</div></button>
                 </div>			 	
	     </div>
	     
	</div>
</div>
	<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/scrap/audit_scrap.js"></script>
</body>
</html>