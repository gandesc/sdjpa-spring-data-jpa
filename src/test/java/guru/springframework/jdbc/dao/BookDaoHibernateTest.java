package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoHibernateTest {
    @Autowired
    EntityManagerFactory emf;

    BookDao bookDao;

    @BeforeEach
    void setup() {
        bookDao = new BookDaoHibernate(emf);
    }

    @Test
    void findAllBooksSortByTitle() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("title")));
        List<Book> books = bookDao.findAllBooksSortByTitle(pageable);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }
    @Test
    void testFindAllBooksPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = bookDao.findAllBooks(pageable);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooks() {
        List<Book> books = bookDao.findAllBooks();

        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        Book deleted = bookDao.getById(saved.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        book.setAuthorId(3L);
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        book.setAuthorId(3L);
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testGetBookByName() {
        Book book = bookDao.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {
        Book book = bookDao.getById(3L);

        assertThat(book.getId()).isNotNull();
    }
}
