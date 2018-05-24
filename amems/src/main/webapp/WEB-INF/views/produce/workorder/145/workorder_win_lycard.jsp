<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/145/workorder_lycardwin.js"></script>
<div class="modal fade in modal-new" id="workorder145_lycard_Modal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="open_win_evaluation_modal" aria-hidden="true">
	<div class="modal-dialog" style="width: 60%;">
		<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                        <div class="font-size-12 " id="modalHeadCN">EO</div>
							<div class="font-size-9" id="modalHeadENG">Evaluation List</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
			<div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
		             <!-- start:搜索框 -->
			   		<div class=" pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch">
			   		         <div class="pull-left padding-left-0 padding-right-0 " style='margin-right:8px;'>
								<span class="pull-left text-right padding-left-0 padding-right-0" style='margin-right:8px;'>
									<div class="font-size-12">工卡类型</div>
									<div class="font-size-9">Type</div>
								</span>
								<div class="pull-left padding-right-0" style='width:200px;'>
									<select class='form-control' id='gklx_search'></select>
								</div>
								<div class='clearfix'></div>
							</div>	
							<!-- 搜索框start -->
							<div class=" pull-right padding-left-0 padding-right-0">
								<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
									<input type="text" placeholder='工卡编号/标题' id="workorder145_lycard_keyword_search" class="form-control" />
								</div>
			                    <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="Workorder145LycardWin.doSearch()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                    </div> 
							</div>
							<!-- 搜索框end -->
					 </div>
		            <!-- end:搜索框 -->
		            	
			        <div class="clearfix"></div>
			         		
					<!-- start:table -->
					<div class="margin-left-8 margin-right-8" style='margin-top:5px;'>
						<div style="overflow-x:auto;" class='table-content'>
							<table class="table table-bordered table-thin table-striped table-hover table-set" style="min-width: 1000px !important">
								<thead>
							   		<tr>
							   		    <th class="colwidth-7" id="checkSingle">
											<div class="font-size-12 line-height-18">操作</div>
											<div class="font-size-9 line-height-18">Operation</div>
										</th>
										<th id="checkAll" style='text-align:center;vertical-align:middle;width:70px;'>
											<a href="#" onclick="SelectUtil.performSelectAll('workorder145_lynrc_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
											<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('workorder145_lycard_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
										</th>
										<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >工卡编号</div><div class="font-size-9 line-height-12" >Work Card No.</div></th>
										<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >ATA章节号</div><div class="font-size-9 line-height-12" >ATA</div></th>
										<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >标题</div><div class="font-size-9 line-height-12" >Title</div></th>
										<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >工卡类型</div><div class="font-size-9 line-height-12" >Type</div></th>
										<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >专业</div><div class="font-size-9 line-height-12" >Skill</div></th>
										<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >区域</div><div class="font-size-9 line-height-12" >Zone</div></th>
										<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >接近</div><div class="font-size-9 line-height-12" >Access</div></th>
										<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >飞机站位</div><div class="font-size-9 line-height-12" >Aircraft Stations</div></th>
										<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >工作类别</div><div class="font-size-9 line-height-12" >Category</div></th>
										<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >工作组</div><div class="font-size-9 line-height-12" >Work Group</div></th>
										<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >工时</div><div class="font-size-9 line-height-12" >MHRs</div></th>
							 		 </tr>
								</thead>
								<tbody id="workorder145_lycard_list">
								
								</tbody>
							</table>
						</div>
						<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="workorder145_lycard_pagination"></div>
					</div>
					<!-- end:table -->
					 
				</div>
				<div class="clearfix"></div>  
			</div>
			<div class="clearfix"></div>  
		   
		    <!-- start:底部按钮 -->
		    <div class="modal-footer">
		           	<div class="col-xs-12 padding-leftright-8"  >
		           		<div class="input-group">
							<span class="input-group-addon modalfootertip" >
				                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
							</span>
							<span class="input-group-addon modalfooterbtn">
		                       <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="Workorder145LycardWin.save();">
									<div class="font-size-12">确定</div>
									<div class="font-size-9">Confirm</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="Workorder145LycardWin.clear();">
									<div class="font-size-12">清空</div>
									<div class="font-size-9">Clear</div>
								</button>
			                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
							    </button>
		                    </span>
		               	</div>
					</div>
			</div>
            <!-- end:底部按钮 -->
               	
		</div>
	</div>
</div>