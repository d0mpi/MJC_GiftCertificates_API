package com.epam.esm.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class BasicHikariDataSource {
    private static final HikariConfig config = new HikariConfig("/database.properties");
    private static final DataSource ds = new HikariDataSource(config);

    private BasicHikariDataSource() {
    }

    public static DataSource getDataSource() {
        return ds;
    }
}
