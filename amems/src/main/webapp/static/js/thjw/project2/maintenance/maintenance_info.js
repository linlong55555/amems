	
	var maintenanceInfo={
			
			//获取当前维修方案
			getWxfa : function(){
				var type = $("#pageType").val();
				if(type == 1){
					return maintenancePlan.wxfa;
				}else if(type == 2){
					return maintenancePlanAudit.wxfa;
				}else if(type == 3){
					return maintenancePlanApproval.wxfa;
				}else if(type == 4){
					return maintenancePlanEffect.wxfa;
				}else if(type == 5){
					return maintenancePlanView.wxfa;
				}
				return {};
			},
			
			// 加载基本信息
			load : function(){
				var this_ = this;
				var wxfa = this_.getWxfa();
				var wxfabb = wxfa.bb?("R"+wxfa.bb):"";
				$("[name='info_rev']").text(wxfabb);
				$("[name='info_rev_bracket']").text(wxfabb?"("+wxfabb+")":"");
				$("[name='info_wxfabh']").text(wxfa.wxfabh||"");
				$("#info_jx").text(wxfa.jx||"");
				$("#info_zwms").text(wxfa.zwms||"");
				$("#info_zt").text(DicAndEnumUtil.getEnumName('maintenanceSchemeStatusEnum',wxfa.zt));
				$("#info_jhSxrq").text((wxfa.jhSxrq||"").substr(0,10));
				$("#info_sjSxrq").text((wxfa.sjSxrq||"").substr(0,10));
				$("#info_zdr").text(wxfa.zdr ? wxfa.zdr.displayName : "");
				$("#info_zdsj").text((wxfa.zdsj||""));
				$("#info_shr").text(wxfa.shr ? wxfa.shr.displayName : "");
				$("#info_shrq").text((wxfa.shsj||""));
				$("#info_shyj").text(wxfa.shyj||"");
				$("#info_pzr").text(wxfa.pfr ? wxfa.pfr.displayName : "");
				$("#info_pzrq").text((wxfa.pfsj||""));
				$("#info_pzyj").text(wxfa.pfyj||"");
				$("#info_sxr").text(wxfa.sxr ? wxfa.sxr.displayName : "");
				$("#info_sxrq").text((wxfa.sxsj||""));
				$("#info_bz").text(wxfa.bz||"");
				$("#info_gbyj").text(wxfa.gbyj||"");
				var html = "";
				if(wxfa.technicalList){
					$.each(wxfa.technicalList, function(i, obj){
						html += '<a href="javascript:void(0);" class="pull-left" title="'+StringUtil.escapeStr(obj.pgdzt)+'" onclick="maintenanceItemList.viewAssessment(\''+obj.id+'\')">'+obj.pgdh+" R"+obj.bb+'</a>';
						html += '<br/>';
					});
				}
				$("#info_pgd").html(html||"");
			}
	};
	
