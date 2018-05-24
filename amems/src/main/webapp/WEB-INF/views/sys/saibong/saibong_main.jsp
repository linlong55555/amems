<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
	<div class="clearfix"></div>
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
					<div class="panel-body padding-bottom-0">
						<!--------搜索框start-------->
						<div  class='searchContent margin-top-0 row-height' >
						<div class=" pull-right padding-left-0 padding-right-0 form-group" >
							<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
								<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">组织机构</div>
									<div class="font-size-9">Organization</div>
								</span>
							<div class=" col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode" class="form-control " name="dprtcode" onchange="load();">
									<c:if test="${dprtcode==user.orgcode}">
										<option value="-1">公共</option>
									</c:if>
									<c:forEach items="${accessDepartments}" var="type">
										<option value="${type.id}" >${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
									</select>
							</div>
						</div>	
						
					<div class=" pull-left padding-left-10 padding-right-0" style="width:250px;" >
						<input placeholder="采番key/说明" id="keyword_search" class="form-control " type="text">
					</div>
                    
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button id="cfgzSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                  
                    </div> 
                    
				</div>
				<!------------搜索框end------->
			
				<div class="clearfix"></div>
				</div>

				<div  class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height"  style="overflow-x:scroll">
					<table id="sqzz" class="table table-thin table-bordered table-set" id="borrow_return_outstock_table" style="min-width:1500px !important;">
						<thead>
							<tr>
								<th class=" colwidth-5"  style="vertical-align: middle;">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class=" colwidth-5" style="vertical-align: middle;">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-10"   >
									<div class="important">
										<div class="font-size-12 line-height-18">采番key</div>
										<div class="font-size-9 line-height-18">Picking key</div>
									</div>
								</th>
								<th class="colwidth-15">
									<div class="important">
										<div class="font-size-12 line-height-18">说明</div>
										<div class="font-size-9 line-height-18">Description</div>
									</div>
								</th>
								<th  class="colwidth-10">
									<div class="font-size-12 line-height-18">固定文字</div>
									<div class="font-size-9 line-height-18">Fixed Text</div>
								</th>
								<th  class="colwidth-10">
									<div class="font-size-12 line-height-18">固定文字顺序</div>
									<div class="font-size-9 line-height-18">Fixed Text Order</div>
								</th>
								<th  class="colwidth-10">
									<div class="font-size-12 line-height-18">日期格式</div>
									<div class="font-size-9 line-height-18">Date Format</div>
								</th>
								<th  class="colwidth-10">
								<div class="font-size-12 line-height-18">日期格式顺序</div>
									<div class="font-size-9 line-height-18">Date Format Order</div>
								</th>
								<th  class="colwidth-10">
									<div class="font-size-12 line-height-18">流水长度</div>
									<div class="font-size-9 line-height-18">Flow Length</div>
								</th>
								<th  class="colwidth-10">
									<div class="font-size-12 line-height-18">流水填补规则</div>
									<div class="font-size-9 line-height-18">Filling Rules</div>
								</th>
								<th  class="colwidth-10">
									<div class="font-size-12 line-height-18">流水顺序</div>
									<div class="font-size-9 line-height-18">Flow Order</div>
								</th>
								<th  class="colwidth-10">
									<div class="font-size-12 line-height-18">分隔符</div>
									<div class="font-size-9 line-height-18">Separator</div>
								</th>
							</tr>
						</thead>
						<tbody id="list">
						
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" class="form-control " id="id" name="id" />
	<!-- start新增修改提示框 -->
	<div class="modal fade" id="alertModaladdupdate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:650px!important;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18" id="accredit"></div>
							<div class="font-size-9 " id="accreditrec"></div>
						</div>
					<input type="hidden"  id="jgdm"/>
						<div class="panel-body padding-top-0 padding-bottom-0" >
							<form id="form1">
								<div class="col-lg-12  padding-left-0 margin-top-10 padding-right-0  ">
									<span class="pull-left col-lg-3 col-sm-3  col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">采番Key</div>
										<div class="font-size-9">Picking Key</div>
									</span>
									<div class="col-lg-9 col-sm-9 col-xs-8 padding-left-8 padding-right-0">
										<!-- <p  class="form-control" id="cfkey" name="cfkey"  disabled="disabled"></p> -->
										<input class="form-control" id="cfkey" name="cfkey" maxlength="20" type="text" disabled="disabled" />
									</div>
								</div>
							
							<div class="clearfix"></div>
							
								<div class="col-lg-12   padding-left-0 margin-top-10 padding-right-0  form-group">
								<span class="pull-left col-lg-3 col-sm-3  col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">说明</div>
									<div class="font-size-9">Description</div>
								</span>
								<div class="col-lg-9 col-sm-9 col-xs-9  padding-left-8 padding-right-0">
									<!-- <p class="form-control" id="cfsm"   maxlength="60"  disabled="disabled"></p> -->
									<input class="form-control" id="cfsm" name="cfsm" maxlength="20" type="text" disabled="disabled"/>
								</div>
							</div>
							
								<div class="clearfix"></div>
							<div  id="order1" class="col-lg-12  padding-left-0 margin-top-0 padding-right-0  ">
								
								<div class="col-lg-12  col-sm-12  col-xs-12 padding-left-0 margin-top-0 padding-right-0  form-group">
									<span class="pull-left col-lg-1 col-sm-1  col-xs-1 text-right padding-left-0 padding-right-0" id="gcfzcsx" name="gcfzcsx">
									</span>
									<span class="pull-left col-lg-2 col-sm-2  col-xs-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">固定文字</div>
										<div class="font-size-9">Fixed Text</div>
									</span>
									<div class="col-lg-9 col-sm-9  col-xs-9 padding-left-8 padding-right-0">
										<input class="form-control" id="ggdwz" name="ggdwz" maxlength="20" type="text"/>
										
									</div>
								</div>
							</div>
					  
							<div class="clearfix"></div>
							
							<div id="order2" class="col-lg-12   padding-left-0 margin-top-0 padding-right-0  form-group">
							
								<div class="col-lg-12  col-sm-12  col-xs-12  padding-left-0 margin-top-0 padding-right-0  ">
								<span class="pull-left col-lg-1 col-sm-1  col-xs-1 text-right padding-left-0 padding-right-0" id="tcfzcsx" name="tcfzcsx" >
								</span>
									<span class="pull-left col-lg-2 col-sm-2  col-xs-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">日期格式</div>
										<div class="font-size-9">Date Format</div>
									</span>
									<div class="col-lg-9 col-sm-9  col-xs-9 padding-left-8 padding-right-0">
										<input class="form-control" id="trqgs" name="trqgs" maxlength="20" type="text" />
									</div>
								</div>
								
							</div>
					  
							<div class="clearfix"></div>
							
							<div id="order3" class="col-lg-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-lg-1 text-right padding-left-0 padding-right-0" id="lcfzcsx" name="lcfzcsx" >
								</span>
								
								<div class="col-lg-6   padding-left-0 margin-top-0 padding-right-0  ">
									<span class="pull-left col-lg-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12"><span style="color: red">*</span>流水长度</div>
										<div class="font-size-9">Flow Length</div>
									</span>
									<div class="col-lg-8	 col-sm-10 padding-left-8 padding-right-0">
										<input class="form-control" id="llshcd" name="llshcd" maxlength="10" type="text"/>
									</div>
								</div>
								<div class="col-xs-5   padding-left-0 margin-top-0 padding-right-0  ">
									<span class="pull-left col-lg-6 text-right padding-left-0 padding-right-0">
										<div class="font-size-12"><span style="color: red">*</span>流水填补规则</div>
										<div class="font-size-9">Filling Rules</div>
									</span>
									<div class="col-lg-6 col-sm-10 padding-left-8 padding-right-0">
										<select class="form-control" id="ltbgz" name="ltbgz"  type="text">
											<option value="0">不填补</option>
											<option value="1">前补0</option>
											<option value="2">后补0</option>
										</select>
									</div>
								</div>
								
							</div>
							<div  id="order4" class="col-lg-12  padding-left-0 margin-top-0 padding-right-0  ">
								
								<div class="col-lg-12  col-sm-12  col-xs-12 padding-left-0 margin-top-0 padding-right-0  form-group">
									<span class="pull-left col-lg-1 col-sm-1  col-xs-1 text-right padding-left-0 padding-right-0" id="dtwzwzsx" name="dtwzwzsx">
									</span>
									<span class="pull-left col-lg-2 col-sm-2  col-xs-2 text-right padding-left-0 padding-right-0">
										<div class="font-size-12">动态文字位置</div>
										<div class="font-size-9">Dynamic position</div>
									</span>
									
									<div class="col-lg-9 col-sm-9  col-xs-9 padding-left-8 padding-right-0">
										<div class="input-group">
											<select class="form-control" id="dtwzwz" name="dtwzwz"  type="text">
											    <option value=""></option>
												<option value="1">固定文字</option>
												<option value="2">日期格式</option>
												<option value="3">流水</option>
											</select>
											<span class="input-group-addon " id="basic-addon2">之前</span>
										</div>
									</div>

								</div>
							</div>
							
					  		<div class="clearfix"></div>
							
							<div class="col-lg-12  padding-left-0 margin-top-0 padding-right-0  form-group">
								<span class="pull-left col-lg-3 col-sm-3  col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">分隔符</div>
									<div class="font-size-9">Separator</div>
								</span>
								<div class="col-lg-9 col-sm-9 col-xs-9  padding-left-8 padding-right-0">
									<input class="form-control" id="fgf"   maxlength="10" />
								</div>
							</div>
							
							<div class="clearfix"></div>
							
							<div class="col-lg-12  padding-left-0 margin-top-0 padding-right-0  ">
								<span class="pull-left col-lg-3 col-sm-3  col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">实例</div>
									<div class="font-size-9">Examples</div>
								</span>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
									<p  class="form-control" style="color: green;" id="lshyl" name="lshyl" disabled="disabled" ></p>
								</div>
							</div>
							
							<div class="clearfix"></div>
								
					     	<div class="text-center margin-top-0 padding-buttom-10 ">
					     	
                     		<button type="button" class="pull-right btn btn-primary padding-top-1 margin-right-0 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
							</button>
							<button id="baocuns" type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="saveUpdate()">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
	                   		</button>
	                   		<button id="baocuns" type="button" class="pull-right btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="preview()">
									<div class="font-size-12">预览</div>
									<div class="font-size-9">Preview</div>
	                   		</button>
                   		</div>
					</form>
				 </div> 
			</div>
			
			<div class="clearfix"></div>
			
			</div>
		</div>
	</div>
</div>
<!-- end新增修改提示框-->
	
<script type="text/javascript" src="${ctx}/static/js/thjw/sys/saibong/saibong_main.js"></script>
</body>
</html>