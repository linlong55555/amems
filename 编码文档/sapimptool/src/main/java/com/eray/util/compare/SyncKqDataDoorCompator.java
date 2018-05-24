package com.eray.util.compare;

import java.util.Comparator;

import com.eray.pbs.po.SyncKqDataDoor;

/**
 * 
 * SyncKqDataDoor 比较器 2016.07.25
 * 将同步到的数据，将recID（记录号）从小到大，一次排列
 * @author ganqing
 *
 */
public class SyncKqDataDoorCompator implements Comparator<SyncKqDataDoor> {

	@Override
	public int compare(SyncKqDataDoor o1, SyncKqDataDoor o2) {
		if (o1.equals(o2)) {
			return 0;
		} else if (o1.getRecID() - o2.getRecID() > 0) {
			return 1;
		}
		return -1;
	}
	
	/*public static void main(String[] args) {
		SyncKqDataDoor o1  = new SyncKqDataDoor();
		o1.setRecID(5);
		
		SyncKqDataDoor o2  = new SyncKqDataDoor();
		o2.setRecID(2);
		
		SyncKqDataDoor o3  = new SyncKqDataDoor();
		o3.setRecID(9);
		
		List<SyncKqDataDoor> doors = new ArrayList<SyncKqDataDoor>();
		doors.add(o1);
		doors.add(o2);
		doors.add(o3);
	//	SyncKqDataDoorCompator compator = new SyncKqDataDoorCompator();
		
		Collections.sort(doors, new SyncKqDataDoorCompator());
		System.out.println("===================");
		for (SyncKqDataDoor door : doors) {
			System.out.println("==" + door.getRecID());
		}
		Integer i = Integer.valueOf("0930");
		System.out.println(i);
	}*/

	

}
