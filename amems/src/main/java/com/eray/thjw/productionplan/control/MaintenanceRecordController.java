package com.eray.thjw.productionplan.control;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.productionplan.po.MaintenanceFailureSummary;
import com.eray.thjw.productionplan.service.MaintenanceFailureSummarySerivce;
import com.eray.thjw.service.PlaneBaseService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.VisibleRangeEnum;

/**
 * @author liub
 * @description 维修故障总结控制层
 * @develop date 2017.01.03
 */
@Controller("maintenanceRecordController1")
@RequestMapping("/production/maintenancerecord")
public class MaintenanceRecordController extends BaseController {
	
	/**
	 * @author liub
	 * @description 维修故障总结service
	 * @develop date 2017.01.04
	 */
	@Resource
	private MaintenanceFailureSummarySerivce summarySerivce;
	
	/**
	 * @author liub
	 * @description 基地service
	 * @develop date 2017.01.03
	 */
	@Resource
	private PlaneBaseService planeBaseService;
	
	/**
	 * @author liub
	 * @description 跳转至维修档案界面
	 * @param
	 * @return 页面视图
	 * @develop date 2017.01.03
	 *
	 */
	@Privilege(code="production:maintenancerecord:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) throws BusinessException {
		model.addAttribute("visibleRangeEnum", VisibleRangeEnum.enumToListMap());
		model.addAttribute("baseList", planeBaseService.findBaseByDprtcode(ThreadVarUtil.getUser().getJgdm()));
		return "productionplan/maintenancerecord/maintenanceRecord_main";
	}
	
	/**
	 * @author liub
	 * @description 增加维修故障总结
	 * @param summary
	 * @develop date 2017.01.04
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody MaintenanceFailureSummary summary) throws BusinessException{
		try {
			return summarySerivce.add(summary);
		} catch (Exception e) {
			 throw new BusinessException("保存维修故障总结失败！",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 修改维修故障总结
	 * @param summary
	 * @develop date 2017.01.04
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@RequestBody MaintenanceFailureSummary summary) throws BusinessException{
		try {
			getLogger().debug("-----------编辑我的档案开始---------");
			summarySerivce.edit(summary);
			getLogger().debug("-----------编辑我的档案结束---------");
			return summary.getId();
		} catch (Exception e) {
			 throw new BusinessException("修改维修故障总结失败！",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param request,id
	 * @return
	 * @develop date 2017.01.04
	 * @throws @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request,String id) throws BusinessException{
		try {
			summarySerivce.deleteSummary(id);
		} catch (Exception e) {
			throw new BusinessException("作废失败！",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据条件分页查询维修故障总结列表
	 * @param request,model,baseEntity
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2017.01.04
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody BaseEntity baseEntity)throws BusinessException {
		String id = String.valueOf(baseEntity.getParamsMap().get("id"));//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		baseEntity.getParamsMap().put("id", null);
		try {
			PageHelper.startPage(baseEntity.getPagination());
			List<Map<String, Object>> list = summarySerivce.queryAllPageList(baseEntity);
			if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
				//分页查询
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecordMap(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						BaseEntity newRecord = new BaseEntity();
						newRecord.getParamsMap().put("id", id);
						List<Map<String, Object>> newRecordList = summarySerivce.queryAllPageList(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
			}else{
				List<Map<String, Object>> newRecordList = new ArrayList<Map<String, Object>>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					BaseEntity newRecord = new BaseEntity();
					newRecord.getParamsMap().put("id", id);
					newRecordList = summarySerivce.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, baseEntity.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, baseEntity.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据航材id查询维修故障总结及对应详情信息
	 * @param id
	 * @return HCMainData
	 * @develop date 2016.12.14
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public MaintenanceFailureSummary selectById(Model model,String id) throws BusinessException {
		try {
			return summarySerivce.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询维修故障总结失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据条件分页查询维修故障总结列表(lucene)
	 * @param request,model,baseEntity
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2017.01.04
	 */
	@ResponseBody
	@RequestMapping(value = "queryLucenePageList", method = RequestMethod.POST)
	public Map<String, Object> queryLucenePageList(@RequestBody BaseEntity baseEntity,HttpServletRequest request,Model model) throws BusinessException{
		try {
			return summarySerivce.queryLucenePageList(baseEntity,SessionUtil.getDprtcodeList(request));
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
}
