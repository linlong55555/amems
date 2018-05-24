package com.eray.util;

/**
 * 全局变量.
 * 
 * @author zh
 * @version 1.0 2013-03-20
 */

public class Global
{
    public static final String COMMA = ",";
    public static final String AUTO = "auto";
    /** 存储用户session */
    public static final String USER = "user";
    /** 用户登录时间 */
    public static final String LOGINTIME = "logintime";
    /** 已经完成的project */
    public static final String PROJECTEND = "C";
    /** 进行中的project */
    public static final String PROJECTING = "R";
    /** 等待calim的project */
    public static final String PROJECTSTART = "U";

    /** 增加 */
    public static final String ADD = "0";
    /** 修改 */
    public static final String EDIT = "1";
    /** 删除 */
    public static final String DELETE = "2";

    /** 初始数据 */
    public static final String ORIGINAL = "0";
    /** 外部导入 */
    public static final String EXPORT = "1";
    /** 外部修改 */
    public static final String EXTERNALFORCES = "2";

    /** 默认值有 */
    public static final String DEFALT_YES = "1";
    /** 默认值无 */
    public static final String DEFALT_NO = "0";
    
    public static final String YES = "Y";
    public static final String NO = "N";
    
    public static final String SHIFT_IN = "IN"; //打卡上班
    
    public static final String SHIFT_OUT = "OUT"; //下班打卡

    public static String removeZeroBefore(String str)
    {
        if (str != null && str.length() > 0)
        {
            while (str.startsWith("0"))
            {
            	str = str.substring(1);
            }
        }
        return str;
    }

    public static String getStageByDesc(String description)
    {
        if ("Receiving".equals(description))
        {
            return "ST01";
        } else if ("System functional pretest(Optional)".equals(description))
        {
            return "ST02";
        } else if ("Defuel(Optional)".equals(description))
        {
            return "ST03";
        } else if ("On Jack(Optional)".equals(description))
        {
            return "ST04";
        } else if ("Power Off(Optional)".equals(description))
        {
            return "ST05";
        } else if ("Hydraulic Off(Optional)".equals(description))
        {
            return "ST06";
        } else if ("Pneumatic Off(Optional)".equals(description))
        {
            return "ST07";
        } else if ("Docking/Removal/Opening".equals(description))
        {
            return "ST08";
        } else if ("Cleaning".equals(description))
        {
            return "ST09";
        } else if ("Modification".equals(description))
        {
            return "ST10";
        } else if ("Initial Inspection".equals(description))
        {
            return "ST11";
        } else if ("Non-Rountine".equals(description))
        {
            return "ST12";
        } else if ("Servicing/Replacement/Repair".equals(description))
        {
            return "ST13";
        } else if ("Adjustment/Initial Test".equals(description))
        {
            return "ST14";
        } else if ("Installations/Lubrications/Closing".equals(description))
        {
            return "ST15";
        } else if ("Off Jack(Optional)".equals(description))
        {
            return "ST16";
        } else if ("Power On(Optional)".equals(description))
        {
            return "ST17";
        } else if ("Hydraulic On(Optional)".equals(description))
        {
            return "ST18";
        } else if ("Pneumatic On(Optional)".equals(description))
        {
            return "ST19";
        } else if ("Refuel(Optional)".equals(description))
        {
            return "ST20";
        } else if ("Operational/Functional Test".equals(description))
        {
            return "ST21";
        } else if ("Finals/Leak Checks/Retractions".equals(description))
        {
            return "ST22";
        } else if ("Engines Running Test".equals(description))
        {
            return "ST23";
        } else if ("Compass".equals(description))
        {
            return "ST24";
        } else if ("Test Flight(Optional)".equals(description))
        {
            return "ST25";
        } else if ("Departing".equals(description))
        {
            return "ST26";
        } else
        {
            return null;
        }
    }

    public static String getDescByStage(String stage)
    {
        if (stage == null)
        {
            return stage;
        }
        if ("ST01".equals(stage))
        {
            return "Receiving";
        } else if ("ST02".equals(stage))
        {
            return "System functional pretest(Optional)";
        } else if ("ST03".equals(stage))
        {
            return "Defuel(Optional)";
        } else if ("ST04".equals(stage))
        {
            return "On Jack(Optional)";
        } else if ("ST05".equals(stage))
        {
            return "Power Off(Optional)";
        } else if ("ST06".equals(stage))
        {
            return "Hydraulic Off(Optional)";
        } else if ("ST07".equals(stage))
        {
            return "Pneumatic Off(Optional)";
        } else if ("ST08".equals(stage))
        {
            return "Docking/Removal/Opening";
        } else if ("ST09".equals(stage))
        {
            return "Cleaning";
        } else if ("ST10".equals(stage))
        {
            return "Modification";
        } else if ("ST11".equals(stage))
        {
            return "Initial Inspection";
        } else if ("ST12".equals(stage))
        {
            return "Non-Rountine";
        } else if ("ST13".equals(stage))
        {
            return "Servicing/Replacement/Repair";
        } else if ("ST14".equals(stage))
        {
            return "Adjustment/Initial Test";
        } else if ("ST15".equals(stage))
        {
            return "Installations/Lubrications/Closing";
        } else if ("ST16".equals(stage))
        {
            return "Off Jack(Optional)";
        } else if ("ST17".equals(stage))
        {
            return "Power On(Optional)";
        } else if ("ST18".equals(stage))
        {
            return "Hydraulic On(Optional)";
        } else if ("ST19".equals(stage))
        {
            return "Pneumatic On(Optional)";
        } else if ("ST20".equals(stage))
        {
            return "Refuel(Optional)";
        } else if ("ST21".equals(stage))
        {
            return "Operational/Functional Test";
        } else if ("ST22".equals(stage))
        {
            return "Finals/Leak Checks/Retractions";
        } else if ("ST23".equals(stage))
        {
            return "Engines Running Test";
        } else if ("ST24".equals(stage))
        {
            return "Compass";
        } else if ("ST25".equals(stage))
        {
            return "Test Flight(Optional)";
        } else if ("ST26".equals(stage))
        {
            return "Departing";
        } else
        {
            return stage;
        }
    }

    public static String getModelByType(String aircraftType)
    {
        if (aircraftType == null)
        {
            return null;
        }
        if (aircraftType.startsWith("B767"))
        {
            return "B767";
        } else if (aircraftType.startsWith("B737-600") || aircraftType.startsWith("B737-700")
                || aircraftType.startsWith("B737-800") || aircraftType.startsWith("B737-900"))
        {
            return "B737NG";
        } else if (aircraftType.startsWith("B737-300") || aircraftType.startsWith("B737-400")
                || aircraftType.startsWith("B737-500"))
        {
            return "B737CL";
        } else if (aircraftType.startsWith("B777"))
        {
            return "B777";
        } else if (aircraftType.startsWith("B747"))
        {
            return "B747";
        } else if (aircraftType.startsWith("B787"))
        {
            return "B787";
        } else if (aircraftType.startsWith("B737-100") || aircraftType.startsWith("B737-200"))
        {
            return "B737OG";
        } else if (aircraftType.startsWith("B757"))
        {
            return "B757";
        }else if (aircraftType.startsWith("B744"))
        {
            return "B744";
        }else if (aircraftType.startsWith("B738"))
        {
            return "B738";
        }else{
            return null;
        }
    }
    

    public static String getTypeByModel(String aircraftType)
    {
        if (aircraftType.equals("B767"))
        {
            return " and ( aircrafttype_ like '767%' )";
        }
        else if (aircraftType.equals("B737NG"))
        {
            return " and ( aircrafttype_ like '737-600%'  or aircrafttype_ like '737-700%'  or aircrafttype_ like '737-800%'  or aircrafttype_ like '737-900%' )";
        } 
        else if (aircraftType.equals("B737CL"))
        {
        	 return " and ( aircrafttype_ like '737-300%'  or aircrafttype_ like '737-400%'  or aircrafttype_ like '737-500%' )";
        } 
        else if (aircraftType.equals("B777"))
        {
            return " and ( aircrafttype_ like '777%' )";
        } 
        else if (aircraftType.equals("B747"))
        {
        	 return " and ( aircrafttype_ like '747%' )";
        } 
        else if (aircraftType.equals("B787"))
        {
        	return " and ( aircrafttype_ like '787%' )";
        } 
        else if (aircraftType.equals("B737OG"))
        {
        	return " and ( aircrafttype_ like '737-100%' or aircrafttype_ like '737-200%' )";
        	
        } else if (aircraftType.equals("B757"))
        {
        	return " and ( aircrafttype_ like '757%' )";
        }
        else if (aircraftType.equals("B744"))
        {
        	return " and ( aircrafttype_ like '744%' )";
        }
        else if (aircraftType.equals("B738"))
        {
        	return " and ( aircrafttype_ like '738%' )";
        }
        else
        {
            return null;
        }
    }

//    public static String[] getTypesByModel(String aircraftModel)
//    {
//        if(aircraftModel==null){
//            return null;
//        }
//        if("B767".equals(aircraftModel)){
//            return new String[]{"B767","B767-100","B767-200","B767-200ER","B767-300","B767-300ER","B767-400","B767X","B767-400ER"};
//        }else if("B737NG".equals(aircraftModel)){
//            return new String[]{"B737-600","B737-700","B737-800","B737-900"};
//        }else if("B737CL".equals(aircraftModel)){
//            return new String[]{"B737-300","B737-400","B737-500"};
//        }else if("B777".equals(aircraftModel)){
//            return new String[]{"B777","B777-200","B777-200ER","B777-200LR","B777F","B777-300","B777-300ER"};
//        }else if("B747".equals(aircraftModel)){
//            return new String[]{"B747","B747-100","B747-100SRB","B747-200","B747-200F","B747-300"
//            		,"B747-400"
//            		,"B747-400ERF","B747-400F","B747LCF","B747-8"};
//        }else if("B787".equals(aircraftModel)){
//            return new String[]{"B787","B787-8","B787-9","B777-10"};
//        }else if("B737OG".equals(aircraftModel)){
//            return new String[]{"B737-100","B737-200"};
//        }else if("B757".equals(aircraftModel)){
//            return new String[]{"B757","B757-200","B757-200F","B757-300"};
//        }else if("B744".equals(aircraftModel)){
//            return new String[]{"B744","B747-400"};
//        }else if("B738".equals(aircraftModel)){
//            return new String[]{"B738","B737-800"};
//        }else if("B737".equals(aircraftModel)){
//            return new String[]{"B737","B737-100","B737-200","B737-300","B737-400","B737-500","B737-600","B737-700","B737-800","B737-900"};
//        }
//        return null;
//    }

	public static String getTypeByType(String aircraftType)
    {
		if (aircraftType == null)
        {
            return null;
        }
		if (aircraftType.startsWith("B737-300")||
        		aircraftType.startsWith("B737-400")||
        		aircraftType.startsWith("B737-500"))
        {
            return "B737CL";
        } else if (aircraftType.startsWith("B737-600")||
        		aircraftType.startsWith("B737-700")||
        		aircraftType.startsWith("B737-800")||
        		aircraftType.startsWith("B737-900"))
        {
            return "B737NG";
        } else if (aircraftType.startsWith("B738"))
        {
            return "B738";
        }else if (aircraftType.startsWith("B744"))
        {
            return "B744";
        }else if (aircraftType.startsWith("B747"))
        {
            return "B747";
        }else if (aircraftType.startsWith("B757"))
        {
            return "B757";
        }else if (aircraftType.startsWith("B767"))
        {
            return "B767";
        } else if (aircraftType.startsWith("B777"))
        {
            return "B777";
        }  else if (aircraftType.startsWith("B787"))
        {
            return "B787";
        } else{
            return null;
        }
    }
}
