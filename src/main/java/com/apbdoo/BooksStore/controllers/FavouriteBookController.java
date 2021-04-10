package com.apbdoo.BooksStore.controllers;

import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.User;
import com.apbdoo.BooksStore.repositories.BookRepository;
import com.apbdoo.BooksStore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/favouriteBook")
@RequiredArgsConstructor
public class FavouriteBookController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @GetMapping(value = "/favouriteBookList/page/{page}")
    public String listFavouriteBooksPageByPage(Model model, @PathVariable("page") int pg, Principal principal) {
        PageRequest pageable = PageRequest.of(pg - 1, 2, Sort.by("id"));

        User user = userRepository.findByUsername(principal.getName());

        Page<Book> bookPage = bookRepository.findAllPageByUserId(user.getId(), pageable);
        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        List<Integer> favouriteBookList = userRepository.getFavouriteBooks(user.getId());
        model.addAttribute("favouriteBookList", favouriteBookList);
        model.addAttribute("activeBookList", true);
        model.addAttribute("books", bookPage.getContent());
        return "favouriteBookList";
    }

    @GetMapping(value = "/removeFavourite/{id}")
    public String removeBookFromFavourite(@PathVariable("id") int id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Book book = bookRepository.getOne(id);
        userRepository.deleteFavouriteBook(user.getId(), book.getId());
        return "redirect:/favouriteBook/favouriteBookList/page/1";
    }
}
