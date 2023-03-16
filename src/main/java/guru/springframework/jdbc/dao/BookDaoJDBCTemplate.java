package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@RequiredArgsConstructor
public class BookDaoJDBCTemplate implements BookDao {
    public final JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        String direction = pageable.getSort().getOrderFor("title").getDirection().name();

        String sql = "SELECT * FROM book ORDER BY title " + direction + " LIMIT ? OFFSET ?";

        return jdbcTemplate.query(
                sql,
                getBookMapper(),
                pageable.getPageSize(),
                pageable.getOffset()
        );
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return jdbcTemplate.query(
                "SELECT * FROM book LIMIT ? OFFSET ? ",
                getBookMapper(),
                pageable.getPageSize(),
                pageable.getOffset()
        );
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return jdbcTemplate.query("SELECT * FROM book LIMIT ? OFFSET ? ", getBookMapper(), pageSize, offset);
    }

    @Override
    public List<Book> findAllBooks() {
        return jdbcTemplate.query("SELECT * FROM book", getBookMapper());
    }

    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?", getBookMapper(), id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE title=?", getBookMapper(), title);
    }

    @Override
    public Book saveNewBook(Book book) {
        jdbcTemplate.update(
                "INSERT INTO book (isbn, publisher, title, author_id) VALUES (?,?,?,?)",
                book.getIsbn(),
                book.getPublisher(),
                book.getTitle(),
                book.getAuthorId()
        );

        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return this.getById(createdId);
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update(
                "UPDATE book SET isbn=?, publisher=?, title=? WHERE id=?",
                book.getIsbn(),
                book.getPublisher(),
                book.getTitle(),
                book.getId()
        );

        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    private RowMapper<Book> getBookMapper() {
        return new BookMapper();
    }
}
