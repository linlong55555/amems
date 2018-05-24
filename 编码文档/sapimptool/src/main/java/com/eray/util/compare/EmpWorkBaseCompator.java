package com.eray.util.compare;

import java.util.Comparator;

import com.eray.pbs.po.EmpWorkBase;

/**
 * 将EmpWorkBase的内容按照ID从小到大的培训 2016.08.01
 * @author ganqing
 *
 */
public class EmpWorkBaseCompator implements Comparator<EmpWorkBase>{

	@Override
	public int compare(EmpWorkBase o1, EmpWorkBase o2) {
		if (o1.equals(o2)) {
			return 0;
		} else if (o1.getId() - o2.getId() > 0) {
			return 1;
		}
		return -1;
	}

}
