package com.epam.esm.mapper;

import com.epam.esm.DatabaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T extends DatabaseEntity> {
    T mapRowToObject(ResultSet rs, int rowNum) throws SQLException;
}
