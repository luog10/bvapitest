package apiutility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class MySqlHelper {
    private static DataSource datasource;

    /**
     * 初始化链接数据库配置
     */
    static void Init() {
        if(datasource==null)
        {
            String jdbcDriver=PropertiesUtil.getValue("dbinfo.properties","driver");
            String dbUrl=PropertiesUtil.getValue("dbinfo.properties","url");
            String dbUsername=PropertiesUtil.getValue("dbinfo.properties","user");
            String dbPassword=PropertiesUtil.getValue("dbinfo.properties","password");
            PoolProperties p = new PoolProperties();
            p.setUrl(dbUrl);
            p.setDriverClassName(jdbcDriver);
            p.setUsername(dbUsername);
            p.setPassword(dbPassword);
            p.setJmxEnabled(true);
            p.setTestWhileIdle(false);
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setMaxActive(100);
            p.setInitialSize(3);
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(10);
            p.setLogAbandoned(true);
            p.setRemoveAbandoned(true);
            p.setJdbcInterceptors(
                    "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
                            "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
            datasource = new DataSource();
            datasource.setPoolProperties(p);
        }
    }
    /**
     * 查询
     * @param sql
     * @return
     */
    public static List<Map<String, Object>> query(String sql) {
        if(datasource ==null) Init();
        List<Map<String, Object>> lm = new ArrayList();
        Connection con = null;
        PreparedStatement ps =null;
        ResultSet rs=null;
        try {
            con = datasource.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rm = rs.getMetaData();
            int cont = rm.getColumnCount();

            while (rs.next()) {
                Map<String, Object> mp = new HashMap();
                for (int c = 1; c <= cont; c++) {
                    Object o = rs.getObject(c);
                    mp.put(rm.getColumnName(c), o == null ? "" : o);
                }
                lm.add(mp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (rs!=null) try {rs.close();}catch (Exception ex) {}
            if (ps!=null) try {ps.close();}catch (Exception ex) {}
            if (con!=null) try {con.close();}catch (Exception ex) {}
        }
        return lm;
    }
    /**
     * 查询
     * @param sql
     * @param lo 条件参数
     * @return
     */
    public static List<Map<String, Object>> query(String sql, Object[] lo) {
        if(datasource ==null) Init();
        List<Map<String, Object>> lm = new ArrayList();
        Connection con = null;
        PreparedStatement ps =null;
        ResultSet rs=null;
        try {
            con = datasource.getConnection();
            ps = con.prepareStatement(sql);
            if (lo != null) {
                for (int i = 1; i <= lo.length; i++) {
                    ps.setObject(i, lo[i - 1]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rm = rs.getMetaData();
            int cont = rm.getColumnCount();
            while (rs.next()) {
                Map<String, Object> mp = new HashMap<String, Object>();
                for (int c = 1; c <= cont; c++) {
                    Object o = rs.getObject(c);
                    mp.put(rm.getColumnName(c), o == null ? "" : o);
                }
                lm.add(mp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (rs!=null) try {rs.close();}catch (Exception ex) {}
            if (ps!=null) try {ps.close();}catch (Exception ex) {}
            if (con!=null) try {con.close();}catch (Exception ex) {}
        }
        return lm;
    }

    /**
     * 保存和修改和删除
     * @param sql
     * @param lo
     * @return
     */
    public static int saveorupdate(String sql, Object[] lo) {
        if(datasource ==null) Init();
        int p = 0;
        Connection con = null;
        PreparedStatement ps =null;
        try {
            con = datasource.getConnection();
            ps = con.prepareStatement(sql);
            if (lo != null) {
                for (int i = 1; i <= lo.length; i++) {
                    ps.setObject(i, lo[i - 1]);
                }
            }
            p = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (ps!=null) try {ps.close();}catch (Exception ex) {}
            if (con!=null) try {con.close();}catch (Exception ex) {}
        }
        return p;
    }
}
