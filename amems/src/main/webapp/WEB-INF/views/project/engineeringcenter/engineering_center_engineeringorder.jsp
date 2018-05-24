<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
 	<div class="col-lg-12 padding-left-0 padding-right-0">
		<div class="col-lg-12  ibox-title"  style='padding:0px;height:25px;background:#ececec;'>
			<label class="pull-left" style='height:25px;line-height:25px;padding-left:10px;'>
		         EO指令/Engineering Order
			</label>
		</div>
		<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;min-height:20px;max-height:200px;">
        <table class="table table-thin table-bordered table-hover table-set" style="min-width:950px"  >
			<thead>
				<tr>
					<th class="colwidth-3" >
						<div class="font-size-12 line-height-18">序号</div>
						<div class="font-size-9 line-height-18">No.</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">工程指令编号</div>
						<div class="font-size-9 line-height-18">E/O No.</div>
					</th>
					<th th_class="cont-exp6" td_class="engineeringorderDisplayFile" table_id=""   class="cont-exp6 downward colwidth-13" onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
						<div class="font-size-12 line-height-18 notwrapnew">关联评估单号</div>
						<div class="font-size-9 line-height-18 notwrapnew">Assessment NO.</div>
					</th>
					<th class="colwidth-20">
						<div class="font-size-12 line-height-18">参考资料</div>
						<div class="font-size-9 line-height-18">Reference material</div>
					</th>
					<th class="colwidth-25">
						<div class="font-size-12 line-height-18">主题</div>
						<div class="font-size-9 line-height-18">Subject</div>
					</th>
					<th class="colwidth-5">
						<div class="font-size-12 line-height-18">版本</div>
						<div class="font-size-9 line-height-18">Revision</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-18">相关改装号</div>
						<div class="font-size-9 line-height-18">MOD No.</div>
					</th>
					<th class="colwidth-30">
						<div class="font-size-12 line-height-18">编写依据</div>
						<div class="font-size-9 line-height-18">AD,Bulletin etc.No</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-18">重复执行</div>
						<div class="font-size-9 line-height-18">Repeated Execution Limit</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-18">重量与平衡影响</div>
						<div class="font-size-9 line-height-18">Weight And Balance</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">状态</div>
						<div class="font-size-9 line-height-18">State</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">制单人</div>
						<div class="font-size-9 line-height-18">Creator </div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">制单时间</div>
						<div class="font-size-9 line-height-18">Create Time</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th>
				</tr>
			</thead>
			<tbody id="engineering_order_list">
			</tbody>
		</table>
    </div>		 
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/engineeringcenter/engineering_center_engineeringorder.js"></script>