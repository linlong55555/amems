<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="">
	<div class='margin-top-0 row-height' >
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left">
							<div class="font-size-12">销毁</div>
							<div class="font-size-9">Destory</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left margin-left-8">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Download</div>
						</button>
						
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">报废日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<input type='text' id='' class='form-control' name="date-range-picker"/>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">部件来源</div>
							<div class="font-size-9">Source</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<select class="form-control">
								<option></option>
							</select>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='报废单号/部件号/部件名称/批次号/序列号/说明'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew destroy_click-btn" >
		                    	<button id="workCardMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="work_card_main.search();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="moreSearchDestory();">
								<div class='input-group input-group-search'>
								<div class="input-group-addon">
								<div class="font-size-12">更多</div>
								<div class="font-size-9 margin-top-5" >More</div>
								</div>
								<div class="input-group-addon">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="iconDestory"></i>
								</div>
								</div>
					   			</button>
		                	</div>
						</div>
					</div>
					<!-- 搜索框end -->
				  <div class='clearfix'></div>
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearchDestory">
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">销毁日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' id='' class='form-control' name="date-range-picker"/>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control'>
								<option></option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="work_card_main.searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
			<div id="" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 tab-table-height" style="overflow:auto;">
				<table id="" class="table table-thin table-bordered table-striped table-hover table-set" >
					<thead>
						<tr>
							<th class="viewCol fixed-column colwidth-7 selectAllImg" style="width: 75px;">
								<a href="#" ><img src="/amems/static/assets/img/d1.png" alt="全选"></a>
								<a href="#" class="margin-left-5" ><img src="/amems/static/assets/img/d2.png" alt="不全选"></a>
							</th>
							<th class="colwidth-15" >
							    <div class="font-size-12 line-height-18">位置</div>
								<div class="font-size-9 line-height-18">Position</div>
							</th>
							<th class="colwidth-9" >
							    <div class="font-size-12 line-height-18">部件号</div>
								<div class="font-size-9 line-height-18">PN</div>
							</th>
							<th class="colwidth-9" >
							    <div class="font-size-12 line-height-18">部件名称</div>
								<div class="font-size-9 line-height-18">Name</div>
							</th>
							<th class="colwidth-9" >
							    <div class="font-size-12 line-height-18">批次号</div>
								<div class="font-size-9 line-height-18">BN</div>
							</th>
							<th class="colwidth-9" >
							    <div class="font-size-12 line-height-18">序列号</div>
								<div class="font-size-9 line-height-18">SN</div>
							</th>
							<th class="colwidth-9 " >
							    <div class="font-size-12 line-height-18">部件来源</div>
								<div class="font-size-9 line-height-18">Source</div>
							</th>
							<th class="colwidth-20" >
							    <div class="font-size-12 line-height-18">说明</div>
								<div class="font-size-9 line-height-18">Desc</div>
							</th>
							<th class="colwidth-9" >
							    <div class="font-size-12 line-height-18">报废单号</div>
								<div class="font-size-9 line-height-18">Scrap No.</div>
							</th>
							<th class="colwidth-9" >
							    <div class="font-size-12 line-height-18">日期</div>
								<div class="font-size-9 line-height-18">Date</div>
							</th>
							<th class="colwidth-9" >
							    <div class="font-size-12 line-height-18">申请人</div>
								<div class="font-size-9 line-height-18">Applicant</div>
							</th>
							<th class="colwidth-9" >
							    <div class="font-size-12 line-height-18">销毁人</div>
								<div class="font-size-9 line-height-18">Destroys</div>
							</th>
							<th class="colwidth-9" >
							    <div class="font-size-12 line-height-18">销毁时间</div>
								<div class="font-size-9 line-height-18">Date</div>
							</th>
							
						</tr>
					</thead>
					<tbody id="">
					<tr>
					<td colspan="13" class="text-center">暂无数据</td>
					</tr>
					</tbody>
				</table>
			</div>
	      <div class="col-xs-12 text-center page-height" id=""></div>
</div>