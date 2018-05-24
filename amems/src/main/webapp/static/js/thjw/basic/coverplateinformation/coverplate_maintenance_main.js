
$(function(){
	Navigation(menuCode, '', '', 'GC-2-5 ', '林龙', '2017-08-02', '李高升', '2017-08-29');// 加载导航栏

	changeList();
	$("#icon").removeClass("icon-caret-down").removeClass("icon-caret-up").addClass("icon-caret-down");
	
	validation();
	//只能输入数字绑定
	
	$("#kggs").keyup(function(){
		backspace($(this),/^(0|[1-9]\d{0,11})((\.)|(\.\d{1,2}?))?$/);
		$('#form').data('bootstrapValidator').updateStatus('kggs', 'NOT_VALIDATED',null);  
	});
	
	$("#hggs").keyup(function(){
		backspace($(this), /^(0|[1-9]\d{0,11})((\.)|(\.\d{1,2}?))?$/);
		$('#form').data('bootstrapValidator').updateStatus('hggs', 'NOT_VALIDATED',null);  
	});
	
	coverplate.resetForm();
	logModal.init({code:'D_021'});
    // 初始化导入
    importModal.init({
	    importUrl:"/basic/coverplateinformation/excelImport",
	    downloadUrl:"/common/templetDownfile/16",
		callback: function(data){
			coverplate.reload();//开始的加载默认的内容 
			 $("#ImportModal").modal("hide");
		}
	});
	
});

/**
  * 显示导入模态框
  */
 function showImportModal(){
	 importModal.show();
 }

// 查询条件
function gatherSearchParam (){
	var searchParam={};
	searchParam.dprtcode=$("#dprtcode").val();
	searchParam.fjjx=$("#fjjx_select").val();
	searchParam.qy=$("#zone").val();
	
	return searchParam;
}

//只能输入数字限制
function backspace(obj, reg){
	var value = obj.val();
	var str = value.split(".");
	while(!(reg.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
	}
	obj.val(value);
}

//重置按钮
function searchreset(){
	$("#dprtcode").val(userJgdm);
	$("#keyword_search").val('');
	changeList();
}

/* 表格拼接 */
function appendContentHtml(list){		 
			var htmlContent = ''; 
			$.each(list,function(index,row){
				 
				if (index % 2 == 0) {
					htmlContent += "<tr  bgcolor=\"#f9f9f9\" >";
				   
				} else {
					htmlContent += "<tr  bgcolor=\"#fefefe\" >";
				}
				
				 htmlContent = htmlContent + "<td class='fixed-column text-center'><i name='edit' class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='basic:coverplateinformation:main:update' "+
				 " id ='"+StringUtil.escapeStr(row.id )+"'"+
				 " onClick=coverplate.edit(this) title='修改 Edit' permissioncode='basic:zone:main:edit'></i>&nbsp;&nbsp;" +
				 " <i  name='remove' class='icon-trash  color-blue cursor-pointer checkPermission' permissioncode='basic:coverplateinformation:main:delete'  onClick=del('"+ row.id +"') title='删除Delete'></i></td>"; 

			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.fjjx)+"'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.gbbh)+"'>"+StringUtil.escapeStr(row.gbbh)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.gbmc)+"'>"+StringUtil.escapeStr(row.gbmc)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.szqywz)+"'>"+StringUtil.escapeStr(row.szqywz)+"</td>";
			    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.kggs)+"'>"+StringUtil.escapeStr(row.kggs)+"</td>";
			    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.hggs)+"'>"+StringUtil.escapeStr(row.hggs)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.qy)+"'>"+StringUtil.escapeStr(row.qy)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.rlgbbs)+"'>"+StringUtil.escapeStr(row.rlgbbs)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.whrxm)+"'>"+StringUtil.escapeStr(row.whrxm)+"</td>";
			    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.whsj)+"'>"+StringUtil.escapeStr(row.whsj)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dprtname)+"'>"+StringUtil.escapeStr(row.dprtname)+"</td>";
			    
		 });
	       $("#coverplate_maintenance_list").empty();
		   $("#coverplate_maintenance_list").html(htmlContent);
		   refreshPermission();
}

// 字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { // 重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc")>=0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	coverplate.goPage(currentPage,obj,orderStyle.split("_")[1]);
}

	

/* 飞机机型初始化方法*/	
function initFjjxOptionFn(e){
	if(e == "" || e==null) {
		 e = userJgdm;
	}
	var typeList = acAndTypeUtil.getACTypeList({DPRTCODE : e});
	var html = "";
	if(typeList.length > 0){
		for(var i = 0; i < typeList.length; i++){
			html += "<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>";
		}
	}else{
		html += "<option value='-1'>暂无数据 No data</option>";
	}
	return html;
}


//删除方法
function del(id){
	var message = '确定要删除吗？';
	AlertUtil.showConfirmMessage(message,{callback:function(){
		coverplate.del(id);
	}});
}

// 切换组织机构时机型查询
function changeList(){
	 
	var dprtcode = $.trim($("#dprtcode").val());
	var planeRegOption = '';
	var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
	if( planeDatas!=null&&planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
		});
	}
	if(planeRegOption==''){
		planeRegOption = "<option value=''>暂无数据 No data</option>";
	}
	$("#fjjx_select").empty();
	$("#fjjx_select").append(planeRegOption);
	coverplate.getZones();
}

/**
 * 切换飞机机型时查询
 */ 
function changeFjjx(){
	coverplate.getZones();
}

/**
 *	搜索按钮 
 */
function search(){
	coverplate.search();
}

function appendZones(list,fjjx){
	var htmlContent = ''; 
	$.each(list,function(index,row){		
		htmlContent +="<option value= '"+row.sz+"' >"+row.sz+"</optiom>";
	});
	$("#zone").empty();
	htmlContent = "<option value=''>显示全部All</option>"+htmlContent;
	$("#zone").append(htmlContent);
}

/**
 *	区域下拉列表 
 */
function zonesHtml(list){
	var htmlContent = ''; 
	$.each(list,function(index,row){		
		htmlContent +="<option value= '"+row.sz+"' >"+row.sz+"</option>";
	});
	if(htmlContent ==''){
	   htmlContent +="<option value=''>暂无数据 No data</option>";
	}
	htmlContent = '<option value="">请选择</option>'+htmlContent;
	return htmlContent
}


/**
 * url转json
 */
function paramString2obj (serializedParams) {  
	  var obj={};
	  function evalThem (str) {
	    var strAry = new Array();
	    strAry = str.split("=");
	    //使用decodeURIComponent解析uri 组件编码
	    for(var i = 0; i < strAry.length; i++){
	      strAry[i] = decodeURIComponent(strAry[i]);
	    }
	    var attributeName = strAry[0];
	    var attributeValue = strAry[1].trim();
	    //如果值中包含"="符号，需要合并值
	    if(strAry.length > 2){
	      for(var i = 2;i<strAry.length;i++){
	        attributeValue += "="+strAry[i].trim();
	      }
	    }
	    if(!attributeValue){
	      return ;
	    }
	    var attriNames = attributeName.split("."),
	      curObj = obj;
	    for(var i = 0; i < (attriNames.length - 1); i++){
	      curObj[attriNames[i]]?"":(curObj[attriNames[i]] = {});
	      curObj = curObj[attriNames[i]];
	    }
	    //使用赋值方式obj[attributeName] = attributeValue.trim();替换
	    //eval("obj."+attributeName+"=\""+attributeValue.trim()+"\";");
	    //解决值attributeValue中包含单引号、双引号时无法处理的问题
	    curObj[attriNames[i]] = attributeValue.trim();
	  };
	  var properties = serializedParams.split("&");
	  for (var i = 0; i < properties.length; i++) {
	    //处理每一个键值对
	    evalThem(properties[i]);
	  };
	  return obj;
	}
/**
 *	更改弹出框飞机机型时调用 
 */
function changeFjjxWin(){
	var obj  = {};
	obj.fjjx = $('#fjjx2').val();
	obj.dprtcode = userJgdm;
	var fn = function(e){
		var qy = $("#qy_select_win").val();
	};
	$('#form').data('bootstrapValidator').updateStatus('qy_select_win', 'NOT_VALIDATED',null);  
	coverplate.getZonesWin(obj,fn);
}


var coverplate = {
		//form表单对象
		formObj : $("#form"),
		//更新或新增参数:0表示更新，1表示新增
		saveUpdateParam :"1",
		search :function(){
			$("th[id$=_order]").each(function() { // 重置class
				$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
			});
			coverplate.goPage(1,"auto","asc");
		},
		/**
		 *	重置表单 
		 */
		resetForm :function(){
			$('#alertModalView').on('shown.bs.modal', function() {
				$('#form').bootstrapValidator('resetForm', false);
				$('#form')[0].reset();
				$("#id").val('');
			});
		},
		/**
		 *	飞机机型初始化 
		 */
		initFjjxOption:function(){
			var html = initFjjxOptionFn('');
			var obj = $("#fjjx_select");
			obj.empty();
			obj.append(html);
			this.getZones();
		},
		/**
		 *	时区选择框初始化 
		 */
		getZones :function(){
			 var this_ = this;
			 var fjjx = $("#fjjx_select").val()
			 var obj ={};
			 obj.lx=1;
			 obj.jx = fjjx;
			 obj.dprtcode=$("#dprtcode").val();
			 startWait();
			 AjaxUtil.ajax({
					url:basePath+"/basic/zone/zoneList",
					type: "post",
					contentType:"application/json;charset=utf-8",
					data:JSON.stringify(obj),
					dataType:"json",
					success:function(list){
						finishWait();
						appendZones(list,fjjx);	
						this_.search();
					}
				});
		},
		getZonesWin:function(e,fn){
			 var this_ = this;
			 var obj ={};
			 obj.lx=1;
			 obj.jx = e.fjjx;
			 obj.dprtcode = e.dprtcode;
			 startWait();
			 AjaxUtil.ajax({
					url:basePath+"/basic/zone/zoneList",
					type: "post",
					contentType:"application/json;charset=utf-8",
					data:JSON.stringify(obj),
					dataType:"json",
					success:function(data){
						finishWait();
						var html = zonesHtml(data);
						$("#qy_select_win").empty();
						$("#qy_select_win").append(html);
						fn(data);
					}
				});
		},
		/**
		 * 打开弹出窗 
		 */
		show:function(){
			$("#alertModalView").modal('show');
			$(".modal-footer").find('.glyphicon-info-sign').css("display",'none');
			$(".modal-footer").find('.alert-info-message').attr('title','').empty();
		},
		/**
		 * 隐藏弹出窗 
		 */
		hide :function(){
			$("#alertModalView").modal('hide');
		},
		/**
		 * 新增
		 */
		add: function(){
			//初始化新增弹出窗
			var this_ = this;
			$("#gbbh").attr("readonly",false);
			this_.saveUpdateParam = "1";
			$("#modalName").html("新增");
			$("#modalEname").html("Add");
			
			//弹出前验证
			var fjjx_choose =$("#fjjx_select").val();
			var dprtcode_choose =$("#dprtcode").val();
			if(dprtcode_choose=""||dprtcode_choose=="undefined"||dprtcode_choose==undefined||fjjx_choose==null||dprtcode_choose!=userJgdm){		 
				AlertUtil.showMessage("只能新增本组织机构下的数据！");
				return ;
			}
			if(fjjx_choose==""||fjjx_choose=="undefined"||fjjx_choose==undefined||fjjx_choose==null){
				AlertUtil.showMessage("机型不能为空！");
				return ;
			}
			var obj = {dprtcode:userJgdm,fjjx:fjjx_choose};
			var fn = function(e){
				this_.show();
				$("#fjjx2").val(fjjx_choose);
				$("#dprtcode1").val(userJgdm);
			};
			this_.getZonesWin(obj,fn);
		},
		/**
		 *	修改 
		 */
		edit:function(obj){
			//初始化弹出窗
			var this_ = this;
			this.saveUpdateParam = "0";
			$("#modalName").html("修改");
			$("#modalEname").html("Update");
			var id = $(obj).attr('id');
			var obj = {id:id};
			AjaxUtil.ajax({
				url: basePath+'/basic/coverplateinformation/queryWinById',
				type: 'post',
				contentType:"application/json;charset=utf-8",
				data: JSON.stringify(obj),
				dataType:"json",
				success: function(e){
					if(e != null){
						//添加机型展示
						$("#gbbh").attr("readonly",true);
						//添加区域信息
						var param = {dprtcode:e.dprtcode,fjjx:e.fjjx};
						var fn = function(data){
								this_.show();
								$("#id").val(e.id);
								$("#gbbh").val(e.gbbh);
								$("#gbmc").val(e.gbmc);
								$("#szqywz").val(e.szqywz);
								$("#kggs").val(e.kggs);
								$("#hggs").val(e.hggs);
								$("#rlgbbs").val(e.rlgbbs);
								$("#fjjx2").val(e.fjjx);
								$("#dprtcode1").val(e.dprtcode);
								$("#qy_select_win").val(e.qy);
							};
						this_.getZonesWin(param,fn);
					}
				}
			});
		},
		del:function(id){
			var this_ = this;
			var obj = {};
			obj.id = id;
			startWait();
			AjaxUtil.ajax({
				url: basePath+'/basic/coverplateinformation/delete',
				type: 'post',
				contentType:"application/json;charset=utf-8",
				data: JSON.stringify(obj),
				dataType:"json",
				success: function(e){
					finishWait();
					AlertUtil.showMessage("删除成功!");
					this_.reload();
				}
			});
		},
		/**
		 * 表单验证
		 */
		check : function(fn){
			var bv = this.formObj.data('bootstrapValidator');
			bv.validate();
			if(bv.isValid()){
				fn();
			}else{
				AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			}
		},
		/**
		 * 清空文本
		 */
		clearform: function(){
			$('#form')[0].reset();
			$("#id").val('');
		},
		/**
		 *	搜索条件展示与隐藏 
		 */
		moreBtn: function(){
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
		},
		/**
		 * 提交
		 */
		saveUpdate: function(){
			var this_ = this;
			var fn = function(){
				var obj = {};
				obj.id = $("#id").val();
				obj.gbbh = $("#gbbh").val().trim();
				obj.gbmc = $("#gbmc").val().trim();
				obj.szqywz = $("#szqywz").val().trim();
				obj.kggs = $("#kggs").val().trim();
				obj.hggs = $("#hggs").val().trim();
				obj.rlgbbs = $("#rlgbbs").val().trim();
				obj.fjjx = $('#fjjx2').val();
				obj.qy = $("#qy_select_win").val().trim();
				obj.dprtcode = $("#dprtcode1").val();
				startWait($("#alertModalView"));
				AjaxUtil.ajax({
					url: basePath+'/basic/coverplateinformation/update',
					type: 'post',
					contentType:"application/json;charset=utf-8",
					data: JSON.stringify(obj),
					dataType:"json",
					modal :$("#alertModalView"),
					success: function(e){
						finishWait($("#alertModalView"));
						AlertUtil.showMessage("保存成功!");
						this_.hide();
						this_.reload();
					}
				});
			}
			this.check(fn);
		},
		// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		reload:function(){
			this.goPage(1,"auto","desc");
		},
		// 带条件搜索的翻页
		AjaxGetDatasWithSearch :function(pageNumber,sortType,sequence){
					var this_ = this;
				    var searchParam = gatherSearchParam();		  	  
				    pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
				    searchParam.pagination = pagination;
				    searchParam.keyword=$.trim($('#keyword_search').val());
				    startWait();
					 AjaxUtil.ajax({
						   url:basePath+"/basic/coverplateinformation/queryCoverPlateList/zt",
						   type: "post",
						   contentType:"application/json;charset=utf-8",
						   dataType:"json",
						   data:JSON.stringify(searchParam),
						   success:function(data){
							   finishWait();
				 			if(data.total >0){
				 				appendContentHtml(data.rows);
				 				new Pagination({
									renderTo : "pagination",
									data: data,
									sortColumn : sortType,
									orderType : sequence,
									extParams:{},
									goPage: function(a,b,c){
										this_.AjaxGetDatasWithSearch(a,b,c);
									}
				 				});
							    FormatUtil.removeNullOrUndefined();
					 			signByKeyword($("#keyword_search").val(),[3,4],"#coverplate_maintenance_list tr td");
				 			}else{
					 			$("#coverplate_maintenance_list").empty();
								$("#pagination").empty();
								$("#coverplate_maintenance_list").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
				 			}
				 			new Sticky({tableId:'coverplate_maintenance_table'});
				 		}
				     }); 	
		},
		// 初始化区域下拉框
		initZoneInformation : function(obj){
			var this_ = this;
			var searchParam = {};
			searchParam.dprtcode = this_.param.dprtcode;
			searchParam.jx = this_.param.jx;
			searchParam.lx = 1;
			startWait();
			AjaxUtil.ajax({
				async: false,
				url: basePath+"/basic/zone/zoneList",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(searchParam),
				success:function(data){
					finishWait();
					var zoneOption = '';
					if(data != null && data.length > 0){
						$.each(data,function(i,row){
							var tempOption = "<option value='"+StringUtil.escapeStr(row.id)+"' >"+StringUtil.escapeStr(row.sz)+"</option>";
							zoneOption += tempOption;
						});
					}
					this_.zoneOption = zoneOption;
					if(typeof(obj)=="function"){
						obj(); 
					}
			    }
			}); 
		},
}

/**
 * 提交
 */
function saveUpdate(){
	coverplate.saveUpdate();
}
/**
 * 表单信息验证
 */
function validation(){
	$("#form").bootstrapValidator({
		 message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	            fjjx2:{
	            	validators:{
	                	notEmpty: {
	                        message: '机型不能为空'
	                    }
	            	}
	            },
	            gbbh: {
	                validators: {
	                    notEmpty: {
	                        message: '盖板编号不能为空'
	                    },
	                    regexp: {
	                    	  regexp: /^[\x00-\xff]*$/,
	 	                      message: '盖板编号不能输入中文及中文符号'
		                }
	                }
	            },
	            kggs: {
	                validators: {
	                    regexp:{
	                    	regexp:/^(0|[1-9]\d{0,11})(\.\d{1,2}?)?$/,
	                    	message:'开盖工时最多可输入12位整数，2位小数'
	                    }
	                }
	            },
	            hggs: {
	                validators: {
	                    regexp:{
	                    	regexp:/^(0|[1-9]\d{0,11})(\.\d{1,2}?)?$/,
	                    	message:'合盖工时最多可输入12位整数，2位小数'
	                    }
	                }
	            }
	        }

	});
}

function exportExcel(){
	var keyword = $("#keyword_search").val();
	var dprtcode = $("#dprtcode").val();
	var fjjx = $("#fjjx_select").val();
	window.open(basePath+"/basic/coverplateinformation/CoverPlate.xls?dprtcode="
 				+ dprtcode + "&fjjx="+fjjx+"&keyword="+ encodeURIComponent(keyword));
	
}

//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
		if(formatUndefine(winId) != ""){
			$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
		}else{
			search(); //调用主列表页查询
		}
	}
};
