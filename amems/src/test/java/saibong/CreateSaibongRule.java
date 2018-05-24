/**
 * 
 */
package saibong;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/** 
 * @Description 
 * @CreateTime 2018年1月22日 下午5:14:31
 * @CreateBy 胡黄驰
 */
public class CreateSaibongRule {
	public static void main(String[] args) {
		Gson gson = new Gson();
		new CreateSaibongRule().xmbld(gson);
	}
	
	/**
	 * 故障保留单
	 * */
	private String ddbld(Gson gson){
		String ret;
		Map map,temp_map,temp_map2;
		Map retmap = new HashMap();
		
		/* part1：固定字符串 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "DD");
		retmap.put("sn:1",map);
		
		/* part2：连接字符 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "-");
		retmap.put("sn:2",map);
		
		/* part3：时间格式 */
		map = new HashMap();
		map.put("type", "$T");
		map.put("join", "Y");
		map.put("cont", "YYYYMM");
		retmap.put("sn:3",map);
		
		/* part4：连接字符 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "-");
		retmap.put("sn:4",map);
		
		/* part5：流水信息 */
		map = new HashMap();
		map.put("type", "$N");
		map.put("join", "N");
		temp_map = new HashMap();
		temp_map.put("len", 3);
		temp_map.put("fill", "0");
		temp_map.put("posi", "B");
		temp_map.put("hex", "D");
		map.put("cont", temp_map);
		retmap.put("sn:5",map);
				
		ret = gson.toJson(retmap);
		System.out.println(ret);
		return ret;
	}
	
	/**
	 * 项目保留单
	 * */
	private String xmbld(Gson gson){
		String ret;
		Map map,temp_map,temp_map2;
		Map retmap = new HashMap();
		
		/* part1：固定字符串 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "IR");
		retmap.put("sn:1",map);
		
		/* part2：连接字符 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "-");
		retmap.put("sn:2",map);
		
		/* part3：时间格式 */
		map = new HashMap();
		map.put("type", "$T");
		map.put("join", "Y");
		map.put("cont", "YYYYMM");
		retmap.put("sn:3",map);
		
		/* part4：连接字符 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "-");
		retmap.put("sn:4",map);
		
		/* part5：流水信息 */
		map = new HashMap();
		map.put("type", "$N");
		map.put("join", "N");
		temp_map = new HashMap();
		temp_map.put("len", 3);
		temp_map.put("fill", "0");
		temp_map.put("posi", "B");
		temp_map.put("hex", "D");
		map.put("cont", temp_map);
		retmap.put("sn:5",map);
				
		ret = gson.toJson(retmap);
		System.out.println(ret);
		return ret;
	}
	
	/**
	 * 缺陷保留单
	 * */
	private String qxbld(Gson gson){
		String ret;
		Map map,temp_map,temp_map2;
		Map retmap = new HashMap();
		
		/* part1：固定字符串 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "DR");
		retmap.put("sn:1",map);
		
		/* part2：连接字符 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "-");
		retmap.put("sn:2",map);
		
		/* part3：时间格式 */
		map = new HashMap();
		map.put("type", "$T");
		map.put("join", "Y");
		map.put("cont", "YYYYMM");
		retmap.put("sn:3",map);
		
		/* part4：连接字符 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "-");
		retmap.put("sn:4",map);
		
		/* part5：流水信息 */
		map = new HashMap();
		map.put("type", "$N");
		map.put("join", "N");
		temp_map = new HashMap();
		temp_map.put("len", 3);
		temp_map.put("fill", "0");
		temp_map.put("posi", "B");
		temp_map.put("hex", "D");
		map.put("cont", temp_map);
		retmap.put("sn:5",map);
				
		ret = gson.toJson(retmap);
		System.out.println(ret);
		return ret;
	}
}
