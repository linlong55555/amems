<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<small class="text-muted row-height" >
列表以月为单位：
<i class='fa fa-star' style='color:black;'></i>&nbsp;表示计划所在的月份
</small>
<div id="year_list_table_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 tab-first-height" style="overflow-x: auto;">
	<table id="yearTable" class="table table-thin table-bordered table-hover text-left table-set" >
		<thead>
			<tr>
			    <th  class="colwidth-8 sorting"   onclick="year_list_table_div.orderBy('lx',this)" name="column_lx">
				    <div class="font-size-12 line-height-18">类型</div>
					<div class="font-size-9 line-height-18">Type</div>
				</th>
				<th class="colwidth-10 sorting" onclick="year_list_table_div.orderBy('shdxbh',this)" name="column_shdxbh" >
					<div class="font-size-12 line-height-18">审核对象</div>
					<div class="font-size-9 line-height-18">Audit object</div>
				</th>
				
				<th class="colwidth-10 ">
				    <div class="font-size-12 line-height-18">责任审核人</div>
					<div class="font-size-9 line-height-18">Auditor</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">1月</div>
					<div class="font-size-9 line-height-18">Jan</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">2月</div>
					<div class="font-size-9 line-height-18">Feb</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">3月</div>
					<div class="font-size-9 line-height-18">Mar</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">4月</div>
					<div class="font-size-9 line-height-18">Apr</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">5月</div>
					<div class="font-size-9 line-height-18">May</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">6月</div>
					<div class="font-size-9 line-height-18">Jun</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">7月</div>
					<div class="font-size-9 line-height-18">Jul</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">8月</div>
					<div class="font-size-9 line-height-18">Aug</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">9月</div>
					<div class="font-size-9 line-height-18">Sep</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">10月</div>
					<div class="font-size-9 line-height-18">Oct</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">11月</div>
					<div class="font-size-9 line-height-18">Nov</div>
				</th>
				<th class="colwidth-5">
					<div class="font-size-12 line-height-18">12月</div>
					<div class="font-size-9 line-height-18">Dec</div>
				</th>
			</tr>
		</thead>
		<tbody id="year_list_table_div_list">
		</tbody>
	</table>
</div>
<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="year_list_table_div_pagination">
</div>
          