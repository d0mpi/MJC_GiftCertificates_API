package com.epam.esm.mapper;

import com.epam.esm.Tag;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<Tag>{
    @Override
    public Tag mapRowToObject(ResultSet rs, int rowNum) throws SQLException {
        return  new Tag(rs.getInt("id"),
                rs.getString("name"));
    }
}
