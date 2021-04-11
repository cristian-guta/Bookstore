package com.apbdoo.BooksStore.controllers;


import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.repositories.AuthorRepository;
import com.apbdoo.BooksStore.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@PreAuthorize("permitAll()")
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @RequestMapping(value = "/authorList/page/{page}")
    public String listAuthorsPageByPage(Model model, @PathVariable("page") int page) {
        PageRequest pageable = PageRequest.of(page - 1, 2, Sort.by("firstName"));
        Page<Author> authorPage = authorRepository.findAllPage(pageable);
        int totalPages = authorPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("activeAuthorList", true);
        model.addAttribute("authors", authorPage.getContent());
        return "authorlist";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "authorList/add", method = RequestMethod.GET)
    public String addAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "addAuthor";
    }

    //Save an author
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveAuthor(Author author, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            if (authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName()) == null) {
                if (firstName(author.getFirstName()) && lastName(author.getLastName()))
                    authorRepository.save(author);
                else {
                    bindingResult.rejectValue("lastName", "err.lastName", "Invalid name");
                    return "addAuthor";
                }
            } else {
                bindingResult.rejectValue("lastName", "err.lastName", "Author already exist");
                return "addAuthor";
            }
        } else {
            return "addAuthor";
        }
        return "redirect:/author/authorList/page/1";
    }


    //Save an author
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/save/test", method = RequestMethod.POST)
    public String saveAuthor(Author author) {
        authorRepository.save(author);
        return "redirect:/author/authorList/page/1";
    }

    public static boolean firstName(String firstName) {
        return firstName.matches("[A-Z][a-z]*");
    }

    public static boolean lastName(String lastName) {
        return lastName.matches("[A-Z][a-zA-Z][^#&<>\"~;$^%{}?]*");
    }

    //Edit an author
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editAuthor(@PathVariable(value = "id") int id, Model model) {
        Author author = authorRepository.findById(id);
        model.addAttribute("author", author);
        return "editAuthor";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editAuthor(Author author, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            if (authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName()) == null) {
                if (firstName(author.getFirstName()) && lastName(author.getLastName()))
                    authorRepository.save(author);
                else {
                    bindingResult.rejectValue("lastName", "err.lastName", "Invalid name");
                    return "editAuthor";
                }
            } else {
                bindingResult.rejectValue("lastName", "err.lastName", "Author already exist");
                return "editAuthor";
            }
        } else {
            return "editAuthor";
        }
        return "redirect:/author/authorList/page/1";
    }

    // Delete an author
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteAuthor(@PathVariable("id") int id, Model model) {
        authorService.deleteAuthor(id);
        return "redirect:/author/authorList/page/1";
    }

    @RequestMapping(value = "/update/{id}/test", method = RequestMethod.GET)
    public String editAuthor(Author author) {
        authorRepository.save(author);
        return "redirect:/author/authorList/page/1";

    }


}

