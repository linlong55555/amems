package com.eray.thjw.project2.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.eray.thjw.project2.po.TechnicalInstruction;
import com.eray.thjw.project2.service.TechnicalInstructionService;

import enu.project2.TechnicalInstructionStatusEnum;

@Controller
@RequestMapping("/project2/instruction")
public class TechnicalInstructionController extends BaseController {

	@Resource
	private TechnicalInstructionService technicalInstructionService;
	@Resource
	private MonitorsettingsService monitorsettingsService;

	private static final Logger log = LoggerFactory.getLogger(MaterialController.class);

	/**
	 * @Description 跳转至技术通告界面
	 * @CreateBy 岳彬彬
	 * @return 页面视图
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:instruction:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage() throws BusinessException {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("technicalInstructionStatusEnum", TechnicalInstructionStatusEnum.enumToListMap());
			return new ModelAndView("project2/technicalInstruction/technicalInstruction_main", model);
		} catch (Exception e) {
			throw new BusinessException("跳转至技术指令失败!", e);
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
	@RequestMapping(value = "queryInstructionList", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody TechnicalInstruction instruction,
			HttpServletRequest request, Model model) throws BusinessException {
		try {
			Map<String, Object> resultMap = technicalInstructionService.queryAllInstruction(instruction);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("跳转技术指令页面失败!", e);
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 新增技术指令
	 * @param record
	 * @param request
	 * @return 技术通告id
	 * @throws Exception
	 */
	@Privilege(code = "project2:instruction:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addRecord(@RequestBody TechnicalInstruction technicalInstruction, HttpServletRequest request)
			throws BusinessException {
		try {
			return technicalInstructionService.insertInstruction(technicalInstruction);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("新增技术指令失败!", e);
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
	public TechnicalInstruction editRecord(@RequestBody TechnicalInstruction technicalInstruction,
			HttpServletRequest request) throws BusinessException {
		try {
			TechnicalInstruction instruction = technicalInstructionService.getRecord(technicalInstruction.getId());
			if (instruction.getZt() == TechnicalInstructionStatusEnum.TAKEEFFECT.getId()
					&& technicalInstruction.getParamsMap() != null
					&& ("1".equals(technicalInstruction.getParamsMap().get("view")))) {
				view4Isjs(instruction);
			}
			return instruction;
		} catch (Exception e) {
			throw new BusinessException("获取技术指令失败!", e);
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
	@Privilege(code = "project2:instruction:main:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateRecord(@RequestBody TechnicalInstruction technicalInstruction, HttpServletRequest request)
			throws BusinessException {
		try {
			return technicalInstructionService.updateRecord(technicalInstruction);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("修改技术指令失败!", e);
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
	@Privilege(code = "project2:instruction:main:03,project2:instruction:main:04")
	@ResponseBody
	@RequestMapping(value = "review", method = RequestMethod.POST)
	public String reiviewRecord(@RequestBody TechnicalInstruction technicalInstruction, HttpServletRequest request)
			throws BusinessException {
		try {
			return technicalInstructionService.getReviewAndApproveRecord(technicalInstruction);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("审核审批技术指令失败!", e);
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 作废
	 * @param record
	 * @param request
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:instruction:main:08")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void deleteRecord(@RequestBody TechnicalInstruction record, HttpServletRequest request)
			throws BusinessException {
		try {
			technicalInstructionService.deleteRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("技术指令作废失败!", e);
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
		model.put("id", id);
		return new ModelAndView("/project2/technicalInstruction/technicalInstruction_view", model);
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
	@Privilege(code = "project2:instruction:main:05")
	@ResponseBody
	@RequestMapping(value = "updateBatchAudit", method = RequestMethod.POST)
	public String updateBatchAudit(@RequestParam("idList[]") List<String> idList, String yj) throws BusinessException {

		try {
			return technicalInstructionService.updateBatchAudit(idList, yj);
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
	@Privilege(code = "project2:instruction:main:06")
	@ResponseBody
	@RequestMapping(value = "updateBatchApprove", method = RequestMethod.POST)
	public String updateBatchApprove(@RequestParam("idList[]") List<String> idList, String yj)
			throws BusinessException {
		try {
			return technicalInstructionService.updateBatchApprove(idList, yj);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("批量批准失败", e);
		}
	}

	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2017年9月6日 下午2:04:08
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param yj
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:instruction:main:07")
	@ResponseBody
	@RequestMapping(value = "doClose", method = RequestMethod.POST)
	public String doClose(@RequestBody TechnicalInstruction record, HttpServletRequest request)
			throws BusinessException {
		try {
			technicalInstructionService.update4Close(record);
			return record.getId();
		} catch (Exception e) {
			throw new BusinessException("关闭技术指令失败", e);
		}
	}
	/**
	 * 
	 * @Description 接收
	 * @CreateTime 2017年9月8日 上午9:41:07
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	private void view4Isjs(TechnicalInstruction record) {
		try {
			technicalInstructionService.updateZt4Isjs(record);
		} catch (Exception e) {
			log.error("技术指令接收失败!");
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
	public List<TechnicalInstruction> queryHistoryList(String id) throws BusinessException {
		try {
			return technicalInstructionService.queryHistoryList(id);
		} catch (Exception e) {
			throw new BusinessException("查询历史版本失败!",e);
		}
	}
	
	/**
	 * @Description 改版技术指令
	 * @CreateTime 2018年2月24日 上午10:31:57
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "revision", method = RequestMethod.POST)
	public String revision(@RequestBody TechnicalInstruction record) throws BusinessException {
		try {
			return technicalInstructionService.doRevision(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("改版技术指令失败!", e);
		}
	}
}
