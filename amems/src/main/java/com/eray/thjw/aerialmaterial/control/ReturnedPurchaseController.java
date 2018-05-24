 package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.dao.ReturnedPurchaseDetailMapper;
import com.eray.thjw.aerialmaterial.po.ReturnedPurchase;
import com.eray.thjw.aerialmaterial.po.ReturnedPurchaseDetail;
import com.eray.thjw.aerialmaterial.service.ReturnedPurchaseService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * 
 * 退货控制器
 * @author ll
 */
@Controller
@RequestMapping("/aerialmaterial/returnedpurchase")
public class ReturnedPurchaseController {
	
	@Resource
	private ReturnedPurchaseService returnedPurchaseService;
	
	@Resource
	private ReturnedPurchaseDetailMapper returnedPurchaseDetailMapper;
	/**
	 * 跳转至退货管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:returnedpurchase:manage")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/material/returnedpurchase/returnedpurchase_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 退货列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "returnedpurchaseList", method = RequestMethod.POST)
	public Map<String, Object> returnedpurchaseList(@RequestBody ReturnedPurchase returnedPurchase,HttpServletRequest request,Model model) throws BusinessException{
		String id = returnedPurchase.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		returnedPurchase.setId(null);
		PageHelper.startPage(returnedPurchase.getPagination());
		List<ReturnedPurchase> list = returnedPurchaseService.queryAllPageList(returnedPurchase);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					ReturnedPurchase returnedPurchase1 = new ReturnedPurchase();
					returnedPurchase1.setId(id);
					List<ReturnedPurchase> newRecordList = returnedPurchaseService.queryAllPageList(returnedPurchase1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, returnedPurchase.getPagination());
		}else{
			List<ReturnedPurchase> newRecordList = new ArrayList<ReturnedPurchase>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				ReturnedPurchase returnedPurchase1 = new ReturnedPurchase();
				returnedPurchase1.setId(id);
				newRecordList = returnedPurchaseService.queryAllPageList(returnedPurchase1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, returnedPurchase1.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, returnedPurchase.getPagination());
		}
	}
	
	/**
	 * @author ll
	 * @description 初始化增加
	 * @param request,response
	 * @return 页面视图
	 * @develop date 2016.10.11
	 *
	 */
	@Privilege(code="aerialmaterial:returnedpurchase:manage:01")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/material/returnedpurchase/returnedpurchase_add", model);
	}
	
	/**
	 * @author ll
	 * @description 保存
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody ReturnedPurchase returnedPurchase) throws BusinessException{
		try {
			return returnedPurchaseService.save(returnedPurchase);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 提交
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public String submit(@RequestBody ReturnedPurchase returnedPurchase) throws BusinessException{
		try {
			return returnedPurchaseService.submit(returnedPurchase);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 初始化编辑借入单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.10.13
	 *
	 */
	@Privilege(code="aerialmaterial:returnedpurchase:manage:02")
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(Model model,String id,HttpServletRequest request) throws BusinessException{
		try {
			model.addAttribute("returnedPurchase", returnedPurchaseService.selectByPrimaryKey(id));
		} catch (Exception e) {
			throw new BusinessException("初始化编辑借入单失败!");
		}finally{}
		return new ModelAndView("material/returnedpurchase/returnedpurchase_edit");
	}

	/**
	 * @author ll
	 * @description 根据借入单id查询提订详情信息
	 * @param request,mainid
	 * @return List<ReserveDetail>
	 * @develop date 2016.10.13
	 */
	@ResponseBody
	@RequestMapping(value = "queryDetailListByMainId", method = RequestMethod.POST)
	public List<ReturnedPurchaseDetail> queryDetailListByMainId(String mainid,HttpServletRequest request)throws BusinessException {
		List<ReturnedPurchaseDetail> list = new ArrayList<ReturnedPurchaseDetail>();
		try {
			list = returnedPurchaseDetailMapper.selectByMainId(mainid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询数据失败");
		}
		return list;
	}
	
	/**
	 * @author ll
	 * @description 修改保存
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "updatesave", method = RequestMethod.POST)
	public String updatesave(@RequestBody ReturnedPurchase returnedPurchase) throws BusinessException{
		try {
			return returnedPurchaseService.updatesave(returnedPurchase);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 修改提交
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "updatesubmit", method = RequestMethod.POST)
	public String updatesubmit(@RequestBody ReturnedPurchase returnedPurchase) throws BusinessException{
		try {
			return returnedPurchaseService.updatesubmit(returnedPurchase);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	
	/**
	 * @author ll
	 * @description 作废
	 * @param request,id
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:returnedpurchase:manage:03")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request,String id) throws BusinessException{
		try {
			returnedPurchaseService.cancel(id);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 撤销
	 * @param request,id
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:returnedpurchase:manage:04")
	@ResponseBody
	@RequestMapping(value = "revoked", method = RequestMethod.POST)
	public void revoked(HttpServletRequest request,String id) throws BusinessException{
		try {
			returnedPurchaseService.revoked(id);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 初始化查看退货单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.10.13
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id,HttpServletRequest request) throws BusinessException{
		try {
			model.addAttribute("returnedPurchase", returnedPurchaseService.selectByPrimaryKey(id));
		} catch (Exception e) {
			throw new BusinessException("初始化查看退货单失败!");
		}finally{}
		return new ModelAndView("material/returnedpurchase/returnedpurchase_view");
	}
}
