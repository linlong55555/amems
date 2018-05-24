<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>检验航材信息</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>

<script>
var pageParam = '${param.pageParam}';
</script>
</head>
<body>
<div class="page-content ">
			<!-- from start -->
	<div class="panel panel-primary">
	<div class="panel-heading" id="NavigationBar"></div>
		<div class="panel-body">
			   <input type="hidden" id="id" value="${queryQuality.id}">
			   <input type="hidden" id="kcid" value="${queryQuality.kcid}">
			   <input type="hidden" id="hclyone" value="${queryQuality.hcly}">
			  <div class="panel panel-default">
				        <div class="panel-heading">
						 <h3 class="panel-title">基本信息  Basic Information</h3>
					    </div>
				<div class="panel-body">	
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
					
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="jydh_div">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">检验单号</div>
								<div class="font-size-9 line-height-18">Check No.</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" value="${erayFns:escapeStr(queryQuality.jydh)}" id="jydh" class="form-control " readonly />
								<input type="hidden" value="${queryQuality.dprtcode}" id="dprtcode" class="form-control " readonly />
							</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">入库单号</div>
								<div class="font-size-9 line-height-18">Maintenance No.</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text"  value="${erayFns:escapeStr(queryQuality.rkdh)}" class="form-control"  readonly/>
								<input type="hidden"  value="${queryQuality.rkdid}" class="form-control" id="rkdid" readonly/>
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
						<div id="div_1"></div>
					   	<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10">
								<label class="col-lg-4 col-sm-4 col-xs-4  text-right padding-left-0 padding-right-0">
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
					    <div id="div_2"></div>
					  	<div class=" col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
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
								<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
								    <input type="text" class="form-control" value="${erayFns:escapeStr(queryQuality.zwms)}"  disabled="disabled"/>
								</div>
							</div>
					   		<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">英文名称</div>
								<div class="font-size-9 line-height-18">F.Name</div></label>
							   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
								 <input type="text" class="form-control"  value="${erayFns:escapeStr(queryQuality.ywms)}" disabled="disabled"/>
							   </div>
					    	</div>
					     
					       <div id="div_3"></div> 
						  	<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Num</div></label>
								   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
										<input type="text"  class="form-control"  id="kcsl" value="${queryQuality.kcsl}" disabled="disabled"/>
								 </div>
						    </div>
						    <div id="div_4"></div> 
						    <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class=" col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
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
									<div class="font-size-9 line-height-18"> Store Name</div>
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
							<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
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
						  <h3 class="panel-title">航材检验  Material Checking</h3>
					    </div>
				<div class="panel-body">
				   <form id="form">
				   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4  text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">航材来源</div>
								<div class="font-size-9 line-height-18">Aircraft Source</div>
							</label>
							<div id="hclyDiv" class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								 <select id="hcly"  name="hcly" class="form-control"  >
							     </select>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">许可证号</div>
								<div class="font-size-9 line-height-18">License</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
								<input type="text" value="${erayFns:escapeStr(queryQuality.xkzh)}" maxlength="50" id="xkzh"  name="xkzh" class="form-control " />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">适航证号</div>
								<div class="font-size-9 line-height-18">Certificate</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" value="${erayFns:escapeStr(queryQuality.shzh)}" id="shzh"  maxlength="50" name="shzh" class="form-control " />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">适航证位置</div>
								<div class="font-size-9 line-height-18">Certificate position</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" value="${erayFns:escapeStr(queryQuality.shzwz)}" id="shzwz"  name="shzwz" maxlength="30" class="form-control "/>
							</div>
						</div>
					</div>
				   <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">货架寿命</div>
								<div class="font-size-9 line-height-18">Shelf-Life</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="hjsm"  name="hjsm" class='form-control datepicker'  value="<fmt:formatDate pattern="yyyy-MM-dd" value="${queryQuality.hjsm}"/>" data-date-format="yyyy-mm-dd"/>
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">TSN</div>
								<div class="font-size-9 line-height-18">TSN</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" value="${erayFns:escapeStr(queryQuality.tsn)}" id="tsn"  name="tsn" maxlength="50" class="form-control " />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">TSO</div>
								<div class="font-size-9 line-height-18">TSO</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 form-group">
								<input type="text" value="${erayFns:escapeStr(queryQuality.tso)}" id="tso"  name="tso"  maxlength="50" class="form-control "  />
							</div>
						</div>
					</div>
				     <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">存放要求</div>
							<div class="font-size-9 line-height-18">Storage Must</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
							<textarea class="form-control" id="cfyq" name="cfyq" maxlength="150" placeholder="最大长度不超过150">${erayFns:escapeStr(queryQuality.cfyq)}</textarea>
						</div>
					</div>
					<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</label>
						<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
							<textarea class="form-control" id="bz" name="bz" maxlength="300" placeholder="最大长度不超过300" >${erayFns:escapeStr(queryQuality.bz)}</textarea>
						</div>
					</div>
					</form>
				    <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0" >
				         <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">检验人</div>
								<div class="font-size-9 line-height-18">Inspection man</div>
							</label>
							 <div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
								<div class='input-group'>
									<input type="text" id="checker"  value="${erayFns:escapeStr(queryQuality.displayname)}" class="form-control "  onchange="ChangeJyr()"/>
							 	<input type="hidden" class="form-control" id="checker2" value=""   />
							 	<input type="hidden" class="form-control" id="checker3" value=" ${erayFns:escapeStr(user.realname)}" />
							 	<input type="hidden" class="form-control" id="jyrid" value="${user.id}" name="jyrid"/>
							 	
							 	<input type="hidden" class="form-control" id="dqdlr" value="${erayFns:escapeStr(user.username)}  ${erayFns:escapeStr(user.realname)}" />
									<span class='input-group-btn'>
									  <button onclick='openUserList()' class='btn btn-primary'><i class='icon-search'></i>
									</button></span>
							    </div>
							</div>
							 
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">检验日期</div>
								<div class="font-size-9 line-height-18">Inspection date</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class='form-control datepicker'  id="jyrq"  value="<fmt:formatDate pattern="yyyy-MM-dd" value="${queryQuality.jyrq}"/>" data-date-format="yyyy-mm-dd" 
			         		       placeholder="请选择日期"   />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">检验结果</div>
								<div class="font-size-9 line-height-18">Inspection Result</div>
							</label>
							<div class=" col-xs-8 padding-left-8 padding-right-0">
									<select class='form-control' id='jyjg'  onchange="checkResult()">
										<option value="0" <c:if test="${queryQuality.jyjg==''}">selected</c:if> >请选择Pleasechoose</option>
										<option value="1" <c:if test="${queryQuality.jyjg=='1'}">selected</c:if> >合格Qualified</option>
										<option value="2" <c:if test="${queryQuality.jyjg=='2'}">selected</c:if> >不合格Unqualified</option>
										<option value="3" <c:if test="${queryQuality.jyjg=='3'}">selected</c:if> >让步接收Compromise</option>
								    </select>
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" id="jgsm_div" style="display: none">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">结果说明</div>
								<div class="font-size-9 line-height-18">Checked desc</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0">
								<textarea class="form-control" id="jgsm" name="jgsm" maxlength="30">${erayFns:escapeStr(queryQuality.jgsm)}</textarea>
							</div>
					   </div>
					</div>	
				</div> 
			</div>
		  
			 <div class="panel panel-default">
			        <div class="panel-heading">
					  <h3 class="panel-title">检验附件  Attachments Files</h3>
				    </div>
				<div class="panel-body">	
				   <div class=" col-lg-4 col-sm-6 col-xs-8  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-3 col-sm-4 col-xs-6 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red"></span>文件说明</div>
							<div class="font-size-9 line-height-18">File desc</div></label>
						 <div class="col-lg-9 col-sm-8 col-xs-6 padding-left-8 padding-right-0" >
							<input type="text" id="wbwjm" name="wbwjm" maxlength="100" class="form-control "  >
						</div>
					</div>
				     <div class=" col-lg-4 col-sm-1 col-xs-4  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-12 "  style="margin-left: 5px ;padding-left: 0"></div>
					</div> 
				      <div  class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow: auto;" >
						<table class="table table-thin table-bordered table-striped table-hover text-center" style="min-width:900px">
							<thead>
								<tr>
									<th style="width:110px;"><div class="font-size-12 line-height-18 " >操作</div>
										<div class="font-size-9 line-height-18">Operation</div></th>
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
									 
								</tbody>
						</table>
					</div>
				</div>	
			</div>	
				
				<div class="text-right" style="margin-top: 20px;margin-bottom: 20px;">
                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1"  onclick="checkSave()">
                    	<div class="font-size-12">保存</div>
						<div class="font-size-9">Save</div></button>
						
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="submitSave()">
                    	<div class="font-size-12">提交</div>
						<div class="font-size-9">Submit</div></button>
						
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back();">
                   	<div class="font-size-12">返回</div>
					<div class="font-size-9">Back</div></button>
                 </div>
				 <div class="clearfix"></div>
			</div>
		</div>
	</div>	
<!-- 基本信息 End -->
	<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	 	
<script type="text/javascript" src="${ctx}/static/js/thjw/material/qualitycheck/edit_qualitycheck.js"></script>
</body>
</html>