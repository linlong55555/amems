/**
 * 选择评估单
 */
open_win_choosePnModal = {
	id:'open_win_choosePnModal',
	paginationId:'open_win_choosePnModal_pagination',
	partList:{},//部件数据
	chooseData:{},//右选择数据
	backData:{},//返回数据
	loaded: false,//是否已加载
	data:[],
	param: {
		parentid:null,//第一层模态框id
		dprtcode:userJgdm,//默认登录人当前机构代码
		fjjx:null,//机型
		callback:function(){}//回调函数
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		this.partList={}//部件数据
		this.chooseData={};//右选择数据
		this.backData={};//返回数据
		$("#"+this.id+"_leftpagination").empty();
		$("#"+this.id+"_leftlist").html("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
		$("#"+this.id+"_rightlist").html("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
		$("#"+this.id+"_downlist").html("<tr id='open_win_choosePnModal_downlist_firsttr'><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initParam();
		AlertUtil.hideModalFooterMessage();
	},
	//初始化参数
	initParam: function(){
		$("#"+this.id+"_keyword_search").val('');
	},
	reset:function(){
		this.initParam();
		$("#"+this.id+"_leftpagination").empty();
		$("#"+this.id+"_leftlist").html("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
		$("#"+this.id+"_rightlist").html("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
	},
	//加载数据
	load : function(pageNumber, sortColumn, orderType){
		var obj ={};
		obj=this.getParams();
		obj.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:5};
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url: basePath+"/material/material/selectPartList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				if(data.total >0){
					this_.data = data.rows;
					this_.appendContentHtml(data.rows);
					new Pagination({
						renderTo : this_.id+"_leftpagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[1,2,3], "#"+this_.id+"_leftlist tr td");
				} else {
					$("#"+this_.id+"_leftlist").empty();
					$("#"+this_.id+"_leftpagination").empty();
					$("#"+this_.id+"_leftlist").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
				}
				finishWait();
		    }
		}); 
	},	
	getParams : function(){		
		 var params = {};
		 var keyword = $.trim($("#"+this.id+"_keyword_search").val());
		 params.dprtcode = this.param.dprtcode;		
		 if('' != keyword){
			 params.keyword = keyword;
		 }
		 return params;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			this_.partList[row.id] = row;
			htmlContent += "<tr id='"+row.id+"' onClick=open_win_choosePnModal.choose('"+ row.id +"','"+row.bjh+ "') >"
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>"; 
		    htmlContent += "<td title='"+StringUtil.escapeStr(row.xingh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xingh)+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(row.zwms)+" "+StringUtil.escapeStr(row.ywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zwms)+" "+StringUtil.escapeStr(row.ywms)+"</td>"; 
		   	htmlContent += "</tr>";
	   });
	   $("#"+this_.id+"_leftlist").html(htmlContent);
	   if(list.length>0){
		   this_.choose(list[0].id,list[0].bjh);
		   $("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
	   }
	},
	//搜索
	search: function(){
		this.load();
	},
	choose :function(id,bjh){
		$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
		$("#"+id).find("td").addClass('bg_tr');
		var leftHeight=$("#leftPNTable").outerHeight();
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url: basePath+"/project2/eo/getBj",
			type: "post",
			dataType:"json",
			data:{bjid:id,
				  dprtcode:this.param.dprtcode,
				  fjjx:this.param.fjjx,
				  bjh:bjh,
			},
			success:function(data){
				if(data.loadingList.length >0 || data.stockList.length >0 || data.outStockList.length >0){
					this_.appendHtml(data,id);	
					$("#rightPNTable").css({"max-height":leftHeight+"px","overflow":"auto"});
				} else {
					$("#"+this_.id+"_rightlist").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_rightlist").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
				}
				finishWait();
		    }
		}); 
	},
	appendHtml: function(data,id){
		var htmlContent = '';
		var this_ = this;
		if(data.loadingList.length>0){
			$.each(data.loadingList,function(index,row){
				var paramsMap={}
				paramsMap.wz=1;
				paramsMap.bjid=id;
				row.paramsMap=paramsMap;				
				this_.chooseData[row.id]=row;
				htmlContent += "<tr onclick='"+this_.id+".rowonclick(\""+ row.id+ "\");' >";
				if(this_.backData[row.id]==null){
					htmlContent += "<td class='text-center'><input type='checkbox' id='"+row.id+"' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_rightlist')\" ></td>";
				}else{
					htmlContent += "<td class='text-center'><input type='checkbox' checked='checked' id='"+row.id+"' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_rightlist')\" ></td>";
				}
				htmlContent += "<td title='"+StringUtil.escapeStr(row.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>"; 
			    htmlContent += "<td title='"+StringUtil.escapeStr(row.fjzch)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.fjzch)+"</td>";
			   	htmlContent += "</tr>";
		   });
		}
		if(data.stockList.length>0){
			$.each(data.stockList,function(index,row){
				var paramsMap={}
				paramsMap.wz=2;
				paramsMap.bjid=id;
				row.paramsMap=paramsMap;			
				this_.chooseData[row.id]=row;
				htmlContent += "<tr onclick='"+this_.id+".rowonclick(\""+ row.id+ "\");' >";
				if(this_.backData[row.id]==null ){
					htmlContent += "<td class='text-center'><input type='checkbox' id='"+row.id+"' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_rightlist')\" ></td>";
				}else{
					htmlContent += "<td class='text-center'><input type='checkbox' checked='checked' id='"+row.id+"' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_rightlist')\" ></td>";
				}
				htmlContent += "<td title='"+StringUtil.escapeStr(row.sn)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='在库"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.kwh)+"' >在库 "+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.kwh)+"</td>";
			   	htmlContent += "</tr>";
		   });
		}
		if(data.outStockList.length>0){
			$.each(data.outStockList,function(index,row){
				var paramsMap={}
				paramsMap.wz=3;
				paramsMap.bjid=id;
				row.paramsMap=paramsMap;			
				this_.chooseData[row.id]=row;
				htmlContent += "<tr onclick='"+this_.id+".rowonclick(\""+ row.id+ "\");' >";
				if(this_.backData[row.id]==null ){
					htmlContent += "<td class='text-center'><input type='checkbox' id='"+row.id+"' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_rightlist')\" ></td>";
				}else{
					htmlContent += "<td class='text-center'><input type='checkbox' checked='checked' id='"+row.id+"' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_rightlist')\" ></td>";
				} 
				htmlContent += "<td title='"+StringUtil.escapeStr(row.sn)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;'>外场 </td>";
			   	htmlContent += "</tr>";
		   });
		}
	   $("#"+this_.id+"_rightlist").html(htmlContent);
	},
	rowonclick: function(id){		
			if($("#"+id).attr("checked")){
				$("#"+id).attr("checked",false);
				this.del(id);
			}else{
				$("#"+id).attr("checked",true);
				this.appenddownhtml(this.chooseData[id]);
			}
			
	},
	save: function(){		
		if(this.param.callback && typeof(this.param.callback) == "function"){		
				var this_ = this;
				var data = [];				
				for(key in this_.backData){
					data.push(this_.backData[key])
				}
				if(data == null ||data==undefined || data.length == 0){
					AlertUtil.showModalFooterMessage("请选择部件!",this_.id);
					return false;
				}
				this_.param.callback(data);
				$("#"+this_.id).modal("hide");
		}
	},
	appenddownhtml: function(data){
		var this_=this;
		var map={};
		var bh='';//编号
		var zwms='';
		var ywms='';
		var xh='';//型号
		var xlh='';//序列号
		var fjzch='';
		var ckh='';
		var kwh='';
		var szwz='';
		var htmlContent='';
		htmlContent += "<tr id='"+data.id+"1"+"'>";
		htmlContent += '<td class="text-center"><div>';
		htmlContent +=  "<i class='icon-trash color-blue cursor-pointer'  onclick='"+this_.id+".del(\""+ data.id+ "\");' title='删除 Delete'>  ";
		htmlContent += "</div></td>";
		if(data.paramsMap.wz==1){
			jh=data.bjh;
			zwms=data.zwmc;
			ywms=data.ywmc;
			xh=data.hcMainData?data.hcMainData.xingh:"";
			xlh=data.xlh;
			fjzch=data.fjzch;
			szwz=fjzch;
			htmlContent += "<td title='"+StringUtil.escapeStr(data.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.bjh)+"</td>"; 
			htmlContent += "<td title='"+StringUtil.escapeStr(data.hcMainData?data.hcMainData.zwms:"")+" "+StringUtil.escapeStr(data.hcMainData?data.hcMainData.ywms:"")+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(data.hcMainData?data.hcMainData.zwms:"")+" "+StringUtil.escapeStr(data.hcMainData?data.hcMainData.ywms:"")+"</td>"; 
			htmlContent += "<td title='"+StringUtil.escapeStr(data.hcMainData?data.hcMainData.xingh:"")+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(data.hcMainData?data.hcMainData.xingh:"")+"</td>"; 
			htmlContent += "<td title='"+StringUtil.escapeStr(data.xlh)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(data.xlh)+"</td>"; 
			htmlContent += "<td title='"+StringUtil.escapeStr(data.fjzch)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.fjzch)+"</td>"; 
		}else if(data.paramsMap.wz==2){
			jh=data.bjh;
			zwms=data.zwms;
			ywms=data.ywms;
			xh=data.xh
			xlh=data.sn;
			ckh=data.ckh
			kwh=data.kwh;
			szwz="在库 "+ckh+" "+kwh;
			htmlContent += "<td  style='text-align:center;vertical-align:middle;'>"+data.bjh+"</td>"; 
			htmlContent += "<td title='"+StringUtil.escapeStr(data.zwms)+" "+StringUtil.escapeStr(data.ywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.zwms)+" "+StringUtil.escapeStr(data.ywms)+"</td>"; 
			htmlContent += "<td title='"+StringUtil.escapeStr(data.xh)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(data.xh)+"</td>"; 
			htmlContent += "<td title='"+StringUtil.escapeStr(data.sn)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(data.sn)+"</td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='在库"+StringUtil.escapeStr(data.ckh)+" "+StringUtil.escapeStr(data.kwh)+"' >在库 "+StringUtil.escapeStr(data.ckh)+" "+StringUtil.escapeStr(data.kwh)+"</td>";
		}else {
			jh=data.bjh;
			zwms=data.zwms;
			ywms=data.ywms;
			xh=data.xh
			xlh=data.sn;
			ckh=data.ckh
			kwh=data.kwh;
			wz='外场';
			htmlContent += "<td  style='text-align:center;vertical-align:middle;'>"+data.bjh+"</td>"; 
			htmlContent += "<td title='"+StringUtil.escapeStr(data.zwms)+" "+StringUtil.escapeStr(data.ywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.zwms)+" "+StringUtil.escapeStr(data.ywms)+"</td>"; 
			htmlContent += "<td title='"+StringUtil.escapeStr(data.xh)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(data.xh)+"</td>"; 
			htmlContent += "<td title='"+StringUtil.escapeStr(data.sn)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(data.sn)+"</td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>外场 </td>";
		}
		htmlContent += "</tr>";
		$("#"+this.id+"_downlist_firsttr").remove();
		$("#"+this.id+"_downlist").append(htmlContent);		
	   	map.bh=jh;
		map.zwms=zwms;
		map.ywms=ywms;
		map.xh=xh;
		map.xlh=xlh;
		map.fjzch=fjzch;
		map.szwz=szwz;
		map.ckh=ckh;
		map.kwh=kwh;
		map.bjid=data.paramsMap.bjid;
		this_.backData[data.id]=map;
	},
	del:function(id){
		$("#"+this.id+"_downlist #"+id+"1").remove();
		$("#"+this.id+"_rightlist #"+id).attr("checked",false);
		delete(this.backData[id]);
	}
};