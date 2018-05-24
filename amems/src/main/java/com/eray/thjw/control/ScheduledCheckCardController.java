package com.eray.thjw.control;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.ScheduledCheckCard;
import com.eray.thjw.service.ScheduledCheckCardService;

/**
 * 定检工卡控制器
 * @author zhuchao
 *
 */
@RequestMapping("/project/scheduledcheckcard")
@Controller
public class ScheduledCheckCardController {
	 
	@Resource
	private ScheduledCheckCardService scheduledCheckCardService;
	 
	/**
	 * 定检工卡管理页面
	 * @return
	 */
	@RequestMapping(value="/main",method={RequestMethod.GET})
	public String main() {
		
		return "/project/scheduledcheckcard/scheduledcheckcard_main";
	}
	
	/**
	 * 查询一页定检工单
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/page",method={RequestMethod.GET})
	public Map<String, Object> list(ScheduledCheckCard card) {
		return scheduledCheckCardService.queryPage(card);
	}
	
	/**
	 * 定检工卡添加页面
	 * @return
	 */
	@RequestMapping(value="/add",method={RequestMethod.GET})
	public String add() {
		
		return "/project/scheduledcheckcard/scheduledcheckcard_add";
	}
	
	/**
	 * 添加定检工卡
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add",method={RequestMethod.POST})
	public void add(ScheduledCheckCard scheduledcheckcard) throws BusinessException {
		try {
			scheduledCheckCardService.add(scheduledcheckcard);
		} catch (Exception e) {
			throw new BusinessException("添加定检工卡失败");
		}
		
	}
	
	/**
	 * 定检工卡编辑页面
	 * @return
	 */
	@RequestMapping(value="/edit",method={RequestMethod.GET})
	public String edit(String id,Model model) throws BusinessException {
		
		try {
			ScheduledCheckCard card = scheduledCheckCardService.load(id);
			model.addAttribute("bean", card);
			return "/project/scheduledcheckcard/scheduledcheckcard_edit";
		} catch (Exception e) {
			throw new BusinessException("加载定检工卡失败");
		}
		
	}
	
	/**
	 * 修改定检工卡
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/edit",method={RequestMethod.POST})
	public void edit(ScheduledCheckCard scheduledcheckcard) throws BusinessException {
		
		try {
			scheduledCheckCardService.edit(scheduledcheckcard);
		} catch (Exception e) {
			throw new BusinessException("修改定检工卡失败");
		}
		
	}
	
	/**
	 * 定检工卡查看页面
	 * @return
	 */
	@RequestMapping(value="/view",method={RequestMethod.GET})
	public String view(String id,Model model) throws BusinessException {
		
		try {
			ScheduledCheckCard card = scheduledCheckCardService.load(id);
			model.addAttribute("bean", card);
			return "/project/scheduledcheckcard/scheduledcheckcard_view";
		} catch (Exception e) {
			throw new BusinessException("加载定检工卡失败");
		}
		
	}

	public ScheduledCheckCardService getScheduledCheckCardService() {
		return scheduledCheckCardService;
	}

	public void setScheduledCheckCardService(ScheduledCheckCardService scheduledCheckCardService) {
		this.scheduledCheckCardService = scheduledCheckCardService;
	}
	 
}
