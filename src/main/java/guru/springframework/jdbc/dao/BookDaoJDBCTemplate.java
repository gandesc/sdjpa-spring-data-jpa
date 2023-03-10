package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@RequiredArgsConstructor
public class BookDaoJDBCTemplate implements BookDao{
    public final JdbcTemplate jdbcTemplate;

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
