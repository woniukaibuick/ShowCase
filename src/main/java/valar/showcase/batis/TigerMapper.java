package valar.showcase.batis;
import org.apache.ibatis.annotations.Select;




/**   
 * @Title: TigerMapper.java 
 * @Package mybatis 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author gongxuesong@globalegrow.com   
 * @date 2017年9月6日 下午3:00:06   
 */
public interface TigerMapper {

//	@Select("select age from Tiger where id = #{id}")
	public Tiger selectTiger(int id);
	
}
