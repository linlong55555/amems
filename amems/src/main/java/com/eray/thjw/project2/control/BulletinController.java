package com.eray.thjw.project2.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.control.MaterialController;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.project2.po.Bulletin;
import com.eray.thjw.project2.po.DistributionDepartment;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.service.BulletinService;

import enu.ThresholdEnum;
import enu.project2.BulletinStatusEnum;
import enu.project2.CompnentTypeEnum;

@Controller
@RequestMapping("/project2/bulletin")
public class BulletinController extends BaseController {

	@Resource
	private BulletinService bulletinService;
	@Resource
	private MonitorsettingsService monitorsettingsService;

	private static final Logger log = LoggerFactory.getLogger(MaterialController.class);

	/**
	 * @Description 跳转至技术通告界面
	 * @CreateBy 岳彬彬
	 * @return 页面视图
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:bulletin:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(Bulletin record) throws BusinessException {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("compnentTypeEnum", CompnentTypeEnum.enumToListMap());
			model.put("bulletinStatusEnum", BulletinStatusEnum.enumToListMap());
			return new ModelAndView("project2/bulletin/bulletin_main", model);
		} catch (Exception e) {
			throw new BusinessException("跳转至维护提示界面失败!", e);
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 技术通告列表
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryBulletinList", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody Bulletin record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Map<String, Object> resultMap = bulletinService.selectBulletinList(record);
			// 获取监控值
			Monitorsettings monitorsettings = monitorsettingsService.getByKeyDprtcode(ThresholdEnum.TGZL.getName(),
					record.getDprtcode());
			resultMap.put("monitorsettings", monitorsettings);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("跳转维护提示页面失败!", e);
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 新增技术通告
	 * @param record
	 * @param request
	 * @return 技术通告id
	 * @throws Exception
	 */
	@Privilege(code = "project2:bulletin:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addRecord(@RequestBody Bulletin record, HttpServletRequest request) throws BusinessException {
		try {
			return bulletinService.insertSelective(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("新增维护提示失败!", e);
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 编辑技术通告
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public Bulletin editRecord(@RequestBody Bulletin record, HttpServletRequest request) throws BusinessException {
		try {
			Bulletin bulletin = bulletinService.getRecord(record.getId());
			if (bulletin.getZt() == BulletinStatusEnum.APPROVAL.getId() && record.getParamsMap() != null
					&& ("1".equals(record.getParamsMap().get("view")))) {
				view4IsCirculuation(record.getId());
			}
			return bulletin;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("获取维护提示失败!", e);
		}

	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 修改技术通告
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Privilege(code = "project2:bulletin:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateRecord(@RequestBody Bulletin record, HttpServletRequest request) throws BusinessException {
		try {
			return bulletinService.updateRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("修改维护提示失败!", e);
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 审核审批
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:bulletin:03,project2:bulletin:04")
	@ResponseBody
	@RequestMapping(value = "review", method = RequestMethod.POST)
	public String reiviewRecord(@RequestBody Bulletin record, HttpServletRequest request) throws BusinessException {
		try {
			return bulletinService.getReviewAndApproveRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("审核审批维护提示失败!", e);
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 作废
	 * @param record
	 * @param request
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:bulletin:07")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void deleteRecord(@RequestBody Bulletin record, HttpServletRequest request) throws BusinessException {
		try {
			bulletinService.deleteRecord(record);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("维护提示作废失败!", e);
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 查看
	 * @CreateTime 2017年8月12日 上午10:11:11
	 * @param id
	 * @param zt
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView Looked(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("compnentTypeEnum", CompnentTypeEnum.enumToListMap());
		model.put("id", id);
		return new ModelAndView("/project2/bulletin/bulletin_view", model);
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 跳转通告传阅
	 * @param annunciate
	 * @return
	 */
	@Privilege(code = "project2:bulletin:circulation:main")
	@RequestMapping(value = "circulation/main", method = RequestMethod.GET)
	public ModelAndView circulation(Bulletin record) throws BusinessException {
		try {
			return new ModelAndView("project2/circulation/circulation_main");
		} catch (Exception e) {
			throw new BusinessException("跳转至维护提示传阅页面失败!", e);
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 通告传阅列表
	 * @CreateTime 2017年8月12日 上午11:47:42
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryCirculationList", method = RequestMethod.POST)
	public Map<String, Object> queryCirculationList(@RequestBody Bulletin record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Map<String, Object> resultMap = bulletinService.getCirlationList(record);
			// 获取监控值
			Monitorsettings monitorsettings = monitorsettingsService.getByKeyDprtcode(ThresholdEnum.TGZL.getName(),
					record.getDprtcode());
			resultMap.put("monitorsettings", monitorsettings);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("跳转维护提示页面失败!", e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "viewMark", method = RequestMethod.POST)
	public List<DistributionDepartment> viewMark(@RequestBody Bulletin record, HttpServletRequest request, Model model)
			throws BusinessException {
		try {
			return bulletinService.getDistributionDepartment(record.getId());
		} catch (Exception e) {
			throw new BusinessException("查看维护提示传阅情况失败!", e);
		}
	}

	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017年8月22日 上午9:31:23
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param yj
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:bulletin:05")
	@ResponseBody
	@RequestMapping(value = "updateBatchAudit", method = RequestMethod.POST)
	public String updateBatchAudit(@RequestParam("idList[]") List<String> idList, String yj) throws BusinessException {

		try {
			return bulletinService.updateBatchAudit(idList, yj);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("批量审核失败", e);
		}
	}

	/**
	 * 
	 * @Description 批量审批
	 * @CreateTime 2017年8月22日 上午9:31:43
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param yj
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:bulletin:06")
	@ResponseBody
	@RequestMapping(value = "updateBatchApprove", method = RequestMethod.POST)
	public String updateBatchApprove(@RequestParam("idList[]") List<String> idList, String yj)
			throws BusinessException {
		try {
			return bulletinService.updateBatchApprove(idList, yj);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("批量批准失败", e);
		}
	}

	/**
	 * 
	 * @Description 导出excel
	 * @CreateTime 2017年8月22日 下午2:08:12
	 * @CreateBy 岳彬彬
	 * @param bulletin
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:bulletin:09")
	@RequestMapping(value = "bulletinExcel.xls")
	public String export(Bulletin bulletin, String tgqxBegin, String tgqxEnd, String jxArr, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			bulletin.setPagination(p);
			if(null != bulletin.getKeyword() && !"".equals(bulletin.getKeyword())){
				String keyword = bulletin.getKeyword();
				keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
				if(keyword.contains("'")){
					keyword=keyword.replace("'", "'|| chr(39) ||'");
				}
				bulletin.setKeyword(keyword);
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (tgqxBegin != null && !"".equals(tgqxBegin)) {
				bulletin.getParamsMap().put("tgqxBegin", df.parse(tgqxBegin));
			}
			if (tgqxEnd != null && !"".equals(tgqxEnd)) {
				bulletin.getParamsMap().put("tgqxEnd", df.parse(tgqxEnd));
			}
			if (jxArr != null && !"".equals(jxArr)) {
				List<String> jxlist = new ArrayList<String>();
				if (jxArr.indexOf(",") > 0) {
					String[] jx = jxArr.split(",");
					for (String string : jx) {
						jxlist.add(string);
					}
				} else {
					jxlist.add(jxArr);
				}
				bulletin.getParamsMap().put("jx", jxlist);
			}
			Map<String, Object> resultMap = bulletinService.selectBulletinList(bulletin);
			@SuppressWarnings("unchecked")
			List<Bulletin> list = (List<Bulletin>) resultMap.get("rows");
			for (Bulletin bulletin2 : list) {
				if (bulletin2.getIsList() != null && bulletin2.getIsList().size() > 0) {
					StringBuffer pgdhlist = new StringBuffer();
					for (Instructionsource is : bulletin2.getIsList()) {
						pgdhlist.append(is.getPgdh()).append(",");
					}
					bulletin2.getParamsMap().put("pgdhlist", pgdhlist.substring(0, pgdhlist.length() - 1));
				}
			}
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "bulletin", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
	/**
	 * 
	 * @Description 圈阅
	 * @CreateTime 2017年8月25日 上午11:31:55
	 * @CreateBy 岳彬彬
	 * @param id
	 */
	private void view4IsCirculuation(String id) {
		try {
			bulletinService.view4IsCirculuation(id);
		} catch (Exception e) {
			log.error("维护提示传阅失败!");
		}
	}
	
	/**
	 * @Description 查询历史版本
	 * @CreateTime 2018年2月23日 下午3:04:09
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "historylist",method={RequestMethod.POST,RequestMethod.GET})
	public List<Bulletin> queryHistoryList(String id) throws BusinessException {
		try {
			return bulletinService.queryHistoryList(id);
		} catch (Exception e) {
			throw new BusinessException("查询历史版本失败!",e);
		}
	}
	
	/**
	 * @Description 改版维护提示
	 * @CreateTime 2018年2月24日 上午10:31:57
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "revision", method = RequestMethod.POST)
	public String revision(@RequestBody Bulletin record) throws BusinessException {
		try {
			return bulletinService.doRevision(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("改版维护提示失败!", e);
		}
	}
}
