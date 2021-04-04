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

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, BookService bookService, BookRepository bookRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
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
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);

        return books;
    }



    public void seedCarts() {
        seedCart(1, 4, userRepository.findByUsername("admin"), bookRepository.findAll().get(0));
        seedCart(2, 2, userRepository.findByUsername("user"), bookRepository.findAll().get(1));
    }

    private void seedCart(int id, int quantity, User user, Book book) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart().setId(id).setQuantity(quantity).setUser(user).setBook(book);
            shoppingCartRepository.save(shoppingCart);
        }
    }


   /* public ShoppingCart addToShoppingCart(@RequestBody BookDTO book, Principal principal) {
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
        Book book = bookRepository.findById(id);
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserIdAndBook(user.getId(), book);

        shoppingCartRepository.deleteAll(shoppingCarts);
        return new ResultDTO().setType("success").setMessage("Book removed from cart.");
    }

    public List<ShoppingCart> getShoppingCarts(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserId(user.getId());
        return shoppingCarts;
    }

    public List<Book> getBooksFromShoppingCart(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserId(user.getId());
        List<Book> books = new ArrayList<Book>();
        for (ShoppingCart shoppingCart : shoppingCarts)
            books.add(shoppingCart.getBook());
        return books;
//        return shoppingCartRepository.findByUserId(user.getId());
    }

    public ResultDTO deleteShoppingCart(int id, Principal principal) {
        Book book = bookRepository.findById(id);
        bookRepository.delete(book);
        User user = userRepository.findByUsername(principal.getName());
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserUsernameAndBook(user.getUsername(),book);
        for (ShoppingCart shoppingCart : shoppingCarts)
            shoppingCartRepository.delete(shoppingCart);
        return new ResultDTO().setType("success").setMessage("Book deleted.");
    }
}