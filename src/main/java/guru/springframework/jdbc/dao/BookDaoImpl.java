package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookDaoImpl implements BookDao {

  private final BookRepository repository;

  @Override
  public List<Book> findAllBooksSortByTitle(Pageable pageable) {
    Page<Book> bookPage = repository.findAll(pageable);

    return bookPage.getContent();
  }

  @Override
  public List<Book> findAllBooks(Pageable pageable) {
    return repository.findAll(pageable).getContent();
  }

  @Override
  public List<Book> findAllBooks(int pageSize, int offset) {
    Pageable pageable = PageRequest.ofSize(pageSize);

    if (offset > 0) {
      pageable = pageable.withPage(offset / pageSize);
    }

    return this.findAllBooks(pageable);
  }

  @Override
  public List<Book> findAllBooks() {
    return repository.findAll();
  }

  @Override
  public Book saveNewBook(Book book) {
    return repository.save(book);
  }

  @Override
  public Book getById(Long id) {
    return repository.getReferenceById(id);
  }

  @Override
  public void deleteBookById(Long id) {
    repository.deleteById(id);
  }

  @Transactional
  @Override
  public Book updateBook(Book book) {
    Book entity = repository.findById(book.getId())
        .orElseThrow(EntityNotFoundException::new);

    entity.setTitle(book.getTitle());
    entity.setIsbn(book.getIsbn());
    entity.setPublisher(book.getPublisher());

    return repository.save(entity);
  }

  @Override
  public Book findBookByTitle(String title) {
    return repository.findByTitle(title)
        .orElseThrow(EntityNotFoundException::new);
  }
}
