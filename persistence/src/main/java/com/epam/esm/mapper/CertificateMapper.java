package com.epam.esm.mapper;

import com.epam.esm.Certificate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRowToObject(ResultSet rs, int rowNum) throws SQLException {
        return new Certificate(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getInt("duration"),
                rs.getTimestamp("create_date").toLocalDateTime(),
                rs.getTimestamp("last_update_date").toLocalDateTime());
    }
}
