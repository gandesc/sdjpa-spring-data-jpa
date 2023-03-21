package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@RequiredArgsConstructor
public class AuthorDaoJDBCTemplate implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastName, Pageable page) {
        String direction = page.getSort()
                .getOrderFor("first_name")
                .getDirection()
                .name();
        String sql = "SELECT * FROM author WHERE last_name = ? ORDER BY first_name " + direction + " LIMIT ? OFFSET ?";

        return jdbcTemplate.query(
                sql,
                getRowMapper(),
                lastName,
                page.getPageSize(),
                page.getOffset());
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    private RowMapper<Author> getRowMapper() {
        return new AuthorMapper();
    }
}
