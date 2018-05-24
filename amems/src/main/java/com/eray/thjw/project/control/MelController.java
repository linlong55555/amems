package com.eray.thjw.project.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project.po.MelChangeSheet;
import com.eray.thjw.project.service.MelService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.mel.MelStatusEnum;

/**
 * @author liub
 * @description MELController类
 */
@RequestMapping("/project/mel")
@Controller("MelController1")
public class MelController extends BaseController {

	@Autowired
	private MelService melService;

	/**
	 * @author liub
	 * @description 跳转至mel界面
	 * @return mel的路径
	 */
	@Privilege(code = "project:mel:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project/mel/mel_main", responseParamMap);
	}
	
	/**
	 * @author liub
	 * @description 
	 * @return 页面视图
	 *
	 */
	@Privilege(code="project:mel:main")
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(String id,String dprtcode, Model model) throws BusinessException {
		model.addAttribute("dprtcode", dprtcode);
		model.addAttribute("id", id);
		return new ModelAndView("project/mel/mel_view");
	}

	/**
	 * @author liub
	 * @description 初始化增加Mel更改单
	 * @return 页面视图
	 *
	 */
	@Privilege(code="project:mel:main:01")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add() throws BusinessException {
		return new ModelAndView("project/mel/mel_add");
	}
	
	/**
	 * @author liub
	 * @description 增加Mel更改单
	 * @param maintenance
	 * @return String
	 */
	@Privilege(code="project:mel:main:01")
	@ResponseBody
	@RequestMapping(value = "addSave", method = RequestMethod.POST)
	public String addSave(@RequestBody MelChangeSheet melChangeSheet) throws BusinessException{	
		try {
			melChangeSheet.setZt(MelStatusEnum.SAVE.getId());
			return melService.save(melChangeSheet);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存Mel更改单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 增加Mel更改单
	 * @param maintenance
	 * @return String
	 */
	@Privilege(code="project:mel:main:03")
	@ResponseBody
	@RequestMapping(value = "addSubmit", method = RequestMethod.POST)
	public String addSubmit(@RequestBody MelChangeSheet melChangeSheet) throws BusinessException{	
		try {
			melChangeSheet.setZt(MelStatusEnum.SUBMIT.getId());
			return melService.save(melChangeSheet);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存Mel更改单失败!",e);
		}
	}
	

	/**
	 * @author liub
	 * @description 初始化修改Mel更改单
	 * @return 页面视图
	 *
	 */
	@Privilege(code="project:mel:main:02")
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(String id,String dprtcode, Model model) throws BusinessException {
		model.addAttribute("dprtcode", dprtcode);
		model.addAttribute("id", id);
		return new ModelAndView("project/mel/mel_edit");
	}
	
	/**
	 * @author liub
	 * @description 修改Mel更改单
	 * @param maintenance
	 * @return String
	 */
	@Privilege(code="project:mel:main:02")
	@ResponseBody
	@RequestMapping(value = "editSave", method = RequestMethod.POST)
	public String editSave(@RequestBody MelChangeSheet melChangeSheet) throws BusinessException{	
		try {
			melChangeSheet.setZt(MelStatusEnum.SAVE.getId());
			return melService.edit(melChangeSheet);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存Mel更改单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 修改Mel更改单
	 * @param maintenance
	 * @return String
	 */
	@Privilege(code="project:mel:main:03")
	@ResponseBody
	@RequestMapping(value = "editSubmit", method = RequestMethod.POST)
	public String editSubmit(@RequestBody MelChangeSheet melChangeSheet) throws BusinessException{	
		try {
			melChangeSheet.setZt(MelStatusEnum.SUBMIT.getId());
			return melService.edit(melChangeSheet);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存Mel更改单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 */
	@Privilege(code="project:mel:main:04")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(String id) throws BusinessException{
		getLogger().info("作废操作  前端参数:id{}", new Object[]{id});	
		try {
			melService.deleteById(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("作废失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化审核Mel更改单
	 * @return 页面视图
	 *
	 */
	@Privilege(code="project:mel:main:05")
	@RequestMapping(value = "audit", method = RequestMethod.GET)
	public ModelAndView audit(String id,String dprtcode, Model model) throws BusinessException {
		model.addAttribute("dprtcode", dprtcode);
		model.addAttribute("id", id);
		return new ModelAndView("project/mel/mel_audit");
	}
	
	/**
	 * @author liub
	 * @description 审核通过
	 * @param melChangeSheet
	 */
	@Privilege(code="project:mel:main:05")
	@ResponseBody
	@RequestMapping(value = "agreedAudit", method = RequestMethod.POST)
	public void agreedAudit(@RequestBody MelChangeSheet melChangeSheet) throws BusinessException{
		try {
			melChangeSheet.setZt(MelStatusEnum.AUDITED.getId());
			melService.updateAudit(melChangeSheet);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 审核驳回
	 * @param fixedCheckItem
	 * @develop date 2016.09.29
	 */
	@Privilege(code="project:mel:main:05")
	@ResponseBody
	@RequestMapping(value = "rejectedAudit", method = RequestMethod.POST)
	public void rejectedAudit(@RequestBody MelChangeSheet melChangeSheet) throws BusinessException{
		try {
			melChangeSheet.setZt(MelStatusEnum.AUDIT_DISMISSED.getId());
			melService.updateAudit(melChangeSheet);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化批准Mel更改单
	 * @return 页面视图
	 *
	 */
	@Privilege(code="project:mel:main:06")
	@RequestMapping(value = "approve", method = RequestMethod.GET)
	public ModelAndView approve(String id,String dprtcode, Model model) throws BusinessException {
		model.addAttribute("dprtcode", dprtcode);
		model.addAttribute("id", id);
		return new ModelAndView("project/mel/mel_approve");
	}
	
	/**
	 * @author liub
	 * @description 批准通过
	 * @param fixedCheckItem
	 */
	@Privilege(code="project:mel:main:06")
	@ResponseBody
	@RequestMapping(value = "agreedApprove", method = RequestMethod.POST)
	public void agreedApprove(@RequestBody MelChangeSheet melChangeSheet) throws BusinessException{
		try {
			melChangeSheet.setZt(MelStatusEnum.APPROVED.getId());
			melService.updateApprove(melChangeSheet);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("批准失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 批准驳回
	 * @param fixedCheckItem
	 * @throws BusinessException 
	 */
	@Privilege(code="project:mel:main:06")
	@ResponseBody
	@RequestMapping(value = "rejectedApprove", method = RequestMethod.POST)
	public void rejectedApprove(@RequestBody MelChangeSheet melChangeSheet) throws BusinessException{
		try {
			melChangeSheet.setZt(MelStatusEnum.APPROVED_DISMISSED.getId());
			melService.updateApprove(melChangeSheet);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("批准驳回失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 指定结束
	 * @param melChangeSheet
	 * @throws BusinessException
	 */
	@Privilege(code="project:mel:main:07")
	@ResponseBody
	@RequestMapping(value = "shutDown", method = RequestMethod.POST)
	public void shutDown(@RequestBody MelChangeSheet melChangeSheet) throws BusinessException{
		getLogger().info("指定结束  前端参数:id{}", new Object[]{melChangeSheet});
		try {
			melChangeSheet.setZt(MelStatusEnum.CLOSE.getId());
			melService.updateShutDown(melChangeSheet);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("指定结束失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询信息
	 * @param melChangeSheet
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody MelChangeSheet melChangeSheet)throws BusinessException {
		String id = melChangeSheet.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		melChangeSheet.setId(null);
		try {
			PageHelper.startPage(melChangeSheet.getPagination());
			List<MelChangeSheet> list = melService.queryAllPageList(melChangeSheet);
			if(((Page)list).getTotal() > 0){
				//分页查询
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						MelChangeSheet newRecord = new MelChangeSheet();
						newRecord.setId(id);
						List<MelChangeSheet> newRecordList = melService.queryAllPageList(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, melChangeSheet.getPagination());
			}else{
				List<MelChangeSheet> newRecordList = new ArrayList<MelChangeSheet>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					MelChangeSheet newRecord = new MelChangeSheet();
					newRecord.setId(id);
					newRecordList = melService.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, melChangeSheet.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, melChangeSheet.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据航材id查询
	 * @param id
	 * @return MelChangeSheet
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public MelChangeSheet selectById(Model model,String id) throws BusinessException {
		MelChangeSheet list = null;
		try {
			list = melService.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询失败!",e);
		}
		return list;
	}
}



