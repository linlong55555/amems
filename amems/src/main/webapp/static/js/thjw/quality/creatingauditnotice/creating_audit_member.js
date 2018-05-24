
memberUtil = {
		member : {},
		getMemberComponents : function(id){
			if(id == null || id == ''){
				id = 'member';
			}
			if(this.member[id] != null && this.member[id] != ''){
				return this.member[id];
			}
			var memberComponents = {
					id:id,
					tbodyId:'dept_info_list',
					param: {
						dragdropWidth:150
					},
					show : function(param){
						
					},
					initRemoveTr : function(){
						var this_ = this;
						$(".removeTr").unbind("click").bind("click",function(){
							var obj = $(this);
							this_.removeTr(obj,"members_td");
						});
					},
					initChangLeader : function(){
						var this_ = this;
						$(".changLeader").unbind("click").bind("click",function(){
							var obj = $(this);
							this_.changLeader(obj);
						});
					},
					initAddMembers : function(){
						var this_ = this;
						$(".addMembers").unbind("click").bind("click",function(){
							this_.addMembers('members_td','dept_info_list',null);
						});
					},
					shcyInit : function(){
						var this_ = this;
						var str = "";
						str +='<tr id="headman_tr">';
						str +='<td style="text-align: center;vertical-align:middle;">组长</td>';
						str +='<td><div class="input-group" style="width:100%"><span class="input-group-btn">';
						str +='<button class="line6 changLeader" title="提升组长 Promote" style="visibility:hidden;height:25px;line-height:25px;padding-left:5px;padding-right:5px;vertical-align:middle;margin-right:5px;" >';
						str +='<i class="fa fa-arrow-up color-blue cursor-pointer"></i>';
						str +='</button></span>';
						str +='<span class="input-group-btn">';
						str +='<button class="line6 removeTr" title="删除 Delete" style="visibility:hidden;height:25px;line-height:25px;padding-left:5px;padding-right:5px;vertical-align:middle;margin-right:5px;" >';
						str +='<i class="fa fa-minus color-blue cursor-pointer"></i>';
						str +='</button></span>';
						str +='<input type="text" name="displayName" class="form-control readonly-style"   ondblclick="openUserWin(this,false,true)" readonly/>';
						str +='<input type="hidden" name="userId" class="form-control readonly-style"    />';
						str +='<input type="hidden" name="userName" class="form-control readonly-style" />';
						str +='<input type="hidden" name="realName" class="form-control readonly-style" />';
						str +='<span class="input-group-btn">';
						str +='<button type="button" class="btn btn-default" onclick="openUserWin(this,true,true)" data-toggle="modal" >';
						str +='<i class="icon-search cursor-pointer"></i>';
						str +='</button></span>';
						str +='</div>';
						str +='</td>';
						str +='<td >';
						str +='<div name="departmentName"></div>';
						str +='<input name="dprtName" type="hidden"  class="form-control"  />';
						str +='<input name="dprtId" type="hidden"  class="form-control"  />';
						str +='<input name="dprtBh" type="hidden"  class="form-control"  />';
						str +='</td>';
						str +='<td>';
						str +='<input type="text" name="zw" class="form-control"  maxlength="100"  />';
						str +='</td>';
						str +='</tr>';
						str +='<tr>';
						str +='<td rowspan="1"  style="text-align: center;vertical-align:middle;" id="members_td">成员';
						str +="&nbsp;<button class='line6 addMembers' style='padding:0px 6px;'>";
						str +='<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>';
						str +=' </button> </td><td colspan="3"></td></tr>';
					   
						$("#dept_info_list",$("#"+this_.id)).empty();
						$("#dept_info_list",$("#"+this_.id)).append(str);
						
						this_.initAddMembers();
						this_.initChangLeader();
						this_.initRemoveTr();
					},
					shcyJz : function(ywid){
						var this_ = this;
						if(ywid != null && ywid != ""){
							AjaxUtil.ajax({
								async: false,
								url:basePath+"/quality/auditMembers/selectByYwid",
								type:"post",
								data:{
									'ywid' : ywid
								},
								dataType:"json",
								success:function(data){
									if(data != null && data.rows.length > 0 ){
										$.each(data.rows,function(index,row){
											if(row.js == 1){
												$("#headman_tr",$("#"+this_.id)).find("input[name='displayName']").val(StringUtil.escapeStr(row.cybh) +" "+ StringUtil.escapeStr(row.cymc));
												$("#headman_tr",$("#"+this_.id)).find("input[name='userId']").val(StringUtil.escapeStr(row.cyid));
												$("#headman_tr",$("#"+this_.id)).find("input[name='userName']").val(StringUtil.escapeStr(row.cybh));
												$("#headman_tr",$("#"+this_.id)).find("input[name='realName']").val(StringUtil.escapeStr(row.cymc));
												$("#headman_tr",$("#"+this_.id)).find("input[name='dprtId']").val(StringUtil.escapeStr(row.bmid));
												$("#headman_tr",$("#"+this_.id)).find("input[name='dprtBh']").val(StringUtil.escapeStr(row.bmbh));
												$("#headman_tr",$("#"+this_.id)).find("input[name='dprtName']").val(StringUtil.escapeStr(row.bmmc));
												$("#headman_tr",$("#"+this_.id)).find("input[name='zw']").val(StringUtil.escapeStr(row.zw));
												$("#headman_tr",$("#"+this_.id)).find("div[name='departmentName']").html(StringUtil.escapeStr(row.bmmc));
											}else{
												this_.addMembers('members_td','dept_info_list',row);
											}
										});
										
										
									}
								}
							});
						}
					},
					changLeader : function(obj){
						var this_ = this;
						var tr_str=$(obj).parents("td").parent("tr");
						var index=tr_str.index();
						var tr_first='';
						
						if(index==1){
							tr_first=tr_str.find("td").eq(0).clone();
							tr_str.find("td").eq(0).remove();
							
						}
						//文本框中的内容
						var col_first=tr_str.find("td").eq(0).find("input").val();
						var col_second=tr_str.find("td").eq(1).find("input").val();
						var col_third=tr_str.find("td").eq(2).find("input").val();
						var content=tr_str.html();
						
						$("#headman_tr",$("#"+this_.id)).find("td").eq(0).remove();
						$("#headman_tr",$("#"+this_.id)).find("td").eq(0).find("button").css("visibility","visible");
						var change_content=$("#headman_tr",$("#"+this_.id)).html();
						var change_first=$("#headman_tr",$("#"+this_.id)).find("td").eq(0).find("input").val();
						var change_second=$("#headman_tr",$("#"+this_.id)).find("td").eq(1).find("input").val();
						var change_third=$("#headman_tr",$("#"+this_.id)).find("td").eq(2).find("input").val();
						$("#headman_tr",$("#"+this_.id)).remove();
						tr_str.remove();
						var headman_tr  ='<tr id="headman_tr">';
							headman_tr +='<td style="text-align: center;vertical-align:middle;">组长</td>';
							headman_tr +=content;
							headman_tr +='</tr>';
						    $("#dept_info_list",$("#"+this_.id)).prepend(headman_tr);
							$("#headman_tr",$("#"+this_.id)).find("td").eq(1).find("button").css("visibility","hidden");
							$("#headman_tr",$("#"+this_.id)).find("td").eq(1).find("button").eq(2).css("visibility","visible");
							$("#headman_tr",$("#"+this_.id)).find("td").eq(1).find("input").eq(0).val(col_first);
							$("#headman_tr",$("#"+this_.id)).find("td").eq(3).find("input").val(col_third);
						var change_tr  ='<tr>';
							change_tr +=tr_first;
							change_tr +=change_content;
							change_tr +='</tr>';
							$(change_tr).insertAfter($("#dept_info_list",$("#"+this_.id)).find("tr").eq(index-1));
							$tr=$("#dept_info_list",$("#"+this_.id)).find("tr").eq(index);
							if(index==1){
							$("#dept_info_list",$("#"+this_.id)).find("tr").eq(index).prepend(tr_first);
							$tr.find("td").eq(1).find("input").eq(0).val(change_first);
							/*$tr.find("td").eq(2).find("input").val(change_second);*/
							$tr.find("td").eq(3).find("input").eq(0).val(change_third);
							/*$("#dept_info_list").find("tr").eq(index).find("td").eq(1).find("button").eq(1).css("visibility","hidden")*/
							}else{
								$tr.find("td").eq(0).find("input").eq(0).val(change_first);
								/*$tr.find("td").eq(1).find("input").val(change_second);*/
								$tr.find("td").eq(2).find("input").eq(0).val(change_third);
							}
							
							this_.initChangLeader();
							this_.initRemoveTr();
					},
					addMembers : function(id,tbodyId,obj){
						var this_ = this;
						var rowspan=parseInt($("#"+id,$("#"+this_.id)).attr("rowspan"));
						var trInfo='';
						trInfo +='<tr>';
						if(rowspan=='undefind' || isNaN(rowspan)){
							$("#"+id,$("#"+this_.id)).parent("tr").remove();
							trInfo +="<td  style='text-align: center;vertical-align:middle;' rowspan='1' id='members_td'>成员";
							trInfo +="&nbsp;<button class='line6 addMembers'  style='padding:0px 6px;'>";
							trInfo +="<i class='icon-plus cursor-pointer color-blue cursor-pointer'></i>";
							trInfo +="</button>";
							trInfo +="</td>";
						}else{
							$("#"+id,$("#"+this_.id)).attr("rowspan",rowspan+1);
						
							
						}
						trInfo+='<td >';
						trInfo+='<div class="input-group" style="width:100%">' ;
							
						trInfo+='<span class="input-group-btn">';
						trInfo+='<button class="line6 changLeader" title="提升组长 Promote" style="height:25px;line-height:25px;padding-left:5px;padding-right:5px;vertical-align:middle;margin-right:5px;" >';
						trInfo+='<i class="fa fa-arrow-up color-blue cursor-pointer"></i>';
						trInfo+='</button>';
						trInfo+='</span>';
						trInfo+='<span class="input-group-btn">';
						trInfo+='<button class="line6 removeTr" title="删除 Delete" style="height:25px;line-height:25px;padding-left:5px;padding-right:5px;vertical-align:middle;margin-right:5px;">';
						trInfo+='<i class="fa fa-minus color-blue cursor-pointer"></i>';
						trInfo+='</button>';
						trInfo+='</span>';
						if(obj != null){
							var str = StringUtil.escapeStr(obj.cybh)+"　"+StringUtil.escapeStr(obj.cymc);
							trInfo+="<input type='text' name='displayName' ondblclick='openUserWin(this,false,true)' class='form-control readonly-style'  readonly   value="+StringUtil.escapeStr(str)+"  />";
							trInfo+="<input type='hidden' name='userId' class='form-control readonly-style'  value="+StringUtil.escapeStr(obj.cyid)+" >";
							trInfo+="<input type='hidden' name='userName' class='form-control readonly-style' value="+StringUtil.escapeStr(obj.cybh)+" >";
							trInfo+="<input type='hidden' name='realName' class='form-control readonly-style' value="+StringUtil.escapeStr(obj.cymc)+">";
						}else{
							trInfo+='<input type="text" name="displayName" ondblclick="openUserWin(this,false,true)" class="form-control readonly-style"  readonly/>';
							trInfo+="<input type='hidden' name='userId' class='form-control readonly-style' >";
							trInfo+="<input type='hidden' name='userName' class='form-control readonly-style' >";
							trInfo+="<input type='hidden' name='realName' class='form-control readonly-style' >";
						}
						trInfo+='<span class="input-group-btn">';
						trInfo+='<button type="button" class="btn btn-default" onclick="openUserWin(this,true,true)" >';
						trInfo+='<i class="icon-search cursor-pointer"></i>';
						trInfo+='</button>';
						trInfo+='</span>';
						trInfo+='</div>';
						trInfo+='</td>';
						trInfo+='<td>';
						if(obj != null){
							trInfo+="<div name='departmentName'>"+StringUtil.escapeStr(obj.bmmc)+"</div>";
							trInfo+="<input type='hidden' name='dprtName' class='form-control readonly-style' value="+StringUtil.escapeStr(obj.bmmc)+" >";
							trInfo+="<input type='hidden' name='dprtId' class='form-control readonly-style' value="+StringUtil.escapeStr(obj.bmid)+" >";
							trInfo+="<input type='hidden' name='dprtBh' class='form-control readonly-style' value="+StringUtil.escapeStr(obj.bmbh)+" >";
							
						}else{
							trInfo+="<div name='departmentName'></div>";
							trInfo+="<input type='hidden' name='dprtName' class='form-control readonly-style'  >";
							trInfo+="<input type='hidden' name='dprtId' class='form-control readonly-style'  >";
							trInfo+="<input type='hidden' name='dprtBh' class='form-control readonly-style' >";
						}
						trInfo+='</td>';
						trInfo+="<td>";
						if(obj != null){
							trInfo+="<input type='text' name='zw' class='form-control'  maxlength='100' value="+StringUtil.escapeStr(obj.zw)+" >";
						}else{
							trInfo+="<input type='text' name='zw' class='form-control'  maxlength='100'>";
						}
						trInfo+='</td></tr>';
					   $("#"+tbodyId,$("#"+this_.id)).append(trInfo);
					   
						this_.initChangLeader();
						this_.initRemoveTr();
					},
					removeTr : function(obj,id){
						var this_ = this;
						var rowspan=parseInt($("#"+id,$("#"+this_.id)).attr("rowspan"));
						if(rowspan==1){
							$(obj).parents("td").parent("tr").remove();
							var notData="<tr><td  style='text-align: center;vertical-align:middle;' id='members_td'>成员";
							notData +="&nbsp;<button class='line6 addMembers' style='padding:0px 6px;'>";
							notData +="<i class='icon-plus cursor-pointer color-blue cursor-pointer'></i>";
							notData +="</button>";
							notData +="</td><td colspan='3' class='text-center'>暂无数据</td></tr>";
							$(notData).insertAfter($("#headman_tr",$("#"+this_.id)));
						}else{
							var index=$(obj).parents("td").parent("tr").index();
							$(obj).parents("td").parent("tr").remove();
							var memeberInfo='';
							if(index==1){
								memeberInfo +="<td  style='text-align: center;vertical-align:middle;' rowspan='1' id='members_td'>成员";
								memeberInfo +="&nbsp;<button class='line6 addMembers' style='padding:0px 6px;'>";
								memeberInfo +="<i class='icon-plus cursor-pointer color-blue cursor-pointer'></i>";
								memeberInfo +="</button>";
								memeberInfo +="</td>";
							$("#dept_info_list",$("#"+this_.id)).find("tr").eq(1).prepend(memeberInfo);		
							}
							$("#"+id,$("#"+this_.id)).attr("rowspan",rowspan-1);
						}
					}
					
					
				}
			this.member[id] = memberComponents;
			return memberComponents;
		}
}









function shcyInit(){
	var str = "";
	str +='<tr id="headman_tr">';
	str +='<td style="text-align: center;vertical-align:middle;">组长</td>';
	str +='<td><div class="input-group" style="width:100%"><span class="input-group-btn">';
	str +='<button class="line6" title="提升组长 Promote" style="visibility:hidden;height:25px;line-height:25px;padding-left:5px;padding-right:5px;vertical-align:middle;margin-right:5px;" onclick=changLeader(this) >';
	str +='<i class="fa fa-arrow-up color-blue cursor-pointer"></i>';
	str +='</button></span>';
	str +='<span class="input-group-btn">';
	str +='<button class="line6" title="删除 Delete" style="visibility:hidden;height:25px;line-height:25px;padding-left:5px;padding-right:5px;vertical-align:middle;margin-right:5px;" onclick=removeTr(this,"members_td")>';
	str +='<i class="fa fa-minus color-blue cursor-pointer"></i>';
	str +='</button></span>';
	str +='<input type="text" name="displayName" class="form-control readonly-style"   ondblclick="openUserWin(this,false,true)" readonly/>';
	str +='<input type="hidden" name="userId" class="form-control readonly-style"    />';
	str +='<input type="hidden" name="userName" class="form-control readonly-style" />';
	str +='<input type="hidden" name="realName" class="form-control readonly-style" />';
	str +='<span class="input-group-btn">';
	str +='<button type="button" class="btn btn-default" onclick="openUserWin(this,true,true)" data-toggle="modal" >';
	str +='<i class="icon-search cursor-pointer"></i>';
	str +='</button></span>';
	str +='</div>';
	str +='</td>';
	str +='<td >';
	str +='<div name="departmentName"></div>';
	str +='<input name="dprtName" type="hidden"  class="form-control"  />';
	str +='<input name="dprtId" type="hidden"  class="form-control"  />';
	str +='<input name="dprtBh" type="hidden"  class="form-control"  />';
	str +='</td>';
	str +='<td>';
	str +='<input type="text" name="zw" class="form-control"  maxlength="100"  />';
	str +='</td>';
	str +='</tr>';
	str +='<tr>';
	str +='<td rowspan="1"  style="text-align: center;vertical-align:middle;" id="members_td">成员';
	str +="&nbsp;<button class='line6' onclick=memberComponents.addMembers('members_td','dept_info_list',null) style='padding:0px 6px;'>";
	str +='<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>';
	str +=' </button> </td><td colspan="3"></td></tr>';
   
	$("#dept_info_list").empty();
	$("#dept_info_list").append(str);
}

//加载数据
function shcyJz(ywid){
	if(ywid != null && ywid != ""){
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/quality/auditMembers/selectByYwid",
			type:"post",
			data:{
				'ywid' : ywid
			},
			dataType:"json",
			success:function(data){
				if(data != null && data.rows.length > 0 ){
					$.each(data.rows,function(index,row){
						if(row.js == 1){
							$("#headman_tr").find("input[name='displayName']").val(StringUtil.escapeStr(row.cybh) +" "+ StringUtil.escapeStr(row.cymc));
							$("#headman_tr").find("input[name='userId']").val(StringUtil.escapeStr(row.id));
							$("#headman_tr").find("input[name='userName']").val(StringUtil.escapeStr(row.cybh));
							$("#headman_tr").find("input[name='realName']").val(StringUtil.escapeStr(row.cymc));
							$("#headman_tr").find("input[name='dprtId']").val(StringUtil.escapeStr(row.bmid));
							$("#headman_tr").find("input[name='dprtBh']").val(StringUtil.escapeStr(row.bmbh));
							$("#headman_tr").find("input[name='dprtName']").val(StringUtil.escapeStr(row.bmmc));
							$("#headman_tr").find("input[name='zw']").val(StringUtil.escapeStr(row.zw));
							$("#headman_tr").find("div[name='departmentName']").html(StringUtil.escapeStr(row.bmmc));
						}else{
							addMembers('members_td','dept_info_list',row);
						}
					});
					
					
				}
			}
		});
	}
}

//成员晋升组长的方法
function changLeader(obj){
	var tr_str=$(obj).parents("td").parent("tr");
	var index=tr_str.index();
	var tr_first='';
	
	if(index==1){
		tr_first=tr_str.find("td").eq(0).clone();
		tr_str.find("td").eq(0).remove();
		
	}
	//文本框中的内容
	var col_first=tr_str.find("td").eq(0).find("input").val();
	var col_second=tr_str.find("td").eq(1).find("input").val();
	var col_third=tr_str.find("td").eq(2).find("input").val();
	var content=tr_str.html();
	
	$("#headman_tr").find("td").eq(0).remove();
	$("#headman_tr").find("td").eq(0).find("button").css("visibility","visible");
	var change_content=$("#headman_tr").html();
	var change_first=$("#headman_tr").find("td").eq(0).find("input").val();
	var change_second=$("#headman_tr").find("td").eq(1).find("input").val();
	var change_third=$("#headman_tr").find("td").eq(2).find("input").val();
	$("#headman_tr").remove();
	tr_str.remove();
	var headman_tr  ='<tr id="headman_tr">';
		headman_tr +='<td style="text-align: center;vertical-align:middle;">组长</td>';
		headman_tr +=content;
		headman_tr +='</tr>';
	    $("#dept_info_list").prepend(headman_tr);
		$("#headman_tr").find("td").eq(1).find("button").css("visibility","hidden");
		$("#headman_tr").find("td").eq(1).find("button").eq(2).css("visibility","visible");
		$("#headman_tr").find("td").eq(1).find("input").eq(0).val(col_first);
		$("#headman_tr").find("td").eq(3).find("input").val(col_third);
	var change_tr  ='<tr>';
		change_tr +=tr_first;
		change_tr +=change_content;
		change_tr +='</tr>';
		$(change_tr).insertAfter($("#dept_info_list").find("tr").eq(index-1));
		$tr=$("#dept_info_list").find("tr").eq(index);
		if(index==1){
		$("#dept_info_list").find("tr").eq(index).prepend(tr_first);
		$tr.find("td").eq(1).find("input").eq(0).val(change_first);
		/*$tr.find("td").eq(2).find("input").val(change_second);*/
		$tr.find("td").eq(3).find("input").eq(0).val(change_third);
		/*$("#dept_info_list").find("tr").eq(index).find("td").eq(1).find("button").eq(1).css("visibility","hidden")*/
		}else{
			$tr.find("td").eq(0).find("input").eq(0).val(change_first);
			/*$tr.find("td").eq(1).find("input").val(change_second);*/
			$tr.find("td").eq(2).find("input").eq(0).val(change_third);
		}
}

//添加
function addMembers(id,tbodyId,obj){
	var rowspan=parseInt($("#"+id).attr("rowspan"));
	var trInfo='';
	trInfo +='<tr>';
	if(rowspan=='undefind' || isNaN(rowspan)){
		$("#"+id).parent("tr").remove();
		trInfo +="<td  style='text-align: center;vertical-align:middle;' rowspan='1' id='members_td'>成员";
		trInfo +="&nbsp;<button class='line6' onclick=addMembers('members_td','dept_info_list',null) style='padding:0px 6px;'>";
		trInfo +="<i class='icon-plus cursor-pointer color-blue cursor-pointer'></i>";
		trInfo +="</button>";
		trInfo +="</td>";
	}else{
		$("#"+id).attr("rowspan",rowspan+1);
	
		
	}
	trInfo+='<td >';
	trInfo+='<div class="input-group" style="width:100%">' ;
		
	trInfo+='<span class="input-group-btn">';
	trInfo+='<button class="line6" title="提升组长 Promote" style="height:25px;line-height:25px;padding-left:5px;padding-right:5px;vertical-align:middle;margin-right:5px;" onclick=changLeader(this)>';
	trInfo+='<i class="fa fa-arrow-up color-blue cursor-pointer"></i>';
	trInfo+='</button>';
	trInfo+='</span>';
	trInfo+='<span class="input-group-btn">';
	trInfo+='<button class="line6" title="删除 Delete" style="height:25px;line-height:25px;padding-left:5px;padding-right:5px;vertical-align:middle;margin-right:5px;" onclick=removeTr(this,"members_td")>';
	trInfo+='<i class="fa fa-minus color-blue cursor-pointer"></i>';
	trInfo+='</button>';
	trInfo+='</span>';
	if(obj != null){
		var str = StringUtil.escapeStr(obj.cybh)+"　"+StringUtil.escapeStr(obj.cymc);
		trInfo+="<input type='text' name='displayName' ondblclick='openUserWin(this,false,true)' class='form-control readonly-style'  readonly   value="+StringUtil.escapeStr(str)+"  />";
		trInfo+="<input type='hidden' name='userId' class='form-control readonly-style'  value="+StringUtil.escapeStr(obj.cyid)+" >";
		trInfo+="<input type='hidden' name='userName' class='form-control readonly-style' value="+StringUtil.escapeStr(obj.cybh)+" >";
		trInfo+="<input type='hidden' name='realName' class='form-control readonly-style' value="+StringUtil.escapeStr(obj.cymc)+">";
	}else{
		trInfo+='<input type="text" name="displayName" ondblclick="openUserWin(this,false,true)" class="form-control readonly-style"  readonly/>';
		trInfo+="<input type='hidden' name='userId' class='form-control readonly-style' >";
		trInfo+="<input type='hidden' name='userName' class='form-control readonly-style' >";
		trInfo+="<input type='hidden' name='realName' class='form-control readonly-style' >";
	}
	trInfo+='<span class="input-group-btn">';
	trInfo+='<button type="button" class="btn btn-default" onclick="openUserWin(this,true,true)" >';
	trInfo+='<i class="icon-search cursor-pointer"></i>';
	trInfo+='</button>';
	trInfo+='</span>';
	trInfo+='</div>';
	trInfo+='</td>';
	trInfo+='<td>';
	if(obj != null){
		trInfo+="<div name='departmentName'>"+StringUtil.escapeStr(obj.bmmc)+"</div>";
		trInfo+="<input type='hidden' name='dprtName' class='form-control readonly-style' value="+StringUtil.escapeStr(obj.bmmc)+" >";
		trInfo+="<input type='hidden' name='dprtId' class='form-control readonly-style' value="+StringUtil.escapeStr(obj.bmid)+" >";
		trInfo+="<input type='hidden' name='dprtBh' class='form-control readonly-style' value="+StringUtil.escapeStr(obj.bmbh)+" >";
		
	}else{
		trInfo+="<div name='departmentName'></div>";
		trInfo+="<input type='hidden' name='dprtName' class='form-control readonly-style'  >";
		trInfo+="<input type='hidden' name='dprtId' class='form-control readonly-style'  >";
		trInfo+="<input type='hidden' name='dprtBh' class='form-control readonly-style' >";
	}
	trInfo+='</td>';
	trInfo+="<td>";
	if(obj != null){
		trInfo+="<input type='text' name='zw' class='form-control'  maxlength='100' value="+StringUtil.escapeStr(obj.zw)+" >";
	}else{
		trInfo+="<input type='text' name='zw' class='form-control'  maxlength='100'>";
	}
	trInfo+='</td></tr>';
   $("#"+tbodyId).append(trInfo);
	
}
//删除
function removeTr(obj,id){
	var rowspan=parseInt($("#"+id).attr("rowspan"));
	if(rowspan==1){
		$(obj).parents("td").parent("tr").remove();
		var notData="<tr><td  style='text-align: center;vertical-align:middle;' id='members_td'>成员";
		notData +="&nbsp;<button class='line6' onclick=addMembers('members_td','dept_info_list',null) style='padding:0px 6px;'>";
		notData +="<i class='icon-plus cursor-pointer color-blue cursor-pointer'></i>";
		notData +="</button>";
		notData +="</td><td colspan='3' class='text-center'>暂无数据</td></tr>";
		$(notData).insertAfter($("#headman_tr"));
	}else{
		var index=$(obj).parents("td").parent("tr").index();
		$(obj).parents("td").parent("tr").remove();
		var memeberInfo='';
		if(index==1){
			memeberInfo +="<td  style='text-align: center;vertical-align:middle;' rowspan='1' id='members_td'>成员";
			memeberInfo +="&nbsp;<button class='line6' onclick=addMembers('members_td','dept_info_list',null) style='padding:0px 6px;'>";
			memeberInfo +="<i class='icon-plus cursor-pointer color-blue cursor-pointer'></i>";
			memeberInfo +="</button>";
			memeberInfo +="</td>";
		$("#dept_info_list").find("tr").eq(1).prepend(memeberInfo);		
		}
		$("#"+id).attr("rowspan",rowspan-1);
	}
	
}
