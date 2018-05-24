<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/hc_list_view.js"></script>
<div class="panel panel-body margin-bottom-0 padding-top-0 padding-left-0 padding-right-0">
       <div class='row-height'>
		<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group " id="hc_list_view_export">
			<a href="javascript:export();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left ">
				<div class="font-size-12">导出</div>
				<div class="font-size-9">Export</div>
			</a>
		</div>
		<div class='clearfix'></div>	
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-bottom-0 padding-left-0 padding-right-0 padding-top-0 " >
		<div class='tab-table-content padding-top-0 padding-left-0 padding-right-0 ' style="overflow: auto;">
				<table class="table table-bordered table-thin table-striped table-hover  text-left table-set" style="min-width: 950px;">
						<thead>
					   		<tr>
					   			<th class="colwidth-5">
									<div class="font-size-12 line-height-18">类型</div>
									<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">型号</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">名称</div>
									<div class="font-size-9 line-height-18">Name</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">需求数量</div>
									<div class="font-size-9 line-height-18">Demand</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">单位</div>
									<div class="font-size-9 line-height-18">Company</div>
								</th>
								<th class="colwidth-10">
									<div class="font-size-12 line-height-18">库存数</div>
									<div class="font-size-9 line-height-18">Inventory</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">库存可用数</div>
									<div class="font-size-9 line-height-18">Inventory Availability</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">互换信息</div>
									<div class="font-size-9 line-height-18">Swap Info</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">件号来源</div>
									<div class="font-size-9 line-height-18">P/N Source</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">说明</div>
									<div class="font-size-9 line-height-18">Desc</div>
								</th>
					 		 </tr>
						</thead>
						<tbody id="hc_list_view_list">
						<tr class='text-center'><td colspan='11'>暂无数据 No data.</td></tr>
						</tbody>
					</table>
				</div>
			</div>
	     	<div class="clearfix"></div>						
</div>