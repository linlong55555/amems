function copy() {
	$("#alertCopyWO").modal("show");
	loadCopyWo(1,"auto","desc");
}
function changeLx(){
   loadCopyWo(1,"auto","desc");
}

function loadCopyWo(pageNumber,sortType,sequence){
	var obj={};
	obj.keyword = $.trim($("#keyword_search_copy").val());
	obj.lx = $.trim($("#lx_search").val());
	obj.dprtcode=$.trim( $('#zzjgid').val()); 
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	AjaxUtil.ajax({
		url:basePath+"/project/workorder/loadCopy",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(obj),
		success:function(data){
			finishWait();
			if(data.total >0){
				appendCopyList(data.rows);
				new Pagination({
					renderTo : "pagination_copy",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(a,b,c){
						loadCopyWo(a,b,c);
					}
				});
				// 标记关键字
				signByKeyword($("#keyword_search_copy").val(),[2]);
			} else {
				$("#copy_wolist").empty();
				$("#pagination_copy").empty();
				$("#copy_wolist").append("<tr><td colspan=\"6\">暂无数据 No data.</td></tr>");
			}
		},
	}); 
}

//按钮选择
function chooesRow2(obj){
	if(obj.type=='radio'){
		var flag = $(obj).is(":checked");
		if(!flag){
			$(obj).attr("checked",true);
		}
	}else{
		var flag = $(obj).is(":checked");
		if(flag){
			 $(obj).removeAttr("checked");
		}else{
			 $(obj).attr("checked",true);
		}
	}
}
//单选行选
function chooesRow1(objradio){
	var obj = $(objradio).find("input[type='radio']");
	var flag = obj.is(":checked");
	if(!flag){
		obj.attr("checked",true);
	}
}
function searchRevision_copy(){
	loadCopyWo(1,"auto","desc");
}
function appendCopyList(list){
   var htmlContent = '';
   $.each(list,function(index,row){
	   if (index % 2 == 0) {
		   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)'>";
	   } else {
		   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow1(this)'>";
	   }
	   htmlContent = htmlContent + "<td><input type=\"radio\" name='glgd' onclick='chooesRow2(this)'>" ;
					 htmlContent = htmlContent + "<input type='hidden' name='id' value='"+formatUndefine(row.id)+"'>";
					 htmlContent = htmlContent + "<input type='hidden' name='gddlx' value='"+formatUndefine(row.gddlx)+"'>";
					 htmlContent = htmlContent + "<input type='hidden' name='gczlbh' value='"+StringUtil.escapeStr(row.gczlbh)+"'>";
					 htmlContent = htmlContent + "<input type='hidden' name='gczlzxdxid' value='"+formatUndefine(row.gczlzxdxid)+"'>";
					 htmlContent = htmlContent + "<input type='hidden' name='gczlid' value='"+formatUndefine(row.gczlid)+"'>";
					 htmlContent = htmlContent + "<input type='hidden' name='lx' value='"+formatUndefine(row.lx)+"'></td>";
	   
	   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.gdbh)+"</td>";
	   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.zy)+"</td>"; 
	   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.displayname)+"</td>";
	   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.gzzw)+"</td>";
	   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zhut)+"'>"+StringUtil.escapeStr(row.zhut)+"</td>";  
	   htmlContent = htmlContent + "</tr>";  
   });
   $("#copy_wolist").html(htmlContent);
}
function appendCopyWo(){
	$('input[name=glgd]:checked').each(function(){  
		var id=$(this).next().val();
		var gddlx=$(this).next().next().val();
		var gczlbh=$(this).next().next().next().val();
		var gczlzxdxid=$(this).next().next().next().next().val();
		var gczlid=$(this).next().next().next().next().next().val();
		var lx=$(this).next().next().next().next().next().next().val();
		if(gczlbh!=null&&gczlzxdxid!=null&&gczlid!=null){
			$("#gczlbh").val(gczlbh);
			$("#gczlzxdxid").val(gczlzxdxid);
			$("#gczlid").val(gczlid);
		}
		if(lx=="GD"){
			loadWo(id,gddlx);
		}else{
			loadJobCard(id);
		}
	});
}
function loadWo(id,gddlx){
	var obj={};
	obj.id=id;
	obj.gddlx=gddlx;
	AjaxUtil.ajax({
		url:basePath+"/project/workorder/loadCopyWo",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(obj),
		success:function(data){
			finishWait();
			$("#jobcard_demo").hide();
			$("#jd").val(data.jd);
			$("#zy").val(data.zy);
			$("#zjhandname").val(formatUndefine(data.zjh)+"  "+formatUndefine(data.zwms));
			$("#zjh").val(formatUndefine(data.zjh));
			$("#isXyjszy").val(formatUndefine(data.isXyjszy));
			$("#gzzw").val(formatUndefine(data.gzzw));
			$("#gzz").val(formatUndefine(data.gzzid));
			if(data.jhgsRs!=null&&data.jhgsRs!=""&&data.jhgsXss!=null&&data.jhgsXss!=""){
				$("#jhgsRs").val(data.jhgsRs);
				$("#jhgsXss").val(data.jhgsXss);
				$("#time").val(data.jhgsRs*data.jhgsXss);
			}
			$("#wjzt").val(data.zhut);
			$("#zhut").val(data.zhut);
			$("#glxx").val(data.glxx);
			$("#tcsj").val(data.tcsj);
			$("#xfgdyy").val(data.xfgdyy);
			$("#bz").val(data.bz);
			
			if($("#gczlbh").val==null||$("#gczlbh").val==""){
				appendOrdrsouce(data.baseWorkOrder.orderSourceList);
			}
			appendWoObj(data.baseWorkOrder.wOActionObj);
			appendWorkContent(data.baseWorkOrder.woJobContent);
			appendWorkCard(data.baseWorkOrder.nonwocardList);
			appendwoAirMaterial(data.baseWorkOrder.woAirMaterial);
			//appendwoJobenclosure(data.baseWorkOrder.woJobenclosure);
		},
	}); 
}
function loadJobCard(id){
	var obj={};
	obj.id=id;
	AjaxUtil.ajax({
		url:basePath+"/project/workorder/loadCopyJobCard",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(obj),
		success:function(data){
			finishWait();
			$("#djgkid").val(data.id);
			if(data.id==""||data.id==null){
				$("#jobcard_demo").hide();
			}else{
				$("#jobcard_demo").show();
				$("#view_gkid").text(data.gdbh);
				$("#temp_id").val(data.id);
				$("#temp_dprtcode").val(data.dprtcode);
			}
			$("#jd").val(data.jd);
			$("#zy").val(data.zy);
			$("#zjhandname").val(formatUndefine(data.zjh)+"  "+formatUndefine(data.zjhZwms));
			$("#zjh").val(formatUndefine(data.zjh));
			$("#isXyjszy").val(data.isXyjszy);
			$("#gzzw").val(data.gzzw);
			$("#gzz").val(data.gzzId);
			if(data.jhgsRs!=null&&data.jhgsRs!=""&&data.jhgsXss!=null&&data.jhgsXss!=""){
				$("#jhgsRs").val(data.jhgsRs);
				$("#jhgsXss").val(data.jhgsXss);
				$("#time").val(data.jhgsRs*data.jhgsXss);
			}
			$("#wjzt").val(data.gzzt);
			$("#zhut").val(data.gzzt);
			$("#glxx").val(data.glxx);
			$("#tcsj").val(data.tcsj);
			$("#xfgdyy").val(data.xfgdyy);
			$("#bz").val(data.bz);
			appendWorkContent(data.woJobContentList);
			appendwoAirMaterial(data.woAirMaterialList);
		},
	}); 
}

//回显评估单数据
function appendOrdrsouce(obj){
	$("#Annunciatelist").html("");
	if(obj!=null){
		var htmlContent = ""; 
		$(obj).each(function(){ 
			htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+this.pgdid+"'>";           
			htmlContent = htmlContent + "<td style='vertical-align:middle'><a href=javascript:lineRemove('"+this.pgdid+"')><button class='line6'><i class='icon-minus cursor-pointer'></i></button></a></td>";
			htmlContent = htmlContent + "<td style='vertical-align:middle'><input type='hidden' name='PgdIdAndPgdh' value="+this.pgdid+","+StringUtil.escapeStr(this.pgdh)+">"+StringUtil.escapeStr(this.pgdh)+"</td>";
			htmlContent = htmlContent + "<td class='text-left' title='"+this.ly+"'style='vertical-align:middle'>"+StringUtil.escapeStr(this.ly)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(this.shzltgh)+"'style='vertical-align:middle'>"+StringUtil.escapeStr(this.shzltgh)+"</td>";  
			htmlContent = htmlContent + "<td style='vertical-align:middle'>"+(this.sxrq.substring(0,10))+"</td>";
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(this.displayname)+"</td>";
			htmlContent = htmlContent + "<td style='vertical-align:middle'>"+(this.pgqx.substring(0,10))+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+zts[this.zt]+"</td>";
			htmlContent = htmlContent + "</tr>";
			pgdids.push(this.pgdid);
			$('#pgjx').val(this.jx);                        //将评估单的机型放在隐藏域中
		});
		$("#Annunciatelist").append(htmlContent);
	}
}
//回显执行对象数据
function appendWoObj(obj){
	var dprtcode=$.trim( $('#zzjgid').val()); 
	$("#zxdxtr input").val(""); 
	$("#zxfl").val(obj.zxfl);
	$("#zjqdid").val(obj.zjqdid);
	$("#overRide").remove();
	$("#searchBut").css("display", "none");
	if($("#gddlx").val()==3){
		$("#zxdxDiv input").val("");
		if(obj.zxfl!='FZJJ'){
			var zxflValue=(obj.zxfl=='ZJJ'?'飞机部件':'机身');
			$("#zxflname").val(zxflValue);
			$("#fjzch").val(obj.fjzch);
			AjaxUtil.ajax({                                                           //根据飞机注册号查询飞机序号和左发序号
				type : 'post',
				url : basePath+"/productionplan/scheduledcheckitem/getXlh?fjzch="+encodeURIComponent(obj.fjzch)+"&dprtcode="+dprtcode,
				dataType : 'json',
				success : function(data) {
					if(data!=null){
						$("#fjxlh").val(data.fjXlh);
						$("#zfxh").val(data.zfXlh);
						$("#yfxh").val(data.yfXlh);
					}
				}
			});
		}else if(obj.zxfl=='FZJJ'){
			$("#zxflname").val("非装机件");
		}else{
			$("#zxflname").val("");
		}	
	}else{
		$("#fjzch").attr("disabled",false);
		$('#jssj').attr("disabled",false);
		$('#qlxh').attr("disabled",false);
		$('#jcsj').attr("disabled",false);
		$('#jcxh').attr("disabled",false);
		if(obj.zxfl!='FZJJ'){
			AjaxUtil.ajax({                                                           //根据飞机注册号取得机型
				type : 'post',
				async:true,
				url : basePath+"/productionplan/planeData/findJx?fjzch="+encodeURIComponent(obj.fjzch)+"&dprtcode="+dprtcode,
				dataType : 'json',
				success : function(data) {
					if(data!=null){
						$("#fjjx").val(data.fjjx);
					}
					var apps = '';
					var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode,FJJX:$("#fjjx").val()});
					if(planeDatas != null && planeDatas.length >0){
						$.each(planeDatas,function(i,planeData){
							if(obj.fjzch==planeData.FJZCH){
								apps += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' selected='selected' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
							}else{
								apps += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
							}
						});
					}
					$("#fjzch").html(apps);
					AjaxUtil.ajax({                                                           //根据飞机注册号查询飞机序号和左发序号
						type : 'post',
						url : basePath+"/productionplan/scheduledcheckitem/getXlh?fjzch="+encodeURIComponent(obj.fjzch)+"&dprtcode="+dprtcode,
						dataType : 'json',
						success : function(data) {
							if(data!=null){
								$("#fjxlh").val(data.fjXlh);
								$("#zfxh").val(data.zfXlh);
								$("#yfxh").val(data.yfXlh);
							}
						}
					});
				}
			});
			if(obj.zxfl=='ZJJ'){
				$("#zxflname").val("飞机部件");
				$("#searchBut").css("display", ""); 
			}else if(obj.zxfl=='FJ'){
				$("#zxflname").val("机身");
			}	
		}else if(obj.zxfl=='FZJJ'){
			$("#fjjx").append("<option id='overRide' value='无' selected='selected'>无</option>");
			$("#zxflname").val("非装机件");
			$("#fjzch").val("");
			$("#fjzch").attr("disabled",true);
			$('#jssj').attr("disabled",true);
			$('#qlxh').attr("disabled",true);
			$('#jcsj').attr("disabled",true);
			$('#jcxh').attr("disabled",true);
			$("#searchBut").css("display", ""); 
		}else{
			$("#zxflname").val("");
		}
	}
	$("#bjh").val(obj.bjh);
	$("#bjxlh").val(obj.bjxlh);
	$("#bjmc").val(obj.bjName);
	//监控项目赋值
	var  jkzF=obj.jkzF;
	var  jkxmbhF=obj.jkxmbhF;
	if(jkzF!=null && jkzF!=""){
		if(jkxmbhF=="calendar"){
			$("#wcrq").val(jkzF);
		}
		if(jkxmbhF=="fuselage_flight_time"){
			$("#jssj").val(jkzF);
		}
		if(jkxmbhF=="landing_gear_cycle"){
			$("#qlxh").val(jkzF);
		}
		if(jkxmbhF=="winch_time"){
			$("#jcsj").val(jkzF);
		}
		if(jkxmbhF=="winch_cycle"){
			$("#jcxh").val(jkzF);
		}
	}

	var  jkzS=obj.jkzS;
	var  jkxmbhS=obj.jkxmbhS;	
	if(jkzS!=null && jkzS!=""){
		if(jkxmbhS=="calendar"){
			$("#wcrq").val(jkzS);
		}
		if(jkxmbhS=="fuselage_flight_time"){
			$("#jssj").val(jkzS);
		}
		if(jkxmbhS=="landing_gear_cycle"){
			$("#qlxh").val(jkzS);
		}
		if(jkxmbhS=="winch_time"){
			$("#jcsj").val(jkzS);
		}
		if(jkxmbhS=="winch_cycle"){
			$("#jcxh").val(jkzS);
		}
	}

	var  jkzT=obj.jkzT;
	var  jkxmbhT=obj.jkxmbhT;	
	if(jkzT!=null && jkzT!=""){
		if(jkxmbhT=="calendar"){
			$("#wcrq").val(jkzT);
		}
		if(jkxmbhT=="fuselage_flight_time"){
			$("#jssj").val(jkzT);
		}
		if(jkxmbhT=="landing_gear_cycle"){
			$("#qlxh").val(jkzT);
		}
		if(jkxmbhT=="winch_time"){
			$("#jcsj").val(jkzT);
		}
		if(jkxmbhT=="winch_cycle"){
			$("#jcxh").val(jkzT);
		}
	}
}
//回显工作内容数据
function appendWorkContent(obj){
	$("#listContent").html("");
	workId = 1;
	if(obj!=null){
		var htmlContent = ""; 
		$(obj).each(function(){  
			htmlContent = htmlContent + "<tr  name='one_line' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
			htmlContent = htmlContent + "<td style='vertical-align:middle'><button class='line6' onclick='del_tr(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button></td>" ;
			htmlContent = htmlContent + "<td style='vertical-align:middle' name='workId'>"+workId+"</td>";  
			htmlContent = htmlContent + "<td><textarea  rows='3' maxlength='1300' placeholder='最大长度不超过1300' class='form-control' type='text' name='gznr'>"+StringUtil.escapeStr(this.gznr)+"</textarea></td>";  
			htmlContent = htmlContent + "<td style='vertical-align:middle'><input maxlength='6' class='form-control' type='text' name='gzz' value='"+StringUtil.escapeStr(this.gzz)+"'/></td>";  
			htmlContent = htmlContent + "<td style='vertical-align:middle'><input name='isBj' type='checkbox' style='width: 20px;height: 20px;' "+(this.isBj==1?"checked='checked'":"")+" /></td>";  
			htmlContent = htmlContent + "</tr>"; 
			workId++;
		});
		$("#listContent").append(htmlContent);
	}
}
//回显工作相关工单的数据
function appendWorkCard(obj){
	$("#glgdCardList").html("");
	glgdgknum = 1;
	if(obj!=null){
		var htmlContent = ""; 
		$(obj).each(function(){  
			htmlContent = htmlContent + "<tr style=\"cursor:pointer\" name='glgd' bgcolor=\"#fefefe\" id='tr1_"+this.xggdid+"'>";
			htmlContent = htmlContent + "<td style='vertical-align:middle'><a href=javascript:lineRemove3('"+this.xggdid+"')><button class='line6'><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button></a></td>";  
			htmlContent = htmlContent + "<td style='vertical-align:middle' name='glgdgknum'><input type='hidden' name='id' value='"+this.xggdid+"'><input type='hidden' name='gdjcid' value='"+this.xgjcgdid+"'>"+glgdgknum+"</td>";  
			htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-left'>"+StringUtil.escapeStr(this.gdbh)+"</td>";  
			if(this.xggdZlx==null||this.xggdZlx==""){
				htmlContent = htmlContent + "<td style='vertical-align:middle'class='text-left'><input type='hidden' name='gdlx' value='"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',this.xggdLx)+"'>"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',this.xggdLx)+"</td>";  
			}else{
				htmlContent = htmlContent + "<td style='vertical-align:middle'class='text-left'><input type='hidden' name='gdlx' value='"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',this.xggdLx)+'-'+DicAndEnumUtil.getEnumName('minWorkOrderTypeEnum',this.xggdZlx)+"'>"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',this.xggdLx)+'-'+DicAndEnumUtil.getEnumName('minWorkOrderTypeEnum',this.xggdZlx)+"</td>";  
			}
			htmlContent = htmlContent + "<td style='vertical-align:middle'><input type='hidden' name='zy' value="+this.zy+">"+this.zy+"</td>";  
			htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-left' title='"+StringUtil.escapeStr(this.zhut)+"'>"+StringUtil.escapeStr(this.zhut)+"</td>";   
			htmlContent = htmlContent + "</tr>";
			gdids.push(this.xggdid);
			glgdgknum++;
		});
		$("#glgdCardList").append(htmlContent);
	}
}
//回显航材耗材工具信息
function appendwoAirMaterial(obj){
	$("#CKlist").html("");
	xuhao = 1;
	if(obj!=null){
		var htmlContent = ""; 
		$(obj).each(function(){  
			htmlContent = htmlContent + "<tr style=\"cursor:pointer\" name='wohchc' bgcolor=\"#fefefe\"  id='tr1_"+this.bjid+"'>";
			htmlContent = htmlContent + "<td><a href=javascript:lineRemove2('"+this.bjid+"')><button class='line6'><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button></a></td>";  
			htmlContent = htmlContent + "<td style='vertical-align:middle' name='xuhao'>"+xuhao+"</td>";  
			htmlContent = htmlContent + "<td  title='"+StringUtil.escapeStr(this.refJhly)+"'><input  maxlength='100' name='refJhly_1' type='text' value='"+StringUtil.escapeStr(this.refJhly)+"' class='form-control'></td>";  
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'title='"+this.ywmc+"'><input type='hidden'  name='ywms_1' value='"+StringUtil.escapeStr(this.ywmc)+"'/>"+StringUtil.escapeStr(this.ywmc)+"</td>"; 
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+this.zwmc+"'><input type='hidden'  name='id_1' value='"+this.bjid+"'/><input type='hidden'  name='zwms_1' value='"+StringUtil.escapeStr(this.zwmc)+"'/>"+StringUtil.escapeStr(this.zwmc)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+this.jh+"' ><input type='hidden'  name='bjh_1' value='"+StringUtil.escapeStr(this.jh)+"'/>"+StringUtil.escapeStr(this.jh)+"</td>";  
			htmlContent = htmlContent + "<td class='text-right' style='vertical-align:middle'><input type='hidden'  name='kycksl_1' value='"+this.kykcsl+"'/>"+(this.kykcsl==null?"0":this.kykcsl)+"</td>";  
			
			htmlContent += "<td class='text-right' style='vertical-align:middle'>";
			var dxtdsl = formatUndefine(this.paramsMap.dxtdsl);
			if(dxtdsl == '' && this.paramsMap.tdjczs > 0){
				htmlContent += 0;
			}else{
				htmlContent += "<a href='javascript:void(0);' bjh='"+StringUtil.escapeStr(this.jh)+"' onclick=\"viewTdkc(this)\">"+dxtdsl+"</a>";
			}
			htmlContent += "</td>";
			
			htmlContent = htmlContent + "<td ><input onkeyup='clearNoNum(this)'  placeholder=''  maxlength='10'  name='sl_1' type='text' value='"+formatUndefine(this.sl)+"' class='form-control'></td>";  
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'><input type='hidden'  name='hclx_1' value='"+this.lx+"'/>"+DicAndEnumUtil.getEnumName('materialTypeEnum',this.lx)+"</td>";  
			htmlContent = htmlContent + "<td title='"+this.bz+"'><input name='bz_1' type='text'  maxlength='300' placeholder='最大长度不超过300' value='"+StringUtil.escapeStr(this.bz)+"' class='form-control'></td>";   
			htmlContent = htmlContent + "</tr>";
			hcids.push(this.bjid);
			xuhao++;
		});
		$("#CKlist").append(htmlContent);
	}
}
//回显附件的相关数据
/*function appendwoJobenclosure(obj){
	if(obj!=null){
		var htmlContent = ""; 
		$(obj).each(function(){ 
			var uuid=this.id;
			htmlContent = htmlContent+'<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+uuid+'\'>';
			htmlContent = htmlContent+'<td><input type="hidden" name="wbwjm" value=\''+this.wbwjm+'\'/><div>';
			htmlContent = htmlContent+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+uuid+ '\')" title="删除 Delete">  ';
			htmlContent = htmlContent+'</div></td>';
			htmlContent = htmlContent+'<td class="text-left"><input type="hidden" name="yswjm" value=\''+this.yswjm+'\'/><input type="hidden" name="nbwjm" value=\''+this.nbwjm+'\'/>'+this.wbwjm+'</td>';
			htmlContent = htmlContent+'<td class="text-left"><input type="hidden" name="cflj" value=\''+this.cflj+'\'/><input type="hidden" name="wjdx" value=\''+this.wjdx+'\'/>'+this.wjdxStr+' </td>';
			htmlContent = htmlContent+'<td class="text-left"><input type="hidden" name="hzm" value=\''+this.hzm+'\'/>'+this.czr_user.displayName+'</td>';
			htmlContent = htmlContent+'<td>'+this.czsj+'</td>';
			htmlContent = htmlContent+'</tr>';	
		 });
		 $('#filelist').append(htmlContent);
	}
}*/

