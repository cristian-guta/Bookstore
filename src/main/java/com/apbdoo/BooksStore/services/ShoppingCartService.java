package com.apbdoo.BooksStore.services;

import com.apbdoo.BooksStore.dto.BookDTO;
import com.apbdoo.BooksStore.dto.ResultDTO;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.ShoppingCart;
import com.apbdoo.BooksStore.models.User;
import com.apbdoo.BooksStore.repositories.BookRepository;
import com.apbdoo.BooksStore.repositories.ShoppingCartRepository;
import com.apbdoo.BooksStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;
    private BookService bookService;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private OrderService orderService;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, BookService bookService,
                               BookRepository bookRepository, UserRepository userRepository, OrderService orderService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    public List<Book> getRandomElement(List<Book> list, int totalItems) {
        Random rand = new Random();

        List<Book> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {
            int randomIndex = rand.nextInt(list.size());
            newList.add(list.get(randomIndex));
        }
        return newList;
    }

    private List<Book> randomizeBooks() {

        return new ArrayList<>(bookRepository.findAll());
    }


    public void seedCarts() {
        seedCart(1, 4, userRepository.findByUsername("admin"), bookRepository.findAll().get(0));
        seedCart(2, 2, userRepository.findByUsername("user"), bookRepository.findAll().get(1));
    }

    private void seedCart(int id, int quantity, User user, Book book) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id);
        List<Book> books = new ArrayList<>();
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart().setId(id).setQuantity(quantity).setUser(user);
            books.add(book);
            shoppingCart.setBooks(books);
            shoppingCartRepository.save(shoppingCart);
        }
    }


    public ShoppingCart addToShoppingCart(@RequestBody BookDTO book, Principal principal) {
        Book bok = new Book();
        bok.setId(book.getId())
                .setBookCategory(book.getBookCategory())
                .setBookInfo(book.getBookInfo())
                .setPrice(book.getPrice());

        User user = userRepository.findByUsername(principal.getName());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        List<Book> books = shoppingCart.getBooks();
        books.add(bok);
        shoppingCart.setBooks(books);
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }
/*
    public ResultDTO removeFromShoppingCart(@PathVariable("id") int id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        List<Book> books = shoppingCart.getBooks();
        books.remove(books.indexOf(bookRepository.findById(id)));
        shoppingCart.setBooks(books);
        shoppingCartRepository.save(shoppingCart);
        return new ResultDTO().setType("success").setMessage("Book removed from cart.");
    }
    */

    public ResultDTO removeFromShoppingCart(@PathVariable("id") int id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        shoppingCart.setBooks(null);
        shoppingCartRepository.save(shoppingCart);
        return new ResultDTO().setType("success").setMessage("Book removed from cart.");
    }

    public ShoppingCart getShoppingCarts(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return shoppingCartRepository.findByUserId(user.getId());
    }

    public List<Book> getBooksFromShoppingCart(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        return new ArrayList<>(shoppingCart.getBooks());
    }

    public ResultDTO deleteFromShoppingCart(int id, Principal principal) {
        Book book = bookRepository.findById(id);
        User user = userRepository.findByUsername(principal.getName());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserUsername(user.getUsername());
        List<Book> books = shoppingCart.getBooks();
        books.remove(book);
        shoppingCart.setBooks(books);
        shoppingCartRepository.save(shoppingCart);
        return new ResultDTO().setType("success").setMessage("Book deleted.");
    }
}