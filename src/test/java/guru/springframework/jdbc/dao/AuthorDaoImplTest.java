package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
public class AuthorDaoImplTest {

  @Autowired
  private AuthorDao authorDao;

  @Test
  void findAllAuthorsByLastName() {
    List<Author> authorList = authorDao.findAllAuthorsByLastName("Smith",
        PageRequest.of(0, 10));

    assertThat(authorList).isNotNull();
    assertThat(authorList.size()).isEqualTo(10);
  }

  @Test
  void findAllAuthorsByLastNameSortFirstNameDesc() {
    List<Author> authorList = authorDao.findAllAuthorsByLastName("Smith",
        PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstName"))));

    assertThat(authorList).isNotNull();
    assertThat(authorList.size()).isEqualTo(10);
    assertThat(authorList.get(0).getLastName()).isEqualTo("Smith");
    assertThat(authorList.get(0).getFirstName()).isEqualTo("Yugal");
  }

  @Test
  void findAllAuthorsByLastNameSortFirstNameAsc() {
    List<Author> authorList = authorDao.findAllAuthorsByLastName("Smith",
        PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstName"))));

    assertThat(authorList).isNotNull();
    assertThat(authorList.size()).isEqualTo(10);
    assertThat(authorList.get(0).getLastName()).isEqualTo("Smith");
    assertThat(authorList.get(0).getFirstName()).isEqualTo("Ahmed");
  }

  @Test
  void findAllAuthorsByLastNameAllRecs() {
    List<Author> authors = authorDao.findAllAuthorsByLastName("Smith", PageRequest.of(0, 100));

    Assertions.assertThat(authors).isNotNull();
    Assertions.assertThat(authors.size()).isEqualTo(40);
  }
}
