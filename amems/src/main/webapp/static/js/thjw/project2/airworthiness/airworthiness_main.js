	var alertModalAdd='AddalertModal';
	var fjzt_list_tableDiv_actualheight="";
	var uploadObj={};
	var objData={};
	var techoIds=[];
	var teorList=[];
	var deleteUploadId = "";
	var OutputAllIds=[];    //下达指令集合
$(function(){
	Navigation(menuCode, '', '', 'gc_001001', '孙霁', '2017-08-02', '孙霁', '2017-08-02');
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
	});
	initAutoComplete();
	$('.date-picker').datepicker({
		autoclose : true,
		clearBtn : true
	});
	//uploader();
	var dprtcode=$("#dprtcode").val();
	multiSelectReset(dprtcode);
	DicAndEnumUtil.registerDic('8','ly',dprtcode);
	
	airworthinessMain.reload();
	//验证
	validation();
	
	//初始化日志功能
	logModal.init({code:'B_G2_000'});
	
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
	        	jswjly: {
	                validators: {
	                	notEmpty: {
	                        message: '来源不能为空'
	                    }
	                }
	            },
	           jswjlx: {
	                validators: {
	                    notEmpty: {
	                        message: '文件类型不能为空'
	                    }
	                }
	            },
	           jswjwjbh: {
	                validators: {
	                    notEmpty: {
	                        message: '文件编号不能为空'
	                    },
	                    regexp: {
	                    	  regexp: /^[\x00-\xff]*$/,
	 	                      message: '文件编号不能输入中文及中文符号'
		                }
	                }
	            },
	      	  zjhs: {
	      		  trigger:"change", //关键配置  
				  validators: {
					  notEmpty: {message: 'ATA章节号不存在'}
				  }
			  }, 
	           jswjbb: {
	                validators: {
	                    notEmpty: {
	                        message: '版本不能为空'
	                    },
	                    regexp: {
	                    	  regexp: /^[\x00-\xff]*$/,
	 	                      message: '版本不能输入中文及中文符号'
		                }
	                }
	            }
	        }
	    });
}

function multiSelectReset(dprtcode){
	$("#wjlxHtml").empty();
	$("#wjlxHtml").html("<select class='form-control' id='wjlx'  multiple='multiple' ></select>");
	DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
	
	//生成多选下拉框
	$('#wjlx').multiselect({
		buttonClass: 'btn btn-default',
	    buttonWidth: '100%',
	    numberDisplayed:100,
	    buttonContainer: '<div class="btn-group" style="width:100%;" />',
	    nonSelectedText:'显示全部 All',
	    allSelectedText:'显示全部 All',
	    includeSelectAllOption: true,
	    selectAllText: '选择全部 Select All'
	});
}

//切换组织机构
function dprtChange(){
	var dprtcode=$("#dprtcode").val();
	$("#ly").empty();
	$("#ly").html("<option value=''>显示全部All</option>");
	//初始化wjlx
	multiSelectReset(dprtcode);
	DicAndEnumUtil.registerDic('8','ly',dprtcode);
}


var airworthinessMain={
		pagination : {},
		gatherSearchParam:function(){
			var searchParam={};
			if (id != "") {
				searchParam.id = id;
				id = "";
			}
			 searchParam.dprtcode=$("#dprtcode").val();
			 searchParam.jswjly=$("#ly").val();
			 searchParam.zt=$("#zt").val();
			 searchParam.keyword=$.trim($('#keyword_search').val());
			 var sxrq=$("#sxrq").val();
			 var pgqx=$("#pgqx").val();
			 var paramsMap={};
			 //生效日期
			 if(null != sxrq && "" != sxrq){
				 var sxrqBegin = sxrq.substring(0,10)+" 00:00:00";
				 var sxrqEnd = sxrq.substring(13,23)+" 23:59:59";
				 paramsMap.sxrqBegin = sxrqBegin;
				 paramsMap.sxrqEnd = sxrqEnd;
			 }else{
				 paramsMap.sxrqBegin = "";
				 paramsMap.sxrqEnd = "";
			 }
			 //评估期限
			 if(null != pgqx && "" != pgqx){
				 var pgqxBegin = pgqx.substring(0,10)+" 00:00:00";
				 var pgqxEnd = pgqx.substring(13,23)+" 23:59:59";
				 paramsMap.pgqxBegin = pgqxBegin;
				 paramsMap.pgqxEnd = pgqxEnd;
			 }else{
				 paramsMap.pgqxBegin = "";
				 paramsMap.pgqxEnd = "";
			 }
			  var xpgbsList=[];
			 if($("#xpgbs1").attr("checked")){
				 xpgbsList.push(1);
			}
			 if($("#xpgbs2").attr("checked")){
				 xpgbsList.push(0);
			 }
			 if(xpgbsList.length>0){
				 paramsMap.xpgbsList=xpgbsList;
			 }
			var wjlx = $("#wjlx").val();
			var wjlxList=[];
			 if(wjlx != null){
					for(var i = 0 ; i < wjlx.length ; i++){
						if('multiselect-all' != wjlx[i]){
							wjlxList.push(wjlx[i]);
						}
					}
				}
			 if(wjlxList != null && wjlxList.length > 0){
				 paramsMap.wjlxList=wjlxList;
			 }
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
			 
			 if(!searchParam.paramsMap ||searchParam.paramsMap.xpgbsList == null || searchParam.paramsMap.xpgbsList.length <= 0){
				 $("#airworthiness_list").empty();
				 $("#airworthiness_pagination").empty();
				 $("#airworthiness_list").append("<tr><td class='text-center' colspan=\"24\">暂无数据 No data.</td></tr>");
				 return false;
			 }
			 if(id != ""){
				 	searchParam.id = id;
					id = "";
				}
			 AjaxUtil.ajax({
				 url:basePath+"/project2/airworthiness/queryAll",
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
							renderTo : "airworthiness_pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							extParams:{},
							goPage: function(a,b,c){
								_this.AjaxGetDatasWithSearch(a,b,c);
							}
						});
			 		// 标记关键字
						   signByKeyword($("#keyword_search").val(),[5,8,16,20]);
			 		
			 		}else{
			 			$("#airworthiness_list").empty();
						$("#airworthiness_pagination").empty();
						$("#airworthiness_list").append("<tr><td class='text-center' colspan=\"24\">暂无数据 No data.</td></tr>");
						
					}
			 			new Sticky({tableId:'quality_check_list_table'});
			 			hideBottom();
			 		}
			 });
		},
		showUp: function(){
			var isBottomShown = false;
			if($(".displayContent").is(":visible")){
				isBottomShown = true;
			}
			if(isBottomShown){
				$("<div class='pull-right' id='hideIcon' style='margin-right:15px;'><img src='"+basePath+"/static/images/down.png' onclick='hideBottom()' style='width:36px;cursor:pointer;'></div>").insertAfter($(".fenye", $("#airworthiness_pagination")));
			}
		},
		reload:function(){
			TableUtil.resetTableSorting("quality_check_list_table");
			hideBottom();
			this.goPage(1,"auto","desc");
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			
			$.each(list,function(index,row){
				htmlContent += "<tr id='"+row.id+"' onclick='airworthinessMain.showChildTR(this)'>";
			    htmlContent += "<td class='fixed-column text-center'>";
			    htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' " +
			    "permissioncode='project2:airworthiness:main:02' onClick=\"edit('"+row.id+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";
			    if(row.zt==0){
			    	htmlContent += "<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='project2:airworthiness:main:03' onClick=\"invalid('"
			    		+ row.id + "')\" title='删除 Delete '></i>&nbsp;&nbsp;";
			    }
			    var yj = getWarningColor(row.paramsMap.syts,row.zt,row.yjzt,pgqx,row);
			    htmlContent += "</td>";
			    htmlContent += yj; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jswjlx))+"'>"+StringUtil.escapeStr(formatUndefine(row.jswjlx))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jswjly))+"'>"+StringUtil.escapeStr(formatUndefine(row.jswjly))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jswjbh))+"'><a href=\"javascript:view('"+row.id+"')\">"+StringUtil.escapeStr(formatUndefine(row.jswjbh))+"</a></td>";
			    htmlContent += "<td class='text-center' title=R"+StringUtil.escapeStr(formatUndefine(row.bb))+">R"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.xzah))+"'>"+StringUtil.escapeStr(formatUndefine(row.xzah))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jswjzt))+"'>"+StringUtil.escapeStr(formatUndefine(row.jswjzt))+"</td>"; 
			    htmlContent += "<td class='text-center' >"+(row.xpgbs==1?"是":"否")+"</td>"; 
			    htmlContent += "<td class='text-center' title='"+(row.pgqx?row.pgqx.substring(0,10):'')+"'>"+(row.pgqx?row.pgqx.substring(0,10):'')+"</td>"; 
			    if(yj !="<td class='text-center'></td>" || row.paramsMap.pgdcount > 0){
			    	htmlContent += "<td class='text-center'>"+row.paramsMap.syts+"</td>"; 
			    }else if(row.pgqx){
			    	htmlContent += "<td class='text-center'>-</td>"; 
			    }else{
			    	htmlContent += "<td class='text-center'></td>"; 
			    }
			    htmlContent += "<td class='text-left' title='"+_this.appendjxfw(row.technicalfileOrderList)+"'>"+_this.appendjxfw(row.technicalfileOrderList)+"</td>"; 
			    var gljswjAndBb = StringUtil.escapeStr(row.paramsMap.gljswjbh);
			    if(row.paramsMap.gljswjbb){
			    	gljswjAndBb += " R"+StringUtil.escapeStr(row.paramsMap.gljswjbb);
			    }
			    htmlContent += "<td class='text-left' title='"+gljswjAndBb+"'><a href=\"javascript:view('"+row.gljswjid+"')\">"+gljswjAndBb+"</a></td>"; 
			    htmlContent += "<td class='text-center' title='"+DicAndEnumUtil.getEnumName('airworthinessEnum',row.zt)+"'>"; 
			    htmlContent += DicAndEnumUtil.getEnumName('airworthinessEnum',row.zt); 
			    htmlContent += "</td>"; 
			    
			   
			    if(row.zj){
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ata))+" "+(row.zj?StringUtil.escapeStr(formatUndefine(row.zj.ywms)):'')+"'>"+StringUtil.escapeStr(formatUndefine(row.ata))+" "+(row.zj?StringUtil.escapeStr(formatUndefine(row.zj.ywms)):'')+"</td>"; 
			    }else{
			    	htmlContent += "<td class='text-left' ></td>"; 
			    }
			    htmlContent += "<td class='text-center' title='"+(row.bfrq?row.bfrq.substring(0,10):'' )+"'>"+(row.bfrq?row.bfrq.substring(0,10):'')+"</td>"; 
			    htmlContent += "<td class='text-center' title='"+(row.sxrq?row.sxrq.substring(0,10):'')+"'>"+(row.sxrq?row.sxrq.substring(0,10):'')+"</td>"; 
			    htmlContent += "<td class='text-center' title='"+(row.dqrq?row.dqrq.substring(0,10):'')+"'>"+(row.dqrq?row.dqrq.substring(0,10):'')+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bz))+"'>"+StringUtil.escapeStr(formatUndefine(row.bz))+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+(row.scfj?StringUtil.escapeStr(formatUndefine(row.scfj.wbwjm +"."+ row.scfj.hzm)):'')+"'>" +
	    		"<a href=\"javascript:downloadfile('"+(row.scfj?row.scfj.id:'#')+"')\">"+(row.scfj?StringUtil.escapeStr(formatUndefine(row.scfj.wbwjm +"."+ row.scfj.hzm)):'')+"</a></td>"; 
			   
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zdr.displayName))+"'>"+StringUtil.escapeStr(formatUndefine(row.zdr.displayName))+"</td>"; 
			    htmlContent += "<td class='text-center' title='"+row.zdsj+"'>"+row.zdsj+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+"'>"+StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+"</td>"; 
			    htmlContent += "</tr>" ;
		   $("#airworthiness_list").empty();
		   $("#airworthiness_list").html(htmlContent);
		   refreshPermission(); 
		 });
		},
		appendjxfw:function(list){
			 var htmlContent="";
			 if(list == null){
				 return htmlContent;
			 }
			 $.each(list,function(index,row){
				 if(index==0){
					 htmlContent += StringUtil.escapeStr(formatUndefine(row.fjjx));
				 }else{
					 htmlContent +=","+StringUtil.escapeStr(formatUndefine(row.fjjx));
				 }
			 });
			 return htmlContent;
		 },
		addTitle:function(){
			$("#maintenance_item_list tr td").each(function(){
				if(!$(this).attr("title")){
					$(this).attr("title", $(this).text());
				}
			});
		},
		//搜索条件显示与隐藏
		search:function(){
		
		},
		queryTechnicalfile:function(jswjid){
			var this_=this;
			AjaxUtil.ajax({
				 url:basePath+"/project2/assessment/selectTechnicalfileByJswjid",
				 type:"post",
				 async: false,
				 data:{
					 'jswjid' : jswjid
				 },
				 dataType:"json",
				 success:function(data){
					 if(data.technicalList.length > 0){
						 
						 this_.appendPgd(data.technicalList);
						 
						 if(data.todoList  && data.todoList.length > 0){
							 this_.appendXdzl(data.todoList);
						 }else{
								$("#airworthiness_xdzl").empty();
								$("#airworthiness_xdzl").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
							 }
					 }else{
						 
						$("#airworthiness_pgd").empty();
						$("#airworthiness_pgd").append("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
						$("#airworthiness_xdzl").empty();
						$("#airworthiness_xdzl").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
					 }
			 		}
			 });
		},
		appendPgd:function(list){
			var this_ = this;
			var htmlContent = '';
			$.each(list,function(index,row){
				var active='';
				if(index == 0){
					active="bg_tr";
				}
				htmlContent += "<tr class=' "+active+" ' id='"+row.id+"' onclick='airworthinessMain.queryTodors(this)'>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jx))+"' >"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.pgr.displayName))+"'>"+StringUtil.escapeStr(formatUndefine(row.pgr.displayName))+"</td>";
			    var pgdhAndBb = StringUtil.escapeStr(row.pgdh);
			    if(row.bb){
			    	pgdhAndBb = pgdhAndBb+" R" +StringUtil.escapeStr(row.bb);
			    }
			    htmlContent += "<td class='text-center' title='"+pgdhAndBb+"'><a href=\"javascript:findAssessmentView('"+row.id+"')\">"+pgdhAndBb+"</a></td>";
			    htmlContent += "<td class='text-center' title='"+DicAndEnumUtil.getEnumName('technicalStatusEnum',row.zt)+"'>"+DicAndEnumUtil.getEnumName('technicalStatusEnum',row.zt)+"</td>";
			    htmlContent += "<td class='text-left' title='"+this_.appendOrder(row.giveInstructionList?row.giveInstructionList:'')+"'>"+this_.appendOrder(row.giveInstructionList?row.giveInstructionList:'')+"</td>";
			    htmlContent += "</tr>" ;	
			});
			$("#airworthiness_pgd").empty();
			$("#airworthiness_pgd").html(htmlContent);
			refreshPermission(); 
		},
		appendOrder:function(list){
			var htmlContent='';
			if(list != null && list !=''){
				$.each(list,function(index,row){
					if(index == 0){
						htmlContent += DicAndEnumUtil.getEnumName('sendOrderEnum',row.zlxl);
					}else{
						if(htmlContent){
							if(DicAndEnumUtil.getEnumName('sendOrderEnum',row.zlxl)){
								htmlContent +="/"+DicAndEnumUtil.getEnumName('sendOrderEnum',row.zlxl);
							}else{
								htmlContent +=DicAndEnumUtil.getEnumName('sendOrderEnum',row.zlxl);
							}
						}else{
							htmlContent +=DicAndEnumUtil.getEnumName('sendOrderEnum',row.zlxl);
						}
					}
				});
			}
			return htmlContent;
		},
		queryTodors:function(obj){
			//行变色
			$(obj).addClass('bg_tr').siblings().removeClass('bg_tr');
			
			var pgdid = $(obj).attr("id");
			
			var this_=this;
			var todo = {
					lyid:pgdid
			};
			AjaxUtil.ajax({
				   url:basePath+"/project2/assessment/queryAllOrderList",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(todo),
				   success:function(data){
						 if(data.length > 0){
							 
							 this_.appendXdzl(data);
						 }else{
							 
							$("#airworthiness_xdzl").empty();
							$("#airworthiness_xdzl").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
						 }
			 		}
			 });
		},
		appendXdzl:function(list){
			var this_ = this;
			 var htmlContent = '';
			 OutputAllIds=[];
			 $.each(list,function(index,row){
				 OutputAllIds.push(index);
				 htmlContent += "<tr>";
				 htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.lybh)+" R"+StringUtil.escapeStr(row.bb)+"' ><a href=\"javascript:findAssessmentView('"+row.lyid+"')\">"+StringUtil.escapeStr(row.lybh)+" R"+StringUtil.escapeStr(row.bb)+"</a></td>";
			     htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('sendOrderEnum',row.dbgzlx))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('sendOrderEnum',row.dbgzlx))+"</td>"; 
			     htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zpr?row.zpr.id:'')+"'>"+StringUtil.escapeStr(row.zpr?row.zpr.id:'')+"</td>"; 
		    	 htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.blqx).substring(0,10)+"'>"+StringUtil.escapeStr(row.blqx).substring(0,10)+"</td>"; 
		    	 if(row.zt==1){
		    		 htmlContent += "<td class='text-center' title='未反馈'>未反馈</td>";
		    	 }else{
		    		 htmlContent += "<td class='text-center' title='已反馈'>" +
		    		 "<a lybh='"+StringUtil.escapeStr(row.lybh)+"'"+
		    		 "fjjx='"+StringUtil.escapeStr(row.fjjx)+"'"+
		    		 "dbgzlx='"+StringUtil.escapeStr(row.dbgzlx)+"'"+
		    		 "blrid='"+StringUtil.escapeStr(row.zpr.id)+"'"+
		    		 "fksj='"+StringUtil.escapeStr(row.fksj)+"'"+
		    		 "fkyj='"+StringUtil.escapeStr(row.fkyj)+"'"+
		    		 "href=\"javascript:void(0)\" onclick =openTodoView(this)>已反馈</a></td>"; 
		    	 }
		    	 htmlContent += "<td class='text-left'  >"+this_.outPut(row.instructionsourceList,index,row.dbgzlx)+"</td>"; 
			     htmlContent += "<td class='text-left' >"+this_.outPutSm(row.instructionsourceList,index)+"</td>"; 
			     htmlContent += "</tr>" ;
			 });
			 $("#airworthiness_xdzl").empty();
			 $("#airworthiness_xdzl").html(htmlContent);
			 refreshPermission();
			
			
		},
		outPutSm: function(data,index){
			 var htmlContent='';
			  if(data!=null){
				   var falg=true;
		 		   for (var i = 0; i < data.length; i++) {
								if(StringUtil.escapeStr(data[i].ywzt)!=''){
									 htmlContent +="<i title='"+StringUtil.escapeStr(data[i].ywzt)+"'>"+StringUtil.escapeStr(data[i].ywzt)+"</i>";
								}else{
									 htmlContent +="";
								}
						   if(falg==true){
							   if(data[i+1]!=null){
								   htmlContent += "<i  name='"+ index+ "icon1' onclick=vieworhideOutput1('"
								   + index + "')></i><div name='"+ index+ "content1' style='display:none;white-space:nowrap;'>";
							   }
							   falg=false;
						   }else{
							   htmlContent +="<br>";
						   }
						
						   if (i != 0 && i == data.length - 1) {
								htmlContent += "</div>";
						   }
				
						  
				   }

			  }

		  return htmlContent;
		},
		outPut:function (data,index,gzlx){
			var htmlContent='';
			  if(data!=null){
				   var falg=true;
		 		   for (var i = 0; i < data.length; i++) {
						   var zt="";
						   if(gzlx==1){//技术通告
							   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('bulletinStatusEnum',data[i].ywdjzt));
						   }else if(gzlx==2){//技术指令
							   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('technicalInstructionStatusEnum',data[i].ywdjzt));
						   }else if(gzlx==3){//维修方案
							   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('maintenanceSchemeStatusEnum',data[i].ywdjzt));
						   }else if(gzlx==4){//下达nrc
							   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workorderStatusEnum',data[i].ywdjzt));
							   htmlContent +="<a title='"+StringUtil.escapeStr(data[i].zlbh)+"【"+zt+"】' href=\"javascript:findOutputView('"+data[i].zlid+"',"+gzlx+")\">"+StringUtil.escapeStr(data[i].zlbh)+"</a>【"+zt+"】";
						   }else if(gzlx==6){//工程指令eo
							   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('engineeringOrderStatusEnum',data[i].ywdjzt));
						   }else if(gzlx==7){//mel
							   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('melStatusEnum',data[i].ywdjzt));
						   }else if(gzlx==8){//工卡
							   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workCardStatusEnum',data[i].ywdjzt));
						   }
						   if(gzlx!=4){
							   htmlContent +="<a title='"+StringUtil.escapeStr(data[i].zlbh)+" R"+StringUtil.escapeStr(data[i].zlbb)+"【"+zt+"】' href=\"javascript:findOutputView('"+data[i].zlid+"',"+gzlx+")\">"+StringUtil.escapeStr(data[i].zlbh)+" R"+StringUtil.escapeStr(data[i].zlbb)+"</a>【"+zt+"】 ";
						   }
		 				  
		 				   if(falg==true){
		 					   if(data[i+1]!=null){
		 						   htmlContent += "<i class='icon-caret-down cursor-pointer' id='"+ index+ "icon1' onclick=vieworhideOutput('"
		 						   + index + "')></i><div name='"+ index+ "content1' style='display:none;white-space:nowrap;'>";
		 					   }
		 					   falg=false;
		 				   }else{
		 					   htmlContent +="<br>";
		 				   }
		 				
		 				   if (i != 0 && i == data.length - 1) {
		 						htmlContent += "</div>";
		 				   }
				   }

			  }
				 		

		  return htmlContent;
		},
		showChildTR:function(obj){
			var this_=this;
			//行变色
			$(obj).addClass('bg_tr').siblings().removeClass('bg_tr');
			// 下方div是否显示
			new Sticky({tableId:'quality_check_list_table'});
			var isBottomShown = false;
			if($(".displayContent").is(":visible")){
				isBottomShown = true;
			}
			TableUtil.showDisplayContent();
			if($("#hideIcon").length == 0){
				$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#airworthiness_pagination .fenye"));
			}
			if(!isBottomShown){
				TableUtil.makeTargetRowVisible($(obj), $("#tableId"));
			}
			var ztContentHtml="";
			
			var airworthinessId=$(obj).attr("id");
			
			//加载评估单数据
			this_.queryTechnicalfile(airworthinessId);
		}
};
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
		airworthinessMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
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
function hideBottom(){
	/*if( $("#indexDiv").css("display")=="block"){
		  $("#indexDiv").slideUp(500);
		 }
		 $("#indexDiv").slideDown(500);*/
	$("#hideIcon").remove();
	TableUtil.hideDisplayContent();
}
function colseEoExecution(){
	$("#EOexecutionModal").modal("show");
}
//获取预警颜色
function getWarningColor(syts,zt,yjzt,pgqx,row){
	var htmlappend = "<td class='text-center'></td>";
	
	if(row.yjtsJb1 < Number(syts) && Number(syts) <= row.yjtsJb2){
		bgcolor = warningColor.level2;//黄色
		htmlappend = "<td class='text-center' title='"+row.yjtsJb1+"&lt;剩余评估期限&lt;="+row.yjtsJb2+"天"+"' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
	}
	if(Number(syts) <= row.yjtsJb1){
		bgcolor = warningColor.level1;//红色
		htmlappend =  "<td class='text-center' title='剩余评估期限&lt;="+row.yjtsJb1+"天"+"' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
	}
	if(zt == 0 && pgqx){
		return htmlappend;
	}
	
	if(yjzt == 0) {
		htmlappend = "<td class='text-center'></td>";
	}
	return htmlappend;
}

function save(){
	//$("#AddalertModal").modal("show");
	ModalUtil.showModal(alertModalAdd);
	$("#modalName").html("适航性资料上传");
	$("#modalEname").html("Add");
	
	validation();
	//隐藏下载和删除按钮
	$("#xiazai").hide();
	$("#shanchu").hide();
	//保存按钮显示
	$("#baocunButton").show();
	$("#dprtId").val(userJgdm);
	//清空弹窗
	modalEmpty();
	//加载机型
	var dprtcode=$("#dprtId").val();
	queryFjjx(dprtcode);
}
$('.date-picker').datepicker({
	autoclose : true,
	clearBtn:true
}).next().on("click", function() {
	$(this).prev().focus();
});
function modalEmpty(){
	//隐藏出现异常的感叹号
	AlertUtil.hideModalFooterMessage();
	var userId=$("#userId").val();
	$("#scfjBox").attr("checked",false);
	$("#fileuploaderSingle").hide();
	$("#scwjWbwjm").hide();
	
	techoIds=[];
	$("#jswjlx").val("");
	$("#jswjlx").empty();
	$("#jswjly").val("");
	$("#jswjly").empty();
	$("#jswjwjbh").val("");
	$("#airworthinessId").val("");
	$("#jswjbb").val("");
	$("#jswjsxrq").val("");
	$("#jswjpgqx").val("");
	$("#jswjdqrq").val("");
	$("#jswjbfrq").val("");
	$("#glshxzlBh").val("");
	$("#glshxzlid").val("");
	$("#gljspgdzhut").val("");
	$("#ataywmc").val("");
	$("#atazwmc").val("");
	$("#scfjId").val("");
	$("#scfjBox").attr("disabled",false);
	
	$("#pgqxDiv").show();
	
	$("#xzah").val("");
	$("#sm").val("");
	$("#downFileId").val("add");
	//上传附件清空
	$("#uploadFile").hide();
	deleteUploadId = "";
	uploader();
	$("#zhut").val("");
	$("#wczjhName").val("");
	$("#wczjh").val("");
	$("#gljswj").html("关联适航性资料");
	$("#airworId").val("");
	$("#bz").val("");
	$("#airworthinessDprtcode").val("");
	$("input:radio[value='1']").attr('checked',true);
	$("#jxchecked").show();
	$('#jxchecked input:checkbox').each(function () {
        $(this).attr('checked',false);
	});
	$('#jxchecked select').each(function () {
		 $(this).val(userId);
         $(this).hide();
	});
	$("#jxchecked input[name='tecoid']").each(function () {
		$(this).val("add");
		$(this).hide();
	});
	var dprtcode=$("#dprtId").val();
	DicAndEnumUtil.registerDic('16','jswjlx',dprtcode);
	DicAndEnumUtil.registerDic('8','jswjly',dprtcode);
	
	$('#jswjlx').removeAttr("disabled"); 
	$('#jswjly').removeAttr("disabled"); 
	$('#jswjwjbh').removeAttr("disabled"); 
	$('#jswjbb').removeAttr("disabled"); 
	$("input:radio[name='Ispg']").removeAttr("disabled"); 
	
	//清空指令list
	teorList = [];
}

function addUser(obj){
	var jx=$(obj).val();
	if($(obj).attr("checked")){
		$(obj).parent().next().css("visibility","visible");
		//$("#use"+StringUtil.escapeStr(formatUndefine(jx))).css("visibility","visible");
		updateTeorList(null,jx,null,null,2);
		/*$("#use"+jx).find("input").each(function() {
				$(this).attr("value", "");
			});*/
		
	}else{
		updateTeorList(null,jx,null,null,4);
		/*var techoId=$("#teco"+StringUtil.escapeStr(formatUndefine(jx))).val();
		if(techoId !="add"){
			techoIds.push(techoId);
		}*/
		$(obj).parent().next().css("visibility","hidden");
		//$("#use"+StringUtil.escapeStr(formatUndefine(jx))).css("visibility","hidden");
	}
}
function verifyDate (){
	obj = {};
	obj.blOption = false;
	obj.message = '';
	if($("#jswjbfrq").val() && $("#jswjsxrq").val() && $("#jswjbfrq").val() > $("#jswjsxrq").val()){
		obj.blOption = true;
		obj.message = '生效日期不能早于颁发日期！';
		return obj;
	}
	if($("#jswjbfrq").val() && $("#jswjdqrq").val() && $("#jswjbfrq").val() > $("#jswjdqrq").val()){
		obj.blOption = true;
		obj.message = '到期日期不能早于颁发日期！';
		return obj;
	}
	if($("#jswjsxrq").val() && $("#jswjdqrq").val() && $("#jswjsxrq").val() > $("#jswjdqrq").val()){
		obj.blOption = true;
		obj.message = '到期日期不能早于生效日期！';
		return obj;
	}
	if($("#jswjbfrq").val() && $("#jswjpgqx").val() && $("#jswjbfrq").val() > $("#jswjpgqx").val()){
		obj.blOption = true;
		obj.message = '评估期限日期不能早于颁发日期！';
		return obj;
	}
	
	
	return obj;
}

function saveUpdate(zt){
	//验证
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
		return false;
	}
	//验证日期是否合法
	var objMessage = verifyDate();
	if(objMessage.blOption){
		AlertUtil.showModalFooterMessage(objMessage.message);
		return false;
	}
	
	var obj={};
	var id=$("#airworthinessId").val();
	var jswjly=$("#jswjly").val();
	var jswjlx=$("#jswjlx").val();
	var wjbh=$("#jswjwjbh").val();
	var jswjzt=$("#zhut").val();
	var bb=$("#jswjbb").val();
	var sxrq=$("#jswjsxrq").val();
	var pgqx="";
	var ata=$("#wczjh").val();
	var bz=$("#bz").val();
	var bfrq=$("#jswjbfrq").val();
	var dqrq=$("#jswjdqrq").val();
	var xzah=$("#xzah").val();
	var glshxzlid=$("#glshxzlid").val();
	var dprtcode=$("#airworthinessDprtcode").val();
	var Ispg=$("input:radio[name='Ispg']:checked").val();
	if(Ispg==1){
		pgqx = $("#jswjpgqx").val();
		if(!pgqx){
			AlertUtil.showModalFooterMessage("选择需评估时，请填写评估期限");
			return false;
		}
		var Ispgjx= true;
		var IspgjxText='选择需评估时，请选择适用性机型！';
		var Isuser= false;
		var IsuserText='';
		$('#jxchecked input:checkbox:checked').each(function() {
			 Ispgjx= false;
			 IspgjxText='';
			var pgr=$(this).parent().next().find("input").eq(1).val();
			if(!pgr){
				Isuser = true;
				IsuserText = "选择机型后，请选择人员！";	
			}
		});
		obj.technicalfileOrderList=teorList;
		if(Ispgjx){
			AlertUtil.showModalFooterMessage(IspgjxText);
			return false;
		}
		if(Isuser){
			AlertUtil.showModalFooterMessage(IsuserText);
			return false;
		}
	}
	obj.xpgbs=Ispg;
	obj.jswjly=jswjly;
	obj.jswjlx=jswjlx;
	obj.jswjbh=wjbh;
	obj.jswjzt=jswjzt;
	obj.bb=bb;
	obj.sxrq=sxrq;
	obj.pgqx=pgqx;
	obj.ata=ata;
	obj.bz=bz;
	obj.bfrq=bfrq;
	obj.xzah=xzah;
	obj.dqrq=dqrq;
	obj.gljswjid=glshxzlid;
	var checked = $("#scfjBox").is(":checked");
	if(checked){
		if(uploadObj.wbwjm){
			obj.scfj=uploadObj;
		}else {
			AlertUtil.showModalFooterMessage("选择源文件后，请上传附件");
			return false;
		}
	}else{
		deleteUploadId = $("#scfjId").val();
	}
	if(deleteUploadId){
		obj.deleteUploadId = deleteUploadId;
	}
	
	obj.techoIds=techoIds;
	obj.dprtcode=dprtcode;
	obj.zt=zt;
	startWait();
	if(id!="" && id != null){
		var url=basePath+"/project2/airworthiness/edit";
		obj.id=id;
		submit(obj,url);
	}else{
		var url=basePath+"/project2/airworthiness/add";
		submit(obj,url);
	}
	
}

function submit(obj,url){
	//遮罩
	startWait();
	AjaxUtil.ajax({
		url:url,
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			 AlertUtil.showErrorMessage("操作成功！");
			$("#AddalertModal").modal("hide");
			finishWait();
			 id = data;
			 airworthinessMain.goPage(airworthinessMain.pagination.page, airworthinessMain.pagination.sort, airworthinessMain.pagination.order);
			 //decodePageParam();
			 //airworthinessMain.goPage(1,"auto","desc");
		}
	});
}

function encodePageParam(){
	 var pageParam={};
	 var params={};
	 params.keyword=$.trim($("#keyword_search").val());
	 params.zt=$.trim($("#zt").val());
	 params.zzjg=$.trim($("#dprtcode").val());
	 pageParam.params=params;
	 pageParam.pagination=pagination;
	 return Base64.encode(JSON.stringify(pageParam));
}
/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam(){
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#keyword_search").val(params.keyword);
		$("#zt").val(params.zt);
		$("#dprtcode").val(params.dprtcode);
		$("#tgqx").val(params.tgqx);
		if(pageParamJson.pagination){
			airworthinessMain.goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			airworthinessMain.goPage(1,"auto","desc");
		}
	}catch(e){
		airworthinessMain.goPage(1,"auto","desc");
	}
}

function scfjBox(obj){
	var checked = $(obj).is(":checked");
	if(checked){
		if(!uploadObj.wbwjm){
			$("#fileuploaderSingle").show();
		}else{
			$("#scwjWbwjm").show();
		}
	}else{
			$("#fileuploaderSingle").hide();
			$("#scwjWbwjm").hide();
	}
	
	
}


//上传
function uploader(){
	uploadObj = {};
		//上传
		var uploadObjSingle = $("#fileuploaderSingle").uploadFile({
			url:basePath+"/common/ajaxUploadFile",
			multiple:true,
			dragDrop:false,
			showStatusAfterSuccess: false,
			showDelete: false,
			formData: {
				"fileType":"order"
				,"wbwjmSingle" : function(){return $('#wbwjmSingle',$("#"+id)).val()}
				},//参考FileType枚举（技术文件类型）
			fileName: "myfile",
			returnType: "json",
			removeAfterUpload:true,
			showStatusAfterSuccess : false,
			showStatusAfterError: false,
			/*<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>*/
			uploadStr:"<i class='fa fa-upload'></i>",
			uploadButtonClass: "btn btn-default",
			onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
			{
				uploadObj.yswjm = data.attachments[0].yswjm;
				uploadObj.wbwjm = data.attachments[0].wbwjm;
				uploadObj.nbwjm = data.attachments[0].nbwjm;
				uploadObj.cflj = data.attachments[0].cflj;
				uploadObj.wjdx = data.attachments[0].wjdx;
				uploadObj.hzm = data.attachments[0].hzm;
				uploadObj.biaoshi="edit";
				//alert(JSON.stringify(data.attachments[0]));
				//$("#wbwjm").val(data.attachments[0].wbwjm);
				$("#scwjWbwjm").html("&nbsp;&nbsp;<a href='#' >"+data.attachments[0].wbwjm+"."+data.attachments[0].hzm+"</a>&nbsp;<i class='icon-remove-sign cursor-pointer color-blue cursor-pointer' style='font-size:15px' title='删除 Delete' onclick='removeFile()'></i>");
				$("#fileuploaderSingle").hide();
				$("#scwjWbwjm").show();
				$("#scfjBox").attr("disabled","disabled");
			}
			//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#wbwjmSingle').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjmSingle').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
		            	
		            	$('.ajax-file-upload-container').html("");
						uploadObjSingle.selectedFiles -= 1;
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
}
//删除附件
function removeFile(id){
	 AlertUtil.showConfirmMessage("确定要删除附件吗？", {callback: function(){
		 
		 $("#scfjBox").attr("disabled",false);
		 
		 uploadObj = {};
		 if(id){
			 deleteUploadId= id;
		 }
		 uploadObj.biaoshi="delete";
		 $("#scwjWbwjm").hide();
		 $("#fileuploaderSingle").show();
}});
}
function openList(){
	var zjh = $.trim($("#wczjh").val());
	var dprtcode = $.trim($("#dprtId").val());
	var parentWinId = "AddalertModal";
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,
		parentWinId:parentWinId,
		callback: function(data){//回调函数
			var wczjh = '';
			var wczjhName = '';
			if(data != null){
				wczjh = data.zjh;
				wczjhEName = data.ywms;
				wczjhCName = data.zwms;
			}
			$("#wczjh").val(formatUndefine(wczjh));
			$("#ataywmc").val(formatUndefine(wczjhEName));
			$("#atazwmc").val(formatUndefine(wczjhCName));
			$("input[name='zjhs']").val(wczjh).change();  
			
		}
	});
}

function openTech(){
	var dprtcode = $.trim($("#dprtId").val());
	var techId = $.trim($("#glshxzlid").val());
	var NotId = $.trim($("#airworthinessId").val());
	TechnicalfileModal.show({
		selected:techId,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,
		id:NotId,
		parentid:'AddalertModal',
		callback: function(data){//回调函数
			var airworId = '';
			var gljspgdzhut = '';
			var glshxzlBh='';
			if(data != null){
				airworId = data.id;
				gljspgdzhut = data.jswjzt;
				glshxzlBh=data.jswjbh; 
			}
			$("#gljspgdzhut").val(formatUndefine(gljspgdzhut));
			$("#glshxzlBh").val(formatUndefine(glshxzlBh));
			$("#glshxzlid").val(formatUndefine(airworId));
		}
	});
}

function syjxshowAndhide(){
	var userId=$("#userId").val();
	var Ispg=$("input:radio[name='Ispg']:checked").val();
	if(Ispg==1){
		$("#jxchecked").show();
		$("#pgqxDiv").show();
		/*$('#jxchecked input:checkbox').each(function () {
	        $(this).attr('checked',false);
		});
		$('#jxchecked select').each(function () {
			 $(this).val(userId);
	         $(this).hide();
		});*/
	}else{
		$("#jswjpgqx").val("");
		$("#pgqxDiv").hide();
		
		$("#jxchecked input[name='tecoid']").each(function () {
			if($(this).val()!="add"){
				techoIds.push($(this).val());
				$(this).val("add");
			}
		});
		$("#jxchecked").hide();
	}
	
}

function edit(id){
	 AjaxUtil.ajax({
		 url:basePath+"/project2/airworthiness/selectById",
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
	
function setObj(objData){
	$("#modalName").html("适航性资料修改");
	$("#modalEname").html("Edit");
	
	if(objData.airworthiness == null){
		AlertUtil.showErrorMessage("该数据不存在");
		return false;
	}
	var airworthiness = objData.airworthiness;
	$("#dprtId").val(airworthiness.dprtcode);
	validation();
	modalEmpty();
	
	var Userchecked = 'edit';
	//保存按钮隐藏
	if(airworthiness.zt == 0){
		$("#baocunButton").show();
	}else{
		$("#baocunButton").hide();
		Userchecked = 'disabled';
	}
	$("#jswjlx").val(formatUndefine(airworthiness.jswjlx));
	$("#jswjly").val(formatUndefine(airworthiness.jswjly));
	$("#jswjwjbh").val(formatUndefine(airworthiness.jswjbh));
	$("#airworthinessId").val(formatUndefine(airworthiness.id));
	$("#jswjbb").val(formatUndefine(airworthiness.bb));
	$("#jswjsxrq").val(airworthiness.sxrq?formatUndefine(airworthiness.sxrq.substring(0,10)):'');
	$("#jswjpgqx").val(airworthiness.pgqx?formatUndefine(airworthiness.pgqx.substring(0,10)):'');
	$("#jswjdqrq").val(airworthiness.dqrq?formatUndefine(airworthiness.dqrq.substring(0,10)):'');
	$("#jswjbfrq").val(airworthiness.bfrq?formatUndefine(airworthiness.bfrq.substring(0,10)):'');
	$("#xzah").val(formatUndefine(airworthiness.xzah));
	$("#sm").val(formatUndefine(airworthiness.scfj?airworthiness.scfj.wbwjm:''));
	$("#downFileId").val(airworthiness.scfj?airworthiness.scfj.id:'');
	$("#uploadFile").show();
	$("#zhut").val(formatUndefine(airworthiness.jswjzt));
	$("#airworthinessDprtcode").val(formatUndefine(airworthiness.dprtcode));
	if(airworthiness.zj){
		$("#ataywmc").val(formatUndefine(airworthiness.zj.ywms));
		$("#atazwmc").val(formatUndefine(airworthiness.zj.zwms));
		$("#wczjh").val(formatUndefine(airworthiness.ata));
	}
	if(airworthiness.paramsMap.gljswjbh !=null && airworthiness.paramsMap.gljswjbh !="" ){
		$("#glshxzlBh").val(formatUndefine(airworthiness.paramsMap.gljswjbh));
		$("#gljspgdzhut").val(formatUndefine(airworthiness.paramsMap.gljswjzt));
	}
	$("#glshxzlid").val(formatUndefine(airworthiness.gljswjid));
	$("#bz").val(formatUndefine(airworthiness.bz));
	
	queryFjjx(airworthiness.dprtcode);
	if(airworthiness.technicalfileOrderList != null){
		var list=airworthiness.technicalfileOrderList;
		$.each(list,function(index,row){
			$.each(teorList,function(index,teor){
				if(teor.fjjx == row.fjjx){
					updateTeorList(row.id,row.fjjx,row.pgrid,row.dprtmentId,3);
					/*var this_=$('input:checkbox[value="'+row.fjjx+'"]');*/
					var this_=$('#check'+teor.index);
					this_.attr('checked',true);
					this_.attr(Userchecked, Userchecked);
					var jxobj = this_.parent().next();
					$(jxobj).css("visibility","visible");
					$(jxobj).find("input").eq(0).val(row.pgr_user?row.pgr_user.displayName:'');
					$(jxobj).find("input").eq(1).val(row.pgr_user?row.pgr_user.id:'');
					if(Userchecked == "disabled"){
						//$(jxobj).removeClass("input-group");
						//$(jxobj).find("span").eq(0).hide();
						/*$(jxobj).find("input").eq(0).removeAttr('ondblclick'); //移除 事件
						$(jxobj).find("input").eq(0).removeClass("readonly-style");*/
					}
				}
			});
		});
	}else{
		$("#jxchecked").hide();
	}
	
		$("input:radio[name='Ispg'][value='"+airworthiness.xpgbs+"']").attr('checked',true);
	if(airworthiness.xpgbs == 1 ){
		$("#jxchecked").show();
	}else{
		$("#jxchecked").hide();
		$("#pgqxDiv").hide();
	}
	
	if(airworthiness.zt != 0){
		//$('#jswjlx').attr("disabled",true); 
		//$('#jswjly').attr("disabled",true); 
		$('#jswjwjbh').attr("disabled",true); 
		$('#jswjbb').attr("disabled",true); 
		if(airworthiness.xpgbs==1){
			$("input:radio[name='Ispg']").attr("disabled",true); 
		}
	}
	if(airworthiness.scfj){
		$("#scfjBox").attr("checked", true);
		$("#scfjBox").attr("disabled", true);
		$("#scwjWbwjm").show();
		$("#scwjWbwjm").html("&nbsp;&nbsp;<a href=javascript:downfile('"+airworthiness.scfj.id+"') >"+airworthiness.scfj.wbwjm+""+airworthiness.scfj.hzm+"</a>&nbsp;<i class='icon-remove-sign cursor-pointer color-blue cursor-pointer' style='font-size:15px' title='删除 Delete' onclick=removeFile('"+airworthiness.scfj.id+"')></i>");
		$("#scfjId").val(airworthiness.scfj.id);
		uploadObj.wbwjm=airworthiness.scfj.wbwjm;
	}
	
	
	$(".date-picker").datepicker("update");
	ModalUtil.showModal(alertModalAdd);
	
}
//附件下载
function downfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
}
function openddownfile(){
	var fileId=$("#downFileId").val();
	downloadfile(fileId);
}

//下载
function downloadfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}
//作废
function invalid(sid){
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		startWait();
			 AjaxUtil.ajax({
				 url:basePath+"/project2/airworthiness/invalid",
				 type:"post",
				 async: false,
				 data:{
					 'id' : sid
				 },
				 dataType:"json",
				 success:function(data){
					 finishWait();
					 if(data.state=="success"){ 
						 AlertUtil.showErrorMessage("删除成功！");
						 hideBottom();
						 airworthinessMain.goPage(1,"auto","desc");
					 }else{
						  AlertUtil.showErrorMessage(data.message);
					 }
				 }
			 });
			 
		 
	}});
}
//查询机型
function queryFjjx(dprtcode){
	AjaxUtil.ajax({
		 url:basePath+"/project/planemodeldata/findAllType",
		 type:"post",
		 async: false,
		 data:{
			 'dprtcode' : dprtcode
		 },
		 dataType:"json",
		 success:function(data){
			 if(data !=null && data.length>0){
				 appendFjjx(data,dprtcode);
			 }
			 
		 }
	 }); 
}
//拼接飞机机型
function appendFjjx(list,dprtcode){
	var htmlContent='';
	var userDprt=$("#dprtId").val();
	var dprtment=$("#dprtment").val();
	var userDisplayName=$("#userDisplayName").val();
	var userId=$("#userId").val();
	if(userDprt != dprtcode){
		userDisplayName='';
		userId='';
	}
	$.each(list,function(index,row){
		var technicalfileOrder={
				id: null,
				fjjx : row,
				pgrid : userId,
				dprtmentId:dprtment,
				zt: 0,
				index:index
		};
		
		//添加机型到技术指令list
		teorList.push(technicalfileOrder);
		
		htmlContent += "<div class='col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group'>";
		htmlContent += "<label class='col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0'>";
		if(index==0){
			htmlContent +="<div class='font-size-12 margin-topnew-2'>评估范围</div>";	
			htmlContent +="<div class='font-size-9'>Applicability</div>";	
		}else{
			htmlContent +="<div class='font-size-12 margin-topnew-2'>　　　　</div>";	
			htmlContent +="<div class='font-size-9'>　　　　</div>";	
		}
		htmlContent +="</label>";
		htmlContent +="<div class='col-md-9 col-sm-8 col-xs-9 padding-leftright-8'>";		
		htmlContent +="<div class='col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0'>";		
		htmlContent +="<div class='input-group' hieght='35px'>";		
		htmlContent +="<label class='input-group-addon input-group-none'  >";		
		htmlContent +="<input onchange='addUser(this)' type='checkbox' value='"+StringUtil.escapeStr(formatUndefine(row))+"' id='check"+index+"' />&nbsp;";		
		htmlContent +=StringUtil.escapeStr(formatUndefine(row))+" &nbsp;";			
		htmlContent +="</label>";			
		htmlContent +="<div class='input-group' id='use"+StringUtil.escapeStr(formatUndefine(row))+"' style='visibility:hidden;'>";			
		htmlContent +="<input type='text' class='form-control readonly-style'  readonly='readonly'  ondblclick='openUserWin(this,true)' />";			
		htmlContent +="<input type='hidden' class='form-control' />";			
		htmlContent +="<span class='input-group-btn'>";			
		htmlContent +="<button type='button' class='btn btn-default form-control' style='height:34px;' data-toggle='modal' onclick='openUserWin(this,false)'>";			
		htmlContent +="<i class='icon-search cursor-pointer'></i>";			
		htmlContent +="</button>";			
		htmlContent +="</span>";			
		htmlContent +="</div>";			
		htmlContent +="<input type='hidden' id='teco"+StringUtil.escapeStr(formatUndefine(row))+"' name='tecoid' value='add'>";			
		htmlContent +="</div></div></div></div>";
		
	});
	$("#jxchecked").empty();
	$("#jxchecked").html(htmlContent);
}

//隐藏Modal时验证销毁重构
$("#"+alertModalAdd).on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     validation();
});



function view(id){
	window.open(basePath+"/project2/airworthiness/view?id="+id);
}
//搜索条件重置
function searchreset(){
	 var zzjgid=$('#dprtId').val();
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
	 $("#dprtcode").val(zzjgid);
	// checked();
	 $("#ly").val("");
	 $("#xpgbs1").attr("checked","checked");
	 $("#xpgbs2").attr("checked","checked");
	 multiSelectReset(zzjgid);
	 //DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
	 
	 
}
//默认来源全部选中
function checked(){
	/*$('#wjlx option').each(function(){
	  this.selected=true;
	});*/
	$('#wjlx').multiselect('selectAll', false);
}

//选择人员
function openUserWin(obj,bl) {
	var userList = [];
	if(bl){
		obj = $(obj).next().next().find("button");
	}
	var userId = $(obj).parent().prev().val();
	var userDisplayName = $(obj).parent().prev().prev().val();
	if(userId){
		var user = {
				id:userId,
				displayName : userDisplayName
		}
		userList.push(user);
	}
	Personel_Tree_Multi_Modal.show({
		checkUserList:userList,//原值，在弹窗中回显
		dprtcode:$("#dprtcode").val(),//
		multi:false,
		callback: function(data){//回调函数
			var bmdm = '';
			var displayName = '';
			var mjsrid = '';
			if(data[0]){
				var jx=$(obj).parent().parent().prev().find("input").eq(0).val();
				updateTeorList(null,jx,data[0].id,data[0].bmdm,null);
				var userId = $(obj).parent().prev().val(data[0].id);
				var userDisplayName = $(obj).parent().prev().prev().val(data[0].displayName);
				var user = {
						id : userId,
						displayName : displayName
				}
			}else{
				$(obj).parent().prev().val('');
				$(obj).parent().prev().prev().val('');
			}
		}
	});
}
	/*//打开调整列表对话框
	function openUserWin(obj,bl) {
		if(bl){
			obj = $(obj).next().next().find("button");
		}
		var userId = $(obj).parent().prev().val();
		var userDisplayName = $(obj).parent().prev().prev().val();
		if(userId){
			var userList = [];
			var user = {
					id:userId,
					displayName : userDisplayName
			}
			userList.push(user);
		}
		User_Tree_Multi_Modal.show({
			clearUser:false,
			parentWinId:"AddalertModal",
			checkUserList:userList,//原值，在弹窗中回显
			dprtcode:$("#dprtId").val(),//
			callback: function(data){//回调函数
				var personName = '';
				if(data != null && data != ""){
					var jx=$(obj).parent().parent().prev().find("input").eq(0).val();
					updateTeorList(null,jx,data[0].id,data[0].bmdm,null);
					var userId = $(obj).parent().prev().val(data[0].id);
					var userDisplayName = $(obj).parent().prev().prev().val(data[0].displayName);
					var user = {
							id : userId,
							displayName : displayName
					}
				}else if(data == 'clear'){
					$(obj).parent().prev().val('');
					$(obj).parent().prev().prev().val('');
				}
			}
		})
	}*/
function updateTeorList(teorId,jx,pgrid,dprtment,zt){
	$.each(teorList,function(index,row){
		if(row.fjjx == jx){
			if(teorId != null){
				row.id=teorId;
			}
			if(pgrid != null){
				row.pgrid=pgrid;
			}
			if(zt != null){
				if(!(zt==2 && row.zt==3)){
					row.zt=zt;
				}
			}
			if(dprtment != null){
				row.dprtmentId=dprtment;
			}
		}
	});
}
/**
 *查询技术评估单
 */
function findAssessmentView(id){
	window.open(basePath+"/project2/assessment/view?id="+id);
}

/**
 *根据指令类型跳转不同的页面 
 */
function findOutputView(id,gzlx){
	if(gzlx==1){//技术通告
		window.open(basePath+"/project2/bulletin/view?id="+id);
	}else if(gzlx==2){//技术指令
		window.open(basePath+"/project2/instruction/view?id="+id);
	}else if(gzlx==3){//维修方案
		window.open(basePath+"/project2/maintenancescheme/view?id="+id);
	}else if(gzlx==4){//非例行工单
		window.open(basePath+"/produce/workorder/woView?gdid="+id);
	}else if(gzlx==6){//工程指令EO
		window.open(basePath+"/project2/eo/view?id="+id);
	}else if(gzlx==7){//MEL
		window.open(basePath+"/project2/mel/view?id="+id);
	}else if(gzlx==8){//工卡
		window.open(basePath+"/project2/workcard/view/"+id);
	}
}
/**
 * 点击表头展开表格
 */
function vieworhideOutputAll(){
	var obj = $("#bottom_hidden_content th[name='output_return']");
	var flag = obj.hasClass("downward");
	if(flag){
		obj.removeClass("downward").addClass("upward");
	}else{
		obj.removeClass("upward").addClass("downward");
	}
	 for(var i=0;i<OutputAllIds.length;i++){
		 if(flag){				
			 $("[name="+OutputAllIds[i]+"content1]").fadeIn(500);
			 $("#"+OutputAllIds[i]+"icon1").removeClass("icon-caret-down");
			 $("#"+OutputAllIds[i]+"icon1").addClass("icon-caret-up");
		 }else{
			 $("[name="+OutputAllIds[i]+"content1]").hide();
			 $("#"+OutputAllIds[i]+"icon1").removeClass("icon-caret-up");
			 $("#"+OutputAllIds[i]+"icon1").addClass("icon-caret-down");
		 }
	 }
	 new Sticky({tableId:'OrderTable'}); //初始化表头浮动
}
function vieworhideOutput(i){
	 var flag = $("[name="+OutputAllIds[i]+"content1]").is(":hidden");
	 if(flag){
		 $("[name="+OutputAllIds[i]+"content1]").fadeIn(500);
		 $("#"+OutputAllIds[i]+"icon1").removeClass("icon-caret-down");
		 $("#"+OutputAllIds[i]+"icon1").addClass("icon-caret-up");
	 }else{
		 $("[name="+OutputAllIds[i]+"content1]").hide();
		 $("#"+OutputAllIds[i]+"icon1").removeClass("icon-caret-up");
		 $("#"+OutputAllIds[i]+"icon1").addClass("icon-caret-down");
	 }
	 new Sticky({tableId:'OrderTable'}); //初始化表头浮动
}
function openTodoView(obj){
	AlertUtil.hideModalFooterMessage();
	$("#lybh").val($(obj).attr("lybh")?$(obj).attr("lybh"):"");
	$("#fjjx").val($(obj).attr("fjjx")?$(obj).attr("fjjx"):"");
	$("#dbgzlx").val(DicAndEnumUtil.getEnumName('sendOrderEnum',($(obj).attr("dbgzlx")?$(obj).attr("lybh"):"")));
	$("#blrid").val($(obj).attr("blrid")?$(obj).attr("blrid"):"");
	$("#fksj").val($(obj).attr("fksj")?$(obj).attr("fksj"):"");
	$("#fkyj").val($(obj).attr("fkyj")?$(obj).attr("fkyj"):"");
	$("#FeedBackModal").modal("show");
}
//导出
function exportExcel(){
	var searchParam = airworthinessMain.gatherSearchParam();
	if(!searchParam.paramsMap.wjlxList){
		searchParam.paramsMap.wjlxList = "";
	}
	if(!searchParam.paramsMap.xpgbsList){
		searchParam.paramsMap.xpgbsList = "";
	}
		window.open(basePath+"/project2/airworthiness/airworthiness.xls?dprtcode="
				+ encodeURIComponent(searchParam.dprtcode)+ "&jswjly="+encodeURIComponent(searchParam.jswjly)+"&keyword="
				+ encodeURIComponent(searchParam.keyword) + "&zt=" + encodeURIComponent(searchParam.zt)+"&paramsMap[sxrqBegin]="
				+ encodeURIComponent(searchParam.paramsMap.sxrqBegin) + "&paramsMap[sxrqEnd]=" + encodeURIComponent(searchParam.paramsMap.sxrqEnd)+"&paramsMap[pgqxBegin]="
				+ encodeURIComponent(searchParam.paramsMap.pgqxBegin) + "&paramsMap[pgqxEnd]=" + encodeURIComponent(searchParam.paramsMap.pgqxEnd)+"&wjlxList="
				+ encodeURIComponent(searchParam.paramsMap.wjlxList) + "&xpgbsList=" + encodeURIComponent(searchParam.paramsMap.xpgbsList));
}

$("#AddalertModal input[name='zjh']").keyup(function(){
	if($("#wczjh").val()!="" && $("#wczjh").val()!=null){
		$("#zjhs").val("");
		$('#form').data('bootstrapValidator')  
		.updateStatus('zjhs', 'NOT_VALIDATED',null)  
		.validateField('zjhs');  
	}else{
		$("#form").data('bootstrapValidator').destroy();
		$('#form').data('bootstrapValidator', null);
		 validation(); 	//初始化验证
	}
});


function initAutoComplete(){
$("#AddalertModal input[name='zjh']").typeahead({
	displayText : function(item){
		return StringUtil.escapeStr(item.zjh);
	},
  source: function (query, process) {
  	AjaxUtil.ajax({
			url: basePath+"/project/fixchapter/limitTen",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				zjh : query.toUpperCase(),
				dprtcode : $("#dprtcode").val()
			}),
			success:function(data){
				if(data.length>0){
//					$("#zjh").val(StringUtil.escapeStr(data[0].zjh));
//					$("#zjhms").val(StringUtil.escapeStr(data[0].zjh)+" "+StringUtil.escapeStr(data[0].ywms));
				}
				process(data);
		    }
		}); 
  },

  highlighter: function (item) {	
  	$("#ataywmc").val("");
  	$("#atazwmc").val("");
  	$("#zjhs").val("");
      return item;
  },

  updater: function (item) {
  	$("#ataywmc").val(StringUtil.escapeStr(item.ywms));
  	$("#atazwmc").val(StringUtil.escapeStr(item.zwms));
  	
  	$("#zjhs").val(StringUtil.escapeStr(item.zjh));
  	$("input[name='zjhs']").val(item.zjh).change();  
  	
  	  $('#form').data('bootstrapValidator')  
	      .updateStatus('zjhs', 'NOT_VALIDATED',null)  
	      .validateField('zjhs');  
      return item;
  }
});
}