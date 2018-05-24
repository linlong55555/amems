package com.eray.thjw.material2.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ToolBorrowReturnRecord;
import com.eray.thjw.material2.service.ToolBorrowReturnRecordService;

/**
 * @author 裴秀
 * @description 工具/设备借归记录
 */
@Controller
@RequestMapping("material/toolborrowreturnlist")
public class ToolBorrowReturnListController extends BaseController{
	
	@Resource
	private ToolBorrowReturnRecordService toolBorrowReturnRecordService;
	
	/**
	 * @Description 工具/设备借归记录
     * @CreateTime 2018年03月01日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:toolborrowreturnlist:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/toolborrowreturnlist/toolborrowreturnlist_main",model);
	}
	
	/**
	 * @Description 查询工具/设备借归记录列表
	 * @CreateTime 2018年4月3日 下午3:27:21
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Map<String, Object> queryList(@RequestBody ToolBorrowReturnRecord record) throws BusinessException{
		try {
			return toolBorrowReturnRecordService.queryPageableList(record);
		} catch (Exception e) {
			throw new BusinessException("查询工具/设备借归记录列表失败!",e);
		}
	}
	
	/**
	 * @Description 导出工具/设备借归记录
	 * @CreateTime 2018年4月4日 下午2:36:21
	 * @CreateBy 韩武
	 * @param json
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "toolborrowreturnlist.xls")
	public String export(String json, Model model) throws BusinessException {
		try {
			ToolBorrowReturnRecord record = JSON.parseObject(json, ToolBorrowReturnRecord.class);
			List<ToolBorrowReturnRecord> list = toolBorrowReturnRecordService.queryList(record);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "toolborrowreturnlist", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
