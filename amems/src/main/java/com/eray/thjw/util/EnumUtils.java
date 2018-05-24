package com.eray.thjw.util;

public class EnumUtils {
  
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		  String className = "enu.SupervisoryLevelEnum";
//		  String v = EnumUtils.getNameById(className,Integer.valueOf(2));
		  String v = EnumUtils.getNameById(className, "2");
		  System.out.println(v);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getNameById(String className,Object id) throws ClassNotFoundException {
		Class<Enum> clazz = (Class<Enum>) Class.forName(className);  
          Enum[] objs = clazz.getEnumConstants();  
          String idTemp = null;
          for (Enum obj : objs) {  
        	  if (id==null) {
				continue;
        	  }
        	  idTemp = ReflectionUtils.getFieldValue(obj,"id").toString();
              if (idTemp.equals(id.toString().trim())) {
				return ReflectionUtils.getFieldValue(obj,"name").toString();
			  }
          }
          return "";
	}
	
}