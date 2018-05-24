package com.eray.thjw.control;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Maintenance;
import com.eray.thjw.service.MaintenanceService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LatestLogoTwoEnum;
import enu.SignificantCoefficientEnum;
import enu.common.EffectiveEnum;

/**
 * @author liub
 * @description 改版生效确认控制层
 * @develop date 2016.09.23
 */
@Controller
@RequestMapping("/project/confirmationofrevision")
public class ConfirmationOfRevisionController extends BaseController {
	
	/**
	 * @author liub
	 * @description 维修方案service
	 * @develop date 2016.08.15
	 */
	@Autowired
	private MaintenanceService maintenanceService;
	
	/**
	 * @author liub
	 * @description 跳转至维修方案改版生效确认界面
	 * @param model
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:confirmationofrevision:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model){
		model.addAttribute("latestLogoTwoEnum", LatestLogoTwoEnum.enumToListMap());
		return "project/maintenance/confirmationofrevision_main";
	}
	
	/**
	 * @author liub
	 * @description 查看详情
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.09.25
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id) throws BusinessException{
		getLogger().info("查看详情  前端参数:id:{}", new Object[]{id});
		try {
			model.addAttribute("maintenance", maintenanceService.getByPrimaryKey(id));
			model.addAttribute("significantCoefficientEnum", SignificantCoefficientEnum.enumToListMap());
			model.addAttribute("effectiveEnum", EffectiveEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("查看详情失败!",e);
		}
		return new ModelAndView("project/maintenance/confirmationofrevision_view");
	}

	/**
	 * @author liub
	 * @description 检查维修方案改版生效权限
	 * @param id
	 * @return 页面视图
	 * @develop date 2016.09.23
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkEffective", method = RequestMethod.POST)
	public void checkEffective(String id) throws BusinessException {
		getLogger().info("检查维修方案改版生效权限  前端参数:id:{}", new Object[]{id});
		try {
			maintenanceService.checkEffective(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("查询维修方案失败!",e);
		}	
	}
	
	
	/**
	 * @author liub
	 * @description 确认生效
	 * @param wxfaId,sxjyrid
	 * @develop date 2016.09.23
	 * @throws @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "sbEffective", method = RequestMethod.POST)
	public void sbEffective(String wxfaId,String sxjyrid) throws BusinessException{	
		getLogger().info("确认生效  前端参数:wxfaId:{},sxjyrid:{}", new Object[]{wxfaId,sxjyrid});
		try {
			maintenanceService.updateEffective(wxfaId,sxjyrid);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("生效维修方案失败!",e);
		}	
	}

	/**
	 * @author liub
	 * @description 查询待生效、生效、失效维修方案
	 * @param maintenance
	 * @return Map<String, Object>
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody Maintenance maintenance)throws BusinessException {
		String id = maintenance.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		maintenance.setId(null);
		PageHelper.startPage(maintenance.getPagination());
		List<Maintenance> list = maintenanceService.queryAllPageListEff(maintenance);
		try {
			if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
				//分页查询
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Maintenance newRecord = new Maintenance();
						newRecord.setId(id);
						List<Maintenance> newRecordList = maintenanceService.queryAllPageListEff(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, maintenance.getPagination());
			}else{
				List<Maintenance> newRecordList = new ArrayList<Maintenance>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Maintenance newRecord = new Maintenance();
					newRecord.setId(id);
					newRecordList = maintenanceService.queryAllPageListEff(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, maintenance.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, maintenance.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
		
}
