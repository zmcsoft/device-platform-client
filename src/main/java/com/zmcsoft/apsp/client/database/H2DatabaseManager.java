package com.zmcsoft.apsp.client.database;

import lombok.extern.slf4j.Slf4j;
import org.hswebframework.expands.script.engine.DynamicScriptEngine;
import org.hswebframework.expands.script.engine.DynamicScriptEngineFactory;
import org.hswebframework.ezorm.core.Database;
import org.hswebframework.ezorm.rdb.executor.AbstractJdbcSqlExecutor;
import org.hswebframework.ezorm.rdb.executor.SqlExecutor;
import org.hswebframework.ezorm.rdb.meta.RDBDatabaseMetaData;
import org.hswebframework.ezorm.rdb.meta.parser.H2TableMetaParser;
import org.hswebframework.ezorm.rdb.render.dialect.H2RDBDatabaseMetaData;
import org.hswebframework.ezorm.rdb.simple.SimpleDatabase;
import org.hswebframework.utils.file.FileUtils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public class H2DatabaseManager {
    static SqlExecutor sqlExecutor;
    static Database    database;

    static {
        try {
            Class.forName("org.h2.Driver");
            sqlExecutor = new H2SqlExecutor();
            RDBDatabaseMetaData metaData = new H2RDBDatabaseMetaData();
            metaData.setParser(new H2TableMetaParser(sqlExecutor));
            database = new SimpleDatabase(metaData, sqlExecutor);
            String script = FileUtils.reader2String("./conf/init.js");
            DynamicScriptEngine engine = DynamicScriptEngineFactory.getEngine("js");
            engine.compile("init", script);
            engine.execute("init", Collections.singletonMap("database", database));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static SqlExecutor getSqlExecutor() {
        return sqlExecutor;
    }

    public static Database getDatabase() {
        return database;
    }
}
