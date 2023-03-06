package guru.springframework.jdbc.repositories;

import guru.springframework.jdbc.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM book WHERE title=:title", nativeQuery = true)
    Book findByTitleWithQueryNative(String title);

    @Query("SELECT b FROM Book b WHERE b.title=:title")
    Book findByTitleWithQueryNamed(@Param("title") String title);

    @Query("SELECT b FROM Book b WHERE b.title=?1")
    Book findByTitleWithQuery(String title);

    Optional<Book> findByTitle(String title);

    Book readByTitle(String title);

    @Nullable
    Book getByTitle(@Nullable String title);

    Stream<Book> findAllByTitleNotNull();

    @Async
    Future<Book> queryByTitle(String title);
}
