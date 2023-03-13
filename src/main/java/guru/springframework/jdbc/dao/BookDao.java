package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAllBooks();

    Book saveNewBook(Book book);

    Book getById(Long id);

    Book updateBook(Book book);

    void deleteBookById(Long id);

    Book findBookByTitle(String title);
}
