package com.eray.thjw.aerialmaterial.control;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.aerialmaterial.service.ComponentCertificateService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;

/**
 * @Description 部件证书controller
 * @CreateTime 2017年9月28日 上午9:58:18
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/aerialmaterial/certificate")
public class ComponentCertificateController extends BaseController {
	
	@Resource
	private ComponentCertificateService componentCertificateService;
	
	/**
	 * @Description 查询部件证书集合
	 * @CreateTime 2017年9月28日 上午9:59:09
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<ComponentCertificate> queryParentList(@RequestBody ComponentCertificate record) throws BusinessException{
		try {
			return componentCertificateService.queryList(record);
		} catch (Exception e) {
			throw new BusinessException("查询部件证书集合失败!",e);
		}
	}

}
