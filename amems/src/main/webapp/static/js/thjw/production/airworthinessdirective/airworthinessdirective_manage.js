 var popModal = {
		 param:{id:'searchModal'}
		 ,goPage:function(pageNumber,sortType,sortField){
				this.AjaxGetDatasWithSearch(pageNumber,sortType,sortField);
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
 			 var param = {};
 			 param.fjzch = $.trim($("#fjzch").val());
 			 param.dprtcode = $.trim($("#dprtcode_search").val());
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
 					  $("#"+popModal.param.id+" #list").append("<tr><td style=\"vertical-align: middle;\"  colspan=\"8\">暂无数据 No data.</td></tr>");
 				   }
 				   refreshPermission();
 				  signByKeyword($('#keyword').val(), [ 7,8, 9, 10])
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
 				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' >"+stopeReasonDict[row.bshyy]+"</td>"; 
 				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>"; 

 				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left'>"+window.formatUndefine(StringUtil.escapeStr(row.zdrrealname)  )+"</td>";
 				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left'>"+window.formatUndefine(row.zdsj)+"</td>"; 
				   
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
 //停场原因
 var stopeReasonDict = ["","定检原因","故障原因","缺件原因(航材、工具)","机组原因","其他原因"];
 
	$(document).ready(function(){
		
		Navigation(menuCode);
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
		
		
		
		
		
		  $('#more').hide();
		  $("#list").empty();
		  $("#pagination").empty();
		  $("#list").append("<tr><td style=\"vertical-align: middle;\"  colspan=\"8\">暂无数据 No data.</td></tr>");
		  
		 
		 /*$('#saveForm #bshSjrq').datetimepicker({
			 lang:"ch", 
			 validateOnBlur:true,
			 format:'Y-m-d H:00'
		 });
		 $('#saveForm #shSjrq').datetimepicker({
			 lang:"ch", 
			 validateOnBlur:true,
			 format:'Y-m-d H:00'
		 });*/
		 
		 /*$('#saveForm #bshYjrq').datetimepicker({
			 lang:"ch", 
			 validateOnBlur:true,
			 format:'Y-m-d H:00'
		 });*/
		 
		/* $('#saveForm #shYjrq').datetimepicker({
			    lang:"ch", 
			    validateOnBlur:true,
			    format:'Y-m-d H:00'
			 }); */ 
	 
		 /*$('#saveForm #bshYjrq').on('blur', function(e) { 
		        $('#saveForm').data('bootstrapValidator')  
		        .updateStatus('bshYjrq', 'NOT_VALIDATED',null)  
		        .validateField('bshYjrq'); 
		        
		        $('#saveForm').data('bootstrapValidator')  
		        .updateStatus('bshSjrq', 'NOT_VALIDATED',null)  
		        .validateField('bshSjrq'); 
		});
		 
		 $('#saveForm #bshSjrq').on('blur', function(e) { 
			 $('#saveForm').data('bootstrapValidator')  
		        .updateStatus('bshSjrq', 'NOT_VALIDATED',null)  
		        .validateField('bshSjrq'); 
			 
			 $('#saveForm').data('bootstrapValidator')  
		        .updateStatus('bshYjrq', 'NOT_VALIDATED',null)  
		        .validateField('bshYjrq'); 
		});
		 
		
		 
		 $('#saveForm #shYjrq').on('blur', function(e) { 
		        $('#saveForm').data('bootstrapValidator')  
		        .updateStatus('shYjrq', 'NOT_VALIDATED',null)  
		        .validateField('shYjrq'); 
		        
		        $('#saveForm').data('bootstrapValidator')  
		        .updateStatus('shSjrq', 'NOT_VALIDATED',null)  
		        .validateField('shSjrq'); 
		});
		 
		 $('#saveForm #shSjrq').on('blur', function(e) { 
			    $('#saveForm').data('bootstrapValidator')  
		        .updateStatus('shSjrq', 'NOT_VALIDATED',null)  
		        .validateField('shSjrq'); 
			 
		        $('#saveForm').data('bootstrapValidator')  
		        .updateStatus('shYjrq', 'NOT_VALIDATED',null)  
		        .validateField('shYjrq'); 
		});*/
		 
		 
		/* $('input.date-range-picker').daterangepicker({
			 format : 'YYYY-MM-DD HH:mm',
			 
			 autoUpdateInput: false,
			 autoApply: true,
			 locale : {  
                 applyLabel : '确定',  
                 cancelLabel : '清除',  
                 fromLabel : '起始时间',  
                 toLabel : '结束时间',  
                 customRangeLabel : '自定义',  
                 daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
                 monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
                         '七月', '八月', '九月', '十月', '十一月', '十二月' ],  
                 firstDay : 1  
             }  
		 }).prev().on("click", function() {
				$(this).next().focus();
		 });
		 
		 $('input.date-range-picker').on('cancel.daterangepicker', function(ev, picker) {
             $(this).val('');
         });*/
		 
		$('.div_qh').on('click',function(){
			var fjzch = $(this).attr('id');
			$('#fjzch').val(fjzch);
			$('#curfjzch').val(fjzch);
			$('#curheight').val($('#div_qh_scorll').scrollTop());
		 
			goPage(1,"auto","");//开始的加载默认的内容 
			$.each($(this).siblings(),function(){
				$(this).removeClass('color_success').removeClass('color_gery').addClass('color_white');
			})
			
			$(this).addClass('color_success');
			$('#btnClear').trigger('click');
			
			//reloadPersons();
			
		});
		
		$('.div_qh').on('mouseover',function(){
			if(!$(this).hasClass("color_success")){
				$(this).removeClass('color_white').addClass('color_gery');
			}
		});
		  
		$('.div_qh').on('mouseout',function(){
			if($(this).attr('id') != $('#fjzch').val()){
				$(this).removeClass('color_gery').addClass('color_white');
			}
		});
 
		var curheight =  $('#curheight').val()
		if($('#curfjzch').val() == ''){
			$('.div_qh:first').trigger('click');
		}
		else{ 
			var arrayqh = $('.div_qh').filter(function(idx,item){
				if($(item).attr('id') == $('#curfjzch').val()){
					 return true;
				}
			});
			if(arrayqh.length>0){
				$(arrayqh[0]).trigger('click');
			}
			else{
				$('.div_qh:first').trigger('click');
			}
		}
		
		$('#div_qh_scorll').scrollTop ( parseInt(curheight));
		
		/**
		 * 显示/隐藏 form
		 */
		$('#more').click(function(){
			if( $('#hasResult').val() =='true'){
				$('#searchModal').modal('show');
				popModal.search();
			}
		});
		
		/**
		 * 清理form数据
		 */
		$('#btnClear').click(function(){
			displaySaveForm()
		});
		
		
		$('#dprtcode_search').on('change',function(){
			$('#dprtcode').val($(this).val())
			$('#curfjzch').val('')
			$('#aftersaveForm').submit();
		});
		
		/**
		 * 保存适航指令
		 */
		$('#btnSave').click(function(){
			if(validationForm()){
				if($.trim($('#curfjzch').val()) == ''){
					AlertUtil.showErrorMessage("请选择指定飞机");
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
						'fjzch' : $.trim($('#fjzch').val())//适航批准人
					}
				  $('#dprtcode').val($('#dprtcode_search').val());
				  var body = window
				  var saveUrl  = basePath+"/production/airworthinessdirective/saveOrUpdate";
				   
				  // 提交数据
				  AjaxUtil.ajax({
					  async: false,
						url:saveUrl,
						type:"post",
						data:data, 
					    dataType:"json",      
						success:function(data){
							$('#aftersaveForm #id').val(data.id);
							AlertUtil.showMessageCallBack({
								message:"保存适航指令成功"
								,callback:function(){
									$('#aftersaveForm').submit();
								}	
							});
						}
				 });
			}
			
		});
		 
		
		//初始化日志功能
		logModal.init({code:'B_S_014'});
		
		
		$('#bshBtn').bind("click",function(event){
				event.preventDefault();
		        bshPzr.selectUser1();
	    });
		
		$('#shBtn').bind("click",function(event){
			event.preventDefault();
			shPzr.selectUser1();
    });
		
		
	});

	function validationForm(){
		
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
			AlertUtil.showErrorMessage('计划停场时间,实际停场时间至少需要填写一个');
			return false; 
		}
		
		result = $.trim($('#saveForm #shYjrq').val())!='' ||  $.trim($('#saveForm #shSjrq').val())!='';
		if(!result){
			AlertUtil.showErrorMessage('计划适航时间,实际适航时间至少需要填写一个');
			return false; 
		}
		
		
		var reg = /^[^\u4e00-\u9fa5]{0,20}$/; 
		var r = bshSpdh.match(reg); 
		if(r==null) {
			$('#bshSpdh').focus();
			AlertUtil.showErrorMessage('不适航审批单号不包含中文且长度不超过20个字符');
			return false; 
		}
		
		var r = shSpdh.match(reg); 
		if(r==null) {
			$('#shSpdh').focus();
			AlertUtil.showErrorMessage('适航审批单号不包含中文且长度不超过20个字符');
			return false; 
		}
		if(bz.length>300){
			$('#bz').focus();
			AlertUtil.showErrorMessage('备注不超过300个字符');
			return false;
		}
		
		return result;
		
	}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sortField){
		 var param = {};
		 param.fjzch = $.trim($("#fjzch").val());
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
			
		   url:basePath+"/production/airworthinessdirective/queryList",
		   type: "post",
		   data:param,
		   async: false,
		   success:function(data){
			   
		    finishWait();
			   if(parseInt(data.total) >0){
				   $('#more').show();
				   $('#hasResult').val('true');
				   $("#list").empty();
				   appendContentHtml(data.rows);
			   } else {
				  $('#more').hide();
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td style=\"vertical-align: middle;\"  colspan=\"8\">暂无数据 No data.</td></tr>");
			   }
			   refreshPermission();
	      } 
	    }); 
		
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr onClick=\"edit('"+ row.id + "')\"  bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr onClick=\"edit('"+ row.id + "')\"   bgcolor=\"#fefefe\">";
			   }

			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"   class='text-center'>"
			   htmlContent = htmlContent + "<i class='text-center icon-edit  color-blue cursor-pointer checkPermission' permissioncode='production:airworthinessdirective:saveOrUpdate' onClick=\"edit('"+ row.id + "')\" title='编辑 Edit'></i>&nbsp;&nbsp;"
			   htmlContent = htmlContent + "<i class='text-center icon-trash color-blue cursor-pointer checkPermission' permissioncode='production:airworthinessdirective:cancel' onClick=\"cancel('"+ row.id + "')\" title='作废 Invalid'></i>"
			   htmlContent = htmlContent +"</td>"; 
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+formatUndefine(index+1)+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+formatUndefine(StringUtil.escapeStr(row.lsh)  )+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+formatUndefine((row.bshRq || '').substring(0,16))  +"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center'>"+formatUndefine((row.shRq || '').substring(0,16)) +"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.bshSpdh) ) +"'>"+formatUndefine(StringUtil.escapeStr(row.bshSpdh))+"</td>";
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.shSpdh)  ) +"'>"+formatUndefine(StringUtil.escapeStr(row.shSpdh))+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left' >"+stopeReasonDict[row.bshyy]+"</td>"; 
			   
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		  
		   var keyword = $.trim($("#keyword").val());
		   signByKeyword(keyword, [2,3,4,5,6,7,8,12,15,17]);
	 }
	 
	//字段排序
	function orderBy(sortField) {
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
	}
	
    /**
     * 跳转至指定页数:三个参数依次为:当前页码,排序规则 排序字段,
     * @param pageNumber
     * @param sortType
     * @param sortField
     */ 
	function goPage(pageNumber,sortType,sortField){
		AjaxGetDatasWithSearch(pageNumber,sortType,sortField);
	}	
		 //信息检索
	function search(){
		goPage(1,"auto","");
	}
		
	 //搜索条件重置
	 function searchreset(){
		 $("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		 $("#divSearch").find("textarea").each(function(){
			 $(this).val("");
		 })
		 
		  $("#divSearch").find("select").each(function(){
			 $(this).val("");
		 })
	 }
		
	 //加载数据到编辑区
	 function edit(id){
		 $('#btnClear').trigger('click');
		 displaySaveForm();
		 AjaxUtil.ajax({
			   url:basePath+"/production/airworthinessdirective/load",
			   type: "get",
			   cache:false,
			   data:{id:id},
			   async: false,
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
					   
					   $('.sh_list #zdsj').text(formatUndefine(data.zdsj));
					   $('.sh_list #zdrrealname').text(formatUndefine(data.zdrrealname) );
					   $('.sh_list #lsh').text(formatUndefine(data.lsh) ); 
					   
					   $('#saveForm #bshyy').val(formatUndefine(data.bshyy));
					   $('#saveForm #bz').val(formatUndefine(data.bz));
					   
				   }
					
			   } 
	    });
	 }
	 
	 //作废
	 function cancel(id){ 
		 var callBackOption = {body:window}
		 AlertUtil.showConfirmMessage("是否继续执行该操作",{
				 option:{id:id}
				 ,callback:function(option){
					 AjaxUtil.ajax({
						   url:basePath+"/production/airworthinessdirective/cancel",
						   type: "post",
						   data:{id:id},
						   async: false,
						   success:function(data){ 
							   AlertUtil.showMessageCallBack({
								   option:callBackOption
									,message:"作废适航指令成功"
									,callback:function(option){
										 $('#curheight').val($('#div_qh_scorll').scrollTop());
										 $('#aftersaveForm').submit();
									}	
								});
							   
						   } 
					    });  
				 }
		 });
		 
	 }
		
		function displaySaveForm(){
			$("#divSave").css("display", "block");
			$("#saveForm").find("input").each(function() {
				$(this).attr("value", "");
			});
			
			 $("#saveForm").find("textarea").each(function(){
				 $(this).val("");
			 })
			 
			 $("#saveForm").find("select").each(function(){
				 $(this).val("");
			 })
 			 $("#bshyy").val("1");
			 
			 $('.sh_list #zdsj').text("");
			 $('.sh_list #zdrrealname').text("");
			 $('.sh_list #lsh').text("");
			 
		}
	 
		/**
		 *信息检索
		 */
		function search(){
			goPage(1,"auto","");
		}
	 
		bshPzr = {
				selectUser1:function (){
					var this_ = this;
					 $('#saveForm')
					//调用用户选择弹窗
					userModal.show({
						dprtcode : $('#dprtcode_search').val(),
						selected:$("#saveForm #"+"bshPzrid").val(),//原值，在弹窗中默认勾选
						callback: function(data){//回调函数
							$("#saveForm #bshPzrid").val(data.id);
							$("#saveForm #bshPzrmc").val(StringUtil.escapeStr(data.displayName));
						}
					}) 
				}
		};
		shPzr = {
				selectUser1:function (){
					var this_ = this;
					//调用用户选择弹窗
					userModal.show({
						dprtcode : $('#dprtcode_search').val(),
						selected:$("#saveForm #"+"shPzrid").val(),//原值，在弹窗中默认勾选
						callback: function(data){//回调函数
							$("#saveForm #shPzrid").val(data.id);
							$("#saveForm #shPzrmc").val(StringUtil.escapeStr(data.displayName));
						}
					}) 
				}
		};