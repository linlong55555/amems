<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="Hc_List_Modal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
   <div class="modal-dialog" style='width:80%;'>
       <div class="modal-content">
           <div class="modal-header modal-header-new" >
                <h4 class="modal-title" >
                   <div class='pull-left'>
                  	  <div class="font-size-14 " id="Hc_List_Modal_modalName"></div>
					  <div class="font-size-12" id="Hc_List_Modal_modalEname"></div>
					  <input type="hidden"  id="mark">
				   </div>
				   <div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				   </div>
				   <div class='clearfix'></div>
                 </h4>
            </div>
            <div class="modal-body" >
              	<div class="col-xs-12  ">
              	<div class="input-group-border margin-top-8 margin-bottom-8">
            		<div class="panel-body padding-bottom-0 padding-left-8 padding-right-8  padding-top-8">	  
	   			 <!-- 搜索框 -->
			    <div  class='searchContent margin-top-0' >
			    <!-- 上传按钮  -->
				<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group  ">
				</div>
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">缺件</div>
							<div class="font-size-9">Short Item</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select id="isWarning_search" class='form-control' >
								<option value="" >显示全部All</option>
								<option value="1" >是</option>
								<option value="0" >否</option>
						    </select>
						</div>
				</div>
				<div class=" col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">仓库</div>
							<div class="font-size-9">Storehouse</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
							<select id="ck_search" class='form-control' multiple='multiple' >
								<option value="" >显示全部All</option>
						    </select>
						</div>
				</div> 
				<!-- 关键字搜索 -->
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='件号/型号/名称/件号来源' class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-search-nomore" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 row-height" onclick="Hc_List_Modal.search();"  style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
				</div>
				</div>
				<div class='clearfix'></div>
			</div>
		<div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-0">
        	<ul id="myTab" class="nav nav-tabs tabNew-style">
               	<li class="active">
               		<a id="material_tab" onclick="Hc_List_Modal.checkedMaterial()" href="#materialTab" data-toggle="tab" aria-expanded="false" >
               			<div class="font-size-12 line-height-12">航材</div>
              			<div class="font-size-9 line-height-9">material</div>
               		</a>
               	</li>
                <li class="" >
					<a id='tool_tab' onclick="Hc_List_Modal.checkedTool()" href='#toolTab' data-toggle="tab"  >
						<div class="font-size-12 line-height-12">工具设备</div>
                   		<div class="font-size-9 line-height-9">Tool</div>
                	</a>
				</li>
           	</ul>
            <div id="myTabContent" class="tab-content margin-bottom-8">
             	<div class="tab-pane active" id="materialTab"  >
             		<div id="material_detail_list_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto">
						<table id="material_detail_list_table" class="table table-thin table-bordered table-striped table-hover  text-left table-set" style="min-width: 950px;">
							<thead id="material_detail_list_thead">
							</thead>
							<tbody id="material_detail_list_tbody">
							</tbody>
						</table>
					</div>
             	</div>
             	<div class="tab-pane fade " id="toolTab" >
             		<div id="tool_detail_list_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto">
						<table id="tool_detail_list_table" class="table table-thin table-bordered table-striped table-hover  text-left table-set" style="min-width: 950px;">
							<thead id="tool_detail_list_thead">
							</thead>
							<tbody id="tool_detail_list_tbody">
							</tbody>
						</table>
					</div>	
             	</div>
           </div>
                 
        </div>
		
	</div>
				</div>
				</div>
                <div class="clearfix"></div>              
           </div>
  				<div class="modal-footer ">
						<div class="col-xs-12 padding-leftright-8" >
						<div class="input-group">
							<span class="input-group-addon modalfootertip" >
				                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
							</span>
							<span class="input-group-addon modalfooterbtn">
							   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
							</button>
							</span>
						</div>
					</div>
				</div>
         </div>
     </div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/customerproject/maintenanceplan/145/hc_list.js"></script><!--当前页面js  -->
