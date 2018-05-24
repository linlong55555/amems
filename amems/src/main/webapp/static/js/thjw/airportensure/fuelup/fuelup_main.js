var zy=["","内部人员","外部人员"];
var xxlx=["","授权","执照"];
var dxsz=["零","壹","贰","叁","肆","伍","陆","柒","捌","玖","拾"];
$(document).ready(function(){
	Navigation(menuCode);
	
	 validation();
	 refreshPrmission();
	 changeOrganization();e
	 decodePageParam();
	//初始化日志功能
	logModal.init({code:'B_J_003'});
	

});


function add(){
	var htmlContent = "";
	htmlContent += "<tr   style=\"cursor:pointer\"  >";
	htmlContent += "<td style='vertical-align: middle; ' align='left'><select class='form-control' id='ypgg' onblur='ypggChange()'></select></td>";  
	htmlContent += "<td style='vertical-align: middle; ' align='right' id='sjjysl1'><input class='form-control text-right' id='sjmd'  maxlength='10'  type='text' onblur='ypggChange()' disabled='disabled' onkeyup='clearNoNum(this)' /></td>";
	htmlContent += "<td style='vertical-align: middle; ' align='right' id='sjjysl1'><input class='form-control text-right' id='sjjysl' onblur='ypggChange()' onkeyup='clearNoNum(this)'  maxlength='10'  /></td>";
	htmlContent += "<td id='dasz' style='vertical-align: middle;' align='left'></td>";
	htmlContent += "<td id='kg' style='vertical-align: middle;' align='left'></td>";
	htmlContent += "</tr>";  
	$("#list1").empty();
	$("#list1").append(htmlContent);
}

//验证
function validation(){
	validatorForm =  $('#form1').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	fjzch: {
	                validators: {
	                	notEmpty: {
	                        message: '飞机注册号不能为空'
	                    }
	                }
	            },
	        	jyrq: {
	                validators: {
	                	notEmpty: {
	                        message: '加油日期不能为空'
	                    }
	                }
	            },
            hbh: {
                validators: {
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不包含中文且长度不超过50个字符'
                    }
                }
            },
            hyddh: {
	            	validators: {
	            		regexp: {
	            			regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
	            			message: '不包含中文且长度不超过50个字符'
	            		}
	            	}
	            },   fjssdw: {
	            	validators: {
	            		  stringLength: {
		                        max: 15,
		                        message: '最大长度不能超过15个字符'
		                    }
	            	}
	            },
            jycch: {
	            	validators: {
	            		regexp: {
	            			regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
	            			message: '不包含中文且长度不超过50个字符'
	            		}
	            	}
	            },
            jydjbh: {
	            	validators: {
	            		regexp: {
	            			regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
	            			message: '不包含中文且长度不超过50个字符'
	            		}
	            	}
	            }
	            ,fyr: {
	            	validators: {
		            		notEmpty: {
		                        message: '加油人不能为空'
		                    },
	            		  stringLength: {
		                        max: 10,
		                        message: '最大长度不能超过10个字符'
		                    }
	            	}
	            },jykssj: {
	            	validators: {
	            		  stringLength: {
		                        max: 20,
		                        message: '最大长度不能超过20个字符'
		                    }
	            	}
	            },jyjssj: {
	            	validators: {
	            		  stringLength: {
		                        max:20,
		                        message: '最大长度不能超过20个字符'
		                    }
	            	}
	            },syr: {
	            	validators: {
	            		  stringLength: {
		                        max: 10,
		                        message: '最大长度不能超过10个字符'
		                    }
	            	}
	            }
        	}
    });
}

	/**
	 * 更改选中的飞机
	 */
	function changeSelectedPlane(){
		 decodePageParam();
	}


	$('#jyrq').datepicker({
		 autoclose: true,
		 clearBtn:true
	}).on('hide', function(e) {
		  $('#form1').data('bootstrapValidator')  
	 .updateStatus('jyrq', 'NOT_VALIDATED',null)  
	 .validateField('jyrq');  
	});

	//改变组织机构时改变飞机注册号
	function changeOrganization(){
		var dprtcode = $.trim($("#dprtcode").val());
		var planeRegOption ='';
		var planeData = acAndTypeUtil.getACRegList({DPRTCODE:StringUtil.escapeStr(dprtcode),FJJX:null});
		if(planeData != null && planeData.length >0){
			$.each(planeData,function(i,planeData){
				if(dprtcode == planeData.DPRTCODE){
					planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
				}
			});
		}
		if(planeRegOption == ''){
			planeRegOption = '<option value="">暂无飞机 No plane</option>';
		}
		$("#fjzch").html(planeRegOption);
		$("#fjzch_f").html(planeRegOption);
	}

	var pagination = {};
	
	/**
	 * 编码页面查询条件和分页
	 * @returns
	 */
	function encodePageParam(){
		var pageParam = {};
		var params = {};
		params.dprtcode = $.trim($("#dprtcode").val());
		params.fjzch = $.trim($("#fjzch_f").val());
		params.keyword = $.trim($("#keyword_search").val());
		params.flightdate = $("#jyrqs").val();
		
		pageParam.params = params;
		pageParam.pagination = pagination;
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
			$("#dprtcode").val(params.dprtcode);
			$("#fjzch_f").val(params.fjzch);
			$("#keyword_search").val(params.keyword);
		    $("#jyrqs").val(params.flightdate);
			
			if(pageParamJson.pagination){
				goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
			}else{
				goPage(1,"auto","desc");
			}
		}catch(e){
			goPage(1,"auto","desc");
		}
	}
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.keyword =  $.trim($("#keyword_search").val());
		obj.fjzch = searchParam.fjzch;
		obj.jyDateBegin = searchParam.jyDateBegin;
		obj.jyDateEnd = searchParam.jyDateEnd;
		obj.dprtcode = searchParam.dprtcode;
		
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
			   url:basePath+"/airportensure/fuelup/fuelupList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				    finishWait();
				    if(data.rows !=""){
				    	appendContentHtml(data.rows);
				    	 new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(a,b,c){
								AjaxGetDatasWithSearch(a,b,c);
							}
						});  
						// 标记关键字
				       signByKeyword($("#keyword_search").val(),[3,9]);
					   } else {
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"22\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'fjjyd'});
		      }
		    }); 
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.dprtcode = $("#dprtcode").val();
		 searchParam.fjzch = $("#fjzch_f").val();
		 var flightdate = $("#jyrqs").val();
		 if(null != flightdate && "" != flightdate){
			 searchParam.jyDateBegin = flightdate.substring(0,4)+"-"+flightdate.substring(5,7)+"-"+flightdate.substring(8,10);
			 searchParam.jyDateEnd = flightdate.substring(12,17)+"-"+flightdate.substring(18,20)+"-"+flightdate.substring(21,23);
		 }
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent += "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
			   }
			   htmlContent += "<td  class='fixed-column text-center'>";
			   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='airportensure:fuelup:main:02' " 
				   		   +  "fjssdw='"+StringUtil.escapeStr(row.fjssdw)+"' jd='"+StringUtil.escapeStr(row.jd)+"' " 
				   		   +  "fjjx='"+StringUtil.escapeStr(row.fjjx)+"' fjzch='"+StringUtil.escapeStr(row.fjzch)+"' " 
				   		   +  "hbh='"+StringUtil.escapeStr(row.hbh)+"' hyddh='"+StringUtil.escapeStr(row.hyddh)+"' " 
				   		   +  "jyrq='"+formatUndefine(row.jyrq)+"' jycch='"+StringUtil.escapeStr(row.jycch)+"' " 
				   		   +  "jydjbh='"+StringUtil.escapeStr(row.jydjbh)+"' fyr='"+StringUtil.escapeStr(row.fyr)+"' " 
				   		   +  "jykssj='"+StringUtil.escapeStr(row.jykssj)+"' jyjssj='"+StringUtil.escapeStr(row.jyjssj)+"' " 
				   		   +  "syr='"+StringUtil.escapeStr(row.syr)+"' ypgg='"+StringUtil.escapeStr(row.ypgg)+"' " 
				   		   +  "sjmd='"+formatUndefine(row.sjmd)+"' sjjysl='"+formatUndefine(row.sjjysl)+"' " 
				   		   +  "bz='"+StringUtil.escapeStr(row.bz)+"' id='"+formatUndefine(row.id)+"' " 
				   		   +  "onClick=\"editSub(event)\" title='修改 Edit'></i>" ;
	   		   htmlContent += "&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='airportensure:fuelup:main:03' onClick=\"cancel('"+ row.id + "')\" title='作废  Invalid'></i>";
			   htmlContent += "</td>";
			   htmlContent += "<td  class='text-center'>"+formatUndefine(index+1)+"</td>";
			   htmlContent += "<td  class='text-left'><a href='javascript:void(0);' " 
						   +  "fjssdw='"+StringUtil.escapeStr(row.fjssdw)+"' jd='"+StringUtil.escapeStr(row.jd)+"' " 
						   +  "fjjx='"+StringUtil.escapeStr(row.fjjx)+"' fjzch='"+StringUtil.escapeStr(row.fjzch)+"' " 
			   			   +  "hbh='"+StringUtil.escapeStr(row.hbh)+"' hyddh='"+StringUtil.escapeStr(row.hyddh)+"' " 
				   		   +  "jyrq='"+formatUndefine(row.jyrq)+"' jycch='"+StringUtil.escapeStr(row.jycch)+"' " 
				   		   +  "jydjbh='"+StringUtil.escapeStr(row.jydjbh)+"' fyr='"+StringUtil.escapeStr(row.fyr)+"' " 
				   		   +  "jykssj='"+StringUtil.escapeStr(row.jykssj)+"' jyjssj='"+StringUtil.escapeStr(row.jyjssj)+"' " 
				   		   +  "syr='"+StringUtil.escapeStr(row.syr)+"' ypgg='"+StringUtil.escapeStr(row.ypgg)+"' " 
				   		   +  "sjmd='"+formatUndefine(row.sjmd)+"' sjjysl='"+formatUndefine(row.sjjysl)+"' " 
				   		   +  "bz='"+StringUtil.escapeStr(row.bz)+"' id='"+formatUndefine(row.id)+"' " 
				   		   +  "onclick=\"viewDetail(event)\">"+StringUtil.escapeStr(row.fjjydbh)+"</a></td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.fjssdw)+"</td>";  
			   htmlContent += "<td class='text-left '>"+StringUtil.escapeStr(row.jdms)+"</td>"; 
			   htmlContent += "<td class='text-left '>"+StringUtil.escapeStr(row.fjjx)+"</td>"; 
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.hbh)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.fjzch)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.hyddh)+"</td>";  
			   htmlContent += "<td class='text-center'>"+formatUndefine(row.jyrq).substring(0,10)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ypgg)+"'>"+StringUtil.escapeStr(row.ypgg)+"</td>";  
			   htmlContent += "<td class='text-right'>"+StringUtil.escapeStr(row.sjmd)+"</td>";  
			   htmlContent += "<td class='text-right'>"+StringUtil.escapeStr(row.sjjysl)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jycch)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jydjbh)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.fyr)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jykssj)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jyjssj)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.syr)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.whrid)+"'>"+StringUtil.escapeStr(row.whrid)+"</td>";  
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	
	//新增初始化
	 function addinto(){
		 add();
		 validation();
		 $("#ypgg").empty();
	     $("#alertModaladdupdate").find("input").each(function() {
			$(this).attr("value", "");
		 });
		 $("#alertModaladdupdate").find("textarea").each(function(){
			 $(this).val("");
		 });
		 //DicAndEnumUtil.registerDic('22','ypgg',$('#dprtcode').val());
		 
		 $("#fjzch").show();
		 $("#fjzchyc").hide();
		 $("#fuelupId").val("");
		 $("#kg").html("");
		 $("#dasz").html("");
		 $("#sjmd").val("");
		 $("#alertModaladdupdate input").attr("disabled",false);
		 $("#alertModaladdupdate select").attr("disabled",false);
		 $("#alertModaladdupdate textarea").attr("disabled",false);
		 $("#accredit").html("新增飞机加油单");
		 
		 $("#biaoshi").val(1);
		 $("#accreditrec").html("Add Fuel Up Record");
		 
		 //获取组织机构下的所有有效状态的油品规格
		 queryAllYpgg($("#zzjgid").val());
		 $("#baocuns").show();
	 	 $("#alertModaladdupdate").modal("show");
	 	 var SelectArr1 = $("#fjzchs select");
		 SelectArr1[0].options[0].selected = true;
	 }
		 
		 
	 $("#sjjysl1 input").keyup(function(){
		 $("#kg").html(($("#sjmd").val()*$("#sjjysl").val()).toFixed(3));
		 $("#dasz").html(NoToChinese($("#sjjysl").val()));
	});
	 
	//作废
	 function cancel(id) {
			AlertUtil.showConfirmMessage("您确定要作废吗？",{callback: function(){
	 		startWait();
	 		AjaxUtil.ajax({
	 			url:basePath + "/airportensure/fuelup/cancelFuelup",
	 			type:"post",
	 			async: false,
	 			data:{
	 				id : id
	 			},
	 			dataType:"json",
	 			success:function(data){
	 				finishWait();
	 				AlertUtil.showMessage('作废成功!');
	 				goPage(1,"auto","desc");//开始的加载默认的内容 
	 				refreshPermission();
	 			}
	 		});
		}});
	 }
		 
	//修改
	 function editSub(e){
		 add();
		 validation();
		 $("#biaoshi").val(1);
		 $("#ypgg").empty();
		 queryAllYpgg($("#zzjgid").val());
		 var id = $(e.target).attr("id");
		 var fjjydbh = $(e.target).attr("fjjydbh");
		 var fjssdw = $(e.target).attr("fjssdw");
		 var jd = $(e.target).attr("jd");
		 var fjzch = $(e.target).attr("fjzch");
		 var hbh = $(e.target).attr("hbh");
		 var hyddh = $(e.target).attr("hyddh");
		 var jyrq = $(e.target).attr("jyrq");
		 var jycch = $(e.target).attr("jycch");
		 var jydjbh = $(e.target).attr("jydjbh");
		 var fyr = $(e.target).attr("fyr");
		 var jykssj = $(e.target).attr("jykssj");
		 var jyjssj = $(e.target).attr("jyjssj");
		 var syr = $(e.target).attr("syr");
		 var ypgg = $(e.target).attr("ypgg");
		 var sjmd = $(e.target).attr("sjmd");
		 var sjjysl = $(e.target).attr("sjjysl");
		 var bz = $(e.target).attr("bz");
		 
		 $("#fuelupId").val(id);
		 $("#fjssdw").val(fjssdw);
		 $("#fjjydbh").val(fjjydbh);
		 $("#jd").val(jd);
		 $("#fjzch").show();
		 $("#fjzchyc").hide();
		 $("#fjzch").val(fjzch);
		 $("#hbh").val(hbh);
		 $("#hyddh").val(hyddh);
		 $("#jyrq").val(jyrq.substring(0,10));
		 $("#jyrq").datepicker("update");
		 $("#jycch").val(jycch);
		 $("#jydjbh").val(jydjbh);
		 $("#fyr").val(fyr);
		 $("#jykssj").val(jykssj);
		 $("#jyjssj").val(jyjssj);
		 $("#syr").val(syr);
		 $("#ypgg").val(ypgg);
		 $("#sjmd").val(sjmd);
		 $("#sjjysl").val(sjjysl);
		 $("#bz").text(bz);
		 
		 $("#kg").html((sjmd*sjjysl).toFixed(3));
		 $("#dasz").html(NoToChinese(sjjysl));
		 $("#accredit").html("修改飞机加油单");
		 $("#accreditrec").html("Update Fuel Up Record");
		 $("#baocuns").show();
		 $("#alertModaladdupdate input").attr("disabled",false);
		 $("#alertModaladdupdate select").attr("disabled",false);
		 $("#alertModaladdupdate textarea").attr("disabled",false);
	 	 $("#alertModaladdupdate").modal("show");
	 }	
		 
	//查看
	 function viewDetail(e){
		 add();
		 $("#biaoshi").val(2);
			$('#alertModaladdupdate').on('shown.bs.modal', function (e) {
				// 替换输入框、下拉框为文本
				
				if($("#biaoshi").val()==2){
					StringUtil.replaceAsText("list1");
				}
				
			});
		 $("#ypgg").empty();
		 queryAllYpgg($("#zzjgid").val());
		 var id = $(e.target).attr("id");
		 var fjjydbh = $(e.target).attr("fjjydbh");
		 var fjssdw = $(e.target).attr("fjssdw");
		 var jd = $(e.target).attr("jd");
		 var fjzch = $(e.target).attr("fjzch");
		 var hbh = $(e.target).attr("hbh");
		 var hyddh = $(e.target).attr("hyddh");
		 var jyrq = $(e.target).attr("jyrq");
		 var jycch = $(e.target).attr("jycch");
		 var jydjbh = $(e.target).attr("jydjbh");
		 var fyr = $(e.target).attr("fyr");
		 var jykssj = $(e.target).attr("jykssj");
		 var jyjssj = $(e.target).attr("jyjssj");
		 var syr = $(e.target).attr("syr");
		 var ypgg = $(e.target).attr("ypgg");
		 var sjmd = $(e.target).attr("sjmd");
		 var sjjysl = $(e.target).attr("sjjysl");
		 var bz = $(e.target).attr("bz");
		 
		 $("#fuelupId").val(id);
		 $("#fjssdw").val(fjssdw);
		 $("#fjjydbh").val(fjjydbh);
		 $("#jd").val(jd);
		 $("#fjzch").hide();
		 $("#fjzchyc").show();
		 $("#fjzchyc").val(fjzch);
		 $("#hbh").val(hbh);
		 $("#hyddh").val(hyddh);
		 $("#jyrq").val(jyrq.substring(0,10));
		 $("#jycch").val(jycch);
		 $("#jydjbh").val(jydjbh);
		 $("#fyr").val(fyr);
		 $("#jykssj").val(jykssj);
		 $("#jyjssj").val(jyjssj);
		 $("#syr").val(syr);
		 $("#ypgg").val(ypgg);
		 $("#sjmd").val(sjmd);
		 $("#sjjysl").val(sjjysl);
		 $("#bz").text(bz);
		 
		 $("#kg").html((sjmd*sjjysl).toFixed(3));
		 $("#dasz").html(NoToChinese(sjjysl));
		 $("#accredit").html("查看飞机加油单");
		 $("#accreditrec").html("Select Fuel Up Record");
		 $("#baocuns").hide();
		 $("#alertModaladdupdate input").attr("disabled",true);
		 $("#alertModaladdupdate select").attr("disabled",true);
		 $("#alertModaladdupdate textarea").attr("disabled",true);
	 	 $("#alertModaladdupdate").modal("show");
	 	
	 }	
		 
	 //保存工作记录
	 function saveUpdate(){
		 $('#form1').data('bootstrapValidator').validate();
		  if(!$('#form1').data('bootstrapValidator').isValid()){
			return false;
		  }			 
		  
		 if( $("#sjjysl").val()==""|| $("#sjjysl").val()==null){
			AlertUtil.showErrorMessage("实际加油数量不能为空");
			return false;
		 }
			
		 if( $("#sjmd").val()==""|| $("#sjmd").val()==null){
			AlertUtil.showErrorMessage("实际密度不能为空");
			return false;
		 }
		  
		 var obj ={};
		 var fuelupId = $("#fuelupId").val();
		 var url ="";
		 if(fuelupId==""){
			 url = basePath+"/airportensure/fuelup/saveFulup";//新增
		 }else{
			 url = basePath+"/airportensure/fuelup/editFulup";//修改
			 obj.id = fuelupId;
		 }
				
	 	 var fjssdw = $("#fjssdw").val();
	 	 var jd = $("#jd").val();
	 	 var fjzch = $("#fjzch").val();
	 	 var hbh = $("#hbh").val();
	 	 var hyddh = $("#hyddh").val();
	 	 var jyrq = $("#jyrq").val();
	 	 var jycch = $("#jycch").val();
	 	 var jydjbh = $("#jydjbh").val();
	 	 var jykssj = $("#jykssj").val();
	 	 var jyjssj = $("#jyjssj").val();
	 	 var syr = $("#syr").val();
	 	 var fyr = $("#fyr").val();
	 	 var ypgg = $("#ypgg").val();
	 	 var sjmd = $("#sjmd").val();
	 	 var sjjysl = $("#sjjysl").val();
	 	 var bz = $("#bz").val();
	 	
		 obj.fjssdw = fjssdw;
		 obj.jd = jd;
		 obj.fjzch = fjzch;
		 obj.hbh = hbh;
		 obj.hyddh = hyddh;
		 obj.jyrq = jyrq;
		 obj.jycch = jycch;
		 obj.jydjbh = jydjbh;
		 obj.jykssj = jykssj;
		 obj.jyjssj = jyjssj;
		 obj.syr = syr;
		 obj.fyr = fyr;
		 obj.ypgg = ypgg;
		 obj.sjmd = sjmd;
		 obj.sjjysl = sjjysl;
		 obj.bz = bz;
					
	 	 startWait($("#alertModaladdupdate"));
	   	 AjaxUtil.ajax({
	 		url:url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#alertModaladdupdate"),
	 		success:function(data){
	 			finishWait($("#alertModaladdupdate"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#alertModaladdupdate").modal("hide");
	 			decodePageParam();
	 			refreshPermission();
	 		}
	   	   });
	 	 }
	 
		 //小写数字转大写
		 function NoToChinese(num) { 
			 if (!/^\d*(\.\d*)?$/.test(num)) { alert("Number is wrong!"); return "Number is wrong!"; } 
			 var AA = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); 
			 var BB = new Array("", "拾", "佰", "仟", "萬", "億", "点", ""); 
			 var a = ("" + num).replace(/(^0*)/g, "").split("."), k = 0, re = ""; 
			 for (var i = a[0].length - 1; i >= 0; i--) { 
			 switch (k) { 
			 case 0: re = BB[7] + re; break; 
			 case 4: if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$").test(a[0])) 
			 re = BB[4] + re; break; 
			 case 8: re = BB[5] + re; BB[7] = BB[5]; k = 0; break; 
			 } 
			 if (k % 4 == 2 && a[0].charAt(i + 2) != 0 && a[0].charAt(i + 1) == 0) re = AA[0] + re; 
			 if (a[0].charAt(i) != 0) re = AA[a[0].charAt(i)] + BB[k % 4] + re; k++; 
			 } 

			 if (a.length > 1) //加上小数部分(如果有小数部分) 
			 { 
			 re += BB[6]; 
			 for (var i = 0; i < a[1].length; i++) re += AA[a[1].charAt(i)]; 
			 } 
			 return re; 
		 } 
		 
		//字段排序
		function orderBy(obj, _obj) {
			$obj = $("#fjjyd th[name='column_"+obj+"']");
			var orderStyle = $obj.attr("class");
			$("#fjjyd .sorting_desc").removeClass("sorting_desc").addClass("sorting");
			$("#fjjyd .sorting_asc").removeClass("sorting_asc").addClass("sorting");
			
			var orderType = "asc";
			if (orderStyle.indexOf ( "sorting_asc")>=0) {
				$obj.removeClass("sorting").addClass("sorting_desc");
				orderType = "desc";
			} else {
				$obj.removeClass("sorting").addClass("sorting_asc");
				orderType = "asc";
			}
			var currentPage = $("#pagination li[class='active']").text();
			goPage(currentPage,obj, orderType);
		}
			
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
		function goPage(pageNumber,sortType,sequence){
			AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		}	
			
		//信息检索
		function searchRevision(){
			goPage(1,"auto","desc");
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
		 changeOrganization();
		 decodePageParam();
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

		//只能输入数字和小数
		 function clearNoNum(obj){
		    	//先把非数字的都替换掉，除了数字
		        obj.value = obj.value.replace(/[^\d.:]/g,"");
		    	if(obj.value.indexOf(".") != -1){
		    		if(obj.value.indexOf(":") != -1){
		    			obj.value = obj.value.substring(0,obj.value.length -1);
		    		}else{
		    			clearNoNumTwoDate(obj);
		    		}
		    	}
		    	
		    	if(obj.value.indexOf(":") != -1){
		    		if(obj.value.indexOf(".") != -1){
		    			obj.value = obj.value.substring(0,obj.value.length -1);
		    		}else{
		    			clearNoNumTwoColon(obj);
		    		}
		    	}
		    	
		    	if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
		      		 if(obj.value.substring(1,2) != "." && obj.value.substring(1,2) != ":"){
		      			obj.value = 0;
		      		 }
		      	 }
		 }
				 
		//只能输入数字和小数,保留两位,小数后不能超过59
		 function clearNoNumTwoDate(obj){
		 	 //先把非数字的都替换掉，除了数字和.
		     obj.value = obj.value.replace(/[^\d.]/g,"");
		     //必须保证第一个为数字而不是.
		     obj.value = obj.value.replace(/^\./g,"");
		     //保证只有出现一个.而没有多个.
		     obj.value = obj.value.replace(/\.{2,}/g,".");
		     //保证.只出现一次，而不能出现两次以上
		     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		     
		     var str = obj.value.split(".");
		     if(str.length > 1){
		    	 var value = obj.value;
		    	 if(str[1].length == 2){
		    		 if(str[1] > 99){
		        		 value = str[0] +".99";
		        	 }
		    	 }
		    	 if(str[1].length > 2){
		    		 value = str[0] +"." + str[1].substring(0,2);
		    	 }
		    	 obj.value = value;
		     }
		 }
				 
		//只能输入数字和冒号,保留两位,冒号后不能超过59
		 function clearNoNumTwoColon(obj){
		 	 //先把非数字的都替换掉，除了数字和.
		     obj.value = obj.value.replace(/[^\d:]/g,"");
		     //必须保证第一个为数字而不是:
		     obj.value = obj.value.replace(/^\:/g,"");
		     //保证只有出现一个.而没有多个.
		     obj.value = obj.value.replace(/\:{2,}/g,":");
		     //保证.只出现一次，而不能出现两次以上
		     obj.value = obj.value.replace(":","$#$").replace(/\:/g,"").replace("$#$",":");
		     
		     var str = obj.value.split(":");
		     if(str.length > 1){
		    	 var value = obj.value;
		    	 if(str[1].length == 2){
		    		 if(str[1] > 59){
		        		 value = str[0] +":59";
		        	 }
		    	 }
		    	 if(str[1].length > 2){
		    		 value = str[0] +":" + str[1].substring(0,2);
		    	 }
		    	 obj.value = value;
		     }
		 }
				 
		//隐藏Modal时验证销毁重构
		 $("#alertModaladdupdate").on("hidden.bs.modal", function() {
		 	 $("#form1").data('bootstrapValidator').destroy();
		      $('#form1').data('bootstrapValidator', null);
		      validation();
		 });
		 //获取组织机构下的所有油品规格
		 function queryAllYpgg(dprtcode){
			 AjaxUtil.ajax({
				   url: basePath+"/basedata/oil/queryByDprtcode",
				   type:"post",
					async: false,
					data:{
						'dprtcode':dprtcode
					},
					dataType:"json",
				   success:function(data){
					  appendpygg(data.oilList);
			    }
			  });
			 
		 }
		 //拼接油品规格
		 function appendpygg(list){
			 var htmlContent = '';
			   $.each(list,function(index,row){
				   htmlContent =htmlContent + "<option ypmd='"+row.ypmd+"' value='"+row.ypgg+"' >"+row.ypgg+"</option>"
			   });
			   $("#ypgg").html(htmlContent);
			   var ypmd=$("#ypgg").find("option:selected").attr("ypmd");
			   $("#sjmd").val(ypmd);
			   
		 }
		 //油品规格的改变事件
		 function ypggChange(){
			 var ypmd=$("#ypgg").find("option:selected").attr("ypmd");
			 $("#sjmd").val(ypmd);
			 
			 $("#kg").html(($("#sjmd").val()*$("#sjjysl").val()).toFixed(3));
			 $("#dasz").html(NoToChinese($("#sjjysl").val()));
		 }
		 