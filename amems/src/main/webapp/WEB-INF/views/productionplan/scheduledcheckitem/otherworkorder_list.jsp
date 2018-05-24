<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

<div  class="tab-pane fade in active" style="padding-left:15px;padding-right:15px;" id="planeLoadingList" >		
							<div class="col-xs-12 padding-left-0 padding-right-0 ">
					
						 <div class="col-lg-2 col-xs-4 padding-left-0 padding-right-0">
								<div class="col-xs-2 col-md-2 padding-left-0 row-height  row-height">
									<button type="button" onclick="otherOutExcel();"
										class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
										<div class="font-size-12">导出</div>
										<div class="font-size-9">Export</div>
									</button>
								</div>
							</div>  
					
					<!--------搜索框start-------->
				<div class=" pull-right padding-left-0 padding-right-0" >
					<div class=" pull-left padding-left-0 padding-right-0" style="width:250px;" >
						<input placeholder="工单单号/件号/序列号/工单原因/备注" id="keyword_search2" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision2();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div> 
				</div>
				<!------------搜索框end------->
			</div>

				<div class="clearfix"></div>
					<div class=" col-xs-12 text-center margin-bottom-5 padding-left-0 padding-right-0 table-h" style="overflow-x:scroll "> 
						<table id="qtgd" class="table table-thin table-bordered   text-center table-set" style="min-width:2300px !important">
							<thead>
								<tr>
									<th class="fixed-column colwidth-5" style="vertical-align: middle;"><div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div></th>
									<th class=" colwidth-5" ><div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div></th>
									<th class=" sorting colwidth-15"  name="column_gdbh" onclick="orderBy2('gdbh',this)" ><div class="important"><div class="font-size-12 line-height-18">工单单号</div></div>
									<div class="font-size-9 line-height-18">Work Order No.</div></th>
									<th class="colwidth-8"><div class="font-size-12 line-height-18">工单类型</div>
									<div class="font-size-9 line-height-18">Work Order Type</div></th>
									<th  class="sorting colwidth-8" name="column_bjxlh" onclick="orderBy2('bjxlh',this)"><div class="important"><div class="font-size-12 line-height-18">序列号</div></div>
									<div  class="font-size-9 line-height-18">S/N</div></th>
									<th class="colwidth-15" id="jihuas_order"><div class="font-size-12 line-height-18">计划</div>
									<div class="font-size-9 line-height-18">Plan</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">剩余</div>
									<div class="font-size-9 line-height-18">Remain</div></th>
									<th class="colwidth-8"><div class="font-size-12 line-height-18">剩余(天)</div>
									<div class="font-size-9 line-height-18">Remain(Day)</div></th>
									<th class="colwidth-30"><div class="font-size-12 line-height-18">任务单号</div>
									<div class="font-size-9 line-height-18">Task No.</div></th>
									<th  class="colwidth-30"><div class="important"><div class="font-size-12 line-height-18">备注</div></div>
									<div class="font-size-9 line-height-18">Remark</div></th>
									<th class="colwidth-15" ><div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C REG</div></th>
									<th class="sorting colwidth-15" name="column_bjh" onclick="orderBy2('bjh',this)"><div class="important"><div class="font-size-12 line-height-18">件号</div></div>
									<div class="font-size-9 line-height-18">P/N</div></th>
									<th class="sorting colwidth-30" name="column_gdms" onclick="orderBy2('gdms',this)"><div class="important"><div class="font-size-12 line-height-18">下发工单原因</div></div>
									<div class="font-size-9 line-height-18">Reason For W/O</div></th>
									<th class="colwidth-15"><div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div></th>
								</tr>
							</thead>
							<tbody id="list2">
							</tbody>
						</table>
						</div>
					</div>
	<!-- start编辑备注提示框 -->
	<div class="modal fade" id="alertModalview2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:50%;">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
					<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">编辑监控备注</div>
							<div class="font-size-9 ">Edit Monitoring Remark</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0" >
							<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-4 col-xs-2 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">工单单号</div>
									<div class="font-size-9">Work Order No.</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-10  padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="gdbh" name="gdbh" disabled="disabled"/>
								</div>
							</div> 
							<div class="col-xs-12 col-sm-6 col-lg-6  padding-left-0 margin-top-10 padding-right-0 margin-bottom-0 ">
								<span class="pull-left col-lg-4 col-xs-2 col-sm-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">工单类型</div>
									<div class="font-size-9">Work Order Type</div>
								</span>
								<div class="col-lg-8 col-sm-8 col-xs-10 padding-left-8 padding-right-0">
									<input type="text" class="form-control " id="gdlx" name="gdlx" disabled="disabled"/>
								</div>
							</div> 
						
							<div class="col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
								<span class="pull-left col-xs-2 col-sm-2 col-lg-2 text-right padding-left-0 padding-right-0">
									<div class="font-size-12">监控备注</div>
									<div class="font-size-9">Monitoring Remark</div>
									<input  type="hidden" id="jkid2" />
									<input  type="hidden" id="gddl" />
									<input  type="hidden" id="gdbh" />
								</span>
								<div class="col-xs-10 col-sm-10 col-lg-10 padding-left-8 padding-right-0">
									<textarea class="form-control" id="jkbz2" name="jkbz2"   maxlength="300" placeholder='长度最大为300'>  </textarea>
								</div>
							</div> 
							
					     	<div class="text-center margin-top-10 padding-buttom-10 ">
					     		<button type="button" class="pull-right btn btn-primary margin-right-0 padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
					     		<button type="button" class="pull-right btn btn-primary margin-right-10 padding-top-1 margin-right-10 padding-bottom-1 margin-bottom-10" onclick="sbDown2()">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
		                   		</button>
                     			
                    		</div><br/>
						 </div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- end编辑备注提示框 -->
<script type="text/javascript"
		src="${ctx}/static/js/thjw/productionplan/scheduledcheckitem/otherworkorder_list.js"></script>