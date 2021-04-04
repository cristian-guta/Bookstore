package com.apbdoo.BooksStore.dto;

import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.Order;
import com.apbdoo.BooksStore.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int id;

    private Double totalPrice;

    private LocalDate orderDate;

    private User user;

    private List<Book> books;

    public OrderDTO (Order order){
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.user = order.getUser();
        this.books = order.getBooks();
    }
}
