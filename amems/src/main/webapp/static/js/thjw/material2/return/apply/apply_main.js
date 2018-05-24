$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				}
		);
		$(".date-picker").datepicker({
			autoclose : true,
			clearBtn : true
		}).on('hide', function(e) {
			$('#form').data('bootstrapValidator')  
			.updateStatus('tlrq', 'NOT_VALIDATED',null)  
			.validateField('tlrq');  
		});
		$('#bjh').on('change', function(e) {
			$('#form').data('bootstrapValidator')  
			.updateStatus('bjh', 'NOT_VALIDATED',null)  
			.validateField('bjh');   
		});
		$('#bjmc').on('change', function(e) {
			$('#form').data('bootstrapValidator')  
			.updateStatus('bjmc', 'NOT_VALIDATED',null)  
			.validateField('bjmc');   
		});
		var dprtcode=$("#dprtcode_search").val();
		//飞机注册号
		var planeRegOption = initfjzch(dprtcode);
		planeRegOption ="<option value=''>显示全部 All</option>" + planeRegOption;
		$("#fjzch_search").html(planeRegOption); 
		//加载数据
		applyMain.reload();
		//验证
		validation();
	});
//验证
function validation(){
 $('#form').bootstrapValidator({
	        message: '数据不合法',
	        excluded:[":disabled"],  
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	tlrq: {
	                validators: {
	                	notEmpty: {
	                        message: '退料日期不能为空'
	                    }
	                }
	            },
	            bjh: {
	                validators: {
	                    notEmpty: {
	                        message: '部件号不能为空'
	                    }
	                }
	            },
	            bjmc: {
	                validators: {
	                    notEmpty: {
	                        message: '部件名称不能为空'
	                    }
	                }
	            },
	           tlsl: {
	                validators: {
	                	 notEmpty: {
		                        message: '退料数量不能为空'
		                    },
	                    regexp: {
	                    	  regexp: /^[0-9]{1,10}(\.[0-9]{0,2})?$/,
	 	                      message: '退料数量只能输入数字'
		                }
	                }
	            },
	           pch: {
	                validators: {
	                    regexp: {
	                    	regexp: /^[\x00-\xff]*$/,
	 	                      message: '批次号不能输入中文及中文符号'
		                }
	                }
	            },
	           xlh: {
	                validators: {
	                    regexp: {
	                    	regexp: /^[\x00-\xff]*$/,
	 	                      message: '序列号不能输入中文及中文符号'
		                }
	                }
	            }
	        }
	    });
}
	
	//切换组织机构
function dprtChange(){
	var planeRegOption = initfjzch($("#dprtcode_search").val());
	planeRegOption ="<option value=''>显示全部 All</option>" + planeRegOption;
	$("#fjzch_search").empty();
	$("#fjzch_search").html(planeRegOption); 
}
//飞机搜索框
function initfjzch(dprtcode){
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode});
	var planeRegOption = "";
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"'>"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
		});
	}else{
		planeRegOption += "<option value='-1' >"+"暂无飞机"+"</option>";
	}
	return planeRegOption;
	
}

	function moreSearch(){
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
    }
	function applyOpen(){
		$("#modalName").html("新增");
		$("#modalEname").html("Add");
		//初始化弹窗
		initModal();
		$("#apply_open_alert").modal("show");
	}
	function edit(id){
		 AjaxUtil.ajax({
			 url:basePath+"/material/return/selectById",
			 type:"post",
			 async: false,
			 data:{
				 'id' : id
			 },
			 dataType:"json",
			 success:function(data){
				 finishWait();
				 setObj(data);
			 }
		 });
	}
	function setObj(obj){
		$("#modalName").html("修订");
		$("#modalEname").html("Revision");
		
		if(obj == null){
			AlertUtil.showErrorMessage("该数据不存在");
			return false;
		}
		//初始化弹窗
		initModal(obj.dprtcode);
		validation();
		$("#rejectedMaterialId").val(obj.id);
		$("#tlrq").val(formatUndefine(obj.tlrq.substring(0,10)));
		$("#tlr").val(formatUndefine(obj.tlr?obj.tlr.displayName:''));
		$("#bjh").val(formatUndefine(obj.bjh));
		$("#bjmc").val(formatUndefine(obj.bjmc));
		$("#pch").val(formatUndefine(obj.pch));
		$("#xlh").val(formatUndefine(obj.xlh));
		$("#tlsl").val(formatUndefine(obj.tlsl));
		$("#tlsl_hide").val(formatUndefine(obj.paramsMap?obj.paramsMap.kcsl:''));
		$("#dw").val(formatUndefine(obj.dw));
		$("input:radio[name='bjly_radio'][value='"+obj.bjly+"']").attr('checked',true);
		$("input:radio[name='sfky_radio'][value='"+obj.bjly+"']").attr('checked',true);
		$("#fjzch").val(formatUndefine(obj.fjzch));
		$("#wllb").val(formatUndefine(obj.wllb));
		$("#sm").val(formatUndefine(obj.sm));
		$("#bjid").val(formatUndefine(obj.bjid));
		$("#kcid").val(formatUndefine(obj.wckcid));
		if(obj.bjly == 2){
			$("#fjzch").css("visibility","visible");
		}else{
			$("#fjzch").css("visibility","hidden");
		}
		$("#apply_open_alert").modal("show");
		
		if(obj.wckcid){
			bjDisabled(1);
			return false;
		}
		if(obj.bjid){
			bjDisabled(2);
			return false;
		}
		if(!obj.wckcid   && !obj.bjid && !obj.xlh){
			xlhChange();
		}
	}
	function initModal(dprtcode){
		if(!dprtcode){
			dprtcode = userJgdm;
		}
		//隐藏出现异常的感叹号
		AlertUtil.hideModalFooterMessage();
		$("#apply_open_alert").find("input:text").val("");
		$("#apply_open_alert").find("textarea").val("");
		$("#rejectedMaterialId").val("");
		$("#bjid").val("");
		$("#dprtcode").val("");
		$("#tlr").val(currentUser.displayName);
		//加载飞机注册号
		var planeRegOption = initfjzch(dprtcode);
		$("#dprtcode").val(dprtcode);
		$("#fjzch").html(planeRegOption); 
		//加载单位
		$("#dw").empty();
		DicAndEnumUtil.registerDic('1','dw',dprtcode);
		$("input:radio[name='bjly_radio'][value='1']").attr('checked',true);
		$("input:radio[name='sfky_radio'][value='1']").attr('checked',true);
		$("#fjzch").css("visibility","hidden");
		bjDisabled(3);
	}
	function fjzchHide(){
		var bjly=$("input:radio[name='bjly_radio']:checked").val();
		if(bjly == 1){
			$("#fjzch").css("visibility","hidden");
		}else{
			$("#fjzch").css("visibility","visible");
		}
	}
	
	var applyMain={
			pagination : {},
			gatherSearchParam:function(){
				var searchParam={};
				 var paramsMap ={};
				if (id != "") {
					searchParam.id = id;
					id = "";
				}
				searchParam.fjzch=$("#fjzch_search").val();
				 searchParam.bjly=$("#bjly_search").val();
				 searchParam.sfky=$("#sfky_search").val();
				 paramsMap.dprtcode=$("#dprtcode_search").val();
				 paramsMap.keyword=$.trim($('#keyword_search').val());
				 var isShList = [];
				 var tlrq=$("#tlrq_search").val();
				 //计划使用日期
				 if(null != tlrq && "" != tlrq){
					 var tlrqBegin = tlrq.substring(0,10)+" 00:00:00";
					 var tlrqEnd = tlrq.substring(13,23)+" 23:59:59";
					 paramsMap.tlrqBegin = tlrqBegin;
					 paramsMap.tlrqEnd = tlrqEnd;
				 }else{
					 paramsMap.tlrqBegin = "";
					 paramsMap.tlrqEnd = "";
				 }
				  var isSh1;
				  var isSh2;
				 if($("#isSh1").attr("checked")){
					 isSh1 = 1;
					 paramsMap.isSh1 = isSh1;
					 isShList.push(isSh1);
				}
				 if($("#isSh2").attr("checked")){
					 isSh2 = 1;
					 paramsMap.isSh2 = isSh2;
					 isShList.push(isSh2);
				 }
				 if(isSh1 == 1 && isSh2 == 1){
					 paramsMap.isSh1 = "";
					 paramsMap.isSh2 = "";
				 }
				 paramsMap.isShList = isShList;
				 searchParam.paramsMap=paramsMap;
				 return searchParam;
			},
			goPage:function(pageNumber,sortType,sequence){
				this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
			},
			AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
				 var _this = this;
				 _this.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
				 var searchParam=_this.gatherSearchParam();
				 searchParam.pagination = _this.pagination;
				 
				 if(!searchParam.paramsMap ||searchParam.paramsMap.isShList == null || searchParam.paramsMap.isShList.length <= 0){
					 $("#apply_list").empty();
					 $("#apply_pagination").empty();
					 $("#apply_list").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
					 return false;
				 }
				 if(id != ""){
					 	searchParam.id = id;
						id = "";
					}
				 AjaxUtil.ajax({
					   url:basePath+"/material/return/queryAll",
					   type: "post",
					   contentType:"application/json;charset=utf-8",
					   dataType:"json",
					   data:JSON.stringify(searchParam),
					   success:function(data){
				 			if(data.rows.length > 0){
							 objData=data.rows;
				 				
				 			_this.appendContentHtml(data.rows);
				 			//分页
				 			var page_ = new Pagination({
								renderTo : "apply_pagination",
								data: data,
								sortColumn : sortType,
								orderType : sequence,
								extParams:{},
								goPage: function(a,b,c){
									_this.AjaxGetDatasWithSearch(a,b,c);
								}
							});
				 		// 标记关键字
							   signByKeyword($("#keyword_search").val(),[4,5,6,7,11]);
				 		
				 		}else{
				 			$("#apply_list").empty();
							$("#apply_pagination").empty();
							$("#apply_list").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
							
						}
				 			new Sticky({tableId:'meteriallist_table'});
				 		}
				 });
			},
			reload:function(){
				TableUtil.resetTableSorting("meteriallist_table");
				this.goPage(1,"auto","desc");
			},
			// 表格拼接
			appendContentHtml:function(list){
				var _this = this;
				var htmlContent = '';
				
				$.each(list,function(index,row){
					htmlContent = htmlContent + "<tr >";
					htmlContent += "<td class='fixed-column text-center'>";
				    if(true){
				     htmlContent += "<i class='fa fa-thumb-tack color-blue cursor-pointer' " +
				     "permissioncode='project2:airworthiness:main:02' onClick=\"edit('"+row.id+"')\" title='修订 Revision'></i>&nbsp;&nbsp;";
					  htmlContent += "<i class='icon-off color-blue cursor-pointer' " +
					  "onClick=\"closeRM('"+row.id+"')\" title='关闭  Close'></i>&nbsp;&nbsp;";
				    }
					htmlContent +="</td>";    
				    htmlContent += "<td class='text-center' title='"+(row.tlrq?row.tlrq.substring(0,10):'')+"'>"+(row.tlrq?row.tlrq.substring(0,10):'')+"</td>";
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.tlr?row.tlr.displayName:'')+"'>"+StringUtil.escapeStr(row.tlr?row.tlr.displayName:'')+"</td>";
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bjmc)+"'>"+StringUtil.escapeStr(row.bjmc)+"</td>";
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>";
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.xlh)+"'>"+StringUtil.escapeStr(row.xlh)+"</td>";
				    if(row.bjly == 1){
				    	htmlContent += "<td class='text-center' title='库房'>库房</td>";
				    }else if(row.bjly == 2){
				    	htmlContent += "<td class='text-center' title='飞机'>飞机 "+StringUtil.escapeStr(row.fjzch)+"</td>";
				    }else{
				    	htmlContent += "<td class='text-left' ></td>";
				    }
				    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.tlsl)+"'>"+StringUtil.escapeStr(row.tlsl)+"</td>";
				    if(row.sfky == 1){
				    	htmlContent += "<td class='text-center' title='可用'>可用</td>";
				    }else{
				    	htmlContent += "<td class='text-center' title='不可用'>不可用</td>";
				    }
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.sm)+"'>"+StringUtil.escapeStr(row.sm)+"</td>";
				    
				    htmlContent += "<td class='text-left' >";
				    $.each(row.receivingRelationshiplist,function(index,row1){
				    	if(StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.shr:' ') != ' '){
				    		if(row1.paramsMap?row1.paramsMap.sjkw:'' != ''){
				    			htmlContent += StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.sjkw:'');//
				    		}else{
				    			htmlContent += StringUtil.escapeStr(row.paramsMap?row.paramsMap.ckh:'')+" "+StringUtil.escapeStr(row.paramsMap?row.paramsMap.kwh:'')+" "+StringUtil.escapeStr(row.paramsMap?row.paramsMap.lscfwz:'');//
				    		}
				    		
				    		htmlContent +="; "+StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.sl:'')+"; "+StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.shr:'')+"; "+StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.shrq:'').substring(0,10);
				    		htmlContent += "<br/>";
				    	}
				    });
				    htmlContent += "</td>";
				    
				    htmlContent += "</tr>" ;
			   $("#apply_list").empty();
			   $("#apply_list").html(htmlContent);
			   refreshPermission(); 
			 });
			}
	}
	// 加载航材数据
	function loadMaterialData(){
		AjaxUtil.ajax({
			url:basePath+"/material/material/selectByBjhAndDprcode",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				bjh : $("#bjh").val(),
				dprtcode : $("#dprtcode").val()
			}),
			success:function(data){
				if(data.hCMainData){
					row = data.hCMainData;
					$("#bjid").val(row.id);
					$("#bjmc").val(formatUndefine(row.ywms) +" "+formatUndefine(row.zwms));
					$("#wllb").val(row.hclx);
					$("#dw").val(row.jldw);
					bjDisabled(2);
					xlhChange();
				}else{
					bjDisabled(3);
					xlhChange();
					$("#dw").attr('disabled',false);
					
				}
				$("#tlsl_hide").val('');
				finishWait();
				
		    }
		}); 
	}
	//加载航材
	function loadMaterialAndOutfield(){
		$("#open_win_materialAndOutfield").modal("show");
		var dprtcode = $.trim($("#dprtcode").val());
		var bjid = $.trim($("#bjid").val());
		materialAndOutfield.show({
			selected:bjid,//原值，在弹窗中默认勾选
			dprtcode:dprtcode,
			parentid:'apply_open_alert',
			callback: function(data){//回调函数
				var bjid = '';
				var bjh = '';
				var bjmc='';
				var pch='';
				var xlh='';
				var tlsl='';
				var wllx='';
				var dw='';
				var kcid='';
				var sjly ='';
				var fjzch ='';
				if(data != null){
					if(data.fl){
						bjid = data.bjid;
						bjh = formatUndefine(data.bjh);
						bjmc=formatUndefine(data.ywms)+" "+formatUndefine(data.zwms);
						pch=formatUndefine(data.pch);
						xlh=formatUndefine(data.sn);
						tlsl=formatUndefine(data.kcsl);
						wllx=formatUndefine(data.paramsMap.d1hclx);
						dw = formatUndefine(data.jldw);
						kcid = formatUndefine(data.id);
						sjly = formatUndefine(data.sjly);
						fjzch = formatUndefine(data.fjzch);
						$("#bjmc").val(formatUndefine($.trim(bjmc)));
						$('#bjmc').change();
						bjDisabled(1);
						if(sjly == 1){
							$("input:radio[name='bjly_radio'][value='"+sjly+"']").attr('checked',true);
							$("#fjzch").val(fjzch);
						}
					}else{
						bjid = data.id;
						bjh = formatUndefine(data.bjh);
						bjmc=formatUndefine(data.ywms)+" "+formatUndefine(data.zwms);
						wllx=formatUndefine(data.hclx);
						dw = formatUndefine(data.jldw);
						$("#bjmc").val(formatUndefine($.trim(bjmc)));
						$('#bjmc').change();
						bjDisabled(2);
					}
					
				}
				$("#bjid").val(formatUndefine(bjid));
				$("#bjh").val(formatUndefine(bjh));
				$("#pch").val(formatUndefine(pch));
				$("#xlh").val(formatUndefine(xlh));
				$("#tlsl").val(formatUndefine(tlsl));
				$("#wllb").val(formatUndefine(wllx));
				$("#kcid").val(formatUndefine(kcid));
				$("#dw").val(formatUndefine(dw));
				$("#tlsl_hide").val(formatUndefine(tlsl));
				$('#bjh').change();
				
			}
		});
	}
	function bjDisabled(fl){
		if(fl == 1){
			$("#bjmc").attr('disabled',true);
			$("#pch").attr('disabled',true);
			$("#xlh").attr('disabled',true);
			$("#wllb").attr('disabled',true);
			$("#tlsl").attr('disabled',false);
			$("#fjzch").attr('disabled',true);
			//$("#tlsl").val('1');
			$("#dw").attr('disabled',true);
			$("input:radio[name='bjly_radio']").attr('disabled',true);
		}else if(fl == 2){
			$("#bjmc").attr('disabled',true);
			$("#wllb").attr('disabled',true);
			$("#pch").attr('disabled',false);
			$("#xlh").attr('disabled',false);
			$("#tlsl").attr('disabled',false);
			$("#dw").attr('disabled',true);
			$("input:radio[name='bjly_radio']").attr('disabled',false);
			$("#fjzch").attr('disabled',false);
		}else if(fl == 3){
			$("#bjid").val('');
			$("#kcid").val('');
			$("#bjmc").attr('disabled',false);
			$("#pch").attr('disabled',false);
			$("#xlh").attr('disabled',false);
			$("#wllb").attr('disabled',false);
			$("#tlsl").attr('disabled',false);
			$("#dw").attr('disabled',false);
			$("input:radio[name='bjly_radio']").attr('disabled',false);
			$("#fjzch").attr('disabled',false);
		}
	}
	
	function add(fl){
		//验证
		$('#form').data('bootstrapValidator').validate();
		if(!$('#form').data('bootstrapValidator').isValid()){
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
			return false;
		}
		var obj = {};
		var tlrq=$("#tlrq").val();
		var bjid=$("#bjid").val();
		var kcid=$("#kcid").val();
		var bjh=$("#bjh").val();
		var bjmc=$("#bjmc").val();
		var pch=$("#pch").val();
		var xlh=$("#xlh").val();
		var tlsl=$("#tlsl").val();
		var dw=$("#dw").val();
		var sfky=$("input:radio[name='sfky_radio']:checked").val();
		var bjly=$("input:radio[name='bjly_radio']:checked").val();
		var wllb=$("#wllb").val();
		var sm=$("#sm").val();
		var dprtcode = $("#dprtcode").val();
		if(tlsl <= 0){
			AlertUtil.showModalFooterMessage("退料数量不能小于等于0！");
			return false;
		}
		if($("#kcid").val()){
			var oldSl = $("#tlsl_hide").val()?$("#tlsl_hide").val():0;
			var newSl = $("#tlsl").val()?$("#tlsl").val():0;
			console.info(parseFloat(oldSl) , parseFloat(newSl))
			if(parseFloat(oldSl) < parseFloat(newSl)){
				AlertUtil.showModalFooterMessage("退料数量不能大于库存数量！");
				return;
			}
		}
		
		obj.tlrq = tlrq;
		obj.bjid = bjid;
		obj.wckcid = kcid;
		obj.bjh = bjh;
		obj.bjmc = bjmc;
		obj.pch = pch;
		obj.xlh = xlh;
		obj.tlsl = tlsl;
		obj.dw = dw;
		obj.sfky = sfky;
		obj.bjly = bjly;
		obj.wllb = wllb;
		obj.sm = sm;
		obj.zt = 2;
		obj.dprtcode = dprtcode;
		if(bjly == 2){
			obj.fjzch = $("#fjzch").val();
		}
		var id = $("#rejectedMaterialId").val();
		var url = "";
		if(id!="" && id != null){
			url=basePath+"/material/return/edit";
			obj.id=id;
			submit(obj,url,fl);
		}else{
			url=basePath+"/material/return/add";
			submit(obj,url,fl);
		}
	}
	
	function submit(obj,url,fl){
		//遮罩
		startWait();
		AjaxUtil.ajax({
			url:url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				finishWait();
				 AlertUtil.showErrorMessage("操作成功！");
				 if(fl){
					 //初始化弹窗
					 initModal();
					// 重置表单验证
					$('#form').data('bootstrapValidator').resetForm(false);
				 }else{
					 $("#apply_open_alert").modal("hide");
				 }
				 id = data;
				 applyMain.goPage(applyMain.pagination.page, applyMain.pagination.sort, applyMain.pagination.order);
			}
		});
	}
	//关闭
	function closeRM(id){
		AlertUtil.showConfirmMessage("确定要关闭吗？", {callback: function(){
			var obj = {};
			url=basePath+"/material/return/close";
			obj.id = id;
			obj.zt = 9;
			submit(obj,url,true)
		}});
	}
	//隐藏Modal时验证销毁重构
	$("#apply_open_alert").on("hidden.bs.modal", function() {
		 $("#form").data('bootstrapValidator').destroy();
	     $('#form').data('bootstrapValidator', null);
	     validation();
	});
	
	function orderBy(obj){
		// 字段排序
			var orderStyle = $("#" + obj + "_order").attr("class");
			$("th[id$=_order]").each(function() { //重置class
				$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
			});
			$("#" + obj + "_" + "order").removeClass("sorting");
			var orderType = "asc";
			if (orderStyle.indexOf("sorting_asc") >= 0) {
				$("#" + obj + "_" + "order").addClass("sorting_desc");
				orderType = "desc";
			} else {
				$("#" + obj + "_" + "order").addClass("sorting_asc");
				orderType = "asc";
			}
			orderStyle = $("#" + obj + "_order").attr("class");
			var currentPage = $("#pagination li[class='active']").text();
			applyMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
	}
	function xlhChange(){
		var xlh = $("#xlh").val();
		if(xlh){
			$("#tlsl").val("1");
			$("#tlsl").attr('disabled',true);
		}else{
			$("#tlsl").attr('disabled',false);
			$("#dw").attr('disabled',false);
		}
	}
	//搜索条件重置
	function searchreset(){
		 $("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		 $("#divSearch").find("textarea").each(function(){
			 $(this).val("");
		 });
		 $("#divSearch").find("select").each(function(){
				$(this).val("");
			});
		 $("#keyword_search").val("");
		 $("#dprtcode_search").val(userJgdm);
		 $("#tlrq_search").val("");
		 $("#isSh1").attr("checked","checked");
		 $("#isSh2").attr("checked",false);
		//飞机注册号
		var planeRegOption = initfjzch(userJgdm);
		planeRegOption ="<option value=''>显示全部 All</option>" + planeRegOption;
		$("#fjzch_search").html(planeRegOption); 
		 
		 
	}
	//数量的改变对比
	function tlslChange(){

	}
