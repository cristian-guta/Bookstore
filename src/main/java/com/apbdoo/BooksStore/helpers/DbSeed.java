package com.apbdoo.BooksStore.helpers;

import com.apbdoo.BooksStore.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DbSeed implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookCategoryService bookCategoryService;

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public void run(String... args) {
        userService.seedUsers();
        bookCategoryService.seedBookCategories();
        authorService.seedAuthors();
        bookInfoService.seedBooksInfo();
        bookService.seedBooks();
        orderService.seedOrders();
        shoppingCartService.seedCarts();
   }
}


