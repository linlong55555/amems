var fxbs = [];
fxbs[1] = "初训";
fxbs[2] = "复训";
var khjg = [];
khjg[1] = "通过";
khjg[0] = "未通过";
var delAttachements = [];
var dprtcode = userJgdm;
$(document).ready(function(){
	Navigation(menuCode);
	 validation();
	 refreshPermission();
	 decodePageParam();
	 DicAndEnumUtil.registerDic('20','gw',$('#dprtcode').val());
	 DicAndEnumUtil.registerDic('34','pxlbs',$('#dprtcode').val());
	 $('input:radio[name="fxbs"]').change(function(){
			if($(this).val()==2){
				if(fxdd==null||fxdd==""){
					$('select#pxxs option:first').attr('selected','true');
				}else{
					$("#pxxs").val(fxdd);
				}
				if(fxksxx==null||fxksxx==""){
					 $('select#ksxs option:first').attr('selected','true');
				}else{
					$("#ksxs").val(fxksxx);
				}
				
			}else if($(this).val()==1){
				if(dd==null||dd==""){
					$('select#pxxs option:first').attr('selected','true');
				}else{
					$("#pxxs").val(dd);
				}
				 if(ksxx==null||ksxx==""){
					 $('select#ksxs option:first').attr('selected','true');
				 }else{
					 $("#ksxs").val(ksxx);
				 }
				 				
			}
		})
});



//改变组织机构时改变培训类别
function changeOrganization(){
	var dprtcode = $.trim($("#dprtcode").val());
	$("#pxlbs").empty();
	$("#pxlbs").html("<option value='' selected='true'>显示全部 All</option>");
	DicAndEnumUtil.registerDic('34','pxlbs',dprtcode);
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
        	xm: {
	                validators: {
	                	notEmpty: {
	                        message: '人员不能为空'
	                    }
	                }
	            },
        	kcbm: {
	            validators: {
	            	notEmpty: {
	                    message: '课程代码不能为空'
	                },
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
	            }
        	},
        	kcmc: {
	            validators: {
	            	notEmpty: {
	                    message: '课程名称不能为空'
	                }
	            }
        	},
        	sjKsrq: {
	            validators: {
	            	notEmpty: {
	                    message: '课程开始日期不能为空'
	                }
	            }
        	},
        	sjks: {
	            validators: {
	            	notEmpty: {
	                    message: '实际课时不能为空'
	                }
	            }
        	},
//        	kcdd: {
//	            validators: {
//	            	notEmpty: {
//	                    message: '培训地点不能为空'
//	                }
//	            }
//        	},
        	cql: {
	            validators: {
	        	  regexp: {
	              	regexp:  /^(\d{1,2}(\.\d{1,2})?|100)$/,
	                  message: '只能输入0.00-100'
	              }
	            }
        	}
        }
    });
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
		
		pageParam.params = params;
		pageParam.pagination = pagination;
		return Base64.encode(JSON.stringify(pageParam));
	}

	/**
	 * 解码页面查询条件和分页 并加载数据
	 * @returns
	 */
	function decodePageParam(){
		TableUtil.resetTableSorting("fjjyd");
		try{
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#dprtcode").val(params.dprtcode);
			
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
		var searchParam =[];
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		var dprtcode = $.trim($("#dprtcode").val());
		
		if (dprtcode != '') {searchParam.dprtcode = dprtcode;}
		obj.pxlb=$("#pxlbs").val();
		obj.keyword =  $.trim($("#keyword_search").val());
	    var pxDate_search = $.trim($("#pxDate_search").val());
	    var pxDateBegin="";
	    var pxDateEnd="";
		 if(null != pxDate_search && "" != pxDate_search){
			 pxDateBegin= pxDate_search.substring(0,4)+"-"+pxDate_search.substring(5,7)+"-"+pxDate_search.substring(8,10)+" 00:00:00";
			 pxDateEnd= pxDate_search.substring(12,17)+"-"+pxDate_search.substring(18,20)+"-"+pxDate_search.substring(21,23)+" 23:59:59";
		 }
		obj.paramsMap ={ ry:$.trim($("#ry").val()),kc:$.trim($("#kc").val()),pxDateBegin:pxDateBegin,pxDateEnd:pxDateEnd};
		
		obj.dprtcode = searchParam.dprtcode;
		
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
			   url:basePath+"/training/record/planPersonList",
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
				    	 signByKeyword($("#keyword_search").val(),[10,11,12,13,14,15,16,17,18,21,23],"#list tr td");
					   } else {
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"27\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'fjjyd'});
		      }
		    }); 
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
			   if(row.pxjhid==null){
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='training:record:main:02' id='"+formatUndefine(row.id)+"' " 
				   +  "onClick=\"editSub(event)\" title='修改 Edit'></i>" ;
				   htmlContent += "&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='training:record:main:03' onClick=\"deleterecords('"+ row.id + "','"+row.kcid+"','"+row.wxrydaid+"')\" title='作废  Invalid'></i>";
			   }else{
				   htmlContent += "&nbsp;&nbsp;<i class='icon-eye-open color-blue cursor-pointer' onClick=\"findto('" + row.pxjhid + "')\" title='查看培训计划 View Training Plan'></i>";
			   }
	   		   htmlContent += "</td>";
			   htmlContent += "<td  class='fixed-column text-center'>"+formatUndefine(index+1)+"</td>";
			   htmlContent += "<td  class='fixed-column text-left'><a href='javascript:void(0);' onclick='goToViewPage(\""+StringUtil.escapeStr(row.maintenancePersonnel?row.maintenancePersonnel.id:'')+"\")'  name='content'>"+StringUtil.escapeStr(row.maintenancePersonnel?row.maintenancePersonnel.rybh:'')+"</a></td>";  
			   htmlContent += "<td class='fixed-column text-left' title='"+StringUtil.escapeStr(row.xm)+"'>"+StringUtil.escapeStr(row.xm)+"</td>"; 
			  
			   htmlContent += "<td  class='fixed-column text-center'>";
			   if(row.paramsMap.wbbs==2){
				   htmlContent += "外部人员"; 
			   }else{
				   htmlContent +=StringUtil.escapeStr(row.gzdw); 
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.kcbm)+"' class='text-left'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"findCourse('" + (row.kcid) + "')\">"+StringUtil.escapeStr(row.kcbm)+"</a>";
			   htmlContent += "</td>";
			   
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.kcmc)+"'>"+StringUtil.escapeStr(row.kcmc)+"</td>";  
			   var sjrqq=formatUndefine(row.sjKsrq).substring(0,10)+(formatUndefine(row.sjKssj)==""?"":" "+formatUndefine(row.sjKssj).substring(0,5));
			    var sjrqh=formatUndefine(row.sjJsrq).substring(0,10)+(formatUndefine(row.sjJssj)==""?"":" "+formatUndefine(row.sjJssj).substring(0,5));
			    var sjrq=sjrqq==""?sjrqh:sjrqq+(sjrqh==""?"":"~"+sjrqh);
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+sjrq+"'>"+sjrq+"</td>";
			   htmlContent += "<td class='text-center' title='"+formatUndefine(row.xcpxrq).substring(0,10)+"'>"+formatUndefine(row.xcpxrq).substring(0,10)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.fjjx)+"'>"+StringUtil.escapeStr(row.fjjx)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zy)+"'>"+StringUtil.escapeStr(row.zy)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pxlb)+"'>"+StringUtil.escapeStr(row.pxlb)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(fxbs[row.fxbs])+"'>"+StringUtil.escapeStr(fxbs[row.fxbs])+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pxxs)+"'>"+StringUtil.escapeStr(row.pxxs)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ksxs)+"'>"+StringUtil.escapeStr(row.ksxs)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pxgh)+"'>"+StringUtil.escapeStr(row.pxgh)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.kcdd)+"'>"+StringUtil.escapeStr(row.kcdd)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.jsxm)+"'>"+StringUtil.escapeStr(row.jsxm)+"</td>";  
			   htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.cql)+"'>"+StringUtil.escapeStr(row.cql)+"</td>";  
			   htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.cj)+"'>"+StringUtil.escapeStr(row.cj)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zs)+"'>"+StringUtil.escapeStr(row.zs)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(khjg[row.khjg])+"'>"+StringUtil.escapeStr(khjg[row.khjg])+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";  
			   htmlContent += "<td class='text-center'>";
				   if(row != null&& row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
					   htmlContent += "<i class='icon-file color-blue cursor-pointer' onClick=\"openCoursewareView('"+ row.id + "')\" title='附件 Attachment'></i>";
				   }
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zdr.username)+"'>"+StringUtil.escapeStr(row.zdr.username)+"</td>";  
			   htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.whsj)+"'>"+StringUtil.escapeStr(row.whsj)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
	//打开调整列表对话框
	 function openLecturerWin() {
	 	var sqksrq=$("#sjKsrq").val();
	 	var kcid=$("#kcid").val();
	 	if(sqksrq==''||kcid==''){
	 		AlertUtil.showErrorMessage("课程代码和计划开始日期不能为空!");
	 		return false;
	 	}
	 	var param = {};
	 	param.sqksrq=sqksrq;
	 	param.kcid=kcid;
	 	param.selected=$("#jsid").val();
	 	param.callback = function(data){
	 		$("#jsxm").val(data.xm);
	 		$("#jsid").val(data.id);
	 	},
	 	lecturer_user.show(param);
	 }
	 
	//查看培训计划界面
	 function findto(id){
	 	window.open(basePath+"/training/trainingnotice/find/"+$.trim(id));
	 }
	 
	//查看课程界面
	 function findCourse(id){
	 	window.open(basePath+"/training/course/view?id="+$.trim(id));
	 }
	 
		/**
		 * 跳转到查看页面
		 * @param id
		 */
		function goToViewPage(id){
			window.open(basePath + "/quality/maintenancepersonnel/view/"+id);
		}
		
	 
	 function del_file(id,obj) {                                           //删除该行附件
			if(id!=null&&id!=""&&id!=undefined){
				fjidlist.push(id);
			}
		    $(obj).parent().parent().parent().remove();    
			delAttachements.push({
				id : key
			});
		}
	
	 function loadAttachmentEdit(id,colOptionhead,fileHead){
			AttachmengsList.show({
				attachHead : true,
				chinaHead : '附件',
				englishHead : 'Courseware Information',
				chinaFileHead : '文件说明',
				englishFileHead : 'Courseware',
				djid:id,
		 		colOptionhead : colOptionhead,
				fileHead : fileHead,
				fileType:"course"
			});//显示附件
		}
	 
 	function openCoursewareView(id){
 		open_win_attachments_list_edit.show({
 			attachHead : true,
 			chinaHead : '附件信息',
 			englishHead : 'Attachment Info',
 			chinaFileHead : '附件名',
 			englishFileHead : 'Attachment',
 			chinaColFileTitle : '附件名',
 			englishColFileTitle : 'Attachment',
 			djid:id,
 	 		colOptionhead : false,
 			fileHead : false,
 			fileType:"course",
 			callback: function(attachments){//回调函数
 			}
 		});//显示附件
 	}
	 
	//作废
	 function deleterecords(id,kcid,wxrydaid) {
			AlertUtil.showConfirmMessage("您确定要作废吗？",{callback: function(){
				var obj ={};
				obj.id=id;
				obj.kcid=kcid;
				obj.wxrydaid=wxrydaid;
	 		startWait();
	 		AjaxUtil.ajax({
	 			url:basePath + "/training/record/deleterecords",
		 		contentType:"application/json;charset=utf-8",
		 		type:"post",
		 		async: false,
		 		data:JSON.stringify(obj),
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
	 
	//数据字典
	 function initDic(){
		$(".modal-footer").find('.glyphicon-info-sign').css("display",'none');
		$(".modal-footer").find('.alert-info-message').attr('title','').empty();
	 	$("#pxlb").empty();
	 	DicAndEnumUtil.registerDic('34','pxlb',$("#dprtcode").val());
	 	$("#pxxs").empty();
	 	DicAndEnumUtil.registerDic('32','pxxs',$("#dprtcode").val());
	 	$("#ksxs").empty();
	 	DicAndEnumUtil.registerDic('33','ksxs',$("#dprtcode").val());
	 }
	 
	//新增初始化
	 function addinto(){
		 validation();
		 initDic();
	     $("#xm").val('');
	     $("#wxrydaid").val('');
	     $("#gzdw").val('');
	     $("#kcid").val('');
	     $("#kcbm").val('');
	     $("#kcmc").val('');
	     $("#jsxm").val('');
	     $("#jsid").val('');
	     $("#sjKsrq").val('');
	     $("#sjKssj").val('');
	     $("#sjJsrq").val('');
	     $("#sjJssj").val('');
	     $("#sjks").val('');
	     $("#pxgh").val('');
	     $("#kcdd").val('');
	     $("#cql").val('');
	     $("#cj").val('');
	     $("#zs").val('');
	     $("#xcpxrq").val('');
	     $("#bz").val('');
	     $("#jx").val('');
	     $("#zy").val('');
	     $('select#pxxs option:first').attr('selected','true');
	     $('select#pxlb option:first').attr('selected','true');
	     $('select#ksxs option:first').attr('selected','true');
		 $("#alertModaladdupdate input[name='fxbs']").get(0).checked=true;
		 $("#alertModaladdupdate input[name='khjg']").get(0).checked=true;
		 $("#alertModaladdupdate input").attr("disabled",false);
		 $("#alertModaladdupdate select").attr("disabled",false);
		 $("#alertModaladdupdate textarea").attr("disabled",false);
		 loadAttachmentEdit('',true,true);//传入参数是否显示
		 $("#modalName").html("新增培训记录");
		 $("#modalEname").html("Add Records");
	 	 $("#alertModaladdupdate").modal("show");
	 	checkPersonList = [];
	 }
	 
	//修改
	 function editSub(e){
		 checkPersonList = [];
		 validation();
		 initDic();
		 var id = $(e.target).attr("id");
		 initReserveDetail(id);//初始化修改数据
		 $("#modalName").html("修改培训记录");
		 loadAttachmentEdit(id,true,true);//传入参数是否显示
		 $("#modalEname").html("Update Records");
	 	 $("#alertModaladdupdate").modal("show");
	 }	
		 
	//初始化修改数据
	 function initReserveDetail(id){
	 	AjaxUtil.ajax({
	 		async: false,
	 		url:basePath+"/training/record/selectByPrimaryKey",
	 		type:"post",
	 		data:{id:id},
	 		dataType:"json",
	 		success:function(data){
	 			if(data !=null){
	 				 $("#id").val(data.id);
	 				 $("#xm").val(data.xm);
	 			     $("#wxrydaid").val(data.wxrydaid);
	 			     $("#gzdw").val(data.gzdw);
	 			     $("#kcid").val(data.kcid);
	 			     $("#kcbm").val(data.kcbm);
	 			     $("#kcmc").val(data.kcmc);
	 			     $("#jsxm").val(data.jsxm);
	 			     $("#jsid").val(data.jsid);
	 			     $("#sjKsrq").val(formatUndefine(data.sjKsrq).substring(0,10));
	 			     $("#sjKsrq").datepicker("update");
	 			     $("#sjKssj").val(data.sjKssj);
	 			     $("#sjJsrq").val(formatUndefine(data.sjJsrq).substring(0,10));
	 			     $("#sjJsrq").datepicker("update");
	 			     $("#sjJssj").val(data.sjJssj);
	 			     $("#sjks").val(data.sjks);
	 			     $("#pxgh").val(data.pxgh);
	 			     $("#kcdd").val(data.kcdd);
	 			     $("#cql").val(data.cql);
	 			     $("#cj").val(data.cj);
	 			     $("#zs").val(data.zs);
	 			     $("#pxxs").val(data.pxxs);
	 			     $("#ksxs").val(data.ksxs);
	 			     $("#xcpxrq").val(formatUndefine(data.xcpxrq).substring(0,10));
	 			     $("#xcpxrq").datepicker("update");
	 			     $("#bz").val(data.bz);
	 			     $("#kcnr").val(data.kcnr);
	 			     $("#jx").val(data.fjjx);
	 			     $("#zy").val(data.zy);
	 			     
	 			    
	 			    $("#alertModaladdupdate input").attr("disabled",false);
	 				 $("#alertModaladdupdate select").attr("disabled",false);
	 				 $("#alertModaladdupdate textarea").attr("disabled",false);
	 				 if(data.kcid!=null){
	 			    	 $("input[name='kcmc']").attr({"disabled":"disabled"});
	 			    	 $("input[name='jx']").attr({"disabled":"disabled"});
	 			     }
	 			     if(data.kcid!=null&&data.fxbs==1){
	 			    	 $("input[name='fxbs']").attr({"disabled":"disabled"});
	 			    	 $("input:radio[name='fxbs'][value='"+data.fxbs+"']").attr("checked",true); 
	 			     }else{
	 			    	 $("input[name='fxbs").removeAttr("disabled");//将按钮可用
	 			    	 $("input:radio[name='fxbs'][value='"+data.fxbs+"']").attr("checked",true); 
	 			     }
	 			     
	 				 $("input:radio[name='khjg'][value='"+data.khjg+"']").attr("checked",true); 
	 			     
	 			}
	 		}
	 	});
	 }
	 
	//查看
	 function viewDetail(e){
		 initDic();
		 var id = $(e.target).attr("id");
		 initReserveDetail(id);//初始化修改数据
	 	 $("#alertModaladdupdate").modal("show");
		 $("#accredit").html("查看培训记录");
		 $("#accreditrec").html("Select Records");
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
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		  }		
		  
		 var obj ={};
		 var id = $("#id").val();
		 var url ="";
		 if(id==""){
			 url = basePath+"/training/record/save";//新增
		 }else{
			 url = basePath+"/training/record/editrecords";//修改
			 obj.id = id;
		 }
				
	 	 var wxrydaid = $("#wxrydaid").val();
	 	 var xm = $("#xm").val();
	 	 var gzdw = $("#gzdw").val();
	 	 var cql = $("#cql").val();
	 	 var zs = $("#zs").val();
	 	 var cj = $("#cj").val();
	 	 var khjg = $("#alertModaladdupdate input[name='khjg']:checked").val();
	 	 var xcpxrq = $("#xcpxrq").val();
	 	 var kcid = $("#kcid").val();
	 	 var kcbm = $("#kcbm").val();
	 	 var kcmc = $("#kcmc").val();
	 	 var pxgh = $("#pxgh").val();
	 	 var bz = $("#bz").val();
	 	 var fxbs = $("#alertModaladdupdate input:radio[name='fxbs']:checked").val(); 
	 	 var pxxs = $("#pxxs").val();
	 	 var ksxs = $("#ksxs").val();
	 	 var pxlb = $("#pxlb").val();
	 	 var kcdd = $("#kcdd").val();
	 	 var jsxm = $("#jsxm").val();
	 	 var jsid = $("#jsid").val();
	 	 var sjKsrq = $("#sjKsrq").val();
	 	 var sjKssj = $("#sjKssj").val();
	 	 var sjJsrq = $("#sjJsrq").val();
	 	 var sjJssj = $("#sjJssj").val();
	 	 var sjks = $("#sjks").val();
	 	 var kcnr = $("#kcnr").val();
	 	 var zy = $("#zy").val();
	 	 var jx = $("#jx").val();
	 	
	 	if($("#xcpxrq").val()!=""){
	 		 var oDate1 = new Date($("#xcpxrq").val());
	 	    var oDate2 = new Date($("#sjKsrq").val());
	 		 if(oDate1.getTime() < oDate2.getTime()){
	 			AlertUtil.showMessage('下次培训日期必须大于课程开始日期!');
	 			return;
	 	    }
	 	}
	 	
	 	
		if(sjKsrq != "" && sjJsrq != null){
			var tempJhkssj = (sjKssj == ""?"00:00":sjKssj);
			var tempJhJssj = (sjJssj == ""?"00:00":sjJssj);
			if(TimeUtil.compareDate(sjKsrq +" " + tempJhkssj + ":00",sjJsrq +" " + tempJhJssj + ":00")){
				AlertUtil.showErrorMessage("实际结束时间不能小于实际开始时间!");
				return false;
			}
		}
	 	
	 	
	 	 
		 obj.wxrydaid = wxrydaid;
		 obj.xm = xm;
		 obj.gzdw = gzdw;
		 obj.cql = cql;
		 obj.zs = zs;
		 obj.cj = cj;
		 obj.khjg = khjg;
		 obj.xcpxrq = xcpxrq;
		 obj.kcid = kcid;
		 obj.kcbm = kcbm;
		 obj.kcmc = kcmc;
		 obj.pxgh = pxgh;
		 obj.bz = bz;
		 obj.fxbs = fxbs;
		 obj.pxxs = pxxs;
		 obj.ksxs = ksxs;
		 obj.pxlb = pxlb;
		 obj.kcdd = kcdd;
		 obj.jsxm = jsxm;
		 obj.jsid = jsid;
		 obj.sjKsrq =sjKsrq;
		 obj.sjKssj = sjKssj;
		 obj.sjJsrq = sjJsrq;
		 obj.sjJssj = sjJssj;
		 obj.sjks = sjks;
		 obj.kcnr = kcnr;
		 obj.zy = zy;
		 obj.fjjx = jx;
		 
		
		 obj.attachments = AttachmengsList.getAttachments();
		 
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
			TableUtil.resetTableSorting("fjjyd");
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
		 $("#ry").val("");
		 $("#kc").val("");
		 $("#dprtcode").val(zzjgid);
		 
		 decodePageParam();
		 
		 var SelectArr1 = $("#pxlbs1 select");
		 if(SelectArr1[0].options[0] != undefined){
			 
			 SelectArr1[0].options[0].selected = true;
		 }
		 changeOrganization();
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

		//隐藏Modal时验证销毁重构
		 $("#alertModaladdupdate").on("hidden.bs.modal", function() {
		 	 $("#form1").data('bootstrapValidator').destroy();
		      $('#form1').data('bootstrapValidator', null);
		      validation();
		 });
		 
		//打开课程列表对话框
		 var fxdd="";//复训培训形式
		 var fxksxx="";//复训考试形式
		 var dd="";//初训培训形式
		 var ksxx="";//初训考试形式
		 function openCourseWin() {
		 	open_win_course_modal.show({
		 		selected : $("#kcid").val(),// 原值，在弹窗中默认勾选
		 		dprtcode:$("#dprtcode").val(),//机构代码
		 		callback : function(data) {// 回调函数
		 			if (data != null && data.id != null) {
		 				course = data;
		 				 $("#kcid").val(formatUndefine(data.id));
		 				$("#kcbm").val(formatUndefine(data.kcbh));
		 				$("#kcmc").val(formatUndefine(data.kcmc));
		 				$("#kcnr").val(formatUndefine(data.kcnr));
		 				$("#jx").val(formatUndefine(data.fjjx));
		 				$('#form1').data('bootstrapValidator')  
			 		      .updateStatus('kcbm', 'NOT_VALIDATED',null)  
			 		      .validateField('kcbm');  
		 				$('#form1').data('bootstrapValidator')  
		 				.updateStatus('kcmc', 'NOT_VALIDATED',null)  
		 				.validateField('kcmc');  
		 				if(data.isFx==1){
		 					 $("input[name='fxbs']").get(0).checked=true;
		 					 $("input[name='fxbs").removeAttr("disabled");//将按钮可用
		 				}else{
		 					 $("input[name='fxbs']").get(0).checked=true;
		 					 $("input[name='fxbs']").attr({"disabled":"disabled"});
		 				}
		 				
		 				 fxdd=data.fxpxxs;
		 				 fxksxx=data.fxksxs;
		 				 dd=data.pxxs;
		 				 ksxx=data.ksxs;
		 				 checkFxbs();
		 			}
		 		}
		 	})
		 }
		 
		//选中复训
		 function checkFxbs(){
		 	if(course.id != null){
		 		selectkcid(course.id);
		 	}
		 }
		 
		/**
		 * 课程代码改变事件
		 * @returns
		 */
		function onKcbmChanged(){
			$("#kcid").val("");
			$('input:radio[name="fxbs"]').prop("disabled",false);//将按钮可用
			$("input[name='kcmc").removeAttr("disabled");//将课程名称可用
			$("input[name='jx").removeAttr("disabled");//将课程名称可用
			fxdd="";
			fxksxx="";
			dd="";
			ksxx="";
			$("#jsid").val('');
			$("#jsxm").val('');
			var obj = {};
			obj.kcbh = $("#kcbm").val();
			obj.dprtcode = $("#dprtcode").val();
			console.info(obj);
			//根据单据id查询信息
			startWait($("#alertModaladdupdate"));
		   	AjaxUtil.ajax({
		 		url:basePath + "/training/course/selectByKcbm",
		 		contentType:"application/json;charset=utf-8",
		 		type:"post",
		 		async: false,
		 		data:JSON.stringify(obj),
		 		dataType:"json",
		 		modal:$("#alertModaladdupdate"),
		 		success:function(data){
		 			console.info(data);
		 			course = data;
		 			finishWait($("#alertModaladdupdate"));
		 			if(data!=null){
		 				 $("#kcid").val(formatUndefine(data.id));
			 				$("#kcbm").val(formatUndefine(data.kcbh));
			 				$("#kcmc").val(formatUndefine(data.kcmc));
			 				$("#kcnr").val(formatUndefine(data.kcnr));
			 				$("#jx").val(formatUndefine(data.fjjx));
			 				$('#form1').data('bootstrapValidator')  
				 		      .updateStatus('kcbm', 'NOT_VALIDATED',null)  
				 		      .validateField('kcbm');  
			 				$('#form1').data('bootstrapValidator')  
			 				.updateStatus('kcmc', 'NOT_VALIDATED',null)  
			 				.validateField('kcmc');  
			 				
			 				if(data.isFx==1){
			 					 $("input[name='fxbs']").get(0).checked=true;
			 					 $("input[name='fxbs").removeAttr("disabled");//将按钮可用
			 				}else{
			 					 $("input[name='fxbs']").get(0).checked=true;
			 					 $("input[name='fxbs']").attr({"disabled":"disabled"});
			 				}
			 				
			 				 fxdd=data.fxpxxs;
			 				 fxksxx=data.fxksxs;
			 				 dd=data.pxxs;
			 				 ksxx=data.ksxs;
			 				checkFxbs();
					};
		 		}
			});
		}
	 
		//改变课程开始日期
		 function changedate(){
			 if($("#kcid").val()!=null&&$("#kcid").val()!=""&&$("#sjKsrq").val()!=""&&$("#sjKsrq").val()!=null){
				 selectkcid($("#kcid").val());
			 }
			 $("#jsid").val('');
			 $("#jsxm").val('');
		 }
		 
		 /**
		  * 讲师改变事件
		  * @returns
		  */
		 function onLecturerChanged(){
		 	$("#jsid").val("");
		 }
		//根据课程id查询课程信息得到复训周期值+周期单位
		 function selectkcid(id){
		 	AjaxUtil.ajax({
		 		async: false,
		 		url:basePath+"/training/course/selectById",
		 		type:"post",
		 		data:{id:id},
		 		dataType:"json",
		 		success:function(data){
		 			if(data !=null||data.zqdw!=null){
		 				var ss=$("#sjKsrq").val();
		 				if(ss!=""&&ss!=null){
		 					var num="";
			 				if(data.zqdw==1){//天
			 					 num=dateAdd(ss,"d",data.zqz);
			 				}else if(data.zqdw==2){//月
			 					var num1=new Date(ss); 
			 					num=dateAdd(num1,"m",data.zqz);
			 				}else if(data.zqdw==3){//年
			 					num=new Date(ss); 
			 					num.setFullYear(num.getFullYear()+data.zqz); 
			 				} 
			 				if(num!=""){
			 					var rDate = new Date(num);  
			 				    var year = rDate.getFullYear();  
			 				    var month = rDate.getMonth() + 1;  
			 				    if (month < 10) month = "0" + month;  
			 				    var date = rDate.getDate();
			 				    if (date < 10) date = "0" + date;  
			 					$("#xcpxrq").val(year + "-" + month + "-" + date);
			 					$("#xcpxrq").datepicker("update");
			 				}
		 				}
		 				 $("input[name='kcmc']").attr({"disabled":"disabled"});
		 				 $("input[name='jx']").attr({"disabled":"disabled"});
		 			
		 			}
		 		}
		 	});
		 }
		 
		 function addMoth(d,m){
		    var ds=d.split('-'),_d=ds[2]-0;
		    var nextM=new Date( ds[0],ds[1]-1+m+1, 0 );
		    var max=nextM.getDate();
		    d=new Date( ds[0],ds[1]-1+m,_d>max? max:_d );
		    return new Date(d.toLocaleDateString().match(/\d+/g).join('-'));
		 }


		 function dateAdd(date,strInterval, Number) {  //参数分别为日期对象，增加的类型，增加的数量 
		                     var dtTmp = date;  
		                     switch (strInterval) {   
		 					    case 'second':
		                         case 's' :
		 						    return new Date(Date.parse(dtTmp) + (1000 * Number));  
		 						case 'minute':
		                         case 'n' :
		 						    return new Date(Date.parse(dtTmp) + (60000 * Number));  
		 						case 'hour':
		                         case 'h' :
		 						    return new Date(Date.parse(dtTmp) + (3600000 * Number)); 
		                         case 'day':							
		                         case 'd' :
		 						    return new Date(Date.parse(dtTmp) + (86400000 * Number)); 
		                         case 'week':							
		                         case 'w' :
		 						    return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
		 						case 'month':
		                         case 'm' :
		 						    return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
		                         case 'year':
		 						case 'y' :
		 						    return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
		                     }  
		                 }

		 
		 var checkPersonList = [];//储存选中的人员
		//打开调整列表对话框
		 function openPersonelWin() {
		 	var this_ = this;
		 	Personel_Tree_Multi_Modal.show({
		 		checkUserList:checkPersonList,//原值，在弹窗中回显
		 		dprtcode:$("#dprtcode").val(),//
		 		multi:false, //是否多选 默认单选
		 		callback: function(data){//回调函数
		 			checkPersonList = [];
		 			var personName = '';
		 			var gzdw='';
		 			var wxrydaid='';
		 			if(data != null && data != ""){
		 				checkPersonList = data;
		 				$.each(data, function(i, row){
		 					personName += formatUndefine(row.xm) + ",";
		 					gzdw+=formatUndefine(row.szdw)+",";
		 					wxrydaid+=formatUndefine(row.id)+",";
		 				});
		 			}
		 			if(personName != ''){
		 				personName = personName.substring(0,personName.length - 1);
		 			}
		 			if(gzdw != ''){
		 				gzdw = gzdw.substring(0,gzdw.length - 1);
		 			}
		 			if(wxrydaid != ''){
		 				wxrydaid = wxrydaid.substring(0,wxrydaid.length - 1);
		 			}
		 			
		 			$("#wxrydaid").val(wxrydaid);
		 			$("#xm").val(personName);
		 			$("#gzdw").val(gzdw);
		 			
		 			$('#form1').data('bootstrapValidator')  
	 				.updateStatus('xm', 'NOT_VALIDATED',null)  
	 				.validateField('xm');  
		 			$('#form1').data('bootstrapValidator')  
		 			.updateStatus('gzdw', 'NOT_VALIDATED',null)  
		 			.validateField('gzdw');  
		 		}
		 	})
		 }
		 

		 
		 $("#sjKsrq").datepicker({
				format:'yyyy-mm-dd',
				autoclose : true,
				clearBtn : true
		 });
		 $("#sjKssj").datetimepicker({
				lang:"ch", 
				format:'H:i'	,
				step:10,			 
				datepicker:false
		 });
		 $("#sjJsrq").datepicker({
				format:'yyyy-mm-dd',
				autoclose : true,
				clearBtn : true
		 });
		 $("#sjJssj").datetimepicker({
				lang:"ch", 
				format:'H:i'	,
				step:10,			 
				datepicker:false
		 });
		 
		 $("#xcpxrq").datepicker({
				format:'yyyy-mm-dd',
				autoclose : true,
				clearBtn : true
		 });
		 
		$('#xcpxrq').datepicker({
			 autoclose: true,
			 clearBtn:true
		}).on('hide', function(e) {
			  $('#form1').data('bootstrapValidator')  
		 .updateStatus('xcpxrq', 'NOT_VALIDATED',null)  
		 .validateField('xcpxrq');  
		});
		
		$('#sjKsrq').datepicker({
			 autoclose: true,
			 clearBtn:true
		}).on('hide', function(e) {
			  $('#form1').data('bootstrapValidator')  
		 .updateStatus('sjKsrq', 'NOT_VALIDATED',null)  
		 .validateField('sjKsrq');  
		});
		
		//只能输入数字
//		function clearNoNumber(obj){
//		     //先把非数字的都替换掉，除了数字
//		     obj.value = obj.value.replace(/[^\d]/g,"");
//		     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
//		  		obj.value = 0;
//		  	 }
//		}

		//只能输入数字和小数,保留两位
		function clearNoNumTwo(obj){
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
		    	 if(str[1].length > 2){
		    		 obj.value = str[0] +"." + str[1].substring(0,2);
		    	 }
		     }
		     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
		  		 if(obj.value.substring(1,2) != "."){
		  			obj.value = 0;
		  		 }
		  	 }
		}
		$("#alertModaladdupdate").on("hidden.bs.modal", function() {
			fxdd="";
			fxksxx="";
			dd="";
			ksxx="";
		});
		
		function exportExcel(){
			var dprtcode = $("#dprtcode").val();
			var keyword = $.trim($("#keyword_search").val());
			var pxlb=$("#pxlbs").val();
		    var pxDate_search = $.trim($("#pxDate_search").val());
		    var ry = $.trim($("#ry").val());
		    var kc = $.trim($("#kc").val());	   
			window.open(basePath+"/training/record/personnelTrainingRecords.xls?dprtcode="+ dprtcode + "&keyword="+ encodeURIComponent(keyword)
					+"&ry=" + encodeURIComponent(ry) +"&kc=" + encodeURIComponent(kc) +"&pxDate="+pxDate_search);
		}