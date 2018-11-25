package org.apache.hadoop.mr.api;

import org.apache.hadoop.util.RunJar;

/**   
* @Title: RunJarTest.java 
* @Package showcase.dw.glbg.mr 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年12月20日 下午1:37:20   
*/
public class RunJarTest {

	/**
	 * 
		RunJar是Hadoop中的一个工具类，结构很简单，只有两个方法：main和unJar。我们从main开始一步步分析。
		     main首先检查传递参数是否符合要求，然后从第一个传递参数中获取jar包的名字，并试图从jar中包中获取manifest信息，以查找mainclass name。如果查找不到mainclass name，则把传递参数中的第二个设为mainclass name。
		     接下去，就是在"hadoop.tmp.dir"下创建一个临时文件夹，并挂载上关闭删除线程。这个临时文件夹用来放置解压后的jar包内容。jar包的解压工作由unJar方法完成，通过JarEntry逐个获取jar包内的内容，包括文件夹和文件，然后释放到临时文件夹中。
		     解压完毕后，开始做classpath的添加，依次把解压临时文件夹、传递进来的jar包、临时文件夹内的classes文件夹和lib里的所有jar包加入到classpath中。接着以这个classpath为搜索URL新建了一个URLClassLoader（要注意这个类加载器的parent包括了刚才bin/hadoop脚本提交时给的classpath），并设置为当前线程的上下文类加载器。
		     最后，利用Class.forName方法，以刚才的那个URLClassLoader为类加载器，动态生成一个mainclass的Class对象，并获取它的main方法，然后以传递参数中剩下的参数作为调用参数来调用这个main方法。
		     好了，从上分析看来，这个RunJar类是一个很简单的类，就是解压传递进来的jar包，再添加一些classpath，然后动态调用jar包里的mainclass的main方法。看到这里，我想你应该知道如何利用java代码来编写一个替代bin/hadoop的程序了，主要就是两步：
		添加Hadoop的依赖库和配置文件；
		解压jar包，再添加一些classpath，并动态调用相应方法。最偷懒的方法，直接用RunJar类就行了。
	 */
	RunJar rj;
	public static void main(String[] args) {
		
	}
}
