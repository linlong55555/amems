 package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.eray.thjw.aerialmaterial.po.Outstock;
import com.eray.thjw.aerialmaterial.po.Secondment;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.OtherBorrowApplyDetailService;
import com.eray.thjw.aerialmaterial.service.OtherBorrowApplyService;
import com.eray.thjw.aerialmaterial.service.OutstockDetailService;
import com.eray.thjw.aerialmaterial.service.OutstockService;
import com.eray.thjw.aerialmaterial.service.SecondmentService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;

/**
 * 
 * 其它飞行队借入申请 
 * @author ll
 *
 */
@Controller
@RequestMapping("/otheraerocade/borrow")
public class OtherBorrowApplyController {
	
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private SecondmentService secondmentService;
	
	@Resource
	private OtherBorrowApplyService otherBorrowApplyService;
	
	@Resource
	private OtherBorrowApplyDetailService otherBorrowApplyDetailService;
	
	@Resource
	private OutstockService outstockService;
	
	@Resource
	private OutstockDetailService outstockDetailService;
	
	@Resource
	private DepartmentService departmentService;
	
	/**
	 * 跳转至其它飞行队借入申请管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="otheraerocade:borrow:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<Secondment> newRecordList= secondmentService.queryOtherOrg(ThreadVarUtil.getUser().getJgdm());
		model.put("newRecordList", newRecordList);
		return new ModelAndView("/material/otherborrow/otherborrow_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 其它飞行队借入申请列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "borrowApplyList", method = RequestMethod.POST)
	public Map<String, Object> borrowApplyList(@RequestBody BorrowApply borrowApply,HttpServletRequest request,Model model) throws BusinessException{
		String id = borrowApply.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		borrowApply.setId(null);
		User user=ThreadVarUtil.getUser();
		borrowApply.setUserCode(user.getJgdm());
		borrowApply.setJddxbh(user.getJgdm());
		
		PageHelper.startPage(borrowApply.getPagination());
		List<BorrowApply> list = otherBorrowApplyService.queryAllPageListjie(borrowApply);
		
		long count = ((Page)list).getTotal();
		if(count > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					BorrowApply borrowApply1 = new BorrowApply();
					borrowApply1.setId(id);
					List<BorrowApply> newRecordList = otherBorrowApplyService.queryAllPageListjie(borrowApply1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
		}else{
			List<BorrowApply> newRecordList = new ArrayList<BorrowApply>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				BorrowApply borrowApply1 = new BorrowApply();
				borrowApply1.setId(id);
				newRecordList = otherBorrowApplyService.queryAllPageListjie(borrowApply1);
				if(newRecordList != null && newRecordList.size() == 1){
					list.add(newRecordList.get(0));
					count = 1;
				}
			}
		}
		
		if(list.size() > 0){
			List<BorrowApply> list1=new ArrayList<BorrowApply>();
			List<String>   xgdjids =new ArrayList<String>();
			
			for (BorrowApply borrowApply2 : list) {
				xgdjids.add(borrowApply2.getId());
			}
			List<Outstock> outstock1=outstockService.selectByAll(xgdjids); 
			for (BorrowApply borrowApply2 : list) {
				boolean falg=true;
				for (Outstock outstock : outstock1) {
					if(borrowApply2.getId().equals(outstock.getXgdjid())){
						falg=false;
						borrowApply2.setCkdh(outstock.getCkdh());
						list1.add(borrowApply2);
					}
				}
				if(falg){
					list1.add(borrowApply2);
				}
			}
			return PageUtil.pack((int)count, list1, borrowApply.getPagination());
		}
		
		return PageUtil.pack(0, list, borrowApply.getPagination());
	}
	
	/**
	 * @author ll
	 * @description 初始化其它飞行队出库
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.01
	 *
	 */
	@Privilege(code="otheraerocade:borrow:main:01")
	@RequestMapping(value = "otherborrowHistory", method = RequestMethod.GET)
	public ModelAndView edit(Model model,String id) throws BusinessException{
		try {
			model.addAttribute("departments", departmentService.queryOrg());
			model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
			model.addAttribute("otheraerocade", otherBorrowApplyService.selectById(id));
			model.addAttribute("otheraerocadeDetail", otherBorrowApplyDetailService.queryReserveDetailListByMainId(id));
		} catch (Exception e) {
			throw new BusinessException("初始化其它飞行队申请单失败!",e);
		}finally{}
		return new ModelAndView("material/otherborrow/otherborrow_history");
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 根据bjh查询库存列表列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "inventoryList", method = RequestMethod.POST)
	public Map<String, Object> inventoryList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramsMap.put("userId", user.getId());
		stock.setParamsMap(paramsMap);
		
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.queryAllPageNormalList(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 根据其它飞行队借入申请单出库
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "stockRemoval", method = RequestMethod.POST)
	public Map<String, Object> stockRemoval(@RequestBody Outstock outstock,HttpServletRequest request,Model model) throws BusinessException{
		return	outstockService.stockRemoval(outstock);
	}
	
	/**
	 * @author ll
	 * @description 初始化出库单页面查看页面
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.01
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id,String ckdh) throws BusinessException{
		try {
			User user=ThreadVarUtil.getUser();
			model.addAttribute("departments", departmentService.queryOrg());
			model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
			model.addAttribute("otheraerocade", otherBorrowApplyService.selectById(id));
			model.addAttribute("otheraerocadeDetail", otherBorrowApplyDetailService.queryReserveDetailListByMainId(id));
			Outstock outstock=outstockService.selectById(ckdh,user.getJgdm());
			model.addAttribute("stockRemoval", outstock);
			model.addAttribute("stockRemovalDetail", outstockDetailService.queryKeyList(outstock.getId()));
			
		} catch (Exception e) {
			throw new BusinessException("初始化出库单页面查看页面单失败!",e);
		}finally{}
		return new ModelAndView("material/otherborrow/otherborrow_view");
	}
	
	
	/**
	 * 根据条件查询所有申请航材
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "queryByMaterials", method = RequestMethod.POST)
	public Map<String, Object> queryByMaterials(String  id,HttpServletRequest request,Model model) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<BorrowApplyDetail> list= otherBorrowApplyDetailService.queryReserveDetailListByMainId(id); //查询定检监控主数据
			resultMap.put("rows",list);
		} catch (Exception e) {
			throw new BusinessException("其它飞行队申请单查询失败",e);
		}
		
		return resultMap;
	}
	
}
