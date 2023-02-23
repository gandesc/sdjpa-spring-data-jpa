package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 8/28/21.
 */
@RequiredArgsConstructor
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository authorRepository;

    @Override
    public Author getById(Long id) {
        return authorRepository.getReferenceById(id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        Author entity = authorRepository.getReferenceById(author.getId());

        entity.setLastName(author.getLastName());
        entity.setFirstName(author.getFirstName());

        return authorRepository.save(entity);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
