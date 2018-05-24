<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="tab-pane fade in active" style="padding-left:0px;padding-right:15px;" >
	<div class="col-xs-12 padding-left-0 padding-right-0" >
				
		<!-- 搜索框start -->
		<div class="pull-left padding-left-0 padding-right-0 row-height">
			<div class=" pull-left padding-left-0 padding-right-0" style="width: 400px;">
				<input type="text" placeholder='关键字/故障描述/经验总结/专业/思路经过/机型/ATA章节号/飞机注册号' id="keyword_info_search" class="form-control ">
			</div>
            <div class=" pull-right padding-left-5 padding-right-0 ">   
				<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchInfo();">
					<div class="font-size-12">搜索</div>
					<div class="font-size-9">Search</div>
                </button>
        	</div> 
		</div>
		<!-- 搜索框end -->
	</div>
	<div class="clearfix"></div>
	<div id ="searchList"  class=" col-xs-12 margin-bottom-5 padding-left-0 padding-right-0 table-h" style="overflow-x:scroll " style="margin-top: 10px;"> 
		<!-- 搜索内容 begin -->
		<div class="line"></div> 
		<div class="search_text col-lg-12 padding-left-0 padding-right-0">
		   <!-- <div class="col-lg-9 padding-left-0 padding-right-0">
		      <h1><a href="#">专业 标题 关键字 武汉易瑞信息技术股份有限公司原驻地东西湖区海峡两岸科技产业园</a></h1>
		      <p>故障描述：2016年2月24日,武汉易瑞信息技术股份有限公司在临空港大道融园国际大厦举办乔迁庆典。出席活动的有易瑞信息执行董事长李初捷,海峡两岸科技产业园主任姜舟,中共武汉市东西湖区委总站部部长李建国,东湖高新技术开发区何科方博士，
		                           湖北金鼎盛集团董事长刘永良等。  活动上他们就易瑞信息在航空行业上的未来发展做出重要讲话。</p>
		    </div> 
		 	<div class=" col-lg-3  padding-right-0">
		      <p><em>所属信息</em>：DA00289 吴蕾 2016-10-20 16:28</p>   
		      <p><em>机型</em>：J76D <em>注册号</em>：B-7767</p>   
		      <p><em>零件号</em>：MJ0001789  <em>ATA章节号</em>：01020</p> 
		      <p><em>组织机构</em>：东一飞  <em>基地</em>：海地1区</p>   
		    </div>  -->
		</div> 
		<div class="clearfix"></div>
		<div class="line"></div> 
		<div class="search_text col-lg-12 padding-left-0 padding-right-0">
		   <!-- <div class="col-lg-9 padding-left-0 padding-right-0">
		      <h1><a href="#">专业 标题 关键字 武汉易瑞信息技术股份有限公司原驻地东西湖区海峡两岸科技产业园</a></h1>
		      <p>故障描述：2016年2月24日,武汉易瑞信息技术股份有限公司在临空港大道融园国际大厦举办乔迁庆典。出席活动的有易瑞信息执行董事长李初捷,海峡两岸科技产业园主任姜舟,中共武汉市东西湖区委总站部部长李建国,东湖高新技术开发区何科方博士，
		                           湖北金鼎盛集团董事长刘永良等。  活动上他们就易瑞信息在航空行业上的未来发展做出重要讲话。</p>
		    </div> 
		 <div class=" col-lg-3  padding-right-0">
		      <p><em>所属信息</em>：DA00289 吴蕾 2016-10-20 16:28</p>   
		      <p><em>机型</em>：J76D <em>注册号</em>：B-7767</p>   
		      <p><em>零件号</em>：MJ0001789  <em>ATA章节号</em>：01020</p> 
		      <p><em>组织机构</em>：东一飞  <em>基地</em>：海地1区</p>  
		    </div>  -->
		</div> 
	<div class="clearfix"></div>
	<!-- 搜索内容 end -->
	</div>
	<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="searchPagination"></div>
	
</div>

<!-------故障总结查询对话框 Start-------->
	
<div class="modal fade" id="alertSearchForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false >
	<div class="modal-dialog" style="width:85%;height:80%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertSearchFormBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">档案详情</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
						
						<div style="overflow-y:auto;height:500px;">
						
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
							
							<div class="search_text col-lg-12 padding-left-0 padding-right-0">
							<p>
								<em>件号</em>：<span id="vbjh"></span>
								<em>机型</em>：<span id="vjx"></span>
								<em>飞机注册号</em>：<span id="vfjzch"></span>
								<em>ATA章节号</em>：<span id="vzjhstr"></span>
								<em>关键字</em>：<span id="vgjc"></span>
								<em>可见范围</em>：<span id="vkjfw"></span>
								<em>基地</em>：<span id="vjdstr"></span>
								<em>专业</em>：<span id="vzy"></span>
							</p> 
							<p><em>故障描述</em>：</p>
							<p>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span id="vgzms"></span>
							</p>
							<p><em>经验总结</em>：</p>
							<p>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span id="vjyzj"></span>
							</p>
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							
							<div class="panel-heading padding-left-16 padding-bottom-10 col-xs-12 " >
								<div class=" pull-left margin-right-10" >
									<div class="font-size-14 line-height-18  margin-bottom-5 font-weight-bold">排故思路及经过</div>
								</div>	
							</div>
							
			            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10" >
		
								<!-- start:table -->
								<div id="detailTableView">
								</div>
								<!-- end:table -->
					     		<div class="clearfix"></div>
							 </div> 
						</div>
						
						<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
							
							<div class="panel-heading padding-left-16 padding-top-3 margin-bottom-10 col-xs-12 " style="border-bottom: 1px solid #ccc;">
								<div class=" pull-left margin-right-10 "  >
									<div class="font-size-14 margin-bottom-5 line-height-18 font-weight-bold">附件</div>
								</div>	
							</div>
							
			            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
		
								<!-- start:table -->
								<div id ="attachmentsListViewTbody" >
								</div>
								<!-- end:table -->
							 </div> 
						</div>
						
						</div>
					 	 <div class="text-right margin-top-10 margin-right-0">
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
			           	</div>
                  		<br/>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>

<!-------故障总结查询对话框 End-------->

	<!-------详情对话框 End-------->

	<div id="viewDetailDiv" class="tip" >
		<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
			<div class="search_text col-lg-12 padding-left-0 padding-right-0">
				<p><em>故障描述：</em></p>
				<p><span id="gzmsDetail"></span>
				</p>
				
				<p><em>经验总结：</em></p>
				<p><span id="jyzjDetail"></span></p>
				</div>
		</div>
	</div>
	
	<!-------详情对话框 End-------->

<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/maintenancerecord/maintenanceRecord_search.js"></script>
