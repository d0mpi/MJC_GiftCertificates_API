package com.epam.esm.pool;

import com.zaxxer.hikari.HikariConfig;

public class BasicHikariDataSource {
    private static HikariConfig config = new HikariConfig("/database.properties");
    private static com.zaxxer.hikari.HikariDataSource ds = new com.zaxxer.hikari.HikariDataSource(config);

    private BasicHikariDataSource() {
    }

    public static com.zaxxer.hikari.HikariDataSource getDataSource() {
        return ds;
    }
}
