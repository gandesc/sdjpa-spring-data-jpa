package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Author.builder()
                .id(rs.getLong("id"))
                .lastName(rs.getString("last_name"))
                .firstName(rs.getString("first_name"))
                .build();
    }
}
