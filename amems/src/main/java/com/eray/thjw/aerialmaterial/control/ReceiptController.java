package com.eray.thjw.aerialmaterial.control;



import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.ReceiptSheet;
import com.eray.thjw.aerialmaterial.service.ReceiptService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.SystemException;
import com.eray.thjw.po.BaseEntity;

/**
 * 收货控制器
 * @author hanwu
 *
 */
@Controller
@RequestMapping("/aerialmaterial/receipt")
public class ReceiptController extends BaseController {
	
	@Autowired
	private ReceiptService receiptService;
	
	/** 新增 */
	private static final String FORWARD_TYPE_ADD = "1";
	
	/** 修改 */
	private static final String FORWARD_TYPE_EDIT = "2";
	
	/** 查看 */
	private static final String FORWARD_TYPE_VIEW = "3";
	
	/**
	 * 跳转至收货主页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:receipt:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Model model) throws BusinessException {
		return "material/receipt/receipt_main";
	}
	
	/**
	 * 收货分页查询
	 * @param receipt
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPage", method = RequestMethod.POST)
	public Map<String, Object> queryPage(@RequestBody ReceiptSheet receipt) throws BusinessException{
		try {
			return receiptService.queryPage(receipt);
		} catch (Exception e) {
			throw new BusinessException("收货分页查询失败!", e);
		}
	}
	
	/**
	 * 跳转至新增收货单页面
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:receipt:add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_ADD);
			return new ModelAndView("material/receipt/receipt_detail", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至收货单新增页面失败!",e);
		}
	}
	
	/**
	 * 跳转至修改收货单页面
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:receipt:edit")
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_EDIT);
			model.put("receipt", receiptService.selectByPrimaryKey(id));
			return new ModelAndView("material/receipt/receipt_detail", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至收货修改增页面失败!",e);
		}
	}
	
	/**
	 * 跳转至查看收货单页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_VIEW);
			model.put("receipt", receiptService.selectByPrimaryKey(id));
			return new ModelAndView("material/receipt/receipt_detail", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至收货单查看页面失败!",e);
		}
	}
	
	/**
	 * 收货页面部件分页查询
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryComponentPage", method = RequestMethod.POST)
	public Map<String, Object> queryComponentPage(@RequestBody BaseEntity baseEntity) throws BusinessException{
		try {
			return receiptService.queryComponentPage(baseEntity);
		} catch (Exception e) {
			throw new BusinessException("部件分页查询失败!", e);
		}
	}
	
	/**
	 * 保存收货单
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestBody ReceiptSheet receipt) throws BusinessException{
		try {
			return receiptService.save(receipt);
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			throw new BusinessException("保存收货单失败!", e);
		}
	}
	
	/**
	 * 提交收货单
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(@RequestBody ReceiptSheet receipt) throws BusinessException{
		try {
			return receiptService.doSubmit(receipt);
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			throw new BusinessException("提交收货单失败!", e);
		}
	}
	
	/**
	 * 作废收货单
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@Privilege(code="aerialmaterial:receipt:scrap")
	@RequestMapping(value = "/scrap", method = RequestMethod.POST)
	public void scrap(@RequestBody ReceiptSheet receipt) throws BusinessException{
		try {
			receiptService.doScrap(receipt.getId());
		} catch (SystemException e) {
			throw new BusinessException(e.getMessage(), e);
		}catch (Exception e) {
			throw new BusinessException("作废收货单失败!", e);
		}
	}
	
	/**
	 * 撤销收货单
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@Privilege(code="aerialmaterial:receipt:revoke")
	@RequestMapping(value = "/revoke", method = RequestMethod.POST)
	public void revoke(@RequestBody ReceiptSheet receipt) throws BusinessException{
		try {
			receiptService.doRevoke(receipt.getId());
		} catch (SystemException e) {
			throw new BusinessException(e.getMessage(), e);
		}catch (Exception e) {
			throw new BusinessException("撤销收货单失败!", e);
		}
	}
	
	/**
	 * 加载收货单数据
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/loadDetail", method = RequestMethod.POST)
	public ReceiptSheet loadDetail(@RequestBody ReceiptSheet receipt) throws BusinessException{
		try {
			return receiptService.queryById(receipt.getId());
		} catch (Exception e) {
			throw new BusinessException("加载收货单数据失败!", e);
		}
	}
	
	
}
