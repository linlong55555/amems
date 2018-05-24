<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
	<div class="pull-right padding-left-0 padding-right-0">
		 <div class=" pull-left padding-left-5 padding-right margin-bottom-10" style="width: 200px;">
	 		<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
	 			<div class="font-size-12">飞机注册号</div>
				<div class="font-size-9">A/C REG</div>
			</span>
			<div class=" col-xs-8 col-sm-8 padding-left-5 padding-right-0">
				 <select class='form-control' id='fjzch' name="fjzch" >
						 
		    	</select>
			</div>
		</div> 
		<div class=" pull-left padding-left-5 padding-right-0" style="width: 200px;">
	        <span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
	        	<div class="font-size-12">组织机构</div>
				<div class="font-size-9">Organization</div>
			</span>
			<div class=" col-xs-8 col-sm-8 padding-left-5 padding-right-0">
					<select id="dprtcode" class="form-control " name="dprtcode">
						<c:choose>
							<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
							<c:forEach items="${accessDepartment}" var="type">
								<option value="${type.id}"
									<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}
								</option>
							</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="">暂无组织机构 No Organization</option>
							</c:otherwise>
						</c:choose>
					</select>
			</div>
		</div>
		
		
		<div class=" pull-left padding-left-5 padding-right-0 margin-bottom-10" style="width: 200px;">
			<input type="hidden" id="isLoadedParts"  name="isLoadedParts" value="1" >
			<input type="text" title='任务单号/任务/部件号/序列号' placeholder='任务单号/任务/部件号/序列号' id="keyword"  name="keyword" 
			class="form-control ">
		</div>
											
        <div class="pull-right padding-left-5 padding-right-0">   
       		<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="ispartMode.load();">
				<div class="font-size-12">搜索</div>
				<div class="font-size-9">Search</div>
			</button>
    	</div>    
	</div>
	<!-- 查询结果 style="margin-bottom:10 !important;"-->
	<div id="list" class="col-lg-12 line4 clearfix padding-left-0 padding-right-0 margin-bottom-5" style="min-height: 300px;">
		
		 
	</div>
</div>

<script type="text/javascript">

ispartMode = {
		load:function(){
			
			if($.trim($('#dprtcode').val()) == ''){
				AlertUtil.showMessage("请选择组织机构");
				return false;
			}
			else{
				$('#ispart #list ').empty();
				AjaxUtil.ajax({
					 type:"post",
					 url:basePath+"/productionplan/plantask/planpanelList",
					 dataType:"json",
					 async : false,
					 cache:false,
					 data:{
						 'isLoadedParts':1
						 ,'fjzch':$.trim($('#ispart #fjzch').val())
						 ,'keyword':$.trim($('#ispart #keyword').val())
						 ,'dprtcode':$.trim($('#ispart #dprtcode').val())
					 },
					 success:function(data) {
						 
						 if(data.plantaskMap!=null){
							 
							 $('#tablist #zjjs').text(formatUndefine(data.zjjs));
							 $('#tablist #fzjjs').text(formatUndefine(data.fzjjs));
							 
							 var content = '';
							 $.each(data.plantaskMap,function(key,data){
								 
								 var fjzch = '<div class="col-lg-12 line3 padding-right-0">'
								 +'<b class="fjzch">'+formatUndefine(key)+'</b>'
								 +'</div>';
								 content = content+fjzch;
								
								 if(data!=null && data.length>0){
									 $.each(data,function(index,item){
										
										   var viewRwdhUrl = (formatUndefine(item.rwlx) ==1)
										   ? '${ctx}/project/workorder/Looked?id='+formatUndefine(item.xggdid)+'&gddlx=4'
										   : '${ctx}/project/workorder/Looked?id='+formatUndefine(item.xggdid) +'&gddlx='+formatUndefine(item.rwlx);
										   var rwdhStr =  '<a class="rwdh" href="#" onclick="window.open(\''+viewRwdhUrl+'\')" title="点击查看详情">'+formatUndefine(item.rwdh)+'</a>'
											 
										 var rwztStr = (formatUndefine(item.xszt) != 3)
										 ?'<button type="button" class=" btn2  pull-right checkPermission complete" permissioncode="productionplan:plantask:planpanel:01"  name="productionplan:plantask:planpanel:01" >'
											+'<div class="font-size-12">完成</div>'	
											+'<div class="font-size-8">Finished</div>'
											+'</button>'
											+'<input type="hidden" name="id" value="'+formatUndefine(item.id)+'"/>'
										 :'';		
											
										 var rwStr = 
									      '<p>'
										 +'<b>任务 :</b>'
										 +'<em title="'+formatUndefine(StringUtil.escapeStr(item.rwxx))+'" class="rwxx" > '
										 + ((formatUndefine(item.rwxx).length>20)?StringUtil.subString(formatUndefine(StringUtil.escapeStr(item.rwxx)),20):formatUndefine(StringUtil.escapeStr(item.rwxx)))
										 +'</em>'
										 +'</p>';
										 
										 var bjh_xlh = (formatUndefine(StringUtil.escapeStr(item.bjh) )+' '+ formatUndefine(StringUtil.escapeStr(item.xlh) ));
										 var bjStr = '<p><b>部件:</b><em title="'+bjh_xlh+'" class="bjh">'
										 + StringUtil.subString(bjh_xlh,20)
										 +'</em></p>'
										 ;	
										 
										  var bjmStr = 
										 '<p>'
										 +'<b>部件名称:</b>'
										 +'<em title="'+formatUndefine( StringUtil.escapeStr(item.bjmc) )+'">'
										 + (StringUtil.subString(formatUndefine(StringUtil.escapeStr(item.bjmc)),20)) 
										 +'</em>'
										 +'</p>';
										 
										 var gjsbStr = '<h2 class="line2">所需航材 / 工具 / 设备 </h2>'
										 +'<ul>';
										 if(item.hcgjs!=null && item.hcgjs.length >0){
											 $.each(item.hcgjs,function(idx,item){
												 gjsbStr += '<li title=\''+formatUndefine(item['hc_lx'])+' '+formatUndefine(StringUtil.escapeStr(item['hc_mc']) )+' '+formatUndefine(item['hc_sl'])+'\'>'
												 if(item['hc_lx'] != undefined){
													 gjsbStr += ' '+formatUndefine(item['hc_lx']) +' ' 
												 }
												 
												 if(item['hc_mc'] != undefined){
													 gjsbStr += ' '+((formatUndefine(StringUtil.escapeStr(item['hc_mc'])).length>25)?StringUtil.subString(formatUndefine(StringUtil.escapeStr(item['hc_mc'])),25):formatUndefine(StringUtil.escapeStr(item['hc_mc'])))
												 }
												 if(item['hc_sl'] != undefined){
													 gjsbStr += ' '+formatUndefine(item['hc_sl']) +' ' 
												 }
												 gjsbStr += '</li>'
											 });
										 }
										 else{
											 gjsbStr += '<li>无</li>'
										 }
										 gjsbStr += '</ul>';
										
										 var bzStr =  
											 '<h2 class="line2">备注</h2>'
											+'<p class="remark" title="'+formatUndefine(StringUtil.escapeStr(item.bz))+'">'+StringUtil.subString(formatUndefine(StringUtil.escapeStr(item.bz)),60)+'</p>'
											+(formatUndefine(item.xszt)==3?'<i><img src="${ctx}/static/assets/img/finished_05.png" alt="finished" /></i>':'')
											;
										 
										 content = content
										 +'<div class="col-lg-2 col-xs-6 border_four margin-bottom-10 padding-left-0 padding-right-0 margin-left-10 club4">'
										 +'<h1 class="line1">'
										 + rwdhStr
										 + rwztStr
										 +'</h1>'
										 + rwStr
										 + bjStr
										 + bjmStr
										 + gjsbStr
										 + bzStr
										 +'</div>'
										 ;
									 })
								 }
								 
								 $('#ispart #list ').empty();
								 $('#ispart #list ').append(content);
								
							 })
						 }
						
					 }
			   });
			}
			//完成	
			ispartMode.afterload();
		}	
		
		,afterload: function(){
			$('.complete').on('click',function(){
				 var id = $(this).next().val();
				 AjaxUtil.ajax({
					 type:"post",
				     async: false,	
				     cache:false,
					 url:basePath+"/productionplan/plantask/complete",
					 dataType:"json",
					 data:{'id':id},
					 success:function(data) {
						 if(data.isLegitimate){
							 ispartMode.load();
						 }
						 else{
							 AlertUtil.showErrorMessage(data.message);
							 ispartMode.load();
						 }
					 }
			    }); 
			})
			
			var keyword = $.trim($('#keyword').val());
			/* $.each($('.fjzch'),function(){
				if($(this).text().indexOf(keyword)>=0){
					$(this).html($(this).html().replace($.trim(keyword),'<font color="red">'+$.trim(keyword)+'</font>'))
				}
			}); */
			
			 
			$.each($('.rwxx'),function(){
				if($(this).text().indexOf(keyword)>=0){  
					$(this).html($(this).html().replace($.trim(keyword),'<font color="red">'+$.trim(keyword)+'</font>'))
				}
			});
			
			$.each($('.bjh'),function(){
				 
				if($(this).text().indexOf(keyword)>=0){
					$(this).html($(this).html().replace(keyword,'<font color="red">'+keyword+'</font>'))
				}
			});
			
			$.each($('.rwdh'),function(){
				 
				if($(this).text().indexOf(keyword)>=0){
					$(this).html($(this).html().replace(keyword,'<font color="red">'+keyword+'</font>'))
				}
			});
			
			//初始化日志功能
			logModal.init({code:'B_S_009_PLAN' ,extparam:{/* IN_ZTS:[1,2], */ISPART:'1'} });
			
			window.refreshPermission(); 
		}
	};
	
$(function(){
	
	Navigation(menuCode);
	
	 $('#isPartBlock').on('click',function(){
			//初始化日志功能
			logModal.resetParam({code:'B_S_009_PLAN',extparam:{/* IN_ZTS:[1,2], */ISPART:'1'}})
			$('#nopart #dprtcode').val($('#nopart #dprtcode').val());
			ispartMode.load();
	 	});
	 
	//改变机构
	$('#ispart #dprtcode').on('change',function(){
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
	 	var planeRegOption = '<option value="">全部</option>';
		if(planeDatas != null && planeDatas.length >0){
			$.each(planeDatas,function(i,planeData){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			});
		}
		$("#fjzch").html(planeRegOption); 
		$('#nopart #dprtcode').val($('#ispart #dprtcode').val());
 	});
	
	$('#ispart #fjzch').on('change',function(){
		ispartMode.load();
 	});
	
 	$("#ispart #dprtcode").trigger("change");
 	ispartMode.load();
	
})
 
</script>
 
							    
   