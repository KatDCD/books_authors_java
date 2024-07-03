package sust.books_authors.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sust.books_authors.services.AuthorDao;
import sust.books_authors.models.Author;

@Controller
public class AuthorController {
    
    @Autowired
    AuthorDao dao;

    @GetMapping(value = "/authors")
    public ModelAndView screenAuthors() {
    ModelAndView view = new ModelAndView("authors.html");

    List<Author> authors = dao.getAll();
    System.out.println(authors);
    view.addObject("authors", authors);

    return view;
  }

  @PostMapping("/authors")
  public String createAuthor(@RequestParam String first_name, @RequestParam String last_name, @RequestParam String notes) {
    dao.create(first_name, last_name, notes);
    return "redirect:/authors";
  }

  @GetMapping("/authors/delete/{id}")
  public String deleteAuthor(@PathVariable int id) {
    // 1. Borramos al autor
    dao.delete(id);
    // 2. Redirigimos a la vista
    return "redirect:/authors";
  }
}





