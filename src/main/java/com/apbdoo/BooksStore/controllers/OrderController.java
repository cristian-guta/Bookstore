package com.apbdoo.BooksStore.controllers;

import com.apbdoo.BooksStore.dto.OrderDTO;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.Order;
import com.apbdoo.BooksStore.models.ShoppingCart;
import com.apbdoo.BooksStore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
/*
    @GetMapping("")
    public List<OrderDTO> getAll(Principal principal) {
        return orderService.getAllOrders(principal);
    }

 */

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable(value = "id") String id) throws IOException {
        return orderService.findOrderById(id);
    }

    @PostMapping("/create")
    public OrderDTO createOrder(@RequestBody List<Book> books, Principal principal) throws IOException {
        return orderService.createOrder(books, principal);
    }


    @GetMapping("/viewCart")
    public String getAll(Principal principal, Model model) {
        //System.out.print(shoppingCartService.getBooksFromShoppingCart(principal));
        //model.addAttribute("booksFromShoppingCart", shoppingCartService.getBooksFromShoppingCart(principal));
        List<Order> orders= orderService.getAllOrders(principal);
        model.addAttribute("orders", orders);
        return "viewOrder";
    }
}