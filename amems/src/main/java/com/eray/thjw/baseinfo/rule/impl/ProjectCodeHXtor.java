package com.eray.thjw.baseinfo.rule.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.baseinfo.dao.ProjectMapper;
import com.eray.thjw.baseinfo.po.Project;
import com.eray.thjw.baseinfo.rule.ProjectCodeRuleable;
import com.eray.thjw.dao.DepartmentMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Department;
import com.eray.thjw.system.dao.DictItemMapper;
import com.eray.thjw.system.dao.SynRelMapper;
import com.eray.thjw.system.po.DictItem;
import com.eray.thjw.system.po.SynRel;
import com.eray.thjw.util.DateUtil;

import enu.project2.CustProjectEnum;

/** 
 * @Description 华夏项目编号
 * @CreateTime 2017-12-5 下午4:12:50
 * @CreateBy 甘清	
 */
@Service
public class ProjectCodeHXtor implements ProjectCodeRuleable {
	
	private static final String LM = "LM"; //航线
	
	private static final String PM = "PM"; //定检
	
	private static final String PA = "PA"; //项目评估
	
	private static final String IM = "IM"; //内饰
	
	private static final String RM = "RM"; //飞机拆解
	
	private static final String AA = "AA"; //飞机引进
	
	private static final String PE = "PE"; //设备加工
	
	private static final String EM = "EM"; //设备维修保养
	
	public static final String PT = "PT"; //喷漆
	
	private static final String MS = "MS"; //航材销售
	
	private static final String FJBH = "000"; //默认飞机编号
	
	private static final Integer SUBINDEX = 3; //飞机注册号截取长度

	@Resource
	private DictItemMapper dictItemMapper;  //数据字典Mapper
	
	@Resource
	private ProjectMapper projectMapper;
	
	@Resource
	private SynRelMapper synRelMapper; //同步关系表
	
	@Resource
	private DepartmentMapper departmentMapper; //部门信息表
	
	@Override
	public String createProjectCode(Project project) throws BusinessException {
		StringBuilder codeSb = new StringBuilder();
		Department dept = departmentMapper.findByTopNode(project.getKhid(), "0");
		if (dept != null && StringUtils.isNotEmpty(dept.getDprtcode())) {
			codeSb = codeSb.append(dept.getDprtcode()); //拼接客户代码
		}
		String xmzl = project.getXmzl(); //获得项目种类
		String zmlb = ""; // 项目类别
		//boolean nbAircraftFlag = true; //默认为内部飞机
		if (StringUtils.isNotEmpty(xmzl)) { //项目种类不为空
			List<DictItem> dicts = dictItemMapper.selectByLxidAndDprtcode(LXID, project.getDprtcode());
			if (dicts != null && dicts.size() > 0) {
				for (DictItem dict : dicts) {
					if (xmzl.equals(dict.getSz())) {
						if (StringUtils.isNotEmpty(dict.getMc())) {
							codeSb = codeSb.append(dict.getMc().trim()); //拼接项目种类
							zmlb = dict.getMc().trim(); //存储项目类别
							break;
						}
					}
				}
			}
		}
		codeSb = codeSb.append(DateUtil.getDateStr("yy", new Date())); //拼接年份
		if (zmlb.equals(LM)) {
			if (project.getWbbs().equals(CustProjectEnum.CUST_TYPE_1.getId())) { //外部客户
				SynRel sel = synRelMapper.selectByDxidAndDprtcode(project.getKhid(), project.getDprtcode()); //判断客户是否为第三方客户
				if (sel == null) { //不存在，标识为第三方飞机,Fjbh=航班号最后三位，如果航班号为空则写入000
					codeSb.append(this.formatHBH(project.getHbh())); //第三方航线取飞机编号后三位数字
					//nbAircraftFlag = false; //改变标示为第三方飞机航线
				} else { //存在,标识为内部飞机,Fjbh=飞机编号，如果飞机编号为空则写入000,不为空，则对应为飞机别名:如 001对应B-3376
					codeSb.append(this.formatFjbzm(project));
				}
			} else { //内部飞机
				codeSb = codeSb.append(this.formatFjbzm(project)); //客户种类为外部,Fjbh=飞机编号，如果飞机编号为空则写入000
			}
		} else if (zmlb.equals(PM) || zmlb.equals(PA) || zmlb.equals(IM) || zmlb.equals(RM) || zmlb.equals(PT)) { //取飞机别名
			//if (StringUtils.isNotEmpty(project.getFjzch())) { //飞机注册号不为空
			//	codeSb = codeSb.append(this.formatFjbzm(project));
			//} else { //飞机注册号为空则补000
			//	codeSb = codeSb.append(FJBH);
			//}
			codeSb.append(this.formatFjbzm(project));
		} else if (zmlb.equals(AA) || zmlb.equals(PE) || zmlb.equals(EM) || zmlb.equals(MS)) { //取000
			codeSb = codeSb.append(FJBH);
		} else { //无法区别类型，写000
			codeSb = codeSb.append(FJBH);
		}
		//取流水号
		 //如果是内部飞机,流水号自增长
		Integer count = 0;
		for (int i = 1; i < 100 ;i++){
			if (i < 10) {
				codeSb  = codeSb.append("0").append(i);
			} else {
				codeSb = codeSb.append(i);
			}
			Project param = new Project();
			param.setDprtcode(project.getDprtcode());
			param.setXmbm(codeSb.toString());
			List<Project> result = projectMapper.checkProject(param);
			count = i;
			if (result !=  null && result.size() > 0) { //说明已经存在
				codeSb = codeSb.delete(codeSb.length() - 2, codeSb.length());
				continue;
			} 
			break;
		}
		if (count >= 100) {
			throw new BusinessException("项目编码生成失败");
		}
		 
		
		return codeSb.toString();
	}
	
	/**
	 * @Description 处理飞机航班号
	 * @CreateTime 2017-12-7 下午4:47:55
	 * @CreateBy 甘清
	 * @param hbh 飞机航班号
	 * @return 
	 */
	private String formatHBH(String hbh) {
		if (StringUtils.isEmpty(hbh)) { //航班号为空 则返回000
			return FJBH; 
		}
		if (hbh.length() <= 3) {
			return hbh;
		}
		return hbh.substring(hbh.length() - SUBINDEX, hbh.length()); 
	}
	/**
	 * @Description 处理飞机注册号（如B-3376 对应001）
	 * @CreateTime 2018-1-19 上午11:12:26
	 * @CreateBy 甘清
	 * @param project 项目
	 * @return 飞机对应的备注名 
	 */
	private String formatFjbzm(Project project) {
		if (StringUtils.isNotEmpty(project.getFjzch())) {
			return StringUtils.isNotEmpty(project.getFjbzm()) ? project.getFjbzm() : FJBH;
		}
		return FJBH;
	}
}
