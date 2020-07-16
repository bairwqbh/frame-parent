package per.cby.frame.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import per.cby.frame.common.db.DriverClass;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC帮助类
 * 
 * @author chenboayng
 *
 */
@Slf4j
@UtilityClass
public class JdbcUtil {

    /**
     * 执行SQL查询
     * 
     * @param driver   驱动类
     * @param url      连接地址
     * @param user     用户名
     * @param password 密码
     * @param sql      SQL语句
     * @return 查询结果
     */
    public List<Map<String, Object>> query(DriverClass driver, String url, String user, String password, String sql) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(driver.getClassPath());
            conn = DriverManager.getConnection(url, user, password);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<String, Object>();
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    item.put(rsmd.getColumnLabel(i), rs.getObject(i));
                }
                list.add(item);
            }
        } catch (ClassNotFoundException | SQLException e) {
            log.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                log.error(e1.getMessage(), e1);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
        return list;
    }

    /**
     * 执行SQL更新
     * 
     * @param driver   驱动类
     * @param url      连接地址
     * @param user     用户名
     * @param password 密码
     * @param sql      SQL语句
     * @return 更新记录数
     */
    public int update(DriverClass driver, String url, String user, String password, String sql) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName(driver.getClassPath());
            conn = DriverManager.getConnection(url, user, password);
            ps = conn.prepareStatement(sql);
            result = ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            log.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                log.error(e1.getMessage(), e1);
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

}
