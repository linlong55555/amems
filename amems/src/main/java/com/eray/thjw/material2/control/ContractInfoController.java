package com.eray.thjw.material2.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ContractInfo;
import com.eray.thjw.material2.service.ContractInfoService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/**
 * @Description 合同明细控制层
 * @CreateTime 2017-8-19 下午3:42:43
 * @CreateBy 刘兵
 */
@Controller
@RequestMapping("/material/contractinfo")
public class ContractInfoController extends BaseController {

	@Resource
	private ContractInfoService contractInfoService;
	
	/**
	 * @Description 根据条件查询合同明细列表
	 * @CreateTime 2017-8-19 下午3:49:49
	 * @CreateBy 刘兵
	 * @param materialTool 合同明细
	 * @return List<MaterialTool> 合同明细集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<ContractInfo> queryAllList(@RequestBody ContractInfo contractInfo)throws BusinessException{
		try {
			return contractInfoService.queryAllList(contractInfo);
		} catch (Exception e) {
			throw new BusinessException("查询合同明细失败!",e);
		}
	}
	/**
	 * 
	 * @Description 查询所有在途数量
	 * @CreateTime 2018-3-14 下午3:21:31
	 * @CreateBy 孙霁
	 * @param contractInfo
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody ContractInfo contractInfo)throws BusinessException{
		try {
			return contractInfoService.queryAll(contractInfo);
		} catch (Exception e) {
			throw new BusinessException("查询在途数据失败!",e);
		}
	}
	
	/**
	 * @Description 根据查询条件分页查询合同明细信息(弹窗)
	 * @CreateTime 2018-3-15 上午11:47:25
	 * @CreateBy 刘兵
	 * @param contractInfo 合同明细
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryWinAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryWinAllPageList(@RequestBody ContractInfo contractInfo)throws BusinessException {
		try {
			PageHelper.startPage(contractInfo.getPagination());
			List<ContractInfo> list = contractInfoService.queryWinAllPageList(contractInfo);
			return PageUtil.pack4PageHelper(list, contractInfo.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	/**
	 * @Description 根据合同id查询合同明细(带库存)
	 * @CreateTime 2018-3-15 上午10:44:33
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return List<ContractInfo> 合同明细集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryDetailByMainid",method={RequestMethod.POST,RequestMethod.GET})
	public List<ContractInfo> queryDetailByMainid(String mainid) throws BusinessException {
		try {
			return contractInfoService.queryDetailByMainid(mainid);
		} catch (Exception e) {
			throw new BusinessException("查询合同明细失败!",e);
		}
	}
	
	/**
	 * @Description 根据需求清单id集合查询合同明细
	 * @CreateTime 2018-4-2 下午2:42:59
	 * @CreateBy 刘兵
	 * @param xqqdIdList
	 * @return List<ContractInfo>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryByXqqdIdList",method={RequestMethod.POST,RequestMethod.GET})
	public List<ContractInfo> queryByXqqdIdList(@RequestParam("xqqdIdList[]")List<String> xqqdIdList) throws BusinessException {
		try {
			return contractInfoService.queryByXqqdIdList(xqqdIdList);
		} catch (Exception e) {
			throw new BusinessException("查询合同明细失败!",e);
		}
	}
	
}
