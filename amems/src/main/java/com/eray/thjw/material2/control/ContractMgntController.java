package com.eray.thjw.material2.control;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ContractMgnt;
import com.eray.thjw.material2.service.ContractMgntService;
import com.eray.thjw.util.WordExportUtils;

import enu.material2.ContractMgntTypeEngEnum;
import enu.material2.ContractMgntTypeEnum;
import enu.material2.ExportFileTempletEnum;

/**
 * @author 裴秀
 * @description 合同管理
 */
@Controller
@RequestMapping("material/contract")
public class ContractMgntController {
	
	/**
	 * @Description 合同管理service
	 * @CreateTime 2018-3-8 上午11:32:12
	 * @CreateBy 刘兵
	 */
	@Autowired
	private ContractMgntService contractMgntService;
	
	/**
	 * @Description 合同管理
     * @CreateTime 2018年03月06日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:contract:mgnt")
	@RequestMapping(value = "mgnt", method = RequestMethod.GET)
	public ModelAndView mgnt(Model model){
		model.addAttribute("contractMgntTypeEnum", ContractMgntTypeEnum.enumToListMap());
	    return new ModelAndView("/material2/contract/mgnt/mgnt_main");
	
	}
	
	/**
	 * @Description 查看合同
	 * @CreateTime 2017-8-16 下午4:16:54
	 * @CreateBy 刘兵
	 * @return 页面视图
	 */
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id")String id, Model model) {
		model.addAttribute("id", id);
		return new ModelAndView("material2/contract/mgnt/contract_mgnt_view");
	}
	
	/**
	 * @Description 新增合同
	 * @CreateTime 2017-8-15 下午3:30:19
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return String 合同id
	 * @throws BusinessException
	 */
	@Privilege(code="material:contract:mgnt:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody ContractMgnt contractMgnt) throws BusinessException{
		try {
			return contractMgntService.save(contractMgnt);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存合同失败!",e);
		}
	}
	
	/**
	 * @Description 编辑合同
	 * @CreateTime 2017-8-15 下午3:30:19
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return String 合同id
	 * @throws BusinessException
	 */
	@Privilege(code="material:contract:mgnt:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody ContractMgnt contractMgnt) throws BusinessException{
		try {
			return contractMgntService.update(contractMgnt);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("编辑合同失败!",e);
		}
	}
	
	/**
	 * @Description 修订合同
	 * @CreateTime 2018-3-13 上午10:00:33
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return String 合同id
	 * @throws BusinessException
	 */
	@Privilege(code="material:contract:mgnt:03")
	@ResponseBody
	@RequestMapping(value = "updateRevise", method = RequestMethod.POST)
	public String updateRevise(@RequestBody ContractMgnt contractMgnt) throws BusinessException{
		try {
			return contractMgntService.updateRevise(contractMgnt);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("修订合同失败!",e);
		}
	}
	
	/**
	 * @Description 删除合同
	 * @CreateTime 2018-3-13 上午10:26:23
	 * @CreateBy 刘兵
	 * @param id 工卡id
	 * @throws BusinessException
	 */
	@Privilege(code="material:contract:mgnt:06")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(String id) throws BusinessException{
		try {
			contractMgntService.delete(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除失败!",e);
		}
	}
	
	/** 
	 * @Description 关闭合同
	 * @CreateTime 2018-3-13 上午11:52:53
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @throws BusinessException
	 */
	@Privilege(code="material:contract:mgnt:04")
	@ResponseBody
	@RequestMapping(value = "shutDown", method = RequestMethod.POST)
	public void shutDown(@RequestBody ContractMgnt contractMgnt) throws BusinessException{
		try {
			contractMgntService.updateShutDown(contractMgnt);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 合同分页列表查询
	 * @CreateTime 2018-3-9 上午11:15:07
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同管理
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody ContractMgnt contractMgnt)throws BusinessException{
		try {
			return contractMgntService.queryAllPageList(contractMgnt);
		} catch (Exception e) {
			throw new BusinessException("合同列表加载失败!",e);
		}	
	}
	
	/**
	 * @Description 模态框中获取合同
	 * @CreateTime 2018-4-3 上午11:47:16
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return List<ContractMgnt> 合同集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryModelList", method = RequestMethod.POST)
	public List<ContractMgnt> queryModelList(@RequestBody ContractMgnt contractMgnt)throws BusinessException{
		try {
			return contractMgntService.queryModelList(contractMgnt);
		} catch (Exception e) {
			throw new BusinessException("合同列表加载失败!",e);
		}	
	}
	
	/**
	 * @Description 根据合同id查询合同及用户信息
	 * @CreateTime 2017-8-15 下午3:30:19
	 * @CreateBy 刘兵
	 * @param id contractMgntid
	 * @return ContractMgnt
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public ContractMgnt selectById(String id) throws BusinessException {
		try {
			return contractMgntService.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询合同失败!",e);
		}
	}
	
	/**
	 * @Description 合同执行跟踪
     * @CreateTime 2018年03月06日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:contract:tracking")
	@RequestMapping(value = "tracking", method = RequestMethod.GET)
	public ModelAndView tracking(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/contract/tracking/tracking_main",model);
	
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramjson 当前参数
	 * @throws BusinessException
	 */
	@RequestMapping(value = "exportWord", method = RequestMethod.GET)
	public void exportWord(String paramjson, HttpServletRequest request, Model model, HttpServletResponse response) throws BusinessException {
		try {
		    paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
		    ContractMgnt contractMgnt = JSON.parseObject(paramjson, ContractMgnt.class);
		    Map<String, Object> map = contractMgntService.exportWord(contractMgnt.getId());
		    String templetFile = ExportFileTempletEnum.getName(contractMgnt.getHtlx(), Integer.parseInt(contractMgnt.getParamsMap().get("zywlx").toString()));
		    WordExportUtils.exportWord(response, map, ContractMgntTypeEngEnum.getName(contractMgnt.getHtlx()), templetFile);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
