package com.epam.esm.util.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class responsible for mapping information from
 * result set row to the database entity
 *
 * @param <T> database entity
 */
public interface RowMapper<T> {
    /**
     * Maps information from rs to the database entity
     *
     * @param rs     result set with information received from the database
     * @param rowNum num of rows in the result set
     * @return object with mapped information
     * @throws SQLException if error occurs during reading result set
     */
    T mapRowToObject(ResultSet rs, int rowNum) throws SQLException;
}
