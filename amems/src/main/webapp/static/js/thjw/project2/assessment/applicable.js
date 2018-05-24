applicable_settings ={
	 id:'applicable_settings',
	 fjzch:[],
	 bjList:[],
	 sy:-1,//0不适用,1为适用,-1为默认值
	 param:{
		operationShow:true,//操作按钮
		dataList:null,//list数据
		sylb:null,//适用类别
		dprtcode:userJgdm,
	 },
	 init:function(param){
		 if(param){
				$.extend(this.param, param);
			}
		 if(this.param.operationShow){
			 $("#"+this.id+"_list").html("<tr id='sylb_modal_tr'><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
		 }else{
			 $("#"+this.id+"_list").html("<tr id='sylb_modal_tr'><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
		 }
		 this.fjzch=[];
		 this.bjList=[];
		 var rObj = document.getElementsByName('sylb_public');// 适用范围
			for (var i = 0; i < rObj.length; i++) {
				if (rObj[i].value == this.param.sylb) {
					rObj[i].checked = 'checked';
				}
			}
		 if(this.param.operationShow){
			 $("#"+this.id+"_operationTd").show();
		 }else{
			 $("#"+this.id+"_operationTd").hide();
		 }
		 if(this.param.dataList != null && this.param.dataList.length>0){
			 console.info(this.param.dataList);
			 this.appendHtml(this.param.dataList);
		 }
	 },
	 choosePN:function(){
		var this_=this;
		$('input[name="sylb_public"]').change(function(){
			$("#"+this_.id+"_list").html("<tr id='sylb_modal_tr'><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");	
		});
		this_.fjzch=[];
		this_.sy=-1;
		open_win_choosePnModal.show({
			parentid:"EOAddModal",
			fjjx:$("#Assessment_Open_Modal_jx").val(),
			dprtcode:this_.param.dprtcode,
			callback:function(data){
				this_.filtData(data);
			}
		});		
	},
	choosePlane:function(){
		var this_=this;
		this_.bjList=[];
		$('input[name="sylb_public"]').change(function(){
			$("#"+this_.id+"_list").html("<tr id='sylb_modal_tr'><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
			this_.fjzch=[];
			this_.sy=-1;
		});
		choosePlaneModal.show({
			parentid:"EOAddModal",
			fjjx:$("#Assessment_Open_Modal_jx").val(),
			existList:this_.fjzch,//回写已经选择的数据
			sy:this_.sy,
			dprtcode:this_.param.dprtcode,
			callback:function(data){
				this_.sy=data.sy;//适用
				var fjdata=[];
				$.each(data.fj,function(index,row){
					var fj={};
					fj.bh=row.fjzch;
					fj.xlh=row.xlh;
					fj.szwz=row.fjzch;
					fjdata.push(fj);
				});
				this_.appendHtml(fjdata);//data是个json格式，data.fj是选择的飞机列表，data.sy是全部适用默认1为勾选，0为不勾选
			}
		});
	},
	appendHtml:function(list){
		
		if(list !=null && list.length>0){
			$("input[name='sylb_public']").not(":checked").attr('disabled','disabled');
		}
		
		var this_=this;
		var htmlContent = "";
		$("#sylb_modal_tr").remove();
		var obj=$("input[name='sylb_public']:checked").val();//获取是选择的部件还是飞机的radio
		$.each(list,function(index,row){
			if(obj == 1){
				this_.fjzch.push(row.bh);				
			}else{
				var bj=row.bh+row.xlh;
				this_.bjList.push(bj);
			}
			htmlContent +="<tr id='contentTr' >" ;
			if(this_.param.operationShow){
				htmlContent +="<td class='text-center'><button class='line6 line6-btn' name='syxSettingMinus' onclick='"+this_.id+".del(this);' type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></td>";		
			}
			htmlContent += "<td name='bh' title='"+StringUtil.escapeStr(row.bh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bh)+"</td>"; 
		    htmlContent += "<td name='xlh' title='"+StringUtil.escapeStr(row.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";
		    htmlContent += "<td name='xh' title='"+StringUtil.escapeStr(row.xh||(row.paramsMap?row.paramsMap.xingh:'')||'')+"'  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xh||(row.paramsMap?row.paramsMap.xingh:'')||'')+"</td>";
		    /*htmlContent += "<td style='text-align:left;vertical-align:middle;'>已下发</td>";	*/
		    htmlContent += "<td name='szwz' style='text-align:left;vertical-align:middle;display:none'>"+row.szwz+"</td>";
		    htmlContent += "</tr>";	
		});
				
		$("#"+this_.id+"_list").append(htmlContent);
			
		if(htmlContent == ""){
			 $("#"+this_.id+"_list").html("<tr id='sylb_modal_tr'><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
		}
		
	},
	del: function(e){
		var this_=this;
		
		if(this.fjzch.length>0){
			var index = this.fjzch.indexOf($(e).parent().next().text());
			if (index > -1) {
				this.fjzch.splice(index, 1);
			}
		}
		if(this.bjList!=null && this.bjList.length>=0 ){
			var index = this.bjList.indexOf($(e).parent().next().text()+$(e).parent().next().next().text());
			if (index > -1) {
				this.bjList.splice(index, 1);
			}
		}
		$(e).parent().parent().remove();
		var table =document.getElementById(this.id+"_table");
		var rows = table.rows.length;
		if(rows == 1){
			$("input[name='sylb_public']").attr('disabled',false);
		    $("#"+this_.id+"_list").html("<tr id='sylb_modal_tr'><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
		}
	},
	filtData:function(list){
		var this_=this;
		var obj='';
		var bjList=this_.bjList;
		if(bjList ==null||bjList == []||bjList.length==0){
			this_.appendHtml(list);
		}else{
			var data=[];
			for (var i = 0; i < list.length; i++) {
				var bjxlh=list[i].bh+list[i].xlh;
				if($.inArray(bjxlh||"", bjList) > -1){				
				}else{
					data.push(list[i]);
				}
			}
			if(data.length>0){
				this_.appendHtml(data);
			}			
		}		
	},
	/**
	 * 获取实用性数据
	 */
	getData : function(){
		var this_ = this;
		var param = [];
		var len = $("#applicable_settings_list").find("tr[id='contentTr']").length;
		if (len > 0){
			var xc = 1;
			$("#applicable_settings_list").find("tr[id='contentTr']").each(function(){
				var tr_this = this;
				var infos ={};
				var index=$(this).index(); //当前行
				var bh =$("td[name='bh']").eq(index).html();
				var xlh =$("td[name='xlh']").eq(index).html();
				var xh =$("td[name='xh']").eq(index).html();
				var szwz =$("td[name='xlh']").eq(index).html();
				
				xc++;
				infos.bh = bh;
				infos.xlh = xlh;
				infos.szwz = szwz;
				infos.xc = xc;
				infos.kjbs = "1";//手工标识：0否、1是
				param.push(infos);
			});
		}
		return param;
	},
	/***
	 * 加载全部的飞机
	 */
	loadAllFj : function(){
		var this_ = this;
		
		if($("#qbsyInput").attr('checked')){ //全部适用加载所有的
			this_.clearData();
			var planData = Assessment_Open_Modal.loadAllDatas(this_.param.dprtcode);
			console.info(planData);
			var fjdata=[];
			$.each(planData,function(index,row){
				var fj={};
				fj.bh=row.fjzch;
				fj.xlh=row.xlh;
				fj.szwz=row.fjzch;
				fjdata.push(fj);
			});
			this_.appendHtml(fjdata);//data是个json格式，data.fj是选择的飞机列表，data.sy是全部适用默认1为勾选，0为不勾选
		}
		
		this_.showHidePlaneMinus();
	},
	showHidePlaneMinus : function(){
		if($("#qbsyInput").attr('checked')){ //全部适用不可删
			$("button[name='syxSettingMinus']").css("visibility", "hidden");
		}else{
			$("button[name='syxSettingMinus']").css("visibility", "visible");
		}
	},
	clearData : function(){
		var this_ = this;
		this_.fjzch = []; 
		this_.bjList = []; 
		$("#"+this_.id+"_list").html("<tr id='sylb_modal_tr'><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
	}
};