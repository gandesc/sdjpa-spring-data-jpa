package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;

public interface BookDao {

    Book saveNewBook(Book book);

    Book getById(Long id);

    void updateBook(Book book);

    void deleteBookById(Long id);

    Book findBookByTitle(String title);
}
