package com.eray.pbs.util;

import java.util.Comparator;

import com.eray.pbs.po.SyncKqDataDoor;

/**
 * 对考勤中的数据进行一次按时间先后顺序排序 2016.11.26
 * @author ganqing
 *
 */
public class SyncKqDataDoorCompare implements Comparator<SyncKqDataDoor> {

	@Override
	public int compare(SyncKqDataDoor o1, SyncKqDataDoor o2) {
		if (o1.getRecID() - o2.getRecID() > 0) {
			return 1;
		}
		return -1;
	}
	/*public static void main(String[] args) {  
		List<SyncKqDataDoor> doors = new ArrayList<SyncKqDataDoor>();
		
		SyncKqDataDoor d1 = new SyncKqDataDoor();
		d1.setRecID(12);
		
		SyncKqDataDoor d2 = new SyncKqDataDoor();
		d2.setRecID(10);
		
		SyncKqDataDoor d3 = new SyncKqDataDoor();
		d3.setRecID(16);
		
		doors.add(d1);
		doors.add(d2);
		doors.add(d3);
		Collections.sort(doors, new SyncKqDataDoorCompare());
        for (SyncKqDataDoor d : doors){
        	System.out.println(d.getRecID());
        }
	}*/

}
