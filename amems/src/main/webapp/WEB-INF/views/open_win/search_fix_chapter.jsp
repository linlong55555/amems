<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/search_fix_chapter.js"></script>
<div class="modal fade in modal-new" id="FixChapterModal" tabindex="-1" role="dialog"  aria-labelledby="FixChapterModal" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 45%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">ATA章节号列表</div>
						<div class="font-size-9">List of ATA</div>
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
								<input type="text" placeholder='ATA章节号/英文描述/中文描述' id="keyword_fix_chapter_search" class="form-control" />
							</div>
		                   	<div class=" pull-right padding-left-5 padding-right-0 ">   
							<button name="keyCodeSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="FixChapterModal.search()">
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
						<div class='table-content col-xs-12 padding-left-0 padding-right-0' id="searchTable">
							<table id="open_win_chapter_basic_table2" class="table table-thin table-bordered table-striped table-hover table-set" >
								<thead>
							   		<tr>
							   			<th class="colwidth-7" >
											<div class="font-size-12 line-height-18">选择</div>
											<div class="font-size-9 line-height-18">Choice</div>
										</th>
										<th class="colwidth-7">
											<div class="important">
												<div class="font-size-12 line-height-18">ATA章节号</div>
												<div class="font-size-9 line-height-18">ATA</div>
											</div>
										</th>
										<th>
											<div class="important">
												<div class="font-size-12 line-height-18">英文描述</div>
												<div class="font-size-9 line-height-18">English Desc</div>
											</div>
										</th>
										<th class="colwidth-20">
											<div class="important">
												<div class="font-size-12 line-height-18">中文描述</div>
												<div class="font-size-9 line-height-18">Chinese Desc</div>
											</div>
										</th>
							 		 </tr>
								</thead>
								<tbody id="fixChapterList">
								</tbody>
							</table>
						</div>
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
	                     	<button type="button" onclick="FixChapterModal.setData()" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button id="zjh_btn_clear" type="button" onclick="FixChapterModal.clearZjh()" class="btn btn-primary padding-top-1 padding-bottom-1" >
								<div class="font-size-12">清空</div>
								<div class="font-size-9">Clear</div>
							</button>
	                    	<button type="button" onclick="FixChapterModal.close()" class="btn btn-primary padding-top-1 padding-bottom-1" >
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