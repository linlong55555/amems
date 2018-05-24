package com.eray.pbs.util;

import java.util.HashMap;
import java.util.Map;

public class StageMap
{
	private static StageMap instance;
	private static Map<String,String> stageMap;
	
	private StageMap(){
	}
	
	public static StageMap getStageMap(){
		if(instance==null){
			instance=new StageMap();
			stageMap=new HashMap<String,String>();
		}
		return instance;
	}
	
	public void putStage(String stage,String stageDescription){
		stageMap.put(stage, stageDescription);
	}

	public String getStageDescription(String stage)
    {
	    return stageMap.get(stage);
    }
}
