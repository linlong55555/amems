var orderNumber = 1;
var delAttachements = [];
var dprtcode = userJgdm;
var checkPersonList1 = [];//储存选中的人员
var checkPersonList2 =[];//储存选中的人员
var state = [];
state[1] = "下发";
state[10] = "完成";
	
$(document).ready(function(){
		   Navigation(menuCode);
		 	refreshPermission();
			decodePageParam();
//			logModal.init({
//				code : 'B_S_019'
//			});
			$('#hbrq').datepicker({
				autoclose : true,
				clearBtn : true
			});
			validation();
			loadAttachmentEdit('',true,true);//传入参数是否显示
		 
		 	 DicAndEnumUtil.registerDic('34','selectpxlb',$("#dprtcode").val());
			    // 初始化导入
			    importModal.init({
				    importUrl:"/training/course/excelImports",
				    downloadUrl:"/common/templetDownfile/23",
					callback: function(data){
					  console.log(data);
						 complete(data);
					  $("#ImportModal").modal("hide");
					}
				});
	 });


function complete(data){
	var datas=data.DATA;
	if(datas){
		for(var i=0;i<datas.length;i++){
			var nums=$("#messageListTable").find("tr").size();
			var ryid=datas[i].ryid;//人员档案id
			var rybh=datas[i].rybh; //人员编号
			var ryxm=datas[i].ryxm //人员姓名
			var xcpxrq=datas[i].xcpxrq; //下次培训日期
			var szdw=datas[i].szdw; //所在单位
			var bz=datas[i].bz;//备注
			var cql=datas[i].cql //出勤率
			var cj=datas[i].cj//成绩
			var khjg=datas[i].khjg//考核结果
			var zs=datas[i].zs//证书
			var $obj=$("#messageListTable").find("input[value='"+ryid+"'][name='wxrydaid']");
			if($obj.size()==1){//更新实参,出勤率,成绩,考核结果,证书,下次培训日期,备注修改
				var $obj2=$obj.parent().parent();
				$obj2.find(":checkbox").attr("checked","checked");
				$obj2.find("input[name='cql']").val(cql);
				$obj2.find("input[name='cql']").parent().attr("title",cql);
				$obj2.find("input[name='cj']").val(cj);
				$obj2.find("input[name='cj']").parent().attr("title",cj);
				$obj2.find(":radio[value='"+khjg+"']").attr("checked","checked");
				$obj2.find("input[name='zs']").val(zs);
				$obj2.find("input[name='zs']").parent().attr("title",zs);
				$obj2.find("input[name='xcpxrq']").val((xcpxrq||"").substring(0,10));
				$obj2.find("input[name='xcpxrq']").parent().attr("title",xcpxrq);
				$obj2.find("input[name='bz']").val(bz);
				$obj2.find("input[name='bz']").parent().attr("title",bz);
			}else{
				var tr="";
				tr += "<tr>";
		 		tr += "<td style='text-align:center;vertical-align:middle;'>";
		 		tr += "<input type='hidden' name='jhid' />";
		 		tr += "<input type='hidden' name='xm' value='"+StringUtil.escapeStr(ryxm)+"'/>";
		 		tr += "<input type='hidden' name='gzdw' value='"+StringUtil.escapeStr(szdw)+"'/>";
		 		tr += "<input type='hidden' name='isYc' />";
		 		tr += "<input type='hidden' name='wxrydaid' value='"+formatUndefine(ryid)+"'/>";
		 		tr += "<button class='line6' onclick=\"removeCol(this,'"+formatUndefine(ryid)+"','')\">";
		 	    tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
		 	    tr += "</button>";
		 		tr += "</td>";
		 		tr += "<td style='text-align:center;vertical-align:middle;'>";
		 		tr += "<span name='orderNumber'>"+(nums+1)+"</span>";
		 		tr += "</td>";
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(rybh)+"'><a href='javascript:void(0);' onclick='goToViewPage(\""+ryid+"\")'  name='content'>"+StringUtil.escapeStr(rybh)+"</a></td>";
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(ryxm)+"'>"+StringUtil.escapeStr(ryxm)+"</td>";
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(szdw)+"'>"+StringUtil.escapeStr(szdw)+"</td>";
		 		tr +=  "<td style='text-align:center;vertical-align:middle;' title='否'>否</td>";
		 		tr +=  "<td style='text-align:center;vertical-align:middle;' ><input name='isSc"+(nums+1)+"' onchange='onchliksc()' type='checkbox' checked='checked'  style='width: 20px;height: 20px;'/></td>";
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(cql)+"'><input type='text' class='form-control' name='cql'  value='"+StringUtil.escapeStr(cql)+"' maxlength='12'  /></td>";
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(cj)+"'><input type='text' class='form-control' name='cj'  value='"+StringUtil.escapeStr(cj)+"' maxlength='12' /></td>";		 		
		 		var checked="";
		 		var checked1="";
		 		if(khjg==1||khjg==null){
		 			checked="checked='checked'";
		 			checked1="";
		 		}else if(khjg==0){
		 			checked="";
		 			checked1="checked='checked'";
		 		}
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' ><label style='margin-right: 0px;font-weight: normal' ><input name='khjg"+(nums+1)+"' type='radio' value='1' "+checked+" />通过 </label><label style='font-weight: normal' ><input name='khjg"+(nums+1)+"' type='radio' value='0' "+checked1+"/>未通过</label> </td>";	 		
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(zs)+"'><input type='text' class='form-control' name='zs'  value='"+StringUtil.escapeStr(zs)+"' maxlength='100'/></td>";
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(xcpxrq)+"'><input type='text' class='xiacipx form-control datetimepicker'  value='"+formatUndefine(xcpxrq).substring(0,10)+"' name='xcpxrq' maxlength='100' /></td>";
		 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(bz)+"'><input class='form-control'  name='bz' maxlength='300' value='"+StringUtil.escapeStr(bz)+"'></td>";
		 		tr += "<td style='vertical-align:middle' name='pxfj' colspan='2' width='40%'><table style='width:170px;'>"; 
		 		tr += "</table></td>";  
			    tr += "<td style='text-align:left;vertical-align:middle;'><div class='uploaderDiv'   style='margin-left: 5px ;padding-left: 0;width:80px;'></div></td>";  
				tr += "</tr>";
				$("#messageListTable").append(tr);
				  upLoadFile();
				  $("#messageListTable").find(".xiacipx").datepicker({
						 autoclose: true,
						 format:'yyyy-mm-dd',
						 clearBtn:true
					})
			}
			
		}		
	}	
}
	
//改变组织机构时改变培训类别
function changeOrganization(){
	$("#selectpxlb").empty();
	$("#selectpxlb").html("<option value='' selected='true'>显示全部 All</option>");
	DicAndEnumUtil.registerDic('34','selectpxlb',$("#dprtcode").val());
}


//验证
function validation(){
	validatorForm = $('#form1').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
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
        	}
//        	kcdd: {
//	            validators: {
//	            	notEmpty: {
//	                    message: '培训地点不能为空'
//	                }
//	            }
//        	}
        }
    });
}

	var pagination = {};
	
	function encodePageParam() {
		
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#keyword").val());
		params.dprtcode = $.trim($("#dprtcode").val());
		pageParam.params = params;
		pageParam.pagination = pagination;
		return Base64.encode(JSON.stringify(pageParam));
	}	
 	function decodePageParam() {
 		 TableUtil.resetTableSorting("fjgz_record_sheet_table");
		try {
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#keyword").val(params.keyword);
			$("#dprtcode").val(params.dprtcode);
			if (pageParamJson.pagination) {
				goPage(pageParamJson.pagination.page,
						pageParamJson.pagination.order,
						pageParamJson.pagination.sort);
			} else {
				goPage(1, "desc", "auto");
			}
		} catch (e) {
			goPage(1, "desc", "auto");
		}
	}				
 	function goPage(pageNumber, sortType, sortField) {
		AjaxGetDatasWithSearch(pageNumber, sortType, sortField);
	}
 
 	function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
 		if(sequence=="jh_ksrq"){
 			sequence="jh_ksrq "+sortType+",jh_kssj";
 		}
		var searchParam = {};
		searchParam.keyword = $.trim($("#keyword").val());
		var dprtcode = $.trim($("#dprtcode").val());
		if (dprtcode != '') {
			searchParam.dprtcode = dprtcode;
		}

		searchParam.pxlb=$("#selectpxlb").val();
		searchParam.jsxm=$("#jsxms").val();
		searchParam.zt=$("#zt").val();
		searchParam.jhlx=$("#jhlx").val();
		 var fxbsList = [];
		 $('input:checkbox[name=fxbs_search]:checked').each(function(){
			 fxbsList.push($(this).val());
		 });
		 if(fxbsList.length == 0){
			 fxbsList.push(3);
		 }
		 var pxDate_search = $.trim($("#pxDate_search").val());
	     var jhrqBegin="";
	     var jhrqEnd="";
		 if(null != pxDate_search && "" != pxDate_search){
			 jhrqBegin= pxDate_search.substring(0,4)+"-"+pxDate_search.substring(5,7)+"-"+pxDate_search.substring(8,10)+" 00:00:00";
			 jhrqEnd= pxDate_search.substring(12,17)+"-"+pxDate_search.substring(18,20)+"-"+pxDate_search.substring(21,23)+" 23:59:59";
		 }
		 searchParam.paramsMap ={fxbsList:fxbsList,jhrqBegin:jhrqBegin,jhrqEnd:jhrqEnd};
		pagination = {
			page : pageNumber,
			sort : sequence,
			order : sortType,
			rows : 20
		};
		searchParam.pagination = pagination;
		if (id != "") {
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			url : basePath + "/training/course/recordsList",
			type : "post",
			data : JSON.stringify(searchParam),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				finishWait();
				if (data.total > 0) {
					appendContentHtml(data.rows);
					new Pagination({
						renderTo : "fjgzjk_pagination",
						data : data,
						sortColumn : sortType,
						orderType : sequence,
						goPage : function(a, b, c) {
							AjaxGetDatasWithSearch(a, b, c);
						}
					});
					// 替换null或undefined
					FormatUtil.removeNullOrUndefined();
					// 标记关键字
					signByKeyword($.trim($("#keyword").val()),[4,5,8,23],"#fjgzjk_list tr td")
				} else {
					$("#fjgzjk_list").empty();
					$("#fjgzjk_pagination").empty();
					$("#fjgzjk_list").append("<tr ><td  class='text-center'  colspan=\"26\">暂无数据 No data.</td></tr>");
				}
				new Sticky({
					tableId : 'fjgz_record_sheet_table'
				});
			}
		});

	}
 
 	function appendContentHtml(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			  if (index % 2 == 0) {
				  htmlContent +="<tr id='"+row.id+"'  scrs='"+row.scrs+"' ycrs='"+row.ycrs+"' zt='"+row.zt+"' kcbh='"+StringUtil.escapeStr(row.course.kcbh)+"' onclick='showDcgzcl(this)' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent +="<tr id='"+row.id+"'  scrs='"+row.scrs+"' ycrs='"+row.ycrs+"' zt='"+row.zt+"' kcbh='"+StringUtil.escapeStr(row.course.kcbh)+"' onclick='showDcgzcl(this)' style=\"cursor:pointer\" bgcolor=\"#fefefe\">";;
			   }
		
			htmlContent += "<td style=\"vertical-align: middle;\" class='fixed-column text-center '>" +
					"<i class='icon-pencil color-blue cursor-pointer checkPermission' permissioncode='training:course:records:01' id='"+StringUtil.escapeStr(row.id)+"' title='录入成绩 Record Results' onclick='updatefind(event)' ></i> " +
					"&nbsp;&nbsp;<i class='icon-eye-open color-blue cursor-pointer' onClick=\"findto('" + row.id + "')\" title='查看培训计划 View Training Plan'></i>"+
					"</td>";
			  	htmlContent += "<td title='"+DicAndEnumUtil.getEnumName('trainingPlanTypeEnum',row.jhlx)+"' class='text-center'>";
			    htmlContent += DicAndEnumUtil.getEnumName('trainingPlanTypeEnum',row.jhlx);
			    htmlContent += "</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(state[row.zt])+"'>"+StringUtil.escapeStr(state[row.zt])+"</td>";
			    
			    htmlContent += "<td title='"+StringUtil.escapeStr(row.course.kcbh)+"' class='text-left'>";
				   htmlContent += "<a href='javascript:void(0);' onclick=\"findCourse('" + (row.course.id) + "')\">"+StringUtil.escapeStr(row.course.kcbh)+"</a>";
				   htmlContent += "</td>";
			    
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.course.kcmc)+"'>"+StringUtil.escapeStr(row.course.kcmc)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.course.fjjx)+"'>"+StringUtil.escapeStr(row.course.fjjx)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.pxjg)+"'>"+StringUtil.escapeStr(row.pxjg)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.kcdd)+"'>"+StringUtil.escapeStr(row.kcdd)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+StringUtil.escapeStr(row.fxbs == 1?"初训":"复训")+"'>"+StringUtil.escapeStr(row.fxbs == 1?"初训":"复训")+"</td>";
			    var pxrqq=formatUndefine(row.jhKsrq).substring(0,10)+(formatUndefine(row.jhKssj)==""?"":" "+formatUndefine(row.jhKssj).substring(0,5));
			    var pxrqh=formatUndefine(row.jhJsrq).substring(0,10)+(formatUndefine(row.jhJssj)==""?"":" "+formatUndefine(row.jhJssj).substring(0,5));
			    var pxrq=pxrqq==""?pxrqh:pxrqq+(pxrqh==""?"":"~"+pxrqh);
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+pxrq+"'>"+pxrq+"</td>";
			    var sjrqq=formatUndefine(row.sjKsrq).substring(0,10)+(formatUndefine(row.sjKssj)==""?"":" "+formatUndefine(row.sjKssj).substring(0,5));
			    var sjrqh=formatUndefine(row.sjJsrq).substring(0,10)+(formatUndefine(row.sjJssj)==""?"":" "+formatUndefine(row.sjJssj).substring(0,5));
			    var sjrq=sjrqq==""?sjrqh:sjrqq+(sjrqh==""?"":"~"+sjrqh);
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+sjrq+"'>"+sjrq+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.jsxm)+"'>"+StringUtil.escapeStr(row.jsxm)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.pxlb)+"'>"+StringUtil.escapeStr(row.pxlb)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.pxxs)+"'>"+StringUtil.escapeStr(row.pxxs)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+StringUtil.escapeStr(row.isJcff== 1?"是":"否")+"'>"+StringUtil.escapeStr(row.isJcff== 1?"是":"否")+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.ksxs)+"'>"+StringUtil.escapeStr(row.ksxs)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.kcnr)+"'>"+StringUtil.escapeStr(row.kcnr)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-right' title='"+StringUtil.escapeStr(row.ks)+"'>"+StringUtil.escapeStr(row.ks)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-right' title='"+StringUtil.escapeStr(row.xt)+"'>"+StringUtil.escapeStr(row.xt)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-right' title='"+StringUtil.escapeStr(row.sjks)+"'>"+StringUtil.escapeStr(row.sjks)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-right' title='"+StringUtil.escapeStr(row.ycrs)+"'>"+StringUtil.escapeStr(row.ycrs)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-right' title='"+StringUtil.escapeStr(row.scrs)+"'>"+StringUtil.escapeStr(row.scrs)+"</td>";
			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zdr.displayName)+"'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";  
			    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.whsj)+"'>"+StringUtil.escapeStr(row.whsj)+"</td>";  
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
				htmlContent += "</tr>";
			 
		});
		$("#fjgzjk_list").empty();
		$("#fjgzjk_list").html(htmlContent);
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
 	
 	/**
 	 * 讲师改变事件
 	 * @returns
 	 */
 	function onLecturerChanged(){
 		$("#jsid").val("");
 	}
 	
 	//查看课程界面
	 function findCourse(id){
	 	window.open(basePath+"/training/course/view?id="+$.trim(id));
	 }
 	
 	//查看培训计划界面
	 function findto(id){
	 	window.open(basePath+"/training/trainingnotice/find/"+$.trim(id));
	 }
	 
 	//修改
	 function updatefind(e){
		 
		 var id = $(e.target).attr("id");
		 initDic();
		 initReserveDetail(id);//初始化修改数据
		 $("#accredit").html("录入成绩");
		 $("#accreditrec").html("Update Business");
	 	 $("#alertModaladdupdate").modal("show");
	 }	
		//数据字典
	 function initDic(){
	 	$("#pxxs").empty();
	 	DicAndEnumUtil.registerDic('32','pxxs',$("#dprtcode").val());
	 	$("#ksxs").empty();
	 	DicAndEnumUtil.registerDic('33','ksxs',$("#dprtcode").val());
	 }
 	
		//初始化修改数据
	 function initReserveDetail(id){
	 	AjaxUtil.ajax({
	 		async: false,
	 		url:basePath+"/training/course/selectByPrimaryKey",
	 		type:"post",
	 		data:{id:id},
	 		dataType:"json",
	 		success:function(data){
	 			if(data !=null){
	 				 $("#kcid").val(data.course.id);
	 				 $("#kcnewid").val(data.course.id);
	 				 $("#kcbm").val(StringUtil.escapeStr(data.course.kcbm));
	 				 $("#id").val(data.id);
	 				 $("#dgbh").html(StringUtil.escapeStr(data.course.kcbh));
	 			     $("#kcmc").val(StringUtil.escapeStr(data.course.kcmc));
	 			     $("#fjjx_mod").val(StringUtil.escapeStr(data.course.fjjx));
	 			     $("#sjKsrq").val(formatUndefine(data.sjKsrq).substring(0,10));
	 			     $("#sjKsrq").datepicker("update");
	 			     $("#sjKssj").val(data.sjKssj);
	 			     $("#sjJsrq").val(formatUndefine(data.sjJsrq).substring(0,10));
	 			     $("#sjJsrq").datepicker("update");
	 			     $("#sjJssj").val(StringUtil.escapeStr(data.sjJssj));
	 			     $("#jsid").val(StringUtil.escapeStr(data.jsid));
	 			     $("#jsxm").val(StringUtil.escapeStr(data.jsxm));
	 			     $("#kcdd").val(StringUtil.escapeStr(data.kcdd));
	 			
	 				 $("#fxbs").val(StringUtil.escapeStr(data.fxbs));
	 				 $("#pxlb").val(StringUtil.escapeStr(data.pxlb));
	 				 $("#fjjx").val(StringUtil.escapeStr(data.fjjx));
	 				 $("#zy").val(StringUtil.escapeStr(data.zy));
	 				 $("#pxjg").val(StringUtil.escapeStr(data.pxjg));
	 				 $("#kcnr").val(StringUtil.escapeStr(data.kcnr));
	 				 
	 			     initTableRow(data.planPersonList);//人员
	 			     
		 			 $("input:radio[name='jsBz'][value='"+data.jsBz+"']").attr("checked",true); 
		 			 $("input:radio[name='isJcff'][value='"+data.isJcff+"']").attr("checked",true); 
		 		     $("#sjks").val(data.sjks);
		 		     
	 				 $("#pxxs",$("#alertModaladdupdate")).val(data.pxxs);
	 				 $("#ksxs",$("#alertModaladdupdate")).val(data.ksxs);
		 		    loadAttachmentEdit(id,true,true);//传入参数是否显示
	 			}
	 		}
	 	});
	 }
	 
	//查看课程界面
	 function findCourses(){
	 	var id =$("#kcid").val();
	 	window.open(basePath+"/training/course/view?id="+$.trim(id));
	 }
	 
	 
	//初始化人员
	 function initTableRow(data){
		 checkPersonList1.splice(0,checkPersonList1.length);
		 checkPersonList2.splice(0,checkPersonList2.length);
		 $("#messageListTable").empty();
		 if(data.length>0){
			//先移除暂无数据一行
			 	var len = $("#messageListTable").length;
			 	if (len == 1) {
			 		if($("#messageListTable").find("td").length == 1){
			 			$("#messageListTable").empty();
			 		}
			 	}
			 	orderNumber=1;
			 	$.each(data,function(index,reserve){
			 		var row = reserve;
			 		row.orderNumber = orderNumber++;
			 		addRow(row,index);
			 	});
		 }else{
			 $("#messageListTable").append("<tr><td  colspan='16' class='text-center'>暂无数据 No data.</td></tr>");
		 }
	 }
	 var personName = '';
	 
	 var fjidlist=[];  //储存删除的课程的附加ids
	//向table新增一行
	 function addRow(obj,index){
		
		 var planPerson = {
					id : obj.wxrydaid,
					xm : obj.xm
			};
			if(obj.maintenancePersonnel != null){
				var displayName = StringUtil.escapeStr(obj.maintenancePersonnel.rybh) + " " + StringUtil.escapeStr(obj.xm);
				personName += displayName + ",";
				planPerson.displayName = displayName;
				checkPersonList1.push(planPerson);
				checkPersonList2.push(planPerson);
			}
		
	 	var tr = "";
	 		tr += "<tr>";
	 		tr += "<td style='text-align:center;vertical-align:middle;'>" +
	 				"<input type='hidden' name='jhid' value='"+formatUndefine(obj.id)+"'/>"+
	 				"<input type='hidden' name='xm' value='"+StringUtil.escapeStr(obj.xm)+"'/>"+
	 				"<input type='hidden' name='gzdw' value='"+StringUtil.escapeStr(obj.gzdw)+"'/>"+
	 				"<input type='hidden' name='isYc' value='"+StringUtil.escapeStr(obj.isYc)+"'/>"+
	 				"<input type='hidden' name='wxrydaid' value='"+formatUndefine(obj.wxrydaid)+"'/>";
	 		
	 		tr += "<button class='line6' onclick=\"removeCol(this,'"+formatUndefine(obj.wxrydaid)+"','"+obj.isYc+"')\">";
	 	    tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
	 	    tr += "</button>";
	 		tr += "</td>";
	 		tr += "<td style='text-align:center;vertical-align:middle;'>";
	 		tr += "<span name='orderNumber'>"+obj.orderNumber+"</span>";
	 		tr += "</td>";
	 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.maintenancePersonnel.rybh)+"'><a href='javascript:void(0);' onclick='goToViewPage(\""+obj.maintenancePersonnel.id+"\")'  name='content'>"+StringUtil.escapeStr(obj.maintenancePersonnel.rybh)+"</a></td>";
	 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.xm)+"'>"+StringUtil.escapeStr(obj.xm)+"</td>";
	 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.gzdw)+"'>"+StringUtil.escapeStr(obj.gzdw)+"</td>";
	 		tr +=  "<td style='text-align:center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.isYc == 1?"是":"否")+"'>"+StringUtil.escapeStr(obj.isYc == 1?"是":"否")+"</td>";
	 		var isyc = "";
	 		var rr=obj.isSc;
	 		if(obj.isYc == 0){
	 			isyc = "disabled='disabled'";
	 			rr=1;
	 		}
	 		var isscchen="";
	 		var isycs="";
	 		if(rr==1){
	 			isscchen="checked='checked'";
	 		}else if(obj.isSc==0){
	 			isscchen="";
	 			isycs = "disabled='disabled'";	 			
	 		}
	 		tr +=  "<td style='text-align:center;vertical-align:middle;' ><input name='isSc"+obj.orderNumber+"' onchange='onchliksc()' type='checkbox'  style='width: 20px;height: 20px;' "+isscchen+" "+isyc+"/></td>";
	 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.cql)+"'><input type='text' class='form-control' name='cql'  value='"+StringUtil.escapeStr(obj.cql)+"' maxlength='12' "+isycs+" /></td>";
	 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.cj)+"'><input type='text' class='form-control' name='cj'  value='"+StringUtil.escapeStr(obj.cj)+"' maxlength='12'  "+isycs+"/></td>";
	 		var checked="";
	 		var checked1="";
	 		if(obj.khjg==1||obj.khjg==null){
	 			checked="checked='checked'";
	 			checked1="";
	 		}else if(obj.khjg==0){
	 			checked="";
	 			checked1="checked='checked'";
	 		}
	 		tr +=  "<td style='text-align:left;vertical-align:middle;' ><label style='margin-right: 0px;font-weight: normal' ><input name='khjg"+obj.orderNumber+"' type='radio' value='1' "+checked+" "+isycs+" />通过 </label><label style='font-weight: normal' ><input name='khjg"+obj.orderNumber+"' type='radio' value='0' "+checked1+" "+isycs+"/>未通过</label> </td>";
	 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zs)+"'><input type='text' class='form-control' name='zs'  value='"+StringUtil.escapeStr(obj.zs)+"' maxlength='100'  "+isycs+"/></td>";
	 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(obj.xcpxrq).substring(0,10)+"'><input type='text' class='xiacipx form-control datetimepicker'  value='"+formatUndefine(obj.xcpxrq).substring(0,10)+"' name='xcpxrq' maxlength='100' "+isycs+" /></td>";
	 		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bz)+"'><input class='form-control'  name='bz' maxlength='300' value='"+StringUtil.escapeStr(obj.bz)+"'	 "+isycs+" ></td>";
	 		tr += "<td style='vertical-align:middle' name='pxfj' colspan='2' width='40%'><table style='width:170px;'>"; 
	 		if(obj.attachments!=null){
	 			$.each(obj.attachments, function(i, row){
	 				console.info(row);
	 				tr +='<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+formatUndefine(row.id)+'\'>';
	 				tr +='<td><div>';
	 				tr +="<i class='icon-trash color-blue cursor-pointer' onclick=\"del_file('"+formatUndefine(row.id)+"',this)\" title='删除附件'> ";
	 				tr +='</div></td>';
	 				tr += "<td class=\"text-left\" title='"+StringUtil.escapeStr(row.wbwjm)+"."+StringUtil.escapeStr(row.hzm)+"'><a href=\"javascript:downloadfile('"+formatUndefine(row.id)+"')\"><input type=\"hidden\" name=\"operate\" value='EDIT'><input type=\"hidden\" name=\"fjid\" value='"+formatUndefine(row.id)+"'><input type=\"hidden\" name=\"yswjm\" value='"+StringUtil.escapeStr(row.yswjm)+"'><input type=\"hidden\" name=\"wbwjm\" value='"+StringUtil.escapeStr(row.wbwjm)+"'><input type=\"hidden\" name=\"nbwjm\" value='"+StringUtil.escapeStr(row.nbwjm)+"'><input type=\"hidden\" name=\"cflj\" value='"+StringUtil.escapeStr(row.cflj)+"'><input type=\"hidden\" name=\"wjdx\" value='"+StringUtil.escapeStr(row.wjdx)+"'>"+StringUtil.escapeStr(row.wbwjm)+"."+StringUtil.escapeStr(row.hzm)+"</a></td>";
	 				tr += '</tr>';	
	 			});
		   }
	 	  tr += "</table></td>";  
	 	  tr += "<td style='text-align:left;vertical-align:middle;'><div class='uploaderDiv'   style='margin-left: 5px ;padding-left: 0;width:80px;'></div></td>";  
		  tr += "</tr>";
	 		$("#messageListTable").append(tr);
	 		
	 		if($("#messageListTable").find(".xiacipx").length>0){
	 			$("#messageListTable").find(".xiacipx").datepicker({
					 autoclose: true,
					 format:'yyyy-mm-dd',
					 clearBtn:true
				}).on('hide', function(e) {
					  $('#form1').data('bootstrapValidator')  
				 .updateStatus('xcpxrq', 'NOT_VALIDATED',null)  
				 .validateField('xcpxrq');  
					  
				});
	 		}
	 
	 		upLoadFile();
	 		onchliksc();
	 }
	 
	 /**
	  * 下载附件
	  */
	 function downloadfile(id) {
	 	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	 } 
	 //去掉实参的时候出勤率，成绩，考核结果，证书，下次培训日期，备注不可编辑，并且默认空
	 function onchliksc(){
		 
	 		$("#messageListTable>tr").each(function(){
	 			var index=$(this).index(); //当前行
	 			var cql =$("input[name='cql']").eq(index);
	 			var cj =$("input[name='cj']").eq(index) ;
	 			var zs =$("input[name='zs']").eq(index);
	 			var khjg = $("input[name='khjg"+(index+1)+"']");
	 			var xcpxrq =$("input[name='xcpxrq']").eq(index);
	 			var bz =$("input[name='bz']").eq(index);
	 			var isSc=$("input:checkbox[name='isSc"+(index+1)+"']:checked").val();
	 			
	 			if(isSc=="on"){
	 				 if($("#kcid").val()!=null&&$("#kcid").val()!=""&&$("#sjKsrq").val()!=""&&$("#sjKsrq").val()!=null){
	 					 selectkcid($("#kcid").val());
	 				 }
	 				
	 				cql.attr("disabled",false);
	 				cj.attr("disabled",false);
	 				khjg.attr("disabled",false);
	 				zs.attr("disabled",false);
	 				xcpxrq.attr("disabled",false);
	 				bz.attr("disabled",false);
	 			}else{
	 				cql.attr("disabled",true);
	 				cj.attr("disabled",true);
	 				khjg.attr("disabled",true);
	 				zs.attr("disabled",true);
	 				xcpxrq.attr("disabled",true);
	 				bz.attr("disabled",true);
	 				cql.val("");
	 				cj.val("");
	 				zs.val("");
	 				xcpxrq.val("");
	 				bz.val("");
	 				$("input[name='khjg"+(index+1)+"'][value='1']").attr("checked",true); 
	 			}
	 			
	 		});
	 }
	 
	 
		/**
		 * 收缩/展开附件
		 * @param id
		 */
		function switchAttachmentContent(id) {
			if ($("#" + id).is(":hidden")) {
				$("#" + id).fadeIn(500);
				$("#" + id + 'icon').removeClass("icon-caret-down");
				$("#" + id + 'icon').addClass("icon-caret-up");
			} else {
				$("#" + id).hide();
				$("#" + id + 'icon').removeClass("icon-caret-up");
				$("#" + id + 'icon').addClass("icon-caret-down");
			}
		}
	 
	 function upLoadFile(){
		 var fjid=""; 
		 $.each($("#messageListTable").find(".uploaderDiv"), function(){
			   var $this = $(this);
			   $this.uploadFile({
				   url:basePath+"/common/ajaxUploadFile",
				   multiple:true,
				   dragDrop:false,
				   showStatusAfterSuccess: false,
				   showDelete: false,
				   
				   maxFileCount:100,
				   formData: {
					   "fileType":"trainingprogramme"
				   },//参考FileType枚举（技术文件类型）
				   fileName: "myfile",
				   returnType: "json",
				   removeAfterUpload:true,
				   uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
				   uploadButtonClass: "ajax-file-upload_ext",
				   onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
				   {
					   var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
					   trHtml = trHtml+'<td><input type="hidden" name="fjid" value="" /><div>';
					   trHtml = trHtml+"<i class='icon-trash color-blue cursor-pointer' onclick=\"del_file('"+fjid+"',this)\" title='删除附件'>";
					   trHtml = trHtml+'</div></td>';
					   trHtml = trHtml+'<td class="text-left" title=\''+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'\'><input type="hidden" name="operate" value="ADD"><input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(data.attachments[0].yswjm)+'\'><input type="hidden" name="hzm" value=\''+StringUtil.escapeStr(data.attachments[0].hzm)+'\'><input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].wbwjm)+'\'><input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].nbwjm)+'\'><input type="hidden" name="cflj" value=\''+data.attachments[0].cflj+'\'><input type="hidden" name="wjdx" value=\''+data.attachments[0].wjdx+'\'>'
					   +StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'</td>';
					   trHtml = trHtml+'</tr>';	 
					   $this.parent().prev().append(trHtml);
				   }
				 //附件特殊字符验证（文件说明限制字符和windows系统保持一致）
					,onSubmit : function (files, xhr) {
						var wbwjm  = $.trim($('#wbwjm').val());
						if(wbwjm.length>0){
							if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
				            	$('#wbwjm').focus();
				            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
				            	
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
		   });
	 }
	 
	//搜索条件重置
	 function searchreset(){
	 	var dprtId = dprtcode;
	 	
	 	$("#keyword").val("");
	 	$("#jsxms").val("");
	 	$("#zt").val("");
	 	$("#pxDate_search").val("");
	 	

	 	$("#divSearch").find("textarea").each(function(){
	 		$(this).val("");
	 	});

	 	$("#divSearch").find("select").each(function() {
	 		$(this).attr("value", "");
	 	});
	 
	 	$("#dprtcode").val(dprtId);
	 	
		 var SelectArr1 = $("#jhlxs select");
		 if(SelectArr1[0].options[0] != undefined){
		 
		 SelectArr1[0].options[0].selected = true;
		 }
		 
		 var SelectArr2 = $("#pxlbs select");
		 SelectArr2[0].options[0].selected = true;
		 
		 var obj = document.getElementsByName('fxbs_search');
		 for(var i=0;i<obj.length;i++){
			 obj[i].checked=true;
	     };
	     changeOrganization();
			decodePageParam();
	 }
	 
	//移除一行
	 function removeCol(e,d,isYc){
	
		var falg=true;
		if(isYc==1){
			falg=false;
			 AlertUtil.showMessage('当前人员不可删除!');
			return;
		}
	 	
		 if(falg){
				$(e).parent().parent().remove();
			 	resXh();
			 	orderNumber--;
			 	var len = $("#messageListTable").find("tr").length;
			 	if(len < 1){
			 		$("#messageListTable").append("<tr><td  colspan='16'  class='text-center'>暂无数据 No data.</td></tr>");
			 	}
				for(var i =0;i<checkPersonList1.length;i++){
					
					if(checkPersonList1[i].id==d){
						checkPersonList1.splice(i,i+1);
					}
				}
		 }
		 
	 
	 }
	 function resXh(){
			
			var len = $("#messageListTable").find("tr").length;
			if (len == 1) {
				if($("#messageListTable").find("td").length ==1){
					return false;
				}
			}
			var orderNumber = 1;
			if (len > 0){
				$("#messageListTable").find("tr").each(function(){
					var index=$(this).index(); //当前行
					$("span[name='orderNumber']").eq(index).html(orderNumber++);
				});
			}
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
	 
	
	 function saveUpdate(){
		 $('#form1').data('bootstrapValidator').validate();
		  if(!$('#form1').data('bootstrapValidator').isValid()){
			return false;
		  }			 
		  	
		 var obj ={};
		 var id = $("#id").val();
		 var sjKsrq = $("#sjKsrq").val();
	 	 var sjKssj = $("#sjKssj").val();
	 	 var sjJsrq = $("#sjJsrq").val();
	 	 var sjJssj = $("#sjJssj").val();
	 	 var jsid = $("#jsid").val();
	 	 var jsxm = $("#jsxm").val();
	 	 var kcdd = $("#kcdd").val();
	 	 var pxxs = $("#pxxs").val();
	 	 var ksxs = $("#ksxs").val();
	 	 
		 var jsBz = $("#alertModaladdupdate input:radio[name='jsBz']:checked").val(); 
		 var isJcff = $("#alertModaladdupdate input:radio[name='isJcff']:checked").val(); 
	 	 var sjks = $("#sjks").val();
	 	 var kcnr = $("#kcnr").val();
	 	 var kcid = $("#kcid").val();
	 	 var kcbm = $("#kcbm").val();
	 	 var kcmc = $("#kcmc").val();
	 	 
	 	 var fxbs = $("#fxbs").val();
	 	 var pxlb = $("#pxlb").val();
	 	 var fjjx = $("#fjjx").val();
	 	 var zy = $("#zy").val();
	 	 var pxjg = $("#pxjg").val();
	 	 
	 	if(sjKsrq != "" && sjJsrq != null){
			var tempJhkssj = (sjKssj == ""?"00:00":sjKssj);
			var tempJhJssj = (sjJssj == ""?"00:00":sjJssj);
			if(TimeUtil.compareDate(sjKsrq +" " + tempJhkssj + ":00",sjJsrq +" " + tempJhJssj + ":00")){
				AlertUtil.showErrorMessage("实际结束时间不能小于实际开始时间!");
				return false;
			}
		}
	 	 obj.id = id;
	 	 obj.sjKsrq = sjKsrq;
		 obj.sjKssj = sjKssj;
		 obj.sjJsrq = sjJsrq;
		 obj.sjJssj = sjJssj;
		 obj.jsid = jsid;
		 obj.jsxm = jsxm;
		 obj.kcdd = kcdd;
		
		 obj.pxxs = pxxs;
		 obj.ksxs = ksxs;
		 obj.jsBz = jsBz;
		 obj.isJcff = isJcff;
		 obj.sjks = sjks;
		 obj.kcnr = kcnr;
		 
		 obj.fxbs = fxbs;
		 obj.pxlb = pxlb;
		 obj.fjjx = fjjx;
		 obj.zy = zy;
		 obj.pxjg = pxjg;
		 
		 var paramsMap={};
		 paramsMap.kcid = kcid;
		 paramsMap.kcnewid = kcnewid;
		 paramsMap.kcbm = kcbm;
		 paramsMap.kcmc = kcmc;
		 obj.paramsMap=paramsMap;
		 
		 var planPersonList = [];          //培训计划-培训课程
		 var len=$("#messageListTable").length;
		 var flag=true;
		 
			if (len == 1) {
				if($("#messageListTable").find("td").length ==1){
					flag = false;
					AlertUtil.showErrorMessage("请选择人员!");
					return planPersonList;
				}
			}
		 	if (len > 0){
		 		$("#messageListTable>tr").each(function(){
		 			var planPerson={};      //培训计划-培训课程人
		 			var index=$(this).index(); //当前行
		 			var jhid =$("input[name='jhid']").eq(index).val(); 
		 			var wxrydaid =$("input[name='wxrydaid']").eq(index).val(); 
		 			var xm =$("input[name='xm']").eq(index).val(); 
		 			var gzdw =$("input[name='gzdw']").eq(index).val(); 
		 			var cql =$("input[name='cql']").eq(index).val(); 
		 			var cj =$("input[name='cj']").eq(index).val(); 
		 			var isYc =$("input[name='isYc']").eq(index).val(); 
		 			var khjg = $("input[name='khjg"+(index+1)+"']:checked").val();
		 			var isSc=$("input:checkbox[name='isSc"+(index+1)+"']:checked").val();
		 			
		 			if(isSc=="on"){
		 				isSc=1;
		 			}else{
		 				isSc=0;
		 				khjg=null;
		 			}
		 			
		 			var zs =$("input[name='zs']").eq(index).val(); 
		 			var xcpxrq =$("input[name='xcpxrq']").eq(index).val(); 
		 			var bz =$("input[name='bz']").eq(index).val(); 
		 			if(cql==""&&isSc==1&&isYc==1){
						$("input[name='cql']").eq(index).focus();
						AlertUtil.showMessage("应参出勤率不能为空!");
						flag=false;
					}
		 			var re =/^(\d{1,2}(\.\d{1,2})?|100)$/;
					if(re.test(cql)==false&&isSc==1&&isYc==1){
						$("input[name='cql']").eq(index).focus();
						AlertUtil.showMessage("应参出勤率只能输入0.00-100!");
						flag=false;
					}
					
				 	if(xcpxrq!=""){
				 		 var oDate3 = new Date(xcpxrq);
				 	    var oDate4 = new Date($("#sjKsrq").val());
				 		 if(oDate3.getTime()< oDate4.getTime()){
				 			$("input[name='xcpxrq']").eq(index).focus();
				 			AlertUtil.showMessage('下次培训日期必须大于课程开始日期!');
				 			flag=false;
				 	    }
				 	}
		 			planPerson.id=formatUndefine(jhid);
		 			planPerson.wxrydaid=wxrydaid;
		 			planPerson.xm = xm;
		 			planPerson.gzdw = gzdw;
		 			planPerson.cql = cql;
		 			planPerson.cj =cj;
		 			planPerson.khjg = khjg;
		 			planPerson.isYc = isYc;
		 			planPerson.isSc = isSc;
		 			planPerson.zs =zs;
		 			planPerson.xcpxrq =xcpxrq;
		 			planPerson.bz =bz;
		 			var attachmentlist =[];
		 			
		 			$(this).find("td[name='pxfj'] tr").each(function(){
		 				var infos2 ={};
		 				var index=$(this).index(); //当前行
		 				var id =$(this).find("input[name='fjid']").val(); 
		 				var yswjm =$(this).find("input[name='yswjm']").val(); 
		 				var nbwjm =$(this).find("input[name='nbwjm']").val();
		 				var wbwjm =$(this).find("input[name='wbwjm']").val();
		 				var hzm =$(this).find("input[name='hzm']").val();
		 				var cflj =$(this).find("input[name='cflj']").val();
		 				var wjdx =$(this).find("input[name='wjdx']").val();
		 				var operate =$(this).find("input[name='operate']").val();
		 				
		 				infos2.id = id;
		 				infos2.yswjm = yswjm;
		 				infos2.nbwjm = nbwjm;
		 				infos2.hzm = hzm;
		 				infos2.wbwjm = wbwjm;
		 				infos2.cflj = cflj;
		 				infos2.wjdx = wjdx;
		 				infos2.operate = operate;
		 				
		 				attachmentlist.push(infos2);
		 			});
		 			var dellen = delAttachements.length;
	 				if (dellen != undefined && dellen > 0) {
	 					for (var i = 0; i < dellen; i++) {
	 						attachmentlist.push({
	 							'id' : delAttachements[i].id,
	 							'operate' : 'DEL'
	 						});
	 					}
	 				}
		 			planPerson.attachments = attachmentlist;  //附件集合属于培训对象的属性
		 			
		 			planPersonList.push(planPerson);  
		 			
		 		});
		 		if(!flag){
					return;
				}
		 	}
		 	var attachmentsObj = attachmentsUtil.getAttachmentsComponents();
			obj.planPersonList=planPersonList;
			obj.fjidlist=fjidlist;
			obj.attachments = attachmentsObj.getAttachments();
		 submitAjax(obj);
		 hideBottom();
	 	 }

	 function submitAjax(obj){
		 startWait($("#alertModaladdupdate"));
	   	 AjaxUtil.ajax({
	 		url:basePath+"/training/course/recordssave",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#alertModaladdupdate"),
	 		success:function(data){
	 			finishWait($("#alertModaladdupdate"));
	 			AlertUtil.showMessage('操作成功!');
	 			$("#alertModaladdupdate").modal("hide");
	 			 $("#form1").bootstrapValidator('resetForm', false);
	 		      validation();
	 			searchFjgzRecord();
	 			//refreshPermission();
	 		}
	   	   });
	 }
	 
	 function saveColse(){
		 $("#form1").bootstrapValidator('resetForm', false);
	      validation();
	 }
     function searchFjgzRecord(){
    	 goPage(1, "desc", "auto");
    	 document.getElementById('bottom_hidden_content').style.display = "none";
    	 $("#selectCourse").html("");
    	 hideBottom();
    	 TableUtil.resetTableSorting("fjgz_record_sheet_table");
     }
     
     function showDcgzcl(this_){
		var id = $(this_).attr("id");
		var scrs = $(this_).attr("scrs");
		var ycrs = $(this_).attr("ycrs");
		var kcbh = $(this_).attr("kcbh");
		var zt = $(this_).attr("zt");
		new Sticky({tableId : 'fjgz_record_sheet_table'});//初始化表头浮动
		$("#manidinfo").val(id);
		showInfo(id,scrs,kcbh,zt,ycrs);
		 
		var isBottomShown = false;
		if($(".displayContent").is(":visible")){
			isBottomShown = true;
		}
	 	TableUtil.showDisplayContent();
	 	if($("#hideIcon").length==0){
	 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($(".fenye"));
		}
	 	
		if(!isBottomShown){
			TableUtil.makeTargetRowVisible($(this_), $("#fjgz_record_sheet_table"));
		}
	 	
	 	 $("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
     }
     
     $("#dprtcode").change(function() {
    	 searchFjgzRecord();
     });
     
     function orderBy(obj) {
    		var orderStyle = $("#" + obj + "_order").attr("class");
    		$("th[id$=_order]").each(
    				function() { // 重置class
    					$(this).removeClass("sorting_desc").removeClass("sorting_asc")
    							.addClass("sorting");
    				});
    		$("#" + obj + "_" + "order").removeClass("sorting");
    		var orderType = "asc";
    		if (orderStyle.indexOf("sorting_asc") >= 0) {
    			$("#" + obj + "_" + "order").addClass("sorting_desc");
    			orderType = "asc";
    		} else {
    			orderType = "desc";
    			$("#" + obj + "_" + "order").addClass("sorting_asc");
    		}
    		orderStyle = $("#" + obj + "_order").attr("class");
    		var currentPage = $("#fjgzjk_pagination li[class='active']").text();
    		goPage(currentPage, orderStyle.split("_")[1], obj);
    	}
 	//隐藏Modal时验证销毁重构
//	 $("#alertModaladdupdate").on("hidden.bs.modal", function() {
//		 $("#form1").bootstrapValidator('resetForm', false);
//	      validation();
//		 $("#form1").data('bootstrapValidator').destroy();
//			$('#form1').data('bootstrapValidator', null);
//	 });
	 

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
		
		function loadAttachmentEdit(id,colOptionhead,fileHead){
			
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents();
			attachmentsObj.show({
				attachHead : true,
				chinaHead : '附件',
				englishHead : 'Courseware Information',
				chinaFileHead : '文件说明',
				englishFileHead : 'Courseware',
				djid:id,
		 		colOptionhead : colOptionhead,
				fileHead : fileHead,
				fileType:"course"
			});//			
		}
		
		//上传附件
		function loadAttachment(param){
			$("#"+param.id).addClass('bg_tr').siblings().removeClass('bg_tr');
			attachments_list_crud.show({
				attachHead : false,
				chinaHead : '附件',
				englishHead : 'Courseware Information',
				chinaFileHead : '文件说明',
				englishFileHead : 'Courseware',
				djid:param.id,
		 		colOptionhead : true,
				fileHead : true,
				fileType:"course",
				callback: function(attachments){//回调函数
					if(attachments != null){
						param.attachments = attachments;
						// 提交数据
						AjaxUtil.ajax({
							url:basePath+"/training/course/edit",
							contentType:"application/json;charset=utf-8",
							type:"post",
							data:JSON.stringify(param),
							dataType:"json",
							success:function(data){
								attachments_list_crud.loadAttachements();
							}
						});
					}
				}
			});//显示附件
		}
		
		//打开调整列表对话框
		 function openPersonelWin() {
		 	var this_ = this;
		 	Personel_Tree_Multi_Modal.show({
		 		checkUserList:checkPersonList1,//原值，在弹窗中回显
		 		dprtcode:$("#dprtcode").val(),//
		 		callback: function(data){//回调函数
		 			if(data != null && data != ""){
		 				
		 				var str=[];
		 				$.each(checkPersonList1, function(i, row){
		 					str.push(formatUndefine(row.id));
						})
		 				$.each(data, function(i, row){
		 					var row1 =[];
				 			var maintenancePersonnel =[];
		 					row1.wxrydaid=row.id;
				 			row1.xm=row.xm;
				 			row1.gzdw=row.szdw;
				 			maintenancePersonnel.rybh=row.rybh;
				 			row1.maintenancePersonnel=maintenancePersonnel;
				 			if($.inArray(row1.wxrydaid, str)==-1){
				 				 if(row1!=null){
						 				var len = $("#messageListTable").length;
									 	if (len == 1) {
									 		if($("#messageListTable").find("td").length == 1){
									 			$("#messageListTable").empty();
									 		}
									 	}
								 		row1.orderNumber = orderNumber++;
								 		row1.isYc=0;
						 				addRow(row1,null);
						 				changedate();
						 			 }else{
						 				 $("#messageListTable").append("<tr><td  colspan='16' class='text-center'>暂无数据 No data.</td></tr>");
						 			 }
				 				
				 			}
				 			
		 				});
		 				
		 			}
		 		
		 		
		 		}
		 	})
		 }
		 
		//改变课程开始日期
		 function changedate(){
			$("#jsid").val('');
		 	$("#jsxm").val('');
			 if($("#kcid").val()!=null&&$("#kcid").val()!=""&&$("#sjKsrq").val()!=""&&$("#sjKsrq").val()!=null){
				 selectkcid($("#kcid").val());
			 }
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
		 			if(data !=null){
		 				var ss=$("#sjKsrq").val();
		 				if(ss!=""&&ss!=null){
		 					var num="";
			 				if(data.zqdw==1){//天
			 					 num=dateAdd(ss,"d",data.zqz);
			 				}else if(data.zqdw==2){//月
			 					 num=addMoth(ss,data.zqz);
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
			 				   if(data.isFx==1){
			 					   $("#messageListTable>tr").each(function(){
			 						   var index=$(this).index(); //当前行
			 						  var isSc=$("input:checkbox[name='isSc"+(index+1)+"']:checked").val();
			 				 			if(isSc=="on"){
			 				 				var xcpxrq =$("input[name='xcpxrq']").eq(index);
			 				 				xcpxrq.val(year + "-" + month + "-" + date);
			 				 				xcpxrq.datepicker("update");
			 				 			}
			 					   });
			 				   }
			 				    
			 				}
		 				}
		 				
		 				 
		 			}
		 		}
		 	});
		 }
		 
		 function addMoth(date, months){ 
				var d=new Date(Date.parse(date.replace(
						/-/g, "/"))); 
				d.setMonth(d.getMonth()+months); 
				var month=d.getMonth()+1; 
				var day = d.getDate(); 
				if(month<10){ 
				month = "0"+month; 
				} 
				if(day<10){ 
				day = "0"+day; 
				} 
				var val = d.getFullYear()+"-"+month+"-"+day; 
				return val; 
			}
		 
//		 function addMoth(d,m){
//			    var ds=d.split('-'),_d=ds[2]-0;
//			    var nextM=new Date( ds[0],ds[1]-1+m+1, 0 );
//			    var max=nextM.getDate();
//			    d=new Date( ds[0],ds[1]-1+m,_d>max? max:_d );
//			    return d.toLocaleDateString().match(/\d+/g).join('-');
//			 }


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
		
		$('#sjKsrq').datepicker({
			 autoclose: true,
			 clearBtn:true
		}).on('hide', function(e) {
			  $('#form1').data('bootstrapValidator')  
		 .updateStatus('sjKsrq', 'NOT_VALIDATED',null)  
		 .validateField('sjKsrq');  
		});
	
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
			
			
			//设置高度
			function authHeight(){
			    //头部高度
			    var headerHeight = $('.header').outerHeight();
			    var headerDownHeight = $('.header-down').outerHeight();
			    //window高度
			    var windowHeight = $(window).height()
			    //尾部的高度
			    var footerHeight = $('.footer').outerHeight()||0;
			    //分页的高度
			    var paginationHeight = $('.page-height:visible').outerHeight()||0;
			    //panelheader的高度
			    var panelHeadingHeight = $('.panel-heading').outerHeight();
			    //调整高度
			    var adjustHeight = $("#adjustHeight").val()||0;
			    //搜索框的高度
			    var searchContent=$(".searchContent").outerHeight()||0;
			    //body的高度
			   
			    
			   //情景1：table-tab
			    //修改1：在页面div class='page-content'上加class='page-content table-tab-type'
			    //修改2：给上方表格的父div 添加class='table-tab-type_table'
			    //修改3：给tab中table的父div 添加class='tab-second-height'
			    
			    
			    if($(".table-tab-type").length>0){
			    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
			    	//表格的高度
			        var tableHeight = bodyHeight - searchContent - paginationHeight  - 30 - adjustHeight;
			      //谷歌的兼容性问题
			        if(navigator.userAgent.indexOf("Chrome") > -1){
			        	tableHeight -= 10;
			        }
			        //隐藏的div是否显示
			        if($("#fileDiv").css("display")=='block'){
			        	//表格的高度
			        	var actualTableHeight= tableHeight*0.5;
			        	//表格的高度
			        	$(".table-tab-type_table").attr('style', 'height:' + actualTableHeight+ 'px !important;overflow-x: auto;');
			        	// tab header 的高度
			        	var table_tab=$("#fileDiv .nav-tabs").outerHeight()||0;
			        	//选中的提示信息
			        	var selectCourse=$("#selectCourse").outerHeight()||0;
			        	//表格的高度
			        	var actualTableOuterHeight=$(".table-tab-type_table").outerHeight()||0;
			        	// tabcontent
			        	var tabcontent=tableHeight-actualTableOuterHeight-table_tab-selectCourse-10;
			        	//如果下方的tab是引用课件信息。
			        	if($(".tab-second-height").length>0){
			        		//是否存在课件上传
			        		var fileHeadheigth=$("#fileHead").outerHeight()||0;
			        		//下方tab的高度
			        		var tab_second_height =tabcontent-fileHeadheigth-20;
			        		//给下方tab中table 的父div 的高度进行赋值
			        		$(".tab-second-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;')
			        	}
			        	
			        }else{
			        	//给表格的父div的高度进行赋值
			        	 $(".table-tab-type_table").attr('style', 'height:' + tableHeight+ 'px !important;overflow-x: auto;');
			        }
			        
			    }
			  //情景2：table-table
			    if($(".table-table-type").length>0){
			    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
			      	//表格的高度
			          var tableHeight = bodyHeight - searchContent - paginationHeight -35 - adjustHeight;
			         //谷歌的兼容性问题
			          if(navigator.userAgent.indexOf("Chrome") > -1){
			          	tableHeight -= 10;
			          }
			        //隐藏的div是否显示
			          if($(".bottom_hidden_content").css("display")=='block'){
			        	  $(".table-height").attr('style', 'height:' + tableHeight*0.45+ 'px !important;overflow-x: auto;');
			        	
			        		  /*$(".table-height").attr('style', 'height:190px !important;overflow-x: auto;');  */
			        	 
			        	  
			        	  var table_height=parseInt($(".table-height").css("height"));
			        	  var bottom_height=tableHeight-(table_height);
			        	  var bottompanelheader=$(".bottom_hidden_content .panel-heading ").outerHeight()||0;
			        	  var bottom_hidden_table_content=bottom_height-bottompanelheader-24;
			        	  $(".bottom-hidden-table-content").css("height",bottom_hidden_table_content+"px")
			          }else{
			        	$(".table-height").attr('style', 'height:' + tableHeight+ 'px !important;overflow-x: auto;');
			          }
			    }
			    
			  //情景3：tab-table
			    

			    //情景4:tab-tab 类型
			    //修改1：在页面div class='page-content'上加class='page-content tab-tab-type'
			    //修改2：在页面第一个ul的class='nav-tabs'的父div 上加class='tab-tab-type_parentdiv'
			    //修改3：在第一个Tab的表格的父div 上添加class='tab-first-height'（有几个tab就得添加几次）
			    //修改4：在第二个Tab的表格的父div 上添加class='tab-second-height'（有几个tab就得添加几次）
			    if($(".tab-tab-type").length>0){
			    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
			     	//表格的高度
			         var tableHeight = bodyHeight - searchContent - paginationHeight  - 30 - adjustHeight;
			        //谷歌的兼容性问题
			         if(navigator.userAgent.indexOf("Chrome") > -1){
			         	tableHeight -= 10;
			         }
			         //first tab header
			         var  tab_tab_type_parentdiv_header=$(".tab-tab-type_parentdiv .nav-tabs").outerHeight()||0
			         //#fileDiv 不显示的情况下 first tab 的高度
			         var tabheight=tableHeight-tab_tab_type_parentdiv_header-10;
			         //隐藏的div是否显示
			         if($("#fileDiv").css("display")=='block'){
			        	 //#fileDiv显示情况下first tab中table的高度
			        	 $(".tab-first-height").attr('style', 'height:' + tabheight*0.5+ 'px !important;overflow-x: auto;');
			        	 //提示信息的高度
			        	 var selectCourse=$("#selectCourse").outerHeight()||0;
			        	 //第二个tab header 的高度
			        	 var secondheader=$("#fileDiv .nav-tabs").outerHeight()||0;
			        	 //第一个tab的高度
			        	 var tab_tab_type_parentdiv=$(".tab-tab-type_parentdiv").outerHeight()||0;
			        	 //第二个tab中table的高度
			        	 var tab_second_height=tableHeight-(tab_tab_type_parentdiv-paginationHeight)-secondheader-selectCourse-10;
			        	//给第二个tab中table的高度赋值
			        	 $(".tab-second-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;') 
			         }else{
			        	//#fileDiv 不显示情况下first tab中table的高度
			        	 $(".tab-first-height").attr('style', 'height:' + tabheight+ 'px !important;overflow-x: auto;');
			         }
			    }  
			    
			}
			
			function hideBottom(){
				$("#hideIcon").remove();
				TableUtil.hideDisplayContent();
			}
			
			// 自定义页面高度
			function customResizeHeight(bodyHeight, tableHeight){
				var body_bottom_height = $(".displayContent").outerHeight()||0;
				var panel_heading_height = $(".displayContent .panel-heading").outerHeight()||0;
				var selectCourseHeight = $("#selectCourse").outerHeight()||0;
				if(selectCourseHeight==0){
					selectCourseHeight = 17;
				}
				var botttom_panel_body = body_bottom_height - selectCourseHeight - panel_heading_height;
				$(".displayContent .panel-body").attr('style', 'height:' + botttom_panel_body+ 'px !important;overflow: auto;')
				
			}
			
function exportExcel(){
	var keyword = $.trim($("#keyword").val());
	var dprtcode = $.trim($("#dprtcode").val());
	var pxlb=$("#selectpxlb").val();
	var jsxm=$("#jsxms").val();
	var zt=$("#zt").val();
	var jhlx=$("#jhlx").val();
	
	 var fxbsList ="";
	 var len=[];
	 $('input:checkbox[name=fxbs_search]:checked').each(function(){
		 fxbsList=$(this).val()+","+fxbsList;
		 len.push($(this).val());
	 });
	 if(len.length == 0){
		 fxbsList="3,";
	 }
	 var pxDate_search = $.trim($("#pxDate_search").val());
     var jhrqBegin="";
     var jhrqEnd="";
	 if(null != pxDate_search && "" != pxDate_search){
		 jhrqBegin= pxDate_search.substring(0,4)+"-"+pxDate_search.substring(5,7)+"-"+pxDate_search.substring(8,10)+" 00:00:00";
		 jhrqEnd= pxDate_search.substring(12,17)+"-"+pxDate_search.substring(18,20)+"-"+pxDate_search.substring(21,23)+" 23:59:59";
	 }
	 
	window.open(basePath+"/training/course/CourseRecords.xls?dprtcode="
 				+ dprtcode 
 				+ "&pxlb="+pxlb
 				+ "&jsxm="+encodeURIComponent(jsxm)
 				+ "&zt="+zt
 				+ "&jhlx="+jhlx
 				+ "&zy="+fxbsList
 				+ "&jhrqBegin="+encodeURIComponent(jhrqBegin)
 				+ "&jhrqEnd="+encodeURIComponent(jhrqEnd)
 				+"&keyword="+ encodeURIComponent(keyword));
	
}

function showImportModal(){
	 importModal.show();	
}