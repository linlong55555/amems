package com.eray.thjw.baseinfo.rule.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.framework.saibong.dao.SaibongSnMapper;
import com.eray.framework.saibong.po.SaibongSn;
import com.eray.thjw.baseinfo.dao.ProjectMapper;
import com.eray.thjw.baseinfo.po.Project;
import com.eray.thjw.baseinfo.rule.ProjectCodeRuleable;
import com.eray.thjw.exception.BusinessException;

/** 
 * @Description 江苏公务机项目编号生成方式
 * @CreateTime 2018-1-25 上午10:18:25
 * @CreateBy 甘清	
 */
@Service
public class ProjectCodeJStor implements ProjectCodeRuleable {
	
	@Resource
	private SaibongSnMapper saibongSnMapper; //采番数据
	
	@Resource
	private ProjectMapper projectMapper; //项目
	
	@Override
	public synchronized String createProjectCode(Project project) throws BusinessException {
		SaibongSn sn = saibongSnMapper.getSaibongSn4ProjectCode(project.getDprtcode(), CFKEY);
		String projectCode = "";
		if (sn == null) {  //初始化处理  
			projectCode = "AAA";
		} else {
			projectCode = this.getLaterLetter(sn.getDjh()); 
		}
		project.setXmbm(projectCode);
		List<Project> projects = projectMapper.checkProject(project);
		if (projects != null && projects.size() > 0) { //采番号已经存在处理
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				projectCode = this.getLaterLetter(projectCode);
				project.setXmbm(projectCode);
				projects = projectMapper.checkProject(project);
				if (projects != null && projects.size() > 0) {
					continue;
				}
				return projectCode;
			}
		} 
		return projectCode; 
	}
	/**
	 * @Description 获得字母
	 * @CreateTime 2018-1-25 上午10:30:23
	 * @CreateBy 甘清
	 * @param letterLength 获得字母长度
	 * @return 生成的字母
	 */
	private String getLetter(int letterLength) {
		char letter = 'A';
		for (int i = 0 ; i <= 25; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append((char)(letter + i));
			for (int j = 0; j <= 25; j++) {
				sb.append((char)(letter + j));
				for (int k = 0; k <= 25; k++) {
					sb.append((char)(letter + k));
					//return sb.toString();
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			sb = new StringBuilder();
		}
		return null;
	}
	
	/**
	 * @Description 获得letter之后的一个字符组合
	 * @CreateTime 2018-1-25 上午11:43:06
	 * @CreateBy 甘清
	 * @param letter
	 * @return 字母组合
	 */
	private String getLaterLetter(String letters) {
		Map<Integer, String> map = null;
		char[] array = letters.toCharArray();
		for (int i = array.length - 1; i >= 0; i--) {
			char c = array[i];
			if (c < 'Z') {
				 map = new HashMap<Integer, String>();
				 map.put(i, String.valueOf((char)(c + 1)));
				 break;
			}
			continue;
		}
		if (map == null) { //如果全部为Z，自动加1进位变为A，如ZZZ的下一个取值为AAAA
			StringBuilder codes = new StringBuilder();
			for (int j = 0; j <= array.length; j ++) {
				codes = codes.append("A");
			}
			return codes.toString();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = array.length - 1; i >= 0; i--) {
			if (map.get(i) == null) {
				sb.append(array[i]);
			} else {
				sb.append(map.get(i));
			}
		}
		return sb.reverse().toString();
	}
	/**
	 * @Description 检查项目编码是否符合字母规范
	 * @CreateTime 2018-1-25 上午11:51:54
	 * @CreateBy 甘清
	 * @param code
	 * @return
	 */
	private boolean checkCode(String code) {
		String regEx = "^[A-Z]+$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(code);
		return matcher.matches();
	}

}
