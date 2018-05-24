<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看航材检验信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
<div class="page-content ">
<!-- from start -->
	<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
		<div class="panel-body">
		    <input type="hidden" id="id" value="${queryQuality.id}">
		           
			<div class="panel panel-default">
		        <div class="panel-heading">
				   <h3 class="panel-title">基本信息  Basic Information</h3>
			    </div>
			  <div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">检验单号</div>
							<div class="font-size-9 line-height-18">Check No.</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" value="${erayFns:escapeStr(queryQuality.jydh)}" class="form-control " readonly />
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">入库单号</div>
							<div class="font-size-9 line-height-18">InStore No.</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text"  value="${erayFns:escapeStr(queryQuality.rkdh)}" class="form-control"  readonly/>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">合同号</div>
							<div class="font-size-9 line-height-18">Contract No.</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text"value="${erayFns:escapeStr(queryQuality.hth)}" class="form-control"  readonly/>
						</div>
					</div>
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">部件号</div>
								<div class="font-size-9 line-height-18">P/N</div></label>
						<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
						    <input type="text"   class="form-control" value="${erayFns:escapeStr(queryQuality.bjh)}"  disabled="disabled"/>
						</div>
					</div>
				</div>
				
				<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
				   	<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0 form-group">
						    <div class="font-size-12 line-height-18">管理级别</div>
							<div class="font-size-9 line-height-18">Management level</div></label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								   <select id="gljb" class="form-control"  disabled="disabled">
										<c:forEach items="${supervisoryLevelEnum}" var="item">
										  <option value="${item.id}" <c:if test="${queryQuality.gljb == item.id }">selected="true"</c:if> >${item.name}</option>
										</c:forEach>
								   </select>
						   </div>
				    </div>
				  	<div class=" col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						    <div class="font-size-12 line-height-18">批次号/序列号</div>
							<div class="font-size-9 line-height-18">B/N（S/N）</div></label>
						   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
								<input type="text"  class="form-control"  value="${erayFns:escapeStr(queryQuality.pch)} ${erayFns:escapeStr(queryQuality.sn)}" disabled="disabled"/>
						 </div>
				    </div>
				    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8   padding-left-8 padding-right-0 form-group">
							    <input type="text" class="form-control" value="${erayFns:escapeStr(queryQuality.zwms)}"  disabled="disabled"/>
							</div>
					</div>
				   	<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						    <div class="font-size-12 line-height-18">英文名称</div>
							<div class="font-size-9 line-height-18">F.Name</div></label>
						   <div class="col-lg-8 col-sm-8 col-xs-8   padding-left-8 padding-right-0 form-group">
								<input type="text" class="form-control"  value="${erayFns:escapeStr(queryQuality.ywms)}" disabled="disabled"/>
						 </div>
				     </div>
				</div>
				
				<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
					  	<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">数量</div>
								<div class="font-size-9 line-height-18">Num</div></label>
							   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
									<input type="text"  class="form-control"  value="${queryQuality.kcsl}" disabled="disabled"/>
							 </div>
					    </div>
					    <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">计量单位</div>
									<div class="font-size-9 line-height-18">Unit</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" value="${erayFns:escapeStr(queryQuality.jldw)}" id="jldw" class="form-control " readonly />
								</div>
					    </div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">仓库名称</div>
									<div class="font-size-9 line-height-18">Store Name</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" value="${erayFns:escapeStr(queryQuality.ckmc)}" class="form-control " readonly />
								</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">库位号</div>
									<div class="font-size-9 line-height-18">Storage location</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" value="${erayFns:escapeStr(queryQuality.kwh)}" class="form-control " readonly />
								</div>
						</div>
				</div>
	
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4  text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">航材类型</div>
							<div class="font-size-9 line-height-18">Airmaterial type</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							 <select id="hclx" class="form-control"  disabled="disabled">
								<c:forEach items="${materialTypeEnum}" var="item">
								  <option value="${item.id}" <c:if test="${queryQuality.hclx == item.id }">selected="true"</c:if> >${item.name}</option>
								</c:forEach>
						     </select>
						</div>
					</div>
				</div>
		    </div>
		 </div>   
		   
		   
		    <div class="panel panel-default">
		        <div class="panel-heading">
				 <h3 class="panel-title">检验 Inspection</h3>
			    </div>
			  <div class="panel-body">
			   <form id="form">
			   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">航材来源</div>
							<div class="font-size-9 line-height-18">Aircraft Source</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text"  value="${queryQuality.hcly}" readonly class="form-control " />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">许可证号</div>
							<div class="font-size-9 line-height-18">License</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
							<input type="text" value="${erayFns:escapeStr(queryQuality.xkzh)}" readonly class="form-control " />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">适航证号</div>
							<div class="font-size-9 line-height-18">Certificate</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
							<input type="text" value="${erayFns:escapeStr(queryQuality.shzh)}" readonly class="form-control " />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4  text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">适航证位置</div>
							<div class="font-size-9 line-height-18">Certificate position</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
							<input type="text" value="${erayFns:escapeStr(queryQuality.shzwz)}" readonly class="form-control "/>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">TSN</div>
							<div class="font-size-9 line-height-18">TSN</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
							<input type="text" value="${erayFns:escapeStr(queryQuality.tsn)}" readonly class="form-control " />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">TSO</div>
							<div class="font-size-9 line-height-18">TSO</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 ">
							<input type="text" value="${erayFns:escapeStr(queryQuality.tso)}" readonly  class="form-control "  />
						</div>
					</div>
				</div>
			     <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 ">
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">存放要求</div>
						<div class="font-size-9 line-height-18">Storage Must</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
						<textarea class="form-control" readonly  >${erayFns:escapeStr(queryQuality.cfyq)}</textarea>
					</div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">备注</div>
						<div class="font-size-9 line-height-18">Remark</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 ">
						<textarea class="form-control"readonly >${erayFns:escapeStr(queryQuality.bz)}</textarea>
					</div>
				</div>
				</form>
			    <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
			         <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">检验人</div>
							<div class="font-size-9 line-height-18">Inspection man</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="checker" readonly  value="${erayFns:escapeStr(queryQuality.displayname)} " class="form-control " />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">检验日期</div>
							<div class="font-size-9 line-height-18">Inspection date</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text"   id="jyrq"  readonly  class="form-control "  value="<fmt:formatDate pattern="yyyy-MM-dd" value="${queryQuality.jyrq}"/>"/>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">检验结果</div>
							<div class="font-size-9 line-height-18">Inspection Result</div>
						</label>
						<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							  <select id="jyjg" class="form-control"  disabled="disabled">
									<c:forEach items="${materialCheckResultEnum}" var="item">
									  		<option value="${item.id}" <c:if test="${queryQuality.jyjg == item.id }">selected="true"</c:if> >${item.name}</option>
									</c:forEach>
						     </select>
						</div>
					</div>
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" id="jgsm_div" >
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">结果说明</div>
							<div class="font-size-9 line-height-18">Checked desc</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0">
							<textarea class="form-control" readonly>${erayFns:escapeStr(queryQuality.jgsm)}</textarea>
						</div>
				   </div>
				</div>	
			</div>   
			</div>
	
		   <div class="panel panel-default">
		        <div class="panel-heading">
				 <h3 class="panel-title">检验附件  Attachments</h3>
			    </div>
			  <div class="panel-body">	
			      <div  class="col-lg-12 col-md-12 padding-left-2"  style="margin-left: 15px;overflow-x:auto; " >
					<table class="table table-thin table-bordered table-striped table-hover" style="min-width:900px">
						<thead>
							<tr>
							   <th>
							       <div class="font-size-12 line-height-18">文件说明</div>
									<div class="font-size-9 line-height-18">File desc</div>
								</th>
								<th>
								<div class="font-size-12 line-height-18">文件大小</div>
									<div class="font-size-9 line-height-18">File size</div>
								</th>
								<th><div class="font-size-12 line-height-18">上传人</div>
									<div class="font-size-9 line-height-18">Operator</div></th>
								<th><div class="font-size-12 line-height-18">上传时间</div>
									<div class="font-size-9 line-height-18">Operate Time</div></th>					
							</tr>
						</thead>
						 <tbody id="filelist">
						    <c:forEach items="${attachment}" var="attachment">
								<tr>
						          <td>
						            <a href='javascript:void(0);' onclick="downloadfile('${attachment.id}')">
						              <c:out value="${attachment.wbwjm}" />
						            </a>
				                 </td>
						          <td>
						                 <c:out value="${attachment.wjdxStr}" />
				                 </td>
						          <td>
						                <c:out value="${attachment.czrname}" />
				                  </td>
						          <td class='text-center'>
						              <fmt:formatDate value='${attachment.czsj}'  var="czsj" pattern='yyyy-MM-dd HH:mm:ss' />
						               <c:out  value="${czsj}"/>
				                  </td>
								</tr>
							 </c:forEach> 
					     </tbody>
					</table>
				</div>
			</div>	
		</div>
			<div class="clearfix"></div>  
		</div>
	</div>
</div>
<!-- 基本信息 End -->
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/qualitycheck/view_qualitycheck.js"></script> 
</body>
</html>