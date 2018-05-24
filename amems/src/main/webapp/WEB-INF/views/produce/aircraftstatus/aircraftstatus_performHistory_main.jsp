<%@ page contentType="text/html; charset=utf-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/aircraftstatus/aircraftstatus_performHistory_main.js"></script>
<div class="col-lg-12 col-xs-12  clearfix padding-left-0 padding-right-0 pull-right" style="overflow-x: auto;" id="status_main_rightSecondDiv">
		     <div class="panel panel-primary left_first_content margin-top-10" style="height:50%">
		     <!-- panel-heading -->
			<div class="panel-heading bg-panel-heading"  >
		     <div class='pull-left'>
		     <div class="font-size-12 line-height-12">维修项目执行历史</div>
				<div class="font-size-9 line-height-9">Maintenance project execution history</div>
		     </div>
			     
			     <div id="hideIcon" class="pull-right" style='height:1px;padding-right:8px;margin-top:-2px;'>
						<img src="${ctx}/static/images/down.png" onclick='aircraftstatus.hideBottom()' style="width:33px;cursor:pointer;">
				</div> 
				<div class='clearfix'></div>
			</div>
				<div class='clearfix'></div>
		    <div class="panel-body  padding-bottom-0 left_first_body" style=" overflow-x: auto;" id="aircraft_performHisotry_status_top_div"> 
		         <div class='' id='aircraft_performHisotry_status_table'>
                  <table class="table table-thin table-bordered table-striped table-hover  table-set " id="aircraft_performHisotry_status_table" style=" min-width: 1600px !important">
				      <thead>                              
							<tr>
								<th class="colwidth-13 ">
				        			<div class="font-size-12 line-height-12">ATA章节号</div>
                        			<div class="font-size-9 line-height-12">ATA</div>
				        		</th>
				        		<th  class="colwidth-7 ">
				        			<div class="font-size-12 line-height-12">项目类型</div>
                        			<div class="font-size-9 line-height-12">Project Type</div>
				        		</th>
				        		<th class="colwidth-13 ">
				        			<div class="font-size-12 line-height-12">任务号</div>
                        			<div class="font-size-9 line-height-12">Task No.</div>
				        		</th>
				        		<th class="colwidth-5 ">
				        			<div class="font-size-12 line-height-12">版本</div>
                        			<div class="font-size-9 line-height-12">Rev.</div>
				        		</th>
				        		<th class="colwidth-7 ">
				        			<div class="font-size-12 line-height-12">参考号</div>
                        			<div class="font-size-9 line-height-12">Reference No.</div>
				        		</th>
				        		<th class="colwidth-15 ">
				        			<div class="font-size-12 line-height-12">任务描述</div>
                        			<div class="font-size-9 line-height-12">Task Description</div>
				        		</th>
				        		<th class="colwidth-13 ">
				        			<div class="font-size-12 line-height-12">工单号</div>
                        			<div class="font-size-9 line-height-12">W/O No.</div>
				        		</th>	
				        		<th class="colwidth-7 ">
				        			<div class="font-size-12 line-height-12">工单附件</div>
                        			<div class="font-size-9 line-height-12">Attachment</div>
				        		</th>
				        		<th class="colwidth-13 ">
				        			<div class="font-size-12 line-height-12">FLB</div>
                        			<div class="font-size-9 line-height-12">FLB</div>
				        		</th>
				        		<th class="colwidth-7 ">
				        			<div class="font-size-12 line-height-12">FLB附件</div>
                        			<div class="font-size-9 line-height-12">Attachment</div>
				        		</th>
				        		<th class="colwidth-10 ">
				        			<div class="font-size-12 line-height-12">件号</div>
                        			<div class="font-size-9 line-height-12">PN</div>
				        		</th>
				        		<th class="colwidth-10 ">
				        			<div class="font-size-12 line-height-12">型号</div>
                        			<div class="font-size-9 line-height-12">Model</div>
				        		</th>
				        		<th class="colwidth-10 ">
				        			<div class="font-size-12 line-height-12">序列号</div>
                        			<div class="font-size-9 line-height-12">SN</div>
				        		</th>
				        		<th class="colwidth-10 ">
				        			<div class="font-size-12 line-height-12">执行日期</div>
                        			<div class="font-size-9 line-height-12">Date</div>
				        		</th>
				        		<th class="colwidth-10 ">
				        			<div class="font-size-12 line-height-12">实际值</div>
                        			<div class="font-size-9 line-height-12">Actual</div>
				        		</th>
				        		<th class="colwidth-10 ">
				        			<div class="font-size-12 line-height-12">计划值</div>
                        			<div class="font-size-9 line-height-12">Planned</div>
				        		</th>
				        		<th class="colwidth-15 ">
				        			<div class="font-size-12 line-height-12">故障信息</div>
                        			<div class="font-size-9 line-height-12">Fault Info</div>
				        		</th>
				        		<th class="colwidth-15 ">
				        			<div class="font-size-12 line-height-12">处理措施</div>
                        			<div class="font-size-9 line-height-12">Measures</div>
				        		</th>
				        		<th class="colwidth-7 ">
				        			<div class="font-size-12 line-height-12">工作者</div>
                        			<div class="font-size-9 line-height-12">Workers</div>
				        		</th>
				        		<th class="colwidth-7 ">
				        			<div class="font-size-12 line-height-12">检查者</div>
                        			<div class="font-size-9 line-height-12">Inspectors</div>
				        		</th>
							</tr> 
						</thead>
					<tbody id="aircraft_performHisotry_status_tbody"><tr class="text-center"><td colspan="21">暂无数据 No data.</td></tr></tbody>
				</table>
				</div>
				 <div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="aircraft_performHisotry_status_Pagination"></div>
			   </div>
			  
             </div>
          </div>