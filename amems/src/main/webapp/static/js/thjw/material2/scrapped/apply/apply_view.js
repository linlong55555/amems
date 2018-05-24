$(document).ready(function(){
	apply_open.init({
		id:$("#bulletinId").val(),
	});
	})
apply_open = {
	id:"apply_open",
	data:[],
	param: {
		obj : {},
		fjzch:null,
		dprtcode:userJgdm,
		id:'',
		operation:true,
		view:false,
		modalName:'',
		modalEname:'',
		modalShow:true,
		callback:function(){}
	},
	init:function(param){
		$('.date-picker').datepicker({
			autoclose : true,
			clearBtn : false
		});
		$("#"+this.id+"_modal input[type='text']").val("");
		$("#"+this.id+"_modal input[type='hidden']").val("");
		$("#"+this.id+"_modal textarea").val("");
		this.param.obj = {};
		this.param.id="";
		if(param){
			$.extend(this.param, param);
		}
		this.loadData();
		$("#"+this.id+"_name").html(this.param.modalName);
		$("#"+this.id+"_ename").html(this.param.modalEname);
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this.id+'_attachments_list_edit');
		attachmentsObj.show({
			djid:this.param.id,
			fileHead : false,
			colOptionhead : false,
			fileType:"apply",
		});//显示附件
		if(this.param.id !=''){
			this.setData(this.param.obj);
		}else{
			TimeUtil.getCurrentDate("#"+this.id+"_bfrq");
			$("#"+this.id+"_sqrid").val(currentUser.id);
			$("#"+this.id+"_sqr").val(currentUser.username+" "+currentUser.realname);
			$("#"+this.id+"_bfdh").attr("disabled",false);
			var str = "#apply_open_modal #processRecord_list";
			$(str).empty();
			$(str).append("<tr class='text-center'><td colspan=\"3\">暂无数据 No data.</td></tr>");
		}
		$("#apply_open_modal").on("hidden.bs.modal", function() {
			$("#apply_open_form").data('bootstrapValidator').destroy();
			$('#apply_open_form').data('bootstrapValidator', null);
			apply_open.validation();
		});
	},
	loadData:function(){
		var param={};
		var this_ = this;
		param.id=this_.param.id;
		AjaxUtil.ajax({
			url : basePath + "/material/scrapped/apply/getRecord",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async : false,
			success : function(data) {
				if(data != null){
					this_.param.obj = data;
				}			
			}
		});
	},
	openUser : function(){
		var this_ = this;
		var userList = [];
		var a = $("#apply_open_shrid").val();
		var b = $("#apply_open_shr").val();
		for (var i = 0; i < a.split(",").length; i++) {
			var p = {
				id : a.split(",")[i],
				displayName : b.split(",")[i]
			};
			userList.push(p);
		}
		if (a == "") {
			userList = "";
		}
		Personel_Tree_Multi_Modal.show({
			checkUserList:userList,//原值，在弹窗中回显
			dprtcode:this_.param.dprtcode,//组织编码
			multi:false, //单选
			callback: function(data){//回调函数
				var displayName = '';
				var mjsrid = '';
				if(data != null && data != ""){
					$.each(data, function(i, row){
						displayName += formatUndefine(row.displayName) + ",";
						mjsrid += formatUndefine(row.id) + ",";
					});
				}
				if(displayName != ''){
					displayName = displayName.substring(0,displayName.length - 1);
				}
				if(mjsrid != ''){
					mjsrid = mjsrid.substring(0,mjsrid.length - 1);
				}
				$("#apply_open_shr").val(displayName);
				$("#apply_open_shrid").val(mjsrid);
			}
		});
	},
	setData:function(data){
		var this_ = this;
		$("#apply_open_bfdh").attr("disabled","disabled");
		$("#apply_open_bfdh").val(data.bfdh);
		$("#apply_open_bfrq").val(data.bfrq?data.bfrq.substring(0,10):"");
		$("#apply_open_sm").val(data.sm);
		$("#apply_open_sqrid").val(data.sqrid);
		$("#apply_open_sqr").val(data.sqr?(data.sqr.username+" "+data.sqr.realname):"");
		$("#apply_open_shrid").val(data.sprid);
		$("#apply_open_shr").val(data.shr?(data.shr.username+" "+data.shr.realname):"");
		$("#apply_open_zt").val(DicAndEnumUtil.getEnumName('applyStatusEnum', data.zt));
		measures_record.show({//流程信息
			id:this.param.id,
			dprtcode:userJgdm,
		});
		this_.load();
	},
	load : function(){
		var this_= this;
		var obj = {};
		obj.id = this_.param.id;
		AjaxUtil.ajax({
			url : basePath + "/material/scrapped/apply/getScrappedDetailList",
			type : "post",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : JSON.stringify(obj),
			success : function(data) {
				finishWait();
				if (data.total > 0) {
					this_.editStock(data.rows);
				} else {
					$("#"+this_.id+"_detail").append("<tr class='text-center'><td colspan=\"9\">暂无数据 No data.</td></tr>");
				}
			}
		});
	
	},
	editStock: function(list){
		var this_=this;
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent += "<tr name='"+row.KCID+"' id='"+row.KCID+"'>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX)+"' >"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.BJH)+"\n"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.BJH)+"</p><p  class='margin-bottom-0 '> "+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"</p></td>";//部件名称
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.PCH)+"\n"+StringUtil.escapeStr(row.SN)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.PCH)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.SN)+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.GG)+"\n"+StringUtil.escapeStr(row.XH)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.GG)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.XH)+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(formatUndefine(row.CKH))+" "+StringUtil.escapeStr(formatUndefine(row.CKMC))+"\n"+StringUtil.escapeStr(formatUndefine(row.KWH))+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(formatUndefine(row.CKH))+" "+StringUtil.escapeStr(formatUndefine(row.CKMC))+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(formatUndefine(row.KWH))+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.BFSL)+StringUtil.escapeStr(row.JLDW)+"'>"+StringUtil.escapeStr(row.BFSL)+StringUtil.escapeStr(row.JLDW)+"</span></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.CQBH)+"'>"+formatUndefine(row.CQBH)+"</td>";//产权 		
			var zcb = '';
			var zjz = '';
			if(formatUndefine(row.ZCB) != ''){
				zcb = formatUndefine(row.ZCB).toFixed(2)+" "+formatUndefine(row.BIZ);
			}
			if(formatUndefine(row.ZJZ) != ''){
				zjz = formatUndefine(row.ZJZ).toFixed(2)+" "+formatUndefine(row.JZBZ);
			}
		 
		    htmlContent += "<td style='text-align:left;vertical-align:middle;' name='kcjz' title='"+formatUndefine(zcb)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(zcb)+"</p></td>"; 
		    if(formatUndefine(row.HJSM) =='' && formatUndefine(row.SYTS)==''){
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"' >"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"</td>";
			}else{
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"\n"+formatUndefine(row.HJSM).substring(0,10)+"/"+formatUndefine(row.SYTS)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"</p><p  class='margin-bottom-0 '>"+formatUndefine(row.HJSM).substring(0,10)+"/"+formatUndefine(row.SYTS)+"</p></td>";
			}
			htmlContent += "</tr>";  
		    
		});
		$("#apply_open_tr").remove();
		$("#"+this_.id+"_detail").append(htmlContent);
	}
}
function getDataById(id){
	var this_ = this;
	var param={};
	param.id=id;
	AjaxUtil.ajax({
		url : basePath + "/material/scrapped/apply/getRecord",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data != null){
				if(typeof(obj)=="function"){
					obj(data); 
				}
			}			
		}
	});
}
