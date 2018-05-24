<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<div class="col-lg-12 padding-left-0 padding-right-0">
		<div class="col-lg-12  ibox-title"  style='padding:0px;height:25px;background:#ececec;'>
			<label id="titleNoticeHead" class="pull-left" style='height:25px;line-height:25px;padding-left:10px;'>
		         	修订维修方案/Amendment Maintenance
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
						<div class="font-size-12 line-height-18">修订通知书编号</div>
						<div class="font-size-9 line-height-18">A/N No.</div>
					</th>
					<th th_class="cont-exp5" td_class="noticeDisplayFile" table_id=""  class="cont-exp5 downward colwidth-13" onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
						<div class="font-size-12 line-height-18">关联评估单号 </div>
						<div class="font-size-9 line-height-18">Assessment NO.</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">通知书类型 </div>
						<div class="font-size-9 line-height-18">Notice Type</div>
					</th>
					<th class="colwidth-10" >
						<div class="font-size-12 line-height-18">机型 </div>
						<div class="font-size-9 line-height-18">Model</div>
					</th>
					<th class="colwidth-30">
						<div class="font-size-12 line-height-18">修订主题 </div>
						<div class="font-size-9 line-height-18">Subject</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">修订人 </div>
						<div class="font-size-9 line-height-18">Revised By</div>
					</th>
					<th class="colwidth-10">
						<div class="font-size-12 line-height-18">修订期限 </div>
						<div class="font-size-9 line-height-18">Revision Period</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">剩余天数 </div>
						<div class="font-size-9 line-height-18">Remain Days</div>
					</th>
					<th class="colwidth-30">
						<div class="font-size-12 line-height-18">修订内容 </div>
						<div class="font-size-9 line-height-18">Revision Contents</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">状态 </div>
						<div class="font-size-9 line-height-18">State</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">接收状态</div>
						<div class="font-size-9 line-height-18">Receive State </div>
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
			<tbody id="revision_notice_book_list">
			</tbody>
		</table>
    </div>		 
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/engineeringcenter/engineering_center_amendmentnotice.js"></script>
