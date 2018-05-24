package com.eray.thjw.project2.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.EOManhourParkingTime;
import com.eray.thjw.project2.service.EOManhourParkingTimeService;

/** 
 * @Description 工时/停场时间  控制层
 * @CreateTime 2017-8-24 下午3:51:32
 * @CreateBy 雷伟	
 */
@Controller
@RequestMapping("/project2/manhourparking")
public class EOManhourParkingTimeController extends BaseController {
	
	@Resource
	private EOManhourParkingTimeService eoManhourParkingTimeService;
	
	/**
	 * @Description 查询EO工时/停场时间
	 * @CreateTime 2017-8-24 下午3:25:16
	 * @CreateBy 雷伟
	 * @param eoManhourParkingTime EO工时/停场时间
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<EOManhourParkingTime> queryAllList(@RequestBody EOManhourParkingTime eoManhourParkingTime)throws BusinessException{
		try {
			return eoManhourParkingTimeService.queryAllList(eoManhourParkingTime);
		} catch (Exception e) {
			throw new BusinessException("查询EO工时/停场时间失败!",e);
		}
	}
	
}
