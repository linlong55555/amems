package com.eray.thjw.aerialmaterial.control;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.eray.thjw.aerialmaterial.po.Contract;
import com.eray.thjw.aerialmaterial.po.ContractPay;
import com.eray.thjw.aerialmaterial.service.ContractDetailService;
import com.eray.thjw.aerialmaterial.service.ContractService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.UrgencyEnum;
import enu.aerialmaterial.ContractDeliveryStatusEnum;
import enu.aerialmaterial.ContractStatusEnum;
import enu.aerialmaterial.ContractTypeEnum;
import enu.aerialmaterial.PayTypeEnum;

/**
 * @author liub
 * @description 合同管理控制层
 * @develop date 2016.11.04
 */
@Controller
@RequestMapping("/aerialmaterial/contract")
public class ContractController extends BaseController {
	
	/**
	 * @author liub
	 * @description 合同管理service
	 * @develop date 2016.11.04
	 */
	@Autowired
	private ContractService contractService;
	
	/**
	 * @author liub
	 * @description 合同详情service
	 * @develop date 2016.11.09
	 */
	@Autowired
	private ContractDetailService contractDetailService;
	
	/**
	 * @author liub
	 * @description 跳转至合同管理界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.10.10
	 *
	 */
	@Privilege(code="aerialmaterial:contract:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String manage(Model model,HttpServletRequest request){
		model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
		model.addAttribute("contractTypeEnum", ContractTypeEnum.enumToListMap());
		model.addAttribute("contractDeliveryStatusEnum", ContractDeliveryStatusEnum.enumToListMap());
		return "material/contract/contract_main";
	}
	
	/**
	 * @author liub
	 * @description 付款统计
	 * @param
	 * @return 页面视图
	 * @develop date 2016.10.10
	 *
	 */
	@Privilege(code="aerialmaterial:contract:statistics")
	@RequestMapping(value = "statistics", method = RequestMethod.GET)
	public String statistics(Model model,HttpServletRequest request){
		model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
		model.addAttribute("contractTypeEnum", ContractTypeEnum.enumToListMap());
		model.addAttribute("contractDeliveryStatusEnum", ContractDeliveryStatusEnum.enumToListMap());
		return "material/contract/contract_statistics";
	}
	
	/**
	 * @author liub
	 * @description 查看合同
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.09
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id){
		model.addAttribute("contract", contractService.selectById(id));
		model.addAttribute("contractTypeEnum", ContractTypeEnum.enumToListMap());
		model.addAttribute("contractStatusEnum", ContractStatusEnum.enumToListMap());
		model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
		return new ModelAndView("material/contract/contract_view");
	}
	
	/**
	 * @author liub
	 * @description 初始化新增合同
	 * @param request,response
	 * @return 页面视图
	 * @develop date 2016.11.07
	 *
	 */
	@Privilege(code="aerialmaterial:contract:main:01")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("contractTypeEnum", ContractTypeEnum.enumToListMap());
		model.put("urgencyEnum", UrgencyEnum.enumToListMap());
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("material/contract/contract_add", model);
	}
	
	/**
	 * @author liub
	 * @description 保存合同
	 * @param contract
	 * @develop date 2016.11.08
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:contract:main:05")
	@ResponseBody
	@RequestMapping(value = "addSave", method = RequestMethod.POST)
	public String addSave(@RequestBody Contract contract) throws BusinessException{
		try {
			contract.setZt(ContractStatusEnum.SAVE.getId());
			return contractService.add(contract);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存合同失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 提交合同
	 * @param contract
	 * @develop date 2016.11.08
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:contract:main:06")
	@ResponseBody
	@RequestMapping(value = "addSubmit", method = RequestMethod.POST)
	public String addSubmit(@RequestBody Contract contract) throws BusinessException{
		try {
			contract.setZt(ContractStatusEnum.SUBMIT.getId());
			return contractService.add(contract);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交合同失败!",e);
		}
	}
	
	/**
	 * 查询航材历史采购价格
	 * @param map
	 * @return
	 */
	@RequestMapping("/list/costHistory")
	public @ResponseBody Map<String, Object> queryCostByBjId(BaseEntity entity){
		return this.contractService.queryCostHisByBjId(entity);
	}
	
	/**
	 * @author liub
	 * @description 检查合同修改权限
	 * @param request,id
	 * @return
	 * @develop date 2016.11.10
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkEdit", method = RequestMethod.POST)
	public void checkEdit(HttpServletRequest request,String id) throws BusinessException {
		try {
			contractService.checkEdit(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("查询合同信息失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化修改合同
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.08
	 *
	 */
	@Privilege(code="aerialmaterial:contract:main:02")
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(Model model,String id){
		model.addAttribute("contract", contractService.selectById(id));
		model.addAttribute("contractTypeEnum", ContractTypeEnum.enumToListMap());
		model.addAttribute("contractStatusEnum", ContractStatusEnum.enumToListMap());
		model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
		return new ModelAndView("material/contract/contract_edit");
	}
	
	/**
	 * @author liub
	 * @description 修改后保存合同
	 * @param contract
	 * @develop date 2016.11.08
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:contract:main:05")
	@ResponseBody
	@RequestMapping(value = "editSave", method = RequestMethod.POST)
	public void editSave(@RequestBody Contract contract) throws BusinessException{
		try {
			contractService.edit(contract);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存合同失败！",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 修改后提交合同
	 * @param contract
	 * @develop date 2016.11.08
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:contract:main:06")
	@ResponseBody
	@RequestMapping(value = "editSubmit", method = RequestMethod.POST)
	public void editSubmit(@RequestBody Contract contract) throws BusinessException{
		try {
			contract.setZt(ContractStatusEnum.SUBMIT.getId());
			contractService.edit(contract);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交合同失败！",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化收货
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.09
	 *
	 */
	@Privilege(code="aerialmaterial:contract:main:08")
	@RequestMapping(value = "comeGood", method = RequestMethod.GET)
	public ModelAndView comeGood(Model model,String id){
		model.addAttribute("contract", contractService.selectById(id));
		model.addAttribute("contractTypeEnum", ContractTypeEnum.enumToListMap());
		model.addAttribute("contractStatusEnum", ContractStatusEnum.enumToListMap());
		model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
		return new ModelAndView("material/contract/contract_comeGood");
	}
	
	/**
	 * @author liub
	 * @description 收货
	 * @param request,contract
	 * @return 
	 * @develop date 2016.11.09
	 */
	@Privilege(code="aerialmaterial:contract:main:08")
	@ResponseBody
	@RequestMapping(value = "comeGood", method = RequestMethod.POST)
	public void comeGood(@RequestBody Contract contract,HttpServletRequest request)throws BusinessException {
		try {
			contractService.updateComeGood(contract);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("合同提交失败！",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化付款
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.12
	 *
	 */
	@Privilege(code="aerialmaterial:contract:main:09")
	@RequestMapping(value = "pay", method = RequestMethod.GET)
	public ModelAndView pay(Model model,String id) throws BusinessException{
		try {
			model.addAttribute("contract", contractService.selectById(id));
			model.addAttribute("contractTypeEnum", ContractTypeEnum.enumToListMap());
			model.addAttribute("contractStatusEnum", ContractStatusEnum.enumToListMap());
			model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
			model.addAttribute("payTypeEnum", PayTypeEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("初始化付款失败!",e);
		}
		return new ModelAndView("material/contract/contract_pay");
	}
	
	/**
	 * @author liub
	 * @description 保存合同付款
	 * @param contractPay
	 * @develop date 2016.11.12
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:contract:main:09")
	@ResponseBody
	@RequestMapping(value = "addPay", method = RequestMethod.POST)
	public void addPay(@RequestBody ContractPay contractPay) throws BusinessException{
		try {
			contractService.addPay(contractPay);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存合同付款失败！",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 修改合同付款
	 * @param contractPay
	 * @develop date 2016.11.12
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:contract:main:09")
	@ResponseBody
	@RequestMapping(value = "editPay", method = RequestMethod.POST)
	public void editPay(@RequestBody ContractPay contractPay) throws BusinessException{
		try {
			contractService.editPay(contractPay);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改合同付款失败！",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 删除合同付款
	 * @param id
	 * @develop date 2016.11.12
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:contract:main:09")
	@ResponseBody
	@RequestMapping(value = "deletePay", method = RequestMethod.POST)
	public void deletePay(String id,String mainId) throws BusinessException{
		try {
			contractService.deletePay(id,mainId);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("删除合同付款失败！",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param request,id
	 * @return
	 * @develop date 2016.11.09
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:contract:main:07")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request,String id) throws BusinessException{
		try {
			contractService.cancel(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("作废合同失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 指定结束
	 * @param contract
	 * @return
	 * @develop date 2016.11.09
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:contract:main:10")
	@ResponseBody
	@RequestMapping(value = "shutDown", method = RequestMethod.POST)
	public void shutDown(@RequestBody Contract contract) throws BusinessException{
		try {
			contract.setZt(ContractStatusEnum.CLOSE.getId());
			contractService.updateShutDown(contract);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("合同指定结束失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询合同信息
	 * @param contract
	 * @return Map<String, Object>
	 * @develop date 2016.11.08
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody Contract contract)throws BusinessException {
		String id = contract.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		contract.setId(null);
		try {
			PageHelper.startPage(contract.getPagination());
			List<Contract> list = contractService.queryAllPageList(contract);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Contract newRecord = new Contract();
						newRecord.setId(id);
						List<Contract> newRecordList = contractService.queryAllPageList(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, contract.getPagination());
			}else{
				List<Contract> newRecordList = new ArrayList<Contract>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Contract newRecord = new Contract();
					newRecord.setId(id);
					newRecordList = contractService.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, contract.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, contract.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询合同付款信息
	 * @param contract
	 * @return Map<String, Object>
	 * @develop date 2016.11.08
	 */
	@ResponseBody
	@RequestMapping(value = "queryContractPayPageList", method = RequestMethod.POST)
	public Map<String, Object> queryContractPayPageList(@RequestBody ContractPay contractPay,HttpServletRequest request,Model model) throws BusinessException{
		
		try {
			PageHelper.startPage(contractPay.getPagination());
			List<ContractPay> list = contractService.queryContractPayPageList(contractPay);
			return PageUtil.pack4PageHelper(list, contractPay.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据合同id查询提订合同详情信息
	 * @param request,mainid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.08
	 */
	@ResponseBody
	@RequestMapping(value = "queryReserveContractDetailList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryReserveContractDetailList(String mainid,HttpServletRequest request)throws BusinessException {
		List<Map<String, Object>> list = null;
		try {
			list = contractDetailService.queryReserveContractDetailList(mainid);
		} catch (Exception e) {
			throw new BusinessException("查询提订合同详情失败!",e);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据合同id查询送修合同详情信息
	 * @param request,mainid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.08
	 */
	@ResponseBody
	@RequestMapping(value = "queryRepairContractMaterialList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryRepairContractMaterialList(String mainid,HttpServletRequest request)throws BusinessException {
		List<Map<String, Object>> list = null;
		try {
			list = contractDetailService.queryRepairContractMaterialList(mainid);
		} catch (Exception e) {
			throw new BusinessException("查询送修合同详情失败1",e);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据条件分页查询付款统计列表
	 * @param request,model,baseEntity
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2016.12.08
	 */
	@ResponseBody
	@RequestMapping(value = "queryPayStatisticsPageList", method = RequestMethod.POST)
	public Map<String, Object> queryPayStatisticsPageList(@RequestBody BaseEntity baseEntity,HttpServletRequest request,Model model) throws BusinessException{
		try {
			PageHelper.startPage(baseEntity.getPagination());
			List<Map<String, Object>> list =  this.contractService.queryPayStatisticsPageList(baseEntity);
			return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询付款统计失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据条件分页查询付款明细列表
	 * @param request,model,baseEntity
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2016.12.08
	 */
	@ResponseBody
	@RequestMapping(value = "queryPayDetailPageList", method = RequestMethod.POST)
	public Map<String, Object> queryPayDetailPageList(@RequestBody BaseEntity baseEntity,HttpServletRequest request,Model model) throws BusinessException{
		try {
			PageHelper.startPage(baseEntity.getPagination());
			List<Map<String, Object>> list = this.contractService.queryPayDetailPageList(baseEntity);
			return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询付款明细失败!",e);
		}
	}
	/**
	 * 付款统计、付款明细导出excel
	 * @param htlx 合同类型
	 * @param gysbm 服务商
	 * @param sdqdrq 合同签订日期
	 * @param dprtcode 组织机构
	 * @param yjcd 紧急程度
	 * @param keyword 关键字
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "cotractOutExcel")
	public String export(String htlx,  String gysbm, String sdqdrq,String dprtcode, String yjcd,
			String keyword, HttpServletRequest request, RedirectAttributesModelMap model) throws BusinessException {
		try {
			keyword = new String((request.getParameter("keyword")).getBytes("iso-8859-1"), "utf-8");
			StringBuffer sbf = new StringBuffer("where 1=1 ");
			sbf.append(" and b1.DPRTCODE='" + dprtcode + "'");
			if (!htlx.equals("")) {
				sbf.append(" and b1.HTLX =" + Integer.valueOf(htlx) + " ");
			}
			if(!gysbm.equals("")){
				sbf.append(" and b1.GYSID='" + gysbm + "'");
			}
			if(!yjcd.equals("")){
				sbf.append(" and b1.JJCD =" + Integer.valueOf(yjcd) + " ");
			}
			if (!keyword.equals("")) {
				if(keyword.contains("'")){
					keyword=keyword.replace("'", "'|| chr(39) ||'");
				}
				sbf.append(" and (b1.HTH like '%" + keyword + "%' )");
			}
			if (!sdqdrq.equals("")) {
				String scrqFirst = sdqdrq.split("~")[0];
				String scrqEnd = sdqdrq.split("~")[1];
				sbf.append(" and b1.HTQDRQ >=to_date('" + scrqFirst + " 00:00:00','YYYY-MM-DD HH24:MI:SS')");
				sbf.append(" and b1.HTQDRQ <=to_date('" + scrqEnd + " 23:59:59','YYYY-MM-DD HH24:MI:SS')");
			}
			model.addFlashAttribute("sql", sbf.toString());
			return "redirect:/report/".concat("xls").concat("/").concat(dprtcode).concat("/contract.xls");
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败!",e);
		}
	}
	
	
	/**
	 * 模态框中查询合同列表
	 * @param contract
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryPageInModal", method = RequestMethod.POST)
	public Map<String, Object> queryPageInModal(@RequestBody Contract contract) throws BusinessException{
		try {
			PageHelper.startPage(contract.getPagination());
			List<Contract> list = this.contractService.queryPageInModal(contract);
			return PageUtil.pack4PageHelper(list, contract.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询合同列表失败!",e);
		}
	}
}
