package com.apbdoo.BooksStore.controllers;

import com.apbdoo.BooksStore.dto.ResultDTO;
import com.apbdoo.BooksStore.models.BookCategory;
import com.apbdoo.BooksStore.services.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/bookcategories")
public class BookCategoryController {

    private BookCategoryService bookCategoryService;

    @Autowired
    public BookCategoryController(BookCategoryService bookcategoryService) {
        this.bookCategoryService = bookcategoryService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("")
    public List<BookCategory> getAll() {
        return bookCategoryService.getAllCategories();
    }


    @PostMapping("/create")
    public BookCategory createBookCategory(@RequestBody String input) {
        return bookCategoryService.createBookCategory(input);
    }

    @PutMapping("/update/{id}")
    public ResultDTO updateBookCategory(@PathVariable(value = "id") int id, @RequestBody BookCategory bookCategory) {
        return bookCategoryService.updateBookCategory(bookCategory);
    }

    @DeleteMapping("/delete/{id}")
    public ResultDTO deleteBookCategory(@PathVariable(value = "id") int id) {
        return bookCategoryService.deleteBookCategory(id);
    }
}

