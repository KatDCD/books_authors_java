package sust.books_authors.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sust.books_authors.models.Author;
import sust.books_authors.models.Book;
import sust.books_authors.services.BookDao;

@Controller
public class BooksController {

  @Autowired
  BookDao dao;

  @GetMapping(value = "/books")
  public ModelAndView screenBooks() {
    ModelAndView view = new ModelAndView("books.html");

    ArrayList<Book> books;
    try {
      books = dao.getAll();
      view.addObject("books", books);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return view;
  }

  @PostMapping(value = "/books")
  public String addBook(@RequestParam String title, @RequestParam String description) {
    // 1. Llamar al dao para que agregue un nuevo libro
    dao.create(title, description);
    // 2. Reidirigir al GET /books
    return "redirect:/books";
  }

  @GetMapping(value = "/books/delete/{id}")
  public String deleteBook(@PathVariable int id) {
    // 1. Le pido al dao que borre el libro
    dao.delete(id);
    // 2. Redirijo a la ruta /books
    return "redirect:/books";
  }

  @GetMapping(value = "/books/detail/{id}")
  public ModelAndView detailBook(@PathVariable int id) {
    ModelAndView view = new ModelAndView("detail_book.html");

    try {
      Book book = dao.getById(id);
      ArrayList<Author> authorsNotRelationated = dao.getAuthorsNotRelationated(id);
      ArrayList<Author> authorsRelationated = dao.getAuthorsRelationated(id);
      view.addObject("book", book);
      view.addObject("authorsNotRelationated", authorsNotRelationated);
      view.addObject("authorsRelationated", authorsRelationated);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return view;
  }

  @PostMapping("/books/{book_id}/addauthor")
  public String addAuthor(@PathVariable int book_id, @RequestParam int author_id) {
    // 1. Agregamos el autor al libro
    dao.addAuthor(book_id, author_id);
    // 2. Redirigimos a /books
    return "redirect:/books/detail/" + book_id;
  }
}
