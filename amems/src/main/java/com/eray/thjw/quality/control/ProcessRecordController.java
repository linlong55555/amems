package com.eray.thjw.quality.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.ProcessRecord;
import com.eray.thjw.quality.service.ProcessRecordService;

/**
 * @Description 流程记录控制层
 * @CreateTime 2018年1月11日 下午2:20:39
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/quality/processrecord")
public class ProcessRecordController {
	
	@Resource
	private ProcessRecordService processRecordService;
	
	/**
	 * @Description 查询流程记录
	 * @CreateTime 2018年1月11日 下午3:03:01
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public List<ProcessRecord> queryList(@RequestBody ProcessRecord record)throws BusinessException{
		try {
			return processRecordService.queryList(record);
		} catch (Exception e) {
			throw new BusinessException("流程记录查询失败！", e);
		}
	}
}
