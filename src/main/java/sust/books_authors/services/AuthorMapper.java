package sust.books_authors.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sust.books_authors.models.Author;

public class AuthorMapper implements RowMapper{
  @Override
  public Object mapRow(ResultSet rs, int numfilas) throws SQLException {
    Author a = new Author(
        rs.getInt("id"),
        rs.getString("first_name"),
        rs.getString("last_name"),
        rs.getString("notes"));
    return a;
  }
}
