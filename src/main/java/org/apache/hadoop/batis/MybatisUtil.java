package org.apache.hadoop.batis;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**   
 * @Title: MybatisUtil.java 
 * @Package  
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author gongxuesong@globalegrow.com   
 * @date 2017年9月6日 下午2:36:06   
 */
public class MybatisUtil {  
      
    private static SqlSessionFactory sqlSessionFactory;  
  
    public static SqlSessionFactory getSessionFactory() throws IOException {  
        if(sqlSessionFactory==null){  
            String resource = "mybatis-config.xml";  
            InputStream inputStream = Resources.getResourceAsStream(resource);  
            return new SqlSessionFactoryBuilder().build(inputStream);  
        }else{  
            return sqlSessionFactory;  
        }  
    }  
} 
