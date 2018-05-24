var zy=["","内部人员","外部人员"];
var jclx=["","奖励","惩罚"];
var xxlx=["","授权","执照"];
var kq=["未参加","出勤"];
$(document).ready(function(){
	Navigation(menuCode);
	//goPage();
	searchRevision();
	 
	 $('input[name=reserveId]:radio').on('click',function(){
		 if($(this).val()=='2') {
			 $('#usernamedisplaynone').show();
			 $('#usernamedisplayback').hide();
		 }
		 else {
			 $('#usernamedisplaynone').hide();
			 $('#usernamedisplayback').show();
		 }
	 });
	 
		

	 
	 validatorForm =  $('#form4').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	fssj: {
	                validators: {
	                	notEmpty: {
	                        message: '发生时间描述不能为空'
	                    }
	                }
	            },
	            bljlnr: {
	            	validators: {
	            		notEmpty: {
	            			message: '不良记录内容不能为空'
	            		}
	            	}
	            }
	        }
	    });
	 

	 
	 refreshPermission();
		validation();
		validation1();
		validation2();
		validation3();
		validation4();
	    // 初始化导入
	    importModal.init({
		    importUrl:"/quality/maintenancepersonnel/excelImport",
		    downloadUrl:"/common/templetDownfile/6",
			callback: function(data){
				// 刷新人员档案
				goPage();
				 $("#ImportModal").modal("hide");
			}
		});
	    
	  //初始化日志功能
		//logModal.init({code:'B_Z_001'});
});
//验证
function validation4(){
	 validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	wxrybh: {
	                validators: {
	                	notEmpty: {
	                        message: '人员编号不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
	                        message: '不能包含中文' 	
	                    }
	                }
	            },
	            wxxm: {
	                validators: {
	                    notEmpty: {
	                        message: '姓名不能为空'
	                    },
	                    regexp: {
	                        regexp: /^([a-zA-Z0-9\u4E00-\u9FA5]+)$/,
	                        message: '只能输入中英文数字'
	                    },
	                    stringLength: {
	                        max: 10,
	                        message: '长度最多不超过10个字符'
	                    }
	                }
	            },
	            zy: {
	            	validators: {
	            		notEmpty: {
	            			message: '专业不能为空'
	            		}
	            	}
	            },
	         
	            wxsr: {
	            	validators: {
	            		notEmpty: {
	            			message: '出生年月不能为空'
	            		}
	            	}
	            },
	            wxyxdz: {
	            	validators: {
	            		 regexp: {
		                        regexp: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
		                        message: '邮箱格式不正确'
		                 }
	            	}
	            }
	        }
	    });
}
//验证
function validation3(){
	 validatorForm =  $('#form5').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	jcrq: {
	                validators: {
	                	notEmpty: {
	                        message: '奖惩日期不能为空'
	                    }
	                }
	            },
	            sm: {
	                validators: {
	                	notEmpty: {
	                        message: '说明不能为空'
	                    }
	                }
	            },
	            cljg: {
	            	validators: {
	            		notEmpty: {
	            			message: '处理结果不能为空'
	            		}
	            	}
	            }
	        }
	    });
}
//验证
function validation2(){
	 validatorForm =  $('#form2').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	zjbh: {
	                validators: {
	                	notEmpty: {
	                        message: '证件编号不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[a-zA-Z0-9_@]{0,50}$/,
	                        message: '只能包含字母数字_@且长度不超过50个字符' 	
	                    }
	                }
	            },
	            zjmc: {
	            	validators: {
	            		notEmpty: {
	            			message: '证件名称不能为空'
	            		}
	            	}
	            }
	        }
	    });
}

//验证
function validation1(){
	 validatorForm =  $('#form3').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	fssj: {
	                validators: {
	                	notEmpty: {
	                        message: '发生时间描述不能为空'
	                    }
	                }
	            },
	            bljlnr: {
	            	validators: {
	            		notEmpty: {
	            			message: '不良记录内容不能为空'
	            		}
	            	}
	            }
	        }
	    });
}



//验证
function validation(){
	 validatorForm =  $('#form1').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	gzsjms: {
	                validators: {
	                	notEmpty: {
	                        message: '工作时间描述不能为空'
	                    }
	                }
	            },
	            gznr: {
	            	validators: {
	            		notEmpty: {
	            			message: '工作内容不能为空'
	            		}
	            	}
	            }
	        }
	    });
	
	 changefjjx();
}



//改变组织机构时改变飞机注册号
function changefjjx(){
	var dprtcode = $.trim($("#dprtcode").val());
	var planeRegOption='';
	var planeDatas=acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
		});
	}
	if(planeRegOption == ''){
		planeRegOption = '<option value="">暂无飞机机型  No plane</option>';
	}
	$("#fjjx").html(planeRegOption);
}

var datas=[];
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(){
		var obj ={};
		var searchParam = gatherSearchParam();
		obj.keyword =  $.trim($("#keyword_search").val());
		obj.dprtcode = searchParam.dprtcode;
		
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/quality/maintenancepersonnel/maintenancepersonnelList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				   datas=data;
				    finishWait();
				    if(data.rows !=""){
				    	appendContentHtml(data.rows);
						// 标记关键字
				       signByKeyword($("#keyword_search").val(),[2]);
				 
				       
					   } else {
						  $("#List").empty();
						  $("#List").append("<tr><td class='text-center' colspan=\"2\">暂无数据 No data.</td></tr>");
						  $("#joblogginglist").empty();
						  $("#joblogginglist").append("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
						  $("#fileslist").empty();
						  $("#fileslist").append("<tr class='text-center'><td colspan=\"6\">暂无数据 No data.</td></tr>");
						  $("#accreditList").empty();
						  $("#accreditList").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
						  $("#badnessList").empty();
						  $("#badnessList").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
						  $("#rewardsList").empty();
						  $("#rewardsList").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
						  $("#cultivateList").empty();
						  $("#cultivateList").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
					   }
		      }
		    }); 
	 }
	 
	 //单行选择
	 function checkedAll() {
		 var falg=false;
		 $("#List tr").on("click",function(){
			  falg=true;
				var index=$(this).index(); //当前行
				var id =$("input[name='id']").eq(index).val(); //当前行，隐藏id的值
				var obj ={};
				obj.id =id;
				startWait();
				AjaxUtil.ajax({
					   url:basePath+"/quality/maintenancepersonnel/maintenancepersonnel",
					   type: "post",
					   contentType:"application/json;charset=utf-8",
					   dataType:"json",
					   data:JSON.stringify(obj),
					   success:function(data){
						    finishWait();
						    $("#xm").text(formatUndefine(data.xm));
						    $("#rybh").text(formatUndefine(data.rybh));
						    $("#zw").text(formatUndefine(data.zw));
						    $("#gwms").text(StringUtil.escapeStr(data.gw));
						    
							TimeUtil.getCurrentDate(function(date){
								 for(var i =0;i<datas.rows.length;i++){
									 var sDate=formatUndefine(data.sr).substr(0,10);
									 var eDate=date;
								
									 var sArr = sDate.split("-");
									 var eArr = eDate.split("-");
									 var sRDate = new Date(sArr[0], sArr[1], sArr[2]);
									 var eRDate = new Date(eArr[0], eArr[1], eArr[2]);
									 var result = ((eRDate-sRDate)/(24*60*60*1000))/365;
									 var a = parseFloat(result.toFixed(0));
									 
									  $("#sr").text(a);	
								 }
						 });
						    
						    $("#yxdz").text(formatUndefine(data.yxdz));
						    $("#xlms").text(StringUtil.escapeStr(data.xl));
						    
						    $("#zyms").text(StringUtil.escapeStr(data.zy));
						    $("#zzh").text(formatUndefine(data.zzh));
						    if(data.zpdzD!=null){
						    	 //$("#imgs").attr("src", window.URL.createObjectURL("downfile?wj="+data.zpdzD+"'"));
						    	$("#imgs").html("<img style='width:100%;height:100%;' src='downfile?wj="+data.zpdzD+"'>");
						    }else{
						    	$("#imgs").html("<img src='"+basePath+"/static/images/maintenanceTest.png' >");
						    }
						    $("#mainid").val(data.id); //工作记录父id
						    $("#filemainid").val(data.id); //附件父id
						    $("#accreditmainid").val(data.id); //授权记录父id
						    $("#badnessmainid").val(data.id); //诚信记录父id
						    $("#rewardsmainid").val(data.id); //奖惩记录父id
						    
						  	goPageaccredit(data.id);//授权记录
						  	
						  	//将右侧人员信息div滚动到最上面
						  	try{
						  		$("#personContentDiv").scrollTop(0);
						  	}catch(e){}
						  	
				      }
				    }); 
			});
		 if(falg==false){
				var id =$("input[name='id']").eq(0).val(); //当前行，隐藏id的值
				var obj ={};
				obj.id =id;
				startWait();
				 AjaxUtil.ajax({
					   url:basePath+"/quality/maintenancepersonnel/maintenancepersonnel",
					   type: "post",
					   contentType:"application/json;charset=utf-8",
					   dataType:"json",
					   data:JSON.stringify(obj),
					   success:function(data){
						    finishWait();
						    $("#xm").text(formatUndefine(data.xm));
						    $("#rybh").text(formatUndefine(data.rybh));
						    $("#zw").text(formatUndefine(data.zw));
						    $("#gwms").text(StringUtil.escapeStr(data.gw));
						    
							TimeUtil.getCurrentDate(function(date){
								 for(var i =0;i<datas.rows.length;i++){
									 var sDate=formatUndefine(data.sr).substr(0,10);
									 var eDate=date;
									 var sArr = sDate.split("-");
									 var eArr = eDate.split("-");
									 var sRDate = new Date(sArr[0], sArr[1], sArr[2]);
									 var eRDate = new Date(eArr[0], eArr[1], eArr[2]);
									 var result = ((eRDate-sRDate)/(24*60*60*1000))/365;
									 var a = parseFloat(result.toFixed(0));
									  $("#sr").text(a);	
								 }
						 });
						    
						    $("#yxdz").text(formatUndefine(data.yxdz));
						    $("#xlms").text(StringUtil.escapeStr(data.xl));
						    $("#zyms").text(StringUtil.escapeStr(data.zy));
						    $("#zzh").text(formatUndefine(data.zzh));
						    if(data.zpdzD!=null){
						    	 //$("#imgs").attr("src", window.URL.createObjectURL("downfile?wj="+data.zpdzD+"'"));
						    	$("#imgs").html("<img src='downfile?wj="+data.zpdzD+"'>");
						    }else{
						    	$("#imgs").html("<img src='"+basePath+"/static/images/maintenanceTest.png' >");
						    }
						    $("#mainid").val(data.id); //工作记录父id
						    $("#filemainid").val(data.id); //附件父id
						    $("#accreditmainid").val(data.id); //授权记录父id
						    $("#badnessmainid").val(data.id); //诚信记录父id
						    $("#rewardsmainid").val(data.id); //奖惩记录父id
						    
						  	goPageaccredit(data.id);//授权记录
				      }
				    });  
		 }
	 }
	 
	 //全部
	 function changeqb(e){
		
		 goPage();
		 excelzy="";
		 excelzznx="";
		 excelbs=0;
		 
	 }
	 //专业
	 function changezy(e){
		 excelzy=e;
		 var fileArray = [];
		 for(var i =0;i<datas.rows.length;i++){
			 if(datas.rows[i].zy == e){
				 fileArray.push(datas.rows[i]);
			 }
		 }
		 $("#xm").text("");
		    $("#rybh").text("");
		    $("#zw").text("");
		    $("#gwms").text("");
		    $("#sr").text("");	
		    $("#yxdz").text("");
		    $("#xlms").text("");
		    $("#zyms").text("");
		    $("#zzh").text("");
		    $("#imgs").html("");
		    if(fileArray.length==0){
		    	 $("#List").empty();
				  $("#List").append("<tr><td class='text-center' colspan=\"2\">暂无数据 No data.</td></tr>");
				  $("#joblogginglist").empty();
				  $("#joblogginglist").append("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
				  $("#fileslist").empty();
				  $("#fileslist").append("<tr class='text-center'><td colspan=\"6\">暂无数据 No data.</td></tr>");
				  $("#accreditList").empty();
				  $("#accreditList").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
				  $("#badnessList").empty();
				  $("#badnessList").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
				  $("#rewardsList").empty();
				  $("#rewardsList").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
				  $("#cultivateList").empty();
				  $("#cultivateList").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>")
		    }else{
		    	 appendContentHtml(fileArray);
		    }
	 }
	 //内外
	 function changeSelected(e){
		 excelbs=e;
	 var fileArray = [];
		for(var i =0;i<datas.rows.length;i++){
			if(datas.rows[i].wbbs == e){
				fileArray.push(datas.rows[i]);
			}
		}
		$("#xm").text("");
	    $("#rybh").text("");
	    $("#zw").text("");
	    $("#gwms").text("");
	    $("#sr").text("");	
	    $("#yxdz").text("");
	    $("#xlms").text("");
	    $("#zyms").text("");
	    $("#zzh").text("");
	    $("#imgs").html("");
	    if(fileArray.length==0){
	    	
	    	  $("#List").empty();
			  $("#List").append("<tr><td class='text-center' colspan=\"2\">暂无数据 No data.</td></tr>");
			  $("#joblogginglist").empty();
			  $("#joblogginglist").append("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
			  $("#fileslist").empty();
			  $("#fileslist").append("<tr class='text-center'><td colspan=\"6\">暂无数据 No data.</td></tr>");
			  $("#accreditList").empty();
			  $("#accreditList").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
			  $("#badnessList").empty();
			  $("#badnessList").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
			  $("#rewardsList").empty();
			  $("#rewardsList").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
			  $("#cultivateList").empty();
			  $("#cultivateList").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>")
	    }else{
	    	appendContentHtml(fileArray);
	    }
	 }
	 
	 //在职年限
	 function changeSelect(e){
		TimeUtil.getCurrentDate(function(date){
			
			 var fileArray = [];
			 for(var i =0;i<datas.rows.length;i++){
				 if(datas.rows[i].rzrq==null||datas.rows[i].rzrq==""){
					 fileArray.push(0);
				 }else{
					 var sDate=formatUndefine(datas.rows[i].rzrq).substr(0,10);
					 var eDate=date;
					 var sArr = sDate.split("-");
					 var eArr = eDate.split("-");
					 var sRDate = new Date(sArr[0], sArr[1], sArr[2]);
					 var eRDate = new Date(eArr[0], eArr[1], eArr[2]);
					 var result = ((eRDate-sRDate)/(24*60*60*1000))/365;
					 var a = parseFloat(result.toFixed(0));
					 excelzznx=e;
							if(e==1){
								if(a<=2){
									fileArray.push(datas.rows[i]);
								}
							}
							if(e==2){
								if(a>2&&a<=5){
									fileArray.push(datas.rows[i]);
								}
							}
							if(e==3){
								if(a>5){
									fileArray.push(datas.rows[i]);
								}
							}
				 }
			 }
			//
			 	$("#xm").text("");
			    $("#rybh").text("");
			    $("#zw").text("");
			    $("#gwms").text("");
			    $("#sr").text("");	
			    $("#yxdz").text("");
			    $("#xlms").text("");
			    $("#zyms").text("");
			    $("#zzh").text("");
			    $("#imgs").html("");
			    if(fileArray.length==0){
			    	
			    	  $("#List").empty();
					  $("#List").append("<tr><td class='text-center' colspan=\"2\">暂无数据 No data.</td></tr>");
					  $("#joblogginglist").empty();
					  $("#joblogginglist").append("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
					  $("#fileslist").empty();
					  $("#fileslist").append("<tr class='text-center'><td colspan=\"6\">暂无数据 No data.</td></tr>");
					  $("#accreditList").empty();
					  $("#accreditList").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
					  $("#badnessList").empty();
					  $("#badnessList").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
					  $("#rewardsList").empty();
					  $("#rewardsList").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
					  $("#cultivateList").empty();
					  $("#cultivateList").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>")
			    }else{
			    	 appendContentHtml(fileArray);
			    }
		
	 });
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.dprtcode = $("#dprtcode").val();
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		 TimeUtil.getCurrentDate(function(date){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
			   }
			   if(row.zpdzX!=null){
				   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-left'><img src='downfile?wj="+StringUtil.escapeStr(row.zpdzX)+"' >"+StringUtil.escapeStr(row.xm);  
			   }else{
				   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-left'><img src='"+basePath+"/static/images/maintenanceTest1.png' >"+StringUtil.escapeStr(row.xm);  
			   }
			   htmlContent += "</td>";
			   
			   if(row.wbbs==1){
				   htmlContent = htmlContent + " <td style='vertical-align:middle' class='text-left'><i class='icon-user' title='内部人员  Wait Remark' style='margin-right: 5px; color:#428bca;'></i>";  
			   }else{
				   htmlContent = htmlContent + " <td style='vertical-align:middle' class='text-left'><i class='icon-user' title='外部人员  Edit Remark' style='margin-right: 5px; '></i>";  
			   }
			   htmlContent = htmlContent + "<input type='hidden'  name='id' value='"+row.id+"'>"+StringUtil.escapeStr(row.zy)+"";  
			   var sDate;
				if(row.rzrq!=null){
					 sDate=formatUndefine(row.rzrq).substr(0,10);
				}else{
					 sDate=date;
				}
				 var eDate=date;
				 var sArr = sDate.split("-");
				 var eArr = eDate.split("-");
				 
				 var sRDate = new Date(sArr[0], sArr[1], sArr[2]);
				 var eRDate = new Date(eArr[0], eArr[1], eArr[2]);
				 var result = ((eRDate-sRDate)/(24*60*60*1000))/365;
				 
				 var a = parseFloat(result.toFixed(0));
				 htmlContent = htmlContent + " "+a+"年 </td>";  
				
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#List").empty();
		   $("#List").html(htmlContent);
		   refreshPermission();
		   checkedAll();
		 });
		 
		// refreshPermission();
		 
	 }
	 
	 
    //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(){
		AjaxGetDatasWithSearch();
	}	
	
	//信息检索
	function searchRevision(){
		
		 //加载专业
		 var zy=DicAndEnumUtil.getDicItems(4,$('#dprtcode').val());
		  var htmlContent = '';
		 for (var i = 0; i < zy.length; i++) {
				htmlContent +=" <li role='presentation'><a role='menuitem' href='javascript:void(0);' tabindex='-1' onclick=\"changezy('"+zy[i].id+"')\">"+zy[i].name+"</a></li>";
		}
		 $("#zyjz").html(htmlContent);
		 
		goPage();
		$("#xm").text("");
	    $("#rybh").text("");
	    $("#zw").text("");
	    $("#gwms").text("");
	    $("#sr").text("");	
	    $("#yxdz").text("");
	    $("#xlms").text("");
	    $("#zyms").text("");
	    $("#zzh").text("");
	    $("#imgs").html("");
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
	 var zzjgid=$('#zzjgid').val();
	 $("#keyword_search").val("");
	 $("#dprtcode").val(zzjgid);
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

		
		$('.date-picker').datepicker({
			autoclose : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
		});
		
		$('.date-picker1').datepicker( {
		    changeMonth: true, 
		    changeYear: true, 
		    showButtonPanel: false, 
		    dateFormat: 'yyyy-MM', 
		    onClose: function(dateText, inst) { 
		        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
		        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
		        $(this).datepicker('setDate', new Date(year, month, 1)); 
		    } 
		});
		
		 $('#example27').multiselect({
			buttonClass : 'btn btn-default',
			buttonWidth : 'auto',

			includeSelectAllOption : true
		}); 
		 
		 //回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		};
		 
		 /**
		  * 选择用户
		  * @returns
		  */
		 function selectUser(){
		 	userModal.show({
		 		selected:$("#userid").val(),//原值，在弹窗中默认勾选
		 		dprtcode:$("#dprtcode").val(),
		 		callback: function(data){//回调函数
		 			$("#userid").val(data.id);
		 			$("#username").val(data.realname);
		 			$("#username1").val(data.realname);
		 			$("#ckbmid").val(data.bmdm);
		 			
		 		}
		 	});
		 }	
		 
		 //授权记录列表
		 function goPageaccredit(mainid){
			var obj ={};
			
			obj.mainid = mainid;
			startWait();
			AjaxUtil.ajax({
				   url:basePath+"/quality/maintenancepersonnel/accreditList",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(obj),
				   success:function(data){
					    finishWait();
					    if(data.rows !=""){
					    	appendContentHtmlaccredit(data.rows);
					    	
						   } else {
							  $("#accreditList").empty();
							  $("#accreditList").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
						   }
					    new Sticky({tableId:'sqjl'});
			      }
			    }); 
		 }
		 //授权记录加载	
		 function appendContentHtmlaccredit(list){
			   var htmlContent = '';
			   $.each(list,function(index,row){
				
				   if (index % 2 == 0) {
					   htmlContent += "<tr bgcolor=\"#f9f9f9\">";
				   } else {
					   htmlContent += "<tr bgcolor=\"#fefefe\">";
				   }
				   
				   htmlContent = htmlContent + "<td class='fixed-column text-center' >";
				   if(row.zt==1){
					   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:authorization:manage:02' zjbh='"+StringUtil.escapeStr(row.zjbh)+"' " +
					   "zjmc='"+StringUtil.escapeStr(row.zjmc)+"' xxlx='"+StringUtil.escapeStr(row.xxlx)+"' " +
					   "bfrq='"+formatUndefine(row.bfrq)+"' bfdw='"+StringUtil.escapeStr(row.bfdw)+"' " +
					   "yxqKs='"+formatUndefine(row.yxqKs)+"' yxqJs='"+formatUndefine(row.yxqJs)+"' sqdj='"+StringUtil.escapeStr(row.sqdj)+"' xc='"+StringUtil.escapeStr(row.xc)+"' " +
					   "fjjx='"+StringUtil.escapeStr(row.fjjx)+"'  bz='"+StringUtil.escapeStr(row.bz)+"'  onClick=\"editAccredit('" + row.id + "',event)\" title='修改 Edit'></i>";
					   htmlContent +="&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='quality:authorization:manage:03' onClick=\"cancelAccredit('"+ row.id + "','"+ row.mainid + "')\" title='作废  Invalid'></i>";
				   }
				   htmlContent += "</td>";
				   htmlContent += "<td class=' text-center' >"+(index+1)+"</td>";
				   htmlContent += "<td class=' text-left' ><input type='hidden'  name='id' value='"+row.id+"'>"+StringUtil.escapeStr(row.zjbh)+"</td>";  
				   htmlContent += "<td class=' text-left' title='"+StringUtil.escapeStr(row.zjmc)+"'>"+StringUtil.escapeStr(row.zjmc)+"</td>";  
				   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(xxlx[row.xxlx])+"</td>";  
				   htmlContent += "<td class='text-center'>"+formatUndefine(row.bfrq).substring(0,10)+"</td>";  
				   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bfdw)+"'>"+StringUtil.escapeStr(row.bfdw)+"</td>";  
				   htmlContent += "<td class='text-center'>"+formatUndefine(row.yxqKs).substring(0,10)+"</td>";  
				   htmlContent += "<td class='text-center'>"+formatUndefine(row.yxqJs).substring(0,10)+"</td>";  
				   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.sqdj)+"</td>";  
				   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.xc)+"'>"+StringUtil.escapeStr(row.xc)+"</td>";  
				   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.fjjx)+"</td>";  
				   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";  
				   htmlContent += "</tr>";  
			   });
			   $("#accreditList").empty();
			   $("#accreditList").html(htmlContent);
			   refreshPermission();
		 }
		 
	 //作废授权记录
	 function cancelAccredit(id,mainid) {
			var accreditmainid = $("#accreditmainid").val();
			AlertUtil.showConfirmMessage("您确定要作废吗？",{callback: function(){
	 		startWait();
	 		var obj ={};
		    obj.mainid = mainid;
		    obj.id = id;
		    AjaxUtil.ajax({
	 			url:basePath + "/quality/maintenancepersonnel/cancelAccredit",
	 			type:"post",
	 			async: false,
	 			contentType:"application/json;charset=utf-8",
				dataType:"json",
			    data:JSON.stringify(obj),
	 			success:function(data){
	 				finishWait();
	 				AlertUtil.showMessage('作废成功!');
	 				goPageaccredit(accreditmainid);
	 				refreshPermission();
	 			}
	 		});
		}});
	 }	 
		 
		 
	//新增授权记录
	 function changeAccredit(){
		 validation2();
		 if($("#accreditmainid").val()==""){
				AlertUtil.showErrorMessage("请选择维修档案人员!");
		 }else{
				$("#accreditId").val("");
			    $("#accredit").html("新增授权记录");
			    $("#accreditrec").html("Add Authorization");
				
			    $("#alertModalAccredit").find("input").each(function() {
					$(this).attr("value", "");
				});
				
				 $("#alertModalAccredit").find("textarea").each(function(){
					 $(this).val("");
				 });
				 
				 $("#alertModalAccredit").find("select").each(function(){
						$(this).val("");
				});
				 $("#wbwjm").val("");
				  $("#filelist").empty();
			 	$("#alertModalAccredit").modal("show");
				 }
		 var SelectArr = $("#xxlxs select");
		  SelectArr[0].options[0].selected = true; 
		  
			 var SelectArr1 = $("#fjjxs select");
			  SelectArr1[0].options[0].selected = true; 
		  
		
	 }
	 
	 //修改授权记录
	 function editAccredit(id,e){
		 validation2();
		 
		 var zjbh = $(e.target).attr("zjbh");
		 var zjmc = $(e.target).attr("zjmc");
		 var xxlx = $(e.target).attr("xxlx");
		 var bfrq = $(e.target).attr("bfrq");
		 $("#bfrq").datepicker("update");
		 var bfdw = $(e.target).attr("bfdw");
		 var yxqKs = $(e.target).attr("yxqKs");
		 var yxqJs = $(e.target).attr("yxqJs");
		 
		 var sqdj = $(e.target).attr("sqdj");
		 var xc = $(e.target).attr("xc");
		 var fjjx = $(e.target).attr("fjjx");
		 var bz = $(e.target).attr("bz");
		 
		 $("#zjbh").val(zjbh);
		 $("#zjmc").val(zjmc);
		 $("#xxlx").val(xxlx);
		 $("#bfrq").val(bfrq);
		 $("#bfdw").val(bfdw);
		 if(formatUndefine(yxqKs)!=""&&formatUndefine(yxqJs)!=""){
			 $("#flightDate_search").val(formatUndefine(yxqKs).substring(0,10)+" ~ "+formatUndefine(yxqJs).substring(0,10));
		 }else{
			 $("#flightDate_search").val("");
		 }
		 $("#sqdj").val(sqdj);
		 $("#xc").val(xc);
		 $("#fjjx").val(fjjx);
		 $("#bz").val(bz);
		 $("#wbwjm").val("");
		 $("#accreditId").val(id);
		 $("#accredit").html("修改授权记录");
		    $("#accreditrec").html("Update Authorization");
		    
		    var obj ={};
			obj.id =id;
			AjaxUtil.ajax({
				   url:basePath+"/quality/authorization/filelist",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(obj),
				   success:function(data){
					   appendContentHtmlrewards(data.rows);
			      }
			    }); 
		    
		 $("#alertModalAccredit").modal("show");
	 }	
	 
	 //文件加载	
	 function appendContentHtmlrewards(list){
		 var trHtml = '';
		 $.each(list,function(index,row){
			 
			 trHtml = trHtml+ '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+row.id+'\'>';
			 trHtml = trHtml+'<td><div>';
			 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile1(\''+row.id+ '\')" title="删除 Delete">  ';
			 trHtml = trHtml+'</div></td>';
			 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(row.wbwjm)+'"> <a href=\'javascript:downloadfile("'+row.id+'");\' >'+StringUtil.escapeStr(row.wbwjm)+'</a></td>';
			 trHtml = trHtml+'<td class="text-left">'+bytesToSize(row.wjdx)+' </td>';
			 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(row.czrname)+'</td>';
			 trHtml = trHtml+'<td>'+StringUtil.escapeStr(row.czsj)+'</td>';
			 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+StringUtil.escapeStr(row.cflj)+'\'/>';
			 trHtml = trHtml+'<input type="hidden" name="biaoshi" value=\'1\'/>';
			 trHtml = trHtml+'<input type="hidden" name="id" value=\''+StringUtil.escapeStr(row.id)+'\'/>';
			 trHtml = trHtml+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(row.yswjm)+'\'/>';
			 trHtml = trHtml+'<input type="hidden" name="wbwjm1" value=\''+StringUtil.escapeStr(row.wbwjm)+'\'>';
			 trHtml = trHtml+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(row.nbwjm)+'\'>';
			 trHtml = trHtml+'<input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(row.cflj)+'\'>';
			 trHtml = trHtml+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(row.wjdx)+'\'>';
			 trHtml = trHtml+'<input type="hidden" name="hzm" value=\''+StringUtil.escapeStr(row.hzm)+'\'>';
			 trHtml = trHtml+'</tr>';	 
		 });
		 
		  $("#filelist").empty();
		   $("#filelist").html(trHtml);
	 }
	 
	//隐藏Modal时验证销毁重构
	 $("#alertModalAccredit").on("hidden.bs.modal", function() {
	 	 $("#form2").data('bootstrapValidator').destroy();
	      $('#form2').data('bootstrapValidator', null);
	      validation2();
	 });
	 
	 //保存授权记录
	 function saveAccredit(){
		 
		 $('#form2').data('bootstrapValidator').validate();
		  if(!$('#form2').data('bootstrapValidator').isValid()){
			return false;
		  }	
			var obj ={};
			var accreditId = $("#accreditId").val();
			var url ="";
			if(accreditId==""){
				 url = basePath+"/quality/maintenancepersonnel/saveAccredit";//新增
			}else{
				 url = basePath+"/quality/maintenancepersonnel/editAccredit";//修改
				 obj.id = accreditId;
			}
		
			
			var workContentParam = [];
			$("#filelist").find("tr").each(function(){
				var infos ={};
				var index=$(this).index(); //当前行
				
				var id =$("input[name='id']").eq(index).val(); //当前行，隐藏id的值
				var yswjm =$("input[name='yswjm']").eq(index).val(); //当前行，隐藏件号的值
				var nbwjm =$("input[name='nbwjm']").eq(index).val(); //当前行，隐藏件号的值
				var wbwjm =$("input[name='wbwjm1']").eq(index).val(); //当前行，隐藏件号的值
				var cflj =$("input[name='cflj']").eq(index).val(); //中文名称
				var wjdx =$("input[name='wjdx']").eq(index).val(); //英文名称
				var biaoshi =$("input[name='biaoshi']").eq(index).val(); //厂家件号
				var hzm =$("input[name='hzm']").eq(index).val(); //航材类型
				
				infos.id  = id;
				infos.yswjm  = yswjm;
				infos.nbwjm  = nbwjm;
				infos.wbwjm  = wbwjm;
				infos.cflj  = cflj;
				infos.wjdx  = wjdx;
				infos.biaoshi  = biaoshi;
				infos.hzm  = hzm;
				workContentParam.push(infos);
			});
			
			obj.attachments=workContentParam;
			
		 	var yxqKs ="";
		 	var yxqJs ="";
			var accreditmainid = $("#accreditmainid").val();
			var zjbh = $("#zjbh").val();
		 	var zjmc = $("#zjmc").val();
		 	var xxlx = $("#xxlx").val();
		 	var bfrq = formatUndefine($("#bfrq").val()).substring(0,10);
		 	var bfdw = $("#bfdw").val();
		 	var flightdate = $("#flightDate_search").val();
		
			 if(null != flightdate && "" != flightdate){
				yxqKs = flightdate.substring(0,4)+"-"+flightdate.substring(5,7)+"-"+flightdate.substring(8,10);
				yxqJs = flightdate.substring(12,17)+"-"+flightdate.substring(18,20)+"-"+flightdate.substring(21,23);
			 }
		 	var sqdj = $("#sqdj").val();
		 	var xc = $("#xc").val();
		 	var fjjx = $("#fjjx").val();
		 	var bz = $("#bz").val();
			obj.mainid = accreditmainid;
			obj.zjbh = zjbh;
			obj.zjmc = zjmc;
			obj.xxlx = xxlx;
			obj.bfrq = bfrq;
			obj.bfdw = bfdw;
			obj.yxqKs = yxqKs;
			obj.yxqJs = yxqJs;
			obj.sqdj = sqdj;
			obj.xc = xc;
			obj.fjjx = fjjx;
			obj.bz = bz;
		 
		 	startWait($("#alertModalAccredit"));
		 	AjaxUtil.ajax({
		 		url:url,
		 		contentType:"application/json;charset=utf-8",
		 		type:"post",
		 		async: false,
		 		data:JSON.stringify(obj),
		 		dataType:"json",
		 		modal:$("#alertModalAccredit"),
		 		success:function(data){
		 			finishWait($("#alertModalAccredit"));
		 			AlertUtil.showMessage('保存成功!');
		 			$("#alertModalAccredit").modal("hide");
		 			goPageaccredit(accreditmainid);
		 			refreshPermission();
		 		}
		 	});
	 }

	//附件列表
	 function goPagefile(mainid){
		 var obj ={};
		 obj.pagination = {sort:"auto",order:"desc"};
		 obj.id = mainid;
		 startWait();
		 AjaxUtil.ajax({
			   url:basePath+"/quality/maintenancepersonnel/maintenanceList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				    finishWait();
				    if(data.rows !=""){
						   appendContentHtmlFile(data.rows);
						   
						// 标记关键字
						   signByKeyword($("#keyword_search").val(),[3,4,7]);
						   
					   } else {
					
						  $("#fileslist").empty();
						  $("#fileslist").append("<tr class='text-center'><td colspan=\"6\">暂无数据 No data.</td></tr>");
					   }
		     }
		   });  
	 }
	 
	 //附件加载	
	 function appendContentHtmlFile(list){
		 var htmlContent = '';
		   $.each(list,function(index,row){
			   
				if (index % 2 == 0) {
					htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
				} else {
					htmlContent += "<tr bgcolor=\"#fefefe\" >";
				}
			   
			   htmlContent = htmlContent + "<td class='text-center'><input class='monitorItem'  monitorclass='calendar' name='ids' id='ids' type='checkbox' value='"+row.id+"' onclick=selectNode('store','list') /><input type='hidden' value='"+StringUtil.escapeStr(row.wbwjm)+"' /></td>";  
			   htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'><a  href='javascript:void(0);' onclick=\"downloadfile('"+row.id+"')\"><img onerror='errorImg(this)' src='"+basePath+"/static/assets/img/"+row.hzm.toLowerCase()+".png' alt='file'><span name='keyword'>"+StringUtil.escapeStr(row.wbwjm)+"</span></a></td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(bytesToSize(row.wjdx))+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.czrid)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.czsj.substring(0,row.czsj.length-3))+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#fileslist").empty();
		   $("#fileslist").html(htmlContent);
		   refreshPermission();
	 }
	 
	//附件下载
	 function downloadfile(id){
		 PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	 }
	 function bytesToSize(bytes) {  
			if(Math.floor(bytes)>1024){
				bytes=(Math.floor(bytes)/1024).toPrecision(3)+"MB";
			}else{
				bytes=bytes+"KB";
			}
		     return bytes;   
	} 
	 
	//字节自动转化
	 function errorImg(img) {
	     img.src = basePath+"/static/assets/img/txt.png";
	     img.onerror = null;
	 }
	 
	/**
	* 删除附件
	* @param newFileName
	*/
	function delfile1(id) {
			$('#'+id).remove();
	}
	 
	//上传
		var uploadObj = $("#fileuploader").uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					showStatusAfterSuccess : false,
					showDelete : false,
					maxFileCount : 100,
					formData : {
						"fileType" : "authorization",
						"wbwjm" : function() {
							return $('#wbwjm').val();
						}
					},
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,
					showStatusAfterError: false,
					uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
					uploadButtonClass: "ajax-file-upload_ext",
					onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
					{
						var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
						 trHtml = trHtml+'<td><div>';
						 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
						 trHtml = trHtml+'</div></td>';
						 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
						 
						 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].wjdxStr)+' </td>';
						 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].user.username)+" "+StringUtil.escapeStr(data.attachments[0].user.realname)+'</td>';
						 trHtml = trHtml+'<td>'+StringUtil.escapeStr(data.attachments[0].czsj)+'</td>';
						 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+StringUtil.escapeStr(data.attachments[0].relativePath)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="biaoshi" value=\'2\'/>';
						 trHtml = trHtml+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(data.attachments[0].yswjm)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="wbwjm1" value=\''+StringUtil.escapeStr(data.attachments[0].wbwjm)+'\'>';
						 trHtml = trHtml+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].nbwjm)+'\'>';
						 trHtml = trHtml+'<input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(data.attachments[0].cflj)+'\'>';
						 trHtml = trHtml+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(data.attachments[0].wjdx)+'\'>';
						 trHtml = trHtml+'<input type="hidden" name="hzm" value=\''+StringUtil.escapeStr(data.attachments[0].nbwjm.split(".")[1])+'\'>';
						 trHtml = trHtml+'</tr>';	 
						 $('#filelist').append(trHtml);
					},onSubmit : function (files, xhr) {
						var wbwjm  = $.trim($('#wbwjm').val());
						if(wbwjm.length>0){
							if(/^[\\<>/|:\"*?]*$/.test(wbwjm)){
				            	$('#wbwjm').focus();
				            	AlertUtil.showErrorMessage("文件说明不能包含特殊字符");
				            	
				            	$('.ajax-file-upload-container').html("");
								uploadObj.selectedFiles -= 1;
								return false;
				            } 
				            else{
				            	return true; 
				            }
						}else{
							return true;
						}
					}
				});

	/**
	* 删除附件
	* @param newFileName
	*/
	function delfile(uuid) {
		var  responses = uploadObj.responses;
		var len = uploadObj.responses.length;
		var fileArray = [];
		var waitDelArray = [];
		if(len > 0 ) {
			for(var i =0;i<len;i++){
				if(responses[i].attachments[0].uuid != uuid){
					fileArray.push(responses[i]);
				}
			}
			uploadObj.responses = fileArray;
			uploadObj.selectedFiles -= 1;
			$('#'+uuid).remove();
		}
	}
	
	 //附件新增
	 function uploading(){
		 
		 if($("#filemainid").val()==""){
				AlertUtil.showErrorMessage("请选择维修档案人员!");
		 }else{
		 
		 $('#filelist').empty();
		 	var fileArray = [];
		 	uploadObj.responses = fileArray;
		 	uploadObj.selectedFiles = 0;
		 $("#uploading").modal("show");
		 }
	 }
	 
	 //附件上传保存
	 function uploadingFile(){
		 
		 var workContentParam = [];
			$("#filelist").find("tr").each(function(){
				var infos ={};
				var index=$(this).index(); //当前行
				var fileName =$("input[name='fileName']").eq(index).val(); //执行对象
				var relativePath =$("input[name='relativePath']").eq(index).val(); //执行对象
				var newFileName =$("input[name='newFileName']").eq(index).val(); //飞机注册号
				var sm =$("input[name='sm']").eq(index).val(); //部件号
				var fileSize =$("input[name='fileSize']").eq(index).val(); //部件号
				
				infos.mainid  = $("#filemainid").val();
				infos.nbwjm  = newFileName;
				infos.cflj  = relativePath;
				infos.yswjm  = fileName;
				infos.wbwjm  = fileName;
				infos.wjdxs  = fileSize;
				workContentParam.push(infos);
			});
			  
				if(workContentParam==""||workContentParam==null){
					AlertUtil.showErrorMessage("上传文件不能为空");
					return false;
				}
			 
			var fixedCheckItem = workContentParam;
			startWait($("#uploading"));
			
			// 提交数据
			AjaxUtil.ajax({
				url : basePath+"/quality/maintenancepersonnel/uploadingFile",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(fixedCheckItem),
				dataType:"json",
				modal:$("#uploading"),
				success:function(data){
						finishWait($("#uploading"));//结束Loading
					if (data.state == "Success") {
						$("#uploading").modal("hide");
					
						 AlertUtil.showErrorMessage("上传成功");
						 goPagefile($("#filemainid").val());
						 refreshPermission();
					} else {
						$("#uploading").modal("hide");
						AlertUtil.showErrorMessage("上传失败");
					}
				}
			});
	 }	 
	 
	 /**
  	  * 显示导入模态框
  	  */
  	 function showImportModal(){
  		 importModal.show();
  	 }
  	 
  	$('#wxsr').datepicker({
  		 autoclose: true,
  		 clearBtn:true
  	}).on('hide', function(e) {
  		  $('#form').data('bootstrapValidator')  
  	 .updateStatus('wxsr', 'NOT_VALIDATED',null)  
  	 .validateField('wxsr');  
  	});
  	
  	$('#jcrq').datepicker({
 		 autoclose: true,
 		 clearBtn:true
 	}).on('hide', function(e) {
 		  $('#form5').data('bootstrapValidator')  
 	 .updateStatus('jcrq', 'NOT_VALIDATED',null)  
 	 .validateField('jcrq');  
 	});

