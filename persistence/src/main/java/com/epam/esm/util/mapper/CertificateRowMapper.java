package com.epam.esm.util.mapper;

import com.epam.esm.Certificate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class responsible for mapping information about {@link Certificate} from
 * result set row to the {@link Certificate}
 */
@Component
public class CertificateRowMapper implements RowMapper<Certificate> {
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
