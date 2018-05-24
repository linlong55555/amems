<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/evaluation.js"></script>
<div class="col-xs-12 padding-left-0 padding-right-0 form-group" id="evaluation_modal_div">
	    <span class="col-lg-1  col-md-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2"><span style="color: red;display:none;" id='evaluation_list_important' class="control-important">*</span>技术评估单</div>
			<div class="font-size-9">Evaluation List</div>
		</span>
		<div class="col-lg-11 col-md-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" id="evaluation_modal_divCss">
			<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
				<table class="table table-thin table-bordered table-striped text-center table-set" style="min-width:1000px;margin-bottom:0px !important" id="evaluation_modal_table" >
						<thead>
							<tr>
							<th style="width: 50px;font-weight:normal" id="evaluation_modal_addEvaluation" >
							   <button class="line6 line6-btn" onclick="evaluation_modal.openSelectEvaluation()"  type="button">
								<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
							   </button>
						   </th>
							<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >技术评估单编号 </div><div class="font-size-9 line-height-12" >Evaluation No.</div></th>
							<th class="colwidth-5" style=' font-weight:normal'><div class="font-size-12 line-height-12" >版本 </div><div class="font-size-9 line-height-12" >Rev.</div></th>
							<th class="colwidth-5" style=' font-weight:normal'><div class="font-size-12 line-height-12" >类型 </div><div class="font-size-9 line-height-12" >Type</div></th>
							<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >适航性资料</div><div class="font-size-9 line-height-12" >Doc.</div></th>
							<th style=' font-weight:normal'><div class="font-size-12 line-height-12" >技术评估单主题</div><div class="font-size-9 line-height-12" >Subject</div></th>
							<th class="colwidth-10" style=' font-weight:normal'><div class="font-size-12 line-height-12" >文件</div><div class="font-size-9 line-height-12" >File</div></th>
							<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >颁发日期</div><div class="font-size-9 line-height-12" >Issue Date</div></th>
							<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >生效日期</div><div class="font-size-9 line-height-12" >Effect Date</div></th>
							<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >到期日期</div><div class="font-size-9 line-height-12" >Due Date</div></th>
							<th class="colwidth-7" style=' font-weight:normal'><div class="font-size-12 line-height-12" >评估工程师</div><div class="font-size-9 line-height-12" >Engineer</div></th>
							</tr> 
			         	</thead>
					<tbody id="evaluation_modal_list">
						
					</tbody>									
				</table>
			</div>
		</div>
		</div>
