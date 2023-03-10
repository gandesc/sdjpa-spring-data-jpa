package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookDaoImpl implements BookDao {

    private final BookRepository repository;

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
