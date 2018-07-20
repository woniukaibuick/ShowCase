package showcase.dw.glbg.udf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Assert;
import org.junit.Test;

import valar.showcase.hive.udf.connectby.ConnectByUDTF;

public class UnitTest {
	public static void main(String[] args) {
		final String[] s = new String[3];
		final int a = 5;
		s[0] = "ji";
		System.err.println(s[0]);
		System.err.println(a);
	}
	
	@Test
	public  void testHashMap() {
		int a = 11;
		System.err.println(Integer.toBinaryString(a));
		System.err.println(Integer.toBinaryString(a>>>1));
		System.err.println(Integer.toBinaryString(a>>>2));
		int b = 11;
		
		System.err.println(Integer.toBinaryString(b));
		System.err.println(Integer.toBinaryString(b<<1));
		System.err.println(Integer.toBinaryString(b<<2));
	}
	@Test
	public void test1gnaorewgher() {
		Assert.assertEquals(0, 0);
	}
	@Test
	public void testMax(){  
        int max = 1;
        Assert.assertEquals(1, max);  
    }  

	@Test
	public void testHiveJDBC() throws Exception {
		String  devNameNodeAddr = "jdbc:hive2://10.40.6.177:10000/default";
//		String  prodNameNodeAddr = "jdbc:hive2://10.40.2.122:10000/default";
		String newVersionDriverName = "org.apache.hive.jdbc.HiveDriver";
		Class.forName(newVersionDriverName); //
		Connection con = DriverManager.getConnection(devNameNodeAddr, "sunkai",
				"sunkai");
		Statement stmt = con.createStatement();
		String createTableHQL = "create table default.connect_tmp(id string,ps_id string,ps_level string) partitioned by(tree_id string)";
		String inertHQL = "insert into default.connect_tmp ";
		
//		ResultSet rs = stmt.executeQuery("select * from dual");
		ResultSet rs = stmt.executeQuery("desc dual");
		while (rs.next()) {
			System.err.println(rs.getString(0));
		}
		

	}
	
	public void getParentOrSonNodeListByHive(Connection con,String ID,boolean isParent) throws SQLException {
		String querySpecificIDLevel = "select tree_id , ps_level form default.tble where id = ? ";
		String queryGreaterLevel = "select id,ps_id,ps_level from default.tble where tree_id = ? and ps_level > ?";
		String queryLessLevel = "select id,ps_id,ps_level from default.tble where tree_id = ? and ps_level < ?";
		PreparedStatement pstmt = con.prepareStatement(querySpecificIDLevel);
		pstmt.setString(1, ID);
		ResultSet res = pstmt.executeQuery();
		ResultSetMetaData resultSetMetaData = res.getMetaData();
		String tree_id = null,ps_level = null;
		while (res.next()) {
			tree_id = res.getString(0);
			ps_level = res.getString(1);
		}
		PreparedStatement pstmt1 = con.prepareStatement(isParent?queryLessLevel:queryGreaterLevel);
		pstmt1.setString(1, tree_id);
		pstmt1.setString(2, ps_level);
		
		ResultSet result = pstmt1.executeQuery();
		while(result.next()) {
			//TODO get result data
		}
		//return List<Node> type
		
	}
	@Test
	public void saveTreeLevel() {
		String insert= "insert into default.tble values(?,?,?,?)";
		
	}
}
