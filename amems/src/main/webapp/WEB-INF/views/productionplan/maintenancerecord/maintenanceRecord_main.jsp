<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机务维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<style type="text/css">
  .tip {
     display: none;
     width: 600px;
     height: auto;
     padding:10px;
     background:#fff;
     border:1px solid #ccc;
     line-height: 100px;
     text-align: left;
     text-indent:2em;
     position: absolute;
     margin: -140px 0 0 200px;
 }
</style>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<input type="hidden" id="adjustHeight" value="65">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content ">
		<!-- BEGIN PAGE TITLE & BREADCRUMB-->
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="clearfix"></div>

			<div class="panel-body padding-bottom-0" >
				<!-----标签导航start---->
				<ul class="nav nav-tabs" role="tablist" id="tablist">
					<li class="active"><a href="#recordSearchId" onclick="searchInfo()" data-toggle="tab" aria-expanded="true">维修档案</a></li>
					<li ><a href="#myRecord" data-toggle="tab" onclick="searchRevision()" aria-expanded="true">我的维修档案</a></li>
				</ul>

				<!-----标签内容start---->
				<div class="tab-content margin-bottom-10">

					<div class="tab-pane fade in active" id="recordSearchId">
						<%@ include file="/WEB-INF/views/productionplan/maintenancerecord/maintenanceRecord_search.jsp"%>
						<!-- 搜索内容 begin -->
						<!-- <div class="line"></div> 
						<div class="search_text col-lg-12 padding-left-0 padding-right-0">
						   <div class="col-lg-9 padding-left-0 padding-right-0">
						      <h1><a href="#">专业 标题 关键字 武汉易瑞信息技术股份有限公司原驻地东西湖区海峡两岸科技产业园</a></h1>
						      <p>故障描述：2016年2月24日,武汉易瑞信息技术股份有限公司在临空港大道融园国际大厦举办乔迁庆典。出席活动的有易瑞信息执行董事长李初捷,海峡两岸科技产业园主任姜舟,中共武汉市东西湖区委总站部部长李建国,东湖高新技术开发区何科方博士，
						                           湖北金鼎盛集团董事长刘永良等。  活动上他们就易瑞信息在航空行业上的未来发展做出重要讲话。</p>
						    </div> 
						 <div class=" col-lg-3  padding-right-0">
						      <p><em>所属信息</em>：DA00289 吴蕾 2016-10-20 16:28</p>   
						      <p><em>机型</em>：J76D <em>注册号</em>：B-7767</p>   
						      <p><em>零件号</em>：MJ0001789  <em>ATA章节号</em>：01020</p> 
						      <p><em>组织机构</em>：东一飞  <em>基地</em>：海地1区</p>   
						    </div> 
						</div> 
						<div class="clearfix"></div>
						<div class="line"></div> 
						<div class="search_text col-lg-12 padding-left-0 padding-right-0">
						   <div class="col-lg-9 padding-left-0 padding-right-0">
						      <h1><a href="#">专业 标题 关键字 武汉易瑞信息技术股份有限公司原驻地东西湖区海峡两岸科技产业园</a></h1>
						      <p>故障描述：2016年2月24日,武汉易瑞信息技术股份有限公司在临空港大道融园国际大厦举办乔迁庆典。出席活动的有易瑞信息执行董事长李初捷,海峡两岸科技产业园主任姜舟,中共武汉市东西湖区委总站部部长李建国,东湖高新技术开发区何科方博士，
						                           湖北金鼎盛集团董事长刘永良等。  活动上他们就易瑞信息在航空行业上的未来发展做出重要讲话。</p>
						    </div> 
						 <div class=" col-lg-3  padding-right-0">
						      <p><em>所属信息</em>：DA00289 吴蕾 2016-10-20 16:28</p>   
						      <p><em>机型</em>：J76D <em>注册号</em>：B-7767</p>   
						      <p><em>零件号</em>：MJ0001789  <em>ATA章节号</em>：01020</p> 
						      <p><em>组织机构</em>：东一飞  <em>基地</em>：海地1区</p>  
						    </div> 
						</div> 
					<div class="clearfix"></div>
					  <div class="line"></div> 
						<div class="search_text col-lg-12 padding-left-0 padding-right-0">
						   <div class="col-lg-9 padding-left-0 padding-right-0">
						      <h1><a href="#">专业 标题 关键字 武汉易瑞信息技术股份有限公司原驻地东西湖区海峡两岸科技产业园</a></h1>
						      <p>故障描述：2016年2月24日,武汉易瑞信息技术股份有限公司在临空港大道融园国际大厦举办乔迁庆典。出席活动的有易瑞信息执行董事长李初捷,海峡两岸科技产业园主任姜舟,中共武汉市东西湖区委总站部部长李建国,东湖高新技术开发区何科方博士，
						                           湖北金鼎盛集团董事长刘永良等。  活动上他们就易瑞信息在航空行业上的未来发展做出重要讲话。</p>
						    </div> 
						     <div class=" col-lg-3  padding-right-0">
						      <p><em>所属信息</em>：DA00289 吴蕾 2016-10-20 16:28</p>   
						      <p><em>机型</em>：J76D 注册号：B-7767</p>   
						      <p><em>零件号</em>：MJ0001789  ATA章节号：01020</p> 
						      <p><em>组织机构</em>：东一飞  基地：海地1区</p>  
						    </div> 
						</div> 
											<div class="clearfix"></div>
					  <div class="line"></div> 
						<div class="search_text col-lg-12 padding-left-0 padding-right-0">
						   <div class="col-lg-9 padding-left-0 padding-right-0">
						      <h1><a href="#">专业 标题 关键字 武汉易瑞信息技术股份有限公司原驻地东西湖区海峡两岸科技产业园</a></h1>
						      <p>故障描述：2016年2月24日,武汉易瑞信息技术股份有限公司在临空港大道融园国际大厦举办乔迁庆典。出席活动的有易瑞信息执行董事长李初捷,海峡两岸科技产业园主任姜舟,中共武汉市东西湖区委总站部部长李建国,东湖高新技术开发区何科方博士，
						                           湖北金鼎盛集团董事长刘永良等。  活动上他们就易瑞信息在航空行业上的未来发展做出重要讲话。</p>
						    </div> 
						     <div class=" col-lg-3  padding-right-0">
						      <p><em>所属信息</em>：DA00289 吴蕾 2016-10-20 16:28</p>   
						      <p><em>机型</em>：J76D 注册号：B-7767</p>   
						      <p><em>零件号</em>：MJ0001789  ATA章节号：01020</p> 
						      <p><em>组织机构</em>：东一飞  基地：海地1区</p> 
						    </div> 
						</div> 
											<div class="clearfix"></div>
					  <div class="line"></div> 
						<div class="search_text col-lg-12 padding-left-0 padding-right-0">
						   <div class="col-lg-9 padding-left-0 padding-right-0">
						      <h1><a href="#">专业 标题 关键字 武汉易瑞信息技术股份有限公司原驻地东西湖区海峡两岸科技产业园</a></h1>
						      <p>故障描述：2016年2月24日,武汉易瑞信息技术股份有限公司在临空港大道融园国际大厦举办乔迁庆典。出席活动的有易瑞信息执行董事长李初捷,海峡两岸科技产业园主任姜舟,中共武汉市东西湖区委总站部部长李建国,东湖高新技术开发区何科方博士，
						                           湖北金鼎盛集团董事长刘永良等。  活动上他们就易瑞信息在航空行业上的未来发展做出重要讲话。</p>
						    </div> 
						     <div class=" col-lg-3  padding-right-0">
						      <p><em>所属信息</em>：DA00289 吴蕾 2016-10-20 16:28</p>   
						      <p><em>机型</em>：J76D 注册号：B-7767</p>   
						      <p><em>零件号</em>：MJ0001789  ATA章节号：01020</p> 
						      <p><em>组织机构</em>：东一飞  基地：海地1区</p>  
						    </div> 
						</div> 
											<div class="clearfix"></div>
					  <div class="line"></div> 
						<div class="search_text col-lg-12 padding-left-0 padding-right-0">
						   <div class="col-lg-9 padding-left-0 padding-right-0">
						      <h1><a href="#">专业 标题 关键字 武汉易瑞信息技术股份有限公司原驻地东西湖区海峡两岸科技产业园</a></h1>
						      <p>故障描述：2016年2月24日,武汉易瑞信息技术股份有限公司在临空港大道融园国际大厦举办乔迁庆典。出席活动的有易瑞信息执行董事长李初捷,海峡两岸科技产业园主任姜舟,中共武汉市东西湖区委总站部部长李建国,东湖高新技术开发区何科方博士，
						                           湖北金鼎盛集团董事长刘永良等。  活动上他们就易瑞信息在航空行业上的未来发展做出重要讲话。</p>
						    </div> 
						     <div class=" col-lg-3  padding-right-0">
						      <p><em>所属信息</em>：DA00289 吴蕾 2016-10-20 16:28</p>   
						      <p><em>机型</em>：J76D 注册号：B-7767</p>   
						      <p><em>零件号</em>：MJ0001789  ATA章节号：01020</p> 
						      <p><em>组织机构</em>：东一飞  基地：海地1区</p>  
						    </div> 
						</div>  -->
						
						<!-- 搜索内容 end -->
					</div>
					
					<div class="tab-pane fade" id="myRecord">
						<div class="row feature">
							<%@ include file="/WEB-INF/views/productionplan/maintenancerecord/maintenanceRecord_list.jsp"%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/productionplan/maintenancerecord/maintenanceRecord_main.js"></script>
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
</body>
</html>