package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoJDBCTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private AuthorDao authorDao;

    @BeforeEach
    public void setUp() {
        authorDao = new AuthorDaoJDBCTemplate(jdbcTemplate);
    }

    @Test
    void findAllAuthorsByLastNameSortFirstNameDesc() {
        List<Author> authorList = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("first_name"))));

        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isEqualTo(10);
        assertThat(authorList.get(0).getLastName()).isEqualTo("Smith");
        assertThat(authorList.get(0).getFirstName()).isEqualTo("Yugal");
    }

    @Test
    void findAllAuthorsByLastNameSortFirstNameAsc() {
        List<Author> authorList = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 10, Sort.by(Sort.Order.asc("first_name"))));

        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isEqualTo(10);
        assertThat(authorList.get(0).getLastName()).isEqualTo("Smith");
        assertThat(authorList.get(0).getFirstName()).isEqualTo("Ahmed");
    }
}
