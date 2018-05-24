package com.eray.pbs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.vo.RevisonSpentTotalBackSAP;

/**
 * 回传revision的总计工时至FTP
 * @author ganqing
 *
 */
@Component
@Transactional(readOnly = true)
public class RevisonSpentTotalBackSAPService {
	
	public List<RevisonSpentTotalBackSAP> getDatasBackSAP() {
		List<RevisonSpentTotalBackSAP> list = new ArrayList<RevisonSpentTotalBackSAP>();
		for (int i=0;i<10;i++) {
			RevisonSpentTotalBackSAP back = new RevisonSpentTotalBackSAP();
			back.setRid(String.valueOf(new Random().nextInt(1000))+i);
			back.setSpentMonth("2016-03");
			back.setTotal("224.40");
			list.add(back);
		}
		return list;
	}

}
