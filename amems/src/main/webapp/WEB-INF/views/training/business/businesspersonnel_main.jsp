<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
				<input type="hidden" id="manidinfo" />
				<input type="hidden" id="dprtId" />
				<input type="hidden" id="fjzchid" />
				<div class="panel panel-default col-xs-12 padding-left-0 padding-right-0" id="status_main_rightSecondDiv">
				<div class="panel-heading bg-panel-heading"  >
			     <div class='pull-left'>
			     <div class="font-size-12 line-height-12">人员信息</div>
					<div class="font-size-9 line-height-9">Information</div>
			     </div>
				     
				     <div id="hideIcon" class="pull-right" style='height:1px;padding-right:8px;margin-top:-2px;'>
							<img src="${ctx}/static/images/down.png" onclick='hideBottom()' style="width:33px;cursor:pointer;">
					</div> 
					<div class='clearfix'></div>
				</div>
				<div class="panel-body padding-bottom-0">
				<!-- 搜索框start -->
				<button type="button" class="btn btn-primary padding-top-1 margin-right-10 padding-bottom-1 row-height pull-left checkPermission bottom-hidden-button"
					permissioncode='training:business:manage:04'	onclick='openPersonelWin()'>
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
				</button>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-10 bottom-hidden-table-content"
					style='overflow:auto;'>
					<table
						class="table table-thin table-bordered table-striped text-center table-set" id="flight_record_sheet_table">
						<thead>
							<tr>
								<th class="fixed-column colwidth-5">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-5 " >
									<div class="font-size-12 line-height-18">人员编号</div>
									<div class="font-size-9 line-height-18">Flight No.</div></th>
								<th class="colwidth-25 ">									
									<div class="font-size-12 line-height-18">人员名字</div>
									<div class="font-size-9 line-height-18">Date</div>
								</th>
								<th class="colwidth-10 ">									
									<div class="font-size-12 line-height-18">机型</div>
									<div class="font-size-9 line-height-18">A/C Type</div>
								</th>
								<th class="colwidth-10 ">									
									<div class="font-size-12 line-height-18">类别</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-10 ">									
									<div class="font-size-12 line-height-18">开始有效期</div>
									<div class="font-size-9 line-height-18">Start Validity</div>
								</th>
								<th class="colwidth-10 ">									
									<div class="font-size-12 line-height-18">截止有效期</div>
									<div class="font-size-9 line-height-18">End Validity</div>
								</th>
								<th class="colwidth-5 " >
									<div class="font-size-12 line-height-18">部门</div>
									<div class="font-size-9 line-height-18">Measures</div>
								</th>
							</tr>
						</thead>
						<tbody id='dcgzcl_list'>

						</tbody>

					</table>
				</div>

				<div class=" col-xs-12  text-center page-height padding-left-0 padding-right-0"  id="fjgzjkInfo_pagination"></div>
				<div class="clearfix"></div>
			
			</div>
			</div>
			<!-- 新增单次故障处理 -->
			<div class="modal fade" id="alertModalDcgz" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-dialog"  >
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
						
								<div class="font-size-16 line-height-18" id="accredit1"></div>
							<div class="font-size-9 " id="accreditrec1"></div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
			            	<div class="col-lg-12 col-xs-12 col-sm-12">
			            	<input type="hidden" id="infoId" />
			            	<form id="form">
			            	<div class="col-xs-12 col-sm-12 col-lg-12 padding-right-0 padding-left-0 margin-top-10  form-group">
								<label class="pull-left col-lg-2 col-xs-2 col-sm-2 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12"><span style="color:red">*</span>维修档案人员</div>
									<div class="font-size-9">Flight No.</div>
								</label>
								<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
									<input type='text' id="hbh" name="hbh" class='col-lg-8 form-control text-left' style="width:90%"/>
									<input type="hidden" id="fxjldid" value=""/>
									<button type="button" class="btn btn-primary form-control"  style="width:10%"
										 data-toggle="modal"
										onclick="openHb()">
										<i class="icon-search cursor-pointer"></i>
									</button>
								</div>
							</div> 


			
	
									</form>    
					     		<div class="clearfix"></div>
					     		<div class="text-right margin-top-10 padding-buttom-10 ">
                               <button onclick="saveinfo()" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" ><div
										class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div></button>
                     	      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
                    			 </div><br/>
						 	 </div>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/personel_tree_multi_business.jsp"%><!-------用户对话框 -------->
	<!-- 新增单次故障处理 -->
	<script type="text/javascript" src="${ctx}/static/js/thjw/training/business/businesspersonnel_main.js"></script>
		