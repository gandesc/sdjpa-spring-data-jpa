package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@RequiredArgsConstructor
public class AuthorDaoHibernate implements AuthorDao{

    private final EntityManagerFactory emf;

    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastName, Pageable page) {
        try (EntityManager em = getEntityManager()) {
            String sql = "SELECT a FROM Author a WHERE a.lastName=:lastName";

            if(page.getSort().isSorted()) {
                String direction = page.getSort().getOrderFor("firstName").getDirection().name();
                sql+=" ORDER BY a.firstName " + direction;
            }

            TypedQuery<Author> query = em.createQuery(sql, Author.class);
            query.setParameter("lastName", lastName);
            query.setMaxResults(page.getPageSize());
            query.setFirstResult(page.getPageNumber());

            return query.getResultList();
        }
    }

    private RowMapper<Author> getMapper() {
        return new AuthorMapper();
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
