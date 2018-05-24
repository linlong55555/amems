package com.eray.thjw.system.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.system.po.UpdateLog;
import com.eray.thjw.system.service.UpdateLogService;

/**
 * 
 * @Description 更新日志
 * @CreateTime 2018年4月23日 下午2:41:26
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/sys/updatelog")
public class UpdateLogController extends BaseController {
	
	@Resource
	private UpdateLogService updateLogService;
	
	/**
	 * 
	 * @Description 查询更新日志
	 * @CreateTime 2018年4月24日 上午10:36:07
	 * @CreateBy 林龙
	 * @param updateLog
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/queryByAll",method={RequestMethod.POST})
	public List<UpdateLog> queryByAll(@RequestBody UpdateLog updateLog) throws BusinessException {
		try {
			return updateLogService.queryByAll(updateLog);
		}catch (BusinessException e) {
			throw e;
		}catch (RuntimeException e) {
			throw new BusinessException("查询更新日志失败!",e);
		} 
	}

}
