package org.apache.hadoop.java.io;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.regex.Pattern;

/**   
* @Title: FileTest.java 
* @Package showcase.dw.glbg.io 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年11月17日 下午1:56:29   
*/
public class FilenameFilterTest {
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("\\*java*\\");
		DirFilter df = new DirFilter(pattern);
		File file = new File("D:\\doc\\Java");
		if (file.exists() && file.isDirectory()) {
			for(String fileName :file.list(df)) {
				System.out.println(fileName);
			}
		}
		

		
	}

}

class DirFilter implements FilenameFilter{
	private Pattern pattern;
	

	public DirFilter(Pattern pattern) {
		super();
		this.pattern = pattern;
	}


	@Override
	public boolean accept(File arg0, String arg1) {
		// TODO Auto-generated method stub
		return pattern.matcher(arg1).matches();
	}
	
}
