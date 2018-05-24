 package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.Date;
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

import com.eray.thjw.aerialmaterial.po.BorrowApply;
import com.eray.thjw.aerialmaterial.po.BorrowApplyDetail;
import com.eray.thjw.aerialmaterial.service.BorrowApplyDetailService;
import com.eray.thjw.aerialmaterial.service.BorrowApplyService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.ReserveStatusEnum;
import enu.aerialmaterial.ContractStatusEnum;
import enu.aerialmaterial.SecondmentTypeEnum;

/**
 * 
 * 借入申请 
 * @author ll
 */
@Controller
@RequestMapping("/aerialmaterial/borrow")
public class BorrowApplyController {
	
	@Resource
	private BorrowApplyService borrowApplyService;
	
	@Resource
	private BorrowApplyDetailService borrowApplyDetailService; 
	
	/**
	 * 跳转至借入申请管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:borrow:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		return new ModelAndView("/material/borrow/borrow_main");
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 借入申请列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "borrowApplyList", method = RequestMethod.POST)
	public Map<String, Object> borrowApplyList(@RequestBody BorrowApply borrowApply,HttpServletRequest request,Model model) throws BusinessException{
		String id = borrowApply.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		borrowApply.setId(null);
		borrowApply.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
		borrowApply.getParamsMap().put("userType",SessionUtil.getUserFromSession(request).getUserType());
		
		PageHelper.startPage(borrowApply.getPagination());
		List<BorrowApply> list = borrowApplyService.queryAllPageListjie(borrowApply);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					BorrowApply borrowApply1 = new BorrowApply();
					borrowApply1.setId(id);
					List<BorrowApply> newRecordList = borrowApplyService.queryAllPageListjie(borrowApply1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, borrowApply.getPagination());
		}else{
			List<BorrowApply> newRecordList = new ArrayList<BorrowApply>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				BorrowApply borrowApply1 = new BorrowApply();
				borrowApply1.setId(id);
				newRecordList = borrowApplyService.queryAllPageListjie(borrowApply1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, borrowApply.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, borrowApply.getPagination());
		}
	}
	
	/**
	 * @author ll
	 * @description 初始化增加借入申请
	 * @param request,response
	 * @return 页面视图
	 * @develop date 2016.10.11
	 *
	 */
	@Privilege(code="aerialmaterial:borrow:main:01")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<Map<String, Object>> map=	SecondmentTypeEnum.enumToListMap();
		model.put("secondmenttype", map);
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/borrow/borrow_add", model);
	}
	
	
	/**
	 * 查询待入库借入申请单，借入申请状态为  2提交、3核实
	 * @param baseEntity
	 */
	@RequestMapping(value="/list/toInstock", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> listToInstock(@RequestBody BaseEntity baseEntity){
		return this.borrowApplyService.queryPage4Instock(baseEntity);
	}
	
	
	/**
	 * @author ll
	 * @description 保存借入单
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "addSave", method = RequestMethod.POST)
	public String add(@RequestBody BorrowApply borrowApply) throws BusinessException{
		try {
			borrowApply.setZt(ReserveStatusEnum.SAVE.getId());
			return borrowApplyService.query(borrowApply);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 提交借入单
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "addSubmit", method = RequestMethod.POST)
	public String addSubmit(@RequestBody BorrowApply borrowApply) throws BusinessException{
		try {
			borrowApply.setZt(ReserveStatusEnum.SUBMIT.getId());
			return borrowApplyService.query(borrowApply);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 检查借入单编辑权限
	 * @param request,id
	 * @return
	 * @develop date 2016.10.13
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkEdit", method = RequestMethod.POST)
	public void checkEdit(HttpServletRequest request,String id) throws BusinessException {
		try {
			borrowApplyService.checkEdit(id);
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
	@Privilege(code="aerialmaterial:borrow:main:02")
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(Model model,String id,HttpServletRequest request) throws BusinessException{
		try {
			model.addAttribute("borrowMain", borrowApplyService.selectByPrimaryKey(id));
			List<Map<String, Object>> map=	SecondmentTypeEnum.enumToListMap();
			model.addAttribute("secondmenttype", map);
			model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("初始化编辑借入单失败!");
		}finally{}
		return new ModelAndView("material/borrow/borrow_edit");
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
	public List<BorrowApplyDetail> queryDetailListByMainId(String mainid,HttpServletRequest request)throws BusinessException {
		List<BorrowApplyDetail> list = new ArrayList<BorrowApplyDetail>();
		try {
			list = borrowApplyDetailService.queryReserveDetailListByMainId(mainid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询数据失败");
		}
		return list;
	}
	
	/**
	 * @author ll
	 * @description 编辑后保存借入单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "editSave", method = RequestMethod.POST)
	public String editSave(@RequestBody BorrowApply borrowApply) throws BusinessException{
		try {
			BorrowApply borrowApply1=borrowApplyService.selectByPrimaryKey(borrowApply.getId());
			if(borrowApply1.getZt()==1){
			}else{
				throw new BusinessException("当前申请单状态为"+ContractStatusEnum.getName(borrowApply1.getZt())+",不可保存");
			}
			return borrowApplyService.queryedit(borrowApply);	
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 编辑后提交借入单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "editSubmit", method = RequestMethod.POST)
	public void editSubmit(@RequestBody BorrowApply borrowApply) throws BusinessException{
		try {
			
			BorrowApply borrowApply1=borrowApplyService.selectByPrimaryKey(borrowApply.getId());
			if(borrowApply1.getZt()==1){
			}else{
				throw new BusinessException("当前申请单状态为"+ContractStatusEnum.getName(borrowApply1.getZt())+",不可提交");
			}
			borrowApply.setZt(ReserveStatusEnum.SUBMIT.getId());
			borrowApplyService.queryedit(borrowApply);
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
	@Privilege(code="aerialmaterial:borrow:main:03")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request,String id) throws BusinessException{
		try {
			borrowApplyService.cancel(id);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 指定结束
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:borrow:main:04")
	@ResponseBody
	@RequestMapping(value = "shutDown", method = RequestMethod.POST)
	public void shutDown(@RequestBody BorrowApply borrowApply) throws BusinessException{
		try {
			borrowApply.setZt(ReserveStatusEnum.CLOSE.getId());
			borrowApply.setZdjsrq(new Date());
			borrowApplyService.updateByPrimaryKeySelective(borrowApply);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	
	/**
	 * @author ll
	 * @description 查看借入单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.09.19
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id,HttpServletRequest request) throws BusinessException{
		try {
			model.addAttribute("borrowMain", borrowApplyService.selectByPrimaryKey(id));
			
			List<Map<String, Object>> map=	SecondmentTypeEnum.enumToListMap();
			model.addAttribute("secondmenttype", map);
			model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("初始化编辑借入单失败!");
		}finally{}

		return new ModelAndView("material/borrow/borrow_view");
	}
}
