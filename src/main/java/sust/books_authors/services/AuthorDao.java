package sust.books_authors.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import sust.books_authors.models.Author;

@Component
public class AuthorDao {

  @Autowired
  JdbcTemplate tpl;

  public List<Author> getAll() {
    return tpl.query("select * from authors", new AuthorMapper());
  }

  public void create(String first_name, String last_name, String notes) {
    String request = "insert into authors (first_name, last_name, notes) values (?, ?, ?)";
    tpl.update(request, first_name, last_name, notes);
  }

  public void delete(int id) {
    String request = "delete from authors where id=?";
    tpl.update(request, id);
  }

}
