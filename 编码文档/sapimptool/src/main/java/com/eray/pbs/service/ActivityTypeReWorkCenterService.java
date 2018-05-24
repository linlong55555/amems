package com.eray.pbs.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.ActivityTypeReWorkCenterDao;
import com.eray.pbs.po.ActivityTypeReWorkCenter;
import com.eray.pbs.thread.WorkSpentBackSAPThread;
import com.eray.util.compare.FileNameCompator;
import com.eray.util.format.FormatUtil;

/**
 * Activity Type接口 service
 * @author ganqing
 *
 */
@Transactional(readOnly = true)
@Service
public class ActivityTypeReWorkCenterService {
	
	@Autowired
	private ActivityTypeReWorkCenterDao activityTypeReWorkCenterDao;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityTypeReWorkCenterService.class);
	
	private String splitStr = "\t";
	
	public Long getCueerntActivityCount() {
		logger.info("check today Activity type. ");
		String markDate = FormatUtil.formatDate(new Date(), "yyyyMMdd");
		long total = activityTypeReWorkCenterDao.getCueerntActivityCount(markDate);
		logger.info("get Activity type: {}" , total);
		return total;
	}
	/**
	 * 读取文件，并解析文件 (文件名为ZWVA20160816210023格式) 2016.08.18
	 * @param pbsDataPath 下载文件的根目录
	 * @param activityURL  activity 接口文件保存的位置
	 * @param activityBackURL activity 接口文件备份的位置
	 */
	@Transactional(readOnly = false)
	public void saveActivity(String pbsDataPath,String activityURL,String activityBackURL) {
		File f = new File(pbsDataPath + activityURL);
		logger.info("read Activity file:{} ",f.getAbsolutePath());
		if(f.isDirectory()) {
			File[] files = f.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.isDirectory()) {
						return false;
					}
					return true;
				}
			});
			if (files.length > 0) {
				if (files.length > 1) { //如果待解析的文件个数大于0
					Arrays.sort(files, new FileNameCompator());
				} 
				File file = files[0];
				logger.info("file name:{}",file.getName());
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					String str = null;
					String markDate = file.getName().substring(4, 12);//FormatUtil.formatDate(new Date(), "yyyyMMdd");
					logger.info("Activity MarkDate:{}",markDate);
					List<ActivityTypeReWorkCenter> list = new ArrayList<ActivityTypeReWorkCenter>();
			        while ((str = br.readLine()) != null) {
			        	logger.info("file content:{}",str);
			        	String[] activities = str.split(splitStr);
			        	ActivityTypeReWorkCenter a = new ActivityTypeReWorkCenter(activities[0],activities[1],markDate);
			        	logger.info("ActivityTypeReWorkCenter:{}",a);
			        	list.add(a);
			        }
			        if (list.size() > 0) { //保存数据,先清除历史数据
			        	logger.info("START check ActivityType.");
			        	activityTypeReWorkCenterDao.deleteAll();
			        	activityTypeReWorkCenterDao.save(list);
			        	logger.info("END save ActivityType.");
			        }
			        br.close();
			        this.moveFile(pbsDataPath, activityBackURL, files); //拷贝文件至指定目录
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 将文件移至另外一个文件夹
	 * @param f 文件名
	 */
	private void moveFile(String pbsDataPath,String activityBackURL,File[] files) {
		logger.info("start copy file to another file.");
		for (File f : files) {
			if (f.exists() && !f.isDirectory()) {
				try {
					File backFile = new File(pbsDataPath + activityBackURL + f.getName());
					if (!backFile.getParentFile().exists()) {
						backFile.getParentFile().mkdirs();
					}
					if (backFile.exists()) { //如果之前有同名的旧文件存在，则先删除
						backFile.delete();
					}
					backFile.createNewFile();
					FileUtils.copyFile(f, backFile);
					f.delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
