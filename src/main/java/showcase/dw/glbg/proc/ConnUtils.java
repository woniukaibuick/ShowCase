package showcase.dw.glbg.proc;
/**   
* @Title: ConnUtils.java 
* @Package showcase.dw.glbg.proc 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年11月11日 下午12:29:53   
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConnUtils {
    private static String url = "jdbc:oracle:thin:@10.40.2.126:1521:geekbi";
    private static String user = "bigdata";
    private static String password = "bigdata";

    /**
     * 说明要访问此类只能通过static或单例模式
     */
    private ConnUtils() {
    }

    // 注册驱动 (只做一次)
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 获取Connection对象
     * 
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 释放资源
     * 
     * @param rs
     * @param st
     * @param conn
     */
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
