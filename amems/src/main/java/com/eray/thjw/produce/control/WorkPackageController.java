package com.eray.thjw.produce.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
import com.eray.thjw.po.Pagination;
import com.eray.thjw.produce.po.Workpackage;
import com.eray.thjw.produce.service.WorkpackageService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.Utils;

import enu.produce.WorkpackageStatusEnum;
import enu.project2.MonitorProjectEnum;

/**
 * 
 * @Description 工包控制器
 * @CreateTime 2017年9月12日 上午9:34:28
 * @CreateBy 岳彬彬
 */
@Controller
@RequestMapping("/produce/workpackage")
public class WorkPackageController extends BaseController {
	@Resource
	private WorkpackageService workpackageService;

	/**
	 * 
	 * @Description 工包视图
	 * @CreateTime 2017年9月12日 上午9:34:46
	 * @CreateBy 岳彬彬
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("workpackageStatusEnum", WorkpackageStatusEnum.enumToListMap());
			return new ModelAndView("produce/workpackage/135/workpackage_main", model);
		} catch (Exception e) {
			throw new BusinessException("工包管理列表跳转失败!", e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "getWorkpackageList", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody Workpackage record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			return workpackageService.getWorkpackageList(record);
		} catch (Exception e) {
			throw new BusinessException("跳转工包管理页面失败!", e);
		}
	}
	
	/**
	 * @Description 查询航材工具需求清单工包信息 
	 * @CreateTime 2018-4-12 下午3:05:01
	 * @CreateBy 刘兵
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryMToolDetailList", method = RequestMethod.POST)
	public Map<String, Object> queryMToolDetailList(@RequestBody Workpackage record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			return workpackageService.queryMToolDetailList(record);
		} catch (Exception e) {
			throw new BusinessException("跳转工包管理页面失败!", e);
		}
	}
	
	/**
	 * @Description 查询预组包工包信息
	 * @CreateTime 2018-4-12 上午11:32:14
	 * @CreateBy 刘兵
	 * @param record
	 * @param request
	 * @param model
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryBurstificationWPList", method = RequestMethod.POST)
	public Map<String, Object> queryBurstificationWPList(@RequestBody Workpackage record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			return workpackageService.queryBurstificationWPList(record);
		} catch (Exception e) {
			throw new BusinessException("跳转工包管理页面失败!", e);
		}
	}

	/**
	 * 
	 * @Description 查看
	 * @CreateTime 2017年9月12日 上午9:35:00
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView Looked(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		return new ModelAndView("produce/workpackage/135/workpackage_view", model);
	}

	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2017年9月27日 下午3:19:09
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addRecord(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {
			return workpackageService.addRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("新增工包失败!", e);
		}
	}

	/**
	 * 
	 * @Description 获取工包数据
	 * @CreateTime 2017年9月27日 下午3:18:57
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getRecord", method = RequestMethod.POST)
	public Workpackage getRecord(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {
			return workpackageService.getRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("获取工包失败!", e);
		}
	}

	/**
	 * 
	 * @Description 更新
	 * @CreateTime 2017年9月27日 下午3:18:46
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage:main:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateRecord(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {
			workpackageService.updateRecord(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("更新工包失败!", e);
		}
	}

	/**
	 * 
	 * @Description 删除工包
	 * @CreateTime 2017年9月27日 下午6:19:12
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage:main:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String deleteRecord(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {
			workpackageService.deleteRecord(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除工包失败!", e);
		}
	}

	/**
	 * 
	 * @Description 指定结束
	 * @CreateTime 2017年9月27日 下午6:19:36
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage:main:07")
	@ResponseBody
	@RequestMapping(value = "endClose", method = RequestMethod.POST)
	public String endCloseRecord(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {
			workpackageService.update4EndClose(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("指定结束工包失败!", e);
		}
	}

	/**
	 * 
	 * @Description 下发
	 * @CreateTime 2017年9月28日 上午10:40:11
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage:main:04")
	@ResponseBody
	@RequestMapping(value = "issued", method = RequestMethod.POST)
	public String IssuedRecord(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {
			workpackageService.update4Issued(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("下发工包失败!", e);
		}
	}

	/**
	 * 
	 * @Description 工包完工反馈
	 * @CreateTime 2017年9月28日 上午11:36:56
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage:main:05")
	@ResponseBody
	@RequestMapping(value = "feedback", method = RequestMethod.POST)
	public String feedbackRecord(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {
			workpackageService.update4Feedback(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("工包完工反馈失败!", e);
		}
	}

	/**
	 * 
	 * @Description 完工关闭
	 * @CreateTime 2017年9月28日 下午1:36:55
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage:main:06")
	@ResponseBody
	@RequestMapping(value = "close", method = RequestMethod.POST)
	public String closeRecord(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {
			workpackageService.update4Close(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("工包完工关闭失败!", e);
		}
	}

	/**
	 * 
	 * @Description 工包明细
	 * @CreateTime 2017年9月29日 下午2:21:37
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getWorpackageDetailList", method = RequestMethod.POST)
	public Map<String, Object> getWorpackageDetailList(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {
			return workpackageService.getWorkpackageDetail(record);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * @Description 飞行记录本查询工包数据
	 * @CreateTime 2017年10月17日 下午3:06:02
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "query4FLB", method = RequestMethod.POST)
	public List<Workpackage> query4FLB(@RequestBody Workpackage record) throws BusinessException {
		try {
			return workpackageService.query4FLB(record);
		} catch (Exception e) {
			throw new BusinessException("飞行记录本查询工包数据失败", e);
		}
	}
	
	/**
	 * @Description 根据条件查询工包信息
	 * @CreateTime 2017-10-19 下午2:38:30
	 * @CreateBy 刘兵
	 * @param record 工包
	 * @return List<Workpackage>工包集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryWorkpackageList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryWorkpackageList(@RequestBody Workpackage record) throws BusinessException {
		try {
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			List<Workpackage> wList = workpackageService.queryWorkpackageList(record);
			for (Workpackage workpackage : wList) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", workpackage.getId());
				resultMap.put("zt", workpackage.getZt());
				resultMap.put("gbbh", workpackage.getGbbh());
				resultMap.put("fjzch", workpackage.getFjzch());
				resultMap.put("gbmc", workpackage.getGbmc());
				resultMap.put("jhZxdw", workpackage.getJhZxdw());
				resultMap.put("wgbs", workpackage.getWgbs());
				resultList.add(resultMap);
			}
			return resultList;
		} catch (Exception e) {
			throw new BusinessException("查询工包数据失败", e);
		}
	}
	/**
	 * 
	 * @Description 验证工包下工单
	 * @CreateTime 2017年11月3日 下午2:49:08
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "query4Close", method = RequestMethod.POST)
	public List<Workpackage> query4Close(@RequestBody Workpackage record) throws BusinessException {
		try {
			return workpackageService.queryWorkpackageList(record);
		} catch (Exception e) {
			throw new BusinessException("查询工包数据失败", e);
		}
	}
	
	/**
	 * 
	 * @Description 修订工单
	 * @CreateTime 2017年11月27日 上午10:33:45
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doRevision", method = RequestMethod.POST)
	public String doRevision(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {
			workpackageService.update4Revision(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("修订失败!", e);
		}
	}
	/**
	 * 
	 * @Description 获取下发状态下的工包
	 * @CreateTime 2017年12月12日 下午4:51:21
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getIssuedWp", method = RequestMethod.POST)
	public List<Workpackage> getIssuedWp(@RequestBody Workpackage record, HttpServletRequest request) throws BusinessException {
		try {		
			return workpackageService.getIssuedWp(record);
		} catch (Exception e) {
			throw new BusinessException("获取工包失败!", e);
		}
	}
	
	/**
	 * 
	 * @Description 工包135导出
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage:main:11")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "WorkPackage135.xls")
	public String export(Workpackage record, String xfrqBegin,String xfrqEnd,String jhwcrqBegin,String jhwcrqEnd,HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			record.setPagination(p);
			record.getParamsMap().put("xfrqBegin", xfrqBegin);
			record.getParamsMap().put("xfrqEnd", xfrqEnd);
			record.getParamsMap().put("jhwcrqBegin", jhwcrqBegin);
			record.getParamsMap().put("jhwcrqEnd", jhwcrqEnd);
			Map<String, Object> resultMap = workpackageService.getWorkpackageList(record);
			List<Workpackage> list = (List<Workpackage>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "gb135", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
	/**
	 * 
	 * @Description 工包明细导出
	 * @CreateTime 2017年12月19日 下午5:06:14
	 * @CreateBy 岳彬彬
	 * @param paramjson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage:main:12")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "workpackageDetail.xls" ,method={RequestMethod.GET})
	public String exportExcel(String paramjson, HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			Workpackage workpackage = Utils.Json.toObject(paramjson, Workpackage.class);
			Workpackage wp = new Workpackage();
			wp.setId((String) workpackage.getParamsMap().get("gbid"));
			wp = workpackageService.getRecord(wp);
			Map<String, Object> resultMap = workpackageService.getWorkpackageDetail(workpackage);
			List<Map<String, Object>> workorderList = (List<Map<String, Object>>) resultMap.get("workorderList");
			for (Map<String, Object> map : workorderList) {
				StringBuffer sbf = new StringBuffer();
				if(null != map.get("JHSJSJ") && !"".equals(map.get("JHSJSJ"))){
					String limtStr = (String) map.get("JHSJSJ");
					String[] limitArr = limtStr.split(",");
					for (int i=0;null != limitArr && i< limitArr.length;i++){
						String jklbh = limitArr[i].split("#_#")[0];
						String val =  limitArr[i].split("#_#")[1];
						if(null != jklbh && MonitorProjectEnum.isTime(jklbh)){
							val = StringAndDate_Util.convertToHour(val);
						}
						if(null != jklbh && !"".equals(jklbh)){
							jklbh =MonitorProjectEnum.getUnit(jklbh);
						}
						sbf.append(val).append(jklbh).append(",");
					}
						map.put("jkl", sbf.substring(0, sbf.length()-1).toString());		
				}
			}
			wp.getParamsMap().put("workorderDetail", workorderList);
			List<Workpackage> list = new ArrayList<Workpackage>();
			list.add(wp);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "workpackageDetail", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
}
