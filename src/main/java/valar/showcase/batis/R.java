package valar.showcase.batis;
import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**   
 * @Title: R.java 
 * @Package  
 * @Description: TODO(demo:http://blog.csdn.net/ziwen00/article/details/38349257) 
 * @author gongxuesong@globalegrow.com   
 * @date 2017年9月5日 下午2:12:37   
 */
public class R {

	public static void main(String[] args) throws IOException {  
        SqlSessionFactory sessionFactory = MybatisUtil.getSessionFactory();  
        SqlSession session = sessionFactory.openSession();
        try {  
            Tiger tiger = (Tiger)session.selectOne("selectTiger",Integer.valueOf(11));
            System.out.println(tiger==null?"empty entity!":tiger.getAge()+"");  
        } finally{  
            session.close();  
        }  
    }  
	/**
	 * 
	* @Title: get 
	* @Description: TODO(description) 
	* @param  
	* @return    
	* @throws
	 */
	private String get(String id) {
		return null;
	}
}
