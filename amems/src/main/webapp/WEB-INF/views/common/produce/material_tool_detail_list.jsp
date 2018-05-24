<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/material_tool_detail_list.js"></script><!--当前界面js  -->

 <script type="text/javascript">
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					material_tool_detail_list.search();//调用主列表页查询
				}
			}
		});
	});
</script>

<div class="panel panel-default" id="material_tool_detail_list">
	<!--标题 STATR -->
	<div class="panel-heading bg-panel-heading">
		<div class="font-size-12 ">航材工具需求清单</div>
		<div class="font-size-9">Material&Tool</div>
	</div>
	<!--标题ENG  -->
	<div class="panel-body padding-bottom-0 padding-left-8 padding-right-8  padding-top-8">	  
	    <!-- 搜索框 -->
			    <div  class='searchContent margin-top-0' >
			    <!-- 上传按钮  -->
				<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group  ">
					<!-- <a href="javascript:;" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission" style='margin-right:5px;'>
						<div class="font-size-12">领料申请</div>
						<div class="font-size-9">Application</div>
					</a>
					<a type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='project2:airworthiness:main:04' style='margin-right:5px;'>
						<div class="font-size-12">自动转采购提订</div>
						<div class="font-size-9">Automatic</div>
					</a>  -->
					<a type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left" style='margin-right:5px;' onclick="material_tool_detail_list.toDemandApply()">
						<div class="font-size-12">需求提报</div>
						<div class="font-size-9">Demand Report</div>
					</a>
					<a type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode='project2:airworthiness:main:04' onclick="material_tool_detail_list.exportExcel()">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</a>
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
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 row-height" onclick="material_tool_detail_list.search();"  style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
				</div>
				</div>
				<div class='clearfix'></div>
			</div>
		<div class="col-xs-12 widget-body clearfix padding-left-0 padding-right-0 padding-top-0 margin-bottom-8">
        	<ul id="myTab" class="nav nav-tabs tabNew-style">
               	<li class="active">
               		<a id="material_tab" onclick="material_tool_detail_list.checkedMaterial()" href="#materialTab" data-toggle="tab" aria-expanded="false" >
               			<div class="font-size-12 line-height-12">器材</div>
              			<div class="font-size-9 line-height-9">Material</div>
               		</a>
               	</li>
                <li class="" >
					<a id='tool_tab' onclick="material_tool_detail_list.checkedTool()" href='#toolTab' data-toggle="tab"  >
						<div class="font-size-12 line-height-12">工具设备</div>
                   		<div class="font-size-9 line-height-9">Tool</div>
                	</a>
				</li>
           	</ul>
            <div id="myTabContent" class="tab-content">
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
