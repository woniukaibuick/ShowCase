package org.apache.hadoop.java.jdbc;
/**   
* @Title: ProcTest.java 
* @Package showcase.dw.glbg.proc 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年11月11日 下午12:29:24   
*/

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ProcTest {

	public static void main(String[] args) {
		Connection conn = null;
		CallableStatement statement = null;
//		String sql = "{call dim.p_dim_department(?,?)}";
		String sql = "{call DW.P_DW_SAL_O_DEP_OFM_ACC_DAY(?,?)}";
		try {
			conn = ConnUtils.getConnection();
			statement = conn.prepareCall(sql);
			statement.setString(1, "20171101");
			statement.setInt(2, 1);
//			statement.registerOutParameter(2, Types.VARCHAR);
			System.out.println("start time:"+System.currentTimeMillis());
//			statement.executeUpdate();
			statement.execute();
			System.out.println("end   time:"+System.currentTimeMillis());
//			String sname = statement.getString(2);
//			System.out.println(sname);
		} catch (Exception e) {
			System.err.println("end   time:"+System.currentTimeMillis());
			e.printStackTrace();
			System.out.println(e.getMessage()); 
		}
	}

}