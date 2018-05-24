
$(document).ready(function(){
 
	refreshPermission();
	Navigation(menuCode, '', '', 'sc_001001', '朱超', '2017-09-14', '朱超', '2017-09-14');
	airworthiness_manage.init({});
	//初始化日志功能
	logModal.init({code:'B_S_014'});
	
});  

var stopeReasonDict = ["","定检原因","故障原因","缺件原因(航材、工具)","机组原因","其他原因"];
var tclxText = ["","计划","非计划"];

var airworthiness_manage = {
		init: function(){
			Navigation(menuCode);
			this.initForm();
			this.loadPlans();
			$first = $('.div_qh:first');
			if($first.length > 0){
				$first.click();
			}
			else {
				this.appendContentHtml([])
			}
			
		},
		/**改变机构*/
		changeOrg: function(){
			this.clearForm();
			this.loadPlans();
			$first = $('.div_qh:first');
			if($first.length > 0){
				$first.click();
			}
			else {
				this.appendContentHtml([])
			}
		},
		/**点击一个飞机*/
		clickPlan: function(ele){
			var $ele = $(ele);
			$('.div_qh').removeClass('color_success');
			$ele.addClass('color_success'); 
			this.search();
			this.clearForm();
		},
		
		/**鼠标停留在飞机上方*/
		onmouseoverPlan: function(ele){
			$(ele).removeClass('color_white').addClass('color_gery');
		},
		
		/**离开一个飞机*/
		onmouseoutPlan: function(ele){
			$(ele).removeClass('color_gery') ;
		},
		/**清除表单*/
		clearForm: function(){
			$('#saveForm :input').val('');
			$('#saveForm #bshyy').val('1');//定检原因
			$('.sh_list #zdsj').text("");
			$('.sh_list #zdrrealname').text("");
			$('.sh_list #lsh').text("");
		},
		/**保存不适航状态*/
		saveForm: function(){
			var _this = this;
			if(this.validationForm()){
				var fjzch = $('.div_qh.color_success').attr('id');
				if(!fjzch){
					AlertUtil.showErrorMessage("请选择指定飞机!");
					return false;
				}
				
				//日期
				  var bshYjrq = $.trim($('#saveForm #bshYjrq').val());
				  var bshSjrq = $.trim($('#saveForm #bshSjrq').val());
				  var shYjrq = $.trim($('#saveForm #shYjrq').val());
				  var shSjrq = $.trim($('#saveForm #shSjrq').val());
				  
				  //时间
				  var bshYjsj = $.trim($('#saveForm #bshYjsj').val());
				  var bshSjsj = $.trim($('#saveForm #bshSjsj').val());
				  var shYjsj = $.trim($('#saveForm #shYjsj').val());
				  var shSjsj = $.trim($('#saveForm #shSjsj').val());
				  
				  bshYjsj = bshYjsj!=''?bshYjsj+':00':'00:00:00';
				  bshSjsj = bshSjsj!=''?bshSjsj+':00':'00:00:00';
				  shYjsj = shYjsj!=''?shYjsj+':00':'00:00:00';
				  shSjsj = shSjsj!=''?shSjsj+':00':'00:00:00';
				  
				  bshYjrq = bshYjrq!=''?(bshYjrq+' '+bshYjsj):'';
				  bshSjrq = bshSjrq!=''?(bshSjrq+' '+bshSjsj):'';
				  shYjrq = shYjrq!=''?(shYjrq+' '+shYjsj):'';
				  shSjrq = shSjrq!=''?(shSjrq+' '+shSjsj):'';
				  
				  var bshrq = bshSjrq!=''?bshSjrq:bshYjrq;
				  var shrq =  shSjrq!=''?shSjrq:shYjrq;
				  if(!TimeUtil.compareDate(shrq,bshrq)){
					   AlertUtil.showErrorMessage("适航时间必须大于停场时间！");
					   return false;
			      }
				   
				  var data = {
						  
						 'bshyy':$('#bshyy').val(),
						 'tcyy':$('#tcyy').val(),
						 'bz':$('#bz').val(),
						'dprtcode':$('#dprtcode_search').val(),
					    'id' :  $.trim($('#saveForm #id').val()),// id
						'bshYjrq' :  bshYjrq,// 预计停厂时间
						'bshSjrq' :  bshSjrq,// 实际停厂时间
						'bshSpdh' :  $.trim($('#saveForm #bshSpdh').val()),// 不适航单号
						'bshPzrid':  $.trim($('#saveForm #bshPzrid').val()),// 不适航批准人
						
						'shYjrq' :  shYjrq,// 预计适航时间
						'shSjrq' :  shSjrq,// 实际适航时间
						'shSpdh' :  $.trim($('#saveForm #shSpdh').val()),// 适航单号
						'shPzrid' : $.trim($('#saveForm #shPzrid').val()),//适航批准人
						'fjzch' : $.trim( fjzch)//适航批准人
					}
				  $('#dprtcode').val($('#dprtcode_search').val());
				  var body = window
				  var saveUrl  = basePath+"/aircraftinfo/airworthiness/saveOrUpdate";
				   
				  // 提交数据
				  AjaxUtil.ajax({
					    async: true,
						url:saveUrl,
						type:"post",
						data:data, 
					    dataType:"json",      
						success:function(data){
							$('#aftersaveForm #id').val(data.id);
							AlertUtil.showMessageCallBack({
								message:"保存适航指令成功!"
								,callback:function(){
									$('.div_qh.color_success').click();
									_this.updatePlan(fjzch)
									 
								}	
							});
						}
				 });
			}
		},
		validationForm: function (){
			
			var result = true;
			//计划停厂日期，时间
			var bshYjrq = $.trim($('#saveForm #bshYjrq').val());
			var bshYjsj = $.trim($('#saveForm #bshYjsj').val());
			
			//实际停厂日期，时间		
			var bshSjrq = $.trim($('#saveForm #bshSjrq').val());
			var bshSjsj = $.trim($('#saveForm #bshSjsj').val());
			 
			//计划适航日期，时间			
			var shYjrq = $.trim($('#saveForm #shYjrq').val());
			var shYjsj = $.trim($('#saveForm #shYjsj').val());

			//实际适航日期，时间	
			var shSjrq = $.trim($('#saveForm #shSjrq').val());
			var shSjsj = $.trim($('#saveForm #shSjsj').val());
			
			//不适航审批单号
			var bshSpdh = $.trim($('#saveForm #bshSpdh').val());
			//适航审批单号		
			var shSpdh = $.trim($('#saveForm #shSpdh').val());
			//备注		
			var bz = $.trim($('#saveForm #bz').val());
			
			result = $.trim($('#saveForm #bshYjrq').val())!='' ||  $.trim($('#saveForm #bshSjrq').val())!='';
			if(!result){
				AlertUtil.showErrorMessage('计划停场时间,实际停场时间至少需要填写一个!');
				return false; 
			}
			
			result = $.trim($('#saveForm #shYjrq').val())!='' ||  $.trim($('#saveForm #shSjrq').val())!='';
			if(!result){
				AlertUtil.showErrorMessage('计划适航时间,实际适航时间至少需要填写一个!');
				return false; 
			}
			
			var reg = /^[^\u4e00-\u9fa5]{0,20}$/; 
			var r = bshSpdh.match(reg); 
			if(r==null) {
				$('#bshSpdh').focus();
				AlertUtil.showErrorMessage('不适航审批单号不包含中文且长度不超过20个字符!');
				return false; 
			}
			
			var r = shSpdh.match(reg); 
			if(r==null) {
				$('#shSpdh').focus();
				AlertUtil.showErrorMessage('适航审批单号不包含中文且长度不超过20个字符!');
				return false; 
			}
			if(bz.length>300){
				$('#bz').focus();
				AlertUtil.showErrorMessage('备注不超过300个字符!');
				return false;
			}
			return result;
		},
		/**初始化表单控件*/
		initForm: function(){
			
			$("#bshYjrq").datepicker({
				format:'yyyy-mm-dd',
				autoclose : true,
				clearBtn : true
			});
			$("#bshYjsj").datetimepicker({
				lang:"ch", 
				format:'H:i'	,step:10,			 
				datepicker:false
			});
			
			$("#bshSjrq").datepicker({
				format:'yyyy-mm-dd',
				autoclose : true,
				clearBtn : true
			});
			$("#bshSjsj").datetimepicker({
				lang:"ch", 
				format:'H:i'	,step:10,			 
				datepicker:false
			});
			
			
			$("#shYjrq").datepicker({
				format:'yyyy-mm-dd',
				autoclose : true,
				clearBtn : true
			});
			$("#shYjsj").datetimepicker({
				lang:"ch", 
				format:'H:i'	,step:10,			 
				datepicker:false
			});
			
			$("#shSjrq").datepicker({
				format:'yyyy-mm-dd',
				autoclose : true,
				clearBtn : true
			});
			$("#shSjsj").datetimepicker({
				lang:"ch", 
				format:'H:i'	,step:10,			 
				datepicker:false 
			});
			
			$('#bshBtn').bind("click",function(event){
				event.preventDefault();
				Personel_Tree_Multi_Modal.show({
					checkUserList:[{id:$("#saveForm #"+"bshPzrid").val(),displayName:$("#saveForm #bshPzrmc").val()}],//原值，在弹窗中回显
					dprtcode:$("#dprtcode_search").val(),//
					multi:false,
					callback: function(data){//回调函数
						var user = $.isArray(data)?data[0]:{id:'',displayName:''};
						$("#saveForm #bshPzrid").val(user.id);
						$("#saveForm #bshPzrmc").val(StringUtil.escapeStr(user.displayName));
					}
				});
				AlertUtil.hideModalFooterMessage();
	    	});
			
			$('#bshPzrmc').bind("dblclick",function(event){
				event.preventDefault();
				Personel_Tree_Multi_Modal.show({
					checkUserList:[{id:$("#saveForm #"+"bshPzrid").val(),displayName:$("#saveForm #bshPzrmc").val()}],//原值，在弹窗中回显
					dprtcode:$("#dprtcode_search").val(),//
					multi:false,
					callback: function(data){//回调函数
						var user = $.isArray(data)?data[0]:{id:'',displayName:''};
						$("#saveForm #bshPzrid").val(user.id);
						$("#saveForm #bshPzrmc").val(StringUtil.escapeStr(user.displayName));
					}
				});
				AlertUtil.hideModalFooterMessage();
				
	    	});
		
			$('#shBtn').bind("click",function(event){
				event.preventDefault();
				Personel_Tree_Multi_Modal.show({
					checkUserList:[{id:$("#saveForm #"+"shPzrid").val(),displayName:$("#saveForm #shPzrmc").val()}],//原值，在弹窗中回显
					dprtcode:$("#dprtcode_search").val(),//
					multi:false,
					callback: function(data){//回调函数
						var user = $.isArray(data)?data[0]:{id:'',displayName:''};
						$("#saveForm #shPzrid").val(user.id);
						$("#saveForm #shPzrmc").val(StringUtil.escapeStr(user.displayName));
					}
				});
				AlertUtil.hideModalFooterMessage();
				
    		});
			
			$('#shPzrmc').bind("dblclick",function(event){
				event.preventDefault();
				Personel_Tree_Multi_Modal.show({
					checkUserList:[{id:$("#saveForm #"+"shPzrid").val(),displayName:$("#saveForm #shPzrmc").val()}],//原值，在弹窗中回显
					dprtcode:$("#dprtcode_search").val(),//
					multi:false,
					callback: function(data){//回调函数
						var user = $.isArray(data)?data[0]:{id:'',displayName:''};
						$("#saveForm #shPzrid").val(user.id);
						$("#saveForm #shPzrmc").val(StringUtil.escapeStr(user.displayName));
					}
				});
				AlertUtil.hideModalFooterMessage();
				 
	    	});
			
		},
		loadPlans: function() {
			var dprtcode  = $('#dprtcode_search').val();
			dprtcode  = dprtcode ? dprtcode : userJgdm;
			var param = {dprtcode : dprtcode};
			$('#div_qh_scorll').empty();
			 
			AjaxUtil.ajax({
				cache:false,
				url : basePath + "/aircraftinfo/airworthiness/plans",
				type : "post",
				dataType : "json",
				data : param,
				async : false,
				success : function(data) {
					if (data && data.length > 0) {
						 //过滤出机构，飞机注册号匹配的飞机
					    var list = $.grep(data,function(plan,idx){
					    		for(var i = 0; i < userACRegList.length; i++) {
						    		 if((userACRegList[i].DPRTCODE == plan.dprtcode) 
						    				 && (userACRegList[i].FJZCH == plan.fjzch)) {
						    			 return true;
						    		 }
						    	 }
						    	 return false;
					    });
					    
					    //生成飞机适航状态，显示
					    if(list && (list.length > 0)) {
					    	var item  = '';
					    	$.each(list,function(idx,plan){
					    		
					    		if(idx==0){
					    			item += "<a href=\"javascript:void(0);\"   onmouseout=\"airworthiness_manage.onmouseoutPlan(this)\"  onmouseover=\"airworthiness_manage.onmouseoverPlan(this)\"  onclick=\"airworthiness_manage.clickPlan(this)\" id=\""+plan.fjzch+"\" class=\"div_qh  col-xs-12 color_success\">";
					    		}
					    		else{
					    			item += "<a href=\"javascript:void(0);\"  onmouseout=\"airworthiness_manage.onmouseoutPlan(this)\"  onmouseover=\"airworthiness_manage.onmouseoverPlan(this)\"  onclick=\"airworthiness_manage.clickPlan(this)\"  id=\""+plan.fjzch+"\" class=\"div_qh  col-xs-12 \">";
					    		}
					    		
						    	item += "<p><b>"+formatUndefine(StringUtil.escapeStr(plan.fjzch)) +"</b><b>"+formatUndefine(StringUtil.escapeStr(plan.xlh)) +"</b></p>";
						    	item += "<p><em>"+formatUndefine(StringUtil.escapeStr(plan.bzm)) +"</em><em>"+formatUndefine(StringUtil.escapeStr(plan.jdms)) +"</em></p>";
						    	item += "<p><em>停场时间 :</em>"+(formatUndefine(StringUtil.escapeStr((plan.bshRq || '').substring(0,16))) ||'')+" </p>";
						    	item += "<p><em>适航时间 :</em>"+(formatUndefine(StringUtil.escapeStr((plan.shRq || '').substring(0,16))) ||'')+" </p>";
						    	
						    	if(plan.shzt == 0) {
						    		item += "<i><img src=\""+basePath+"/static/assets/img/right.png\" alt=\"ok\"></i>";
						    	}
						    	else{
						    		item += "<i><img src=\""+basePath+"/static/assets/img/wrong.png\" alt=\"finished\"></i>";
						    	}
						    	item += "</a>";
					    	})
					    	$('#div_qh_scorll').append(item);
					    }
					}
					
				}
		    });
			
		},
		/** 更新飞机*/
		updatePlan: function(fjzch) {
			 
			var dprtcode  = $('#dprtcode_search').val();
			dprtcode  = dprtcode ? dprtcode : userJgdm;
			var param = {dprtcode : dprtcode};
			AjaxUtil.ajax({
				cache:false,
				url : basePath + "/aircraftinfo/airworthiness/plans",
				type : "post",
				dataType : "json",
				data : param,
				async : false,
				success : function(data) {
					if (data && data.length > 0) {
						 //过滤出机构，飞机注册号匹配的飞机
					    var list = $.grep(data,function(plan,idx){
					    		for(var i = 0; i < userACRegList.length; i++) {
						    		 if((userACRegList[i].DPRTCODE == plan.dprtcode) 
						    				 && (userACRegList[i].FJZCH == plan.fjzch)) {
						    			 return true;
						    		 }
						    	 }
						    	 return false;
					    });
					    
					    
					    if(list && (list.length > 0)) {
					    	 for(var i = 0; i < list.length; i++){
					    		 if(list[i].fjzch == fjzch){
					    			    var item  = '';
					    			    var plan = list[i];
					    			    item += "<p><b>"+formatUndefine(StringUtil.escapeStr(plan.fjzch)) +"</b><b>"+formatUndefine(StringUtil.escapeStr(plan.xlh)) +"</b></p>";
								    	item += "<p><em>"+formatUndefine(StringUtil.escapeStr(plan.bzm)) +"</em><em>"+formatUndefine(StringUtil.escapeStr(plan.jdms)) +"</em></p>";
								    	item += "<p><em>停场时间 :</em>"+(formatUndefine(StringUtil.escapeStr((plan.bshRq || '').substring(0,16))) ||'')+" </p>";
								    	item += "<p><em>适航时间 :</em>"+(formatUndefine(StringUtil.escapeStr((plan.shRq || '').substring(0,16))) ||'')+" </p>";
								    	if(plan.shzt == 0) {
								    		item += "<i><img src=\""+basePath+"/static/assets/img/right.png\" alt=\"ok\"></i>";
								    	}
								    	else{
								    		item += "<i><img src=\""+basePath+"/static/assets/img/wrong.png\" alt=\"finished\"></i>";
								    	}
								       $('.div_qh.color_success ').empty();	 
					    			   $('.div_qh.color_success ').html(item);
					    			  
					    			 break;
					    		 }
					    	 }
					    }
					   
					    
					    
					}
					
				}
		    });
			
		},
		/**加载飞机适航列表*/
		search: function() {
			this.goPage(1,"auto","");
		},
		goPage: function(pageNumber,sortType,sortField){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sortField);
		},
		AjaxGetDatasWithSearch: function (pageNumber,sortType,sortField){
			 var _this = this;
			 var fjzch = $('.div_qh.color_success').attr('id');
			 var param = {};
			 param.fjzch =  fjzch;
			 /* $.trim($("#fjzch").val()); */
			 param.dprtcode = $.trim($("#dprtcode_search").val());
			 
			 param['pagination.sort'] = sortField;				    //排序列字段名
			 param['pagination.order'] = sortType;					//排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'
			 param['pagination.page'] = pageNumber;				    //页数
			 param["pagination.rows"] = 8;		
			 
			 if(id!=undefined && id != ''){
				 param['id'] = id;
			 }
			startWait();
			AjaxUtil.ajax({
				
			   url:basePath+"/aircraftinfo/airworthiness/queryList",
			   type: "post",
			   data:param,
			   async: true,
			   success:function(data){
			    finishWait();
				   if(parseInt(data.total) >0){
					   $('.more').show();
					   $('#hasResult').val('true');
					   $("#list").empty();
					   _this.appendContentHtml(data.rows);
				   } else {
					  $('.more').hide();
					  $("#list").empty();
					  $("#pagination").empty();
					  $("#list").append("<tr><td style=\"vertical-align: middle;\"  colspan=\"9\">暂无数据 No data.</td></tr>");
				   }
				   refreshPermission();
		      } 
		    }); 
		 },
		 appendContentHtml: function (list){
			   var htmlContent = '';
			   $.each(list,function(index,row){
				   if (index % 2 == 0) {
					   htmlContent = htmlContent + "<tr onClick=\"airworthiness_manage.edit('"+ row.id + "')\"  bgcolor=\"#f9f9f9\">";
				   } else {
					   htmlContent = htmlContent + "<tr onClick=\"airworthiness_manage.edit('"+ row.id + "')\"   bgcolor=\"#fefefe\">";
				   }

				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"   class='text-center'>"
				   htmlContent = htmlContent + "<i class='text-center icon-edit  color-blue cursor-pointer checkPermission' permissioncode='aircraftinfo:airworthiness:saveOrUpdate' onClick=\"airworthiness_manage.edit('"+ row.id + "')\" title='编辑 Edit'></i>&nbsp;&nbsp;"
				   htmlContent = htmlContent + "<i class='text-center icon-trash color-blue cursor-pointer checkPermission' permissioncode='aircraftinfo:airworthiness:cancel' onClick=\"airworthiness_manage.cancel('"+ row.id + "')\" title='删除 Delete'></i>"
				   htmlContent = htmlContent +"</td>"; 
				   
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+formatUndefine(index+1)+"</td>"; 
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+formatUndefine(StringUtil.escapeStr(row.lsh)  )+"</td>"; 
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+formatUndefine((row.bshRq || '').substring(0,16))  +"</td>"; 
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+formatUndefine((row.shRq || '').substring(0,16)) +"</td>"; 
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.bshSpdh) ) +"'>"+formatUndefine(StringUtil.escapeStr(row.bshSpdh))+"</td>";
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.shSpdh)  ) +"'>"+formatUndefine(StringUtil.escapeStr(row.shSpdh))+"</td>"; 
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' >"+tclxText[row.bshyy]+"</td>"; 
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' >"+StringUtil.escapeStr(row.tcyy)+"</td>"; 
				   
				   htmlContent = htmlContent + "</tr>";  
				    
			   });
			   $("#list").empty();
			   $("#list").html(htmlContent);
			  
			   var keyword = $.trim($("#keyword").val());
			   signByKeyword(keyword, [2,3,4,5,6,7,8,12,15,17]);
		 },
		/**字段排序*/
		orderBy: function (sortField) {
			var sortColum = $("#" + sortField + "_order");
			var orderStyle = sortColum.attr("class");
			$("th[id$=_order]").each(function() { //重置class
				$(this).removeClass().addClass("sorting");
			});
			sortColum.removeClass("sorting");
			if (orderStyle == "sorting_asc") {
				sortColum.addClass("sorting_desc");
			} else {
				sortColum.addClass("sorting_asc");
			}
			orderStyle = $("#" + sortField + "_order").attr("class");
			var currentPage = $("#pagination li[class='active']").text();
			currentPage = currentPage==''?1:currentPage;
			goPage(currentPage,orderStyle.split("_")[1],sortField);
		},
		/**加载一个适航状态*/
		loadOne: function(){
			
		},
		/**加载部分适航状态*/
		loadlist4Part:function(){
			
		},
		/**加载数据到编辑区*/
		 edit: function (id){
			 this.clearForm();
			 AjaxUtil.ajax({
				   url:basePath+"/aircraftinfo/airworthiness/load",
				   type: "get",
				   cache:false,
				   data:{id:id},
				   async: true,
				   success:function(data){ 
					   if(data !=null){
						   
						   $('#saveForm #bshYjrq').val(formatUndefine((data.bshYjrq || '').substring(0,10)));
						   $('#saveForm #bshSjrq').val(formatUndefine((data.bshSjrq || '').substring(0,10)));
						   
						   
						   $('#saveForm #bshYjsj').val(formatUndefine((data.bshYjrq || '').substring(11,16)));
						   $('#saveForm #bshSjsj').val(formatUndefine((data.bshSjrq || '').substring(11,16)));
						   
						   $('#saveForm #bshSpdh').val(formatUndefine(data.bshSpdh) );
						   $('#saveForm #bshPzrid').val(formatUndefine(data.bshPzrid));
						   $('#saveForm #bshPzrmc').val(formatUndefine(data.bshpzrName) );
						   
						   $('#saveForm #shYjrq').val(formatUndefine((data.shYjrq || '').substring(0,10)));
						   $('#saveForm #shSjrq').val(formatUndefine((data.shSjrq || '').substring(0,10)));
						   
						   $('#saveForm #shYjsj').val(formatUndefine((data.shYjrq || '').substring(11,16)));
						   $('#saveForm #shSjsj').val(formatUndefine((data.shSjrq || '').substring(11,16)));
						   
						   
						   $('#saveForm #shSpdh').val(formatUndefine(data.shSpdh) );
						   $('#saveForm #shPzrid').val(formatUndefine(data.shPzrid));
						   $('#saveForm #shPzrmc').val(formatUndefine(data.shpzrName) );
						   
						   $('#saveForm #id').val(formatUndefine(data.id));
						   
						   $('.sh_list #zdsj').text(formatUndefine(data.whsj));
						   $('.sh_list #zdrrealname').text(formatUndefine(data.zdrrealname) );
						   $('.sh_list #lsh').text(formatUndefine(data.lsh) ); 
						   
						   $('#saveForm #bshyy').val(formatUndefine(data.bshyy));
						   $('#saveForm #tcyy').val(formatUndefine(data.tcyy));
						   $('#saveForm #bz').val(formatUndefine(data.bz));
						   
					   }
						
				   } 
		    });
		 },
		 
		 /**删除*/
		 cancel: function (id){
			 var callBackOption = {body:window}
			 AlertUtil.showConfirmMessage("确定要删除吗？",{
					 option:{id:id}
					 ,callback:function(option){
						 AjaxUtil.ajax({
							   url:basePath+"/aircraftinfo/airworthiness/cancel",
							   type: "post",
							   data:{id:id},
							   async: true,
							   success:function(data){ 
								   AlertUtil.showMessageCallBack({
									   option:callBackOption
										,message:"删除适航指令成功!"
										,callback:function(option){
											 /* $('#curheight').val($('#div_qh_scorll').scrollTop()); */
											 $('.div_qh.color_success').click();
										}	
									});
								   
							   } 
						    });  
					 }
			 });
		 },
		 /**更多*/
		 more: function(){
			 if( $('#hasResult').val() =='true'){
				 var fjzch = $('.div_qh.color_success').attr('id');
				 var dprtcode = $('#dprtcode_search').val();
				 var param = {fjzch:fjzch,dprtcode:dprtcode};
				 popModal.show(param);
			 }
		 },
		 airworthinessStatusToggle: function (obj){
				var i = $(obj);
				if(i.hasClass("icon-caret-left")){
					i.removeClass("icon-caret-left").addClass("icon-caret-right");
					$("#left_div").hide();
					$("#right_div").removeClass("col-sm-9").addClass("col-sm-12");
				}else{
					i.removeClass("icon-caret-right").addClass("icon-caret-left");
					$("#left_div").show();
					$("#right_div").removeClass("col-sm-12").addClass("col-sm-9");
				}
				App.resizeHeight();
		},
	
		 
}

var popModal = {
		 param:{id:'searchModal'}
		 ,goPage:function(pageNumber,sortType,sortField){
				this.AjaxGetDatasWithSearch(pageNumber,sortType,sortField);
		 }	
		 ,show: function(option){
			 
			 $.extend(this.param, option);
			 $('#searchModal #keyword').val('');
			 $('#searchModal').modal('show');
			 this.search();
		 }
		 //信息检索
		,search:function(){
				this.goPage(1,"auto","");
		}
	    //搜索条件重置
		,searchreset:function (){
			 $("#divSearch").find("input").each(function() {
				$(this).attr("value", "");
			});
			
			 $("#divSearch").find("textarea").each(function(){
				 $(this).val("");
			 })
			 
			  $("#divSearch").find("select").each(function(){
				 $(this).val("");
			 })
			 
			//初始化日志功能
			logModal.clearParam();
			logModal.init({code:'B_S_014'});
		 }
		
		,AjaxGetDatasWithSearch:function (pageNumber,sortType,sortField){
			var _this = this;
			 var param = {};
			 param.fjzch = $.trim(_this.param.fjzch);
			 param.dprtcode = $.trim(_this.param.dprtcode);
			 param.keyword = $.trim($("#"+this.param.id+" #divSearch  #keyword").val());
			 param['pagination.sort'] = sortField;				    //排序列字段名
			 param['pagination.order'] = sortType;					//排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'
			 param['pagination.page'] = pageNumber;				    //页数
			 param["pagination.rows"] = 10;		
			 
			startWait();
			AjaxUtil.ajax({
			   url:basePath+"/production/airworthinessdirective/queryPage",
			   type: "post",
			   data:param,
			   async: false,
			   success:function(data){
			    finishWait();
				   if(data!=null && data.rows.length >0){
					  popModal.appendContentHtml(data.rows);
					  
					 data.pageable = data.pagination;
					var page_ = new Pagination({
						renderTo : "air_pagination",
						data : data,
						sortColumn : sortField,
						orderType : sortType,
						extParams : {},
						goPage : function(a, b, c) {
							popModal.AjaxGetDatasWithSearch(a, c, b);
						}// ,
					// controller: this_
					});
					
				   } else {
					  $("#"+popModal.param.id+" #list").empty();
					  $("#"+popModal.param.id+" #air_pagination").empty();
					  $("#"+popModal.param.id+" #list").append("<tr><td style=\"vertical-align: middle;\"  colspan=\"16\">暂无数据 No data.</td></tr>");
				   }
				   refreshPermission();
				  signByKeyword($('#keyword').val(), [ 7,8, 9, 10], "#"+popModal.param.id+" #list tr td")
		      } 
		    }); 
			
		 }
		 ,appendContentHtml:function (list){
			   var htmlContent = '';
			   $.each(list,function(index,row){
				   if (index % 2 == 0) {
					   htmlContent = htmlContent + "<tr    bgcolor=\"#f9f9f9\">";
				   } else {
					   htmlContent = htmlContent + "<tr     bgcolor=\"#fefefe\">";
				   }

				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+formatUndefine(index+1)+"</td>";
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+formatUndefine(StringUtil.escapeStr(row.lsh))+"</td>";
				   
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+window.formatUndefine((row.bshYjrq || '').substring(0,16) )+"</td>"; 
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+window.formatUndefine((row.bshSjrq || '').substring(0,16) )+"</td>"; 
				   
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+window.formatUndefine((row.shYjrq || '').substring(0,16) )+"</td>"; 
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+window.formatUndefine((row.shSjrq || '').substring(0,16) )+"</td>"; 
				   
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' title='"+window.formatUndefine(StringUtil.escapeStr(row.bshSpdh)) +"'>"+window.formatUndefine(StringUtil.escapeStr(row.bshSpdh))+"</td>";
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' title='"+window.formatUndefine(StringUtil.escapeStr(row.shSpdh)) +"'>"+window.formatUndefine(StringUtil.escapeStr(row.shSpdh))+"</td>"; 
				   
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left'>"+window.formatUndefine(StringUtil.escapeStr(row.shpzrName)  )+"</td>";
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left'>"+window.formatUndefine(StringUtil.escapeStr(row.bshpzrName)  )+"</td>";
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' >"+tclxText[row.bshyy]+"</td>"; 
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' >"+StringUtil.escapeStr(row.tcyy)+"</td>"; 
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>"; 

				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left'>"+window.formatUndefine(StringUtil.escapeStr(row.zdrrealname)  )+"</td>";
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left'>"+window.formatUndefine(row.whsj)+"</td>"; 
				   
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left'>"+window.formatUndefine(StringUtil.escapeStr(row.dprtname)  )+"</td>";
				   htmlContent = htmlContent + "</tr>";  
				    
			   });
			   $("#"+this.param.id+" #list").empty();
			   $("#"+this.param.id+" #list").html(htmlContent);
			 
		 }
		 
		//字段排序
		,orderBy:function (sortField) {
		 
			var sortColum = $("#"+this.param.id+" #" + sortField + "_order");
			var orderStyle = sortColum.attr("class");
			$("#"+this.param.id+" th[id$=_order]").each(function() { //重置class
				$(this).removeClass().addClass("sorting");
			});
			sortColum.removeClass("sorting");
			if (orderStyle == "sorting_asc") {
				sortColum.addClass("sorting_desc");
			} else {
				sortColum.addClass("sorting_asc");
			}
			orderStyle = $("#"+this.param.id+" #" + sortField + "_order").attr("class");
			var currentPage = $("#"+this.param.id+" #pagination li[class='active']").text();
			currentPage = currentPage==''?1:currentPage;
			this.goPage(currentPage,orderStyle.split("_")[1],sortField);
		}
		 
};
function customResizeHeight(bodyHeight, tableHeight){
	$("#airworthinessstatus-leftBody").css({"height":(bodyHeight+17)+"px"})
	var divSave=$("#divSave").outerHeight();
	var historyHeight=$("#panelBodyHeight .panel-heading").outerHeight();
	var panelBodyHeight=bodyHeight-divSave-historyHeight;
	/*$("#panelBodyHeight .panel-body").css({"min-height":"40px","max-height":panelBodyHeight+"px"})*/
	$("#airworthinessstatus-body").css({"height":(bodyHeight+17)+"px","overflow":"auto"});
	  // 浮动日志div高度
    $("#floatDiv").height($(".page-content:first>div").height()-13);
    try{
    	$("#logTableDiv").height($("#log_pagination").offset().top-$("#logTableDiv").offset().top - 5);
    }catch(e){}
	
}


