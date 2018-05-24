<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
<input id="newFileName" type="hidden"/>
<input id="relativePath" type="hidden"/>
<input id="fileName" type="hidden"/>
<input id="id" value="${technicalFile.id}" type="hidden"/>
<input id="ly1" value="${erayFns:escapeStr(technicalFile.ly)}" type="hidden"/>
<input id="fl1" value="${erayFns:escapeStr(technicalFile.fl)}" type="hidden"/>
<input id="wjlx1" value="${erayFns:escapeStr(technicalFile.wjlx)}" type="hidden"/>
<input id="bb1" value="${erayFns:escapeStr(technicalFile.bb)}" type="hidden"/>
<input id="syx1" value="${erayFns:escapeStr(technicalFile.syx)}" type="hidden"></input>
<input id="zt" value="${technicalFile.zt}" type="hidden"></input>
<input id="pgrid1" value="${technicalFile.pgrid}" type="hidden"></input>
<input id="technicalFileDprtcode" value="${technicalFile.dprtcode}" type="hidden" />
<input id="list1"  type="hidden" ></input>
	<div class="page-content " >
		<!-------导航Start--->
<script type="text/javascript">
var pageParam = '${param.pageParam}';
$(document).ready(function(){
//导航
Navigation(menuCode,"技术文件审核","Review");
});
</script>
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>

			<div class="panel-body">
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">评估单号</div>
							<div class="font-size-9 line-height-18">Assessment NO.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<label class="form-control " style="font-weight:normal" readonly="readonly">${erayFns:escapeStr(technicalFile.pgdh)}</label>
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">来源</div>
							<div class="font-size-9 line-height-18">Source</div></label>
						 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
						 <input id="ly" class="form-control " name="ly" value="${erayFns:escapeStr(technicalFile.ly)}" disabled="disabled" />
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">机型</div>
							<div class="font-size-9 line-height-18">Model</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input id="jx" class="form-control " name="jx" value="${erayFns:escapeStr(technicalFile.jx)}" disabled="disabled" />
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">分类</div>
							<div class="font-size-9 line-height-18">Category</div></label>
							 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
							 <input id="fl" class="form-control " name="fl" value="${erayFns:escapeStr(technicalFile.fl)}" disabled="disabled" />
						</div>
				</div>
					
				<div class="clearfix"></div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">文件类型</div>
							<div class="font-size-9 line-height-18">File Type</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input id="wjlx" class="form-control " name="wjlx" value="${erayFns:escapeStr(technicalFile.wjlx)}" disabled="disabled" />
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">上传日期</div>
							<div class="font-size-9 line-height-18">Upload Date</div></label>
						 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
								<input type="text" id="zdsj" value="<fmt:formatDate value='${technicalFile.zdsj}' pattern='yyyy-MM-dd' />" name="zdsj"   maxlength="30" class="form-control"  disabled="disabled">
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">版本</div>
							<div class="font-size-9 line-height-18">Revision</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input id="bb" class="form-control " name="bb" value="${erayFns:escapeStr(technicalFile.bb)}" disabled="disabled" />
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">机型工程师</div>
							<div class="font-size-9 line-height-18">Engineer</div></label>
						 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
					 	<input id="pgrid" class="form-control " name="pgrid" value="${technicalFile.pgr_user.displayName}" disabled="disabled" />
						</div>
				</div>
					
				<div class="clearfix"></div>
					
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">生效日期</div>
							<div class="font-size-9 line-height-18">Effective Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control date-picker" value="<fmt:formatDate value='${technicalFile.sxrq}' pattern='yyyy-MM-dd' />" id="sxrq" name="sxrq" data-date-format="yyyy-mm-dd" type="text" disabled="disabled"/>
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">评估期限</div>
							<div class="font-size-9 line-height-18">Assess period</div></label>
						 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
								<input class="form-control  date-picker" value="<fmt:formatDate value='${technicalFile.pgqx}' pattern='yyyy-MM-dd' />" id="pgqx" name="pgqx" data-date-format="yyyy-mm-dd" type="text" disabled="disabled"/>
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">State</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control"  name="ztnum" value="${technicalFile.ztText}" id="ztnum" disabled="disabled"/>
						</div>
				</div>
					
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">ATA章节号</div>
							<div class="font-size-9 line-height-18">ATA</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control"  name="ztnum" value="${erayFns:escapeStr(technicalFile.zjh)} ${erayFns:escapeStr(technicalFile.zj.zwms)}" id="ztnum" disabled="disabled"/>
						</div>
				</div>
				
				<div class="clearfix"></div>
					
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单人</div>
							<div class="font-size-9 line-height-18">Creator</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control"  name="ztnum" value="${technicalFile.zdr_user.displayName}" id="ztnum" disabled="disabled"/>
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">制单时间</div>
							<div class="font-size-9 line-height-18">Create Time</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control"  name="ztnum" value="<fmt:formatDate value='${technicalFile.zdsj}' pattern='yyyy-MM-dd HH:mm:ss' />" id="ztnum" disabled="disabled"/>
						</div>
				</div>
					
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">文件编号</div>
							<div class="font-size-9 line-height-18">File No.</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" id="shzltgh" value="${erayFns:escapeStr(technicalFile.shzltgh)}" name="shzltgh" placeholder='长度最大为30'   maxlength="30" class="form-control"  disabled="disabled">
						</div>
				</div>
					
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">原文件</div>
							<div class="font-size-9 line-height-18">File</div></label>
						 <div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" >
							<a href=javascript:downfile("${technicalUpload.id}")>${erayFns:escapeStr(technicalUpload.wbwjm)}${erayFns:escapeStr(technicalUpload.hzm)}</a>
						</div>
				</div>
				
				<div class="clearfix"></div>
			
				<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-right-0 padding-left-0 ">
						<div class="font-size-12 line-height-18">备注</div>
						<div class="font-size-9 line-height-18">Remark</div>
				</label>
				<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 form-group padding-right-0">
						<textarea class="form-control" id="bz"  maxlength="300" disabled="disabled">${erayFns:escapeStr(technicalFile.bz)}</textarea>
				</div>
					
				<div class="clearfix"></div>
					
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">发布日期</div>
							<div class="font-size-9 line-height-18">Release Date</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control  date-picker" type="text" name="fbrq" id="fbrq" data-date-format="yyyy-mm-dd" value="<fmt:formatDate value='${technicalFile.fbrq}' pattern='yyyy-MM-dd' />" disabled="disabled" />
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">等级</div>
							<div class="font-size-9 line-height-18">Level</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control" type="text" name="dj" id="dj" value="${erayFns:escapeStr(technicalFile.dj)}" disabled="disabled" />
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">紧急程度</div>
							<div class="font-size-9 line-height-18">Emergency</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control" type="text" name="jjcd" id="jjcd" value="${erayFns:escapeStr(technicalFile.jjcd)}" disabled="disabled" />
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">完成种类</div>
							<div class="font-size-9 line-height-18">Compliance</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class="form-control" name="wczl" id="wczl" disabled="disabled padding-right-0">
								<option value="1" <c:if test="${'1' eq technicalFile.wczl}">selected</c:if>>强制</option>
								<option value="2" <c:if test="${'2' eq technicalFile.wczl}">selected</c:if>>建议</option>
								<option value="3" <c:if test="${'3' eq technicalFile.wczl}">selected</c:if>>选做</option>
							</select>
						</div>
				</div>
				
				<div class="clearfix"></div>
					
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
							<div class="font-size-12 line-height-18">相关文件</div>
							<div class="font-size-9 line-height-18">Related Files</div>
						</label>
						<div class="col-lg-8 col-sm-6 col-xs-4 padding-left-8 form-group padding-right-0 " style='margin-bottom:0px;'>
							<textarea class="form-control" id="xgwj"  maxlength="300" disabled="disabled">${erayFns:escapeStr(technicalFile.xgwj)}</textarea>
						</div>
					</div>
					
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-bottom-10 padding-right-0 form-group">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
							<div class="font-size-12 line-height-18">文件主题</div>
							<div class="font-size-9 line-height-18">Subject</div>
						</label>
						<div class="col-lg-8 col-sm-6 col-xs-4 padding-left-8 form-group padding-right-0 " style='margin-bottom:0px;'>
							<textarea class="form-control" id="wjzt"  maxlength="300" disabled="disabled">${erayFns:escapeStr(technicalFile.wjzt)}</textarea>
						</div>
					</div>
					
				<div class="clearfix"></div>
				
				<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0  padding-right-0 ">	
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
							<div class="font-size-12 line-height-18">涉及改装</div>
							<div class="font-size-9 line-height-18">MOD No.</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
								<textarea class="form-control" id="sjgz"   maxlength="300" disabled="disabled" >${erayFns:escapeStr(technicalFile.sjgz)}</textarea>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0  padding-right-0 padding-top-10">	
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
							<div class="font-size-12 line-height-18">文件摘要</div>
							<div class="font-size-9 line-height-18">Abstract</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 form-group padding-right-0">
							<textarea class="form-control" id="wjzy"   maxlength="300" disabled="disabled">${erayFns:escapeStr(technicalFile.wjzy)}</textarea>
					</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18"><span style="color: red">*</span>适用性</div>
							<div class="font-size-9 line-height-18">Affectivity</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="syx" class="form-control"  name="syx"  onchange="showOrHide(this.value);" disabled="disabled">
							<option value="1">适用</option>
							<option value="0">不适用</option>
							</select>
						</div>
				</div>
				
				<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" id="syxdxms" >
						<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">受影响对象描述</div>
							<div class="font-size-9 line-height-18">Desc</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control"  name="syxdx" value="${erayFns:escapeStr(technicalFile.syxdx)}" id="syxdx" maxlength="60" disabled="disabled"/>
						</div>
				</div>

				</div>
				
				<div id="xfwj">
			
				<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 "  >
						<div class="font-size-12 line-height-18">下达指令</div>
						<div class="font-size-9 line-height-18">Order</div>
				</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 ">
						<div class="col-lg-12 col-sm-12 col-xs-12  ">
							<div class="col-lg-3 col-sm-6 col-xs-12  ">
							 	<label style="padding-right: 10px; font-weight:normal"><input class="pull-left margin-right-5 " type="checkbox" id="isJstg" value="${technicalFile.isJstg}" name="isJstg" disabled="disabled" /><div class="pull-right"><div class="font-size-12 line-height-18">维护提示</div><div class="font-size-9 line-height-12">Technical Bulletin</div></div> </label> 
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  ">
						 		<label style="padding-right: 10px; font-weight:normal"><input class="pull-left margin-right-5 " type="checkbox" id="isJszl" value="${technicalFile.isJszl}" name="isJszl"  disabled="disabled" /><div class="pull-right"><div class="font-size-12 line-height-18">技术指令</div><div class="font-size-9 line-height-12">Technical Order</div></div> </label> 
						 	</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  ">
						 		<label style="padding-right: 10px; font-weight:normal"><input class="pull-left margin-right-5 " type="checkbox" id="isWxfa" value="${technicalFile.isWxfa}" name="isWxfa"   disabled="disabled"/><div class="pull-right"><div class="font-size-12 line-height-18">修订维修方案</div><div class="font-size-9 line-height-12">Amendment Maintenance</div></div> </label> 
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  ">
						 		<label style="padding-right: 10px; font-weight:normal"><input class="pull-left margin-right-5 " type="checkbox" id="isXdmel" value="${technicalFile.isXdmel}" name="isXdmel"   disabled="disabled"/><div class="pull-right"><div class="font-size-12 line-height-18">修订MEL</div><div class="font-size-9 line-height-12">Amendment MEL</div></div> </label> 
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12  ">
							<div class="col-lg-3 col-sm-6 col-xs-12  ">
						 		<label style="padding-right: 10px; font-weight:normal" ><input class="pull-left margin-right-5 " type="checkbox" id="isXdgk" value="${technicalFile.isXdgk}" name="isXdgk"   disabled="disabled" /><div class="pull-right"><div class="font-size-12 line-height-18">修订工卡</div><div class="font-size-9 line-height-12">Amendment Card</div></div> </label> 
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  " >
						 	<label style="padding-right: 10px; font-weight:normal"><input class="pull-left margin-right-5 " type="checkbox" id="isGczl" value="${technicalFile.isGczl}" name="isGczl"  disabled="disabled" /><div class="pull-right"><div class="font-size-12 line-height-18">工程指令</div><div class="font-size-9 line-height-12">Engineering Order</div></div> </label> 
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  ">	
								 <label style="padding-right: 10px; font-weight:normal"><input class="pull-left margin-right-5 " type="checkbox" id="isFjgd" value="${technicalFile.isFjgd}" name="isFjgd"  disabled="disabled" /><div class="pull-right"><div class="font-size-12 line-height-18">附加工单</div><div class="font-size-9 line-height-12">Attached W/O</div></div> </label> 
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  ">
							 	<label style="padding-right: 10px; font-weight:normal"><input class="pull-left margin-right-5 " type="checkbox" id="isFlxgd" value="${technicalFile.isFlxgd}" name="isFlxgd"  disabled="disabled" /><div class="pull-right"><div class="font-size-12 line-height-18">排故工单</div><div class="font-size-9 line-height-12">Troubleshooting W/O</div></div> </label> 
						 	</div>
						 	<div class="col-lg-3 col-sm-6 col-xs-12  ">
						 		<label style="padding-right: 10px; font-weight:normal"><input class="pull-left margin-right-5 " type="checkbox" id="isZjfj" value="${technicalFile.isZjfj}" name="isZjfj"  disabled="disabled" /><div class="pull-right"><div class="font-size-12 line-height-18">转交飞行部</div><div class="font-size-9 line-height-12">Flight Dept</div></div> </label> 
							</div>
							<div class="col-lg-3 col-sm-6 col-xs-12  ">
									<div class='input-group'>
										 <label style="padding-right: 10px; font-weight:normal"><input onclick="showOrHides(this.value);" class="pull-left margin-right-5 " type="checkbox" id="isQt" value="${technicalFile.isQt}" name="isQt"  disabled="disabled"/><div class="pull-right"><div class="font-size-12 line-height-18">其他</div><div class="font-size-9 line-height-12">Other</div></div> </label>   
										<span class='input-group-btn'>
										   <input maxlength="30" style="float: right; width: 200px; margin-left: 3px; margin-top: -3px;" class="form-control" id="qtMs" value="${erayFns:escapeStr(technicalFile.qtMs)}" name="qtMs" id="qtMs" disabled="disabled">
										</button>
										</span>
					   			 </div>
							</div>
					 </div>
					</div>
				</div>
				
				<div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0  margin-right-10 margin-bottom-10" style="width: 90%; margin-left: 9%;" id="yxdxList">
                    <div class="nav nav-tabs">
					<div class="font-size-14 line-height-18" style="font-weight:bold;">受影响的对象列表</div>
					<div class="font-size-9  line-height-18">List of Affected Object</div>
                    </div>
                  <div class="tab-content">
                  			 <div class="col-lg-12 col-xs-12">
							    <div class="text-left margin-top-10">
				                </div>
								<!-- start:table -->
								<div class="margin-top-10 ">
								<div class="col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 padding-top-0 "  style="overflow-x:auto;">
									<table class="table table-bordered table-striped table-hover text-center" style="min-width:1000px">
										<thead> 
									   		<tr>
												<th class="colwidth-10">
													<div class="font-size-12 notwrap">执行分类</div>
													<div class="font-size-9 notwrap">Category</div>
												</th>
												<th class="colwidth-10">
													<div class="font-size-12 notwrap">飞机注册号</div>
													<div class="font-size-9 notwrap">A/C REG</div>
												</th>
												<th class="colwidth-15">
													<div class="font-size-12 notwrap">部件号</div>
													<div class="font-size-9 notwrap">P/N</div>
												</th>
												<th class="colwidth-13">
													<div class="font-size-12 notwrap">部件序列号</div>
													<div class="font-size-9 notwrap">S/N</div>
												</th>
												<th class="colwidth-20">
													<div class="font-size-12 notwrap">标准工时</div>
													<div class="font-size-9 notwrap">Plan Man-hours</div>
												</th>
									 		 </tr>
										</thead>
										<tbody id="rotatable">
										</tbody>
									</table>
									</div>
								</div>
								<!-- end:table -->
			                
						 	 </div>

                  </div>	
				</div>
					
				<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">免费航材</div>
							<div class="font-size-9 line-height-18">Free Material</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
							<label style="margin-right: 20px;font-weight: normal">
								<input name="isMfhc" type="radio" value="1"  disabled="disabled" <c:if test="${'1' eq technicalFile.isMfhc}">checked</c:if> />是
							</label> 
							
							<label style="font-weight: normal">
								<input name="isMfhc" type="radio" value="0" disabled="disabled" <c:if test="${'0' eq technicalFile.isMfhc}">checked</c:if> />否 
							</label> 
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">自备航材</div>
							<div class="font-size-9 line-height-18">Self Material</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
							<label style="margin-right: 20px;font-weight: normal">
								<input name="isZbhc" type="radio" value="1"  disabled="disabled" <c:if test="${'1' eq technicalFile.isZbhc}">checked</c:if> />是
							</label> 
							
							<label style="font-weight: normal">
								<input name="isZbhc" type="radio" value="0" disabled="disabled" <c:if test="${'0' eq technicalFile.isZbhc}">checked</c:if> />否 
							</label> 
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">需特殊工具</div>
							<div class="font-size-9 line-height-18">Special Tool</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
							<label style="margin-right: 20px;font-weight: normal">
								<input name="isTsgj" type="radio" value="1"  disabled="disabled" <c:if test="${'1' eq technicalFile.isTsgj}">checked</c:if> />是
							</label> 
							
							<label style="font-weight: normal">
								<input name="isTsgj" type="radio" value="0" disabled="disabled" <c:if test="${'0' eq technicalFile.isTsgj}">checked</c:if> />否 
							</label> 
						</div>
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">影响重量平衡</div>
							<div class="font-size-9 line-height-18">Weight Balance</div>
						</label>
						<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
							<label style="margin-right: 20px;font-weight: normal">
								<input name="isYxzlph" type="radio" value="1" disabled="disabled" <c:if test="${'1' eq technicalFile.isYxzlph}">checked</c:if> />是
							</label> 
							
							<label style="font-weight: normal">
								<input name="isYxzlph" type="radio" value="0" disabled="disabled" <c:if test="${'0' eq technicalFile.isYxzlph}">checked</c:if> />否 
							</label> 
						</div>
					</div>
					
				</div>
					
					<div class="clearfix"></div>
					
					<div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 margin-bottom-10 padding-right-0 padding-top-8 form-group">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">关闭条件
								<div class="font-size-9 line-height-18">Close Condition</div></label>
								<div class=" col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
								<input class="form-control" type="text" name="gbtj" id="gbtj" value="${erayFns:escapeStr(technicalFile.gbtj)}" disabled="disabled" />
							</div>
					</div>
					
                   <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
						<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"><div class="font-size-12 line-height-18">服务信件内容</div>
							<div class="font-size-9 line-height-18">Contents</div></label>
							<div class=" col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
							<input class="form-control" name="fwxjnr" id="fwxjnr" value="${erayFns:escapeStr(technicalFile.fwxjnr)}" disabled="disabled" >
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"  >
						<div class="font-size-12 line-height-18">机型工程师意见</div>
						<div class="font-size-9 line-height-18">Engineer Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8  padding-right-0">
							<textarea class="form-control" id="pgyj"   maxlength="150" disabled="disabled">${erayFns:escapeStr(technicalFile.pgyj)}</textarea>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
						　<label style="margin-right: 50px"></label> 　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">评估人　<label style="margin-right: 50px; font-weight:normal">${technicalFile.pgr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Evaluator</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">评估时间　<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${technicalFile.pgsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Evaluate time</div>
						</div>
					</div>
				
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18">技术室主任意见</div>
						<div class="font-size-9 line-height-18">Director Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
							<textarea class="form-control" id="shyj" maxlength="150">${erayFns:escapeStr(technicalFile.shyj)}</textarea>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
						<div class="font-size-12 line-height-18"></div>
						<div class="font-size-9 line-height-18"></div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
						　<label style="margin-right: 50px"></label>　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">审核人　<label style="margin-right: 50px; font-weight:normal">${technicalFile.shr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Reviewer</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">审核时间 　<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${technicalFile.shsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Review Time</div>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-top-8"  >
						<div class="font-size-12 line-height-18">生产经理或授权委托人意见</div>
						<div class="font-size-9 line-height-12">Manager/Authorized Opinion</div>
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8 padding-right-0">
							<textarea class="form-control" id="pfyj" disabled="disabled">${erayFns:escapeStr(technicalFile.pfyj)}</textarea>
					</div>
					
					<div class="clearfix"></div>
					
					<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 padding-top-8"  >
					</label>
					<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-top-8">
						　
						<div class="pull-left padding-right-10">
						<div class="font-size-12 line-height-18">批准人　<label style="margin-right: 50px; font-weight:normal">${technicalFile.pfr_user.displayName}</label></div>
						<div class="font-size-9 line-height-12">Appr. By</div>
						</div>
						<div class="pull-left">
						<div class="font-size-12 line-height-18">批准时间　<label style="margin-right: 50px; font-weight:normal"> <fmt:formatDate value='${technicalFile.pfsj}' pattern='yyyy-MM-dd HH:mm:ss ' /></label></div>
						<div class="font-size-9 line-height-12">Approved Time</div>
						</div>
					</div>
			
					<div class="clearfix"></div>
				
					<div class="col-lg-12 text-right padding-left-0 padding-right-0">
					  <button onclick="btnSave()" class="btn btn-primary padding-top-1 padding-bottom-1" ><div
										class="font-size-12">审核通过</div>
									<div class="font-size-9">Approved</div></button>
                   <button onclick="btnSave1()" class="btn btn-primary padding-top-1 padding-bottom-1 " ><div
						class="font-size-12">审核驳回</div>
					<div class="font-size-9">Rejected</div></button>
                    	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()"><div
									class="font-size-12">返回</div>
								<div class="font-size-9">Back</div></button>
                    </div>
				
                    
				<div class="clearfix"></div>
				
				
			</div>
			
				  
					
					
			
		</div>
	</div>
	</div>
	<link href="${ctx }/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
<script src="${ctx }/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/technicalfile/audit_technicalfile.js"></script>
</body>
</html>