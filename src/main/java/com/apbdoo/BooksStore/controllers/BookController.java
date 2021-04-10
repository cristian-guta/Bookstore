package com.apbdoo.BooksStore.controllers;

import com.apbdoo.BooksStore.dto.BookDTO;
import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.BookCategory;
import com.apbdoo.BooksStore.models.User;
import com.apbdoo.BooksStore.repositories.AuthorRepository;
import com.apbdoo.BooksStore.repositories.BookCategoryRepository;
import com.apbdoo.BooksStore.repositories.BookRepository;
import com.apbdoo.BooksStore.repositories.UserRepository;
import com.apbdoo.BooksStore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/book")
public class BookController {

    private BookService bookService;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private BookCategoryRepository bookCategoryRepository;
    private UserRepository userRepository;

    @Autowired
    public BookController(BookService bookService, BookRepository bookRepository, AuthorRepository authorRepository,
                          BookCategoryRepository bookCategoryRepository, UserRepository userRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookCategoryRepository = bookCategoryRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/*")
    public String homePage() {
        return "home";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public @ResponseBody
    List<Book> bookRest() {
        return bookRepository.findAll();
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "bookList/page/{page}")
    public String listBooksPageByPage(Model model, @PathVariable("page") int pg, Principal principal) {
        PageRequest pageable = PageRequest.of(pg - 1, 2, Sort.by("id"));
        Page<Book> bookPage = bookRepository.findAllPage(pageable);
        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        User user = userRepository.findByUsername(principal.getName());
        List<Integer> favouriteBookList = userRepository.getFavouriteBooks(user.getId());
        model.addAttribute("favouriteBookList", favouriteBookList);
        model.addAttribute("activeBookList", true);
        model.addAttribute("books", bookPage.getContent());
        return "booklist";
    }

    @PostMapping("/create")
    public BookDTO createBook(@RequestBody BookDTO newBook) {
        return bookService.createBook(newBook);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("bookCategories", bookCategoryRepository.findAll());
        return "addBook";
    }

    @GetMapping(value = "/addFavourite/{id}")
    public String addBookToFavourite(@PathVariable("id") int id, Principal principal) {
        Book book = bookRepository.getOne(id);
        User user = userRepository.findByUsername(principal.getName());
        userRepository.addFavouriteBook(user.getId(), book.getId());
        return "redirect:/book/bookList/page/1";
    }

    @GetMapping(value = "/removeFavourite/{id}")
    public String removeBookFromFavourite(@PathVariable("id") int id, Principal principal) {
        Book book = bookRepository.getOne(id);
        User user = userRepository.findByUsername(principal.getName());
        userRepository.deleteFavouriteBook(user.getId(), book.getId());
        return "redirect:/book/bookList/page/1";
    }

    @RequestMapping(value = "/403")
    public String Error403() {
        return "403";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveBook(Book book) {
        bookRepository.save(book);
        return "redirect:bookList";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editBook(@PathVariable("id") int id, Model model) {
        Book book = bookRepository.findById(id);

        model.addAttribute("book", book);
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("bookCategories", bookCategoryRepository.findAll());
        return "editBook";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateBook(Book book) {
        bookRepository.save(book);
        return "redirect:/book/bookList/page/1";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/allByCategory")
    public List<BookDTO> getAllBooksByCategory(@RequestBody BookCategory bookCategory) {
        return bookService.getAllBooksByCategory(bookCategory);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("")
    public BookDTO getBook(@RequestBody String title) {
        return bookService.getBook(title);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/allByAuthor")
    public List<BookDTO> getBooksByAuthor(@RequestBody Author author) {
        return bookService.getBooksByAuthor(author);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") int id, Model model) {
        bookService.deleteBook(id);
        return "redirect:/book/bookList/page/1";
    }
}