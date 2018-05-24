<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/choosePlane.js"></script>
<div class="modal fade modal-new" id="choosePlaneModal" tabindex="-1" role="dialog"  aria-labelledby="choosePlaneModal" aria-hidden="true" data-keyboard="false" data-backdrop="static"
>
      <div class="modal-dialog">
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-12 ">飞机列表</div>
							<div class="font-size-9">Plane List</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" style='padding-top:0px;'>
              	<div class="input-group-border" style='margin-top:8px;padding-top:5px;margin-bottom:8px;'>
              		<div style="overflow-x:auto;" class='padding-left-right-8 table-content' id='searchTable' >
								<table class="table table-thin table-striped table-hover table-set" style='margin-bottom:0px !important'>
									<thead>
								   		<tr>
								   			<th  class="selectAllImg" id="checkAll" style='text-align:center;vertical-align:middle;width:60px;'>
												<a href="#" onclick="SelectUtil.performSelectAll('choosePlaneModal_list')" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
												<a href="#" class="margin-left-5" onclick="SelectUtil.performSelectClear('choosePlaneModal_list')" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
											</th>
											<th class="colwidth-7">
													<div class="font-size-12 line-height-12">飞机注册号</div>
													<div class="font-size-9 line-height-12">A/C Reg</div>
											</th>
											<th class="colwidth-7">
													<div class="font-size-12 line-height-12">MSN</div>
													<div class="font-size-9 line-height-12">MSN</div>
											</th>
											<th class="colwidth-7">
													<div class="font-size-12 line-height-12">备注名</div>
													<div class="font-size-9 line-height-12">Note</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="choosePlaneModal_list">
									
								    </tbody>
								</table>
							</div>
               	</div>
               	<div class="col-xs-12 text-center  padding-right-0 page-height " id="choosePlaneModal_pagination">
                <div class="clearfix"></div>              
           </div>
            <div class="clearfix"></div>  
           </div>
            <div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8" >
				<div class="input-group">
				<span class="input-group-addon modalfootertip" >
	                   <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
				</span>
                    <span class="input-group-addon modalfooterbtn">
                      <!-- <label class='margin-right-5 label-input' ><input type='checkbox' name='qbsy' checked="checked"/>&nbsp;全部适用</label> -->
                      <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="choosePlaneModal.save()" >
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
	                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
					    </button>
                    </span>
               	</div><!-- /input-group -->
			</div>
			</div>
          </div>
	</div>
<!--  弹出框结束-->