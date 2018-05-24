var nonchineseReg = /^[\x00-\xff]*$/;
var cycleReg = /^(0|[1-9]\d*)$/;
var timeReg = /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/;
var yjtsJb1 = '';
var yjtsJb2 = '';
var yjtsJb3 = '';
var fjzch_0 = '';
$(function(){
	Navigation(menuCode, '', '', 'SC-1-1', '林龙', '2017-09-25', '孙霁', '2017-09-25');
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
	});
	var dprtcode=$("#dprtcode_search").val();
	
	aircraftinfoMain.reload(true,"syts1","asc");
	//验证
	//validation();
	//初始化日志功能
	logModal.init({code:'AircraftinfoD_007'});
	//加载机型
	jxChange(dprtcode);
	//验证
	validation();
	//添加keyup事件
	bindEvent();
	//添加改变事件
	tablecs();
	//添加归属
	selectGs(dprtcode);
});
//添加归属
function selectGs(dprtcode){
		var obj = {
				'dprtcode':dprtcode
		};
		obj.pagination = {page:1,sort:'auto',order:'desc',rows:100000};
		AjaxUtil.ajax({
			   url:basePath+"/baseinfo/customer/selectByDprtcode",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				   finishWait();
				   if(data.total != null &&  data.total > 0){
					   appendSelectGs(data.rows);
				   }else{
					   $("#gs_search").empty();
						$("#gs_search").append("<option value='ALL'>显示全部All</option><option value='BDW'>本单位</option>");
				   }
			   }
		   });
		
}
function appendSelectGs(list){
	var htmlappend = "<option value='ALL'>显示全部All</option>";
		htmlappend +="<option value='BDW'>本单位</option>";
		$.each(list,function(index,row){
			htmlappend += "<option value='"+row.id+"'>"+row.khbm+" "+row.khmc+"</option>";
		});
		$("#gs_search").empty();
		$("#gs_search").append(htmlappend);
}

/**
 * 添加改变事件
 */
function tablecs(){
	$("#bjcsTable").find("input").change(function(){
		$(this).removeClass("border-color-red");
		$(this).next().next().removeClass("border-color-red");
	});
	$("#rsylTable").find("input").change(function(){
		$(this).removeClass("border-color-red");
		$(this).next().next().removeClass("border-color-red");
	});
	$("#jsfxxs").change(function(){
		$(this).removeClass("border-color-red");
		$(this).next().next().removeClass("border-color-red");
	});
}
/**
 * 添加事件
 */
function bindEvent(){
	/*$("#"+param.id+" .monitorItem").click(function(){
		showOrHideMonitor($(this));
	});
	$("#"+param.id+" .calendarUnit").click(function(){
		changeUnit($(this));
	});*/
	$(".fxyz").keyup(function(){
		backspace($(this), timeReg, true);
	});
	$(".fxzsyz").keyup(function(){
		backspace($(this), cycleReg);
	});
}

function backspace(obj, reg, replace){
	var value = obj.val();
	if(replace){
		value = value.replace(/：/g, ":");
	}
	while(!(reg.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
    }
	if(!value){
		$('#form').data('bootstrapValidator')  
		.updateStatus('jsfxxs', 'NOT_VALIDATED',null)  
		.validateField('jsfxxs');  
	}
	obj.val(value);
}
//验证
function validation(){
 $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	fjzch: {
	                validators: {
	                	notEmpty: {
	                        message: '飞机注册号不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]*$/,
	                        message: '飞机注册号不能含有中文'
	                    }
	                }
	            },
	            fjjx: {
	                validators: {
	                    notEmpty: {
	                        message: '飞机机型不能为空'
	                    }
	                }
	            },
	            xlh: {
	                validators: {
	                    notEmpty: {
	                        message: 'MSN不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]*$/,
	                        message: 'MSN不能含有中文'
	                    }
	                }
	            },
	            rhyzph: {
	            	validators: {
	            		regexp: {
	            			regexp: /^[^\u4e00-\u9fa5]*$/,
	            			message: '滑油牌号不能含有中文'
	            		}
	            	}
	            },
	            yyyph: {
	            	validators: {
	            		regexp: {
	            			regexp: /^[^\u4e00-\u9fa5]*$/,
	            			message: '液压油牌号不能含有中文'
	            		}
	            	}
	            },
	            jscshrq: {
	            	validators: {
	            		notEmpty: {
	            			message: '初始化日期不能为空'
	            		}
	            	}
	            },
	            jsfxxs: {
	            	validators: {
	            		notEmpty: {
	            			message: '飞行小时不能为空'
	            		}
	            	}
	            },
	            jsfxxh: {
	            	validators: {
	            		notEmpty: {
	            			message: '飞行循环不能为空'
	            		},
	            		regexp: {
	            			regexp: /^[0-9]+(\[0-5][0,9])?$/,
	            			message: '飞行循环格式有误'
	            		}
	            	}
	            },
	            gjdjzh: {
	            	validators: {
	            		notEmpty: {
	            			message: '国籍证号不能为空'
	            		},
	            		regexp: {
	            			regexp: /^[^\u4e00-\u9fa5]*$/,
	            			message: '国籍证号不能含有中文'
	            		}
	            	}
	            },
	            shzh: {
	            	validators: {
	            		notEmpty: {
	            			message: '适航证号不能为空'
	            		},
	            		regexp: {
	            			regexp: /^[^\u4e00-\u9fa5]*$/,
	            			message: '适航证号不能含有中文'
	            		}
	            	}
	            },
	            wxdtxkzh: {
	            	validators: {
	            		notEmpty: {
	            			message: '无线电台执照号不能为空'
	            		},
	            		regexp: {
	            			regexp: /^[^\u4e00-\u9fa5]*$/,
	            			message: '无线电台执照号不能含有中文'
	            		}
	            	}
	            },
	            gjdjzjkrq: {
				    validators : {
							notEmpty : {
								message : '颁发日期不能为空'
							},
							callback : {
								message : '颁发日期必须小于有效期',
								callback : function(value, validator) {
									validator.revalidateField($("#gjdjzyxq"));
									var wxdtbfrq = $("#gjdjzjkrq").val();
									var dtzzjkrq = $("#gjdjzyxq").val();
									if (!wxdtbfrq || !dtzzjkrq) {
										return true;
									}
									if (wxdtbfrq >= dtzzjkrq) {
										return false;
									}
									return true;
								}
							}
						}
	            },
	            shzjkrq: {
	            	validators: {
	            		notEmpty: {
	            			message: '颁发日期不能为空'
	            		},
	                	callback: {
	                        message: '颁发日期必须小于有效期',
	                        callback: function(value, validator) {
	                        	validator.revalidateField($("#shzzjkrq"));
	                			var shzjkrq = $("#shzjkrq").val();
	                			var shzzjkrq = $("#shzzjkrq").val();
	                			if(!shzzjkrq || !shzjkrq){
	                				validator.updateStatus('shzzjkrq', 'VALID', 'callback'); 
	                				return true;
	                			}	
	                			if(shzjkrq >= shzzjkrq){
	                				return false;
	                			}
	                			return true;
	                        }
	                    }
	            	}
	            },
	            wxdtbfrq: {
	            	validators: {
	            		notEmpty: {
	            			message: '颁发日期不能为空'
	            		},
	                	callback: {
	                        message: '颁发日期必须小于有效期',
	                        callback: function(value, validator) {
	                        	validator.revalidateField($("#dtzzjkrq"));
	                			var wxdtbfrq = $("#wxdtbfrq").val();
	                			var dtzzjkrq = $("#dtzzjkrq").val();
	                			if(!wxdtbfrq || !dtzzjkrq){
	                				return true;
	                			}
	                			if(wxdtbfrq>= dtzzjkrq){
	                				return false;
	                			}
	                			return true;
	                        }
	                    }
	            	}
	            },
	            gjdjzyxq:{
	               	validators: {
	            		notEmpty: {
	            			message: '有效期不能为空'
	            		},
	                	callback: {
	                        message: '有效期必须大于颁发日期',
	                        callback: function(value, validator) {
	                			var gjdjzyxq = $("#gjdjzyxq").val();
	                			var gjdjzjkrq = $("#gjdjzjkrq").val();
	                			if(!gjdjzyxq || !gjdjzjkrq){
	                				validator.updateStatus('gjdjzyxq', 'VALID', 'callback');
	                				return true;
	                			}
	                			if(gjdjzyxq <= gjdjzjkrq){
	                				validator.updateStatus('gjdjzyxq', 'INVALID', 'callback'); 
	                				return false;
	                			}
	                			validator.updateStatus('gjdjzyxq', 'VALID', 'callback');
	                			return true;
	                        }
	                    }
	            	}
	            		            	
	            },
	            shzzjkrq: {
	            	validators: {
	            		notEmpty: {
	            			message: '有效期不能为空'
	            		},
	                	callback: {
	                        message: '有效期必须大于颁发日期',
	                        callback: function(value, validator) {
	                			var shzzjkrq = $("#shzzjkrq").val();
	                			var shzjkrq = $("#shzjkrq").val();
	                			if(!shzzjkrq || !shzjkrq){
	                				validator.updateStatus('shzjkrq', 'VALID', 'callback');
	                				return true;
	                			}
	                			if(shzjkrq >= shzzjkrq){
	                				validator.updateStatus('shzjkrq', 'INVALID', 'callback'); 
	                				return false;
	                			}
	                			validator.updateStatus('shzjkrq', 'VALID', 'callback');
	                			return true;
	                        }
	                    }
	            	}
	            },
	            dtzzjkrq: {
	            	validators: {
	            		notEmpty: {
	            			message: '有效期不能为空'
	            		},
	                	callback: {
	                        message: '有效期必须大于颁发日期',
	                        callback: function(value, validator) {
	                			var wxdtbfrq = $("#wxdtbfrq").val();
	                			var dtzzjkrq = $("#dtzzjkrq").val();
	                			if(!wxdtbfrq || !dtzzjkrq){
	                				validator.updateStatus('wxdtbfrq', 'VALID', 'callback');
	                				return true;
	                			}
	                			if(wxdtbfrq >= dtzzjkrq){
	                				validator.updateStatus('wxdtbfrq', 'INVALID', 'callback'); 
	                				return false;
	                			}
	                			validator.updateStatus('wxdtbfrq', 'VALID', 'callback');
	                			return true;
	                        }
	                    }
	            	}
	            }
	            
	        }
	    });
}
function jxChange(dprtcode){
	var data = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
 	var option = '<option value="">显示全部All</option>';
 	if(data.length >0){
		$.each(data,function(i,obj){
			option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
		});
  	 }
 	$("#jx_search").empty();
 	$("#jx_search").append(option);
}

//切换组织机构
function dprtChange(){
	var dprtcode=$("#dprtcode_search").val();
	jxChange(dprtcode);
	selectGs(dprtcode);
}

var aircraftinfoMain={
		gatherSearchParam:function(){
			var searchParam={};
			if (fjzch_0 != "") {
				searchParam.fjzch = fjzch_0;
				fjzch_0 = "";
			}
			 searchParam.fjjx=$("#jx_search").val();
			 searchParam.dprtcode=$("#dprtcode_search").val();
			 searchParam.keyword=$.trim($('#keyword_search').val());
			 var gmrq=$("#gmrq_search").val();
			 var scrq=$("#scrq_search").val();
			 var paramsMap={};
			 paramsMap.userId = userId;
			 paramsMap.userType = userType;
			 //购买日期
			 if(null != gmrq && "" != gmrq){
				 var gmrqBegin = gmrq.substring(0,10)+" 00:00:00";
				 var gmrqEnd = gmrq.substring(13,23)+" 23:59:59";
				 paramsMap.gmrqBegin = gmrqBegin;
				 paramsMap.gmrqEnd = gmrqEnd;
			 }
			 //生产期限
			 if(null != scrq && "" != scrq){
				 var scrqBegin = scrq.substring(0,10)+" 00:00:00";
				 var scrqEnd = scrq.substring(13,23)+" 23:59:59";
				 paramsMap.scrqBegin = scrqBegin;
				 paramsMap.scrqEnd = scrqEnd;
			 }
			 //归属
			 gs = $("#gs_search").val();
			 if(gs == 'BDW'){
				 searchParam.isSsbdw = 1;
			 }
			 if(gs !='ALL' && gs !='BDW'){
				 searchParam.ssdwid = gs;
			 }
			 
			 searchParam.paramsMap=paramsMap;
			 return searchParam;
		},
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 var searchParam=_this.gatherSearchParam();
			 searchParam.pagination = pagination;
			 AjaxUtil.ajax({
				 url:basePath+"/quality/radiolicense/queryAll",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
			 			if(data.rows.length > 0){
		 				 yjtsJb1 =data.monitorsettings.yjtsJb1;
						 yjtsJb2 =data.monitorsettings.yjtsJb2;
						 yjtsJb3 =data.monitorsettings.yjtsJb3;
						 
			 			_this.appendContentHtml(data.rows);
			 			//分页
			 			var page_ = new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							extParams:{},
							goPage: function(a,b,c){
								_this.AjaxGetDatasWithSearch(a,b,c);
							}
						});
			 		// 标记关键字
						   signByKeyword($("#keyword_search").val(),[4,5,12]);
			 		
			 		}else{
			 			$("#list").empty();
						$("#pagination").empty();
						$("#list").append("<tr><td class='text-center' colspan=\"21\">暂无数据 No data.</td></tr>");
						
					}
			 			new Sticky({tableId:'quality_check_list_table'});
			 		}
			 });
		},
		reload:function(fl,syts,asc){
			TableUtil.resetTableSorting("quality_check_list_table");
			if(fl){
				this.goPage(1,syts,asc);
			}else{
				this.goPage(1,"auto","desc");
			}
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			
			$.each(list,function(index,row){
					htmlContent += "<tr >";
			    htmlContent += "<td class='fixed-column text-center' style='text-align:center;vertical-align:middle;'>";
			    if(row.zt==1){
			    	htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' " +
			    	"permissioncode='quality:radiolicense:main:01' onClick=\"edit('"+row.fjzch+"','"+row.dprtcode+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";
			    }
			    htmlContent += "</td>";
			    var yj ="";
			    var lxMessage = '有效期';
			    var map=row.paramsMap;
			    var zt=row.zt;
			    yj=getWarningColor(lxMessage,map,zt);
//			    if(row.syts1>row.syts2){
//			    	lxMessage = "年检有效期"
//			    	yj = getWarningColor(lxMessage,row.paramsMap.syts2,row.zt); 
//			    }else{
//			    	lxMessage = "有效期"
//			    	yj = getWarningColor(lxMessage,row.paramsMap.syts1,row.zt); 
//			    }
			    htmlContent += yj; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.fjjx)+"'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.fjzch)+"'><a href=\"javascript:view('"+row.fjzch+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(row.fjzch)+"</a></td>";
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.xlh)+"'>"+StringUtil.escapeStr(row.xlh)+"</td>";
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.bzm)+"'>"+StringUtil.escapeStr(row.bzm)+"</td>";
			    var gs = '';
			    if(row.customer){
			    	 gs =row.customer.khbm +" "+row.customer.khmc;
			    }else{
			    	 gs = '本单位';
			    }
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(gs)+"'>"+StringUtil.escapeStr(gs)+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.gjdjzh)+" "+(row.gjdjzyxq?row.gjdjzyxq.substring(0,10):'')+"'>"+StringUtil.escapeStr(row.gjdjzh)+" "+(row.gjdjzyxq?row.gjdjzyxq.substring(0,10):'')+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.shzh)+" "+(row.shzzjkrq?row.shzzjkrq.substring(0,10):'')+"'>"+StringUtil.escapeStr(row.shzh)+" "+(row.shzzjkrq?row.shzzjkrq.substring(0,10):'')+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.wxdtxkzh)+" "+(row.dtzzjkrq?row.dtzzjkrq.substring(0,10):'')+"'>"+StringUtil.escapeStr(row.wxdtxkzh)+" "+(row.dtzzjkrq?row.dtzzjkrq.substring(0,10):'')+"</td>"; 
			    htmlContent +=	"<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
				if(row.paramsMap && ((row.paramsMap.gjCount && row.paramsMap.gjCount > 0) || (row.paramsMap.shCount && row.paramsMap.shCount > 0) || (row.paramsMap.wxCount && row.paramsMap.wxCount > 0) )){
					htmlContent += '<i gjdjzfjid="'+row.gjdjzfjid+'" shzfjid="'+row.shzfjid+'" wxdtzzfjid="'+row.wxdtzzfjid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
				htmlContent +=	"</td>";
				 htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.base?row.base.dprtname:'')+"'>"+StringUtil.escapeStr(row.base?row.base.dprtname:'')+"</td>";
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+(row.scrq?row.scrq.substring(0,10):'')+"'>"+(row.scrq?row.scrq.substring(0,10):'')+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+(row.ccrq?row.ccrq.substring(0,10):'')+"'>"+(row.ccrq?row.ccrq.substring(0,10):'')+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+(row.gmrq?row.gmrq.substring(0,10):'')+"'>"+(row.gmrq?row.gmrq.substring(0,10):'')+"</td>"; 
			    //有效期
			    var htmls="";
			    if(row.gjdjzyxq){
			    	htmls+="国际登记证:"+row.gjdjzyxq.substring(0,10)+"<br/>";
			    }
			    if(row.shzzjkrq){
			    	htmls+="适航证:"+row.shzzjkrq.substring(0,10)+"<br/>";
			    }
			    if(row.dtzzjkrq){
			    	htmls+="无线电台执照:"+row.dtzzjkrq.substring(0,10);
			    }
			    htmlContent+="<td style='text-align:center;vertical-align:middle;' class='text-center'>"+htmls+"</td>";
			    htmlContent+="<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+row.paramsMap.yxqsy+"'>"+row.paramsMap.yxqsy+"</td>";
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+(row.zt==1?'有效':'无效')+"'>"+(row.zt==1?'有效':'无效')+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"'>"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.whsj)+"'>"+StringUtil.escapeStr(row.whsj)+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='text-center' title='"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>"; 
			    htmlContent += "</tr>" ;
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission(); 
		   initWebuiPopover();
		 });
		}
};
//附件列表
function initWebuiPopover(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#aircraftinfo_main_table_top_div").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}
function getHistoryAttachmentList(obj){//获取历史附件列表
	var jsonData = [
         {mainid : $(obj).attr('gjdjzfjid'), type : '国籍登记证号'},
         {mainid : $(obj).attr('shzfjid'), type : '适航证号'},
         {mainid : $(obj).attr('wxdtzzfjid'), type : '无线电台执照号'}
    ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}
//查看
function view(fjzch,dprtcode){
	window.open(basePath+"/aircraftinfo/view?fjzch="+fjzch+"&dprtcode="+dprtcode);
}
//获取预警颜色
function getWarningColor(lxMessage,map,zt){
	var syts1=map.syts1;
	var syts2=map.syts2;
	var syts3=map.syts3;
	var htmlappend = "<td style='text-align:center;vertical-align:middle;' class='text-center'></td>";
	//获取最小值
    var ary=[Number(syts1),Number(syts2),Number(syts3)];
        ary.sort(function(a,b){
        	return (a-b);
        });
    var min=ary[0];

	if(min<=yjtsJb1){
		bgcolor = warningColor.level1;//红色
		htmlappend =  "<td style='text-align:center;vertical-align:middle;' class='text-center' title='剩余"+lxMessage+"&lt;="+yjtsJb1+"天"+"' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
	} else if (yjtsJb1<min&&min<=yjtsJb2) {
		bgcolor = warningColor.level2;// 黄色
		htmlappend = "<td class='text-center' title='"
				+ yjtsJb1
				+ "&lt;剩余"
				+ lxMessage
				+ "&lt;="
				+ yjtsJb2
				+ "天"
				+ "' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:"
				+ bgcolor + "' /></td>";

	}else{
		htmlappend = "<td style='text-align:center;vertical-align:middle;' class='text-center'></td>";
	}
	if(zt == 0) {
		htmlappend = "<td style='text-align:center;vertical-align:middle;' class='text-center'></td>";
	}
	return htmlappend;
}
function startOrcancel(obj){
	var str = '';
	var fjzch = $(obj).attr("fjzch");
	var fjjx = $(obj).attr("fjjx");
	var dprtcode = $(obj).attr("dprtcode");
	var zt = $(obj).attr("zt");
	if(zt ==1){
		str = '确定要注销吗？';
	}else{
		str = '确定要启用吗？';
	}
	AlertUtil.showConfirmMessage(str, {callback: function(){
		startWait();
		if(zt ==1){
			str = '注销成功!';
			zt = 0;
		}else{
			str = '启用成功!';
			zt = 1;
		}
		var url = basePath+"/aircraftinfo/startOrcancel";
		var aircraftinfo = {
				'fjzch':fjzch,
				'fjjx':fjjx,
				'dprtcode':dprtcode,
				'zt':zt
		}
		performAction(url, aircraftinfo, str);
	}});
}
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
		aircraftinfoMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
}
//搜索条件显示与隐藏 
function search() {
	
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}
//新增
function add(){
	dprtcode = userJgdm;
 	var obj = {
 			
 	};
 	aircraftinfoModal.show({
 		modalHeadChina : '新增',
		modalHeadEnglish : 'Add',
		type:1,//1:新增,2:修改,3:审核,4:批准
		viewObj:obj,
		blOption:true,
		dprtcode:dprtcode,//机构代码
		callback : function(data) {// 回调函数
			if (data != null) {
				var message = '保存成功!';
				var url = basePath+"/aircraftinfo/save";
				performAction(url, data, message);
				
			}
		}
	});
}
//修改
function edit(fjzch, dprtcode){
	aircraftinfo = {
			'fjzch':fjzch,
			'dprtcode':dprtcode
	}
	selectById(aircraftinfo,function(obj){
			aircraftinfoModal.show({
				modalHeadChina : '修改飞机三证监控',
				modalHeadEnglish : 'Edit',
				type:2,//编辑
				viewObj:obj,
				dprtcode:dprtcode,//机构代码
				callback : function(data) {//回调函数
				if (data != null) {
					id = data;	
					var message = '保存成功!';
					var url = basePath+"/quality/radiolicense/updatesz";
					performAction(url, data, message);
				}
			}
		});
	});
}
function selectById(aircraftinfo,obj){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/aircraftinfo/selectById",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(aircraftinfo),
		dataType:"json",
		success:function(data){
			if(data != null){
				if(typeof(obj)=="function"){
					obj(data.aircraftinfo); 
				}
			}
		}
	});
}
function performAction(url, param, message){
	var this_ = this;
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage(message);
			$("#aircraftinfoModal").modal("hide");
			if(data){
				fjzch_0 = data;
			}
			aircraftinfoMain.reload(true,"syts1","asc");
		}
	});
}
//搜索条件重置
function searchreset(){
	 var zzjgid=$('#dprtId').val();
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	 $("#keyword_search").val("");
	 $("#dprtcode_search").val(userJgdm);
	 jxChange(userJgdm);
	 selectGs(userJgdm);
}
//附件下载
function downfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
}
