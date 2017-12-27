package com.zmcsoft.apsp.client.database;

import com.alibaba.fastjson.JSON;
import com.zmcsoft.apsp.client.javascript.JavaScriptObject;
import netscape.javascript.JSObject;
import org.hswebframework.ezorm.rdb.executor.AbstractJdbcSqlExecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author zhouhao
 * @since
 */
public class H2SqlExecutor extends AbstractJdbcSqlExecutor implements JavaScriptObject {
    private String url      = "jdbc:h2:file:./data/apsp";
    private String username = "sa";
    @SuppressWarnings("all")
    private String password = "";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    protected Object getSqlParamValue(Object param, String paramName) {
        if (param instanceof JSObject) {
            JSObject object = ((JSObject) param);
            return object.getMember(paramName);
        }
        return super.getSqlParamValue(param, paramName);
    }

    @Override
    public Map<String, Object> single(String sql, Object params) throws SQLException {
        return super.single(sql, convertParam(params));
    }

    @Override
    public List<Map<String, Object>> list(String sql, Object params) throws SQLException {
        return super.list(sql, convertParam(params));
    }

    @Override
    public int update(String sql, Object params) throws SQLException {
        return super.update(sql, convertParam(params));
    }

    protected Object convertParam(Object param) {
        return param instanceof String ? JSON.parse((String) param) : param;
    }

    @Override
    public void releaseConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public String getName() {
        return "sqlExecutor";
    }
}
