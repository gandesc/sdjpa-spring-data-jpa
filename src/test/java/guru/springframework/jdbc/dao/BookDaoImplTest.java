package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
public class BookDaoImplTest {

    @Autowired
    BookDao bookDao;

    @Test
    void testFindAllBooksPage1_SortByTitle() {
        List<Book> books = bookDao.findAllBooksSortByTitle(
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("title")))
        );

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage1_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 10));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage2_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(1, 10));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage1_pageable0() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(10, 10));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void testFindAllBooksPage1() {
        List<Book> books = bookDao.findAllBooks(10, 0);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage2() {
        List<Book> books = bookDao.findAllBooks(10, 10);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage10() {
        List<Book> books = bookDao.findAllBooks(10, 100);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void testFindAllBooks() {
        List<Book> books = this.bookDao.findAllBooks();

        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(1);
    }

    @Test
    void getById() {
        Book book = bookDao.getById(3L);

        assertThat(book).isNotNull();
    }

    @Test
    void findBookByTitle() {
        Book book = bookDao.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void saveNewBook() {
        Book book = createBook();
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    private Book createBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("My Book");

        return book;
    }

    @Test
    void updateBook() {
        Book book = createBook();
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void deleteBookById() {
        Book book = createBook();
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        assertThrows(JpaObjectRetrievalFailureException.class,
                () -> bookDao.getById(saved.getId()));
    }
}
