package com.eray.util.compare;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 对多个文件进行排序，按名称从大到小排列 2016.08.18
 * @author ganqing
 *
 */
public class FileNameCompator implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		if (o1.getName().equals(o2.getName())) {
			return 0;
		} 
		if (o1.getName().compareTo(o2.getName()) > 0) {
			return -1;
		}
		return 1;
	}
	
	/*public static void main(String[] args) {
		File f1 = new File("E:\\pbdata");
		File[] files = f1.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {
					return false;
				}
				return true;
			}
		});
		Arrays.sort(files, new FileNameCompator());
		for (File f : files) {
			System.out.println(f.getName());
		}
	}
*/
}
