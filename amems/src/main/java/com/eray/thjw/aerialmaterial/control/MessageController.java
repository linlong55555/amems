package com.eray.thjw.aerialmaterial.control;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.aerialmaterial.po.Message;
import com.eray.thjw.aerialmaterial.service.MessageService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;

/**
 * @author liub
 * @description 留言控制层
 * @develop date 2016.11.13
 */
@Controller
@RequestMapping("/aerialmaterial/message")
public class MessageController extends BaseController {
	
	/**
	 * @author liub
	 * @description 留言service
	 * @develop date 2016.11.13
	 */
	@Autowired
	private MessageService messageService;

	/**
	 * @author liub
	 * @description 保存留言
	 * @param message
	 * @develop date 2016.11.13
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void addPay(@RequestBody Message message) throws BusinessException{
		try {
			messageService.add(message);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author liub
	 * @description 删除message
	 * @param id
	 * @develop date 2016.11.13
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void deletePay(String id) throws BusinessException{
		try {
			messageService.delete(id);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author liub
	 * @description 根据单据id、提醒人读取留言
	 * @param djid
	 * @develop date 2016.11.15
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "updateViewStatusByDjIdUId", method = RequestMethod.POST)
	public void updateViewStatusByDjIdUId(String djid) throws BusinessException{
		try {
			messageService.updateViewStatusByDjIdUId(djid);
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
	
	/**
	 * @author liub
	 * @description 根据条件查询留言信息
	 * @param request,message
	 * @return List<Message>
	 * @develop date 2016.11.13
	 */
	@ResponseBody
	@RequestMapping(value = "queryMessageList", method = RequestMethod.POST)
	public List<Message> queryMessageList(@RequestBody Message message,HttpServletRequest request)throws BusinessException {
		List<Message> list = null;
		try {
			list = messageService.queryMessageList(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询留言信息失败");
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据单据id集合查询留言信息
	 * @param request,djidList
	 * @return List<Message>
	 * @develop date 2016.11.16
	 */
	@ResponseBody
	@RequestMapping(value = "queryMessageListByDjIdList", method = RequestMethod.POST)
	public List<Message> queryMessageListByDjIdList(@RequestParam("djidList[]")List<String> djidList,HttpServletRequest request)throws BusinessException {
		
		List<Message> list = null;
		try {
			list = messageService.queryMessageListByDjIdList(djidList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询留言信息失败");
		}
		return list;
	}

}
