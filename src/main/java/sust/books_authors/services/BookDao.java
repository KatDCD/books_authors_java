package sust.books_authors.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sust.books_authors.models.Author;
import sust.books_authors.models.Book;

@Component
public class BookDao {

  @Autowired
  JdbcTemplate tpl;

  public ArrayList<Book> getAll() throws SQLException {
    // 1. Pedimos una conexión al objeto JdbcTemplate
    Connection conn = tpl.getDataSource().getConnection();
    // 2. Creamos una consulta y la ejecutamos
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select * from books");
    // 3. Creamos una lista de libros vacía
    ArrayList<Book> books = new ArrayList<Book>();
    // 4. Vamos llenando la lista con los registros de la respuesta SQL
    while (rs.next()) {
      books.add(new Book(
          rs.getInt("id"),
          rs.getString("title"),
          rs.getString("description")));
    }
    return books;
  }

  public Book getById(int id) throws SQLException {
    // 1. Pedimos una conexión al objeto JdbcTemplate
    Connection conn = tpl.getDataSource().getConnection();
    // 2. Creamos una consulta y la ejecutamos
    PreparedStatement stmt = conn.prepareStatement(
        "select * from books where id=?");
    stmt.setInt(1, id);
    ResultSet rs = stmt.executeQuery();
    // 3. Retornamos un objeto del tipo Libro
    rs.next();
    return new Book(
        rs.getInt("id"),
        rs.getString("title"),
        rs.getString("description"));
  }

  public void create(String title, String description) {
    String request = "insert into books (title, description) values (?, ?)";
    tpl.update(request, title, description);
  }

  public void addAuthor(int book_id, int author_id) {
    String request = "insert into bookauthor (book_id, author_id) values (?, ?)";
    tpl.update(request, book_id, author_id);
  }

  public void delete(int id) {
    String request = "delete from books where id=?";
    tpl.update(request, id);
  }

  public ArrayList<Author> getAuthorsNotRelationated(int book_id) throws SQLException {
    // 1. Pedimos una conexión al objeto JdbcTemplate
    Connection conn = tpl.getDataSource().getConnection();
    // 2. Creamos una consulta y la ejecutamos
    PreparedStatement stmt = conn.prepareStatement(
        "select * from authors where id not in (select authors.id from authors join bookauthor on authors.id = bookauthor.author_id where book_id = ?)");
    stmt.setInt(1, book_id);
    ResultSet rs = stmt.executeQuery();

    // 3. Creamos el arraylist y lo vamos llenando
    ArrayList<Author> authorsNotRelationated = new ArrayList<Author>();
    while (rs.next()) {
      authorsNotRelationated.add(new Author(
          rs.getInt("id"), rs.getString("first_name"),
          rs.getString("last_name"), rs.getString("notes")));
    }
    return authorsNotRelationated;
  }

  public ArrayList<Author> getAuthorsRelationated(int book_id) throws SQLException {
    // 1. Pedimos una conexión al objeto JdbcTemplate
    Connection conn = tpl.getDataSource().getConnection();
    // 2. Creamos una consulta y la ejecutamos
    PreparedStatement stmt = conn.prepareStatement(
        "select * from authors join bookauthor on authors.id = bookauthor.author_id where book_id = ?");
    stmt.setInt(1, book_id);
    ResultSet rs = stmt.executeQuery();

    // 3. Creamos el arraylist y lo vamos llenando
    ArrayList<Author> authorsRelationated = new ArrayList<Author>();
    while (rs.next()) {
      authorsRelationated.add(new Author(
          rs.getInt("id"), rs.getString("first_name"),
          rs.getString("last_name"), rs.getString("notes")));
    }
    return authorsRelationated;
  }
}
