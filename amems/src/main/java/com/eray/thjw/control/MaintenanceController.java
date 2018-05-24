package com.eray.thjw.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @description 维修方案控制层
 * @develop date 2016.08.15
 */
@Controller
@RequestMapping("/project/maintenance")
public class MaintenanceController extends BaseController {
	
	/**
	 * @author liub
	 * @description 维修方案service
	 * @develop date 2016.08.15
	 */
	@Autowired
	private MaintenanceService maintenanceService;
	
	/**
	 * @author liub
	 * @description 跳转至维修方案界面
	 * @param model
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:maintenance:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model){
		model.addAttribute("latestLogoTwoEnum", LatestLogoTwoEnum.enumToListMap());
		return "project/maintenance/maintenance_main";
	}
	
	/**
	 * @author liub
	 * @description 查看详情
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.09.27
	 *
	 */
	@RequestMapping(value = "viewDetail", method = RequestMethod.GET)
	public ModelAndView viewDetail(Model model,String id) throws BusinessException{
		getLogger().info("查看详情  前端参数:id:{}", new Object[]{id});
		try {
			model.addAttribute("maintenance", maintenanceService.selectById(id));
		} catch (Exception e) {
			throw new BusinessException("查看详情失败!",e);
		}
		return new ModelAndView("project/maintenance/maintenance_viewDetail");
	}
	
	/**
	 * @author liub
	 * @description 查看差异
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.09.27
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id) throws BusinessException{
		getLogger().info("查看详情  前端参数:id:{}", new Object[]{id});
		try {
			Maintenance maintenance = maintenanceService.getByPrimaryKey(id);
			model.addAttribute("maintenance", maintenance);
			model.addAttribute("maintenanceOld", maintenanceService.getByPrimaryKey(maintenance.getfBbid()));
			model.addAttribute("significantCoefficientEnum", SignificantCoefficientEnum.enumToListMap());
			model.addAttribute("effectiveEnum", EffectiveEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("查看差异失败!",e);
		}
		return new ModelAndView("project/maintenance/maintenance_view");
	}

	/**
	 * @author liub
	 * @description 初始化增加维修方案
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:maintenance:main:01")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add() throws BusinessException {
		return new ModelAndView("project/maintenance/maintenance_add");
	}
	
	/**
	 * @author liub
	 * @description 增加维修方案
	 * @param maintenance
	 * @return String
	 * @develop date 2016.08.17
	 */
	@Privilege(code="project:maintenance:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(Maintenance maintenance) throws BusinessException{	
		try {
			return maintenanceService.saveMaintenance(maintenance);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存维修方案失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 检查维修方案修改权限
	 * @param id
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkEdit", method = RequestMethod.POST)
	public void checkEdit(String id) throws BusinessException {
		getLogger().info("检查维修方案修改权限 前端参数:id:{}", new Object[]{id});
		try {
			maintenanceService.checkUpdMt(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询维修方案失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化修改维修方案
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:maintenance:main:02")
	@RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id")String id,Model model) throws BusinessException {
		getLogger().info("初始化修改维修方案 前端参数:id:{}", new Object[]{id});
		try {
			model.addAttribute("maintenance", maintenanceService.selectById(id));
		} catch (Exception e) {
			throw new BusinessException("初始化修改维修方案失败!",e);
		}
		return "project/maintenance/maintenance_edit";
	}
	
	/**
	 * @author liub
	 * @description 修改维修方案
	 * @param maintenance
	 * @develop date 2016.08.17
	 */
	@Privilege(code="project:maintenance:main:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void edit(Maintenance maintenance) throws BusinessException{	
		getLogger().info("修改维修方案  前端参数:maintenance:{}", new Object[]{maintenance});
		try {
			maintenanceService.edit(maintenance);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("修改维修方案失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 检查维修方案改版权限
	 * @param id
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkModify", method = RequestMethod.POST)
	public void checkModify(String id) throws BusinessException {
		getLogger().info("检查维修方案改版权限 前端参数:id:{}", new Object[]{id});
		try {
			maintenanceService.checkMdfMt(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询维修方案失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化改版维修方案
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:maintenance:main:04")
	@RequestMapping(value = "{id}/modify", method = RequestMethod.GET)
	public String modify(Model model,@PathVariable("id")String id) throws BusinessException {
		getLogger().info("初始化改版维修方案 前端参数:id:{}", new Object[]{id});
		try {
			model.addAttribute("maintenance", maintenanceService.selectById(id));
		} catch (Exception e) {
			throw new BusinessException("初始化改版维修方案失败!",e);
		}
		return "project/maintenance/maintenance_modify";
	}
	
	/**
	 * @author liub
	 * @description 改版维修方案
	 * @param xdtzsidStr,maintenance
	 * @return String
	 * @develop date 2016.08.17
	 */
	@Privilege(code="project:maintenance:main:04")
	@ResponseBody
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modify(Maintenance maintenance,String xdtzsidStr) throws BusinessException{		
		getLogger().info("改版维修方案  前端参数:maintenance:{}", new Object[]{maintenance});	
		try {
			return maintenanceService.modifyMaintenance(maintenance,xdtzsidStr);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("改版维修方案失败!",e);
		}	
	}
	
	/**
	 * @author liub
	 * @description 检查维修方案提交生产权限
	 * @param id
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkSbProd", method = RequestMethod.POST)
	public void checkSbProd(String id) throws BusinessException {
		getLogger().info("检查维修方案提交生产权限 前端参数:id:{}", new Object[]{id});
		try {
			maintenanceService.checkSbProdMt(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询维修方案失败!",e);
		}	
	}
	
	/**
	 * @author liub
	 * @description 初始化提交生产
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.18
	 *
	 */
	@Privilege(code="project:maintenance:main:03")
	@RequestMapping(value = "{id}/sbProd", method = RequestMethod.GET)
	public String sbProd(Model model,@PathVariable("id")String id) throws BusinessException {
		getLogger().info("初始化提交生产:id:{}", new Object[]{id});
		try {
			model.addAttribute("maintenance", maintenanceService.selectById(id));
		} catch (Exception e) {
			throw new BusinessException("初始化提交生产失败!",e);
		}	
		return "project/maintenance/maintenance_sbProd";
	}
	
	/**
	 * @author liub
	 * @description 提交生产
	 * @param maintenance
	 * @develop date 2016.08.18
	 */
	@Privilege(code="project:maintenance:main:03")
	@ResponseBody
	@RequestMapping(value = "sbProd", method = RequestMethod.POST)
	public void sbProd(Maintenance maintenance) throws BusinessException{	
		getLogger().info("提交生产  前端参数:maintenance:{}", new Object[]{maintenance});
		try {
			maintenanceService.updateSbProdMaintenance(maintenance);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交生产失败!",e);
		}	
	}
	
	/**
	 * @author liub
	 * @description 检查维修方案生效状态
	 * @param wxfabh,bb,dprtcode
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkEffectiveState", method = RequestMethod.POST)
	public void checkEffectiveState(String wxfabh,String bb,String dprtcode) throws BusinessException {
		getLogger().info("检查维修方案生效状态 前端参数:wxfabh:{} bb:{} dprtcode:{}", new Object[]{wxfabh,bb,dprtcode});
		try {
			maintenanceService.checkEffectiveState(wxfabh,bb,dprtcode);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询维修方案失败!",e);
		}	
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 * @develop date 2016.08.18
	 */
	@Privilege(code="project:maintenance:main:05")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(String id) throws BusinessException{
		getLogger().info("作废操作  前端参数:id{}", new Object[]{id});	
		try {
			maintenanceService.deleteMaintenance(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("作废维修方案失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据维修方案编号、机构代码查询版本集合
	 * @param wxfabh,dprtcode
	 * @return List<BigDecimal>
	 * @develop date 2016.08.24
	 */
	@ResponseBody
	@RequestMapping(value = "queryBbListByWxfabh", method = RequestMethod.POST)
	public List<BigDecimal> queryBbListByWxfabh(String wxfabh,String dprtcode)throws BusinessException {
		try {
			return maintenanceService.queryBbListByWxfabh(wxfabh,dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败！",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据维修方案编号、机构代码查询机型
	 * @param wxfabh,dprtcode
	 * @return String
	 * @develop date 2016.11.22
	 */
	@ResponseBody
	@RequestMapping(value = "getFjjxByWxfabh", method = RequestMethod.POST)
	public String getFjjxByWxfabh(String wxfabh,String dprtcode)throws BusinessException {
		try {
			return maintenanceService.getFjjxByWxfabh(wxfabh,dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据条件查询维修方案信息
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
		try {
			PageHelper.startPage(maintenance.getPagination());
			List<Maintenance> list = maintenanceService.queryAllPageList(maintenance);
			if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
				//分页查询
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Maintenance newRecord = new Maintenance();
						newRecord.setId(id);
						List<Maintenance> newRecordList = maintenanceService.queryAllPageList(newRecord);
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
					newRecordList = maintenanceService.queryAllPageList(newRecord);
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
	
	/**
	 * @author liub
	 * @description 根据角色拥有的机构代码查询维修方案信息,去重
	 * @return List<Map<String, Object>>
	 * @develop date 2017.04.06
	 */
	@ResponseBody
	@RequestMapping(value = "selectByDprtcodeList", method = RequestMethod.POST)
	public List<Map<String, Object>> selectByDprtcodeList()throws BusinessException {
		try {
			return maintenanceService.selectByDprtcodeList();
		} catch (Exception e) {
			throw new BusinessException("查询维修方案失败!",e);
		}
	}
	
	/**
	 * @author sunji
	 * @description 根据机型查询维修方案
	 * @param maintenance
	 * @return Map<String, Object>
	 * @develop date 2016.10.10
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageDistincList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageDistincList(@RequestBody Maintenance maintenance)throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("rows", maintenanceService.selectMaintenanceByJx(maintenance));
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
		return resultMap;
	}
	
		
}
