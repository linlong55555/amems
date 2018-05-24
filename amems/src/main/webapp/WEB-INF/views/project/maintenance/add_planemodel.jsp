<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<script>
	var pageParam = '${param.pageParam}';
	</script>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<style>
	      td:hover {
	        background-color: rgb(168,213,252);
	        cursor: default;
	      }
	</style>
</head>
<body class="page-header-fixed">
		<input maxlength="20"  type="hidden" id="isScheduler" value="${isScheduler }"/>
		<input maxlength="20"  type="hidden" id="isDuration" value="${isDuration }"/>
		<input maxlength="20"  type="hidden" id="isActualhours" value="${isActualhours }"/>
		<!-------导航Start--->
<div class="page-content">
			<!-- from start -->
<div class="panel panel-primary">
  <div class="panel-heading" id="NavigationBar"></div>
		<div class="panel-body">
			    <div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 padding-left-0" >
			    <div class="panel panel-default">
			        <div class="panel-heading">
						 <h3 class="panel-title">飞机机型 Model</h3>
				   </div>
				<div class="panel-body">
		         <form id="form">
					<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0">
					
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0"  >
							<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18"><span style="color: red">*</span>飞机型号</div>
								<div class="font-size-9 line-height-18">Model</div></label>
								<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
								<input  type="text"  id="fjjx"  name="fjjx" class="form-control"  >
							</div>
						</div>
					</form>	
					
					<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0"  >
						<label  class="col-xs-4 text-right padding-left-0 padding-right-0"><div
								class="font-size-12 line-height-18">定检计划公式</div>
							<div class="font-size-9 line-height-18">C/M</div>
						</label>
						<div class=" col-xs-8 padding-left-8 padding-right-0 form-group">
							<select id="gsDjjh" class="form-control">
							       <option  value="1">(计划&实际)取小+周期</option>
							       <option value="2">实际+周期</option>
							       <option value="3">计划+周期</option>
							</select>
						</div>	
					</div>
					
					<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
						<label class="col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 line-height-18">特殊情况</div>
							<div class="font-size-9 line-height-18">S/C</div>
						</label>
						<div class="col-xs-8  padding-left-8 padding-right-0 form-group">
							<label style="margin-right: 20px;font-weight: normal"><input name="isTsqk" type="radio" value="1" checked/>有</label> 
							<label style="font-weight: normal"><input name="isTsqk" type="radio" value="0" />无 </label> 
						</div>
					</div>
				</div>
			</div>
				</div>		
					<div class="panel panel-default">
					        <div class="panel-heading">
								    <h3 class="panel-title">日使用量设置 Setting&nbsp;&nbsp;<small class="text-muted">(说明：输入0或空时默认为1)</small></h3>
						   </div>
					<div class="panel-body">
					  <form id="form2">
				       <div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0" >
				       
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18">机身飞行时间</div>
									<div class="font-size-9 line-height-18">Flight HRS.</div></label>
								 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
									<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' id="rJsfxsj" name="rJsfxsj" >
								</div>
							</div>
							
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18">搜索灯时间</div>
									<div class="font-size-9 line-height-18">Search Time</div></label>
								 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
									<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' id="rSsdsj"  name="rSsdsj">
								</div>
							</div>
							
							<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
								<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18">绞车时间</div>
									<div class="font-size-9 line-height-18">Winch Time</div></label>
								 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
									<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' id="rJcsj"   name="rJcsj">
								</div>
							</div>
							
					      <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
										class="font-size-12 line-height-18">起落循环</div>
									<div class="font-size-9 line-height-18">Flight CYCS.</div></label>
								 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
									<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' id="rQljxh" name="rQljxh" >
								</div>
							</div>
						 </div>	
						 
				     <div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0" >	  
				     
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">绞车循环</div>
								<div class="font-size-9 line-height-18">Winch CYCS.</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' id="rJcxh" name="rJcxh" >
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
						
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">外吊挂循环</div>
								<div class="font-size-9 line-height-18">E/S CYCS.</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' id="rWdgxh" name="rWdgxh">
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
						
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N1</div>
								<div class="font-size-9 line-height-18">N1</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' id="rN1" name="rN1">
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N2</div>
								<div class="font-size-9 line-height-18">N2</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' id="rN2" name="rN2">
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N3</div>
								<div class="font-size-9 line-height-18">N3</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' id="rN3" name="rN3" >
							</div>
						</div>
						
						 <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N4</div>
								<div class="font-size-9 line-height-18">N4</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' id="rN4" name="rN4" >
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N5</div>
								<div class="font-size-9 line-height-18">N5</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input maxlength="4"  type="text" class="form-control "onkeyup='clearNoNum(this)' name="rN5" id="rN5" >
							</div>
						</div>
						
					  <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">N6</div>
								<div class="font-size-9 line-height-18">N6</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' name="rN6"  id="rN6">
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0" >
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">特殊监控1</div>
								<div class="font-size-9 line-height-18">TS1</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input maxlength="4"  type="text" class="form-control " onkeyup='clearNoNum(this)' name="rTsjk1" id="rTsjk1">
							</div>
						</div>
						
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-xs-4 text-right padding-left-0 padding-right-0"><div
									class="font-size-12 line-height-18">特殊监控2</div>
								<div class="font-size-9 line-height-18">TS2</div></label>
							 <div class="col-xs-8 padding-left-8 padding-right-0 form-group" >
								<input maxlength="4"  type="text" class="form-control "onkeyup='clearNoNum(this)' name="rTsjk2"  id="rTsjk2" >
							</div>
						</div>
					</div>
					</form>
					</div>
				</div>		
					<div class="clearfix"></div>
					<div class="panel panel-default">
					        <div class="panel-heading">
								    <h3 class="panel-title">关联部件 Related P/N</h3>
						   </div>
					<div class="panel-body">	 	   
			  <div class="col-lg-12   padding-right-0" style="overflow-x: auto;">
					<table class="table-set table table-bordered table-striped table-hover text-center table-thin"   id="addtr" style="min-width:900px">
						<thead>
							<tr>
							    <th class="colwidth-3">
							         <button onclick="addTr()" class="line6 ">
								       <i class="icon-plus cursor-pointer color-blue cursor-pointer'" ></i>
							        </button>
							    </th>	
								<th class="colwidth-13"><div class="font-size-12 line-height-18">飞机部件号</div>
								<div class="font-size-9 line-height-16">P/N</div></th>
								<th class="colwidth-15"><div class="font-size-12 line-height-18">英文名称</div>
								<div class="font-size-9 line-height-16">F.Name</div></th>
								<th class="colwidth-13"><div class="font-size-12 line-height-18">中文名称</div>
								<div class="font-size-9 line-height-16">CH.Name</div></th>
								<th class="colwidth-20"><div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-16">Remark</div></th>
							</tr>
						 </thead>
						 <tbody  id="CKlist">
						 	<tr class='text-center' id="zwsj"><td colspan="5">暂无数据 No data.</td></tr>
						 </tbody>     
					</table>
			   </div>
			  </div>
			 </div>  
			</div>
			
			
			<div style="display: none"><input maxlength="20"  id="planedataId"  type="text"  name="menuId"/> </div>
               
            <div class="clearfix"></div>
               
            <div class="col-lg-12 text-right padding-left-0 padding-right-0" style="margin-bottom: 10px">
                    <button onclick="savePlaneModelData()" class="btn btn-primary padding-top-1 padding-bottom-1" ><div
									class="font-size-12">保存</div>
								<div class="font-size-9">Save</div></button>
                   	<button class="btn btn-primary padding-top-1 padding-bottom-1" onclick="back()"><div
								class="font-size-12">返回</div>
							<div class="font-size-9">Back</div></button>
            </div>
		</div>
	</div>

<!-- 航材耗材的模态框 -->

<div class="modal fade" id="alertModalHcxx" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:50%">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalUserBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">关联部件数据</div>
						<div class="font-size-9 ">Related Parts</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
				         <div class=" pull-right padding-left-0 padding-right-0 margin-top-10">
				         
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder="部件号/中英文" id="keyword_search" class="form-control ">
							</div>
							
		                    <div class=" pull-right padding-left-5 padding-right-0 ">   
								<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
		                   		</button>
		                    </div> 
						</div>    
			           <div class="clearfix"></div>

							<!-- start:table -->
							<div style="overflow-x: auto;margin-top: 10px">
								<table class="table-set table table-thin table-bordered table-striped table-hover " style="min-width:900px">
									<thead>
										<tr>
											<th class="colwidth-3"><div class="font-size-12 line-height-18">选择</div>
												<div class="font-size-9 line-height-18">Choice</div></th>
											<th class="colwidth-11">
												<div class="important">
													<div class="font-size-12 line-height-18">部件号</div>
													<div class="font-size-9 line-height-16">P/N</div>
												</div>
											</th>
											<th class="colwidth-12">
												<div class="important">
													<div class="font-size-12 line-height-18">英文名称</div>
													<div class="font-size-9 line-height-16">F.Name</div>
												</div>
											</th>
											<th class="colwidth-12">
												<div class="important">
													<div class="font-size-12 line-height-18">中文名称</div>
													<div class="font-size-9 line-height-16">CH.Name</div>
												</div>
											</th>
										</tr>
									</thead>
									<tbody id="Hcxxlist">
									</tbody>
								</table>
							</div>
							<div class="col-xs-12 text-center" id="pagination3">
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="appendHcxx()"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
								
			                </div>
				     		<div class="clearfix"></div>
					 	 </div>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
<!-------航材耗材 End-------->
<script src="${ctx}/static/js/workdetail.js"></script>
<%@ include file="/WEB-INF/views/alert.jsp"%>
 <script type="text/javascript" src="${ctx}/static/js/thjw/project/maintenance/add_planemodel.js"></script> 
</body>
</html>
