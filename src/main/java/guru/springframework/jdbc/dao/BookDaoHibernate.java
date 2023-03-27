package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class BookDaoHibernate implements BookDao {
    private final EntityManagerFactory emf;

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        try(EntityManager em = emf.createEntityManager()) {
            String hql = "SELECT b FROM Book b ORDER BY b.title "
                    + pageable.getSort().getOrderFor("title").getDirection().name();
            TypedQuery<Book> query = em.createQuery(hql, Book.class);
            query.setFirstResult(pageable.getPageNumber())
                    .setMaxResults(pageable.getPageSize());

            return query.getResultList();
        }
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            query.setFirstResult(pageable.getPageNumber());
            query.setMaxResults(pageable.getPageSize());

            return query.getResultList();
        }
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return null;
    }

    @Override
    public List<Book> findAllBooks() {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            return query.getResultList();
        }
    }


    @Override
    public Book getById(Long id) {
        try (EntityManager em = getEntityManager()) {
            return em.find(Book.class, id);
        }
    }

    @Override
    public Book findBookByTitle(String title) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.title=:title", Book.class);
            query.setParameter("title", title);

            return query.getSingleResult();
        }
    }

    @Override
    public Book saveNewBook(Book book) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.persist(book);
            em.flush();
            em.getTransaction().commit();
            em.close();

            return book;
        }
    }

    @Override
    public Book updateBook(Book book) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.merge(book);
            em.getTransaction().commit();

            return em.find(Book.class, book.getId());
        }
    }

    @Override
    public void deleteBookById(Long id) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            Book book = em.find(Book.class, id);
            em.remove(book);
            em.getTransaction().commit();
        }
    }


    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
