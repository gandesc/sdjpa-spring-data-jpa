package guru.springframework.jdbc;

import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testBookFuture() throws ExecutionException, InterruptedException {
        Future<Book> future = bookRepository.queryByTitle("Clean Code");

        Book book = future.get();

        assertThat(book).isNotNull();
    }

    @Test
    void testEmptyResultException() {
        assertThrows(
                EmptyResultDataAccessException.class,
                () -> bookRepository.readByTitle("foobar4")
        );
    }

    @Test
    void testNullParam() {
        assertNull(bookRepository.getByTitle(null));
    }

    @Test
    void testNoException() {
        assertNull(bookRepository.getByTitle("foo"));
    }

    @Test
    void testFindAllByTitleNotNull() {
        AtomicInteger counter = new AtomicInteger();

        bookRepository.findAllByTitleNotNull()
                        .forEach(book -> counter.incrementAndGet());

        assertThat(counter.get()).isGreaterThan(1);
    }
}
