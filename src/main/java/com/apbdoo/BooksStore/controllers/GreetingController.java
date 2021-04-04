package com.apbdoo.BooksStore.controllers;

import com.apbdoo.BooksStore.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    private AuthorController authorController;
    private BookController bookController;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value="/*")
    public String homePage(){
        return "home";
    }

    @RequestMapping(value="/login")
    public String login(){
        return "login";
    }

/*
    @RequestMapping({"/authors"})
    public String getAuthorsPage(Model model){
        model.addAttribute("authors", authorController.getAll());
        return "greeting";
    }


    @GetMapping
    @RequestMapping("authors/{id}/delete")
    public String deleteById(@PathVariable Integer id){

        authorController.deleteAuthor(id);
        return "redirect:/greeting";
    }

    @RequestMapping({"/books"})
    public String getBooksPage(Model model){
        model.addAttribute("books", bookController.getBook("Maytrei"));
        return "greeting";
    }
*/
}