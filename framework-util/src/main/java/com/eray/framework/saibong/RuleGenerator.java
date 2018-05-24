package com.eray.framework.saibong;

import java.util.HashMap;
import java.util.Map;

import com.eray.framework.enu.HexEnum;
import com.eray.framework.enu.PosiEnum;
import com.google.gson.Gson;

/** 
 * @Description 采番规则内容生成
 * @CreateTime 2018年1月12日 上午9:40:15
 * @CreateBy 徐勇	
 */
public class RuleGenerator {
	
	public static void main(String[] args) {
		RuleGenerator ruleGenerator = new RuleGenerator();
		ruleGenerator.testFXJL();//飞行记录本
		ruleGenerator.testGDH();//飞行记录本
	}
	
	/**
	 * @Description 飞行记录本采番
	 * @CreateTime 2018年1月17日 下午2:19:20
	 * @CreateBy 徐勇
	 */
	@SuppressWarnings("unchecked")
	private void testFXJL(){
		Map map = new HashMap();
		map.put("sn:1", get$S("FLB", false));
		map.put("sn:2", get$S("-",false));
		map.put("sn:3", get$D("fjzch", -4, 0, null, null, true));
		map.put("sn:4", get$S("-",false));
		map.put("sn:5", get$N(HexEnum.D, PosiEnum.B, "0", 5));
		Gson gson = new Gson();
		String s = gson.toJson(map);
		System.out.println("飞行记录本FXJL");
		System.out.println(s);
	}
	
	private void testGDH(){
		Map map = new HashMap();
		Map pattern = new HashMap();
		pattern.put("1", 0);
		pattern.put("2", 1);
		map.put("sn:1", get$D("fjzch", null, null, pattern, "0", true));
		map.put("sn:2", get$N(HexEnum.D, PosiEnum.B, "0", 5));
		Gson gson = new Gson();
		String s = gson.toJson(map);
		System.out.println("江苏公务机工单号FXJL");
		System.out.println(s);
	}
	
	
	/**
	 * @Description 计算固定文字
	 * @CreateTime 2018年1月12日 下午2:04:23
	 * @CreateBy 徐勇
	 * @param content 内容
	 * @param isJoin 是否参与流水运算
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map get$S(String content, boolean isJoin){
		Map map = new HashMap();
		map.put("type", "$S");
		map.put("cont", content);
		map.put("join", isJoin?"Y":"N");
		return map;
	}
	
	/**
	 * @Description 计算时间
	 * @CreateTime 2018年1月12日 下午2:07:00
	 * @CreateBy 徐勇
	 * @param content 计算时间格式
	 * @param isJoin 是否参与流水运算
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map get$T(String content, boolean isJoin){
		Map map = new HashMap();
		map.put("type", "$T");
		map.put("cont", content);
		map.put("join", isJoin?"Y":"N");
		return map;
	}
	
	/**
	 * @Description 计算流水
	 * @CreateTime 2018年1月12日 下午2:10:28
	 * @CreateBy 徐勇
	 * @param hexEnu 进制
	 * @param posiEnum 填充方式
	 * @param fill 填充位置
	 * @param len 长度
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map get$N(HexEnum hexEnu, PosiEnum posiEnum, String fill, int len){
		Map map = new HashMap();
		map.put("type", "$N");
		map.put("join", "N");
		Map contMap = new HashMap();
		contMap.put("hex", hexEnu.toString());
		contMap.put("posi", posiEnum.toString());
		contMap.put("fill", fill);
		contMap.put("len", len);
		map.put("cont", contMap);
		return map;
	}
	
	/**
	 * @Description 生成动态文字
	 * @CreateTime 2018年1月12日 下午2:41:32
	 * @CreateBy 徐勇
	 * @param field entity中字段
	 * @param start 截取
	 * @param end 截取
	 * @param pattern 替换
	 * @param pattern_default 默认替换
	 * @param isJoin 是否参与流水计算
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map get$D(String field, Integer start, Integer end, Map pattern, String pattern_default, boolean isJoin){
		Map map = new HashMap();
		map.put("type", "$D");
		map.put("join", isJoin?"Y":"N");
		Map contMap = new HashMap();
		contMap.put("field", field);
		contMap.put("start", start);
		contMap.put("end", end);
		contMap.put("pattern-default", pattern_default);
		contMap.put("pattern", pattern);
		map.put("cont", contMap);
		return map;
	}
	
	
}
