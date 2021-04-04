package com.apbdoo.BooksStore.controllers;

import com.apbdoo.BooksStore.dto.BookInfoDTO;
import com.apbdoo.BooksStore.dto.ResultDTO;
import com.apbdoo.BooksStore.models.BookInfo;
import com.apbdoo.BooksStore.services.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/bookinfo")
public class BookInfoController {
    private BookInfoService bookInfoService;

    @Autowired
    public BookInfoController(BookInfoService bookInfoService) {
        this.bookInfoService = bookInfoService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("")
    public List<BookInfo> getAll() {
        return bookInfoService.getAll();
    }


    @PostMapping("/create")
    public BookInfoDTO createBookInfo(@RequestBody BookInfoDTO newBookInfo) {
        return bookInfoService.createBookInfo(newBookInfo);
    }

    @PutMapping("/update/{id}")
    public BookInfoDTO editBookInfo(@PathVariable(value = "id") int id, @RequestBody BookInfoDTO editBookInfo) {
        return bookInfoService.editBookInfo(id, editBookInfo);
    }

    @DeleteMapping("/delete/{id}")
    public ResultDTO deleteBookInfo(@PathVariable(value = "id") int id) {
        return bookInfoService.deleteBookInfo(id);
    }

}