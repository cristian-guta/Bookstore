package com.apbdoo.BooksStore.repositories;


import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    Order findOrderById(int id);
    List<Order> findOrdersByUserId(int id);
    List<Order> findByOrderByIdAsc();
    Optional<Order> findFirstByBooksContains(Book book);
    Order findByUserIdAndOrderDate(int id, LocalDate date);
    List<Order> findByOrderDateBetween(LocalDate date1, LocalDate date2);
    List<Order> findByBooks(Book book);
}
