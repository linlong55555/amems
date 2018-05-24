/**
 * 
 */
package jedis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

import com.eray.framework.redis.RedisPool;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.produce.po.Workorder;
import com.google.gson.Gson;

/** 
 * @Description 
 * @CreateTime 2017年12月20日 下午10:01:15
 * @CreateBy 
 */
public class JT {

	public BaseEntity entity;
	
	public JT(BaseEntity obj){
		entity = obj;
	}
	
	public JT(){
		entity = null;
	}
	
	/** 
	 * @Description 
	 * @CreateTime 2017年12月20日 下午10:01:16
	 * @CreateBy 
	 * @param args
	 */
	public static void main(String[] args) {
		//new JT().test01();
		//new JT().gsontest2();
//		PlanPerson obj = new PlanPerson();
//		obj.setGzdw("99887766EERRhchu胡黄驰！");
//		new JT(obj).method();
		new JT().redisTest02();
//		new JT().redisSet();
//		new JT().redisGet();
	}
	
	public void redisTest02(){
		Workorder workorder = new Workorder();
		workorder.setFjzch("agsfwfae");
		String dprtcode = "16fef637-2659-4df5-8944-bb7e0605848a";
		String key = "EC225";
		try {
			String v = SNGeneratorFactory.generate(dprtcode, key, workorder);
			System.out.println(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void redisGet(){
		String dprtcode = "16fef637-2659-4df5-8944-bb7e0605848a";
		String key = "EC225";
		StringBuffer keysb = new StringBuffer();
		keysb.append("SNR:").append(dprtcode);
		Jedis jedis = RedisPool.getInstance().getJedisResourceWithAuth();
		String v = jedis.hget(keysb.toString(), key);
		System.out.println("key="+keysb.toString()+",field="+key);
		System.out.println("当前数据为:");
		System.out.println(v);
		jedis.close();
	}
	
	private void redisSet(){
		String dprtcode = "16fef637-2659-4df5-8944-bb7e0605848a";
		String key = "EC225";
		String value = gsonData();
		StringBuffer keysb = new StringBuffer();
		keysb.append("SNR:").append(dprtcode);
		Jedis jedis = RedisPool.getInstance().getJedisResourceWithAuth();
		jedis.hset(keysb.toString(), key, value);
//		jedis.set("009","hello hchu!  1122334455");
//		System.out.println(jedis.bgsave());
		String v = jedis.hget(keysb.toString(), key);
		System.out.println("key="+keysb.toString()+",field="+key);
		System.out.println("当前数据为:");
		System.out.println(v);
		jedis.close();
	}
	
	private String gsonData(){
		Map map,temp_map,temp_map2;
		Map retmap = new HashMap();
		/* 时间格式 */
		map = new HashMap();
		map.put("type", "$T");
		map.put("join", "Y");
		map.put("cont", "YYYYMMDD");
		retmap.put("sn:1",map);
		
		/* 流水信息 */
		map = new HashMap();
		map.put("type", "$N");
		map.put("join", "N");
		temp_map = new HashMap();
		temp_map.put("len", 6);
		temp_map.put("fill", "0");
		temp_map.put("posi", "B");
		temp_map.put("hex", "D");
		map.put("cont", temp_map);
		retmap.put("sn:4",map);
		
		/* 固定字符串 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "NRC");
		retmap.put("sn:2",map);
		
		/* 数据转换 */
		map = new HashMap();
		map.put("type", "$D");
		map.put("join", "Y");
		temp_map = new HashMap();
		temp_map.put("field", "fjzch");
		temp_map.put("start", 0);
		temp_map.put("end", -1);
		temp_map2 = new HashMap();
		temp_map2.put("1", "RNT");
		temp_map2.put("2", "EO");
		temp_map2.put("3", "NRC");
		temp_map2.put("4", "NRC");
		temp_map2.put("5", "NRC");
		temp_map2.put("9", "FLB");
		temp_map.put("pattern", temp_map2);
		temp_map2.put("pattern-default", "");
		map.put("cont", temp_map);
		retmap.put("sn:3",map);
		
		Gson gson = new Gson();
		String s = gson.toJson(retmap);
		System.out.println(s);
		return s;
	}
	
	private void redisTest01(){
//		Jedis jedis = RedisPool.getInstance().getJedisResourceWithAuth();
//		jedis.set("009","hello hchu!  1122334455");
//		System.out.println(jedis.bgsave());
//		String value = jedis.get("009");
//		System.out.println(value);
//		value = jedis.clientList();
//		System.out.println(value);
//		jedis.close();
	}
	
	private JT test01(){
		System.out.println(new HashMap().get("hchu"));
		return new JT();
	}
	
	
	
	private void gsontest(){
		List list = new ArrayList();
		List list2 = new ArrayList();
		Map map,temp_map,temp_map2;
		/* 时间格式 */
		map = new HashMap();
		map.put("sn:1", "$T");
		map.put("join", "Y");
		map.put("cont", "YYYYMMDDHH24MI");
		list.add(map);
		
		/* 流水信息 */
		map = new HashMap();
		map.put("sn:2", "$N");
		map.put("join", "N");
		temp_map = new HashMap();
		temp_map.put("len", 6);
		temp_map.put("fill", "0");
		temp_map.put("posi", "B");
		temp_map.put("hex", "D");
		map.put("cont", temp_map);
		list.add(map);
		
		/* 固定字符串 */
		map = new HashMap();
		map.put("sn:3", "$S");
		map.put("join", "Y");
		map.put("cont", "NRC");
		list.add(map);
		
		/* 数据转换 */
		map = new HashMap();
		map.put("sn:4", "$D");
		map.put("join", "Y");
		temp_map = new HashMap();
		temp_map.put("field", "fjzch");
		temp_map.put("start", 0);
		temp_map.put("end", -1);
		temp_map2 = new HashMap();
		temp_map2.put("1", "RNT");
		temp_map2.put("2", "EO");
		temp_map2.put("3", "NRC");
		temp_map2.put("4", "NRC");
		temp_map2.put("5", "NRC");
		temp_map2.put("9", "FLB");
		temp_map2.put("default", "");
		temp_map.put("pattern", temp_map2);
		map.put("cont", temp_map);
		list.add(map);
		
		Gson gson = new Gson();
		String s = gson.toJson(list);
		System.out.println(s);
		
		list2 = gson.fromJson(s, List.class);
		System.out.println(list2.size());
		System.out.println(((Map)((Map)((Map)list2.get(3)).get("cont")).get("pattern")).get("2"));
	}
	
	private void gsontest2(){
		Map map,temp_map,temp_map2;
		Map retmap = new HashMap();
		/* 时间格式 */
		map = new HashMap();
		map.put("type", "$D");
		map.put("join", "Y");
		map.put("cont", "YYYYMMDDHH24MI");
		retmap.put("sn:1",map);
		
		/* 流水信息 */
		map = new HashMap();
		map.put("type", "$N");
		map.put("join", "N");
		temp_map = new HashMap();
		temp_map.put("len", 6);
		temp_map.put("fill", "0");
		temp_map.put("posi", "B");
		temp_map.put("hex", "D");
		map.put("cont", temp_map);
		retmap.put("sn:2",map);
		
		/* 固定字符串 */
		map = new HashMap();
		map.put("type", "$S");
		map.put("join", "Y");
		map.put("cont", "NRC");
		retmap.put("sn:3",map);
		
		/* 数据转换 */
		map = new HashMap();
		map.put("type", "$D");
		map.put("join", "Y");
		temp_map = new HashMap();
		temp_map.put("field", "fjzch");
		temp_map.put("start", 0);
		temp_map.put("end", -1);
		temp_map2 = new HashMap();
		temp_map2.put("1", "RNT");
		temp_map2.put("2", "EO");
		temp_map2.put("3", "NRC");
		temp_map2.put("4", "NRC");
		temp_map2.put("5", "NRC");
		temp_map2.put("9", "FLB");
		temp_map.put("pattern", temp_map2);
		temp_map2.put("pattern-default", "");
		map.put("cont", temp_map);
		retmap.put("sn:4",map);
		
		Gson gson = new Gson();
		String s = gson.toJson(retmap);
		System.out.println(s);
		
		retmap = gson.fromJson(s.trim(), Map.class);
		System.out.println(retmap);
	}

	public void method(){
		try{
	        System.out.println(entity.getClass().toString());
	        Field field = entity.getClass().getDeclaredField("hhhc");//获得属性  
	        field.setAccessible(true);
	        System.out.println(field.get(entity));
		} catch (Exception e) {  
	        e.printStackTrace();  
	    } 
	}
}
