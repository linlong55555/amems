package com.eray.thjw.aerialmaterial.control;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.QualityReview;
import com.eray.thjw.aerialmaterial.po.QualityReviewRecord;
import com.eray.thjw.aerialmaterial.service.QualityReviewRecordService;
import com.eray.thjw.aerialmaterial.service.QualityReviewService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.ThresholdEnum;

/**
 * @author 
 * @description 自我质量检查
 * @develop 
 */
@Controller
@RequestMapping("/quality/selfquality")
public class QualityReviewController extends BaseController {
	@Autowired
	private QualityReviewService qualityReviewService;
	@Autowired
	private QualityReviewRecordService qualityReviewRecordService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private MonitorsettingsService monitorsettingsService;
	
	@Privilege(code="quality:selfquality:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
			model.put("lx", "1");
			return new ModelAndView("quality/selfquality/selfquality_main", model);
	}
	/**
	 * 获取列表
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getListPage", method = RequestMethod.POST)
	public Map<String, Object> recordList(@RequestBody QualityReview record,
			HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		record.setId(null);
		List<String> zrbmList=new ArrayList<String>();
		List<Department> departmentList = null;
		if (record.getParamsMap() != null && record.getParamsMap().get("lx").equals("2")) {
			departmentList = departmentService.getChildrentList(ThreadVarUtil.getUser().getBmdm());
			for (Department department : departmentList) {
				zrbmList.add(department.getId());
			}
			record.getParamsMap().put("list", zrbmList);
		}
		PageHelper.startPage(record.getPagination());
		List<QualityReview> recordList = null;
		if(record.getParamsMap() != null && record.getParamsMap().get("lx").equals("2")){
			if(null != departmentList && departmentList.size() > 0){
				recordList = qualityReviewService.selectQualityReviewList(record);
			}
		}else{
			recordList = qualityReviewService.selectQualityReviewList(record);
		}
		if (null != recordList && recordList.size()>0) {
			if (((Page) recordList).getTotal() > 0) {
				// 分页查询
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if (!PageUtil.hasRecord(recordList, id)) {// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						// 在查询条件中增加ID
						QualityReview record1 = new QualityReview();
						record1.setId(id);
						List<QualityReview> newRecordList = qualityReviewService.selectQualityReviewList(record1);
						if (newRecordList != null && newRecordList.size() == 1) {
							// 将记录放入结果集第一条
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
			} else {
				List<QualityReview> newRecordList = new ArrayList<QualityReview>(1);
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					QualityReview record2 = new QualityReview();
					record2.setId(id);
					newRecordList = qualityReviewService.selectQualityReviewList(record2);
					if (newRecordList != null && newRecordList.size() == 1) {
						return PageUtil.pack(1, newRecordList, record.getPagination());
					}
				}
				resultMap = PageUtil.pack(0, newRecordList, record.getPagination());
			}
		}
		resultMap.put("zrbmlist", zrbmList);
		Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.ZWZLSCQX.getName(), record.getDprtcode());
		resultMap.put("monitorsettings", monitorsettings);
		return resultMap;
	}
	/**
	 * 跳转到新增页面
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "quality:selfquality:main:01")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("quality/selfquality/selfquality_add", model);
		} catch (Exception e) {
			throw new BusinessException("跳转至自我审查新增页面失败!", e);
		}
	}
	/**
	 * 新增
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addRecord", method = RequestMethod.POST)
	public String addRecord(@RequestBody QualityReview record,
			HttpServletRequest request, Model model) throws Exception {
			String id = UUID.randomUUID().toString();
			record.setId(id);
			qualityReviewService.insertRecord(record);
		return record.getId();
		}
	
	@Privilege(code = "quality:selfquality:main:02,quality:selfquality:main:05,quality:selfquality:rectification:01")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(String id,HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("qualityReview", qualityReviewService.selectQualityReviewById(id));
			return new ModelAndView("quality/selfquality/selfquality_edit", model);
		} catch (Exception e) {
			throw new BusinessException("跳转至自我审查修改页面失败!", e);
		}
	}
	/**
	 * 修改
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Privilege(code = "quality:selfquality:main:04,quality:selfquality:main:03")
	@ResponseBody
	@RequestMapping(value = "updateRecord", method = RequestMethod.POST)
	public String updateRecord(@RequestBody QualityReview record,
			HttpServletRequest request, Model model) throws Exception {
			
			qualityReviewService.updateQualityReviewById(record);
		return record.getId();
		}
	/**
	 * 跳转反馈部门
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:selfquality:rectification")
	@RequestMapping(value = "rectification", method = RequestMethod.GET)
	public ModelAndView main1(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
			model.put("lx", "2");
			return new ModelAndView("quality/selfquality/selfquality_main", model);
	}
	
	@ResponseBody
	@RequestMapping(value = "getJlgc", method = RequestMethod.POST)
	public List<QualityReviewRecord> getJlgc(@RequestBody QualityReviewRecord qualityReciewRecord,
			HttpServletRequest request, Model model) throws Exception {
			
		return qualityReviewRecordService.selectRecordList(qualityReciewRecord.getMainid());
		}
	
	@RequestMapping(value = "/look", method = RequestMethod.GET)
	public ModelAndView look(String id,HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("qualityReview", qualityReviewService.selectQualityReviewById(id));
			return new ModelAndView("quality/selfquality/selfquality_view", model);
		} catch (Exception e) {
			throw new BusinessException("跳转至自我审查修改页面失败!", e);
		}
	}
}
