package com.eray.thjw.training.control;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.training.service.PlanPersonService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * @author 林龙
 * @description 维修人员培训记录
 */
@Controller
@RequestMapping("/training/record")
public class RecordController extends BaseController {
	
	@Resource
	private PlanPersonService planPersonService;
	
	/**
	 * 跳转至维修人员培训记录管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="training:record:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/training/record/record_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 维修人员培训记录列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "planPersonList", method = RequestMethod.POST)
	public Map<String, Object> planPersonList(@RequestBody PlanPerson planPerson,HttpServletRequest request,Model model) throws BusinessException{
		String id = planPerson.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		planPerson.setId(null);
		PageHelper.startPage(planPerson.getPagination());
		List<PlanPerson> list = planPersonService.queryAllPageplanPersonList(planPerson);
		if(((Page)list).getTotal() > 0){
			
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					PlanPerson planPerson1 = new PlanPerson();
					planPerson1.setId(id);
					List<PlanPerson> newRecordList = planPersonService.queryAllPageplanPersonList(planPerson1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, planPerson.getPagination());
		}else{
			List<PlanPerson> newRecordList = new ArrayList<PlanPerson>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				PlanPerson planPerson1 = new PlanPerson();
				planPerson1.setId(id);
				newRecordList = planPersonService.queryAllPageplanPersonList(planPerson1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, planPerson.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, planPerson.getPagination());
		}
	}
	
	/**
	 * @author ll
	 * @description 新增
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="training:record:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(@RequestBody PlanPerson planPerson) throws BusinessException{
		try {
			planPersonService.save(planPerson);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	
	/**
	 * @author ll
	 * @description 
	 * @param request,mainid
	 * @develop date 2016.10.13
	 */
	@ResponseBody
	@RequestMapping(value = "selectByPrimaryKey", method = RequestMethod.POST)
	public PlanPerson queryDetailListByMainId(String id,HttpServletRequest request)throws BusinessException {
		PlanPerson planPerson = new PlanPerson();
		try {
			planPerson = planPersonService.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询数据失败");
		}
		return planPerson;
	}
	
	/**
	 * @author ll
	 * @description 修改
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="training:record:main:02")
	@ResponseBody
	@RequestMapping(value = "editrecords", method = RequestMethod.POST)
	public void editrecords(@RequestBody PlanPerson planPerson) throws BusinessException{
		try {
			planPersonService.update(planPerson);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 作废培训记录
	 * @param request,id
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="training:record:main:03")
	@ResponseBody
	@RequestMapping(value = "deleterecords", method = RequestMethod.POST)
	public void deleterecords(@RequestBody PlanPerson planPerson) throws BusinessException{
		try {
			planPersonService.delete(planPerson);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017年12月6日 下午3:12:24
	 * @CreateBy 岳彬彬
	 * @param planPerson
	 * @param ry
	 * @param kc
	 * @param pxDate
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="training:record:main:04")
	@RequestMapping(value = "personnelTrainingRecords.xls")
	public String export(PlanPerson planPerson,String ry, String kc, String pxDate, HttpServletRequest request,Model model) throws BusinessException {
		try {
			planPerson.getParamsMap().put("kc", kc);
			planPerson.getParamsMap().put("ry", ry);
			if(null != pxDate && !"".equals(pxDate)){
				planPerson.getParamsMap().put("pxDateBegin", pxDate.substring(0, 10).concat(" 00:00:00"));
				planPerson.getParamsMap().put("pxDateEnd", pxDate.substring(13, 23).concat(" 23:59:59"));		
			}
			if(null != planPerson.getKeyword() && !"".equals(planPerson.getKeyword())){
				String keyword = planPerson.getKeyword();
				keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
				if(keyword.contains("'")){
					keyword=keyword.replace("'", "'|| chr(39) ||'");
				}
				planPerson.setKeyword(keyword);
			}
			List<PlanPerson> list = planPersonService.queryAllPageplanPersonList(planPerson);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "personnelTrainingRecords", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
}
