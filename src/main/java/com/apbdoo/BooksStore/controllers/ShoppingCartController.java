package com.apbdoo.BooksStore.controllers;

import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.ShoppingCart;
import com.apbdoo.BooksStore.models.User;
import com.apbdoo.BooksStore.repositories.BookRepository;
import com.apbdoo.BooksStore.repositories.ShoppingCartRepository;
import com.apbdoo.BooksStore.repositories.UserRepository;
import com.apbdoo.BooksStore.services.OrderService;
import com.apbdoo.BooksStore.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;
    private BookRepository bookRepository;
    private ShoppingCartRepository shoppingCartRepository;
    private UserRepository userRepository;
    private OrderService orderService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, BookRepository bookRepository,
                                  ShoppingCartRepository shoppingCartRepository, UserRepository userRepository,
                                  OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.bookRepository = bookRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }
/*  @GetMapping("/add/viewCart")
    public String getAll(Principal principal, Model model) {
        //System.out.print(shoppingCartService.getBooksFromShoppingCart(principal));
        //model.addAttribute("booksFromShoppingCart", shoppingCartService.getBooksFromShoppingCart(principal));
        List<ShoppingCart> shoppingCarts= shoppingCartService.getShoppingCarts(principal);
        model.addAttribute("shoppingCarts", shoppingCarts);
        model.addAttribute("totalPrice",shoppingCartController.totalPriceShoppingCart(principal, model));
        return "viewShoppingCart";
    }*/


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "shoppingCartList/page/{page}")
    public String listShoppingPageByPage(Model model, @PathVariable("page") int pg, Principal principal) {
        PageRequest pageable = PageRequest.of(pg - 1, 2, Sort.by("quantity"));
        User user = userRepository.findByUsername(principal.getName());
        Page<ShoppingCart> shoppingCartPage = shoppingCartRepository.findAllPage(user.getUsername(), pageable);
        int totalPages = shoppingCartPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("activeShoppingList", true);
        model.addAttribute("shoppingCart", shoppingCartRepository.findByUserId(user.getId()));
        model.addAttribute("totalPrice", totalPriceShoppingCart(principal, model));
        return "viewShoppingCart";
    }

    public String totalPriceShoppingCart(Principal principal, Model model) {
        Double totalPrice = 0.0;
        User user = userRepository.findByUsername(principal.getName());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        for (Book b : shoppingCart.getBooks()) {
            totalPrice += b.getPrice();
        }
        int quantity = shoppingCart.getQuantity();
        model.addAttribute("totalPrice", totalPrice);
        return String.format("%.02f", totalPrice);
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    public String addToShoppingCart(@PathVariable("id") int id, Principal principal, Model model) {
        Book book = bookRepository.findById(id);
        User user = userRepository.findByUsername(principal.getName());
        ShoppingCart shoppingCart;
        if (shoppingCartRepository.findByUserId(user.getId()) == null) {
            shoppingCart = new ShoppingCart().setQuantity(1).setBooks(Arrays.asList(book)).setUser(user);
        } else {
            shoppingCart = shoppingCartRepository.findByUserUsername(user.getUsername());
            List<Book> books = shoppingCart.getBooks();
            books.add(book);
            shoppingCart.setQuantity(shoppingCart.getQuantity() + 1).setBooks(books);
        }
        shoppingCartRepository.save(shoppingCart);
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("book", book);
        return "redirect:/book/bookList/page/1";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteFromShoppingCart(@PathVariable("id") int id, Principal principal) {
        shoppingCartService.deleteFromShoppingCart(id, principal);
        return "redirect:/shoppingCart/shoppingCartList/page/1?";
    }

    @GetMapping("/order")
    public String placeOrder(Principal principal, Model model) throws IOException {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("name", userRepository.findByUsername(user.getUsername()));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        List<Book> books = shoppingCart.getBooks();
        orderService.createOrder(books, principal);
        return "viewOrder";
    }
}


