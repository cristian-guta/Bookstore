package com.apbdoo.BooksStore.services;

import com.apbdoo.BooksStore.dto.OrderDTO;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.Order;
import com.apbdoo.BooksStore.models.User;
import com.apbdoo.BooksStore.repositories.BookRepository;
import com.apbdoo.BooksStore.repositories.OrderRepository;
import com.apbdoo.BooksStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    BookRepository bookRepository;
    EmailService emailService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, BookRepository bookRepository, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.emailService = emailService;
    }

    public OrderDTO findOrderById(String id) {
        Optional<Order> optionalOrder = orderRepository.findById(Integer.parseInt(id));
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            return new OrderDTO(order);
        } else {
            return new OrderDTO();
        }
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

    public void seedOrders() {

        seedOrder(1,  userRepository.findByUsername("admin"), randomizeBooks());
//        seedOrder(3,  "25-01-2020", userRepository.findByUsername("user"), randomizeBooks());
//        seedOrder(3,  userRepository.findByUsername("admin"), randomizeBooks(), null);
//        seedOrder(4,  userRepository.findByUsername("root"), randomizeBooks(), null);
    }

    private void seedOrder(int id, User user, List<Book> books) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.now();

        Order order = orderRepository.findOrderById(id);
        if (order == null) {
            order = new Order()
                    .setId(id)
                    .setOrderDate(date)
                    .setUser(user)
                    .setBooks(books);
            orderRepository.save(order);
        }
    }
/*
    public List<OrderDTO> getAllOrders(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<OrderDTO> listOfOrders = new ArrayList<>();
        orderRepository.findOrdersByUserId(user.getId()).forEach(order -> {
            userRepository.findByUsername(user.getUsername());
            bookRepository.findById(order.getId());
            listOfOrders.add(new OrderDTO(order));
        });
        return listOfOrders;
    }*/
public List<Order> getAllOrders(Principal principal) {
    User user = userRepository.findByUsername(principal.getName());
    List<Order> listOfOrders = new ArrayList<>();
    orderRepository.findOrdersByUserId(user.getId()).forEach(order -> {
        userRepository.findByUsername(user.getUsername());
        bookRepository.findById(order.getId());
        listOfOrders.add(order);
    });
    return listOfOrders;
}



    public OrderDTO createOrder(List<Book> books, Principal principal) throws IOException {

        LocalDate date = LocalDate.now();
        Double totalPrice = 0.0;
        User user = userRepository.findByUsername(principal.getName());
        for (Book book : books) {
            totalPrice += book.getPrice();
        }
        Order order = new Order()
                .setOrderDate(date)
                .setUser(user)
                .setBooks(books)
                .setTotalPrice(totalPrice);

        emailService.createPDF(books, principal);
        orderRepository.save(order);
        return new OrderDTO()
                .setOrderDate(order.getOrderDate())
                .setUser(order.getUser())
                .setBooks(order.getBooks())
                .setTotalPrice(order.getTotalPrice());

    }

}
