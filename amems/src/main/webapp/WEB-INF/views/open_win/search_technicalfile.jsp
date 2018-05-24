<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/search_technicalfile.js"></script>
<div class="modal fade in modal-new" id="TechnicalfileModal" tabindex="-1" role="dialog"  aria-labelledby="TechnicalfileModal" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 60%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">适航性资料列表</div>
						<div class="font-size-9">Airworthiness</div>
				 	</div>
 					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
           	<div class="modal-body" style='padding-top:0px;'>
             	<div class="input-group-border" style='margin-top:8px;padding-top:5px;'>
                   	<div class=" pull-right padding-left-0 padding-right-8 margin-top-0 modalSearch">	
       		
						<!-- 搜索框start -->
						<div class=" pull-right padding-left-0 padding-right-0">
							<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
								<input type="text" placeholder='文件编号/主题'  class="form-control" id="keyword_technicalfile_search" >
							</div>
		                   	<div class=" pull-right padding-left-5 padding-right-0 ">   
							<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="TechnicalfileModal.search()">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                  	</div> 
						</div>
					<!-- 搜索框end -->
					</div>
	            	
		         	<div class="clearfix"></div>
		         		
					<!-- start:table -->
					<div class="margin-left-8 margin-right-8" style='margin-top:5px;margin-bottom:5px;'>
						<div class='col-xs-12 padding-left-0 padding-right-0 table-content' id="searchTable">
							<table  class="table table-thin table-bordered table-striped table-hover table-set" >
								<thead>
								   		<tr>
											<th class=" colwidth-5">
												<div class="font-size-12 line-height-18">选择</div>
												<div class="font-size-9 line-height-18">Choice</div>
											</th>
											<th class="colwidth-7 ">
												<div class="font-size-12 line-height-18">文件类型</div>
												<div class="font-size-9 line-height-18">Doc. type</div>
											</th>
											<th class="colwidth-7 ">
												<div class="font-size-12 line-height-18">来源</div>
												<div class="font-size-9 line-height-18">Issued By</div>
											</th>
											<th class="colwidth-10 ">
												<div class="important">
													<div class="font-size-12 line-height-18">文件编号</div>
													<div class="font-size-9 line-height-18">Doc. No.</div>
												</div>
											</th>
											<th class="colwidth-5 ">
												<div class="font-size-12 line-height-18">版本</div>
												<div class="font-size-9 line-height-18">Rev</div>
											</th>
											<th class="colwidth-10 ">
												<div class="font-size-12 line-height-18">修正案号</div>
												<div class="font-size-9 line-height-18">Amendment</div>
											</th>
											<th class="colwidth-30 ">
												<div class="important">
													<div class="font-size-12 line-height-18">主题</div>
													<div class="font-size-9 line-height-18">Subject</div>
												</div>
											</th>
											<th class="colwidth-10" >
												<div class="font-size-12 line-height-18">文件</div>
												<div class="font-size-9 line-height-18">File</div>
											</th>
											<th class="colwidth-10 " >
												<div class="font-size-12 line-height-18">ATA章节号</div>
												<div class="font-size-9 line-height-18">ATA No.</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="technicalfileList">
									
									</tbody>
							</table>
						</div>
						<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="technicalfilePagination"></div>
						
						<div class="clearfix"></div>  
					</div>
				</div>
               <div class="clearfix"></div>              
          </div>
          <div class="clearfix"></div>  
          <div class="modal-footer">
          		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
	                     	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="TechnicalfileModal.setData()" data-dismiss="modal">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button id="jswj_btn_clear" type="button" onclick="TechnicalfileModal.clearJswj()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">清空</div>
								<div class="font-size-9">Clear</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
	                   	</span>
              		</div><!-- /input-group -->
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->